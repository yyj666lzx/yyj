import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
public class Pd {
    Connection con=null;
    Statement stm=null;
    String str, h;
    public Pd(String str, String h) {
        this.str=str;
        this.h=h;
    }
    public void pd() {
        try {
            con=GetDBConnection.connectDB("java", "root", "0000");
            stm=con.createStatement();

            // 查询 bc 和 bca 中的 sl 总和以及 kczj
            String queryBcSql = "select SUM(sl) as bc_sum, SUM(kczj) as bc_kczj from bc where hh=?";
            PreparedStatement pstmtbc = con.prepareStatement(queryBcSql);
            pstmtbc.setString(1, h);
            ResultSet bcResultSet=pstmtbc.executeQuery();
            int bcSum=0;
            int bcKczj=0;
            if (bcResultSet.next()) {
                bcSum=bcResultSet.getInt("bc_sum");
                bcKczj=bcResultSet.getInt("bc_kczj");
            }

            String queryBcaSql = "select SUM(sl) as bca_sum, SUM(kczj) as bca_kczj from bca where hh=?";
            PreparedStatement pstmtbca = con.prepareStatement(queryBcaSql);
            pstmtbca.setString(1, h);
            ResultSet bcaResultSet = pstmtbca.executeQuery();
            int bcaSum=0;
            int bcaKczj=0;
            if (bcaResultSet.next()) {
                bcaSum=bcaResultSet.getInt("bca_sum");
                bcaKczj=bcaResultSet.getInt("bca_kczj");
            }

            // 查询 zc 中的 sl 和 kczj
            String queryZcSql = "select sl,kczj from zc where hh=?";
            PreparedStatement pstmtzc = con.prepareStatement(queryZcSql);
            pstmtzc.setString(1, h);
            ResultSet zcResultSet = pstmtzc.executeQuery();
            int zcSl=0;
            int zcKczj=0;
            if (zcResultSet.next()) {
                zcSl=zcResultSet.getInt("sl");
                zcKczj=zcResultSet.getInt("kczj");
            }

            if ((bcSum+bcaSum)<zcSl) {
                int sum=zcSl-bcaSum-bcSum;
                double yl=zcSl*zcKczj-(bcaSum+bcSum)*(bcaKczj+bcKczj);
                String insertZcSql = "insert into pd(mc,hh,kcs,pjj,kczj,sl,yl) values "+"("+str+","+sum+","+yl+")";
                stm.executeUpdate(insertZcSql);
            } else {
                int sum=(bcaSum+bcSum)-zcSl;
                double yl=(bcaSum+bcSum) * (bcaKczj+bcKczj)-zcSl*zcKczj;
                String insertZcSql= "insert into pd(mc,hh,kcs,pjj,kczj,sl,yl) values "+"("+str+","+sum+","+yl+")";
                stm.executeUpdate(insertZcSql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}