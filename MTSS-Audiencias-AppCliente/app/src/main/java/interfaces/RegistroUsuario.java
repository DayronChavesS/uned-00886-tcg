package interfaces;

import database.MetodosDatabase;
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.Action;
import models.Inspector;
import models.Oficina;
import models.Puesto;
import models.Region;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.text.DefaultEditorKit;
import methods.Telemetria;
import models.Persona;
import models.Usuario;

public class RegistroUsuario extends javax.swing.JFrame {
    
    public RegistroUsuario() {
        initComponents();
        initVariables();
        initForm();
    }
    
    private void crearPantallaInicio(){
        pantallaInicio = new Inicio();
        pantallaInicio.setLocationRelativeTo(null);
        pantallaInicio.setVisible(true);
        disposeVariables();
        this.dispose();
    }
    
    private void crearPantallaInicioSesion(){
        pantallaIniciarSesion = new IniciosSesion();
        pantallaIniciarSesion.setLocationRelativeTo(null);
        pantallaIniciarSesion.setVisible(true);
        disposeVariables();
        this.dispose();
    }
    
    private void capturarDatos() throws Exception {
        persona.setCedula(txtFieldCedula.getText(),true);
        persona.setPrimerNombre(txtFieldPrimerNombre.getText());
        persona.setSegundoNombre(txtFieldSegundoNombre.getText());
        persona.setPrimerApellido(txtFieldPrimerApellido.getText());
        persona.setSegundoApellido(txtFieldSegundoApellido.getText());
        usuario.setEmail(txtFieldEmail.getText(),true);
        usuario.setContraseña(String.valueOf(txtFieldContraseña.getPassword()));
        usuario.setEnLinea(false);
        contraseña2 = String.valueOf(txtFieldContraseña2.getPassword());
        verificarContraseña(usuario.getContraseña(), contraseña2);
        inspector.setPuesto(cbmbxPuesto.getSelectedItem().toString());
        inspector.setOficina(cbmbxOficina.getSelectedItem().toString());
        inspector.setRegion(cbmbxRegion.getSelectedItem().toString(), inspector.getOficina());
        usuario.setPersona(persona);
        inspector.setPersona(persona);
        inspector.setUsuario(usuario);

    }

    private void guardarDatos() throws Exception {
        try{
            inspector = database.guardarInspector(inspector);
        }catch(Exception ex){
            reportarError(ex);
        }
    }
    
    private void verificarContraseña(String contraseña1, String contraseña2)throws Exception{
        if (!contraseña1.equals(contraseña2)) {
            throw new Exception("contraseña no coincide");
        }
    }
    
    private void setComboBoxModels(){
        cbmbxRegion.setModel(new DefaultComboBoxModel(Region.values()));
        cbmbxOficina.setModel(new DefaultComboBoxModel(Oficina.values()));
        cbmbxPuesto.setModel(new DefaultComboBoxModel(Puesto.values()));
        //SE REMUEVE EL PUESTO ADMINISTRADOR POR RAZONES DE SEGURIDAD
        cbmbxPuesto.removeItemAt(0);
        //SE INSERTA VALORES GENERICOS
        cbmbxRegion.insertItemAt("Seleccione una región...", 0);
        cbmbxOficina.insertItemAt("Seleccione una oficina...", 0);
        cbmbxPuesto.insertItemAt("Seleccione un puesto...", 0);
        cbmbxRegion.setSelectedIndex(0);
        cbmbxOficina.setSelectedIndex(0);
        cbmbxPuesto.setSelectedIndex(0);
    }
    
