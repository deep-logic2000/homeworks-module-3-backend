BEGIN;

DROP TABLE IF EXISTS customer_employer;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS employers;

CREATE TABLE customers
(
    id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(255)        NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    age   INT                 NOT NULL
);

CREATE TABLE employers
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL
);

CREATE TABLE accounts
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    number      VARCHAR(36) UNIQUE NOT NULL,
    currency    VARCHAR(3)         NOT NULL,
    balance DOUBLE NOT NULL DEFAULT 0.0,
    customer_id BIGINT             NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers (id)
);


CREATE TABLE customer_employer
(
    customer_id BIGINT NOT NULL,
    employer_id BIGINT NOT NULL,
    PRIMARY KEY (customer_id, employer_id),
    FOREIGN KEY (customer_id) REFERENCES customers (id),
    FOREIGN KEY (employer_id) REFERENCES employers (id)
);

COMMIT;