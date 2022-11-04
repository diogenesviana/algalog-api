alter table entrega
add constraint fk_entrega_cliente
foreign key (cliente_id) references cliente (id);