    private void updateComboBoxOficina(){
        cbmbxOficina.removeAllItems();
        cbmbxOficina.insertItemAt("Seleccione una oficina...", 0);
        cbmbxOficina.setSelectedIndex(0);
        switch (cbmbxRegion.getSelectedIndex()) {
            case 0 -> {
                return;
            }
            case 1 -> {
                int index = 1;
                for (int i = 0; i < 4; i++) {
                    cbmbxOficina.insertItemAt(Oficina.values()[i].toString(), index);
                    index++;
                }
                break;
            }
            case 2 -> {
                int index = 1;
                for (int i = 4; i < 8; i++) {
                    cbmbxOficina.insertItemAt(Oficina.values()[i].toString(), index);
                    index++;
                }
                break;
            }
            case 3 -> {
                int index = 1;
                for (int i = 8; i < 13; i++) {
                    cbmbxOficina.insertItemAt(Oficina.values()[i].toString(), index);
                    index++;
                }
                break;
            }
            case 4 -> {
                int index = 1;
                for (int i = 13; i < 16; i++) {
                    cbmbxOficina.insertItemAt(Oficina.values()[i].toString(), index);
                    index++;
                }
                break;
            }
            case 5 -> {
                int index = 1;
                for (int i = 16; i < 21; i++) {
                    cbmbxOficina.insertItemAt(Oficina.values()[i].toString(), index);
                    index++;
                }
                break;
            }
            case 6 -> {
                int index = 1;
                for (int i = 21; i < 25; i++) {
                    cbmbxOficina.insertItemAt(Oficina.values()[i].toString(), index);
                    index++;
                }
                break;
            }
        }
    }
    
