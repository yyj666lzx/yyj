import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Input {
    Connection con=null;
    Statement stm=null;
    String str,h;

    public Input(String str, String h) {
        this.str=str;
        this.h=h;
    }

    public void ruku() {
        try {
            con= GetDBConnection.connectDB("java", "root", "0000");
            stm= con.createStatement();

            // 查询数据库表中是否存在 hh
            String querySql = "select * from rk where hh ='"+h+"'";
            ResultSet resultSet = stm.executeQuery(querySql);

            if (resultSet.next()) {
                // 如果存在，则让 sl 加 1
                int sl = resultSet.getInt("sl") + 1;
                String updateSql = "update rk set sl = "+sl+" where hh = '"+h+"'";
               int i= stm.executeUpdate(updateSql);
            } else {
                // 如果不存在，则将相应的记录插入表中并将 sl 置为 1
                String insertSql = "insert into rk (mc,hh,kcs,pjj,kczj,kcbh,sl) values " + "("+str+","+1+")";
                System.out.println(insertSql);
                int i= stm.executeUpdate(insertSql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}