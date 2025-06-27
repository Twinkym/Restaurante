-- ==============================================================
-- SCRIPT DE INSERCIÓN DE DATOS PARA restaurante_db
-- ASEGÚRESE QUE LA BASE DE DATOS 'restaurante_db' YA HA SIDO CREADA
-- Y QUE LA ESTRUCTURA DE LAS TABLAS YA EXISTE (POR EJEMPLO, VIA JPA/Hibernate)
-- ==============================================================

-- ===========================
-- LIMPIEZA DE TABLAS EXISTENTES
-- ===========================
-- Esto asegura que al ejecutar el script múltiples veces, no se dupliquen los datos
-- Se debe respetar el orden de las tablas debido a las restricciones de clave foránea
-- (Primero las que dependen de otras, luego sus dependencias)

-- Deshabilitar restricciones de clave foránea temporalmente si es necesario para DELETE ALL
-- (La sintaxis varía según la base de datos, esto es un ejemplo general)
-- SET REFERENTIAL_INTEGRITY FALSE; -- Para H2/HSQLDB
-- PRAGMA foreign_keys = OFF;        -- Para SQLite
-- ALTER TABLE tabla_hija NOCHECK CONSTRAINT FK_restriction; -- Para SQL Server (por tabla)

-- DELETE FROM reserva_platos_solicitados; -- Si existe una tabla de unión para platos en reserva
DELETE FROM reserva;
DELETE FROM mesa;
DELETE FROM plato;
DELETE FROM productos;
DELETE FROM categorias;
DELETE FROM restaurante; -- Esta tabla solo tiene una fila, pero la eliminamos para asegurar

-- Habilitar restricciones de clave foránea de nuevo
-- SET REFERENTIAL_INTEGRATE TRUE; -- Para H2/HSQLDB
-- PRAGMA foreign_keys = ON;         -- Para SQLite
-- ALTER TABLE tabla_hija CHECK CONSTRAINT FK_restriction; -- Para SQL Server (por tabla)


-- ===========================
-- INSERCIÓN DE CATEGORÍAS
-- ===========================
INSERT INTO categorias (id, nombre, descripcion, activo) VALUES
(1, 'Bebidas', 'Bebidas frías y calientes'),
(2, 'Entrantes', 'Aperitivos y entrantes para empezar'),
(3, 'Platos Principales', 'La especialidad de la casa'),
(4, 'Postres', 'El toque dulce para finalizar'),
(5, 'Menú Infantil', 'Platos diseñados para los más pequeños');

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
-- Si 'restaurante' es una tabla que se espera que tenga solo una fila y el ID está gestionado
-- por la aplicación o es fijo, puedes usar INSERT OR REPLACE INTO o DELETE + INSERT.
-- Dado que solo hay un registro, DELETE + INSERT es simple y efectivo.
INSERT INTO restaurante (id, nombre, direccion, telefono, nif, email, sitio_web, horario) VALUES
(1, 'El Buen Sabor', 'Plaza de la Comida 1, 08001 Barcelona', '930123456', 'B12345678', 'contacto@elbuensabor.es', 'http://www.elbuensabor.es', 'L-D de 12:00 a 23:00');

-- ===========================
-- INSERCIÓN DE DATOS DE MESAS
-- ===========================
-- Si 'mesa' tiene un ID autoincrement, no debes especificar el ID
INSERT INTO mesa (numero, capacidad, disponible) VALUES (1, 2, true);
INSERT INTO mesa (numero, capacidad, disponible) VALUES (2, 4, true);
INSERT INTO mesa (numero, capacidad, disponible) VALUES (3, 4, false);
INSERT INTO mesa (numero, capacidad, disponible) VALUES (4, 6, true);
INSERT INTO mesa (numero, capacidad, disponible) VALUES (5, 2, true);
INSERT INTO mesa (numero, capacidad, disponible) VALUES (6, 6, true);
INSERT INTO mesa (numero, capacidad, disponible) VALUES (7, 8, false);
INSERT INTO mesa (numero, capacidad, disponible) VALUES (8, 4, true);

