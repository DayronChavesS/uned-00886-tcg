package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDatabase {
        
    private static final String HOST_NAME = "localhost";
    private static final String PORT = "3306";
    private static final String DATBASE_NAME = "comparecencias";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://"+HOST_NAME+":"+PORT+"/"+DATBASE_NAME;

    public Connection mySqlConnection = null;
    
    public ConexionDatabase() {

    }
    
    public void cerrarConexion() {
        try {
            if (mySqlConnection != null) {
                if (!mySqlConnection.isClosed()) {
                    mySqlConnection.close();
                    mySqlConnection = null;
                }
            }
        } catch (SQLException ex) {
            System.out.print(ex);
        }
    
    }
    
    public void abrirConexion() {
        try {
            if (mySqlConnection == null) {
                mySqlConnection = DriverManager.getConnection(URL,USER,PASSWORD);
            }
        } catch (SQLException ex) {   
            System.out.print("Error al conectar a la base de datos.");
            System.out.print("SQLException: " + ex.getMessage());
            System.out.print("SQLState: " + ex.getSQLState());
            System.out.print("VendorError: " + ex.getErrorCode());
        }
    }
}
            

/*
UNIVERSIDAD ESTATAL A DISTANCIA
VICERRECTORIA ACADÉMICA 
ESCUELA DE CIENCIAS EXACTAS Y NATURALES 
CARRERA INGENIERÍA INFORMÁTICA 

Desarrollar una aplicación de escritorio
Para la administración de comparecencias del
Ministerio de Trabajo y Seguridad Social de la
Región Huetar Caribe

MODALIDAD ESCOGIDA: PROYECTO

PARTE PROGRAMADA
PARA OPTAR POR EL TÍTULO DE 
BACHILLER EN INGENIERÍA INFORMÁTICA 

PROPRIETARIO:
MOISES ROMERO PRADO
CEDULA 303370265

AUTORES:
ROBERT JESÚS CASCANTE ARAYA,
CÉDULA 305180118
CORREO jesuscascantearaya@gmail.com
TELEFONO 88943263
DAYRON ANTONY CHAVES SANDOVAL,
CÉDULA 305240018 
TELEFONO 83959225
CORREO dayron.chaves@pm.me

CENTRO UNIVERSITARIO DE TURRIALBA
PAC 2023-1
TURRIALBA, 2023  
*/