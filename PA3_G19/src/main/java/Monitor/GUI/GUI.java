package Monitor.GUI;

import Monitor.Entities.Monitor;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import Utils.RequestMessage;

/**
 * Monitor graphical interface.
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class GUI extends javax.swing.JFrame {

    private Monitor monitor;

    /**
     * Creates new form Monitor_GUI.
     *
     * @param port monitor server port
     * @param hostname load balancer host name
     * @param lbPort load balancer port
     * @param heartbeatThreshold heartbeat threshold
     */
    public GUI(/*int port, String hostname, int lbPort, int heartbeatThreshold*/) {
        initComponents();
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelTitle = new javax.swing.JLabel();
        jLayeredPaneServerRequests = new javax.swing.JLayeredPane();
        jPanelBase = new javax.swing.JPanel();
        jButtonEnd = new javax.swing.JButton();
        jTabbedPane = new javax.swing.JTabbedPane();
        jScrollPaneLB = new javax.swing.JScrollPane();
        jTableLB = new javax.swing.JTable();
        jScrollPaneServer = new javax.swing.JScrollPane();
        jTableServer = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableClients = new javax.swing.JTable();
        jPanelServer = new javax.swing.JPanel();
        jButtonBack = new javax.swing.JButton();
        jLabelTitleServer = new javax.swing.JLabel();
        jScrollPaneRequests = new javax.swing.JScrollPane();
        jTableRequests = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        monitorPortNumberLabel = new javax.swing.JLabel();
        heartbeat = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        LBStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelTitle.setText("Monitor");
        jLabelTitle.setMaximumSize(new java.awt.Dimension(70, 22));

        jButtonEnd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonEnd.setText("Terminate");
        jButtonEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEndActionPerformed(evt);
            }
        });

        jTableLB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Request ID", "Client ID", "Server ID", "IN", "DeadLine"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPaneLB.setViewportView(jTableLB);

        jTabbedPane.addTab("Load Balancer", jScrollPaneLB);

        jTableServer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Server ID", "State", "N. of Requests", "Requests"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableServer.setColumnSelectionAllowed(true);
        jTableServer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableServerMouseClicked(evt);
            }
        });
        jScrollPaneServer.setViewportView(jTableServer);
        jTableServer.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (jTableServer.getColumnModel().getColumnCount() > 0) {
            jTableServer.getColumnModel().getColumn(3).setCellRenderer(new TableButtonRenderer());
        }
        jTableServer.setName("Server");
        jTableServer.setRowHeight(35);
        jTableServer.addMouseListener(new JTableButtonMouseListener(jTableServer));

        jTabbedPane.addTab("Server", jScrollPaneServer);

        jTableClients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Req. Pending", "Req. Being Processed", "Req. Rejected", "Req. Processed"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(jTableClients);

        jTabbedPane.addTab("Clients", jScrollPane5);

        javax.swing.GroupLayout jPanelBaseLayout = new javax.swing.GroupLayout(jPanelBase);
        jPanelBase.setLayout(jPanelBaseLayout);
        jPanelBaseLayout.setHorizontalGroup(
            jPanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBaseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanelBaseLayout.createSequentialGroup()
                .addGap(245, 245, 245)
                .addComponent(jButtonEnd)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelBaseLayout.setVerticalGroup(
            jPanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBaseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonEnd)
                .addContainerGap())
        );

        jPanelServer.setEnabled(false);

        jButtonBack.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonBack.setText("Back");
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        jLabelTitleServer.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelTitleServer.setText("Server Requests");
        jLabelTitleServer.setMaximumSize(new java.awt.Dimension(150, 17));
        jLabelTitleServer.setPreferredSize(new java.awt.Dimension(150, 17));

        jTableRequests.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Request", "Client", "Nº of Iterations", "DeadLine"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPaneRequests.setViewportView(jTableRequests);

        javax.swing.GroupLayout jPanelServerLayout = new javax.swing.GroupLayout(jPanelServer);
        jPanelServer.setLayout(jPanelServerLayout);
        jPanelServerLayout.setHorizontalGroup(
            jPanelServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelServerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneRequests, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelServerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelTitleServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(192, 192, 192))
            .addGroup(jPanelServerLayout.createSequentialGroup()
                .addGap(254, 254, 254)
                .addComponent(jButtonBack)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelServerLayout.setVerticalGroup(
            jPanelServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelServerLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(jLabelTitleServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPaneRequests, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonBack)
                .addContainerGap())
        );

        jLayeredPaneServerRequests.setLayer(jPanelBase, javax.swing.JLayeredPane.PALETTE_LAYER);
        jLayeredPaneServerRequests.setLayer(jPanelServer, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPaneServerRequestsLayout = new javax.swing.GroupLayout(jLayeredPaneServerRequests);
        jLayeredPaneServerRequests.setLayout(jLayeredPaneServerRequestsLayout);
        jLayeredPaneServerRequestsLayout.setHorizontalGroup(
            jLayeredPaneServerRequestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPaneServerRequestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanelServer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPaneServerRequestsLayout.setVerticalGroup(
            jLayeredPaneServerRequestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPaneServerRequestsLayout.createSequentialGroup()
                .addContainerGap(118, Short.MAX_VALUE)
                .addComponent(jPanelBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jLayeredPaneServerRequestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPaneServerRequestsLayout.createSequentialGroup()
                    .addGap(0, 94, Short.MAX_VALUE)
                    .addComponent(jPanelServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel3.setText("Heatbeat Threshold");

        monitorPortNumberLabel.setText("XXXX");

        heartbeat.setText("YYYY");

        jLabel4.setText("Port Number");

        jLabel1.setText("Load Balancer Status");

        LBStatus.setText("----");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(285, Short.MAX_VALUE)
                .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(260, 260, 260))
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(monitorPortNumberLabel)
                    .addComponent(heartbeat, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jLayeredPaneServerRequests, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(311, 311, 311)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(123, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(516, 516, 516)
                    .addComponent(LBStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(16, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monitorPortNumberLabel)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(heartbeat))
                .addContainerGap(250, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jLayeredPaneServerRequests, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(72, 72, 72)
                    .addComponent(jLabel1)
                    .addContainerGap(258, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(74, 74, 74)
                    .addComponent(LBStatus)
                    .addContainerGap(256, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Back button event.
     *
     * @param evt event
     */
    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        jLayeredPaneServerRequests.setLayer(jPanelBase, 2);
        jLayeredPaneServerRequests.setLayer(jPanelServer, 0);
        jLayeredPaneServerRequests.repaint();
    }//GEN-LAST:event_jButtonBackActionPerformed

    private void jTableServerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableServerMouseClicked
    }//GEN-LAST:event_jTableServerMouseClicked

    /**
     * End button action.
     *
     * @param evt event
     */
    private void jButtonEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEndActionPerformed
        //monitor.closeSockets();
        System.exit(0);
    }//GEN-LAST:event_jButtonEndActionPerformed

    /**
     * View server requests button action.
     *
     * @param object server id
     */
    private void jButtonServerInfoActionPerformed(Integer serverID) {
        jLabelTitleServer.setText("Server " + serverID + " Requests");
        loadServerRequests(serverID);
        jLayeredPaneServerRequests.setLayer(jPanelServer, 2);
        jLayeredPaneServerRequests.setLayer(jPanelBase, 0);
        jLayeredPaneServerRequests.repaint();
    }

    public void setMonitorInformation(int portNumber, int heartbeatThreshold) {
        monitorPortNumberLabel.setText("" + portNumber);
        heartbeat.setText("" + heartbeatThreshold);
    }
    
    public void setLBStatus(String status) {
        LBStatus.setText(status);
    }

    /**
     * Custom List Item Renderer.
     */
    class TableButtonRenderer extends DefaultTableCellRenderer {

        private static final long serialVersionUID = -7799441088157759804L;
        private JButton button;

        TableButtonRenderer() {
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int col) {

            button = new JButton();
            button.setText("View Server Requests");
            return button;
        }

    }

    public class JTableButtonMouseListener extends MouseAdapter {

        private final JTable table;

        public JTableButtonMouseListener(JTable table) {
            this.table = table;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int column = table.getColumnModel().getColumnIndexAtX(e.getX());
            int row = e.getY() / table.getRowHeight();
            if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
                if (((String) table.getValueAt(row, 1)).equals("UP")) {
                    Integer value = (Integer) table.getValueAt(row, 0);
                    jButtonServerInfoActionPerformed(value);
                }
            }
        }
    }

    /**
     * Load requests being handled by a server to the table.
     *
     * @param id server id
     */
    private synchronized void loadServerRequests(Integer serverID) {
        if (monitor == null) {
            return;
        }
        DefaultTableModel model;
        model = (DefaultTableModel) jTableRequests.getModel();
        cleanTable(model);

        boolean exists;
        for (RequestMessage request : monitor.getServerRequests(serverID)) {
            exists = false;
            for (int i = 0; i < model.getRowCount(); i++) {
                if ((Integer) model.getValueAt(i, 0) == request.requestID()) {
                    exists = true;
                }
            }
            if (!exists) {
                model.addRow(new Object[]{request.requestID(), request.clientID(), request.nIterations(), request.requestCode()});
            }
        }
    }

    /**
     * Clean server requests table.
     *
     * @param model table model
     */
    private synchronized void cleanTable(DefaultTableModel model) {
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }

    /**
     * Remove request from requests table.
     *
     * @param requestID request id
     */
    public synchronized void removeRequestFromRequestTable(int requestID) {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> {
                removeRequestFromRequestTable(requestID);
            });
            return;
        }
        DefaultTableModel model;
        model = (DefaultTableModel) jTableRequests.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            if ((Integer) model.getValueAt(i, 0) == requestID) {
                model.removeRow(i);
            }
        }
    }

    /**
     * Add a new server to the table.
     *
     * @param serverID server id
     */
    public synchronized void addServerToTable(int serverID) {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> {
                addServerToTable(serverID);
            });
            return;
        }
        DefaultTableModel model;
        model = (DefaultTableModel) jTableServer.getModel();
        model.addRow(new Object[]{serverID, "UP", 0});
    }

    /**
     * Update client table.
     *
     * @param clietnID client id
     */
    public synchronized void updateClientTable(int clientID, int pending,
            int beingProcessed, int rejected, int processed) {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> {
                updateClientTable(clientID, pending, beingProcessed, rejected, processed);
            });
            return;
        }
        DefaultTableModel model;
        model = (DefaultTableModel) jTableClients.getModel();
        boolean exists = false;
        for (int i = 0; i < model.getRowCount(); i++) {
            if ((Integer) model.getValueAt(i, 0) == clientID) {
                model.setValueAt(pending, i, 1);
                model.setValueAt(beingProcessed, i, 2);
                model.setValueAt(rejected, i, 3);
                model.setValueAt(processed, i, 4);
                exists = true;
            }
        }
        if (!exists) {
            model.addRow(new Object[]{clientID, pending, beingProcessed, rejected, processed});
        }
    }

    /**
     * Set server down on GUI.
     *
     * @param serverID server id
     */
    public synchronized void setServerDown(int serverID) {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> {
                setServerDown(serverID);
            });
            return;
        }
        DefaultTableModel model;
        model = (DefaultTableModel) jTableServer.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            if ((Integer) model.getValueAt(i, 0) == serverID) {
                model.setValueAt("DOWN", i, 1);
            }
        }
    }

    /**
     * Set the number of requests that a server is handling.
     *
     * @param serverID server id
     * @param nRequests number of requests
     */
    public synchronized void setNRequestsServer(int serverID, int nRequests) {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> {
                setNRequestsServer(serverID, nRequests);
            });
            return;
        }
        DefaultTableModel model;
        model = (DefaultTableModel) jTableServer.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            if ((Integer) model.getValueAt(i, 0) == serverID) {
                model.setValueAt(nRequests, i, 2);
            }
        }
    }

    /**
     * Add a new request to the load balancer table.
     *
     * @param request new request
     */
    public synchronized void addRequestToLBTable(RequestMessage request) {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> {
                addRequestToLBTable(request);
            });
            return;
        }
        DefaultTableModel model;
        model = (DefaultTableModel) jTableLB.getModel();
        model.addRow(new Object[]{request.requestID(), request.clientID(), request.serverID(), request.nIterations(), request.deadline()});
    }

    /**
     * Add a new to the request table, if the server requests is being shown.
     *
     * @param requestID request id
     * @param clientID client id
     * @param iterations number of iterations
     * @param current current state of the request
     * @param serverID server id
     */
    public synchronized void addRequestToTableRequest(RequestMessage request) {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> {
                addRequestToTableRequest(request);
            });
            return;
        }
        if (jLabelTitleServer.getText().equals("Server " + request.serverID() + " Requests")) {
            DefaultTableModel model;
            model = (DefaultTableModel) jTableRequests.getModel();
            boolean exists = false;
            for (int i = 0; i < model.getRowCount(); i++) {
                if ((Integer) model.getValueAt(i, 0) == request.requestID()) {
                    exists = true;
                }
            }
            if (!exists) {
                model.addRow(new Object[]{request.requestID(), request.clientID(), request.nIterations(), request.deadline()});
            }
        }
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
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new GUI().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LBStatus;
    private javax.swing.JLabel heartbeat;
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonEnd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JLabel jLabelTitleServer;
    private javax.swing.JLayeredPane jLayeredPaneServerRequests;
    private javax.swing.JPanel jPanelBase;
    private javax.swing.JPanel jPanelServer;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPaneLB;
    private javax.swing.JScrollPane jScrollPaneRequests;
    private javax.swing.JScrollPane jScrollPaneServer;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTable jTableClients;
    private javax.swing.JTable jTableLB;
    private javax.swing.JTable jTableRequests;
    private javax.swing.JTable jTableServer;
    private javax.swing.JLabel monitorPortNumberLabel;
    // End of variables declaration//GEN-END:variables
}
