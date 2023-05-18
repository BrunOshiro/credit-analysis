CREATE TABLE IF NOT EXISTS credit_analysis (
    id uuid NOT NULL,
    client_id uuid,
    client_cpf VARCHAR(11),
    client_name VARCHAR(100),
    monthly_income DOUBLE,
    requested_amount DOUBLE,
    approved_limit DOUBLE,
    annual_interest DOUBLE,
    withdraw DOUBLE,
    creation_date DATE
    PRIMARY KEY (id)
);