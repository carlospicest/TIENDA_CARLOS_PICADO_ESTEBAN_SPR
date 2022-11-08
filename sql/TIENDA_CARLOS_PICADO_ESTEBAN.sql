-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.4.17-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             11.2.0.6213
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para tienda_carlos_picado_esteban
CREATE DATABASE IF NOT EXISTS `tienda_carlos_picado_esteban` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `tienda_carlos_picado_esteban`;

-- Volcando estructura para tabla tienda_carlos_picado_esteban.cancelaciones_pedido
CREATE TABLE IF NOT EXISTS `cancelaciones_pedido` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_pedido` int(11) NOT NULL,
  `motivo` varchar(255) NOT NULL,
  `estado` varchar(255) NOT NULL,
  `num_solicitud` varchar(255) NOT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `cancelaciones_pedido_ibfk_1` (`id_pedido`),
  CONSTRAINT `cancelaciones_pedido_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla tienda_carlos_picado_esteban.cancelaciones_pedido: ~1 rows (aproximadamente)
/*!40000 ALTER TABLE `cancelaciones_pedido` DISABLE KEYS */;
INSERT INTO `cancelaciones_pedido` (`id`, `id_pedido`, `motivo`, `estado`, `num_solicitud`, `fecha`) VALUES
	(2, 6, 'Producto recibido en mal estado.\r\nLos daños que presenta imposibilitan su uso.', 'PP', 'S0', '2022-11-05 23:12:32'),
	(6, 7, 'El producto no va bien', 'PP', 'CAN_S0', '2022-11-07 12:35:16');
/*!40000 ALTER TABLE `cancelaciones_pedido` ENABLE KEYS */;

-- Volcando estructura para tabla tienda_carlos_picado_esteban.categorias
CREATE TABLE IF NOT EXISTS `categorias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla tienda_carlos_picado_esteban.categorias: ~6 rows (aproximadamente)
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` (`id`, `nombre`, `descripcion`) VALUES
	(1, 'Trajes', ''),
	(2, 'Calcetines', 'Una categoría sutil y pecular'),
	(3, 'Pijamas', ''),
	(4, 'Pantalones', ''),
	(5, 'Camisas', ''),
	(6, 'Camisetas', '');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;

-- Volcando estructura para tabla tienda_carlos_picado_esteban.configuracion
CREATE TABLE IF NOT EXISTS `configuracion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clave` varchar(255) DEFAULT NULL,
  `valor` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla tienda_carlos_picado_esteban.configuracion: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `configuracion` DISABLE KEYS */;
INSERT INTO `configuracion` (`id`, `clave`, `valor`, `tipo`) VALUES
	(1, 'num_factura', '0', 'VARCHAR');
/*!40000 ALTER TABLE `configuracion` ENABLE KEYS */;

