INSERT INTO payment.credit_entry(id, customer_id, total_credit_amount, version)
    VALUES('5753f362-da02-4b4f-ab7f-e24fba1dec63', 'd215b5f8-0249-4dc5-89a3-51fd148cfb41', 50000.00, 0);
INSERT INTO payment.credit_history(id, customer_id, amount, type)
    VALUES('5753f362-da02-4b4f-ab7f-e24fba1dec65', 'd215b5f8-0249-4dc5-89a3-51fd148cfb41', 10000.00, 'CREDIT');
INSERT INTO payment.credit_history(id, customer_id, amount, type)
    VALUES('5753f362-da02-4b4f-ab7f-e24fba1dec66', 'd215b5f8-0249-4dc5-89a3-51fd148cfb41', 60000.00, 'CREDIT');
INSERT INTO payment.credit_history(id, customer_id, amount, type)
    VALUES('5753f362-da02-4b4f-ab7f-e24fba1dec67', 'd215b5f8-0249-4dc5-89a3-51fd148cfb41', 20000.00, 'DEBIT');

INSERT INTO payment.credit_entry(id, customer_id, total_credit_amount, version)
    VALUES('5753f362-da02-4b4f-ab7f-e24fba1dec70', 'd215b5f8-0249-4dc5-89a3-51fd148cfb42', 100.00, 0);
INSERT INTO payment.credit_history(id, customer_id, amount, type)
    VALUES('5753f362-da02-4b4f-ab7f-e24fba1dec71', 'd215b5f8-0249-4dc5-89a3-51fd148cfb42', 100.00, 'CREDIT');