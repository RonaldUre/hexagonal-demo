-- ==============================
-- Customers
-- ==============================
CREATE TABLE customers (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    email         VARCHAR(255) UNIQUE NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==============================
-- Restaurants
-- ==============================
CREATE TABLE restaurants (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    active        BOOLEAN DEFAULT TRUE,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==============================
-- Orders
-- ==============================
CREATE TABLE orders (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id   BIGINT NOT NULL,
    restaurant_id BIGINT NOT NULL,
    status        VARCHAR(50) NOT NULL,   -- ej: CREATED, PAID, APPROVED, CANCELLED
    total_amount  DECIMAL(10,2) NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) REFERENCES customers(id),
    CONSTRAINT fk_order_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);

-- ==============================
-- Payments
-- ==============================
CREATE TABLE payments (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id      BIGINT NOT NULL,
    amount        DECIMAL(10,2) NOT NULL,
    status        VARCHAR(50) NOT NULL,   -- ej: PENDING, COMPLETED, FAILED
    transaction_id VARCHAR(100),
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_payment_order FOREIGN KEY (order_id) REFERENCES orders(id)
);

-- ==============================
-- Outbox (para eventos de dominio)
-- ==============================
CREATE TABLE outbox (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    aggregate_id  BIGINT NOT NULL,          -- ej: order_id
    aggregate_type VARCHAR(100) NOT NULL,   -- ej: "Order"
    type          VARCHAR(100) NOT NULL,    -- ej: "OrderCreatedEvent"
    payload       JSON NOT NULL,            -- evento serializado
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    processed     BOOLEAN DEFAULT FALSE
);
