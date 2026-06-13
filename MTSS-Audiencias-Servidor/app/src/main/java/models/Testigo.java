package models;

public class Testigo{
    /*
        DECLARACION DE VARIABLES
     */
    private Persona persona;
    private Gestionante gestionante;
    private Gestionado gestionado;
    
    /*
        DECLARACION DE TAMAÑOS:
        NO APLICA.
    */

    /*
        CONSTRUCTOR
     */
    
    public Testigo() {
    }

    public Testigo(int idTestigo, Persona persona, Gestionante gestionante, 
            Gestionado gestionado) {
        setPersona(persona);
        setGestionante(gestionante);
        setGestionado(gestionado);
    }
    
    /*
        GETTERS & SETTERS
     */

    public final Persona getPersona() {
        return persona;
    }

    public final void setPersona(Persona persona) {
        this.persona = persona;
    }

    public final Gestionante getGestionante() {
        return gestionante;
    }

    public final void setGestionante(Gestionante gestionante) {
        this.gestionante = gestionante;
    }

    public final Gestionado getGestionado() {
        return gestionado;
    }

    public final void setGestionado(Gestionado gestionado) {
        this.gestionado = gestionado;
    }

    @Override
    public String toString() {
        String part1 = "";
        String part2 = "";
        String part3 = "";
 
                
                
                part1= """

                        Testigo
                        =======
                        """+persona.toString();
                
                part2 = """
                        
                        Atestigua a
                        ===========
                        """;
                
        if(gestionante != null){
            part3 = gestionante.toString();
        
        }else if(gestionado != null){
            part3 = gestionado.toString();
        }
        
        return part1 + part2 + part3;
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