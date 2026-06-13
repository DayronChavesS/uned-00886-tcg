-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema comparecencias
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema comparecencias
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `comparecencias` DEFAULT CHARACTER SET utf8 ;
USE `comparecencias` ;

-- -----------------------------------------------------
-- Table `comparecencias`.`Persona`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comparecencias`.`Persona` (
  `idPersona` INT NOT NULL AUTO_INCREMENT,
  `cedula` VARCHAR(15) NOT NULL,
  `primerNombre` VARCHAR(45) NOT NULL,
  `segundoNombre` VARCHAR(45) NULL,
  `primerApellido` VARCHAR(45) NOT NULL,
  `segundoApellido` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idPersona`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comparecencias`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comparecencias`.`Usuario` (
  `idPersona` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NOT NULL,
  `contraseña` VARCHAR(45) NOT NULL,
  `enLinea` TINYINT NOT NULL,
  `autorizado` VARCHAR(1) NOT NULL DEFAULT 'P',
  INDEX `fk_Usuario_Persona_idx` (`idPersona` ASC) VISIBLE,
  PRIMARY KEY (`idPersona`),
  CONSTRAINT `fk_Usuario_Persona`
    FOREIGN KEY (`idPersona`)
    REFERENCES `comparecencias`.`Persona` (`idPersona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comparecencias`.`CatalogoPuesto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comparecencias`.`CatalogoPuesto` (
  `idPuesto` INT NOT NULL,
  `nombre` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`idPuesto`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comparecencias`.`CatalogoRegion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comparecencias`.`CatalogoRegion` (
  `idRegion` INT NOT NULL,
  `nombre` VARCHAR(33) NOT NULL,
  PRIMARY KEY (`idRegion`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comparecencias`.`CatalogoOficina`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comparecencias`.`CatalogoOficina` (
  `idOficina` INT NOT NULL,
  `nombre` VARCHAR(34) NOT NULL,
  PRIMARY KEY (`idOficina`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comparecencias`.`Inspector`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comparecencias`.`Inspector` (
  `idPersona` INT NOT NULL AUTO_INCREMENT,
  `idPuesto` INT NOT NULL,
  `idRegion` INT NOT NULL,
  `idOficina` INT NOT NULL,
  INDEX `fk_Inspector_Usuario1_idx` (`idPersona` ASC) VISIBLE,
  INDEX `fk_Inspector_CatalogoPuesto1_idx` (`idPuesto` ASC) VISIBLE,
  INDEX `fk_Inspector_CatalogoRegion1_idx` (`idRegion` ASC) VISIBLE,
  INDEX `fk_Inspector_CatalogoOficina1_idx` (`idOficina` ASC) VISIBLE,
  PRIMARY KEY (`idPersona`),
  CONSTRAINT `fk_Inspector_Usuario1`
    FOREIGN KEY (`idPersona`)
    REFERENCES `comparecencias`.`Usuario` (`idPersona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Inspector_CatalogoPuesto1`
    FOREIGN KEY (`idPuesto`)
    REFERENCES `comparecencias`.`CatalogoPuesto` (`idPuesto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Inspector_CatalogoRegion1`
    FOREIGN KEY (`idRegion`)
    REFERENCES `comparecencias`.`CatalogoRegion` (`idRegion`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Inspector_CatalogoOficina1`
    FOREIGN KEY (`idOficina`)
    REFERENCES `comparecencias`.`CatalogoOficina` (`idOficina`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comparecencias`.`Audio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comparecencias`.`Audio` (
  `idAudio` INT NOT NULL AUTO_INCREMENT,
  `nombreAudio` VARCHAR(45) NOT NULL,
  `duracionAudio` VARCHAR(8) NULL,
  `pathArchivoAudio` VARCHAR(999) NOT NULL,
  `pathArchivoAnotaciones` VARCHAR(999) NOT NULL,
  PRIMARY KEY (`idAudio`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comparecencias`.`CatalogoCaso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comparecencias`.`CatalogoCaso` (
  `idCaso` INT NOT NULL,
  `tipoCaso` VARCHAR(56) NOT NULL,
  PRIMARY KEY (`idCaso`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comparecencias`.`Comparecencia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comparecencias`.`Comparecencia` (
  `idComparecencia` INT NOT NULL AUTO_INCREMENT,
  `codigoSILAC` VARCHAR(15) NOT NULL,
  `idCaso` INT NOT NULL,
  `ubicacion` VARCHAR(200) NOT NULL,
  `linkExpediente` VARCHAR(200) NOT NULL,
  `fecha` DATE NOT NULL,
  PRIMARY KEY (`idComparecencia`),
  INDEX `fk_Comparecencia_CatalogoCaso1_idx` (`idCaso` ASC) VISIBLE,
  CONSTRAINT `fk_Comparecencia_CatalogoCaso1`
    FOREIGN KEY (`idCaso`)
    REFERENCES `comparecencias`.`CatalogoCaso` (`idCaso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comparecencias`.`PersonaJuridica`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comparecencias`.`PersonaJuridica` (
  `idPersonaJuridica` INT NOT NULL AUTO_INCREMENT,
  `cedulaJuridica` VARCHAR(13) NOT NULL,
  `nombreRazonSocial` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idPersonaJuridica`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comparecencias`.`Gestionado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comparecencias`.`Gestionado` (
  `idGestionado` INT NOT NULL AUTO_INCREMENT,
  `idPersona` INT NULL,
  `idPersonaJuridica` INT NULL,
  `idComparecencia` INT NOT NULL,
  INDEX `fk_Gestionado_Persona1_idx` (`idPersona` ASC) VISIBLE,
  INDEX `fk_Gestionado_Comparecencia1_idx` (`idComparecencia` ASC) VISIBLE,
  PRIMARY KEY (`idGestionado`),
  INDEX `fk_Gestionado_PersonaJuridica1_idx` (`idPersonaJuridica` ASC) VISIBLE,
  CONSTRAINT `fk_Gestionado_Persona1`
    FOREIGN KEY (`idPersona`)
    REFERENCES `comparecencias`.`Persona` (`idPersona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Gestionado_Comparecencia1`
    FOREIGN KEY (`idComparecencia`)
    REFERENCES `comparecencias`.`Comparecencia` (`idComparecencia`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Gestionado_PersonaJuridica1`
    FOREIGN KEY (`idPersonaJuridica`)
    REFERENCES `comparecencias`.`PersonaJuridica` (`idPersonaJuridica`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comparecencias`.`Gestionante`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comparecencias`.`Gestionante` (
  `idGestionante` INT NOT NULL AUTO_INCREMENT,
  `idPersona` INT NULL,
  `idPersonaJuridica` INT NULL,
  `idComparecencia` INT NOT NULL,
  INDEX `fk_Gestionante_Comparecencia1_idx` (`idComparecencia` ASC) VISIBLE,
  PRIMARY KEY (`idGestionante`),
  INDEX `fk_Gestionante_PersonaJuridica1_idx` (`idPersonaJuridica` ASC) VISIBLE,
  CONSTRAINT `fk_Gestionante_Persona1`
    FOREIGN KEY (`idPersona`)
    REFERENCES `comparecencias`.`Persona` (`idPersona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Gestionante_Comparecencia1`
    FOREIGN KEY (`idComparecencia`)
    REFERENCES `comparecencias`.`Comparecencia` (`idComparecencia`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Gestionante_PersonaJuridica1`
    FOREIGN KEY (`idPersonaJuridica`)
    REFERENCES `comparecencias`.`PersonaJuridica` (`idPersonaJuridica`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comparecencias`.`InspectorXComparecencia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comparecencias`.`InspectorXComparecencia` (
  `idPersona` INT NOT NULL,
  `idComparecencia` INT NOT NULL,
  INDEX `fk_Comparecencia_has_Inspector_Inspector1_idx` (`idPersona` ASC) VISIBLE,
  CONSTRAINT `fk_Comparecencia_has_Inspector_Comparecencia1`
    FOREIGN KEY (`idComparecencia`)
    REFERENCES `comparecencias`.`Comparecencia` (`idComparecencia`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Comparecencia_has_Inspector_Inspector1`
    FOREIGN KEY (`idPersona`)
    REFERENCES `comparecencias`.`Inspector` (`idPersona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comparecencias`.`AudioXComparecencia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comparecencias`.`AudioXComparecencia` (
  `idAudio` INT NOT NULL,
  `idComparecencia` INT NOT NULL,
  INDEX `fk_Audio_has_Comparecencia_Comparecencia1_idx` (`idComparecencia` ASC) VISIBLE,
  INDEX `fk_Audio_has_Comparecencia_Audio1_idx` (`idAudio` ASC) VISIBLE,
  CONSTRAINT `fk_Audio_has_Comparecencia_Audio1`
    FOREIGN KEY (`idAudio`)
    REFERENCES `comparecencias`.`Audio` (`idAudio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Audio_has_Comparecencia_Comparecencia1`
    FOREIGN KEY (`idComparecencia`)
    REFERENCES `comparecencias`.`Comparecencia` (`idComparecencia`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comparecencias`.`Acompañante`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comparecencias`.`Acompañante` (
  `idPersona` INT NOT NULL,
  `idGestionante` INT NULL,
  `idGestionado` INT NULL,
  `tipoAcompañante` VARCHAR(30) NOT NULL,
  `enCondicionDe` VARCHAR(45) NULL,
  INDEX `fk_Acompañante_Persona1_idx` (`idPersona` ASC) VISIBLE,
  INDEX `fk_Acompañante_Gestionado1_idx` (`idGestionado` ASC) VISIBLE,
  INDEX `fk_Acompañante_Gestionante1_idx` (`idGestionante` ASC) VISIBLE,
  PRIMARY KEY (`idPersona`),
  CONSTRAINT `fk_Acompañante_Persona1`
    FOREIGN KEY (`idPersona`)
    REFERENCES `comparecencias`.`Persona` (`idPersona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Acompañante_Gestionado1`
    FOREIGN KEY (`idGestionado`)
    REFERENCES `comparecencias`.`Gestionado` (`idGestionado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Acompañante_Gestionante1`
    FOREIGN KEY (`idGestionante`)
    REFERENCES `comparecencias`.`Gestionante` (`idGestionante`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comparecencias`.`Representante`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comparecencias`.`Representante` (
  `idPersona` INT NOT NULL,
  `idGestionante` INT NULL,
  `idGestionado` INT NULL,
  INDEX `fk_Representante_Persona1_idx` (`idPersona` ASC) VISIBLE,
  INDEX `fk_Representante_Gestionante1_idx` (`idGestionante` ASC) VISIBLE,
  INDEX `fk_Representante_Gestionado1_idx` (`idGestionado` ASC) VISIBLE,
  PRIMARY KEY (`idPersona`),
  CONSTRAINT `fk_Representante_Persona1`
    FOREIGN KEY (`idPersona`)
    REFERENCES `comparecencias`.`Persona` (`idPersona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Representante_Gestionante1`
    FOREIGN KEY (`idGestionante`)
    REFERENCES `comparecencias`.`Gestionante` (`idGestionante`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Representante_Gestionado1`
    FOREIGN KEY (`idGestionado`)
    REFERENCES `comparecencias`.`Gestionado` (`idGestionado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `comparecencias`.`Testigo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `comparecencias`.`Testigo` (
  `idPersona` INT NOT NULL,
  `idGestionante` INT NULL,
  `idGestionado` INT NULL,
  INDEX `fk_Testigo_Persona1_idx` (`idPersona` ASC) VISIBLE,
  INDEX `fk_Testigo_Gestionante1_idx` (`idGestionante` ASC) VISIBLE,
  INDEX `fk_Testigo_Gestionado1_idx` (`idGestionado` ASC) VISIBLE,
  PRIMARY KEY (`idPersona`),
  CONSTRAINT `fk_Testigo_Persona1`
    FOREIGN KEY (`idPersona`)
    REFERENCES `comparecencias`.`Persona` (`idPersona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Testigo_Gestionante1`
    FOREIGN KEY (`idGestionante`)
    REFERENCES `comparecencias`.`Gestionante` (`idGestionante`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Testigo_Gestionado1`
    FOREIGN KEY (`idGestionado`)
    REFERENCES `comparecencias`.`Gestionado` (`idGestionado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- AUTOLOAD CATALOGOS
-- -----------------------------------------------------

INSERT INTO `catalogocaso`(`idCaso`,`tipoCaso`)VALUES('0','Discriminación en razón de género, edad, entre otros');
INSERT INTO `catalogocaso`(`idCaso`,`tipoCaso`)VALUES('1','Despido de Trabajador Licencia de Paternidad');
INSERT INTO `catalogocaso`(`idCaso`,`tipoCaso`)VALUES('2','Trabajadora embarazada o estado de lactancia');
INSERT INTO `catalogocaso`(`idCaso`,`tipoCaso`)VALUES('3','Trabajadora embarazada. Restricción de derechos');
INSERT INTO `catalogocaso`(`idCaso`,`tipoCaso`)VALUES('4','Fraccionamiento de jornada laboral');
INSERT INTO `catalogocaso`(`idCaso`,`tipoCaso`)VALUES('5','Hostigamiento laboral');
INSERT INTO `catalogocaso`(`idCaso`,`tipoCaso`)VALUES('6','Hostigamiento sexual');
INSERT INTO `catalogocaso`(`idCaso`,`tipoCaso`)VALUES('7','Despido de aforado sindicato');

INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('0', 'Oficina Provincial de Cartago');
INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('1', 'Oficina Provincial de Heredia');
INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('2', 'Oficina Cantonal de Puriscal');
INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('3', 'Oficina Cantonal de los Santos');
INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('4', 'Oficina Cantonal de Ciudad Quesada');
INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('5', 'Oficina Cantonal de Grecia');
INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('6', 'Oficina Cantonal de Naranjo');
INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('7', 'Oficina Cantonal de La Fortuna');
INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('8', 'Oficina Cantonal de Siquirres');
INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('9', 'Oficina Cantonal de Pococí');
INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('10', 'Oficina Cantonal de Talamanca');
INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('11', 'Oficina Cantonal de Turrialba');
INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('12', 'Oficina Cantonal de Guácimo');
INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('13', 'Oficina Cantonal de San Ramón');
INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('14', 'Oficina Cantonal de Orotina');
INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('15', 'Oficina Cantonal de Aguirre-Quepos');
INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('16', 'Oficina Cantonal de Santa Cruz');
INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('17', 'Oficina Cantonal de Cañas');
INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('18', 'Oficina Cantonal de Nicoya');
INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('19', 'Oficina Cantonal Nandayure');
INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('20', 'Oficina Cantonal Upala');
INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('21', 'Oficina Cantonal de Palmar Norte');
INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('22', 'Oficina Cantonal de Corredores');
INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('23', 'Oficina Cantonal de Golfito');
INSERT INTO `catalogooficina` (`idOficina`, `nombre`) VALUES ('24', 'Oficina Cantonal de Coto Brus');

INSERT INTO `catalogopuesto`(`idPuesto`,`nombre`)VALUES('0','Administrador(a)');
INSERT INTO `catalogopuesto`(`idPuesto`,`nombre`)VALUES('1','Jefe Regional');
INSERT INTO `catalogopuesto`(`idPuesto`,`nombre`)VALUES('2','Coordinador(a) de Inspección');
INSERT INTO `catalogopuesto`(`idPuesto`,`nombre`)VALUES('3','Inspector(a)');
INSERT INTO `catalogopuesto`(`idPuesto`,`nombre`)VALUES('4','Asesor(a) Legal');

INSERT INTO `catalogoregion`(`idRegion`,`nombre`)VALUES('0','Oficina Regional Central');
INSERT INTO `catalogoregion`(`idRegion`,`nombre`)VALUES('1','Oficina Regional Huetar Norte');
INSERT INTO `catalogoregion`(`idRegion`,`nombre`)VALUES('2','Oficina Regional Huetar Caribe');
INSERT INTO `catalogoregion`(`idRegion`,`nombre`)VALUES('3','Oficina Regional Pacífico Central');
INSERT INTO `catalogoregion`(`idRegion`,`nombre`)VALUES('4','Oficina Regional Chorotega');
INSERT INTO `catalogoregion`(`idRegion`,`nombre`)VALUES('5','Oficina Regional Brunca');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;