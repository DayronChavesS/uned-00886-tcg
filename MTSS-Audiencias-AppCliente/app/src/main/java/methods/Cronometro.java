package methods;

import javax.swing.JLabel;

public class Cronometro{
    private final Telemetria telemetria = new Telemetria();
    private int hours = 0;
    private int minutes = 0;
    private int seconds = 0;
    private Boolean running; 
    
    public void setHours(int hour) {
        hours = hour;
    }

    public void setMinutes(int min) {
        minutes = min;
    }

    public void setSeconds(int sec) {
        seconds = sec;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public Boolean getRunning() {
        return running;
    }
    
    public void setRunning(Boolean value) {
        this.running = value;
    }
    
    public String getTime(){
        String hourString;
        String minuteString;
        String secondString;
        
        if(hours < 10){
            hourString = "0"+hours;
        }else{
            hourString = String.valueOf(hours);
        }
        if(minutes < 10){
            minuteString = "0"+minutes;
        }else{
            minuteString = String.valueOf(minutes);
        }      
        if(seconds < 10){
            secondString = "0"+seconds;
        }else{
            secondString = String.valueOf(seconds);
        }
        
        return hourString+":"+minuteString+":"+secondString;  
    }
    
    public void setTime(String tiempo){
        seconds = Integer.parseInt(tiempo.replaceAll(".*(?=.{2})", ""));
        minutes = Integer.parseInt(tiempo.replaceAll("((?<!.{3}).)|((?!.{4}).)", ""));
        hours = Integer.parseInt(tiempo.replaceAll("(?<=.{2}).*", ""));
    }
    
    public void stop(JLabel tiempo){
        hours = 0;
        minutes = 0;
        seconds = 0;
        running = false;
        tiempo.setText(getTime());
    }

    public void start(JLabel tiempo){
        setRunning(true);
        new Thread(() -> {
                while(getRunning()) {
                    try {
                        seconds++;
                        if (getSeconds() == 60) {
                            minutes++;
                            setSeconds(0);
                        }
                        
                        if (getMinutes() == 60) {
                            hours++;
                            setMinutes(0);
                        }
                        
                        if (getHours() > 99 && getMinutes() == 60 && getSeconds() == 60) {
                            setHours(0);
                            setMinutes(0);
                            setSeconds(0);
                            break;
                        }
                        tiempo.setText(getTime());
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        telemetria.logActivity("Error en el cronometro.");
                        telemetria.logException(ex);                    
                    }
                }
        }).start();
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