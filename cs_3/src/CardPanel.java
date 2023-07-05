import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import javax.swing.border.*;

public class CardPanel extends JPanel{
    private Box leftbox;
    private Box rightbox;
    private Box rowbox;
    public CardPanel(){
        super();
        setPreferredSize(new Dimension(400, 0));//此方法设置大小才有效
        Border lineBorder = new LineBorder(Color.DARK_GRAY, 2);
        setBorder(lineBorder);
        leftbox=Box.createVerticalBox();
        rightbox=Box.createVerticalBox();
        rowbox=Box.createHorizontalBox();
        rowbox.add(leftbox);
//   rowbox.add(Box.createHorizontalStrut(8));
        rowbox.add(rightbox);
        add(rowbox);
    }

    public Box getLeftBox(){
        return leftbox;
    }

    public Box getRightBox(){
        return rightbox;
    }

}