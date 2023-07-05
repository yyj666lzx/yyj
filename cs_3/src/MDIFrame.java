import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class MDIFrame extends JFrame {
    private static JDesktopPane desktopPane;
    JMenuBar menubar;
    Statement stmt;
    ResultSet rs;
    Connection con;
    public MDIFrame(){
     this("");
    }
    public MDIFrame(String s) {
        super(s);
       Container contentPane = this.getContentPane();
       contentPane.setLayout(new BorderLayout());
       init();
       setExtendedState(JFrame.MAXIMIZED_BOTH);
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       setVisible(true);
       desktopPane = new JDesktopPane();
       contentPane.add(desktopPane, BorderLayout.CENTER);
    }
        public void init() {
        menubar = new JMenuBar();
        JMenu[] menus = new JMenu[6];
        MapMenuItem[] ma = new MapMenuItem[5];
        String code = "", title = "", function = "";
        int count = 0;
        int ismenu = 0;
        try {
            con = GetDBConnection.connectDB("yyj", "root", "0000");
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from menu order by code");
            while (rs.next()) {
                code = rs.getString("code").trim();
                title = rs.getString("title").trim();
                ismenu = rs.getInt("ismenu");
                function = rs.getString("functionclassname").trim();
                count = code.length() / 2;
                if (ismenu ==1){
                    menus[count] = new JMenu(title);
                    menubar.add(menus[count]);
                    if (count > 0) {
                        menus[count-1].add(menus[count]);
                    }
                } else {
                    ma[count] = new MapMenuItem(title, function);
                    menus[count-1].add(ma[count]);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setJMenuBar(menubar);
    }
    public static JDesktopPane getDesktopPane() {
        return desktopPane;
    }
           
}