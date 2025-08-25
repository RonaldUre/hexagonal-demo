-- V2: Add active field to customers table
-- This field controls whether a customer can login (true) or is blocked (false)

ALTER TABLE customers 
ADD COLUMN active BOOLEAN NOT NULL DEFAULT TRUE;

-- Add index for better performance when filtering active customers
CREATE INDEX idx_customers_active ON customers(active);

-- Add composite index for active customers by email (useful for login queries)
CREATE INDEX idx_customers_email_active ON customers(email, active);