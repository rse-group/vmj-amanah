echo SELECT 'CREATE DATABASE aisco_product_rumahamara' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'aisco_product_rumahamara') \gexec | psql "postgresql://postgres:postgres@localhost"
for %%G in (sql/*.sql) do psql -a -f sql/%%G "postgresql://postgres:postgres@localhost/aisco_product_rumahamara"

java -cp aisco.product.rumahamara --module-path aisco.product.rumahamara -m aisco.product.rumahamara