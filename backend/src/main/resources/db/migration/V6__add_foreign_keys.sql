ALTER TABLE produto ADD CONSTRAINT fk_produto_categoria_id FOREIGN KEY (categoria_id) REFERENCES categoria(id);
ALTER TABLE venda ADD CONSTRAINT fk_venda_cliente_id FOREIGN KEY (cliente_id) REFERENCES cliente(id);
ALTER TABLE item_venda ADD CONSTRAINT fk_item_venda_venda_id FOREIGN KEY (venda_id) REFERENCES venda(id);
ALTER TABLE item_venda ADD CONSTRAINT fk_item_venda_produto_id FOREIGN KEY (produto_id) REFERENCES produto(id);
