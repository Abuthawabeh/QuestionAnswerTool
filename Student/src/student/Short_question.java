package student;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.text.*;

public class Short_question extends JFrame {
    private javax.swing.JButton plainButton;
    private JComboBox chooseExam;
    ArrayList tableName;
    ArrayList e;
    String Exam_name = "exam";
    JTextField arrayText[];
    private Container c;
    private FlowLayout layout;

    public Short_question() {
        super("اسئلة الامتحانات");
           try {
                String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
                Class.forName(driver);
           } catch (ClassNotFoundException cnfex) {
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
        }  catch (SQLException sqlex) {
        }

/////
        // Object[] possibleValues = { "First", "Second", "Third" };
        Exam_name = (String) JOptionPane.showInputDialog(null,
                "Choose one", "Input",
                JOptionPane.INFORMATION_MESSAGE, null,
                tableName.toArray(), tableName.get(0));
        plainButton = new javax.swing.JButton("النتيجة");
        layout = new FlowLayout();
        c = getContentPane();
        c.setLayout(layout);

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
            ResultSet rs1 = statement.executeQuery("select DISTINCT g from " + Exam_name);
            e = new ArrayList();
            JLabel array[] = new JLabel[10];
            arrayText = new JTextField[10];
            int i = 0;
            while (rs1.next()) {
                array[i] = new JLabel();
                array[i].setText((i + 1) + "  " + rs1.getString("g") + "\n\n\n");
                getContentPane().add(array[i]);
                arrayText[ i] = new JTextField(20);
                getContentPane().add(arrayText[ i]);
                ++i;
            }
            ResultSet rs3 = statement.executeQuery("select DISTINCT g from " + Exam_name);
            //rs1.beforeFirst();
            i = 0;
            while (rs3.next()) {
                e.add(rs3.getString("g"));
                ++i;
            }
            statement.close();
            conn.close();
        }  catch (SQLException sqlex) {
        }

        plainButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                   try {
                    String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
                    Class.forName(driver);
                } catch (ClassNotFoundException cnfex) {
                }

                try {
                    // connect to db using DriverManager
                    String databaseName = "../database/db5.mdb";
                    Connection conn1 = DriverManager.getConnection("jdbc:ucanaccess://" + databaseName);
                    // Create a statement object
                    Statement statement1 = conn1.createStatement();
                    ResultSet rs2 = statement1.executeQuery("select  g,m,x from " + Exam_name);
                    int y, j;
                    int mark = 0;
                    int total = 0;
                    StringTokenizer tokens;
                    String n, r, w, u = "";
                    for (int i = 0; i < e.size(); ++i) {
                        while (rs2.next()) {
                            n = rs2.getString("g");
                            if (n.equals(e.get(i))) {
                                tokens = new StringTokenizer(arrayText[i].getText());
                                j = rs2.getInt("x");
                                total += j;
                                r = rs2.getString("m");
                                while (tokens.hasMoreTokens()) {
                                    w = tokens.nextToken();
                                    if (w.equals(r) && (!u.contains(w))) {
                                        mark += j;
                                        u += w;
                                    }
                                }
                                u = "";
                            }
                        }
                        rs2 = statement1.executeQuery("select  g,m,x from " + Exam_name);
                    }
                    JOptionPane.showMessageDialog(null, "Result= " + mark + "\n From " + total);
                    statement1.close();
                    conn1.close();
                }  catch (SQLException sqlex) {
                }
            }
        });
        ////////////////
        getContentPane().add(plainButton);
        setSize(250, 350);
    }
    public static void main(String args[]) {
            new Short_question().show();
    }
}
