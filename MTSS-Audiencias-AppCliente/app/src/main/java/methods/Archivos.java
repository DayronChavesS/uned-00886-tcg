package methods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import models.Audio;
import models.Comparecencia;

public class Archivos {
    Telemetria telemetria = new Telemetria();

    private final String localDir = System.getProperty("user.dir");
    private final String comparecenciasPath = methods.Global.appConfig.getProperty("comparecencias_path");
    private final File configFile = new File(localDir, "audiencias.conf");
    private String currentUUID;
    
    public Archivos() {
    }
    
    public void crearArchivoConfiguracion(){
        Runnable runnableTask = () -> {            
            try {
                configFile.createNewFile();
                telemetria.logActivity("Se creo el archivo de configuraciones.");
            } catch (IOException ex) {
                telemetria.logActivity("Error al crear el archivo de configuraciones.");
                telemetria.logException(ex);
            }
        };
        Global.ioExecutor.execute(runnableTask);    
    }

    public Boolean guardarArchivoPropiedades(String path) {
        try {
            Callable<Boolean> task = () -> {

                FileInputStream inputProperty = new FileInputStream(configFile.getAbsolutePath());
                Global.appConfig.load(inputProperty);
                Writer inputStream = new FileWriter(configFile);
                Global.appConfig.store(inputStream, null);
                return true;
            };
            Future<Boolean> future = Global.ioExecutor.submit(task);
            return future.get();
        } catch (Exception ex) {
            telemetria.logActivity("Error al definir la carpeta para guardar propiedades.");
            telemetria.logException(ex);
            return false;
        }
    }
    
    public void cargarArchivoPropiedades(){
        try {
            Runnable runnableTask = () -> {            
                try {
                    Global.appConfig.load(new FileInputStream(configFile.getAbsolutePath()));
                } catch (IOException ex) {
                    telemetria.logActivity("Error al leer archivo de configuraciones.");
                    telemetria.logException(ex);                
                }
            };
            Global.ioExecutor.execute(runnableTask);    
        } catch (Exception e) {
            telemetria.logActivity("Error al leer archivo de configuraciones.");
            telemetria.logException(e);
        }
    }
    
    public Boolean archivoPropiedadesExiste(){
        return configFile.exists();
    }
    
    public Boolean crearCarpetaComparecencias() {
        File carpetaComparecencias = new File(Global.appConfig.getProperty("comparecencias_path"));
        carpetaComparecencias.mkdir();
        telemetria.logActivity("Se creo la carpeta para guardar comparecencias.");
        return validarCarpeta(carpetaComparecencias.toPath());
    }
    
    public Boolean validarCarpeta(Path elDestino) {
        if (!Files.isDirectory(elDestino)) {
            telemetria.logActivity("El destino no es un directorio.");
            return false;
        }
        if (!Files.exists(elDestino)) {
            telemetria.logActivity("El destino no se creo o no existe.");
            return false;
        }
        if (!Files.isWritable(elDestino)) {
            telemetria.logActivity("La aplicacion no tiene permisos de escritura para ese destino.");
            return false;
        }
        if (!Files.isReadable(elDestino)) {
            telemetria.logActivity("La aplicacion no tiene permisos de lectura ese destino.");
            return false;
        }
        if (!Files.isExecutable(elDestino)) {
            telemetria.logActivity("La aplicacion no tiene permisos de ejecucion en ese destino.");
            return false;
        }
        return true;
    }
    
    public void crearCarpetaNuevaComparecencia(Comparecencia comparecencia){
        telemetria.logActivity("Se ha creado la carpeta donde guardar la nueva comparecencia.");
        //los archivos se organizan en carpetas con el codigo SILAC del caso.
        File carpetaComparecencia = new File(
                  comparecenciasPath 
                + System.getProperty("file.separator")
                + comparecencia.getCodigoSILAC() 
                + System.getProperty("file.separator")
        );
        carpetaComparecencia.mkdir();
    }
    
    public Audio crearArchivoAudio(Comparecencia comparecencia, Audio audio) throws IOException{
        try{
            telemetria.logActivity("Se ha creado el archivo de audio para guardar la grabacion.");
            currentUUID = generarUUIDAudio();
            File archivoAudio = new File(
                      comparecenciasPath 
                    + System.getProperty("file.separator")
                    + comparecencia.getCodigoSILAC() + System.getProperty("file.separator")
                    + currentUUID + ".wav"
            );
            archivoAudio.createNewFile();

            audio.setArchivoAudio(archivoAudio);
            audio.setPathArchivoAudio(archivoAudio.getAbsolutePath());
        }catch(Exception e){
            telemetria.logActivity("Error al crear el archivo de audio.");
            telemetria.logException(e);
        }
        return audio;
    }
    
    public Audio crearArchivoAnotaciones(Comparecencia comparecencia, Audio audio) throws IOException{
        try{
            telemetria.logActivity("Se ha creado el archivo de texto para guardar las anotaciones.");
            File archivoAnotacion = new File(
                      comparecenciasPath 
                    + System.getProperty("file.separator")
                    + comparecencia.getCodigoSILAC() + System.getProperty("file.separator")
                    + currentUUID + ".json"
            );
            archivoAnotacion.createNewFile();

            audio.setArchivoAnotaciones(archivoAnotacion);
            audio.setPathArchivoAnotaciones(archivoAnotacion.getAbsolutePath());
        }catch(Exception e){
            telemetria.logActivity("Error al crear el archivo de anotaciones.");
            telemetria.logException(e);
        }
        return audio;
    }
    
    boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
    
