package methods;

public class Proceso {
    
    public Proceso(){}
    
    public void execConfigurarMicronofo(){
        String command = "";
        if(System.getProperty("os.name").contains("Windows")){
            command = "cmd /c \"control mmsys.cpl sounds\"";
        }else{
            return;
        }
        
        try{
            Runtime.getRuntime().exec(command);
        }catch(Exception e){}
    }
    
    public void execNavegadorWeb(String elLink) {

        String command = "";
        if (System.getProperty("os.name").contains("Windows")) {
            command = "cmd /c \"start "+elLink+"\""; //executes default browser
        } else {
            return;
        }
        try {
            Runtime.getRuntime().exec(command);
        } catch (Exception e) {
        }

    }
}
