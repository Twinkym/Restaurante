-- === Categorías iniciales === --
INSERT INTO categoria (id, nombre, descripcion) VALUES
(1, 'Bebidas', 'Bebidas frías y calientes'),
(2, 'Entrantes', 'Aperitivos y entrantes'),
(3, 'Platos Principales', 'Comidas fuertes' ),
(4, 'Postres', 'Dulces y frutas'),
(5, 'Menú infantil', 'Platos para niños');

-- === Productos iniciales === --
INSERT INTO producto (id, nombre, descripcion, precio, stock, categoria_id) VALUES
(1, 'Coca-Cola', 'Refresco de cola de 33cl', 1.50, 50, 1),
(2, 'Agua Mineral', 'Agua sin gas de 500ml', 1.00, 30, 1),
(3, 'Ensalada César', 'Lechuga, pollo, parmesano y salsa', 6.50, 20, 2),
(4, 'Croquetas Caseras', 'Jamón ibérico y bechamel', 5.00, 25, 2),
(5, 'Lasaña de Carne', 'Lasaña tradicional al horno', 8.00, 15, 3),
(6, 'Tarta de Queso', 'Con base de galleta y mermelada de fresa', 4.00, 10, 4),
(7, 'Mini hamburguesa', 'Con papas y zumo', 5.50, 12, 5);