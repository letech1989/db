CREATE SCHEMA db_bank;



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
