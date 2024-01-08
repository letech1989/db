INSERT INTO db_bank.account (id, customer_number, account_number, current_balance, customer_name, address, email, mobile)
VALUES (1, '123456789043567', '44494444', 1071.78,  'Shamsher Yadav','Kastrup 2770 copenhagen', 'shamsherdemo@gmail.com', 12343223);


INSERT INTO db_bank.transaction (id, customer_number, account_number, owner_name, amount, initiation_date, completion_date, reference)
VALUES (1, '012345678901234', '44444444', 'Shamsher Yadav', 100.00, '2023-12-18 10:30', '2023-12-18 10:32', 'cash withdrwal');

