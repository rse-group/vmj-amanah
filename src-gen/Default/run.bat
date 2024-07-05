echo SELECT 'CREATE DATABASE aisco_product_default' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'aisco_product_default') \gexec | psql "postgresql://postgres:postgres@localhost"
for %%G in (sql/*.sql) do psql -a -f sql/%%G "postgresql://postgres:postgres@localhost/aisco_product_default"

java -cp aisco.product.default --module-path aisco.product.default -m aisco.product.default