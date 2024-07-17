USE `db_books_sp3`;

DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;

-- *************************
-- Estructura de tabla `users`
-- *************************

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(68) NOT NULL,
  `enabled` tinyint NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ***************************
-- insertando datos en `users`
-- ***************************

INSERT INTO `users` 
VALUES 
('alfredo','{bcrypt}$2a$10$rp5zP8AqKS4O2CeGinhCZuO2KKZ.4ikf91v.jv4Ay5NNd.47kLrzi',1),
('agustin','{bcrypt}$2a$10$OqnTHxwBlB09JB51FG72u.15z.p/2J50MdumiOFq1bcqp3.Ry6S8u',1),
('edita','{bcrypt}$2a$10$WSAGkjGfp/GTTGLtxBAB4.6brF9.LkIRSL1.4t.EgT1Emd.50/Gbi',1);


-- *********************************
-- Estructura de tabla `authorities`
-- *********************************

CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `authorities_idx_1` (`username`,`authority`),
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ***********************************
-- Insertando datos en `authorities`
-- **********************************

INSERT INTO `authorities` 
VALUES 
('alfredo','ROLE_Empleado'),
('agustin','ROLE_Empleado'),
('agustin','ROLE_Jefe'),
('edita','ROLE_Empleado'),
('edita','ROLE_Jefe')


