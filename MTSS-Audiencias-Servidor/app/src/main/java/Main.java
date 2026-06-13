import database.ConexionDatabase;
import server.Server;

public class Main {
    
    public static void main(String[] args) {

        //se abre un hilo para iniciar el servidor y atender consultas
        Thread hiloServidor = new Thread(new Runnable() {
            @Override
            public void run() {
                iniciarServidor();
            }

        });
        
        //se abre un hilo para conectar a las base de datos y atender consultas
        Thread hiloBaseDeDatos = new Thread(new Runnable() {
            @Override
            public void run() {
                conectarBaseDeDatos();
            }

        });
        
        hiloServidor.start();
        hiloBaseDeDatos.start();

    }
    
    public static void iniciarServidor(){
        //arrancar el servidor
        try {
            Server server = new Server();
            server.iniciarServidor();
        } catch (Exception ex) {
            System.out.println("Fallo al crear el servidor, abortando.");
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
    
    public static void conectarBaseDeDatos(){
        //conectar con la base de datos
        ConexionDatabase mysql = new ConexionDatabase();
        mysql.abrirConexion();
    }
    
    
    
}
