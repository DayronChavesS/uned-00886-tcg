package models;

//Extiende de usuario, al crear inspector se crea usuario y persona.
public class Inspector{
    
     /*
        DECLARACION DE VARIABLES
     */
    
    private Persona persona;
    private Usuario usuario;
    private Puesto puesto;
    private Region region;
    private Oficina oficina;
    
    /*
        CONSTRUCTOR
        1. El constructor valida que la oficina pertenezca a la region seleccionada
    */
    public Inspector(){
    
    }
    
    public Inspector(Puesto puesto, Region region, Oficina oficina) throws Exception {
        setPuesto(puesto);
        setOficina(oficina);
        setRegion(region, oficina);
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public final Puesto getPuesto() {
        return puesto;
    }

    public final void setPuesto(int puesto) throws Exception {
        if (puesto < 0 || puesto > 4) {
            throw new Exception("puesto null error");
        }
        this.puesto = Puesto.values()[puesto];
    }
    
    public final void setPuesto(Puesto puesto) throws Exception {
        if (puesto == null) {
            throw new Exception("puesto null error");
        }
        this.puesto = puesto;
    }
    
    public final void setPuesto(String puesto) throws Exception {
        if(puesto.contains("Seleccione un"))
            throw new Exception("puesto null error");
        //para cada uno de los puestos dentro del Enum Puestos
        for (Puesto elPuesto : Puesto.values()){
            //si el elemento seleccionado en el combobox es el mismo del iterador
            if(puesto.equals(elPuesto.toString())){
                //se setea dicho puesto en el inspector
                setPuesto(elPuesto);
                return;
            }
        }
        throw new Exception("puesto null error");
    }

    public final Region getRegion() {        
        return region;
    }

    public final void setRegion(int region) throws Exception {
        if (region < 0 || region > 5) {
            throw new Exception("region null error");
        }
  
        this.region = Region.values()[region];
    }
    
    public final void setRegion(Region region, Oficina oficina) throws Exception {
        if (region == null) {
            throw new Exception("region null error");
        }
        
        if (!validarRegion(region, oficina)) {
            throw new Exception("region error");
        }
        
        this.region = region;
    }

    public final void setRegion(String region, Oficina oficina) throws Exception {
        if(region.equals("Seleccione un valor..."))
            throw new Exception("region null error");
        for (Region laRegion : Region.values()){
            if(region.equals(laRegion.toString())){
                setRegion(laRegion, oficina);
                return;
            }
        }
        throw new Exception("region null error");
    }
    
    public final Oficina getOficina() {
        return oficina;
    }
    
    public final void setOficina(int oficina) throws Exception {
        if (oficina < 0 || oficina > 24) {
            throw new Exception("oficina null error");
        }
        
        this.oficina = Oficina.values()[oficina];
    }

    public final void setOficina(Oficina oficina) throws Exception {
        if (oficina == null) {
            throw new Exception("oficina null error");
        }
        
        this.oficina = oficina;
    }
    
    public final void setOficina(String oficina) throws Exception {
        if(oficina.equals("Seleccione un valor..."))
            throw new Exception("oficina null error");
        for (Oficina laOficina : Oficina.values()){
            if(oficina.equals(laOficina.toString())){
                setOficina(laOficina);
                return;
            }
        }
        throw new Exception("oficina null error");
    }
    
    //se valida que la oficina pertenezca a su respectiva region
    public final Boolean validarRegion(Region region, Oficina oficina){
        
        if ((region == Region.CENTRAL) && (oficina == Oficina.CARTAGO
                                        || oficina == Oficina.HEREDIA
                                        || oficina == Oficina.PURISCAL
                                        || oficina == Oficina.LOS_SANTOS
        )) {
            return true;
        } else if ((region == Region.HUETAR_NORTE) && (oficina == Oficina.CIUDAD_QUESADA
                                                    || oficina == Oficina.GRECIA
                                                    || oficina == Oficina.NARANJO
                                                    || oficina == Oficina.LA_FORTUNA
        )) {
            return true;
        } else if ((region == Region.HUETAR_CARIBE) && (oficina == Oficina.SIQUIRRES
                                                    || oficina == Oficina.POCOCI
                                                    || oficina == Oficina.TALAMANCA
                                                    || oficina == Oficina.TURRIALBA
                                                    || oficina == Oficina.GUACIMO
        )) {
            return true;
        } else if ((region == Region.PACIFICO_CENTRAL) && (oficina == Oficina.SAN_RAMON
                                                        || oficina == Oficina.OROTINA
                                                        || oficina == Oficina.AGUIRRE_QUEPOS
        )) {
            return true;
        } else if ((region == Region.CHOROTEGA) && (oficina == Oficina.SANTA_CRUZ
                                                || oficina == Oficina.CAÑAS
                                                || oficina == Oficina.NICOYA
                                                || oficina == Oficina.NANDAYURE
                                                || oficina == Oficina.UPALA
        )) {
            return true;
        } else if ((region == Region.BRUNCA) && (oficina == Oficina.PALMAR_NORTE
                                            || oficina == Oficina.CORREDORES
                                            || oficina == Oficina.GOLFITO
                                            || oficina == Oficina.COTO_BRUS
        )) {
            return true;
        } else {
            return false;
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