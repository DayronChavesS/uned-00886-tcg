package methods;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public class GrabarAudio {
    
    Telemetria telemetria = new Telemetria();
    File wavFile;

    private static final int BUFFER_SIZE = 4096;
    private ByteArrayOutputStream recordBytes;
    private TargetDataLine audioLine = null;
    private AudioFormat format;
    private DataLine.Info info;
    private boolean isRunning = true;
    private boolean isPaused = false;
    private boolean canRecord = true;
    
    public GrabarAudio(File file){
        wavFile = file;
        runChecks();
        programarCopiaSeguridad();
    }
    
    private void runChecks(){
        format = getAudioFormat();
        info = new DataLine.Info(TargetDataLine.class, format);
        
        if (!AudioSystem.isLineSupported(info)) {
            telemetria.logActivity("Este sistema no puede realizar grabaciones.");
            canRecord = false;
        }
        if (!AudioSystem.isFileTypeSupported(AudioFileFormat.Type.WAVE)) {
            telemetria.logActivity("Este sistema no soporta WAVE.");
            canRecord = false;
        }
        
        format = null;
        info = null;
    }
    
    public Boolean getCanRecord(){
        return canRecord;
    }
    
    AudioFormat getAudioFormat() {
        /*
            Configuracion para archivos de poco tamaño.
        */
        float sampleRate = 10000;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

    public void start(){
        new Thread(() -> {
            try{
                format = getAudioFormat();

                info = new DataLine.Info(TargetDataLine.class, format);

                audioLine = AudioSystem.getTargetDataLine(format);

                audioLine.open(format);

                audioLine.start();

                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = 0;

                recordBytes = new ByteArrayOutputStream();

                isRunning = true;

                while (isRunning) {
                    bytesRead = audioLine.read(buffer, 0, buffer.length);
                    if(!isPaused){
                        recordBytes.write(buffer, 0, bytesRead);
                    }
                    
                }
            }catch(Exception e){
                telemetria.logException(e);
            }
        }).start();
    }

    public void stop(){
        try{
            isPaused = false;
            isRunning = false;
            audioLine.close();
        }catch(Exception e){
            telemetria.logException(e);
        }
    }
    
    public void pause(){
        isPaused = true;
    }
    
    public void resume(){
        isPaused = false;
    }

    public void save(){
            try{
                isRunning = false;
                byte[] audioData = recordBytes.toByteArray();
                ByteArrayInputStream byteInput = new ByteArrayInputStream(audioData);
                AudioInputStream audioInputStream = new AudioInputStream(byteInput, format, audioData.length / format.getFrameSize());
                AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, wavFile);
                audioInputStream.close();
                recordBytes.close();
            }catch(Exception e){
                telemetria.logException(e);
            }
    }
    
    private void programarCopiaSeguridad(){
        methods.Global.bckExecutor.scheduleWithFixedDelay(createBackup, 1, 1, TimeUnit.MINUTES);
    }
    
    final Runnable createBackup = new Runnable() {
        public void run() {
            try {
                pause();
                byte[] audioData = recordBytes.toByteArray();
                ByteArrayInputStream byteInput = new ByteArrayInputStream(audioData);
                AudioInputStream audioInputStream = new AudioInputStream(byteInput, format, audioData.length / format.getFrameSize());
                AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, wavFile);
                resume();
            } catch (Exception e) {
                telemetria.logException(e);
            }
        }
    };
    
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