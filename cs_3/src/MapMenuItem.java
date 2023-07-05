import javax.swing.*;
import java.awt.event.*;

class MapMenuItem extends JMenuItem implements ActionListener{
    String buttonFuctionClassName;
    MapMenuItem(String buttonFunctionClassName)
    {
        super();
        addActionListener(this);
        this.buttonFuctionClassName=buttonFunctionClassName;
    }

    MapMenuItem(String text, Icon icon,String buttonFunctionClassName)
    {
        super(text,icon);
        addActionListener(this);
        this.buttonFuctionClassName=buttonFunctionClassName;
    }
    MapMenuItem(String text,String buttonFunctionClassName)
    {
        super(text);
        addActionListener(this);
        this.buttonFuctionClassName=buttonFunctionClassName;
    }
    MapMenuItem(Icon icon,String buttonFunctionClassName)
    {
        super(icon);
        addActionListener(this);
        this.buttonFuctionClassName=buttonFunctionClassName;
    }
    public void actionPerformed(ActionEvent event) {
        InterfaceFunction curForm;
        try{
            Class cs=Class.forName(buttonFuctionClassName);
            curForm=(InterfaceFunction)cs.newInstance();
            curForm.ExecuteForm(this);
        }
        catch(Exception e){
            System.out.println("类:("+buttonFuctionClassName+"不存在！");
        }
    }
}