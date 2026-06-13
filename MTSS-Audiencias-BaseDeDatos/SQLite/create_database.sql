--
-- Archivo generado con SQLiteStudio v3.4.4 el dom. jun. 18 13:27:39 2023
--
-- Codificaci�n de texto usada: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Tabla: acompañante
DROP TABLE IF EXISTS acompañante;

CREATE TABLE IF NOT EXISTS acompañante (
    idPersona       INTEGER      NOT NULL,
    idGestionante   INTEGER      DEFAULT NULL,
    idGestionado    INTEGER      DEFAULT NULL,
    tipoacompañante VARCHAR (30) NOT NULL CHECK(length(tipoacompañante) <= 30),
    enCondicionDe   VARCHAR (45) DEFAULT NULL CHECK(length(enCondicionDe) <= 45),
    PRIMARY KEY (
        idPersona
    ),
    CONSTRAINT fk_acompañante_Gestionado1 FOREIGN KEY (
        idGestionado
    )
    REFERENCES gestionado (idGestionado),
    CONSTRAINT fk_acompañante_Gestionante1 FOREIGN KEY (
        idGestionante
    )
    REFERENCES gestionante (idGestionante),
    CONSTRAINT fk_acompañante_Persona1 FOREIGN KEY (
        idPersona
    )
    REFERENCES persona (idPersona) 
);


-- Tabla: audio
DROP TABLE IF EXISTS audio;

CREATE TABLE IF NOT EXISTS audio (
    idAudio                INTEGER       NOT NULL
                                         PRIMARY KEY AUTOINCREMENT,
    nombreAudio            VARCHAR (45)  NOT NULL CHECK(length(nombreAudio) <= 45),
    duracionAudio          VARCHAR (8)   DEFAULT NULL CHECK(length(duracionAudio) <= 8),
    pathArchivoAudio       VARCHAR (999) NOT NULL CHECK(length(pathArchivoAudio) <= 999),
    pathArchivoAnotaciones VARCHAR (999) NOT NULL CHECK(length(pathArchivoAnotaciones) <= 999)
);


-- Tabla: audioxcomparecencia
DROP TABLE IF EXISTS audioxcomparecencia;

CREATE TABLE IF NOT EXISTS audioxcomparecencia (
    idAudio         INTEGER NOT NULL,
    idComparecencia INTEGER NOT NULL,
    CONSTRAINT fk_Audio_has_Comparecencia_Audio1 FOREIGN KEY (
        idAudio
    )
    REFERENCES audio (idAudio),
    CONSTRAINT fk_Audio_has_Comparecencia_Comparecencia1 FOREIGN KEY (
        idComparecencia
    )
    REFERENCES comparecencia (idComparecencia) 
);


-- Tabla: catalogocaso
DROP TABLE IF EXISTS catalogocaso;

CREATE TABLE IF NOT EXISTS catalogocaso (
    idCaso   INTEGER      NOT NULL,
    tipoCaso VARCHAR (56) NOT NULL CHECK(length(tipoCaso) <= 56),
    PRIMARY KEY (
        idCaso
    )
);

INSERT INTO catalogocaso (
                             idCaso,
                             tipoCaso
                         )
                         VALUES (
                             0,
                             'Discriminación en razón de género, edad, entre otros'
                         );

INSERT INTO catalogocaso (
                             idCaso,
                             tipoCaso
                         )
                         VALUES (
                             1,
                             'Despido de Trabajador Licencia de Paternidad'
                         );

INSERT INTO catalogocaso (
                             idCaso,
                             tipoCaso
                         )
                         VALUES (
                             2,
                             'Trabajadora embarazada o estado de lactancia'
                         );

INSERT INTO catalogocaso (
                             idCaso,
                             tipoCaso
                         )
                         VALUES (
                             3,
                             'Trabajadora embarazada. Restricción de derechos'
                         );

INSERT INTO catalogocaso (
                             idCaso,
                             tipoCaso
                         )
                         VALUES (
                             4,
                             'Fraccionamiento de jornada laboral'
                         );

INSERT INTO catalogocaso (
                             idCaso,
                             tipoCaso
                         )
                         VALUES (
                             5,
                             'Hostigamiento laboral'
                         );

INSERT INTO catalogocaso (
                             idCaso,
                             tipoCaso
                         )
                         VALUES (
                             6,
                             'Hostigamiento sexual'
                         );

