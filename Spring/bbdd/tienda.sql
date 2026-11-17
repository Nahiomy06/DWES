CREATE DATABASE tienda_decroly CHARACTER SET utf8mb4;
CREATE USER 'tienda'@'localhost' IDENTIFIED BY 'Tienda2026!';
GRANT ALL PRIVILEGES ON tienda_decroly.* TO 'tienda'@'localhost';

USE tienda_decroly;
CREATE TABLE prueba_jdbc (
 id BIGINT AUTO_INCREMENT PRIMARY KEY,
 nombre VARCHAR(100) NOT NULL,
 precio DECIMAL(10,2) NOT NULL,
 stock INT NOT NULL
);
INSERT INTO prueba_jdbc (nombre, precio, stock) VALUES
 ('Teclado mecanico', 49.90, 15),
 ('Raton inalambrico', 19.95, 40),
 ('Monitor 24 pulgadas', 139.00, 8);
