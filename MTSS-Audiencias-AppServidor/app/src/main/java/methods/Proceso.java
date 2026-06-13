package methods;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Proceso {
    
    public static Boolean execServidor(){
        
        ///!\ ATENCION
        //Este metodo solo funciona con los compilados, imposible testear en entorno de desarrollo.
        
        String localDir = System.getProperty("user.dir");
        String serverPath = localDir + System.getProperty("file.separator") + "Servidor.exe";

        try {
            if (System.getProperty("os.name").contains("Windows")) {

                ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "\"", serverPath, "\"");
                builder.redirectErrorStream(true);
                Process process = builder.start();

                BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuffer buffer = new StringBuffer();
                String line = "";

                buffer.append(line).append("\n");
                line = inputReader.readLine();
                
                if (line.contains("Se ha creado un servidor!")) {

                    process.waitFor();
                    inputReader.close();
                    return true;

                } else {
                    return false;
                }

            }else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
