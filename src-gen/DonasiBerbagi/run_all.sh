#!/bin/bash
source ~/.zshrc  

cleanup() {
    echo "Exiting script..."
    pkill -P $$
    exit 1
}

trap cleanup SIGINT

echo "Enter the path to the frontend directory: "
read -p "Enter the path to the frontend directory: " frontend_dir

echo "SELECT 'CREATE DATABASE aisco_product_donasiberbagi' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'aisco_product_donasiberbagi') \gexec" | psql "postgresql://postgres:postgres@localhost"
for file in sql/*.sql; do
    psql -a -f "$file" "postgresql://postgres:postgres@localhost/aisco_product_donasiberbagi"
done

java -cp aisco.product.donasiberbagi --module-path aisco.product.donasiberbagi -m aisco.product.donasiberbagi &

cd $frontend_dir && {
    npm install && {
        npm run json:server &
        npm run start &
    }
}

wait