INSERT INTO catalogocaso (
                             idCaso,
                             tipoCaso
                         )
                         VALUES (
                             7,
                             'Despido de aforado sindicato'
                         );


-- Tabla: catalogooficina
DROP TABLE IF EXISTS catalogooficina;

CREATE TABLE IF NOT EXISTS catalogooficina (
    idOficina INTEGER      NOT NULL,
    nombre    VARCHAR (34) NOT NULL CHECK(length(nombre) <= 34),
    PRIMARY KEY (
        idOficina
    )
);

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                0,
                                'Oficina Provincial de Cartago'
                            );

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                1,
                                'Oficina Provincial de Heredia'
                            );

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                2,
                                'Oficina Cantonal de Puriscal'
                            );

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                3,
                                'Oficina Cantonal de los Santos'
                            );

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                4,
                                'Oficina Cantonal de Ciudad Quesada'
                            );

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                5,
                                'Oficina Cantonal de Grecia'
                            );

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                6,
                                'Oficina Cantonal de Naranjo'
                            );

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                7,
                                'Oficina Cantonal de La Fortuna'
                            );

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                8,
                                'Oficina Cantonal de Siquirres'
                            );

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                9,
                                'Oficina Cantonal de Pococ�'
                            );

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                10,
                                'Oficina Cantonal de Talamanca'
                            );

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                11,
                                'Oficina Cantonal de Turrialba'
                            );

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                12,
                                'Oficina Cantonal de Gu�cimo'
                            );

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                13,
                                'Oficina Cantonal de San Ram�n'
                            );

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                14,
                                'Oficina Cantonal de Orotina'
                            );

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                15,
                                'Oficina Cantonal de Aguirre-Quepos'
                            );

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                16,
                                'Oficina Cantonal de Santa Cruz'
                            );

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                17,
                                'Oficina Cantonal de Ca�as'
                            );

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                18,
                                'Oficina Cantonal de Nicoya'
                            );

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                19,
                                'Oficina Cantonal Nandayure'
                            );

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                20,
                                'Oficina Cantonal Upala'
                            );

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                21,
                                'Oficina Cantonal de Palmar Norte'
                            );

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                22,
                                'Oficina Cantonal de Corredores'
                            );

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                23,
                                'Oficina Cantonal de Golfito'
                            );

INSERT INTO catalogooficina (
                                idOficina,
                                nombre
                            )
                            VALUES (
                                24,
                                'Oficina Cantonal de Coto Brus'
                            );


-- Tabla: catalogopuesto
DROP TABLE IF EXISTS catalogopuesto;

CREATE TABLE IF NOT EXISTS catalogopuesto (
    idPuesto INTEGER      NOT NULL,
    nombre   VARCHAR (30) NOT NULL CHECK(length(nombre) <= 30),
    PRIMARY KEY (
        idPuesto
    )
);

INSERT INTO catalogopuesto (
                               idPuesto,
                               nombre
                           )
                           VALUES (
                               0,
                               'Administrador(a)'
                           );

INSERT INTO catalogopuesto (
                               idPuesto,
                               nombre
                           )
                           VALUES (
                               1,
                               'Jefe Regional'
                           );

INSERT INTO catalogopuesto (
                               idPuesto,
                               nombre
                           )
                           VALUES (
                               2,
                               'Coordinador(a) de Inspecci�n'
                           );

INSERT INTO catalogopuesto (
                               idPuesto,
                               nombre
                           )
                           VALUES (
                               3,
                               'Inspector(a)'
                           );

INSERT INTO catalogopuesto (
                               idPuesto,
                               nombre
                           )
                           VALUES (
                               4,
                               'Asesor(a) Legal'
                           );


-- Tabla: catalogoregion
DROP TABLE IF EXISTS catalogoregion;

CREATE TABLE IF NOT EXISTS catalogoregion (
    idRegion INTEGER      NOT NULL,
    nombre   VARCHAR (33) NOT NULL CHECK(length(nombre) <= 33),
    PRIMARY KEY (
        idRegion
    )
);

INSERT INTO catalogoregion (
                               idRegion,
                               nombre
                           )
                           VALUES (
                               0,
                               'Oficina Regional Central'
                           );

INSERT INTO catalogoregion (
                               idRegion,
                               nombre
                           )
                           VALUES (
                               1,
                               'Oficina Regional Huetar Norte'
                           );

