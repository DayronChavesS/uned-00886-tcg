package models;

//clase correspondiente al empleado, trabajador, o quien puso la denuncia.
public class Gestionante {

    /*
        DECLARACION DE VARIABLES
     */
    private int idGestionante;
    private Persona persona; //imposible extender dos clases al mismo tiempo, insertar objeto en su lugar
    private PersonaJuridica personaJuridica;
    private Comparecencia comparecencia;

    public Gestionante() {
    }

    public Gestionante(int idGestionante, Persona persona, PersonaJuridica personaJuridica, 
            Comparecencia comparecencia) {
        setIdGestionante(idGestionante);
        setPersona(persona);
        setPersonaJuridica(personaJuridica);
        setComparecencia(comparecencia);
    }
    
     /*
        GETTERS & SETTERS
     */

    public final int getIdGestionante() {
        return idGestionante;
    }

    public final void setIdGestionante(int idGestionante) {
        this.idGestionante = idGestionante;
    }

    
    public final Persona getPersona() {
        return persona;
    }

    public final void setPersona(Persona persona) {
        this.persona = persona;
    }

    public final PersonaJuridica getPersonaJuridica() {
        return personaJuridica;
    }

    public final void setPersonaJuridica(PersonaJuridica personaJuridica) {
        this.personaJuridica = personaJuridica;
    }

    public final Comparecencia getComparecencia() {
        return comparecencia;
    }

    public final void setComparecencia(Comparecencia comparecencia) {
        this.comparecencia = comparecencia;
    }

    @Override
    public String toString() {
        String part1 = """
                       
                       Gestionante
                       ===========
                       """;
        
        
        if(persona != null){
            return part1+persona.toString();
        }else if(personaJuridica != null){
            return part1+personaJuridica.toString();
        }
        return "";
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