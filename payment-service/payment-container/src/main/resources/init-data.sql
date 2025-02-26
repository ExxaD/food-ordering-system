INSERT INTO payment.credit_entry(id, customer_id, total_credit_amount)
    VALUES('5753f362-da02-4b4f-ab7f-e24fba1dec63', 'd215b5f8-0249-4dc5-89a3-51fd148cfb41', 500.00);
INSERT INTO payment.credit_history(id, customer_id, amount, type)
    VALUES('5753f362-da02-4b4f-ab7f-e24fba1dec65', 'd215b5f8-0249-4dc5-89a3-51fd148cfb41', 100.00, 'CREDIT');
INSERT INTO payment.credit_history(id, customer_id, amount, type)
    VALUES('5753f362-da02-4b4f-ab7f-e24fba1dec66', 'd215b5f8-0249-4dc5-89a3-51fd148cfb41', 600.00, 'CREDIT');
INSERT INTO payment.credit_history(id, customer_id, amount, type)
    VALUES('5753f362-da02-4b4f-ab7f-e24fba1dec67', 'd215b5f8-0249-4dc5-89a3-51fd148cfb41', 200.00, 'DEBIT');

INSERT INTO payment.credit_entry(id, customer_id, total_credit_amount)
    VALUES('5753f362-da02-4b4f-ab7f-e24fba1dec70', 'd215b5f8-0249-4dc5-89a3-51fd148cfb42', 100.00);
INSERT INTO payment.credit_history(id, customer_id, amount, type)
    VALUES('5753f362-da02-4b4f-ab7f-e24fba1dec71', 'd215b5f8-0249-4dc5-89a3-51fd148cfb42', 100.00, 'CREDIT');