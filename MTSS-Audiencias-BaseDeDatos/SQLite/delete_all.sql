PRAGMA foreign_keys = OFF;

        DELETE FROM acompañante;
        DELETE FROM audio;
        DELETE FROM audioxcomparecencia;
        -- DELETE FROM catalogocaso;
        -- DELETE FROM catalogooficina;
        -- DELETE FROM catalogopuesto;
        -- DELETE FROM catalogoregion;
        DELETE FROM comparecencia;
        DELETE FROM gestionado;
        DELETE FROM gestionante;
        DELETE FROM inspector;
        DELETE FROM inspectorxcomparecencia;
        DELETE FROM persona;
        DELETE FROM personajuridica;
        DELETE FROM representante;
        DELETE FROM testigo;
        DELETE FROM usuario;
        DELETE FROM sqlite_sequence;
		
PRAGMA foreign_keys = ON;