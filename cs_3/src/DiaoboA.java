import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DiaoboA {
    Connection con = null;
    Statement stm = null;
    String str, h;
    ResultSet rs;

    public DiaoboA(String str, String h) {
        this.h = h;
        this.str = str;
    }

    public void diaoboA() {
        try {
            con= GetDBConnection.connectDB("java", "root", "0000");
            stm= con.createStatement();

            String checkQuery="select * from bca where hh = '"+h+"'";
            rs= stm.executeQuery(checkQuery);

            if (rs.next()) {
                int sl=rs.getInt("sl");
                int updatedSl= sl+1;
                String updateQuery= "update bca set sl = " + updatedSl + " where hh = '"+h+"'";
               int i= stm.executeUpdate(updateQuery);
            } else {
                String insertQuery = "insert into bca (mc,hh,kcs,pjj,kczj,sl) values"+"("+str+","+1+")";
               int i= stm.executeUpdate(insertQuery);
            }

            String checkbc="select * from bc where hh='"+h+"'";
            rs=stm.executeQuery(checkbc);
            if (rs.next()){
                int slbc=rs.getInt("sl");
                if (slbc==1){
                    String deletebc="delete from bc where hh='"+h+"'";
                    int i=stm.executeUpdate(deletebc);
                }else{
                    String updatebc= "update bca set sl= sl-1 where hh = '"+h+"'";
                    int j=  stm.executeUpdate(updatebc);
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