CREATE TABLE `Cita` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`fecha` DATETIME NOT NULL,
	`motivo` MEDIUMTEXT NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`idPaciente` INT NOT NULL,
	`idUsuario` INT NOT NULL,
	PRIMARY KEY (`id`) USING BTREE,
	INDEX `FK_cita_paciente` (`idPaciente`) USING BTREE,
	INDEX `FK_cita_usuario` (`idUsuario`) USING BTREE,
	CONSTRAINT `FK_cita_paciente` FOREIGN KEY (`idPaciente`) REFERENCES `Paciente` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT `FK_cita_usuario` FOREIGN KEY (`idUsuario`) REFERENCES `Usuario` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
;


CREATE TABLE `Consulta` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`diagnostico` MEDIUMTEXT NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`tratamiento` MEDIUMTEXT NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`examenMedico` MEDIUMTEXT NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`idPaciente` INT NOT NULL,
	`idCita` INT NOT NULL,
	`idUsuario` INT NOT NULL,
	PRIMARY KEY (`id`) USING BTREE,
	INDEX `FK_consulta_paciente` (`idPaciente`) USING BTREE,
	INDEX `FK_consulta_cita` (`idCita`) USING BTREE,
	INDEX `FK_consulta_usuario` (`idUsuario`) USING BTREE,
	CONSTRAINT `FK_consulta_cita` FOREIGN KEY (`idCita`) REFERENCES `Cita` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT `FK_consulta_paciente` FOREIGN KEY (`idPaciente`) REFERENCES `Paciente` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT `FK_consulta_usuario` FOREIGN KEY (`idUsuario`) REFERENCES `Usuario` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
;


CREATE TABLE `Inventario` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`nombre` VARCHAR(100) NOT NULL DEFAULT '' COLLATE 'utf8mb4_0900_ai_ci',
	`cantidad` INT UNSIGNED NOT NULL,
	`descripcion` MEDIUMTEXT NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `nombre` (`nombre`) USING BTREE
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
;



CREATE TABLE `MovimientoInventario` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`fecha` DATE NOT NULL,
	`cantidad` INT NOT NULL,
	`idInventario` INT NOT NULL,
	`idUsuario` INT NOT NULL,
	PRIMARY KEY (`id`) USING BTREE,
	INDEX `FK_movimientoInventario_inventario` (`idInventario`) USING BTREE,
	INDEX `FK_movimientoInventario_usuario` (`idUsuario`) USING BTREE,
	CONSTRAINT `FK_movimientoInventario_inventario` FOREIGN KEY (`idInventario`) REFERENCES `Inventario` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT `FK_movimientoInventario_usuario` FOREIGN KEY (`idUsuario`) REFERENCES `Usuario` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
;



CREATE TABLE `Paciente` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`nombre` TINYTEXT NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`telefono` TINYTEXT NULL DEFAULT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`correo` TINYTEXT NULL DEFAULT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`fechaNacimiento` DATE NOT NULL,
	`genero` INT NULL DEFAULT NULL,
	PRIMARY KEY (`id`) USING BTREE
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
;



CREATE TABLE `Usuario` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`nombre` TINYTEXT NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`contrasena` TINYTEXT NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
	`rol` INT NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`) USING BTREE
)
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
;
