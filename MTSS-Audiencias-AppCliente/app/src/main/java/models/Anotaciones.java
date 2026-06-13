package models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;
import methods.Telemetria;

public class Anotaciones {
        
    Telemetria telemetria = new Telemetria();
    
    //SI LAS LISTAS SE HACEN PRIVADAS
    //JACKSON FALLARA AL CREAR EL JSON
    public List<Anotacion> listAnotaciones;
    public List<AnotacionEspecial> listAnotacionesEspeciales;
    public List<Segmento> listSegmentos;
    public List<Marcador> listMarcadores;
    
    public Anotaciones(){
        listAnotaciones = new LinkedList<>();
        listAnotacionesEspeciales = new LinkedList<>();
        listSegmentos = new LinkedList<>();
        listMarcadores = new LinkedList<>();
    }
    
    
    
    
    public void createAnotacion(String tiempo, String contenido){
        Anotacion nuevaAnotacion = new Anotacion();
        nuevaAnotacion.setTiempo(tiempo);
        nuevaAnotacion.setContenido(contenido);
        listAnotaciones.add(nuevaAnotacion);
    }
    public void createAnotacionEspecial(String tiempo, String tipo, String contenido){
        AnotacionEspecial nuevaAnotacionEspecial = new AnotacionEspecial();
        nuevaAnotacionEspecial.setTiempo(tiempo);
        nuevaAnotacionEspecial.setTipo(tipo);
        nuevaAnotacionEspecial.setContenido(contenido);
        listAnotacionesEspeciales.add(nuevaAnotacionEspecial);
    }
    public void createInicioSegmento(String tiempoInicio, String contenido){
        Segmento nuevoSegmento = new Segmento();
        nuevoSegmento.setTiempoInicio(tiempoInicio);
        nuevoSegmento.setTitulo(contenido);
        listSegmentos.add(nuevoSegmento);
    }
    public void createFinSegmento(String tiempoFin){
        getLastSegmento().setTiempoFin(tiempoFin);
    }
    public void createMarcador(String tiempo, String titulo){
        Marcador marcador = new Marcador();
        marcador.setTiempo(tiempo);
        marcador.setTitulo(titulo);
        listMarcadores.add(marcador);
    }
    
    
    
    public void removeAnotacion(int index){
        if(!listAnotaciones.isEmpty())
        listAnotaciones.remove(index);
    }
    public void removeAnotacionEspecial(int index){
        if(!listAnotacionesEspeciales.isEmpty())
        listAnotacionesEspeciales.remove(index);
    }
    public void removeSegmento(int index){
        if(!listSegmentos.isEmpty())
        listSegmentos.remove(index);
    }
    public void removeMarcador(int index){
        if(!listMarcadores.isEmpty())
        listMarcadores.remove(index);
    }
    
    

    public void updateAnotacion(int index, String nuevoContenido){
        listAnotaciones.get(index).setContenido(nuevoContenido);
    }
    public void updateAnotacionEspecial(int index, String nuevoContenido){
        listAnotacionesEspeciales.get(index).setContenido(nuevoContenido);
    }
    public void updateSegmento(int index, String nuevoContenido){
        listSegmentos.get(index).setTitulo(nuevoContenido);
    }
    public void updateMarcador(int index, String nuevoTitulo){
        listMarcadores.get(index).setTitulo(nuevoTitulo);
    }
    
    
    
    private Anotacion getFirstAnotacion() {
        return listAnotaciones.iterator().next();
    }
    private Anotacion getLastAnotacion() {
        Anotacion lastElement = null;

        for (Anotacion element : listAnotaciones) {
            lastElement = element;
        }

        return lastElement;
    }
    
    
    
    private AnotacionEspecial getFirstAnotacionEspecial() {
        return listAnotacionesEspeciales.iterator().next();
    }
    private AnotacionEspecial getLastAnotacionEspecial() {
        AnotacionEspecial lastElement = null;

        for (AnotacionEspecial element : listAnotacionesEspeciales) {
            lastElement = element;
        }

        return lastElement;
    }
    
    
    
    private Segmento getFirstSegmento() {
        return listSegmentos.iterator().next();
    }
    private Segmento getLastSegmento() {
        Segmento lastElement = null;

        for (Segmento element : listSegmentos) {
            lastElement = element;
        }

        return lastElement;
    }


    

    private Marcador getFirstMarcador() {
        return listMarcadores.iterator().next();
    }
    private Marcador getLastMarcador() {
        Marcador lastElement = null;

        for (Marcador element : listMarcadores) {
            lastElement = element;
        }

        return lastElement;
    }
    
    
    
    
    public int getIndexAnotacionWhere(String condicion) {
        int index = 0;
        for (Anotacion element : listAnotaciones) {
            if(element.getContenido().equals(condicion))
                return index;
            else{
                index++;
            }
        }

        return -1;
    }

