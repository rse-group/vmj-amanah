#!/bin/bash

cleanup() {
    echo "Exiting script..."
    pkill -P $$
    exit 1
}

trap cleanup SIGINT

read -p "Enter the path to the frontend directory: " frontend_dir

echo "SELECT 'CREATE DATABASE aisco_product_amanahgregdemo' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'aisco_product_amanahgregdemo') \gexec" | psql "postgresql://postgres:postgres@localhost"
for file in sql/*.sql; do
    psql -a -f "$file" "postgresql://postgres:postgres@localhost/aisco_product_amanahgregdemo"
done

java -cp aisco.product.amanahgregdemo --module-path aisco.product.amanahgregdemo -m aisco.product.amanahgregdemo &

cd $frontend_dir && {
    npm install && {
        npm run json:server &
        npm run start &
    }
}

wait