    private void reportarError(Exception ex){
        
        switch (ex.getMessage()) {
            case "cedula null error" -> {
                crearMensajeError("La cedula no puede estar vacia.");
                telemetria.logException(ex);
                break;
            }

            case "primerNombre null error" -> {
                crearMensajeError("El primer nombre no puede estar vacio.");
                telemetria.logException(ex);
                break;
            }

            case "primerApellido null error" -> {
                crearMensajeError("El primer apellido no puede estar vacio.");
                telemetria.logException(ex);
                break;
            }

            case "segundoApellido null error" -> {
                crearMensajeError("El segundo apellido no puede estar vacio.");
                telemetria.logException(ex);
                break;
            }

            case "email null error" -> {
                crearMensajeError("El email no puede estar vacio.");
                telemetria.logException(ex);
                break;
            }

            case "contraseña null error" -> {
                crearMensajeError("La contraseña no puede estar vacia.");
                telemetria.logException(ex);
                break;
            }

            case "puesto null error" -> {
                crearMensajeError("No ha seleccionado un puesto.");
                telemetria.logException(ex);
                break;
            }

            case "region null error" -> {
                crearMensajeError("No ha seleccionado una region.");
                telemetria.logException(ex);
                break;
            }

            case "oficina null error" -> {
                crearMensajeError("No ha seleccionado una oficina.");
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
            case "email size error" -> {
                crearMensajeError("El correo es demasiado grande.");
                telemetria.logException(ex);
                break;
            }
            case "email format error" -> {
                crearMensajeError("Solo se aceptan correos del dominio \"@mtss.go.cr\"");
                telemetria.logException(ex);
                break;
            }
            case "contraseña big size error" -> {
                crearMensajeError("La contraseña es demasiado grande.");
                telemetria.logException(ex);
                break;
            }
            case "contraseña small size error" -> {
                crearMensajeError("La contraseña es demasiado corta.");
                telemetria.logException(ex);
                break;
            }
            case "contraseña no coincide" -> {
                crearMensajeError("Las contraseñas no coinciden.");
                telemetria.logException(ex);
                break;
            }
            case "region error" -> {
                crearMensajeError("La oficina que ha seleccionado no pertenece a esa region.");
                telemetria.logException(ex);
                break;
            }
            case "cedula UNIQUE error" -> {
                crearMensajeError("Ya hay una persona registrada con ese numero de cedula.");
                telemetria.logException(ex);
                break;
            }
            case "email UNIQUE error" -> {
                crearMensajeError("Ya hay una persona registrada con ese email.");
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
    
    private void crearMensajeError(String error){
        JOptionPane.showMessageDialog(null, 
                error,
                "ERROR",
                JOptionPane.ERROR_MESSAGE);
    }
    
    private void procesarFormulario(){
        try {
            //FASE 1: SE CAPTURAN LOS DATOS INGRESADOS
            capturarDatos();
            
            //FASE 2: SE GUARDAN LOS DATOS
            guardarDatos();

            //FASE 3: SE INTENTA INICIAR SESION
            //se pide confirmacion del usuario
            laRespuesta = JOptionPane.showConfirmDialog(null, 
                    """
                      Se ha registrado su usuario. 
                      ¿Desea iniciar sesion?
                    """,
                    "Registro exitoso",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);

            //se procesa la respuesta
            if (laRespuesta == JOptionPane.NO_OPTION) {
                crearPantallaInicio();
                
            } else {
                crearPantallaInicioSesion();
            }

        } catch (Exception ex) {
            reportarError(ex);
        }
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
        
        txtFieldPrimerNombre.setComponentPopupMenu( menu );
        txtFieldPrimerNombre.setComponentPopupMenu( menu );
        txtFieldSegundoNombre.setComponentPopupMenu( menu );
        txtFieldPrimerApellido.setComponentPopupMenu( menu );
        txtFieldSegundoApellido.setComponentPopupMenu( menu );
        txtFieldEmail.setComponentPopupMenu( menu );
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelBanner = new javax.swing.JPanel();
        labelBanner = new javax.swing.JLabel();
        labelInstruccion = new javax.swing.JLabel();
        btnContinuar = new javax.swing.JButton();
        panelDatosPersonales = new javax.swing.JPanel();
        txtFieldPrimerApellido = new javax.swing.JTextField();
        txtFieldPrimerNombre = new javax.swing.JTextField();
        txtFieldSegundoApellido = new javax.swing.JTextField();
        txtFieldCedula = new javax.swing.JTextField();
        txtFieldSegundoNombre = new javax.swing.JTextField();
        labelSegundoNombre = new javax.swing.JLabel();
        labelSegundoApellido = new javax.swing.JLabel();
        labelSeparator2 = new javax.swing.JLabel();
        labelSeparator = new javax.swing.JLabel();
        labelPrimerNombre = new javax.swing.JLabel();
        labelCedula = new javax.swing.JLabel();
        labelPrimerApellido = new javax.swing.JLabel();
        labelMandatory1 = new javax.swing.JLabel();
        labelMandatory2 = new javax.swing.JLabel();
        labelMandatory3 = new javax.swing.JLabel();
        labelMandatory4 = new javax.swing.JLabel();
        panelDatosInspector = new javax.swing.JPanel();
        labelRegion = new javax.swing.JLabel();
        labelPuesto = new javax.swing.JLabel();
        labelOficina = new javax.swing.JLabel();
        cbmbxPuesto = new javax.swing.JComboBox<>();
        cbmbxRegion = new javax.swing.JComboBox<>();
        cbmbxOficina = new javax.swing.JComboBox<>();
        labelMandatory5 = new javax.swing.JLabel();
        labelMandatory6 = new javax.swing.JLabel();
        labelMandatory7 = new javax.swing.JLabel();
        panelDatosUsuario = new javax.swing.JPanel();
        labelEmail = new javax.swing.JLabel();
        labelContraseña = new javax.swing.JLabel();
        txtFieldEmail = new javax.swing.JTextField();
        labelContraseña1 = new javax.swing.JLabel();
        txtFieldContraseña = new javax.swing.JPasswordField();
        txtFieldContraseña2 = new javax.swing.JPasswordField();
        labelMandatory8 = new javax.swing.JLabel();
        labelMandatory9 = new javax.swing.JLabel();
        labelMandatory10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("SGC | Registrar Usuario");
        setBackground(new java.awt.Color(255, 255, 255));
        setIconImage(new ImageIcon(getClass().getResource("/favicon.png")).getImage());
        setMaximumSize(new java.awt.Dimension(400, 680));
        setMinimumSize(new java.awt.Dimension(400, 680));
        setPreferredSize(new java.awt.Dimension(400, 680));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(365, 650));
        jPanel1.setMinimumSize(new java.awt.Dimension(365, 650));

        labelBanner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/banners/banner-registro.png"))); // NOI18N

        javax.swing.GroupLayout panelBannerLayout = new javax.swing.GroupLayout(panelBanner);
        panelBanner.setLayout(panelBannerLayout);
        panelBannerLayout.setHorizontalGroup(
            panelBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelBanner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelBannerLayout.setVerticalGroup(
            panelBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelBanner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        labelInstruccion.setText("Ingrese la información que se le solicita:");

        btnContinuar.setText("Continuar");
        btnContinuar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnContinuar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnContinuarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnContinuarMouseExited(evt);
            }
        });
        btnContinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContinuarActionPerformed(evt);
            }
        });

