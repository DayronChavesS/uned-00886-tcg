package models;

import java.io.Serializable;

public class Usuario implements Serializable{
    
    private Persona persona;
    private String email;
    private String contraseña;
    private Boolean enLinea;

    /*
        DECLARACION DE TAMAÑOS
     */
    private static final int EMAIL_SIZE = 45;
    private static final int CONTRASENA_SIZE = 45;

    /*
        CONSTRUCTOR
        1. El constructor aplica las restricciones de tamaño
        Arroja excepcion si el input excede los limites de la base de datos.
        Los sets tambien tienen este comportamiento.
        2. Valida que el correo y la constraseña cumpla los requisitos necesarios
     */
   public Usuario(){
   
   }
   
    public Usuario(String email, String contraseña, Boolean enLinea) throws Exception {
        setEmail(email,true);
        setContraseña(contraseña);
        setEnLinea(enLinea);
    }

        
    /*
        GETTERS & SETTERS
     */

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    
    public final String getEmail() {
        return email;
    }

    public final void setEmail(String email, Boolean modoVerificacion) throws Exception {
        if(modoVerificacion){
            if ((email == null)||("".equals(email))) {
                throw new Exception("email null error");
            }

            if (email.replace("@mtss.go.cr", "").equals("")) {
                throw new Exception("email null error");
            }

            if(!email.contains("@mtss.go.cr")){
                throw new Exception("email format error");
            }
            if (email.length() > EMAIL_SIZE) {
                throw new Exception("email size error");
            }
        }
        this.email = email;
    }

    public final String getContraseña() {
        return contraseña;
    }

    public final void setContraseña(String contraseña) throws Exception {
        if ((contraseña == null)||("".equals(contraseña))) {
            throw new Exception("contraseña null error");
        }
        
        if (contraseña.length() > CONTRASENA_SIZE) {
            throw new Exception("contraseña big size error");
        }
        
        if (contraseña.length() < 6) {
            throw new Exception("contraseña small size error");
        }
        
        this.contraseña = contraseña;
    }

    public final Boolean getEnLinea() {
        return enLinea;
    }

    public final void setEnLinea(Boolean enLinea) {
        this.enLinea = enLinea;
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