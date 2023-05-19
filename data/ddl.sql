CREATE TABLE IF NOT EXISTS credit_analysis (
    id uuid NOT NULL,
    client_id uuid,
    client_cpf VARCHAR(11),
    client_name VARCHAR(100),
    monthly_income DOUBLE PRECISION,
    requested_amount DOUBLE PRECISION,
    approved BOOLEAN,
    approved_limit DOUBLE PRECISION,
    annual_interest DOUBLE PRECISION,
    withdraw DOUBLE PRECISION,
    creation_date DATE,
    PRIMARY KEY (id)
);