        panelDatosPersonales.setBackground(new java.awt.Color(255, 255, 255));
        panelDatosPersonales.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos personales", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));

        txtFieldPrimerApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFieldPrimerApellidoKeyPressed(evt);
            }
        });

        txtFieldPrimerNombre.setMaximumSize(new java.awt.Dimension(107, 22));
        txtFieldPrimerNombre.setMinimumSize(new java.awt.Dimension(107, 22));
        txtFieldPrimerNombre.setPreferredSize(new java.awt.Dimension(107, 22));
        txtFieldPrimerNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFieldPrimerNombreKeyPressed(evt);
            }
        });

        txtFieldSegundoApellido.setMaximumSize(new java.awt.Dimension(107, 22));
        txtFieldSegundoApellido.setMinimumSize(new java.awt.Dimension(107, 22));
        txtFieldSegundoApellido.setPreferredSize(new java.awt.Dimension(107, 22));
        txtFieldSegundoApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFieldSegundoApellidoKeyPressed(evt);
            }
        });

        txtFieldCedula.setMaximumSize(new java.awt.Dimension(107, 22));
        txtFieldCedula.setMinimumSize(new java.awt.Dimension(107, 22));
        txtFieldCedula.setPreferredSize(new java.awt.Dimension(107, 22));
        txtFieldCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFieldCedulaKeyPressed(evt);
            }
        });

        txtFieldSegundoNombre.setMaximumSize(new java.awt.Dimension(107, 22));
        txtFieldSegundoNombre.setMinimumSize(new java.awt.Dimension(107, 22));
        txtFieldSegundoNombre.setPreferredSize(new java.awt.Dimension(107, 22));
        txtFieldSegundoNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFieldSegundoNombreKeyPressed(evt);
            }
        });

        labelSegundoNombre.setText("Segundo nombre");

        labelSegundoApellido.setText("Segundo apellido");

        labelSeparator2.setText("-");

        labelSeparator.setText("-");

        labelPrimerNombre.setText("Primer nombre");

        labelCedula.setText("Cedula de identidad");

        labelPrimerApellido.setText("Primer apellido");

        labelMandatory1.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelMandatory1.setText("*");

        labelMandatory2.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory2.setText("*");

        labelMandatory3.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory3.setText("*");

        labelMandatory4.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory4.setText("*");

        javax.swing.GroupLayout panelDatosPersonalesLayout = new javax.swing.GroupLayout(panelDatosPersonales);
        panelDatosPersonales.setLayout(panelDatosPersonalesLayout);
        panelDatosPersonalesLayout.setHorizontalGroup(
            panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                        .addComponent(labelCedula)
                        .addGap(7, 7, 7)
                        .addComponent(labelMandatory1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                        .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFieldCedula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                                .addComponent(labelPrimerApellido)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelMandatory3, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                                .addComponent(labelPrimerNombre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelMandatory2, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                                .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelDatosPersonalesLayout.createSequentialGroup()
                                        .addComponent(txtFieldPrimerApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelDatosPersonalesLayout.createSequentialGroup()
                                        .addComponent(txtFieldPrimerNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelSeparator2)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtFieldSegundoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFieldSegundoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelSegundoNombre)
                                    .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                                        .addComponent(labelSegundoApellido)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelMandatory4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        panelDatosPersonalesLayout.setVerticalGroup(
            panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCedula)
                    .addComponent(labelMandatory1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFieldCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPrimerNombre)
                    .addComponent(labelMandatory2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSegundoNombre))
                .addGap(6, 6, 6)
                .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFieldPrimerNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSeparator2)
                    .addComponent(txtFieldSegundoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosPersonalesLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelMandatory3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelMandatory4))
                        .addGap(2, 2, 2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDatosPersonalesLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelSegundoApellido)
                            .addComponent(labelPrimerApellido))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(panelDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFieldPrimerApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFieldSegundoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSeparator))
                .addContainerGap())
        );

        panelDatosInspector.setBackground(new java.awt.Color(255, 255, 255));
        panelDatosInspector.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de inspector", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));

        labelRegion.setText("Región");

        labelPuesto.setText("Puesto");

        labelOficina.setText("Oficina");

        cbmbxPuesto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbmbxPuesto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cbmbxPuesto.setMaximumSize(new java.awt.Dimension(231, 22));
        cbmbxPuesto.setMinimumSize(new java.awt.Dimension(231, 22));
        cbmbxPuesto.setPreferredSize(new java.awt.Dimension(231, 22));

        cbmbxRegion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbmbxRegion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cbmbxRegion.setMaximumSize(new java.awt.Dimension(231, 22));
        cbmbxRegion.setMinimumSize(new java.awt.Dimension(231, 22));
        cbmbxRegion.setPreferredSize(new java.awt.Dimension(231, 22));
        cbmbxRegion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbmbxRegionActionPerformed(evt);
            }
        });

        cbmbxOficina.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbmbxOficina.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cbmbxOficina.setMaximumSize(new java.awt.Dimension(231, 22));
        cbmbxOficina.setMinimumSize(new java.awt.Dimension(231, 22));
        cbmbxOficina.setPreferredSize(new java.awt.Dimension(231, 22));

        labelMandatory5.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory5.setText("*");

        labelMandatory6.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory6.setText("*");

        labelMandatory7.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory7.setText("*");

        javax.swing.GroupLayout panelDatosInspectorLayout = new javax.swing.GroupLayout(panelDatosInspector);
        panelDatosInspector.setLayout(panelDatosInspectorLayout);
        panelDatosInspectorLayout.setHorizontalGroup(
            panelDatosInspectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosInspectorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosInspectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbmbxRegion, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbmbxOficina, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbmbxPuesto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelDatosInspectorLayout.createSequentialGroup()
                        .addComponent(labelPuesto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelMandatory5, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDatosInspectorLayout.createSequentialGroup()
                        .addComponent(labelRegion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelMandatory6, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDatosInspectorLayout.createSequentialGroup()
                        .addComponent(labelOficina)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelMandatory7, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelDatosInspectorLayout.setVerticalGroup(
            panelDatosInspectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosInspectorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosInspectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPuesto)
                    .addComponent(labelMandatory5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbmbxPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosInspectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelRegion)
                    .addComponent(labelMandatory6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbmbxRegion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosInspectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelOficina)
                    .addComponent(labelMandatory7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cbmbxOficina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelDatosUsuario.setBackground(new java.awt.Color(255, 255, 255));
        panelDatosUsuario.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de usuario", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));

        labelEmail.setText("Email");

        labelContraseña.setText("Contraseña");

        txtFieldEmail.setText("@mtss.go.cr");
        txtFieldEmail.setMaximumSize(new java.awt.Dimension(231, 22));
        txtFieldEmail.setMinimumSize(new java.awt.Dimension(231, 22));
        txtFieldEmail.setPreferredSize(new java.awt.Dimension(231, 22));
        txtFieldEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFieldEmailKeyPressed(evt);
            }
        });

        labelContraseña1.setText("Confirmar contraseña");

        txtFieldContraseña.setMaximumSize(new java.awt.Dimension(231, 22));
        txtFieldContraseña.setMinimumSize(new java.awt.Dimension(231, 22));
        txtFieldContraseña.setPreferredSize(new java.awt.Dimension(231, 22));
        txtFieldContraseña.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFieldContraseñaKeyPressed(evt);
            }
        });

        txtFieldContraseña2.setMaximumSize(new java.awt.Dimension(231, 22));
        txtFieldContraseña2.setMinimumSize(new java.awt.Dimension(231, 22));
        txtFieldContraseña2.setPreferredSize(new java.awt.Dimension(231, 22));
        txtFieldContraseña2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFieldContraseña2KeyPressed(evt);
            }
        });

        labelMandatory8.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory8.setText("*");

        labelMandatory9.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory9.setText("*");

        labelMandatory10.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory10.setText("*");

        javax.swing.GroupLayout panelDatosUsuarioLayout = new javax.swing.GroupLayout(panelDatosUsuario);
        panelDatosUsuario.setLayout(panelDatosUsuarioLayout);
        panelDatosUsuarioLayout.setHorizontalGroup(
            panelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFieldContraseña2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFieldContraseña, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelDatosUsuarioLayout.createSequentialGroup()
                        .addComponent(labelEmail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelMandatory8, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDatosUsuarioLayout.createSequentialGroup()
                        .addComponent(labelContraseña)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelMandatory9, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDatosUsuarioLayout.createSequentialGroup()
                        .addComponent(labelContraseña1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelMandatory10, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtFieldEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelDatosUsuarioLayout.setVerticalGroup(
            panelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelEmail)
                    .addComponent(labelMandatory8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelContraseña)
                    .addComponent(labelMandatory9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFieldContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelContraseña1)
                    .addComponent(labelMandatory10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFieldContraseña2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelBanner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelInstruccion)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelDatosInspector, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelDatosUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelDatosPersonales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnContinuar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(labelInstruccion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDatosPersonales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDatosInspector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDatosUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnContinuar)
                .addGap(24, 24, 24))
            .addComponent(panelBanner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnContinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContinuarActionPerformed
        telemetria.logActivity("Se hizo click en continuar.");            
        procesarFormulario();
    }//GEN-LAST:event_btnContinuarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        //se pide confirmacion del usuario
        laRespuesta = JOptionPane.showConfirmDialog(null, 
                """
                  ¿Desea regresar a la pantalla de inicio?
                  Todos los datos ingresados se perderan.
                """,
                "ATENCION",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);


        //se procesa la respuesta
        if (laRespuesta == JOptionPane.YES_OPTION) {
            telemetria.logActivity("Se sale de pantalla de registro de usuario.");
            crearPantallaInicio();
        }

    }//GEN-LAST:event_formWindowClosing

    private void cbmbxRegionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbmbxRegionActionPerformed
        updateComboBoxOficina();
    }//GEN-LAST:event_cbmbxRegionActionPerformed

    private void txtFieldPrimerNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFieldPrimerNombreKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtFieldPrimerNombreKeyPressed

    private void txtFieldSegundoNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFieldSegundoNombreKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtFieldSegundoNombreKeyPressed

    private void txtFieldPrimerApellidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFieldPrimerApellidoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtFieldPrimerApellidoKeyPressed

    private void txtFieldSegundoApellidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFieldSegundoApellidoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtFieldSegundoApellidoKeyPressed

    private void txtFieldEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFieldEmailKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtFieldEmailKeyPressed

    private void txtFieldContraseñaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFieldContraseñaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtFieldContraseñaKeyPressed

    private void txtFieldContraseña2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFieldContraseña2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtFieldContraseña2KeyPressed

    private void txtFieldCedulaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFieldCedulaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtFieldCedulaKeyPressed

    private void btnContinuarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnContinuarMouseEntered
        setButtonActiveColor(btnContinuar);
    }//GEN-LAST:event_btnContinuarMouseEntered

    private void btnContinuarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnContinuarMouseExited
        setButtonNormalColor(btnContinuar);
    }//GEN-LAST:event_btnContinuarMouseExited

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            //setear tema como el tema de windows
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new RegistroUsuario().setVisible(true);
        });
    }
    
    private void initVariables(){
        PANTONE2727C = new Color(82, 132, 196);
        PANTONE1595C = new Color(221, 103, 15);
        telemetria = new Telemetria();
        database = new MetodosDatabase();
        inspector = new Inspector();
        persona = new Persona();
        usuario = new Usuario();
        laRespuesta = 0;
        contraseña2 = "";
    }
    
    private void disposeVariables(){
        telemetria = null;
        database = null;
        inspector = null;
        persona = null;
        usuario = null;
        laRespuesta = 0;
        pantallaIniciarSesion = null;
        pantallaInicio = null;
        contraseña2 = "";
    }
    
    private void initForm(){
        telemetria.logActivity("Se ingresa a pantalla de registro de usuario.");
        setButtonNormalColor(btnContinuar);
        addTextFieldPopup();
        setComboBoxModels();
    }
    
    private Color PANTONE2727C;
    private Color PANTONE1595C;
    private Telemetria telemetria;
    private MetodosDatabase database;
    private Persona persona;
    private Usuario usuario;
    private Inspector inspector;
    private int laRespuesta;
    private Inicio pantallaInicio;
    private IniciosSesion pantallaIniciarSesion;
    private String contraseña2;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnContinuar;
    private javax.swing.JComboBox<String> cbmbxOficina;
    private javax.swing.JComboBox<String> cbmbxPuesto;
    private javax.swing.JComboBox<String> cbmbxRegion;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelBanner;
    private javax.swing.JLabel labelCedula;
    private javax.swing.JLabel labelContraseña;
    private javax.swing.JLabel labelContraseña1;
    private javax.swing.JLabel labelEmail;
    private javax.swing.JLabel labelInstruccion;
    private javax.swing.JLabel labelMandatory1;
    private javax.swing.JLabel labelMandatory10;
    private javax.swing.JLabel labelMandatory2;
    private javax.swing.JLabel labelMandatory3;
    private javax.swing.JLabel labelMandatory4;
    private javax.swing.JLabel labelMandatory5;
    private javax.swing.JLabel labelMandatory6;
    private javax.swing.JLabel labelMandatory7;
    private javax.swing.JLabel labelMandatory8;
    private javax.swing.JLabel labelMandatory9;
    private javax.swing.JLabel labelOficina;
    private javax.swing.JLabel labelPrimerApellido;
    private javax.swing.JLabel labelPrimerNombre;
    private javax.swing.JLabel labelPuesto;
    private javax.swing.JLabel labelRegion;
    private javax.swing.JLabel labelSegundoApellido;
    private javax.swing.JLabel labelSegundoNombre;
    private javax.swing.JLabel labelSeparator;
    private javax.swing.JLabel labelSeparator2;
    private javax.swing.JPanel panelBanner;
    private javax.swing.JPanel panelDatosInspector;
    private javax.swing.JPanel panelDatosPersonales;
    private javax.swing.JPanel panelDatosUsuario;
    private javax.swing.JTextField txtFieldCedula;
    private javax.swing.JPasswordField txtFieldContraseña;
    private javax.swing.JPasswordField txtFieldContraseña2;
    private javax.swing.JTextField txtFieldEmail;
    private javax.swing.JTextField txtFieldPrimerApellido;
    private javax.swing.JTextField txtFieldPrimerNombre;
    private javax.swing.JTextField txtFieldSegundoApellido;
    private javax.swing.JTextField txtFieldSegundoNombre;
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