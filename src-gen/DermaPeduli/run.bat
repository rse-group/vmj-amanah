echo SELECT 'CREATE DATABASE aisco_product_dermapeduli' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'aisco_product_dermapeduli') \gexec | psql "postgresql://postgres:postgres@localhost"
for %%G in (sql/*.sql) do psql -a -f sql/%%G "postgresql://postgres:postgres@localhost/aisco_product_dermapeduli"

java -cp aisco.product.dermapeduli --module-path aisco.product.dermapeduli -m aisco.product.dermapeduli