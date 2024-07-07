INSERT INTO customers (name, email, age)
VALUES ('John Doe', 'john.doe@example.com', 30),
       ('Peter Red', 'peter@marvel.com', 25),
       ('Jim Carrie', 'jimc@gmail.com', 33),
       ('Jack Smith', 'jack@gmail.com', 29),
       ('Steve Kong', 'steve@gmail.com', 41);

INSERT INTO employers (name, address)
VALUES ('Tech Corp', '123 Tech Avenue'),
       ('Health Inc', '456 Health Street'),
       ('Edu Labs', '789 Education Road');

INSERT INTO accounts (number, currency, balance, customer_id)
VALUES ('2f16e785-06fe-46cd-b7c2-f38d88c4c199', 'USD', 25000.0, 1),
       ('5d124abe-df55-41a5-8286-7bad5e254126', 'CHF', 19000.0, 1),
       ('311aa987-40e3-42a9-ae81-106f18081036', 'GBP', 1000.0, 2),
       ('5cdaf927-6e55-4a5b-a71e-12cb4579bf3f', 'UAH', 68200.0, 3),
       ('95128a67-669b-4682-ab35-a785ecbb3410', 'EUR', 15200.0, 4),
       ('f2760c64-d8b7-4b20-86ce-da3ad3eb5a2a', 'UAH', 75800.0, 5);


INSERT INTO customer_employer (customer_id, employer_id)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (2, 3),
       (3, 1),
       (4, 1),
       (4, 3),
       (5, 2);

INSERT INTO roles (role_name, customer_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
	   ('USER', 3),
	   ('USER', 4),
	   ('ADMIN', 5);
