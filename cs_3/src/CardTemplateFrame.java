import javax.swing.*;
import java.awt.BorderLayout;

    public class CardTemplateFrame extends JInternalFrame {//JInternalFrame
    protected CardPanel cardpanel;    //卡片输入面板部分
    protected DataNavigatePanel navigatepanel; //数据导航条面板
    protected PagePanel pagePanel;  //多页面板
    public CardTemplateFrame(){
        super();
        pagePanel=new PagePanel();
        cardpanel=new CardPanel();
        navigatepanel=new DataNavigatePanel();
        add(navigatepanel, BorderLayout.SOUTH);
        add(cardpanel, BorderLayout.WEST);
        add(pagePanel, BorderLayout.CENTER);
        maximizable = true;
        resizable=true;
        closable = true;
        setVisible(true);
        validate();
    }
    public CardTemplateFrame(int type){
        super();
        pagePanel=new PagePanel(type);
        cardpanel=new CardPanel();
        navigatepanel=new DataNavigatePanel();
        add(navigatepanel, BorderLayout.SOUTH);
        add(cardpanel, BorderLayout.WEST);
        add(pagePanel, BorderLayout.CENTER);
        maximizable = true;
        resizable=true;
        closable = true;
        setVisible(true);
        validate();
    }
    public CardTemplateFrame(String title){
        super(title);
    }

    public CardPanel getCardPanel(){
        return cardpanel;
    }

        public DataNavigatePanel getDataNavigatePanel(){
        return navigatepanel;
    }

}