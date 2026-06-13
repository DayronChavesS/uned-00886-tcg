package interfaces;

import java.awt.event.KeyEvent;
import models.Oficina;
import models.Puesto;
import models.Region;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import models.Inspector;
import models.Persona;
import models.Usuario;
import server.MetodosServidor;

public class RegistroAdministrador extends javax.swing.JFrame {
    
    public RegistroAdministrador() {
        initComponents();
        initVariables();
        initForm();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelDatosPersonalesAdmi = new javax.swing.JPanel();
        txtFieldCedula = new javax.swing.JFormattedTextField();
        labelPrimerNombre = new javax.swing.JLabel();
        labelCedula = new javax.swing.JLabel();
        labelPrimerApellido = new javax.swing.JLabel();
        labelSegundoApellido = new javax.swing.JLabel();
        labelSegundoNombre = new javax.swing.JLabel();
        labelSeparator = new javax.swing.JLabel();
        labelSeparator2 = new javax.swing.JLabel();
        labelMandatory1 = new javax.swing.JLabel();
        labelMandatory2 = new javax.swing.JLabel();
        labelMandatory3 = new javax.swing.JLabel();
        labelMandatory4 = new javax.swing.JLabel();
        txtPrimerNombre = new javax.swing.JTextField();
        txtSegundoNombre = new javax.swing.JTextField();
        txtSegundoApellido = new javax.swing.JTextField();
        txtPrimerApellido = new javax.swing.JTextField();
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
        lblTituloRegistroAdmi = new javax.swing.JLabel();
        btnGuardarAdmi = new javax.swing.JButton();
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
        setTitle("Servidor | Registro");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        panelDatosPersonalesAdmi.setBackground(new java.awt.Color(255, 255, 255));
        panelDatosPersonalesAdmi.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos personales", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));

        try {
            txtFieldCedula.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#0###0###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtFieldCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFieldCedulaKeyPressed(evt);
            }
        });

        labelPrimerNombre.setText("Primer nombre");

        labelCedula.setText("Cedula de identidad");

        labelPrimerApellido.setText("Primer apellido");

        labelSegundoApellido.setText("Segundo apellido");

        labelSegundoNombre.setText("Segundo nombre");

        labelSeparator.setText("-");

        labelSeparator2.setText("-");

        labelMandatory1.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory1.setText("*");

        labelMandatory2.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory2.setText("*");

        labelMandatory3.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory3.setText("*");

        labelMandatory4.setForeground(new java.awt.Color(255, 51, 51));
        labelMandatory4.setText("*");

        javax.swing.GroupLayout panelDatosPersonalesAdmiLayout = new javax.swing.GroupLayout(panelDatosPersonalesAdmi);
        panelDatosPersonalesAdmi.setLayout(panelDatosPersonalesAdmiLayout);
        panelDatosPersonalesAdmiLayout.setHorizontalGroup(
            panelDatosPersonalesAdmiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosPersonalesAdmiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosPersonalesAdmiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosPersonalesAdmiLayout.createSequentialGroup()
                        .addGroup(panelDatosPersonalesAdmiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDatosPersonalesAdmiLayout.createSequentialGroup()
                                .addComponent(labelCedula)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelMandatory1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtFieldCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDatosPersonalesAdmiLayout.createSequentialGroup()
                        .addGroup(panelDatosPersonalesAdmiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelDatosPersonalesAdmiLayout.createSequentialGroup()
                                .addComponent(txtPrimerApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelSeparator2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSegundoApellido))
                            .addGroup(panelDatosPersonalesAdmiLayout.createSequentialGroup()
                                .addGroup(panelDatosPersonalesAdmiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelDatosPersonalesAdmiLayout.createSequentialGroup()
                                        .addComponent(labelPrimerNombre)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelMandatory2, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelDatosPersonalesAdmiLayout.createSequentialGroup()
                                        .addComponent(labelPrimerApellido)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelMandatory3, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtPrimerNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(panelDatosPersonalesAdmiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelDatosPersonalesAdmiLayout.createSequentialGroup()
                                        .addGap(17, 17, 17)
                                        .addComponent(labelSegundoApellido)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelMandatory4, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(panelDatosPersonalesAdmiLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(labelSeparator)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panelDatosPersonalesAdmiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(panelDatosPersonalesAdmiLayout.createSequentialGroup()
                                                .addComponent(labelSegundoNombre)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addComponent(txtSegundoNombre))))))
                        .addGap(38, 38, 38))))
        );
        panelDatosPersonalesAdmiLayout.setVerticalGroup(
            panelDatosPersonalesAdmiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosPersonalesAdmiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosPersonalesAdmiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCedula)
                    .addComponent(labelMandatory1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFieldCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosPersonalesAdmiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPrimerNombre)
                    .addComponent(labelSegundoNombre)
                    .addComponent(labelMandatory2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(panelDatosPersonalesAdmiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSeparator)
                    .addComponent(txtPrimerNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSegundoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosPersonalesAdmiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPrimerApellido)
                    .addComponent(labelSegundoApellido)
                    .addComponent(labelMandatory3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelMandatory4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosPersonalesAdmiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSeparator2)
                    .addComponent(txtSegundoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrimerApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        panelDatosInspector.setBackground(new java.awt.Color(255, 255, 255));
        panelDatosInspector.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de empleo", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));

        labelRegion.setText("Región");

        labelPuesto.setText("Puesto");

        labelOficina.setText("Oficina");

        cbmbxPuesto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbmbxPuesto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        cbmbxRegion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbmbxRegion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cbmbxRegion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbmbxRegionActionPerformed(evt);
            }
        });

        cbmbxOficina.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbmbxOficina.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

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
                    .addComponent(cbmbxRegion, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbmbxOficina, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbmbxPuesto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelDatosInspectorLayout.createSequentialGroup()
                        .addGroup(panelDatosInspectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbmbxOficina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblTituloRegistroAdmi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloRegistroAdmi.setText("Ingrese la información que se le solicita a continuación:");

        btnGuardarAdmi.setText("Continuar");
        btnGuardarAdmi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarAdmiActionPerformed(evt);
            }
        });

        panelDatosUsuario.setBackground(new java.awt.Color(255, 255, 255));
        panelDatosUsuario.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de usuario", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));

        labelEmail.setText("Email");

        labelContraseña.setText("Contraseña");

        txtFieldEmail.setText("@mtss.go.cr");
        txtFieldEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFieldEmailKeyPressed(evt);
            }
        });

        labelContraseña1.setText("Confirmar contraseña");

        txtFieldContraseña.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFieldContraseñaKeyPressed(evt);
            }
        });

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
                    .addComponent(txtFieldContraseña2)
                    .addComponent(txtFieldEmail)
                    .addComponent(txtFieldContraseña)
                    .addGroup(panelDatosUsuarioLayout.createSequentialGroup()
                        .addGroup(panelDatosUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                .addComponent(labelMandatory10, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTituloRegistroAdmi, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                    .addComponent(panelDatosPersonalesAdmi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDatosInspector, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDatosUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardarAdmi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloRegistroAdmi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDatosPersonalesAdmi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDatosInspector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDatosUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarAdmi)
                .addGap(6, 6, 6))
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
        persona.setPrimerNombre(txtPrimerNombre.getText());
        persona.setSegundoNombre(txtSegundoNombre.getText());
        persona.setPrimerApellido(txtPrimerApellido.getText());
        persona.setSegundoApellido(txtSegundoApellido.getText());
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
    
    private void guardarDatos() throws Exception{
        
        inspector = servidor.registrarAdministrador(inspector);
        if (inspector == null) {
            inspector = new Inspector();
            throw new Exception("error registro");
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
        int index = 1;
        switch (cbmbxRegion.getSelectedIndex()) {
            case 0:
                return;
            
            case 1:
                
                for (int i = 0; i < 4; i++) {
                    cbmbxOficina.insertItemAt(Oficina.values()[i].toString(), index);
                    index++;
                }
                break;
            
            case 2:
                
                for (int i = 4; i < 8; i++) {
                    cbmbxOficina.insertItemAt(Oficina.values()[i].toString(), index);
                    index++;
                }
                break;
            
            case 3:
                
                for (int i = 8; i < 13; i++) {
                    cbmbxOficina.insertItemAt(Oficina.values()[i].toString(), index);
                    index++;
                }
                break;
            
            case 4:
                
                for (int i = 13; i < 16; i++) {
                    cbmbxOficina.insertItemAt(Oficina.values()[i].toString(), index);
                    index++;
                }
                break;
            
            case 5:
                
                for (int i = 16; i < 21; i++) {
                    cbmbxOficina.insertItemAt(Oficina.values()[i].toString(), index);
                    index++;
                }
                break;
            
            case 6:
                
                for (int i = 21; i < 25; i++) {
                    cbmbxOficina.insertItemAt(Oficina.values()[i].toString(), index);
                    index++;
                }
                break;
            
        }
    }
    
        private void reportarError(Exception ex){
        
        switch (ex.getMessage()) {
            case "cedula null error" -> {
                crearMensajeError("La cedula no puede estar vacia.");
                break;
            }

            case "primerNombre null error" -> {
                crearMensajeError("El primer nombre no puede estar vacio.");
                break;
            }

            case "primerApellido null error" -> {
                crearMensajeError("El primer apellido no puede estar vacio.");
                break;
            }

            case "segundoApellido null error" -> {
                crearMensajeError("El segundo apellido no puede estar vacio.");
                break;
            }

            case "email null error" -> {
                crearMensajeError("El email no puede estar vacio.");
                break;
            }

            case "contraseña null error" -> {
                crearMensajeError("La contraseña no puede estar vacia.");
                break;
            }

            case "puesto null error" -> {
                crearMensajeError("No ha seleccionado un puesto.");
                break;
            }

            case "region null error" -> {
                crearMensajeError("No ha seleccionado una region.");
                break;
            }

            case "oficina null error" -> {
                crearMensajeError("No ha seleccionado una oficina.");
                break;
            }
            case "primerNombre size error" -> {
                crearMensajeError("El primer nombre es demasiado grande.");
                break;
            }
            case "segundoNombre size error" -> {
                crearMensajeError("El segundo nombre es demasiado grande.");
                break;
            }
            case "primerApellido size error" -> {
                crearMensajeError("El primer apellido es demasiado grande.");
                break;
            }
            case "segundoApellido size error" -> {
                crearMensajeError("El segundo apellido es demasiado grande.");
                break;
            }
            case "email size error" -> {
                crearMensajeError("El correo es demasiado grande.");
                break;
            }
            case "email format error" -> {
                crearMensajeError("Solo se aceptan correos del dominio \"@mtss.go.cr\"");
                break;
            }
            case "contraseña big size error" -> {
                crearMensajeError("La contraseña es demasiado grande.");
                break;
            }
            case "contraseña small size error" -> {
                crearMensajeError("La contraseña es demasiado corta.");
                break;
            }
            case "contraseña no coincide" -> {
                crearMensajeError("Las contraseñas no coinciden.");
                break;
            }
            case "region error" -> {
                crearMensajeError("La oficina que ha seleccionado no pertenece a esa region.");
                break;
            }
            case "cedula UNIQUE error" -> {
                crearMensajeError("Ya hay una persona registrada con ese numero de cedula.");
                break;
            }
            case "email UNIQUE error" -> {
                crearMensajeError("Ya hay una persona registrada con ese email.");
                break;
            }
            case "error registro" -> {
                crearMensajeError("No se ha logrado registrar ese usuario.");
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
    
    private void procesarFormulario(){
        try {
            //FASE 1: SE CAPTURAN LOS DATOS INGRESADOS
            capturarDatos();
            
            //FASE 2: SE GUARDAN LOS DATOS
            guardarDatos();

            //FASE 3: SE INTENTA INICIAR SESION
            //se pide confirmacion del usuario
            laRespuesta = JOptionPane.showConfirmDialog(null, """
                                                              Se ha registrado su usuario. 
                                                              \u00bfDesea iniciar sesion?""",
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
    
    private void txtFieldCedulaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFieldCedulaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesarFormulario();
        }
    }//GEN-LAST:event_txtFieldCedulaKeyPressed

    private void cbmbxRegionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbmbxRegionActionPerformed
        updateComboBoxOficina();
    }//GEN-LAST:event_cbmbxRegionActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        int respuesta = 0;
        respuesta = JOptionPane.showConfirmDialog(null,
                """
                ¿Desea regresar a la pantalla de inicio?
                Todos los datos ingresados se perderán.
                """,
                "ATENCION",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        //se procesa la respuesta
        if (respuesta == JOptionPane.YES_OPTION) {
            crearPantallaInicio();
        }
    }//GEN-LAST:event_formWindowClosing

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

    private void btnGuardarAdmiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarAdmiActionPerformed
        procesarFormulario();
    }//GEN-LAST:event_btnGuardarAdmiActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RegistroAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroAdministrador().setVisible(true);
            }
        });
    }
    
    private void initVariables(){
        inspector = new Inspector();
        persona = new Persona();
        usuario = new Usuario();
        laRespuesta = 0;
        contraseña2 = "";
        servidor = new MetodosServidor(inspector);
    }
    
    private void disposeVariables(){
        inspector = null;
        persona = null;
        usuario = null;
        laRespuesta = 0;
        pantallaIniciarSesion = null;
        pantallaInicio = null;
        contraseña2 = "";
        servidor = null;
    }
    
    private void initForm(){
        setComboBoxModels();
    }
    
    private Persona persona;
    private Usuario usuario;
    private Inspector inspector;
    private int laRespuesta;
    private Inicio pantallaInicio;
    private IniciosSesion pantallaIniciarSesion;
    private String contraseña2;
    private MetodosServidor servidor;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardarAdmi;
    private javax.swing.JComboBox<String> cbmbxOficina;
    private javax.swing.JComboBox<String> cbmbxPuesto;
    private javax.swing.JComboBox<String> cbmbxRegion;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelCedula;
    private javax.swing.JLabel labelContraseña;
    private javax.swing.JLabel labelContraseña1;
    private javax.swing.JLabel labelEmail;
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
    private javax.swing.JLabel lblTituloRegistroAdmi;
    private javax.swing.JPanel panelDatosInspector;
    private javax.swing.JPanel panelDatosPersonalesAdmi;
    private javax.swing.JPanel panelDatosUsuario;
    private javax.swing.JFormattedTextField txtFieldCedula;
    private javax.swing.JPasswordField txtFieldContraseña;
    private javax.swing.JPasswordField txtFieldContraseña2;
    private javax.swing.JTextField txtFieldEmail;
    private javax.swing.JTextField txtPrimerApellido;
    private javax.swing.JTextField txtPrimerNombre;
    private javax.swing.JTextField txtSegundoApellido;
    private javax.swing.JTextField txtSegundoNombre;
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