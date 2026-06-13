package interfaces;

import java.awt.Color;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import models.Inspector;
import server.MetodosServidor;

public class Principal extends javax.swing.JFrame {

    public Principal() {
        initComponents();
        initVariables();
        initForm();
    }
    
    public Principal(Inspector inspector){
        this();
        this.inspector = inspector;
        postInitForm();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnDetenerServi = new javax.swing.JButton();
        btnCopiaSeguridad = new javax.swing.JButton();
        btnAutorizarUsuarios = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        labelLOCAL = new javax.swing.JLabel();
        labelUsuariosConectados = new javax.swing.JLabel();
        labelNumeroUsuarios = new javax.swing.JLabel();
        labelEstadoServidor = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblNombreAdmin = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnLimiteAlmacenamiento = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Servidor | Pantalla Principal");
        setMaximumSize(new java.awt.Dimension(450, 450));
        setMinimumSize(new java.awt.Dimension(450, 450));
        setPreferredSize(new java.awt.Dimension(450, 450));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnDetenerServi.setText("Detener Servidor");
        btnDetenerServi.setMaximumSize(new java.awt.Dimension(150, 50));
        btnDetenerServi.setMinimumSize(new java.awt.Dimension(150, 50));
        btnDetenerServi.setPreferredSize(new java.awt.Dimension(150, 50));
        btnDetenerServi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetenerServiActionPerformed(evt);
            }
        });

        btnCopiaSeguridad.setText("Copia de Seguridad");
        btnCopiaSeguridad.setMaximumSize(new java.awt.Dimension(180, 50));
        btnCopiaSeguridad.setMinimumSize(new java.awt.Dimension(180, 50));
        btnCopiaSeguridad.setPreferredSize(new java.awt.Dimension(180, 50));
        btnCopiaSeguridad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopiaSeguridadActionPerformed(evt);
            }
        });

        btnAutorizarUsuarios.setText("Autorizar Usuarios");
        btnAutorizarUsuarios.setMaximumSize(new java.awt.Dimension(180, 50));
        btnAutorizarUsuarios.setMinimumSize(new java.awt.Dimension(180, 50));
        btnAutorizarUsuarios.setPreferredSize(new java.awt.Dimension(180, 50));
        btnAutorizarUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutorizarUsuariosActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        labelLOCAL.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelLOCAL.setText("Servidor: ");

        labelUsuariosConectados.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelUsuariosConectados.setText("Usuarios Conectados:");

        labelNumeroUsuarios.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelNumeroUsuarios.setText("0");

        labelEstadoServidor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelEstadoServidor.setText("ESTADO_SERVIDOR");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelLOCAL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelEstadoServidor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelUsuariosConectados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelNumeroUsuarios)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelLOCAL)
                    .addComponent(labelEstadoServidor)
                    .addComponent(labelUsuariosConectados)
                    .addComponent(labelNumeroUsuarios))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Bienvenido, Administrador");

        lblNombreAdmin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNombreAdmin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNombreAdmin.setText("[NOMBRE_ADMINISTRADOR].");

        jLabel3.setBackground(java.awt.SystemColor.textHighlight);
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setForeground(java.awt.SystemColor.textHighlight);
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Sistema de Gestión de Comparecencias del MTSS");

        btnLimiteAlmacenamiento.setText("Limite de Almacenamiento");
        btnLimiteAlmacenamiento.setMaximumSize(new java.awt.Dimension(180, 50));
        btnLimiteAlmacenamiento.setMinimumSize(new java.awt.Dimension(180, 50));
        btnLimiteAlmacenamiento.setPreferredSize(new java.awt.Dimension(180, 50));
        btnLimiteAlmacenamiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimiteAlmacenamientoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNombreAdmin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnCopiaSeguridad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAutorizarUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLimiteAlmacenamiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDetenerServi, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(lblNombreAdmin)
                .addGap(18, 18, 18)
                .addComponent(btnAutorizarUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCopiaSeguridad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLimiteAlmacenamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDetenerServi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void crearPantallaCopiaSeguridad(){
        CopiaSeguridad copia = new CopiaSeguridad(inspector);
        copia.setLocationRelativeTo(null);
        copia.setVisible(true);
        this.dispose();
    }
    
    private void crearPantallaLimiteAlmacenamiento(){
        LimiteAlmacenamiento limite = new LimiteAlmacenamiento(inspector);
        limite.setLocationRelativeTo(null);
        limite.setVisible(true);
        this.dispose();
    }
    
    private void crearPantallaAutorizacionUsuarios(){
        AutorizacionUsuarios autorizar = new AutorizacionUsuarios(inspector);
        autorizar.setLocationRelativeTo(null);
        autorizar.setVisible(true);
        this.dispose();
    }
    
    private void crearPantallaInicio(){
        Inicio inicio = new Inicio();
        inicio.setLocationRelativeTo(null);
        inicio.setVisible(true);
        this.dispose();
    }
    
    private void configurarUsuario() {
        lblNombreAdmin.setText(inspector.getPersona().getPrimerApellido()+".");
    }
    
    private void btnCopiaSeguridadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopiaSeguridadActionPerformed
        crearPantallaCopiaSeguridad();
    }//GEN-LAST:event_btnCopiaSeguridadActionPerformed

    private void btnAutorizarUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAutorizarUsuariosActionPerformed
        crearPantallaAutorizacionUsuarios();
    }//GEN-LAST:event_btnAutorizarUsuariosActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        int respuesta = 0;
        respuesta = JOptionPane.showConfirmDialog(null,
                """
                ¿Desea cerrar sesión y regresar a la pantalla de inicio?
                """,
                "ATENCION",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        //se procesa la respuesta
        if (respuesta == JOptionPane.YES_OPTION) {
            servidor.logoutInspector(inspector.getPersona().getIdPersona());
            crearPantallaInicio();        
        }
    }//GEN-LAST:event_formWindowClosing

    private void btnDetenerServiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetenerServiActionPerformed
        int respuesta = 0;
        respuesta = JOptionPane.showConfirmDialog(null,
                """
                ¿Realmente desea detener el servidor?
                """,
                "ATENCION",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        //se procesa la respuesta
        if (respuesta == JOptionPane.YES_OPTION) {
            servidor.logoutInspector(inspector.getPersona().getIdPersona());
            servidor.detenerServidor();
            crearPantallaInicio();
        }        
    }//GEN-LAST:event_btnDetenerServiActionPerformed

    private void btnLimiteAlmacenamientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimiteAlmacenamientoActionPerformed
        crearPantallaLimiteAlmacenamiento();
    }//GEN-LAST:event_btnLimiteAlmacenamientoActionPerformed

    private void verificarConexionServidor(){
        if(!servidor.verificarEstadoServidor()){
            labelEstadoServidor.setText("NO DISPONIBLE");
            labelEstadoServidor.setForeground(Color.RED);
        }else{
            labelEstadoServidor.setText("DISPONIBLE");
            labelEstadoServidor.setForeground(Color.BLUE);
        }
    }
    
    private final Runnable runContador = new Runnable() {
        public void run() {
            try {
                int usuariosEnLinea = servidor.getUsuariosEnLinea();
                labelNumeroUsuarios.setText(String.valueOf(usuariosEnLinea));
            } catch (Exception e) {
                System.out.println("Error al obtener numero de usuarios en linea.");
                System.out.println(e);
            }
        }
    };
    
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }
    
    private void initForm(){
        verificarConexionServidor();
    }
    
    private void postInitForm(){
        configurarUsuario();
        servidor.setInspector(inspector);
    }

    private void initVariables(){
        inspector = new Inspector();
        servidor = new MetodosServidor(inspector);
        userExecutor = Executors.newScheduledThreadPool(1);
        userExecutor.scheduleWithFixedDelay(runContador, 0, 5, TimeUnit.MINUTES);
    }
    
    private Inspector inspector;
    private MetodosServidor servidor;
    private ScheduledExecutorService userExecutor;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAutorizarUsuarios;
    private javax.swing.JButton btnCopiaSeguridad;
    private javax.swing.JButton btnDetenerServi;
    private javax.swing.JButton btnLimiteAlmacenamiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labelEstadoServidor;
    private javax.swing.JLabel labelLOCAL;
    private javax.swing.JLabel labelNumeroUsuarios;
    private javax.swing.JLabel labelUsuariosConectados;
    private javax.swing.JLabel lblNombreAdmin;
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