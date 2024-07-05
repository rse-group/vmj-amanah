echo SELECT 'CREATE DATABASE aisco_product_blog' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'aisco_product_blog') \gexec | psql "postgresql://postgres:postgres@localhost"
for %%G in (sql/*.sql) do psql -a -f sql/%%G "postgresql://postgres:postgres@localhost/aisco_product_blog"

java -cp aisco.product.blog --module-path aisco.product.blog -m aisco.product.blog