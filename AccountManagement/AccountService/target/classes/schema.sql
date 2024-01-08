CREATE SCHEMA db_bank;

CREATE TABLE db_bank.account (
    id bigint NOT NULL PRIMARY KEY,
    customer_number CHAR(15) NOT NULL,
    account_number CHAR(8) NOT NULL,
    current_balance NUMERIC(10,3) NOT NULL,
    customer_name VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    mobile NUMERIC(10) not null,
    email varchar(255),
    UNIQUE (customer_number, account_number)
);

CREATE SEQUENCE db_bank.transaction_sequence START WITH 5;
CREATE TABLE db_bank.transaction (
    id bigint NOT NULL PRIMARY KEY,
    customer_number CHAR(15) NOT NULL,
    account_number CHAR(8) NOT NULL,
    owner_name VARCHAR(255),
    amount NUMERIC(10,3) NOT NULL,
    initiation_date timestamp NOT NULL,
    completion_date TIMESTAMP,
    reference VARCHAR(255)

);
