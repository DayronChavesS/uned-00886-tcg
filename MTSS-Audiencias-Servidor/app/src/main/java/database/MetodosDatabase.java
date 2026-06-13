package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import models.Inspector;
import models.Oficina;
import models.Persona;
import models.Puesto;
import models.Region;
import models.Usuario;

public class MetodosDatabase{
    
    private ConexionDatabase conexion;
    private PreparedStatement consulta;
    private ResultSet resultado;
    private Map<String, Map<String,String>> downloadData = new HashMap<>();
    private Map<String, Map<String,String>> uploadData = new HashMap<>();
    
    public MetodosDatabase() {
        initVariables();
    }
    
    private void initVariables(){

        conexion = new ConexionDatabase();
    }
    
    private void closeAll() {
        try {
            if (conexion.mySqlConnection != null) {
                conexion.cerrarConexion();
            }
            if (consulta != null) {
                consulta.close();
                consulta = null;
            }
            if (resultado != null) {
                resultado.close();
                resultado = null;
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar conexion con base de datos.");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }

    public Boolean cedulaIsUnique(String cedula)  {
        try {
            conexion.abrirConexion();
            consulta = conexion.mySqlConnection.prepareStatement(SQL.CHECK_CEDULA.toString());
            consulta.setString(1, cedula);
            resultado = consulta.executeQuery();

            if (!resultado.next()) {
                System.out.println("La cedula es unica.");
                closeAll();
                return true;
            } else {
                System.out.println("cedula no es unica.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar cedula.");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            closeAll();
        }
        return null;
    }

    public Boolean emailIsUnique(String email)  {
        try {
            conexion.abrirConexion();
            consulta = conexion.mySqlConnection.prepareStatement(SQL.CHECK_EMAIL.toString());
            consulta.setString(1, email);
            resultado = consulta.executeQuery();

            if (!resultado.next()) {
                System.out.println("email es unico.");
                closeAll();
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al determinar si el correo es unico.");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            closeAll();
        }
        System.out.println("email no es unico.");
        return false;
    }

    public Inspector guardarInspector(Inspector inspector) {
        insertarPersona(inspector.getPersona());

        inspector.getPersona().setIdPersona(
                getIdLastPersona());
        
        inspector.getUsuario().getPersona().setIdPersona(
                inspector.getPersona().getIdPersona());

        insertarUsuario(inspector.getUsuario());

        insertarInspector(inspector);
        return inspector;
    }

    public int getIdLastPersona() {
        PreparedStatement consultaLocal = null;
        ResultSet resultadoLocal = null;

        try {
            conexion.abrirConexion();
            consultaLocal = conexion.mySqlConnection.prepareStatement(SQL.ID_LAST_ROW_PERSONA.toString());
            resultadoLocal = consultaLocal.executeQuery();

            if (resultadoLocal.next()) {
                System.out.println("Se obtuvo un idPersona.");
                int elId = resultadoLocal.getInt(1);
                return elId;
            }
            consultaLocal.close();
            resultadoLocal.close();

        } catch (SQLException e) {
            System.out.println("Error al obtener el ID generado por la base de datos.");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            System.out.println(e);
        } finally {
            consultaLocal = null;
            resultadoLocal = null;
        }

        System.out.println("No hay filas.");
        return -1;
    }

    public Boolean insertarInspector(Inspector inspector){
        try {
            conexion.abrirConexion();
            consulta = conexion.mySqlConnection.prepareStatement(SQL.INSERT_INSPECTOR.toString());

            consulta.setInt(1, inspector.getPersona().getIdPersona());
            consulta.setInt(2, inspector.getPuesto().ordinal());
            consulta.setInt(3, inspector.getRegion().ordinal());
            consulta.setInt(4, inspector.getOficina().ordinal());

            consulta.executeUpdate();
            System.out.println("Se inserto un inspector en la base de datos.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error al guardar datos de usuario en base de datos.");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            System.out.println(e);
            return false;
        } finally {
            closeAll();
        }
    }

    public Boolean insertarPersona(Persona persona) {
        try {
            conexion.abrirConexion();
            consulta = conexion.mySqlConnection.prepareStatement(SQL.INSERT_PERSONA.toString());

            consulta.setInt(1, getIdLastPersona() + 1);
            consulta.setString(2, persona.getCedula());
            consulta.setString(3, persona.getPrimerNombre());
            consulta.setString(4, persona.getSegundoNombre());
            consulta.setString(5, persona.getPrimerApellido());
            consulta.setString(6, persona.getSegundoApellido());

            consulta.executeUpdate();
            System.out.println("Se inserto una persona en la base de datos.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error al guardar datos de persona en base de datos.");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            System.out.println(e);
            return false;
        } finally {
            closeAll();
        }
    }

    public Boolean insertarUsuario(Usuario usuario) {
        try {
            conexion.abrirConexion();
            consulta = conexion.mySqlConnection.prepareStatement(SQL.INSERT_USUARIO.toString());

            consulta.setInt(1, usuario.getPersona().getIdPersona());
            consulta.setString(2, usuario.getEmail());
            consulta.setString(3, usuario.getContraseña());
            consulta.setBoolean(4, usuario.getEnLinea());

            consulta.executeUpdate();
            System.out.println("Se inserto un usuario en la base de datos.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error al guardar datos de usuario en base de datos.");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            System.out.println(e);
            return false;
        } finally {
            closeAll();
        }
    }
    
    public int getIdInspector(String cedula) {
        try {
            conexion.abrirConexion();
            consulta = conexion.mySqlConnection.prepareStatement(SQL.GET_ID_INSPECTOR.toString());
            consulta.setString(1, cedula);
            resultado = consulta.executeQuery();

            if (resultado.next()) {
                System.out.println("Se obtuvo un idInspector.");
                int elId = resultado.getInt(1);
                closeAll();
                return elId;
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el idInspector generado por la base de datos.");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            System.out.println(e);
        } finally {
            closeAll();
        }
        System.out.println("No existe idInspector para esa cedula.");
        return -1;
    }

    public Inspector createObjectInspector(int idPersona) {
        if (idPersona == 0) {
            return null;
        }

        Inspector elInspector = new Inspector();
        ConexionDatabase conexionLocal = new ConexionDatabase();
        conexionLocal.abrirConexion();
        PreparedStatement consultaLocal = null;
        ResultSet resultadoLocal = null;
        try {
            conexionLocal.abrirConexion();
            consultaLocal = conexionLocal.mySqlConnection.prepareStatement(SQL.CREATE_OBJECT_INSPECTOR.toString());
            consultaLocal.setInt(1, idPersona);
            resultadoLocal = consultaLocal.executeQuery();

            if (resultadoLocal.next()) {
                System.out.println("Se obtuvo un objeto inspector.");
                elInspector.setPuesto(Puesto.values()[resultadoLocal.getInt(2)]);
                elInspector.setOficina(Oficina.values()[resultadoLocal.getInt(4)]);
                elInspector.setRegion(Region.values()[resultadoLocal.getInt(3)], Oficina.values()[resultadoLocal.getInt(4)]);
            } else {
                System.out.println("No existe ningun inspector con esa cedula.");
                return null;
            }
            consultaLocal.close();
            resultadoLocal.close();
        } catch (SQLException e) {
            System.out.println("Error al obtener el objeto inspector desde la base de datos.");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            System.out.println(e);
        } catch (Exception e) {
            System.out.println("Error al obtener el objeto inspector desde la base de datos.");
            System.out.println(e);
        } finally {
            conexionLocal.cerrarConexion();
            conexionLocal = null;
            consultaLocal = null;
            resultadoLocal = null;
        }
        elInspector.setPersona(createObjectPersona(idPersona));
        elInspector.setUsuario(createObjectUsuario(idPersona));
        return elInspector;
    }

    public Persona createObjectPersona(int idPersona) {
        if (idPersona == 0) {
            return null;
        }

        Persona laPersona = new Persona();
        ConexionDatabase conexionLocal = new ConexionDatabase();
        conexionLocal.abrirConexion();
        PreparedStatement consultaLocal;
        ResultSet resultadoLocal;

        try {

            conexionLocal.abrirConexion();
            consultaLocal = conexionLocal.mySqlConnection.prepareStatement(SQL.CREATE_OBJECT_PERSONA.toString());
            consultaLocal.setInt(1, idPersona);
            resultadoLocal = consultaLocal.executeQuery();

            if (resultadoLocal.next()) {
                System.out.println("Se obtuvo un objeto Persona.");
                laPersona.setIdPersona(resultadoLocal.getInt(1));
                laPersona.setCedula(resultadoLocal.getString(2), false);
                laPersona.setPrimerNombre(resultadoLocal.getString(3));
                laPersona.setSegundoNombre(resultadoLocal.getString(4));
                laPersona.setPrimerApellido(resultadoLocal.getString(5));
                laPersona.setSegundoApellido(resultadoLocal.getString(6));
            } else {
                System.out.println("No existe objeto Persona para ese Id.");
                return null;
            }

            consultaLocal.close();
            resultadoLocal.close();
            return laPersona;

        } catch (SQLException e) {
            System.out.println("Error al obtener el objeto persona desde la base de datos.");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            System.out.println(e);
        } catch (Exception e) {
            System.out.println("Error al obtener el objeto persona desde la base de datos.");
            System.out.println(e);

        } finally {
            conexionLocal.cerrarConexion();
            conexionLocal = null;
            consultaLocal = null;
            resultadoLocal = null;
        }
        return null;
    }

    public Usuario createObjectUsuario(int idPersona) {
        if (idPersona == 0) {
            return null;
        }

        Usuario elUsuario = new Usuario();
        ConexionDatabase conexionLocal = new ConexionDatabase();
        conexionLocal.abrirConexion();
        PreparedStatement consultaLocal;
        ResultSet resultadoLocal;

        try {

            conexionLocal.abrirConexion();
            consultaLocal = conexionLocal.mySqlConnection.prepareStatement(SQL.CREATE_OBJECT_USUARIO.toString());
            consultaLocal.setInt(1, idPersona);
            resultadoLocal = consultaLocal.executeQuery();

            if (resultadoLocal.next()) {
                System.out.println("Se obtuvo un objeto Usuario.");
                elUsuario.setEmail(resultadoLocal.getString(2), false);
                elUsuario.setContraseña(resultadoLocal.getString(3));
                elUsuario.setEnLinea(resultadoLocal.getBoolean(4));
            } else {
                System.out.println("No existe objeto Usuario para ese Id.");
                return null;
            }

            consultaLocal.close();
            resultadoLocal.close();

        } catch (SQLException e) {
            System.out.println("Error al obtener el objeto Usuario desde la base de datos.");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            System.out.println(e);
        } catch (Exception e) {
            System.out.println("Error al obtener el objeto Usuario desde la base de datos.");
            System.out.println(e);

        } finally {

            conexionLocal.cerrarConexion();
            conexionLocal = null;
            consultaLocal = null;
            resultadoLocal = null;
        }
        elUsuario.setPersona(createObjectPersona(idPersona));
        return elUsuario;
    }

    public Boolean userExist(String cedula, String contraseña) {
        try {
            conexion.abrirConexion();
            consulta = conexion.mySqlConnection.prepareStatement(SQL.VERIFY_USER.toString());
            consulta.setString(1, cedula);
            consulta.setString(2, contraseña);
            resultado = consulta.executeQuery();

            //si no hay resultados, no existe el usuario
            if (!resultado.next()) {
                System.out.println("El usuario ingresado no existe.");
                closeAll();
                return false;
            }

        } catch (SQLException ex) {
            System.out.println("Error en la verificación del usuario y contraseña");
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            System.out.println(ex);
            return false;
        } finally {
            closeAll();
        }
        System.out.println("El usuario ingresado existe.");
        return true;
    }

    public Boolean userIsAdmin(int idPersona) {
        try {
            conexion.abrirConexion();
            consulta = conexion.mySqlConnection.prepareStatement(SQL.CHECK_ADMIN.toString());
            consulta.setInt(1, idPersona);
            resultado = consulta.executeQuery();

            //si no hay resultados, no existe el usuario
            if (!resultado.next()) {
                System.out.println("El usuario ingresado no es administrador.");
                closeAll();
                return false;
            }

        } catch (SQLException ex) {
            System.out.println("Error en la verificación de privilegios de administrador.");
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            System.out.println(ex);
            return false;
        } finally {
            closeAll();
        }
        System.out.println("El usuario ingresado es administrador.");
        return true;
    }
    
    public void loginInspector(int idInspector) {
        new Thread(() -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.mySqlConnection.prepareStatement(SQL.LOGIN.toString());

                consulta.setInt(1, idInspector);
                consulta.executeUpdate();

                System.out.println("Se inicio la sesion.");
            } catch (SQLException e) {
                System.out.println("Error al iniciar la sesion.");
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
                System.out.println(e);
            } finally {
                closeAll();
            }
        }).start();
    }

    public void logoutInspector(int idInspector) {
        new Thread(() -> {
            try {
                conexion.abrirConexion();
                consulta = conexion.mySqlConnection.prepareStatement(SQL.LOGOUT.toString());

                consulta.setInt(1, idInspector);
                consulta.executeUpdate();

                System.out.println("Se cerro la sesion.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la sesion.");
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
                System.out.println(e);
            } finally {
                closeAll();
            }
        }).start();
    }

    
/*
DIAGRAMA DE FLUJO PARA DESCARGAR E IMPORTAR
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

    
    public void descargarLaComparecencia(Map theData){
        try {
            //FASE 0: SE OBTIENEN LOS DATOS
            this.uploadData =  theData;
            
            //FASE 1: ABRIR BASE DE DATOS
            conexion.abrirConexion();

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
            System.out.println("Error al importar los datos de la comparecencia.");
            System.out.println(e.getMessage());
            System.out.println(e);            
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
                    consulta = conexion.mySqlConnection.prepareStatement(SQL.ID_LAST_ROW_PERSONA.toString());
                    resultado = consulta.executeQuery();
                    while (resultado.next()) {
                        newID = resultado.getInt(1)+1;
                    }
                    
                    
                    consulta = conexion.mySqlConnection.prepareStatement(SQL.INSERT_PERSONA.toString());
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
            System.out.println("Error al importar los datos de una persona.");
            System.out.println(e);
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
            System.out.println("Error al actualizar los datos de persona durante la descarga.");
            System.out.println(e); 
        }

        
    }
    
    private void downloadPersonaJuridica(){
        int oldID = 0;
        int newID = 0;
        try {
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                if (mapTable.getKey().matches("(personajuridica)[0-9]+")) {
                    
                    consulta = conexion.mySqlConnection.prepareStatement(SQL.ID_LAST_ROW_PERSONAJURIDICA.toString());
                    resultado = consulta.executeQuery();
                    while (resultado.next()) {
                        newID = resultado.getInt(1)+1;
                    }
                    
                    consulta = conexion.mySqlConnection.prepareStatement(SQL.INSERT_PERSONAJURIDICA.toString());
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
            System.out.println("Error al importar los datos de una persona juridica.");
            System.out.println(e.getMessage());
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
            System.out.println("Error al actualizar los datos de persona juridica durante la descarga.");
            System.out.println(e); 
        }
    }

    private void downloadUsuario(){
        try{
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                if (mapTable.getKey().matches("(usuario)[0-9]+")) {

                    consulta = conexion.mySqlConnection.prepareStatement(SQL.INSERT_USUARIO.toString());
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
            System.out.println("Error al importar los datos de usuario.");
            System.out.println(e);
        }
    }
    
    private void downloadInspector(){
        try {
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                if (mapTable.getKey().matches("(inspector)(?!xcomparecencia)[0-9]+")) {

                    consulta = conexion.mySqlConnection.prepareStatement(SQL.INSERT_INSPECTOR.toString());
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
            System.out.println("Error al importar los datos de inspector");
            System.out.println(e);
        } 
    }
    
    private void downloadComparecencia(){
        int oldID = 0;
        int newID = 0;
        try{
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                if (mapTable.getKey().matches("comparecencia")) {

                    consulta = conexion.mySqlConnection.prepareStatement(SQL.ID_LAST_ROW_COMPARECENCIA.toString());
                    resultado = consulta.executeQuery();
                    while (resultado.next()) {
                        newID = resultado.getInt(1)+1;
                    }
                    
                    consulta = conexion.mySqlConnection.prepareStatement(SQL.INSERT_COMPARECENCIA.toString());
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
            System.out.println("Error al importar los datos de comparecencia durante la descarga.");
            System.out.println(e);
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
            System.out.println("Error al actualizar las dependencias de la comparecencia.");
            System.out.println(e); 
        }
    }

    private void downloadAudio(){
        int oldID = 0;
        int newID = 0;
        try{
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                if (mapTable.getKey().matches("(audio)[0-9]+")) {
                        
                    consulta = conexion.mySqlConnection.prepareStatement(SQL.ID_LAST_ROW_AUDIO.toString());
                    resultado = consulta.executeQuery();
                    while (resultado.next()) {
                        newID = resultado.getInt(1)+1;
                    }
                    
                    consulta = conexion.mySqlConnection.prepareStatement(SQL.INSERT_AUDIO.toString());
                    Map<String, String> theTable = mapTable.getValue();
                    
                    for (Map.Entry<String,String> mapColumns : theTable.entrySet()){
                    
                        //se actualizan los path
                        if (mapColumns.getKey().equals("pathArchivoAudio")){
                            
                            String newPath = System.getProperty("user.dir");
                            String oldPath =  mapColumns.getValue();
                            String fileName = oldPath.replaceAll(".*(?=.{33})", "");
                            String newFullPath = newPath+"\\REPOSITORIO_COMPARECENCIAS\\"+fileName;
                            mapColumns.setValue(newFullPath);
                            
                        }
                        else if(mapColumns.getKey().equals("pathArchivoAnotaciones")) {
                            
                            String newPath = System.getProperty("user.dir");
                            String oldPath =  mapColumns.getValue();
                            String fileName = oldPath.replaceAll(".*(?=.{34})", "");
                            String newFullPath = newPath+"\\REPOSITORIO_COMPARECENCIAS\\"+fileName;
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
            System.out.println("Error al importar datos del audio durante la descarga.");
            System.out.println(e);
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
            System.out.println("Error al actualizar los datos del audio durante la descarga.");
            System.out.println(e); 
        }
    }
        
    private void downloadInspectorXComparecencia(){
        try {
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                if (mapTable.getKey().matches("(inspectorxcomparecencia)[0-9]+")) {

                    consulta = conexion.mySqlConnection.prepareStatement(SQL.INSERT_INSPECTORXCOMPARECENCIA.toString());
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
            System.out.println("Error al importar la relacion inspector comparencia.");
            System.out.println(e);
        } 
    }
    
    private void downloadAudioXComparecencia(){
        try {
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                if (mapTable.getKey().matches("(audioxcomparecencia)[0-9]+")) {

                    consulta = conexion.mySqlConnection.prepareStatement(SQL.INSERT_AUDIOXCOMPARECENCIA.toString());
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
            System.out.println("Error al importar la relacion audio comparecencia.");
            System.out.println(e);
        }   
    }
    
    private void downloadGestionante(){
        int oldID = 0;
        int newID = 0;
        
        try {
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                if (mapTable.getKey().matches("(gestionante)")) {

                                        
                    consulta = conexion.mySqlConnection.prepareStatement(SQL.ID_LAST_ROW_GESTIONANTE.toString());
                    resultado = consulta.executeQuery();
                    while (resultado.next()) {
                        newID = resultado.getInt(1)+1;
                    }
                    
                    consulta = conexion.mySqlConnection.prepareStatement(SQL.INSERT_GESTIONANTE.toString());
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
            System.out.println("Error al importar los datos de gestionante");
            System.out.println(e);
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
            System.out.println("Error al actualizar las dependencias del gestionante.");
            System.out.println(e); 
        }
    }
    
    private void downloadGestionado(){
        int oldID = 0;
        int newID = 0;
        
        try {
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                if (mapTable.getKey().matches("(gestionado)")) {

                    consulta = conexion.mySqlConnection.prepareStatement(SQL.ID_LAST_ROW_GESTIONADO.toString());
                    resultado = consulta.executeQuery();
                    while (resultado.next()) {
                        newID = resultado.getInt(1)+1;
                    }
                    
                    consulta = conexion.mySqlConnection.prepareStatement(SQL.INSERT_GESTIONADO.toString());
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
            System.out.println("Error al importar los datos de gestionado");
            System.out.println(e);
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
            System.out.println("Error al actualizar las dependencias del gestionado.");
            System.out.println(e); 
        }
    }

    private void downloadTestigo(){        
        try {
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                if (mapTable.getKey().matches("(testigo)[0-9]+")) {

                    consulta = conexion.mySqlConnection.prepareStatement(SQL.INSERT_TESTIGO.toString());
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
            System.out.println("Error al importar los datos del testigo");
            System.out.println(e);
        } 
    }

    private void downloadAcompañante(){
        try {
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                if (mapTable.getKey().matches("(acompañante)[0-9]+")) {

                    consulta = conexion.mySqlConnection.prepareStatement(SQL.INSERT_ACOMPAÑANTE.toString());
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
            System.out.println("Error al importar los datos del acompañante");
            System.out.println(e);
        }  
    }

    private void downloadRepresentante(){
        try {
            for (Map.Entry<String, Map<String, String>> mapTable : uploadData.entrySet()) {

                if (mapTable.getKey().matches("(representante)[0-9]+")) {

                    consulta = conexion.mySqlConnection.prepareStatement(SQL.INSERT_REPRESENTANTE.toString());
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
            System.out.println("Error al importar los datos del representante");
            System.out.println(e);
        }  
    }
   
    
/*******************************************************************************/
    
    public Map extractComparecencia(int idComparecencia) {

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
            conexion.abrirConexion();
            consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_COMPARECENCIAS.toString());
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
            consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_AUDIOXCOMPARECENCIAS.toString());
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
            consulta = conexion.mySqlConnection.prepareStatement(
            """
            SELECT idAudio FROM audioxcomparecencia  
            WHERE audioxcomparecencia.idComparecencia = ?
            """
            );
            
            consulta.setInt(1, idComparecencia);
            elResultado = consulta.executeQuery();
            
            //SAVE audio [MULTI]
            while (elResultado.next()) {
                consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_AUDIO.toString());
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
            consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_GESTIONANTE.toString());
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
            consulta = conexion.mySqlConnection.prepareStatement(
            """
            SELECT idGestionante,idPersona,idPersonaJuridica FROM gestionante
            WHERE gestionante.idComparecencia = ?
            """
            );
            consulta.setInt(1, idComparecencia);
            elResultado = consulta.executeQuery();
            
            //SAVE gestionantexpersona
            int idPersona;
            while (elResultado.next()) {
                if (elResultado.getInt(2) != 0) {
                    idPersona = elResultado.getInt(2);
                    consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_PERSONA.toString());
                    consulta.setInt(1, idPersona);

                    ResultSet subResultado = consulta.executeQuery();
                    while (subResultado.next()) {
                        Map<String, String> gestionantePersona = new HashMap<>();
                        gestionantePersona.put("idPersona", String.valueOf(subResultado.getInt(1)));
                        gestionantePersona.put("cedula", subResultado.getString(2));
                        gestionantePersona.put("primerNombre", subResultado.getString(3));
                        gestionantePersona.put("segundoNombre", subResultado.getString(4));
                        gestionantePersona.put("primerApellido", subResultado.getString(5));
                        gestionantePersona.put("segundoApellido", subResultado.getString(6));
                        uploadData.put("persona" + personaCount, gestionantePersona);
                        personaCount++;
                    }
                }
            }
            
            //SAVE gestionantexpersonajuridica
            int idPersonaJuridica;
            while (elResultado.next()) {
                if (elResultado.getInt(3) != 0) {
                    idPersonaJuridica = elResultado.getInt(3);
                    consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_PERSONA_JURIDICA.toString());
                    consulta.setInt(1, idPersonaJuridica);

                    ResultSet subResultado = consulta.executeQuery();
                    while (subResultado.next()) {
                        Map<String, String> gestionantePersonaJuridica = new HashMap<>();
                        gestionantePersonaJuridica.put("idPersonaJuridica", String.valueOf(subResultado.getInt(1)));
                        gestionantePersonaJuridica.put("cedulaJuridica", subResultado.getString(2));
                        gestionantePersonaJuridica.put("nombreRazonSocial", subResultado.getString(3));
                        uploadData.put("personajuridica" + personaJuridicaCount, gestionantePersonaJuridica);
                        personaJuridicaCount++;
                    }
                }
            }
            
            
            //SAVE gestionado
            consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_GESTIONADO.toString());
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
            consulta = conexion.mySqlConnection.prepareStatement(
            """
            SELECT idGestionado,idPersona,idPersonaJuridica FROM gestionado
            WHERE gestionado.idComparecencia = ?
            """
            );
            consulta.setInt(1, idComparecencia);
            elResultado = consulta.executeQuery();
            
            //SAVE gestionadoxpersona
            idPersona = 0;
            while (elResultado.next()) {
                if (elResultado.getInt(2) != 0) {
                    //personaCount++;
                    idPersona = elResultado.getInt(2);
                    consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_PERSONA.toString());
                    consulta.setInt(1, idPersona);

                    ResultSet subResultado = consulta.executeQuery();
                    while (subResultado.next()) {
                        Map<String, String> gestionadoPersona = new HashMap<>();
                        gestionadoPersona.put("idPersona", String.valueOf(subResultado.getInt(1)));
                        gestionadoPersona.put("cedula", subResultado.getString(2));
                        gestionadoPersona.put("primerNombre", subResultado.getString(3));
                        gestionadoPersona.put("segundoNombre", subResultado.getString(4));
                        gestionadoPersona.put("primerApellido", subResultado.getString(5));
                        gestionadoPersona.put("segundoApellido", subResultado.getString(6));
                        uploadData.put("persona" + personaCount, gestionadoPersona);
                        personaCount++;
                    }
                }
            }
            
            //SAVE gestionadoxpersonajuridica
            idPersonaJuridica = 0;
            while (elResultado.next()) {
                if (elResultado.getInt(3) != 0) {
                    //personaJuridicaCount++;
                    idPersonaJuridica = elResultado.getInt(3);
                    consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_PERSONA_JURIDICA.toString());
                    consulta.setInt(1, idPersonaJuridica);

                    ResultSet subResultado = consulta.executeQuery();
                    while (subResultado.next()) {
                        Map<String, String> gestionadoPersonaJuridica = new HashMap<>();
                        gestionadoPersonaJuridica.put("idPersonaJuridica", String.valueOf(subResultado.getInt(1)));
                        gestionadoPersonaJuridica.put("cedulaJuridica", subResultado.getString(2));
                        gestionadoPersonaJuridica.put("nombreRazonSocial", subResultado.getString(3));
                        uploadData.put("personajuridica" + personaJuridicaCount, gestionadoPersonaJuridica);
                        personaJuridicaCount++;
                    }
                }
            }
            
            
            
            
            
            
            //ACOMPAÑANTES
            //GESTIONANTE
            
            //SAVE testigoxgestionante
            consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_TESTIGO_GESTIONANTE.toString());
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
            consulta = conexion.mySqlConnection.prepareStatement(
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
                consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_PERSONA.toString());
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
            consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_ACOMPAÑANTE_GESTIONANTE.toString());
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
            consulta = conexion.mySqlConnection.prepareStatement(
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
                consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_PERSONA.toString());
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
            consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_REPRESENTANTE_GESTIONANTE.toString());
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
            consulta = conexion.mySqlConnection.prepareStatement(
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
                consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_PERSONA.toString());
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
            consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_TESTIGO_GESTIONADO.toString());
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
            consulta = conexion.mySqlConnection.prepareStatement(
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
                consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_PERSONA.toString());
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
            consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_ACOMPAÑANTE_GESTIONADO.toString());
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
            consulta = conexion.mySqlConnection.prepareStatement(
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
                consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_PERSONA.toString());
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
            consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_REPRESENTANTE_GESTIONADO.toString());
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
            consulta = conexion.mySqlConnection.prepareStatement(
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
                consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_PERSONA.toString());
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
            consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_INSPECTORXCOMPARECENCIAS.toString());
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
            consulta = conexion.mySqlConnection.prepareStatement(
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
                consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_INSPECTOR.toString());
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
                
                consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_USUARIO.toString());
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
                
                consulta = conexion.mySqlConnection.prepareStatement(SQL.UPLOAD_PERSONA.toString());
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
            System.out.println("Error al extraer los datos de la comparecencia.");
            System.out.println(e);
        }finally{
            closeAll();
        }


        return null;

    }

