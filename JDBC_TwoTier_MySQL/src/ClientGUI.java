

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * This gui does all the visuals and jazz
 * @author charles
 */
public class ClientGUI extends JFrame {
    private DBDriver mDBDriver;
    private DBConnector mDBConnector;

    // connect to database
    private void jButton1ActionPerformed(ActionEvent evt) {

        mDBConnector = new DBConnector(jComboBox1.getSelectedItem().toString(),jComboBox2.getSelectedItem().toString(),jTextField1.getText(),
                new String(jPasswordField1.getPassword()));

        try {
            mDBConnector.CloseConnection();
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(), "Database error",
                    JOptionPane.ERROR_MESSAGE);
        }catch(IllegalStateException es) {
            //ignore
        }
        //TODO: find out if multiple throws can exist in one try
        try {
            mDBConnector.OpenConnection();
            mDBDriver = new DBDriver(mDBConnector);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(), "Database error",
                    JOptionPane.ERROR_MESSAGE);
        }catch(ClassNotFoundException cnf) {
            JOptionPane.showMessageDialog(null,
                    cnf.getMessage(), "Driver Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        if(mDBConnector.isConnected()) {
            jLabel6.setText("" + jComboBox2.getSelectedItem().toString());
            jLabel6.setForeground(new java.awt.Color(0, 255, 0));
        }else {
            jLabel6.setText("Connected to: -- ");
            jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        }
    }

// Clear Query Window
    private void jButton2ActionPerformed(ActionEvent evt) {
        jTextPane1.setText("");
    }

    // Submit
    private void jButton3ActionPerformed(ActionEvent evt) {
        String queryToExecute = jTextPane1.getText().toLowerCase();
        boolean isSelect = queryToExecute.contains("select");
        if(!isSelect) {
            try {
                mDBDriver.updateData(queryToExecute);
            }catch(SQLException e) {
                JOptionPane.showMessageDialog(null,
                        e.getMessage(), "Database error",
                        JOptionPane.ERROR_MESSAGE);
            }catch(IllegalStateException n) {
                JOptionPane.showMessageDialog(null,
                        n.getMessage(), "Database error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }else {
            try {
                DefaultTableModel dbModel= mDBDriver.loadData(queryToExecute);
                jTable1.setModel(dbModel);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null,
                        e.getMessage(), "Database error",
                        JOptionPane.ERROR_MESSAGE);
            }catch(IllegalStateException n) {
                JOptionPane.showMessageDialog(null,
                        n.getMessage(), "Database error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    // Clear Result Table
    private void jButton4ActionPerformed(ActionEvent evt) {
        jTable1.setModel(new DefaultTableModel(new Object[][]{
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
        },
                new String[]{
                        "    ", "    ", "    ", "    "
                }));

    }
    // Declare entities

    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JComboBox jComboBox1;
    private JComboBox jComboBox2;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPasswordField jPasswordField1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTable jTable1;
    private JTextField jTextField1;
    private JTextPane jTextPane1;

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientGUI().setVisible(true);
            }
        });

    }

    /**
     * Creates new form ClientGUI
     */
    public ClientGUI() {
        initComponents();
        this.setSize(900,500);
    }

    private void initComponents() {

        jPanel1 = new JPanel();
        jLabel7 = new JLabel();
        jLabel8 = new JLabel();
        jLabel2 = new JLabel();
        jComboBox1 = new JComboBox();
        jLabel3 = new JLabel();
        jComboBox2 = new JComboBox();
        jLabel4 = new JLabel();
        jTextField1 = new JTextField();
        jLabel5 = new JLabel();
        jPasswordField1 = new JPasswordField();
        jPanel2 = new JPanel();
        jScrollPane1 = new JScrollPane();
        jTextPane1 = new JTextPane();
        jLabel1 = new JLabel();
        jPanel3 = new JPanel();
        jLabel6 = new JLabel();
        jButton1 = new JButton();
        jButton2 = new JButton();
        jButton3 = new JButton();
        jScrollPane2 = new JScrollPane();
        jTable1 = new JTable();
        jPanel4 = new JPanel();
        jButton4 = new JButton();
        jLabel9 = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridLayout(5, 2));

        jLabel7.setText("DB Config");
        jPanel1.add(jLabel7);
        jPanel1.add(jLabel8);

        jLabel2.setText("Driver");
        jPanel1.add(jLabel2);

        jComboBox1.setModel(new DefaultComboBoxModel(new String[] { "com.mysql.jdbc.Driver" }));
        jPanel1.add(jComboBox1);

        jLabel3.setText("Database URL");
        jPanel1.add(jLabel3);

        jComboBox2.setModel(new DefaultComboBoxModel(new String[] { "jdbc:mysql://54.85.107.167:3306/project2", "jdbc:mysql://localhost:3306/project2", "jdbc:mysql://localhost:3310/project2" }));
        jPanel1.add(jComboBox2);

        jLabel4.setText("Username");
        jPanel1.add(jLabel4);

        jTextField1.setText("root");
        jPanel1.add(jTextField1);

        jLabel5.setText("Password");
        jPanel1.add(jLabel5);

        jPasswordField1.setText("root");

        jPanel1.add(jPasswordField1);

        jScrollPane1.setViewportView(jTextPane1);

        jLabel1.setText("Query Window");

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 160, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1))
        );

        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setText("Connected to: --");
        jPanel3.add(jLabel6);

        jButton1.setText("Connect To Database");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        jButton2.setText("Clear Commands");
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jButton2ActionPerformed(actionEvent);
            }
        });
        jPanel3.add(jButton2);

        jButton3.setText("Execute SQL Query");
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jButton3ActionPerformed(actionEvent);
            }
        });
        jPanel3.add(jButton3);

        jTable1.setModel(new DefaultTableModel(
                new Object[][]{
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String[]{
                        "    ", "    ", "    ", "    "
                }
        ));
        jScrollPane2.setViewportView(jTable1);

        jButton4.setText("Clear Result Window");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton4)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton4)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel9.setText("SQL Execution Window");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 375, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane2)
                                        .addComponent(jPanel3, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel9)
                                                .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9)
                                .addGap(2, 2, 2)
                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }


    // End of variables declaration                   
}
