package server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import database.MetodosDatabase;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import models.Inspector;
import org.apache.commons.io.IOUtils;

//clase para permitir el procesamiento de multiples clientes
//un hilo por cliente
public class MetodosServidor extends Thread{

    private MetodosDatabase database = new MetodosDatabase();
    private Socket clientSocket = null;
    private BufferedReader clientInput;
    private DataOutputStream serverOutput;
    private List<String> clientRequest = new LinkedList<>();
    private Map<String, Map<String,String>> uploadData = new HashMap<>();
    
    public MetodosServidor(Socket socket) {
        super();
        this.clientSocket = socket;
    }

    public void run(){
        try {
                        
            System.out.println("Se ha establecido una nueva conexion.");
            
            Object objRequest = recibirObjeto();
            if(objRequest instanceof List){
                clientRequest = (List)objRequest;
            }else{
                objRequest = null;
                cerrarConexion();
            }

            if(clientRequest != null){
                switch(clientRequest.get(0))
                {
                    default: cerrarConexion();
                    case "00": estadoServidor();                break;
                    case "03": enviarListaComparecencias();     break;
                    case "04": guardarComparecencia();          break;
                    case "05": guardarAudio();                  break;
                    case "06": guardarAnotacion();              break;
                    case "07": enviarComparecencia();           break;
                    case "08": enviarAudio();                   break;
                    case "09": enviarAnotacion();               break;
                    case "10": enviarIdComparecencia();         break;
                    case "87": numeroUsuariosEnLinea();         break;
                    case "88": usuarioEsAdmin();                break;
                    case "89": enviarListaUsuariosPendientes(); break;
                    case "90": autorizarUsuario();              break;
                    case "91": denegarUsuario();                break;
                    case "92": createObjPersona();              break;
                    case "93": createObjUser();                 break;
                    case "94": createObjAdmin();                break;
                    case "95": getIdAdmin();                    break;
                    case "96": adminExist();                    break;
                    case "97": signUpAdministrador();           break;
                    case "98": loginAdministrador();            break;
                    case "99": logoutAdministrador();           break;
                    case "100": detenerServidor();              break;
                }
            }

            System.out.println("Se cierra la conexion con el cliente.");
            cerrarConexion();
            
        } catch (Exception ex) {
            System.out.println("Error al leer input de cliente.");
            System.out.println(ex.getMessage());
        }
    }
    
    private void cerrarConexion(){
        try{
            if(serverOutput != null){
                serverOutput.close();
            }
            if(clientInput != null){
                clientInput.close();
            }
        }catch(Exception ex){
            System.out.println("Fallo al cerrar la conexion.");
            System.out.println(ex.getMessage());
        }
    }
    
    private void estadoServidor() {
        try {
            enviarMensaje("true");           
        } catch (Exception ex) {
            System.out.println("Fallo al enviar estado del servidor.");
            System.out.println(ex.getMessage()); 
        }
    }
    
    /**************************************************************************/
    //METODOS APP CLIENTE
    
    private void enviarListaComparecencias() {
        
        List<Object[]> listaDatos = new LinkedList<>();
        
        /*
        El envio de comparecencias es realizado con base en la
        informacion del usuario solicitante para proteger la
        privacidad.
        */
                                            //idPuesto
        switch(Integer.parseInt(clientRequest.get(1))){
        
            case 0 ->{
                //envia todo
                listaDatos = database.getAllComparecencias(listaDatos);
                break;
            }
            
            case 1 ->{
                //envia todo en su region                                                   
                listaDatos = database.getComparecenciasByRegion(    //idRegion
                        listaDatos, Integer.parseInt(clientRequest.get(2)
                ));
                break;
            }
            
            case 2 -> {
                //envia todo en su oficina                                                  
                listaDatos = database.getComparecenciasByOficina(   //idOficina
                        listaDatos, Integer.parseInt(clientRequest.get(3)
                ));
                break;
            }
            
            case 3 -> {
                //envia solo los de su autoria                                              
                listaDatos = database.getComparecenciasByInspector( //idPersona
                        listaDatos, Integer.parseInt(clientRequest.get(4)
                ));
                break;
            }
            
            case 4 -> {
                //envia solo a los que tiene acceso                                         
                listaDatos = database.getComparecenciasByAsesor(    //idPersona
                        listaDatos, Integer.parseInt(clientRequest.get(4)
                ));
                break;
            }
            
            default -> { 
                //no hacer nada
                break; 
            } 
        }
        
        enviarObjeto(listaDatos);
    }

