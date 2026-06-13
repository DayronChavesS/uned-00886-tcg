package interfaces;

import database.ConexionDatabase;
import database.MetodosDatabase;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import methods.Archivos;
import methods.Telemetria;
import models.Audio;
import models.Comparecencia;
import models.Inspector;
import models.Puesto;
import server.ConexionServidor;
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
    
    private void crearPantallaInicio(){
        pantallaInicio = new Inicio();
        pantallaInicio.setLocationRelativeTo(null);
        pantallaInicio.setVisible(true);
        disposeVariables();
        this.dispose();
    }
    
    private void crearPantallaGrabacion(){
        pantallaGrabar = new Comparecencias(inspector, comparecencia, audio);
        pantallaGrabar.setLocationRelativeTo(null);
        pantallaGrabar.setVisible(true);
        disposeVariables();
        this.dispose();
    }
    
    private void crearPantallaRegistroGrabacion(){
        pantallaRegistro = new RegistroGrabacion(inspector);
        pantallaRegistro.setLocationRelativeTo(null);
        pantallaRegistro.setVisible(true);
        disposeVariables();
        this.dispose();
    }
    
    private void crearPantallaReproducirGrabacion(){
        pantallaReproducir = new ReproducirGrabacion(inspector, comparecencia, audio);
        pantallaReproducir.setLocationRelativeTo(null);
        pantallaReproducir.setVisible(true);
        disposeVariables();
        this.dispose();
    }
    
    private void desactivarBotones(){
        btnReproducir.setEnabled(false);
        setButtonDisabledColor(btnReproducir);
        btnExportar.setEnabled(false);
        setButtonDisabledColor(btnExportar);
        btnSubir.setEnabled(false);
        setButtonDisabledColor(btnSubir);
        btnDescargar.setEnabled(false);
        setButtonDisabledColor(btnDescargar);
        
        /*estos botones deberian estar activos*/
        btnGrabar.setText("Grabar");
        btnGrabar.setEnabled(true);
        setButtonNormalColor(btnGrabar);
        btnImportar.setEnabled(true);
        setButtonNormalColor(btnImportar);
    }
    
    private void activarBotones() {
        if (filaSeleccionada[5].equals("L")) {
            activarBotononesComparecenciaLocal();
        } else {
            activarBotononesComparecenciaOnline();
        }

        if (sinInternet) {
            desactivarFuncionesOnline();
        }

        if (inspector.getPuesto() == Puesto.ASESOR_LEGAL) {
            desactivarFuncionesInspectores();
        }
        
    }
    
    private void desactivarFuncionesOnline(){
        btnSubir.setEnabled(false);
        setButtonDisabledColor(btnSubir);
        btnDescargar.setEnabled(false);
        setButtonDisabledColor(btnDescargar);
    }
    
    private void desactivarFuncionesInspectores(){
        btnSubir.setEnabled(false);
        setButtonDisabledColor(btnSubir);
        btnExportar.setEnabled(false);
        setButtonDisabledColor(btnExportar);
        btnGrabar.setEnabled(false);
        setButtonDisabledColor(btnGrabar);
    }
    
    private void activarBotononesComparecenciaLocal(){
        btnReproducir.setEnabled(true);
        setButtonNormalColor(btnReproducir);
        btnExportar.setEnabled(true);
        setButtonNormalColor(btnExportar);
        btnSubir.setEnabled(true);
        setButtonNormalColor(btnSubir);
        btnDescargar.setEnabled(false);
        setButtonDisabledColor(btnDescargar);
    }

    private void activarBotononesComparecenciaOnline(){
        btnGrabar.setText("Grabar");
        btnGrabar.setEnabled(false);
        setButtonDisabledColor(btnGrabar);
        btnReproducir.setEnabled(false);
        setButtonDisabledColor(btnReproducir);
        btnExportar.setEnabled(false);
        setButtonDisabledColor(btnExportar);
        btnImportar.setEnabled(false);
        setButtonDisabledColor(btnImportar);
        btnSubir.setEnabled(false);
        setButtonDisabledColor(btnSubir);
        btnDescargar.setEnabled(true);
        setButtonNormalColor(btnDescargar);
    }
    
    private void crearMenuExportar(){
        menu = new JPopupMenu();
        menu.add("Exportar Todo").addActionListener(e-> {
            exportarComparecencia();
        });
        menu.add("Exportar Audio").addActionListener(e-> {
            exportarSoloAudio();
        });
    }
    
    private void configurarSpinners(){
        spinnerFechaDesde.setModel(new SpinnerDateModel());
        spinnerFechaHasta.setModel(new SpinnerDateModel());
        spinnerFechaDesde.setEditor(new JSpinner.DateEditor(spinnerFechaDesde, "yyyy-MM-dd"));
        spinnerFechaHasta.setEditor(new JSpinner.DateEditor(spinnerFechaHasta, "yyyy-MM-dd"));
    }
    
    private void configurarUsuario() {
        labelNombreUsuario.setText(inspector.getPersona().getPrimerNombre()
                + " " + inspector.getPersona().getSegundoNombre()
                + " " + inspector.getPersona().getPrimerApellido()
                + " " + inspector.getPersona().getSegundoApellido());
        labelTipoUsuario.setText(inspector.getPuesto().toString()+":");
        
        if(inspector.getPuesto().toString().contains("Asesor(a)")){
            desactivarFuncionesInspectores();
        }
    }
    
    private void forceExit(){
        JOptionPane.showMessageDialog(null, 
            """
            Lo sentimos, se ha perdido la conexion con el SQLITE.
            Imposible continuar.
            """,
            "ERROR FATAL",
            JOptionPane.ERROR_MESSAGE);
            
        telemetria.logActivity("Salida forzada del programa.");
        System.exit(0);
    }
    
    private void crearImportadorDeArchivos() {
        telemetria.logActivity("Se creo el importador de archivos.");
        importFileChooser.setDialogTitle("Seleccione la comparecencia que desea importar.");
        importFileChooser.setMultiSelectionEnabled(false);
        importFileChooser.setApproveButtonText("Importar");
        importFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        importFileChooser.setAcceptAllFileFilterUsed(false);
        importFileChooser.addChoosableFileFilter(filtroMTSS);
        
        result = importFileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            telemetria.logActivity("Se selecciono un archivo a importar.");    
            archivoSeleccionado = importFileChooser.getSelectedFile().getAbsolutePath();
        }else{
            telemetria.logActivity("Se cancelo la seleccion.");
        }
    }
    
    private void importarComparecencia(){
        archivoSeleccionado = "";
        crearImportadorDeArchivos();
        if(!"".equals(archivoSeleccionado)){
            String comparecenciaPath = archivo.importarComparecencia(archivoSeleccionado);
            database.importarLaComparecencia(comparecenciaPath);
            listarDatos();
        }
    }
     
    private void crearExportadorDeArchivos() {
        telemetria.logActivity("Seleccione el directorio donde desea exportar el archivo.");
        exportFileChooser.setDialogTitle("Seleccione el directorio donde desea exportar la comparecencia.");
        exportFileChooser.setMultiSelectionEnabled(false);
        exportFileChooser.setApproveButtonText("Exportar");
        exportFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        result = exportFileChooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            telemetria.logActivity("Se selecciono un directorio a exportar.");    
            directorioSeleccionado = exportFileChooser.getSelectedFile().getAbsolutePath();
        }else{
            telemetria.logActivity("Se cancelo la seleccion.");
        }
    }
    
    private void exportarComparecencia(){
        directorioSeleccionado = "";
        crearExportadorDeArchivos();
        
        if(!"".equals(directorioSeleccionado)){
            try{
            obtenerDatosFila();
            String pathComparecencia = methods.Global.appConfig.getProperty("comparecencias_path") 
                                       + comparecencia.getCodigoSILAC() + System.getProperty("file.separator");

            archivo.deleteFileIfExist(pathComparecencia + "data.db");
            database.createExportDataBase(pathComparecencia);

            database.exportComparecencia(comparecencia.getIdComparecencia());

            archivo.exportarComparecencia(pathComparecencia, 
                                          filaSeleccionada[1], 
                                           directorioSeleccionado);

            }catch(Exception e){
                telemetria.logActivity("Error al exportar comparecencia.");
                telemetria.logException(e);
            }
        }
    }
    
    private void exportarSoloAudio(){
        directorioSeleccionado = "";
        crearExportadorDeArchivos();

        if(!"".equals(directorioSeleccionado)){
            try{
                obtenerDatosFila();

                List<Integer> audioIDs = database.getAudioXComparecencia(comparecencia.getIdComparecencia());

                if(audioIDs.size() == 1){
                    audio = database.createObjectAudio(audioIDs.get(0));
                     archivo.exportarAudio(audio.getPathArchivoAudio(), 
                                              directorioSeleccionado, 
                                              audio.getNombreAudio());
                }else{
                    int index = 1;
                    for (Integer id : audioIDs) {
                        if(id != 0){
                            audio = database.createObjectAudio(id);
                            archivo.exportarAudio(audio.getPathArchivoAudio(), 
                                                  directorioSeleccionado, 
                                                  audio.getNombreAudio()+"("+index+")");
                            index++;
                        }
                    } 
                }
            }catch(Exception e){
                telemetria.logActivity("Error al exportar el audio de la comparecencia.");
                telemetria.logException(e);
            }
        }
    }
    
    private void realizarBusqueda(){
        
        laBusqueda = txtBuscarGrabacion.getText();
        //ESTO NO SE PUEDE POR EL FILTRO DE FECHA
        /*if("".equals(laBusqueda)){
            listarDatos();
            return;
        }*/
        
        if(laBusqueda.equals("Buscar comparecencia...")){
            laBusqueda = "";
        }
        
        tipoBusqueda = cmbTipoBusqueda.getSelectedItem().toString();
        if(tipoBusqueda.equals("Anotación")){
            buscarAnotaciones(laBusqueda);
            return;
        }
        
        fechaDesde = (java.util.Date)spinnerFechaDesde.getValue();
        fechaHasta = (java.util.Date)spinnerFechaHasta.getValue();
        ascendente = rdbtnAlfabeticoA_Z.isSelected();
        descendente = rdbtnAlfabeticoA_Z.isSelected();
        
        tblPrincipalLocalModel.setRowCount(0);
        tblComparecencias.setModel(database.buscarDatosTabla(tblPrincipalLocalModel,
                                  laBusqueda, tipoBusqueda,
                                  fechaDesde, fechaHasta, 
                                  ascendente, descendente));
        
        tblComparecencias.revalidate();
        tblComparecencias.repaint();
    }
    
    private void buscarAnotaciones(String laBusqueda){
        
        if("".equals(laBusqueda)){
            listarDatos();
            return;
        }
        
        List<String> listaPaths = database.getAllAnotacionesPath();
        List<String> listaCoincidencias = archivo.buscarLaAnotacion(laBusqueda, listaPaths);
        
        if(!listaCoincidencias.isEmpty()){
            tblPrincipalLocalModel.setRowCount(0);

            tblComparecencias.setModel(
                    database.buscarDatosTablaAnotaciones(tblPrincipalLocalModel, 
                            listaCoincidencias));
  
            tblComparecencias.revalidate();
            tblComparecencias.repaint();
        }
    }
    
    private void listarDatos(){
        try {
            tblPrincipalLocalModel.setRowCount(0); //elimina los datos
            tblPrincipalOnlineModel.setRowCount(0);
            tblPrincipalFullModel.setRowCount(0);
            
            if(sinInternet){
                tblPrincipalLocalModel = database.getDatosTablaPantallaPrincipal(tblPrincipalLocalModel);
                tblComparecencias.setModel(tblPrincipalLocalModel);
            }else{
                
                //se obtiene el modelo de datos local
                //se obtiene el modelo de datos en linea
                //se crea un nuevo modelo juntando ambos
                tblPrincipalLocalModel = database.getDatosTablaPantallaPrincipal(tblPrincipalLocalModel);
                tblPrincipalOnlineModel = servidor.getComparecenciasOnline(tblPrincipalOnlineModel);
                tblPrincipalFullModel = unirTablas(tblPrincipalFullModel);
                tblComparecencias.setModel(tblPrincipalFullModel);
            }
            configurarTamañoColumna();
            tblComparecencias.revalidate();
            tblComparecencias.repaint();
        } catch (Exception ex) {
            telemetria.logException(ex);
        }
    }
    
    private DefaultTableModel unirTablas(DefaultTableModel tblPrincipalFullModel){
                
        for(Vector rowData : tblPrincipalLocalModel.getDataVector()){
            tblPrincipalFullModel.addRow(rowData);
        }
        
        for(Vector rowData : tblPrincipalOnlineModel.getDataVector()){
            tblPrincipalFullModel.addRow(rowData);
        }
        
        return tblPrincipalFullModel;
    }
    
    private void configurarTablaLocal(){
        tblPrincipalLocalModel.addColumn("SILAC");
        tblPrincipalLocalModel.addColumn("Nombre");
        tblPrincipalLocalModel.addColumn("Tipo");
        tblPrincipalLocalModel.addColumn("Ubicación");
        tblPrincipalLocalModel.addColumn("Fecha");
        tblPrincipalLocalModel.addColumn("");
    }
    
    private void configurarTablaOnline(){
        tblPrincipalOnlineModel.addColumn("SILAC");
        tblPrincipalOnlineModel.addColumn("Nombre");
        tblPrincipalOnlineModel.addColumn("Tipo");
        tblPrincipalOnlineModel.addColumn("Ubicación");
        tblPrincipalOnlineModel.addColumn("Fecha");
        tblPrincipalOnlineModel.addColumn("");
    }
    
    private void configurarTablaFull(){
        tblPrincipalFullModel.addColumn("SILAC");
        tblPrincipalFullModel.addColumn("Nombre");
        tblPrincipalFullModel.addColumn("Tipo");
        tblPrincipalFullModel.addColumn("Ubicación");
        tblPrincipalFullModel.addColumn("Fecha");
        tblPrincipalFullModel.addColumn("");
    }
    
    private void configurarTamañoColumna(){
        tblComparecencias.getColumnModel().getColumn(0).setPreferredWidth(85);  //SILAC
        tblComparecencias.getColumnModel().getColumn(1).setPreferredWidth(195); //NOMBRE
        tblComparecencias.getColumnModel().getColumn(2).setPreferredWidth(185); //TIPO CASO
        tblComparecencias.getColumnModel().getColumn(3).setPreferredWidth(191); //UBICACION
        tblComparecencias.getColumnModel().getColumn(4).setPreferredWidth(65);  //FECHA
        tblComparecencias.getColumnModel().getColumn(5).setPreferredWidth(16);  //ORIGEN
    }
    
    private void obtenerDatosFila(){
        filaSeleccionada = new String[6];
        int rowSelected = tblComparecencias.getSelectedRow();
        filaSeleccionada[0] = tblComparecencias.getValueAt(rowSelected, 0).toString(); //silac
        filaSeleccionada[1] = tblComparecencias.getValueAt(rowSelected, 1).toString(); //nombre
        filaSeleccionada[2] = tblComparecencias.getValueAt(rowSelected, 2).toString(); //tipo
        filaSeleccionada[3] = tblComparecencias.getValueAt(rowSelected, 3).toString(); //ubicacion
        filaSeleccionada[4] = tblComparecencias.getValueAt(rowSelected, 4).toString(); //fecha
        filaSeleccionada[5] = tblComparecencias.getValueAt(rowSelected, 5).toString(); //origen
        try{
            if(filaSeleccionada[5].equals("L")){
                int idComparecencia = database.getIdComparecencia(filaSeleccionada[0]);
                comparecencia = database.createObjectComparecencia(idComparecencia);
            }else{
                //se obtiene el codigo silac para posterior descarga
                comparecencia.setCodigoSILAC(filaSeleccionada[0]);
            }
        }
        catch(Exception e){
            telemetria.logActivity("Error al obtener los datos de la fila seleccionada.");
            telemetria.logException(e);
        }
    }
    
    private void crearOtroAudio() throws Exception{
        obtenerDatosFila();
        audio = new Audio();
        audio.setNombreAudio(filaSeleccionada[1]);
    }
    
    private void cargarAudioExistente(){
        try {
            obtenerDatosFila();
            listaAudios = database.getAudioXComparecencia(comparecencia.getIdComparecencia());
            if (listaAudios.size() > 1) {
                int idAudio = crearDialogoSeleccionAudio();
                audio = database.createObjectAudio(idAudio);
            } else {
                audio = database.createObjectAudio(listaAudios.get(0));
            }
        } catch (Exception e){
            telemetria.logActivity("Error al obtener los datos del audio.");
            telemetria.logException(e);
        }
    }
    
    private int crearDialogoSeleccionAudio(){
        try {
            
            Integer[] indexList = new Integer[listaAudios.size()];
            for(int i = 0; i < listaAudios.size(); i++){
                indexList[i] = i+1;
            }
            
            int theIndex = JOptionPane.showOptionDialog(null, "Seleccione el audio que desea reproducir:", "Selección de Audio", 
                                                              0, 3, null, indexList, null);
            
            return listaAudios.get(theIndex);
        } catch (Exception e) {
            telemetria.logActivity("Error al crear el dialogo de seleccion.");
            telemetria.logException(e);
        }
        return -1;
    }
    
    private void verificarConexionSQLITE() {
        conexionDatabase.abrirConexion();
        if (conexionDatabase.sqliteConexion == null) {
            labelEstadoSQLITE.setText("NO DISPONIBLE");
            labelEstadoSQLITE.setForeground(PANTONE1595C);
            forceExit();
        } else {
            labelEstadoSQLITE.setText("DISPONIBLE");
            labelEstadoSQLITE.setForeground(PANTONE2727C);
        }
        conexionDatabase.cerrarConexionSQLITE();
    }

    private void verificarConexionServidor() {
        if (!servidor.verificarEstadoServidor()) {
            labelEstadoServidor.setText("NO DISPONIBLE");
            labelEstadoServidor.setForeground(PANTONE1595C);
            sinInternet = true;
            desactivarFuncionesOnline();
        } else {
            labelEstadoServidor.setText("DISPONIBLE");
            labelEstadoServidor.setForeground(PANTONE2727C);
        }
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

        importFileChooser = new javax.swing.JFileChooser();
        exportFileChooser = new javax.swing.JFileChooser();
        btnGroupAlfabetico = new javax.swing.ButtonGroup();
        pnlFondo = new javax.swing.JPanel();
        panelBusqueda = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        txtBuscarGrabacion = new javax.swing.JTextField();
        btnBuscarComparecencia = new javax.swing.JButton();
        panelBusquedaAvanzada = new javax.swing.JPanel();
        pnlBuscarPor = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbTipoBusqueda = new javax.swing.JComboBox<>();
        pnlFiltrar = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        spinnerFechaDesde = new javax.swing.JSpinner();
        spinnerFechaHasta = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        pnlOrdenar = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        rdbtnAlfabeticoA_Z = new javax.swing.JRadioButton();
        rdbtnAlfabeticoZ_A = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblComparecencias = new javax.swing.JTable();
        btnReproducir = new javax.swing.JButton();
        btnGrabar = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();
        btnImportar = new javax.swing.JButton();
        btnSubir = new javax.swing.JButton();
        btnDescargar = new javax.swing.JButton();
        pnlEstado = new javax.swing.JPanel();
        pnlUsuario = new javax.swing.JPanel();
        labelTipoUsuario = new javax.swing.JLabel();
        labelNombreUsuario = new javax.swing.JLabel();
        pnlLocal = new javax.swing.JPanel();
        labelLOCAL = new javax.swing.JLabel();
        labelEstadoSQLITE = new javax.swing.JLabel();
        pnlServidor = new javax.swing.JPanel();
        labelSERVIDOR = new javax.swing.JLabel();
        labelEstadoServidor = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("SGC | Pantalla Principal");
        setIconImage(new ImageIcon(getClass().getResource("/favicon.png")).getImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlFondo.setBackground(new java.awt.Color(255, 255, 255));
        pnlFondo.setMaximumSize(new java.awt.Dimension(772, 547));
        pnlFondo.setMinimumSize(new java.awt.Dimension(772, 547));
        pnlFondo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlFondoMouseClicked(evt);
            }
        });

        panelBusqueda.setBackground(new java.awt.Color(255, 255, 255));
        panelBusqueda.setMaximumSize(new java.awt.Dimension(772, 237));
        panelBusqueda.setMinimumSize(new java.awt.Dimension(772, 237));
        panelBusqueda.setPreferredSize(new java.awt.Dimension(772, 237));

        lblTitulo.setBackground(new java.awt.Color(255, 255, 255));
        lblTitulo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(82, 132, 196));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Sistema de Gestión de Comparecencias del MTSS");

        txtBuscarGrabacion.setForeground(new java.awt.Color(153, 153, 153));
        txtBuscarGrabacion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtBuscarGrabacion.setText("Buscar comparecencia...");
        txtBuscarGrabacion.setBorder(new javax.swing.border.LineBorder(java.awt.SystemColor.controlShadow, 1, true));
        txtBuscarGrabacion.setCaretPosition(0);
        txtBuscarGrabacion.setMargin(new java.awt.Insets(5, 30, 5, 5));
        txtBuscarGrabacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBuscarGrabacionMouseClicked(evt);
            }
        });
        txtBuscarGrabacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarGrabacionKeyReleased(evt);
            }
        });

        btnBuscarComparecencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/principal/search.png"))); // NOI18N
        btnBuscarComparecencia.setBorderPainted(false);
        btnBuscarComparecencia.setContentAreaFilled(false);
        btnBuscarComparecencia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscarComparecencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarComparecenciaActionPerformed(evt);
            }
        });

        panelBusquedaAvanzada.setBackground(new java.awt.Color(255, 255, 255));

        pnlBuscarPor.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Buscar por...");

        cmbTipoBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Código SILAC", "Nombre Comparecencia", "Tipo Caso", "Ubicación", "Fecha", "Anotación" }));
        cmbTipoBusqueda.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.controlHighlight));
        cmbTipoBusqueda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbTipoBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoBusquedaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBuscarPorLayout = new javax.swing.GroupLayout(pnlBuscarPor);
        pnlBuscarPor.setLayout(pnlBuscarPorLayout);
        pnlBuscarPorLayout.setHorizontalGroup(
            pnlBuscarPorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBuscarPorLayout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addGroup(pnlBuscarPorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(40, 40, 40))
        );
        pnlBuscarPorLayout.setVerticalGroup(
            pnlBuscarPorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarPorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbTipoBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlFiltrar.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("Filtrar por fecha");

        spinnerFechaDesde.setModel(new javax.swing.SpinnerDateModel());
        spinnerFechaDesde.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        spinnerFechaHasta.setModel(new javax.swing.SpinnerDateModel());
        spinnerFechaHasta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setText("Desde");

        jLabel5.setText("Hasta");

        javax.swing.GroupLayout pnlFiltrarLayout = new javax.swing.GroupLayout(pnlFiltrar);
        pnlFiltrar.setLayout(pnlFiltrarLayout);
        pnlFiltrarLayout.setHorizontalGroup(
            pnlFiltrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFiltrarLayout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(pnlFiltrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(spinnerFechaDesde)
                    .addComponent(spinnerFechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        pnlFiltrarLayout.setVerticalGroup(
            pnlFiltrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFiltrarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spinnerFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spinnerFechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        pnlOrdenar.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setText("Ordenar por");

        rdbtnAlfabeticoA_Z.setBackground(new java.awt.Color(255, 255, 255));
        rdbtnAlfabeticoA_Z.setText("Alfabetico de A-Z");

        rdbtnAlfabeticoZ_A.setBackground(new java.awt.Color(255, 255, 255));
        rdbtnAlfabeticoZ_A.setText("Alfabetico de Z-A");

        javax.swing.GroupLayout pnlOrdenarLayout = new javax.swing.GroupLayout(pnlOrdenar);
        pnlOrdenar.setLayout(pnlOrdenarLayout);
        pnlOrdenarLayout.setHorizontalGroup(
            pnlOrdenarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOrdenarLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(pnlOrdenarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdbtnAlfabeticoZ_A)
                    .addComponent(rdbtnAlfabeticoA_Z)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlOrdenarLayout.setVerticalGroup(
            pnlOrdenarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOrdenarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdbtnAlfabeticoA_Z)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdbtnAlfabeticoZ_A)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelBusquedaAvanzadaLayout = new javax.swing.GroupLayout(panelBusquedaAvanzada);
        panelBusquedaAvanzada.setLayout(panelBusquedaAvanzadaLayout);
        panelBusquedaAvanzadaLayout.setHorizontalGroup(
            panelBusquedaAvanzadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBusquedaAvanzadaLayout.createSequentialGroup()
                .addComponent(pnlBuscarPor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlOrdenar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBusquedaAvanzadaLayout.setVerticalGroup(
            panelBusquedaAvanzadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBusquedaAvanzadaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBusquedaAvanzadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlOrdenar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlFiltrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlBuscarPor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelBusquedaLayout = new javax.swing.GroupLayout(panelBusqueda);
        panelBusqueda.setLayout(panelBusquedaLayout);
        panelBusquedaLayout.setHorizontalGroup(
            panelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelBusquedaAvanzada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelBusquedaLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(txtBuscarGrabacion, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBuscarComparecencia)
                .addContainerGap(75, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelBusquedaLayout.setVerticalGroup(
            panelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addGap(18, 18, 18)
                .addGroup(panelBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnBuscarComparecencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtBuscarGrabacion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(panelBusquedaAvanzada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setBackground(java.awt.SystemColor.control);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.controlHighlight));
        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setToolTipText("");
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(750, 140));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(750, 140));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(750, 140));

        tblComparecencias.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.controlHighlight));
        tblComparecencias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblComparecencias.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblComparecencias.setAutoscrolls(false);
        tblComparecencias.setGridColor(new java.awt.Color(255, 255, 255));
        tblComparecencias.setSelectionBackground(java.awt.SystemColor.textHighlight);
        tblComparecencias.setSelectionForeground(java.awt.SystemColor.textHighlightText);
        tblComparecencias.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblComparecencias.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblComparecencias.setShowHorizontalLines(true);
        tblComparecencias.getTableHeader().setReorderingAllowed(false);
        tblComparecencias.setUpdateSelectionOnSort(false);
        tblComparecencias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblComparecenciasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblComparecencias);

        btnReproducir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/principal/play.png"))); // NOI18N
        btnReproducir.setText("Reproducir");
        btnReproducir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReproducir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnReproducir.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnReproducir.setMaximumSize(new java.awt.Dimension(100, 40));
        btnReproducir.setMinimumSize(new java.awt.Dimension(100, 40));
        btnReproducir.setPreferredSize(new java.awt.Dimension(120, 40));
        btnReproducir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnReproducirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnReproducirMouseExited(evt);
            }
        });
        btnReproducir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReproducirActionPerformed(evt);
            }
        });

        btnGrabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/principal/record.png"))); // NOI18N
        btnGrabar.setText("Grabar");
        btnGrabar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGrabar.setHideActionText(true);
        btnGrabar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGrabar.setMaximumSize(new java.awt.Dimension(120, 40));
        btnGrabar.setMinimumSize(new java.awt.Dimension(120, 40));
        btnGrabar.setPreferredSize(new java.awt.Dimension(120, 40));
        btnGrabar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGrabarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGrabarMouseExited(evt);
            }
        });
        btnGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGrabarActionPerformed(evt);
            }
        });

        btnExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/principal/export.png"))); // NOI18N
        btnExportar.setText("Exportar");
        btnExportar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExportar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnExportar.setMaximumSize(new java.awt.Dimension(120, 40));
        btnExportar.setMinimumSize(new java.awt.Dimension(120, 40));
        btnExportar.setPreferredSize(new java.awt.Dimension(120, 40));
        btnExportar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExportarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExportarMouseExited(evt);
            }
        });
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        btnImportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/principal/import.png"))); // NOI18N
        btnImportar.setText("Importar");
        btnImportar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImportar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnImportar.setMaximumSize(new java.awt.Dimension(120, 40));
        btnImportar.setMinimumSize(new java.awt.Dimension(120, 40));
        btnImportar.setPreferredSize(new java.awt.Dimension(120, 40));
        btnImportar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnImportarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnImportarMouseExited(evt);
            }
        });
        btnImportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportarActionPerformed(evt);
            }
        });

        btnSubir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/principal/upload.png"))); // NOI18N
        btnSubir.setText("Subir");
        btnSubir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSubir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSubir.setMaximumSize(new java.awt.Dimension(120, 40));
        btnSubir.setMinimumSize(new java.awt.Dimension(120, 40));
        btnSubir.setPreferredSize(new java.awt.Dimension(120, 40));
        btnSubir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSubirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSubirMouseExited(evt);
            }
        });
        btnSubir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubirActionPerformed(evt);
            }
        });

        btnDescargar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/principal/download.png"))); // NOI18N
        btnDescargar.setText("Descargar");
        btnDescargar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDescargar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDescargar.setMaximumSize(new java.awt.Dimension(120, 40));
        btnDescargar.setMinimumSize(new java.awt.Dimension(120, 40));
        btnDescargar.setPreferredSize(new java.awt.Dimension(120, 40));
        btnDescargar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDescargarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDescargarMouseExited(evt);
            }
        });
        btnDescargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescargarActionPerformed(evt);
            }
        });

        pnlEstado.setBackground(new java.awt.Color(200, 200, 200));
        pnlEstado.setMaximumSize(new java.awt.Dimension(784, 18));
        pnlEstado.setMinimumSize(new java.awt.Dimension(784, 18));

        pnlUsuario.setBackground(new java.awt.Color(200, 200, 200));
        pnlUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(111, 114, 113)));
        pnlUsuario.setMaximumSize(new java.awt.Dimension(475, 18));
        pnlUsuario.setMinimumSize(new java.awt.Dimension(475, 18));

        labelTipoUsuario.setBackground(new java.awt.Color(255, 255, 255));
        labelTipoUsuario.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelTipoUsuario.setText("TIPO_USUARIO");
        labelTipoUsuario.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        labelNombreUsuario.setBackground(new java.awt.Color(255, 255, 255));
        labelNombreUsuario.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelNombreUsuario.setText("NOMBRE_USUARIO");
        labelNombreUsuario.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout pnlUsuarioLayout = new javax.swing.GroupLayout(pnlUsuario);
        pnlUsuario.setLayout(pnlUsuarioLayout);
        pnlUsuarioLayout.setHorizontalGroup(
            pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTipoUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelNombreUsuario)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlUsuarioLayout.setVerticalGroup(
            pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUsuarioLayout.createSequentialGroup()
                .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTipoUsuario)
                    .addComponent(labelNombreUsuario))
                .addGap(0, 0, 0))
        );

        pnlLocal.setBackground(new java.awt.Color(200, 200, 200));
        pnlLocal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(111, 114, 113)));
        pnlLocal.setMaximumSize(new java.awt.Dimension(148, 18));
        pnlLocal.setMinimumSize(new java.awt.Dimension(148, 18));
        pnlLocal.setPreferredSize(new java.awt.Dimension(148, 18));

        labelLOCAL.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelLOCAL.setText("LOCAL:");
        labelLOCAL.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        labelEstadoSQLITE.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelEstadoSQLITE.setText("NO_DISPONIBLE");
        labelEstadoSQLITE.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout pnlLocalLayout = new javax.swing.GroupLayout(pnlLocal);
        pnlLocal.setLayout(pnlLocalLayout);
        pnlLocalLayout.setHorizontalGroup(
            pnlLocalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLocalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelLOCAL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelEstadoSQLITE)
                .addContainerGap(7, Short.MAX_VALUE))
        );
        pnlLocalLayout.setVerticalGroup(
            pnlLocalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlLocalLayout.createSequentialGroup()
                .addGroup(pnlLocalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelLOCAL)
                    .addComponent(labelEstadoSQLITE))
                .addGap(0, 0, 0))
        );

        pnlServidor.setBackground(new java.awt.Color(200, 200, 200));
        pnlServidor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(111, 114, 113)));
        pnlServidor.setMaximumSize(new java.awt.Dimension(161, 18));
        pnlServidor.setMinimumSize(new java.awt.Dimension(161, 18));
        pnlServidor.setPreferredSize(new java.awt.Dimension(161, 18));

        labelSERVIDOR.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelSERVIDOR.setText("SERVIDOR:");
        labelSERVIDOR.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        labelEstadoServidor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelEstadoServidor.setText("NO_DISPONIBLE");
        labelEstadoServidor.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout pnlServidorLayout = new javax.swing.GroupLayout(pnlServidor);
        pnlServidor.setLayout(pnlServidorLayout);
        pnlServidorLayout.setHorizontalGroup(
            pnlServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlServidorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelSERVIDOR)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelEstadoServidor)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlServidorLayout.setVerticalGroup(
            pnlServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlServidorLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnlServidorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSERVIDOR)
                    .addComponent(labelEstadoServidor)))
        );

        javax.swing.GroupLayout pnlEstadoLayout = new javax.swing.GroupLayout(pnlEstado);
        pnlEstado.setLayout(pnlEstadoLayout);
        pnlEstadoLayout.setHorizontalGroup(
            pnlEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEstadoLayout.createSequentialGroup()
                .addComponent(pnlUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlEstadoLayout.setVerticalGroup(
            pnlEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlLocal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(pnlUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(pnlServidor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout pnlFondoLayout = new javax.swing.GroupLayout(pnlFondo);
        pnlFondo.setLayout(pnlFondoLayout);
        pnlFondoLayout.setHorizontalGroup(
            pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(pnlFondoLayout.createSequentialGroup()
                .addGap(189, 189, 189)
                .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFondoLayout.createSequentialGroup()
                        .addComponent(btnGrabar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnImportar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDescargar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlFondoLayout.createSequentialGroup()
                        .addComponent(btnReproducir, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSubir, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlFondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(pnlFondoLayout.createSequentialGroup()
                .addComponent(panelBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlFondoLayout.setVerticalGroup(
            pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFondoLayout.createSequentialGroup()
                .addComponent(panelBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSubir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReproducir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImportar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDescargar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGrabar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(pnlEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGrabarActionPerformed
        telemetria.logActivity("Se hizo click en grabar.");
        
        try{
        if(btnGrabar.getText().equals("Grabar")){
            crearPantallaRegistroGrabacion();
        }else{
            crearOtroAudio();
            crearPantallaGrabacion();
        }
        }catch(Exception e){
            telemetria.logActivity("Error al grabar.");
            telemetria.logException(e);
        }
    }//GEN-LAST:event_btnGrabarActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        telemetria.logActivity("Se hizo click en exportar grabacion.");
        menu.show(btnExportar, btnExportar.getWidth()/2, btnExportar.getHeight()/2);
    }//GEN-LAST:event_btnExportarActionPerformed

    private void btnSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubirActionPerformed
        telemetria.logActivity("Se hizo click en subir grabacion.");
        obtenerDatosFila();
        
        final LoadingScreen upload = new LoadingScreen(1, comparecencia, inspector);
        
        new Thread(() -> {
            try {

                while (true) {

                    if (upload.finProceso) {
                        upload.setVisible(false);
                        upload.dispose();
                        listarDatos();
                        break;
                    }

                    Thread.sleep(1000);
                }
            } catch (Exception e) {
            }
        }).start();
    }//GEN-LAST:event_btnSubirActionPerformed

    private void btnDescargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescargarActionPerformed
        telemetria.logActivity("Se hizo click en descargar grabacion.");
        obtenerDatosFila();
        final LoadingScreen download = new LoadingScreen(2, comparecencia, inspector);

        new Thread(() -> {
            try {

                while (true) {

                    if (download.finProceso) {
                        download.setVisible(false);
                        download.dispose();
                        listarDatos();
                        break;
                    }

                    Thread.sleep(1000);
                }
            } catch (Exception e) {
            }
        }).start();

    }//GEN-LAST:event_btnDescargarActionPerformed

    private void btnImportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportarActionPerformed
        telemetria.logActivity("Se hizo click en importar grabacion.");
        importarComparecencia();
    }//GEN-LAST:event_btnImportarActionPerformed

     private void btnBuscarComparecenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarComparecenciaActionPerformed
        telemetria.logActivity("Se hizo click en buscar comparecencia.");
        realizarBusqueda();
    }//GEN-LAST:event_txtBuscarComparecenciaActionPerformed

    private void txtBuscarGrabacionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarGrabacionKeyReleased
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
           realizarBusqueda();
        }
    }//GEN-LAST:event_txtBuscarGrabacionKeyReleased

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        laRespuesta = JOptionPane.showConfirmDialog(null,
                "¿Desea cerrar la sesión?",
                "ATENCION",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        //se procesa la respuesta
        if (laRespuesta == JOptionPane.YES_OPTION) {
            telemetria.logActivity("Se sale de pantalla principal.");
            database.logoutInspector(inspector.getPersona().getIdPersona());
            crearPantallaInicio();
        }
    }//GEN-LAST:event_formWindowClosing

    private void btnReproducirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReproducirActionPerformed
        telemetria.logActivity("Se hizo click en reproducir comparecencia.");
        cargarAudioExistente();
        if(audio != null){
            crearPantallaReproducirGrabacion();
        }
    }//GEN-LAST:event_btnReproducirActionPerformed

    private void txtBuscarGrabacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarGrabacionMouseClicked
        if(txtBuscarGrabacion.getText().contains("Buscar")){
            txtBuscarGrabacion.setText("");
        }
        txtBuscarGrabacion.setForeground(Color.BLACK);
    }//GEN-LAST:event_txtBuscarGrabacionMouseClicked

    private void tblComparecenciasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblComparecenciasMouseClicked
        if(!tblComparecencias.getSelectionModel().isSelectionEmpty()){
            obtenerDatosFila();
            btnGrabar.setText("Reanudar");
            
            if(!"SIN AUDIOS".equals(filaSeleccionada[1])){
                activarBotones();
            }
        }
    }//GEN-LAST:event_tblComparecenciasMouseClicked

    private void pnlFondoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlFondoMouseClicked
        tblComparecencias.getSelectionModel().clearSelection();
        desactivarBotones();
    }//GEN-LAST:event_pnlFondoMouseClicked

    private void cmbTipoBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoBusquedaActionPerformed
        if(cmbTipoBusqueda.getSelectedItem().toString().equals("Anotación")){
            spinnerFechaDesde.setEnabled(false);
            spinnerFechaHasta.setEnabled(false);
            rdbtnAlfabeticoA_Z.setEnabled(false);
            rdbtnAlfabeticoZ_A.setEnabled(false);
        }else{
            spinnerFechaDesde.setEnabled(true);
            spinnerFechaHasta.setEnabled(true);
            rdbtnAlfabeticoA_Z.setEnabled(true);
            rdbtnAlfabeticoZ_A.setEnabled(true);
        }
    }//GEN-LAST:event_cmbTipoBusquedaActionPerformed

    private void btnReproducirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReproducirMouseEntered
        if(btnReproducir.isEnabled())
        setButtonActiveColor(btnReproducir);
    }//GEN-LAST:event_btnReproducirMouseEntered

    private void btnReproducirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReproducirMouseExited
        if(btnReproducir.isEnabled())
        setButtonNormalColor(btnReproducir);
    }//GEN-LAST:event_btnReproducirMouseExited

    private void btnGrabarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGrabarMouseEntered
        if(btnGrabar.isEnabled())
        setButtonActiveColor(btnGrabar);
    }//GEN-LAST:event_btnGrabarMouseEntered

    private void btnGrabarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGrabarMouseExited
        if(btnGrabar.isEnabled())
        setButtonNormalColor(btnGrabar);
    }//GEN-LAST:event_btnGrabarMouseExited

    private void btnExportarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExportarMouseEntered
        if(btnExportar.isEnabled())
        setButtonActiveColor(btnExportar);
    }//GEN-LAST:event_btnExportarMouseEntered

    private void btnExportarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExportarMouseExited
        if(btnExportar.isEnabled())
        setButtonNormalColor(btnExportar);
    }//GEN-LAST:event_btnExportarMouseExited

    private void btnSubirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubirMouseEntered
        if(btnSubir.isEnabled())
        setButtonActiveColor(btnSubir);
    }//GEN-LAST:event_btnSubirMouseEntered

    private void btnSubirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubirMouseExited
        if(btnSubir.isEnabled())
        setButtonNormalColor(btnSubir);
    }//GEN-LAST:event_btnSubirMouseExited

    private void btnDescargarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDescargarMouseEntered
        if(btnDescargar.isEnabled())
        setButtonActiveColor(btnDescargar);
    }//GEN-LAST:event_btnDescargarMouseEntered

    private void btnDescargarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDescargarMouseExited
        if(btnDescargar.isEnabled())
        setButtonNormalColor(btnDescargar);
    }//GEN-LAST:event_btnDescargarMouseExited

    private void btnImportarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImportarMouseEntered
        if(btnImportar.isEnabled())
        setButtonActiveColor(btnImportar);
    }//GEN-LAST:event_btnImportarMouseEntered

    private void btnImportarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImportarMouseExited
        if(btnImportar.isEnabled())
        setButtonNormalColor(btnImportar);
    }//GEN-LAST:event_btnImportarMouseExited
    
    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
        
        }

        java.awt.EventQueue.invokeLater(() -> {
            new Principal().setVisible(true);
        });
    }
        
    private void initForm(){
        telemetria.logActivity("Se ingresa a pantalla principal.");
        setButtonNormalColor(btnReproducir);
        setButtonNormalColor(btnGrabar);
        setButtonNormalColor(btnExportar);
        setButtonNormalColor(btnImportar);
        setButtonNormalColor(btnSubir);
        setButtonNormalColor(btnDescargar);
        desactivarBotones();
        verificarConexionSQLITE();
        verificarConexionServidor();
        configurarSpinners();
        btnGroupAlfabetico.add(rdbtnAlfabeticoA_Z);
        btnGroupAlfabetico.add(rdbtnAlfabeticoZ_A);
        rdbtnAlfabeticoA_Z.setSelected(true);
        crearMenuExportar();
        tblComparecencias.setDefaultEditor(Object.class, null);
        configurarTablaLocal();
        configurarTablaOnline();
        configurarTablaFull();
    }
    
    private void postInitForm(){
        configurarUsuario();
        servidor.setInspector(inspector);
        listarDatos();
    }
    
    private void initVariables(){
        PANTONE2727C = new Color(82, 132, 196);
        PANTONE1595C = new Color(221, 103, 15);
        PANTONE420C = new Color(200, 200, 200);
        PANTONE422C =  new Color(157, 160, 161);
        telemetria = new Telemetria();
        comparecencia = new Comparecencia();
        database = new MetodosDatabase();
        servidor = new MetodosServidor(inspector);
        conexionDatabase = new ConexionDatabase();
        tblPrincipalLocalModel = new DefaultTableModel();
        tblPrincipalOnlineModel = new DefaultTableModel();
        tblPrincipalFullModel = new DefaultTableModel();
        conexionServidor = new ConexionServidor();
        filtroMTSS = new FileNameExtensionFilter("Comparecencias MTSS", "mtss");
        archivo = new Archivos();
        audio = new Audio();
        result = 0;
        laRespuesta = 0;
        laBusqueda = "";
        tipoBusqueda = "";
        fechaDesde = null;
        fechaHasta = null;
        ascendente = false;
        descendente = false;
        sinInternet = false;
    }
    
    private void disposeVariables(){
        telemetria = null;
        inspector = null;
        database = null;
        servidor = null;
        tblPrincipalLocalModel = null;
        tblPrincipalOnlineModel = null;
        tblPrincipalFullModel = null;
        conexionServidor = null;
        conexionDatabase = null;
        filtroMTSS = null;
        archivo = null;
        audio = null;
        result = 0;
        laRespuesta = 0;
        pantallaInicio = null;
        tipoBusqueda = null;
        fechaDesde = null;
        fechaHasta = null;
        ascendente = null;
        descendente = null;
        directorioSeleccionado = null;
        archivoSeleccionado = null;
        sinInternet = null;
    }
    
    private Color PANTONE2727C;
    private Color PANTONE1595C;
    private Color PANTONE420C;
    private Color PANTONE422C;
    private Telemetria telemetria;
    private Inspector inspector;
    private Comparecencia comparecencia;
    private MetodosDatabase database;
    private MetodosServidor servidor;
    private DefaultTableModel tblPrincipalLocalModel;
    private DefaultTableModel tblPrincipalOnlineModel;
    private DefaultTableModel tblPrincipalFullModel;
    private ConexionServidor conexionServidor;
    private ConexionDatabase conexionDatabase;
    private FileNameExtensionFilter filtroMTSS;
    private Archivos archivo;
    private Audio audio;
    private List<Integer> listaAudios;
    private int result;
    private RegistroGrabacion pantallaRegistro;
    private ReproducirGrabacion pantallaReproducir;
    private Comparecencias pantallaGrabar;
    private JPopupMenu menu;
    private int laRespuesta;
    private Inicio pantallaInicio;
    private String laBusqueda;
    private String tipoBusqueda;
    private java.util.Date fechaDesde;
    private java.util.Date fechaHasta;
    private Boolean ascendente;
    private Boolean descendente;
    private String directorioSeleccionado;
    private String archivoSeleccionado;
    private String[] filaSeleccionada;
    private Boolean sinInternet;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarComparecencia;
    private javax.swing.JButton btnDescargar;
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnGrabar;
    private javax.swing.ButtonGroup btnGroupAlfabetico;
    private javax.swing.JButton btnImportar;
    private javax.swing.JButton btnReproducir;
    private javax.swing.JButton btnSubir;
    private javax.swing.JComboBox<String> cmbTipoBusqueda;
    private javax.swing.JFileChooser exportFileChooser;
    private javax.swing.JFileChooser importFileChooser;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelEstadoSQLITE;
    private javax.swing.JLabel labelEstadoServidor;
    private javax.swing.JLabel labelLOCAL;
    private javax.swing.JLabel labelNombreUsuario;
    private javax.swing.JLabel labelSERVIDOR;
    private javax.swing.JLabel labelTipoUsuario;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panelBusqueda;
    private javax.swing.JPanel panelBusquedaAvanzada;
    private javax.swing.JPanel pnlBuscarPor;
    private javax.swing.JPanel pnlEstado;
    private javax.swing.JPanel pnlFiltrar;
    private javax.swing.JPanel pnlFondo;
    private javax.swing.JPanel pnlLocal;
    private javax.swing.JPanel pnlOrdenar;
    private javax.swing.JPanel pnlServidor;
    private javax.swing.JPanel pnlUsuario;
    private javax.swing.JRadioButton rdbtnAlfabeticoA_Z;
    private javax.swing.JRadioButton rdbtnAlfabeticoZ_A;
    private javax.swing.JSpinner spinnerFechaDesde;
    private javax.swing.JSpinner spinnerFechaHasta;
    private javax.swing.JTable tblComparecencias;
    private javax.swing.JTextField txtBuscarGrabacion;
    // End of variables declaration//GEN-END:variables
}

