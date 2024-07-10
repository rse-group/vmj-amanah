echo SELECT 'CREATE DATABASE aisco_product_paymentgateway' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'aisco_product_paymentgateway') \gexec | psql "postgresql://postgres:postgres@localhost"
for %%G in (sql/*.sql) do psql -a -f sql/%%G "postgresql://postgres:postgres@localhost/aisco_product_paymentgateway"

java -cp aisco.product.paymentgateway --module-path aisco.product.paymentgateway -m aisco.product.paymentgateway