/*******************************************************************************/
    
    public int getIdComparecenciaBySILAC(String codigoSILAC){
        int idComparecencia = 0;
        
        try{
            conexion.abrirConexion();
            consulta = conexion.mySqlConnection.prepareStatement(SQL.GET_ID_COMPARECENCIA_BY_SILAC.toString());
            consulta.setString(1, codigoSILAC);
            resultado = consulta.executeQuery();
            while (resultado.next()) {
                idComparecencia = resultado.getInt(1);
            }
        }catch(Exception e){
            System.out.println("Error al obtener el ultimo ID de comparecencia");
            System.out.println(e);
        }
        
        return idComparecencia;
    }
    
    public int getIdLastComparecencia(){
        int idComparecencia = 0;
        
        try{
            conexion.abrirConexion();
            consulta = conexion.mySqlConnection.prepareStatement(SQL.ID_LAST_ROW_COMPARECENCIA.toString());
            resultado = consulta.executeQuery();
            while (resultado.next()) {
                idComparecencia = resultado.getInt(1);
            }
        }catch(Exception e){
            System.out.println("Error al obtener el ultimo ID de comparecencia");
            System.out.println(e);
        }
        
        return idComparecencia;
    }
    
    public List<Integer> getAudioRelatedComparecencia(int idComparecencia){
        List<Integer> audioIDS =  new LinkedList<>();
        
        try {

            conexion.abrirConexion();
            consulta = conexion.mySqlConnection.prepareStatement(SQL.GET_AUDIO_RELATED_COMPARECENCIA.toString());
            consulta.setInt(1, idComparecencia);
            consulta.setInt(2, idComparecencia);
            resultado = consulta.executeQuery();

            while (resultado.next()) {
                audioIDS.add(resultado.getInt(1));
            }
            
            return audioIDS;
        } catch (Exception e) {
            System.out.println("Error al obtener la lista de audios.");
            System.out.println(e);
        }finally{
            closeAll();
        }
        return null;
    }
    
    public String getAudioPathByPath(int idComparecencia, String path){
        try{
            conexion.abrirConexion();
            consulta = conexion.mySqlConnection.prepareStatement(SQL.GET_AUDIOPATH_BY_PATH.toString());
            consulta.setInt(1, idComparecencia);
            consulta.setString(2, "%" + path + "%");
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
    
    public String getAnotacionesPathByPath(int idComparecencia, String path){
        try{
            conexion.abrirConexion();
            consulta = conexion.mySqlConnection.prepareStatement(SQL.GET_ANOTACIONPATH_BY_PATH.toString());
            consulta.setInt(1, idComparecencia);
            consulta.setString(2, "%" + path + "%");
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

    public List<Object[]> getAllComparecencias(List<Object[]> listaDatos) {
            try {
                                
                conexion.abrirConexion();
                consulta = conexion.mySqlConnection.prepareStatement(SQL.LIST_ALL_COMPARECENCIAS.toString());
                resultado = consulta.executeQuery();
                    
                while (resultado.next()) {
                    Object[] fila = new Object[6];
                    fila[0] = resultado.getString(1);
                    fila[1] = resultado.getString(2);
                    fila[2] = resultado.getString(3);
                    fila[3] = resultado.getString(4);
                    fila[4] = resultado.getString(5);
                    fila[5] = "S"; //S = Proviene de la base de datos online
                    listaDatos.add(fila);
                }
                
                return listaDatos;
            } catch (SQLException e) {
                System.out.println("Error al obtener los datos para la tabla de pantalla principal.");
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
                System.out.println(e);
            } finally {
                closeAll();
            }
            return null;
    }
    
    public List<Object[]> getComparecenciasByRegion(List<Object[]> listaDatos, int idRegion) {
            try {
                                
                conexion.abrirConexion();
                consulta = conexion.mySqlConnection.prepareStatement(SQL.LIST_COMPARECENCIAS_BY_REGION.toString());
                consulta.setInt(1, idRegion);
                resultado = consulta.executeQuery();
                    
                while (resultado.next()) {
                    Object[] fila = new Object[6];
                    fila[0] = resultado.getString(1);
                    fila[1] = resultado.getString(2);
                    fila[2] = resultado.getString(3);
                    fila[3] = resultado.getString(4);
                    fila[4] = resultado.getString(5);
                    fila[5] = "S"; //S = Proviene de la base de datos online
                    listaDatos.add(fila);
                }
                
                return listaDatos;
            } catch (SQLException e) {
                System.out.println("Error al obtener los datos para la tabla de pantalla principal.");
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
                System.out.println(e);
            } finally {
                closeAll();
            }
            return null;
    }
    
    public List<Object[]> getComparecenciasByOficina(List<Object[]> listaDatos, int idOficina) {
            try {
                                
                conexion.abrirConexion();
                consulta = conexion.mySqlConnection.prepareStatement(SQL.LIST_COMPARECENCIAS_BY_OFICINA.toString());
                consulta.setInt(1, idOficina);
                resultado = consulta.executeQuery();
                    
                while (resultado.next()) {
                    Object[] fila = new Object[6];
                    fila[0] = resultado.getString(1);
                    fila[1] = resultado.getString(2);
                    fila[2] = resultado.getString(3);
                    fila[3] = resultado.getString(4);
                    fila[4] = resultado.getString(5);
                    fila[5] = "S"; //S = Proviene de la base de datos online
                    listaDatos.add(fila);
                }
                
                return listaDatos;
            } catch (SQLException e) {
                System.out.println("Error al obtener los datos para la tabla de pantalla principal.");
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
                System.out.println(e);
            } finally {
                closeAll();
            }
            return null;
    }

    public List<Object[]> getComparecenciasByInspector(List<Object[]> listaDatos, int idPersona) {
            try {
                                
                conexion.abrirConexion();
                consulta = conexion.mySqlConnection.prepareStatement(SQL.LIST_COMPARECENCIAS_BY_AUTHOR.toString());
                consulta.setInt(1, idPersona);
                resultado = consulta.executeQuery();
                    
                while (resultado.next()) {
                    Object[] fila = new Object[6];
                    fila[0] = resultado.getString(1);
                    fila[1] = resultado.getString(2);
                    fila[2] = resultado.getString(3);
                    fila[3] = resultado.getString(4);
                    fila[4] = resultado.getString(5);
                    fila[5] = "S"; //S = Proviene de la base de datos online
                    listaDatos.add(fila);
                }
                
                return listaDatos;
            } catch (SQLException e) {
                System.out.println("Error al obtener los datos para la tabla de pantalla principal.");
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
                System.out.println(e);
            } finally {
                closeAll();
            }
            return null;
    }
    
    public List<Object[]> getComparecenciasByAsesor(List<Object[]> listaDatos, int idPersona) {
            try {
                                
                conexion.abrirConexion();
                consulta = conexion.mySqlConnection.prepareStatement(SQL.LIST_COMPARECENCIAS_BY_ACCESS.toString());
                consulta.setInt(1, idPersona);
                resultado = consulta.executeQuery();
                    
                while (resultado.next()) {
                    Object[] fila = new Object[6];
                    fila[0] = resultado.getString(1);
                    fila[1] = resultado.getString(2);
                    fila[2] = resultado.getString(3);
                    fila[3] = resultado.getString(4);
                    fila[4] = resultado.getString(5);
                    fila[5] = "S"; //S = Proviene de la base de datos online
                    listaDatos.add(fila);
                }
                
                return listaDatos;
            } catch (SQLException e) {
                System.out.println("Error al obtener los datos para la tabla de pantalla principal.");
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
                System.out.println(e);
            } finally {
                closeAll();
            }
            return null;
    }

/******************************************************************************/
    
    public List<Object[]> listUsuariosPendientes(List<Object[]> listaDatos) {
        try {

            conexion.abrirConexion();
            consulta = conexion.mySqlConnection.prepareStatement(SQL.LIST_AUTORIZACION_PENDIENTE.toString());
            resultado = consulta.executeQuery();

            while (resultado.next()) {
                Object[] fila = new Object[5];
                fila[0] = resultado.getString(1);
                fila[1] = resultado.getString(2);
                fila[2] = resultado.getString(3);
                fila[3] = resultado.getString(4);
                fila[4] = resultado.getString(5);
                listaDatos.add(fila);
            }

            return listaDatos;
        } catch (SQLException e) {
            System.out.println("Error al obtener los datos para la tabla de pantalla principal.");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            System.out.println(e);
        } finally {
            closeAll();
        }
        return null;
    }

    public void autorizarUsuario(int idPersona) {
        try {

            conexion.abrirConexion();
            consulta = conexion.mySqlConnection.prepareStatement(SQL.AUTORIZAR_USUARIO.toString());
            consulta.setInt(1, idPersona);
            consulta.executeUpdate();
            
            System.out.println("Se autorizo un usuario para subir comparecencias.");
        } catch (SQLException e) {
            System.out.println("Error al autorizar un usuario para subir comparecencias.");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            System.out.println(e);
        } finally {
            closeAll();
        }
    }
    
    public void denegarUsuario(int idPersona) {
        try {

            conexion.abrirConexion();
            consulta = conexion.mySqlConnection.prepareStatement(SQL.DENEGAR_USUARIO.toString());
            consulta.setInt(1, idPersona);
            consulta.executeUpdate();
            
            System.out.println("Se nego un usuario para subir comparecencias.");
        } catch (SQLException e) {
            System.out.println("Error al denegar un usuario para subir comparecencias.");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            System.out.println(e);
        } finally {
            closeAll();
        }
    }
    
    public int getUsuariosEnLinea(){
        int enLinea = 0;
        
        try{
            conexion.abrirConexion();
            consulta = conexion.mySqlConnection.prepareStatement(SQL.GET_USUARIOS_ENLINEA.toString());
            resultado = consulta.executeQuery();
            while (resultado.next()) {
                enLinea = resultado.getInt(1);
            }
        }catch(Exception e){
            System.out.println("Error al obtener la cantidad de usuarios en linea.");
            System.out.println(e);
        }
        
        return enLinea;
    }
    
/******************************************************************************/

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