class LoadingScreen extends javax.swing.JFrame{
    
    public Boolean finProceso = false;
    
    private Color PANTONE2727C = new Color(82, 132, 196);
    private Color PANTONE2717C = new Color(191, 211, 238);
    private JLabel uploadImage = new JLabel(new ImageIcon(getClass().getResource("/images/upload.png")));
    private JLabel downloadImage = new JLabel(new ImageIcon(getClass().getResource("/images/download.png")));
    private MetodosServidor servidor;
    private MetodosDatabase database;
    private Comparecencia comparecencia;
    private Inspector inspector;
    private JProgressBar progressBar = new JProgressBar();
    private JLabel message = new JLabel();
    private int idComparecenciaLocal = -1;
    private int idComparecenciaOnline = -1;
    private List<Integer> audioIDS;
    
    public LoadingScreen(int mode, Comparecencia comparecencia, Inspector inspector) {
        EventQueue.invokeLater(() -> {
            
            this.comparecencia = comparecencia;
            this.inspector =  inspector;
            servidor = new MetodosServidor(inspector);
            database = new MetodosDatabase();
            
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }
            
            switch (mode) {
                case 1 -> {
                    createGUI();
                    addUploadImage();
                    addProgressBar();
                    addMessage();
                    this.setVisible(true);
                    executeUpload();
                    break;
                }
                case 2 -> {
                    createGUI();
                    addDownloadImage();
                    addProgressBar();
                    addMessage();
                    this.setVisible(true);
                    executeDownload();
                    break;
                }
                
                default -> {
                    dispose();
                }
            }
        });

    }
    
    private void createGUI(){
        this.getContentPane().setLayout(null);
        this.setUndecorated(true);
        this.setSize(600,400);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.WHITE); 
    }
    
    private void addDownloadImage(){
        downloadImage.setSize(600,200);
        this.add(downloadImage);
    }
    
    private void addUploadImage(){
        uploadImage.setSize(600,200);
        this.add(uploadImage);
    }
    
    private void addMessage()
    {
        message.setBounds(250,320,200,40);
        message.setForeground(Color.black);
        message.setFont(new Font("arial",Font.PLAIN,15));
        this.add(message);
    }
    private void addProgressBar(){
        progressBar.setBounds(100,280,400,30);
        progressBar.setBorderPainted(true);
        progressBar.setStringPainted(true);
        progressBar.setBackground(PANTONE2717C);
        progressBar.setForeground(PANTONE2727C);
        progressBar.setValue(0);
        this.add(progressBar);
    }
    
    private void executeUpload() {
        new Thread(() -> {
            try {
                
                progressBar.setValue(25);
                message.setText("Subiendo comparecencia...");
                subirComparecencia();
                
                progressBar.setValue(50);
                message.setText("Subiendo audios...");
                subirAudios();
                
                progressBar.setValue(75);
                message.setText("Subiendo anotaciones...");
                subirAnotaciones();
                
                progressBar.setValue(100);
                message.setText("Subida finalizada.");
                Thread.sleep(2000);
                
                finProceso = true;              
            } catch (Exception e) {
                e.printStackTrace();
            }
        }        
    ).start();

}
    
    private void executeDownload() {
        new Thread(() -> {
            try {
                
                progressBar.setValue(25);
                message.setText("Descargando comparecencia...");
                descargarComparecencia();
                
                progressBar.setValue(50);
                message.setText("Descargando audios...");
                descargarAudios();
                
                progressBar.setValue(75);
                message.setText("Descargando anotaciones...");
                descargarAnotaciones();
                
                progressBar.setValue(100);
                message.setText("Descarga finalizada.");
                Thread.sleep(2000);
                
                finProceso = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ).start();
    }
    
    private void subirComparecencia() {
        idComparecenciaLocal = comparecencia.getIdComparecencia();
        idComparecenciaOnline = servidor.subirComparecencia(idComparecenciaLocal);
    }

    private void subirAudios() {
        audioIDS = database.getAudioXComparecencia(idComparecenciaLocal);
        for (Integer idAudio : audioIDS) {
            String audioPath = database.getAudioPathByIDs(idComparecenciaLocal, idAudio);
            servidor.subirAudio(idComparecenciaOnline, audioPath);
        }
    }

    private void subirAnotaciones() {
        for (Integer idAudio : audioIDS) {
            String anotacionPath = database.getAnotacionesPathByIDs(idComparecenciaLocal, idAudio);
            servidor.subirAnotacion(idComparecenciaOnline, anotacionPath);
        }
    }

    private void descargarComparecencia() {
        idComparecenciaOnline = servidor.getIdComparecencia(comparecencia.getCodigoSILAC());
        servidor.descargarComparecencia(idComparecenciaOnline);
        idComparecenciaLocal = database.getIdLastComparecencia();
    }

    private void descargarAudios() {
        audioIDS = database.getAudioXComparecencia(idComparecenciaLocal);
        for (Integer idAudio : audioIDS) {
            String audioPath = database.getAudioPathByIDs(idComparecenciaLocal, idAudio);
            servidor.descargarAudio(idComparecenciaOnline, audioPath);
        }
    }

    private void descargarAnotaciones() {
        for (Integer idAudio : audioIDS) {
            String anotacionPath = database.getAnotacionesPathByIDs(idComparecenciaLocal, idAudio);
            servidor.descargarAnotacion(idComparecenciaOnline, anotacionPath);
        }
    }
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