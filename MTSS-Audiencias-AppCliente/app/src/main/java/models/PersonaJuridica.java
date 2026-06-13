package models;
import database.MetodosDatabase;

public class PersonaJuridica {
    MetodosDatabase database = new MetodosDatabase();

    /*
        DECLARACION DE VARIABLES
     */
    private int idPersonaJuridica;
    private String cedulaJuridica;
    private String nombreRazonSocial;

    /*
        DECLARACION DE TAMAÑOS
     */
    private static final int CEDULA_JURIDICA_SIZE = 13;
    private static final int RAZON_SOCIAL_SIZE = 45;

    /*
        CONSTRUCTOR
        El constructor aplica las restricciones de tamaño
        null, reporta excepciones si encuentra problemas.
     */

    public PersonaJuridica() {
    }
    
    
    public PersonaJuridica(int idPersonaJuridica, String cedulaJuridica, 
            String nombreRazonSocial) throws Exception {
        
        setIdPersonaJuridica(idPersonaJuridica);
        setCedulaJuridica(cedulaJuridica,true);
        setNombreRazonSocial(nombreRazonSocial);
        
    }
    
    /*
    GETTERS & SETTERS
     */
    
    public final int getIdPersonaJuridica() {
        return idPersonaJuridica;
    }

  
    public final void setIdPersonaJuridica(int idPersonaJuridica) {    
        this.idPersonaJuridica = idPersonaJuridica;
    }

    public final String getCedulaJuridica() {
        return cedulaJuridica;
    }

    public final void setCedulaJuridica(String cedulaJuridica, Boolean modoVerificacion) throws Exception {
        if(modoVerificacion){
            if ((cedulaJuridica == null) ||(" -   -      ".equals(cedulaJuridica))) {
                throw new Exception("cedula juridica null error");
            }

            if (cedulaJuridica.length() > CEDULA_JURIDICA_SIZE) {
                throw new Exception("cedula juridica size error");
            }

            if(!database.cedulaJuridicaIsUnique(cedulaJuridica)){ 
                throw new Exception("cedula juridica UNIQUE error");
            }
        }
        this.cedulaJuridica = cedulaJuridica;
    }

    public final String getNombreRazonSocial() {
        return nombreRazonSocial;
    }

    public final void setNombreRazonSocial(String nombreRazonSocial) throws Exception{
        if ((nombreRazonSocial == null) ||("".equals(nombreRazonSocial))) {
            throw new Exception("razon social null error");
        }
        
        if (nombreRazonSocial.length() > RAZON_SOCIAL_SIZE) {
            throw new Exception("razon social size error");
        }
        this.nombreRazonSocial = nombreRazonSocial;
    }

    @Override
    public String toString() {
    return  """
            
            Persona Juridica
            ================
            Cedula Juridica: 
            """+cedulaJuridica+
            """
            \nNombre Razon Social: 
            """+nombreRazonSocial;
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