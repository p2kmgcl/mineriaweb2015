/**
 * @author Pablo Molina y Gregorio Carazo
 */

package proyecto;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import websphinx.Link;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import websphinx.Crawler;
import websphinx.DownloadParameters;

abstract public class MainWindow extends javax.swing.JFrame {

    abstract public void resetCrawler();
    abstract public void runCrawler();
    abstract public void stopCrawler();
    abstract public Crawly getCrawler();
    
    private int skippedLinks = 0; // Enlaces ignorados por el crawler
    private int tooDeepLinks = 0; // Enlaces no visitados por ser demasiado profundos
    private int visitedLinks = 0; // Enlaces visitados
    private int errorLinks = 0; // Visitas fallidas (404, 403...)

    private final ArrayList<Restriccion> restricciones;
    Object[][] data;
    String[] columnNames = {"Tipo", "Elemento", "Valor"};
    Table tabla;
    /**
     * Creates new form MainWindow
     */
    public class Table extends DefaultTableModel {

        public Table(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;//To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    public MainWindow() {
        initComponents();
        tabla =  new Table(data, columnNames);
        jTable_tabla_restricciones.setModel(tabla);
        restricciones = new ArrayList<>();
        
    }
    
    public void clearLog () {
        System.out.println("Log cleared");
        this.jTextAreaRun.setText("");
    }
    
    public void log (String line) {
        System.out.println(line);
        this.jTextAreaRun.setText(
            this.jTextAreaRun.getText() + line + '\n');
    }
    
    public void incSkippedLinks () {
        this.skippedLinks++;
        this.jLabelSkippedLinks.setText("" + this.skippedLinks);
    }
    
    public void incTooDeepLinks () {
        this.tooDeepLinks++;
        this.jLabelTooDeepLinks.setText("" + this.tooDeepLinks);
    }
    
    public void incVisitedLinks () {
        this.visitedLinks++;
        this.jLabelVisitedLinks.setText("" + this.visitedLinks);
    }
    
    public void incErrorLinks () {
        this.errorLinks++;
        this.jLabelErrorLinks.setText("" + this.errorLinks);
    }
    
