import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Output {
    Connection con = null;
    Statement stm = null;
    ResultSet rs;
    String str,h;
    public Output(String str,String h) {
        this.str=str;
        this.h=h;
    }
    public void chuku() {
        try {
            con= GetDBConnection.connectDB("java", "root", "0000");
            stm= con.createStatement();

            // 查询数据库表中是否存在 hh
            String querySql= "select * from ck where hh = '"+h+"'";
            ResultSet resultSet= stm.executeQuery(querySql);

            if (resultSet.next()) {
                // 如果存在，则让 sl 加 1
                int sl= resultSet.getInt("sl") + 1;
                String updateSql = "update ck set sl = "+sl+" where hh = '"+h+"'";
               int i= stm.executeUpdate(updateSql);
            } else {
                // 如果不存在，则将相应的记录插入表中并将 sl 置为 1
                String insertSql = "insert into ck (mc,hh,kcs,pjj,kczj,kcbh,sl) values " + "("+str+","+1+")";
               int i= stm.executeUpdate(insertSql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}