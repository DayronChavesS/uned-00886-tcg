package models;

import java.io.File;
import java.sql.Time;
import java.text.SimpleDateFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class Audio {

    /*
        DECLARACION DE VARIABLES
     */
    private int idAudio;
    private String nombreAudio;
    private String duracionAudio;
    private String pathArchivoAudio;
    private String pathArchivoAnotaciones;
    private File archivoAudio = new File("");
    private File archivoAnotaciones = new File("");
    
    /*
        DECLARACION DE TAMAÑOS
     */
    private static final int NOMBRE_SIZE = 45;
    private static final int PATH_AUDIO_SIZE = 999;
    private static final int PATH_ANOTATION_SIZE = 999;

    /*
        CONSTRUCTOR
        1. El constructor aplica las restricciones de tamaño
           Corta si excede el limite permitido
        2. Observe que el constructor no necesita los paths de los audios, los
           obtiene automaticamente de los objetos File.
     */
    
    public Audio() {}

    public Audio(int idAudio, String nombreAudio, String duracionAudio, 
            String pathArchivoAudio, String pathArchivoAnotaciones,
            File archivoAudio, File archivoAnotaciones) throws Exception {
        
        setIdAudio(idAudio);
        setNombreAudio(nombreAudio);
        setDuracionAudio(duracionAudio);
        setArchivoAudio(archivoAudio);
        setArchivoAnotaciones(archivoAnotaciones);
        setPathArchivoAudio(pathArchivoAudio);
        setPathArchivoAnotaciones(pathArchivoAnotaciones);
    }


    /*
    GETTERS & SETTERS
     */

    public final int getIdAudio() {
        return idAudio;
    }

    public final void setIdAudio(int idAudio) {
        this.idAudio = idAudio;
    }

    public final String getNombreAudio() {
        return nombreAudio;
    }

    public final void setNombreAudio(String nombreAudio) throws Exception {
        if ((nombreAudio == null) ||("".equals(nombreAudio))) {
            throw new Exception("nombreAudio null error");
        }
        
        if (nombreAudio.length() > NOMBRE_SIZE ) {
            throw new Exception("nombreAudio size error");
        }
        
        //concatenar un valor unico para evitar duplicados
        this.nombreAudio = nombreAudio  ;
    }

    public final String getDuracionAudio() {
        return this.duracionAudio;
    }

    public final void setDuracionAudio(String duracionAudio) {
        this.duracionAudio = duracionAudio;
    }

    public final String getPathArchivoAudio() {
        return pathArchivoAudio;
    }

    public final void setPathArchivoAudio(String pathArchivoAudio) throws Exception {

        if ((pathArchivoAudio == null) ||("".equals(pathArchivoAudio))) {
            throw new Exception("audio path null error");
        }
        
        if (pathArchivoAudio.length() > PATH_AUDIO_SIZE) {
            throw new Exception("audio path size error");
        }
        
        this.pathArchivoAudio = pathArchivoAudio;
    }

    public final String getPathArchivoAnotaciones() {
        return pathArchivoAnotaciones;
    }

    public final void setPathArchivoAnotaciones(String pathArchivoAnotaciones) throws Exception {
        
        if ((pathArchivoAnotaciones == null) ||("".equals(pathArchivoAnotaciones))) {
            throw new Exception("anotaciones path null error");
        }
        
        if (pathArchivoAnotaciones.length() > PATH_ANOTATION_SIZE) {
            throw new Exception("anotaciones path size error");
        }
        
        this.pathArchivoAnotaciones = pathArchivoAnotaciones;
    }

    public final File getArchivoAudio() {
        return archivoAudio;
    }

    public final void setArchivoAudio(File archivoAudio) {
        this.archivoAudio = archivoAudio;
    }

    public final File getArchivoAnotaciones() {
        return archivoAnotaciones;
    }

    public final void setArchivoAnotaciones(File archivoAnotaciones) {
        this.archivoAnotaciones = archivoAnotaciones;
    }

    public final void createArchivoAudio(){
        File audio = new File(pathArchivoAudio);
        setArchivoAudio(audio);
    }
    
    public final void createArchivoAnotaciones(){
        File anotaciones = new File(pathArchivoAnotaciones);
        setArchivoAnotaciones(anotaciones);
    }
    
    public final double calcularDuracionArchivo(File archivoAudio) throws Exception {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(archivoAudio);
        AudioFormat format = audioInputStream.getFormat();
        long frames = audioInputStream.getFrameLength();
        double durationInSeconds = (frames+0.0) / format.getFrameRate();  
        return durationInSeconds;
    }
    
    public final String convertDoubleToTime(Double duracion){
      
      int value = duracion.intValue();
      String hours = String.format("%02d",(value/3600));
      String minutes = String.format("%02d",(value/60%60));
      String seconds =  String.format("%02d",(value%60));
 
      return hours+":"+minutes+":"+seconds;
    }
    
    public final Double convertTimeToDouble(String tiempo){
        //"HH:mm:ss"
        int seconds = Integer.parseInt(tiempo.replaceAll(".*(?=.{2})", ""));
        int minutes = Integer.parseInt(tiempo.replaceAll("((?<!.{3}).)|((?!.{4}).)", ""));
        int hours = Integer.parseInt(tiempo.replaceAll("(?<=.{2}).*", ""));
        
        return Double.valueOf((hours*60*60)+(minutes*60)+seconds);
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