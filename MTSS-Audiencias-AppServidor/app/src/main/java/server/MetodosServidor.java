package server;

import java.util.LinkedList;
import java.util.List;import javax.swing.table.DefaultTableModel;
import models.Inspector;
import models.Persona;
import models.Usuario;

public class MetodosServidor {

/*
    CODIGOS DE CONSULTAS:
    case "87": numeroUsuariosEnLinea();
    case "88": usuarioEsAdmin();
    case "89": enviarListaUsuariosPendientes();
    case "90": autorizarUsuario();
    case "91": denegarUsuario();
    case "92": createObjPersona();
    case "93": createObjUser();
    case "94": createObjAdmin();
    case "95": getIdAdmin();
    case "96": adminExist();
    case "97": signUpAdministrador()
    case "98": loginAdministrador();
    case "99": logoutAdministrador();
    case "100": detenerServidor();
*/
    
    private Inspector inspector;
    private ConexionServidor conexionServidor;
    private List<String> solicitudServidor;
    private String respuestaServidor;
        
    public MetodosServidor() {
        initVariables();
    }
    
    public MetodosServidor(Inspector inspector) {
        this();
        this.inspector =  inspector;
    }

    private void initVariables(){
        conexionServidor = new ConexionServidor();
        solicitudServidor = new LinkedList<>();
        respuestaServidor = "";
    }
    
