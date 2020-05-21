import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

import java.sql.*;

public class HistoryManager extends JFrame {
    private HistoryTableModel hisTableModel;
    JFrame frame1;
    JLabel l0, l1, l2;
    JComboBox c1;
    JButton b1;
    Connection con;
    ResultSet rs, rs1;
    Statement st, st1;
    PreparedStatement pst;
    String ids;
    static JTable table;
    String[] columnNames = {"Sno","URL", "Size in MB", "Elapsed Time", "Location" ,"Time"};
    String from;

    public HistoryManager() {
        // Set application title.
        setTitle("History");
         
        // Set window size.
        setSize(640, 480);
         
        // Handle window closing events.
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        try {
            Class.forName("com.mysql.jdbc.Driver");  // (1)
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/downloadmgr", "root", "asdf;lkj");  // (2)
            st = con.createStatement();
        } catch (Exception e) {
            //TODO: handle exception
        }
        frame1 = new JFrame("History");
        frame1.setLayout(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        table = new JTable();
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        String sno="";
        String url = "";
        String size= "";
        String eTime= "";
        String loc = "";
        String time= "";
        try {
            pst = con.prepareStatement("select * from history");
            ResultSet rs = pst.executeQuery();
            int i = 0;
            while (rs.next()) {
                sno = String.valueOf(rs.getInt("sno"));
                url = rs.getString("url");
                size = Float.toString(rs.getFloat("size"));
                eTime = rs.getString("eTime");
                loc = rs.getString("location");
                time = rs.getString("Time");

                
                model.addRow(new Object[]{sno, url, size, eTime, loc, time});
                i++;
            }
            frame1.add(scroll);
            frame1.setVisible(true);
            frame1.setSize(400, 300);
        } catch (Exception e) {
        }

    }

    
}