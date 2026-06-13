package models;
import database.MetodosDatabase;

public class Persona{
    MetodosDatabase database = new MetodosDatabase();
    /*
        DECLARACION DE VARIABLES
     */
    private int idPersona;
    private String cedula;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;

    /*
        DECLARACION DE TAMAÑOS
     */
    private static final int CEDULA_MIN_SIZE = 9;
    private static final int CEDULA_MAX_SIZE = 13;
    private static final int PRIMER_NOMBRE_SIZE = 45;
    private static final int SEGUNDO_NOMBRE_SIZE = 45;
    private static final int PRIMER_APELLIDO_SIZE = 45;
    private static final int SEGUNDO_APELLIDO_SIZE = 45;

    /*
        CONSTRUCTOR
        El constructor aplica las restricciones de tamaño
        Arroja excepcion si el input excede los limites de la base de datos.
        Los sets tambien tienen este comportamiento.
     */
    
    public Persona(){
    
    }
    
    Persona(int idPersona, String cedula, String primerNombre, String segundoNombre, 
            String primerApellido, String segundoApellido) throws Exception {
        setIdPersona(idPersona);
        setCedula(cedula,true);
        setPrimerNombre(primerNombre);
        setSegundoNombre(segundoNombre);
        setPrimerApellido(primerApellido);
        setSegundoApellido(segundoApellido);
    }

    /*
        GETTERS & SETTERS
     */

    public final int getIdPersona() {
        return idPersona;
    }

    public final void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }
    
    public final String getCedula() {
        return cedula;
    }

    public final void setCedula(String cedula, Boolean modoVerificacion) throws Exception{
        if(modoVerificacion){
            if ((cedula == null) || ("".equals(cedula))) {
                throw new Exception("cedula null error");
            }

            if (cedula.length() > CEDULA_MAX_SIZE) {
                throw new Exception("cedula max size error");
            }
            
            if (cedula.length() < CEDULA_MIN_SIZE) {
                throw new Exception("cedula min size error");
            }

            if(!database.cedulaIsUnique(cedula)){ 
                throw new Exception("cedula UNIQUE error");
            }
        }
        this.cedula = cedula;
    }

    public final String getPrimerNombre() {
        return primerNombre;
    }

    public final void setPrimerNombre(String primerNombre) throws Exception{
        if ((primerNombre == null)||("".equals(primerNombre))) {
            throw new Exception("primerNombre null error");
        }
        
        if (primerNombre.length() > PRIMER_NOMBRE_SIZE) {
            throw new Exception("primerNombre size error");
        }
        
        this.primerNombre = primerNombre;
    }

    public final String getSegundoNombre() {
        return segundoNombre;
    }

    public final void setSegundoNombre(String segundoNombre) throws Exception{
        
        if (!(segundoNombre == null)) {
            if (segundoNombre.length() > SEGUNDO_NOMBRE_SIZE) {
                throw new Exception("segundoNombre size error");
            }        
        }

        this.segundoNombre = segundoNombre;
    }

    public final String getPrimerApellido() {
        return primerApellido;
    }

    public final void setPrimerApellido(String primerApellido) throws Exception{
        if ((primerApellido == null)||("".equals(primerApellido))) {
            throw new Exception("primerApellido null error");
        }

        if (primerApellido.length() > PRIMER_APELLIDO_SIZE) {
            throw new Exception("primerApellido size error");
        }
        
        this.primerApellido = primerApellido;
    }

    public final String getSegundoApellido() {
        return segundoApellido;
    }

    public final void setSegundoApellido(String segundoApellido) throws Exception{
        if ((segundoApellido == null)||("".equals(segundoApellido))) {
            throw new Exception("segundoApellido null error");
        }
        
        if (segundoApellido.length() > SEGUNDO_APELLIDO_SIZE) {
            throw new Exception("segundoApellido size error");
        }
        
        this.segundoApellido = segundoApellido;
    }

    @Override
    public String toString() {
    return  """
            
            Persona
            =======
            Identificacion: 
            """+cedula+
            """
            \nNombre Completo: 
            """+primerNombre+" "+(segundoNombre == null ? segundoNombre:"")+" "+primerApellido+" "+segundoApellido;        
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