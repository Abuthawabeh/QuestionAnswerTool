/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teacher;

/**
 *
 * @author Ala
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.text.*;
import java.sql.*;

public class Teacher extends JFrame {

    String Exam_name;
    ArrayList tableName;
    String t;
    private javax.swing.JButton plainButton;
    private javax.swing.JButton NEW;
    private JComboBox chooseExam;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextArea output;

    public Teacher() {
        super("ادخال الاسئلة");
        plainButton = new javax.swing.JButton("اضف الكلمة المفتاحية");
        NEW = new javax.swing.JButton("امتحان جديد");
        JPanel buttonPanel;
        Icon bug1 = new ImageIcon("moh.gif");
        jLabel1 = new JLabel(bug1);
        jTextField1 = new javax.swing.JTextField(20);
        jTextField1.setHorizontalAlignment(JTextField.RIGHT);
        output = new JTextArea(11, 30);
        //output.setHorizontalAlignment(JTextArea.RIGHT);
        getContentPane().setLayout(new FlowLayout());
//gbconstraints.fill=GridBagConstraints.BOTH;
        //getContentPane().setBackground(Color.blue);
        output.setEditable(false);
        plainButton.setEnabled(false);
        jTextField1.setEditable(false);
///
        NEW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                do {
                    Exam_name = JOptionPane.showInputDialog("Enter Exam Name");
                } while (Exam_name == null);
                t = "";
                output.setText("");
                try {
                    String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
                    Class.forName(driver);
                } catch (ClassNotFoundException cnfex) {
                }

                try {
                    // connect to db using DriverManager
                    String databaseName = "../database/db5.mdb";
                    Connection conn3 = DriverManager.getConnection("jdbc:ucanaccess://" + databaseName);
                    // Create a statement object
                    Statement statement3 = conn3.createStatement();
                    statement3.executeUpdate("CREATE TABLE  " + Exam_name + " (g varchar(50), m varchar(50),x int )");
                    statement3.close();
                    conn3.close();
                } catch (SQLException sqlex) {
                }
                plainButton.setEnabled(true);
                jTextField1.setEditable(true);
            }
        });

///
        plainButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String g = jTextField1.getText();
                try {
                    String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
                    Class.forName(driver);
                } catch (ClassNotFoundException cnfex) {
                }

                try {
                    // connect to db using DriverManager
                    String databaseName = "../database/db5.mdb";
                    Connection conn = DriverManager.getConnection("jdbc:ucanaccess://" + databaseName);
                    // Create a statement object
                    Statement statement = conn.createStatement();
                    t = g;
                    int y, x;
                    String f, m;
                    do {
                        m = JOptionPane.showInputDialog("اضف الكلمة المفتاحية");
                        //t=t+"\n"+m;
                        x = Integer.parseInt(JOptionPane.showInputDialog("ادخل قيمة المفتاح"));
                        statement.executeUpdate("insert into " + Exam_name + " values('" + g + "','" + m + "'," + x + ")");
                        f = JOptionPane.showInputDialog("اضغط الرقم واحد لاضافة مفتاح جديد\\n اضغط اي رقم للخروج");
                        y = Integer.parseInt(f);
                    } while (y == 1);
                    t = t + "\n-----------------------------------------------------------------------------\n";
                    statement.close();
                    output.append(t);
                    conn.close();
                } catch (SQLException sqlex) {
                }
            }
        });

///
        try {
            String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
            Class.forName(driver);
        } catch (ClassNotFoundException cnfex) {
            System.err.println("" + cnfex.getMessage());
        }
        try {
            // connect to db using DriverManager
            String databaseName = "../database/db5.mdb";
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://" + databaseName);
            DatabaseMetaData metadata = conn.getMetaData();
            String[] tableTypes = {"TABLE"};        
            ResultSet tables = metadata.getTables(null, null, null, tableTypes);
            // Create a statement object
            //Statement statement =	conn.createStatement();
            tableName = new ArrayList();
            int i = 0;
            while (tables.next()) {
                tableName.add(tables.getString("TABLE_NAME"));
                ++i;
            }
            conn.close();
        } catch (SQLException sqlex) {
            System.err.println("" + sqlex.getMessage());
        }

        chooseExam = new JComboBox(tableName.toArray());
        chooseExam.addItemListener(
                new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        Exam_name = (String) tableName.get(chooseExam.getSelectedIndex());
                        plainButton.setEnabled(true);
                        jTextField1.setEditable(true);
                        t = "";
                        output.setText("");
                        //getContentPane().validate();
                    }
                }
        );

        buttonPanel = new JPanel();
        buttonPanel.setLayout(
                new GridLayout(1, 5));
        JLabel j0 = new JLabel("    ");
        buttonPanel.add(j0);
        buttonPanel.add(NEW);
//getContentPane().add(NEW);
//getContentPane().add(chooseExam);
        JLabel j1 = new JLabel("   ");
        buttonPanel.add(j1);
        buttonPanel.add(chooseExam);
        JLabel j2 = new JLabel(" تعديل امتحان سابق ");
        buttonPanel.add(j2);
        getContentPane().add(buttonPanel);
        JLabel j3 = new JLabel("======================================= ");
        getContentPane().add(j3);
        getContentPane().add(jLabel1);
        JLabel j4 = new JLabel("====================================== ");
        getContentPane().add(j4);
        getContentPane().add(jTextField1);
        getContentPane().add(plainButton);
        getContentPane().add(output);
        setSize(370, 370);
    }

    public static void main(String args[]) {
        new Teacher().show();
    }

}
