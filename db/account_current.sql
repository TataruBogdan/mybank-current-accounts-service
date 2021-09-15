

DROP TABLE IF EXISTS account_current;

CREATE TABLE account_current(
    iban text PRIMARY KEY,
	balance NUMERIC(10,2) NOT NULL DEFAULT 0,
	individual_id int,
	start_date date,
	primary_account boolean,
    current_status status
);