    public void setInspector(Inspector inspector){
        this.inspector = inspector;
    }
    
    
    public Boolean verificarEstadoServidor() {
        try {

            if (conexionServidor.conectarServidor()) {

                solicitudServidor.clear();
                solicitudServidor.add("00");
                conexionServidor.enviarObjeto(solicitudServidor);

                return "true".equals(conexionServidor.recibirMensaje());
            }

            return false;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }

    public DefaultTableModel getListaUsuariosPendientes(DefaultTableModel tblModelAutorizacion) {
        try {
            if (conexionServidor.conectarServidor()) {

                solicitudServidor.clear();
                solicitudServidor.add("89");
                conexionServidor.enviarObjeto(solicitudServidor);

                Object objDatos = conexionServidor.recibirObjeto();

                if (objDatos instanceof List) {
                    List<Object[]> listaDatos = (List) objDatos;

                    for (Object[] row : listaDatos) {
                        tblModelAutorizacion.addRow(row);
                    }

                    return tblModelAutorizacion;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        } finally {
            conexionServidor.desconectarServidor();
        }
        return null;
    }

    public void autorizarUsuario(int idPersona) {
        try {
            if (conexionServidor.conectarServidor()) {

                solicitudServidor.clear();
                solicitudServidor.add("90");
                solicitudServidor.add(String.valueOf(idPersona));
                conexionServidor.enviarObjeto(solicitudServidor);

            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            conexionServidor.desconectarServidor();
        }
    }

    public void denegarUsuario(int idPersona) {
        try {
            if (conexionServidor.conectarServidor()) {

                solicitudServidor.clear();
                solicitudServidor.add("91");
                solicitudServidor.add(String.valueOf(idPersona));
                conexionServidor.enviarObjeto(solicitudServidor);

            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            conexionServidor.desconectarServidor();
        }
    }

    public Inspector registrarAdministrador(Inspector nuevoInspector) {
        try {

            if (conexionServidor.conectarServidor()) {

                solicitudServidor.clear();
                solicitudServidor.add("97");
                conexionServidor.enviarObjeto(solicitudServidor);

                //se espera la confirmacion del servidor
                //para enviar objeto inspector
                if (conexionServidor.recibirMensaje().equals("ready")) {

                    conexionServidor.enviarObjeto(nuevoInspector);

                    if (conexionServidor.recibirMensaje().equals("confirm")) {

                        conexionServidor.enviarMensaje("ready");

                        Object objInspector;
                        objInspector = conexionServidor.recibirObjeto();

                        if (objInspector instanceof Inspector) {
                            nuevoInspector = (Inspector) objInspector;
                            return nuevoInspector;
                        }

                    } else {
                        return null;
                    }
                }

                return null;
            }

            return null;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        } finally {
            conexionServidor.desconectarServidor();
        }
    }
    
     public Boolean usuarioEsAdmin(int idPersona) {
        try {
            if (conexionServidor.conectarServidor()) {

                solicitudServidor.clear();
                solicitudServidor.add("88");
                solicitudServidor.add(String.valueOf(idPersona));
                conexionServidor.enviarObjeto(solicitudServidor);

                return "true".equals(conexionServidor.recibirMensaje());
            }
            return false;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        } finally {
            conexionServidor.desconectarServidor();
        }
    }

    public Boolean userExist(String cedula, String contraseña) {
        try {
            if (conexionServidor.conectarServidor()) {

                solicitudServidor.clear();
                solicitudServidor.add("96");
                solicitudServidor.add(cedula);
                solicitudServidor.add(contraseña);
                conexionServidor.enviarObjeto(solicitudServidor);

                return "true".equals(conexionServidor.recibirMensaje());
            }
            return false;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        } finally {
            conexionServidor.desconectarServidor();
        }
    }

    public Integer getIdInspector(String cedula) {
        try {
            if (conexionServidor.conectarServidor()) {

                solicitudServidor.clear();
                solicitudServidor.add("95");
                solicitudServidor.add(cedula);
                conexionServidor.enviarObjeto(solicitudServidor);

                return Integer.valueOf(conexionServidor.recibirMensaje());
            }
            return -1;
        } catch (Exception ex) {
            System.out.println(ex);
            return -1;
        } finally {
            conexionServidor.desconectarServidor();
        }
    }

    public Inspector createObjectInspector(int id) {
        try {
            if (conexionServidor.conectarServidor()) {

                solicitudServidor.clear();
                solicitudServidor.add("94");
                solicitudServidor.add(String.valueOf(id));
                conexionServidor.enviarObjeto(solicitudServidor);

                Object objInspector;
                objInspector = conexionServidor.recibirObjeto();

                if (objInspector instanceof Inspector) {

                    Inspector nuevoInspector;
                    nuevoInspector = (Inspector) objInspector;
                    return nuevoInspector;

                } else {
                    return null;
                }
            }
            return null;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        } finally {
            conexionServidor.desconectarServidor();
        }
    }

    /*
    public Persona createObjectPersona(int id) {
        try {
            if (conexionServidor.conectarServidor()) {

                solicitudServidor.clear();
                solicitudServidor.add("92");
                conexionServidor.enviarObjeto(solicitudServidor);

            }
            return null;
        } catch (Exception ex) {
            return null;
        } finally {
            conexionServidor.desconectarServidor();
        }
    }
    
    public Usuario createObjectUsuario(int id) {
        try {
            if (conexionServidor.conectarServidor()) {

                solicitudServidor.clear();
                solicitudServidor.add("93");
                conexionServidor.enviarObjeto(solicitudServidor);

            }
            return null;
        } catch (Exception ex) {
            return null;
        } finally {
            conexionServidor.desconectarServidor();
        }
    }
     */
    public Boolean loginInspector(int id) {
        try {
            if (conexionServidor.conectarServidor()) {

                solicitudServidor.clear();
                solicitudServidor.add("98");
                solicitudServidor.add(String.valueOf(id));
                conexionServidor.enviarObjeto(solicitudServidor);
                
                return "true".equals(conexionServidor.recibirMensaje());
            }
            return false;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        } finally {
            conexionServidor.desconectarServidor();
        }
    }

    public Boolean logoutInspector(int id) {
        try {
            if (conexionServidor.conectarServidor()) {

                solicitudServidor.clear();
                solicitudServidor.add("99");
                solicitudServidor.add(String.valueOf(id));
                conexionServidor.enviarObjeto(solicitudServidor);

                return "true".equals(conexionServidor.recibirMensaje());
            }
            return false;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        } finally {
            conexionServidor.desconectarServidor();
        }
    }
    
    public Integer getUsuariosEnLinea() {
        try {
            if (conexionServidor.conectarServidor()) {

                solicitudServidor.clear();
                solicitudServidor.add("87");
                conexionServidor.enviarObjeto(solicitudServidor);

                return Integer.valueOf(conexionServidor.recibirMensaje());
            }
            return -1;
        } catch (Exception ex) {
            System.out.println(ex);
            return -1;
        } finally {
            conexionServidor.desconectarServidor();
        }
    }

    public void detenerServidor() {
        if (conexionServidor.conectarServidor()) {

            solicitudServidor.clear();
            solicitudServidor.add("100");
            conexionServidor.enviarObjeto(solicitudServidor);
            conexionServidor.desconectarServidor();

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