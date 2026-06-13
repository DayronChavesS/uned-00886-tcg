package interfaces;

import database.MetodosDatabase;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import methods.Archivos;
import methods.Telemetria;
import models.*;

public class RegistroGrabacion extends javax.swing.JFrame {

    public RegistroGrabacion() {
        initComponents();
        initVariables();
        initForm();
        //anotacion = anotacion.jsonToObject(JSON);
    }
    
    public RegistroGrabacion(Inspector inspector){
        this();
        this.inspector = inspector;
    }
        
    private void crearPantallaPrincipal(){
        pantallaPrincipal = new Principal(inspector);
        pantallaPrincipal.setLocationRelativeTo(null);
        pantallaPrincipal.setVisible(true);
        disposeVariables();
        this.dispose();
    }
    
    private void crearPantallaComparecencia(){
        pantallaComparecencia = new Comparecencias(inspector, comparecencia, audio);
        pantallaComparecencia.setLocationRelativeTo(null);
        pantallaComparecencia.setVisible(true);
        disposeVariables();
        this.dispose();
    }
    
    private void crearCarpetaComparecencia() throws Exception{
        archivo.crearCarpetaNuevaComparecencia(comparecencia);
    }
        
    private void capturarDatosGrabacion() throws Exception {
        telemetria.logActivity("Se capturan los datos de la grabacion.");
        comparecencia.setCodigoSILAC(txtCodigoSILAC.getText());
        audio.setNombreAudio(txtNombreGrabacion.getText());
        comparecencia.setTipoCaso(cmbTipoCaso.getSelectedItem().toString());
        comparecencia.setUbicacion(txtUbicacionGrabacion.getText());
        comparecencia.setLinkExpediente(txtLinkExpediente.getText());
        comparecencia.setFecha((java.util.Date)spnFechaComparecencia.getValue(), true);
    }

    private void guardarDatosGrabacion() throws Exception {
        comparecencia = database.guardarComparecencia(comparecencia);
        database.guardarInspectorxComparecencia(inspector, comparecencia);
        comparecencia = database.createObjectComparecencia(comparecencia.getIdComparecencia());
    }
    
    private void capturarDatosGestionante() throws Exception {
        telemetria.logActivity("Se capturan los datos del gestionante.");
        
        //juridico
        if (cmbTipoIdentificacionGestionante.getSelectedIndex() == 2) {
            personaJuridica.setCedulaJuridica(txtfldCedulaJuridicaGestionante.getText(),true);
            personaJuridica.setNombreRazonSocial(txtfldRazonSocialGestionante.getText());
            gestionante.setPersonaJuridica(personaJuridica);
            gestionante.setComparecencia(comparecencia);
          //fisico
        } else {
            persona.setCedula(txtfldCedulaGestionante.getText(),true);
            persona.setPrimerNombre(txtfldPrimerNombreGestionante.getText());
            persona.setSegundoNombre(txtfldSegundoNombreGestionante.getText());
            persona.setPrimerApellido(txtfldPrimerApellidoGestionante.getText());
            persona.setSegundoApellido(txtfldSegundoApellidoGestionante.getText());
            gestionante.setPersona(persona);
            gestionante.setComparecencia(comparecencia);
        }

    }

    private void guardarDatosGestionante() throws Exception {
        if (cmbTipoIdentificacionGestionante.getSelectedIndex() == 2) {
            gestionante = database.guardarGestionanteJuridico(gestionante);
        }else{
            gestionante = database.guardarGestionante(gestionante);
        }
    }

    private void capturarDatosGestionado() throws Exception {
        telemetria.logActivity("Se capturan los datos del gestionado.");

        //juridico
        if (cmbTipoIdentificacionGestionado.getSelectedIndex() == 2) {
            personaJuridica.setCedulaJuridica(txtfldCedulaJuridicaGestionado.getText(),true);
            personaJuridica.setNombreRazonSocial(txtfldRazonSocialGestionado.getText());
            gestionado.setPersonaJuridica(personaJuridica);
            gestionado.setComparecencia(comparecencia);
          //fisico
        } else {
            persona.setCedula(txtfldCedulaGestionado.getText(),true);
            persona.setPrimerNombre(txtfldPrimerNombreGestionado.getText());
            persona.setSegundoNombre(txtfldSegundoNombreGestionado.getText());
            persona.setPrimerApellido(txtfldPrimerApellidoGestionado.getText());
            persona.setSegundoApellido(txtfldSegundoApellidoGestionado.getText());
            gestionado.setPersona(persona);
            gestionado.setComparecencia(comparecencia);
        }
        
        telemetria.logActivity("Se han ingresado todos los datos obligatorios.");
    }

    private void guardarDatosGestionado() throws Exception {
        if (cmbTipoIdentificacionGestionado.getSelectedIndex() == 2) {
            gestionado = database.guardarGestionadoJuridico(gestionado);
        }else{
            gestionado = database.guardarGestionado(gestionado);
        }
    }

    private void capturarDatosTestigo() throws Exception {
        telemetria.logActivity("Se capturan los datos del testigo.");
        testigo = new Testigo();
        persona.setCedula(txtCedulaTestigo.getText(),true);
        persona.setPrimerNombre(txtPrimerNombreTestigo.getText());
        persona.setSegundoNombre(txtSegundoNombreTestigo.getText());
        persona.setPrimerApellido(txtPrimerApellidoTestigo.getText());
        persona.setSegundoApellido(txtSegundoApellidoTestigo.getText());
        testigo.setPersona(persona);
        if(lastTab == 1){
            testigo.setGestionante(gestionante);
        }
        else if(lastTab == 2){
            testigo.setGestionado(gestionado);
        }
    }

    private void guardarDatosTestigo() throws Exception {
        testigo = database.guardarTestigo(testigo);
    }

    private void capturarDatosAcompañante() throws Exception {
        telemetria.logActivity("Se capturan los datos del acompañante.");
        acompañante = new Acompañante();
        persona.setCedula(txtCedulaAcompañante.getText(),true);
        persona.setPrimerNombre(txtPrimerNombreAcompañante.getText());
        persona.setSegundoNombre(txtSegundoNombreAcompañante.getText());
        persona.setPrimerApellido(txtPrimerApellidoAcompañante.getText());
        persona.setSegundoApellido(txtSegundoApellidoAcompañante.getText());
        acompañante.setPersona(persona);
        acompañante.setTipoAcompañante(cmbTipoAcompañante.getSelectedItem().toString());
        acompañante.setEnCondicionDe(txtCondicionAcompañante.getText());
        if(lastTab == 1){
            acompañante.setGestionante(gestionante);
        }
        else if(lastTab == 2){
            acompañante.setGestionado(gestionado);
        }
    }

    private void guardarDatosAcompañante() throws Exception {
        acompañante = database.guardarAcompañante(acompañante);
    }

    private void capturarDatosRepresentante() throws Exception {
        telemetria.logActivity("Se capturan los datos del representante.");
        representante = new Representante();
        persona.setCedula(txtCedulaRepresentante.getText(),true);
        persona.setPrimerNombre(txtPrimerNombreRepresentante.getText());
        persona.setSegundoNombre(txtSegundoNombreRepresentante.getText());
        persona.setPrimerApellido(txtPrimerApellidoRepresentante.getText());
        persona.setSegundoApellido(txtSegundoApellidoRepresentante.getText());
        representante.setPersona(persona);
        if(lastTab == 1){
            representante.setGestionante(gestionante);
        }
        else if(lastTab == 2){
            representante.setGestionado(gestionado);
        }
    }

    private void guardarDatosRepresentante() throws Exception {
        representante = database.guardarRepresentante(representante);
    }
    
    private void configurarSpinners(){
        spnFechaComparecencia.setModel(new SpinnerDateModel());
        spnFechaComparecencia.setEditor(new JSpinner.DateEditor(spnFechaComparecencia, "yyyy-MM-dd"));
    }
    
     public void addTextFieldPopup()
    {
        JPopupMenu menu = new JPopupMenu();
        Action cut = new DefaultEditorKit.CutAction();
        cut.putValue(Action.NAME, "Cortar");
        menu.add( cut );

        Action copy = new DefaultEditorKit.CopyAction();
        copy.putValue(Action.NAME, "Copiar");
        menu.add( copy );

        Action paste = new DefaultEditorKit.PasteAction();
        paste.putValue(Action.NAME, "Pegar");
        menu.add( paste );
        
        txtCodigoSILAC.setComponentPopupMenu( menu );
        txtNombreGrabacion.setComponentPopupMenu( menu );
        txtUbicacionGrabacion.setComponentPopupMenu( menu );
        txtfldCedulaJuridicaGestionante.setComponentPopupMenu( menu );
        txtfldRazonSocialGestionante.setComponentPopupMenu( menu );
        txtfldCedulaGestionante.setComponentPopupMenu( menu );
        txtfldPrimerNombreGestionante.setComponentPopupMenu( menu );
        txtfldSegundoNombreGestionante.setComponentPopupMenu( menu );
        txtfldPrimerApellidoGestionante.setComponentPopupMenu( menu );
        txtfldSegundoApellidoGestionante.setComponentPopupMenu( menu );
        txtfldCedulaJuridicaGestionado.setComponentPopupMenu( menu );
        txtfldRazonSocialGestionado.setComponentPopupMenu( menu );
        txtfldCedulaGestionado.setComponentPopupMenu( menu );
        txtfldPrimerNombreGestionado.setComponentPopupMenu( menu );
        txtfldSegundoNombreGestionado.setComponentPopupMenu( menu );
        txtfldPrimerApellidoGestionado.setComponentPopupMenu( menu );
        txtfldSegundoApellidoGestionado.setComponentPopupMenu( menu );
        txtCedulaTestigo.setComponentPopupMenu( menu );
        txtPrimerNombreTestigo.setComponentPopupMenu( menu );
        txtSegundoNombreTestigo.setComponentPopupMenu( menu );
        txtPrimerApellidoTestigo.setComponentPopupMenu( menu );
        txtSegundoApellidoTestigo.setComponentPopupMenu( menu );
        txtCedulaAcompañante.setComponentPopupMenu( menu );
        txtPrimerNombreAcompañante.setComponentPopupMenu( menu );
        txtSegundoNombreAcompañante.setComponentPopupMenu( menu );
        txtPrimerApellidoAcompañante.setComponentPopupMenu( menu );
        txtSegundoApellidoAcompañante.setComponentPopupMenu( menu );
        txtCondicionAcompañante.setComponentPopupMenu( menu );
        txtCedulaRepresentante.setComponentPopupMenu( menu );
        txtPrimerNombreRepresentante.setComponentPopupMenu( menu );
        txtSegundoNombreRepresentante.setComponentPopupMenu( menu );
        txtPrimerApellidoRepresentante.setComponentPopupMenu( menu );
        txtSegundoApellidoRepresentante.setComponentPopupMenu( menu );
    }
    
    private void activarFormularioGrabacion() {
        spnFechaComparecencia.setEnabled(true);
        txtCodigoSILAC.setEnabled(true);
        txtNombreGrabacion.setEnabled(true);
        txtUbicacionGrabacion.setEnabled(true);
        cmbTipoCaso.setEnabled(true);
        contenedorTabs.setEnabledAt(0, true);
        btnGuardar.setEnabled(true);
        setButtonNormalColor(btnGuardar);
    }

    private void desactivarFormularioGrabacion() {
        spnFechaComparecencia.setEnabled(false);
        txtCodigoSILAC.setEnabled(false);
        txtNombreGrabacion.setEnabled(false);
        txtUbicacionGrabacion.setEnabled(false);
        cmbTipoCaso.setEnabled(false);
        contenedorTabs.setEnabledAt(0, false);
        btnGuardar.setEnabled(false);
        setButtonDisabledColor(btnGuardar);
    }
    
    private void borrarFormularioGrabacion() {
        txtCodigoSILAC.setText("");
        txtNombreGrabacion.setText("");
        txtUbicacionGrabacion.setText("");
        cmbTipoCaso.setSelectedIndex(0);
    }

    private void activarFormularioGestionanteFisico() {
        txtfldCedulaGestionante.setEnabled(true);
        txtfldPrimerApellidoGestionante.setEnabled(true);
        txtfldPrimerNombreGestionante.setEnabled(true);
        txtfldSegundoApellidoGestionante.setEnabled(true);
        txtfldSegundoNombreGestionante.setEnabled(true);
        btnAgregarAcompañanteGestionante.setEnabled(true);
        setButtonNormalColor(btnAgregarAcompañanteGestionante);
        btnAgregarRepresentanteGestionante.setEnabled(true);
        setButtonNormalColor(btnAgregarRepresentanteGestionante);
        btnAgregarTestigoGestionante.setEnabled(true);
        setButtonNormalColor(btnAgregarTestigoGestionante);
        contenedorTabs.setEnabledAt(1, true);
        btnGuardar.setEnabled(true);
        setButtonNormalColor(btnGuardar);
    }
    
    private void activarFormularioGestionanteJuridico() {
        txtfldCedulaJuridicaGestionante.setEnabled(true);
        txtfldRazonSocialGestionante.setEnabled(true);
        btnAgregarAcompañanteGestionante.setEnabled(true);
        setButtonNormalColor(btnAgregarAcompañanteGestionante);
        btnAgregarRepresentanteGestionante.setEnabled(true);
        setButtonNormalColor(btnAgregarRepresentanteGestionante);
        btnAgregarTestigoGestionante.setEnabled(true);
        setButtonNormalColor(btnAgregarTestigoGestionante);
        contenedorTabs.setEnabledAt(1, true);
        btnGuardar.setEnabled(true);
        setButtonNormalColor(btnGuardar);
    }

