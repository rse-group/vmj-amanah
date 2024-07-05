echo SELECT 'CREATE DATABASE aisco_product_houseofhopes' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'aisco_product_houseofhopes') \gexec | psql "postgresql://postgres:postgres@localhost"
for %%G in (sql/*.sql) do psql -a -f sql/%%G "postgresql://postgres:postgres@localhost/aisco_product_houseofhopes"

java -cp aisco.product.houseofhopes --module-path aisco.product.houseofhopes -m aisco.product.houseofhopes