    private void guardarComparecencia() {

        Map<String, Map<String,String>> comparecenciaMap = new HashMap<>();
        enviarMensaje("ready");
        Object comparecenciaObj = recibirObjeto();
        if(comparecenciaObj instanceof Map){
            comparecenciaMap = (Map<String, Map<String,String>>) comparecenciaObj;
            database.descargarLaComparecencia(comparecenciaMap);
            comparecenciaObj = null;
        }else{
            comparecenciaObj = null;
        }
        
        //se envia al cliente el id de la nueva comparencencia para
        //procesos posteriores
        int newIDComparecencia = database.getIdLastComparecencia();
        enviarMensaje(String.valueOf(newIDComparecencia));
    }

    private void guardarAudio() {

        String filePath = database.getAudioPathByPath(
                                    Integer.parseInt(clientRequest.get(4)),
                                    clientRequest.get(5));

        if(!filePath.equals("")){
            enviarMensaje("ready");
            recibirArchivo(filePath);
        }else{
            enviarMensaje("-1");
        }
    }
    

    private void guardarAnotacion() {

        String filePath = database.getAnotacionesPathByPath(
                                    Integer.parseInt(clientRequest.get(4)),
                                    clientRequest.get(5));

        if(!filePath.equals("")){
            enviarMensaje("ready");
            recibirArchivo(filePath);
        }else{
            enviarMensaje("-1");
        }
    }
        

    private void enviarComparecencia() {
        try{
            
        uploadData = database.extractComparecencia(
                              Integer.parseInt(clientRequest.get(4)
                              ));
        
        enviarObjeto(uploadData);
        }catch(Exception e){
            System.out.println("Fallo al enviar la comparecencia.");
            System.out.println(e.getMessage()); 
        }
   }

    
    private void enviarAudio() {
        String filePath = database.getAudioPathByPath(
                                    Integer.parseInt(clientRequest.get(4)),
                                    clientRequest.get(5));
        
        if(!filePath.equals("")){
            enviarMensaje("confirm");
            if("ready".equals(recibirMensaje())){
                enviarArchivo(filePath);
            }
        }else{
            enviarMensaje("-1");
        }    
    }
    
    private void enviarAnotacion() {
        String filePath = database.getAnotacionesPathByPath(
                                    Integer.parseInt(clientRequest.get(4)),
                                    clientRequest.get(5));


        if(!filePath.equals("")){
            enviarMensaje("confirm");
            if("ready".equals(recibirMensaje())){
                enviarArchivo(filePath);
            }
        }else{
            enviarMensaje("-1");
        }    
    }
    
    
    private void enviarIdComparecencia() {
        int idComparecencia = database.getIdComparecenciaBySILAC(clientRequest.get(4));
        enviarMensaje(String.valueOf(idComparecencia));
    }
    
    /**************************************************************************/
    
        
    /**************************************************************************/
    //METODOS APP SERVIDOR
    
    public void enviarListaUsuariosPendientes(){
        List<Object[]> listaDatos = new LinkedList<>();
        listaDatos = database.listUsuariosPendientes(listaDatos);
        enviarObjeto(listaDatos);
    }
    
    public void autorizarUsuario(){
        database.autorizarUsuario(Integer.parseInt(clientRequest.get(1)));
    }
    
    public void denegarUsuario(){
        database.denegarUsuario(Integer.parseInt(clientRequest.get(1)));
    }
    
    public void createObjPersona(){
        //innecesario
    }
    
    public void createObjUser(){
        //innecesario
    }
    
    public void createObjAdmin(){
        
        Inspector inspector = database.createObjectInspector(
                                Integer.parseInt(clientRequest.get(1))
                              );
        
        enviarObjeto(inspector);
        
    }
    
    public void getIdAdmin(){
        
       int elID = database.getIdInspector(
                        clientRequest.get(1)
                  );
       
        enviarMensaje(String.valueOf(elID));
        
    }
    
    public void usuarioEsAdmin(){
        Boolean esAdmin = database.userIsAdmin(
                            Integer.parseInt(clientRequest.get(1))
                        );
        
        enviarMensaje(String.valueOf(esAdmin));
    }
    
    public void adminExist(){
        Boolean exist = database.userExist(
                            clientRequest.get(1),
                            clientRequest.get(2)
                        );
        
        enviarMensaje(String.valueOf(exist));
    }
    
