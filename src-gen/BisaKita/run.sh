
echo "SELECT 'CREATE DATABASE aisco_product_bisakita' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'aisco_product_bisakita') \gexec" | psql "postgresql://postgres:@localhost"
for file in sql/*.sql; do
    psql -a -f "$file" "postgresql://postgres:@localhost/aisco_product_bisakita"
done

java -cp aisco.product.bisakita --module-path aisco.product.bisakita -m aisco.product.bisakita