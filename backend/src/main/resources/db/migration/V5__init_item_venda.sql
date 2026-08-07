CREATE TABLE item_venda (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(19,2) NOT NULL,
    subtotal DECIMAL(19,2) NOT NULL,
    venda_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL
);
