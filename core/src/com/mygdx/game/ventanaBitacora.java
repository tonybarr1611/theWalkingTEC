/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mygdx.game;

import com.mygdx.game.Componente.*;
import com.mygdx.game.Componentes.*;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
/**
 *
 * @author barra
 */
public class ventanaBitacora extends javax.swing.JFrame {
    ArrayList<Componente> componentes;
    /**
     * Creates new form ventanaBitacora
     */
    public ventanaBitacora(ArrayList<Componente> componentes) {
        this.componentes = componentes;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbxComponentes = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaBitacora = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
        for (Componente comp : componentes){
            model.addElement(comp.getNombre());
        }
        cbxComponentes.setModel(model);

        cbxComponentes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxComponentesActionPerformed(evt);
            }
        });

        txaBitacora.setColumns(20);
        txaBitacora.setRows(5);
        jScrollPane1.setViewportView(txaBitacora);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxComponentes, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 840, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(cbxComponentes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 701, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxComponentesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxComponentesActionPerformed
        String seleccionado = (cbxComponentes.getSelectedItem()).toString();
        for (Componente comp : componentes){
            if (comp.getNombre() == seleccionado){
                String bitacora = comp.getNombre() + "\n Vida: " + comp.getVida() + "\n Golpes por segundo: " + comp.getCantidadGolpes() + "\n";
                txaBitacora.setText(bitacora + comp.getBitacora());
            }
        }
    }//GEN-LAST:event_cbxComponentesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxComponentes;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txaBitacora;
    // End of variables declaration//GEN-END:variables
}