-- Volcando estructura para tabla tienda_carlos_picado_esteban.descuentos
CREATE TABLE IF NOT EXISTS `descuentos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) DEFAULT NULL,
  `descuento` float DEFAULT NULL,
  `fecha_inicio` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `fecha_fin` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla tienda_carlos_picado_esteban.descuentos: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `descuentos` DISABLE KEYS */;
/*!40000 ALTER TABLE `descuentos` ENABLE KEYS */;

-- Volcando estructura para tabla tienda_carlos_picado_esteban.detalles_pedido
CREATE TABLE IF NOT EXISTS `detalles_pedido` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_pedido` int(11) NOT NULL,
  `id_producto` int(11) NOT NULL,
  `unidades` int(11) NOT NULL,
  `precio_unidad` double NOT NULL DEFAULT 0,
  `impuesto` double NOT NULL DEFAULT 0,
  `total` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_pedido` (`id_pedido`),
  KEY `id_producto` (`id_producto`),
  CONSTRAINT `detalles_pedido_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id`),
  CONSTRAINT `detalles_pedido_ibfk_2` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla tienda_carlos_picado_esteban.detalles_pedido: ~5 rows (aproximadamente)
/*!40000 ALTER TABLE `detalles_pedido` DISABLE KEYS */;
INSERT INTO `detalles_pedido` (`id`, `id_pedido`, `id_producto`, `unidades`, `precio_unidad`, `impuesto`, `total`) VALUES
	(9, 6, 1, 1, 2236, 21, 2236),
	(10, 6, 2, 5, 1254, 21, 6270),
	(11, 6, 3, 2, 1.76, 21, 3.52),
	(12, 7, 1, 8, 1854.45, 21, 14835.6),
	(13, 7, 2, 8, 2458, 21, 19664),
	(14, 8, 1, 3, 1854.45, 21, 5563.35),
	(15, 8, 2, 2, 2458, 21, 4916),
	(16, 8, 3, 5, 1.76, 21, 8.8),
	(17, 9, 1, 1, 1854.45, 21, 1854.45),
	(18, 9, 2, 1, 2458, 21, 2458),
	(19, 10, 1, 1, 1854.45, 21, 1854.45),
	(20, 10, 2, 1, 2458, 21, 2458),
	(21, 11, 1, 1, 1854.45, 21, 1854.45),
	(22, 11, 2, 1, 2458, 21, 2458),
	(23, 11, 3, 1, 1.76, 21, 1.76);
/*!40000 ALTER TABLE `detalles_pedido` ENABLE KEYS */;

-- Volcando estructura para tabla tienda_carlos_picado_esteban.impuestos
CREATE TABLE IF NOT EXISTS `impuestos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `impuesto` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla tienda_carlos_picado_esteban.impuestos: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `impuestos` DISABLE KEYS */;
/*!40000 ALTER TABLE `impuestos` ENABLE KEYS */;

-- Volcando estructura para tabla tienda_carlos_picado_esteban.metodos_pago
CREATE TABLE IF NOT EXISTS `metodos_pago` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `metodo_pago` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla tienda_carlos_picado_esteban.metodos_pago: ~4 rows (aproximadamente)
/*!40000 ALTER TABLE `metodos_pago` DISABLE KEYS */;
INSERT INTO `metodos_pago` (`id`, `metodo_pago`) VALUES
	(1, 'Transferencia Bancaria'),
	(2, 'PayPal'),
	(3, 'Visa'),
	(4, 'Mastercard');
/*!40000 ALTER TABLE `metodos_pago` ENABLE KEYS */;

