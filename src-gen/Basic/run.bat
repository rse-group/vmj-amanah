echo SELECT 'CREATE DATABASE aisco_product_basic' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'aisco_product_basic') \gexec | psql "postgresql://postgres:postgres@localhost"
for %%G in (sql/*.sql) do psql -a -f sql/%%G "postgresql://postgres:postgres@localhost/aisco_product_basic"

java -cp aisco.product.basic --module-path aisco.product.basic -m aisco.product.basic