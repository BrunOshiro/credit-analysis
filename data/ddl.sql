CREATE TABLE IF NOT EXISTS credit_analysis (
    id uuid PRIMARY KEY,
    client_id uuid,
    client_cpf VARCHAR(11),
    client_name VARCHAR(100),
    monthly_income NUMERIC(10,2),
    requested_amount NUMERIC(10,2),
    approved BOOLEAN,
    approved_limit NUMERIC(10,2),
    annual_interest NUMERIC(10,2),
    withdraw NUMERIC(10,2),
    creation_date TIMESTAMP
);