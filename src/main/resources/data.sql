-- ===========================
-- INSERCIÓN DE CATEGORÍAS
-- ===========================
DELETE FROM categorias;
INSERT INTO categorias (id, nombre, descripcion, activo) VALUES
(1, 'Bebidas', 'Bebidas frías y calientes', true),
(2, 'Entrantes', 'Aperitivos y entrantes para empezar', true),
(3, 'Platos Principales', 'La especialidad de la casa', true),
(4, 'Postres', 'El toque dulce para finalizar', true),
(5, 'Menú Infantil', 'Platos diseñados para los más pequeños', true);
-- ===========================
-- INSERCIÓN DE PRODUCTOS
-- ===========================
INSERT INTO productos (id, nombre, descripcion, precio, stock, disponible, categoria_id) VALUES
(1, 'Coca-Cola', 'Refresco de cola de 33cl', 2.50, 100, true, 1),
(2, 'Agua Mineral sin Gas', 'Botella de agua de 500ml', 1.50, 80, true, 1),
(3, 'Ensalada César', 'Lechuga romana, pollo a la parrilla, croutons y aderezo César', 8.50, 20, true, 2),
(4, 'Croquetas de Jamón Ibérico', 'Ración de 6 unidades de croquetas caseras', 7.00, 25, true, 2),
(5, 'Entrecot a la Parrilla', '300g de entrecot de ternera con patatas y pimientos', 18.00, 15, true, 3),
(6, 'Tarta de Queso Casera', 'Porción de tarta de queso con coulis de frutos rojos', 5.50, 10, true, 4);
-- ==================================
-- INSERCIÓN DE DATOS DEL RESTAURANTE
-- ==================================
INSERT INTO restaurante (id, nombre, direccion, telefono, nif, email, sitio_web, horario) VALUES
(1, 'El Buen Sabor', 'Plaza de la Comida 1, 08001 Barcelona', '930123456', 'B12345678', 'contacto@elbuensabor.es', 'http://www.elbuensabor.es', 'L-D de 12:00 a 23:00');
-- ===========================
-- INSERCIÓN DE DATOS DE MESAS
-- ===========================
INSERT INTO mesa (numero, capacidad, disponible) VALUES (1, 2, true);
INSERT INTO mesa (numero, capacidad, disponible) VALUES (2, 4, true);
INSERT INTO mesa (numero, capacidad, disponible) VALUES (3, 4, false);
INSERT INTO mesa (numero, capacidad, disponible) VALUES (4, 6, true);
INSERT INTO mesa (numero, capacidad, disponible) VALUES (5, 2, true);
INSERT INTO mesa (numero, capacidad, disponible) VALUES (6, 6, true);
INSERT INTO mesa (numero, capacidad, disponible) VALUES (7, 8, false);
INSERT INTO mesa (numero, capacidad, disponible) VALUES (8, 4, true);
-- ===============================
-- INSERCIÓN DE DATOS DE RESERVAS
-- ===============================
INSERT INTO reserva (num_comensales, nombre_cliente, telefono, email, fecha, hora, mesa_id) VALUES
(1, 'Cliente 1', '600111222', 'cliente1@mail.com', '2025-07-02', '13:00:00', 1),
(3, 'Cliente 2', '600222333', 'cliente2@mail.com', '2025-07-02', '13:30:00', 2),
(4, 'Cliente 3', '600333444', 'cliente3@mail.com', '2025-07-02', '14:00:00', 3),
(5, 'Cliente 4', '600444555', 'cliente4@mail.com', '2025-07-02', '14:30:00', 4),
(2, 'Cliente 5', '600555666', 'cliente5@mail.com', '2025-07-02', '15:00:00', 5),
(6, 'Cliente 6', '600666777', 'cliente6@mail.com', '2025-07-02', '15:30:00', 6);
-- ===============================
-- INSERCIÓN DE DATOS PLATOS
-- ===============================

DELETE FROM plato;

ALTER TABLE plato AUTO_INCREMENT = 1; -- (si usas autoincremento)


INSERT INTO plato (nombre, descripcion, categoria, precio, imagen_url, es_caliente)
VALUES
  -- Platos de carne
  ('Entrecot a la parrilla', 'Jugoso entrecot de ternera a la brasa', 'carne', 18.50, '/img/platos/Entrecot-a-la-parrilla.webp', true),
  ('Costillas BBQ', 'Costillas cocinadas a fuego lento con salsa BBQ casera', 'carne', 15.00, '/img/platos/Costillas-BBQ.webp', true),
  ('Pollo al ajillo', 'Clásico plato español con ajo y perejil', 'carne', 12.00, '/img/platos/Pollo-al-ajillo.webp', true),
  ('Conejo a la parrilla', 'Clásico plato español de montaña', 'carne', 10.00, '/img/platos/Conejo-a-la-parrilla.webp', true),
  ('Serranito sevillano', 'Tapa clásica sevillana', 'carne', 7.00, '/img/platos/Serranito-sevillano.webp', true),

  -- Platos de pescado
  ('Lomo de Merluza al Horno', 'Merluza fresca horneada con patatas panaderas y cebolla caramelizada', 'pescado', 14.50, '/img/platos/merluza-al-horno-con-patatas.webp', true),
  ('Tartar de Atún Rojo', 'Atún rojo marinado con soja y sésamo sobre base de aguacate', 'pescado', 13.50, '/img/platos/tartar-de-atún-rojo.webp', false),
  ('Pulpo a la Gallega', 'Pulpo cocido con patatas, pimentón y aceite de oliva virgen extra', 'pescado', 12.00, '/img/platos/pulpo-a-la-gallega.webp', true),
  ('Bacalao al Pil-Pil', 'Tradicional bacalao con salsa emulsionada de ajo y aceite', 'pescado', 10.50, '/img/platos/Bacalao-al-Pil-Pil.webp', true),
  ('Salmón Teriyaki', 'Lomo de salmón glaseado con salsa teriyaki y verduras salteadas', 'pescado', 16.50, '/img/platos/teriyaki-salmon.webp', true),
  ('tartar-de-atún-rojo', 'Delicioso tartar de atún rojo con cebolla caramelizada', 'pescado', 12.30, '/img/platos/tartar-de-atún-rojo.webp', true),

  -- Platos vegetarianos
  ('Risotto de Setas', 'Cremoso arroz arbóreo con mezcla de setas silvestres y parmesano', 'vegetariano', 9.00, '/img/platos/risotto-de-setas.webp', true),
  ('Hamburguesa Vegana', 'Hamburguesa de lentejas y remolacha con pan integral y vegetales frescos', 'vegetariano', 10.30, '/img/platos/Hamburguesa-Vegana.webp', true),
  ('Wok de Verduras', 'Verduras de temporada salteadas al wok con salsa de soja', 'vegetariano', 11.00, '/img/platos/wok-de-verduras.webp', true),
  ('Lasaña de Verduras', 'Capas de berenjena, calabacín y tomate con bechamel vegetal', 'vegetariano', 13.50, '/img/platos/Lasaña-Vegetal.webp', true);


