package methods;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import server.MetodosServidor;

public class StorageMonitor {
    
    public static Boolean hayAlmacenamiento;             
    private static long LIMITE_ALMACENAMIENTO_DISPONIBLE = 10737418240L; //10GB, 10.000MB.
    private static String PATH_REPOSITORIO_COMPARECENCIAS = System.getProperty("user.dir")
                                                            + System.getProperty("file.separator")
                                                            + "REPOSITORIO_COMPARECENCIAS"
                                                            + System.getProperty("file.separator");
    
    private static ScheduledFuture<?> storageMonitorHandler;
    private static ScheduledExecutorService strgExecutor = Executors.newScheduledThreadPool(1);
    
    public static void initStorageMonitor(){                            //SE EJECUTA UNA VEZ POR HORA
        storageMonitorHandler = strgExecutor.scheduleWithFixedDelay(runMonitor, 0, 1, TimeUnit.HOURS); 
    }
    
    public static void updateLimit(long nuevoLimite){
        LIMITE_ALMACENAMIENTO_DISPONIBLE = nuevoLimite;
    }
    
    private static final Runnable runMonitor = new Runnable() {
        public void run() {
            try {
                monitor();
            } catch (Exception e) {
            }
        }
    };
    
    private static void monitor() {
        try {
            
            createPathIfNotExist();
            File pathRepositorio = new File(PATH_REPOSITORIO_COMPARECENCIAS);
            long almacenamientoDisponible = pathRepositorio.getUsableSpace();

            if (almacenamientoDisponible > LIMITE_ALMACENAMIENTO_DISPONIBLE) {
                hayAlmacenamiento = true;
            } else {
                hayAlmacenamiento = false;
                MetodosServidor servidor = new MetodosServidor();
                servidor.detenerServidor();
            }
            
        } catch (Exception ex) {
            System.out.println("Error al verificar almacenamiento disponible.");
            System.out.println("Se reintentara en una hora.");
            System.out.println(ex);
        }
    }
    
    private static void createPathIfNotExist() {
        try {
            File file = new File(PATH_REPOSITORIO_COMPARECENCIAS);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.mkdir();
            }
        } catch (Exception ex) {
            System.out.println("Error al crear ruta de repositorio de comparecencias.");
            System.out.println(ex);
        }
    }
}