INSERT INTO catalogoregion (
                               idRegion,
                               nombre
                           )
                           VALUES (
                               2,
                               'Oficina Regional Huetar Caribe'
                           );

INSERT INTO catalogoregion (
                               idRegion,
                               nombre
                           )
                           VALUES (
                               3,
                               'Oficina Regional Pac�fico Central'
                           );

INSERT INTO catalogoregion (
                               idRegion,
                               nombre
                           )
                           VALUES (
                               4,
                               'Oficina Regional Chorotega'
                           );

INSERT INTO catalogoregion (
                               idRegion,
                               nombre
                           )
                           VALUES (
                               5,
                               'Oficina Regional Brunca'
                           );


-- Tabla: comparecencia
DROP TABLE IF EXISTS comparecencia;

CREATE TABLE IF NOT EXISTS comparecencia (
    idComparecencia INTEGER       NOT NULL
                                  PRIMARY KEY AUTOINCREMENT,
    codigoSILAC     VARCHAR (15)  NOT NULL CHECK(length(codigoSILAC) <= 15),
    idCaso          INTEGER       NOT NULL,
    ubicacion       VARCHAR (200) NOT NULL CHECK(length(ubicacion) <= 200),
    linkExpediente  VARCHAR (999) NOT NULL CHECK(length(linkExpediente) <= 999),
    fecha           DATE          NOT NULL,
    CONSTRAINT fk_Comparecencia_CatalogoCaso1 FOREIGN KEY (
        idCaso
    )
    REFERENCES catalogocaso (idCaso) 
);


-- Tabla: gestionado
DROP TABLE IF EXISTS gestionado;

CREATE TABLE IF NOT EXISTS gestionado (
    idGestionado      INTEGER NOT NULL
                              PRIMARY KEY AUTOINCREMENT,
    idPersona         INTEGER DEFAULT NULL,
    idPersonaJuridica INTEGER DEFAULT NULL,
    idComparecencia   INTEGER NOT NULL,
    CONSTRAINT fk_Gestionado_Comparecencia1 FOREIGN KEY (
        idComparecencia
    )
    REFERENCES comparecencia (idComparecencia),
    CONSTRAINT fk_Gestionado_Persona1 FOREIGN KEY (
        idPersona
    )
    REFERENCES persona (idPersona),
    CONSTRAINT fk_Gestionado_PersonaJuridica1 FOREIGN KEY (
        idPersonaJuridica
    )
    REFERENCES personajuridica (idPersonaJuridica) 
);


-- Tabla: gestionante
DROP TABLE IF EXISTS gestionante;

CREATE TABLE IF NOT EXISTS gestionante (
    idGestionante     INTEGER NOT NULL
                              PRIMARY KEY AUTOINCREMENT,
    idPersona         INTEGER DEFAULT NULL,
    idPersonaJuridica INTEGER DEFAULT NULL,
    idComparecencia   INTEGER NOT NULL,
    CONSTRAINT fk_Gestionante_Comparecencia1 FOREIGN KEY (
        idComparecencia
    )
    REFERENCES comparecencia (idComparecencia),
    CONSTRAINT fk_Gestionante_Persona1 FOREIGN KEY (
        idPersona
    )
    REFERENCES persona (idPersona),
    CONSTRAINT fk_Gestionante_PersonaJuridica1 FOREIGN KEY (
        idPersonaJuridica
    )
    REFERENCES personajuridica (idPersonaJuridica) 
);


-- Tabla: inspector
DROP TABLE IF EXISTS inspector;

CREATE TABLE IF NOT EXISTS inspector (
    idPersona INTEGER NOT NULL
                      PRIMARY KEY AUTOINCREMENT,
    idPuesto  INTEGER NOT NULL,
    idRegion  INTEGER NOT NULL,
    idOficina INTEGER NOT NULL,
    CONSTRAINT fk_Inspector_CatalogoOficina1 FOREIGN KEY (
        idOficina
    )
    REFERENCES catalogooficina (idOficina),
    CONSTRAINT fk_Inspector_CatalogoPuesto1 FOREIGN KEY (
        idPuesto
    )
    REFERENCES catalogopuesto (idPuesto),
    CONSTRAINT fk_Inspector_CatalogoRegion1 FOREIGN KEY (
        idRegion
    )
    REFERENCES catalogoregion (idRegion),
    CONSTRAINT fk_Inspector_Usuario1 FOREIGN KEY (
        idPersona
    )
    REFERENCES usuario (idPersona) 
);


