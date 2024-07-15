-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-07-2024 a las 06:44:54
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bankinc_tc`
--
CREATE DATABASE IF NOT EXISTS `bankinc_tc` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `bankinc_tc`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `control_saldos`
--

CREATE TABLE `control_saldos` (
  `id` bigint(20) NOT NULL,
  `id_tc` int(11) NOT NULL,
  `saldo_actual` int(11) NOT NULL,
  `saldo_anterior` int(11) NOT NULL,
  `valor_ultima_recarga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `control_saldos`
--

INSERT INTO `control_saldos` (`id`, `id_tc`, `saldo_actual`, `saldo_anterior`, `valor_ultima_recarga`) VALUES
(1, 6, 1700, 1200, 1000),
(2, 7, 1500, 900, 1500);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `control_transacciones`
--

CREATE TABLE `control_transacciones` (
  `id` bigint(20) NOT NULL,
  `nombre_comercio` varchar(255) NOT NULL,
  `valor_compra` int(11) NOT NULL,
  `fecha_compra` datetime(6) NOT NULL,
  `id_tc` int(11) NOT NULL,
  `ind_anulado` bit(1) NOT NULL,
  `hora_compra` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `control_transacciones`
--

INSERT INTO `control_transacciones` (`id`, `nombre_comercio`, `valor_compra`, `fecha_compra`, `id_tc`, `ind_anulado`, `hora_compra`) VALUES
(7, 'Olson-Robel', 300, '2024-07-13 00:00:00.000000', 6, b'0', '00:00:00'),
(8, 'Monahan, Kuhic and Schultz', 500, '2024-07-13 00:00:00.000000', 6, b'1', '21:52:45'),
(14, 'Mann-Volkman', 600, '2024-07-14 00:00:00.000000', 7, b'1', '14:30:44');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `info_tarjetas`
--

CREATE TABLE `info_tarjetas` (
  `id` int(11) NOT NULL,
  `nombre_titular` varchar(100) NOT NULL,
  `apellido_titular` varchar(100) NOT NULL,
  `numero_tc` varchar(16) NOT NULL,
  `numero_tc_enmascarada` varchar(16) NOT NULL,
  `fecha_tc` varchar(7) NOT NULL,
  `id_producto` int(11) NOT NULL,
  `ind_activo` bit(1) NOT NULL,
  `ind_bloqueo` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `info_tarjetas`
--

INSERT INTO `info_tarjetas` (`id`, `nombre_titular`, `apellido_titular`, `numero_tc`, `numero_tc_enmascarada`, `fecha_tc`, `id_producto`, `ind_activo`, `ind_bloqueo`) VALUES
(6, 'Renetta', 'Pagac', '3689457230013185', '3689********3185', '11/2027', 2, b'1', b'0'),
(7, 'Dirk', 'Harris', '3689453619769903', '3689********9903', '06/2027', 2, b'1', b'0');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id` int(11) NOT NULL,
  `nombre_producto` varchar(255) NOT NULL,
  `codigo` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id`, `nombre_producto`, `codigo`) VALUES
(1, 'Tarjeta Debito', '156375'),
(2, 'Tarjeta Credito', '368945');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `control_saldos`
--
ALTER TABLE `control_saldos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_id_tc` (`id_tc`);

--
-- Indices de la tabla `control_transacciones`
--
ALTER TABLE `control_transacciones`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_id_tc_tr` (`id_tc`);

--
-- Indices de la tabla `info_tarjetas`
--
ALTER TABLE `info_tarjetas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_id_producto` (`id_producto`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `control_saldos`
--
ALTER TABLE `control_saldos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `control_transacciones`
--
ALTER TABLE `control_transacciones`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `info_tarjetas`
--
ALTER TABLE `info_tarjetas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `control_saldos`
--
ALTER TABLE `control_saldos`
  ADD CONSTRAINT `fk_id_tc` FOREIGN KEY (`id_tc`) REFERENCES `info_tarjetas` (`id`);

--
-- Filtros para la tabla `control_transacciones`
--
ALTER TABLE `control_transacciones`
  ADD CONSTRAINT `fk_id_tc_tr` FOREIGN KEY (`id_tc`) REFERENCES `info_tarjetas` (`id`);

--
-- Filtros para la tabla `info_tarjetas`
--
ALTER TABLE `info_tarjetas`
  ADD CONSTRAINT `fk_id_productos` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
