DROP TABLE IF EXISTS product, category;

CREATE TABLE category (
  id            SERIAL PRIMARY KEY,
  category_name VARCHAR(255) NOT NULL
);

CREATE TABLE product (
  id           SERIAL PRIMARY KEY,
  product_name VARCHAR(255)     NOT NULL,
  count        INTEGER          NOT NULL,
  price        DOUBLE PRECISION NOT NULL,
  description  VARCHAR(1000),
  category_id  INTEGER REFERENCES category (id)
);

INSERT INTO category(category_name) VALUES ('First');
INSERT INTO category(category_name) VALUES ('Second');


INSERT INTO product(product_name, count, price, description, category_id) VALUES ('Testing test', 2, 3.15, 'desc', 1);
INSERT INTO product(product_name, count, price, description, category_id) VALUES ('Frog', 22, 13.15, 'desc', 2);
INSERT INTO product(product_name, count, price, description, category_id) VALUES ('Test', 32, 43.15, 'desc', 1);
INSERT INTO product(product_name, count, price, description, category_id) VALUES ('Front', 112, 223.15, 'desc', 1);