    public Boolean deleteFileIfExist(String elArchivo){
        File file = new File(elArchivo);
        return (file.exists() && file.delete());
    }
        
    public void escribirArchivoAnotaciones(String anotacionesPath, String JSON){
         Runnable runnableTask = () -> {            
            try {
                File archivoAnotaciones = new File(anotacionesPath);
                FileWriter escribirAnotaciones = new FileWriter(archivoAnotaciones,true);
                escribirAnotaciones.write(JSON);
                escribirAnotaciones.close();
                telemetria.logActivity("Se escribio correctamente en el archivo de anotaciones.");

            } catch (Exception e) {
                telemetria.logActivity("Error al escribir en el archivo de anotaciones.");
                telemetria.logException(e);
            }
        };
        Global.ioExecutor.execute(runnableTask);    
    }
    
    public String leerArchivoAnotaciones(String anotacionesPath) {
        try {
            Callable<String> task = () -> {
                Scanner lector = new Scanner(new File(anotacionesPath));
                String JSON = "";
                while (lector.hasNextLine()) {
                    JSON = lector.nextLine();
                }
                lector.close();
                return JSON;
            };
            Future<String> future = Global.ioExecutor.submit(task);
            return future.get();
        } catch (Exception e) {
            telemetria.logActivity("Error al leer archivo de anotaciones.");
            telemetria.logException(e);
            return null;
        }
    }
    
    /* //busqueda en sistema de archivos
    public List<String> listarTodosLosJSON(){
        List<String> listaJSONs = new LinkedList<>();
        
        try (Stream<Path> walkStream = Files.walk(Paths.get(comparecenciasPath))) {
            walkStream.filter(p -> p.toFile().isFile()).forEach(f -> {
                if (f.toString().endsWith(".json")) {
                    listaJSONs.add(f.toAbsolutePath().toString());
                }
            });     
        }catch(Exception e){
            telemetria.logActivity("Error al listar todos los JSON.");
            telemetria.logException(e);
        }
        return listaJSONs;
    }
    */
    
    public List<String> buscarLaAnotacion(String laBusqueda, List<String> listaJSONs){

        List<String> listaFiltrada = new LinkedList<>();
        for(String path : listaJSONs){
            
            String jsonDATA = leerArchivoAnotaciones(path);
            if(jsonDATA.toLowerCase().contains(laBusqueda.toLowerCase())){
                listaFiltrada.add(path);
            }
            
        }
        return listaFiltrada;
    }
    
    public final String generarUUIDAudio(){
        Random rng = new Random();
        String text = "";
        for (int i = 0; i < 11; i++)
        {
            if((i == 2) || (i == 4) || (i == 9)){
                text += "-";
            }
            text += String.valueOf(rng.nextInt(9));
        }
        return text;
    }
    
    public void exportarAudio(String origen, String destino, String nombreGrabacion) throws IOException{
        File elOrigen = new File(origen);
        File elDestino = new File(destino+System.getProperty("file.separator")+nombreGrabacion+".wav");

            Files.copy(elOrigen.toPath(), 
                       elDestino.toPath(), 
                       StandardCopyOption.REPLACE_EXISTING);
    
    }
    
    public void exportarComparecencia(String elOrigen, String nombreComparecencia, String elDestino) throws IOException{
        String pathOrigen = elOrigen;
        String pathDestino = elDestino + System.getProperty("file.separator") + nombreComparecencia + ".mtss";
        
        crearElZip(pathOrigen, pathDestino);
    }
    
    private void crearElZip(String pathOrigen, String pathDestino){
        try{
            FileOutputStream elZip = new FileOutputStream(pathDestino);
            ZipOutputStream zipCreator = new ZipOutputStream(elZip);

            File fileToZip = new File(pathOrigen);
            comprimirElDirectorio(fileToZip, fileToZip.getName(), zipCreator);
            zipCreator.close();
            elZip.close();
        }catch(Exception ex){
            telemetria.logActivity("Error al archivar el directorio.");
            telemetria.logException(ex);
        }
    }
    
    //metodo recursivo
    private void comprimirElDirectorio(File fileToZip, String fileName, ZipOutputStream zipOut) {
        try{
            if (fileToZip.isHidden()) {
                return;
            }
            if (fileToZip.isDirectory()) {
                if (fileName.endsWith("/")) {
                    zipOut.putNextEntry(new ZipEntry(fileName));
                    zipOut.closeEntry();
                } else {
                    zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                    zipOut.closeEntry();
                }
                File[] children = fileToZip.listFiles();
                for (File childFile : children) {
                    comprimirElDirectorio(childFile, fileName + "/" + childFile.getName(), zipOut);
                }
                return;
            }
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }catch(IOException e){
            telemetria.logActivity("Error al archivar el directorio.");
            telemetria.logException(e);
        }
    }
    
    public String importarComparecencia(String elOrigen){
        File newFile = new File("");
        try{
            String fileZip = elOrigen;
            File destDir = new File(comparecenciasPath);

            byte[] buffer = new byte[1024];
            ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
            ZipEntry zipEntry = zis.getNextEntry();
            deleteDirectory(new File(destDir.getAbsolutePath() + System.getProperty("file.separator") + zipEntry.getName()));
            while (zipEntry != null) {
                newFile = windowsCheck(destDir, zipEntry);
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    // fix for Windows-created archives
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }

                    // write file content
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
                zipEntry = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();
        }catch(Exception ex){
            telemetria.logActivity("Error al extraer el Zip.");
            telemetria.logException(ex);
        }
        return newFile.getAbsolutePath();
    }
    
    public File windowsCheck(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Este zip es incompatible con el Sistema Operativo.");
        }

        return destFile;
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