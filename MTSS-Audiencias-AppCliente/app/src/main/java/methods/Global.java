package methods;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//Esta clase es para guardar objetos que deben ser accedidos desde todas partes
//O que crear instancias de sus clases no es viable.

public class Global {
    
    /*
        PROPERTIES
    
        Este objeto es para guardar la configuracion de la aplicacion.
        Actualmente solo almacena la ruta de las comparecencias.
    */
    
    public static Properties appConfig = new Properties();

    /*
        OBJETO PARA MANEJAR CONCURRENCIA
        Maneja todos los hilos y tareas que se van creando y las ejecuta una
        una por una. Un nuevo hilo se ejecutara solamente hasta que el hilo
        anterior haya termiando su trabajo.  Evita el bloqueo de procesos y
        archivos.
    
        MULTIHILOS
        La aplicacion es multihilos para maximizar la velocidad de la aplicacion.
        Evita que la escritura de archivos y las consultas a la base de datos,
        relenticen la interfaz.
    
        io = para toda escritura o lectura de archivos.
        db = para todas los comandos y consultas a base de datos local.
        server = para todos los comandos y consultas con el servidor.
    */
    
    public static ExecutorService ioExecutor = new ThreadPoolExecutor(
                                        1, 1, 0L, TimeUnit.MILLISECONDS, new 
                                        LinkedBlockingQueue<>());
    
    public static ExecutorService dbExecutor = new ThreadPoolExecutor(
                                    1, 1, 0L, TimeUnit.MILLISECONDS, new 
                                    LinkedBlockingQueue<>());
    
    public static ExecutorService serverExecutor = new ThreadPoolExecutor(
                                1, 1, 0L, TimeUnit.MILLISECONDS, new 
                                LinkedBlockingQueue<>());
    
    public static ExecutorService bgExecutor = new ThreadPoolExecutor(
                            1, 1, 0L, TimeUnit.MILLISECONDS, new 
                            LinkedBlockingQueue<>());
    
    public static ScheduledExecutorService bckExecutor = Executors.newScheduledThreadPool(1);
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