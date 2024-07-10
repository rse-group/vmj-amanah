echo SELECT 'CREATE DATABASE aisco_product_amanahgregdemo' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'aisco_product_amanahgregdemo') \gexec | psql "postgresql://postgres:postgres@localhost"
for %%G in (sql/*.sql) do psql -a -f sql/%%G "postgresql://postgres:postgres@localhost/aisco_product_amanahgregdemo"

java -cp aisco.product.amanahgregdemo --module-path aisco.product.amanahgregdemo -m aisco.product.amanahgregdemo