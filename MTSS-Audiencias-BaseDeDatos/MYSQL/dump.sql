CREATE DATABASE  IF NOT EXISTS `comparecencias` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `comparecencias`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: comparecencias
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `acompañante`
--

DROP TABLE IF EXISTS `acompañante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acompañante` (
  `idPersona` int NOT NULL,
  `idGestionante` int DEFAULT NULL,
  `idGestionado` int DEFAULT NULL,
  `tipoAcompañante` varchar(30) NOT NULL,
  `enCondicionDe` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idPersona`),
  KEY `fk_Acompañante_Persona1_idx` (`idPersona`),
  KEY `fk_Acompañante_Gestionado1_idx` (`idGestionado`),
  KEY `fk_Acompañante_Gestionante1_idx` (`idGestionante`),
  CONSTRAINT `fk_Acompañante_Gestionado1` FOREIGN KEY (`idGestionado`) REFERENCES `gestionado` (`idGestionado`),
  CONSTRAINT `fk_Acompañante_Gestionante1` FOREIGN KEY (`idGestionante`) REFERENCES `gestionante` (`idGestionante`),
  CONSTRAINT `fk_Acompañante_Persona1` FOREIGN KEY (`idPersona`) REFERENCES `persona` (`idPersona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acompañante`
--

LOCK TABLES `acompañante` WRITE;
/*!40000 ALTER TABLE `acompañante` DISABLE KEYS */;
/*!40000 ALTER TABLE `acompañante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audio`
--

DROP TABLE IF EXISTS `audio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audio` (
  `idAudio` int NOT NULL AUTO_INCREMENT,
  `nombreAudio` varchar(45) NOT NULL,
  `duracionAudio` varchar(8) DEFAULT NULL,
  `pathArchivoAudio` varchar(999) NOT NULL,
  `pathArchivoAnotaciones` varchar(999) NOT NULL,
  PRIMARY KEY (`idAudio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audio`
--

LOCK TABLES `audio` WRITE;
/*!40000 ALTER TABLE `audio` DISABLE KEYS */;
/*!40000 ALTER TABLE `audio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audioxcomparecencia`
--

DROP TABLE IF EXISTS `audioxcomparecencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audioxcomparecencia` (
  `idAudio` int NOT NULL,
  `idComparecencia` int NOT NULL,
  KEY `fk_Audio_has_Comparecencia_Comparecencia1_idx` (`idComparecencia`),
  KEY `fk_Audio_has_Comparecencia_Audio1_idx` (`idAudio`),
  CONSTRAINT `fk_Audio_has_Comparecencia_Audio1` FOREIGN KEY (`idAudio`) REFERENCES `audio` (`idAudio`),
  CONSTRAINT `fk_Audio_has_Comparecencia_Comparecencia1` FOREIGN KEY (`idComparecencia`) REFERENCES `comparecencia` (`idComparecencia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audioxcomparecencia`
--

LOCK TABLES `audioxcomparecencia` WRITE;
/*!40000 ALTER TABLE `audioxcomparecencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `audioxcomparecencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `catalogocaso`
--

DROP TABLE IF EXISTS `catalogocaso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `catalogocaso` (
  `idCaso` int NOT NULL,
  `tipoCaso` varchar(56) NOT NULL,
  PRIMARY KEY (`idCaso`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalogocaso`
--

LOCK TABLES `catalogocaso` WRITE;
/*!40000 ALTER TABLE `catalogocaso` DISABLE KEYS */;
INSERT INTO `catalogocaso` VALUES (0,'Discriminación en razón de género, edad, entre otros'),(1,'Despido de Trabajador Licencia de Paternidad'),(2,'Trabajadora embarazada o estado de lactancia'),(3,'Trabajadora embarazada. Restricción de derechos'),(4,'Fraccionamiento de jornada laboral'),(5,'Hostigamiento laboral'),(6,'Hostigamiento sexual'),(7,'Despido de aforado sindicato');
/*!40000 ALTER TABLE `catalogocaso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `catalogooficina`
--

DROP TABLE IF EXISTS `catalogooficina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `catalogooficina` (
  `idOficina` int NOT NULL,
  `nombre` varchar(34) NOT NULL,
  PRIMARY KEY (`idOficina`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalogooficina`
--

LOCK TABLES `catalogooficina` WRITE;
/*!40000 ALTER TABLE `catalogooficina` DISABLE KEYS */;
INSERT INTO `catalogooficina` VALUES (0,'Oficina Provincial de Cartago'),(1,'Oficina Provincial de Heredia'),(2,'Oficina Cantonal de Puriscal'),(3,'Oficina Cantonal de los Santos'),(4,'Oficina Cantonal de Ciudad Quesada'),(5,'Oficina Cantonal de Grecia'),(6,'Oficina Cantonal de Naranjo'),(7,'Oficina Cantonal de La Fortuna'),(8,'Oficina Cantonal de Siquirres'),(9,'Oficina Cantonal de Pococí'),(10,'Oficina Cantonal de Talamanca'),(11,'Oficina Cantonal de Turrialba'),(12,'Oficina Cantonal de Guácimo'),(13,'Oficina Cantonal de San Ramón'),(14,'Oficina Cantonal de Orotina'),(15,'Oficina Cantonal de Aguirre-Quepos'),(16,'Oficina Cantonal de Santa Cruz'),(17,'Oficina Cantonal de Cañas'),(18,'Oficina Cantonal de Nicoya'),(19,'Oficina Cantonal Nandayure'),(20,'Oficina Cantonal Upala'),(21,'Oficina Cantonal de Palmar Norte'),(22,'Oficina Cantonal de Corredores'),(23,'Oficina Cantonal de Golfito'),(24,'Oficina Cantonal de Coto Brus');
/*!40000 ALTER TABLE `catalogooficina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `catalogopuesto`
--

DROP TABLE IF EXISTS `catalogopuesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `catalogopuesto` (
  `idPuesto` int NOT NULL,
  `nombre` varchar(30) NOT NULL,
  PRIMARY KEY (`idPuesto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalogopuesto`
--

LOCK TABLES `catalogopuesto` WRITE;
/*!40000 ALTER TABLE `catalogopuesto` DISABLE KEYS */;
INSERT INTO `catalogopuesto` VALUES (0,'Administrador(a)'),(1,'Jefe Regional'),(2,'Coordinador(a) de Inspección'),(3,'Inspector(a)'),(4,'Asesor(a) Legal');
/*!40000 ALTER TABLE `catalogopuesto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `catalogoregion`
--

DROP TABLE IF EXISTS `catalogoregion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `catalogoregion` (
  `idRegion` int NOT NULL,
  `nombre` varchar(33) NOT NULL,
  PRIMARY KEY (`idRegion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalogoregion`
--

LOCK TABLES `catalogoregion` WRITE;
/*!40000 ALTER TABLE `catalogoregion` DISABLE KEYS */;
INSERT INTO `catalogoregion` VALUES (0,'Oficina Regional Central'),(1,'Oficina Regional Huetar Norte'),(2,'Oficina Regional Huetar Caribe'),(3,'Oficina Regional Pacífico Central'),(4,'Oficina Regional Chorotega'),(5,'Oficina Regional Brunca');
/*!40000 ALTER TABLE `catalogoregion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comparecencia`
--

DROP TABLE IF EXISTS `comparecencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comparecencia` (
  `idComparecencia` int NOT NULL AUTO_INCREMENT,
  `codigoSILAC` varchar(15) NOT NULL,
  `idCaso` int NOT NULL,
  `ubicacion` varchar(200) NOT NULL,
  `fecha` date NOT NULL,
  PRIMARY KEY (`idComparecencia`),
  KEY `fk_Comparecencia_CatalogoCaso1_idx` (`idCaso`),
  CONSTRAINT `fk_Comparecencia_CatalogoCaso1` FOREIGN KEY (`idCaso`) REFERENCES `catalogocaso` (`idCaso`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comparecencia`
--

LOCK TABLES `comparecencia` WRITE;
/*!40000 ALTER TABLE `comparecencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `comparecencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gestionado`
--

DROP TABLE IF EXISTS `gestionado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gestionado` (
  `idGestionado` int NOT NULL AUTO_INCREMENT,
  `idPersona` int DEFAULT NULL,
  `idPersonaJuridica` int DEFAULT NULL,
  `idComparecencia` int NOT NULL,
  PRIMARY KEY (`idGestionado`),
  KEY `fk_Gestionado_Persona1_idx` (`idPersona`),
  KEY `fk_Gestionado_Comparecencia1_idx` (`idComparecencia`),
  KEY `fk_Gestionado_PersonaJuridica1_idx` (`idPersonaJuridica`),
  CONSTRAINT `fk_Gestionado_Comparecencia1` FOREIGN KEY (`idComparecencia`) REFERENCES `comparecencia` (`idComparecencia`),
  CONSTRAINT `fk_Gestionado_Persona1` FOREIGN KEY (`idPersona`) REFERENCES `persona` (`idPersona`),
  CONSTRAINT `fk_Gestionado_PersonaJuridica1` FOREIGN KEY (`idPersonaJuridica`) REFERENCES `personajuridica` (`idPersonaJuridica`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gestionado`
--

LOCK TABLES `gestionado` WRITE;
/*!40000 ALTER TABLE `gestionado` DISABLE KEYS */;
/*!40000 ALTER TABLE `gestionado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gestionante`
--

DROP TABLE IF EXISTS `gestionante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gestionante` (
  `idGestionante` int NOT NULL AUTO_INCREMENT,
  `idPersona` int DEFAULT NULL,
  `idPersonaJuridica` int DEFAULT NULL,
  `idComparecencia` int NOT NULL,
  PRIMARY KEY (`idGestionante`),
  KEY `fk_Gestionante_Comparecencia1_idx` (`idComparecencia`),
  KEY `fk_Gestionante_PersonaJuridica1_idx` (`idPersonaJuridica`),
  KEY `fk_Gestionante_Persona1` (`idPersona`),
  CONSTRAINT `fk_Gestionante_Comparecencia1` FOREIGN KEY (`idComparecencia`) REFERENCES `comparecencia` (`idComparecencia`),
  CONSTRAINT `fk_Gestionante_Persona1` FOREIGN KEY (`idPersona`) REFERENCES `persona` (`idPersona`),
  CONSTRAINT `fk_Gestionante_PersonaJuridica1` FOREIGN KEY (`idPersonaJuridica`) REFERENCES `personajuridica` (`idPersonaJuridica`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gestionante`
--

LOCK TABLES `gestionante` WRITE;
/*!40000 ALTER TABLE `gestionante` DISABLE KEYS */;
/*!40000 ALTER TABLE `gestionante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inspector`
--

DROP TABLE IF EXISTS `inspector`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inspector` (
  `idPersona` int NOT NULL AUTO_INCREMENT,
  `idPuesto` int NOT NULL,
  `idRegion` int NOT NULL,
  `idOficina` int NOT NULL,
  PRIMARY KEY (`idPersona`),
  KEY `fk_Inspector_Usuario1_idx` (`idPersona`),
  KEY `fk_Inspector_CatalogoPuesto1_idx` (`idPuesto`),
  KEY `fk_Inspector_CatalogoRegion1_idx` (`idRegion`),
  KEY `fk_Inspector_CatalogoOficina1_idx` (`idOficina`),
  CONSTRAINT `fk_Inspector_CatalogoOficina1` FOREIGN KEY (`idOficina`) REFERENCES `catalogooficina` (`idOficina`),
  CONSTRAINT `fk_Inspector_CatalogoPuesto1` FOREIGN KEY (`idPuesto`) REFERENCES `catalogopuesto` (`idPuesto`),
  CONSTRAINT `fk_Inspector_CatalogoRegion1` FOREIGN KEY (`idRegion`) REFERENCES `catalogoregion` (`idRegion`),
  CONSTRAINT `fk_Inspector_Usuario1` FOREIGN KEY (`idPersona`) REFERENCES `usuario` (`idPersona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inspector`
--

LOCK TABLES `inspector` WRITE;
/*!40000 ALTER TABLE `inspector` DISABLE KEYS */;
/*!40000 ALTER TABLE `inspector` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inspectorxcomparecencia`
--

DROP TABLE IF EXISTS `inspectorxcomparecencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inspectorxcomparecencia` (
  `idPersona` int NOT NULL,
  `idComparecencia` int NOT NULL,
  KEY `fk_Comparecencia_has_Inspector_Inspector1_idx` (`idPersona`),
  KEY `fk_Comparecencia_has_Inspector_Comparecencia1` (`idComparecencia`),
  CONSTRAINT `fk_Comparecencia_has_Inspector_Comparecencia1` FOREIGN KEY (`idComparecencia`) REFERENCES `comparecencia` (`idComparecencia`),
  CONSTRAINT `fk_Comparecencia_has_Inspector_Inspector1` FOREIGN KEY (`idPersona`) REFERENCES `inspector` (`idPersona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inspectorxcomparecencia`
--

LOCK TABLES `inspectorxcomparecencia` WRITE;
/*!40000 ALTER TABLE `inspectorxcomparecencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `inspectorxcomparecencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persona`
--

DROP TABLE IF EXISTS `persona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persona` (
  `idPersona` int NOT NULL AUTO_INCREMENT,
  `cedula` varchar(15) NOT NULL,
  `primerNombre` varchar(45) NOT NULL,
  `segundoNombre` varchar(45) DEFAULT NULL,
  `primerApellido` varchar(45) NOT NULL,
  `segundoApellido` varchar(45) NOT NULL,
  PRIMARY KEY (`idPersona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persona`
--

LOCK TABLES `persona` WRITE;
/*!40000 ALTER TABLE `persona` DISABLE KEYS */;
/*!40000 ALTER TABLE `persona` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personajuridica`
--

DROP TABLE IF EXISTS `personajuridica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `personajuridica` (
  `idPersonaJuridica` int NOT NULL AUTO_INCREMENT,
  `cedulaJuridica` varchar(13) NOT NULL,
  `nombreRazonSocial` varchar(45) NOT NULL,
  PRIMARY KEY (`idPersonaJuridica`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personajuridica`
--

LOCK TABLES `personajuridica` WRITE;
/*!40000 ALTER TABLE `personajuridica` DISABLE KEYS */;
/*!40000 ALTER TABLE `personajuridica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `representante`
--

DROP TABLE IF EXISTS `representante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `representante` (
  `idPersona` int NOT NULL,
  `idGestionante` int DEFAULT NULL,
  `idGestionado` int DEFAULT NULL,
  PRIMARY KEY (`idPersona`),
  KEY `fk_Representante_Persona1_idx` (`idPersona`),
  KEY `fk_Representante_Gestionante1_idx` (`idGestionante`),
  KEY `fk_Representante_Gestionado1_idx` (`idGestionado`),
  CONSTRAINT `fk_Representante_Gestionado1` FOREIGN KEY (`idGestionado`) REFERENCES `gestionado` (`idGestionado`),
  CONSTRAINT `fk_Representante_Gestionante1` FOREIGN KEY (`idGestionante`) REFERENCES `gestionante` (`idGestionante`),
  CONSTRAINT `fk_Representante_Persona1` FOREIGN KEY (`idPersona`) REFERENCES `persona` (`idPersona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `representante`
--

LOCK TABLES `representante` WRITE;
/*!40000 ALTER TABLE `representante` DISABLE KEYS */;
/*!40000 ALTER TABLE `representante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testigo`
--

DROP TABLE IF EXISTS `testigo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `testigo` (
  `idPersona` int NOT NULL,
  `idGestionante` int DEFAULT NULL,
  `idGestionado` int DEFAULT NULL,
  PRIMARY KEY (`idPersona`),
  KEY `fk_Testigo_Persona1_idx` (`idPersona`),
  KEY `fk_Testigo_Gestionante1_idx` (`idGestionante`),
  KEY `fk_Testigo_Gestionado1_idx` (`idGestionado`),
  CONSTRAINT `fk_Testigo_Gestionado1` FOREIGN KEY (`idGestionado`) REFERENCES `gestionado` (`idGestionado`),
  CONSTRAINT `fk_Testigo_Gestionante1` FOREIGN KEY (`idGestionante`) REFERENCES `gestionante` (`idGestionante`),
  CONSTRAINT `fk_Testigo_Persona1` FOREIGN KEY (`idPersona`) REFERENCES `persona` (`idPersona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testigo`
--

LOCK TABLES `testigo` WRITE;
/*!40000 ALTER TABLE `testigo` DISABLE KEYS */;
/*!40000 ALTER TABLE `testigo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `idPersona` int NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `contraseña` varchar(45) NOT NULL,
  `enLinea` tinyint NOT NULL,
  `autorizado` varchar(1) NOT NULL DEFAULT 'P',
  PRIMARY KEY (`idPersona`),
  KEY `fk_Usuario_Persona_idx` (`idPersona`),
  CONSTRAINT `fk_Usuario_Persona` FOREIGN KEY (`idPersona`) REFERENCES `persona` (`idPersona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-18 13:13:25
