-- =====================================
-- AgriTrust Database Initialization
-- =====================================
-- Bu dosya Docker ilk çalıştığında otomatik olarak tabloları oluşturur.
-- PostgreSQL Master node üzerinde çalışır, Slave node'a otomatik replike olur.

-- =====================================
-- 1. USERS TABLE (Authentication & Authorization)
-- Aleyna'nın JWT authentication için kullanacağı tablo
-- =====================================

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,  -- Hashed password (BCrypt)
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',  -- ADMIN, PRODUCER, CONSUMER, DISTRIBUTOR
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

-- Index on username for faster authentication queries
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);

-- =====================================
-- 2. PRODUCTS TABLE (Supply Chain Items)
-- Tarımsal ürünlerin temel bilgileri
-- =====================================

CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,  -- Sebze, Meyve, Tahıl, etc.
    origin VARCHAR(100) NOT NULL,  -- Üreticinin lokasyonu / bölgesi
    base_price DECIMAL(10, 2) NOT NULL,  -- Üreticideki başlangıç fiyatı
    current_price DECIMAL(10, 2) NOT NULL,  -- Güncel fiyat (her transferde güncellenir)
    quantity DECIMAL(10, 2) NOT NULL,  -- Miktar (kg, ton, etc.)
    unit VARCHAR(20) DEFAULT 'kg',  -- Birim (kg, ton, adet)
    producer_id INTEGER REFERENCES users(id),  -- Üretici referansı
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'ACTIVE'  -- ACTIVE, SOLD, EXPIRED
);

-- Indexes for frequently queried columns
CREATE INDEX idx_products_category ON products(category);
CREATE INDEX idx_products_origin ON products(origin);
CREATE INDEX idx_products_producer ON products(producer_id);
CREATE INDEX idx_products_status ON products(status);

-- =====================================
-- 3. EVENTS TABLE (Audit Log / Value Chain Tracking)
-- "Distributed Event-Log Architecture" için
-- Tedarik zincirindeki her hareketi kaydeder
-- =====================================

CREATE TABLE IF NOT EXISTS events (
    id SERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    event_type VARCHAR(50) NOT NULL,  -- HARVESTED, TRANSPORTED, STORED, SOLD, INSPECTED
    actor_id INTEGER REFERENCES users(id),  -- Olayı gerçekleştiren kullanıcı
    location VARCHAR(100),  -- Olayın gerçekleştiği lokasyon
    description TEXT,  -- Olay detayları
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    metadata JSONB  -- Ek bilgiler (sıcaklık, nem, kalite skoru vs.)
);

-- Indexes for audit queries
CREATE INDEX idx_events_product ON events(product_id);
CREATE INDEX idx_events_type ON events(event_type);
CREATE INDEX idx_events_actor ON events(actor_id);
CREATE INDEX idx_events_timestamp ON events(timestamp);

-- =====================================
-- 4. TRANSACTIONS TABLE (Transfer Records)
-- Tedarik zincirindeki transferleri takip eder
-- "Compliance Engine" tarafından markup hesaplaması için kullanılır
-- =====================================

CREATE TABLE IF NOT EXISTS transactions (
    id SERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    from_node_id INTEGER REFERENCES users(id),  -- Gönderen (PRODUCER, DISTRIBUTOR, etc.)
    to_node_id INTEGER NOT NULL REFERENCES users(id),  -- Alan (DISTRIBUTOR, CONSUMER, etc.)
    quantity DECIMAL(10, 2) NOT NULL,  -- Transfer edilen miktar
    price_per_unit DECIMAL(10, 2) NOT NULL,  -- Birim fiyat
    total_amount DECIMAL(10, 2) NOT NULL,  -- Toplam tutar
    markup_percentage DECIMAL(5, 2),  -- Fiyat artış yüzdesi (Compliance Engine için)
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_flagged BOOLEAN DEFAULT FALSE,  -- Etik olmayan markup tespit edildiyse TRUE
    notes TEXT
);

-- Indexes for transaction queries and compliance checks
CREATE INDEX idx_transactions_product ON transactions(product_id);
CREATE INDEX idx_transactions_from ON transactions(from_node_id);
CREATE INDEX idx_transactions_to ON transactions(to_node_id);
CREATE INDEX idx_transactions_date ON transactions(transaction_date);
CREATE INDEX idx_transactions_flagged ON transactions(is_flagged);

-- =====================================
-- 5. INITIAL DATA (Seed Data)
-- Test ve development için başlangıç verileri
-- =====================================

-- Admin user (password: admin123 - BCrypt hash)
-- UYARI: Production'da bu silinmeli veya şifre değiştirilmeli!
INSERT INTO users (username, password, email, role) 
VALUES ('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'admin@agritrust.com', 'ADMIN')
ON CONFLICT (username) DO NOTHING;

-- Sample producer user (password: producer123)
INSERT INTO users (username, password, email, role) 
VALUES ('producer1', '$2a$10$dXJ3SW6G7P50lGGkKeJiZeW0cCcEn3W9cFLYFW4qlOUjz9VF.vX7m', 'producer@agritrust.com', 'PRODUCER')
ON CONFLICT (username) DO NOTHING;

-- Sample consumer user (password: consumer123)
INSERT INTO users (username, password, email, role) 
VALUES ('consumer1', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'consumer@agritrust.com', 'CONSUMER')
ON CONFLICT (username) DO NOTHING;

-- =====================================
-- COMPLETION MESSAGE
-- =====================================

DO $$
BEGIN
    RAISE NOTICE '✅ AgriTrust database schema initialized successfully!';
    RAISE NOTICE '📊 Tables created: users, products, events, transactions';
    RAISE NOTICE '🔐 Default admin user: admin / admin123';
    RAISE NOTICE '⚠️  Remember to change default passwords in production!';
END $$;