    private void desactivarFormularioGestionante() {
        txtfldCedulaGestionante.setEnabled(false);
        txtfldPrimerApellidoGestionante.setEnabled(false);
        txtfldPrimerNombreGestionante.setEnabled(false);
        txtfldSegundoApellidoGestionante.setEnabled(false);
        txtfldSegundoNombreGestionante.setEnabled(false);
        txtfldCedulaJuridicaGestionante.setEnabled(false);
        txtfldRazonSocialGestionante.setEnabled(false);
        btnAgregarAcompañanteGestionante.setEnabled(false);
        setButtonDisabledColor(btnAgregarAcompañanteGestionante);
        btnAgregarRepresentanteGestionante.setEnabled(false);
        setButtonDisabledColor(btnAgregarRepresentanteGestionante);
        btnAgregarTestigoGestionante.setEnabled(false);
        setButtonDisabledColor(btnAgregarTestigoGestionante);
        contenedorTabs.setEnabledAt(1, false);
        btnGuardar.setEnabled(false);
        setButtonDisabledColor(btnGuardar);
    }
    
    private void borrarFormularioGestionante() {
        txtfldCedulaGestionante.setText("");
        txtfldPrimerApellidoGestionante.setText("");
        txtfldPrimerNombreGestionante.setText("");
        txtfldSegundoNombreGestionante.setText("");
        txtfldSegundoApellidoGestionante.setText("");
        txtfldCedulaJuridicaGestionante.setText("");
        txtfldRazonSocialGestionante.setText("");
        cmbTipoIdentificacionGestionante.setSelectedIndex(0);
    }

    private void activarFormularioGestionadoFisico() {
        txtfldCedulaGestionado.setEnabled(true);
        txtfldPrimerApellidoGestionado.setEnabled(true);
        txtfldPrimerNombreGestionado.setEnabled(true);
        txtfldSegundoApellidoGestionado.setEnabled(true);
        txtfldSegundoNombreGestionado.setEnabled(true);
        btnAgregarAcompañanteGestionado.setEnabled(true);
        setButtonNormalColor(btnAgregarAcompañanteGestionado);
        btnAgregarRepresentanteGestionado.setEnabled(true);
        setButtonNormalColor(btnAgregarRepresentanteGestionado);
        btnAgregarTestigoGestionado.setEnabled(true);
        setButtonNormalColor(btnAgregarTestigoGestionado);
        contenedorTabs.setEnabledAt(2, true);
        btnGuardar.setEnabled(true);
        setButtonNormalColor(btnGuardar);
    }
    
    private void activarFormularioGestionadoJuridico() {
        txtfldCedulaJuridicaGestionado.setEnabled(true);
        txtfldRazonSocialGestionado.setEnabled(true);
        btnAgregarAcompañanteGestionado.setEnabled(true);
        setButtonNormalColor(btnAgregarAcompañanteGestionado);
        btnAgregarRepresentanteGestionado.setEnabled(true);
        setButtonNormalColor(btnAgregarRepresentanteGestionado);
        btnAgregarTestigoGestionado.setEnabled(true);
        setButtonNormalColor(btnAgregarTestigoGestionado);
        contenedorTabs.setEnabledAt(2, true);
        btnGuardar.setEnabled(true);
        setButtonNormalColor(btnGuardar);
    }

    private void desactivarFormularioGestionado() {
        txtfldCedulaGestionado.setEnabled(false);
        txtfldPrimerApellidoGestionado.setEnabled(false);
        txtfldPrimerNombreGestionado.setEnabled(false);
        txtfldSegundoApellidoGestionado.setEnabled(false);
        txtfldSegundoNombreGestionado.setEnabled(false);
        txtfldCedulaJuridicaGestionado.setEnabled(false);
        txtfldRazonSocialGestionado.setEnabled(false);
        btnAgregarAcompañanteGestionado.setEnabled(false);
        setButtonDisabledColor(btnAgregarAcompañanteGestionado);
        btnAgregarRepresentanteGestionado.setEnabled(false);
        setButtonDisabledColor(btnAgregarRepresentanteGestionado);
        btnAgregarTestigoGestionado.setEnabled(false);
        setButtonDisabledColor(btnAgregarTestigoGestionado);
        contenedorTabs.setEnabledAt(2, false);
        btnGuardar.setEnabled(false);
        setButtonDisabledColor(btnGuardar);
    }
    
    private void borrarFormularioGestionado() {
        txtfldCedulaGestionado.setText("");
        txtfldPrimerApellidoGestionado.setText("");
        txtfldPrimerNombreGestionado.setText("");
        txtfldSegundoApellidoGestionado.setText("");
        txtfldSegundoNombreGestionado.setText("");
        txtfldCedulaJuridicaGestionado.setText("");
        txtfldRazonSocialGestionado.setText("");
        cmbTipoIdentificacionGestionado.setSelectedIndex(0);
    }

    private void activarFormularioAcompañante() {
        txtCedulaAcompañante.setEnabled(true);
        txtCondicionAcompañante.setEnabled(false);
        txtPrimerApellidoAcompañante.setEnabled(true);
        txtPrimerNombreAcompañante.setEnabled(true);
        txtSegundoApellidoAcompañante.setEnabled(true);
        txtSegundoNombreAcompañante.setEnabled(true);
        cmbTipoAcompañante.setEnabled(true);
        contenedorTabs.setEnabledAt(3, true);
        btnGuardar.setEnabled(true);
        setButtonNormalColor(btnGuardar);
    }

    private void desactivarFormularioAcompañante() {
        txtCedulaAcompañante.setEnabled(false);
        txtCondicionAcompañante.setEnabled(false);
        txtPrimerApellidoAcompañante.setEnabled(false);
        txtPrimerNombreAcompañante.setEnabled(false);
        txtSegundoApellidoAcompañante.setEnabled(false);
        txtSegundoNombreAcompañante.setEnabled(false);
        cmbTipoAcompañante.setEnabled(false);
        contenedorTabs.setEnabledAt(3, false);
        btnGuardar.setEnabled(false);
        setButtonDisabledColor(btnGuardar);
    }
    
    private void borrarFormularioAcompañante() {
        txtCedulaAcompañante.setText("");
        txtCondicionAcompañante.setText("");
        txtPrimerApellidoAcompañante.setText("");
        txtPrimerNombreAcompañante.setText("");
        txtSegundoApellidoAcompañante.setText("");
        txtSegundoNombreAcompañante.setText("");
        cmbTipoAcompañante.setSelectedIndex(0);
    }

    private void activarFormularioRepresentante() {
        txtCedulaRepresentante.setEnabled(true);
        txtPrimerApellidoRepresentante.setEnabled(true);
        txtPrimerNombreRepresentante.setEnabled(true);
        txtSegundoApellidoRepresentante.setEnabled(true);
        txtSegundoNombreRepresentante.setEnabled(true);
        contenedorTabs.setEnabledAt(4, true);
        btnGuardar.setEnabled(true);
        setButtonNormalColor(btnGuardar);
    }

    private void desactivarFormularioRepresentante() {
        txtCedulaRepresentante.setEnabled(false);
        txtPrimerApellidoRepresentante.setEnabled(false);
        txtPrimerNombreRepresentante.setEnabled(false);
        txtSegundoApellidoRepresentante.setEnabled(false);
        txtSegundoNombreRepresentante.setEnabled(false);
        contenedorTabs.setEnabledAt(4, false);
        btnGuardar.setEnabled(false);
        setButtonDisabledColor(btnGuardar);
    }
    
    private void borrarFormularioRepresentante() {
        txtCedulaRepresentante.setText("");
        txtPrimerApellidoRepresentante.setText("");
        txtPrimerNombreRepresentante.setText("");
        txtSegundoApellidoRepresentante.setText("");
        txtSegundoNombreRepresentante.setText("");
    }
    
    private void activarFormularioTestigo() {
        txtCedulaTestigo.setEnabled(true);
        txtPrimerApellidoTestigo.setEnabled(true);
        txtPrimerNombreTestigo.setEnabled(true);
        txtSegundoApellidoTestigo.setEnabled(true);
        txtSegundoNombreTestigo.setEnabled(true);
        contenedorTabs.setEnabledAt(5, true);
        btnGuardar.setEnabled(true);
        setButtonNormalColor(btnGuardar);
    }

    private void desactivarFormularioTestigo() {
        txtCedulaTestigo.setEnabled(false);
        txtPrimerApellidoTestigo.setEnabled(false);
        txtPrimerNombreTestigo.setEnabled(false);
        txtSegundoApellidoTestigo.setEnabled(false);
        txtSegundoNombreTestigo.setEnabled(false);
        contenedorTabs.setEnabledAt(5, false);
        btnGuardar.setEnabled(false);
        setButtonDisabledColor(btnGuardar);
    }
    
    private void borrarFormularioTestigo() {
        txtCedulaTestigo.setText("");
        txtPrimerApellidoTestigo.setText("");
        txtPrimerNombreTestigo.setText("");
        txtSegundoApellidoTestigo.setText("");
        txtSegundoNombreTestigo.setText("");
    }
    
    private void reportarError(Exception ex){
        
        switch(ex.getMessage()){
            case "SILAC null error" -> {
                crearMensajeError("El código SILAC no puede estar vacío.");
                telemetria.logException(ex);
                break;
            }
            case "SILAC size error" -> {
                crearMensajeError("El código SILAC es demasiado grande.");
                telemetria.logException(ex);
                break;
            }
            case "nombreAudio null error" ->{
                crearMensajeError("El nombre de la grabacion no puede estar vacio.");
                telemetria.logException(ex);
                break;
            }
            case "tipoCaso null error" -> {
                crearMensajeError("La razón no puede estar vacía.");
                telemetria.logException(ex);
                break;
            }
            case "ubicacion null error" -> {
                crearMensajeError("La ubicación no puede estar vacía.");
                telemetria.logException(ex);
                break;
            }
            case "ubicacion size error" -> {
                crearMensajeError("La ubicación es demasiado grande.");
                telemetria.logException(ex);
                break;
            }
            case "fecha null error" -> {
                crearMensajeError("La fecha que ha introducido es incomprensible.");
                telemetria.logException(ex);
                break;
            }
            case "fecha pasado error" -> {
                crearMensajeError("La fecha que ha introducido está en el pasado.");
                telemetria.logException(ex);
                break;
            }
            case "fecha futuro error" -> {
                crearMensajeError("La fecha que ha introducido está en el futuro.");
                telemetria.logException(ex);
                break;
            }
            case "cedula null error" -> {
                crearMensajeError("La cedula no puede estar vacía.");
                telemetria.logException(ex);
                break;
            }
            case "cedula max size error" -> {
                crearMensajeError("La cedula es demasiado grande.");
                telemetria.logException(ex);
                break;
            }
            case "cedula min size error" -> {
                crearMensajeError("La cedula es muy pequeña.");
                telemetria.logException(ex);
                break;
            }
            case "primerNombre null error" -> {
                crearMensajeError("El primer nombre no puede estar vacío.");
                telemetria.logException(ex);
                break;
            }
            case "primerApellido null error" -> {
                crearMensajeError("El primer apellido no puede estar vacío.");
                telemetria.logException(ex);
                break;
            }
            case "primerNombre size error" -> {
                crearMensajeError("El primer nombre es demasiado grande.");
                telemetria.logException(ex);
                break;
            }
            case "segundoNombre size error" -> {
                crearMensajeError("El segundo nombre es demasiado grande.");
                telemetria.logException(ex);
                break;
            }
            case "primerApellido size error" -> {
                crearMensajeError("El primer apellido es demasiado grande.");
                telemetria.logException(ex);
                break;
            }
            case "segundoApellido size error" -> {
                crearMensajeError("El segundo apellido es demasiado grande.");
                telemetria.logException(ex);
                break;
            }
            case "segundoApellido null error" -> {
                crearMensajeError("El segundo apellido no puede estar vacío.");
                telemetria.logException(ex);
                break;
            }
            case "cedula UNIQUE error" -> {
                crearMensajeError("Ya hay una persona registrada con ese número de cedula.");
                telemetria.logException(ex);
                break;
            }
            case "cedula juridica null error" -> {
                crearMensajeError("La cedula juridica no puede estar vacia.");
                telemetria.logException(ex);
                break;
            }
            case "cedula juridica UNIQUE error" -> {
                crearMensajeError("Ya hay una persona jurídica registrada con ese número de cedula.");
                telemetria.logException(ex);
                break;
            }
            case "cedula juridica size error" -> {
                crearMensajeError("La cedula jurídica es demasiado grande.");
                telemetria.logException(ex);
                break;
            }
            case "razon social null error" -> {
                crearMensajeError("La razón social no puede estar vacía.");
                telemetria.logException(ex);
                break;
            }
            case "razon social size error" -> {
                crearMensajeError("La razón social es demasiado grande.");
                telemetria.logException(ex);
                break;
            }
            case "tipo acompañante null error" -> {
                crearMensajeError("El tipo de acompañante no puede estar vacío.");
                telemetria.logException(ex);
                break;
            }
            case "tipo acompañante size error" -> {
                crearMensajeError("El tipo de acompañante es demasiado grande.");
                telemetria.logException(ex);
                break;
            }
            case "condicion null error" -> {
                crearMensajeError("La condición del acompañante no puede estar vacia.");
                telemetria.logException(ex);
                break;
            }
            case "condicion size error" -> {
                crearMensajeError("La condición del acompañante es demasiado grande.");
                telemetria.logException(ex);
                break;
            }
            default -> {
                crearMensajeError(ex.getMessage());
                telemetria.logException(ex);
                break;
            }
        }
    }
    
