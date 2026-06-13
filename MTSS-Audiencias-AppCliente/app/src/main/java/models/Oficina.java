package models;

//Esta clase es un catalogo de puestos de la direccion nacional del inspeccion
//del MTSS. Su uso como modelo de comboBox garantiza la consistencia de los 
//datos.
public enum Oficina {

    //CENTRAL 0
    CARTAGO("Oficina Provincial de Cartago"),
    HEREDIA("Oficina Provincial de Heredia"),
    PURISCAL("Oficina Cantonal de Puriscal"),
    LOS_SANTOS("Oficina Cantonal de los Santos"),
    //HUETAR_NORTE 4
    CIUDAD_QUESADA("Oficina Cantonal de Ciudad Quesada"),
    GRECIA("Oficina Cantonal de Grecia"),
    NARANJO("Oficina Cantonal de Naranjo"),
    LA_FORTUNA("Oficina Cantonal de La Fortuna"),
    //HUETAR_CARIBE 8
    SIQUIRRES("Oficina Cantonal de Siquirres"),
    POCOCI("Oficina Cantonal de Pococí"),
    TALAMANCA("Oficina Cantonal de Talamanca"),
    TURRIALBA("Oficina Cantonal de Turrialba"),
    GUACIMO("Oficina Cantonal de Guácimo"),
    //PACIFICO_CENTRAL 13
    SAN_RAMON("Oficina Cantonal de San Ramón"),
    OROTINA("Oficina Cantonal de Orotina"),
    AGUIRRE_QUEPOS("Oficina Cantonal de Aguirre-Quepos"),
    //CHOROTEGA 16
    SANTA_CRUZ("Oficina Cantonal de Santa Cruz"),
    CAÑAS("Oficina Cantonal de Cañas"),
    NICOYA("Oficina Cantonal de Nicoya"),
    NANDAYURE("Oficina Cantonal Nandayure"),
    UPALA("Oficina Cantonal Upala"),
    //BRUNCA 21
    PALMAR_NORTE("Oficina Cantonal de Palmar Norte"),
    CORREDORES("Oficina Cantonal de Corredores"),
    GOLFITO("Oficina Cantonal de Golfito"),
    COTO_BRUS("Oficina Cantonal de Coto Brus");
    
    /*
        El siguiente codigo permite tomar los valores String y colocarlos
        en el ComboBox. No eliminar.
     */
    private final String display;

    private Oficina(String s) {
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