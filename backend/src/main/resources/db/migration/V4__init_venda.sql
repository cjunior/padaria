CREATE TABLE venda (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    valor_total DECIMAL(19,2) NOT NULL,
    status VARCHAR(255) NOT NULL,
    forma_pagamento VARCHAR(255),
    criado_em TIMESTAMP NOT NULL,
    cliente_id BIGINT
);
