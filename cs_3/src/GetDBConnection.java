import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class GetDBConnection {
    static Connection con=null;
    Statement stmt;
    public static Connection connectDB(String DBName,String id,String p){

        String uri="jdbc:mysql://localhost:3306/"+DBName+"?useSSL=true&serverTimezone=CST&characterEncoding=utf-8";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
//            System.out.println("加载驱动成功");
        }catch(Exception e){
            System.out.println(e);
        }
        try{
            con=DriverManager.getConnection(uri,id,p);
//            System.out.println("数据库链接成功");
        }catch(SQLException e){
            System.out.println(e);
        }
        return con;
    }
//    public Statement getStatement() throws SQLException {
//        stmt=con.createStatement();
//        return this.stmt ;
//    }

}