-- Tabla: inspectorxcomparecencia
DROP TABLE IF EXISTS inspectorxcomparecencia;

CREATE TABLE IF NOT EXISTS inspectorxcomparecencia (
    idPersona       INTEGER NOT NULL,
    idComparecencia INTEGER NOT NULL,
    CONSTRAINT fk_Comparecencia_has_Inspector_Comparecencia1 FOREIGN KEY (
        idComparecencia
    )
    REFERENCES comparecencia (idComparecencia),
    CONSTRAINT fk_Comparecencia_has_Inspector_Inspector1 FOREIGN KEY (
        idPersona
    )
    REFERENCES inspector (idPersona) 
);


-- Tabla: persona
DROP TABLE IF EXISTS persona;

CREATE TABLE IF NOT EXISTS persona (
    idPersona       INTEGER      NOT NULL
                                 PRIMARY KEY AUTOINCREMENT,
    cedula          VARCHAR (15) NOT NULL CHECK(length(cedula) <= 15),
    primerNombre    VARCHAR (45) NOT NULL CHECK(length(primerNombre) <= 45),
    segundoNombre   VARCHAR (45) DEFAULT NULL CHECK(length(segundoNombre) <= 45),
    primerApellido  VARCHAR (45) NOT NULL CHECK(length(primerApellido) <= 45),
    segundoApellido VARCHAR (45) NOT NULL CHECK(length(segundoApellido) <= 45)
);


-- Tabla: personajuridica
DROP TABLE IF EXISTS personajuridica;

CREATE TABLE IF NOT EXISTS personajuridica (
    idPersonaJuridica INTEGER      NOT NULL
                                   PRIMARY KEY AUTOINCREMENT,
    cedulaJuridica    VARCHAR (13) NOT NULL CHECK(length(cedulaJuridica) <= 13),
    nombreRazonSocial VARCHAR (45) NOT NULL CHECK(length(nombreRazonSocial) <= 45)
);


-- Tabla: representante
DROP TABLE IF EXISTS representante;

CREATE TABLE IF NOT EXISTS representante (
    idPersona     INTEGER NOT NULL,
    idGestionante INTEGER DEFAULT NULL,
    idGestionado  INTEGER DEFAULT NULL,
    PRIMARY KEY (
        idPersona
    ),
    CONSTRAINT fk_Representante_Gestionado1 FOREIGN KEY (
        idGestionado
    )
    REFERENCES gestionado (idGestionado),
    CONSTRAINT fk_Representante_Gestionante1 FOREIGN KEY (
        idGestionante
    )
    REFERENCES gestionante (idGestionante),
    CONSTRAINT fk_Representante_Persona1 FOREIGN KEY (
        idPersona
    )
    REFERENCES persona (idPersona) 
);


-- Tabla: testigo
DROP TABLE IF EXISTS testigo;

CREATE TABLE IF NOT EXISTS testigo (
    idPersona     INTEGER NOT NULL,
    idGestionante INTEGER DEFAULT NULL,
    idGestionado  INTEGER DEFAULT NULL,
    PRIMARY KEY (
        idPersona
    ),
    CONSTRAINT fk_Testigo_Gestionado1 FOREIGN KEY (
        idGestionado
    )
    REFERENCES gestionado (idGestionado),
    CONSTRAINT fk_Testigo_Gestionante1 FOREIGN KEY (
        idGestionante
    )
    REFERENCES gestionante (idGestionante),
    CONSTRAINT fk_Testigo_Persona1 FOREIGN KEY (
        idPersona
    )
    REFERENCES persona (idPersona) 
);


-- Tabla: usuario
DROP TABLE IF EXISTS usuario;

CREATE TABLE IF NOT EXISTS usuario (
    idPersona  INTEGER       NOT NULL
                             PRIMARY KEY AUTOINCREMENT,
    email      VARCHAR (100) NOT NULL CHECK(length(email) <= 100),
    contraseña VARCHAR (45)  NOT NULL CHECK(length(contraseña) <= 45),
    enLinea    INTEGER       NOT NULL,
    autorizado VARCHAR (1)   NOT NULL CHECK(length(autorizado) <= 1)
                             DEFAULT 'P',
    CONSTRAINT fk_Usuario_Persona FOREIGN KEY (
        idPersona
    )
    REFERENCES persona (idPersona) 
);