-- Volcando estructura para tabla tienda_carlos_picado_esteban.opciones
CREATE TABLE IF NOT EXISTS `opciones` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `alias` varchar(255) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `alias` (`alias`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla tienda_carlos_picado_esteban.opciones: ~10 rows (aproximadamente)
/*!40000 ALTER TABLE `opciones` DISABLE KEYS */;
INSERT INTO `opciones` (`id`, `alias`, `nombre`, `url`) VALUES
	(1, 'NAVBAR_CATALOGO', 'Catálogo', 'catalogo'),
	(2, 'HEADER_CARRITO', 'Carrito', 'carrito'),
	(3, 'NAVBAR_OPCIONES', 'Opciones', ''),
	(4, 'HEADER_PERFIL_USUARIO', 'Perfil de usuario', 'usuario'),
	(5, 'NAVBAR_HISTORIAL_PEDIDOS', 'Historial de pedidos', 'historial_pedido'),
	(6, 'NAVBAR_MODIFICAR_CONTRASENA', 'Modificar contraseña', 'password'),
	(7, 'HEADER_CREAR_CUENTA', 'Crear cuenta', 'registro'),
	(8, 'HEADER_INICIAR_SESION', 'Iniciar sesión', 'login'),
	(9, 'HEADER_CERRAR_SESION', 'Cerrar sesión', 'logout'),
	(10, 'HEADER_BIENVENIDA_USUARIO', '', NULL);
/*!40000 ALTER TABLE `opciones` ENABLE KEYS */;

-- Volcando estructura para tabla tienda_carlos_picado_esteban.opciones_menu
CREATE TABLE IF NOT EXISTS `opciones_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_rol` int(11) NOT NULL,
  `id_opcion` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_rol` (`id_rol`),
  KEY `id_opcion` (`id_opcion`),
  CONSTRAINT `opciones_menu_ibfk_1` FOREIGN KEY (`id_rol`) REFERENCES `roles` (`id`),
  CONSTRAINT `opciones_menu_ibfk_2` FOREIGN KEY (`id_opcion`) REFERENCES `opciones` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla tienda_carlos_picado_esteban.opciones_menu: ~9 rows (aproximadamente)
/*!40000 ALTER TABLE `opciones_menu` DISABLE KEYS */;
INSERT INTO `opciones_menu` (`id`, `id_rol`, `id_opcion`) VALUES
	(1, 1, 1),
	(2, 2, 1),
	(3, 2, 3),
	(4, 2, 4),
	(5, 2, 5),
	(6, 2, 6),
	(7, 1, 8),
	(8, 1, 7),
	(9, 2, 9),
	(10, 2, 10),
	(11, 3, 10),
	(12, 3, 3),
	(13, 3, 9),
	(14, 1, 1),
	(15, 2, 9),
	(16, 4, 9),
	(17, 4, 10),
	(18, 4, 3),
	(20, 3, 1);
/*!40000 ALTER TABLE `opciones_menu` ENABLE KEYS */;

-- Volcando estructura para tabla tienda_carlos_picado_esteban.pedidos
CREATE TABLE IF NOT EXISTS `pedidos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `metodo_pago` varchar(255) DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `num_factura` varchar(255) DEFAULT NULL,
  `total` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `pedidos_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla tienda_carlos_picado_esteban.pedidos: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `pedidos` DISABLE KEYS */;
INSERT INTO `pedidos` (`id`, `id_usuario`, `fecha`, `metodo_pago`, `estado`, `num_factura`, `total`) VALUES
	(6, 44, '2022-11-08 13:25:56', 'Visa', 'E', 'A1', 502.22),
	(7, 40, '2022-11-08 13:25:59', 'Transferencia Bancaria', 'E', 'A0', 34499.6),
	(8, 40, '2022-11-08 13:25:58', 'PayPal', 'E', 'AF2', 10488.15),
	(9, 40, '2022-11-08 13:25:57', 'PayPal', 'E', 'AF2', 4312.45),
	(10, 40, '2022-11-08 13:25:58', 'Visa', 'E', 'AF2', 4312.45),
	(11, 40, '2022-11-08 13:32:55', 'Transferencia Bancaria', 'PE', 'AF2', 4314.21);
/*!40000 ALTER TABLE `pedidos` ENABLE KEYS */;

-- Volcando estructura para tabla tienda_carlos_picado_esteban.productos
CREATE TABLE IF NOT EXISTS `productos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_categoria` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `precio` decimal(20,2) NOT NULL DEFAULT 0.00,
  `stock` int(11) NOT NULL,
  `impuesto` double NOT NULL DEFAULT 0,
  `imagen` varchar(255) DEFAULT NULL,
  `baja` tinyint(1) DEFAULT NULL,
  `fecha_alta` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `id_categoria` (`id_categoria`),
  CONSTRAINT `productos_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categorias` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla tienda_carlos_picado_esteban.productos: ~5 rows (aproximadamente)
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` (`id`, `id_categoria`, `nombre`, `descripcion`, `precio`, `stock`, `impuesto`, `imagen`, `baja`, `fecha_alta`) VALUES
	(1, 1, 'Traje de seda Armani H', 'Traje de hombre', 1854.45, 50, 21, 'traje_amani', 0, '2022-11-01 13:49:19'),
	(2, 1, 'Traje de Ka\'Lium', 'Traje de mujer', 2458.00, 50, 21, 'traje_kalium', 0, '2022-11-01 11:20:53'),
	(3, 2, 'Calcetines WinniePoo', 'Calcetines de talla pequeña', 1.76, 25, 21, 'calcetines_winniepoo', 0, '2022-11-01 13:41:46'),
	(4, 4, 'Pantalones de Rosa Mosqueta', 'Pantalones de color rojo ideal para bodas', 568.00, 14, 21, 'pantalon_ros_mosqueta', 0, '2022-10-20 00:00:00'),
	(5, 5, 'Pruebaaaaaaaaaaaaaaaaaaaaaa!', 'Pruyeba', 125.99, 22, 21, NULL, 1, '2022-11-05 11:51:06');
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;

-- Volcando estructura para tabla tienda_carlos_picado_esteban.proveedores
CREATE TABLE IF NOT EXISTS `proveedores` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `localidad` varchar(255) DEFAULT NULL,
  `provincia` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `cif` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla tienda_carlos_picado_esteban.proveedores: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `proveedores` DISABLE KEYS */;
/*!40000 ALTER TABLE `proveedores` ENABLE KEYS */;

-- Volcando estructura para tabla tienda_carlos_picado_esteban.roles
CREATE TABLE IF NOT EXISTS `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rol` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `rol` (`rol`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla tienda_carlos_picado_esteban.roles: ~4 rows (aproximadamente)
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`id`, `rol`) VALUES
	(3, 'Administrador'),
	(1, 'Anonimo'),
	(2, 'Cliente'),
	(4, 'Empleado');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;

-- Volcando estructura para tabla tienda_carlos_picado_esteban.usuarios
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_rol` int(11) DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `salt` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `nombre` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `apellido1` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `apellido2` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `direccion` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `provincia` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `localidad` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `telefono` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `dni` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `imagen` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `baja` tinyint(1) DEFAULT NULL,
  `fecha_alta` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `id_rol` (`id_rol`),
  CONSTRAINT `usuarios_ibfk_1` FOREIGN KEY (`id_rol`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla tienda_carlos_picado_esteban.usuarios: ~5 rows (aproximadamente)
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` (`id`, `id_rol`, `email`, `password`, `salt`, `nombre`, `apellido1`, `apellido2`, `direccion`, `provincia`, `localidad`, `telefono`, `dni`, `imagen`, `baja`, `fecha_alta`) VALUES
	(40, 3, 'carlos@gmail.com', '49f07dc5f411cfd578fbe8ba0df05890684406dcceb16219d0fdc3889ed1624574b152d68668801aea88914ee909c4120e253160c8800333fce14029ca6e8f29e1c74826142a4ba95b2a7638f1ca66cef80f675c09bd15e4e84a84d92172594cd1128d9b0d2a7838dba2d20a3bcfbf198de98fdbc1de0f4704738e98a0', '3c535dea168b626e5c0b88397edaf09d8c443c88607e455540677a96995333a9', 'Carlos', 'Picado', 'Esteban', 'Calle de Fuenteovejuna 12', 'Zamora', 'Zamora', '658888888', '44555625X', NULL, 0, '2022-11-07 00:16:41'),
	(41, 2, 'lucasmt2@gmail.com', 'f84dae1603a30dc00adc604dc9d414bd42d2bf800dbdf585e1def8a533742dc04f10541a13a417f97b33a42f1ceccb6ca214c4aaeb4924169abf92b56cf14b16ecc85667c933bd80fdb46117e464a71c46acc25dbf94d96a8109d0be742813d2a836cfb76985315f4750363f334bbd3031eb0c8d7a2fa3e1827d512506', '58a3248ddd42b505bd873eaecb66ed0e72efd7a3971b3fe4ea1dfe98e0ae0e6d', 'Lucas', 'Mateo', 'Hernández', 'Calle Santa Eulalia', 'Burgos', 'Burgos', '684112222', '74588546S', NULL, 0, '2022-11-05 11:46:04'),
	(44, 4, 'lau@gmail.com', 'f3e3f6a1f0bc6d556ce71bf5463903c1111f5704da0bac5ac934425fdf643a24397715cc6e9e33808ed1dbacc7f353dfda3d6fe7232788e1365cb528b0016450239d8c850ecc3825a0b17d0eedbadcba109531f77399c27eb93f6099f71e69f82ef502751da8f7bea7fb96f71449b0c1319265b52ce5822dd9a3a3d82e', '489e82ae49e399b364b81cdcf87393777a9b9b65779d78e602728db789f067d3', 'Laura', 'Bustana', 'Clementi', 'Calle de algoq', 'Araba/Álava', 'Álava', '658888888', '69854511J', NULL, 0, '2022-11-05 11:46:07'),
	(45, 3, 'javcs@outlook.com', 'a8e10ecf9ce5a320025422638a810b8b2b2f122594ae74718bbb997c6788050c8db28907824dde69fc651f12daa3b92514ea369d38006937685b6a499ad44079974d7937cdb65fb9ead9823ca8dc851efa6236de4cc6abe2e857134e0026f8342f4db2a9b74711706312767fa54133178c2149f241b87b644655d072f7', '30ab64487ca17841478b97c55d374a95b532194da8fa5de81b0b176951833e5e', 'Javier', 'Del canto', 'Santo', 'Avda la feria', 'Zamora', 'Zamora', '655454545', '98544541X', NULL, 0, '2022-11-05 17:17:59'),
	(47, 2, 'sandra@gmail.com', 'a3b7f66abb73d7df31e15ece27fcf64c454a0da11a4700aee2399508bd35f4898ac88b9d0859347799796edc059ff517dd0403c58e0272d70f199ff945e1a351b269f4c7eb30a26b68e02c13aa830026790fca7cbccec27c6d4a01dc1044dcedf59605450da7468406441bf9591d311fbd8d5269460da90e04f0d09ec3', '13111770d4c4879d7fe0f3763dacfe0dc49697301b139408d8706490099518dc', 'Sandra', 'Navas', 'Tolsa', 'Santa clara 21', 'Zamora', 'Zamora', '652222222', '454848742S', NULL, 0, '2022-11-05 11:29:22'),
	(48, 2, 'paulamafe@gmail.com', 'a7ce31b9cd1d975c810e4ec0a036639c299031546fe8c430bdce2252afa2a72160e673e909f6b084f16d93ba4b6b7b8aaf7678a91d81e118ccc25d6fc66fe191ad091c6d7658e36cb721823fb82d389d81242daef47da492a8a6e8f6a59f3e79f9a992faa800247608ac97d0ff4dcdcf5d35ac5e6814e126876ad29e1b', 'a88e672a469518e9630186cd369ae7ffb5c0196387b02c29292693b6e47430fa', 'Paula', 'Mateo', 'Fernández', 'Calle de las monsergas, 21', 'Cantabria', 'Cantabria', '654212121', '11785254S', NULL, 0, '2022-11-06 23:12:07');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;

-- Volcando estructura para tabla tienda_carlos_picado_esteban.valoraciones
CREATE TABLE IF NOT EXISTS `valoraciones` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_producto` int(11) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `valoracion` int(11) DEFAULT NULL,
  `comentario` varchar(255) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `id_producto` (`id_producto`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `valoraciones_ibfk_1` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id`),
  CONSTRAINT `valoraciones_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla tienda_carlos_picado_esteban.valoraciones: ~6 rows (aproximadamente)
/*!40000 ALTER TABLE `valoraciones` DISABLE KEYS */;
INSERT INTO `valoraciones` (`id`, `id_producto`, `id_usuario`, `valoracion`, `comentario`, `fecha`) VALUES
	(1, 1, 40, 5, 'Tejido firme y muy cómodo', '2022-11-01 09:51:11'),
	(2, 1, 45, 2, 'El traje llegó con muchos agujeros y mas que un traje parecía un saco', '2022-11-01 09:52:11'),
	(3, 4, 41, 3, 'Aspecto increíble aunque puede ser mejorado', '2022-11-01 11:06:42'),
	(4, 4, 44, 5, 'Muy buen pantalón y la comodidad de llevarlo', '2022-11-01 13:26:59'),
	(5, 3, 40, 5, 'Cómodos y muy bonitos', '2022-11-01 13:37:38'),
	(6, 3, 41, 5, 'Son perfectos e ideales para un regalo de cumpleaños', '2022-11-01 13:37:58');
/*!40000 ALTER TABLE `valoraciones` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
