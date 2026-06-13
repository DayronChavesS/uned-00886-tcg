package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import methods.Telemetria;

public class ConexionDatabase {
    
    Telemetria telemetria = new Telemetria();
    
    private static final String DATABASE_NAME = "comparecencias.db";
    private static final String LOCAL_DIR = System.getProperty("user.dir");
    private static final String DEV_PATH = "/src/main/resources/sqlite/"+DATABASE_NAME;
    private static final String EXE_PATH =  "/database/"+DATABASE_NAME;
    
    private static final String URL = "jdbc:sqlite:" + LOCAL_DIR + DEV_PATH;

    public Connection sqliteConexion = null;
    public Connection exportConexion = null;
    
    public ConexionDatabase() {

    }
    
    public void cerrarConexionSQLITE() {
        try {
            if(sqliteConexion != null){
                if(!sqliteConexion.isClosed()){
                    sqliteConexion.close();
                    sqliteConexion = null;
                    telemetria.logActivity("Se cerro conexion con base de datos sqlite.");
                }
            }
        } catch (SQLException ex) {
            telemetria.logActivity("Error al cerrar la conexion con la base de datos");
            telemetria.logException(ex);
        }
    
    }
    
    public void cerrarConexionEXPORT() {
        try {
            if(exportConexion != null){
                if((!exportConexion.isClosed())){
                    exportConexion.close();
                    exportConexion = null;
                    telemetria.logActivity("Se cerro conexion con base de datos temporal.");
                }
            }
        } catch (SQLException ex) {
            telemetria.logActivity("Error al cerrar la conexion con la base de datos");
            telemetria.logException(ex);
        }
    
    }
    
    public void abrirConexion() {
        try {
            if (sqliteConexion == null) {
                sqliteConexion = DriverManager.getConnection(URL);
                telemetria.logActivity("Conexion con base de datos.");
            }
        } catch (SQLException ex) {   
            telemetria.logActivity("Error al conectar a la base de datos.");
            telemetria.logActivity("SQLException: " + ex.getMessage());
            telemetria.logActivity("SQLState: " + ex.getSQLState());
            telemetria.logActivity("VendorError: " + ex.getErrorCode());
            telemetria.logException(ex);
        }
    }
    
    public void createTemporalDB(String pathComparecencia) {

        pathComparecencia += "data.db";
        String url = "jdbc:sqlite:"+pathComparecencia;

        try {
            exportConexion = DriverManager.getConnection(url);
            telemetria.logActivity("Se creo la base de datos para exportar datos.");
            
        } catch (SQLException ex) {
            telemetria.logActivity("Error al crear base de datos para exportar datos.");
            telemetria.logActivity("SQLException: " + ex.getMessage());
            telemetria.logActivity("SQLState: " + ex.getSQLState());
            telemetria.logActivity("VendorError: " + ex.getErrorCode());
            telemetria.logException(ex);
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