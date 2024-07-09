
echo "SELECT 'CREATE DATABASE aisco_product_yayasanarga' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'aisco_product_yayasanarga') \gexec" | psql "postgresql://postgres:postgres@localhost"
for file in sql/*.sql; do
    psql -a -f "$file" "postgresql://postgres:postgres@localhost/aisco_product_yayasanarga"
done

java -cp aisco.product.yayasanarga --module-path aisco.product.yayasanarga -m aisco.product.yayasanarga