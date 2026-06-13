package interfaces;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import models.Inspector;
import server.MetodosServidor;
import database.ConexionDatabase;
import javax.swing.JButton;
import methods.Telemetria;
import server.ConexionServidor;

public class Inicio extends javax.swing.JFrame {

    public Inicio() {
        initComponents();
        initVariables();
        initForm();
    }

    private void crearPantallaRegistro(){
        pantallaRegistro = new RegistroUsuario();
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
        
    private void verificarConexionSQLITE(){
        conexionDatabase.abrirConexion();
        if(conexionDatabase.sqliteConexion == null){
            labelEstadoSQLITE.setText("NO DISPONIBLE");
            labelEstadoSQLITE.setForeground(PANTONE1595C);
            forceExit();
        }else{
            labelEstadoSQLITE.setText("DISPONIBLE");
            labelEstadoSQLITE.setForeground(PANTONE2727C);
        }
       conexionDatabase.cerrarConexionSQLITE();
    }
    
    private void verificarConexionServidor(){
        if(!servidor.verificarEstadoServidor()){
            labelEstadoServidor.setText("NO DISPONIBLE");
            labelEstadoServidor.setForeground(PANTONE1595C);
        }else{
            labelEstadoServidor.setText("DISPONIBLE");
            labelEstadoServidor.setForeground(PANTONE2727C);
        }
    }

    private void forceExit() {
        JOptionPane.showMessageDialog(null, """
                                            Fallo al establecer conexion con SQLite.
                                            Imposible continuar.
                                            """,
                "ERROR FATAL",
                JOptionPane.ERROR_MESSAGE);
        
        telemetria.logActivity("Se sale del programa.");
        System.exit(0);
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
        labelInstruccion = new javax.swing.JLabel();
        btnIniciarSesion = new javax.swing.JButton();
        btnRegistrarUsuario = new javax.swing.JButton();
        labelBANNER = new javax.swing.JLabel();
        pnlEstado = new javax.swing.JPanel();
        pnlLocal = new javax.swing.JPanel();
        labelLOCAL = new javax.swing.JLabel();
        labelEstadoSQLITE = new javax.swing.JLabel();
        pnlServidor = new javax.swing.JPanel();
        labelSERVIDOR = new javax.swing.JLabel();
        labelEstadoServidor = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Sistema de Gestion de Comparecencias");
        setBackground(new java.awt.Color(255, 255, 255));
        setIconImage(new ImageIcon(getClass().getResource("/favicon.png")).getImage());
        setLocation(new java.awt.Point(0, 0));
        setMaximumSize(new java.awt.Dimension(415, 439));
        setMinimumSize(new java.awt.Dimension(415, 439));
        setPreferredSize(new java.awt.Dimension(415, 439));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(415, 400));
        jPanel1.setMinimumSize(new java.awt.Dimension(415, 400));
        jPanel1.setPreferredSize(new java.awt.Dimension(415, 400));

        labelInstruccion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelInstruccion.setText("Seleccione la forma en que desea continuar.");

        btnIniciarSesion.setText("Iniciar Sesión");
        btnIniciarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIniciarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnIniciarSesionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnIniciarSesionMouseExited(evt);
            }
        });
        btnIniciarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarSesionActionPerformed(evt);
            }
        });

        btnRegistrarUsuario.setText("Registrar Usuario");
        btnRegistrarUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegistrarUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRegistrarUsuarioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRegistrarUsuarioMouseExited(evt);
            }
        });
        btnRegistrarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarUsuarioActionPerformed(evt);
            }
        });

        labelBANNER.setIcon(new javax.swing.ImageIcon(getClass().getResource("/banners/mtss-banner.png"))); // NOI18N

        pnlEstado.setBackground(new java.awt.Color(255, 255, 255));
        pnlEstado.setMaximumSize(new java.awt.Dimension(400, 18));
        pnlEstado.setMinimumSize(new java.awt.Dimension(400, 18));

        pnlLocal.setBackground(new java.awt.Color(200, 200, 200));
        pnlLocal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(111, 114, 113)));
        pnlLocal.setMaximumSize(new java.awt.Dimension(400, 18));
        pnlLocal.setMinimumSize(new java.awt.Dimension(400, 18));

        labelLOCAL.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelLOCAL.setText("LOCAL:");

        labelEstadoSQLITE.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelEstadoSQLITE.setText("NO_DISPONIBLE");

        javax.swing.GroupLayout pnlLocalLayout = new javax.swing.GroupLayout(pnlLocal);
        pnlLocal.setLayout(pnlLocalLayout);
        pnlLocalLayout.setHorizontalGroup(
            pnlLocalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLocalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelLOCAL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelEstadoSQLITE)
                .addContainerGap())
        );
        pnlLocalLayout.setVerticalGroup(
            pnlLocalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLocalLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnlLocalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelLOCAL)
                    .addComponent(labelEstadoSQLITE)))
        );

        pnlServidor.setBackground(new java.awt.Color(200, 200, 200));
        pnlServidor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(111, 114, 113)));
        pnlServidor.setMaximumSize(new java.awt.Dimension(400, 18));
        pnlServidor.setMinimumSize(new java.awt.Dimension(400, 18));

        labelSERVIDOR.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelSERVIDOR.setText("SERVIDOR:");

        labelEstadoServidor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelEstadoServidor.setText("NO_DISPONIBLE");

        javax.swing.GroupLayout pnlServidorLayout = new javax.swing.GroupLayout(pnlServidor);
        pnlServidor.setLayout(pnlServidorLayout);
        pnlServidorLayout.setHorizontalGroup(
            pnlServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlServidorLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(labelSERVIDOR)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelEstadoServidor)
                .addGap(0, 0, 0))
        );
        pnlServidorLayout.setVerticalGroup(
            pnlServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlServidorLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnlServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSERVIDOR)
                    .addComponent(labelEstadoServidor)))
        );

        javax.swing.GroupLayout pnlEstadoLayout = new javax.swing.GroupLayout(pnlEstado);
        pnlEstado.setLayout(pnlEstadoLayout);
        pnlEstadoLayout.setHorizontalGroup(
            pnlEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEstadoLayout.createSequentialGroup()
                .addComponent(pnlLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlEstadoLayout.setVerticalGroup(
            pnlEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEstadoLayout.createSequentialGroup()
                .addGroup(pnlEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelBANNER, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRegistrarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnIniciarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelInstruccion, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(labelBANNER)
                .addGap(30, 30, 30)
                .addComponent(labelInstruccion)
                .addGap(18, 18, 18)
                .addComponent(btnIniciarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRegistrarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addComponent(pnlEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnIniciarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarSesionActionPerformed
        telemetria.logActivity("Se hizo click en iniciar sesion.");
        crearPantallaInicioSesion();
    }//GEN-LAST:event_btnIniciarSesionActionPerformed

    private void btnRegistrarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarUsuarioActionPerformed
        telemetria.logActivity("Se hizo click en registrar usuario.");
        crearPantallaRegistro();
    }//GEN-LAST:event_btnRegistrarUsuarioActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        //se pide confiramacion
        laRespuesta = JOptionPane.showConfirmDialog(null,
                "¿Realmente desea salir?",
                "ATENCION",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        //se procesa la respuesta
        if (laRespuesta == JOptionPane.YES_OPTION) {
            telemetria.logActivity("Se sale del programa.");
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    private void btnIniciarSesionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIniciarSesionMouseEntered
        setButtonActiveColor(btnIniciarSesion);
    }//GEN-LAST:event_btnIniciarSesionMouseEntered

    private void btnIniciarSesionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIniciarSesionMouseExited
        setButtonNormalColor(btnIniciarSesion);
    }//GEN-LAST:event_btnIniciarSesionMouseExited

    private void btnRegistrarUsuarioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistrarUsuarioMouseEntered
        setButtonActiveColor(btnRegistrarUsuario);
    }//GEN-LAST:event_btnRegistrarUsuarioMouseEntered

    private void btnRegistrarUsuarioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistrarUsuarioMouseExited
        setButtonNormalColor(btnRegistrarUsuario);
    }//GEN-LAST:event_btnRegistrarUsuarioMouseExited

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
        telemetria.logActivity("Se ingresa a pantalla de inicio.");
        verificarConexionSQLITE();
        verificarConexionServidor();
        setButtonNormalColor(btnIniciarSesion);
        setButtonNormalColor(btnRegistrarUsuario);
    }
    
    private void disposeVariables(){
        telemetria = null;
        inspectorActivo = null;
        servidor = null;
        conexionDatabase = null;
        pantallaRegistro = null;
        pantallaIniciarSesion = null;
        pantallaPrincipal = null;
    }
    
    private void initVariables(){
        PANTONE2727C = new Color(82, 132, 196);
        PANTONE1595C = new Color(221, 103, 15);
        telemetria = new Telemetria();
        inspectorActivo = new Inspector();
        servidor = new MetodosServidor(inspectorActivo);
        conexionDatabase = new ConexionDatabase();
        nombreUsuario = "";
        laRespuesta = 0;
    }
    
    private Color PANTONE2727C;
    private Color PANTONE1595C;
    private Telemetria telemetria;
    private Inspector inspectorActivo;
    private MetodosServidor servidor;
    private ConexionDatabase conexionDatabase;
    private RegistroUsuario pantallaRegistro;
    private IniciosSesion pantallaIniciarSesion;
    private Principal pantallaPrincipal;
    private String nombreUsuario;
    private int laRespuesta;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciarSesion;
    private javax.swing.JButton btnRegistrarUsuario;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelBANNER;
    private javax.swing.JLabel labelEstadoSQLITE;
    private javax.swing.JLabel labelEstadoServidor;
    private javax.swing.JLabel labelInstruccion;
    private javax.swing.JLabel labelLOCAL;
    private javax.swing.JLabel labelSERVIDOR;
    private javax.swing.JPanel pnlEstado;
    private javax.swing.JPanel pnlLocal;
    private javax.swing.JPanel pnlServidor;
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