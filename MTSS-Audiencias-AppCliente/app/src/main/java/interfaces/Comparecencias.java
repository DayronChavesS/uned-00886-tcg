package interfaces;

import database.MetodosDatabase;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import methods.GrabarAudio;
import methods.Telemetria;
import models.*;
import methods.Cronometro;
import methods.Archivos;
import methods.Proceso;

public class Comparecencias extends javax.swing.JFrame {    
    
    public Comparecencias() {
        initComponents();
        initVariables();
        initForm();
    }
    
    public Comparecencias(Inspector inspector, Comparecencia comparecencia, Audio audio){
        this();
        this.inspector = inspector;
        this.comparecencia = comparecencia;
        this.audio = audio;
        postInitForm();
    }
        
    private void crearPantallaPrincipal() {
        telemetria.logActivity("Ingreso al sistema.");
        pantallaPrincipal = new Principal(inspector);
        pantallaPrincipal.setLocationRelativeTo(null);
        pantallaPrincipal.setVisible(true);
        disposeVariables();
        this.dispose();
    }
    
    private void guardarDatosAudio() throws Exception{
        audio = database.guardarAudio(audio);
        database.guardarAudioXComparecencia(audio, comparecencia);
    }
    
    private void crearArchivosAudio() throws IOException{
        audio = archivo.crearArchivoAudio(comparecencia, audio);
        audio = archivo.crearArchivoAnotaciones(comparecencia, audio);
    }

    public void guardarDuracionAudio(){
        try{
            double duracionDouble = audio.calcularDuracionArchivo(audio.getArchivoAudio());
            String duracionString = audio.convertDoubleToTime(duracionDouble);
            database.updateDuracionAudio(audio.getIdAudio(), duracionString);
        }catch(Exception e){
            telemetria.logActivity("Se sale de pantalla de la pantalla de comparecencias.");
            telemetria.logException(e);

        }

    }
    
