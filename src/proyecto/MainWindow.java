/**
 * @author Pablo Molina y Gregorio Carazo
 */

package proyecto;

import java.awt.Color;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import websphinx.Link;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JScrollBar;
import javax.swing.table.DefaultTableModel;
import websphinx.Crawler;
import websphinx.DownloadParameters;

abstract public class MainWindow extends javax.swing.JFrame {

    abstract public void resetCrawler();
    abstract public void runCrawler(ArrayList<Restriccion> restrictions);
    abstract public void stopCrawler();
    abstract public Crawly getCrawler();
    
    private int skippedLinks = 0; // Enlaces ignorados por el crawler
    private int tooDeepLinks = 0; // Enlaces no visitados por ser demasiado profundos
    private int visitedLinks = 0; // Enlaces visitados
    private int errorLinks = 0; // Visitas fallidas (404, 403...)

    private Lanzar_Sparky lanzado;
    private final ArrayList<Restriccion> restricciones;
    Object[][] data;
    DefaultListModel<String> lista;
    String[] columnNames = {"Tipo", "Invertir", "RegExp", "Valor"};
    Table tabla;
    boolean modificar_url;
    Color color_link = new Color(0, 0, 204);
    Color color_link_visitado = new Color(109, 0, 109);
    
    /**
     * Creates new form MainWindow
     */
    public class Table extends DefaultTableModel {

        public Table(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
        
    }
    
    public MainWindow() {
        String urls[] = {
           "http://www.ccsu.edu/anthropology/",
           "http://www.art.ccsu.edu/",
           "http://www.communication.ccsu.edu/",
           "http://web.ccsu.edu/criminology/",
           "http://www.design.ccsu.edu/",
           "http://www.econ.ccsu.edu/",
           "http://www.english.ccsu.edu/",
           "http://www.geography.ccsu.edu/",
           "http://www.history.ccsu.edu/",
           "http://web.ccsu.edu/journalism/",
           "http://www.modlang.ccsu.edu/",
           "http://www.music.ccsu.edu/",
           "http://you.ccsu.edu/philosophy/",
           "http://www.polisci.ccsu.edu/",
           "http://web.ccsu.edu/psychology/",
           "http://www.sociology.ccsu.edu/",
           "http://www.theatre.ccsu.edu/"
        };
        initComponents();
        lanzado = new Lanzar_Sparky(urls);
        tabla =  new Table(data, columnNames);
        lista = new DefaultListModel<>();
        for(int i = 0; i < urls.length; ++i){
            lista.addElement(urls[i]);
        } 
        jList_url.setModel(lista);
        jTable_tabla_restricciones.setModel(tabla);
        restricciones = new ArrayList<>();
        modificar_url = false;
    }

    private void inicia_ayuda () {
        // Paneles
        jPanel_ayuda_url.setVisible(false);
        jPanel_ayuda_opcionesarriba.setVisible(false);
        jPanel_ayuda_restricciones.setVisible(false);
        jPanel_ayuda_restricciones_insertar.setVisible(false);
        jPanel_ayuda_opcionesabajo.setVisible(false);
        jPanel_ayuda_errores.setVisible(false);
        // enlaces
        jLabel_ayuda_url.setForeground(color_link);
        jLabel_ayuda_opciones_arriba.setForeground(color_link);
        jLabel_ayuda_restricciones.setForeground(color_link);
        jLabel_ayuda_restricciones_insertar.setForeground(color_link);
        jLabel_ayuda_opciones_abajo.setForeground(color_link);
        jLabel_ayuda_errores.setForeground(color_link);
    }
    
    public void clearLog () {
        System.out.println("Log cleared");
        this.jTextAreaRun.setText("");
    }
    
    public void log (String line) {
        System.out.println(line);
        JScrollBar scrollBar = this.jScrollPaneRun.getVerticalScrollBar();
        scrollBar.setValue(scrollBar.getMaximum());
        this.jTextAreaRun.setText(this.jTextAreaRun.getText() + line + '\n');
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
        String urls[] = new String[lista.getSize()];
        lista.copyInto(urls);
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
                lanzado.sparky.setDomain(Crawler.SUBTREE);
                break;
            case 1:
                this.log("Alcance: Servidor");
                this.getCrawler().setDomain(Crawler.SERVER);
                lanzado.sparky.setDomain(Crawler.SERVER);
                break;
            case 2:
                this.log("Alcance: Web");
                this.getCrawler().setDomain(Crawler.WEB);
                lanzado.sparky.setDomain(Crawler.WEB);
                break;
        }
        
        // Límite de saltos
        this.log("Límite de saltos: " + (int) this.jSpinnerMaxDepth.getValue());
        this.getCrawler().setMaxDepth((int) this.jSpinnerMaxDepth.getValue());
        lanzado.sparky.setMaxDepth((int) this.jSpinnerMaxDepth.getValue());
        
        // Tipo de exploración
        switch (this.jComboBoxExplorationType.getSelectedIndex()) {
            case 0:
                this.log("Exploración por anchura");
                this.getCrawler().setDepthFirst(false);
                lanzado.sparky.setDepthFirst(false);
                break;
            case 1:
                this.log("Exploración por profundidad");
                this.getCrawler().setDepthFirst(true);
                lanzado.sparky.setDepthFirst(true);
                break;
        }
        
        // Enlaces
        switch (this.jComboBoxLinkType.getSelectedIndex()) {
            case 0:
                this.log("Exploración de hiperenlaces");
                this.getCrawler().setLinkType(Crawler.HYPERLINKS);
                lanzado.sparky.setLinkType(Crawler.HYPERLINKS);
                break;
            case 1:
                this.log("Exploración de hiperenlaces e imágenes");
                this.getCrawler().setLinkType(Crawler.HYPERLINKS_AND_IMAGES);
                lanzado.sparky.setLinkType(Crawler.HYPERLINKS_AND_IMAGES);
                break;
            case 2:
                this.log("Exploración completa");
                this.getCrawler().setLinkType(Crawler.ALL_LINKS);
                lanzado.sparky.setLinkType(Crawler.ALL_LINKS);
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
        lanzado.sparky.setDownloadParameters(downloadParameters);
    }
    