    /*
    //descartado por ser incompatible con cedulas de San Jose
    private void setTxtFieldMask() throws Exception{
        if(currentTab == 1){
            if(cmbTipoIdentificacionGestionante.getSelectedIndex() == 1){
                txtfldCedulaGestionante.setFormatterFactory(
                        new DefaultFormatterFactory(
                            new MaskFormatter("#0###0###")));
                txtfldCedulaGestionante.setText("");
            }
            if(cmbTipoIdentificacionGestionante.getSelectedIndex() == 3){
                txtfldCedulaGestionante.setFormatterFactory(
                        new DefaultFormatterFactory(
                            new MaskFormatter("############")));
                txtfldCedulaGestionante.setText("");
            }
        }
        if(currentTab == 2){
            if(cmbTipoIdentificacionGestionado.getSelectedIndex() == 1){
                txtfldCedulaGestionado.setFormatterFactory(
                        new DefaultFormatterFactory(
                            new MaskFormatter("#0###0###")));
                txtfldCedulaGestionado.setText("");
            }
            if(cmbTipoIdentificacionGestionado.getSelectedIndex() == 3){
                txtfldCedulaGestionado.setFormatterFactory(
                        new DefaultFormatterFactory(
                            new MaskFormatter("############")));
                txtfldCedulaGestionado.setText("");
            }
        }
    }
    */
    
    private void crearMensajeError(String error){
        JOptionPane.showMessageDialog(null, 
                error,
                "ERROR",
                JOptionPane.ERROR_MESSAGE);
    }
    
    private void crearMensajeInformativo(String info) {
        JOptionPane.showMessageDialog(null,
                "Se han guardado los datos del "
                + info
                + " satisfactoriamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    private Boolean confirmarGuardado(){
        laRespuesta = JOptionPane.showConfirmDialog(null, 
                """
                Si guarda los datos no podrá agregar más acompañantes.
                ¿Realmente desea continuar?
                """,
                "ATENCION",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        //se procesa la respuesta
        return laRespuesta == JOptionPane.YES_OPTION;
    }
    
    private Boolean sePuedeGuardar(){
        laRespuesta = JOptionPane.showConfirmDialog(null, 
                """
                Para realizar este proceso es necesario guardar los datos actuales. 
                \u00bfDesea guardar y continuar?
                """,
                "ATENCION",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        //se procesa la respuesta
        return laRespuesta == JOptionPane.YES_OPTION;
    }
    
    private Boolean comparecenciaExist() throws Exception{
        if(database.getIdComparecencia(comparecencia.getCodigoSILAC()) != -1){
            desactivarFormularioGrabacion();            
            JOptionPane.showMessageDialog(null, 
            """
            Ya existe una comparecencia con ese c\u00f3digo SILAC.
            Regrese a la pantalla principal y reanúdela.
             """,
            "Informacion",
            JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }
    
    private void actualizarBotonGuardar(){
        switch(currentTab) {
            case 0 -> {
                if (todosLosDatosIngresados == true) {
                    crearBotonIniciarGrabacion();
                }else{
                    btnGuardar.setText("Guardar Grabación");
                }
                break;
            }
            case 1 -> {
                //asume guardoGestionado == true
                if(lastTab == 3 || lastTab == 4 || lastTab == 5){
                    btnGuardar.setText("Continuar");
                    btnGuardar.setEnabled(true);       
                }else if(!seGuardoGestionante){
                    btnGuardar.setText("Guardar Gestionante");
                }
                break;
            }
            case 2 -> {
                //asume guardoGestionante == true
                if(lastTab == 3 || lastTab == 4 || lastTab == 5){
                    btnGuardar.setText("Continuar");
                    btnGuardar.setEnabled(true);
                }
                else if(!seGuardoGestionado){
                    btnGuardar.setText("Guardar Gestionado");
                }
                else if (todosLosDatosIngresados == true) {
                    crearBotonIniciarGrabacion();
                }
                break;
            }
            case 3 -> {
                if(lastTab == 2){
                    btnGuardar.setText("Agregar Acompañante al Gestionado");
                }
                else{
                    btnGuardar.setText("Agregar Acompañante al Gestionante");
                }
                break;
            }
            case 4 -> {
                if(lastTab == 2){
                    btnGuardar.setText("Agregar Representante al Gestionado");        
                }
                else{
                    btnGuardar.setText("Agregar Representante al Gestionante");
                }
                break;
            }
            case 5 -> {
                if(lastTab == 2){
                    btnGuardar.setText("Agregar Testigo al Gestionado");        
                }
                else{
                    btnGuardar.setText("Agregar Testigo al Gestionante");
                }
                break;
            }
        }
    }
    
    private void procesarFormulario(){
        try {
                if(sePuedeSalir == true){
                    crearCarpetaComparecencia();
                    crearPantallaComparecencia();
                    return;
                }

                switch (currentTab) {
                    case 0 -> {
                        capturarDatosGrabacion();
                        if(!comparecenciaExist()){
                            guardarDatosGrabacion();
                            borrarFormularioGrabacion();
                            desactivarFormularioGrabacion();
                            contenedorTabs.setEnabledAt(1, true);
                            contenedorTabs.setSelectedIndex(1);
                            lastTab = currentTab;
                            currentTab = contenedorTabs.getSelectedIndex();
                            actualizarBotonGuardar();
                        }else{
                            crearPantallaPrincipal();
                        }
                        break;
                    }
                    case 1 -> {
                        if (!seGuardoGestionante) {
                            if (!confirmarGuardado()) {
                                return;
                            }
                            capturarDatosGestionante();
                            guardarDatosGestionante();
                            seGuardoGestionante = true;
                        }
                        borrarFormularioGestionante();
                        desactivarFormularioGestionante();
                        crearMensajeInformativo("Gestionante");
                        contenedorTabs.setEnabledAt(2, true);
                        contenedorTabs.setSelectedIndex(2);
                        lastTab = currentTab;
                        currentTab = contenedorTabs.getSelectedIndex();
                        actualizarBotonGuardar();
                        break;

                    }
                    case 2 -> {
                        if (!seGuardoGestionado) {
                            if (!confirmarGuardado()) {
                                return;
                            }
                            capturarDatosGestionado();
                            guardarDatosGestionado();
                            seGuardoGestionado = true;
                            todosLosDatosIngresados = true;
                        }
                        borrarFormularioGestionado();
                        desactivarFormularioGestionado();
                        crearMensajeInformativo("Gestionado");
                        cmbTipoIdentificacionGestionado.setEnabled(false);
                        lastTab = currentTab;
                        currentTab = contenedorTabs.getSelectedIndex();
                        actualizarBotonGuardar();
                        break;
                    }
                    case 3 -> {
                        capturarDatosAcompañante();
                        guardarDatosAcompañante();
                        borrarFormularioAcompañante();
                        desactivarFormularioAcompañante();
                        crearMensajeInformativo("Acompañante");
                        contenedorTabs.setEnabledAt(lastTab, true);
                        contenedorTabs.setSelectedIndex(lastTab);
                        lastTab = currentTab;
                        currentTab = contenedorTabs.getSelectedIndex();
                        todosLosDatosIngresados = true;
                        actualizarBotonGuardar();
                        break;
                    }
                    case 4 -> {
                        capturarDatosRepresentante();
                        guardarDatosRepresentante();
                        borrarFormularioRepresentante();
                        desactivarFormularioRepresentante();
                        crearMensajeInformativo("Representante");
                        contenedorTabs.setEnabledAt(lastTab, true);
                        contenedorTabs.setSelectedIndex(lastTab);
                        lastTab = currentTab;
                        currentTab = contenedorTabs.getSelectedIndex();
                        todosLosDatosIngresados = true;
                        actualizarBotonGuardar();
                        break;

                    }
                    case 5 -> {
                        capturarDatosTestigo();
                        guardarDatosTestigo();
                        borrarFormularioTestigo();
                        desactivarFormularioTestigo();
                        crearMensajeInformativo("Testigo");
                        contenedorTabs.setEnabledAt(lastTab, true);
                        contenedorTabs.setSelectedIndex(lastTab);
                        lastTab = currentTab;
                        currentTab = contenedorTabs.getSelectedIndex();
                        todosLosDatosIngresados = true;
                        actualizarBotonGuardar();
                        break;
                    }
                    default -> {
                        break;
                        //DO NOTHING
                    }
                }
            } catch (Exception ex) {
                reportarError(ex);
            }
    }
    
    private void crearBotonIniciarGrabacion(){
        btnGuardar.setBackground(Color.RED);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnGuardar.setText("Iniciar Grabación");
        btnGuardar.setOpaque(true);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setEnabled(true);
        sePuedeSalir = true;
    }
    
    private void setButtonNormalColor(JButton theButton){
        theButton.setBackground(PANTONE2727C);
        theButton.setForeground(Color.WHITE);
        theButton.setOpaque(true);
        theButton.setBorderPainted(false);
    }
    
    private void setButtonActiveColor(JButton theButton){
        theButton.setBackground(PANTONE1595C);
        theButton.setForeground(Color.WHITE);
        theButton.setOpaque(true);
        theButton.setBorderPainted(false);
    }
    
    private void setButtonDisabledColor(JButton theButton){
        theButton.setBackground(PANTONE422C);
        theButton.setForeground(PANTONE420C);
        theButton.setOpaque(true);
        theButton.setBorderPainted(false);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelBanner = new javax.swing.JLabel();
        pnlFondo = new javax.swing.JPanel();
        contenedorTabs = new javax.swing.JTabbedPane();
        pnlGrabacion = new javax.swing.JPanel();
        lblTituloInformativo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtNombreGrabacion = new javax.swing.JTextField();
        txtUbicacionGrabacion = new javax.swing.JTextField();
        cmbTipoCaso = new javax.swing.JComboBox<>();
        txtCodigoSILAC = new javax.swing.JFormattedTextField();
        spnFechaComparecencia = new javax.swing.JSpinner();
        lblNombreGrabacion = new javax.swing.JLabel();
        lblTipoCaso = new javax.swing.JLabel();
        lblUbicacion = new javax.swing.JLabel();
        lblFechaComparecencia = new javax.swing.JLabel();
        lblSILAC = new javax.swing.JLabel();
        labelMandatory45 = new javax.swing.JLabel();
        labelMandatory46 = new javax.swing.JLabel();
        labelMandatory47 = new javax.swing.JLabel();
        labelMandatory48 = new javax.swing.JLabel();
        labelMandatory49 = new javax.swing.JLabel();
        lblLinkExpediente = new javax.swing.JLabel();
        txtLinkExpediente = new javax.swing.JTextField();
        pnlGestionante = new javax.swing.JPanel();
        lblInstrucciones2 = new javax.swing.JLabel();
        panelDatosGestionante = new javax.swing.JPanel();
        txtfldPrimerNombreGestionante = new javax.swing.JTextField();
        txtfldSegundoNombreGestionante = new javax.swing.JTextField();
        txtfldPrimerApellidoGestionante = new javax.swing.JTextField();
        txtfldSegundoApellidoGestionante = new javax.swing.JTextField();
        lblCedula2 = new javax.swing.JLabel();
        labelPrimerNombre2 = new javax.swing.JLabel();
        labelSegundoNombre2 = new javax.swing.JLabel();
        labelSegundoApellido2 = new javax.swing.JLabel();
        labelPrimerApellido2 = new javax.swing.JLabel();
        labelSeparator4 = new javax.swing.JLabel();
        labelSeparator5 = new javax.swing.JLabel();
        labelMandatory19 = new javax.swing.JLabel();
        labelMandatory20 = new javax.swing.JLabel();
        labelMandatory21 = new javax.swing.JLabel();
        labelMandatory22 = new javax.swing.JLabel();
        txtfldCedulaGestionante = new javax.swing.JTextField();
        panelSeleccionarAcompañantes = new javax.swing.JPanel();
        btnAgregarTestigoGestionante = new javax.swing.JButton();
        btnAgregarAcompañanteGestionante = new javax.swing.JButton();
        btnAgregarRepresentanteGestionante = new javax.swing.JButton();
        labelMandatory41 = new javax.swing.JLabel();
        lblCedula3 = new javax.swing.JLabel();
        cmbTipoIdentificacionGestionante = new javax.swing.JComboBox<>();
        panelDatosGestionante1 = new javax.swing.JPanel();
        txtfldCedulaJuridicaGestionante = new javax.swing.JFormattedTextField();
        txtfldRazonSocialGestionante = new javax.swing.JTextField();
        lblCedula10 = new javax.swing.JLabel();
        labelPrimerNombre7 = new javax.swing.JLabel();
        labelMandatory54 = new javax.swing.JLabel();
        labelMandatory55 = new javax.swing.JLabel();
        pnlGestionado = new javax.swing.JPanel();
        lblInstrucciones3 = new javax.swing.JLabel();
        panelDatosGestionado = new javax.swing.JPanel();
        txtfldPrimerNombreGestionado = new javax.swing.JTextField();
        txtfldSegundoNombreGestionado = new javax.swing.JTextField();
        txtfldPrimerApellidoGestionado = new javax.swing.JTextField();
        txtfldSegundoApellidoGestionado = new javax.swing.JTextField();
        lblCedula4 = new javax.swing.JLabel();
        labelPrimerNombre3 = new javax.swing.JLabel();
        labelSegundoNombre3 = new javax.swing.JLabel();
        labelSegundoApellido3 = new javax.swing.JLabel();
        labelPrimerApellido3 = new javax.swing.JLabel();
        labelSeparator6 = new javax.swing.JLabel();
        labelSeparator7 = new javax.swing.JLabel();
        labelMandatory23 = new javax.swing.JLabel();
        labelMandatory24 = new javax.swing.JLabel();
        labelMandatory25 = new javax.swing.JLabel();
        labelMandatory26 = new javax.swing.JLabel();
        txtfldCedulaGestionado = new javax.swing.JTextField();
        lblCedula5 = new javax.swing.JLabel();
        cmbTipoIdentificacionGestionado = new javax.swing.JComboBox<>();
        labelMandatory50 = new javax.swing.JLabel();
        panelDatosJuridicosGestionado = new javax.swing.JPanel();
        txtfldCedulaJuridicaGestionado = new javax.swing.JFormattedTextField();
        lblCedula11 = new javax.swing.JLabel();
        labelPrimerNombre8 = new javax.swing.JLabel();
        labelMandatory56 = new javax.swing.JLabel();
        labelMandatory57 = new javax.swing.JLabel();
        txtfldRazonSocialGestionado = new javax.swing.JTextField();
        panelSeleccionarAcompañantes2 = new javax.swing.JPanel();
        btnAgregarTestigoGestionado = new javax.swing.JButton();
        btnAgregarAcompañanteGestionado = new javax.swing.JButton();
        btnAgregarRepresentanteGestionado = new javax.swing.JButton();
        pnlAcompañante = new javax.swing.JPanel();
        panelDatosAcompañante = new javax.swing.JPanel();
        txtPrimerNombreAcompañante = new javax.swing.JTextField();
        txtSegundoNombreAcompañante = new javax.swing.JTextField();
        txtPrimerApellidoAcompañante = new javax.swing.JTextField();
        txtSegundoApellidoAcompañante = new javax.swing.JTextField();
        lblCedula7 = new javax.swing.JLabel();
        labelPrimerNombre6 = new javax.swing.JLabel();
        labelSegundoNombre6 = new javax.swing.JLabel();
        labelSegundoApellido6 = new javax.swing.JLabel();
        labelPrimerApellido6 = new javax.swing.JLabel();
        labelSeparator12 = new javax.swing.JLabel();
        labelSeparator13 = new javax.swing.JLabel();
        labelMandatory31 = new javax.swing.JLabel();
        labelMandatory32 = new javax.swing.JLabel();
        labelMandatory33 = new javax.swing.JLabel();
        labelMandatory34 = new javax.swing.JLabel();
        txtCedulaAcompañante = new javax.swing.JTextField();
        panelDatosAcompañante1 = new javax.swing.JPanel();
        cmbTipoAcompañante = new javax.swing.JComboBox<>();
        txtCondicionAcompañante = new javax.swing.JTextField();
        lblCedula8 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        labelMandatory39 = new javax.swing.JLabel();
        labelMandatory40 = new javax.swing.JLabel();
        lblTituloInformativo2 = new javax.swing.JLabel();
        pnlRepresentante = new javax.swing.JPanel();
        panelDatosRepresentante = new javax.swing.JPanel();
        txtPrimerNombreRepresentante = new javax.swing.JTextField();
        txtSegundoNombreRepresentante = new javax.swing.JTextField();
        txtPrimerApellidoRepresentante = new javax.swing.JTextField();
        txtSegundoApellidoRepresentante = new javax.swing.JTextField();
        lblCedula9 = new javax.swing.JLabel();
        labelPrimerNombre5 = new javax.swing.JLabel();
        labelSegundoNombre5 = new javax.swing.JLabel();
        labelSegundoApellido5 = new javax.swing.JLabel();
        labelPrimerApellido5 = new javax.swing.JLabel();
        labelSeparator10 = new javax.swing.JLabel();
        labelSeparator11 = new javax.swing.JLabel();
        labelMandatory35 = new javax.swing.JLabel();
        labelMandatory36 = new javax.swing.JLabel();
        labelMandatory37 = new javax.swing.JLabel();
        labelMandatory38 = new javax.swing.JLabel();
        txtCedulaRepresentante = new javax.swing.JTextField();
        lblTituloInformativo3 = new javax.swing.JLabel();
        pnlTestigo = new javax.swing.JPanel();
        panelDatosTestigo = new javax.swing.JPanel();
        txtPrimerNombreTestigo = new javax.swing.JTextField();
        txtSegundoNombreTestigo = new javax.swing.JTextField();
        txtPrimerApellidoTestigo = new javax.swing.JTextField();
        txtSegundoApellidoTestigo = new javax.swing.JTextField();
        lblCedula6 = new javax.swing.JLabel();
        labelPrimerNombre4 = new javax.swing.JLabel();
        labelSegundoNombre4 = new javax.swing.JLabel();
        labelSegundoApellido4 = new javax.swing.JLabel();
        labelPrimerApellido4 = new javax.swing.JLabel();
        labelSeparator8 = new javax.swing.JLabel();
        labelSeparator9 = new javax.swing.JLabel();
        labelMandatory27 = new javax.swing.JLabel();
        labelMandatory28 = new javax.swing.JLabel();
        labelMandatory29 = new javax.swing.JLabel();
        labelMandatory30 = new javax.swing.JLabel();
        txtCedulaTestigo = new javax.swing.JTextField();
        lblTituloInformativo4 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("SGG | Registrar Grabacion");
        setBackground(new java.awt.Color(255, 255, 255));
        setIconImage(new ImageIcon(getClass().getResource("/favicon.png")).getImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        labelBanner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/banners/banner-registro.png"))); // NOI18N

        pnlFondo.setBackground(new java.awt.Color(255, 255, 255));

        contenedorTabs.setBackground(new java.awt.Color(255, 255, 255));
        contenedorTabs.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        pnlGrabacion.setBackground(new java.awt.Color(255, 255, 255));

        lblTituloInformativo.setText("Ingrese la información que se le solicita a continuación:");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Información"));

        txtNombreGrabacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreGrabacionKeyPressed(evt);
            }
        });

        txtUbicacionGrabacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUbicacionGrabacionKeyPressed(evt);
            }
        });

        cmbTipoCaso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione el tipo de caso...", "Discriminación en razón de género, edad, entre otros", "Despido de Trabajador Licencia de Paternidad", "Trabajadora embarazada o estado de lactancia", "Trabajadora embarazada. Restricción de derechos", "Fraccionamiento de jornada laboral", "Hostigamiento laboral", "Hostigamiento sexual", "Despido de aforado sindicato" }));
        cmbTipoCaso.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        try {
            txtCodigoSILAC.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("UU-UU-#####-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCodigoSILAC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoSILACKeyPressed(evt);
            }
        });

