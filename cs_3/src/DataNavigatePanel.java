import javax.swing.*;
import java.util.*;
import java.sql.*;
import java.awt.*;
import java.awt.Dimension;
import javax.swing.border.*;

public class DataNavigatePanel extends JPanel
{
    ResultSet rs;
    Vector <JButton> toolbts;
    public DataNavigatePanel(){
        super();
        toolbts=new Vector();
        setPreferredSize(new Dimension(0, 60));//此方法设置大小才有效
        Border lineBorder = new LineBorder(Color.GRAY, 2);
        setBorder(lineBorder);
    }
    public void setResultSet(ResultSet rs)
    {this.rs=rs;}
    public void addButton(JButton bt)
    {
        toolbts.add(bt);
    }
}
