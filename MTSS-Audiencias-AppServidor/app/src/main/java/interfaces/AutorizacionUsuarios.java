package interfaces;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Inspector;
import server.MetodosServidor;

public class AutorizacionUsuarios extends javax.swing.JFrame {

    public AutorizacionUsuarios() {
        initComponents();
        initVariables();
        initForm();
    }
    
    public AutorizacionUsuarios(Inspector inspector){
        this();
        this.inspector = inspector;
        postInitForm();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblListaUsuarios = new javax.swing.JTable();
        btnAutorizar = new javax.swing.JButton();
        btnNoAutorizar = new javax.swing.JButton();
        lblInstruccion = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Servidor | Autorizar");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        tblListaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblListaUsuarios.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblListaUsuarios.setAutoscrolls(false);
        tblListaUsuarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblListaUsuarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblListaUsuarios.setShowHorizontalLines(true);
        tblListaUsuarios.getTableHeader().setResizingAllowed(false);
        tblListaUsuarios.getTableHeader().setReorderingAllowed(false);
        tblListaUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListaUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblListaUsuarios);

        btnAutorizar.setText("Autorizar");
        btnAutorizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutorizarActionPerformed(evt);
            }
        });

        btnNoAutorizar.setText("No Autorizar");
        btnNoAutorizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNoAutorizarActionPerformed(evt);
            }
        });

        lblInstruccion.setText("Los siguientes usuarios tienen autorización pendiente:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNoAutorizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAutorizar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 986, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblInstruccion)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lblInstruccion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAutorizar)
                    .addComponent(btnNoAutorizar))
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void crearPantallaPrincipal(){
        Principal principal = new Principal(inspector);
        principal.setLocationRelativeTo(null);
        principal.setVisible(true);
        this.dispose();
    }
    
    private void activarBotones(){
        btnAutorizar.setEnabled(true);
        btnNoAutorizar.setEnabled(true);
    }
    
    private void desactivarBotones(){
        btnAutorizar.setEnabled(false);
        btnNoAutorizar.setEnabled(false);
    }
    
    private void configurarTabla(){
        tblModelAutorizacion.addColumn("Cedula");
        tblModelAutorizacion.addColumn("Correo");
        tblModelAutorizacion.addColumn("Puesto");
        tblModelAutorizacion.addColumn("Región");
        tblModelAutorizacion.addColumn("Oficina");
        tblListaUsuarios.setModel(tblModelAutorizacion);
    }
    
    private void obtenerDatosFila(){
        filaSeleccionada = new String[6];
        int rowSelected = tblListaUsuarios.getSelectedRow();
        filaSeleccionada[0] = tblListaUsuarios.getValueAt(rowSelected, 0).toString(); //cedula
        filaSeleccionada[1] = tblListaUsuarios.getValueAt(rowSelected, 1).toString(); //correo
        filaSeleccionada[2] = tblListaUsuarios.getValueAt(rowSelected, 2).toString(); //puesto
        filaSeleccionada[3] = tblListaUsuarios.getValueAt(rowSelected, 3).toString(); //region
        filaSeleccionada[4] = tblListaUsuarios.getValueAt(rowSelected, 4).toString(); //oficina
    }
    
    private void listarUsuariosPendientes() {
        try {
            tblModelAutorizacion.setRowCount(0); //elimina los datos
            tblModelAutorizacion = servidor.getListaUsuariosPendientes(tblModelAutorizacion);
            tblListaUsuarios.setModel(tblModelAutorizacion);
            tblListaUsuarios.revalidate();
            tblListaUsuarios.repaint();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    private int crearMensajeInformativo(){
        Object[] options = { "De acuerdo" };
        int respuesta = JOptionPane.showOptionDialog(
                        null, "Se han aplicado los cambios satisfactoriamente", "Exito",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
                        options, options[0]
        );
        
        return respuesta;
    }
    
    private void autorizarUsuario(){
        int idUsuario = servidor.getIdInspector(filaSeleccionada[0]);
        servidor.autorizarUsuario(idUsuario);
        desactivarBotones();
        crearMensajeInformativo();
        listarUsuariosPendientes();
    }
    
    private void denegarUsuario(){
        int idUsuario = servidor.getIdInspector(filaSeleccionada[0]);
        servidor.denegarUsuario(idUsuario);
        desactivarBotones();
        crearMensajeInformativo();
        listarUsuariosPendientes();
    }
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        int respuesta = 0;
        respuesta = JOptionPane.showConfirmDialog(null,
                "¿Desea regresar a la pantalla principal?",
                "ATENCION",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        //se procesa la respuesta
        if (respuesta == JOptionPane.YES_OPTION) {
            crearPantallaPrincipal();
        }
    }//GEN-LAST:event_formWindowClosing

    private void btnAutorizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAutorizarActionPerformed
        autorizarUsuario();
    }//GEN-LAST:event_btnAutorizarActionPerformed

    private void btnNoAutorizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNoAutorizarActionPerformed
        denegarUsuario();
    }//GEN-LAST:event_btnNoAutorizarActionPerformed

    private void tblListaUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListaUsuariosMouseClicked
        obtenerDatosFila();
        activarBotones();
    }//GEN-LAST:event_tblListaUsuariosMouseClicked

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        tblListaUsuarios.getSelectionModel().clearSelection();
        desactivarBotones();
    }//GEN-LAST:event_jPanel1MouseClicked


    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AutorizacionUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AutorizacionUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AutorizacionUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AutorizacionUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AutorizacionUsuarios().setVisible(true);
            }
        });
    }
    
    private void initForm(){
        desactivarBotones();
        configurarTabla();
        listarUsuariosPendientes();
    }
    
    private void postInitForm(){
        servidor.setInspector(inspector);
    }
    
    private void initVariables(){
        tblListaUsuarios.setDefaultEditor(Object.class, null);
        tblModelAutorizacion = new DefaultTableModel();
        inspector =  new Inspector();
        servidor =  new MetodosServidor(inspector);
    }
    
    private Inspector inspector;
    private MetodosServidor servidor;
    private DefaultTableModel tblModelAutorizacion;
    private String[] filaSeleccionada;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAutorizar;
    private javax.swing.JButton btnNoAutorizar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblInstruccion;
    private javax.swing.JTable tblListaUsuarios;
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