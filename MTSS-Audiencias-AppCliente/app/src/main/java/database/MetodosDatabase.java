package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import javax.swing.table.DefaultTableModel;
import methods.Global;
import methods.Telemetria;
import models.*;

public class MetodosDatabase{
    
    private Telemetria telemetria;
    private ConexionDatabase conexion;
    private PreparedStatement consulta;
    private ResultSet resultado;
    private int elId;

    private DateFormat dateFormater;
    private String pathDATADB;
    private Map<String, List<String>> uploadData = new HashMap<>();

    
    public MetodosDatabase() {
        initVariables();
    }
    
    private void initVariables(){
        telemetria = new Telemetria();
        conexion = new ConexionDatabase();
        //persona = new Persona(); NO INSTANCIAR AQUI, CICLO INFINTO
        //personaJuridica = new PersonaJuridica();;
        //usuario = new Usuario(); NO INSTANCIAR AQUI, CICLO INFINTO
        //consulta = ABSTRACTO. 
        //resultado = ABSTRACTO.
        elId = 0;
        dateFormater = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    private void closeAll() {
        try {
            if(conexion.sqliteConexion != null){
                conexion.cerrarConexionSQLITE();
            }
            if(conexion.exportConexion != null){
                conexion.cerrarConexionEXPORT();
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
    
    public Boolean conexionIsValid() {
        conexion.abrirConexion();
        if (conexion.sqliteConexion != null) {
            conexion.cerrarConexionSQLITE();
            return true;
        } else {
            return false;
        }
    }
    
/*******************************************************************************/    

    public Acompañante guardarAcompañante(Acompañante acompañante) throws Exception{
        insertarPersona(acompañante.getPersona());
        acompañante.getPersona().setIdPersona(
                getIdLastPersona()
        );
        insertarAcompañante(acompañante);
        return acompañante;
    }
    
    public Audio guardarAudio(Audio audio) throws Exception{
        if(insertarAudio(audio)){
            audio.setIdAudio(
                    getIdLastAudio());        
        }
        return audio;
    }

    public Boolean guardarAudioXComparecencia(Audio audio, Comparecencia comparecencia) throws Exception{
                
        insertarAudioXComparecencia(
                audio.getIdAudio(),
                comparecencia.getIdComparecencia());
        return true;
    }
    
    public Comparecencia guardarComparecencia(Comparecencia comparecencia) throws Exception{
        insertarComparecencia(comparecencia);
        comparecencia.setIdComparecencia(
                getIdLastComparecencia());
        return comparecencia;
    }

    public Gestionado guardarGestionado(Gestionado gestionado) throws Exception{
        insertarPersona(gestionado.getPersona());

        gestionado.getPersona().setIdPersona(
                getIdLastPersona());

        insertarGestionado(gestionado);
        
        gestionado.setIdGestionado(getIdLastGestionado());
        return gestionado;
    }
    
    public Gestionado guardarGestionadoJuridico(Gestionado gestionado) throws Exception{
        insertarPersonaJuridica(gestionado.getPersonaJuridica());

        gestionado.getPersonaJuridica().setIdPersonaJuridica(
                getIdLastPersonaJuridica());

        insertarGestionado(gestionado);
        gestionado.setIdGestionado(getIdLastGestionado());
        return gestionado;
    }
    
    public Gestionante guardarGestionante(Gestionante gestionante) throws Exception {
        insertarPersona(gestionante.getPersona());

        gestionante.getPersona().setIdPersona(
                getIdLastPersona());

        insertarGestionante(gestionante);
        gestionante.setIdGestionante(getIdLastGestionante());
        return gestionante;
    }

    public Gestionante guardarGestionanteJuridico(Gestionante gestionante) throws Exception {
        insertarPersonaJuridica(gestionante.getPersonaJuridica());

        gestionante.getPersonaJuridica().setIdPersonaJuridica(
                getIdLastPersonaJuridica());

        insertarGestionante(gestionante);
        gestionante.setIdGestionante(getIdLastGestionante());
        return gestionante;
    }

    public Inspector guardarInspector(Inspector inspector) throws Exception {
        insertarPersona(inspector.getPersona());
        
        inspector.getPersona().setIdPersona(
                getIdLastPersona());
        inspector.getUsuario().getPersona().setIdPersona(
                inspector.getPersona().getIdPersona());
        
        insertarUsuario(inspector.getUsuario());

        insertarInspector(inspector);
        return inspector;
    }
    
    public Boolean guardarInspectorxComparecencia(Inspector inspector, Comparecencia comparecencia) throws Exception{
        
        insertarInspectorxComparecencia(
                inspector.getPersona().getIdPersona(),
                comparecencia.getIdComparecencia()
        );
        
        return true;
    }
    
    public Representante guardarRepresentante(Representante representante) throws Exception{
        insertarPersona(representante.getPersona());
        
        representante.getPersona().setIdPersona(
                getIdLastPersona()
        );
        
        insertarRepresentante(representante);
        return representante;
    }

    public Testigo guardarTestigo(Testigo testigo) throws Exception{
        insertarPersona(testigo.getPersona());
        
        testigo.getPersona().setIdPersona(
                getIdLastPersona()
        );
        
        insertarTestigo(testigo);
        return testigo;
    }


    
/*******************************************************************************/    
 
   
    
    public Boolean insertarAcompañante(Acompañante acompañante) throws Exception{
        Callable<Boolean> task = () -> {
            try {

                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.INSERT_ACOMPAÑANTE.toString());

                consulta.setInt(1, acompañante.getPersona().getIdPersona());
                if(acompañante.getGestionado() != null){
                    consulta.setInt(2, acompañante.getGestionado().getIdGestionado());
                }
                else if(acompañante.getGestionante() != null){
                    consulta.setInt(3, acompañante.getGestionante().getIdGestionante());
                }
                consulta.setString(4, acompañante.getTipoAcompañante());
                consulta.setString(5, acompañante.getEnCondicionDe());

                consulta.executeUpdate();
                telemetria.logActivity("Se inserto el acompañante en la base de datos.");
                return true;
            } catch (SQLException e) {
                telemetria.logActivity("Error al insertar acompañante en base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
                return false;
            } finally {
                closeAll();
            }
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    public Boolean insertarAudio(Audio audio) throws Exception{
        Callable<Boolean> task = () -> {
            try {

                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.INSERT_AUDIO.toString());
                
                //consulta.setInt(1, audio.getIdAudio()); (AUTOINCREMENT)
                consulta.setString(2, audio.getNombreAudio());
                consulta.setString(3, audio.getDuracionAudio());
                consulta.setString(4, audio.getPathArchivoAudio());
                consulta.setString(5, audio.getPathArchivoAnotaciones());
                
                consulta.executeUpdate();
                telemetria.logActivity("Se inserto el audio en la base de datos.");
                return true;
            } catch (SQLException e) {
                telemetria.logActivity("Error al insertar el audio en base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
                return false;
            } finally {
                closeAll();
            }
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }

    public Boolean insertarAudioXComparecencia(int IdAudio, int IdComparecencia) throws Exception{
        Callable<Boolean> task = () -> {
            try {

                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.INSERT_AUDIOXCOMPARECENCIA.toString());

                consulta.setInt(1, IdAudio);
                consulta.setInt(2, IdComparecencia);
                
                consulta.executeUpdate();
                telemetria.logActivity("Se inserto la relacion audioXcomparecencia en la base de datos.");
                return true;
            } catch (SQLException e) {
                telemetria.logActivity("Error al insertar la relacion audioXcomparecencia en base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
                return false;
            } finally {
                closeAll();
            }
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    public Boolean insertarTiposCasos() throws Exception{
        Callable<Boolean> task = () -> {
            try {

                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.INSERT_CASO.toString());

                for (TipoCaso elTipoCaso : TipoCaso.values()){
                    consulta.setInt(1, elTipoCaso.ordinal());
                    consulta.setString(2, elTipoCaso.toString());
                    consulta.executeUpdate();
                }
                telemetria.logActivity("Se insertaron tipos de casos en la base de datos.");
                return true;
            } catch (SQLException e) {
                telemetria.logActivity("Error al insertar tipos de casos en base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
                return false;
            } finally {
                closeAll();
            }
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    public Boolean insertarOficinas() throws Exception {
        Callable<Boolean> task = () -> {
            try {

                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.INSERT_OFICINA.toString());

                //para todas las oficinas en el enum oficina
                for (Oficina laOficina : Oficina.values()){
                    //tome el indice de la oficina en el enum
                    consulta.setInt(1, laOficina.ordinal());
                    //tome el valor dentro de ese enum particular
                    consulta.setString(2, laOficina.toString());
                    //guardelos en la base de datos
                    consulta.executeUpdate();
                }

                telemetria.logActivity("Se insertaron oficinas en la base de datos.");
                return true;
            } catch (SQLException e) {
                telemetria.logActivity("Error insertar oficinas en base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
                return false;
            } finally {
                closeAll();
            }
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    public Boolean insertarPuestos() throws Exception {
        Callable<Boolean> task = () -> {
            try {

                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.INSERT_PUESTO.toString());

                for (Puesto elPuesto : Puesto.values()){
                    consulta.setInt(1, elPuesto.ordinal());
                    consulta.setString(2, elPuesto.toString());
                    consulta.executeUpdate();
                }

                telemetria.logActivity("Se insertaron puestos en la base de datos.");
                return true;
            } catch (SQLException e) {
                telemetria.logActivity("Error al insertar puestos en base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
                return false;
            } finally {
                closeAll();
            }
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    public Boolean insertarRegiones() throws Exception {
        Callable<Boolean> task = () -> {
            try {

                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.INSERT_REGION.toString());

                for (Region laRegion : Region.values()){
                    consulta.setInt(1, laRegion.ordinal());
                    consulta.setString(2, laRegion.toString());
                    consulta.executeUpdate();
                }

                telemetria.logActivity("Se insertaron regiones en la base de datos.");
                return true;
            } catch (SQLException e) {
                telemetria.logActivity("Error al insertar regiones en base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
                return false;
            } finally {
                closeAll();
            }
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    public Boolean insertarComparecencia(Comparecencia comparecencia) throws Exception{
        Callable<Boolean> task = () -> {
            try {

                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.INSERT_COMPARECENCIA.toString());

                //consulta.setInt(1, comparecencia.getIdComparecencia()); (AUTOINCREMENTO)
                consulta.setString(2, comparecencia.getCodigoSILAC());
                consulta.setInt(3, comparecencia.getTipoCaso().ordinal());
                consulta.setString(4, comparecencia.getUbicacion());
                consulta.setString(5, comparecencia.getLinkExpediente());
                consulta.setString(6, comparecencia.getFecha());

                consulta.executeUpdate();
                telemetria.logActivity("Se inserto la comparecencia en la base de datos.");
                return true;
            } catch (SQLException e) {
                telemetria.logActivity("Error al insertar la comparecencia en base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
                return false;
            } finally {
                closeAll();
            }
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }

    public Boolean insertarGestionado(Gestionado gestionado) throws Exception{
        Callable<Boolean> task = () -> {
            try {

                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.INSERT_GESTIONADO.toString());

                //consulta.setInt(1, gestionado.getIdGestionante()); (AUTOINCREMENT)
                if(gestionado.getPersona() != null)
                {
                    consulta.setInt(2, gestionado.getPersona().getIdPersona());
                }
                if(gestionado.getPersonaJuridica() != null)
                {
                    consulta.setInt(3, gestionado.getPersonaJuridica().getIdPersonaJuridica());
                }
                consulta.setInt(4, gestionado.getComparecencia().getIdComparecencia());

                consulta.executeUpdate();
                telemetria.logActivity("Se inserto el gestionado en la base de datos.");
                return true;
            } catch (SQLException e) {
                telemetria.logActivity("Error al insertar el gestionado en base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
                return false;
            } finally {
                closeAll();
            }
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    public Boolean insertarGestionante(Gestionante gestionante) throws Exception{
        Callable<Boolean> task = () -> {
            try {

                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.INSERT_GESTIONANTE.toString());
                
                //consulta.setInt(1, gestionante.getIdGestionante()); (AUTOINCREMENT)
                if(gestionante.getPersona() != null)
                {
                    consulta.setInt(2, gestionante.getPersona().getIdPersona());
                }
                if(gestionante.getPersonaJuridica() != null)
                {
                    consulta.setInt(3, gestionante.getPersonaJuridica().getIdPersonaJuridica());
                }
                consulta.setInt(4, gestionante.getComparecencia().getIdComparecencia());

                consulta.executeUpdate();
                telemetria.logActivity("Se inserto el gestionante en la base de datos.");
                return true;
            } catch (SQLException e) {
                telemetria.logActivity("Error al insertar gestionante en base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
                return false;
            } finally {
                closeAll();
            }
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    public Boolean insertarInspector(Inspector inspector) throws Exception {
        Callable<Boolean> task = () -> {
            try{
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.INSERT_INSPECTOR.toString());

                consulta.setInt(1, inspector.getPersona().getIdPersona());
                consulta.setInt(2, inspector.getPuesto().ordinal());
                consulta.setInt(3, inspector.getRegion().ordinal());
                consulta.setInt(4, inspector.getOficina().ordinal());

                consulta.executeUpdate();
                telemetria.logActivity("Se inserto un inspector en la base de datos.");
                return true;
            } catch (SQLException e) {
                telemetria.logActivity("Error al guardar datos de usuario en base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e); 
                return false;
            } finally {
                closeAll();
            }
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    public Boolean insertarInspectorxComparecencia(int IdPersona, int IdComparecencia) throws Exception{
        Callable<Boolean> task = () -> {
            try {

                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.INSERT_INSPECTORXCOMPARECENCIA.toString());

                consulta.setInt(1, IdComparecencia);
                consulta.setInt(2, IdPersona);
                
                consulta.executeUpdate();
                telemetria.logActivity("Se inserto la relacion inspectorXcomparecencia en la base de datos.");
                return true;
            } catch (SQLException e) {
                telemetria.logActivity("Error al insertar la relacion inspectorXcomparecencia en base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
                return false;
            } finally {
                closeAll();
            }
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }

    public Boolean insertarPersona(Persona persona) throws Exception {
        Callable<Boolean> task = () -> {            
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.INSERT_PERSONA.toString());

                //consulta.setInt(1, inspector.getIdPersona()); (AUTOINCREMENT)
                consulta.setString(2, persona.getCedula());
                consulta.setString(3, persona.getPrimerNombre());
                consulta.setString(4, persona.getSegundoNombre());
                consulta.setString(5, persona.getPrimerApellido());
                consulta.setString(6, persona.getSegundoApellido());

                consulta.executeUpdate();
                telemetria.logActivity("Se inserto una persona en la base de datos.");
                return true;
            } catch (SQLException e) {
                telemetria.logActivity("Error al guardar datos de persona en base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
                return false;
            } finally {
                closeAll();
            }
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }

    public Boolean insertarPersonaJuridica(PersonaJuridica personaJuridica) throws Exception{
        Callable<Boolean> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.INSERT_PERSONAJURIDICA.toString());

                //consulta.setInt(1, personaJuridica.getIdPersonaJuridica()); (AUTOINCREMENT)
                consulta.setString(2, personaJuridica.getCedulaJuridica());
                consulta.setString(3, personaJuridica.getNombreRazonSocial());

                consulta.executeUpdate();
                telemetria.logActivity("Se inserto una persona juridica en la base de datos.");
                return true;
            } catch (SQLException e) {
                telemetria.logActivity("Error al guardar datos de persona juridica en base de datos");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
                return false;
            } finally {
                closeAll();
            }
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    public Boolean insertarRepresentante(Representante representante) throws Exception{
        Callable<Boolean> task = () -> {
            try {

                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.INSERT_REPRESENTANTE.toString());

                consulta.setInt(1, representante.getPersona().getIdPersona());
                if(representante.getGestionante() != null){
                    consulta.setInt(2, representante.getGestionante().getIdGestionante());
                }
                else if(representante.getGestionado() != null){
                    consulta.setInt(3, representante.getGestionado().getIdGestionado());
                }

                consulta.executeUpdate(); 
                telemetria.logActivity("Se inserto el representante en la base de datos.");
                return true;
            } catch (SQLException e) {
                telemetria.logActivity("Error al insertar representante en base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e); 
                return false;
            } finally {
                closeAll();
            }
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }

    public Boolean insertarTestigo(Testigo testigo) throws Exception{
        Callable<Boolean> task = () -> {
            try {

                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.INSERT_TESTIGO.toString());

                consulta.setInt(1, testigo.getPersona().getIdPersona());
                if(testigo.getGestionante() != null){
                    consulta.setInt(2, testigo.getGestionante().getIdGestionante());
                }
                else if(testigo.getGestionado() != null){
                    consulta.setInt(3, testigo.getGestionado().getIdGestionado());
                }
                
                consulta.executeUpdate();
                telemetria.logActivity("Se inserto el testigo en la base de datos.");
                return true;
            } catch (SQLException e) {
                telemetria.logActivity("Error al insertar testigo en base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
                return false;
            } finally {
                closeAll();
            }
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }

    public Boolean insertarUsuario(Usuario usuario) throws Exception {
        Callable<Boolean> task = () -> {            
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.INSERT_USUARIO.toString());

                consulta.setInt(1, usuario.getPersona().getIdPersona());
                consulta.setString(2, usuario.getEmail());
                consulta.setString(3, usuario.getContraseña());
                consulta.setBoolean(4, usuario.getEnLinea());

                consulta.executeUpdate();
                telemetria.logActivity("Se inserto un usuario en la base de datos.");
                return true;
            } catch (SQLException e) {
                telemetria.logActivity("Error al guardar datos de usuario en base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
                return false;
            } finally {
                closeAll();
            }
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    
    
/*******************************************************************************/    

    
    
    public int getIdAcompañante(String cedula) throws Exception {
           Callable<Integer> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_ID_ACOMPAÑANTE.toString());
                consulta.setString(1, cedula);
                resultado = consulta.executeQuery();

                if (resultado.next()) {
                    telemetria.logActivity("Se obtuvo un idAcompañante.");
                    elId = resultado.getInt(1);
                    closeAll();
                    return elId;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al obtener el idAcompañante generado por la base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            telemetria.logActivity("No existe idAcompañante para esa cedula.");
            return -1;
        };
        Future<Integer> future = Global.dbExecutor.submit(task);
        return future.get();
    }

    public int getIdAudio(String nombreAudio) throws Exception {
           Callable<Integer> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_ID_AUDIO.toString());
                consulta.setString(1, nombreAudio);
                resultado = consulta.executeQuery();

                if (resultado.next()) {
                    telemetria.logActivity("Se obtuvo un idAudio.");
                    elId = resultado.getInt(1);
                    closeAll();
                    return elId;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al obtener el idAudio generado por la base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            telemetria.logActivity("No existe idAudio para ese nombreAudio.");
            return -1;
        };
        Future<Integer> future = Global.dbExecutor.submit(task);
        return future.get();
    }

    public int getIdComparecencia(String codigoSILAC) throws Exception {
           Callable<Integer> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_ID_COMPARECENCIA.toString());
                consulta.setString(1, codigoSILAC);
                resultado = consulta.executeQuery();

                if (resultado.next()) {
                    telemetria.logActivity("Se obtuvo un idComparecencia.");
                    elId = resultado.getInt(1);
                    closeAll();
                    return elId;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al obtener el idComparecencia generado por la base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            telemetria.logActivity("No existe idComparecencia para ese codigoSILAC.");
            return -1;
        };
        Future<Integer> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    public int getIdGestionado(String cedula) throws Exception {
           Callable<Integer> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_ID_GESTIONADO.toString());
                consulta.setString(1, cedula);
                resultado = consulta.executeQuery();

                if (resultado.next()) {
                    telemetria.logActivity("Se obtuvo un idGestionado.");
                    elId = resultado.getInt(1);
                    closeAll();
                    return elId;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al obtener el idGestionado generado por la base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            telemetria.logActivity("No existe idGestionado para esa cedula.");
            return -1;
        };
        Future<Integer> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    public int getIdGestionante(String cedula) throws Exception {
           Callable<Integer> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_ID_GESTIONANTE.toString());
                consulta.setString(1, cedula);
                resultado = consulta.executeQuery();

                if (resultado.next()) {
                    telemetria.logActivity("Se obtuvo un idGestionante.");
                    elId = resultado.getInt(1);
                    closeAll();
                    return elId;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al obtener el idGestionante generado por la base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            telemetria.logActivity("No existe idGestionante para esa cedula.");
            return -1;
        };
        Future<Integer> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    public int getIdInspector(String cedula) throws Exception {
           Callable<Integer> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_ID_INSPECTOR.toString());
                consulta.setString(1, cedula);
                resultado = consulta.executeQuery();

                if (resultado.next()) {
                    telemetria.logActivity("Se obtuvo un idInspector.");
                    elId = resultado.getInt(1);
                    closeAll();
                    return elId;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al obtener el idInspector generado por la base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            telemetria.logActivity("No existe idInspector para esa cedula.");
            return -1;
        };
        Future<Integer> future = Global.dbExecutor.submit(task);
        return future.get();
    }

    public int getIdPersona(String cedula) throws Exception {
        Callable<Integer> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_ID_PERSONA.toString());
                consulta.setString(1, cedula);
                resultado = consulta.executeQuery();

                if (resultado.next()) {
                    telemetria.logActivity("Se obtuvo un idPersona.");
                    elId = resultado.getInt(1);
                    closeAll();
                    return elId;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al obtener el ID generado por la base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            telemetria.logActivity("No existe idPersona para esa cedula.");
            return -1;
        };
        Future<Integer> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    public int getIdPersonaJuridica(String cedulaJuridica) throws Exception {
        Callable<Integer> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_ID_PERSONAJURIDICA.toString());
                consulta.setString(1, cedulaJuridica);
                resultado = consulta.executeQuery();

                if (resultado.next()) {
                    telemetria.logActivity("Se obtuvo un idJuridico.");
                    elId = resultado.getInt(1);
                    closeAll();
                    return elId;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al obtener el idJuridico generado por la base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            telemetria.logActivity("No existe idJuridico para esa cedula.");
            return -1;
        };
        Future<Integer> future = Global.dbExecutor.submit(task);
        return future.get();
    }
        
    public int getIdRepresentante(String cedula) throws Exception {
           Callable<Integer> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_ID_REPRESENTANTE.toString());
                consulta.setString(1, cedula);
                resultado = consulta.executeQuery();

                if (resultado.next()) {
                    telemetria.logActivity("Se obtuvo un idRepresentante.");
                    elId = resultado.getInt(1);
                    closeAll();
                    return elId;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al obtener el idRepresentante generado por la base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            telemetria.logActivity("No existe idRepresentante para esa cedula.");
            return -1;
        };
        Future<Integer> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    public int getIdTestigo(String cedula) throws Exception {
           Callable<Integer> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_ID_TESTIGO.toString());
                consulta.setString(1, cedula);
                resultado = consulta.executeQuery();

                if (resultado.next()) {
                    telemetria.logActivity("Se obtuvo un idTestigo.");
                    elId = resultado.getInt(1);
                    closeAll();
                    return elId;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al obtener el idTestigo generado por la base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            telemetria.logActivity("No existe id para esa cedula.");
            return -1;
        };
        Future<Integer> future = Global.dbExecutor.submit(task);
        return future.get();
    }

    public int getIdUsuario(String cedula) throws Exception {
           Callable<Integer> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_ID_USUARIO.toString());
                consulta.setString(1, cedula);
                resultado = consulta.executeQuery();

                if (resultado.next()) {
                    telemetria.logActivity("Se obtuvo un idUsuario.");
                    elId = resultado.getInt(1);
                    closeAll();
                    return elId;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al obtener el idUsuario generado por la base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            telemetria.logActivity("No existe idUsuario para esa cedula.");
            return -1;
        };
        Future<Integer> future = Global.dbExecutor.submit(task);
        return future.get();
    }


    
 /*******************************************************************************/    
 
    
    
    public int getIdLastAcompañante() throws Exception {
           Callable<Integer> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.ID_LAST_ROW_ACOMPAÑANTE.toString());
                resultado = consulta.executeQuery();

                if (resultado.next()) {
                    telemetria.logActivity("Se obtuvo un idAcompañante.");
                    elId = resultado.getInt(1);
                    closeAll();
                    return elId;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al obtener el idAcompañante generado por la base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            telemetria.logActivity("No hay filas.");
            return -1;
        };
        Future<Integer> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    public int getIdLastAudio() throws Exception {
           Callable<Integer> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.ID_LAST_ROW_AUDIO.toString());
                resultado = consulta.executeQuery();

                if (resultado.next()) {
                    telemetria.logActivity("Se obtuvo un idAudio.");
                    elId = resultado.getInt(1);
                    closeAll();
                    return elId;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al obtener el idAudio generado por la base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            telemetria.logActivity("No existe idAudio para ese nombreAudio.");
            return -1;
        };
        Future<Integer> future = Global.dbExecutor.submit(task);
        return future.get();
    }

    public int getIdLastComparecencia() {
        try {
            Callable<Integer> task = () -> {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.ID_LAST_ROW_COMPARECENCIA.toString());
                resultado = consulta.executeQuery();

                if (resultado.next()) {
                    elId = resultado.getInt(1);
                    closeAll();
                    return elId;
                }
                return -1;
            };
            Future<Integer> future = Global.dbExecutor.submit(task);
            return future.get();
        } catch (Exception e) {
            telemetria.logActivity("Error al obtener el idComparecencia generado por la base de datos.");
            telemetria.logException(e);
        } finally {
            closeAll();
        }
        return -1;
    }
    
    public int getIdLastGestionado() throws Exception {
           Callable<Integer> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.ID_LAST_ROW_GESTIONADO.toString());
                resultado = consulta.executeQuery();

                if (resultado.next()) {
                    elId = resultado.getInt(1);
                    closeAll();
                    return elId;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al obtener el idGestionado generado por la base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            return -1;
        };
        Future<Integer> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    public int getIdLastGestionante() throws Exception {
           Callable<Integer> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.ID_LAST_ROW_GESTIONANTE.toString());
                resultado = consulta.executeQuery();

                if (resultado.next()) {
                    elId = resultado.getInt(1);
                    closeAll();
                    return elId;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al obtener el idGestionante generado por la base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            return -1;
        };
        Future<Integer> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    public int getIdLastInspector() throws Exception {
           Callable<Integer> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.ID_LAST_ROW_INSPECTOR.toString());
                resultado = consulta.executeQuery();

                if (resultado.next()) {
                    elId = resultado.getInt(1);
                    closeAll();
                    return elId;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al obtener el idInspector generado por la base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            return -1;
        };
        Future<Integer> future = Global.dbExecutor.submit(task);
        return future.get();
    }

    public int getIdLastPersona() throws Exception {
        Callable<Integer> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.ID_LAST_ROW_PERSONA.toString());
                resultado = consulta.executeQuery();

                if (resultado.next()) {
                    telemetria.logActivity("Se obtuvo un idPersona.");
                    elId = resultado.getInt(1);
                    closeAll();
                    return elId;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al obtener el ID generado por la base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            telemetria.logActivity("No hay filas.");
            return -1;
        };
        Future<Integer> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    public int getIdLastPersonaJuridica() throws Exception {
        Callable<Integer> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.ID_LAST_ROW_PERSONAJURIDICA.toString());
                resultado = consulta.executeQuery();

                if (resultado.next()) {
                    telemetria.logActivity("Se obtuvo un idJuridico.");
                    elId = resultado.getInt(1);
                    closeAll();
                    return elId;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al obtener el idJuridico generado por la base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            telemetria.logActivity("No hay filas.");
            return -1;
        };
        Future<Integer> future = Global.dbExecutor.submit(task);
        return future.get();
    }
        
    public int getIdLastRepresentante() throws Exception {
           Callable<Integer> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_ID_REPRESENTANTE.toString());
                resultado = consulta.executeQuery();

                if (resultado.next()) {
                    telemetria.logActivity("Se obtuvo un idRepresentante.");
                    elId = resultado.getInt(1);
                    closeAll();
                    return elId;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al obtener el idRepresentante generado por la base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            telemetria.logActivity("No hay filas.");
            return -1;
        };
        Future<Integer> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    public int getIdLastTestigo() throws Exception {
           Callable<Integer> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_ID_TESTIGO.toString());
                resultado = consulta.executeQuery();

                if (resultado.next()) {
                    telemetria.logActivity("Se obtuvo un idTestigo.");
                    elId = resultado.getInt(1);
                    closeAll();
                    return elId;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al obtener el idTestigo generado por la base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            telemetria.logActivity("No hay filas.");
            return -1;
        };
        Future<Integer> future = Global.dbExecutor.submit(task);
        return future.get();
    }

    public int getIdLastUsuario() throws Exception {
           Callable<Integer> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_ID_USUARIO.toString());
                resultado = consulta.executeQuery();

                if (resultado.next()) {
                    telemetria.logActivity("Se obtuvo un idUsuario.");
                    elId = resultado.getInt(1);
                    closeAll();
                    return elId;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al obtener el idUsuario generado por la base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            telemetria.logActivity("No hay filas.");
            return -1;
        };
        Future<Integer> future = Global.dbExecutor.submit(task);
        return future.get();
    }

 /*******************************************************************************/    

    public List<Integer> getAudioXComparecencia(int idComparecencia) {
        try {
            Callable<List<Integer>> task = () -> {
                List<Integer> audiosRelacionados = new LinkedList<>();
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_AUDIO_RELATED_COMPARECENCIA.toString());
                consulta.setInt(1, idComparecencia);
                consulta.setInt(2, idComparecencia);
                resultado = consulta.executeQuery();

                while (resultado.next()) {
                    audiosRelacionados.add(resultado.getInt(1));
                }
                return audiosRelacionados;
            };
            Future<List<Integer>> future = Global.dbExecutor.submit(task);
            return future.get();
        } catch (Exception e) {
            telemetria.logActivity("Error los id de audio relacionados con la comparecencia.");
            telemetria.logException(e);
        } finally {
            closeAll();
        }
        return null;
    }
           
    public String getAudioPathByIDs(int idComparecencia, int idAudio){
        try{
            conexion.abrirConexion();
            consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_AUDIOPATH_BY_IDS.toString());
            consulta.setInt(1, idComparecencia);
            consulta.setInt(2, idAudio);
            resultado = consulta.executeQuery();
            while(resultado.next()){
                return resultado.getString(1);
            }
        }catch(Exception e){
            System.out.println("Error al obtener el path del audio.");
            System.out.println(e);
        }finally{
            closeAll();
        }
        return "";
    }
    
    public String getAnotacionesPathByIDs(int idComparecencia, int idAudio){
        try{
            conexion.abrirConexion();
            consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_ANOTACIONPATH_BY_IDS.toString());
            consulta.setInt(1, idComparecencia);
            consulta.setInt(2, idAudio);
            resultado = consulta.executeQuery();
            while(resultado.next()){
                return resultado.getString(1);
            }
        }catch(Exception e){
            System.out.println("Error al obtener el path del audio.");
            System.out.println(e);
        }finally{
            closeAll();
        }
        return "";
    }
    
    public Boolean updateDuracionAudio(int idAudio, String duracion) throws Exception{
        Callable<Boolean> task = () -> {
            try {

                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_DURACION_AUDIO.toString());
                
                consulta.setString(1, duracion);
                consulta.setInt(2, idAudio);
                
                consulta.executeUpdate();
                telemetria.logActivity("Se inserto la duracion del audio en la base de datos.");
                return true;
            } catch (SQLException e) {
                telemetria.logActivity("Error al insertar la duracion del audio en base de datos.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
                return false;
            } finally {
                closeAll();
            }
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }

    
 /*******************************************************************************/    
    
    
    public Acompañante createObjectAcompañante(int idPersona) throws Exception {
        int[] id = new int[3];
        Acompañante elAcompañante = new Acompañante();
        ConexionDatabase conexionLocal = new ConexionDatabase();
        conexionLocal.abrirConexion();
        PreparedStatement consultaLocal;
        ResultSet resultadoLocal;

        conexionLocal.abrirConexion();
        consultaLocal = conexionLocal.sqliteConexion.prepareStatement(SQL.CREATE_OBJECT_ACOMPAÑANTE.toString());
        consultaLocal.setInt(1, idPersona);
        resultadoLocal = consultaLocal.executeQuery();

        try {
            if (resultadoLocal.next()) {
                telemetria.logActivity("Se obtuvo un objeto Acompañante.");
                id[0] = resultadoLocal.getInt(1);
                id[1] = resultadoLocal.getInt(2);
                id[2] = resultadoLocal.getInt(3);
                elAcompañante.setTipoAcompañante(resultadoLocal.getString(4));
                elAcompañante.setEnCondicionDe(resultadoLocal.getString(5));
            } else {
                telemetria.logActivity("No existe objeto Acompañante para ese Id.");
                return null;
            }

        } catch (SQLException e) {
            telemetria.logActivity("Error al obtener el objeto Acompañante desde la base de datos.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);
        } finally {
            conexionLocal.cerrarConexionSQLITE();
            conexionLocal = null;
            consultaLocal.close();
            consultaLocal = null;
            resultadoLocal.close();
            resultadoLocal = null;
        }
        elAcompañante.setPersona(createObjectPersona(id[0]));
        elAcompañante.setGestionante(createObjectGestionante(id[1]));
        elAcompañante.setGestionado(createObjectGestionado(id[2]));
        return elAcompañante;
    }

    public Audio createObjectAudio(int idAudio) throws Exception {
        if(idAudio == 0){
            return null;
        }
        
        Audio elAudio = new Audio();

        ConexionDatabase conexionLocal = new ConexionDatabase();
        conexionLocal.abrirConexion();
        PreparedStatement consultaLocal;
        ResultSet resultadoLocal;

        conexionLocal.abrirConexion();
        consultaLocal = conexionLocal.sqliteConexion.prepareStatement(SQL.CREATE_OBJECT_AUDIO.toString());
        consultaLocal.setInt(1, idAudio);
        resultadoLocal = consultaLocal.executeQuery();

        try {
            if (resultadoLocal.next()) {
                telemetria.logActivity("Se obtuvo un objeto Audio.");
                elAudio.setIdAudio(resultadoLocal.getInt(1));
                elAudio.setNombreAudio(resultadoLocal.getString(2));
                elAudio.setDuracionAudio(resultadoLocal.getString(3));
                elAudio.setPathArchivoAudio(resultadoLocal.getString(4));
                elAudio.setPathArchivoAnotaciones(resultadoLocal.getString(5));
                elAudio.createArchivoAudio();
                elAudio.createArchivoAnotaciones();
            } else {
                telemetria.logActivity("No existe objeto Audio para ese nombreAudio.");
                return null;
            }
            return elAudio;

        } catch (SQLException e) {
            telemetria.logActivity("Error al obtener el objeto Audio desde la base de datos.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);
        } finally {
            conexionLocal.cerrarConexionSQLITE();
            conexionLocal = null;
            consultaLocal.close();
            consultaLocal = null;
            resultadoLocal.close();
            resultadoLocal = null;
        }
        return null;
    }

    public Comparecencia createObjectComparecencia(int idComparecencia) throws Exception {
        if(idComparecencia == 0){
            return null;
        }
        
        Comparecencia laComparecencia = new Comparecencia();

        ConexionDatabase conexionLocal = new ConexionDatabase();
        conexionLocal.abrirConexion();
        PreparedStatement consultaLocal;
        ResultSet resultadoLocal;

        conexionLocal.abrirConexion();
        consultaLocal = conexionLocal.sqliteConexion.prepareStatement(SQL.CREATE_OBJECT_COMPARECENCIA.toString());
        consultaLocal.setInt(1, idComparecencia);
        resultadoLocal = consultaLocal.executeQuery();

        try {
            if (resultadoLocal.next()) {
                telemetria.logActivity("Se obtuvo un objeto Comparecencia.");
                laComparecencia.setIdComparecencia(resultadoLocal.getInt(1));
                laComparecencia.setCodigoSILAC(resultadoLocal.getString(2));
                laComparecencia.setTipoCaso(resultadoLocal.getInt(3));
                laComparecencia.setUbicacion(resultadoLocal.getString(4));
                laComparecencia.setLinkExpediente(resultadoLocal.getString(5));
                laComparecencia.setFecha(dateFormater.parse(resultadoLocal.getString(6)), false);
            } else {
                telemetria.logActivity("No existe objeto Comparecencia para ese codigoSILAC.");
                return null;
            }
            return laComparecencia;
        } catch (SQLException e) {
            telemetria.logActivity("Error al obtener el objeto Comparecencia desde la base de datos.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);
        } finally {
            conexionLocal.cerrarConexionSQLITE();
            conexionLocal = null;
            consultaLocal.close();
            consultaLocal = null;
            resultadoLocal.close();
            resultadoLocal = null;
        }
        return null;
    }

    public Gestionado createObjectGestionado(int idGestionado) throws Exception {
        if(idGestionado == 0){
            return null;
        }
        
        Gestionado elGestionado = new Gestionado();
        int[] id = new int[3];
        try {
            ConexionDatabase conexionLocal = new ConexionDatabase();
            conexionLocal.abrirConexion();
            PreparedStatement consultaLocal;
            ResultSet resultadoLocal;

            conexionLocal.abrirConexion();
            consultaLocal = conexionLocal.sqliteConexion.prepareStatement(SQL.CREATE_OBJECT_GESTIONADO.toString());
            consultaLocal.setInt(1, idGestionado);
            resultadoLocal = consultaLocal.executeQuery();

            if (resultadoLocal.next()) {
                telemetria.logActivity("Se obtuvo un objeto Gestionado.");
                elGestionado.setIdGestionado(resultadoLocal.getInt(1));
                id[0] = resultadoLocal.getInt(2);
                id[1] = resultadoLocal.getInt(3);
                id[2] = resultadoLocal.getInt(4);
            } else {
                telemetria.logActivity("No existe objeto Gestionado para ese Id.");
                return null;
            }

            conexionLocal.cerrarConexionSQLITE();
            conexionLocal = null;
            consultaLocal.close();
            consultaLocal = null;
            resultadoLocal.close();
            resultadoLocal = null;
        } catch (SQLException e) {
            telemetria.logActivity("Error al obtener el objeto Gestionado desde la base de datos.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);
        }
        elGestionado.setPersona(createObjectPersona(id[0]));
        elGestionado.setPersonaJuridica(createObjectPersonaJuridica(id[1]));
        elGestionado.setComparecencia(createObjectComparecencia(id[2]));
        return elGestionado;
    }

    public Gestionante createObjectGestionante(int idGestionante) throws Exception {
        if(idGestionante == 0){
            return null;
        }
        
        Gestionante elGestionante = new Gestionante();
        int[] id = new int[3];
        try {
            ConexionDatabase conexionLocal = new ConexionDatabase();
            conexionLocal.abrirConexion();
            PreparedStatement consultaLocal;
            ResultSet resultadoLocal;

            conexionLocal.abrirConexion();
            consultaLocal = conexionLocal.sqliteConexion.prepareStatement(SQL.CREATE_OBJECT_GESTIONANTE.toString());
            consultaLocal.setInt(1, idGestionante);
            resultadoLocal = consultaLocal.executeQuery();

            if (resultadoLocal.next()) {
                telemetria.logActivity("Se obtuvo un objeto Gestionante.");
                elGestionante.setIdGestionante(resultadoLocal.getInt(1));
                id[0] = resultadoLocal.getInt(2);
                id[1] = resultadoLocal.getInt(3);
                id[2] = resultadoLocal.getInt(4);
            } else {
                telemetria.logActivity("No existe objeto Gestionante para ese Id.");
                return null;
            }

            conexionLocal.cerrarConexionSQLITE();
            conexionLocal = null;
            consultaLocal.close();
            consultaLocal = null;
            resultadoLocal.close();
            resultadoLocal = null;
        } catch (SQLException e) {
            telemetria.logActivity("Error al obtener el objeto Gestionante desde la base de datos.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);
        }
        elGestionante.setPersona(createObjectPersona(id[0]));
        elGestionante.setPersonaJuridica(createObjectPersonaJuridica(id[1]));
        elGestionante.setComparecencia(createObjectComparecencia(id[2]));
        return elGestionante;
    }

    public Inspector createObjectInspector(int idPersona) throws Exception {
        if(idPersona == 0){
            return null;
        }
        
        Inspector elInspector = new Inspector();
        ConexionDatabase conexionLocal = new ConexionDatabase();
        conexionLocal.abrirConexion();
        PreparedStatement consultaLocal;
        ResultSet resultadoLocal;

        conexionLocal.abrirConexion();
        consultaLocal = conexionLocal.sqliteConexion.prepareStatement(SQL.CREATE_OBJECT_INSPECTOR.toString());
        consultaLocal.setInt(1, idPersona);
        resultadoLocal = consultaLocal.executeQuery();

        try {
            if (resultadoLocal.next()) {
                telemetria.logActivity("Se obtuvo un objeto inspector.");
                elInspector.setPuesto(Puesto.values()[resultadoLocal.getInt(2)]);
                elInspector.setOficina(Oficina.values()[resultadoLocal.getInt(4)]);
                elInspector.setRegion(Region.values()[resultadoLocal.getInt(3)], Oficina.values()[resultadoLocal.getInt(4)]);
            } else {
                telemetria.logActivity("No existe ningun inspector con esa cedula.");
                return null;
            }
        } catch (SQLException e) {
            telemetria.logActivity("Error al obtener el objeto inspector desde la base de datos.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);
        } finally {
            conexionLocal.cerrarConexionSQLITE();
            conexionLocal = null;
            consultaLocal.close();
            consultaLocal = null;
            resultadoLocal.close();
            resultadoLocal = null;
        }
        elInspector.setPersona(createObjectPersona(idPersona));
        elInspector.setUsuario(createObjectUsuario(idPersona));
        return elInspector;
    }

    public Persona createObjectPersona(int idPersona) throws Exception {
        if(idPersona == 0){
            return null;
        }
        
        Persona laPersona = new Persona();
        ConexionDatabase conexionLocal = new ConexionDatabase();
        conexionLocal.abrirConexion();
        PreparedStatement consultaLocal;
        ResultSet resultadoLocal;

        conexionLocal.abrirConexion();
        consultaLocal = conexionLocal.sqliteConexion.prepareStatement(SQL.CREATE_OBJECT_PERSONA.toString());
        consultaLocal.setInt(1, idPersona);
        resultadoLocal = consultaLocal.executeQuery();

        try {
            if (resultadoLocal.next()) {
                telemetria.logActivity("Se obtuvo un objeto Persona.");
                laPersona.setIdPersona(resultadoLocal.getInt(1));
                laPersona.setCedula(resultadoLocal.getString(2), false);
                laPersona.setPrimerNombre(resultadoLocal.getString(3));
                laPersona.setSegundoNombre(resultadoLocal.getString(4));
                laPersona.setPrimerApellido(resultadoLocal.getString(5));
                laPersona.setSegundoApellido(resultadoLocal.getString(6));
            } else {
                telemetria.logActivity("No existe objeto Persona para ese Id.");
                return null;
            }
            return laPersona;

        } catch (SQLException e) {
            telemetria.logActivity("Error al obtener el objeto persona desde la base de datos.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);
        } finally {
            conexionLocal.cerrarConexionSQLITE();
            conexionLocal = null;
            consultaLocal.close();
            consultaLocal = null;
            resultadoLocal.close();
            resultadoLocal = null;
        }
        return null;
    }

    public PersonaJuridica createObjectPersonaJuridica(int idPersonaJuridica) throws Exception {
        if(idPersonaJuridica == 0){
            return null;
        }
        
        PersonaJuridica laPersonaJuridica = new PersonaJuridica();
        ConexionDatabase conexionLocal = new ConexionDatabase();
        conexionLocal.abrirConexion();
        PreparedStatement consultaLocal;
        ResultSet resultadoLocal;

        conexionLocal.abrirConexion();
        consultaLocal = conexionLocal.sqliteConexion.prepareStatement(SQL.CREATE_OBJECT_PERSONA_JURIDICA.toString());
        consultaLocal.setInt(1, idPersonaJuridica);
        resultadoLocal = consultaLocal.executeQuery();

        try {
            if (resultadoLocal.next()) {
                telemetria.logActivity("Se obtuvo un objeto Juridico.");
                laPersonaJuridica.setIdPersonaJuridica(resultadoLocal.getInt(1));
                laPersonaJuridica.setCedulaJuridica(resultadoLocal.getString(2), false);
                laPersonaJuridica.setNombreRazonSocial(resultadoLocal.getString(3));
            } else {
                telemetria.logActivity("No existe objeto Juridico para ese Id.");
                return null;
            }
            return laPersonaJuridica;
        } catch (SQLException e) {
            telemetria.logActivity("Error al obtener el objeto Juridico desde la base de datos.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);
        } finally {
            conexionLocal.cerrarConexionSQLITE();
            conexionLocal = null;
            consultaLocal.close();
            consultaLocal = null;
            resultadoLocal.close();
            resultadoLocal = null;

        }
        return null;
    }

    public Representante createObjectRepresentante(int idPersona) throws Exception {
        if(idPersona == 0){
            return null;
        }
        
        int[] id = new int[3];
        ConexionDatabase conexionLocal = new ConexionDatabase();
        conexionLocal.abrirConexion();
        PreparedStatement consultaLocal;
        ResultSet resultadoLocal;

        conexionLocal.abrirConexion();
        consultaLocal = conexionLocal.sqliteConexion.prepareStatement(SQL.CREATE_OBJECT_REPRESENTANTE.toString());
        consultaLocal.setInt(1, idPersona);
        resultadoLocal = consultaLocal.executeQuery();

        try {
            if (resultadoLocal.next()) {
                telemetria.logActivity("Se obtuvo un objeto representante.");
                id[0] = resultadoLocal.getInt(1);
                id[1] = resultadoLocal.getInt(2);
                id[2] = resultadoLocal.getInt(3);
            } else {
                telemetria.logActivity("No existe objeto representante para ese Id.");
                return null;
            }
        } catch (SQLException e) {
            telemetria.logActivity("Error al obtener el objeto representante desde la base de datos.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);
        } finally {
            conexionLocal.cerrarConexionSQLITE();
            conexionLocal = null;
            consultaLocal.close();
            consultaLocal = null;
            resultadoLocal.close();
            resultadoLocal = null;
        }
        Representante elRepresentante = new Representante();
        elRepresentante.setPersona(createObjectPersona(id[0]));
        elRepresentante.setGestionante(createObjectGestionante(id[1]));
        elRepresentante.setGestionado(createObjectGestionado(id[2]));
        return elRepresentante;
    }

    public Testigo createObjectTestigo(int idPersona) throws Exception {
        if(idPersona == 0){
            return null;
        }
        
        int[] id = new int[3];
        ConexionDatabase conexionLocal = new ConexionDatabase();
        conexionLocal.abrirConexion();
        PreparedStatement consultaLocal;
        ResultSet resultadoLocal;

        conexionLocal.abrirConexion();
        consultaLocal = conexionLocal.sqliteConexion.prepareStatement(SQL.CREATE_OBJECT_TESTIGO.toString());
        consultaLocal.setInt(1, idPersona);
        resultadoLocal = consultaLocal.executeQuery();

        try {

            if (resultadoLocal.next()) {
                telemetria.logActivity("Se obtuvo un objeto Testigo.");
                id[0] = resultadoLocal.getInt(1);
                id[1] = resultadoLocal.getInt(2);
                id[2] = resultadoLocal.getInt(3);
            } else {
                telemetria.logActivity("No existe objeto Testigo para ese Id.");
                return null;
            }

        } catch (SQLException e) {
            telemetria.logActivity("Error al obtener el objeto Testigo desde la base de datos.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);
        } finally {

            conexionLocal.cerrarConexionSQLITE();
            conexionLocal = null;
            consultaLocal.close();
            consultaLocal = null;
            resultadoLocal.close();
            resultadoLocal = null;
        }
        Testigo elTestigo = new Testigo();
        elTestigo.setPersona(createObjectPersona(id[0]));
        elTestigo.setGestionante(createObjectGestionante(id[1]));
        elTestigo.setGestionado(createObjectGestionado(id[2]));
        return elTestigo;
    }

    public Usuario createObjectUsuario(int idPersona) throws Exception {
        if(idPersona == 0){
            return null;
        }
        
        Usuario elUsuario = new Usuario();
        ConexionDatabase conexionLocal = new ConexionDatabase();
        conexionLocal.abrirConexion();
        PreparedStatement consultaLocal;
        ResultSet resultadoLocal;

        conexionLocal.abrirConexion();
        consultaLocal = conexionLocal.sqliteConexion.prepareStatement(SQL.CREATE_OBJECT_USUARIO.toString());
        consultaLocal.setInt(1, idPersona);
        resultadoLocal = consultaLocal.executeQuery();

        try {

            if (resultadoLocal.next()) {
                telemetria.logActivity("Se obtuvo un objeto Usuario.");
                elUsuario.setEmail(resultadoLocal.getString(2), false);
                elUsuario.setContraseña(resultadoLocal.getString(3));
                elUsuario.setEnLinea(resultadoLocal.getBoolean(4));
            } else {
                telemetria.logActivity("No existe objeto Usuario para ese Id.");
                return null;
            }

        } catch (SQLException e) {
            telemetria.logActivity("Error al obtener el objeto Usuario desde la base de datos.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);
        } finally {

            conexionLocal.cerrarConexionSQLITE();
            conexionLocal = null;
            consultaLocal.close();
            consultaLocal = null;
            resultadoLocal.close();
            resultadoLocal = null;
        }
        elUsuario.setPersona(createObjectPersona(idPersona));
        return elUsuario;
    }


    
/*******************************************************************************/    
    
    
    
    public Boolean cedulaIsUnique(String cedula) throws Exception{
        Callable<Boolean> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.CHECK_CEDULA.toString());
                consulta.setString(1, cedula);
                resultado = consulta.executeQuery();

                if (!resultado.next()) {
                    telemetria.logActivity("La cedula es unica.");
                    closeAll();
                    return true;
                }else{
                    telemetria.logActivity("cedula no es unica.");
                    return false;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al verificar cedula.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            return null;
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }

    public Boolean emailIsUnique(String email) throws Exception{
        Callable<Boolean> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.CHECK_EMAIL.toString());
                consulta.setString(1, email);
                resultado = consulta.executeQuery();

                if (!resultado.next()) {
                    telemetria.logActivity("email es unico.");
                    closeAll();
                    return true;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al revisar catalogo oficina.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            telemetria.logActivity("email no es unico.");
            return false;
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    public Boolean cedulaJuridicaIsUnique(String cedulaJuridica) throws Exception{
        Callable<Boolean> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.CHECK_CEDULA_JURIDICA.toString());
                consulta.setString(1, cedulaJuridica);
                resultado = consulta.executeQuery();

                if (!resultado.next()) {
                    telemetria.logActivity("La cedula juridica es unica.");
                    closeAll();
                    return true;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al verificar cedula juridica.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            telemetria.logActivity("cedula juridica no es unica.");
            return false;
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    
    
/*******************************************************************************/    

    
    
    //verifica que los catalogos esten llenos
    //para establecer las relaciones
    public void llenarCatalogos() throws Exception {
        if (catalogoOficinaIsEmpty()) {
            insertarOficinas();
            if (catalogoRegionIsEmpty()) {
                insertarRegiones();
                if (catalogoPuestoIsEmpty()) {
                    insertarPuestos();
                    if (catalogoTipoCasoIsEmpty()) {
                        insertarTiposCasos();
                    }
                }
            }

        }
    }

    public Boolean catalogoOficinaIsEmpty() throws Exception {
        Callable<Boolean> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.SELECT_OFICINA.toString());
                resultado = consulta.executeQuery();

                if (!resultado.next()) {
                    telemetria.logActivity("Catalogo oficina esta vacio.");
                    closeAll();
                    return true;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al revisar catalogo oficina.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            telemetria.logActivity("Catalogo oficina no esta vacio.");
            return false;
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }

    public Boolean catalogoRegionIsEmpty() throws Exception {
        Callable<Boolean> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.SELECT_REGION.toString());
                resultado = consulta.executeQuery();

                if (!resultado.next()) {
                    telemetria.logActivity("Catalogo region esta vacio.");
                    closeAll();
                    return true;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al revisar catalogo region.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            telemetria.logActivity("Catalogo region no esta vacio.");
            return false;
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }

    public Boolean catalogoPuestoIsEmpty() throws Exception {
        Callable<Boolean> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.SELECT_PUESTO.toString());
                resultado = consulta.executeQuery();

                if (!resultado.next()) {
                    telemetria.logActivity("Catalogo puesto esta vacio.");
                    closeAll();
                    return true;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al revisar catalogo puesto.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            telemetria.logActivity("Catalogo region no esta vacio.");
            return false;
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    public Boolean catalogoTipoCasoIsEmpty() throws Exception{
        Callable<Boolean> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.SELECT_CASO.toString());
                resultado = consulta.executeQuery();

                if (!resultado.next()) {
                    telemetria.logActivity("Catalogo razon esta vacio.");
                    closeAll();
                    return true;
                }
            } catch (SQLException e) {
                telemetria.logActivity("Error al revisar catalogo oficina.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            telemetria.logActivity("Catalogo razon no esta vacio.");
            return false;
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    

    
/*******************************************************************************/    
   
    
    
    public Boolean userExist(String cedula, String contraseña) throws Exception{
        Callable<Boolean> task = () -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.VERIFY_USER.toString());
                consulta.setString(1, cedula);
                consulta.setString(2, contraseña);
                resultado = consulta.executeQuery();

                //si no hay resultados, no existe el usuario
                if (!resultado.next()) {
                    telemetria.logActivity("El usuario ingresado no existe.");
                    closeAll();
                    return false;
                }

            } catch (SQLException ex) {
                System.out.println("Error en la verificación del usuario y contraseña");
                telemetria.logActivity("SQLException: " + ex.getMessage());
                telemetria.logActivity("SQLState: " + ex.getSQLState());
                telemetria.logActivity("VendorError: " + ex.getErrorCode());
                telemetria.logException(ex);
                return false;
            } finally {
                closeAll();
            }
            telemetria.logActivity("El usuario ingresado existe.");
            return true;
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }

    public void loginInspector(int idInspector){
        Global.dbExecutor.execute(
        new Thread(() -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.LOGIN.toString());

                consulta.setInt(1, idInspector);
                consulta.executeUpdate();

                telemetria.logActivity("Se inicio la sesion.");
            } catch (SQLException e) {
                telemetria.logActivity("Error al iniciar la sesion.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
        }));
    }
    
    public void logoutInspector(int idInspector){
        Global.dbExecutor.execute(
        new Thread(() -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.sqliteConexion.prepareStatement(SQL.LOGOUT.toString());

                consulta.setInt(1, idInspector);
                consulta.executeUpdate();

                telemetria.logActivity("Se cerro la sesion.");
            } catch (SQLException e) {
                telemetria.logActivity("Error al cerrar la sesion.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
        }));
    }


    

/*******************************************************************************/    

    
    
    public DefaultTableModel getDatosTablaPantallaPrincipal(DefaultTableModel laTabla) throws Exception {
        Callable<DefaultTableModel> task = () -> {
            try {
                conexion.abrirConexion();
                
                String sql = SQL.GET_NUMBER_ROWS_COMPARECENCIA.toString();
                sql = sql.replace("expDB.", "");
                
                consulta = conexion.sqliteConexion.prepareStatement(sql);
                resultado = consulta.executeQuery();
                
                if(!resultado.next()){
                    return null;
                }
                
                //CON AUDIOS
                consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_DATOS_PANTALLA_PRINCIPAL.toString());
                resultado = consulta.executeQuery();
                    
                Object[] fila = new Object[6];
                while (resultado.next()) {
                    fila[0] = resultado.getString(1);
                    fila[1] = resultado.getString(2);
                    fila[2] = resultado.getString(3);
                    fila[3] = resultado.getString(4);
                    fila[4] = resultado.getString(5);
                    fila[5] = "L"; //L = Proviene de la base de datos local
                    laTabla.addRow(fila);
                }
                
                //SIN AUDIOS
                consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_DATOS_PANTALLA_PRINCIPAL_NO_AUDIO.toString());
                resultado = consulta.executeQuery();
                while (resultado.next()) {
                    fila[0] = resultado.getString(1);
                    fila[1] = "SIN AUDIOS";
                    fila[2] = resultado.getString(3);
                    fila[3] = resultado.getString(4);
                    fila[4] = resultado.getString(5);
                    fila[5] = "L";
                    laTabla.addRow(fila);
                }
                
                return laTabla;
            } catch (SQLException e) {
                telemetria.logActivity("Error al obtener los datos para la tabla de pantalla principal.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {
                closeAll();
            }
            return null;
        };
        Future<DefaultTableModel> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    public List<Object> getDatosComparecencia(int idComparecencia) throws Exception{
        //Callable<List<Object>> task = () -> {
            List<Object> listaPresentes = new LinkedList<>();
            try {
                ConexionDatabase conexionLocal = new ConexionDatabase();
                conexionLocal.abrirConexion();
                PreparedStatement consultaLocal;
                ResultSet resultadoLocal;
                
                int idGestionado = 0;
                int idGestionante = 0;

                telemetria.logActivity("Se buscan gestionante, gestionado, testigo, acompañante y representante de la comparecencia.");

                //GESTIONANTE
                consultaLocal = conexionLocal.sqliteConexion.prepareStatement(SQL.GET_GESTIONANTE_RELATED_COMPARECENCIA.toString());
                consultaLocal.setInt(1, idComparecencia);
                resultadoLocal = consultaLocal.executeQuery();

                if (resultadoLocal.next()) {
                    idGestionante = resultadoLocal.getInt(1);
                    listaPresentes.add(createObjectGestionante(idGestionante));
                }

                //GESTIONADO
                consultaLocal = conexionLocal.sqliteConexion.prepareStatement(SQL.GET_GESTIONADO_RELATED_COMPARECENCIA.toString());
                consultaLocal.setInt(1, idComparecencia);
                resultadoLocal = consultaLocal.executeQuery();

                if (resultadoLocal.next()) {
                    idGestionado = resultadoLocal.getInt(1);
                    listaPresentes.add(createObjectGestionado(idGestionado));
                }

                //[GESTIONANTE]
                //TESTIGO 
                consultaLocal = conexionLocal.sqliteConexion.prepareStatement(SQL.GET_TESTIGO_RELATED_GESTIONANTE.toString());
                consultaLocal.setInt(1, idGestionante);
                resultadoLocal = consultaLocal.executeQuery();

                while (resultadoLocal.next()) {
                    int idPersona = resultadoLocal.getInt(1);
                    listaPresentes.add(createObjectTestigo(idPersona));
                }

                //[GESTIONANTE]
                //REPRESENTANTE
                consultaLocal = conexionLocal.sqliteConexion.prepareStatement(SQL.GET_REPRESENTANTE_RELATED_GESTIONANTE.toString());
                consultaLocal.setInt(1, idGestionante);
                resultadoLocal = consultaLocal.executeQuery();

                while (resultadoLocal.next()) {
                    int idPersona = resultadoLocal.getInt(1);
                    listaPresentes.add(createObjectRepresentante(idPersona));
                }

                //[GESTIONANTE]
                //ACOMPAÑANTE
                consultaLocal = conexionLocal.sqliteConexion.prepareStatement(SQL.GET_ACOMPAÑANTE_RELATED_GESTIONANTE.toString());
                consultaLocal.setInt(1, idGestionante);
                resultadoLocal = consultaLocal.executeQuery();

                while (resultadoLocal.next()) {
                    int idPersona = resultadoLocal.getInt(1);
                    listaPresentes.add(createObjectAcompañante(idPersona));
                }

                //[GESTIONADO]
                //TESTIGO
                consultaLocal = conexionLocal.sqliteConexion.prepareStatement(SQL.GET_TESTIGO_RELATED_GESTIONADO.toString());
                consultaLocal.setInt(1, idGestionado);
                resultadoLocal = consultaLocal.executeQuery();

                while (resultadoLocal.next()) {
                    int idPersona = resultadoLocal.getInt(1);
                    listaPresentes.add(createObjectTestigo(idPersona));
                }

                //[GESTIONADO]
                //REPRESENTANTE
                consultaLocal = conexionLocal.sqliteConexion.prepareStatement(SQL.GET_REPRESENTANTE_RELATED_GESTIONADO.toString());
                consultaLocal.setInt(1, idGestionado);
                resultadoLocal = consultaLocal.executeQuery();

                while (resultadoLocal.next()) {
                    int idPersona = resultadoLocal.getInt(1);
                    listaPresentes.add(createObjectRepresentante(idPersona));
                }

                //[GESTIONADO]
                //ACOMPAÑANTE
                consultaLocal = conexionLocal.sqliteConexion.prepareStatement(SQL.GET_ACOMPAÑANTE_RELATED_GESTIONADO.toString());
                consultaLocal.setInt(1, idGestionado);
                resultadoLocal = consultaLocal.executeQuery();

                while (resultadoLocal.next()) {
                    int idPersona = resultadoLocal.getInt(1);
                    listaPresentes.add(createObjectAcompañante(idPersona));
                }
                
                conexionLocal.cerrarConexionSQLITE();
                conexionLocal = null;
                consultaLocal.close();
                consultaLocal = null;
                resultadoLocal.close();
                resultadoLocal = null;
                
                return listaPresentes;
            } catch (SQLException e) {
                telemetria.logActivity("Error al obtener los datos de la comparecencia para la grabacion.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
            } finally {

            }
            return listaPresentes;
        //};
        //Future<List<Object>> future = Global.dbExecutor.submit(task);
        //return future.get();
    }
    
    public DefaultTableModel buscarDatosTabla(DefaultTableModel laTabla,
                                        String laBusqueda, String tipoBusqueda, 
                                        java.util.Date fechaDesde, 
                                        java.util.Date fechaHasta, 
                                        Boolean ascendente, Boolean descendente){
        
        String sqlQUERY = crearSentenciaBusqueda(laBusqueda, tipoBusqueda, 
                                        fechaDesde, fechaHasta, 
                                        ascendente, descendente);
        try {
            conexion.abrirConexion();
            consulta = conexion.sqliteConexion.prepareStatement(sqlQUERY);
            resultado = consulta.executeQuery();
            
            while (resultado.next()) {
                Object[] fila = new Object[6];
                fila[0] = resultado.getString(1);
                fila[1] = resultado.getString(2);
                fila[2] = resultado.getString(3);
                fila[3] = resultado.getString(4);
                fila[4] = resultado.getString(5);
                fila[5] = "L";
                laTabla.addRow(fila);
            }
            if (laTabla.getRowCount() != 0) {
                telemetria.logActivity("La busqueda ha retornado resultados.");
                return laTabla;
            } else {
                telemetria.logActivity("La busqueda ha retornado ningun resultado.");
                return laTabla;
            }
        } catch (SQLException e) {
            telemetria.logActivity("Error al listar los datos de grabacion en la busqueda.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);            
        } finally {
            closeAll();
        }
        return laTabla;
    }
    
    private String crearSentenciaBusqueda(String laBusqueda, String tipoBusqueda, 
                                        java.util.Date fechaDesde, 
                                        java.util.Date fechaHasta, 
                                        Boolean ascendente, Boolean descendente){
        
        SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        String newSQL = SQL.BUSQUEDA_DATOS_TABLA.toString();
        
        try{


            switch(tipoBusqueda.charAt(0)){
                case 'C' -> {
                    newSQL = newSQL.replace("[A]", "comp.codigoSilac");
                    break;
                }

                case 'T' -> {
                    newSQL = newSQL.replace("[A]", "cat.tipoCaso");
                    break;
                }

                case 'U' -> {
                    newSQL = newSQL.replace("[A]", "comp.ubicacion");
                    break;
                }
                
                case 'N' -> {
                    newSQL = newSQL.replace("[A]", "au.nombreAudio");
                    break;
                }
                
                case 'F' -> {
                    newSQL = newSQL.replace("[A]", "comp.fecha");
                    break;
                }
            }

            newSQL = newSQL.replace("[B]", laBusqueda);
            newSQL = newSQL.replace("[C]", dtf.format(fechaDesde));
            newSQL = newSQL.replace("[D]", dtf.format(fechaHasta));

            if(ascendente){
                newSQL = newSQL.replace("[E]", "ASC");
            }else{
                newSQL = newSQL.replace("[E]", "DESC");
            }
            return newSQL;
        }catch(Exception e){
            telemetria.logActivity("Fallo al construir la sentencia SQL.");
            telemetria.logException(e);            
        }
        return newSQL;
    }

    public List<String> getAllAnotacionesPath() {
        List<String> idCoincidentes = new LinkedList<>();
        try {
            conexion.abrirConexion();
            consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_PATHS_ANOTACIONES.toString());
            resultado = consulta.executeQuery();

            while (resultado.next()) {
                idCoincidentes.add(resultado.getString(1));
            }

            return idCoincidentes;
        } catch (SQLException e) {
            telemetria.logActivity("Error al obtener todos los paths de anotaciones.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);
        } finally {
            closeAll();
        }
        return null;
    }

    public DefaultTableModel buscarDatosTablaAnotaciones(DefaultTableModel laTabla, List<String> listaCoincidencias){
        try {

            conexion.abrirConexion();
            consulta = conexion.sqliteConexion.prepareStatement(SQL.BUSQUEDA_ANOTACIONES.toString());
            
            for(String elPath : listaCoincidencias){
                consulta.setString(1, elPath);
                resultado = consulta.executeQuery();
                while (resultado.next()) {
                    Object[] fila = new Object[6];
                    fila[0] = resultado.getString(1);
                    fila[1] = resultado.getString(2);
                    fila[2] = resultado.getString(3);
                    fila[3] = resultado.getString(4);
                    fila[4] = resultado.getString(5);
                    fila[5] = "L";
                    laTabla.addRow(fila);
                }
            }

            if (laTabla.getRowCount() != 0) {
                telemetria.logActivity("La busqueda ha retornado resultados.");
                return laTabla;
            } else {
                telemetria.logActivity("La busqueda ha retornado ningun resultado.");
                return laTabla;
            }
        } catch (SQLException e) {
            telemetria.logActivity("Error al listar los datos de grabacion en la busqueda.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);            
        } finally {
            closeAll();
        }
        return laTabla;
    
    }

    
/*
DIAGRAMA DE FLUJO PARA EXTRAER DATOS Y EXPORTAR
================================================
[INICIO]
|    
o-comparecencia ([A]IDcomp)
|   |
|   o- audioxcomparecencia ([A]IDComp X [B]IDAu) [MULTI]*
|   |     |
|   |     o-audio (IDAu = [B])
|   | 
|   o-gestionante (=[A]IDComp)
|   |    |
|   |    o-persona (=[C]IDGent)
|   |    |
|   |    o-personajuridica (=[C]IDGnte)
|   |    |
|   |    o-representante (=[C]IDGnte) [MULTI]*
|   |    |    |
|   |    |    o-persona (=[D]IDPer)
|   |    |
|   |    o-testigo (=[C]IDGnte) [MULTI]*
|   |    |    | 
|   |    |    o-persona (=[E]IDPer)
|   |    |    |
|   |    o-acompañante(=[C]IDGnte) [MULTI]*
|   |         |
|   |         o-persona (=[F]IDPer)
|   | 
|   o-gestionado (=[A]IDComp)
|   |    |
|   |    o-persona (=[G]IDGado)
|   |    |
|   |    o-personajuridica (=[G]IDGado)
|   |    |
|   |    o-representante (=[G]IDGado) [MULTI]*
|   |    |    |
|   |    |    o-persona (=[H]IDPer)
|   |    |
|   |    o-testigo (=[G]IDGado) [MULTI]*
|   |    |    |
|   |    |    o-persona (=[I]IDPer)
|   |    |
|   |    o-acompañante (=[G]IDGado) [MULTI]*
|   |         |
|   |         o-persona (=[J]IDPer)
|   | 
|   o-inspectorxcomparecencia ([A]IDComp X [K]IDPer)
|   |    |
|   |    o-inspector [=K]IDPer
|   |        |
|   |        o-usuario [=K]IDPer
|   |            |
|   |            o-persona [=K]IDPer
*/    

    public Boolean createExportDataBase(String pathComparecencia) throws Exception{
            Callable<Boolean> task = () -> {            
            try {
                
                conexion.createTemporalDB(pathComparecencia);
                pathDATADB = pathComparecencia + "data.db";
                for(int i = 0; i < 13; i++){ //CREA TABLAS
                    consulta = conexion.exportConexion.prepareStatement(SQL.values()[i].toString());
                    consulta.executeUpdate();
                }
                
                telemetria.logActivity("Se creo una base de datos para almacenar los datos a exportar.");
                return true;
            } catch (SQLException e) {
                closeAll();
                telemetria.logActivity("Error al crear la base de datos temporal.");
                telemetria.logActivity("SQLException: " + e.getMessage());
                telemetria.logActivity("SQLState: " + e.getSQLState());
                telemetria.logActivity("VendorError: " + e.getErrorCode());
                telemetria.logException(e);
                return false;
            } finally {
                closeAll();
            }
        };
        Future<Boolean> future = Global.dbExecutor.submit(task);
        return future.get();
    }
    
    public int exportComparecencia(int idComparecencia){
            try {
                
            //FASE 1: UNIR LA BASE DE DATOS
            conexion.abrirConexion();
            String sqlQuery = SQL.ATTACH_DATADB.toString().replace("[DB]", pathDATADB);
            consulta = conexion.sqliteConexion.prepareStatement(sqlQuery);
            consulta.executeUpdate();

            //FASE 2: INSERTAR COMPARECENCIA SELECCIONADA
            consulta = conexion.sqliteConexion.prepareStatement(SQL.EXPORT_COMPARECENCIAS.toString());
            consulta.setInt(1, idComparecencia);
            consulta.executeUpdate();
            
            //FASE 3: EXPORTAR TODO LO DEMAS
            if(exportAudioXComparecencia(idComparecencia) != -1)
            {if(exportGestionanteXComparecencia(idComparecencia) != -1){
             if(exportGestionadoXComparecencia(idComparecencia) != -1){
             if(exportInspectorXComparecencia(idComparecencia) != -1){
             return 0;}}}}
        } catch (SQLException e) {
            telemetria.logActivity("Error al exportar la tabla comparecencias.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);            
        } finally {
            closeAll();
        }
        return -1;
    }
    
    public int exportAudioXComparecencia(int idComparecencia){
        try {
            //FASE 1: INSERTAR RELACION AUDIOXCOMPARECENCIA SELECCIONADA
            consulta = conexion.sqliteConexion.prepareStatement(SQL.EXPORT_AUDIOXCOMPARECENCIAS.toString());
            consulta.setInt(1, idComparecencia);
            consulta.executeUpdate();
            
            //FASE 2: OBTENER TODOS LOS AUDIOS RELACIONADOS CON ESA COMPARECENCIA
            consulta = conexion.sqliteConexion.prepareStatement(
            """
                    SELECT idAudio FROM expDB.audioxcomparecencia  
                    WHERE expDB.audioxcomparecencia.idComparecencia = ?
            """
            );
            consulta.setInt(1, idComparecencia);
            resultado = consulta.executeQuery();

                while (resultado.next()) {
                    //FASE 3: EXPORTAR LOS DATOS DE SUS AUDIOS
                    exportAudio(resultado.getInt(1));
                }
                return 0;

        } catch (SQLException e) {
            telemetria.logActivity("Error al al exportar la tabla audio x comparecencia");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);            
        }
        return -1;
    }
    
    public int exportAudio(int idAudio){
        try {
            //FASE 1: INSERTAR EL AUDIO
            consulta = conexion.sqliteConexion.prepareStatement(SQL.EXPORT_AUDIO.toString());
            consulta.setInt(1, idAudio);
            consulta.executeUpdate();
            //FASE 2: REGRESAR A METODO EXPORTCOMPARECENCIAS
            return 0;
        } catch (SQLException e) {
            telemetria.logActivity("Error al exportar la tabla audio.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);            
        }
        return -1;
    }
        
    public int exportGestionanteXComparecencia(int idComparecencia){
        try {            
            //FASE 1: INSERTAR GESTIONANTE SELECCIONADO
            consulta = conexion.sqliteConexion.prepareStatement(SQL.EXPORT_GESTIONANTE.toString());
            consulta.setInt(1, idComparecencia);
            consulta.executeUpdate();
            
            //FASE 2: ENCONTRAR CON QUE PERSONA NATURAL 
            //O JURIDICA ESTA RELACIONADA EL GESTIONANTE
            consulta = conexion.sqliteConexion.prepareStatement(
            """
                    SELECT idGestionante,idPersona,idPersonaJuridica FROM expDB.gestionante
                    WHERE expDB.gestionante.idComparecencia = ?
            """
            );
            consulta.setInt(1, idComparecencia);
            resultado = consulta.executeQuery();

            int idGestionante = 0;
                while (resultado.next()) {
                    idGestionante = resultado.getInt(1);
                    int[] fila = new int[2];
                    fila[0] = resultado.getInt(2);
                    fila[1] = resultado.getInt(3);
                    //FASE 3: EXPORTAR LOS DATOS DE LA PERSONA DEL GESTIONANTE
                    if(fila[0] != 0){exportPersona(fila[0]);}
                    if(fila[1] != 0){exportPersonaJuridica(fila[1]);}
                }

            
            //FASE 4: ENCONTRAR TESTIGOS, REPRESENTANTES O ACOMPAÑANTES
            exportGestionanteXTestigo(idGestionante);
            exportGestionanteXAcompañante(idGestionante);
            exportGestionanteXRepresentante(idGestionante);
            return 0;
        } catch (SQLException e) {
            telemetria.logActivity("Error al exportar los datos de gestionante x comparecencia.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);            
        }
        return -1;
    }
    
    public int exportGestionanteXTestigo(int idGestionante){
            try {
            //FASE 1: INSERTAR TESTIGO
            consulta = conexion.sqliteConexion.prepareStatement(SQL.EXPORT_TESTIGO_GESTIONANTE.toString());
            consulta.setInt(1, idGestionante);
            consulta.executeUpdate();
            
            //FASE 2: OBTENER LA PERSONA RELACIONADA AL TESTIGO
            consulta = conexion.sqliteConexion.prepareStatement(
            """
                    SELECT idPersona FROM expDB.testigo  
                    WHERE expDB.testigo.idGestionante = ?
            """
            );
            consulta.setInt(1, idGestionante);
            resultado = consulta.executeQuery();

                while (resultado.next()) {
                    //FASE 3: EXPORTAR LOS DATOS PERSONALES DEL TESTIGO
                    exportPersona(resultado.getInt(1));
                }
                return 0;

        } catch (SQLException e) {
            telemetria.logActivity("Error al al exportar los datos de gestionante x testigo.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);            
        }
        return -1;
    }
        
    public int exportGestionanteXAcompañante(int idGestionante){
            try {
            //FASE 1: INSERTAR ACOMPAÑANTE
            consulta = conexion.sqliteConexion.prepareStatement(SQL.EXPORT_ACOMPAÑANTE_GESTIONANTE.toString());
            consulta.setInt(1, idGestionante);
            consulta.executeUpdate();
            
            //FASE 2: OBTENER LA PERSONA RELACIONADA AL ACOMPAÑANTE
            consulta = conexion.sqliteConexion.prepareStatement(
            """
                    SELECT idPersona FROM expDB.acompañante
                    WHERE expDB.acompañante.idGestionante = ?
            """
            );
            consulta.setInt(1, idGestionante);
            resultado = consulta.executeQuery();

                while (resultado.next()) {
                    //FASE 3: EXPORTAR LOS DATOS PERSONALES DEL ACOMPAÑANTE
                    exportPersona(resultado.getInt(1));
                }
                return 0;

        } catch (SQLException e) {
            telemetria.logActivity("Error al exportar los datos de gestionante x acompañante.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);            
        }
        return -1;
    }
       
    public int exportGestionanteXRepresentante(int idGestionante){
        try {
            //FASE 1: INSERTAR PRESENTANTE
            consulta = conexion.sqliteConexion.prepareStatement(SQL.EXPORT_REPRESENTANTE_GESTIONANTE.toString());
            consulta.setInt(1, idGestionante);
            consulta.executeUpdate();
            
            //FASE 2: OBTENER LA PERSONA RELACIONADA AL REPRESENTANTE
            consulta = conexion.sqliteConexion.prepareStatement(
            """
                    SELECT idPersona FROM expDB.representante
                    WHERE expDB.representante.idGestionante = ?
            """
            );
            consulta.setInt(1, idGestionante);
            resultado = consulta.executeQuery();

                while (resultado.next()) {
                    //FASE 3: EXPORTAR LOS DATOS PERSONALES DEL REPRESENTANTE
                    exportPersona(resultado.getInt(1));
                }
                return 0;

        } catch (SQLException e) {
            telemetria.logActivity("Error al exportar los datos de gestionante x representante.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);            
        }
        return -1;
    }
    
    public int exportGestionadoXComparecencia(int idComparecencia){
            try {            
            //FASE 1: INSERTAR GESTIONANTE SELECCIONADO
            consulta = conexion.sqliteConexion.prepareStatement(SQL.EXPORT_GESTIONADO.toString());
            consulta.setInt(1, idComparecencia);
            consulta.executeUpdate();
            
            //FASE 2: ENCONTRAR CON QUE PERSONA NATURAL 
            //O JURIDICA ESTA RELACIONADA EL GESTIONADO
            consulta = conexion.sqliteConexion.prepareStatement(
            """
                    SELECT idGestionado,idPersona,idPersonaJuridica FROM expDB.gestionado
                    WHERE expDB.gestionado.idComparecencia = ?
            """
            );
            consulta.setInt(1, idComparecencia);
            resultado = consulta.executeQuery();

            int idGestionado = 0;
                while (resultado.next()) {
                    idGestionado = resultado.getInt(1);
                    int[] fila = new int[2];
                    fila[0] = resultado.getInt(2);
                    fila[1] = resultado.getInt(3);
                    //FASE 3: EXPORTAR LOS DATOS PERSONALES DEL GESTIONANTE
                    if(fila[0] != 0){exportPersona(fila[0]);}
                    if(fila[1] != 0){exportPersonaJuridica(fila[1]);}
                }

            
            //FASE 4: ENCONTRAR TESTIGOS, REPRESENTANTES O ACOMPAÑANTES
            exportGestionadoXTestigo(idGestionado);
            exportGestionadoXAcompañante(idGestionado);
            exportGestionadoXRepresentante(idGestionado);
            return 0;
        } catch (SQLException e) {
            telemetria.logActivity("Error al exportar los datos de gestionado x comparecencia.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);            
        }
        return -1;
    }
    
    public int exportGestionadoXTestigo(int idGestionado){
         try {
            //FASE 1: INSERTAR TESTIGO
            consulta = conexion.sqliteConexion.prepareStatement(SQL.EXPORT_TESTIGO_GESTIONADO.toString());
            consulta.setInt(1, idGestionado);
            consulta.executeUpdate();
            
            //FASE 2: OBTENER LA PERSONA RELACIONADA AL TESTIGO
            consulta = conexion.sqliteConexion.prepareStatement(
            """
                    SELECT idPersona FROM expDB.testigo  
                    WHERE expDB.testigo.idGestionado = ?
            """
            );
            consulta.setInt(1, idGestionado);
            resultado = consulta.executeQuery();

                while (resultado.next()) {
                    //FASE 3: EXPORTAR LOS DATOS PERSONALES DEL TESTIGO
                    exportPersona(resultado.getInt(1));
                }
                return 0;

        } catch (SQLException e) {
            telemetria.logActivity("Error al exportar los datos de gestionado x testigo.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);            
        }
        return -1;
    }
    
    public int exportGestionadoXAcompañante(int idGestionado){
        try {
            //FASE 1: INSERTAR ACOMPAÑANTE
            consulta = conexion.sqliteConexion.prepareStatement(SQL.EXPORT_ACOMPAÑANTE_GESTIONADO.toString());
            consulta.setInt(1, idGestionado);
            consulta.executeUpdate();
            
            //FASE 2: OBTENER LA PERSONA RELACIONADA AL ACOMPAÑANTE
            consulta = conexion.sqliteConexion.prepareStatement(
            """
                    SELECT idPersona FROM expDB.acompañante
                    WHERE expDB.acompañante.idGestionado = ?
            """
            );
            consulta.setInt(1, idGestionado);
            resultado = consulta.executeQuery();

                while (resultado.next()) {
                    //FASE 3: EXPORTAR LOS DATOS PERSONALES DEL ACOMPAÑANTE
                    exportPersona(resultado.getInt(1));
                }
                return 0;

        } catch (SQLException e) {
            telemetria.logActivity("Error al exportar los datos de gestionado x acompañante.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);            
        }
        return -1;
    }
    
    public int exportGestionadoXRepresentante(int idGestionado){
                try {
            //FASE 1: INSERTAR PRESENTANTE
            consulta = conexion.sqliteConexion.prepareStatement(SQL.EXPORT_REPRESENTANTE_GESTIONADO.toString());
            consulta.setInt(1, idGestionado);
            consulta.executeUpdate();
            
            //FASE 2: OBTENER LA PERSONA RELACIONADA AL REPRESENTANTE
            consulta = conexion.sqliteConexion.prepareStatement(
            """
            SELECT idPersona FROM expDB.representante
            WHERE expDB.representante.idGestionado = ?
            """
            );
            consulta.setInt(1, idGestionado);
            resultado = consulta.executeQuery();

                while (resultado.next()) {
                    //FASE 3: EXPORTAR LOS DATOS PERSONALES DEL REPRESENTANTE
                    exportPersona(resultado.getInt(1));
                }
                return 0;

        } catch (SQLException e) {
            telemetria.logActivity("Error al exportar los datos de gestionado x representante.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);            
        }
        return -1;
    }
    
    public int exportInspectorXComparecencia(int idComparecencia){
            try {
            //FASE 1: INSERTAR RELACION INSPECTORXCOMPARECENCIA SELECCIONADA
            consulta = conexion.sqliteConexion.prepareStatement(SQL.EXPORT_INSPECTORXCOMPARECENCIAS.toString());
            consulta.setInt(1, idComparecencia);
            consulta.executeUpdate();
            
            //FASE 2: OBTENER TODOS LOS INSPECTORES RELACIONADOS CON ESA COMPARECENCIA
            consulta = conexion.sqliteConexion.prepareStatement(
            """
                    SELECT idPersona FROM expDB.inspectorxcomparecencia  
                    WHERE expDB.inspectorxcomparecencia.idComparecencia = ?
            """
            );
            consulta.setInt(1, idComparecencia);
            resultado = consulta.executeQuery();

                while (resultado.next()) {
                    //FASE 3: EXPORTAR LOS DATOS DE SUS USUARIOS
                    exportInspector(resultado.getInt(1));
                }
                return 0;

        } catch (SQLException e) {
            telemetria.logActivity("Error al exportar los datos de inspector x comparecencia.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);            
        }
        return -1;
    }
    
    public int exportInspector(int idPersona){
            try {
            //FASE 1: INSERTAR RELACION AUDIOXCOMPARECENCIA SELECCIONADA
            consulta = conexion.sqliteConexion.prepareStatement(SQL.EXPORT_INSPECTOR.toString());
            consulta.setInt(1, idPersona);
            consulta.executeUpdate();
            
            /*
            //FASE 2: OBTENER EL USUARIO RELACIONADO CON EL INSPECTOR
            consulta = conexion.sqliteConexion.prepareStatement(
            """
                    SELECT idPersona FROM expDB.Inspector
                    WHERE expDB.inspector.idPersona = ?
            """
            );
            consulta.setInt(1, idPersona);
            resultado = consulta.executeQuery();

                while (resultado.next()) {
                    //FASE 3: EXPORTAR LOS DATOS DE SUS USUARIOS
                }
            */
            exportUsuario(idPersona);
            return 0;

        } catch (SQLException e) {
            telemetria.logActivity("Error al exportar los datos de un inspector.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);            
        }
        return -1;
    }
    
    public int exportUsuario(int idPersona){
        try {
            //FASE 1: INSERTAR PRESENTANTE
            consulta = conexion.sqliteConexion.prepareStatement(SQL.EXPORT_USUARIO.toString());
            consulta.setInt(1, idPersona);
            consulta.executeUpdate();
            
            /*
            //FASE 2: OBTENER LA PERSONA RELACIONADA AL USUARIO
            consulta = conexion.sqliteConexion.prepareStatement(
            """
                    SELECT idPersona FROM expDB.persona
                    WHERE expDB.persona.idPersona = ?
            """
            );
            consulta.setInt(1, idPersona);
            resultado = consulta.executeQuery();

                while (resultado.next()) {
                    //FASE 3: EXPORTAR LOS DATOS PERSONALES DEL USUARIO
                }
            */
            exportPersona(idPersona);
            return 0;
        } catch (SQLException e) {
            telemetria.logActivity("Error al exportar los datos de un usuario.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);            
        }
        return -1;
    }
    
    public int exportPersona(int idPersona){
        try {
            //FASE 1: INSERTAR LA PERSONA
            consulta = conexion.sqliteConexion.prepareStatement(SQL.EXPORT_PERSONA.toString());
            consulta.setInt(1, idPersona);
            consulta.executeUpdate();
            //FASE 2: REGRESAR A FUNCION ANTERIOR
            return 0;
        } catch (SQLException e) {
            telemetria.logActivity("Error al exportar los datos de la persona.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);            
        }
        return -1;
    }
    
    public int exportPersonaJuridica(int idPersonaJuridica){
           try {
            //FASE 1: INSERTAR LA PERSONA JURIDICA
            consulta = conexion.sqliteConexion.prepareStatement(SQL.EXPORT_PERSONA_JURIDICA.toString());
            consulta.setInt(1, idPersonaJuridica);
            consulta.executeUpdate();
            //FASE 2: REGRESAR A FUNCION ANTERIOR
            return 0;
        } catch (SQLException e) {
            telemetria.logActivity("Error al exportar los datos de una persona juridica.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);            
        }
        return -1;
    }

    
/*
DIAGRAMA DE FLUJO PARA IMPORTAR
[DEBE CUMPLIR TODAS LAS RESTRICCIONES Y LLAVES FORANEAS]
=========================================================
[INICIO]
|    
o- persona (ACTUALIZAR IDS PARA TODO)
|
o- personajuridica (ACTUALIZAR IDS PARA GESTIONANTE Y GESTIONADO) <---- ESTAS AQUI
|
o- usuario
|  |
|  o- inspector  
|     
o- comparecencia (ACTUALIZAR IDS PARA GESTIONANTE, GESTIONADO, CXA)
|    
o- audio (ACTUALIZAR IDS EN CXA, ACTUALIZAR PATHS)
|
o- inspectorxcomparecencia
|
o- comparecenciaxaudio
|
o- gestionado & gestionante (ACTUALIZAR IDS)
    |
    o- representante
    |    
    o- testigo
    |
    o- acompañante
    
PROCEDIMIENTO    
===========================================================    
-> GUARDAR VIEJO ID EN MEMORIA
|
|-> BORRAR VIEJO ID DE LA DB TEMPORAL (INCREMENTAL BLOCK)
|
|--> INSERTAR LA PERSONA
|
|---> OBTENER NUEVO ID DE LA ULTIMA FILA DE LA TABLA
|
|----> ACTUALIZAR TODAS LAS TABLAS DE DB TEMPORAL
       QUE TIENEN EL IDPERSONA VIEJO GUARDADO
       EN MEMORIA, INSERTAR EL NUEVO ID    
*/  

    
    public void importarLaComparecencia(String thePATH){
        try {
            //FASE 1: UNIR LA BASE DE DATOS
            conexion.abrirConexion();
            pathDATADB = thePATH;
            String sqlQuery = SQL.ATTACH_DATADB.toString().replace("[DB]", pathDATADB);
            consulta = conexion.sqliteConexion.prepareStatement(sqlQuery);
            consulta.executeUpdate();

            //FASE 2: COMENZAR PROCESO
            importPersona();
            importPersonaJuridica();
            importUsuario();
            importInspector();
            importComparecencia();
            importAudio();
            importInspectorXComparecencia();
            importAudioXComparecencia();
            importGestionado();
            importGestionante();
            importTestigo();
            importAcompañante();
            importRepresentante();
        } catch (SQLException e) {
            telemetria.logActivity("Error al importar los datos de la comparecencia.");
            telemetria.logActivity("SQLException: " + e.getMessage());
            telemetria.logActivity("SQLState: " + e.getSQLState());
            telemetria.logActivity("VendorError: " + e.getErrorCode());
            telemetria.logException(e);            
        } finally {
            closeAll();
        }
    }
    
    // /!\: IMPORTANT TABLE
    public void importPersona(){
        try{
            int rowSize = 0;
            //FASE 1: AVERIGUAR NUMERO DE FILAS A IMPORTAR
            String sqlQuery = SQL.GET_NUMBER_ROWS_PERSONA.toString();
            consulta = conexion.sqliteConexion.prepareStatement(sqlQuery);
            resultado = consulta.executeQuery();

            if(resultado.next()){
                rowSize = resultado.getInt(1);
                
                int oldID;
                int currentRow;
                //CICLO CON NUMERO DE FILAS A IMPORTAR EN ESTA TABLA
                for(currentRow = 1; currentRow <= rowSize; currentRow++){
                        //FASE 2: GUARDAR EL VIEJO ID
                        consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_ID_PERSONA_BYROW.toString());
                        consulta.setInt(1, currentRow);
                        resultado = consulta.executeQuery();
                        oldID = resultado.getInt(1);

                        //FASE 3: NULLEAR EL ID PARA PODER INSERTAR LOS DATOS
                        consulta = conexion.sqliteConexion.prepareStatement(SQL.NULL_ID_PERSONA.toString());
                        consulta.setInt(1, currentRow);
                        consulta.executeUpdate();

                        //FASE 4: INSERTAR LA PERSONA EN LA BASE DE DATOS PRINCIPAL
                        consulta = conexion.sqliteConexion.prepareStatement(SQL.IMPORT_PERSONA.toString());
                        consulta.setInt(1, currentRow);
                        consulta.executeUpdate();

                        //FASE 5: ACTUALIZAR TODAS LAS TABLAS QUE DEPENDIAN DEL ID
                        updatePersonaDependencies(currentRow, oldID);

                }
            }else{//sin filas: nada que importar y actualizar.
                return;
            }
        } catch (Exception e) {
            telemetria.logActivity("Error al importar los datos de persona.");
            telemetria.logException(e);
        }
    }
    
    public void updatePersonaDependencies(int currentRow, int oldID){
        try{
            //FASE 1: SE ACTUALIZA LA FILA RECIEN INSERTADA PARA UTILIZARLA COMO REFERENCIA
            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_PERSONA_PERSONA.toString());
            consulta.setInt(1, currentRow);
            consulta.executeUpdate();
            
            //FASE 2: SE ACTUALIZACIAN EL RESTO DE TABLAS CON BASE EN ESE ID
            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_PERSONA_USUARIO.toString());
            consulta.setInt(1, currentRow);
            consulta.setInt(2, oldID);
            consulta.executeUpdate();

            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_PERSONA_INSPECTOR.toString());
            consulta.setInt(1, currentRow);
            consulta.setInt(2, oldID);
            consulta.executeUpdate();
            
            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_PERSONA_INSPECTORXCOMPARECENCIAS.toString());
            consulta.setInt(1, currentRow);
            consulta.setInt(2, oldID);
            consulta.executeUpdate();

            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_PERSONA_GESTIONADO.toString());
            consulta.setInt(1, currentRow);
            consulta.setInt(2, oldID);
            consulta.executeUpdate();

            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_PERSONA_GESTIONANTE.toString());
            consulta.setInt(1, currentRow);
            consulta.setInt(2, oldID);
            consulta.executeUpdate();

            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_PERSONA_TESTIGO.toString());
            consulta.setInt(1, currentRow);
            consulta.setInt(2, oldID);
            consulta.executeUpdate();

            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_PERSONA_ACOMPAÑANTE.toString());
            consulta.setInt(1, currentRow);
            consulta.setInt(2, oldID);
            consulta.executeUpdate();

            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_PERSONA_REPRESENTANTE.toString());
            consulta.setInt(1, currentRow);
            consulta.setInt(2, oldID);
            consulta.executeUpdate();
        
        }catch(Exception e){
            telemetria.logActivity("Error al actualizar los datos de persona durante importancion.");
            telemetria.logException(e); 
        }

        
    }
    
    // /!\: IMPORTANT TABLE
    public void importPersonaJuridica(){
        try{
            int rowSize = 0;
            String sqlQuery = SQL.GET_NUMBER_ROWS_PERSONA_JURIDICA.toString();
            consulta = conexion.sqliteConexion.prepareStatement(sqlQuery);
            resultado = consulta.executeQuery();

            if(resultado.next()){
                rowSize = resultado.getInt(1);
                
                int oldID;
                int currentRow;
                for(currentRow = 1; currentRow <= rowSize; currentRow++){

                        consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_ID_PERSONA_JURIDICA_BYROW.toString());
                        consulta.setInt(1, currentRow);
                        resultado = consulta.executeQuery();
                        oldID = resultado.getInt(1);

                        consulta = conexion.sqliteConexion.prepareStatement(SQL.NULL_ID_PERSONA_JURIDICA.toString());
                        consulta.setInt(1, currentRow);
                        consulta.executeUpdate();

                        consulta = conexion.sqliteConexion.prepareStatement(SQL.IMPORT_PERSONA_JURIDICA.toString());
                        consulta.setInt(1, currentRow);
                        consulta.executeUpdate();

                        updatePersonaJuridicaDependencies(currentRow, oldID);

                }
            }else{
                return;
            }
        } catch (Exception e) {
            telemetria.logActivity("Error al importar los datos de persona juridica.");
            telemetria.logException(e);
        }
    }
    
    public void updatePersonaJuridicaDependencies(int currentRow, int oldID){
        try{
            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_PERSONA_JURIDICA.toString());
            consulta.setInt(1, currentRow);
            consulta.executeUpdate();
            
            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_PERSONA_JURIDICA_GESTIONADO.toString());
            consulta.setInt(1, currentRow);
            consulta.setInt(2, oldID);
            consulta.executeUpdate();

            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_PERSONA_JURIDICA_GESTIONANTE.toString());
            consulta.setInt(1, currentRow);
            consulta.setInt(2, oldID);
            consulta.executeUpdate();
        }catch(Exception e){
            telemetria.logActivity("Error al actualizar los datos de persona juridica durante importancion.");
            telemetria.logException(e);
        }
    }

    public void importUsuario(){
        try{
            consulta = conexion.sqliteConexion.prepareStatement(SQL.IMPORT_USUARIO.toString());
            consulta.executeUpdate();
        } catch (Exception e) {
            telemetria.logActivity("Error al importar los datos de usuario");
            telemetria.logException(e);
        } 
    }
    
    public void importInspector(){
        try{

            consulta = conexion.sqliteConexion.prepareStatement(SQL.IMPORT_INSPECTOR.toString());
            consulta.executeUpdate();

        } catch (Exception e) {
            telemetria.logActivity("Error al importar los datos de inspector.");
            telemetria.logException(e);
        }
    }
    
    // /!\: IMPORTANT TABLE
    public void importComparecencia(){
        try{
            int rowSize = 0;
            String sqlQuery = SQL.GET_NUMBER_ROWS_COMPARECENCIA.toString();
            consulta = conexion.sqliteConexion.prepareStatement(sqlQuery);
            resultado = consulta.executeQuery();

            if(resultado.next()){
                rowSize = resultado.getInt(1);
                
                int oldID;
                int currentRow;
                for(currentRow = 1; currentRow <= rowSize; currentRow++){

                        consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_ID_COMPARECENCIA_BYROW.toString());
                        consulta.setInt(1, currentRow);
                        resultado = consulta.executeQuery();
                        oldID = resultado.getInt(1);

                        consulta = conexion.sqliteConexion.prepareStatement(SQL.NULL_ID_COMPARECENCIA.toString());
                        consulta.setInt(1, currentRow);
                        consulta.executeUpdate();

                        consulta = conexion.sqliteConexion.prepareStatement(SQL.IMPORT_COMPARECENCIA.toString());
                        consulta.setInt(1, currentRow);
                        consulta.executeUpdate();

                        updateComparecenciaDependencies(currentRow, oldID);

                }
            }else{
                return;
            }
        } catch (Exception e) {
            telemetria.logActivity("Error al actualizar los datos de comparecencia durante importancion.");
            telemetria.logException(e);
        }
    }

    public void updateComparecenciaDependencies(int currentRow, int oldID){
        try{
            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_COMPARECENCIA.toString());
            consulta.setInt(1, currentRow);
            consulta.executeUpdate();
            
            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_COMPARECENCIA_GESTIONADO.toString());
            consulta.setInt(1, currentRow);
            consulta.setInt(2, oldID);
            consulta.executeUpdate();

            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_COMPARECENCIA_GESTIONANTE.toString());
            consulta.setInt(1, currentRow);
            consulta.setInt(2, oldID);
            consulta.executeUpdate();
            
            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_COMPARECENCIA_AXC.toString());
            consulta.setInt(1, currentRow);
            consulta.setInt(2, oldID);
            consulta.executeUpdate();
            
            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_COMPARECENCIA_IXC.toString());
            consulta.setInt(1, currentRow);
            consulta.setInt(2, oldID);
            consulta.executeUpdate();
        
        }catch(Exception e){
            telemetria.logActivity("Error al actualizar los datos de comparecencia durante importancion.");
            telemetria.logException(e);
        }
    }

    public void importAudio(){
        try{
            int rowSize = 0;
            String sqlQuery = SQL.GET_NUMBER_ROWS_AUDIO.toString();
            consulta = conexion.sqliteConexion.prepareStatement(sqlQuery);
            resultado = consulta.executeQuery();

            if(resultado.next()){
                rowSize = resultado.getInt(1);
                
                int oldID;
                int currentRow;
                String newPath;
                for(currentRow = 1; currentRow <= rowSize; currentRow++){
                        
                        newPath = updateAudioPath(currentRow);
                        consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_AUDIO_PATH.toString());
                        consulta.setString(1, newPath);
                        consulta.setInt(2, currentRow);
                        consulta.executeUpdate();

                        newPath = updateAnotacionesPath(currentRow);
                        consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ANOTACIONES_PATH.toString());
                        consulta.setString(1, newPath);
                        consulta.setInt(2, currentRow);
                        consulta.executeUpdate();
                        
                        consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_ID_AUDIO_BYROW.toString());
                        consulta.setInt(1, currentRow);
                        resultado = consulta.executeQuery();
                        oldID = resultado.getInt(1);

                        consulta = conexion.sqliteConexion.prepareStatement(SQL.NULL_ID_AUDIO.toString());
                        consulta.setInt(1, currentRow);
                        consulta.executeUpdate();

                        consulta = conexion.sqliteConexion.prepareStatement(SQL.IMPORT_AUDIO.toString());
                        consulta.setInt(1, currentRow);
                        consulta.executeUpdate();

                        updateAudioDependencies(currentRow, oldID);

                }
            }else{
                return;
            }
        } catch (Exception e) {
            telemetria.logActivity("Error al importar los datos de audio.");
            telemetria.logException(e);
        }        
    }
    
    public void updateAudioDependencies(int currentRow, int oldID){
        try{
            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_AUDIO.toString());
            consulta.setInt(1, currentRow);
            consulta.executeUpdate();
            
            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_AUDIO_AXC.toString());
            consulta.setInt(1, currentRow);
            consulta.setInt(2, oldID);
            consulta.executeUpdate();
        }catch(Exception e){
            telemetria.logActivity("Error al actualizar los datos de audio durante importancion.");
            telemetria.logException(e);
        }
    }
    
    public String updateAudioPath(int currentRow){
        try{
            //se obtiene el directorio relativo
            String newPath = methods.Global.appConfig.getProperty("comparecencias_path");
            String fileName = "";
            
            //se obtiene el nombre del audio
            consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_AUDIO_PATH_BYROW.toString());
            consulta.setInt(1, currentRow);
            resultado = consulta.executeQuery();

            if(resultado.next()){
                    fileName = resultado.getString(1);
                    fileName = fileName.replaceAll(".*(?=.{33})", "");
            }

            return newPath + fileName;
        }catch(Exception e){
            telemetria.logActivity("Error al actualizar el path del audio..");
            telemetria.logException(e);
            return "";
        }
    }
    
    public String updateAnotacionesPath(int currentRow){
        try{
            String newPath = methods.Global.appConfig.getProperty("comparecencias_path");
            String fileName = "";

            consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_ANOTACIONES_PATH_BYROW.toString());
            consulta.setInt(1, currentRow);
            resultado = consulta.executeQuery();

            if(resultado.next()){
                fileName = resultado.getString(1);
                fileName = fileName.replaceAll(".*(?=.{34})", "");
            }
            
            return newPath + fileName;
        }catch(Exception e){
            telemetria.logActivity("Error al actualizar el path de anotaciones.");
            telemetria.logException(e);
            return "";
        }
    }
    
    public void importInspectorXComparecencia(){
        try{
            consulta = conexion.sqliteConexion.prepareStatement(SQL.IMPORT_INSPECTORXCOMPARECENCIAS.toString());
            consulta.executeUpdate();
        } catch (Exception e) {
            telemetria.logActivity("Error al importar los datos de inspectorxcomparecencia");
            telemetria.logException(e);
        }  
    }
    
    public void importAudioXComparecencia(){
        try{

            consulta = conexion.sqliteConexion.prepareStatement(SQL.IMPORT_AUDIOXCOMPARECENCIAS.toString());
            consulta.executeUpdate();

        } catch (Exception e) {
            telemetria.logActivity("Error al importar los datos de comparecenciaxaudio");
            telemetria.logException(e);
        }   
    }
            
    // /!\: IMPORTANT TABLE
    public void importGestionado(){
        try{
            int rowSize = 0;
            String sqlQuery = SQL.GET_NUMBER_ROWS_GESTIONADO.toString();
            consulta = conexion.sqliteConexion.prepareStatement(sqlQuery);
            resultado = consulta.executeQuery();

            if(resultado.next()){
                rowSize = resultado.getInt(1);
                
                int oldID;
                int currentRow;
                for(currentRow = 1; currentRow <= rowSize; currentRow++){

                        consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_ID_GESTIONADO_BYROW.toString());
                        consulta.setInt(1, currentRow);
                        resultado = consulta.executeQuery();
                        oldID = resultado.getInt(1);

                        consulta = conexion.sqliteConexion.prepareStatement(SQL.NULL_ID_GESTIONADO.toString());
                        consulta.setInt(1, currentRow);
                        consulta.executeUpdate();

                        consulta = conexion.sqliteConexion.prepareStatement(SQL.IMPORT_GESTIONADO.toString());
                        consulta.setInt(1, currentRow);
                        consulta.executeUpdate();

                        updateGestionadoDependencies(currentRow, oldID);

                }
            }else{
                return;
            }
        } catch (Exception e) {
            telemetria.logActivity("Error al importar los datos de gestionado.");
            telemetria.logException(e);
        }           
    }
    
    public void updateGestionadoDependencies(int currentRow, int oldID){
        try{
            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_GESTIONADO.toString());
            consulta.setInt(1, currentRow);
            consulta.executeUpdate();
            
            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_GESTIONADO_TESTIGO.toString());
            consulta.setInt(1, currentRow);
            consulta.setInt(2, oldID);
            consulta.executeUpdate();

            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_GESTIONADO_ACOMPAÑANTE.toString());
            consulta.setInt(1, currentRow);
            consulta.setInt(2, oldID);
            consulta.executeUpdate();
            
            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_GESTIONADO_REPRESENTANTE.toString());
            consulta.setInt(1, currentRow);
            consulta.setInt(2, oldID);
            consulta.executeUpdate();        
        }catch(Exception e){
            telemetria.logActivity("Error al actualizar los datos de gestionado durante importancion.");
            telemetria.logException(e);
        }
    }

    // /!\: IMPORTANT TABLE
    public void importGestionante(){
        try{
            int rowSize = 0;
            String sqlQuery = SQL.GET_NUMBER_ROWS_GESTIONANTE.toString();
            consulta = conexion.sqliteConexion.prepareStatement(sqlQuery);
            resultado = consulta.executeQuery();

            if(resultado.next()){
                rowSize = resultado.getInt(1);
                
                int oldID;
                int currentRow;
                for(currentRow = 1; currentRow <= rowSize; currentRow++){

                        consulta = conexion.sqliteConexion.prepareStatement(SQL.GET_ID_GESTIONANTE_BYROW.toString());
                        consulta.setInt(1, currentRow);
                        resultado = consulta.executeQuery();
                        oldID = resultado.getInt(1);

                        consulta = conexion.sqliteConexion.prepareStatement(SQL.NULL_ID_GESTIONANTE.toString());
                        consulta.setInt(1, currentRow);
                        consulta.executeUpdate();

                        consulta = conexion.sqliteConexion.prepareStatement(SQL.IMPORT_GESTIONANTE.toString());
                        consulta.setInt(1, currentRow);
                        consulta.executeUpdate();

                        updateGestionanteDependencies(currentRow, oldID);

                }
            }else{
                return;
            }
        } catch (Exception e) {
            telemetria.logActivity("Error al importar los datos de gestionante.");
            telemetria.logException(e);
        }       
    }
    
    public void updateGestionanteDependencies(int currentRow, int oldID){
        try{
            //FASE 1: SE ACTUALIZA LA FILA RECIEN INSERTADA PARA UTILIZARLA COMO REFERENCIA
            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_GESTIONANTE.toString());
            consulta.setInt(1, currentRow);
            consulta.executeUpdate();
            
            //FASE 2: SE ACTUALIZACIAN EL RESTO DE TABLAS CON BASE EN ESE ID
            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_GESTIONANTE_TESTIGO.toString());
            consulta.setInt(1, currentRow);
            consulta.setInt(2, oldID);
            consulta.executeUpdate();

            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_GESTIONANTE_ACOMPAÑANTE.toString());
            consulta.setInt(1, currentRow);
            consulta.setInt(2, oldID);
            consulta.executeUpdate();
            
            consulta = conexion.sqliteConexion.prepareStatement(SQL.UPDATE_ID_GESTIONANTE_REPRESENTANTE.toString());
            consulta.setInt(1, currentRow);
            consulta.setInt(2, oldID);
            consulta.executeUpdate();
        }catch(Exception e){
            telemetria.logActivity("Error al actualizar los datos de gestionante durante importancion.");
            telemetria.logException(e);
        }
    }

    public void importTestigo(){
        try{
            consulta = conexion.sqliteConexion.prepareStatement(SQL.IMPORT_TESTIGO.toString());
            consulta.executeUpdate();
        } catch (Exception e) {
            telemetria.logActivity("Error al importar los datos de testigo.");
            telemetria.logException(e);
        }
    }

    public void importAcompañante(){
        try{
            consulta = conexion.sqliteConexion.prepareStatement(SQL.IMPORT_ACOMPAÑANTE.toString());
            consulta.executeUpdate();
        } catch (Exception e) {
            telemetria.logActivity("Error al importar los datos de acompañante.");
            telemetria.logException(e);
        }
    }

    public void importRepresentante(){
        try{
            consulta = conexion.sqliteConexion.prepareStatement(SQL.IMPORT_REPRESENTANTE.toString());
            consulta.executeUpdate();
        } catch (Exception e) {
            telemetria.logActivity("Error al importar los datos de representante.");
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