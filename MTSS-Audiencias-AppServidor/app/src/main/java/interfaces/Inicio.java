package interfaces;

import java.awt.Color;
import javax.swing.JOptionPane;
import models.Inspector;
import server.MetodosServidor;

public class Inicio extends javax.swing.JFrame {

    public Inicio() {
        initComponents();
        initVariables();
        initForm();
    }

    private void crearPantallaRegistro(){
        pantallaRegistro = new RegistroAdministrador();
        pantallaRegistro.setLocationRelativeTo(null);
        pantallaRegistro.setVisible(true);
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
    
    private void desactivarFuncionesOnline(){
        btnIniciarSesion.setEnabled(false);
        btnRegistrarAdministrador.setEnabled(false);
    }
    
    private void activarFuncionesOnline(){
        btnIniciarSesion.setEnabled(true);
        btnRegistrarAdministrador.setEnabled(true);
    }
            
    private void verificarConexionServidor(){
        if(!servidor.verificarEstadoServidor()){
            labelEstadoServidor.setText("NO DISPONIBLE");
            labelEstadoServidor.setForeground(Color.RED);
            desactivarFuncionesOnline();
            btnIniciarServidor.setEnabled(true);
        }else{
            labelEstadoServidor.setText("DISPONIBLE");
            labelEstadoServidor.setForeground(Color.BLUE);
            activarFuncionesOnline();
            btnIniciarServidor.setEnabled(false);
        }
    }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelInstruccion = new javax.swing.JLabel();
        btnIniciarSesion = new javax.swing.JButton();
        btnRegistrarAdministrador = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        labelSERVIDOR = new javax.swing.JLabel();
        labelEstadoServidor = new javax.swing.JLabel();
        btnIniciarServidor = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Servidor | Inicio");
        setBackground(new java.awt.Color(255, 255, 255));
        setLocation(new java.awt.Point(0, 0));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        labelInstruccion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelInstruccion.setText("Seleccione la forma en que desea continuar.");

        btnIniciarSesion.setText("Iniciar Sesión");
        btnIniciarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIniciarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarSesionActionPerformed(evt);
            }
        });

        btnRegistrarAdministrador.setText("Registrar Administrador");
        btnRegistrarAdministrador.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegistrarAdministrador.setMaximumSize(new java.awt.Dimension(99, 23));
        btnRegistrarAdministrador.setMinimumSize(new java.awt.Dimension(99, 23));
        btnRegistrarAdministrador.setPreferredSize(new java.awt.Dimension(99, 23));
        btnRegistrarAdministrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarAdministradorActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        labelSERVIDOR.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelSERVIDOR.setText("SERVIDOR:");

        labelEstadoServidor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelEstadoServidor.setText("Conectando...");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(labelSERVIDOR, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelEstadoServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSERVIDOR)
                    .addComponent(labelEstadoServidor))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        btnIniciarServidor.setText("Iniciar Servidor");
        btnIniciarServidor.setMaximumSize(new java.awt.Dimension(99, 23));
        btnIniciarServidor.setMinimumSize(new java.awt.Dimension(99, 23));
        btnIniciarServidor.setPreferredSize(new java.awt.Dimension(99, 23));
        btnIniciarServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarServidorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(labelInstruccion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRegistrarAdministrador, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                    .addComponent(btnIniciarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnIniciarServidor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(labelInstruccion)
                .addGap(16, 16, 16)
                .addComponent(btnRegistrarAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnIniciarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(btnIniciarServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnIniciarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarSesionActionPerformed
        crearPantallaInicioSesion();
    }//GEN-LAST:event_btnIniciarSesionActionPerformed

    private void btnRegistrarAdministradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarAdministradorActionPerformed
        crearPantallaRegistro();
    }//GEN-LAST:event_btnRegistrarAdministradorActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        //se pide confiramacion
        laRespuesta = JOptionPane.showConfirmDialog(null,
                """
                ¿Realmente desea salir?
                Esta accion detendra el servidor.
                """,
                "ATENCION",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        //se procesa la respuesta
        if (laRespuesta == JOptionPane.YES_OPTION) {
            servidor.detenerServidor();
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    private void crearMensajePositivo() {
        JOptionPane.showMessageDialog(null,
                """
                El servidor se ha iniciado correctamente.
                """,
                "Exito",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void crearMensajeNegativo() {
        JOptionPane.showMessageDialog(null,
                """
                No se ha podido iniciar el servidor.
                Verifique que Servidor.exe existe.
                """,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
    
    private void crearMensajeAlmacenamiento() {
        JOptionPane.showMessageDialog(null,
                """
                Lo sentimos.
                No hay suficiente espacio de almacenamiento para continuar.
                """,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
    
    private void btnIniciarServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarServidorActionPerformed
        if(methods.StorageMonitor.hayAlmacenamiento){
            if(methods.Proceso.execServidor()){
                crearMensajePositivo();
            }else{
                crearMensajeNegativo();
            }
            verificarConexionServidor();
        }else{
            crearMensajeAlmacenamiento();
        }
    }//GEN-LAST:event_btnIniciarServidorActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Inicio().setVisible(true);
        });
    }

    private void initForm(){
        verificarConexionServidor();
    }
    
    private void disposeVariables(){
        inspectorActivo = null;
        servidor = null;
        pantallaRegistro = null;
        pantallaIniciarSesion = null;
    }
    
    private void initVariables(){
        inspectorActivo = new Inspector();
        servidor = new MetodosServidor(inspectorActivo);
        laRespuesta = 0;
    }
    
    private Inspector inspectorActivo;
    private MetodosServidor servidor;
    private RegistroAdministrador pantallaRegistro;
    private IniciosSesion pantallaIniciarSesion;
    private int laRespuesta;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciarServidor;
    private javax.swing.JButton btnIniciarSesion;
    private javax.swing.JButton btnRegistrarAdministrador;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelEstadoServidor;
    private javax.swing.JLabel labelInstruccion;
    private javax.swing.JLabel labelSERVIDOR;
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