-- ===============================
-- INSERCIÓN DE DATOS PLATOS
-- (Asumo que 'plato' tiene un ID autoincrement, por lo que no se inserta explícitamente)
-- ===============================
INSERT INTO plato (nombre, descripcion, categoria, precio, imagen_url) VALUES
  ('Entrecot a la parrilla', 'Jugoso entrecot de ternera a la brasa', 'carne', 18.50, '/img/platos/entrecot.webp'),
  ('Costillas BBQ', 'Costillas cocinadas a fuego lento con salsa BBQ casera', 'carne', 15.00, '/img/platos/costillas.webp'),
  ('Pollo al ajillo', 'Clásico plato español con ajo y perejil', 'carne', 12.00, '/img/platos/pollo-ajillo.webp'),
  ('Conejo a la parrilla', 'Clásico plato español de montaña', 'carne', 10.00, '/img/platos/conejo-a-la-parrilla.webp'),
  ('Serranito sevillano', 'Tapa clásica sevillana', 'carne', 7.00, '/img/platos/Serranito-sevillano.webp'),
  ('Lomo de Merluza al Horno', 'Merluza fresca horneada con patatas panaderas y cebolla caramelizada', 'pescado', 14.50, '/img/platos/merluza-horno.webp'),
  ('Tartar de Atún Rojo', 'Atún rojo marinado con soja y sésamo sobre base de aguacate', 'pescado', 16.00, '/img/platos/tartar-atun.webp'),
  ('Pulpo a la Gallega', 'Pulpo cocido con patatas, pimentón y aceite de oliva virgen extra', 'pescado', 17.00, '/img/platos/pulpo-gallega.webp'),
  ('Bacalao al Pil-Pil', 'Tradicional bacalao con salsa emulsionada de ajo y aceite', 'pescado', 15.50, '/img/platos/bacalao-pilpil.webp'),
  ('Salmón Teriyaki', 'Lomo de salmón glaseado con salsa teriyaki y verduras salteadas', 'pescado', 16.50, '/img/platos/salmon-teriyaki.webp'),
  ('Risotto de Setas', 'Cremoso arroz arbóreo con mezcla de setas silvestres y parmesano', 'vegetariano', 13.00, '/img/platos/risotto-setas.webp'),
  ('Hamburguesa Vegana', 'Hamburguesa de lentejas y remolacha con pan integral y vegetales frescos', 'vegetariano', 12.00, '/img/platos/hamburguesa-vegana.webp'),
  ('Wok de Verduras', 'Verduras de temporada salteadas al wok con salsa de soja', 'vegetariano', 11.00, '/img/platos/wok-verduras.webp'),
  ('Ensalada de Quinoa', 'Quinoa con aguacate, tomate cherry, pepino y vinagreta cítrica', 'vegetariano', 10.50, '/img/platos/ensalada-quinoa.webp'),
  ('Lasaña de Verduras', 'Capas de berenjena, calabacín y tomate con bechamel vegetal', 'vegetariano', 13.50, '/img/platos/lasagna-vegetal.webp');


-- ===============================
-- INSERCIÓN DE DATOS DE RESERVAS
-- ===============================
-- Nota: Los IDs de mesa (mesa_id) deben existir en la tabla 'mesa'.
-- Si 'reserva' tiene un ID autoincrement, no debes especificar el ID.
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


INSERT INTO plato (nombre, descripcion, categoria, precio, imagen_url)
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
INSERT INTO plato (nombre, descripcion, categoria, precio, imagen_url)
VALUES
  -- Platos de carne
  ('Entrecot a la parrilla', 'Jugoso entrecot de ternera a la brasa', 'carne', 18.50, '/img/platos/entrecot.webp', true),
  ('Costillas BBQ', 'Costillas cocinadas a fuego lento con salsa BBQ casera', 'carne', 15.00, '/img/platos/costillas.webp', true),
  ('Pollo al ajillo', 'Clásico plato español con ajo y perejil', 'carne', 12.00, '/img/platos/pollo-ajillo.webp', true),
  ('Conejo a la parrilla', 'Clásico plato español de montaña', 'carne', 10.00, '/img/platos/conejo-a-la-parrilla.webp', true),
  ('Serranito sevillano', 'Tapa clásica sevillana', 'carne', 7.00, '/img/platos/Serranito-sevillano.webp', true),

  -- Platos de pescado
  ('Lomo de Merluza al Horno', 'Merluza fresca horneada con patatas panaderas y cebolla caramelizada', 'pescado', 14.50, '/img/platos/merluza-horno.webp', true),
  ('Tartar de Atún Rojo', 'Atún rojo marinado con soja y sésamo sobre base de aguacate', 'pescado', 16.00, '/img/platos/tartar-atun.webp', false),
  ('Pulpo a la Gallega', 'Pulpo cocido con patatas, pimentón y aceite de oliva virgen extra', 'pescado', 17.00, '/img/platos/pulpo-gallega.webp', true),
  ('Bacalao al Pil-Pil', 'Tradicional bacalao con salsa emulsionada de ajo y aceite', 'pescado', 15.50, '/img/platos/bacalao-pilpil.webp', true),
  ('Salmón Teriyaki', 'Lomo de salmón glaseado con salsa teriyaki y verduras salteadas', 'pescado', 16.50, '/img/platos/salmon-teriyaki.webp', true),

  -- Platos vegetarianos
  ('Risotto de Setas', 'Cremoso arroz arbóreo con mezcla de setas silvestres y parmesano', 'vegetariano', 13.00, '/img/platos/risotto-setas.webp', true),
  ('Hamburguesa Vegana', 'Hamburguesa de lentejas y remolacha con pan integral y vegetales frescos', 'vegetariano', 12.00, '/img/platos/hamburguesa-vegana.webp', true),
  ('Wok de Verduras', 'Verduras de temporada salteadas al wok con salsa de soja', 'vegetariano', 11.00, '/img/platos/wok-verduras.webp', true),
  ('Ensalada de Quinoa', 'Quinoa con aguacate, tomate cherry, pepino y vinagreta cítrica', 'vegetariano', 10.50, '/img/platos/ensalada-quinoa.webp', false),
  ('Lasaña de Verduras', 'Capas de berenjena, calabacín y tomate con bechamel vegetal', 'vegetariano', 13.50, '/img/platos/lasagna-vegetal.webp', true);



