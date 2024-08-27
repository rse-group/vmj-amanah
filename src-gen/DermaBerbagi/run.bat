echo SELECT 'CREATE DATABASE aisco_product_dermaberbagi' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'aisco_product_dermaberbagi') \gexec | psql "postgresql://postgres:postgres@localhost"
for %%G in (sql/*.sql) do psql -a -f sql/%%G "postgresql://postgres:postgres@localhost/aisco_product_dermaberbagi"

java -cp aisco.product.dermaberbagi --module-path aisco.product.dermaberbagi -m aisco.product.dermaberbagi