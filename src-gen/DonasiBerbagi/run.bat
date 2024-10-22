echo SELECT 'CREATE DATABASE aisco_product_donasiberbagi' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'aisco_product_donasiberbagi') \gexec | psql "postgresql://postgres:postgres@localhost"
for %%G in (sql/*.sql) do psql -a -f sql/%%G "postgresql://postgres:postgres@localhost/aisco_product_donasiberbagi"

java -cp aisco.product.donasiberbagi --module-path aisco.product.donasiberbagi -m aisco.product.donasiberbagi