    public int getIndexAnotacionEspecialWhere(String condicion) {
        int index = 0;

        for (AnotacionEspecial element : listAnotacionesEspeciales) {
            if(element.getContenido().equals(condicion))
                return index;
            else{
                index++;
            }
        }

        return -1;
    }

    public int getIndexSegmentoWhere(String condicion) {
        int index = 0;

        for (Segmento element : listSegmentos) {
            if(element.getTitulo().equals(condicion))
                return index;
            else{
                index++;
            }
        }

        return -1;
    }

    public int getIndexMarcadorWhere(String condicion) {
        int index = 0;

        for (Marcador element : listMarcadores) {
            if(element.getTitulo().equals(condicion))
                return index;
            else{
                index++;
            }
        }

        return -1;
    }
    
    
    public DefaultTableModel loadTable(DefaultTableModel modelAnotaciones){
        
        for (Anotacion anotacion: listAnotaciones) {
            Object[] theRow = new Object[3];
            theRow[0] = anotacion.getTiempo();
            theRow[1] = "Anotación";
            theRow[2] = anotacion.getContenido();
            modelAnotaciones.addRow(theRow);
        }
        
        for (AnotacionEspecial anotacionEspecial: listAnotacionesEspeciales) {
            Object[] theRow = new Object[3];
            theRow[0] = anotacionEspecial.getTiempo();
            theRow[1] = anotacionEspecial.getTipo();
            theRow[2] = anotacionEspecial.getContenido();
            modelAnotaciones.addRow(theRow);
        }
        
        for (Marcador marcador: listMarcadores) {
            Object[] theRow = new Object[3];
            theRow[0] = marcador.getTiempo();
            theRow[1] = "Marcador";
            theRow[2] = marcador.getTitulo();
            modelAnotaciones.addRow(theRow);
        }
        
        for (Segmento segmento: listSegmentos) {
            Object[] theRow = new Object[3];
            theRow[0] = segmento.getTiempoInicio() +" - "+ segmento.getTiempoFin();
            theRow[1] = "Segmento";
            theRow[2] = segmento.getTitulo();
            modelAnotaciones.addRow(theRow);
        }
        
        return modelAnotaciones;
    }
    
    
    public String objectToJSON(Anotaciones lasAnotaciones){
        
        ObjectMapper mapper;
        String jsonListaAnotaciones = "";
        
        try {
            mapper = new ObjectMapper();
            jsonListaAnotaciones = mapper.writeValueAsString(lasAnotaciones);
        } catch (JsonProcessingException ex) {
            telemetria.logActivity("Error al convertir anotaciones en JSON");
            telemetria.logException(ex);
        }
        
        return jsonListaAnotaciones;
    }
    
    public Anotaciones jsonToObject(String jsonString){
        ObjectMapper objectMapper;
        Anotaciones anotaciones = new Anotaciones();
        
        try {
            objectMapper = new ObjectMapper();
            anotaciones = objectMapper.readValue(jsonString, Anotaciones.class);
        } catch (JsonProcessingException ex) {
            telemetria.logActivity("Error al convertir el JSON en objeto");
            telemetria.logException(ex);
        }
        
        return anotaciones;
    }
}

class Anotacion {
    private String tiempo;
    private String contenido;

    public Anotacion() {
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}

class AnotacionEspecial{
    private String tiempo;
    private String tipo;
    private String contenido;

    public AnotacionEspecial() {
    }

    public AnotacionEspecial(String tiempo, String tipo, String contenido) {
        this.tiempo = tiempo;
        this.tipo = tipo;
        this.contenido = contenido;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }    
}

class Segmento {
    
    private String tiempoInicio;
    private String tiempoFin;
    private String titulo;

    public Segmento() {
    }

    public Segmento(String tiempoInicio, String tiempoFin, String titulo) {
        this.tiempoInicio = tiempoInicio;
        this.tiempoFin = tiempoFin;
        this.titulo = titulo;
    }

    public String getTiempoInicio() {
        return tiempoInicio;
    }

    public void setTiempoInicio(String tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }

    public String getTiempoFin() {
        if(this.tiempoFin != null){
            return tiempoFin;
        }
        return "[FIN]";
    }

    public void setTiempoFin(String tiempoFin) {
            this.tiempoFin = tiempoFin;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
}

class Marcador {
    private String tiempo;
    private String titulo;

    public Marcador() {
    }

    public Marcador(String tiempo, String titulo) {
        this.tiempo = tiempo;
        this.titulo = titulo;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}

/*
   public <Anotacion> Anotacion getAnotacionWhere(final Iterable<Anotacion> elements) {
        Anotacion lastElement = null;

        for (Anotacion element : elements) {
            lastElement = element;
        }

        return lastElement;
    }

*/


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