#!/bin/bash
source ~/.zshrc  

cleanup() {
    pkill -P $$
    rm java.log
    exit 1
}

trap cleanup SIGINT

echo "SELECT 'CREATE DATABASE aisco_product_withdraw' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'aisco_product_withdraw') \gexec" | psql "postgresql://postgres:@localhost"

java -cp aisco.product.withdraw --module-path aisco.product.withdraw -m aisco.product.withdraw 2>&1 | tee java.log &
JAVA_PID=$!
TEE_PID=$(pgrep -n tee)
tail -f java.log --pid=$TEE_PID | while read -r LINE; do
    if [[ "$LINE" == *"== CREATING OBJECTS AND BINDING ENDPOINTS =="* ]]; then
        break
    fi
done

for file in sql/*.sql; do
    psql -a -f "$file" "postgresql://postgres:@localhost/aisco_product_withdraw"
done

wait