    public void signUpAdministrador() {
        try {
            enviarMensaje("ready");

            Inspector elInspector;
            Object objInspector = recibirObjeto();

            if (objInspector instanceof Inspector) {
                elInspector = (Inspector) objInspector;
                objInspector = null;

                if (database.cedulaIsUnique(elInspector.getPersona().getCedula())) {
                    if (database.emailIsUnique(elInspector.getUsuario().getEmail())) {

                        elInspector = database.guardarInspector(elInspector);

                        //se envia de vuelta el inspector con los datos completos
                        enviarMensaje("confirm");
                        if (recibirMensaje().equals("ready")) {
                            enviarObjeto(elInspector);
                        }

                    } else {
                        enviarMensaje("-1");
                    }
                } else {
                    enviarMensaje("-2");
                }

            } else {
                objInspector = null;
                enviarMensaje("-3");
            }
        } catch (Exception e) {
            enviarMensaje("-4");
        }
    }
    
    public void loginAdministrador(){
        
        database.loginInspector(
            Integer.parseInt(clientRequest.get(1))
        );
       
        enviarMensaje("true");
    }
    
    public void logoutAdministrador(){
    
        database.logoutInspector(
            Integer.parseInt(clientRequest.get(1))
        );
       
        enviarMensaje("true");
        
    }
    
    public void numeroUsuariosEnLinea(){
        int enLinea = database.getUsuariosEnLinea();
        enviarMensaje(String.valueOf(enLinea));
    }
    
    public void detenerServidor(){
        try {
            while(Server.servidorIniciado == true){
                Server.serverSocket.close(); //se cierran las conexiones
                if(Server.serverSocket.isClosed()){
                    Server.servidorIniciado = false;
                    break;
                }
            }
            System.exit(0); //se cierra el programa
        } catch (IOException ex) {
            System.out.println("Error al apagar el servidor, reintentando...");
            try {
                Thread.sleep(50000);
                detenerServidor();
            } catch (InterruptedException IntEx) {
                detenerServidor();
            }
        }
    }
    
    /**************************************************************************/
    
    //se abre un canal de salida y se envian los datos
    public void enviarMensaje(String laSolicitud){
        try{
            serverOutput = new DataOutputStream(clientSocket.getOutputStream());
            serverOutput.writeBytes(laSolicitud + "\n");

            /*
                ATENCION:
                FALLAR EN INCLUIR "\n" EN EL MENSAJE CONGELARA EL PROGRAMA.
            */

            serverOutput.flush();
        }catch(Exception ex){
            System.out.println("Fallo al enviar una respuesta al cliente.");
            System.out.println(ex);
        }
    }
    
    
    //se abre un canal de entrada y se espera hasta recibir respuesta
    public String recibirMensaje(){
        
        String laRespuesta = "";
        try{
            clientInput= new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while (true) {
                //se deja de esperar respuesta si la conexion se cae
                if (!clientSocket.isConnected() || clientSocket.isClosed()) {
                    break;
                }

                //si se han obtenido datos, se lee y se rompe el ciclo
                if(clientInput.ready()){
                    laRespuesta = clientInput.readLine();
                    break;
                }
            }
        }catch(Exception ex){
            System.out.println("Fallo al obtener respuesta del cliente.");
            System.out.println(ex);
            return "false";
        }
        return laRespuesta;
    }
    
    public void enviarArchivo(String pathOrigen){
        try{
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());            
            FileInputStream fis = new FileInputStream(pathOrigen);            
            IOUtils.copy(fis, dos);
            fis.close();
            dos.close();
            clientSocket.close();
        }catch(Exception ex){
            System.out.println("Fallo al enviar archivo al cliente.");
            System.out.println(ex);
        }
    }
    
    public void recibirArchivo(String pathDestino){        
        try{
            File file = new File(pathDestino);
            if(!file.exists()){
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            FileOutputStream fos = new FileOutputStream(file);
            IOUtils.copy(dis, fos);
            fos.close();
        }catch(Exception ex){
            System.out.println("Fallo al descargar archivo desde cliente.");
            System.out.println(ex);
        }
    }
    
   public void enviarObjeto(Object elObjeto){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            oos.writeObject(elObjeto);
        }catch(Exception ex){
            System.out.println("Fallo al enviar objeto al servidor.");
            System.out.println(ex);
        }
    }
    
    public Object recibirObjeto() {
        Object obj = new Object();
        
        try{
            DataInputStream in = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            ObjectInputStream ois = new ObjectInputStream(in);
            obj = ois.readObject();
        }catch(Exception ex){
            System.out.println("Error al leer el objeto enviado por el cliente.");
            System.out.println(ex);
            return null;
        }
        
        return obj;
    }
}