-- �ndice: idx_acompañante_fk_acompañante_Gestionado1_idx
DROP INDEX IF EXISTS idx_acompañante_fk_acompañante_Gestionado1_idx;

CREATE INDEX IF NOT EXISTS idx_acompañante_fk_acompañante_Gestionado1_idx ON acompañante (
    idGestionado
);


-- �ndice: idx_acompañante_fk_acompañante_Gestionante1_idx
DROP INDEX IF EXISTS idx_acompañante_fk_acompañante_Gestionante1_idx;

CREATE INDEX IF NOT EXISTS idx_acompañante_fk_acompañante_Gestionante1_idx ON acompañante (
    idGestionante
);


-- �ndice: idx_acompañante_fk_acompañante_Persona1_idx
DROP INDEX IF EXISTS idx_acompañante_fk_acompañante_Persona1_idx;

CREATE INDEX IF NOT EXISTS idx_acompañante_fk_acompañante_Persona1_idx ON acompañante (
    idPersona
);


-- �ndice: idx_audioxcomparecencia_fk_Audio_has_Comparecencia_Audio1_idx
DROP INDEX IF EXISTS idx_audioxcomparecencia_fk_Audio_has_Comparecencia_Audio1_idx;

CREATE INDEX IF NOT EXISTS idx_audioxcomparecencia_fk_Audio_has_Comparecencia_Audio1_idx ON audioxcomparecencia (
    idAudio
);


-- �ndice: idx_audioxcomparecencia_fk_Audio_has_Comparecencia_Comparecencia1_idx
DROP INDEX IF EXISTS idx_audioxcomparecencia_fk_Audio_has_Comparecencia_Comparecencia1_idx;

CREATE INDEX IF NOT EXISTS idx_audioxcomparecencia_fk_Audio_has_Comparecencia_Comparecencia1_idx ON audioxcomparecencia (
    idComparecencia
);


-- �ndice: idx_comparecencia_fk_Comparecencia_CatalogoCaso1_idx
DROP INDEX IF EXISTS idx_comparecencia_fk_Comparecencia_CatalogoCaso1_idx;

CREATE INDEX IF NOT EXISTS idx_comparecencia_fk_Comparecencia_CatalogoCaso1_idx ON comparecencia (
    idCaso
);


-- �ndice: idx_gestionado_fk_Gestionado_Comparecencia1_idx
DROP INDEX IF EXISTS idx_gestionado_fk_Gestionado_Comparecencia1_idx;

CREATE INDEX IF NOT EXISTS idx_gestionado_fk_Gestionado_Comparecencia1_idx ON gestionado (
    idComparecencia
);


-- �ndice: idx_gestionado_fk_Gestionado_Persona1_idx
DROP INDEX IF EXISTS idx_gestionado_fk_Gestionado_Persona1_idx;

CREATE INDEX IF NOT EXISTS idx_gestionado_fk_Gestionado_Persona1_idx ON gestionado (
    idPersona
);


-- �ndice: idx_gestionado_fk_Gestionado_PersonaJuridica1_idx
DROP INDEX IF EXISTS idx_gestionado_fk_Gestionado_PersonaJuridica1_idx;

CREATE INDEX IF NOT EXISTS idx_gestionado_fk_Gestionado_PersonaJuridica1_idx ON gestionado (
    idPersonaJuridica
);


-- �ndice: idx_gestionante_fk_Gestionante_Comparecencia1_idx
DROP INDEX IF EXISTS idx_gestionante_fk_Gestionante_Comparecencia1_idx;

CREATE INDEX IF NOT EXISTS idx_gestionante_fk_Gestionante_Comparecencia1_idx ON gestionante (
    idComparecencia
);


-- �ndice: idx_gestionante_fk_Gestionante_Persona1
DROP INDEX IF EXISTS idx_gestionante_fk_Gestionante_Persona1;

CREATE INDEX IF NOT EXISTS idx_gestionante_fk_Gestionante_Persona1 ON gestionante (
    idPersona
);


-- �ndice: idx_gestionante_fk_Gestionante_PersonaJuridica1_idx
DROP INDEX IF EXISTS idx_gestionante_fk_Gestionante_PersonaJuridica1_idx;

CREATE INDEX IF NOT EXISTS idx_gestionante_fk_Gestionante_PersonaJuridica1_idx ON gestionante (
    idPersonaJuridica
);