    public String obtenerNombreAudio(){
        String nombreAudio = JOptionPane.showInputDialog(null, "Digite el nombre de las grabaciones de esta comparecencia:", "Nombre de la Grabación", JOptionPane.QUESTION_MESSAGE);
        
        if (nombreAudio != null) {
            if (!nombreAudio.isEmpty()) {
                return nombreAudio;
            } else {
                JOptionPane.showMessageDialog(null, """
                                                    Debe ingresar un nombre.
                                                    Inténtelo de nuevo.
                                                    """,
                                                    "ERROR",
                                                    JOptionPane.ERROR_MESSAGE);
            }
        }
        return "SIN AUDIOS";
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
    
    private void cargarDatosAnotacionesEspeciales() {
        
        for (Object elemento : listaPresentes) {
            if (elemento instanceof Testigo) {
                String nombreCompleto = "Testigo: "
                        + " " + ((Testigo) elemento).getPersona().getPrimerNombre()
                        + " " + ((Testigo) elemento).getPersona().getSegundoNombre()
                        + " " + ((Testigo) elemento).getPersona().getPrimerApellido()
                        + " " + ((Testigo) elemento).getPersona().getSegundoApellido();

                cmbAnotacionesEspeciales.addItem(nombreCompleto);
            
            } else if (elemento instanceof Acompañante) {
                String nombreCompleto = "Acompañante: "
                        + " " + ((Acompañante) elemento).getPersona().getPrimerNombre()
                        + " " + ((Acompañante) elemento).getPersona().getSegundoNombre()
                        + " " + ((Acompañante) elemento).getPersona().getPrimerApellido()
                        + " " + ((Acompañante) elemento).getPersona().getSegundoApellido();

                cmbAnotacionesEspeciales.addItem(nombreCompleto);
            
            } else if (elemento instanceof Representante) {
                String nombreCompleto = "Representante: "
                        + " " + ((Representante) elemento).getPersona().getPrimerNombre()
                        + " " + ((Representante) elemento).getPersona().getSegundoNombre()
                        + " " + ((Representante) elemento).getPersona().getPrimerApellido()
                        + " " + ((Representante) elemento).getPersona().getSegundoApellido();

                cmbAnotacionesEspeciales.addItem(nombreCompleto);
            
            } else if (elemento instanceof Gestionado) {

                if (((Gestionado) elemento).getPersona() != null) {

                    String nombreCompleto = "Gestionado: "
                            + " " + ((Gestionado) elemento).getPersona().getPrimerNombre()
                            + " " + ((Gestionado) elemento).getPersona().getSegundoNombre()
                            + " " + ((Gestionado) elemento).getPersona().getPrimerApellido()
                            + " " + ((Gestionado) elemento).getPersona().getSegundoApellido();
                    cmbAnotacionesEspeciales.addItem(nombreCompleto);

                } else if(((Gestionado) elemento).getPersonaJuridica() != null) {
                    String nombreCompleto = "Gestionado: "
                            + " " + ((Gestionado) elemento).getPersonaJuridica().getNombreRazonSocial();

                    cmbAnotacionesEspeciales.addItem(nombreCompleto);
                }

            } else if (elemento instanceof Gestionante) {
                if (((Gestionante) elemento).getPersona() != null) {

                    String nombreCompleto = "Gestionante: "
                            + " " + ((Gestionante) elemento).getPersona().getPrimerNombre()
                            + " " + ((Gestionante) elemento).getPersona().getSegundoNombre()
                            + " " + ((Gestionante) elemento).getPersona().getPrimerApellido()
                            + " " + ((Gestionante) elemento).getPersona().getSegundoApellido();
                    cmbAnotacionesEspeciales.addItem(nombreCompleto);

                } else if (((Gestionante) elemento).getPersonaJuridica() != null) {
                    String nombreCompleto = "Gestionante: "
                            + " " + ((Gestionante) elemento).getPersonaJuridica().getNombreRazonSocial();

                    cmbAnotacionesEspeciales.addItem(nombreCompleto);

                }

            }
        }
    }
    
    public void recargarTabla(){
        try {
            tblAnotacionesModel.setRowCount(0); //elimina los datos
            tblAnotaciones.setModel(anotacion.loadTable(tblAnotacionesModel));
            tblAnotaciones.revalidate();
            tblAnotaciones.repaint();
        } catch (Exception ex) {
            telemetria.logException(ex);
        }
    }
    
    private int identificarFila(){
        
        int rowSelected = tblAnotaciones.getSelectedRow();
        String tipo = tblAnotaciones.getValueAt(rowSelected, 1).toString();
        String contenido = tblAnotaciones.getValueAt(rowSelected, 2).toString();
        
        switch(tipo){
            case "Anotación" ->{
                int index = anotacion.getIndexAnotacionWhere(contenido);
                if (index != -1) return index;
            } 
            
            case "Segmento" ->{
                int index = anotacion.getIndexSegmentoWhere(contenido);
                if (index != -1) return index;
            }
            
            case "Marcador" ->{
                 int index =  anotacion.getIndexMarcadorWhere(contenido);
                 if (index != -1) return index;
            }
            
            default -> {
                int index = anotacion.getIndexAnotacionEspecialWhere(contenido);
                if (index != -1) return index;
            }
        }
        
        return -1;
    }
    
    private String identificarTipoFila(){
        int rowSelected = tblAnotaciones.getSelectedRow();
        String tipo = tblAnotaciones.getValueAt(rowSelected, 1).toString();
        return tipo;
    }
    
    private void activarAnotaciones() {
        btnAgregarAnotaciones.setEnabled(true);
        setButtonNormalColor(btnAgregarAnotaciones);
        btnActualizarAnotacion.setEnabled(true);
        setButtonNormalColor(btnActualizarAnotacion);
        btnBorrarAnotacion.setEnabled(true);
        setButtonNormalColor(btnBorrarAnotacion);
        
        btnAgregarMarcadores.setEnabled(true);
        setButtonNormalColor(btnAgregarMarcadores);
        btnActualizarMarcadores.setEnabled(true);
        setButtonNormalColor(btnActualizarMarcadores);
        btnBorrarMarcadores.setEnabled(true);
        setButtonNormalColor(btnBorrarMarcadores);
        
        btnAgregarSegmentos.setEnabled(true);
        setButtonNormalColor(btnAgregarSegmentos);
        btnBorrarSegmentos.setEnabled(true);
        setButtonNormalColor(btnBorrarSegmentos);
        btnActualizarSegmentos.setEnabled(true);
        setButtonNormalColor(btnActualizarSegmentos);
        
        cmbAnotacionesEspeciales.setEnabled(true);
    }
    
    private void desactivarAnotaciones(){
        btnAgregarAnotaciones.setEnabled(false);
        setButtonDisabledColor(btnAgregarAnotaciones);
        btnActualizarAnotacion.setEnabled(false);
        setButtonDisabledColor(btnActualizarAnotacion);
        btnBorrarAnotacion.setEnabled(false);
        setButtonDisabledColor(btnBorrarAnotacion);

        btnAgregarMarcadores.setEnabled(false);
        setButtonDisabledColor(btnAgregarMarcadores);
        btnActualizarMarcadores.setEnabled(false);
        setButtonDisabledColor(btnActualizarMarcadores);
        btnBorrarMarcadores.setEnabled(false);
        setButtonDisabledColor(btnBorrarMarcadores);
        
        btnAgregarSegmentos.setEnabled(false);
        setButtonDisabledColor(btnAgregarSegmentos);
        btnBorrarSegmentos.setEnabled(false);
        setButtonDisabledColor(btnBorrarSegmentos);
        btnActualizarSegmentos.setEnabled(false);
        setButtonDisabledColor(btnActualizarSegmentos);
        
        cmbAnotacionesEspeciales.setEnabled(false);
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
    
    private void Salir() {
        laRespuesta = JOptionPane.showConfirmDialog(null,
                "¿Desea guardar la grabacion y salir a la pantalla principal?\n",
                "GRABACION DETENIDA",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        //se procesa la respuesta
        if (laRespuesta == JOptionPane.YES_OPTION) {
            desactivarAnotaciones();

            telemetria.logActivity("Se sale de pantalla de la pantalla de comparecencias.");

            grabacionIniciada = false;
            cronometro.stop(lblTiempo);
            grabacion.stop();
            grabacion.save();
            grabacion = null;

            guardarDuracionAudio();
            String JSON = anotacion.objectToJSON(anotacion);
            archivo.escribirArchivoAnotaciones(audio.getPathArchivoAnotaciones(), JSON);

            crearPantallaPrincipal();
        }

    }

    private void forceExit() {
        JOptionPane.showMessageDialog(null,
                """
                Lo sentimos, su dispositivo no puede realizar grabaciones.
                Imposible continuar.
                """,
                "ERROR FATAL",
                JOptionPane.ERROR_MESSAGE);

        crearPantallaPrincipal();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlGrabacionComparecencia = new javax.swing.JPanel();
        panelDocumentacion = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAnotaciones = new javax.swing.JTable();
        panelAnotacion = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAnotaciones = new javax.swing.JTextArea();
        btnBorrarAnotacion = new javax.swing.JButton();
        btnActualizarAnotacion = new javax.swing.JButton();
        btnAgregarAnotaciones = new javax.swing.JButton();
        cmbAnotacionesEspeciales = new javax.swing.JComboBox<>();
        lblAnotacionesEspeciales1 = new javax.swing.JLabel();
        panelSegmento = new javax.swing.JPanel();
        btnBorrarSegmentos = new javax.swing.JButton();
        btnAgregarSegmentos = new javax.swing.JButton();
        btnActualizarSegmentos = new javax.swing.JButton();
        txtTituloSegmento = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        panelMarcador = new javax.swing.JPanel();
        btnAgregarMarcadores = new javax.swing.JButton();
        btnBorrarMarcadores = new javax.swing.JButton();
        btnActualizarMarcadores = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtTituloMarcador = new javax.swing.JTextField();
        lblTiempo = new javax.swing.JLabel();
        btnGrabarDetener = new javax.swing.JButton();
        btnPausarReanudar = new javax.swing.JButton();
        btnConfigurarMicrofono = new javax.swing.JButton();
        btnLinkSILAC = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("SGC | Nueva Grabación");
        setIconImage(new ImageIcon(getClass().getResource("/favicon.png")).getImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlGrabacionComparecencia.setBackground(new java.awt.Color(255, 255, 255));
        pnlGrabacionComparecencia.setToolTipText("");
        pnlGrabacionComparecencia.setName(""); // NOI18N

        panelDocumentacion.setBackground(new java.awt.Color(255, 255, 255));
        panelDocumentacion.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(157, 160, 161)), "Documentación", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 0, 10))); // NOI18N

        jScrollPane2.setForeground(new java.awt.Color(200, 200, 200));

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
        tblAnotaciones.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblAnotaciones.setShowHorizontalLines(true);
        jScrollPane2.setViewportView(tblAnotaciones);

        panelAnotacion.setBackground(new java.awt.Color(255, 255, 255));
        panelAnotacion.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(200, 200, 200), 1, true), "Anotación", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.TOP));

        txtAnotaciones.setColumns(20);
        txtAnotaciones.setRows(5);
        jScrollPane1.setViewportView(txtAnotaciones);

        btnBorrarAnotacion.setText("Borrar");
        btnBorrarAnotacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBorrarAnotacion.setMaximumSize(new java.awt.Dimension(85, 25));
        btnBorrarAnotacion.setMinimumSize(new java.awt.Dimension(85, 25));
        btnBorrarAnotacion.setPreferredSize(new java.awt.Dimension(85, 25));
        btnBorrarAnotacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBorrarAnotacionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBorrarAnotacionMouseExited(evt);
            }
        });
        btnBorrarAnotacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarAnotacionActionPerformed(evt);
            }
        });

        btnActualizarAnotacion.setText("Actualizar");
        btnActualizarAnotacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizarAnotacion.setMaximumSize(new java.awt.Dimension(85, 25));
        btnActualizarAnotacion.setMinimumSize(new java.awt.Dimension(85, 25));
        btnActualizarAnotacion.setPreferredSize(new java.awt.Dimension(85, 25));
        btnActualizarAnotacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnActualizarAnotacionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnActualizarAnotacionMouseExited(evt);
            }
        });
        btnActualizarAnotacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarAnotacionActionPerformed(evt);
            }
        });

        btnAgregarAnotaciones.setText("Agregar");
        btnAgregarAnotaciones.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregarAnotaciones.setMaximumSize(new java.awt.Dimension(85, 25));
        btnAgregarAnotaciones.setMinimumSize(new java.awt.Dimension(85, 25));
        btnAgregarAnotaciones.setPreferredSize(new java.awt.Dimension(85, 25));
        btnAgregarAnotaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarAnotacionesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarAnotacionesMouseExited(evt);
            }
        });
        btnAgregarAnotaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarAnotacionesActionPerformed(evt);
            }
        });

        cmbAnotacionesEspeciales.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Anotación", "Prueba", "Importante" }));
        cmbAnotacionesEspeciales.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblAnotacionesEspeciales1.setText("Tipo de Anotación");

        javax.swing.GroupLayout panelAnotacionLayout = new javax.swing.GroupLayout(panelAnotacion);
        panelAnotacion.setLayout(panelAnotacionLayout);
        panelAnotacionLayout.setHorizontalGroup(
            panelAnotacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAnotacionLayout.createSequentialGroup()
                .addGroup(panelAnotacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAnotacionLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAnotacionLayout.createSequentialGroup()
                        .addGap(0, 51, Short.MAX_VALUE)
                        .addGroup(panelAnotacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAnotacionLayout.createSequentialGroup()
                                .addComponent(btnBorrarAnotacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnActualizarAnotacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAgregarAnotaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAnotacionLayout.createSequentialGroup()
                                .addComponent(lblAnotacionesEspeciales1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbAnotacionesEspeciales, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        panelAnotacionLayout.setVerticalGroup(
            panelAnotacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAnotacionLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelAnotacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbAnotacionesEspeciales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAnotacionesEspeciales1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelAnotacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarAnotaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizarAnotacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBorrarAnotacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panelSegmento.setBackground(new java.awt.Color(255, 255, 255));
        panelSegmento.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(200, 200, 200), 1, true), "Segmento", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        btnBorrarSegmentos.setText("Borrar");
        btnBorrarSegmentos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBorrarSegmentos.setMaximumSize(new java.awt.Dimension(85, 25));
        btnBorrarSegmentos.setMinimumSize(new java.awt.Dimension(85, 25));
        btnBorrarSegmentos.setPreferredSize(new java.awt.Dimension(85, 25));
        btnBorrarSegmentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBorrarSegmentosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBorrarSegmentosMouseExited(evt);
            }
        });
        btnBorrarSegmentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarSegmentosActionPerformed(evt);
            }
        });

        btnAgregarSegmentos.setText("Agregar Inicio");
        btnAgregarSegmentos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregarSegmentos.setMaximumSize(new java.awt.Dimension(85, 25));
        btnAgregarSegmentos.setMinimumSize(new java.awt.Dimension(85, 25));
        btnAgregarSegmentos.setPreferredSize(new java.awt.Dimension(85, 25));
        btnAgregarSegmentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarSegmentosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarSegmentosMouseExited(evt);
            }
        });
        btnAgregarSegmentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarSegmentosActionPerformed(evt);
            }
        });

        btnActualizarSegmentos.setText("Actualizar");
        btnActualizarSegmentos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizarSegmentos.setMaximumSize(new java.awt.Dimension(85, 25));
        btnActualizarSegmentos.setMinimumSize(new java.awt.Dimension(85, 25));
        btnActualizarSegmentos.setPreferredSize(new java.awt.Dimension(85, 25));
        btnActualizarSegmentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnActualizarSegmentosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnActualizarSegmentosMouseExited(evt);
            }
        });
        btnActualizarSegmentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarSegmentosActionPerformed(evt);
            }
        });

        jLabel1.setText("Titulo:");

        javax.swing.GroupLayout panelSegmentoLayout = new javax.swing.GroupLayout(panelSegmento);
        panelSegmento.setLayout(panelSegmentoLayout);
        panelSegmentoLayout.setHorizontalGroup(
            panelSegmentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSegmentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSegmentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSegmentoLayout.createSequentialGroup()
                        .addGap(0, 3, Short.MAX_VALUE)
                        .addComponent(btnBorrarSegmentos, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnActualizarSegmentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAgregarSegmentos, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSegmentoLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtTituloSegmento, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        panelSegmentoLayout.setVerticalGroup(
            panelSegmentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSegmentoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTituloSegmento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSegmentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBorrarSegmentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizarSegmentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarSegmentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panelMarcador.setBackground(new java.awt.Color(255, 255, 255));
        panelMarcador.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(200, 200, 200), 1, true), "Marcador", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));

        btnAgregarMarcadores.setText("Agregar");
        btnAgregarMarcadores.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregarMarcadores.setMaximumSize(new java.awt.Dimension(85, 25));
        btnAgregarMarcadores.setMinimumSize(new java.awt.Dimension(85, 25));
        btnAgregarMarcadores.setPreferredSize(new java.awt.Dimension(85, 25));
        btnAgregarMarcadores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAgregarMarcadoresMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAgregarMarcadoresMouseExited(evt);
            }
        });
        btnAgregarMarcadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarMarcadoresActionPerformed(evt);
            }
        });

        btnBorrarMarcadores.setText("Borrar");
        btnBorrarMarcadores.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBorrarMarcadores.setMaximumSize(new java.awt.Dimension(85, 25));
        btnBorrarMarcadores.setMinimumSize(new java.awt.Dimension(85, 25));
        btnBorrarMarcadores.setPreferredSize(new java.awt.Dimension(85, 25));
        btnBorrarMarcadores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBorrarMarcadoresMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBorrarMarcadoresMouseExited(evt);
            }
        });
        btnBorrarMarcadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarMarcadoresActionPerformed(evt);
            }
        });

        btnActualizarMarcadores.setText("Actualizar");
        btnActualizarMarcadores.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizarMarcadores.setMaximumSize(new java.awt.Dimension(85, 25));
        btnActualizarMarcadores.setMinimumSize(new java.awt.Dimension(85, 25));
        btnActualizarMarcadores.setPreferredSize(new java.awt.Dimension(85, 25));
        btnActualizarMarcadores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnActualizarMarcadoresMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnActualizarMarcadoresMouseExited(evt);
            }
        });
        btnActualizarMarcadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarMarcadoresActionPerformed(evt);
            }
        });

        jLabel2.setText("Titulo:");

        javax.swing.GroupLayout panelMarcadorLayout = new javax.swing.GroupLayout(panelMarcador);
        panelMarcador.setLayout(panelMarcadorLayout);
        panelMarcadorLayout.setHorizontalGroup(
            panelMarcadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMarcadorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMarcadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMarcadorLayout.createSequentialGroup()
                        .addComponent(txtTituloMarcador)
                        .addGap(6, 6, 6))
                    .addGroup(panelMarcadorLayout.createSequentialGroup()
                        .addComponent(btnBorrarMarcadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnActualizarMarcadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAgregarMarcadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelMarcadorLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panelMarcadorLayout.setVerticalGroup(
            panelMarcadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMarcadorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTituloMarcador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelMarcadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarMarcadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizarMarcadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBorrarMarcadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelDocumentacionLayout = new javax.swing.GroupLayout(panelDocumentacion);
        panelDocumentacion.setLayout(panelDocumentacionLayout);
        panelDocumentacionLayout.setHorizontalGroup(
            panelDocumentacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDocumentacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDocumentacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(panelDocumentacionLayout.createSequentialGroup()
                        .addComponent(panelAnotacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelDocumentacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelSegmento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelMarcador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panelDocumentacionLayout.setVerticalGroup(
            panelDocumentacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDocumentacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(panelDocumentacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelDocumentacionLayout.createSequentialGroup()
                        .addComponent(panelSegmento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelMarcador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelAnotacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );

        lblTiempo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTiempo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTiempo.setText("00:00:00");

        btnGrabarDetener.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/grabar/mic.png"))); // NOI18N
        btnGrabarDetener.setToolTipText("Iniciar/Detener Grabación");
        btnGrabarDetener.setContentAreaFilled(false);
        btnGrabarDetener.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGrabarDetener.setMaximumSize(new java.awt.Dimension(40, 30));
        btnGrabarDetener.setMinimumSize(new java.awt.Dimension(40, 30));
        btnGrabarDetener.setPreferredSize(new java.awt.Dimension(40, 30));
        btnGrabarDetener.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGrabarDetenerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGrabarDetenerMouseExited(evt);
            }
        });
        btnGrabarDetener.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGrabarDetenerActionPerformed(evt);
            }
        });

        btnPausarReanudar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/grabar/pause.png"))); // NOI18N
        btnPausarReanudar.setToolTipText("Continuar/Pausar Grabación");
        btnPausarReanudar.setContentAreaFilled(false);
        btnPausarReanudar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPausarReanudar.setMaximumSize(new java.awt.Dimension(40, 30));
        btnPausarReanudar.setMinimumSize(new java.awt.Dimension(40, 30));
        btnPausarReanudar.setPreferredSize(new java.awt.Dimension(40, 30));
        btnPausarReanudar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPausarReanudarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPausarReanudarMouseExited(evt);
            }
        });
        btnPausarReanudar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPausarReanudarActionPerformed(evt);
            }
        });

        btnConfigurarMicrofono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/grabar/settings.png"))); // NOI18N
        btnConfigurarMicrofono.setToolTipText("Configurar Micrófono");
        btnConfigurarMicrofono.setBorderPainted(false);
        btnConfigurarMicrofono.setContentAreaFilled(false);
        btnConfigurarMicrofono.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfigurarMicrofono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigurarMicrofonoActionPerformed(evt);
            }
        });

        btnLinkSILAC.setText("[CODIGO_SILAC]");
        btnLinkSILAC.setBorder(null);
        btnLinkSILAC.setBorderPainted(false);
        btnLinkSILAC.setContentAreaFilled(false);
        btnLinkSILAC.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLinkSILAC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLinkSILACActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlGrabacionComparecenciaLayout = new javax.swing.GroupLayout(pnlGrabacionComparecencia);
        pnlGrabacionComparecencia.setLayout(pnlGrabacionComparecenciaLayout);
        pnlGrabacionComparecenciaLayout.setHorizontalGroup(
            pnlGrabacionComparecenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTiempo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlGrabacionComparecenciaLayout.createSequentialGroup()
                .addGroup(pnlGrabacionComparecenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGrabacionComparecenciaLayout.createSequentialGroup()
                        .addGap(316, 316, 316)
                        .addComponent(btnConfigurarMicrofono, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnGrabarDetener, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPausarReanudar, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGrabacionComparecenciaLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(panelDocumentacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlGrabacionComparecenciaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnLinkSILAC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlGrabacionComparecenciaLayout.setVerticalGroup(
            pnlGrabacionComparecenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGrabacionComparecenciaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelDocumentacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLinkSILAC)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTiempo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGrabacionComparecenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPausarReanudar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlGrabacionComparecenciaLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(pnlGrabacionComparecenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnConfigurarMicrofono, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnGrabarDetener, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(pnlGrabacionComparecencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnlGrabacionComparecencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPausarReanudarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPausarReanudarActionPerformed
        
        laRespuesta = JOptionPane.showConfirmDialog(null, 
                """
                  ¿Desea continuar la acción seleccionada?
                """,
                "ATENCION",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);


        //se procesa la respuesta
        if (laRespuesta == JOptionPane.YES_OPTION) {
            
            telemetria.logActivity("Se hizo click en pausar/reanudar.");
            if(grabacionPausada == false){
                btnPausarReanudar.setIcon(resume);
                grabacionPausada = true;
                cronometro.setRunning(false);
                grabacion.pause();
            }else{
                btnPausarReanudar.setIcon(pause);
                grabacionPausada = false;
                cronometro.start(lblTiempo);
                grabacion.resume();
            }            
        }
        

    }//GEN-LAST:event_btnPausarReanudarActionPerformed

    private void btnGrabarDetenerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGrabarDetenerActionPerformed
        
        laRespuesta = JOptionPane.showConfirmDialog(null, 
                """
                  ¿Desea continuar?
                """,
                "ATENCION",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);


        //se procesa la respuesta
        if (laRespuesta == JOptionPane.YES_OPTION) {
            
            telemetria.logActivity("Se hizo click en grabar/detener.");
            if(grabacionIniciada == false){
                /*
                    si se reanudo una grabacion que no tenia ningun audio,
                    se solicita al usuario que ingrese el nombre del audio nuevamente.
                */
                try{
                    if("SIN AUDIOS".equals(audio.getNombreAudio())){

                        String nombreAudio = "SIN AUDIOS";

                        //Este es un ciclo insistente, rompe hasta que se ingrese un nombre valido.
                        while("SIN AUDIOS".equals(nombreAudio)){
                            nombreAudio = obtenerNombreAudio();
                        }

                        audio.setNombreAudio(nombreAudio);  
                    }
                    crearArchivosAudio();                
                    guardarDatosAudio();
                    grabacion = new GrabarAudio(audio.getArchivoAudio());
                    if(grabacion.getCanRecord() == false){forceExit();}
                }catch(Exception e){
                    return;
                }
                btnGrabarDetener.setIcon(stop);
                grabacionIniciada = true;
                cronometro.start(lblTiempo);
                grabacion.start();
                activarAnotaciones();
                btnPausarReanudar.setEnabled(true);
            }else{
                //btnGrabarDetener.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/grabar/mic.png")));
                Salir();
            }
        }
    }//GEN-LAST:event_btnGrabarDetenerActionPerformed

    private void btnActualizarMarcadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarMarcadoresActionPerformed
        String actualizacion = txtTituloMarcador.getText();
        txtTituloMarcador.setText("");
        
        int index = identificarFila();
        if(index != -1){
            anotacion.updateMarcador(index, actualizacion);
            recargarTabla();
        }
    }//GEN-LAST:event_btnActualizarMarcadoresActionPerformed

    private void btnAgregarMarcadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarMarcadoresActionPerformed
        String tiempo = cronometro.getTime();
        String titulo = txtTituloMarcador.getText();
        txtTituloMarcador.setText("");

        anotacion.createMarcador(tiempo, titulo);
        recargarTabla();
    }//GEN-LAST:event_btnAgregarMarcadoresActionPerformed

    private void btnAgregarSegmentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarSegmentosActionPerformed
        if(btnAgregarSegmentos.getText().contains("Inicio")){
            String tiempo = cronometro.getTime();
            String titulo = txtTituloSegmento.getText();
            anotacion.createInicioSegmento(tiempo, titulo);
            recargarTabla();
            
            btnAgregarSegmentos.setText("Agregar Fin");
        }else{
            String tiempo = cronometro.getTime();
            anotacion.createFinSegmento(tiempo);
            txtTituloSegmento.setText("");
            recargarTabla();
            
            btnAgregarSegmentos.setText("Agregar Inicio");
        }
    }//GEN-LAST:event_btnAgregarSegmentosActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
         //se pide confirmacion del usuario
        laRespuesta = JOptionPane.showConfirmDialog(null, 
                "¿Desea regresar a la pantalla principal?\n",
                "ATENCION",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        //se procesa la respuesta
        if (laRespuesta == JOptionPane.YES_OPTION) {
            telemetria.logActivity("Se sale de pantalla de la pantalla de comparecencias.");
            crearPantallaPrincipal();        
        }
    }//GEN-LAST:event_formWindowClosing

    private void btnActualizarSegmentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarSegmentosActionPerformed
        String actualizacion = txtTituloSegmento.getText();
        txtTituloSegmento.setText("");
        int index = identificarFila();
        if(index != -1){
            anotacion.updateSegmento(index, actualizacion);
            recargarTabla();
        }
    }//GEN-LAST:event_btnActualizarSegmentosActionPerformed

    private void btnBorrarSegmentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarSegmentosActionPerformed
        int index = identificarFila();
        if(index != -1){
            anotacion.removeSegmento(index);
            recargarTabla();
        }
    }//GEN-LAST:event_btnBorrarSegmentosActionPerformed

    private void btnBorrarMarcadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarMarcadoresActionPerformed
        int index = identificarFila();
        if(index != -1){
            anotacion.removeMarcador(index);
            recargarTabla();
        }
    }//GEN-LAST:event_btnBorrarMarcadoresActionPerformed

    private void btnBorrarAnotacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarAnotacionActionPerformed
        int index = identificarFila();
        if(index != -1){
            if(identificarTipoFila().equals("Anotación"))
            {
                anotacion.removeAnotacion(index);
            }else{
                anotacion.removeAnotacionEspecial(index);
            }
            recargarTabla();
        }
    }//GEN-LAST:event_btnBorrarAnotacionActionPerformed

    private void btnAgregarAnotacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarAnotacionesActionPerformed
        String tiempo = cronometro.getTime();
        String contenido = txtAnotaciones.getText();
        txtAnotaciones.setText("");
        if(cmbAnotacionesEspeciales.getSelectedItem().toString().contains("Anotación"))
        {
            anotacion.createAnotacion(tiempo, contenido);
        }else{
            String tipo = cmbAnotacionesEspeciales.getSelectedItem().toString();
            anotacion.createAnotacionEspecial(tiempo, tipo, contenido);
        }
        recargarTabla();
    }//GEN-LAST:event_btnAgregarAnotacionesActionPerformed

    private void btnActualizarAnotacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarAnotacionActionPerformed
        String actualizacion = txtAnotaciones.getText();
        txtAnotaciones.setText("");

        int index = identificarFila();
        if(index != -1){
            if(identificarTipoFila().equals("Anotación"))
            {
                anotacion.updateAnotacion(index, actualizacion);
            }else{
                anotacion.updateAnotacionEspecial(index, actualizacion);
            }
            recargarTabla();
        }
    }//GEN-LAST:event_btnActualizarAnotacionActionPerformed

    private void btnBorrarAnotacionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBorrarAnotacionMouseEntered
        if(btnBorrarAnotacion.isEnabled())
        setButtonActiveColor(btnBorrarAnotacion);
    }//GEN-LAST:event_btnBorrarAnotacionMouseEntered

    private void btnBorrarAnotacionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBorrarAnotacionMouseExited
        if(btnBorrarAnotacion.isEnabled())
        setButtonNormalColor(btnBorrarAnotacion);
    }//GEN-LAST:event_btnBorrarAnotacionMouseExited

    private void btnActualizarAnotacionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarAnotacionMouseEntered
        if(btnActualizarAnotacion.isEnabled())
        setButtonActiveColor(btnActualizarAnotacion);
    }//GEN-LAST:event_btnActualizarAnotacionMouseEntered

    private void btnActualizarAnotacionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarAnotacionMouseExited
        if(btnActualizarAnotacion.isEnabled())
        setButtonNormalColor(btnActualizarAnotacion);
    }//GEN-LAST:event_btnActualizarAnotacionMouseExited

    private void btnAgregarAnotacionesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarAnotacionesMouseEntered
        if(btnAgregarAnotaciones.isEnabled())
        setButtonActiveColor(btnAgregarAnotaciones);
    }//GEN-LAST:event_btnAgregarAnotacionesMouseEntered

    private void btnAgregarAnotacionesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarAnotacionesMouseExited
        if(btnAgregarAnotaciones.isEnabled())
        setButtonNormalColor(btnAgregarAnotaciones);
    }//GEN-LAST:event_btnAgregarAnotacionesMouseExited

    private void btnBorrarSegmentosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBorrarSegmentosMouseEntered
        if(btnBorrarSegmentos.isEnabled())
        setButtonActiveColor(btnBorrarSegmentos);
    }//GEN-LAST:event_btnBorrarSegmentosMouseEntered

    private void btnBorrarSegmentosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBorrarSegmentosMouseExited
        if(btnBorrarSegmentos.isEnabled())
        setButtonNormalColor(btnBorrarSegmentos);
    }//GEN-LAST:event_btnBorrarSegmentosMouseExited

    private void btnActualizarSegmentosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarSegmentosMouseEntered
        if(btnActualizarSegmentos.isEnabled())
        setButtonActiveColor(btnActualizarSegmentos);
    }//GEN-LAST:event_btnActualizarSegmentosMouseEntered

    private void btnActualizarSegmentosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarSegmentosMouseExited
        if(btnActualizarSegmentos.isEnabled())
        setButtonNormalColor(btnActualizarSegmentos);
    }//GEN-LAST:event_btnActualizarSegmentosMouseExited

    private void btnAgregarSegmentosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarSegmentosMouseEntered
        if(btnAgregarSegmentos.isEnabled())
        setButtonActiveColor(btnAgregarSegmentos);
    }//GEN-LAST:event_btnAgregarSegmentosMouseEntered

    private void btnAgregarSegmentosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarSegmentosMouseExited
        if(btnAgregarSegmentos.isEnabled())
        setButtonNormalColor(btnAgregarSegmentos);
    }//GEN-LAST:event_btnAgregarSegmentosMouseExited

    private void btnBorrarMarcadoresMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBorrarMarcadoresMouseEntered
        if(btnBorrarMarcadores.isEnabled())
        setButtonActiveColor(btnBorrarMarcadores);
    }//GEN-LAST:event_btnBorrarMarcadoresMouseEntered

    private void btnBorrarMarcadoresMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBorrarMarcadoresMouseExited
        if(btnBorrarMarcadores.isEnabled())
        setButtonNormalColor(btnBorrarMarcadores);
    }//GEN-LAST:event_btnBorrarMarcadoresMouseExited

    private void btnActualizarMarcadoresMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMarcadoresMouseEntered
        if(btnActualizarMarcadores.isEnabled())
        setButtonActiveColor(btnActualizarMarcadores);
    }//GEN-LAST:event_btnActualizarMarcadoresMouseEntered

    private void btnActualizarMarcadoresMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMarcadoresMouseExited
        if(btnActualizarMarcadores.isEnabled())
        setButtonNormalColor(btnActualizarMarcadores);
    }//GEN-LAST:event_btnActualizarMarcadoresMouseExited

    private void btnAgregarMarcadoresMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarMarcadoresMouseEntered
        if(btnAgregarMarcadores.isEnabled())
        setButtonActiveColor(btnAgregarMarcadores);
    }//GEN-LAST:event_btnAgregarMarcadoresMouseEntered

    private void btnAgregarMarcadoresMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarMarcadoresMouseExited
        if(btnAgregarMarcadores.isEnabled())
        setButtonNormalColor(btnAgregarMarcadores);
    }//GEN-LAST:event_btnAgregarMarcadoresMouseExited

    private void btnGrabarDetenerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGrabarDetenerMouseEntered
        if (  btnGrabarDetener.getIcon().toString().equals(mic.toString()) ||
              btnGrabarDetener.getIcon().toString().equals(mic_active.toString()) ){
            
            btnGrabarDetener.setIcon(mic_active);
        } else {
            btnGrabarDetener.setIcon(stop_active);
        }
    }//GEN-LAST:event_btnGrabarDetenerMouseEntered

    private void btnGrabarDetenerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGrabarDetenerMouseExited
        if (  btnGrabarDetener.getIcon().toString().equals(mic.toString()) ||
              btnGrabarDetener.getIcon().toString().equals(mic_active.toString()) ){
            
            btnGrabarDetener.setIcon(mic);
        } else {
            btnGrabarDetener.setIcon(stop);
        }
    }//GEN-LAST:event_btnGrabarDetenerMouseExited

    private void btnPausarReanudarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPausarReanudarMouseEntered
        if (  btnPausarReanudar.getIcon().toString().equals(pause.toString()) ||
              btnPausarReanudar.getIcon().toString().equals(pause_active.toString()) ){
            
            btnPausarReanudar.setIcon(pause_active);
        } else {
            btnPausarReanudar.setIcon(resume_active);
        }
    }//GEN-LAST:event_btnPausarReanudarMouseEntered

    private void btnPausarReanudarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPausarReanudarMouseExited
        if (  btnPausarReanudar.getIcon().toString().equals(pause.toString()) ||
              btnPausarReanudar.getIcon().toString().equals(pause_active.toString()) ){
            btnPausarReanudar.setIcon(pause);
        } else {
            btnPausarReanudar.setIcon(resume);
        }
    }//GEN-LAST:event_btnPausarReanudarMouseExited

    private void btnConfigurarMicrofonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigurarMicrofonoActionPerformed
        proceso.execConfigurarMicronofo();
    }//GEN-LAST:event_btnConfigurarMicrofonoActionPerformed

    private void btnLinkSILACActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLinkSILACActionPerformed
        proceso.execNavegadorWeb(comparecencia.getLinkExpediente());
    }//GEN-LAST:event_btnLinkSILACActionPerformed
    
    //codigo que se ejecuta en caso de que la aplicacion
    //se cierre en medio de una grabacion.
    private void addEmergencySave() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                grabacion.save();
                telemetria.logActivity(
                """
                La aplicacion se ha cerrado inesperadamente,
                se ha guardado el audio.
                """);
                System.exit(0);
            }
        });
    }

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
            //setear tema como el tema de windows
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Comparecencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Comparecencias().setVisible(true);
        });
    }

    private void initForm(){
        addEmergencySave();
        desactivarAnotaciones();
        btnPausarReanudar.setDisabledIcon(pause_disabled);
        btnPausarReanudar.setEnabled(false);
        tblAnotacionesModel.addColumn("Tiempo");
        tblAnotacionesModel.addColumn("Tipo"); 
        tblAnotacionesModel.addColumn("Contenido");
        tblAnotaciones.setModel(tblAnotacionesModel);      
        telemetria.logActivity("Se ingresa a pantalla de crear grabacion.");
    }
    
    private void postInitForm(){
        cargarDatosRegistroComparecencia();
        cargarDatosAnotacionesEspeciales();
        configureBtnLinkSILAC();
    }
    
    private void disposeVariables() {
        grabacion = null;
        telemetria = null;
        database = null;
        audio = null;
        cronometro = null;
        anotacion = null;
        archivo = null;
        pantallaPrincipal = null;
        tblAnotacionesModel = null;
        laRespuesta = 0;
        grabacionIniciada = null;
        grabacionPausada = null;
    }
    
    private void initVariables(){
        PANTONE2727C = new Color(82, 132, 196);
        PANTONE1595C = new Color(221, 103, 15);
        PANTONE420C = new Color(200, 200, 200);
        PANTONE422C =  new Color(157, 160, 161);
        pause = new ImageIcon(getClass().getResource("/icons/grabar/pause.png"));
        mic = new ImageIcon(getClass().getResource("/icons/grabar/mic.png"));
        stop = new ImageIcon(getClass().getResource("/icons/grabar/stop.png"));
        resume = new ImageIcon(getClass().getResource("/icons/grabar/resume.png"));
        pause_active = new ImageIcon(getClass().getResource("/icons/grabar/pause-active.png"));
        mic_active = new ImageIcon(getClass().getResource("/icons/grabar/mic-active.png"));
        stop_active = new ImageIcon(getClass().getResource("/icons/grabar/stop-active.png"));
        resume_active = new ImageIcon(getClass().getResource("/icons/grabar/resume-active.png"));
        pause_disabled = new ImageIcon(getClass().getResource("/icons/grabar/pause-disabled.png"));
        tblAnotaciones.setDefaultEditor(Object.class, null);
        telemetria = new Telemetria();
        database = new MetodosDatabase();
        proceso = new Proceso();
        audio = new Audio();
        cronometro = new Cronometro();
        anotacion =  new Anotaciones();
        archivo = new Archivos();
        tblAnotacionesModel = new DefaultTableModel();          
        grabacionIniciada = false;
        grabacionPausada = false;
    }
    
    private Color PANTONE2727C;
    private Color PANTONE1595C;
    private Color PANTONE420C;
    private Color PANTONE422C;
    private ImageIcon pause;
    private ImageIcon mic;
    private ImageIcon stop;
    private ImageIcon resume;
    private ImageIcon pause_active;
    private ImageIcon mic_active;
    private ImageIcon stop_active;
    private ImageIcon resume_active;
    private ImageIcon pause_disabled;
    private Telemetria telemetria;
    private MetodosDatabase database;
    private Proceso proceso;
    private Inspector inspector;
    private Comparecencia comparecencia;
    private Audio audio;
    private List<Object> listaPresentes;
    private GrabarAudio grabacion;
    private Cronometro cronometro;
    private Anotaciones anotacion;
    private Archivos archivo;
    private int laRespuesta;
    private Principal pantallaPrincipal;
    private DefaultTableModel tblAnotacionesModel;
    private Boolean grabacionIniciada;
    private Boolean grabacionPausada;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarAnotacion;
    private javax.swing.JButton btnActualizarMarcadores;
    private javax.swing.JButton btnActualizarSegmentos;
    private javax.swing.JButton btnAgregarAnotaciones;
    private javax.swing.JButton btnAgregarMarcadores;
    private javax.swing.JButton btnAgregarSegmentos;
    private javax.swing.JButton btnBorrarAnotacion;
    private javax.swing.JButton btnBorrarMarcadores;
    private javax.swing.JButton btnBorrarSegmentos;
    private javax.swing.JButton btnConfigurarMicrofono;
    private javax.swing.JButton btnGrabarDetener;
    private javax.swing.JButton btnLinkSILAC;
    private javax.swing.JButton btnPausarReanudar;
    private javax.swing.JComboBox<String> cmbAnotacionesEspeciales;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAnotacionesEspeciales1;
    private javax.swing.JLabel lblTiempo;
    private javax.swing.JPanel panelAnotacion;
    private javax.swing.JPanel panelDocumentacion;
    private javax.swing.JPanel panelMarcador;
    private javax.swing.JPanel panelSegmento;
    private javax.swing.JPanel pnlGrabacionComparecencia;
    private javax.swing.JTable tblAnotaciones;
    private javax.swing.JTextArea txtAnotaciones;
    private javax.swing.JTextField txtTituloMarcador;
    private javax.swing.JTextField txtTituloSegmento;
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