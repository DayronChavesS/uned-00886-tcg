package models;

//Esta clase es un catalogo de las razones que ameritan un fuero de proteccion 
//especial. Su uso como modelo de comboBox garantiza la consistencia de los 
//datos.
public enum TipoCaso {

    DISCRIMINACION("Discriminación en razón de género, edad, entre otros"),
    DESPIDO_PATERNIDAD("Despido de Trabajador Licencia de Paternidad"),
    EMBARAZO_LACTANCIA("Trabajadora embarazada o estado de lactancia"),
    EMBARAZO_RESTRICCION_DERECHOS("Trabajadora embarazada. Restricción de derechos"),
    FRACCIONAMIENTO("Fraccionamiento de jornada laboral"),
    HOSTIGAMIENTO_LABORAL("Hostigamiento laboral"),
    HOSTIGAMIENTO_SEXUAL("Hostigamiento sexual"),
    DESPIDO_SINDICATO("Despido de aforado sindicato");

    /*
        El siguiente codigo permite tomar los valores String y colocarlos
        en el ComboBox. No eliminar.
     */
    private final String display;

    private TipoCaso(String s) {
        display = s;
    }

    @Override
    public String toString() {
        return display;
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