-- �ndice: idx_inspector_fk_Inspector_CatalogoOficina1_idx
DROP INDEX IF EXISTS idx_inspector_fk_Inspector_CatalogoOficina1_idx;

CREATE INDEX IF NOT EXISTS idx_inspector_fk_Inspector_CatalogoOficina1_idx ON inspector (
    idOficina
);


-- �ndice: idx_inspector_fk_Inspector_CatalogoPuesto1_idx
DROP INDEX IF EXISTS idx_inspector_fk_Inspector_CatalogoPuesto1_idx;

CREATE INDEX IF NOT EXISTS idx_inspector_fk_Inspector_CatalogoPuesto1_idx ON inspector (
    idPuesto
);


-- �ndice: idx_inspector_fk_Inspector_CatalogoRegion1_idx
DROP INDEX IF EXISTS idx_inspector_fk_Inspector_CatalogoRegion1_idx;

CREATE INDEX IF NOT EXISTS idx_inspector_fk_Inspector_CatalogoRegion1_idx ON inspector (
    idRegion
);


-- �ndice: idx_inspector_fk_Inspector_Usuario1_idx
DROP INDEX IF EXISTS idx_inspector_fk_Inspector_Usuario1_idx;

CREATE INDEX IF NOT EXISTS idx_inspector_fk_Inspector_Usuario1_idx ON inspector (
    idPersona
);


-- �ndice: idx_inspectorxcomparecencia_fk_Comparecencia_has_Inspector_Comparecencia1
DROP INDEX IF EXISTS idx_inspectorxcomparecencia_fk_Comparecencia_has_Inspector_Comparecencia1;

CREATE INDEX IF NOT EXISTS idx_inspectorxcomparecencia_fk_Comparecencia_has_Inspector_Comparecencia1 ON inspectorxcomparecencia (
    idComparecencia
);


-- �ndice: idx_inspectorxcomparecencia_fk_Comparecencia_has_Inspector_Inspector1_idx
DROP INDEX IF EXISTS idx_inspectorxcomparecencia_fk_Comparecencia_has_Inspector_Inspector1_idx;

CREATE INDEX IF NOT EXISTS idx_inspectorxcomparecencia_fk_Comparecencia_has_Inspector_Inspector1_idx ON inspectorxcomparecencia (
    idPersona
);


-- �ndice: idx_representante_fk_Representante_Gestionado1_idx
DROP INDEX IF EXISTS idx_representante_fk_Representante_Gestionado1_idx;

CREATE INDEX IF NOT EXISTS idx_representante_fk_Representante_Gestionado1_idx ON representante (
    idGestionado
);


-- �ndice: idx_representante_fk_Representante_Gestionante1_idx
DROP INDEX IF EXISTS idx_representante_fk_Representante_Gestionante1_idx;

CREATE INDEX IF NOT EXISTS idx_representante_fk_Representante_Gestionante1_idx ON representante (
    idGestionante
);


-- �ndice: idx_representante_fk_Representante_Persona1_idx
DROP INDEX IF EXISTS idx_representante_fk_Representante_Persona1_idx;

CREATE INDEX IF NOT EXISTS idx_representante_fk_Representante_Persona1_idx ON representante (
    idPersona
);


-- �ndice: idx_testigo_fk_Testigo_Gestionado1_idx
DROP INDEX IF EXISTS idx_testigo_fk_Testigo_Gestionado1_idx;

CREATE INDEX IF NOT EXISTS idx_testigo_fk_Testigo_Gestionado1_idx ON testigo (
    idGestionado
);


-- �ndice: idx_testigo_fk_Testigo_Gestionante1_idx
DROP INDEX IF EXISTS idx_testigo_fk_Testigo_Gestionante1_idx;

CREATE INDEX IF NOT EXISTS idx_testigo_fk_Testigo_Gestionante1_idx ON testigo (
    idGestionante
);


-- �ndice: idx_testigo_fk_Testigo_Persona1_idx
DROP INDEX IF EXISTS idx_testigo_fk_Testigo_Persona1_idx;

CREATE INDEX IF NOT EXISTS idx_testigo_fk_Testigo_Persona1_idx ON testigo (
    idPersona
);


-- �ndice: idx_usuario_fk_Usuario_Persona_idx
DROP INDEX IF EXISTS idx_usuario_fk_Usuario_Persona_idx;

CREATE INDEX IF NOT EXISTS idx_usuario_fk_Usuario_Persona_idx ON usuario (
    idPersona
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
