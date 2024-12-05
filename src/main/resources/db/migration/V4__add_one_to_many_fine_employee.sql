ALTER TABLE Fine
    ADD employee_id BIGINT;

ALTER TABLE Fine
    ADD CONSTRAINT FK_employee_id FOREIGN KEY (employee_id) REFERENCES Employee(id);