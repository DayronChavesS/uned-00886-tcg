package server;

import database.ConexionDatabase;
import database.SQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import javax.swing.table.DefaultTableModel;
import methods.Global;
import methods.Telemetria;
import models.Inspector;

public class MetodosServidor {

/*
    CODIGOS DE CONSULTAS:
    case "00": estadoServidor();
    case "01": registrarUsuario();
    case "02": iniciarSesion();
    case "03": enviarListaComparecencias();
    case "04": guardarDatos();
    case "05": guardarAnotaciones();
    case "06": guardarAudio();
    case "07": enviarDatos();
    case "08": enviarAnotaciones();
    case "09": enviarAudio();
    case "10": buscarComparecencia();
*/    
    
    private Telemetria telemetria;
    private Inspector inspector;
    private ConexionServidor conexionServidor;
    private ConexionDatabase conexionSQLite;
    private PreparedStatement consulta;
    private ResultSet resultado;
    private Map<String, Map<String,String>> uploadData;
    private Map<String, Map<String,String>> downloadData;
    private List<String> solicitudServidor;
    private String respuestaServidor;
        
    public MetodosServidor(Inspector inspector) {
        initVariables();
        this.inspector =  inspector;
    }

    private void initVariables(){
        telemetria = new Telemetria();
        conexionSQLite = new ConexionDatabase();
        conexionServidor = new ConexionServidor();
        uploadData = new HashMap<>();
        downloadData = new HashMap<>();
        solicitudServidor = new LinkedList<>();
        respuestaServidor = "";
    }
    
    public void setInspector(Inspector inspector){
        this.inspector = inspector;
    }
    
