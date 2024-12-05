CREATE TABLE fine
(
    id                 BIGINT IDENTITY PRIMARY KEY,
    employee_type      NVARCHAR(255)  NOT NULL,
    signature          NVARCHAR(255)  NULL,
    violation_date     DATE           NULL,
    violation_reason   NVARCHAR(255)  NOT NULL,
    amount             FLOAT NOT NULL,
    currency           NVARCHAR(255)  NOT NULL,
    administrative_fee FLOAT DEFAULT 100.00,
    pdf                NVARCHAR(255)  NULL,
    payment_deadline   DATE           NULL
);
CREATE TABLE employee
(
    id            BIGINT IDENTITY PRIMARY KEY,
    first_name    NVARCHAR(255) NOT NULL,
    last_name     NVARCHAR(255) NOT NULL,
    employee_type NVARCHAR(255) NOT NULL,
    job_status    NVARCHAR(255) NOT NULL,
    company_name  NVARCHAR(255) NULL
);
