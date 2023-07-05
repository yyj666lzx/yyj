import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Tzrk {
    Statement stm;
    Connection con;
    double dj;
    int sl;
    String mc, h;

    public Tzrk(String h, String mc, double dj) {
        this.h = h;
        this.mc = mc;
        this.dj = dj;
    }

    public void taizhang() {
        try {
            con= GetDBConnection.connectDB("java", "root", "0000");
            stm= con.createStatement();

            // 从表rk中查询sl
            String rkQuery= "select sl from rk where hh = '" + h + "'";
            ResultSet rkResult = stm.executeQuery(rkQuery);
            if (rkResult.next()) {
                sl = rkResult.getInt("sl");
            }

            // 更新表gr中的sl和je
            String grinsert= "insert into gr(hh, mc, sl, dj, je) VALUES ('"+h+"','"+mc+"',"+sl+","+dj+","+(dj*sl)+")";
            int i = stm.executeUpdate(grinsert);

            String zcQuery="select sl, pjj from zc where hh = '" + h + "'";
            ResultSet zcResult = stm.executeQuery(zcQuery);
            if (zcResult.next()) {
                int zcSl= zcResult.getInt("sl");
                double zcPjj= zcResult.getDouble("pjj");

                // 查询bc表中的sl和pjj
                String bcQuery = "select sl,pjj from bc where hh = '" + h + "'";
                ResultSet bcResult = stm.executeQuery(bcQuery);
                if (bcResult.next()) {
                    int bcSl= bcResult.getInt("sl");
                    double bcPjj= bcResult.getDouble("pjj");

                    // 计算je
                    double je=(zcSl-bcSl)*zcPjj;

                    String jcInsert= "insert into jc(hh, mc, sl, dj, je) values ('"+h+"','"+mc+"',"+(zcSl-bcSl)+","+dj+","+je+")";
                    int j=stm.executeUpdate(jcInsert);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}