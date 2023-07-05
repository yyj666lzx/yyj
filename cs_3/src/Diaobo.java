import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Diaobo {
    Connection con = null;
    Statement stm = null;
    String str, h;
    ResultSet rs;

    public Diaobo(String str, String h) {
        this.h = h;
        this.str = str;
    }

    public void diaobo() {
        try {
            con=GetDBConnection.connectDB("java", "root", "0000");
            stm=con.createStatement();

            String checkbc="select * from bc where hh = '"+h+"'";
            rs=stm.executeQuery(checkbc);


            if (rs.next()) {
                int sl= rs.getInt("sl");
                int updatedSl = sl+1;
                String updateQuery= "update bc set sl = " + updatedSl + " where hh = '"+h+"'";
               int i= stm.executeUpdate(updateQuery);
            } else {
                String insertba = "insert into bc (mc,hh,kcs,pjj,kczj,sl) values "+"("+str+","+1+")";
               int i= stm.executeUpdate(insertba);
            }

            String checkbca="select * from bca where hh='"+h+"'";
            rs=stm.executeQuery(checkbca);
            if (rs.next()){
                int slbca=rs.getInt("sl");
                if (slbca==1){
                    String deletebca="delete from bca where hh='"+h+"'";
                    int i=stm.executeUpdate(deletebca);
                }else{
                    String updatebca= "update bca set sl=sl-1 where hh = '"+h+"'";
                    int j= stm.executeUpdate(updatebca);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}