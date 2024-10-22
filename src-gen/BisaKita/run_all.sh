#!/bin/bash

cleanup() {
    echo "Exiting script..."
    pkill -P $$
    exit 1
}

trap cleanup SIGINT

read -p "Enter the path to the frontend directory: " frontend_dir

echo "SELECT 'CREATE DATABASE aisco_product_bisakita' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'aisco_product_bisakita') \gexec" | psql "postgresql://postgres:postgres@localhost"
for file in sql/*.sql; do
    psql -a -f "$file" "postgresql://postgres:postgres@localhost/aisco_product_bisakita"
done

java -cp aisco.product.bisakita --module-path aisco.product.bisakita -m aisco.product.bisakita &

cd $frontend_dir && {
    npm install && {
        npm run json:server &
        npm run start &
    }
}

wait