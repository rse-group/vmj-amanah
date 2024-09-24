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

echo "SELECT 'CREATE DATABASE aisco_product_goodsdonation' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'aisco_product_goodsdonation') \gexec" | psql "postgresql://postgres:postgres@localhost"
for file in sql/*.sql; do
    psql -a -f "$file" "postgresql://postgres:postgres@localhost/aisco_product_goodsdonation"
done

java -cp aisco.product.goodsdonation --module-path aisco.product.goodsdonation -m aisco.product.goodsdonation &

cd $frontend_dir && {
    npm install && {
        npm run json:server &
        npm run start &
    }
}

wait