package server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConexionServidor {

    private String DIRECCION_SERVIDOR;
    private int PUERTO_SERVIDOR;
    private String laSolicitud;             //el mensaje enviado por el cliente
    private String laRespuesta;             //la respuesta recibida desde el servidor
    private Socket serverSocket;            //socket para conectarse al servidor
    
    public ConexionServidor(){
        initVariables();
    }
    
    private void initVariables(){
        DIRECCION_SERVIDOR = "0.0.0.0";
        PUERTO_SERVIDOR = 9999;
        laSolicitud = "";         
        laRespuesta= ""; 
    }
    
    //se crea un nuevo socket con direccion y puerto definidos
    public Boolean conectarServidor() {
        try{
            serverSocket = new Socket(DIRECCION_SERVIDOR, PUERTO_SERVIDOR);
            return true;
        }catch(Exception ex){
            return false;
        } 
    }

    //se cierra los canales y sockets creados
    public void desconectarServidor() {
        try{
            if (serverSocket != null){
                serverSocket.close();
            }
        }catch(Exception ex){
            serverSocket = null;
        }
    }
    
    //se abre un canal de salida y se envian los datos
    public void enviarMensaje(String laSolicitud) {
        try{
            DataOutputStream clientOutput= new DataOutputStream(serverSocket.getOutputStream());
            clientOutput.writeBytes(laSolicitud + "\n");
            clientOutput.flush();

            /*
                ATENCION:
                FALLAR EN INCLUIR "\n" EN EL MENSAJE CONGELARA EL PROGRAMA.
            */

        }catch(Exception ex){
        }
    }
    
    //se abre un canal de entrada y se espera hasta recibir respuesta
    public String recibirMensaje(){
        String laRespuesta = "";
        
        try{
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(serverSocket.getInputStream())); 
            while (true) {
                //se deja de esperar respuesta si la conexion se cae
                if (!serverSocket.isConnected() || serverSocket.isClosed()) {
                    break;
                }

                //si se han obtenido datos, se lee y se rompe el ciclo
                if(serverInput.ready()){
                    laRespuesta = serverInput.readLine();
                    break;
                }

            }
        }catch(Exception ex){
        }
        return laRespuesta;
    }
        
    public void enviarObjeto(Object elObjeto){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(serverSocket.getOutputStream());
            oos.writeObject(elObjeto);
        }catch(Exception ex){}
    }
    
        
    public Object recibirObjeto() {
        Object obj = new Object();
        
        try{
            DataInputStream in = new DataInputStream(new BufferedInputStream(serverSocket.getInputStream()));
            ObjectInputStream ois = new ObjectInputStream(in);
            obj = ois.readObject();
        }catch(Exception ex){
            return null;
        }
        
        return obj;
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