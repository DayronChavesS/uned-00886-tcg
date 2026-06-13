package interfaces;

import java.awt.Color;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import methods.Telemetria;
import methods.Archivos;

public class Configuracion extends javax.swing.JFrame {

    public Configuracion() {
        initComponents();
        initVariables();
        initForm();
    }

    private void crearExploradorDeArchivos() {
        telemetria.logActivity("Se creo el explorador de archivos.");
        configFileChooser.setDialogTitle("Seleccione el directorio donde desea guardar las audiencias.");
        configFileChooser.setMultiSelectionEnabled(false);
        configFileChooser.setApproveButtonText("Aceptar");
        configFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        result = configFileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            telemetria.logActivity("Se selecciono un directorio de guardado.");    
            elPath = configFileChooser.getSelectedFile().getAbsolutePath();
            if(!elPath.endsWith(System.getProperty("file.separator"))){
                elPath = elPath + System.getProperty("file.separator");
            }
            txtFieldDirectorio.setText(elPath);
        }else{
            telemetria.logActivity("Se cancelo la seleccion.");
        }
    }
    
    private void crearPantallaInicio(){
        pantallaInicio = new Inicio();
        pantallaInicio.setLocationRelativeTo(null);
        pantallaInicio.setVisible(true);
        disposeVariables();
        this.dispose();
    }
    
    private void crearArchivoConfiguracion(){
        archivo.crearArchivoConfiguracion();
        archivo.guardarArchivoPropiedades(elPath);
        archivo.cargarArchivoPropiedades();
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

        configFileChooser = new javax.swing.JFileChooser();
        pnlInicioSesion = new javax.swing.JPanel();
        labelBanner = new javax.swing.JLabel();
        labelInstruccion2 = new javax.swing.JLabel("<html><p>A continuacion, seleccione el directorio en donde desea <br/> guardar las comparecencias y sus anotaciones.</p></html>", SwingConstants.LEFT);
        btnCancelar = new javax.swing.JButton();
        txtFieldDirectorio = new javax.swing.JTextField();
        labelInstruccion1 = new javax.swing.JLabel();
        btnExaminar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Configuracion Inicial");
        setBackground(new java.awt.Color(255, 255, 255));
        setIconImage(new ImageIcon(getClass().getResource("/favicon.png")).getImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlInicioSesion.setBackground(new java.awt.Color(255, 255, 255));

        labelBanner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/banners/banner-inicio.png"))); // NOI18N

        btnCancelar.setText("Cancelar");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelarMouseExited(evt);
            }
        });
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        txtFieldDirectorio.setEditable(false);
        txtFieldDirectorio.setBackground(new java.awt.Color(255, 255, 255));
        txtFieldDirectorio.setMaximumSize(new java.awt.Dimension(160, 22));
        txtFieldDirectorio.setMinimumSize(new java.awt.Dimension(160, 22));
        txtFieldDirectorio.setPreferredSize(new java.awt.Dimension(160, 22));

        labelInstruccion1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelInstruccion1.setText("Bienvenido al sistema de gestión de comparecencias.");

        btnExaminar.setText("Examinar...");
        btnExaminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExaminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExaminarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExaminarMouseExited(evt);
            }
        });
        btnExaminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExaminarActionPerformed(evt);
            }
        });

        btnAceptar.setText("Aceptar");
        btnAceptar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAceptarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAceptarMouseExited(evt);
            }
        });
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlInicioSesionLayout = new javax.swing.GroupLayout(pnlInicioSesion);
        pnlInicioSesion.setLayout(pnlInicioSesionLayout);
        pnlInicioSesionLayout.setHorizontalGroup(
            pnlInicioSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInicioSesionLayout.createSequentialGroup()
                .addComponent(labelBanner)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInicioSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInicioSesionLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pnlInicioSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInicioSesionLayout.createSequentialGroup()
                                .addComponent(txtFieldDirectorio, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInicioSesionLayout.createSequentialGroup()
                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(pnlInicioSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addComponent(btnExaminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(38, 38, 38))
                    .addGroup(pnlInicioSesionLayout.createSequentialGroup()
                        .addGroup(pnlInicioSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelInstruccion2, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelInstruccion1))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        pnlInicioSesionLayout.setVerticalGroup(
            pnlInicioSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInicioSesionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelInstruccion1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelInstruccion2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlInicioSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExaminar)
                    .addComponent(txtFieldDirectorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlInicioSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnAceptar))
                .addContainerGap())
            .addComponent(labelBanner)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlInicioSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlInicioSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        telemetria.logActivity("Se hizo click en aceptar.");
        methods.Global.appConfig.setProperty("comparecencias_path",elPath);
        if(archivo.crearCarpetaComparecencias()){
            crearArchivoConfiguracion();
            crearPantallaInicio();
        }else{
                JOptionPane.showMessageDialog(null, """
                                                    No es posible usar ese destino para guardar las comparecencias.
                                                    Seleccione otro destino.
                                                    """, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        telemetria.logActivity("Se hizo click en cancelar.");
        //se dispara el evento de cerrar ventana
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnExaminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExaminarActionPerformed
        telemetria.logActivity("Se hizo click en examinar.");
        crearExploradorDeArchivos();
    }//GEN-LAST:event_btnExaminarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        //se pide confiramacion
        laRespuesta = JOptionPane.showConfirmDialog(null,
                "¿Realmente desea salir? \n",
                "ATENCION",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        //se procesa la respuesta
        if (laRespuesta == JOptionPane.YES_OPTION) {
            telemetria.logActivity("Se sale del programa.");
            disposeVariables();
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    private void btnExaminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExaminarMouseEntered
        setButtonActiveColor(btnExaminar);
    }//GEN-LAST:event_btnExaminarMouseEntered

    private void btnExaminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExaminarMouseExited
        setButtonNormalColor(btnExaminar);
    }//GEN-LAST:event_btnExaminarMouseExited

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        setButtonActiveColor(btnCancelar);
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        setButtonNormalColor(btnCancelar);
    }//GEN-LAST:event_btnCancelarMouseExited

    private void btnAceptarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseEntered
        setButtonActiveColor(btnAceptar);
    }//GEN-LAST:event_btnAceptarMouseEntered

    private void btnAceptarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseExited
        setButtonNormalColor(btnAceptar);
    }//GEN-LAST:event_btnAceptarMouseExited

    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Configuracion().setVisible(true);
        });
    }
    
    private void initForm(){
        telemetria.logActivity("Se ingresa a la pantalla de configuracion.");
        txtFieldDirectorio.setText(elPath);
        setButtonNormalColor(btnAceptar);
        setButtonNormalColor(btnCancelar);
        setButtonNormalColor(btnExaminar);
    }
    
    private void initVariables(){
        PANTONE2727C = new Color(82, 132, 196);
        PANTONE1595C = new Color(221, 103, 15);
        telemetria = new Telemetria();
        archivo = new Archivos();
        elPath =  System.getProperty("user.dir")
                 +System.getProperty("file.separator")
                 +"Comparecencias"
                 +System.getProperty("file.separator");
    }
    
    private void disposeVariables(){
        telemetria = null;
        archivo = null;
        elPath = null;
        laRespuesta = 0;
        result = 0;
        pantallaInicio = null;
    }
    
    private Color PANTONE2727C;
    private Color PANTONE1595C;
    private Telemetria telemetria;
    private Archivos archivo;
    private String elPath;
    private int laRespuesta;
    private int result;
    private Inicio pantallaInicio;
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExaminar;
    private javax.swing.JFileChooser configFileChooser;
    private javax.swing.JLabel labelBanner;
    private javax.swing.JLabel labelInstruccion1;
    private javax.swing.JLabel labelInstruccion2;
    private javax.swing.JPanel pnlInicioSesion;
    private javax.swing.JTextField txtFieldDirectorio;
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