        spnFechaComparecencia.setModel(new javax.swing.SpinnerDateModel());

        lblNombreGrabacion.setText("Nombre de la grabación");

        lblTipoCaso.setText("Tipo de caso");

        lblUbicacion.setText("Ubicación");

        lblFechaComparecencia.setText("Fecha de comparecencia");

        lblSILAC.setText("Código SILAC");

        labelMandatory45.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory45.setText("*");

        labelMandatory46.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory46.setText("*");

        labelMandatory47.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory47.setText("*");

        labelMandatory48.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory48.setText("*");

        labelMandatory49.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory49.setText("*");

        lblLinkExpediente.setText("Link de expediente");

        txtLinkExpediente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLinkExpedienteKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblNombreGrabacion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelMandatory46, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblTipoCaso)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelMandatory47, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblUbicacion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelMandatory48, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblSILAC)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelMandatory45, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblFechaComparecencia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelMandatory49, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(spnFechaComparecencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTipoCaso, 0, 277, Short.MAX_VALUE)
                    .addComponent(txtNombreGrabacion)
                    .addComponent(txtCodigoSILAC)
                    .addComponent(txtUbicacionGrabacion)
                    .addComponent(lblLinkExpediente)
                    .addComponent(txtLinkExpediente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSILAC, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelMandatory45))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigoSILAC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreGrabacion)
                    .addComponent(labelMandatory46))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNombreGrabacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipoCaso)
                    .addComponent(labelMandatory47))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbTipoCaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUbicacion)
                    .addComponent(labelMandatory48))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUbicacionGrabacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLinkExpediente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtLinkExpediente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFechaComparecencia)
                    .addComponent(labelMandatory49))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spnFechaComparecencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlGrabacionLayout = new javax.swing.GroupLayout(pnlGrabacion);
        pnlGrabacion.setLayout(pnlGrabacionLayout);
        pnlGrabacionLayout.setHorizontalGroup(
            pnlGrabacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGrabacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGrabacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlGrabacionLayout.createSequentialGroup()
                        .addComponent(lblTituloInformativo)
                        .addGap(0, 51, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlGrabacionLayout.setVerticalGroup(
            pnlGrabacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGrabacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloInformativo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(218, Short.MAX_VALUE))
        );

        contenedorTabs.addTab("Grabación", pnlGrabacion);

        pnlGestionante.setBackground(new java.awt.Color(255, 255, 255));

        lblInstrucciones2.setText("Ingrese la informacion que se le solicita a continuacion:");

        panelDatosGestionante.setBackground(new java.awt.Color(255, 255, 255));
        panelDatosGestionante.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Persona Fisica", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));

        txtfldPrimerNombreGestionante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfldPrimerNombreGestionanteKeyPressed(evt);
            }
        });

        txtfldSegundoNombreGestionante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfldSegundoNombreGestionanteKeyPressed(evt);
            }
        });

        txtfldPrimerApellidoGestionante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfldPrimerApellidoGestionanteKeyPressed(evt);
            }
        });

        txtfldSegundoApellidoGestionante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfldSegundoApellidoGestionanteKeyPressed(evt);
            }
        });

        lblCedula2.setText("Cedula");

        labelPrimerNombre2.setText("Primer nombre");

        labelSegundoNombre2.setText("Segundo nombre");

        labelSegundoApellido2.setText("Segundo apellido");

        labelPrimerApellido2.setText("Primer apellido");

        labelSeparator4.setText("-");

        labelSeparator5.setText("-");

        labelMandatory19.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory19.setText("*");

        labelMandatory20.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory20.setText("*");

        labelMandatory21.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory21.setText("*");

        labelMandatory22.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory22.setText("*");

        txtfldCedulaGestionante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfldCedulaGestionanteKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelDatosGestionanteLayout = new javax.swing.GroupLayout(panelDatosGestionante);
        panelDatosGestionante.setLayout(panelDatosGestionanteLayout);
        panelDatosGestionanteLayout.setHorizontalGroup(
            panelDatosGestionanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosGestionanteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosGestionanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosGestionanteLayout.createSequentialGroup()
                        .addComponent(txtfldPrimerApellidoGestionante, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelSeparator5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfldSegundoApellidoGestionante, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDatosGestionanteLayout.createSequentialGroup()
                        .addComponent(lblCedula2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelMandatory19, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDatosGestionanteLayout.createSequentialGroup()
                        .addGroup(panelDatosGestionanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDatosGestionanteLayout.createSequentialGroup()
                                .addComponent(labelPrimerApellido2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelMandatory21, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelDatosGestionanteLayout.createSequentialGroup()
                                .addComponent(txtfldPrimerNombreGestionante, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelSeparator4)))
                        .addGap(7, 7, 7)
                        .addGroup(panelDatosGestionanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtfldSegundoNombreGestionante, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelSegundoNombre2)
                            .addGroup(panelDatosGestionanteLayout.createSequentialGroup()
                                .addComponent(labelSegundoApellido2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelMandatory22, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelDatosGestionanteLayout.createSequentialGroup()
                        .addComponent(labelPrimerNombre2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelMandatory20, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtfldCedulaGestionante, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDatosGestionanteLayout.setVerticalGroup(
            panelDatosGestionanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDatosGestionanteLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panelDatosGestionanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCedula2)
                    .addComponent(labelMandatory19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfldCedulaGestionante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosGestionanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPrimerNombre2)
                    .addComponent(labelSegundoNombre2)
                    .addComponent(labelMandatory20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosGestionanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfldSegundoNombreGestionante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfldPrimerNombreGestionante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSeparator4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosGestionanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPrimerApellido2)
                    .addComponent(labelSegundoApellido2)
                    .addComponent(labelMandatory21)
                    .addComponent(labelMandatory22))
                .addGroup(panelDatosGestionanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosGestionanteLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(txtfldPrimerApellidoGestionante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDatosGestionanteLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDatosGestionanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtfldSegundoApellidoGestionante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelSeparator5))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelSeleccionarAcompañantes.setBackground(new java.awt.Color(255, 255, 255));
        panelSeleccionarAcompañantes.setBorder(javax.swing.BorderFactory.createTitledBorder("Acompañantes"));

        btnAgregarTestigoGestionante.setText("Agregar Testigo");
        btnAgregarTestigoGestionante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregarTestigoGestionante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarTestigoGestionanteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarTestigoGestionanteMouseExited(evt);
            }
        });
        btnAgregarTestigoGestionante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarTestigoGestionanteActionPerformed(evt);
            }
        });

        btnAgregarAcompañanteGestionante.setText("Agregar Acompañante");
        btnAgregarAcompañanteGestionante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregarAcompañanteGestionante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarAcompañanteGestionanteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarAcompañanteGestionanteMouseExited(evt);
            }
        });
        btnAgregarAcompañanteGestionante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarAcompañanteGestionanteActionPerformed(evt);
            }
        });

        btnAgregarRepresentanteGestionante.setText("Agregar Representante");
        btnAgregarRepresentanteGestionante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregarRepresentanteGestionante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarRepresentanteGestionanteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarRepresentanteGestionanteMouseExited(evt);
            }
        });
        btnAgregarRepresentanteGestionante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarRepresentanteGestionanteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSeleccionarAcompañantesLayout = new javax.swing.GroupLayout(panelSeleccionarAcompañantes);
        panelSeleccionarAcompañantes.setLayout(panelSeleccionarAcompañantesLayout);
        panelSeleccionarAcompañantesLayout.setHorizontalGroup(
            panelSeleccionarAcompañantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSeleccionarAcompañantesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSeleccionarAcompañantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregarTestigoGestionante, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAgregarRepresentanteGestionante, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                    .addComponent(btnAgregarAcompañanteGestionante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelSeleccionarAcompañantesLayout.setVerticalGroup(
            panelSeleccionarAcompañantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSeleccionarAcompañantesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAgregarAcompañanteGestionante, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAgregarRepresentanteGestionante, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAgregarTestigoGestionante, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        labelMandatory41.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory41.setText("*");

        lblCedula3.setText("Tipo de identificacion");

        cmbTipoIdentificacionGestionante.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione la identificación...", "Persona física", "Persona jurídica", "Dimáx o cedula residencial" }));
        cmbTipoIdentificacionGestionante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbTipoIdentificacionGestionante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoIdentificacionGestionanteActionPerformed(evt);
            }
        });

        panelDatosGestionante1.setBackground(new java.awt.Color(255, 255, 255));
        panelDatosGestionante1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos personales", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP), "Persona Juridica", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));

        try {
            txtfldCedulaJuridicaGestionante.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#-###-######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtfldCedulaJuridicaGestionante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfldCedulaJuridicaGestionanteKeyPressed(evt);
            }
        });

        txtfldRazonSocialGestionante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfldRazonSocialGestionanteKeyPressed(evt);
            }
        });

        lblCedula10.setText("Cedula");

        labelPrimerNombre7.setText("Nombre de razón social");

        labelMandatory54.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory54.setText("*");

        labelMandatory55.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory55.setText("*");

        javax.swing.GroupLayout panelDatosGestionante1Layout = new javax.swing.GroupLayout(panelDatosGestionante1);
        panelDatosGestionante1.setLayout(panelDatosGestionante1Layout);
        panelDatosGestionante1Layout.setHorizontalGroup(
            panelDatosGestionante1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosGestionante1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosGestionante1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtfldRazonSocialGestionante)
                    .addGroup(panelDatosGestionante1Layout.createSequentialGroup()
                        .addGroup(panelDatosGestionante1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDatosGestionante1Layout.createSequentialGroup()
                                .addComponent(lblCedula10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelMandatory54, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelDatosGestionante1Layout.createSequentialGroup()
                                .addGroup(panelDatosGestionante1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtfldCedulaJuridicaGestionante, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelPrimerNombre7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelMandatory55, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 173, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelDatosGestionante1Layout.setVerticalGroup(
            panelDatosGestionante1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosGestionante1Layout.createSequentialGroup()
                .addGroup(panelDatosGestionante1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCedula10)
                    .addComponent(labelMandatory54))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfldCedulaJuridicaGestionante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosGestionante1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPrimerNombre7)
                    .addComponent(labelMandatory55))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfldRazonSocialGestionante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlGestionanteLayout = new javax.swing.GroupLayout(pnlGestionante);
        pnlGestionante.setLayout(pnlGestionanteLayout);
        pnlGestionanteLayout.setHorizontalGroup(
            pnlGestionanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGestionanteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGestionanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelDatosGestionante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlGestionanteLayout.createSequentialGroup()
                        .addGroup(pnlGestionanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlGestionanteLayout.createSequentialGroup()
                                .addComponent(lblCedula3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelMandatory41, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmbTipoIdentificacionGestionante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblInstrucciones2))
                        .addGap(0, 51, Short.MAX_VALUE))
                    .addComponent(panelDatosGestionante1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelSeleccionarAcompañantes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlGestionanteLayout.setVerticalGroup(
            pnlGestionanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGestionanteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInstrucciones2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGestionanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCedula3)
                    .addComponent(labelMandatory41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbTipoIdentificacionGestionante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelDatosGestionante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelDatosGestionante1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelSeleccionarAcompañantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        contenedorTabs.addTab("Gestionante", pnlGestionante);

        pnlGestionado.setBackground(new java.awt.Color(255, 255, 255));

        lblInstrucciones3.setText("Ingrese la informacion que se le solicita a continuacion:");

        panelDatosGestionado.setBackground(new java.awt.Color(255, 255, 255));
        panelDatosGestionado.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Persona Fisica", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));

        txtfldPrimerNombreGestionado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfldPrimerNombreGestionadoKeyPressed(evt);
            }
        });

        txtfldSegundoNombreGestionado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfldSegundoNombreGestionadoKeyPressed(evt);
            }
        });

        txtfldPrimerApellidoGestionado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfldPrimerApellidoGestionadoKeyPressed(evt);
            }
        });

        txtfldSegundoApellidoGestionado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfldSegundoApellidoGestionadoKeyPressed(evt);
            }
        });

        lblCedula4.setText("Cedula");

        labelPrimerNombre3.setText("Primer nombre");

        labelSegundoNombre3.setText("Segundo nombre");

        labelSegundoApellido3.setText("Segundo apellido");

        labelPrimerApellido3.setText("Primer apellido");

        labelSeparator6.setText("-");

        labelSeparator7.setText("-");

        labelMandatory23.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory23.setText("*");

        labelMandatory24.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory24.setText("*");

        labelMandatory25.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory25.setText("*");

        labelMandatory26.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory26.setText("*");

        txtfldCedulaGestionado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfldCedulaGestionadoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelDatosGestionadoLayout = new javax.swing.GroupLayout(panelDatosGestionado);
        panelDatosGestionado.setLayout(panelDatosGestionadoLayout);
        panelDatosGestionadoLayout.setHorizontalGroup(
            panelDatosGestionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosGestionadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosGestionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosGestionadoLayout.createSequentialGroup()
                        .addComponent(txtfldPrimerApellidoGestionado, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelSeparator7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfldSegundoApellidoGestionado, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDatosGestionadoLayout.createSequentialGroup()
                        .addComponent(lblCedula4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelMandatory23, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDatosGestionadoLayout.createSequentialGroup()
                        .addGroup(panelDatosGestionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDatosGestionadoLayout.createSequentialGroup()
                                .addComponent(labelPrimerApellido3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelMandatory25, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelDatosGestionadoLayout.createSequentialGroup()
                                .addComponent(txtfldPrimerNombreGestionado, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelSeparator6)))
                        .addGap(7, 7, 7)
                        .addGroup(panelDatosGestionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtfldSegundoNombreGestionado, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelSegundoNombre3)
                            .addGroup(panelDatosGestionadoLayout.createSequentialGroup()
                                .addComponent(labelSegundoApellido3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelMandatory26, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelDatosGestionadoLayout.createSequentialGroup()
                        .addComponent(labelPrimerNombre3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelMandatory24, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtfldCedulaGestionado, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDatosGestionadoLayout.setVerticalGroup(
            panelDatosGestionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDatosGestionadoLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panelDatosGestionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCedula4)
                    .addComponent(labelMandatory23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfldCedulaGestionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosGestionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPrimerNombre3)
                    .addComponent(labelSegundoNombre3)
                    .addComponent(labelMandatory24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosGestionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfldSegundoNombreGestionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfldPrimerNombreGestionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSeparator6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosGestionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPrimerApellido3)
                    .addComponent(labelSegundoApellido3)
                    .addComponent(labelMandatory25)
                    .addComponent(labelMandatory26))
                .addGroup(panelDatosGestionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosGestionadoLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(txtfldPrimerApellidoGestionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDatosGestionadoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDatosGestionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtfldSegundoApellidoGestionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelSeparator7))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblCedula5.setText("Tipo de Identificacion");

        cmbTipoIdentificacionGestionado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione la identificación...", "Persona física", "Persona jurídica", "Dimáx o cedula residencial" }));
        cmbTipoIdentificacionGestionado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbTipoIdentificacionGestionado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoIdentificacionGestionadoActionPerformed(evt);
            }
        });

        labelMandatory50.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory50.setText("*");

        panelDatosJuridicosGestionado.setBackground(new java.awt.Color(255, 255, 255));
        panelDatosJuridicosGestionado.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos personales", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP), "Persona Juridica", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));

        try {
            txtfldCedulaJuridicaGestionado.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#-###-######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtfldCedulaJuridicaGestionado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfldCedulaJuridicaGestionadoKeyPressed(evt);
            }
        });

        lblCedula11.setText("Cedula");

        labelPrimerNombre8.setText("Nombre de razon social");

        labelMandatory56.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory56.setText("*");

        labelMandatory57.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory57.setText("*");

        txtfldRazonSocialGestionado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfldRazonSocialGestionadoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelDatosJuridicosGestionadoLayout = new javax.swing.GroupLayout(panelDatosJuridicosGestionado);
        panelDatosJuridicosGestionado.setLayout(panelDatosJuridicosGestionadoLayout);
        panelDatosJuridicosGestionadoLayout.setHorizontalGroup(
            panelDatosJuridicosGestionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosJuridicosGestionadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosJuridicosGestionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtfldRazonSocialGestionado)
                    .addGroup(panelDatosJuridicosGestionadoLayout.createSequentialGroup()
                        .addGroup(panelDatosJuridicosGestionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDatosJuridicosGestionadoLayout.createSequentialGroup()
                                .addComponent(lblCedula11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelMandatory56, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelDatosJuridicosGestionadoLayout.createSequentialGroup()
                                .addGroup(panelDatosJuridicosGestionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtfldCedulaJuridicaGestionado, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelPrimerNombre8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelMandatory57, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 173, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelDatosJuridicosGestionadoLayout.setVerticalGroup(
            panelDatosJuridicosGestionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosJuridicosGestionadoLayout.createSequentialGroup()
                .addGroup(panelDatosJuridicosGestionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCedula11)
                    .addComponent(labelMandatory56))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfldCedulaJuridicaGestionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosJuridicosGestionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPrimerNombre8)
                    .addComponent(labelMandatory57))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfldRazonSocialGestionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelSeleccionarAcompañantes2.setBackground(new java.awt.Color(255, 255, 255));
        panelSeleccionarAcompañantes2.setBorder(javax.swing.BorderFactory.createTitledBorder("Acompañantes"));

        btnAgregarTestigoGestionado.setText("Agregar Testigo");
        btnAgregarTestigoGestionado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregarTestigoGestionado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarTestigoGestionadoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarTestigoGestionadoMouseExited(evt);
            }
        });
        btnAgregarTestigoGestionado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarTestigoGestionadoActionPerformed(evt);
            }
        });

        btnAgregarAcompañanteGestionado.setText("Agregar Acompañante");
        btnAgregarAcompañanteGestionado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregarAcompañanteGestionado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarAcompañanteGestionadoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarAcompañanteGestionadoMouseExited(evt);
            }
        });
        btnAgregarAcompañanteGestionado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarAcompañanteGestionadoActionPerformed(evt);
            }
        });

        btnAgregarRepresentanteGestionado.setText("Agregar Representante");
        btnAgregarRepresentanteGestionado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregarRepresentanteGestionado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarRepresentanteGestionadoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarRepresentanteGestionadoMouseExited(evt);
            }
        });
        btnAgregarRepresentanteGestionado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarRepresentanteGestionadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSeleccionarAcompañantes2Layout = new javax.swing.GroupLayout(panelSeleccionarAcompañantes2);
        panelSeleccionarAcompañantes2.setLayout(panelSeleccionarAcompañantes2Layout);
        panelSeleccionarAcompañantes2Layout.setHorizontalGroup(
            panelSeleccionarAcompañantes2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSeleccionarAcompañantes2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSeleccionarAcompañantes2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregarTestigoGestionado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAgregarRepresentanteGestionado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                    .addComponent(btnAgregarAcompañanteGestionado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelSeleccionarAcompañantes2Layout.setVerticalGroup(
            panelSeleccionarAcompañantes2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSeleccionarAcompañantes2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAgregarAcompañanteGestionado, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAgregarRepresentanteGestionado, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAgregarTestigoGestionado, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlGestionadoLayout = new javax.swing.GroupLayout(pnlGestionado);
        pnlGestionado.setLayout(pnlGestionadoLayout);
        pnlGestionadoLayout.setHorizontalGroup(
            pnlGestionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGestionadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGestionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelDatosGestionado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlGestionadoLayout.createSequentialGroup()
                        .addGroup(pnlGestionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblInstrucciones3)
                            .addGroup(pnlGestionadoLayout.createSequentialGroup()
                                .addComponent(lblCedula5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelMandatory50, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmbTipoIdentificacionGestionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 51, Short.MAX_VALUE))
                    .addComponent(panelDatosJuridicosGestionado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelSeleccionarAcompañantes2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlGestionadoLayout.setVerticalGroup(
            pnlGestionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGestionadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInstrucciones3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGestionadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCedula5)
                    .addComponent(labelMandatory50))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbTipoIdentificacionGestionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelDatosGestionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelDatosJuridicosGestionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelSeleccionarAcompañantes2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        contenedorTabs.addTab("Gestionado", pnlGestionado);

        pnlAcompañante.setBackground(new java.awt.Color(255, 255, 255));

        panelDatosAcompañante.setBackground(new java.awt.Color(255, 255, 255));
        panelDatosAcompañante.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos personales", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP), "Datos personales", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));

        txtPrimerNombreAcompañante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPrimerNombreAcompañanteKeyPressed(evt);
            }
        });

        txtSegundoNombreAcompañante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSegundoNombreAcompañanteKeyPressed(evt);
            }
        });

        txtPrimerApellidoAcompañante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPrimerApellidoAcompañanteKeyPressed(evt);
            }
        });

        txtSegundoApellidoAcompañante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSegundoApellidoAcompañanteKeyPressed(evt);
            }
        });

        lblCedula7.setText("Cedula");

        labelPrimerNombre6.setText("Primer nombre");

        labelSegundoNombre6.setText("Segundo nombre");

        labelSegundoApellido6.setText("Segundo apellido");

        labelPrimerApellido6.setText("Primer apellido");

        labelSeparator12.setText("-");

        labelSeparator13.setText("-");

        labelMandatory31.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory31.setText("*");

        labelMandatory32.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory32.setText("*");

        labelMandatory33.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory33.setText("*");

        labelMandatory34.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory34.setText("*");

        txtCedulaAcompañante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCedulaAcompañanteKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelDatosAcompañanteLayout = new javax.swing.GroupLayout(panelDatosAcompañante);
        panelDatosAcompañante.setLayout(panelDatosAcompañanteLayout);
        panelDatosAcompañanteLayout.setHorizontalGroup(
            panelDatosAcompañanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosAcompañanteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosAcompañanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosAcompañanteLayout.createSequentialGroup()
                        .addComponent(txtPrimerApellidoAcompañante, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelSeparator13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSegundoApellidoAcompañante, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDatosAcompañanteLayout.createSequentialGroup()
                        .addComponent(lblCedula7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelMandatory31, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDatosAcompañanteLayout.createSequentialGroup()
                        .addGroup(panelDatosAcompañanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDatosAcompañanteLayout.createSequentialGroup()
                                .addComponent(labelPrimerApellido6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelMandatory33, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelDatosAcompañanteLayout.createSequentialGroup()
                                .addComponent(txtPrimerNombreAcompañante, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelSeparator12)))
                        .addGap(7, 7, 7)
                        .addGroup(panelDatosAcompañanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSegundoNombreAcompañante, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelSegundoNombre6)
                            .addGroup(panelDatosAcompañanteLayout.createSequentialGroup()
                                .addComponent(labelSegundoApellido6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelMandatory34, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelDatosAcompañanteLayout.createSequentialGroup()
                        .addComponent(labelPrimerNombre6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelMandatory32, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCedulaAcompañante, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDatosAcompañanteLayout.setVerticalGroup(
            panelDatosAcompañanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDatosAcompañanteLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panelDatosAcompañanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCedula7)
                    .addComponent(labelMandatory31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCedulaAcompañante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosAcompañanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPrimerNombre6)
                    .addComponent(labelSegundoNombre6)
                    .addComponent(labelMandatory32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosAcompañanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSegundoNombreAcompañante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrimerNombreAcompañante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSeparator12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosAcompañanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPrimerApellido6)
                    .addComponent(labelSegundoApellido6)
                    .addComponent(labelMandatory33)
                    .addComponent(labelMandatory34))
                .addGroup(panelDatosAcompañanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosAcompañanteLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(panelDatosAcompañanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPrimerApellidoAcompañante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelSeparator13)))
                    .addGroup(panelDatosAcompañanteLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSegundoApellidoAcompañante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelDatosAcompañante1.setBackground(new java.awt.Color(255, 255, 255));
        panelDatosAcompañante1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Otros datos", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));

        cmbTipoAcompañante.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione un tipo de persona...", "Abogado (a)", "Responsable (Menor Edad)", "Sindicalista", "Otro" }));
        cmbTipoAcompañante.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbTipoAcompañante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoAcompañanteActionPerformed(evt);
            }
        });

        txtCondicionAcompañante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCondicionAcompañanteKeyPressed(evt);
            }
        });

        lblCedula8.setText("Tipo de persona");

        jLabel28.setText("En condición de:");

        labelMandatory39.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory39.setText("*");

        labelMandatory40.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory40.setText("*");

        javax.swing.GroupLayout panelDatosAcompañante1Layout = new javax.swing.GroupLayout(panelDatosAcompañante1);
        panelDatosAcompañante1.setLayout(panelDatosAcompañante1Layout);
        panelDatosAcompañante1Layout.setHorizontalGroup(
            panelDatosAcompañante1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosAcompañante1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosAcompañante1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCondicionAcompañante)
                    .addGroup(panelDatosAcompañante1Layout.createSequentialGroup()
                        .addGroup(panelDatosAcompañante1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDatosAcompañante1Layout.createSequentialGroup()
                                .addComponent(lblCedula8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelMandatory39, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelDatosAcompañante1Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelMandatory40, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmbTipoAcompañante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 120, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelDatosAcompañante1Layout.setVerticalGroup(
            panelDatosAcompañante1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosAcompañante1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panelDatosAcompañante1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCedula8)
                    .addComponent(labelMandatory39))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbTipoAcompañante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosAcompañante1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(labelMandatory40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCondicionAcompañante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblTituloInformativo2.setText("Ingrese la información que se le solicita a continuación:");

        javax.swing.GroupLayout pnlAcompañanteLayout = new javax.swing.GroupLayout(pnlAcompañante);
        pnlAcompañante.setLayout(pnlAcompañanteLayout);
        pnlAcompañanteLayout.setHorizontalGroup(
            pnlAcompañanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAcompañanteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAcompañanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelDatosAcompañante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlAcompañanteLayout.createSequentialGroup()
                        .addComponent(lblTituloInformativo2, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 38, Short.MAX_VALUE))
                    .addComponent(panelDatosAcompañante1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlAcompañanteLayout.setVerticalGroup(
            pnlAcompañanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAcompañanteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloInformativo2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelDatosAcompañante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelDatosAcompañante1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(228, Short.MAX_VALUE))
        );

        contenedorTabs.addTab("Acompañante", pnlAcompañante);

        pnlRepresentante.setBackground(new java.awt.Color(255, 255, 255));

        panelDatosRepresentante.setBackground(new java.awt.Color(255, 255, 255));
        panelDatosRepresentante.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos personales", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP), "Datos personales", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));

        txtPrimerNombreRepresentante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPrimerNombreRepresentanteKeyPressed(evt);
            }
        });

        txtSegundoNombreRepresentante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSegundoNombreRepresentanteKeyPressed(evt);
            }
        });

        txtPrimerApellidoRepresentante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPrimerApellidoRepresentanteKeyPressed(evt);
            }
        });

        txtSegundoApellidoRepresentante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSegundoApellidoRepresentanteKeyPressed(evt);
            }
        });

        lblCedula9.setText("Cedula");

        labelPrimerNombre5.setText("Primer nombre");

        labelSegundoNombre5.setText("Segundo nombre");

        labelSegundoApellido5.setText("Segundo apellido");

        labelPrimerApellido5.setText("Primer apellido");

        labelSeparator10.setText("-");

        labelSeparator11.setText("-");

        labelMandatory35.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory35.setText("*");

        labelMandatory36.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory36.setText("*");

        labelMandatory37.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory37.setText("*");

        labelMandatory38.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory38.setText("*");

        txtCedulaRepresentante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCedulaRepresentanteKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelDatosRepresentanteLayout = new javax.swing.GroupLayout(panelDatosRepresentante);
        panelDatosRepresentante.setLayout(panelDatosRepresentanteLayout);
        panelDatosRepresentanteLayout.setHorizontalGroup(
            panelDatosRepresentanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosRepresentanteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosRepresentanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosRepresentanteLayout.createSequentialGroup()
                        .addComponent(txtPrimerApellidoRepresentante, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelSeparator11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSegundoApellidoRepresentante, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDatosRepresentanteLayout.createSequentialGroup()
                        .addComponent(lblCedula9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelMandatory35, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDatosRepresentanteLayout.createSequentialGroup()
                        .addGroup(panelDatosRepresentanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDatosRepresentanteLayout.createSequentialGroup()
                                .addComponent(labelPrimerApellido5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelMandatory37, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelDatosRepresentanteLayout.createSequentialGroup()
                                .addComponent(txtPrimerNombreRepresentante, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelSeparator10)))
                        .addGap(7, 7, 7)
                        .addGroup(panelDatosRepresentanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSegundoNombreRepresentante, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelSegundoNombre5)
                            .addGroup(panelDatosRepresentanteLayout.createSequentialGroup()
                                .addComponent(labelSegundoApellido5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelMandatory38, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelDatosRepresentanteLayout.createSequentialGroup()
                        .addComponent(labelPrimerNombre5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelMandatory36, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCedulaRepresentante, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDatosRepresentanteLayout.setVerticalGroup(
            panelDatosRepresentanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDatosRepresentanteLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panelDatosRepresentanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCedula9)
                    .addComponent(labelMandatory35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCedulaRepresentante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosRepresentanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPrimerNombre5)
                    .addComponent(labelSegundoNombre5)
                    .addComponent(labelMandatory36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosRepresentanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSegundoNombreRepresentante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrimerNombreRepresentante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSeparator10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosRepresentanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPrimerApellido5)
                    .addComponent(labelSegundoApellido5)
                    .addComponent(labelMandatory37)
                    .addComponent(labelMandatory38))
                .addGroup(panelDatosRepresentanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosRepresentanteLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(panelDatosRepresentanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPrimerApellidoRepresentante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelSeparator11)))
                    .addGroup(panelDatosRepresentanteLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSegundoApellidoRepresentante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblTituloInformativo3.setText("Ingrese la información que se le solicita a continuación:");

        javax.swing.GroupLayout pnlRepresentanteLayout = new javax.swing.GroupLayout(pnlRepresentante);
        pnlRepresentante.setLayout(pnlRepresentanteLayout);
        pnlRepresentanteLayout.setHorizontalGroup(
            pnlRepresentanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRepresentanteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRepresentanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRepresentanteLayout.createSequentialGroup()
                        .addComponent(lblTituloInformativo3, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 38, Short.MAX_VALUE))
                    .addComponent(panelDatosRepresentante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlRepresentanteLayout.setVerticalGroup(
            pnlRepresentanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRepresentanteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloInformativo3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelDatosRepresentante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(363, Short.MAX_VALUE))
        );

        contenedorTabs.addTab("Representante", pnlRepresentante);

        pnlTestigo.setBackground(new java.awt.Color(255, 255, 255));

        panelDatosTestigo.setBackground(new java.awt.Color(255, 255, 255));
        panelDatosTestigo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos personales", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP), "Datos personales", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));

        txtPrimerNombreTestigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPrimerNombreTestigoKeyPressed(evt);
            }
        });

        txtSegundoNombreTestigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSegundoNombreTestigoKeyPressed(evt);
            }
        });

        txtPrimerApellidoTestigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPrimerApellidoTestigoKeyPressed(evt);
            }
        });

        txtSegundoApellidoTestigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSegundoApellidoTestigoKeyPressed(evt);
            }
        });

        lblCedula6.setText("Cedula");

        labelPrimerNombre4.setText("Primer nombre");

        labelSegundoNombre4.setText("Segundo nombre");

        labelSegundoApellido4.setText("Segundo apellido");

        labelPrimerApellido4.setText("Primer apellido");

        labelSeparator8.setText("-");

        labelSeparator9.setText("-");

        labelMandatory27.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory27.setText("*");

        labelMandatory28.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory28.setText("*");

        labelMandatory29.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory29.setText("*");

        labelMandatory30.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory30.setText("*");

        txtCedulaTestigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCedulaTestigoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelDatosTestigoLayout = new javax.swing.GroupLayout(panelDatosTestigo);
        panelDatosTestigo.setLayout(panelDatosTestigoLayout);
        panelDatosTestigoLayout.setHorizontalGroup(
            panelDatosTestigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosTestigoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosTestigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosTestigoLayout.createSequentialGroup()
                        .addGroup(panelDatosTestigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDatosTestigoLayout.createSequentialGroup()
                                .addComponent(lblCedula6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelMandatory27, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelDatosTestigoLayout.createSequentialGroup()
                                .addGroup(panelDatosTestigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelDatosTestigoLayout.createSequentialGroup()
                                        .addComponent(labelPrimerApellido4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelMandatory29, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelDatosTestigoLayout.createSequentialGroup()
                                        .addComponent(txtPrimerNombreTestigo, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelSeparator8))
                                    .addGroup(panelDatosTestigoLayout.createSequentialGroup()
                                        .addComponent(labelPrimerNombre4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelMandatory28, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(7, 7, 7)
                                .addGroup(panelDatosTestigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelSegundoNombre4)
                                    .addComponent(txtSegundoNombreTestigo, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelDatosTestigoLayout.createSequentialGroup()
                                        .addComponent(labelSegundoApellido4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelMandatory30, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(87, Short.MAX_VALUE))
                    .addGroup(panelDatosTestigoLayout.createSequentialGroup()
                        .addGroup(panelDatosTestigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDatosTestigoLayout.createSequentialGroup()
                                .addComponent(txtPrimerApellidoTestigo, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelSeparator9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSegundoApellidoTestigo, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtCedulaTestigo, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panelDatosTestigoLayout.setVerticalGroup(
            panelDatosTestigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDatosTestigoLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panelDatosTestigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCedula6)
                    .addComponent(labelMandatory27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCedulaTestigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosTestigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPrimerNombre4)
                    .addComponent(labelMandatory28)
                    .addComponent(labelSegundoNombre4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosTestigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSegundoNombreTestigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrimerNombreTestigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSeparator8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosTestigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPrimerApellido4)
                    .addComponent(labelSegundoApellido4)
                    .addComponent(labelMandatory29)
                    .addComponent(labelMandatory30))
                .addGroup(panelDatosTestigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosTestigoLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(panelDatosTestigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPrimerApellidoTestigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelSeparator9)))
                    .addGroup(panelDatosTestigoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSegundoApellidoTestigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblTituloInformativo4.setText("Ingrese la información que se le solicita a continuación:");

        javax.swing.GroupLayout pnlTestigoLayout = new javax.swing.GroupLayout(pnlTestigo);
        pnlTestigo.setLayout(pnlTestigoLayout);
        pnlTestigoLayout.setHorizontalGroup(
            pnlTestigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTestigoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTestigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelDatosTestigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlTestigoLayout.createSequentialGroup()
                        .addComponent(lblTituloInformativo4, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 38, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlTestigoLayout.setVerticalGroup(
            pnlTestigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTestigoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloInformativo4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelDatosTestigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(363, Short.MAX_VALUE))
        );

        contenedorTabs.addTab("Testigo", pnlTestigo);

        btnGuardar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarMouseExited(evt);
            }
        });
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFondoLayout = new javax.swing.GroupLayout(pnlFondo);
        pnlFondo.setLayout(pnlFondoLayout);
        pnlFondoLayout.setHorizontalGroup(
            pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenedorTabs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlFondoLayout.setVerticalGroup(
            pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFondoLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(contenedorTabs, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(labelBanner)
                .addGap(0, 0, 0)
                .addComponent(pnlFondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelBanner)
                    .addComponent(pnlFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        laRespuesta = JOptionPane.showConfirmDialog(null, 
                """
                ¿Desea regresar a la pantalla principal?
                Todos los datos ingresados se perder\u00e1n.
                """,
                "ATENCION",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        //se procesa la respuesta
        if (laRespuesta == JOptionPane.YES_OPTION) {
            telemetria.logActivity("Se sale de pantalla de registro de grabacion.");
            crearPantallaPrincipal();
        }
    }//GEN-LAST:event_formWindowClosing

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        telemetria.logActivity("Se hizo click en guardar datos.");
        procesarFormulario();
    }//GEN-LAST:event_btnGuardarActionPerformed
    
    private void cmbTipoIdentificacionGestionanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoIdentificacionGestionanteActionPerformed
        telemetria.logActivity("Se selecciona el tipo de identificacion del gestionante");
        
        try {
            desactivarFormularioGestionante();
            if (cmbTipoIdentificacionGestionante.getSelectedIndex() != 0) {
                if (cmbTipoIdentificacionGestionante.getSelectedIndex() == 2) {
                    activarFormularioGestionanteJuridico();
                } else {
                    //setTxtFieldMask();
                    activarFormularioGestionanteFisico();
                }
            }
        } catch (Exception ex) {
            telemetria.logException(ex);
        }
    }//GEN-LAST:event_cmbTipoIdentificacionGestionanteActionPerformed

    private void cmbTipoIdentificacionGestionadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoIdentificacionGestionadoActionPerformed
        telemetria.logActivity("Se selecciona el tipo de identificacion del gestionado");
        
        try {
            desactivarFormularioGestionado();
            if (cmbTipoIdentificacionGestionado.getSelectedIndex() != 0) {
                if (cmbTipoIdentificacionGestionado.getSelectedIndex() == 2) {
                    activarFormularioGestionadoJuridico();
                } else {
                    //setTxtFieldMask();
                    activarFormularioGestionadoFisico();
                }
            }
        } catch (Exception ex) {
            telemetria.logException(ex);
        }
    }//GEN-LAST:event_cmbTipoIdentificacionGestionadoActionPerformed

    private void btnAgregarAcompañanteGestionanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarAcompañanteGestionanteActionPerformed
        try {
            if (!seGuardoGestionante) {
                if (!sePuedeGuardar()) {
                    return;
                }
                capturarDatosGestionante();
                guardarDatosGestionante();
                seGuardoGestionante = true;
            }
            lastTab = 1;
            activarFormularioAcompañante();
            contenedorTabs.setEnabledAt(1, false);
            contenedorTabs.setSelectedIndex(3);
            currentTab = contenedorTabs.getSelectedIndex();
            actualizarBotonGuardar();
        } catch (Exception ex) {
            telemetria.logException(ex);
            reportarError(ex);
        }
    }//GEN-LAST:event_btnAgregarAcompañanteGestionanteActionPerformed

    private void btnAgregarRepresentanteGestionanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarRepresentanteGestionanteActionPerformed
        try {
            if (!seGuardoGestionante) {
                if (!sePuedeGuardar()) {
                    return;
                }
                capturarDatosGestionante();
                guardarDatosGestionante();
                seGuardoGestionante = true;
            }
            lastTab = 1;
            activarFormularioRepresentante();
            contenedorTabs.setEnabledAt(1, false);
            contenedorTabs.setSelectedIndex(4);
            currentTab = contenedorTabs.getSelectedIndex();
            actualizarBotonGuardar();
        }catch (Exception ex) {
            reportarError(ex);
            telemetria.logException(ex);
        }
    }//GEN-LAST:event_btnAgregarRepresentanteGestionanteActionPerformed

    private void btnAgregarTestigoGestionanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarTestigoGestionanteActionPerformed
        try {
            if (!seGuardoGestionante) {
                if (!sePuedeGuardar()) {
                    return;
                }
                capturarDatosGestionante();
                guardarDatosGestionante();
                seGuardoGestionante = true;
            }
            lastTab = 1;
            activarFormularioTestigo();
            contenedorTabs.setEnabledAt(1, false);
            contenedorTabs.setSelectedIndex(5);
            currentTab = contenedorTabs.getSelectedIndex();
            actualizarBotonGuardar();
        } catch (Exception ex) {
            reportarError(ex);
            telemetria.logException(ex);
        }
    }//GEN-LAST:event_btnAgregarTestigoGestionanteActionPerformed

    private void btnAgregarAcompañanteGestionadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarAcompañanteGestionadoActionPerformed
        try {
            if (!seGuardoGestionado) {
                if (!sePuedeGuardar()) {
                    return;
                }
                capturarDatosGestionado();
                guardarDatosGestionado();
                seGuardoGestionado = true;
            }
            lastTab = 2;
            activarFormularioAcompañante();
            contenedorTabs.setEnabledAt(2, false);
            contenedorTabs.setSelectedIndex(3);
            currentTab = contenedorTabs.getSelectedIndex();
            sePuedeSalir = false;
            actualizarBotonGuardar();
        } catch (Exception ex) {
            reportarError(ex);
            telemetria.logException(ex);
        }
    }//GEN-LAST:event_btnAgregarAcompañanteGestionadoActionPerformed

    private void btnAgregarRepresentanteGestionadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarRepresentanteGestionadoActionPerformed
        try {
            if (!seGuardoGestionado) {
                if (!sePuedeGuardar()) {
                    return;
                }
                capturarDatosGestionado();
                guardarDatosGestionado();
                seGuardoGestionado = true;
            }
            lastTab = 2;
            activarFormularioRepresentante();
            contenedorTabs.setEnabledAt(2, false);
            contenedorTabs.setSelectedIndex(4);
            currentTab = contenedorTabs.getSelectedIndex();
            sePuedeSalir = false;
            actualizarBotonGuardar();
        } catch (Exception ex) {
            reportarError(ex);
            telemetria.logException(ex);
        }
    }//GEN-LAST:event_btnAgregarRepresentanteGestionadoActionPerformed

    private void btnAgregarTestigoGestionadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarTestigoGestionadoActionPerformed
        try {
            if (!seGuardoGestionado) {
                if (!sePuedeGuardar()) {
                    return;
                }
                capturarDatosGestionado();
                guardarDatosGestionado();
                seGuardoGestionado = true;
            }
            lastTab = 2;
            activarFormularioTestigo();
            contenedorTabs.setEnabledAt(2, false);
            contenedorTabs.setSelectedIndex(5);
            currentTab = contenedorTabs.getSelectedIndex();
            sePuedeSalir = false;
            actualizarBotonGuardar();
        } catch (Exception ex) {
            reportarError(ex);
            telemetria.logException(ex);
        }
    }//GEN-LAST:event_btnAgregarTestigoGestionadoActionPerformed

    private void txtCodigoSILACKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoSILACKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtCodigoSILACKeyPressed

    private void txtNombreGrabacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreGrabacionKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtNombreGrabacionKeyPressed

    private void txtUbicacionGrabacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUbicacionGrabacionKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtUbicacionGrabacionKeyPressed

    private void txtfldPrimerNombreGestionanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfldPrimerNombreGestionanteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtfldPrimerNombreGestionanteKeyPressed

    private void txtfldSegundoNombreGestionanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfldSegundoNombreGestionanteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtfldSegundoNombreGestionanteKeyPressed

    private void txtfldPrimerApellidoGestionanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfldPrimerApellidoGestionanteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtfldPrimerApellidoGestionanteKeyPressed

    private void txtfldSegundoApellidoGestionanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfldSegundoApellidoGestionanteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtfldSegundoApellidoGestionanteKeyPressed

    private void txtfldCedulaJuridicaGestionanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfldCedulaJuridicaGestionanteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtfldCedulaJuridicaGestionanteKeyPressed

    private void txtfldRazonSocialGestionanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfldRazonSocialGestionanteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtfldRazonSocialGestionanteKeyPressed

    private void txtfldPrimerNombreGestionadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfldPrimerNombreGestionadoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtfldPrimerNombreGestionadoKeyPressed

    private void txtfldSegundoNombreGestionadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfldSegundoNombreGestionadoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtfldSegundoNombreGestionadoKeyPressed

    private void txtfldPrimerApellidoGestionadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfldPrimerApellidoGestionadoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtfldPrimerApellidoGestionadoKeyPressed

    private void txtfldSegundoApellidoGestionadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfldSegundoApellidoGestionadoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtfldSegundoApellidoGestionadoKeyPressed

    private void txtfldCedulaJuridicaGestionadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfldCedulaJuridicaGestionadoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtfldCedulaJuridicaGestionadoKeyPressed

    private void txtfldRazonSocialGestionadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfldRazonSocialGestionadoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtfldRazonSocialGestionadoKeyPressed

    private void txtPrimerNombreAcompañanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrimerNombreAcompañanteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtPrimerNombreAcompañanteKeyPressed

    private void txtSegundoNombreAcompañanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSegundoNombreAcompañanteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtSegundoNombreAcompañanteKeyPressed

    private void txtPrimerApellidoAcompañanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrimerApellidoAcompañanteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtPrimerApellidoAcompañanteKeyPressed

    private void txtSegundoApellidoAcompañanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSegundoApellidoAcompañanteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtSegundoApellidoAcompañanteKeyPressed

    private void txtCondicionAcompañanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCondicionAcompañanteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtCondicionAcompañanteKeyPressed

    private void txtPrimerNombreRepresentanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrimerNombreRepresentanteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtPrimerNombreRepresentanteKeyPressed

    private void txtSegundoNombreRepresentanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSegundoNombreRepresentanteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtSegundoNombreRepresentanteKeyPressed

    private void txtPrimerApellidoRepresentanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrimerApellidoRepresentanteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtPrimerApellidoRepresentanteKeyPressed

    private void txtSegundoApellidoRepresentanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSegundoApellidoRepresentanteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtSegundoApellidoRepresentanteKeyPressed

    private void txtPrimerNombreTestigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrimerNombreTestigoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtPrimerNombreTestigoKeyPressed

    private void txtSegundoNombreTestigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSegundoNombreTestigoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtSegundoNombreTestigoKeyPressed

    private void txtPrimerApellidoTestigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrimerApellidoTestigoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtPrimerApellidoTestigoKeyPressed

    private void txtSegundoApellidoTestigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSegundoApellidoTestigoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtSegundoApellidoTestigoKeyPressed

    private void cmbTipoAcompañanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoAcompañanteActionPerformed
        if (cmbTipoAcompañante.getSelectedIndex() == 1) {
            txtCondicionAcompañante.setText("");
        } else {
            txtCondicionAcompañante.setEnabled(true);
        }
    }//GEN-LAST:event_cmbTipoAcompañanteActionPerformed

    private void txtfldCedulaGestionanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfldCedulaGestionanteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtfldCedulaGestionanteKeyPressed

    private void txtfldCedulaGestionadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfldCedulaGestionadoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtfldCedulaGestionadoKeyPressed

    private void txtCedulaAcompañanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaAcompañanteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtCedulaAcompañanteKeyPressed

    private void txtCedulaRepresentanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaRepresentanteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtCedulaRepresentanteKeyPressed

    private void txtCedulaTestigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaTestigoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtCedulaTestigoKeyPressed

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        if (!"Iniciar Grabación".equals(btnGuardar.getText()))
            if (btnGuardar.isEnabled())
                setButtonActiveColor(btnGuardar);
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        if (!"Iniciar Grabación".equals(btnGuardar.getText()))
            if (btnGuardar.isEnabled())
                setButtonNormalColor(btnGuardar);
    }//GEN-LAST:event_btnGuardarMouseExited

    private void btnAgregarAcompañanteGestionanteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarAcompañanteGestionanteMouseEntered
        if (btnAgregarAcompañanteGestionante.isEnabled())
        setButtonActiveColor(btnAgregarAcompañanteGestionante);
    }//GEN-LAST:event_btnAgregarAcompañanteGestionanteMouseEntered

    private void btnAgregarAcompañanteGestionanteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarAcompañanteGestionanteMouseExited
        if (btnAgregarAcompañanteGestionante.isEnabled())
        setButtonNormalColor(btnAgregarAcompañanteGestionante);
    }//GEN-LAST:event_btnAgregarAcompañanteGestionanteMouseExited

    private void btnAgregarRepresentanteGestionanteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarRepresentanteGestionanteMouseEntered
        if (btnAgregarRepresentanteGestionante.isEnabled())
        setButtonActiveColor(btnAgregarRepresentanteGestionante);
    }//GEN-LAST:event_btnAgregarRepresentanteGestionanteMouseEntered

    private void btnAgregarRepresentanteGestionanteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarRepresentanteGestionanteMouseExited
        if (btnAgregarRepresentanteGestionante.isEnabled())
        setButtonNormalColor(btnAgregarRepresentanteGestionante);
    }//GEN-LAST:event_btnAgregarRepresentanteGestionanteMouseExited

    private void btnAgregarTestigoGestionanteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarTestigoGestionanteMouseEntered
        if (btnAgregarTestigoGestionante.isEnabled())
        setButtonActiveColor(btnAgregarTestigoGestionante);
    }//GEN-LAST:event_btnAgregarTestigoGestionanteMouseEntered

    private void btnAgregarTestigoGestionanteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarTestigoGestionanteMouseExited
        if (btnAgregarTestigoGestionante.isEnabled())
        setButtonNormalColor(btnAgregarTestigoGestionante);
    }//GEN-LAST:event_btnAgregarTestigoGestionanteMouseExited

    private void btnAgregarAcompañanteGestionadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarAcompañanteGestionadoMouseEntered
        if (btnAgregarAcompañanteGestionado.isEnabled())
        setButtonActiveColor(btnAgregarAcompañanteGestionado);
    }//GEN-LAST:event_btnAgregarAcompañanteGestionadoMouseEntered

    private void btnAgregarAcompañanteGestionadoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarAcompañanteGestionadoMouseExited
        if (btnAgregarAcompañanteGestionado.isEnabled())
        setButtonNormalColor(btnAgregarAcompañanteGestionado);
    }//GEN-LAST:event_btnAgregarAcompañanteGestionadoMouseExited

    private void btnAgregarRepresentanteGestionadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarRepresentanteGestionadoMouseEntered
        if (btnAgregarRepresentanteGestionado.isEnabled())
        setButtonActiveColor(btnAgregarRepresentanteGestionado);
    }//GEN-LAST:event_btnAgregarRepresentanteGestionadoMouseEntered

    private void btnAgregarRepresentanteGestionadoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarRepresentanteGestionadoMouseExited
        if (btnAgregarRepresentanteGestionado.isEnabled())
        setButtonNormalColor(btnAgregarRepresentanteGestionado);
    }//GEN-LAST:event_btnAgregarRepresentanteGestionadoMouseExited

    private void btnAgregarTestigoGestionadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarTestigoGestionadoMouseEntered
        if (btnAgregarTestigoGestionado.isEnabled())
        setButtonActiveColor(btnAgregarTestigoGestionado);
    }//GEN-LAST:event_btnAgregarTestigoGestionadoMouseEntered

    private void btnAgregarTestigoGestionadoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarTestigoGestionadoMouseExited
        if (btnAgregarTestigoGestionado.isEnabled())
        setButtonNormalColor(btnAgregarTestigoGestionado);
    }//GEN-LAST:event_btnAgregarTestigoGestionadoMouseExited

    private void txtLinkExpedienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLinkExpedienteKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLinkExpedienteKeyPressed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            //setear tema como el tema de windows
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new RegistroGrabacion().setVisible(true);
        });
    }

    private void initForm() {
        telemetria.logActivity("Se ingresa a pantalla de registro de grabacion.");
        actualizarBotonGuardar();
        setButtonNormalColor(btnGuardar);
        configurarSpinners();
        addTextFieldPopup();
        desactivarFormularioGestionante();
        borrarFormularioGestionante();
        desactivarFormularioGestionado();
        borrarFormularioGestionado();
        desactivarFormularioAcompañante();
        borrarFormularioAcompañante();
        desactivarFormularioRepresentante();
        borrarFormularioRepresentante();
        desactivarFormularioTestigo();
        borrarFormularioTestigo();
        activarFormularioGrabacion();
    }

    private void initVariables() {
        PANTONE2727C = new Color(82, 132, 196);
        PANTONE1595C = new Color(221, 103, 15);
        PANTONE420C = new Color(200, 200, 200);
        PANTONE422C =  new Color(157, 160, 161);
        telemetria = new Telemetria();
        database = new MetodosDatabase();
        archivo = new Archivos();
        persona = new Persona();
        personaJuridica = new PersonaJuridica();
        gestionado = new Gestionado();
        gestionante = new Gestionante();
        comparecencia = new Comparecencia();
        audio = new Audio();
        testigo = new Testigo();
        acompañante = new Acompañante();
        representante = new Representante();
        seGuardoGestionado = false;
        seGuardoGestionante = false;
        todosLosDatosIngresados = false;
        sePuedeSalir = false;
        currentTab = 0;
        lastTab = 0;
        laRespuesta = 0;
    }

    private void disposeVariables() {
        telemetria = null;
        database = null;
        archivo = null;
        persona = null;
        personaJuridica = null;
        gestionado = null;
        gestionante = null;
        comparecencia = null;
        audio = null;
        testigo = null;
        acompañante = null;
        representante = null;
        seGuardoGestionado = null;
        seGuardoGestionante = null;
        todosLosDatosIngresados = null;
        sePuedeSalir = null;
        currentTab = 0;
        lastTab = 0;
        laRespuesta = 0;
    }
    
    private Color PANTONE2727C;
    private Color PANTONE1595C;
    private Color PANTONE420C;
    private Color PANTONE422C;
    private Telemetria telemetria;
    private MetodosDatabase database;
    private Archivos archivo;
    private Persona persona;
    private PersonaJuridica personaJuridica;
    private Inspector inspector;
    private Gestionado gestionado;
    private Gestionante gestionante;
    private Comparecencia comparecencia;
    private Audio audio;
    private Testigo testigo;
    private Acompañante acompañante;
    private Representante representante;
    private Principal pantallaPrincipal;
    private Comparecencias pantallaComparecencia;
    private Boolean seGuardoGestionado;
    private Boolean seGuardoGestionante;
    private Boolean todosLosDatosIngresados;
    private Boolean sePuedeSalir;
    private int currentTab;
    private int lastTab;
    private int laRespuesta;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarAcompañanteGestionado;
    private javax.swing.JButton btnAgregarAcompañanteGestionante;
    private javax.swing.JButton btnAgregarRepresentanteGestionado;
    private javax.swing.JButton btnAgregarRepresentanteGestionante;
    private javax.swing.JButton btnAgregarTestigoGestionado;
    private javax.swing.JButton btnAgregarTestigoGestionante;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbTipoAcompañante;
    private javax.swing.JComboBox<String> cmbTipoCaso;
    private javax.swing.JComboBox<String> cmbTipoIdentificacionGestionado;
    private javax.swing.JComboBox<String> cmbTipoIdentificacionGestionante;
    private javax.swing.JTabbedPane contenedorTabs;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelBanner;
    private javax.swing.JLabel labelMandatory19;
    private javax.swing.JLabel labelMandatory20;
    private javax.swing.JLabel labelMandatory21;
    private javax.swing.JLabel labelMandatory22;
    private javax.swing.JLabel labelMandatory23;
    private javax.swing.JLabel labelMandatory24;
    private javax.swing.JLabel labelMandatory25;
    private javax.swing.JLabel labelMandatory26;
    private javax.swing.JLabel labelMandatory27;
    private javax.swing.JLabel labelMandatory28;
    private javax.swing.JLabel labelMandatory29;
    private javax.swing.JLabel labelMandatory30;
    private javax.swing.JLabel labelMandatory31;
    private javax.swing.JLabel labelMandatory32;
    private javax.swing.JLabel labelMandatory33;
    private javax.swing.JLabel labelMandatory34;
    private javax.swing.JLabel labelMandatory35;
    private javax.swing.JLabel labelMandatory36;
    private javax.swing.JLabel labelMandatory37;
    private javax.swing.JLabel labelMandatory38;
    private javax.swing.JLabel labelMandatory39;
    private javax.swing.JLabel labelMandatory40;
    private javax.swing.JLabel labelMandatory41;
    private javax.swing.JLabel labelMandatory45;
    private javax.swing.JLabel labelMandatory46;
    private javax.swing.JLabel labelMandatory47;
    private javax.swing.JLabel labelMandatory48;
    private javax.swing.JLabel labelMandatory49;
    private javax.swing.JLabel labelMandatory50;
    private javax.swing.JLabel labelMandatory54;
    private javax.swing.JLabel labelMandatory55;
    private javax.swing.JLabel labelMandatory56;
    private javax.swing.JLabel labelMandatory57;
    private javax.swing.JLabel labelPrimerApellido2;
    private javax.swing.JLabel labelPrimerApellido3;
    private javax.swing.JLabel labelPrimerApellido4;
    private javax.swing.JLabel labelPrimerApellido5;
    private javax.swing.JLabel labelPrimerApellido6;
    private javax.swing.JLabel labelPrimerNombre2;
    private javax.swing.JLabel labelPrimerNombre3;
    private javax.swing.JLabel labelPrimerNombre4;
    private javax.swing.JLabel labelPrimerNombre5;
    private javax.swing.JLabel labelPrimerNombre6;
    private javax.swing.JLabel labelPrimerNombre7;
    private javax.swing.JLabel labelPrimerNombre8;
    private javax.swing.JLabel labelSegundoApellido2;
    private javax.swing.JLabel labelSegundoApellido3;
    private javax.swing.JLabel labelSegundoApellido4;
    private javax.swing.JLabel labelSegundoApellido5;
    private javax.swing.JLabel labelSegundoApellido6;
    private javax.swing.JLabel labelSegundoNombre2;
    private javax.swing.JLabel labelSegundoNombre3;
    private javax.swing.JLabel labelSegundoNombre4;
    private javax.swing.JLabel labelSegundoNombre5;
    private javax.swing.JLabel labelSegundoNombre6;
    private javax.swing.JLabel labelSeparator10;
    private javax.swing.JLabel labelSeparator11;
    private javax.swing.JLabel labelSeparator12;
    private javax.swing.JLabel labelSeparator13;
    private javax.swing.JLabel labelSeparator4;
    private javax.swing.JLabel labelSeparator5;
    private javax.swing.JLabel labelSeparator6;
    private javax.swing.JLabel labelSeparator7;
    private javax.swing.JLabel labelSeparator8;
    private javax.swing.JLabel labelSeparator9;
    private javax.swing.JLabel lblCedula10;
    private javax.swing.JLabel lblCedula11;
    private javax.swing.JLabel lblCedula2;
    private javax.swing.JLabel lblCedula3;
    private javax.swing.JLabel lblCedula4;
    private javax.swing.JLabel lblCedula5;
    private javax.swing.JLabel lblCedula6;
    private javax.swing.JLabel lblCedula7;
    private javax.swing.JLabel lblCedula8;
    private javax.swing.JLabel lblCedula9;
    private javax.swing.JLabel lblFechaComparecencia;
    private javax.swing.JLabel lblInstrucciones2;
    private javax.swing.JLabel lblInstrucciones3;
    private javax.swing.JLabel lblLinkExpediente;
    private javax.swing.JLabel lblNombreGrabacion;
    private javax.swing.JLabel lblSILAC;
    private javax.swing.JLabel lblTipoCaso;
    private javax.swing.JLabel lblTituloInformativo;
    private javax.swing.JLabel lblTituloInformativo2;
    private javax.swing.JLabel lblTituloInformativo3;
    private javax.swing.JLabel lblTituloInformativo4;
    private javax.swing.JLabel lblUbicacion;
    private javax.swing.JPanel panelDatosAcompañante;
    private javax.swing.JPanel panelDatosAcompañante1;
    private javax.swing.JPanel panelDatosGestionado;
    private javax.swing.JPanel panelDatosGestionante;
    private javax.swing.JPanel panelDatosGestionante1;
    private javax.swing.JPanel panelDatosJuridicosGestionado;
    private javax.swing.JPanel panelDatosRepresentante;
    private javax.swing.JPanel panelDatosTestigo;
    private javax.swing.JPanel panelSeleccionarAcompañantes;
    private javax.swing.JPanel panelSeleccionarAcompañantes2;
    private javax.swing.JPanel pnlAcompañante;
    private javax.swing.JPanel pnlFondo;
    private javax.swing.JPanel pnlGestionado;
    private javax.swing.JPanel pnlGestionante;
    private javax.swing.JPanel pnlGrabacion;
    private javax.swing.JPanel pnlRepresentante;
    private javax.swing.JPanel pnlTestigo;
    private javax.swing.JSpinner spnFechaComparecencia;
    private javax.swing.JTextField txtCedulaAcompañante;
    private javax.swing.JTextField txtCedulaRepresentante;
    private javax.swing.JTextField txtCedulaTestigo;
    private javax.swing.JFormattedTextField txtCodigoSILAC;
    private javax.swing.JTextField txtCondicionAcompañante;
    private javax.swing.JTextField txtLinkExpediente;
    private javax.swing.JTextField txtNombreGrabacion;
    private javax.swing.JTextField txtPrimerApellidoAcompañante;
    private javax.swing.JTextField txtPrimerApellidoRepresentante;
    private javax.swing.JTextField txtPrimerApellidoTestigo;
    private javax.swing.JTextField txtPrimerNombreAcompañante;
    private javax.swing.JTextField txtPrimerNombreRepresentante;
    private javax.swing.JTextField txtPrimerNombreTestigo;
    private javax.swing.JTextField txtSegundoApellidoAcompañante;
    private javax.swing.JTextField txtSegundoApellidoRepresentante;
    private javax.swing.JTextField txtSegundoApellidoTestigo;
    private javax.swing.JTextField txtSegundoNombreAcompañante;
    private javax.swing.JTextField txtSegundoNombreRepresentante;
    private javax.swing.JTextField txtSegundoNombreTestigo;
    private javax.swing.JTextField txtUbicacionGrabacion;
    private javax.swing.JTextField txtfldCedulaGestionado;
    private javax.swing.JTextField txtfldCedulaGestionante;
    private javax.swing.JFormattedTextField txtfldCedulaJuridicaGestionado;
    private javax.swing.JFormattedTextField txtfldCedulaJuridicaGestionante;
    private javax.swing.JTextField txtfldPrimerApellidoGestionado;
    private javax.swing.JTextField txtfldPrimerApellidoGestionante;
    private javax.swing.JTextField txtfldPrimerNombreGestionado;
    private javax.swing.JTextField txtfldPrimerNombreGestionante;
    private javax.swing.JTextField txtfldRazonSocialGestionado;
    private javax.swing.JTextField txtfldRazonSocialGestionante;
    private javax.swing.JTextField txtfldSegundoApellidoGestionado;
    private javax.swing.JTextField txtfldSegundoApellidoGestionante;
    private javax.swing.JTextField txtfldSegundoNombreGestionado;
    private javax.swing.JTextField txtfldSegundoNombreGestionante;
    // End of variables declaration//GEN-END:variables
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