package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

  
public class Server {
    
    static final int PUERTO_SERVIDOR = 9999;

    public static ServerSocket serverSocket;
    public static Boolean servidorIniciado = false;
    Socket clientSocket;
    
    public void iniciarServidor() throws Exception
    {
        servidorIniciado = true;
        
        // Crear socket con puerto
        serverSocket = new ServerSocket(PUERTO_SERVIDOR);
        System.out.println("Se ha creado un servidor!");
        System.out.println("IP: " + serverSocket.getInetAddress());
        System.out.println("PORT: " + serverSocket.getLocalPort());
        
        //El servidor se mantendra activo hasta que detenerServidor() lo apague.
        while (servidorIniciado) {
            //se inicia la aceptacion de consultas
            clientSocket = serverSocket.accept();
     
            //Se crea un nuevo hilo por cada nuevo cliente
            MetodosServidor multi = new MetodosServidor(clientSocket);
            multi.start();
        }
    }
}
