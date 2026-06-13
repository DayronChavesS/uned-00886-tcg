package models;

public class Acompañante{
    /*
        DECLARACION DE VARIABLES
     */
    private Persona persona;
    private Gestionante gestionante;
    private Gestionado gestionado;
    private String tipoAcompañante;
    private String enCondicionDe;
    
    /*
        DECLARACION DE TAMAÑOS:
    */
    
    private static final int TIPO_ACOMPANANTE_SIZE = 30;
    private static final int CONDICION_SIZE = 45;

    /*
        CONSTRUCTOR
    */

    public Acompañante() {
    }

    public Acompañante(int idAcompañante, Persona persona, Gestionante gestionante, 
            Gestionado gestionado, String tipoAcompañante, String enCondicionDe) 
            throws Exception {
        setPersona(persona);
        setGestionante(gestionante);
        setGestionado(gestionado);
        setTipoAcompañante(tipoAcompañante);
        setEnCondicionDe(enCondicionDe);
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

    public String getTipoAcompañante() {
        return tipoAcompañante;
    }
    
    
    public final void setTipoAcompañante(String tipoAcompañante) throws Exception {
        if ((tipoAcompañante == null) ||(tipoAcompañante.contains("Seleccione un"))) {
            throw new Exception("tipo acompañante null error");
        }
        
        if (tipoAcompañante.length() > TIPO_ACOMPANANTE_SIZE) {
            throw new Exception("tipo acompañante size error");
        }
        this.tipoAcompañante = tipoAcompañante;
    }

    public final String getEnCondicionDe() {
        return enCondicionDe;
    }

    public final void setEnCondicionDe(String enCondicionDe) throws Exception {
        if ((enCondicionDe == null) ||("".equals(enCondicionDe))) {
            throw new Exception("condicion null error");
        }
        
        if (enCondicionDe.length() > CONDICION_SIZE) {
            throw new Exception("condicion size error");
        }
        this.enCondicionDe = enCondicionDe;
    }

    @Override
    public String toString() {
        String part1 = "";
        String part2 = "";
        String part3 = "";
 
                
                
                part1= """

                        Acompañante
                        ===========
                        """+persona.toString();
                
                part2 = """
                        
                        Acompaña a
                        ==========
                        """;
                
        if(gestionante != null){
            part3 = gestionante.toString();
        
        }else if(gestionado != null){
            part3 = gestionado.toString();
        }
        
        return part1 + part2 + part3;

        //return "Acompa\u00f1ante{" + "persona=" + persona + ", gestionante=" + gestionante + ", gestionado=" + gestionado + ", tipoAcompa\u00f1ante=" + tipoAcompañante + ", enCondicionDe=" + enCondicionDe + '}';
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