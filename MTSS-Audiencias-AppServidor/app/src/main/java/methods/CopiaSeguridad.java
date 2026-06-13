package methods;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class CopiaSeguridad {
    
    private static ScheduledFuture<?> backupHandler;
    private static ScheduledExecutorService bckExecutor = Executors.newScheduledThreadPool(1);
    public static int backupInterval = 15;
    
    public static void initBackups(){
        backupHandler = bckExecutor.scheduleWithFixedDelay(createBackup, 0, backupInterval, TimeUnit.DAYS);
    }
    
    public static void updateInterval(){
        backupHandler.cancel(true);
        backupHandler = bckExecutor.scheduleWithFixedDelay(createBackup, backupInterval, backupInterval, TimeUnit.DAYS);
    }
    
    //cambiar estos valores para ajustarlos a su entorno
    private static final String MYSQLDUMP_PATH = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe";
    private static final String MYSQL_USERNAME = "root";
    private static final String MYSQL_PASSWORD = "root";
    private static final String DATABASE_NAME = "comparecencias";
    private static final String SAVE_FILENAME = "database_backup.sql";
    private static final String SAVE_PATH =  System.getProperty("user.dir")
                                            +System.getProperty("file.separator")
                                            +"COPIA_SEGURIDAD"
                                            +System.getProperty("file.separator")
                                            +SAVE_FILENAME;
                                           

    private static final Runnable createBackup = new Runnable() {
        public void run() {
            try {
                execCopiaSeguridad();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    };
    
    private static void execCopiaSeguridad() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                createPathIfNotExist();
                
                String[] command = crearComando();
                ProcessBuilder builder = new ProcessBuilder(command);
                builder.redirectErrorStream(true);
                Process process = builder.start();
                
                //DESCOMENTE ESTO SI NECESITA VER EL OUTPUT DEL PROCESO
                /*
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuffer buffer = new StringBuffer();
                String line = "";

                buffer.append(line).append("\n");

                while ((line = inputReader.readLine()) != null) {
                    System.out.println(line);
                }

                process.waitFor();
                inputReader.close();
                process.destroy();
                */
            }
        } catch (Exception e) {
            System.out.println("Error al ejecutar copia de seguridad.");
            System.out.println(e);
        }
    }
    
    private static String[] crearComando(){
        return new String[]{
            "cmd.exe"          
            ,"/c"
            ,"\""
            ,MYSQLDUMP_PATH
            ,"-u"
            ,MYSQL_USERNAME
            ,"-p"+MYSQL_PASSWORD
            ,DATABASE_NAME
            ,"-R"
            ,"-e"
            ,"--triggers"
            ,"--single-transaction"
            ,">"
            ,SAVE_PATH
            ,"\""
        };
    }
    
    private static void createPathIfNotExist() {
        try {
            File file = new File(SAVE_PATH);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (Exception ex) {
            System.out.println("Error al crear ruta de copia de seguridad.");
            System.out.println(ex);
        }
    }
        
}
