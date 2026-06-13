package models;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Comparecencia {

    /*
        DECLARACION DE VARIABLES
     */
    private int idComparecencia;
    private String codigoSILAC;
    private TipoCaso tipoCaso;
    private String ubicacion;
    private java.sql.Date fecha;
    private String linkExpediente;
    
    /*
        DECLARACION DE TAMAÑOS
     */
    private static final int CODIGOSILAC_SIZE = 15;
    private static final int UBICACION_SIZE = 200;

    public Comparecencia() {
    }

    public Comparecencia(int idComparecencia, String codigoSILAC, TipoCaso tipoCaso, String ubicacion, Date fecha) {
        this.idComparecencia = idComparecencia;
        this.codigoSILAC = codigoSILAC;
        this.tipoCaso = tipoCaso;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
    }
    
     /*
        GETTERS & SETTERS
     */

    public final int getIdComparecencia() {
        return idComparecencia;
    }

    public final void setIdComparecencia(int idComparecencia) {
        this.idComparecencia = idComparecencia;
    }

    public final String getCodigoSILAC() {
        return codigoSILAC;
    }

    public final void setCodigoSILAC(String codigoSILAC) throws Exception {
        if ((codigoSILAC == null)||("  -  -     -  ".equals(codigoSILAC))) {
            throw new Exception("SILAC null error");
        }

        if (codigoSILAC.length() > CODIGOSILAC_SIZE) {
            throw new Exception("SILAC size error");
        }
        this.codigoSILAC = codigoSILAC;
    }

    public final TipoCaso getTipoCaso() {
        return tipoCaso;
    }

    public final void setTipoCaso(int tipoCaso) throws Exception {
        if (tipoCaso < 0 || tipoCaso > 7) {
            throw new Exception("tipoCaso error");
        }
        this.tipoCaso = TipoCaso.values()[tipoCaso];
    }
    
    public final void setTipoCaso(TipoCaso tipoCaso) throws Exception {
        if (tipoCaso == null) {
            throw new Exception("tipoCaso null error");
        }
        this.tipoCaso = tipoCaso;
    }
    
    public final void setTipoCaso(String tipoCaso) throws Exception {
        if(tipoCaso.contains("Seleccione"))
                throw new Exception("tipoCaso null error");
        for (TipoCaso elTipoCaso : TipoCaso.values()){
            if(elTipoCaso.toString().equals(tipoCaso)){
                setTipoCaso(elTipoCaso);
                return;
            }
        }
        
    }

    public final String getUbicacion() {
        return ubicacion;
    }

    public final void setUbicacion(String ubicacion) throws Exception {
        if ((ubicacion == null)||("".equals(ubicacion))) {
            throw new Exception("ubicacion null error");
        }

        if (ubicacion.length() > UBICACION_SIZE) {
            throw new Exception("ubicacion size error");
        }
        this.ubicacion = ubicacion;
    }

    public final String getFecha() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(this.fecha);
    }

    public final void setFecha(java.util.Date fecha, Boolean verificar) throws Exception {
        java.sql.Date mySQLDate = new java.sql.Date(fecha.getTime());        
        if(verificar){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date tiempoActual = new java.util.Date();

            if (mySQLDate == null) {
                throw new Exception("fecha null error");
            }
            if(formatter.format(mySQLDate).compareTo(formatter.format(tiempoActual)) == -1){
                throw new Exception("fecha pasado error");
            }
            if((mySQLDate.compareTo(new Date(System.currentTimeMillis())) == 1)){
                throw new Exception("fecha futuro error");
            }
        }
        this.fecha = mySQLDate;
    }
    
    public String getLinkExpediente() {
        return linkExpediente;
    }

    public void setLinkExpediente(String linkExpediente) throws Exception  {
        
        /*
        if(!linkExpediente.contains("https")){
            throw new Exception("link error");
        }
        
        if(!linkExpediente.contains("www")){
            throw new Exception("link error");
        }
        
        if(!linkExpediente.contains("mtss.go.cr")){
            throw new Exception("link error");
        }
        */
        
        this.linkExpediente = linkExpediente;
    }

    @Override
    public String toString() {
        return """
               
               Comparecencia
               =============
               SILAC: 
               """+codigoSILAC+
               """
               \nTipo de Caso: 
               """+tipoCaso+
               """
               \nUbicacion: 
               """+ubicacion+
               """
               \nLink del Expediente: 
               """+linkExpediente+
               """
               \nFecha: 
               """+fecha;
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