    public void crawlerEnd () {
        this.log("Calculando matriz de adyacencia");
        if(main.SAVE_MATRIX != null){
            String urls[] = new String[lista.getSize()];
            lista.copyInto(urls);
            lanzado.start();
        }
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
        jScrollPaneRun = new javax.swing.JScrollPane();
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
        jDialog_restriccion_add = new javax.swing.JDialog();
        jButton_restriccion_aceptar = new javax.swing.JButton();
        jButton_restriccion_cancelar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jTextField_restriction_value = new javax.swing.JTextField();
        jCheckBox_restriction_regexp = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        jComboBox_restriction_type = new javax.swing.JComboBox();
        jCheckBox_restriction_invert = new javax.swing.JCheckBox();
        jPopupMenu_lista = new javax.swing.JPopupMenu();
        jMenuItem_menu_lista_modificar = new javax.swing.JMenuItem();
        jMenuItem_menu_lista_eliminar = new javax.swing.JMenuItem();
        jMenuItem_menu_lista_aniadir = new javax.swing.JMenuItem();
        jDialog_edit_url = new javax.swing.JDialog();
        jTextField_edit_url = new javax.swing.JTextField();
        jButton_edit_url_aceptar = new javax.swing.JButton();
        jButton_edit_url_cancelar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jFileChooser_folder = new javax.swing.JFileChooser();
        jFileChooser_file = new javax.swing.JFileChooser();
        jFileChooser_matrix = new javax.swing.JFileChooser();
        jDialog_restriccion_edit = new javax.swing.JDialog();
        jButton_restriccionedit_aceptar = new javax.swing.JButton();
        jButton_restriccionedit_cancelar = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jTextField_restrictionedit_value = new javax.swing.JTextField();
        jCheckBox_restrictionedit_regexp = new javax.swing.JCheckBox();
        jLabel15 = new javax.swing.JLabel();
        jComboBox_restrictionedit_type = new javax.swing.JComboBox();
        jCheckBox_restrictionedit_invert = new javax.swing.JCheckBox();
        jFrame_ayuda = new javax.swing.JFrame();
        jPanel1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLayeredPane_ayuda = new javax.swing.JLayeredPane();
        jPanel_ayuda_url = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel_ayuda_opcionesarriba = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jPanel_ayuda_restricciones = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jPanel_ayuda_restricciones_insertar = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel_ayuda_opcionesabajo = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jPanel_ayuda_errores = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel_ayuda_url = new javax.swing.JLabel();
        jLabel_ayuda_opciones_arriba = new javax.swing.JLabel();
        jLabel_ayuda_restricciones = new javax.swing.JLabel();
        jLabel_ayuda_opciones_abajo = new javax.swing.JLabel();
        jLabel_ayuda_errores = new javax.swing.JLabel();
        jLabel_ayuda_restricciones_insertar = new javax.swing.JLabel();
        jDialog_licencia = new javax.swing.JDialog();
        jLabel54 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel55 = new javax.swing.JLabel();
        jDialog_about = new javax.swing.JDialog();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
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
        jButton_addRestriction = new javax.swing.JButton();
        jCheckBoxObeyRobots = new javax.swing.JCheckBox();
        checkboxSaveInFolder = new javax.swing.JCheckBox();
        checkboxGenerateMatrix = new javax.swing.JCheckBox();
        checkboxConcatenateResults = new javax.swing.JCheckBox();
        buttonGenerateMatrix = new javax.swing.JButton();
        buttonSaveInFolder = new javax.swing.JButton();
        buttonConcatenateResults = new javax.swing.JButton();
        buttonStartCrawler = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList_url = new javax.swing.JList();
        jButton_editRestriction = new javax.swing.JButton();
        jButton_deleteRestriction = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem_iniciar = new javax.swing.JMenuItem();
        jMenuItem_quit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem_ayuda = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        JDialog_Run.setMinimumSize(new java.awt.Dimension(600, 580));
        JDialog_Run.setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
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
        jScrollPaneRun.setViewportView(jTextAreaRun);

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
                    .addComponent(jScrollPaneRun, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE))
                .addContainerGap())
        );
        JDialog_RunLayout.setVerticalGroup(
            JDialog_RunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JDialog_RunLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneRun, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jDialog_restriccion_add.setMinimumSize(new java.awt.Dimension(380, 190));
        jDialog_restriccion_add.setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        jDialog_restriccion_add.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        jDialog_restriccion_add.setResizable(false);

        jButton_restriccion_aceptar.setText("Añadir");
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

        jLabel10.setText("Valor:");

        jTextField_restriction_value.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_restriction_valueActionPerformed(evt);
            }
        });

        jCheckBox_restriction_regexp.setText("Tratar como expresión regular");
        jCheckBox_restriction_regexp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_restriction_regexpActionPerformed(evt);
            }
        });

        jLabel11.setText("Tipo:");

        jComboBox_restriction_type.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Enlace", "Página" }));

        jCheckBox_restriction_invert.setText("Invertir restricción");

        javax.swing.GroupLayout jDialog_restriccion_addLayout = new javax.swing.GroupLayout(jDialog_restriccion_add.getContentPane());
        jDialog_restriccion_add.getContentPane().setLayout(jDialog_restriccion_addLayout);
        jDialog_restriccion_addLayout.setHorizontalGroup(
            jDialog_restriccion_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog_restriccion_addLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog_restriccion_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog_restriccion_addLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton_restriccion_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_restriccion_aceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jDialog_restriccion_addLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_restriction_value))
                    .addGroup(jDialog_restriccion_addLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox_restriction_type, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jDialog_restriccion_addLayout.createSequentialGroup()
                        .addGroup(jDialog_restriccion_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox_restriction_invert)
                            .addComponent(jCheckBox_restriction_regexp))
                        .addGap(0, 116, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jDialog_restriccion_addLayout.setVerticalGroup(
            jDialog_restriccion_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog_restriccion_addLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog_restriccion_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField_restriction_value, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog_restriccion_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jComboBox_restriction_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox_restriction_regexp)
                .addGroup(jDialog_restriccion_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog_restriccion_addLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox_restriction_invert)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog_restriccion_addLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                        .addGroup(jDialog_restriccion_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_restriccion_cancelar)
                            .addComponent(jButton_restriccion_aceptar))
                        .addContainerGap())))
        );

        jMenuItem_menu_lista_modificar.setText("Modificar");
        jMenuItem_menu_lista_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_menu_lista_modificarActionPerformed(evt);
            }
        });
        jPopupMenu_lista.add(jMenuItem_menu_lista_modificar);

        jMenuItem_menu_lista_eliminar.setText("Eliminar");
        jMenuItem_menu_lista_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_menu_lista_eliminarActionPerformed(evt);
            }
        });
        jPopupMenu_lista.add(jMenuItem_menu_lista_eliminar);

        jMenuItem_menu_lista_aniadir.setText("Añadir");
        jMenuItem_menu_lista_aniadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_menu_lista_aniadirActionPerformed(evt);
            }
        });
        jPopupMenu_lista.add(jMenuItem_menu_lista_aniadir);

        jDialog_edit_url.setMinimumSize(new java.awt.Dimension(402, 155));
        jDialog_edit_url.setModal(true);
        jDialog_edit_url.setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        jDialog_edit_url.setResizable(false);

        jButton_edit_url_aceptar.setText("Aceptar");
        jButton_edit_url_aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_edit_url_aceptarActionPerformed(evt);
            }
        });

        jButton_edit_url_cancelar.setText("Cancelar");
        jButton_edit_url_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_edit_url_cancelarActionPerformed(evt);
            }
        });

        jLabel9.setText("Introduzca la url");

        javax.swing.GroupLayout jDialog_edit_urlLayout = new javax.swing.GroupLayout(jDialog_edit_url.getContentPane());
        jDialog_edit_url.getContentPane().setLayout(jDialog_edit_urlLayout);
        jDialog_edit_urlLayout.setHorizontalGroup(
            jDialog_edit_urlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog_edit_urlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog_edit_urlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jDialog_edit_urlLayout.createSequentialGroup()
                        .addGroup(jDialog_edit_urlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_edit_url)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog_edit_urlLayout.createSequentialGroup()
                                .addGap(0, 181, Short.MAX_VALUE)
                                .addComponent(jButton_edit_url_cancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton_edit_url_aceptar)))
                        .addContainerGap())))
        );
        jDialog_edit_urlLayout.setVerticalGroup(
            jDialog_edit_urlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog_edit_urlLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_edit_url, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(jDialog_edit_urlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_edit_url_cancelar)
                    .addComponent(jButton_edit_url_aceptar))
                .addContainerGap())
        );

        jFileChooser_folder.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        jFileChooser_folder.setCurrentDirectory(new java.io.File("C:\\tmp"));
        jFileChooser_folder.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        jFileChooser_folder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser_folderActionPerformed(evt);
            }
        });

        jFileChooser_file.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        jFileChooser_file.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser_fileActionPerformed(evt);
            }
        });

        jFileChooser_matrix.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        jFileChooser_matrix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser_matrixActionPerformed(evt);
            }
        });

        jDialog_restriccion_edit.setMinimumSize(new java.awt.Dimension(380, 180));
        jDialog_restriccion_edit.setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        jDialog_restriccion_edit.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        jDialog_restriccion_edit.setResizable(false);

        jButton_restriccionedit_aceptar.setText("Modificar");
        jButton_restriccionedit_aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_restriccionedit_aceptarActionPerformed(evt);
            }
        });

        jButton_restriccionedit_cancelar.setText("Cancelar");
        jButton_restriccionedit_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_restriccionedit_cancelarActionPerformed(evt);
            }
        });

        jLabel14.setText("Valor:");

        jTextField_restrictionedit_value.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_restrictionedit_valueActionPerformed(evt);
            }
        });

        jCheckBox_restrictionedit_regexp.setText("Tratar como expresión regular");
        jCheckBox_restrictionedit_regexp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_restrictionedit_regexpActionPerformed(evt);
            }
        });

        jLabel15.setText("Tipo:");

        jComboBox_restrictionedit_type.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Enlace", "Página" }));

        jCheckBox_restrictionedit_invert.setText("Invertir restricción");

        javax.swing.GroupLayout jDialog_restriccion_editLayout = new javax.swing.GroupLayout(jDialog_restriccion_edit.getContentPane());
        jDialog_restriccion_edit.getContentPane().setLayout(jDialog_restriccion_editLayout);
        jDialog_restriccion_editLayout.setHorizontalGroup(
            jDialog_restriccion_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog_restriccion_editLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog_restriccion_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog_restriccion_editLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton_restriccionedit_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_restriccionedit_aceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jDialog_restriccion_editLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_restrictionedit_value))
                    .addGroup(jDialog_restriccion_editLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox_restrictionedit_type, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jDialog_restriccion_editLayout.createSequentialGroup()
                        .addGroup(jDialog_restriccion_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox_restrictionedit_invert)
                            .addComponent(jCheckBox_restrictionedit_regexp))
                        .addGap(0, 116, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jDialog_restriccion_editLayout.setVerticalGroup(
            jDialog_restriccion_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog_restriccion_editLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog_restriccion_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextField_restrictionedit_value, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog_restriccion_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jComboBox_restrictionedit_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox_restrictionedit_regexp)
                .addGroup(jDialog_restriccion_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog_restriccion_editLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox_restrictionedit_invert)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog_restriccion_editLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                        .addGroup(jDialog_restriccion_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_restriccionedit_cancelar)
                            .addComponent(jButton_restriccionedit_aceptar))
                        .addContainerGap())))
        );

        jFrame_ayuda.setMinimumSize(new java.awt.Dimension(670, 405));
        jFrame_ayuda.setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Bienvenido a la ayuda");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(116, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel_ayuda_url.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("En la lista introduzca las ulrs a las que quiera aplicarle el Crawler,");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel20.setText("si quiere modificar, añadir o eliminar cualquier url pulse el boton derecho ");

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel21.setText("del raton, para que le aparezca el submenu con las correspondientes ");

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setText("opciones.");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto/submenu_ayuda.jpg"))); // NOI18N
        jButton1.setBorderPainted(false);

        javax.swing.GroupLayout jPanel_ayuda_urlLayout = new javax.swing.GroupLayout(jPanel_ayuda_url);
        jPanel_ayuda_url.setLayout(jPanel_ayuda_urlLayout);
        jPanel_ayuda_urlLayout.setHorizontalGroup(
            jPanel_ayuda_urlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ayuda_urlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_ayuda_urlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_ayuda_urlLayout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)))
            .addGroup(jPanel_ayuda_urlLayout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel_ayuda_urlLayout.setVerticalGroup(
            jPanel_ayuda_urlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ayuda_urlLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 65, Short.MAX_VALUE))
        );

        jLayeredPane_ayuda.add(jPanel_ayuda_url);
        jPanel_ayuda_url.setBounds(0, 0, 470, 320);

        jPanel_ayuda_opcionesarriba.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel23.setText("Opciones superiores:");

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel24.setText("    1. Alcance: Indica si el crawler tiene que visitar las paginas que cuelguen");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel25.setText("    de un servidor, del mismo subarbol o toda la web");

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel26.setText("    2. Limite de saltos: Establece la profundidad de busqueda, 0 solo los enlaces");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel27.setText("    de esa pagina, 1 los enlaces de los enlaces, etc. Notese que consumira más");

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel28.setText("    memoria cuanta más profundidad establezca.");

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel29.setText("    3. Tipo de exploración: indica si queremos que recorra niveles o se introduzca");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel30.setText("     hasta que llegue el final en profundidad.");

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel31.setText("    4. Enlaces: indica que queremos descargar, html, html e imagenes o todo.");

        javax.swing.GroupLayout jPanel_ayuda_opcionesarribaLayout = new javax.swing.GroupLayout(jPanel_ayuda_opcionesarriba);
        jPanel_ayuda_opcionesarriba.setLayout(jPanel_ayuda_opcionesarribaLayout);
        jPanel_ayuda_opcionesarribaLayout.setHorizontalGroup(
            jPanel_ayuda_opcionesarribaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel_ayuda_opcionesarribaLayout.setVerticalGroup(
            jPanel_ayuda_opcionesarribaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ayuda_opcionesarribaLayout.createSequentialGroup()
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jLayeredPane_ayuda.add(jPanel_ayuda_opcionesarriba);
        jPanel_ayuda_opcionesarriba.setBounds(0, 0, 470, 320);

        jPanel_ayuda_restricciones.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto/tabla_restricciones.jpg"))); // NOI18N
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("Esta es la tabla de restricciones en la cual se muestran los valores de las");

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel32.setText("restricciones actuales, si queremos eliminar o modificar una restriccion, clickamos ");

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel33.setText("sobre la fila y pinchamos sobre la opción que queremos aplicar.");

        javax.swing.GroupLayout jPanel_ayuda_restriccionesLayout = new javax.swing.GroupLayout(jPanel_ayuda_restricciones);
        jPanel_ayuda_restricciones.setLayout(jPanel_ayuda_restriccionesLayout);
        jPanel_ayuda_restriccionesLayout.setHorizontalGroup(
            jPanel_ayuda_restriccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ayuda_restriccionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_ayuda_restriccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel_ayuda_restriccionesLayout.createSequentialGroup()
                .addComponent(jButton2)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel_ayuda_restriccionesLayout.setVerticalGroup(
            jPanel_ayuda_restriccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ayuda_restriccionesLayout.createSequentialGroup()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel33)
                .addGap(0, 62, Short.MAX_VALUE))
        );

        jLayeredPane_ayuda.add(jPanel_ayuda_restricciones);
        jPanel_ayuda_restricciones.setBounds(0, 0, 470, 320);

        jPanel_ayuda_restricciones_insertar.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/proyecto/aniadir_restriccion.jpg"))); // NOI18N
        jButton3.setBorderPainted(false);

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel34.setText("Como podemos ver en las restricciones tenemos:");

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel35.setText("1. Valor: colocamos una cadena de caracteres.");

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel36.setText("2. Tipo: enlace si queremos que se lo aplique a la url, o página para el contenido");

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel37.setText("3. Tratar como expresión regular: si no lo marcamos buscara la cadena");

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel38.setText("establecida en valor, sino sera una expresión regular.");

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel39.setText("4. Invertir restricción: sirve para que solo de descargue las que tengan valor o al ");

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel40.setText("revés.");

        javax.swing.GroupLayout jPanel_ayuda_restricciones_insertarLayout = new javax.swing.GroupLayout(jPanel_ayuda_restricciones_insertar);
        jPanel_ayuda_restricciones_insertar.setLayout(jPanel_ayuda_restricciones_insertarLayout);
        jPanel_ayuda_restricciones_insertarLayout.setHorizontalGroup(
            jPanel_ayuda_restricciones_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ayuda_restricciones_insertarLayout.createSequentialGroup()
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel_ayuda_restricciones_insertarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_ayuda_restricciones_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_ayuda_restricciones_insertarLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel_ayuda_restricciones_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37)
                            .addGroup(jPanel_ayuda_restricciones_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel39)
                            .addGroup(jPanel_ayuda_restricciones_insertarLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel_ayuda_restricciones_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel38)
                                    .addComponent(jLabel40))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_ayuda_restricciones_insertarLayout.setVerticalGroup(
            jPanel_ayuda_restricciones_insertarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ayuda_restricciones_insertarLayout.createSequentialGroup()
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel40)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jLayeredPane_ayuda.add(jPanel_ayuda_restricciones_insertar);
        jPanel_ayuda_restricciones_insertar.setBounds(0, 0, 470, 320);

        jPanel_ayuda_opcionesabajo.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel41.setText("Opciones inferiores:");

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel42.setText("1. Obedecer ficheros robots.txt: si esta opción esta marcada respetara los ");

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel43.setText("ficheros.");

        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel44.setText("2. Guardar en carpeta: selecciona una carpeta para guardar los ficheros que se ");

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel45.setText("descargue.");

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel47.setText("3. Generar matriz de adyacencia: para generar la matriz y guardarla en un fichero.");

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel49.setText("4. Concatenar ficheros: para unir todos los string en un fichero .arff.");

        javax.swing.GroupLayout jPanel_ayuda_opcionesabajoLayout = new javax.swing.GroupLayout(jPanel_ayuda_opcionesabajo);
        jPanel_ayuda_opcionesabajo.setLayout(jPanel_ayuda_opcionesabajoLayout);
        jPanel_ayuda_opcionesabajoLayout.setHorizontalGroup(
            jPanel_ayuda_opcionesabajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel_ayuda_opcionesabajoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_ayuda_opcionesabajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel42)
                    .addComponent(jLabel44)
                    .addGroup(jPanel_ayuda_opcionesabajoLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel_ayuda_opcionesabajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel43)
                            .addComponent(jLabel45)))
                    .addComponent(jLabel47)
                    .addComponent(jLabel49))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel_ayuda_opcionesabajoLayout.setVerticalGroup(
            jPanel_ayuda_opcionesabajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ayuda_opcionesabajoLayout.createSequentialGroup()
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
        );

        jLayeredPane_ayuda.add(jPanel_ayuda_opcionesabajo);
        jPanel_ayuda_opcionesabajo.setBounds(0, 0, 470, 320);

        jPanel_ayuda_errores.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel46.setText("Errores");

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel48.setText("El principal error es que la libreria Crawler consume mucha memoria, y puede ser ");

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel50.setText("que el Crawler produzca un error de memoria, es decir, que se quede sin ");

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel51.setText("memoria. Si esto le ha sucedido por favor entre en este link:");

        jTextField1.setEditable(false);
        jTextField1.setText("http://es.wikihow.com/aumentar-la-memoria-Java-en-Windows-7");

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel52.setText("Este link lo hace desde la interfaz:");

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel53.setText("Este link lo soluciona desde la consola");

        jTextField2.setEditable(false);
        jTextField2.setText("http://bit.ly/1Guptbi");

        javax.swing.GroupLayout jPanel_ayuda_erroresLayout = new javax.swing.GroupLayout(jPanel_ayuda_errores);
        jPanel_ayuda_errores.setLayout(jPanel_ayuda_erroresLayout);
        jPanel_ayuda_erroresLayout.setHorizontalGroup(
            jPanel_ayuda_erroresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ayuda_erroresLayout.createSequentialGroup()
                .addGroup(jPanel_ayuda_erroresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_ayuda_erroresLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel48))
                    .addGroup(jPanel_ayuda_erroresLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel51))
                    .addGroup(jPanel_ayuda_erroresLayout.createSequentialGroup()
                        .addGap(211, 211, 211)
                        .addComponent(jLabel46))
                    .addGroup(jPanel_ayuda_erroresLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel_ayuda_erroresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                            .addComponent(jLabel52)
                            .addComponent(jLabel53)
                            .addComponent(jTextField2)))
                    .addGroup(jPanel_ayuda_erroresLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel50)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel_ayuda_erroresLayout.setVerticalGroup(
            jPanel_ayuda_erroresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ayuda_erroresLayout.createSequentialGroup()
                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );

        jLayeredPane_ayuda.add(jPanel_ayuda_errores);
        jPanel_ayuda_errores.setBounds(0, 0, 470, 320);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("Contenido:");

        jLabel_ayuda_url.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_ayuda_url.setForeground(new java.awt.Color(0, 0, 204));
        jLabel_ayuda_url.setText("<html><span style=\"text-decoration: underline;\">1. Urls</span></html>");
        jLabel_ayuda_url.setToolTipText("");
        jLabel_ayuda_url.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_ayuda_url.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_ayuda_urlMouseClicked(evt);
            }
        });

        jLabel_ayuda_opciones_arriba.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_ayuda_opciones_arriba.setForeground(new java.awt.Color(0, 0, 204));
        jLabel_ayuda_opciones_arriba.setText("<html><span style=\"text-decoration: underline;\">2. Opciones Superiores</span></html>");
        jLabel_ayuda_opciones_arriba.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_ayuda_opciones_arriba.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_ayuda_opciones_arribaMouseClicked(evt);
            }
        });

        jLabel_ayuda_restricciones.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_ayuda_restricciones.setForeground(new java.awt.Color(0, 0, 204));
        jLabel_ayuda_restricciones.setText("<html><span style=\"text-decoration: underline;\">3. Restricciones</span></html>");
        jLabel_ayuda_restricciones.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_ayuda_restricciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_ayuda_restriccionesMouseClicked(evt);
            }
        });

        jLabel_ayuda_opciones_abajo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_ayuda_opciones_abajo.setForeground(new java.awt.Color(0, 0, 204));
        jLabel_ayuda_opciones_abajo.setText("<html><span style=\"text-decoration: underline;\">4. Opciones Inferiores</span></html>");
        jLabel_ayuda_opciones_abajo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_ayuda_opciones_abajo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_ayuda_opciones_abajoMouseClicked(evt);
            }
        });

        jLabel_ayuda_errores.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_ayuda_errores.setForeground(new java.awt.Color(0, 0, 204));
        jLabel_ayuda_errores.setText("<html><span style=\"text-decoration: underline;\">5. Errores</span></html>");
        jLabel_ayuda_errores.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_ayuda_errores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_ayuda_erroresMouseClicked(evt);
            }
        });

        jLabel_ayuda_restricciones_insertar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_ayuda_restricciones_insertar.setForeground(new java.awt.Color(0, 0, 204));
        jLabel_ayuda_restricciones_insertar.setText("<html><span style=\"text-decoration: underline;\">3.1. Insertar o modificar</span></html>");
        jLabel_ayuda_restricciones_insertar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_ayuda_restricciones_insertar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_ayuda_restricciones_insertarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_ayuda_url)
                            .addComponent(jLabel_ayuda_opciones_arriba)
                            .addComponent(jLabel_ayuda_opciones_abajo)
                            .addComponent(jLabel_ayuda_errores))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel_ayuda_restricciones)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 12, Short.MAX_VALUE)
                                .addComponent(jLabel_ayuda_restricciones_insertar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(17, 17, 17))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel_ayuda_url, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel_ayuda_opciones_arriba, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel_ayuda_restricciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel_ayuda_restricciones_insertar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel_ayuda_opciones_abajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel_ayuda_errores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(196, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jFrame_ayudaLayout = new javax.swing.GroupLayout(jFrame_ayuda.getContentPane());
        jFrame_ayuda.getContentPane().setLayout(jFrame_ayudaLayout);
        jFrame_ayudaLayout.setHorizontalGroup(
            jFrame_ayudaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrame_ayudaLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jFrame_ayudaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLayeredPane_ayuda, javax.swing.GroupLayout.Alignment.TRAILING)))
        );
        jFrame_ayudaLayout.setVerticalGroup(
            jFrame_ayudaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame_ayudaLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane_ayuda))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jDialog_licencia.setMinimumSize(new java.awt.Dimension(557, 196));
        jDialog_licencia.setModal(true);
        jDialog_licencia.setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        jDialog_licencia.setResizable(false);

        jLabel54.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel54.setText("Licencia pmgc.");

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton4.setText("Aceptar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel55.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel55.setText("es decir, GRATUITA previo pago de 50 euros por cabeza.");

        javax.swing.GroupLayout jDialog_licenciaLayout = new javax.swing.GroupLayout(jDialog_licencia.getContentPane());
        jDialog_licencia.getContentPane().setLayout(jDialog_licenciaLayout);
        jDialog_licenciaLayout.setHorizontalGroup(
            jDialog_licenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog_licenciaLayout.createSequentialGroup()
                .addGroup(jDialog_licenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog_licenciaLayout.createSequentialGroup()
                        .addGap(197, 197, 197)
                        .addComponent(jLabel54))
                    .addGroup(jDialog_licenciaLayout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(jLabel55))
                    .addGroup(jDialog_licenciaLayout.createSequentialGroup()
                        .addGap(191, 191, 191)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(132, Short.MAX_VALUE))
        );
        jDialog_licenciaLayout.setVerticalGroup(
            jDialog_licenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog_licenciaLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel54)
                .addGap(18, 18, 18)
                .addComponent(jLabel55)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        jDialog_about.setMinimumSize(new java.awt.Dimension(557, 200));
        jDialog_about.setModal(true);
        jDialog_about.setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        jDialog_about.setPreferredSize(new java.awt.Dimension(557, 200));
        jDialog_about.setResizable(false);

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel56.setText("Aplicación creada por:");

        jLabel57.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel57.setText("Pablo Molina");

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel58.setText("Email: pmg00015@red.ujaen.es");

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel59.setText("Email: gcm00014@red.ujaen.es");

        jLabel60.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel60.setText("Gregorio Carazo");

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton5.setText("Aceptar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog_aboutLayout = new javax.swing.GroupLayout(jDialog_about.getContentPane());
        jDialog_about.getContentPane().setLayout(jDialog_aboutLayout);
        jDialog_aboutLayout.setHorizontalGroup(
            jDialog_aboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog_aboutLayout.createSequentialGroup()
                .addGroup(jDialog_aboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog_aboutLayout.createSequentialGroup()
                        .addGap(196, 196, 196)
                        .addComponent(jLabel56))
                    .addGroup(jDialog_aboutLayout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addGroup(jDialog_aboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jDialog_aboutLayout.createSequentialGroup()
                                .addComponent(jLabel60)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel59))
                            .addGroup(jDialog_aboutLayout.createSequentialGroup()
                                .addComponent(jLabel57)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel58))))
                    .addGroup(jDialog_aboutLayout.createSequentialGroup()
                        .addGap(216, 216, 216)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(121, Short.MAX_VALUE))
        );
        jDialog_aboutLayout.setVerticalGroup(
            jDialog_aboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog_aboutLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel56)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDialog_aboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(jLabel58))
                .addGap(18, 18, 18)
                .addGroup(jDialog_aboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(jLabel59))
                .addGap(18, 18, 18)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(440, 740));
        setResizable(false);

        jLabel1.setText("Alcance:");

        jLabel2.setText("Límite de saltos:");

        jComboBoxScope.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Subárbol", "Servidor", "Web" }));
        jComboBoxScope.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxScopeActionPerformed(evt);
            }
        });

        jSpinnerMaxDepth.setValue(1);

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

        jButton_addRestriction.setText("Añadir");
        jButton_addRestriction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_addRestrictionActionPerformed(evt);
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
        buttonGenerateMatrix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGenerateMatrixActionPerformed(evt);
            }
        });

        buttonSaveInFolder.setText("Examinar...");
        buttonSaveInFolder.setEnabled(false);
        buttonSaveInFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveInFolderActionPerformed(evt);
            }
        });

        buttonConcatenateResults.setText("Examinar...");
        buttonConcatenateResults.setEnabled(false);
        buttonConcatenateResults.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonConcatenateResultsActionPerformed(evt);
            }
        });

        buttonStartCrawler.setText("Iniciar rastreo");
        buttonStartCrawler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonStartCrawlerActionPerformed(evt);
            }
        });

        jList_url.setName(""); // NOI18N
        jList_url.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList_urlMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jList_url);

        jButton_editRestriction.setText("Modificar");
        jButton_editRestriction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_editRestrictionActionPerformed(evt);
            }
        });

        jButton_deleteRestriction.setText("Eliminar");
        jButton_deleteRestriction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_deleteRestrictionActionPerformed(evt);
            }
        });

        jLabel12.setText("Lista de URLS:");

        jLabel13.setText("Restricciones de acceso:");

        jMenu1.setText("Archivo");

        jMenuItem_iniciar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem_iniciar.setText("Iniciar rastreo");
        jMenuItem_iniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_iniciarActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem_iniciar);

        jMenuItem_quit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem_quit.setText("Salir");
        jMenuItem_quit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_quitActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem_quit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ayuda");

        jMenuItem_ayuda.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem_ayuda.setText("Mostrar ayuda");
        jMenuItem_ayuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_ayudaActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem_ayuda);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Licencia");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("Acerca de");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
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
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jCheckBoxObeyRobots)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jButton_deleteRestriction)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton_editRestriction)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton_addRestriction, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel13))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_addRestriction)
                    .addComponent(jButton_editRestriction)
                    .addComponent(jButton_deleteRestriction))
                .addGap(18, 18, 18)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonStartCrawler, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_addRestrictionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_addRestrictionActionPerformed
        this.jDialog_restriccion_add.setLocationRelativeTo(this);
        this.jDialog_restriccion_add.setVisible(true);
    }//GEN-LAST:event_jButton_addRestrictionActionPerformed

    private void jCheckBoxObeyRobotsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxObeyRobotsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxObeyRobotsActionPerformed

    private void checkboxGenerateMatrixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxGenerateMatrixActionPerformed
        this.buttonGenerateMatrix.setEnabled(this.checkboxGenerateMatrix.isSelected());
        if (!this.checkboxGenerateMatrix.isSelected()) {
            main.SAVE_MATRIX = null;
        }
    }//GEN-LAST:event_checkboxGenerateMatrixActionPerformed

    private void buttonSaveInFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveInFolderActionPerformed
        this.jFileChooser_folder.showDialog(this, null);
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
        this.runCrawler(this.restricciones);
        this.buttonToggleCrawler.setText("Pausar");
        this.JDialog_Run.setLocationRelativeTo(this);
        this.JDialog_Run.setModal(true);
        this.JDialog_Run.setVisible(true);
    }//GEN-LAST:event_buttonStartCrawlerActionPerformed

    private void jComboBoxScopeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxScopeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxScopeActionPerformed

    private void checkboxSaveInFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxSaveInFolderActionPerformed
        this.buttonSaveInFolder.setEnabled(this.checkboxSaveInFolder.isSelected());
        if (!this.checkboxSaveInFolder.isSelected()) {
            main.SAVE_FOLDER = null;
        }
    }//GEN-LAST:event_checkboxSaveInFolderActionPerformed

    private void checkboxConcatenateResultsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxConcatenateResultsActionPerformed
        this.buttonConcatenateResults.setEnabled(this.checkboxConcatenateResults.isSelected());
        if (!this.checkboxConcatenateResults.isSelected()) {
            main.SAVE_FILE = null;
        }
    }//GEN-LAST:event_checkboxConcatenateResultsActionPerformed
       
    private void jButton_restriccion_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_restriccion_cancelarActionPerformed
        jTextField_restriction_value.setText("Introducir expresion regular");
        jDialog_restriccion_add.setVisible(false);
    }//GEN-LAST:event_jButton_restriccion_cancelarActionPerformed

    private void jButton_restriccion_aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_restriccion_aceptarActionPerformed
        String tipo = jComboBox_restriction_type.getSelectedItem().toString();
        boolean invertir = jCheckBox_restriction_invert.isSelected();
        boolean expresion = jCheckBox_restriction_regexp.isSelected();
        String valor = jTextField_restriction_value.getText();
        Restriccion linea_restriccion = new Restriccion(tipo, invertir, expresion, valor);
        
        Object[] restriccion = {
            tipo,
            (invertir) ? "Sí" : "No",
            (expresion) ? "Sí" : "No",
            jTextField_restriction_value.getText()
        };
        
        tabla.addRow(restriccion);
        restricciones.add(linea_restriccion);
        jTextField_restriction_value.setText("Introducir expresion regular");
        jDialog_restriccion_add.setVisible(false);
    }//GEN-LAST:event_jButton_restriccion_aceptarActionPerformed

    private void buttonToggleCrawlerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonToggleCrawlerActionPerformed
        if (buttonToggleCrawler.getText().equals("Pausar")) {
            this.log("Pausando rastreo");
            this.buttonToggleCrawler.setText("Continuar");
            this.getCrawler().pause();
        } else {
            this.log("Reanudando rastreo");
            this.buttonToggleCrawler.setText("Pausar");
            this.runCrawler(this.restricciones);
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

    private void jList_urlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_urlMouseClicked
        // TODO add your handling code here:
        jList_url.setSelectedIndex(jList_url.locationToIndex(evt.getPoint()));
        if(evt.getButton() == java.awt.event.MouseEvent.BUTTON3 && jList_url.getSelectedIndex() != -1){
            jPopupMenu_lista.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jList_urlMouseClicked

    private void jMenuItem_menu_lista_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_menu_lista_modificarActionPerformed
        // TODO add your handling code here:
        jTextField_edit_url.setText((String) jList_url.getSelectedValue());
        modificar_url = true;
        jDialog_edit_url.setLocationRelativeTo(this);
        jDialog_edit_url.setVisible(true);
    }//GEN-LAST:event_jMenuItem_menu_lista_modificarActionPerformed

    private void jButton_edit_url_aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_edit_url_aceptarActionPerformed
        // TODO add your handling code here:
        if(modificar_url == true)
            lista.setElementAt(jTextField_edit_url.getText(), jList_url.getSelectedIndex());
        else
            lista.addElement(jTextField_edit_url.getText());
        jDialog_edit_url.setVisible(false);
    }//GEN-LAST:event_jButton_edit_url_aceptarActionPerformed

    private void jMenuItem_menu_lista_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_menu_lista_eliminarActionPerformed
        // TODO add your handling code here:
        lista.removeElementAt(jList_url.getSelectedIndex());
    }//GEN-LAST:event_jMenuItem_menu_lista_eliminarActionPerformed

    private void jButton_edit_url_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_edit_url_cancelarActionPerformed
        // TODO add your handling code here:
        jDialog_edit_url.setVisible(false);
    }//GEN-LAST:event_jButton_edit_url_cancelarActionPerformed

    private void jMenuItem_menu_lista_aniadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_menu_lista_aniadirActionPerformed
        // TODO add your handling code here:
        jTextField_edit_url.setText("");
        jDialog_edit_url.setLocationRelativeTo(this);
        jDialog_edit_url.setVisible(true);
    }//GEN-LAST:event_jMenuItem_menu_lista_aniadirActionPerformed

    private void jFileChooser_folderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser_folderActionPerformed
        main.SAVE_FOLDER = this.jFileChooser_folder.getSelectedFile();
    }//GEN-LAST:event_jFileChooser_folderActionPerformed

    private void jFileChooser_fileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser_fileActionPerformed
        main.SAVE_FILE = this.jFileChooser_file.getSelectedFile();
    }//GEN-LAST:event_jFileChooser_fileActionPerformed

    private void buttonConcatenateResultsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonConcatenateResultsActionPerformed
        this.jFileChooser_file.showDialog(this, null);
    }//GEN-LAST:event_buttonConcatenateResultsActionPerformed

    private void jMenuItem_ayudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_ayudaActionPerformed
        // TODO add your handling code here:
        inicia_ayuda();
        jFrame_ayuda.setVisible(true);
    }//GEN-LAST:event_jMenuItem_ayudaActionPerformed

    private void jLabel_ayuda_urlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_ayuda_urlMouseClicked
        // TODO add your handling code here:
        inicia_ayuda();
        System.out.println();
        jPanel_ayuda_url.setVisible(true);
        jLabel_ayuda_url.setForeground(color_link_visitado);
        jLayeredPane_ayuda.moveToFront(jPanel_ayuda_url);
    }//GEN-LAST:event_jLabel_ayuda_urlMouseClicked

    private void jLabel_ayuda_opciones_arribaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_ayuda_opciones_arribaMouseClicked
        // TODO add your handling code here:
        inicia_ayuda();
        jPanel_ayuda_opcionesarriba.setVisible(true);
        jLabel_ayuda_opciones_arriba.setForeground(color_link_visitado);
        jLayeredPane_ayuda.moveToFront(jPanel_ayuda_opcionesarriba);
    }//GEN-LAST:event_jLabel_ayuda_opciones_arribaMouseClicked

    private void jTextField_restriction_valueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_restriction_valueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_restriction_valueActionPerformed

    private void jCheckBox_restriction_regexpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_restriction_regexpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox_restriction_regexpActionPerformed

    private void jButton_editRestrictionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_editRestrictionActionPerformed
        int selectedRow = jTable_tabla_restricciones.getSelectedRow();
        if (selectedRow != -1) {
            Restriccion restriction = restricciones.get(selectedRow);
            jTextField_restrictionedit_value.setText(restriction.getValor());
            jComboBox_restrictionedit_type.setSelectedIndex(restriction.getTipo());
            jCheckBox_restrictionedit_regexp.setSelected(restriction.getExpresion());
            jCheckBox_restrictionedit_invert.setSelected(restriction.getInvertir());
            jDialog_restriccion_edit.setLocationRelativeTo(this);
            jDialog_restriccion_edit.setVisible(true);
        }
    }//GEN-LAST:event_jButton_editRestrictionActionPerformed

    private void jButton_deleteRestrictionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_deleteRestrictionActionPerformed
        int selectedRow = jTable_tabla_restricciones.getSelectedRow();
        if (selectedRow != -1) {
            tabla.removeRow(selectedRow);
            restricciones.remove(selectedRow);
        }
    }//GEN-LAST:event_jButton_deleteRestrictionActionPerformed

    private void jButton_restriccionedit_aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_restriccionedit_aceptarActionPerformed
        int selectedRow = jTable_tabla_restricciones.getSelectedRow();
        Restriccion restriction = restricciones.get(selectedRow);
        
        restriction.setValor(jTextField_restrictionedit_value.getText());
        restriction.setTipo(jComboBox_restrictionedit_type.getSelectedItem().toString());
        restriction.setExpresion(jCheckBox_restrictionedit_regexp.isSelected());
        restriction.setInvertir(jCheckBox_restrictionedit_invert.isSelected());
        
        tabla.setValueAt(jComboBox_restrictionedit_type.getSelectedItem().toString(), selectedRow, 0);
        tabla.setValueAt((restriction.getInvertir()) ? "Sí" : "No", selectedRow, 1);
        tabla.setValueAt((restriction.getExpresion()) ? "Sí" : "No", selectedRow, 2);
        tabla.setValueAt(restriction.getValor(), selectedRow, 3);
        jDialog_restriccion_edit.setVisible(false);
    }//GEN-LAST:event_jButton_restriccionedit_aceptarActionPerformed

    private void jButton_restriccionedit_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_restriccionedit_cancelarActionPerformed
        jDialog_restriccion_edit.setVisible(false);
    }//GEN-LAST:event_jButton_restriccionedit_cancelarActionPerformed

    private void jTextField_restrictionedit_valueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_restrictionedit_valueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_restrictionedit_valueActionPerformed

    private void jCheckBox_restrictionedit_regexpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_restrictionedit_regexpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox_restrictionedit_regexpActionPerformed

    private void jMenuItem_quitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_quitActionPerformed
        System.exit(-1);
    }//GEN-LAST:event_jMenuItem_quitActionPerformed

    private void jLabel_ayuda_restriccionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_ayuda_restriccionesMouseClicked
        // TODO add your handling code here:
        inicia_ayuda();
        jPanel_ayuda_restricciones.setVisible(true);
        jLabel_ayuda_restricciones.setForeground(color_link_visitado);
        jLayeredPane_ayuda.moveToFront(jPanel_ayuda_restricciones);
    }//GEN-LAST:event_jLabel_ayuda_restriccionesMouseClicked

    private void jLabel_ayuda_opciones_abajoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_ayuda_opciones_abajoMouseClicked
        // TODO add your handling code here:
        inicia_ayuda();
        jPanel_ayuda_opcionesabajo.setVisible(true);
        jLabel_ayuda_opciones_abajo.setForeground(color_link_visitado);
        jLayeredPane_ayuda.moveToFront(jPanel_ayuda_opcionesabajo);
    }//GEN-LAST:event_jLabel_ayuda_opciones_abajoMouseClicked

    private void jLabel_ayuda_erroresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_ayuda_erroresMouseClicked
        // TODO add your handling code here:
        inicia_ayuda();
        jPanel_ayuda_errores.setVisible(true);
        jLabel_ayuda_errores.setForeground(color_link_visitado);
        jLayeredPane_ayuda.moveToFront(jPanel_ayuda_errores);
    }//GEN-LAST:event_jLabel_ayuda_erroresMouseClicked

    private void jLabel_ayuda_restricciones_insertarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_ayuda_restricciones_insertarMouseClicked
        // TODO add your handling code here:
        inicia_ayuda();
        jPanel_ayuda_restricciones_insertar.setVisible(true);
        jLabel_ayuda_restricciones_insertar.setForeground(color_link_visitado);
        jLayeredPane_ayuda.moveToFront(jPanel_ayuda_restricciones_insertar);
    }//GEN-LAST:event_jLabel_ayuda_restricciones_insertarMouseClicked

    private void jFileChooser_matrixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser_matrixActionPerformed
        // TODO add your handling code here:
        main.SAVE_MATRIX = this.jFileChooser_matrix.getSelectedFile();
    }//GEN-LAST:event_jFileChooser_matrixActionPerformed

    private void buttonGenerateMatrixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGenerateMatrixActionPerformed
        // TODO add your handling code here:
        this.jFileChooser_matrix.showDialog(this, null);
    }//GEN-LAST:event_buttonGenerateMatrixActionPerformed

    private void jMenuItem_iniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_iniciarActionPerformed
        // TODO add your handling code here:
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
        this.runCrawler(this.restricciones);
        this.buttonToggleCrawler.setText("Pausar");
        this.JDialog_Run.setLocationRelativeTo(this);
        this.JDialog_Run.setModal(true);
        this.JDialog_Run.setVisible(true);
    }//GEN-LAST:event_jMenuItem_iniciarActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        jDialog_licencia.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        jDialog_licencia.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        jDialog_about.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        jDialog_about.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

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
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton_addRestriction;
    private javax.swing.JButton jButton_deleteRestriction;
    private javax.swing.JButton jButton_editRestriction;
    private javax.swing.JButton jButton_edit_url_aceptar;
    private javax.swing.JButton jButton_edit_url_cancelar;
    private javax.swing.JButton jButton_restriccion_aceptar;
    private javax.swing.JButton jButton_restriccion_cancelar;
    private javax.swing.JButton jButton_restriccionedit_aceptar;
    private javax.swing.JButton jButton_restriccionedit_cancelar;
    private javax.swing.JCheckBox jCheckBoxObeyRobots;
    private javax.swing.JCheckBox jCheckBox_restriction_invert;
    private javax.swing.JCheckBox jCheckBox_restriction_regexp;
    private javax.swing.JCheckBox jCheckBox_restrictionedit_invert;
    private javax.swing.JCheckBox jCheckBox_restrictionedit_regexp;
    private javax.swing.JComboBox jComboBoxExplorationType;
    private javax.swing.JComboBox jComboBoxLinkType;
    private javax.swing.JComboBox jComboBoxScope;
    private javax.swing.JComboBox jComboBox_restriction_type;
    private javax.swing.JComboBox jComboBox_restrictionedit_type;
    private javax.swing.JDialog jDialog_about;
    private javax.swing.JDialog jDialog_edit_url;
    private javax.swing.JDialog jDialog_licencia;
    private javax.swing.JDialog jDialog_restriccion_add;
    private javax.swing.JDialog jDialog_restriccion_edit;
    private javax.swing.JFileChooser jFileChooser_file;
    private javax.swing.JFileChooser jFileChooser_folder;
    private javax.swing.JFileChooser jFileChooser_matrix;
    private javax.swing.JFrame jFrame_ayuda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelErrorLinks;
    private javax.swing.JLabel jLabelSkippedLinks;
    private javax.swing.JLabel jLabelTooDeepLinks;
    private javax.swing.JLabel jLabelVisitedLinks;
    private javax.swing.JLabel jLabel_ayuda_errores;
    private javax.swing.JLabel jLabel_ayuda_opciones_abajo;
    private javax.swing.JLabel jLabel_ayuda_opciones_arriba;
    private javax.swing.JLabel jLabel_ayuda_restricciones;
    private javax.swing.JLabel jLabel_ayuda_restricciones_insertar;
    private javax.swing.JLabel jLabel_ayuda_url;
    private javax.swing.JLayeredPane jLayeredPane_ayuda;
    private javax.swing.JList jList_url;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem_ayuda;
    private javax.swing.JMenuItem jMenuItem_iniciar;
    private javax.swing.JMenuItem jMenuItem_menu_lista_aniadir;
    private javax.swing.JMenuItem jMenuItem_menu_lista_eliminar;
    private javax.swing.JMenuItem jMenuItem_menu_lista_modificar;
    private javax.swing.JMenuItem jMenuItem_quit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel_ayuda_errores;
    private javax.swing.JPanel jPanel_ayuda_opcionesabajo;
    private javax.swing.JPanel jPanel_ayuda_opcionesarriba;
    private javax.swing.JPanel jPanel_ayuda_restricciones;
    private javax.swing.JPanel jPanel_ayuda_restricciones_insertar;
    private javax.swing.JPanel jPanel_ayuda_url;
    private javax.swing.JPopupMenu jPopupMenu_lista;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPaneRun;
    private javax.swing.JSpinner jSpinnerMaxDepth;
    private javax.swing.JTable jTable_tabla_restricciones;
    private javax.swing.JTextArea jTextAreaRun;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField_edit_url;
    private javax.swing.JTextField jTextField_restriction_value;
    private javax.swing.JTextField jTextField_restrictionedit_value;
    // End of variables declaration//GEN-END:variables
}
