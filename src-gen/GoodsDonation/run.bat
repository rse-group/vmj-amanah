echo SELECT 'CREATE DATABASE aisco_product_goodsdonation' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'aisco_product_goodsdonation') \gexec | psql "postgresql://postgres:postgres@localhost"
for %%G in (sql/*.sql) do psql -a -f sql/%%G "postgresql://postgres:postgres@localhost/aisco_product_goodsdonation"

java -cp aisco.product.goodsdonation --module-path aisco.product.goodsdonation -m aisco.product.goodsdonation