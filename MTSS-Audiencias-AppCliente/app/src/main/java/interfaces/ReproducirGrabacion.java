package interfaces;

import database.MetodosDatabase;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import methods.ReproducirAudio;
import methods.Archivos;
import methods.Telemetria;
import methods.Cronometro;
import methods.Proceso;
import models.*;

public class ReproducirGrabacion extends javax.swing.JFrame {

    public ReproducirGrabacion() {
        initComponents();
        initVariables();
        initForm();
    }
    
    public ReproducirGrabacion(Inspector inspector, Comparecencia comparecencia, Audio audio){
        this();
        this.inspector = inspector;
        this.comparecencia = comparecencia;
        this.audio = audio;
        postInitForm();
    }

    private void crearPantallaPrincipal() {
        pantallaPrincipal = new Principal(inspector);
        pantallaPrincipal.setLocationRelativeTo(null);
        pantallaPrincipal.setVisible(true);
        disposeVariables();
        this.dispose();
    }
    
    public void recargarTabla(){
        try {
            tblAnotacionesModel.setRowCount(0);
            tblAnotaciones.setModel(anotacion.loadTable(tblAnotacionesModel));
            tblAnotaciones.revalidate();
            tblAnotaciones.repaint();
        } catch (Exception ex) {
            telemetria.logActivity("Error al cargar la tabla con anotaciones.");
            telemetria.logException(ex);
        }
    }
    
    private void cargarDatosRegistroComparecencia(){
        try{
            listaPresentes = database.getDatosComparecencia(
                           comparecencia.getIdComparecencia());
        }catch(Exception e){
            telemetria.logActivity("error al cargar los datos de la comparecencia.");
            telemetria.logException(e);
        }
    
    }
    
    private void cargarDatosComboBox() {
        
        cmbConsultasGrabacion.addItem("Comparecencia");
        
        for (Object elemento : listaPresentes) {
            if (elemento instanceof Testigo) {
                String nombreCompleto = "Testigo: "
                        + " " + ((Testigo) elemento).getPersona().getPrimerNombre()
                        + " " + ((Testigo) elemento).getPersona().getSegundoNombre()
                        + " " + ((Testigo) elemento).getPersona().getPrimerApellido()
                        + " " + ((Testigo) elemento).getPersona().getSegundoApellido();

                cmbConsultasGrabacion.addItem(nombreCompleto);
            
            } else if (elemento instanceof Acompañante) {
                String nombreCompleto = "Acompañante: "
                        + " " + ((Acompañante) elemento).getPersona().getPrimerNombre()
                        + " " + ((Acompañante) elemento).getPersona().getSegundoNombre()
                        + " " + ((Acompañante) elemento).getPersona().getPrimerApellido()
                        + " " + ((Acompañante) elemento).getPersona().getSegundoApellido();

                cmbConsultasGrabacion.addItem(nombreCompleto);
            
            } else if (elemento instanceof Representante) {
                String nombreCompleto = "Representante: "
                        + " " + ((Representante) elemento).getPersona().getPrimerNombre()
                        + " " + ((Representante) elemento).getPersona().getSegundoNombre()
                        + " " + ((Representante) elemento).getPersona().getPrimerApellido()
                        + " " + ((Representante) elemento).getPersona().getSegundoApellido();

                cmbConsultasGrabacion.addItem(nombreCompleto);
            
            } else if (elemento instanceof Gestionado) {

                if (((Gestionado) elemento).getPersona() != null) {

                    String nombreCompleto = "Gestionado: "
                            + " " + ((Gestionado) elemento).getPersona().getPrimerNombre()
                            + " " + ((Gestionado) elemento).getPersona().getSegundoNombre()
                            + " " + ((Gestionado) elemento).getPersona().getPrimerApellido()
                            + " " + ((Gestionado) elemento).getPersona().getSegundoApellido();
                    cmbConsultasGrabacion.addItem(nombreCompleto);

                } else if(((Gestionado) elemento).getPersonaJuridica() != null) {
                    String nombreCompleto = "Gestionado: "
                            + " " + ((Gestionado) elemento).getPersonaJuridica().getNombreRazonSocial();

                    cmbConsultasGrabacion.addItem(nombreCompleto);
                }

            } else if (elemento instanceof Gestionante) {
                if (((Gestionante) elemento).getPersona() != null) {

                    String nombreCompleto = "Gestionante: "
                            + " " + ((Gestionante) elemento).getPersona().getPrimerNombre()
                            + " " + ((Gestionante) elemento).getPersona().getSegundoNombre()
                            + " " + ((Gestionante) elemento).getPersona().getPrimerApellido()
                            + " " + ((Gestionante) elemento).getPersona().getSegundoApellido();
                    cmbConsultasGrabacion.addItem(nombreCompleto);

                } else if (((Gestionante) elemento).getPersonaJuridica() != null) {
                    String nombreCompleto = "Gestionante: "
                            + " " + ((Gestionante) elemento).getPersonaJuridica().getNombreRazonSocial();

                    cmbConsultasGrabacion.addItem(nombreCompleto);

                }

            }
        }
    }

