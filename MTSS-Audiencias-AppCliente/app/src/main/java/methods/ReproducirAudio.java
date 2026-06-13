package methods;

import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import models.Audio;

public class ReproducirAudio {
    
    Telemetria telemetria = new Telemetria();
    private Clip clip;
    private FloatControl volumeControl;
    private AudioFormat format;
    private AudioInputStream inputStream;
    private volatile boolean running,looping;
    private int currentFrame;
    private int frameLenght;
    public boolean endOfFile = false;
    public boolean ignoreUpdate = false;
    
    public ReproducirAudio(){
    
    }
    
    public ReproducirAudio(Audio elAudio){
        looping = running = false;
        try {
            clip = AudioSystem.getClip();
            inputStream = AudioSystem.getAudioInputStream(elAudio.getArchivoAudio());
            format = inputStream.getFormat();
            clip.open(inputStream);
            frameLenght = clip.getFrameLength();
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
            telemetria.logActivity("Error al reproducir el archivo..");
            telemetria.logException(ex);
        }
    }
    
    public void play() {
        endOfFile = false;
        running = true;
        clip.setFramePosition(0);
        new Thread(() -> {
            clip.start();
            while(true){
                currentFrame = clip.getFramePosition();
                if(clip.getMicrosecondPosition() == clip.getMicrosecondLength()){
                    endOfFile = true;
                    break;
                }
                if(!running){
                    break;
                }
            }
        }).start();
    }
    public void pause(){
        running = false;
        clip.stop();
    }
    public void resume(){
        if(endOfFile){
            play();
            return;
        }
        
        running = true;
        new Thread(() -> {
            clip.start();
            while(true){
                currentFrame = clip.getFramePosition();
                if(clip.getMicrosecondPosition() == clip.getMicrosecondLength()){
                    endOfFile = true;
                    break;
                }
                if(!running){
                    break;
                }
            }
        }).start();
    }
    public void stop(){
        running = false;
        looping = false;
        clip.setFramePosition(0);
    }
    public void loop(){
        looping = true;
        new Thread(() -> {
            clip.start();
            while(looping){
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }).start();
    }
    public boolean isResumed(){
        return clip.getMicrosecondPosition()>0;
    }
    
    public void cambiarVolumen(float valor){
        volumeControl.setValue(valor);
    }
    
    public int getProgress(){
        return (int) (((double) currentFrame / (double) frameLenght) * 100);
    }
    
    public void setProgress(int sliderPosition){
        double newFrame = ((double) frameLenght * ((double) sliderPosition / 100.0));
        clip.setFramePosition((int) newFrame);
    }
    
    public double getCurrentTime() {
        currentFrame = clip.getFramePosition();
        double time = (double) currentFrame / format.getFrameRate();
        return time;
   }
    
   public void setCurrentTime(double time){
       double nuevoTiempo = time * format.getFrameRate();
       clip.setFramePosition((int)nuevoTiempo);
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