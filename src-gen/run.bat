echo SELECT 'CREATE DATABASE aisco_product_' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'aisco_product_') \gexec | psql "postgresql://postgres:postgres@localhost"
for %%G in (sql/*.sql) do psql -a -f sql/%%G "postgresql://postgres:postgres@localhost/aisco_product_"

java -cp aisco.product. --module-path aisco.product. -m aisco.product.