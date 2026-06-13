 package interfaces;

import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import models.Inspector;
import server.MetodosServidor;
 
public class IniciosSesion extends javax.swing.JFrame {

    public IniciosSesion() {
        initComponents();
        initVariables();
    }
    
    private void crearPantallaInicio(){
        pantallaInicio = new Inicio();
        pantallaInicio.setLocationRelativeTo(null);
        pantallaInicio.setVisible(true);
        disposeVariables();
        this.dispose();
    }
    
    private void crearPantallaPrincipal() {
        pantallaPrincipal = new Principal(elInspector);
        pantallaPrincipal.setLocationRelativeTo(null);
        pantallaPrincipal.setVisible(true);
        disposeVariables();
        this.dispose();
    }
    
    private void procesarFormulario(){
        try {
            capturarDatos();
            if (verificarDatos()) {
                cargarDatos();
                crearPantallaPrincipal();
            }
        } catch (Exception e) {
            reportarError(e);
        }
    }
    
    private void capturarDatos(){
        cedula = txtCedula.getText();
        contraseña = String.valueOf(txtContraseña.getPassword());
    }
    
    private Boolean verificarDatos() throws Exception {
        if (servidor.userExist(cedula, contraseña)) {
            
            int id = servidor.getIdInspector(cedula);
            if(servidor.usuarioEsAdmin(id)){
                return true;
            } else {
                crearMensajeError("El usuario ingresado no es administrador.");
                return false;
            } 
            
        } else {
            crearMensajeError("Usuario o contraseña invalidos.");
            return false;
        }

    }

    private void cargarDatos() throws Exception {
        int id = servidor.getIdInspector(cedula);
        if(id != -1){
            elInspector = servidor.createObjectInspector(id);
            servidor.loginInspector(elInspector.getPersona().getIdPersona());
        }
    }
    
    private void reportarError(Exception ex){
        
        switch (ex.getMessage()) {
            case "cedula null error" -> {
                crearMensajeError("La cedula no puede estar vacía.");
                break;
            }
            default -> {
                crearMensajeError(ex.getMessage());
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
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlInicioSesion = new javax.swing.JPanel();
        labelInstruccion = new javax.swing.JLabel();
        labelCedula = new javax.swing.JLabel();
        labelContraseña = new javax.swing.JLabel();
        btnContinuar = new javax.swing.JButton();
        txtContraseña = new javax.swing.JPasswordField();
        txtCedula = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Servidor | Iniciar Sesión");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlInicioSesion.setBackground(new java.awt.Color(255, 255, 255));

        labelInstruccion.setText("Ingrese la información que se le solicita:");

        labelCedula.setText("Cedula de identidad:");

        labelContraseña.setText("Contraseña:");

        btnContinuar.setText("Continuar");
        btnContinuar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnContinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContinuarActionPerformed(evt);
            }
        });

        txtContraseña.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtContraseñaKeyPressed(evt);
            }
        });

        try {
            txtCedula.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#0###0###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCedulaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout pnlInicioSesionLayout = new javax.swing.GroupLayout(pnlInicioSesion);
        pnlInicioSesion.setLayout(pnlInicioSesionLayout);
        pnlInicioSesionLayout.setHorizontalGroup(
            pnlInicioSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInicioSesionLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(pnlInicioSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelInstruccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelCedula, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                    .addComponent(labelContraseña, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                    .addComponent(btnContinuar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtContraseña)
                    .addComponent(txtCedula))
                .addContainerGap())
        );
        pnlInicioSesionLayout.setVerticalGroup(
            pnlInicioSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInicioSesionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelInstruccion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelCedula)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelContraseña)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnContinuar)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlInicioSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlInicioSesion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        crearPantallaInicio();
    }//GEN-LAST:event_formWindowClosing

    private void btnContinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContinuarActionPerformed
        procesarFormulario();
    }//GEN-LAST:event_btnContinuarActionPerformed

    private void txtCedulaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtCedulaKeyPressed

    private void txtContraseñaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContraseñaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtContraseñaKeyPressed
    
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
            new IniciosSesion().setVisible(true);
        });
    }
    
    private void initVariables() {
        elInspector = new Inspector();
        cedula = "";
        contraseña = "";
        servidor = new MetodosServidor(elInspector);
    }
    
    private void disposeVariables(){
        elInspector = null;
        cedula = null;
        contraseña = null;
        pantallaInicio = null;
        pantallaPrincipal = null;
        servidor = null;
    }
    
    private Inspector elInspector;
    private String cedula;
    private String contraseña;
    private Inicio pantallaInicio;
    private Principal pantallaPrincipal;
    private MetodosServidor servidor;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnContinuar;
    private javax.swing.JLabel labelCedula;
    private javax.swing.JLabel labelContraseña;
    private javax.swing.JLabel labelInstruccion;
    private javax.swing.JPanel pnlInicioSesion;
    private javax.swing.JFormattedTextField txtCedula;
    private javax.swing.JPasswordField txtContraseña;
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