import interfaces.Inicio;
import interfaces.Configuracion;
import methods.Archivos;
import javax.swing.UIManager;
import methods.Telemetria;
import database.MetodosDatabase;
import javax.swing.JOptionPane;

public class Main {
    
    static Telemetria telemetria;
    static MetodosDatabase database;
    static Inicio inicio;
    static Configuracion configuracion;
    static Archivos archivo;


    public static void main(String[] args) {        

        try{
            initVariables();
            setearTemaSistema();
            testBaseDeDatos();
            iniciarTelemetria();
            
            if(archivo.archivoPropiedadesExiste()){
                archivo.cargarArchivoPropiedades();
                crearPantallaInicio();
                dispose();
            }else{
                crearPantallaConfiguracion();
                dispose();
            }
        } catch(Exception ex){
            telemetria.logActivity("Ha ocurrido un error al iniciar la aplicacion.");
            telemetria.logException(ex);
        }
    }  
    
    static void initVariables(){
        telemetria = new Telemetria();
        archivo = new Archivos();
        database = new MetodosDatabase();
    }
    
    static void iniciarTelemetria(){
        telemetria.escribirTelemetria();
        telemetria.logActivity("Inicio del programa.");
    }
    
    static void testBaseDeDatos() {
        if (!database.conexionIsValid()) {
            JOptionPane.showMessageDialog(null, """
                                            No se logro encontrar el archivo comparecencias.db.
                                            Imposible continuar.
                                            """,
                    "ERROR FATAL",
                    JOptionPane.ERROR_MESSAGE);

            telemetria.logActivity("Se sale del programa.");
            System.exit(0);
        }
    }
    
    static void setearTemaSistema(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            telemetria.logActivity("Fallo al configurar apariencia de la aplicacion. ");
            telemetria.logException(e);
        }
    }
    
    static void crearPantallaConfiguracion(){
        configuracion = new Configuracion();
        configuracion.setLocationRelativeTo(null);
        configuracion.setVisible(true);
    }
    
    static void crearPantallaInicio(){
        inicio = new Inicio();
        inicio.setLocationRelativeTo(null);
        inicio.setVisible(true);
    }
    
    static void dispose(){
        telemetria = null;
        database = null;
        archivo = null;
        inicio = null;
        configuracion = null;
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