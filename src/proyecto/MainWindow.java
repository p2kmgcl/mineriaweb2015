/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.awt.Dialog;
import java.util.ArrayList;

/**
 *
 * @author vagrant
 */
public class MainWindow extends javax.swing.JFrame {
    private ArrayList<Restriccion> restricciones;
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        restricciones = new ArrayList<Restriccion>();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_tabla_restricciones = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        checkboxSaveInFolder = new javax.swing.JCheckBox();
        checkboxGenerateMatrix = new javax.swing.JCheckBox();
        checkboxConcatenateResults = new javax.swing.JCheckBox();
        buttonGenerateMatrix = new javax.swing.JButton();
        buttonSaveInFolder = new javax.swing.JButton();
        buttonConcatenateResults = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        jDialog_restriccion.setMinimumSize(new java.awt.Dimension(500, 243));
        jDialog_restriccion.setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        jDialog_restriccion.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        jDialog_restriccion.setPreferredSize(new java.awt.Dimension(500, 243));
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
        setMaximumSize(new java.awt.Dimension(440, 740));
        setMinimumSize(new java.awt.Dimension(440, 740));
        setPreferredSize(new java.awt.Dimension(440, 650));
        setResizable(false);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setToolTipText("");
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setText("Alcance:");

        jLabel2.setText("Límite de saltos:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Subárbol", "Servidor", "Web" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Tipo de exploración: ");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Anchura", "Profundidad" }));

        jLabel4.setText("Enlaces:");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hiperenlaces", "Imágenes", "Todos" }));

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
        jTable_tabla_restricciones.getColumnModel().getColumn(0).setResizable(false);

        jButton1.setText("Añadir restricción");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Obedecer ficheros robots.txt");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
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

        jButton5.setText("Iniciar rastreo");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
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
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSpinner1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                            .addComponent(jCheckBox1)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 5, Short.MAX_VALUE)))
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
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(14, 14, 14)
                .addComponent(jCheckBox1)
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
                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jDialog_restriccion.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void checkboxGenerateMatrixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxGenerateMatrixActionPerformed
        this.buttonGenerateMatrix.setEnabled(this.checkboxGenerateMatrix.isSelected());
    }//GEN-LAST:event_checkboxGenerateMatrixActionPerformed

    private void buttonSaveInFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveInFolderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonSaveInFolderActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

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
        if(jToggleButton_restriccion_enlace.isSelected() == true){
            switch((String) jComboBox_restriccion_cumplir.getSelectedItem()){
                case "Si":
                    restricciones.add(new Restriccion("Enlace", true,(String) jComboBox_restriccion_elemento.getSelectedItem(), jTextField_restriccion_valor.getText()));
                    break;
                case "No":
                    restricciones.add(new Restriccion("Enlace", false, (String) jComboBox_restriccion_elemento.getSelectedItem(), jTextField_restriccion_valor.getText()));
                    break;
            }
        } else{
            switch((String) jComboBox_restriccion_cumplir.getSelectedItem()){
                case "Si":
                    restricciones.add(new Restriccion("Pagina", true,(String) jComboBox_restriccion_elemento.getSelectedItem(), jTextField_restriccion_valor.getText()));
                    break;
                case "No":
                    restricciones.add(new Restriccion("Pagina", false, (String) jComboBox_restriccion_elemento.getSelectedItem(), jTextField_restriccion_valor.getText()));
                    break;
            }
        }
        
        jTextField_restriccion_valor.setText("Introducir expresion regular");
        jDialog_restriccion.setVisible(false);
    }//GEN-LAST:event_jButton_restriccion_aceptarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonConcatenateResults;
    private javax.swing.JButton buttonGenerateMatrix;
    private javax.swing.JButton buttonSaveInFolder;
    private javax.swing.JCheckBox checkboxConcatenateResults;
    private javax.swing.JCheckBox checkboxGenerateMatrix;
    private javax.swing.JCheckBox checkboxSaveInFolder;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton_restriccion_aceptar;
    private javax.swing.JButton jButton_restriccion_cancelar;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox_restriccion_cumplir;
    private javax.swing.JComboBox jComboBox_restriccion_elemento;
    private javax.swing.JDialog jDialog_restriccion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTable jTable_tabla_restricciones;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField_restriccion_valor;
    private javax.swing.JToggleButton jToggleButton_restriccion_enlace;
    private javax.swing.JToggleButton jToggleButton_restriccion_pagina;
    // End of variables declaration//GEN-END:variables
}