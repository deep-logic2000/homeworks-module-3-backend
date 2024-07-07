DROP TABLE IF EXISTS customer_employer;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS employers;
DROP TABLE IF EXISTS roles;

CREATE TABLE customers
(
    id                 SERIAL PRIMARY KEY,
    name               VARCHAR(255)        NOT NULL,
    email              VARCHAR(255) UNIQUE NOT NULL,
    age                INT                 NOT NULL,
    password           VARCHAR(128),
    phone_number       VARCHAR(20),
    created_date       TIMESTAMP NULL,
    last_modified_date TIMESTAMP NULL,
    enabled            BOOLEAN DEFAULT TRUE NOT NULL
);

CREATE TABLE employers
(
    id                 SERIAL PRIMARY KEY,
    name               VARCHAR(255) NOT NULL,
    address            VARCHAR(255) NOT NULL,
    created_date       TIMESTAMP NULL,
    last_modified_date TIMESTAMP NULL
);

CREATE TABLE accounts
(
    id                 SERIAL PRIMARY KEY,
    number             VARCHAR(36) UNIQUE NOT NULL,
    currency           VARCHAR(3)         NOT NULL,
    balance            DOUBLE PRECISION   NOT NULL DEFAULT 0.0,
    customer_id        BIGINT             NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers (id),
    created_date       TIMESTAMP NULL,
    last_modified_date TIMESTAMP NULL
);


CREATE TABLE customer_employer
(
    customer_id BIGINT NOT NULL,
    employer_id BIGINT NOT NULL,
    PRIMARY KEY (customer_id, employer_id),
    FOREIGN KEY (customer_id) REFERENCES customers (id),
    FOREIGN KEY (employer_id) REFERENCES employers (id)
);

CREATE TABLE roles
(
    role_id   SERIAL PRIMARY KEY,
    role_name VARCHAR(30),
    customer_id BIGINT,
    FOREIGN KEY (customer_id) REFERENCES customers (id)
);