    private void configureBtnLinkSILAC(){
        if(!comparecencia.getLinkExpediente().equals("")){
            String underlineText = comparecencia.getCodigoSILAC();
            underlineText = "<html><u>"+underlineText+"</u></html>";
            btnLinkSILAC.setText(underlineText);
            btnLinkSILAC.setForeground(Color.BLUE);
        }else{
            btnLinkSILAC.setEnabled(false);
            String normalText = comparecencia.getCodigoSILAC();
            normalText = "<html><font color = black>"+normalText+"</font></html>";
            btnLinkSILAC.setText(normalText);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelGrabacion = new javax.swing.JPanel();
        btnReproducirPausar = new javax.swing.JButton();
        btnDetenerReproduccion = new javax.swing.JButton();
        sliderVolumen = new javax.swing.JSlider();
        btnVolume = new javax.swing.JButton();
        panelAnotacion = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAnotaciones = new javax.swing.JTable();
        panelAnotacionEspecial = new javax.swing.JPanel();
        cmbConsultasGrabacion = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDatosGrabacion = new javax.swing.JTextArea();
        lblTiempo = new javax.swing.JLabel();
        progressSlider = new javax.swing.JSlider();
        btnLinkSILAC = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("SGC | Reproducir Grabacion");
        setBackground(new java.awt.Color(255, 255, 255));
        setIconImage(new ImageIcon(getClass().getResource("/favicon.png")).getImage());
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

        panelGrabacion.setBackground(new java.awt.Color(255, 255, 255));
        panelGrabacion.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)), "Controles de Reproducción", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 0, 10))); // NOI18N
        panelGrabacion.setName("Grabacion"); // NOI18N
        panelGrabacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelGrabacionMouseClicked(evt);
            }
        });

        btnReproducirPausar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reproducir/play.png"))); // NOI18N
        btnReproducirPausar.setToolTipText("Reproducir/Pausar Grabación");
        btnReproducirPausar.setContentAreaFilled(false);
        btnReproducirPausar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReproducirPausar.setMaximumSize(new java.awt.Dimension(40, 30));
        btnReproducirPausar.setMinimumSize(new java.awt.Dimension(40, 30));
        btnReproducirPausar.setPreferredSize(new java.awt.Dimension(40, 30));
        btnReproducirPausar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnReproducirPausarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnReproducirPausarMouseExited(evt);
            }
        });
        btnReproducirPausar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReproducirPausarActionPerformed(evt);
            }
        });

        btnDetenerReproduccion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reproducir/stop.png"))); // NOI18N
        btnDetenerReproduccion.setToolTipText("Detener Audio");
        btnDetenerReproduccion.setContentAreaFilled(false);
        btnDetenerReproduccion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDetenerReproduccion.setMaximumSize(new java.awt.Dimension(40, 30));
        btnDetenerReproduccion.setMinimumSize(new java.awt.Dimension(40, 30));
        btnDetenerReproduccion.setPreferredSize(new java.awt.Dimension(40, 30));
        btnDetenerReproduccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDetenerReproduccionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDetenerReproduccionMouseExited(evt);
            }
        });
        btnDetenerReproduccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetenerReproduccionActionPerformed(evt);
            }
        });

        sliderVolumen.setBackground(new java.awt.Color(255, 255, 255));
        sliderVolumen.setMaximum(6);
        sliderVolumen.setMinimum(-80);
        sliderVolumen.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderVolumenStateChanged(evt);
            }
        });

        btnVolume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reproducir/volume.png"))); // NOI18N
        btnVolume.setToolTipText("Volumen");
        btnVolume.setBorderPainted(false);
        btnVolume.setContentAreaFilled(false);
        btnVolume.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVolume.setMaximumSize(new java.awt.Dimension(40, 40));
        btnVolume.setMinimumSize(new java.awt.Dimension(40, 40));
        btnVolume.setPreferredSize(new java.awt.Dimension(40, 40));
        btnVolume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolumeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelGrabacionLayout = new javax.swing.GroupLayout(panelGrabacion);
        panelGrabacion.setLayout(panelGrabacionLayout);
        panelGrabacionLayout.setHorizontalGroup(
            panelGrabacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGrabacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnDetenerReproduccion, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReproducirPausar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(575, 575, 575)
                .addComponent(btnVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sliderVolumen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        panelGrabacionLayout.setVerticalGroup(
            panelGrabacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGrabacionLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(panelGrabacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDetenerReproduccion, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReproducirPausar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGrabacionLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelGrabacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sliderVolumen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        panelAnotacion.setBackground(new java.awt.Color(255, 255, 255));
        panelAnotacion.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(200, 200, 200), 1, true), "Anotaciones", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));

        tblAnotaciones.setAutoCreateRowSorter(true);
        tblAnotaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblAnotaciones.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        tblAnotaciones.setGridColor(new java.awt.Color(255, 255, 255));
        tblAnotaciones.setSelectionBackground(java.awt.SystemColor.textHighlight);
        tblAnotaciones.setSelectionForeground(java.awt.SystemColor.textHighlightText);
        tblAnotaciones.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblAnotaciones.setShowHorizontalLines(true);
        tblAnotaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAnotacionesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblAnotaciones);

        javax.swing.GroupLayout panelAnotacionLayout = new javax.swing.GroupLayout(panelAnotacion);
        panelAnotacion.setLayout(panelAnotacionLayout);
        panelAnotacionLayout.setHorizontalGroup(
            panelAnotacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAnotacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        panelAnotacionLayout.setVerticalGroup(
            panelAnotacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAnotacionLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelAnotacionEspecial.setBackground(new java.awt.Color(255, 255, 255));
        panelAnotacionEspecial.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(200, 200, 200), 1, true), "Datos de Grabación", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));
        panelAnotacionEspecial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelAnotacionEspecialMouseClicked(evt);
            }
        });

        cmbConsultasGrabacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione una categoria ..." }));
        cmbConsultasGrabacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbConsultasGrabacionActionPerformed(evt);
            }
        });

        txtDatosGrabacion.setEditable(false);
        txtDatosGrabacion.setBackground(new java.awt.Color(255, 255, 255));
        txtDatosGrabacion.setColumns(20);
        txtDatosGrabacion.setRows(5);
        txtDatosGrabacion.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDatosGrabacion.setMaximumSize(new java.awt.Dimension(232, 84));
        txtDatosGrabacion.setMinimumSize(new java.awt.Dimension(232, 84));
        txtDatosGrabacion.setSelectionColor(java.awt.SystemColor.textHighlight);
        jScrollPane1.setViewportView(txtDatosGrabacion);

        javax.swing.GroupLayout panelAnotacionEspecialLayout = new javax.swing.GroupLayout(panelAnotacionEspecial);
        panelAnotacionEspecial.setLayout(panelAnotacionEspecialLayout);
        panelAnotacionEspecialLayout.setHorizontalGroup(
            panelAnotacionEspecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAnotacionEspecialLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbConsultasGrabacion, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelAnotacionEspecialLayout.setVerticalGroup(
            panelAnotacionEspecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAnotacionEspecialLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAnotacionEspecialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAnotacionEspecialLayout.createSequentialGroup()
                        .addComponent(cmbConsultasGrabacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 117, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        lblTiempo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTiempo.setText("00:00:00");

        progressSlider.setBackground(new java.awt.Color(255, 255, 255));
        progressSlider.setPaintLabels(true);
        progressSlider.setPaintTicks(true);
        progressSlider.setValue(0);
        progressSlider.setMaximumSize(new java.awt.Dimension(888, 36));
        progressSlider.setMinimumSize(new java.awt.Dimension(888, 36));
        progressSlider.setPreferredSize(new java.awt.Dimension(888, 36));
        progressSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                progressSliderStateChanged(evt);
            }
        });
        progressSlider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                progressSliderMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                progressSliderMouseExited(evt);
            }
        });

        btnLinkSILAC.setText("[CODIGO_SILAC]");
        btnLinkSILAC.setBorder(null);
        btnLinkSILAC.setBorderPainted(false);
        btnLinkSILAC.setContentAreaFilled(false);
        btnLinkSILAC.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLinkSILAC.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLinkSILAC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLinkSILACActionPerformed(evt);
            }
        });

        jLabel1.setText("Reproduciendo:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelGrabacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panelAnotacionEspecial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelAnotacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(6, 6, 6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(progressSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTiempo))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLinkSILAC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelAnotacionEspecial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelAnotacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(progressSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTiempo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLinkSILAC)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(panelGrabacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
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

    private void btnReproducirPausarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReproducirPausarActionPerformed
        telemetria.logActivity("Se hizo click en reproducir.");
        if(reproduciendoAudio){
            reproduciendoAudio = false;
            cronometro.setRunning(false);
            sonido.pause();
            //progressSlider.setEnabled(true);
            btnReproducirPausar.setIcon(play);
        }else{
            reproduciendoAudio = true;
            cronometro.start(lblTiempo);
            sonido.resume();
            audioMonitor();
            audioDetenido = false;
            //progressSlider.setEnabled(false);
            btnReproducirPausar.setIcon(pause);
        }
        
        
    }//GEN-LAST:event_btnReproducirPausarActionPerformed

    private void btnDetenerReproduccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetenerReproduccionActionPerformed
        telemetria.logActivity("Se hizo click en detener.");
        audioDetenido = true;
        reproduciendoAudio = false;
        sonido.pause();
        sonido.stop();
        cronometro.stop(lblTiempo);
        progressSlider.setValue(0);
        btnReproducirPausar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reproducir/play.png")));
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_btnDetenerReproduccionActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        //se pide confiramacion
        laRespuesta = JOptionPane.showConfirmDialog(null,
                "¿Desea regresar a la pantalla principal? \n",
                "ATENCION",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        //se procesa la respuesta
        if (laRespuesta == JOptionPane.YES_OPTION) {
            telemetria.logActivity("Se sale de pantalla de reproduccion grabacion.");
            crearPantallaPrincipal();
        }
    }//GEN-LAST:event_formWindowClosing

    private void cmbConsultasGrabacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbConsultasGrabacionActionPerformed
        String elItem = cmbConsultasGrabacion.getSelectedItem().toString();

        if (elItem.contains("Comparecencia")) {
            txtDatosGrabacion.setText(comparecencia.toString());
            return;
        }

        if (elItem.contains("Gestionante:")) {
            for (Object elemento : listaPresentes) {
                if (elemento instanceof Gestionante) {
                    if (((Gestionante) elemento).getPersona() != null) {

                        String nombreCompleto = "";
                        nombreCompleto = ((Gestionante) elemento).getPersona().getPrimerNombre()
                                + " " + ((Gestionante) elemento).getPersona().getSegundoNombre()
                                + " " + ((Gestionante) elemento).getPersona().getPrimerApellido()
                                + " " + ((Gestionante) elemento).getPersona().getSegundoApellido();

                        String elItemSinPrefijo = elItem.replace("Gestionante:  ", "");
                        if (elItemSinPrefijo.equals(nombreCompleto)) {

                            txtDatosGrabacion.setText(((Gestionante) elemento).toString());
                            return;

                        }

                    } else if (((Gestionante) elemento).getPersonaJuridica() != null) {
                        if (elItem.contains(((Gestionante) elemento).getPersonaJuridica().getNombreRazonSocial())) {

                            txtDatosGrabacion.setText(((Gestionante) elemento).toString());
                            return;

                        }
                    }
                }
            }

        }

        if (elItem.contains("Gestionado:")) {
            for (Object elemento : listaPresentes) {
                if (elemento instanceof Gestionado) {
                    if (((Gestionado) elemento).getPersona() != null) {

                        String nombreCompleto = "";
                        nombreCompleto = ((Gestionado) elemento).getPersona().getPrimerNombre()
                                + " " + ((Gestionado) elemento).getPersona().getSegundoNombre()
                                + " " + ((Gestionado) elemento).getPersona().getPrimerApellido()
                                + " " + ((Gestionado) elemento).getPersona().getSegundoApellido();

                        String elItemSinPrefijo = elItem.replace("Gestionado:  ", "");
                        if (elItemSinPrefijo.equals(nombreCompleto)) {

                            txtDatosGrabacion.setText(((Gestionado) elemento).toString());
                            return;

                        }
                    } else if (((Gestionado) elemento).getPersonaJuridica() != null) {
                        if (elItem.contains(((Gestionado) elemento).getPersonaJuridica().getNombreRazonSocial())) {

                            txtDatosGrabacion.setText(((Gestionado) elemento).toString());
                            return;

                        }
                    }
                }
            }
        }

        if (elItem.contains("Representante:")) {
            for (Object elemento : listaPresentes) {
                if (elemento instanceof Representante) {

                    String nombreCompleto = "";
                    nombreCompleto = ((Representante) elemento).getPersona().getPrimerNombre()
                            + " " + ((Representante) elemento).getPersona().getSegundoNombre()
                            + " " + ((Representante) elemento).getPersona().getPrimerApellido()
                            + " " + ((Representante) elemento).getPersona().getSegundoApellido();

                    String elItemSinPrefijo = elItem.replace("Representante:  ", "");
                    if (elItemSinPrefijo.equals(nombreCompleto)) {

                        txtDatosGrabacion.setText(((Representante) elemento).toString());
                        return;

                    }
                }
            }
        }

        if (elItem.contains("Acompañante:")) {
            for (Object elemento : listaPresentes) {
                if (elemento instanceof Acompañante) {

                    String nombreCompleto = "";
                    nombreCompleto = ((Acompañante) elemento).getPersona().getPrimerNombre()
                            + " " + ((Acompañante) elemento).getPersona().getSegundoNombre()
                            + " " + ((Acompañante) elemento).getPersona().getPrimerApellido()
                            + " " + ((Acompañante) elemento).getPersona().getSegundoApellido();

                    String elItemSinPrefijo = elItem.replace("Acompañante:  ", "");
                    if (elItemSinPrefijo.equals(nombreCompleto)) {

                        txtDatosGrabacion.setText(((Acompañante) elemento).toString());
                        return;

                    }
                }
            }
        }

        if (elItem.contains("Testigo:")) {

            for (Object elemento : listaPresentes) {
                if (elemento instanceof Testigo) {

                    String nombreCompleto = "";
                    nombreCompleto = ((Testigo) elemento).getPersona().getPrimerNombre()
                            + " " + ((Testigo) elemento).getPersona().getSegundoNombre()
                            + " " + ((Testigo) elemento).getPersona().getPrimerApellido()
                            + " " + ((Testigo) elemento).getPersona().getSegundoApellido();

                    String elItemSinPrefijo = elItem.replace("Testigo:  ", "");
                    if (elItemSinPrefijo.equals(nombreCompleto)) {

                        txtDatosGrabacion.setText(((Testigo) elemento).toString());
                        return;

                    }
                }
            }
        }
    }//GEN-LAST:event_cmbConsultasGrabacionActionPerformed

    public void audioMonitor() {
        new Thread(() -> {
            try {
                while (!audioDetenido) {
                    Thread.sleep(1000);
                    if(reproduciendoAudio){
                        progressSlider.setValue(sonido.getProgress());
                    }
                    if (sonido.endOfFile == true) {
                        progressSlider.setValue(0);
                        cronometro.setRunning(false);
                        cronometro.stop(lblTiempo);
                        btnReproducirPausar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reproducir/play.png")));
                        break;
                    }
                }
            } catch (Exception e) {
                telemetria.logActivity("Fallo en el monitor de audio.");
                telemetria.logException(e);
            }
        }).start();
    }
    
    private void obtenerDatosFila(){
        int rowSelected = tblAnotaciones.getSelectedRow();
        tiempoSeleccionado = tblAnotaciones.getValueAt(rowSelected, 0).toString();
        if(tiempoSeleccionado.length() > 8){
            tiempoSeleccionado = tiempoSeleccionado.substring(0, 8);
        }
    }
    
    private void sliderVolumenStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderVolumenStateChanged
        sonido.cambiarVolumen(sliderVolumen.getValue());
    }//GEN-LAST:event_sliderVolumenStateChanged

    private void progressSliderMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_progressSliderMouseEntered
        ignoreChange = false;
    }//GEN-LAST:event_progressSliderMouseEntered

    private void progressSliderMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_progressSliderMouseExited
        ignoreChange = true;
    }//GEN-LAST:event_progressSliderMouseExited

    private void progressSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_progressSliderStateChanged
        if(ignoreChange){
            return;
        }else{
            audioPosition = progressSlider.getValue();
            sonido.setProgress(audioPosition);
            cronometro.setTime(audio.convertDoubleToTime(sonido.getCurrentTime()));
            lblTiempo.setText(cronometro.getTime());
        }
    }//GEN-LAST:event_progressSliderStateChanged

    private void tblAnotacionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAnotacionesMouseClicked
        obtenerDatosFila();
        sonido.setCurrentTime(audio.convertTimeToDouble(tiempoSeleccionado));
        cronometro.setTime(tiempoSeleccionado);
        lblTiempo.setText(cronometro.getTime());
        progressSlider.setValue(sonido.getProgress());
    }//GEN-LAST:event_tblAnotacionesMouseClicked

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        tblAnotaciones.getSelectionModel().clearSelection();
    }//GEN-LAST:event_jPanel1MouseClicked

    private void panelGrabacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelGrabacionMouseClicked
        tblAnotaciones.getSelectionModel().clearSelection();
    }//GEN-LAST:event_panelGrabacionMouseClicked

    private void panelAnotacionEspecialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAnotacionEspecialMouseClicked
        tblAnotaciones.getSelectionModel().clearSelection();
    }//GEN-LAST:event_panelAnotacionEspecialMouseClicked

    private void btnVolumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolumeActionPerformed
        if(conVolumen){
            conVolumen = false;
            btnVolume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reproducir/mute.png")));
            lastVolumeValue = sliderVolumen.getValue();
            sliderVolumen.setValue(-80);
        }else{
            conVolumen = true;
            btnVolume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reproducir/volume.png")));
            sliderVolumen.setValue(lastVolumeValue);
        }
    }//GEN-LAST:event_btnVolumeActionPerformed

    private void btnDetenerReproduccionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDetenerReproduccionMouseEntered
        btnDetenerReproduccion.setIcon(stop_active);
    }//GEN-LAST:event_btnDetenerReproduccionMouseEntered

    private void btnDetenerReproduccionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDetenerReproduccionMouseExited
        btnDetenerReproduccion.setIcon(stop);
    }//GEN-LAST:event_btnDetenerReproduccionMouseExited

    private void btnReproducirPausarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReproducirPausarMouseEntered
        if (  btnReproducirPausar.getIcon().toString().equals(pause.toString()) ||
              btnReproducirPausar.getIcon().toString().equals(pause_active.toString()) ){
            
            btnReproducirPausar.setIcon(pause_active);
        } else {
            btnReproducirPausar.setIcon(play_active);
        }
    }//GEN-LAST:event_btnReproducirPausarMouseEntered

    private void btnReproducirPausarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReproducirPausarMouseExited
        if (  btnReproducirPausar.getIcon().toString().equals(pause.toString()) ||
              btnReproducirPausar.getIcon().toString().equals(pause_active.toString()) ){
            
            btnReproducirPausar.setIcon(pause);
        } else {
            btnReproducirPausar.setIcon(play);
        }
    }//GEN-LAST:event_btnReproducirPausarMouseExited

    private void btnLinkSILACActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLinkSILACActionPerformed
        proceso.execNavegadorWeb(comparecencia.getLinkExpediente());
    }//GEN-LAST:event_btnLinkSILACActionPerformed

    
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ReproducirGrabacion().setVisible(true);
        });
    }
    
    private void postInitForm(){
        configureBtnLinkSILAC();
        sonido = new ReproducirAudio(audio);
        String JSON = archivo.leerArchivoAnotaciones(audio.getPathArchivoAnotaciones());
        anotacion = anotacion.jsonToObject(JSON);
        recargarTabla();
        cargarDatosRegistroComparecencia();
        cargarDatosComboBox();
    }

    private void initForm(){
        telemetria.logActivity("Se ingresa a pantalla de reproducir grabacion.");
        tblAnotaciones.setDefaultEditor(Object.class, null);
        tblAnotacionesModel.addColumn("Tiempo");
        tblAnotacionesModel.addColumn("Tipo"); 
        tblAnotacionesModel.addColumn("Contenido");           
        tblAnotaciones.setModel(tblAnotacionesModel);
        //progressSlider.setEnabled(false);
    }
    
    private void initVariables(){
        PANTONE2727C = new Color(82, 132, 196);
        PANTONE1595C = new Color(221, 103, 15);
        pause = new ImageIcon(getClass().getResource("/icons/reproducir/pause.png"));
        stop = new ImageIcon(getClass().getResource("/icons/reproducir/stop.png"));
        play = new ImageIcon(getClass().getResource("/icons/reproducir/play.png"));
        pause_active = new ImageIcon(getClass().getResource("/icons/reproducir/pause-active.png"));
        stop_active = new ImageIcon(getClass().getResource("/icons/reproducir/stop-active.png"));
        play_active = new ImageIcon(getClass().getResource("/icons/reproducir/play-active.png"));
        laRespuesta = 0;
        tblAnotacionesModel = new DefaultTableModel();
        proceso = new Proceso();
        telemetria = new Telemetria();
        database = new MetodosDatabase();
        archivo = new Archivos();
        anotacion = new Anotaciones();
        cronometro = new Cronometro();
        reproduciendoAudio = false;
        ignoreChange = true;
        audioDetenido = false;
        conVolumen = true;
        lastVolumeValue = 0;
    }
    
    private void disposeVariables(){
        sonido = null;
        comparecencia = null;
        audio = null;
        archivo = null;
        listaPresentes = null;
        pantallaPrincipal = null;
        laRespuesta = 0;
        tblAnotacionesModel = null;
        anotacion = null;
        telemetria = null;
        database = null;
        cronometro = null;
        reproduciendoAudio = null;
        audioDetenido =  null;
        audioPosition = 0;
        ignoreChange = null;
        tiempoSeleccionado = null;
        conVolumen = null;
    }
    
    private Color PANTONE2727C;
    private Color PANTONE1595C;
    private ImageIcon pause;
    private ImageIcon stop;
    private ImageIcon play;
    private ImageIcon pause_active;
    private ImageIcon stop_active;
    private ImageIcon play_active;
    private Inspector inspector;
    private Comparecencia comparecencia;
    private Audio audio;
    private List<Object> listaPresentes;
    private Principal pantallaPrincipal;
    private int laRespuesta;
    private DefaultTableModel tblAnotacionesModel;
    private Archivos archivo;
    private Anotaciones anotacion;
    private Telemetria telemetria;
    private Proceso proceso;
    private MetodosDatabase database;
    private ReproducirAudio sonido;
    private Cronometro cronometro;
    private Boolean reproduciendoAudio;
    private Boolean audioDetenido;
    private int audioPosition;
    private Boolean ignoreChange;
    private String tiempoSeleccionado;
    private Boolean conVolumen;
    private int lastVolumeValue;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDetenerReproduccion;
    private javax.swing.JButton btnLinkSILAC;
    private javax.swing.JButton btnReproducirPausar;
    private javax.swing.JButton btnVolume;
    private javax.swing.JComboBox<String> cmbConsultasGrabacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTiempo;
    private javax.swing.JPanel panelAnotacion;
    private javax.swing.JPanel panelAnotacionEspecial;
    private javax.swing.JPanel panelGrabacion;
    private javax.swing.JSlider progressSlider;
    private javax.swing.JSlider sliderVolumen;
    private javax.swing.JTable tblAnotaciones;
    private javax.swing.JTextArea txtDatosGrabacion;
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