import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
 
// This class manages the download table's data.
class HistoryTableModel extends AbstractTableModel
        implements Observer {
     
    // These are the names for the table's columns.
    private static final String[] columnNames = {"Sno","URL", "Size in MB", "Elapsed Time", "Location" ,"Time"};
     
    // These are the classes for each column's values.
    private static final Class[] columnClasses = {String.class, String.class,
    JProgressBar.class, String.class, String.class, String.class, String.class, String.class};
     
    // The table's list of downloads.
    private ArrayList<Download> HistoryList = new ArrayList<Download>();
     
    // Add a new download to the table.
    public void addHistory(Download download) {
         
        // Register to be notified when the download changes.
        download.addObserver(this);
         
        HistoryList.add(download);
        
        // Fire table row insertion notification to table.
        fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
    }
     
    // Get a download for the specified row.
    public Download getDownload(int row) {
        return HistoryList.get(row);
    }
     
    // Remove a download from the list.
    public void clearDownload(int row) {
        HistoryList.remove(row);
         
        // Fire table row deletion notification to table.
        fireTableRowsDeleted(row, row);
    }
     
    // Get table's column count.
    public int getColumnCount() {
        return columnNames.length;
    }
     
    // Get a column's name.
    public String getColumnName(int col) {
        return columnNames[col];
    }
     
    // Get a column's class.
    public Class getColumnClass(int col) {
        return columnClasses[col];
    }
     
    // Get table's row count.
    public int getRowCount() {
        return HistoryList.size();
    }
     
    // Get value for a specific row and column combination.
    public Object getValueAt(int row, int col) {
         
        Download download = HistoryList.get(row);
        switch (col) {
            case 0: // Sno
                return 0;
            case 1: // URL
                return download.getUrl();
            case 2: // Size
                long size = download.getSize();
                return (size == -1) ? "" : Float.toString((float)size/1048576);
            case 3: //Elapsed Time
                return download.getElapsedTime();
            case 4: //Location
                return download.getAvgSpeed();
            case 5: //Time
                return 111;
        }
        
        return "";
    }
     
  /* Update is called when a Download notifies its
     observers of any changes */
    public void update(Observable o, Object arg) {
        int index = HistoryList.indexOf(o);
        // Fire table row update notification to table.
        fireTableRowsUpdated(index, index);
    }
}