    private void closeAll() {
        try {
            if(conexionSQLite.sqliteConexion != null){
                conexionSQLite.cerrarConexionSQLITE();
            }
            if(consulta != null){
                consulta.close();
                consulta = null;
            }
            if(resultado != null){
                resultado.close();
                resultado = null;
            }
        } catch (SQLException e) {
            telemetria.logActivity("Error al cerrar la conexion con base de datos.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);
        }
    }
    
    public Boolean verificarEstadoServidor(){
        try {
            Callable<Boolean> task = () -> {
                
                if (conexionServidor.conectarServidor()) {

                    solicitudServidor.clear();
                    solicitudServidor.add("00");
                    conexionServidor.enviarObjeto(solicitudServidor);

                    return "true".equals(conexionServidor.recibirMensaje());
                }
                
                return false;
            };
            Future<Boolean> future = Global.dbExecutor.submit(task);
            return future.get();
        } catch (Exception ex) {
            telemetria.logActivity("Fallo al verificar estado de servidor.");
            telemetria.logException(ex);
            return false;
        }
    }
    
    public DefaultTableModel getComparecenciasOnline(DefaultTableModel tblPrincipalOnlineModel){
        try {
            if (conexionServidor.conectarServidor()) {

                solicitudServidor.clear();
                solicitudServidor.add("03");
                solicitudServidor.add(String.valueOf(inspector.getPuesto().ordinal()));
                solicitudServidor.add(String.valueOf(inspector.getRegion().ordinal()));
                solicitudServidor.add(String.valueOf(inspector.getOficina().ordinal()));
                solicitudServidor.add(String.valueOf(inspector.getPersona().getIdPersona()));
                conexionServidor.enviarObjeto(solicitudServidor);
                
                Object objDatos = conexionServidor.recibirObjeto();
                
                if(objDatos instanceof List){
                    List<Object[]> listaDatos = (List) objDatos;
                    
                    for(Object[] row : listaDatos){
                        tblPrincipalOnlineModel.addRow(row);
                    }
                    
                    return tblPrincipalOnlineModel;
                }
            }
        } catch (Exception ex) {
            telemetria.logActivity("Fallo al obtener las comparecencias en linea.");
            telemetria.logException(ex);
            return null;
        } finally {
            conexionServidor.desconectarServidor();
        }
        return null;
    }
    
    public Integer subirComparecencia(int idComparecencia) {
        try {
            //Callable<Boolean> task = () -> {
                if (conexionServidor.conectarServidor()) {
                    
                    solicitudServidor.clear();
                    solicitudServidor.add("04");
                    solicitudServidor.add(String.valueOf(inspector.getPuesto().ordinal()));
                    solicitudServidor.add(String.valueOf(inspector.getRegion().ordinal()));
                    solicitudServidor.add(String.valueOf(inspector.getOficina().ordinal()));
                    conexionServidor.enviarObjeto(solicitudServidor); //se prepara al servidor
                                                                      //para recibir datos

                    uploadData = extractComparecencia(idComparecencia);
                    
                    respuestaServidor = conexionServidor.recibirMensaje();
                    if (respuestaServidor.equals("ready")) {
                        conexionServidor.enviarObjeto(uploadData);
                    }
                    
                    //se recibe el nuevo id
                    respuestaServidor = conexionServidor.recibirMensaje();
                    return Integer.valueOf(respuestaServidor);
                }
            //};
            //Future<Boolean> future = Global.serverExecutor.submit(task);
            //return future.get();
        } catch (Exception ex) {
            telemetria.logActivity("Fallo al enviar comparecencia.");
            telemetria.logException(ex);
            return -1;
        } finally {
            conexionServidor.desconectarServidor();
        }
        return -1;
    }

    public Boolean subirAudio(int idComparecenciaOnline, String audioPath){
        try{
            if (conexionServidor.conectarServidor()) {

                solicitudServidor.clear();
                solicitudServidor.add("05");
                solicitudServidor.add(String.valueOf(inspector.getPuesto().ordinal()));
                solicitudServidor.add(String.valueOf(inspector.getRegion().ordinal()));
                solicitudServidor.add(String.valueOf(inspector.getOficina().ordinal()));
                solicitudServidor.add(String.valueOf(idComparecenciaOnline));
                String fileName = audioPath.replaceAll(".*(?=.{18})", "");
                solicitudServidor.add(fileName);
                conexionServidor.enviarObjeto(solicitudServidor);
                
                respuestaServidor = conexionServidor.recibirMensaje();
                if (respuestaServidor.equals("ready")) {
                    conexionServidor.enviarArchivo(audioPath);
                }
                
            }
        }catch(Exception ex){
            telemetria.logActivity("Fallo al enviar audio de la comparecencia.");
            telemetria.logException(ex);
            return false;
        }finally{
            conexionServidor.desconectarServidor();
        }
        return false;
    }
    
    public Boolean subirAnotacion(int idComparecenciaOnline, String anotacionPath){
        try{
            if (conexionServidor.conectarServidor()) {

                solicitudServidor.clear();
                solicitudServidor.add("06");
                solicitudServidor.add(String.valueOf(inspector.getPuesto().ordinal()));
                solicitudServidor.add(String.valueOf(inspector.getRegion().ordinal()));
                solicitudServidor.add(String.valueOf(inspector.getOficina().ordinal()));
                solicitudServidor.add(String.valueOf(idComparecenciaOnline));
                String fileName = anotacionPath.replaceAll(".*(?=.{19})", "");
                solicitudServidor.add(fileName);
                conexionServidor.enviarObjeto(solicitudServidor);
                
                respuestaServidor = conexionServidor.recibirMensaje();
                if (respuestaServidor.equals("ready")) {
                    conexionServidor.enviarArchivo(anotacionPath);
                }
                
            }
        }catch(Exception ex){
            telemetria.logActivity("Fallo al enviar audio de la comparecencia.");
            telemetria.logException(ex);
            return false;
        }finally{
            conexionServidor.desconectarServidor();
        }
        return false;
    }
    
    public Boolean descargarComparecencia(int idComparecenciaOnline){
        try {
            Callable<Boolean> task = () -> {
                
                if(conexionServidor.conectarServidor()){
                    
                    solicitudServidor.clear();
                    solicitudServidor.add("07");
                    solicitudServidor.add(String.valueOf(inspector.getPuesto().ordinal()));
                    solicitudServidor.add(String.valueOf(inspector.getRegion().ordinal()));
                    solicitudServidor.add(String.valueOf(inspector.getOficina().ordinal()));
                    solicitudServidor.add(String.valueOf(idComparecenciaOnline));
                    conexionServidor.enviarObjeto(solicitudServidor);

                    Object comparecenciaObj = conexionServidor.recibirObjeto();
                    if(comparecenciaObj instanceof Map){
                        downloadData = (Map<String, Map<String,String>>) comparecenciaObj;
                        descargarLaComparecencia(downloadData);
                        comparecenciaObj = null;
                        return true;
                    }else{
                        comparecenciaObj = null;
                        return false;
                    }
                }
                return false;
            };
            Future<Boolean> future = Global.serverExecutor.submit(task);
            return future.get();
        } catch (Exception ex) {
            telemetria.logActivity("Fallo al descargar la comparecencia desde el servidor.");
            telemetria.logException(ex);
            return false;
        } finally {
            conexionServidor.desconectarServidor();
        }
    }
    
    public Boolean descargarAudio(int idComparecenciaOnline, String audioPath){
        try {
            Callable<Boolean> task = () -> {
                
                if(conexionServidor.conectarServidor()){
                                        
                    solicitudServidor.clear();
                    solicitudServidor.add("08");
                    solicitudServidor.add(String.valueOf(inspector.getPuesto().ordinal()));
                    solicitudServidor.add(String.valueOf(inspector.getRegion().ordinal()));
                    solicitudServidor.add(String.valueOf(inspector.getOficina().ordinal()));
                    solicitudServidor.add(String.valueOf(idComparecenciaOnline));
                    String fileName = audioPath.replaceAll(".*(?=.{18})", "");
                    solicitudServidor.add(fileName);
                    
                    conexionServidor.enviarObjeto(solicitudServidor);

                    if("confirm".equals(conexionServidor.recibirMensaje())){
                        conexionServidor.enviarMensaje("ready");
                        conexionServidor.recibirArchivo(audioPath);
                        return true;
                    }
                }
                return false;
            };
            Future<Boolean> future = Global.serverExecutor.submit(task);
            return future.get();
        } catch (Exception ex) {
            telemetria.logActivity("Fallo al descargar el audio de la comparecencia desde el servidor.");
            telemetria.logException(ex);
            return false;
        } finally {
            conexionServidor.desconectarServidor();
        }
    }
    
    public Boolean descargarAnotacion(int idComparecenciaOnline, String anotacionPath){
        try {
            Callable<Boolean> task = () -> {
                
                if(conexionServidor.conectarServidor()){
                    
                    solicitudServidor.clear();
                    solicitudServidor.add("09");
                    solicitudServidor.add(String.valueOf(inspector.getPuesto().ordinal()));
                    solicitudServidor.add(String.valueOf(inspector.getRegion().ordinal()));
                    solicitudServidor.add(String.valueOf(inspector.getOficina().ordinal()));
                    solicitudServidor.add(String.valueOf(idComparecenciaOnline));
                    String fileName = anotacionPath.replaceAll(".*(?=.{19})", "");
                    solicitudServidor.add(fileName);
                    conexionServidor.enviarObjeto(solicitudServidor);
                    
                    if("confirm".equals(conexionServidor.recibirMensaje())){
                        conexionServidor.enviarMensaje("ready");
                        conexionServidor.recibirArchivo(anotacionPath);
                        return true;
                    }
                    
                }
                return false;
            };
            Future<Boolean> future = Global.serverExecutor.submit(task);
            return future.get();
        } catch (Exception ex) {
            telemetria.logActivity("Fallo al descargar la anotacion de la comparecencia desde el servidor.");
            telemetria.logException(ex);
            return false;
        } finally {
            conexionServidor.desconectarServidor();
        }
    }

    public int getIdComparecencia(String codigoSILAC){
        try {
            Callable<Integer> task = () -> {
                
                if(conexionServidor.conectarServidor()){
                    
                    solicitudServidor.clear();
                    solicitudServidor.add("10");
                    solicitudServidor.add(String.valueOf(inspector.getPuesto().ordinal()));
                    solicitudServidor.add(String.valueOf(inspector.getRegion().ordinal()));
                    solicitudServidor.add(String.valueOf(inspector.getOficina().ordinal()));
                    solicitudServidor.add(codigoSILAC);
                    conexionServidor.enviarObjeto(solicitudServidor);

                    int idComparecencia = Integer.parseInt(conexionServidor.recibirMensaje());
                    return idComparecencia;
                }
                return -1;
            };
            Future<Integer> future = Global.serverExecutor.submit(task);
            return future.get();
        } catch (Exception ex) {
            telemetria.logActivity("Fallo al obtener idComparecencia del servidor.");
            telemetria.logException(ex);
            return -1;
        } finally {
            conexionServidor.desconectarServidor();
        }
    }
    

    
//SERVIDOR = MISMA LOGICA QUE EXPORTAR E IMPORTAR DE BASE DE DATOS
//           PERO SE TRANSFIEREN LOS DATOS POR MEDIO
//           DE LISTAS DE LISTAS DE STRING CON CLAVES
//           YA QUE DEBEN SER SERIALIZABLES.
/******************************************************************************/
    
    //SUBIR
    
    private Map extractComparecencia(int idComparecencia) {

        try{
            
            int personaCount = 1;
            int personaJuridicaCount = 1;
            int testigoCount = 1;
            int acompañanteCount = 1;
            int representanteCount = 1;
            int audioCount = 1;
            int audioXComparecenciaCount = 1;
            int inspectorXComparecenciaCount = 1;
            int inspectorCount = 1;
            int usuarioCount = 1;
            
            //SAVE comparecencia
            conexionSQLite.abrirConexion();
            consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_COMPARECENCIAS.toString());
            consulta.setInt(1, idComparecencia);
            ResultSet elResultado = consulta.executeQuery();
            
            while(elResultado.next()){
                Map<String,String> laComparecencia = new HashMap<>();
                laComparecencia.put("idComparecencia",String.valueOf(elResultado.getInt(1)));
                laComparecencia.put("codigoSILAC",elResultado.getString(2));
                laComparecencia.put("idCaso",String.valueOf(elResultado.getInt(3)));
                laComparecencia.put("ubicacion",elResultado.getString(4));
                laComparecencia.put("linkExpediente",elResultado.getString(5));
                laComparecencia.put("fecha",String.valueOf(elResultado.getString(6)));
                uploadData.put("comparecencia", laComparecencia);
            }

            //SAVE audioxcomparecencia
            consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_AUDIOXCOMPARECENCIAS.toString());
            consulta.setInt(1, idComparecencia);
            elResultado = consulta.executeQuery();
            
            while(elResultado.next()){
                Map<String,String> elAudioXComparecencia = new HashMap<>();
                elAudioXComparecencia.put("idAudio",String.valueOf(elResultado.getInt(1)));
                elAudioXComparecencia.put("idComparecencia",String.valueOf(elResultado.getInt(2)));
                uploadData.put("audioxcomparecencia"+audioXComparecenciaCount, elAudioXComparecencia);
                audioXComparecenciaCount++;
            }
            
            //FIND audio
            consulta = conexionSQLite.sqliteConexion.prepareStatement(
            """
            SELECT idAudio FROM audioxcomparecencia  
            WHERE audioxcomparecencia.idComparecencia = ?
            """
            );
            
            consulta.setInt(1, idComparecencia);
            elResultado = consulta.executeQuery();
            
            //SAVE audio [MULTI]
            while (elResultado.next()) {
                consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_AUDIO.toString());
                consulta.setInt(1, elResultado.getInt(1));
                
                ResultSet subResultado = consulta.executeQuery();
                while (subResultado.next()) {
                    Map<String,String> elAudio = new HashMap<>();
                    elAudio.put("idAudio",String.valueOf(subResultado.getInt(1)));
                    elAudio.put("nombreAudio",subResultado.getString(2));
                    elAudio.put("duracionAudio",subResultado.getString(3));
                    elAudio.put("pathArchivoAudio",subResultado.getString(4));
                    elAudio.put("pathArchivoAnotaciones",subResultado.getString(5));
                    uploadData.put("audio" + audioCount, elAudio);
                    audioCount++;
                }
                
            }
            
