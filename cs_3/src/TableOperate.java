import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

class TableOperate{
    private String table="clggb";

    private Connection con = null;
    private Statement stm = null;
    private ResultSet rs = null;
    private ResultSetMetaData rsmd = null;

    private int Count = 0;
    private int columns = 0;

    private String[] Names = null;
    private String[] result = null;

    private int currentRow = 0;

    public TableOperate(){
        load();
    }
    public void load() {
        DefaultTableModel model = (DefaultTableModel) PagePanel.table.getModel();
        try {
            con = GetDBConnection.connectDB("java","root","0000");
            stm = con.createStatement();
            rs = stm.executeQuery("select count(*) from " + table);
            rs.next();
            Count = rs.getInt(1);

            rs = stm.executeQuery("select * from " + table);
            rsmd = rs.getMetaData();
            columns = rsmd.getColumnCount();

            Names = new String[columns];
            for (int i = 1; i <= columns; i++) {
                Names[i - 1] = rsmd.getColumnName(i);
            }
            result = new String[columns];
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public int getColumnCount() {
        return columns;
    }
    public String getTable() {
        return table;
    }

    public String[] getColumnNames() {
        return Names;
    }

    public String[] getFirstRecord() {
        try {
            rs.first();
            for (int i = 1; i <= columns; i++) {
                result[i - 1] = rs.getString(i);
            }
            currentRow = rs.getRow();
            System.out.println(rs.getRow());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public String[] getPreviousRecord() throws SQLException {
        rs.previous();
        if (rs.isBeforeFirst()) {
            rs.first();
        }
        for (int i = 1; i <= columns; i++) {
            result[i - 1] = rs.getString(i);
        }
        currentRow = rs.getRow();
        System.out.println(rs.getRow());
        return result;
    }

    public String[] getNextRecord() throws SQLException {
        rs.next();
        if (rs.isAfterLast()) {
            rs.last();
        }
        for (int i = 1; i <= columns; i++) {
            result[i - 1] = rs.getString(i);
        }
        currentRow = rs.getRow();
        System.out.println(rs.getRow());
        return result;
    }

    public String[] getLastRecord() throws SQLException {
        rs.last();
        for (int i = 1; i <= columns; i++) {
            result[i - 1] = rs.getString(i);
        }
        currentRow = rs.getRow();
        System.out.println(rs.getRow());
        return result;
    }

    public void insertRecord(String[] values) {
        String sql = "";

        try {
            for (int i = 0; i < values.length; i++) {
                if (rsmd.getColumnType(i + 1) == Types.VARCHAR
                        || rsmd.getColumnType(i + 1) == Types.TIMESTAMP) {
                    sql += "'" + values[i] + "'";
                } else {
                    sql += values[i];
                }
                if (i < values.length - 1)
                    sql += ",";
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            Statement insertStm = con.createStatement();
            insertStm.execute("insert into " + table + " values(" + sql + ")");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void updateRecord(String[] values) {
        String sql = "";

        String whereSQL = "";
        try {
            for (int i = 0; i < values.length; i++) {
                Vector keyFields = getKeyFields();

                if (rsmd.getColumnType(i + 1) == Types.VARCHAR
                        || rsmd.getColumnType(i + 1) == Types.TIMESTAMP) {
                    sql += rsmd.getColumnName(i + 1) + "='" + values[i] + "'";
                    if (keyFields.contains(rsmd.getColumnName(i + 1)))
                        whereSQL += rsmd.getColumnName(i + 1) + "='"
                                + values[i] + "'";
                } else {
                    sql += rsmd.getColumnName(i + 1) + "=" + values[i] + "";
                    if (keyFields.contains(rsmd.getColumnName(i + 1)))
                        whereSQL += rsmd.getColumnName(i + 1) + "=" + values[i];
                }
                if (i < values.length - 1)
                    sql += ",";
            }
            Statement updateStm = con.createStatement();
            updateStm.executeUpdate("update " + table + " set " + sql
                    + " where " + whereSQL);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    // 获取主键字段的函数
    private Vector getKeyFields() {
        // 主键字段可能包含多个字段，所以使用向量存储
        Vector key = new Vector();
        try {
            // 创建数据库元数据类变量
            DatabaseMetaData dmd = con.getMetaData();

            // 得到指定表的主键信息
            ResultSet keys = dmd.getPrimaryKeys(null, null, table);

            // 记录数与主键字段数相等，结果集的第4个字段为字段名称
            while (keys.next()) {
                key.add(keys.getString(4));
                System.out.println("keys.getString(4)=" + keys.getString(4));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return key;
    }

    public String[] getAbsoluteRecord(int number) {
        try {
            rs.beforeFirst();
            while (rs.next())
                if (rs.getRow() == number)
                    break;
            if (rs.isAfterLast())
                rs.last();
            for (int i = 1; i <= columns; i++) {
                result[i - 1] = rs.getString(i);
            }
            currentRow = number;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public void deleteRecord(String[] values) {
        String whereSQL = "";
        try {
            Vector keyFields = getKeyFields();

            for (int i = 0; i < values.length; i++) {
                if (keyFields.contains(rsmd.getColumnName(i + 1))) {
                    if (rsmd.getColumnType(i + 1) == Types.VARCHAR
                            || rsmd.getColumnType(i + 1) == Types.TIMESTAMP) {
                        whereSQL += rsmd.getColumnName(i + 1) + "='"
                                + values[i] + "'";
                    } else {
                        whereSQL += rsmd.getColumnName(i + 1) + "=" + values[i];
                    }
                }
            }
            Statement deleteStm = con.createStatement();
            deleteStm.executeUpdate("delete from " + table + " where "
                    + whereSQL);
        } catch (Exception e) {

        }
    }

    public int getCurrentRow() {
        return currentRow;
    }
}