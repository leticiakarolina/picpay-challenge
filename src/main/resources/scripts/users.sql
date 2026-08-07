INSERT IGNORE INTO users (
    first_name,
    last_name,
    cpf,
    email,
    password_auth,
    cnpj
) VALUES
('João',     'Silva',     '12345678901', 'joao@email.com',     '$2a$10$hash1', NULL),
('Maria',    'Oliveira',  '23456789012', 'maria@email.com',    '$2a$10$hash2', NULL),
('Carlos',   'Souza',     '34567890123', 'carlos@email.com',   '$2a$10$hash3', NULL),
('Ana',      'Costa',     '45678901234', 'ana@email.com',      '$2a$10$hash4', NULL),
('Fernanda', 'Lima',      '56789012345', 'fernanda@email.com', '$2a$10$hash5', NULL),

('Mercado',  'XPTO',      '67890123456', 'xpto@email.com',     '$2a$10$hash6', '12345678000195'),
('Loja',     'Tech',      '78901234567', 'tech@email.com',     '$2a$10$hash7', '98765432000111'),
('Farmácia', 'Vida',      '89012345678', 'vida@email.com',     '$2a$10$hash8', '11222333000144'),
('Padaria',  'Central',   '90123456789', 'central@email.com',  '$2a$10$hash9', '44555666000177'),
('Livraria', 'Saber',     '01234567890', 'saber@email.com',    '$2a$10$hash10','77888999000122');