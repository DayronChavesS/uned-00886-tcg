package methods;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.filechooser.FileSystemView;

public class Telemetria{
        
    private String localDir;
    private File log;
    private File dir;
    private FileWriter fileWriter;
    private DateTimeFormatter dtf;
    private LocalDateTime now;
    
    public Telemetria(){
        localDir = System.getProperty("user.dir");
        log = new File(localDir, "log.txt");
        dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss");  
    }
    
    public void escribirTelemetria() {
        Global.ioExecutor.execute(
        new Thread(() -> {
            try {
                fileWriter = new FileWriter(log, false);
                fileWriter.write(crearEncabezado()
                        + capturarInformacionGeneral()
                        + capturarInformacionHardware()
                        + capturarInformacionDiscos()
                        + capturarEstructuraDirectorio(log.getParentFile()));
                fileWriter.close();
            } catch (IOException ex) {
            }
        }));
    }
    
    public void logActivity(String event) {
        Global.ioExecutor.execute(
        new Thread(() -> {
            try {
                now = LocalDateTime.now();

                fileWriter = new FileWriter(log, true);
                fileWriter.write("[EVENT] - " + "[" + dtf.format(now) + "]" + ": "
                        + event + "\n");
                fileWriter.close();
            } catch (IOException ex) {
            }
        }));
    }
    
    public void logException(Exception exception) {
        Global.ioExecutor.execute(
        new Thread(() -> {
            try {
                now = LocalDateTime.now();

                fileWriter = new FileWriter(log, true);
                fileWriter.write("\n" + "/!\\ [ERROR] - " + "[" + dtf.format(now) + "]" + " /!\\" + "\n");
                fileWriter.write("MESSAGE: " + exception.getMessage() + "\n");
                fileWriter.write("CAUSE: " + exception.getCause() + "\n");

                //NECESARIO PARA CAPTURAR STACKTRACE
                StringWriter sw = new StringWriter();
                exception.printStackTrace(new PrintWriter(sw));

                /*ELIMINAR INFORMACION IRRELEVANTE
                String regex = "^.*(at java|at org.sqlite).*$";
                String stacktraceString = sw.toString();
                String stacktraceReplace = stacktraceString.replaceAll(regex, "");
                 */
                fileWriter.write("STACKTRACE: " + sw.toString() + "\n\n");
                fileWriter.close();
            } catch (IOException ex) {
            }
        }));
    }
    
    private String crearEncabezado(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("==================================================================="+"\n");
        sb.append("ATENCION:" + "\n");
        sb.append("La informacion que contiene este archivo de texto NO se envia a internet." + "\n");
        sb.append("Usted solo debe enviar estos datos a los desarrolladores del programa." + "\n");
        sb.append("Los desarrolladores usaran estos datos exclusivamente para mejorar el programa." + "\n");
        sb.append("==================================================================="+"\n\n");

        return sb.toString();
    }
    
    private String capturarInformacionGeneral() {

        StringBuilder sb = new StringBuilder();
        sb.append("==================================================================="+"\n");
        sb.append("| GENERAL DATA |" + "\n");
        
        sb.append("| JAVA PLATFORM PROPERTIES |" + "\n");
        for (Object propertyKeyName : System.getProperties().keySet()) {
            sb.append(propertyKeyName + ":  " + System.getProperty(propertyKeyName.toString())+"\n");
        }

        sb.append("\n" + "| OPERATING SYSTEM PROPERTIES |" + "\n");
        System.getenv().forEach((k, v) -> {
            sb.append(k + ":" + v + "\n");
        });
        
        sb.append("==================================================================="+"\n\n");

        return sb.toString();
    }
    
    private String capturarInformacionHardware(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("==================================================================="+"\n");
        sb.append("| HARDWARE DATA |" + "\n");
        
        sb.append("NO_CPU: " + Runtime.getRuntime().availableProcessors()+"\n");
        sb.append("TOTAL_RAM: " + Runtime.getRuntime().totalMemory()+"\n");
        sb.append("FREE_RAM: " + Runtime.getRuntime().freeMemory()+"\n");
        sb.append("USABLE_RAM: " + Runtime.getRuntime().maxMemory()+"\n");

        sb.append("==================================================================="+"\n\n");
        
        return sb.toString();
    }
    
    private String capturarInformacionDiscos(){
        StringBuilder sb = new StringBuilder();
        sb.append("==================================================================="+"\n");
        sb.append("| DISKS DATA |" + "\n");
        
        FileSystemView fsv = FileSystemView.getFileSystemView();
        sb.append("HOME DIRECTORY: " + fsv.getHomeDirectory() + "\n");

        
        File[] roots = fsv.getRoots();
        for (int i = 0; i < roots.length; i++) {
            sb.append("DISK_NO." + i+1 + ": " + roots[i] + "\n");
        }
        
        File[] f = File.listRoots();
        for (int i = 0; i < f.length; i++) {
            sb.append("DISK: " + f[i] + "\n");
            sb.append("Display name: " + fsv.getSystemDisplayName(f[i]) + "\n");
            sb.append("Is drive: " + fsv.isDrive(f[i]) + "\n");
            sb.append("Is floppy: " + fsv.isFloppyDrive(f[i]) + "\n");
            sb.append("Readable: " + f[i].canRead() + "\n");
            sb.append("Writable: " + f[i].canWrite() + "\n");
            sb.append("Total space: " + f[i].getTotalSpace() + "\n");
            sb.append("Usable space: " + f[i].getUsableSpace() + "\n");
        }
        

        sb.append("==================================================================="+"\n\n");
        return sb.toString();
    }
    
    private String capturarEstructuraDirectorio(File folder) {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        int indent = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("==================================================================="+"\n");
        sb.append("| DIRECTORY TREE |" + "\n");
        printDirectoryTree(folder, indent, sb);
        sb.append("==================================================================="+"\n\n");
        sb.append("==================================================================="+"\n");
        sb.append("| SESSION ACTIVIY |" + "\n");
        return sb.toString();
    }

    private void printDirectoryTree(File folder, int indent,
            StringBuilder sb) {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        sb.append(getIndentString(indent));
        sb.append("+--");
        sb.append(folder.getName());
        sb.append("/");
        sb.append("\n");
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                printDirectoryTree(file, indent + 1, sb);
            } else {
                printFile(file, indent + 1, sb);
            }
        }

    }

    private void printFile(File file, int indent, StringBuilder sb) {
        sb.append(getIndentString(indent));
        sb.append("+--");
        sb.append(file.getName());
        sb.append("\n");
    }

    private String getIndentString(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("|  ");
        }
        return sb.toString();
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