            //SAVE gestionante
            consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_GESTIONANTE.toString());
            consulta.setInt(1, idComparecencia);
            elResultado = consulta.executeQuery();
            int idGestionante = 0;
            while(elResultado.next()){
                //GET idGestionante
                idGestionante = elResultado.getInt(1);
                Map<String,String> elGestionante = new HashMap<>();
                elGestionante.put("idGestionante",String.valueOf(elResultado.getInt(1)));
                elGestionante.put("idPersona",String.valueOf(elResultado.getInt(2)));
                elGestionante.put("idPersonaJuridica",String.valueOf(elResultado.getInt(3)));
                elGestionante.put("idComparecencia",String.valueOf(elResultado.getInt(4)));
                uploadData.put("gestionante", elGestionante);
            }
            

            
            //FIND persona|juridicaXgestionante
            consulta = conexionSQLite.sqliteConexion.prepareStatement(
            """
            SELECT idGestionante,idPersona,idPersonaJuridica FROM gestionante
            WHERE gestionante.idComparecencia = ?
            """
            );
            consulta.setInt(1, idComparecencia);
            elResultado = consulta.executeQuery();
            
            //SAVE gestionantexpersona
            int idPersona;
            if(elResultado.getInt(2) != 0){
                idPersona = elResultado.getInt(2);
                consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_PERSONA.toString());
                consulta.setInt(1, idPersona);
                
                ResultSet subResultado = consulta.executeQuery();
                while (subResultado.next()) {
                    Map<String,String> gestionantePersona = new HashMap<>();
                    gestionantePersona.put("idPersona",String.valueOf(subResultado.getInt(1)));
                    gestionantePersona.put("cedula",subResultado.getString(2));
                    gestionantePersona.put("primerNombre",subResultado.getString(3));
                    gestionantePersona.put("segundoNombre",subResultado.getString(4));
                    gestionantePersona.put("primerApellido",subResultado.getString(5));
                    gestionantePersona.put("segundoApellido",subResultado.getString(6));
                    uploadData.put("persona"+personaCount, gestionantePersona);   
                    personaCount++;
                }
            }
            
            //SAVE gestionantexpersonajuridica
            int idPersonaJuridica;
            if(elResultado.getInt(3) != 0){
                idPersonaJuridica = elResultado.getInt(3);
                consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_PERSONA_JURIDICA.toString());
                consulta.setInt(1, idPersonaJuridica);
                
                ResultSet subResultado = consulta.executeQuery();
                while (subResultado.next()) {
                    Map<String,String> gestionantePersonaJuridica = new HashMap<>();
                    gestionantePersonaJuridica.put("idPersonaJuridica",String.valueOf(subResultado.getInt(1)));
                    gestionantePersonaJuridica.put("cedulaJuridica",subResultado.getString(2));
                    gestionantePersonaJuridica.put("nombreRazonSocial",subResultado.getString(3));
                    uploadData.put("personajuridica"+personaJuridicaCount, gestionantePersonaJuridica);
                    personaJuridicaCount++;
                }
                
            }
            
            
            //SAVE gestionado
            consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_GESTIONADO.toString());
            consulta.setInt(1, idComparecencia);
            
            elResultado = consulta.executeQuery();
            int idGestionado = 0;
            while(elResultado.next()){
                //GET idGestionado
                idGestionado = elResultado.getInt(1);
                Map<String,String> elGestionado = new HashMap<>();
                elGestionado.put("idGestionado",String.valueOf(elResultado.getInt(1)));
                elGestionado.put("idPersona",String.valueOf(elResultado.getInt(2)));
                elGestionado.put("idPersonaJuridica",String.valueOf(elResultado.getInt(3)));
                elGestionado.put("idComparecencia",String.valueOf(elResultado.getInt(4)));
                uploadData.put("gestionado", elGestionado);
            }
            
            
            

            
            //FIND persona|juridicaXgestionado
            consulta = conexionSQLite.sqliteConexion.prepareStatement(
            """
            SELECT idGestionado,idPersona,idPersonaJuridica FROM gestionado
            WHERE gestionado.idComparecencia = ?
            """
            );
            consulta.setInt(1, idComparecencia);
            elResultado = consulta.executeQuery();
            
            //SAVE gestionadoxpersona
            idPersona = 0;
            if(elResultado.getInt(2) != 0){
                //personaCount++;
                idPersona = elResultado.getInt(2);
                consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_PERSONA.toString());
                consulta.setInt(1, idPersona);
                
                ResultSet subResultado = consulta.executeQuery();
                while (subResultado.next()) {
                    Map<String,String> gestionadoPersona = new HashMap<>();
                    gestionadoPersona.put("idPersona",String.valueOf(subResultado.getInt(1)));
                    gestionadoPersona.put("cedula",subResultado.getString(2));
                    gestionadoPersona.put("primerNombre",subResultado.getString(3));
                    gestionadoPersona.put("segundoNombre",subResultado.getString(4));
                    gestionadoPersona.put("primerApellido",subResultado.getString(5));
                    gestionadoPersona.put("segundoApellido",subResultado.getString(6));
                    uploadData.put("persona"+personaCount, gestionadoPersona);
                    personaCount++;
                }
            }
            
            //SAVE gestionadoxpersonajuridica
            idPersonaJuridica = 0;
            if(elResultado.getInt(3) != 0){
                //personaJuridicaCount++;
                idPersonaJuridica = elResultado.getInt(3);
                consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_PERSONA_JURIDICA.toString());
                consulta.setInt(1, idPersonaJuridica);
                
                ResultSet subResultado = consulta.executeQuery();
                while (subResultado.next()) {
                    Map<String,String> gestionadoPersonaJuridica = new HashMap<>();
                    gestionadoPersonaJuridica.put("idPersonaJuridica",String.valueOf(subResultado.getInt(1)));
                    gestionadoPersonaJuridica.put("cedulaJuridica",subResultado.getString(2));
                    gestionadoPersonaJuridica.put("nombreRazonSocial",subResultado.getString(3));
                    uploadData.put("personajuridica"+personaJuridicaCount, gestionadoPersonaJuridica);  
                    personaJuridicaCount++;
                }
            }
            
            
            
            
            
            
            //ACOMPAÑANTES
            //GESTIONANTE
            
            //SAVE testigoxgestionante
            consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_TESTIGO_GESTIONANTE.toString());
            consulta.setInt(1, idGestionante);
            elResultado = consulta.executeQuery();
            
            while(elResultado.next()){
                Map<String,String> elTestigo = new HashMap<>();
                elTestigo.put("idPersona",String.valueOf(elResultado.getInt(1)));
                elTestigo.put("idGestionante",String.valueOf(elResultado.getInt(2)));
                elTestigo.put("idGestionado",String.valueOf(elResultado.getInt(3)));
                uploadData.put("testigo"+testigoCount, elTestigo);
                testigoCount++;
            }

            
            //FIND testigoxpersona [MULTI]
            consulta = conexionSQLite.sqliteConexion.prepareStatement(
            """
                    SELECT idPersona FROM testigo  
                    WHERE testigo.idGestionante = ?
            """);
            consulta.setInt(1, idGestionante);
            elResultado = consulta.executeQuery();
           
            idPersona = 0;
            while (elResultado.next()) {
                //personaCount++;
                idPersona = elResultado.getInt(1);
                consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_PERSONA.toString());
                consulta.setInt(1, idPersona);
                
                ResultSet subResultado = consulta.executeQuery();
                while (subResultado.next()) {
                    Map<String,String> testigoPersona = new HashMap<>();
                    testigoPersona.put("idPersona",String.valueOf(subResultado.getInt(1)));
                    testigoPersona.put("cedula",subResultado.getString(2));
                    testigoPersona.put("primerNombre",subResultado.getString(3));
                    testigoPersona.put("segundoNombre",subResultado.getString(4));
                    testigoPersona.put("primerApellido",subResultado.getString(5));
                    testigoPersona.put("segundoApellido",subResultado.getString(6));
                    uploadData.put("persona"+personaCount, testigoPersona);   
                    personaCount++;
                }
            }            
        
            
            
            
            
            
            //SAVE acompañantexgestionante
            consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_ACOMPAÑANTE_GESTIONANTE.toString());
            consulta.setInt(1, idGestionante);
            elResultado = consulta.executeQuery();

            while(elResultado.next()){
                Map<String,String> elAcompañante = new HashMap<>();
                elAcompañante.put("idPersona",String.valueOf(elResultado.getInt(1)));
                elAcompañante.put("idGestionante",String.valueOf(elResultado.getInt(2)));
                elAcompañante.put("idGestionado",String.valueOf(elResultado.getInt(3)));
                elAcompañante.put("tipoAcompañante",elResultado.getString(4));
                elAcompañante.put("enCondicionDe",elResultado.getString(5));
                uploadData.put("acompañante"+acompañanteCount, elAcompañante);
                acompañanteCount++;
            }

            
            //FIND acompañantexpersona [MULTI]
            consulta = conexionSQLite.sqliteConexion.prepareStatement(
            """
                    SELECT idPersona FROM acompañante 
                    WHERE acompañante.idGestionante = ?
            """);
            consulta.setInt(1, idGestionante);
            elResultado = consulta.executeQuery();
           
            idPersona = 0;
            while (elResultado.next()) {
                //personaCount++;
                idPersona = elResultado.getInt(1);
                consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_PERSONA.toString());
                consulta.setInt(1, idPersona);
                ResultSet subResultado = consulta.executeQuery();
                while (subResultado.next()) {
                    Map<String,String> acompañantePersona = new HashMap<>();
                    acompañantePersona.put("idPersona",String.valueOf(subResultado.getInt(1)));
                    acompañantePersona.put("cedula",subResultado.getString(2));
                    acompañantePersona.put("primerNombre",subResultado.getString(3));
                    acompañantePersona.put("segundoNombre",subResultado.getString(4));
                    acompañantePersona.put("primerApellido",subResultado.getString(5));
                    acompañantePersona.put("segundoApellido",subResultado.getString(6));
                    uploadData.put("persona"+personaCount, acompañantePersona);   
                    personaCount++;
                }
            }
            
            
            
            
            
            //SAVE representantexgestionante
            consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_REPRESENTANTE_GESTIONANTE.toString());
            consulta.setInt(1, idGestionante);
            elResultado = consulta.executeQuery();

            while(elResultado.next()){
                Map<String,String> elRepresentante = new HashMap<>();
                elRepresentante.put("idPersona",String.valueOf(elResultado.getInt(1)));
                elRepresentante.put("idGestionante",String.valueOf(elResultado.getInt(2)));
                elRepresentante.put("idGestionado",String.valueOf(elResultado.getInt(3)));
                uploadData.put("representante"+representanteCount, elRepresentante);
                representanteCount++;
            }
            
            //FIND representantexpersona [MULTI]
            consulta = conexionSQLite.sqliteConexion.prepareStatement(
            """
            SELECT idPersona FROM representante
            WHERE representante.idGestionante = ?
            """);
            consulta.setInt(1, idGestionante);
            elResultado = consulta.executeQuery();
           
            idPersona = 0;
            while (elResultado.next()) {
                //personaCount++;
                idPersona = elResultado.getInt(1);
                consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_PERSONA.toString());
                consulta.setInt(1, idPersona);
                ResultSet subResultado = consulta.executeQuery();
                while (subResultado.next()) {
                    Map<String,String> representantePersona = new HashMap<>();
                    representantePersona.put("idPersona",String.valueOf(subResultado.getInt(1)));
                    representantePersona.put("cedula",subResultado.getString(2));
                    representantePersona.put("primerNombre",subResultado.getString(3));
                    representantePersona.put("segundoNombre",subResultado.getString(4));
                    representantePersona.put("primerApellido",subResultado.getString(5));
                    representantePersona.put("segundoApellido",subResultado.getString(6));
                    uploadData.put("persona"+personaCount, representantePersona);   
                    personaCount++;
                }
            }
                        
            
            
            
            
            
            //ACOMPAÑANTES
            //GESTIONADO
            
            //SAVE testigoxgestionado
            consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_TESTIGO_GESTIONADO.toString());
            consulta.setInt(1, idGestionado);
            elResultado = consulta.executeQuery();

            while (elResultado.next()) {
                Map<String,String> elTestigo = new HashMap<>();
                elTestigo.put("idPersona",String.valueOf(elResultado.getInt(1)));
                elTestigo.put("idGestionante",String.valueOf(elResultado.getInt(2)));
                elTestigo.put("idGestionado",String.valueOf(elResultado.getInt(3)));
                uploadData.put("testigo"+testigoCount, elTestigo);
                testigoCount++;
            }
            


            
            //FIND testigoxpersona [MULTI]
            consulta = conexionSQLite.sqliteConexion.prepareStatement(
            """
                    SELECT idPersona FROM testigo  
                    WHERE testigo.idGestionado = ?
            """);
            consulta.setInt(1, idGestionado);
            elResultado = consulta.executeQuery();
           
            idPersona = 0;
            while (elResultado.next()) {
                //personaCount++;
                idPersona = elResultado.getInt(1);
                consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_PERSONA.toString());
                consulta.setInt(1, idPersona);
                ResultSet subResultado = consulta.executeQuery();
                while (subResultado.next()) {
                    Map<String,String> testigoPersona = new HashMap<>();
                    testigoPersona.put("idPersona",String.valueOf(subResultado.getInt(1)));
                    testigoPersona.put("cedula",subResultado.getString(2));
                    testigoPersona.put("primerNombre",subResultado.getString(3));
                    testigoPersona.put("segundoNombre",subResultado.getString(4));
                    testigoPersona.put("primerApellido",subResultado.getString(5));
                    testigoPersona.put("segundoApellido",subResultado.getString(6));
                    uploadData.put("persona"+personaCount, testigoPersona);   
                    personaCount++;
                }

            }            
        
            
            
            
            
            
            //SAVE acompañantexgestionado
            consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_ACOMPAÑANTE_GESTIONADO.toString());
            consulta.setInt(1, idGestionado);
            elResultado = consulta.executeQuery();

            while(elResultado.next()){
                Map<String,String> elAcompañante = new HashMap<>();
                elAcompañante.put("idPersona",String.valueOf(elResultado.getInt(1)));
                elAcompañante.put("idGestionante",String.valueOf(elResultado.getInt(2)));
                elAcompañante.put("idGestionado",String.valueOf(elResultado.getInt(3)));
                elAcompañante.put("tipoAcompañante",elResultado.getString(4));
                elAcompañante.put("enCondicionDe",elResultado.getString(5));
                uploadData.put("acompañante"+acompañanteCount, elAcompañante);
                acompañanteCount++;
            }

            
            //FIND acompañantexpersona [MULTI]
            consulta = conexionSQLite.sqliteConexion.prepareStatement(
            """
                    SELECT idPersona FROM acompañante 
                    WHERE acompañante.idGestionado = ?
            """);
            consulta.setInt(1, idGestionado);
            elResultado = consulta.executeQuery();
           
            idPersona = 0;
            while (elResultado.next()) {
                //personaCount++;
                idPersona = elResultado.getInt(1);
                consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_PERSONA.toString());
                consulta.setInt(1, idPersona);
                ResultSet subResultado = consulta.executeQuery();
                while (subResultado.next()) {
                    Map<String,String> acompañantePersona = new HashMap<>();
                    acompañantePersona.put("idPersona",String.valueOf(subResultado.getInt(1)));
                    acompañantePersona.put("cedula",subResultado.getString(2));
                    acompañantePersona.put("primerNombre",subResultado.getString(3));
                    acompañantePersona.put("segundoNombre",subResultado.getString(4));
                    acompañantePersona.put("primerApellido",subResultado.getString(5));
                    acompañantePersona.put("segundoApellido",subResultado.getString(6));
                    uploadData.put("persona"+personaCount, acompañantePersona);   
                    personaCount++;
                }
            }
            
            
            
            
            
            //SAVE representantexgestionado
            consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_REPRESENTANTE_GESTIONADO.toString());
            consulta.setInt(1, idGestionante);
            elResultado = consulta.executeQuery();

            while(elResultado.next()){
                Map<String,String> elRepresentante = new HashMap<>();
                elRepresentante.put("idPersona",String.valueOf(elResultado.getInt(1)));
                elRepresentante.put("idGestionante",String.valueOf(elResultado.getInt(2)));
                elRepresentante.put("idGestionado",String.valueOf(elResultado.getInt(3)));
                uploadData.put("representante"+representanteCount, elRepresentante);
                representanteCount++;
            }

            
            
            //FIND representantexpersona [MULTI]
            consulta = conexionSQLite.sqliteConexion.prepareStatement(
            """
            SELECT idPersona FROM representante
            WHERE representante.idGestionado = ?
            """);
            consulta.setInt(1, idGestionado);
            elResultado = consulta.executeQuery();
           
            idPersona = 0;
            while (elResultado.next()) {
                //personaCount++;
                idPersona = elResultado.getInt(1);
                consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_PERSONA.toString());
                consulta.setInt(1, idPersona);
                ResultSet subResultado = consulta.executeQuery();
                while (subResultado.next()) {
                    Map<String,String> representantePersona = new HashMap<>();
                    representantePersona.put("idPersona",String.valueOf(subResultado.getInt(1)));
                    representantePersona.put("cedula",subResultado.getString(2));
                    representantePersona.put("primerNombre",subResultado.getString(3));
                    representantePersona.put("segundoNombre",subResultado.getString(4));
                    representantePersona.put("primerApellido",subResultado.getString(5));
                    representantePersona.put("segundoApellido",subResultado.getString(6));
                    uploadData.put("persona"+personaCount, representantePersona);   
                    personaCount++;
                }
            }
            
            
            
            
            
            
            //SAVE inspectorxcomparecencia
            consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_INSPECTORXCOMPARECENCIAS.toString());
            consulta.setInt(1, idComparecencia);
            elResultado = consulta.executeQuery();

            while(elResultado.next()){
                Map<String,String> InspectorXComparecencia = new HashMap<>();
                InspectorXComparecencia.put("idPersona",String.valueOf(elResultado.getInt(1)));
                InspectorXComparecencia.put("idComparecencia",String.valueOf(elResultado.getInt(2)));
                uploadData.put("inspectorxcomparecencia"+inspectorXComparecenciaCount, InspectorXComparecencia);
                inspectorXComparecenciaCount++;
            }

            //FIND inspector
            consulta = conexionSQLite.sqliteConexion.prepareStatement(
            """
                    SELECT idPersona FROM inspectorxcomparecencia  
                    WHERE inspectorxcomparecencia.idComparecencia = ?
            """
            );
            consulta.setInt(1, idComparecencia);
            elResultado = consulta.executeQuery();

            idPersona = 0;
            while (elResultado.next()) {
                idPersona = elResultado.getInt(1);
                consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_INSPECTOR.toString());
                consulta.setInt(1, idPersona);
                ResultSet subResultado = consulta.executeQuery();
                while (subResultado.next()) {
                    Map<String,String> elInspector = new HashMap<>();
                    elInspector.put("idPersona",String.valueOf(subResultado.getInt(1)));
                    elInspector.put("idPuesto",String.valueOf(subResultado.getInt(2)));
                    elInspector.put("idRegion",String.valueOf(subResultado.getInt(3)));
                    elInspector.put("idOficina",String.valueOf(subResultado.getInt(4)));
                    uploadData.put("inspector"+inspectorCount, elInspector);
                    inspectorCount++;
                }
                
                consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_USUARIO.toString());
                consulta.setInt(1, idPersona);
                subResultado = consulta.executeQuery();
                while (subResultado.next()) {
                    Map<String,String> elUsuario = new HashMap<>();
                    elUsuario.put("idPersona",String.valueOf(subResultado.getInt(1)));
                    elUsuario.put("email",subResultado.getString(2));
                    elUsuario.put("contraseña",subResultado.getString(3));
                    elUsuario.put("enLinea",String.valueOf(subResultado.getBoolean(4)));
                    uploadData.put("usuario"+usuarioCount, elUsuario);
                    usuarioCount++;
                }
                
                consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.UPLOAD_PERSONA.toString());
                consulta.setInt(1, idPersona);
                subResultado = consulta.executeQuery();
                while (subResultado.next()) {
                    //personaCount++;
                    Map<String,String> inspectorPersona = new HashMap<>();
                    inspectorPersona.put("idPersona",String.valueOf(subResultado.getInt(1)));
                    inspectorPersona.put("cedula",subResultado.getString(2));
                    inspectorPersona.put("primerNombre",subResultado.getString(3));
                    inspectorPersona.put("segundoNombre",subResultado.getString(4));
                    inspectorPersona.put("primerApellido",subResultado.getString(5));
                    inspectorPersona.put("segundoApellido",subResultado.getString(6));
                    uploadData.put("persona"+personaCount, inspectorPersona); 
                    personaCount++;
                }
            }
            
            return uploadData;
        }catch(Exception e){
            telemetria.logActivity("Error al extraer los datos de la comparecencia.");
            telemetria.logException(e);
        }finally{
            closeAll();
        }


        return null;

    }

    
    //DESCARGAR
    private void descargarLaComparecencia(Map theData){
        try {
            //FASE 0: SE OBTIENEN LOS DATOS
            this.uploadData =  theData;
            
            //FASE 1: ABRIR BASE DE DATOS
            conexionSQLite.abrirConexion();

            //FASE 2: COMENZAR PROCESO
            downloadPersona();
            downloadPersonaJuridica();
            downloadUsuario();
            downloadInspector();
            downloadComparecencia();
            downloadAudio();
            downloadInspectorXComparecencia();
            downloadAudioXComparecencia();
            downloadGestionante();
            downloadGestionado();
            downloadTestigo();
            downloadAcompañante();
            downloadRepresentante();
        } catch (Exception e) {
            telemetria.logActivity("Error al importar los datos de la comparecencia.");
            telemetria.logActivity(e.getMessage());
            telemetria.logException(e);    
        } finally {
            closeAll();
        }
    }
    
    private void downloadPersona() {

        int oldID = 0;
        int newID = 0;
        try {
            //se itera atraves de la lista de listas
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                //si se encuentra una persona
                if (mapTable.getKey().matches("(persona)(?!juridica)[0-9]+")) {

                    //para tablas autoincrement se obtiene el nuevo ID de antemano
                    consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.ID_LAST_ROW_PERSONA.toString());
                    resultado = consulta.executeQuery();
                    while (resultado.next()) {
                        newID = resultado.getInt(1)+1;
                    }
                    
                    
                    consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.INSERT_PERSONA.toString());
                    Map<String, String> theTable = mapTable.getValue();
                    
                    //se itera sobre la informacion de la persona
                    for (Map.Entry<String,String> mapColumns : theTable.entrySet()){
                    
                        switch(mapColumns.getKey()){
                        
                            case "idPersona" -> {
                                oldID = Integer.parseInt(mapColumns.getValue());
                                consulta.setInt(1, newID);
                            }
                            case "cedula" -> {
                                consulta.setString(2, mapColumns.getValue());
                            }
                            case "primerNombre" -> {
                                consulta.setString(3, mapColumns.getValue());
                            }
                            case "segundoNombre" -> {
                                consulta.setString(4, mapColumns.getValue());
                            }
                            case "primerApellido" -> {
                                consulta.setString(5, mapColumns.getValue());
                            }
                            case "segundoApellido" -> {
                                consulta.setString(6, mapColumns.getValue());
                            }

                        }
                    }
                    
                    //al tener la consulta armada, se ejecuta el insert
                    consulta.executeUpdate();

                    //se actualizan las llaves foreneas dependientes
                    updatePersonaDependencies(oldID, newID);
                }
            }
            //se remueve la informacion para acelerar el proceso
            uploadData.entrySet().removeIf(entries->entries.getKey().matches("(persona)(?!juridica)[0-9]+"));
        } catch (Exception e) {
            telemetria.logActivity("Error al importar los datos de una persona.");
            telemetria.logException(e);
        }
    }
    
    private void updatePersonaDependencies(int oldID, int newID){
        try{    
            for (Map.Entry<String, Map<String, String>> map : uploadData.entrySet()) {

                if (map.getKey().matches("(usuario)[0-9]+")) {

                    Map<String, String> theMap = map.getValue();
                    //se conoce el resultado sin iterar
                    theMap.replace("idPersona", String.valueOf(oldID), String.valueOf(newID));
                    continue;
                }

                else if (map.getKey().matches("(inspector)(?!xcomparecencia)[0-9]+")) {

                    Map<String, String> theMap = map.getValue();
                    theMap.replace("idPersona", String.valueOf(oldID), String.valueOf(newID));
                    continue;
                }

                else if (map.getKey().matches("(inspectorxcomparecencia)[0-9]+")) {

                    Map<String, String> theMap = map.getValue();
                    theMap.replace("idPersona", String.valueOf(oldID), String.valueOf(newID));
                    continue;
                }

                else if (map.getKey().matches("gestionado")) {

                    Map<String, String> theMap = map.getValue();
                    theMap.replace("idPersona", String.valueOf(oldID), String.valueOf(newID));
                    continue;
                }

                else if (map.getKey().matches("gestionante")) {

                    Map<String, String> theMap = map.getValue();
                    theMap.replace("idPersona", String.valueOf(oldID), String.valueOf(newID));
                    continue;
                }

                else if (map.getKey().matches("(testigo)[0-9]+")) {

                    Map<String, String> theMap = map.getValue();
                    theMap.replace("idPersona", String.valueOf(oldID), String.valueOf(newID));
                    continue;
                }

                else if (map.getKey().matches("(acompañante)[0-9]+")) {

                    Map<String, String> theMap = map.getValue();
                    theMap.replace("idPersona", String.valueOf(oldID), String.valueOf(newID));
                    continue;
                }

                else if (map.getKey().matches("(representante)[0-9]+")) {

                    Map<String, String> theMap = map.getValue();
                    theMap.replace("idPersona", String.valueOf(oldID), String.valueOf(newID));
                    continue;
                }
            }
        }catch(Exception e){
            telemetria.logActivity("Error al actualizar los datos de persona durante la descarga.");
            telemetria.logException(e); 
        }

        
    }
    
    private void downloadPersonaJuridica(){
        int oldID = 0;
        int newID = 0;
        try {
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                if (mapTable.getKey().matches("(personajuridica)[0-9]+")) {
                    
                    consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.ID_LAST_ROW_PERSONAJURIDICA.toString());
                    resultado = consulta.executeQuery();
                    while (resultado.next()) {
                        newID = resultado.getInt(1)+1;
                    }
                    
                    consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.INSERT_PERSONAJURIDICA.toString());
                    Map<String, String> theTable = mapTable.getValue();
                    
                    for (Map.Entry<String,String> mapColumns : theTable.entrySet()){
                    
                        switch(mapColumns.getKey()){
                        
                            case "idPersonaJuridica" -> {
                                oldID = Integer.parseInt(mapColumns.getValue());
                                consulta.setInt(1, newID);
                            }
                            case "cedulaJuridica" -> {
                                consulta.setString(2, mapColumns.getValue());
                            }
                            case "nombreRazonSocial" -> {
                                consulta.setString(3, mapColumns.getValue());
                            }
                        }
                    }
                    
                    consulta.executeUpdate();
                    updatePersonaJuridicaDependencies(oldID, newID);
                }
            }
            uploadData.entrySet().removeIf(entries->entries.getKey().matches("(personajuridica)[0-9]+"));
        } catch (Exception e) {
            telemetria.logActivity("Error al importar los datos de una persona juridica.");
            telemetria.logActivity(e.getMessage());
        }
    }
    
    private void updatePersonaJuridicaDependencies(int oldID, int newID){
        try{    
            for (Map.Entry<String, Map<String, String>> map : uploadData.entrySet()) {

                if (map.getKey().matches("gestionante")) {

                    Map<String, String> theMap = map.getValue();
                    theMap.replace("idPersonaJuridica", String.valueOf(oldID), String.valueOf(newID));
                    continue;
                }

                else if (map.getKey().matches("gestionado")) {

                    Map<String, String> theMap = map.getValue();
                    theMap.replace("idPersonaJuridica", String.valueOf(oldID), String.valueOf(newID));
                    continue;
                }
            }
        }catch(Exception e){
            telemetria.logActivity("Error al actualizar los datos de persona juridica durante la descarga.");
            telemetria.logException(e); 
        }
    }

    private void downloadUsuario(){
        try{
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                if (mapTable.getKey().matches("(usuario)[0-9]+")) {

                    consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.INSERT_USUARIO.toString());
                    Map<String, String> theTable = mapTable.getValue();
                    
                    for (Map.Entry<String,String> mapColumns : theTable.entrySet()){
                    
                        switch(mapColumns.getKey()){
                        
                            case "idPersona" -> {
                                consulta.setInt(1, Integer.parseInt(mapColumns.getValue()));
                            }
                            case "email" -> {
                                consulta.setString(2, mapColumns.getValue());
                            }
                            case "contraseña" -> {
                                consulta.setString(3, mapColumns.getValue());
                            }
                            case "enLinea" -> {
                                consulta.setBoolean(4, Boolean.parseBoolean(mapColumns.getValue()));
                            }
                        }
                    }
                    
                    consulta.executeUpdate();

                }
            }
            uploadData.entrySet().removeIf(entries->entries.getKey().matches("(usuario)[0-9]+"));
        } catch (Exception e) {
            telemetria.logActivity("Error al importar los datos de usuario.");
            telemetria.logException(e);
        }
    }
    
    private void downloadInspector(){
        try {
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                if (mapTable.getKey().matches("(inspector)(?!xcomparecencia)[0-9]+")) {

                    consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.INSERT_INSPECTOR.toString());
                    Map<String, String> theTable = mapTable.getValue();
                    
                    for (Map.Entry<String,String> mapColumns : theTable.entrySet()){
                    
                        switch(mapColumns.getKey()){
                        
                            case "idPersona" -> {
                                consulta.setInt(1, Integer.parseInt(mapColumns.getValue()));
                            }
                            case "idPuesto" -> {
                                consulta.setInt(2, Integer.parseInt(mapColumns.getValue()));
                            }
                            case "idRegion" -> {
                                consulta.setInt(3, Integer.parseInt(mapColumns.getValue()));
                            }
                            case "idOficina" -> {
                                consulta.setInt(4, Integer.parseInt(mapColumns.getValue()));
                            }
                        }
                    }
                    
                    consulta.executeUpdate();

                }
            }
            uploadData.entrySet().removeIf(entries->entries.getKey().matches("(inspector)(?!xcomparecencia)[0-9]+"));
        } catch (Exception e) {
            telemetria.logActivity("Error al importar los datos de inspector");
            telemetria.logException(e);
        } 
    }
    
    private void downloadComparecencia(){
        int oldID = 0;
        int newID = 0;
        try{
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                if (mapTable.getKey().matches("comparecencia")) {

                    consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.ID_LAST_ROW_COMPARECENCIA.toString());
                    resultado = consulta.executeQuery();
                    while (resultado.next()) {
                        newID = resultado.getInt(1)+1;
                    }
                    
                    consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.INSERT_COMPARECENCIA.toString());
                    Map<String, String> theTable = mapTable.getValue();
                    
                    //se itera sobre la informacion de la persona
                    for (Map.Entry<String,String> mapColumns : theTable.entrySet()){
                    
                        switch(mapColumns.getKey()){
                        
                            case "idComparecencia" -> {
                                oldID = Integer.parseInt(mapColumns.getValue());
                                consulta.setInt(1, newID);
                            }
                            case "codigoSILAC" -> {
                                consulta.setString(2, mapColumns.getValue());
                            }
                            case "idCaso" -> {
                                consulta.setString(3, mapColumns.getValue());
                            }
                            case "ubicacion" -> {
                                consulta.setString(4, mapColumns.getValue());
                            }
                            case "linkExpediente" -> {
                                consulta.setString(5, mapColumns.getValue());
                            }
                            case "fecha" -> {
                                consulta.setString(6, mapColumns.getValue());
                            }
                        }
                        
                    }
                    
                    consulta.executeUpdate();
                    updateComparecenciaDependencies(oldID, newID);
                }
            }
            uploadData.entrySet().removeIf(entries->entries.getKey().matches("comparecencia"));
        } catch (Exception e) {
            telemetria.logActivity("Error al importar los datos de comparecencia durante la descarga.");
            telemetria.logException(e);
        }
    }

    private void updateComparecenciaDependencies(int oldID, int newID){        
        try{    
            for (Map.Entry<String, Map<String, String>> map : uploadData.entrySet()) {

                if (map.getKey().matches("gestionante")) {

                    Map<String, String> theMap = map.getValue();
                    theMap.replace("idComparecencia", String.valueOf(oldID), String.valueOf(newID));
                    continue;
                }

                else if (map.getKey().matches("gestionado")) {

                    Map<String, String> theMap = map.getValue();
                    theMap.replace("idComparecencia", String.valueOf(oldID), String.valueOf(newID));
                    continue;
                }

                else if (map.getKey().matches("(inspectorxcomparecencia)[0-9]+")) {

                    Map<String, String> theMap = map.getValue();
                    theMap.replace("idComparecencia", String.valueOf(oldID), String.valueOf(newID));
                    continue;
                }

                else if (map.getKey().matches("(audioxcomparecencia)[0-9]+")) {

                    Map<String, String> theMap = map.getValue();
                    theMap.replace("idComparecencia", String.valueOf(oldID), String.valueOf(newID));
                    continue;
                }
            }
        }catch(Exception e){
            telemetria.logActivity("Error al actualizar las dependencias de la comparecencia.");
            telemetria.logException(e); 
        }
    }

    private void downloadAudio(){
        int oldID = 0;
        int newID = 0;
        try{
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                if (mapTable.getKey().matches("(audio)[0-9]+")) {
                        
                    consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.ID_LAST_ROW_AUDIO.toString());
                    resultado = consulta.executeQuery();
                    while (resultado.next()) {
                        newID = resultado.getInt(1)+1;
                    }
                    
                    consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.INSERT_AUDIO.toString());
                    Map<String, String> theTable = mapTable.getValue();
                    
                    for (Map.Entry<String,String> mapColumns : theTable.entrySet()){

                        //se actualizan los path
                        if (mapColumns.getKey().equals("pathArchivoAudio")){
                            
                            String newPath = methods.Global.appConfig.getProperty("comparecencias_path");
                            String oldPath =  mapColumns.getValue();
                            String fileName = oldPath.replaceAll(".*(?=.{33})", "");
                            String newFullPath = newPath+fileName;
                            mapColumns.setValue(newFullPath);
                            
                        }
                        else if(mapColumns.getKey().equals("pathArchivoAnotaciones")) {
                            
                            String newPath = methods.Global.appConfig.getProperty("comparecencias_path");
                            String oldPath =  mapColumns.getValue();
                            String fileName = oldPath.replaceAll(".*(?=.{34})", "");
                            String newFullPath = newPath+fileName;
                            mapColumns.setValue(newFullPath);
                            
                        }
                        
                        
                        switch(mapColumns.getKey()){
                        
                            case "idAudio" -> {
                                oldID = Integer.parseInt(mapColumns.getValue());
                                consulta.setInt(1, newID);
                            }
                            case "nombreAudio" -> {
                                consulta.setString(2, mapColumns.getValue());
                            }
                            case "duracionAudio" -> {
                                consulta.setString(3, mapColumns.getValue());
                            }
                            case "pathArchivoAudio" -> {
                                consulta.setString(4, mapColumns.getValue());
                            }
                            case "pathArchivoAnotaciones" -> {
                                consulta.setString(5, mapColumns.getValue());
                            }
                        }
                        
                    }
                    
                    consulta.executeUpdate();
                    updateAudioDependencies(oldID, newID);
                }
            }
            uploadData.entrySet().removeIf(entries->entries.getKey().matches("(audio)[0-9]+"));
        } catch (Exception e) {
            telemetria.logActivity("Error al importar datos del audio durante la descarga.");
            telemetria.logException(e);
        }       
    }
    
    private void updateAudioDependencies(int oldID, int newID){
        try{    
            for (Map.Entry<String, Map<String, String>> map : uploadData.entrySet()) {

                if (map.getKey().matches("(audioxcomparecencia)[0-9]+")) {

                    Map<String, String> theMap = map.getValue();
                    theMap.replace("idAudio", String.valueOf(oldID), String.valueOf(newID));
                    continue;
                }
                
            }
        }catch(Exception e){
            telemetria.logActivity("Error al actualizar los datos del audio durante la descarga.");
            telemetria.logException(e); 
        }
    }
        
    private void downloadInspectorXComparecencia(){
        try {
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                if (mapTable.getKey().matches("(inspectorxcomparecencia)[0-9]+")) {

                    consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.INSERT_INSPECTORXCOMPARECENCIA.toString());
                    Map<String, String> theTable = mapTable.getValue();
                    
                    for (Map.Entry<String,String> mapColumns : theTable.entrySet()){
                    
                        switch(mapColumns.getKey()){
                        
                            case "idPersona" -> {
                                consulta.setInt(1, Integer.parseInt(mapColumns.getValue()));
                            }
                            case "idComparecencia" -> {
                                consulta.setInt(2, Integer.parseInt(mapColumns.getValue()));
                            }
                        }
                    }
                    
                    consulta.executeUpdate();

                }
            }
            uploadData.entrySet().removeIf(entries->entries.getKey().matches("(inspectorxcomparecencia)[0-9]+"));
        } catch (Exception e) {
            telemetria.logActivity("Error al importar la relacion inspector comparencia.");
            telemetria.logException(e);
        } 
    }
    
    private void downloadAudioXComparecencia(){
        try {
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                if (mapTable.getKey().matches("(audioxcomparecencia)[0-9]+")) {

                    consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.INSERT_AUDIOXCOMPARECENCIA.toString());
                    Map<String, String> theTable = mapTable.getValue();
                    
                    for (Map.Entry<String,String> mapColumns : theTable.entrySet()){
                    
                        switch(mapColumns.getKey()){
                        
                            case "idAudio" -> {
                                consulta.setInt(1, Integer.parseInt(mapColumns.getValue()));
                            }
                            case "idComparecencia" -> {
                                consulta.setInt(2, Integer.parseInt(mapColumns.getValue()));
                            }
                        }
                    }
                    
                    consulta.executeUpdate();

                }
            }
            uploadData.entrySet().removeIf(entries->entries.getKey().matches("(audioxcomparecencia)[0-9]+"));
        } catch (Exception e) {
            telemetria.logActivity("Error al importar la relacion audio comparecencia.");
            telemetria.logException(e);
        }   
    }
    
    private void downloadGestionante(){
        int oldID = 0;
        int newID = 0;
        
        try {
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                if (mapTable.getKey().matches("(gestionante)")) {

                                        
                    consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.ID_LAST_ROW_GESTIONANTE.toString());
                    resultado = consulta.executeQuery();
                    while (resultado.next()) {
                        newID = resultado.getInt(1)+1;
                    }
                    
                    consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.INSERT_GESTIONANTE.toString());
                    Map<String, String> theTable = mapTable.getValue();
                    
                    for (Map.Entry<String,String> mapColumns : theTable.entrySet()){
                    
                        switch(mapColumns.getKey()){
                        
                            case "idGestionante" -> {
                                oldID = Integer.parseInt(mapColumns.getValue());
                                consulta.setInt(1, newID);
                            }
                            case "idPersona" -> {
                                if(!mapColumns.getValue().equals("0")){
                                consulta.setInt(2, Integer.parseInt(mapColumns.getValue()));
                                }else{
                                    consulta.setNull(2, java.sql.Types.INTEGER);
                                }
                            }
                            case "idPersonaJuridica" -> {
                                if(!mapColumns.getValue().equals("0")){
                                consulta.setInt(3, Integer.parseInt(mapColumns.getValue()));
                                }else{
                                    consulta.setNull(3, java.sql.Types.INTEGER);
                                }
                            }
                            case "idComparecencia" -> {
                                consulta.setInt(4, Integer.parseInt(mapColumns.getValue()));
                            }
                        }
                    }
                    consulta.executeUpdate();


                    updateGestionanteDependencies(oldID, newID);
                }
            }
            uploadData.entrySet().removeIf(entries->entries.getKey().matches("gestionante"));
        } catch (Exception e) {
            telemetria.logActivity("Error al importar los datos de gestionante");
            telemetria.logException(e);
        } 
    }
    
    private void updateGestionanteDependencies(int oldID, int newID){        
        try{    
            for (Map.Entry<String, Map<String, String>> map : uploadData.entrySet()) {

                if (map.getKey().matches("(testigo)[0-9]+")) {

                    Map<String, String> theMap = map.getValue();
                    theMap.replace("idGestionante", String.valueOf(oldID), String.valueOf(newID));
                    continue;
                }

                else if (map.getKey().matches("(acompañante)[0-9]+")) {

                    Map<String, String> theMap = map.getValue();
                    theMap.replace("idGestionante", String.valueOf(oldID), String.valueOf(newID));
                    continue;
                }

                else if (map.getKey().matches("(representante)[0-9]+")) {

                    Map<String, String> theMap = map.getValue();
                    theMap.replace("idGestionante", String.valueOf(oldID), String.valueOf(newID));
                    continue;
                }
            }
        }catch(Exception e){
            telemetria.logActivity("Error al actualizar las dependencias del gestionante.");
            telemetria.logException(e); 
        }
    }
    
    private void downloadGestionado(){
        int oldID = 0;
        int newID = 0;
        
        try {
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                if (mapTable.getKey().matches("(gestionado)")) {

                    consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.ID_LAST_ROW_GESTIONADO.toString());
                    resultado = consulta.executeQuery();
                    while (resultado.next()) {
                        newID = resultado.getInt(1)+1;
                    }
                    
                    consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.INSERT_GESTIONADO.toString());
                    Map<String, String> theTable = mapTable.getValue();
                    
                    for (Map.Entry<String,String> mapColumns : theTable.entrySet()){
                    
                        switch(mapColumns.getKey()){
                        
                            case "idGestionado" -> {
                                oldID = Integer.parseInt(mapColumns.getValue());
                                consulta.setInt(1, newID);
                            }
                            case "idPersona" -> {
                                if(!mapColumns.getValue().equals("0")){
                                consulta.setInt(2, Integer.parseInt(mapColumns.getValue()));
                                }else{
                                    consulta.setNull(2, java.sql.Types.INTEGER);
                                }
                            }
                            case "idPersonaJuridica" -> {
                                if(!mapColumns.getValue().equals("0")){
                                consulta.setInt(3, Integer.parseInt(mapColumns.getValue()));
                                }else{
                                    consulta.setNull(3, java.sql.Types.INTEGER);
                                }
                            }
                            case "idComparecencia" -> {
                                consulta.setInt(4, Integer.parseInt(mapColumns.getValue()));
                            }
                        }
                    }
                    consulta.executeUpdate();
                    updateGestionadoDependencies(oldID, newID);
                }
            }
            uploadData.entrySet().removeIf(entries->entries.getKey().matches("gestionado"));
        } catch (Exception e) {
            telemetria.logActivity("Error al importar los datos de gestionado");
            telemetria.logException(e);
        }         
    }
    
    private void updateGestionadoDependencies(int oldID, int newID){
        try{    
            for (Map.Entry<String, Map<String, String>> map : uploadData.entrySet()) {

                if (map.getKey().matches("(testigo)[0-9]+")) {

                    Map<String, String> theMap = map.getValue();
                    theMap.replace("idGestionado", String.valueOf(oldID), String.valueOf(newID));
                    continue;
                }

                else if (map.getKey().matches("(acompañante)[0-9]+")) {

                    Map<String, String> theMap = map.getValue();
                    theMap.replace("idGestionado", String.valueOf(oldID), String.valueOf(newID));
                    continue;
                }

                else if (map.getKey().matches("(representante)[0-9]+")) {

                    Map<String, String> theMap = map.getValue();
                    theMap.replace("idGestionado", String.valueOf(oldID), String.valueOf(newID));
                    continue;
                }
            }
        }catch(Exception e){
            telemetria.logActivity("Error al actualizar las dependencias del gestionado.");
            telemetria.logException(e); 
        }
    }

    private void downloadTestigo(){        
        try {
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                if (mapTable.getKey().matches("(testigo)[0-9]+")) {

                    consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.INSERT_TESTIGO.toString());
                    Map<String, String> theTable = mapTable.getValue();
                    
                    for (Map.Entry<String,String> mapColumns : theTable.entrySet()){
                    
                        switch(mapColumns.getKey()){
                        
                            case "idPersona" -> {
                                consulta.setInt(1, Integer.parseInt(mapColumns.getValue()));
                            }
                            case "idGestionante" -> {
                                if(!mapColumns.getValue().equals("0")){
                                consulta.setInt(2, Integer.parseInt(mapColumns.getValue()));
                                }else{
                                    consulta.setNull(2, java.sql.Types.INTEGER);
                                }
                            }
                            case "idGestionado" -> {
                                if(!mapColumns.getValue().equals("0")){
                                consulta.setInt(3, Integer.parseInt(mapColumns.getValue()));
                                }else{
                                    consulta.setNull(3, java.sql.Types.INTEGER);
                                }
                            }
                        }
                    }
                    consulta.executeUpdate();
                }
            }
            uploadData.entrySet().removeIf(entries->entries.getKey().matches("(testigo)[0-9]+"));
        } catch (Exception e) {
            telemetria.logActivity("Error al importar los datos del testigo");
            telemetria.logException(e);
        } 
    }

    private void downloadAcompañante(){
        try {
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                if (mapTable.getKey().matches("(acompañante)[0-9]+")) {

                    consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.INSERT_ACOMPAÑANTE.toString());
                    Map<String, String> theTable = mapTable.getValue();
                    
                    for (Map.Entry<String,String> mapColumns : theTable.entrySet()){
                    
                        switch(mapColumns.getKey()){
                        
                            case "idPersona" -> {
                                consulta.setInt(1, Integer.parseInt(mapColumns.getValue()));
                            }
                            case "idGestionante" -> {
                                if(!mapColumns.getValue().equals("0")){
                                consulta.setInt(2, Integer.parseInt(mapColumns.getValue()));
                                }else{
                                    consulta.setNull(2, java.sql.Types.INTEGER);
                                }
                            }
                            case "idGestionado" -> {
                                if(!mapColumns.getValue().equals("0")){
                                consulta.setInt(3, Integer.parseInt(mapColumns.getValue()));
                                }else{
                                    consulta.setNull(3, java.sql.Types.INTEGER);
                                }
                            }
                            case "tipoAcompañante" -> {
                                consulta.setString(4, mapColumns.getValue());
                            }
                            case "enCondicionDe" -> {
                                consulta.setString(5, mapColumns.getValue());
                            }
                        }
                    }
                    consulta.executeUpdate();
                }
            }
            uploadData.entrySet().removeIf(entries->entries.getKey().matches("(acompañante)[0-9]+"));
        } catch (Exception e) {
            telemetria.logActivity("Error al importar los datos del acompañante");
            telemetria.logException(e);
        }  
    }

    private void downloadRepresentante(){
        try {
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                if (mapTable.getKey().matches("(representante)[0-9]+")) {

                    consulta = conexionSQLite.sqliteConexion.prepareStatement(SQL.INSERT_REPRESENTANTE.toString());
                    Map<String, String> theTable = mapTable.getValue();
                    
                    for (Map.Entry<String,String> mapColumns : theTable.entrySet()){
                    
                        switch(mapColumns.getKey()){
                        
                            case "idPersona" -> {
                                consulta.setInt(1, Integer.parseInt(mapColumns.getValue()));
                            }
                            case "idGestionante" -> {
                                if(!mapColumns.getValue().equals("0")){
                                consulta.setInt(2, Integer.parseInt(mapColumns.getValue()));
                                }else{
                                    consulta.setNull(2, java.sql.Types.INTEGER);
                                }
                            }
                            case "idGestionado" -> {
                                if(!mapColumns.getValue().equals("0")){
                                consulta.setInt(3, Integer.parseInt(mapColumns.getValue()));
                                }else{
                                    consulta.setNull(3, java.sql.Types.INTEGER);
                                }
                            }
                        }
                    }
                    consulta.executeUpdate();
                }
            }
            uploadData.entrySet().removeIf(entries->entries.getKey().matches("(representante)[0-9]+"));
        } catch (Exception e) {
            telemetria.logActivity("Error al importar los datos del representante");
            telemetria.logException(e);
        }  
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