    public void setCrawlerOptions () {
        // Urls
        String[] urls = this.textAreaUrls.getText().split("\n");
        for (String url : urls) {
            try {
                this.getCrawler().addRoot(new Link(new URL(url)));
                this.log("Añadida url '" + url + "'");
            } catch (MalformedURLException ex) {
                this.log("Ignorada url '" + url + "'");
                Logger.getLogger(Crawly.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Alcance
        switch (this.jComboBoxScope.getSelectedIndex()) {
            case 0:
                this.log("Alcance: Subarbol");
                this.getCrawler().setDomain(Crawler.SUBTREE);
                break;
            case 1:
                this.log("Alcance: Servidor");
                this.getCrawler().setDomain(Crawler.SERVER);
                break;
            case 2:
                this.log("Alcance: Web");
                this.getCrawler().setDomain(Crawler.WEB);
                break;
        }
        
        // Límite de saltos
        this.log("Límite de saltos: " + (int) this.jSpinnerMaxDepth.getValue());
        this.getCrawler().setMaxDepth((int) this.jSpinnerMaxDepth.getValue());
    
        // Tipo de exploración
        switch (this.jComboBoxExplorationType.getSelectedIndex()) {
            case 0:
                this.log("Exploración por anchura");
                this.getCrawler().setDepthFirst(false);
                break;
            case 1:
                this.log("Exploración por profundidad");
                this.getCrawler().setDepthFirst(true);
                break;
        }
        
        // Enlaces
        switch (this.jComboBoxLinkType.getSelectedIndex()) {
            case 0:
                this.log("Exploración de hiperenlaces");
                this.getCrawler().setLinkType(Crawler.HYPERLINKS);
                break;
            case 1:
                this.log("Exploración de hiperenlaces e imágenes");
                this.getCrawler().setLinkType(Crawler.HYPERLINKS_AND_IMAGES);
                break;
            case 2:
                this.log("Exploración completa");
                this.getCrawler().setLinkType(Crawler.ALL_LINKS);
                break;
        }
        
        // Restricciones...
        
        // Robots.txt
        DownloadParameters downloadParameters = new DownloadParameters();
        if (this.jCheckBoxObeyRobots.isSelected()) {
            this.log("Obedeciendo ficheros robots.txt");
            downloadParameters.changeObeyRobotExclusion(true);
        } else {
            this.log("Ignorando ficheros robots.txt");
            downloadParameters.changeObeyRobotExclusion(false);
        }
        downloadParameters.changeUserAgent("ChupiCrawler Mozilla/5.0 (X11; U; Linux x86_64;");
        this.getCrawler().setDownloadParameters(downloadParameters);
    }
    
    public void crawlerEnd () {
        this.buttonToggleCrawler.setVisible(false);
        this.buttonStopCrawler.setText("Cerrar log");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JDialog_Run = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaRun = new javax.swing.JTextArea();
        buttonStopCrawler = new javax.swing.JButton();
        buttonToggleCrawler = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabelTooDeepLinks = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabelSkippedLinks = new javax.swing.JLabel();
        jLabelVisitedLinks = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabelErrorLinks = new javax.swing.JLabel();
        jDialog_restriccion = new javax.swing.JDialog();
        jToggleButton_restriccion_enlace = new javax.swing.JToggleButton();
        jToggleButton_restriccion_pagina = new javax.swing.JToggleButton();
        jLabel_restriccion_cumplirse = new javax.swing.JLabel();
        jComboBox_restriccion_cumplir = new javax.swing.JComboBox();
        jLabel_restriccion_elemento = new javax.swing.JLabel();
        jComboBox_restriccion_elemento = new javax.swing.JComboBox();
        jLabel_restriccion_valor = new javax.swing.JLabel();
        jTextField_restriccion_valor = new javax.swing.JTextField();
        jButton_restriccion_aceptar = new javax.swing.JButton();
        jButton_restriccion_cancelar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaUrls = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxScope = new javax.swing.JComboBox();
        jSpinnerMaxDepth = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxExplorationType = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxLinkType = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_tabla_restricciones = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jCheckBoxObeyRobots = new javax.swing.JCheckBox();
        checkboxSaveInFolder = new javax.swing.JCheckBox();
        checkboxGenerateMatrix = new javax.swing.JCheckBox();
        checkboxConcatenateResults = new javax.swing.JCheckBox();
        buttonGenerateMatrix = new javax.swing.JButton();
        buttonSaveInFolder = new javax.swing.JButton();
        buttonConcatenateResults = new javax.swing.JButton();
        buttonStartCrawler = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        JDialog_Run.setMinimumSize(new java.awt.Dimension(600, 580));
        JDialog_Run.setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        JDialog_Run.setPreferredSize(new java.awt.Dimension(600, 580));
        JDialog_Run.setResizable(false);
        JDialog_Run.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                runDialogClosed(evt);
            }
        });

        jTextAreaRun.setColumns(20);
        jTextAreaRun.setRows(5);
        jTextAreaRun.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextAreaRun.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextAreaRun.setEnabled(false);
        jTextAreaRun.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jScrollPane3.setViewportView(jTextAreaRun);

        buttonStopCrawler.setText("Finalizar");
        buttonStopCrawler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonStopCrawlerActionPerformed(evt);
            }
        });

        buttonToggleCrawler.setText("Pausar");
        buttonToggleCrawler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonToggleCrawlerActionPerformed(evt);
            }
        });

        jLabel5.setText("Ignorados: ");

        jLabel7.setText("Demasiado profundos:");

        jLabelTooDeepLinks.setText("000000");

        jLabel6.setText("Descargados:");

        jLabelSkippedLinks.setText("000000");

        jLabelVisitedLinks.setText("000000");

        jLabel8.setText("Fallidos:");

        jLabelErrorLinks.setText("000000");

        javax.swing.GroupLayout JDialog_RunLayout = new javax.swing.GroupLayout(JDialog_Run.getContentPane());
        JDialog_Run.getContentPane().setLayout(JDialog_RunLayout);
        JDialog_RunLayout.setHorizontalGroup(
            JDialog_RunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDialog_RunLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JDialog_RunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDialog_RunLayout.createSequentialGroup()
                        .addGroup(JDialog_RunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(JDialog_RunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JDialog_RunLayout.createSequentialGroup()
                                .addComponent(jLabelVisitedLinks)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonToggleCrawler)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonStopCrawler))
                            .addGroup(JDialog_RunLayout.createSequentialGroup()
                                .addGroup(JDialog_RunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelSkippedLinks)
                                    .addComponent(jLabelErrorLinks)
                                    .addComponent(jLabelTooDeepLinks))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE))
                .addContainerGap())
        );
        JDialog_RunLayout.setVerticalGroup(
            JDialog_RunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDialog_RunLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(JDialog_RunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabelSkippedLinks))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDialog_RunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabelTooDeepLinks, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDialog_RunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelErrorLinks)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JDialog_RunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JDialog_RunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(buttonStopCrawler)
                        .addComponent(buttonToggleCrawler))
                    .addComponent(jLabel6)
                    .addComponent(jLabelVisitedLinks))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jDialog_restriccion.setMinimumSize(new java.awt.Dimension(500, 243));
        jDialog_restriccion.setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        jDialog_restriccion.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        jDialog_restriccion.setResizable(false);

        jToggleButton_restriccion_enlace.setSelected(true);
        jToggleButton_restriccion_enlace.setText("Enlace");
        jToggleButton_restriccion_enlace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_restriccion_enlaceActionPerformed(evt);
            }
        });

        jToggleButton_restriccion_pagina.setText("Pagina");
        jToggleButton_restriccion_pagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_restriccion_paginaActionPerformed(evt);
            }
        });

        jLabel_restriccion_cumplirse.setText("Debe cumplirse:");

        jComboBox_restriccion_cumplir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Si", "No" }));
        jComboBox_restriccion_cumplir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_restriccion_cumplirActionPerformed(evt);
            }
        });

        jLabel_restriccion_elemento.setText("Elemento:");

        jComboBox_restriccion_elemento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Titulo", "Etiqueta", "Contenido" }));
        jComboBox_restriccion_elemento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_restriccion_elementoActionPerformed(evt);
            }
        });

        jLabel_restriccion_valor.setText("Valor:");

        jTextField_restriccion_valor.setText("Introducir expresion regular");
        jTextField_restriccion_valor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField_restriccion_valorMouseClicked(evt);
            }
        });

        jButton_restriccion_aceptar.setText("Aceptar");
        jButton_restriccion_aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_restriccion_aceptarActionPerformed(evt);
            }
        });

        jButton_restriccion_cancelar.setText("Cancelar");
        jButton_restriccion_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_restriccion_cancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog_restriccionLayout = new javax.swing.GroupLayout(jDialog_restriccion.getContentPane());
        jDialog_restriccion.getContentPane().setLayout(jDialog_restriccionLayout);
        jDialog_restriccionLayout.setHorizontalGroup(
            jDialog_restriccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog_restriccionLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jDialog_restriccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog_restriccionLayout.createSequentialGroup()
                        .addComponent(jLabel_restriccion_cumplirse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(49, 49, 49))
                    .addGroup(jDialog_restriccionLayout.createSequentialGroup()
                        .addGroup(jDialog_restriccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel_restriccion_elemento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel_restriccion_valor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(82, 98, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog_restriccionLayout.createSequentialGroup()
                        .addGroup(jDialog_restriccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton_restriccion_cancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jToggleButton_restriccion_enlace, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)))
                .addGroup(jDialog_restriccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jToggleButton_restriccion_pagina, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox_restriccion_cumplir, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox_restriccion_elemento, javax.swing.GroupLayout.Alignment.LEADING, 0, 232, Short.MAX_VALUE)
                    .addComponent(jTextField_restriccion_valor, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton_restriccion_aceptar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jDialog_restriccionLayout.setVerticalGroup(
            jDialog_restriccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog_restriccionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog_restriccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton_restriccion_enlace)
                    .addComponent(jToggleButton_restriccion_pagina))
                .addGap(18, 18, 18)
                .addGroup(jDialog_restriccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_restriccion_cumplirse, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_restriccion_cumplir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDialog_restriccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_restriccion_elemento, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_restriccion_elemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDialog_restriccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_restriccion_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_restriccion_valor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(jDialog_restriccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_restriccion_cancelar)
                    .addComponent(jButton_restriccion_aceptar))
                .addGap(24, 24, 24))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(440, 740));
        setResizable(false);

        textAreaUrls.setColumns(20);
        textAreaUrls.setRows(5);
        textAreaUrls.setText("http://www.ccsu.edu/anthropology/\nhttp://www.art.ccsu.edu/\nhttp://www.communication.ccsu.edu/\nhttp://web.ccsu.edu/criminology/\nhttp://www.design.ccsu.edu/\nhttp://www.econ.ccsu.edu/\nhttp://www.english.ccsu.edu/\nhttp://www.geography.ccsu.edu/\nhttp://www.history.ccsu.edu/\nhttp://web.ccsu.edu/journalism/\nhttp://www.modlang.ccsu.edu/\nhttp://www.music.ccsu.edu/\nhttp://you.ccsu.edu/philosophy/\nhttp://www.polisci.ccsu.edu/\nhttp://web.ccsu.edu/psychology/\nhttp://www.sociology.ccsu.edu/\nhttp://www.theatre.ccsu.edu/\n");
        textAreaUrls.setToolTipText("");
        jScrollPane1.setViewportView(textAreaUrls);

        jLabel1.setText("Alcance:");

        jLabel2.setText("Límite de saltos:");

        jComboBoxScope.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Subárbol", "Servidor", "Web" }));
        jComboBoxScope.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxScopeActionPerformed(evt);
            }
        });

        jLabel3.setText("Tipo de exploración: ");

        jComboBoxExplorationType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Anchura", "Profundidad" }));

        jLabel4.setText("Enlaces:");

        jComboBoxLinkType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hiperenlaces", "Hiperenlaces e imágenes", "Todos" }));

        jTable_tabla_restricciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tipo", "Elemento", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable_tabla_restricciones);
        if (jTable_tabla_restricciones.getColumnModel().getColumnCount() > 0) {
            jTable_tabla_restricciones.getColumnModel().getColumn(0).setResizable(false);
        }

        jButton1.setText("Añadir restricción");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jCheckBoxObeyRobots.setSelected(true);
        jCheckBoxObeyRobots.setText("Obedecer ficheros robots.txt");
        jCheckBoxObeyRobots.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxObeyRobotsActionPerformed(evt);
            }
        });

        checkboxSaveInFolder.setText("Guardar en carpeta");
        checkboxSaveInFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxSaveInFolderActionPerformed(evt);
            }
        });

        checkboxGenerateMatrix.setText("Generar matriz de adyacencia");
        checkboxGenerateMatrix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxGenerateMatrixActionPerformed(evt);
            }
        });

        checkboxConcatenateResults.setText("Concatenar resultados");
        checkboxConcatenateResults.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxConcatenateResultsActionPerformed(evt);
            }
        });

        buttonGenerateMatrix.setText("Examinar...");
        buttonGenerateMatrix.setEnabled(false);

        buttonSaveInFolder.setText("Examinar...");
        buttonSaveInFolder.setEnabled(false);
        buttonSaveInFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveInFolderActionPerformed(evt);
            }
        });

        buttonConcatenateResults.setText("Examinar...");
        buttonConcatenateResults.setEnabled(false);

        buttonStartCrawler.setText("Iniciar rastreo");
        buttonStartCrawler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonStartCrawlerActionPerformed(evt);
            }
        });

        jMenu1.setText("Archivo");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Abrir");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Guardar");
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Salir");
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ayuda");

        jCheckBoxMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        jCheckBoxMenuItem1.setText("Mostrar ayuda");
        jMenu2.add(jCheckBoxMenuItem1);

        jMenuItem4.setText("Licencia");
        jMenu2.add(jMenuItem4);

        jMenuItem5.setText("Acerca de");
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonStartCrawler, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxScope, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSpinnerMaxDepth)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxExplorationType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxLinkType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkboxGenerateMatrix)
                            .addComponent(checkboxSaveInFolder))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonGenerateMatrix, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonSaveInFolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(checkboxConcatenateResults)
                        .addGap(68, 68, 68)
                        .addComponent(buttonConcatenateResults, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBoxObeyRobots)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxScope, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jSpinnerMaxDepth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBoxExplorationType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBoxLinkType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(14, 14, 14)
                .addComponent(jCheckBoxObeyRobots)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkboxSaveInFolder)
                    .addComponent(buttonSaveInFolder))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkboxGenerateMatrix)
                    .addComponent(buttonGenerateMatrix))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkboxConcatenateResults)
                    .addComponent(buttonConcatenateResults))
                .addGap(18, 18, 18)
                .addComponent(buttonStartCrawler, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jDialog_restriccion.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCheckBoxObeyRobotsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxObeyRobotsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxObeyRobotsActionPerformed

    private void checkboxGenerateMatrixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxGenerateMatrixActionPerformed
        this.buttonGenerateMatrix.setEnabled(this.checkboxGenerateMatrix.isSelected());
    }//GEN-LAST:event_checkboxGenerateMatrixActionPerformed

    private void buttonSaveInFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveInFolderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonSaveInFolderActionPerformed

    private void buttonStartCrawlerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStartCrawlerActionPerformed
        this.visitedLinks = 0;
        this.skippedLinks = 0;
        this.tooDeepLinks = 0;
        this.errorLinks = 0;
        this.jLabelVisitedLinks.setText("0");
        this.jLabelSkippedLinks.setText("0");
        this.jLabelTooDeepLinks.setText("0");
        this.jLabelErrorLinks.setText("0");
        this.buttonToggleCrawler.setVisible(true);
        this.buttonStopCrawler.setText("Finalizar");
        this.clearLog();
        this.resetCrawler();
        this.log("Configurando crawler");
        this.setCrawlerOptions();
        this.log("Lanzando crawler");
        this.runCrawler();
        this.buttonToggleCrawler.setText("Pausar");
        this.JDialog_Run.setLocationRelativeTo(this);
        this.JDialog_Run.setModal(true);
        this.JDialog_Run.setVisible(true);
    }//GEN-LAST:event_buttonStartCrawlerActionPerformed

    private void jComboBoxScopeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxScopeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxScopeActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void checkboxSaveInFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxSaveInFolderActionPerformed
        this.buttonSaveInFolder.setEnabled(this.checkboxSaveInFolder.isSelected());
    }//GEN-LAST:event_checkboxSaveInFolderActionPerformed

    private void checkboxConcatenateResultsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxConcatenateResultsActionPerformed
        this.buttonConcatenateResults.setEnabled(this.checkboxConcatenateResults.isSelected());
    }//GEN-LAST:event_checkboxConcatenateResultsActionPerformed
       
    private void jComboBox_restriccion_cumplirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_restriccion_cumplirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_restriccion_cumplirActionPerformed

    private void jComboBox_restriccion_elementoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_restriccion_elementoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_restriccion_elementoActionPerformed

    private void jToggleButton_restriccion_enlaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_restriccion_enlaceActionPerformed
        // TODO add your handling code here:
        jToggleButton_restriccion_enlace.setSelected(true);
        if(jToggleButton_restriccion_pagina.isSelected() == true){
            jToggleButton_restriccion_pagina.setSelected(false);
        }
    }//GEN-LAST:event_jToggleButton_restriccion_enlaceActionPerformed

    private void jToggleButton_restriccion_paginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_restriccion_paginaActionPerformed
        // TODO add your handling code here:
        jToggleButton_restriccion_pagina.setSelected(true);
        if(jToggleButton_restriccion_enlace.isSelected() == true){
            jToggleButton_restriccion_enlace.setSelected(false);
        }
    }//GEN-LAST:event_jToggleButton_restriccion_paginaActionPerformed

    private void jTextField_restriccion_valorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_restriccion_valorMouseClicked
        // TODO add your handling code here:
        if(jTextField_restriccion_valor.getText().equals("Introducir expresion regular"))
            jTextField_restriccion_valor.setText("");
    }//GEN-LAST:event_jTextField_restriccion_valorMouseClicked

    private void jButton_restriccion_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_restriccion_cancelarActionPerformed
        // TODO add your handling code here:
        jTextField_restriccion_valor.setText("Introducir expresion regular");
        jDialog_restriccion.setVisible(false);
    }//GEN-LAST:event_jButton_restriccion_cancelarActionPerformed

    private void jButton_restriccion_aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_restriccion_aceptarActionPerformed
        // TODO add your handling code here:
        Restriccion linea_restriccion = null;
        String tipo = " ";
        if(jToggleButton_restriccion_enlace.isSelected() == true){
            tipo = "Enlace";
            switch((String) jComboBox_restriccion_cumplir.getSelectedItem()){
                case "Si":
                    linea_restriccion = new Restriccion("Enlace", true,(String) jComboBox_restriccion_elemento.getSelectedItem(), jTextField_restriccion_valor.getText());
                    break;
                case "No":
                    linea_restriccion = new Restriccion("Enlace", false, (String) jComboBox_restriccion_elemento.getSelectedItem(), jTextField_restriccion_valor.getText());
                    break;
            }
        } else{
            tipo = "Pagina";
            switch((String) jComboBox_restriccion_cumplir.getSelectedItem()){
                case "Si":
                    linea_restriccion = new Restriccion("Pagina", true,(String) jComboBox_restriccion_elemento.getSelectedItem(), jTextField_restriccion_valor.getText());
                    break;
                case "No":
                    linea_restriccion = new Restriccion("Pagina", false, (String) jComboBox_restriccion_elemento.getSelectedItem(), jTextField_restriccion_valor.getText());
                    break;
            }
        }
        Object[] restriccion = {tipo, jComboBox_restriccion_elemento.getSelectedItem(), jTextField_restriccion_valor.getText()};
        tabla.addRow(restriccion);
        restricciones.add(linea_restriccion);
        jTextField_restriccion_valor.setText("Introducir expresion regular");
        jDialog_restriccion.setVisible(false);
    }//GEN-LAST:event_jButton_restriccion_aceptarActionPerformed

    private void buttonToggleCrawlerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonToggleCrawlerActionPerformed
        if (buttonToggleCrawler.getText().equals("Pausar")) {
            this.log("Pausando rastreo");
            this.buttonToggleCrawler.setText("Continuar");
            this.getCrawler().pause();
        } else {
            this.log("Reanudando rastreo");
            this.buttonToggleCrawler.setText("Pausar");
            this.runCrawler();
        }
    }//GEN-LAST:event_buttonToggleCrawlerActionPerformed

    private void buttonStopCrawlerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStopCrawlerActionPerformed
        this.stopCrawler();
        JDialog_Run.setVisible(false);
    }//GEN-LAST:event_buttonStopCrawlerActionPerformed

    private void runDialogClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_runDialogClosed
        this.stopCrawler();
        JDialog_Run.setVisible(false);
    }//GEN-LAST:event_runDialogClosed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog JDialog_Run;
    private javax.swing.JButton buttonConcatenateResults;
    private javax.swing.JButton buttonGenerateMatrix;
    private javax.swing.JButton buttonSaveInFolder;
    private javax.swing.JButton buttonStartCrawler;
    private javax.swing.JButton buttonStopCrawler;
    private javax.swing.JButton buttonToggleCrawler;
    private javax.swing.JCheckBox checkboxConcatenateResults;
    private javax.swing.JCheckBox checkboxGenerateMatrix;
    private javax.swing.JCheckBox checkboxSaveInFolder;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_restriccion_aceptar;
    private javax.swing.JButton jButton_restriccion_cancelar;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBox jCheckBoxObeyRobots;
    private javax.swing.JComboBox jComboBoxExplorationType;
    private javax.swing.JComboBox jComboBoxLinkType;
    private javax.swing.JComboBox jComboBoxScope;
    private javax.swing.JComboBox jComboBox_restriccion_cumplir;
    private javax.swing.JComboBox jComboBox_restriccion_elemento;
    private javax.swing.JDialog jDialog_restriccion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelErrorLinks;
    private javax.swing.JLabel jLabelSkippedLinks;
    private javax.swing.JLabel jLabelTooDeepLinks;
    private javax.swing.JLabel jLabelVisitedLinks;
    private javax.swing.JLabel jLabel_restriccion_cumplirse;
    private javax.swing.JLabel jLabel_restriccion_elemento;
    private javax.swing.JLabel jLabel_restriccion_valor;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSpinner jSpinnerMaxDepth;
    private javax.swing.JTable jTable_tabla_restricciones;
    private javax.swing.JTextArea jTextAreaRun;
    private javax.swing.JTextField jTextField_restriccion_valor;
    private javax.swing.JToggleButton jToggleButton_restriccion_enlace;
    private javax.swing.JToggleButton jToggleButton_restriccion_pagina;
    private javax.swing.JTextArea textAreaUrls;
    // End of variables declaration//GEN-END:variables
}
