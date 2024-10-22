echo SELECT 'CREATE DATABASE aisco_product_bisakita' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'aisco_product_bisakita') \gexec | psql "postgresql://postgres:postgres@localhost"
for %%G in (sql/*.sql) do psql -a -f sql/%%G "postgresql://postgres:postgres@localhost/aisco_product_bisakita"

java -cp aisco.product.bisakita --module-path aisco.product.bisakita -m aisco.product.bisakita