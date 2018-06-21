/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Model.ItemResult;
import Search.QuerySearch;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 *
 * @author ASUS
 */
public class MainUI extends javax.swing.JFrame {

    /**
     * Creates new form MainUI
     */
    public MainUI() {
        initComponents();
        setVisible(true);
        txtContentNews.setText("");
    }

    private void initListResult(Set result) {
        DefaultListModel<ItemResult> listModel = new DefaultListModel<>();
        int i = 0;
        for (Object kv : result) {
            i++;
            if(i == 100){
                break;
            }
            listModel.addElement(new ItemResult(kv.toString().split("=")[0], kv.toString().split("=")[1]));
        }
        listResult.setModel(listModel);
        txtContentNews.setText("Done! click on item in list to view content");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButton1 = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        edtQuerySearch = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listResult = new javax.swing.JList<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtContentNews = new javax.swing.JTextArea();

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SEARCH NEWS PROJECT");
        setBackground(new java.awt.Color(255, 255, 255));
        setName("Search news"); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "SEARCH FORM", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 0, 12), new java.awt.Color(0, 0, 102))); // NOI18N

        edtQuerySearch.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        edtQuerySearch.setForeground(new java.awt.Color(0, 0, 102));
        edtQuerySearch.setText("Query search here");
        edtQuerySearch.setMargin(new java.awt.Insets(2, 5, 2, 2));
        edtQuerySearch.setPreferredSize(new java.awt.Dimension(60, 35));

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 102));
        jButton1.setText("SEARCH");
        jButton1.setPreferredSize(new java.awt.Dimension(70, 35));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(edtQuerySearch, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edtQuerySearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "RESULT FORM", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Times New Roman", 0, 12), new java.awt.Color(0, 0, 102))); // NOI18N

        listResult.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        listResult.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onItemListClicked(evt);
            }
        });
        jScrollPane3.setViewportView(listResult);

        jScrollPane1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        txtContentNews.setEditable(false);
        txtContentNews.setColumns(20);
        txtContentNews.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtContentNews.setLineWrap(true);
        txtContentNews.setRows(5);
        txtContentNews.setText("Lightweight means that the Swing component does not have native peer of it's own, it shares a (common) native peer. This native peer comes from the AWT container it is added to (this is typically the window) and is shared amongst all the Swing components within that container hierarchy...  AWT provides the \"heavy\" lifting, connecting to the native OS and providing the core channel through which Swing components get rendered. It also provides much of the native integration, such as the SystemTray, Desktop and per pixel translucency APIs which can be used by Swing  Why use Swing over AWT then....why not just use AWT?  This is matter of opinion, but generally, AWT was replaced by Swing and provides a much more flexible graphical API from which to develop. Because it doesn't rely on the platforms native components, it means you are free to develop components that you need and can be made to run across multiple platforms.  Swing also borrows much of the AWT API, including the Event Queue  JTree and JTable would be my first argument for using Swing over AWT ;)");
        txtContentNews.setAutoscrolls(false);
        txtContentNews.setCaretPosition(0);
        txtContentNews.setMargin(new java.awt.Insets(5, 5, 5, 5));
        txtContentNews.setName("txtContentNews"); // NOI18N
        txtContentNews.setOpaque(false);
        jScrollPane1.setViewportView(txtContentNews);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1)
                .addGap(27, 27, 27))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       
        txtContentNews.setText("Searching.... please wait......");
        listResult.setModel(new DefaultListModel<>());
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        initListResult(new QuerySearch().searchWithQuery(edtQuerySearch.getText().toString()));
                        
                    } catch (Exception ex) {
                        Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();

        } catch (Exception ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void onItemListClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onItemListClicked
        // TODO add your handling code here:
        System.out.println(listResult.getSelectedValue().getTittle());
        try {
            if(listResult.getSelectedValue().getTittle().contains("vnexpress") || 
                    listResult.getSelectedValue().getTittle().contains("thanhnien")){
                String text = new String(Files.readAllBytes(Paths.get("news_dataset/" +
                    listResult.getSelectedValue().getTittle())), StandardCharsets.UTF_8);
            txtContentNews.setText(text);
            } else {
                String text = new String(Files.readAllBytes(Paths.get("news_dataset/" +
                    listResult.getSelectedValue().getTittle())), StandardCharsets.UTF_16);
            txtContentNews.setText(text);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_onItemListClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField edtQuerySearch;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<ItemResult> listResult;
    private javax.swing.JTextArea txtContentNews;
    // End of variables declaration//GEN-END:variables
}
