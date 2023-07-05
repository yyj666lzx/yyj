import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

class bcAFunction extends CardTemplateFrame implements InterfaceFunction , ActionListener {
    JTextField[] jtfs = null;
    JButton First = new JButton("第一条");
    JButton Previous = new JButton("上一条");
    JButton Next = new JButton("下一条");
    JButton Last = new JButton("最后一条");
    JButton Insert = new JButton("插入记录");
    JButton Update = new JButton("更新记录");
    JButton Delete = new JButton("删除记录");
    JButton Cancel = new JButton("取消更新");
    JButton db=new JButton("调入bc");


    TableOperate tableOperate = null;
 public bcAFunction(){
  closable =true; 
  setVisible(true); 
  setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 }
    public  void ExecuteForm(JMenuItem src)
    {
        closable =true;
        try {
            MDIFrame.getDesktopPane().add(this);
            setMaximum(true);
            repaint();
            setVisible(true);
        }
        catch (Exception e) {}
        tableOperate = new TableOperate();

        jtfs = new JTextField[tableOperate.getColumnCount()];
        for (int i = 0; i < tableOperate.getColumnCount(); i++) {

            cardpanel.getLeftBox().add(new JLabel(tableOperate.getColumnNames()[i]));
            cardpanel.getLeftBox().add(Box.createVerticalGlue());
            jtfs[i] = new JTextField(20);
            cardpanel.getRightBox().add(jtfs[i]);
            cardpanel.getRightBox().add(Box.createVerticalGlue());
        }
        navigatepanel.add(First);
        navigatepanel.add(Previous);
        navigatepanel.add(Next);
        navigatepanel.add(Last);
        navigatepanel.add(Insert);
        navigatepanel.add(Update);
        navigatepanel.add(Delete);
        navigatepanel.add(Cancel);
        navigatepanel.add(db);

        First.addActionListener(this);
        Previous.addActionListener(this);
        Next.addActionListener(this);
        Last.addActionListener(this);
        Insert.addActionListener(this);
        Update.addActionListener(this);
        Delete.addActionListener(this);
        Cancel.addActionListener(this);
        db.addActionListener(this);
        fill(tableOperate.getFirstRecord());
    }

    public void fill(String[] values) {
        for (int i = 0; i < tableOperate.getColumnCount(); i++) {
            jtfs[i].setText(values[i]);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == First) {
            fill(tableOperate.getFirstRecord());
        } else if (e.getSource() == Previous) {
            try {
                fill(tableOperate.getPreviousRecord());
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } else if (e.getSource() == Next) {
            try {
                fill(tableOperate.getNextRecord());
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } else if (e.getSource() == Last) {
            try {
                fill(tableOperate.getLastRecord());
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } else if (e.getSource() == Insert) {
            tableOperate.insertRecord(getAllStrings());
            tableOperate.load();
            try {
                fill(tableOperate.getLastRecord());
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } else if (e.getSource() == Update) {
            tableOperate.updateRecord(getAllStrings());
            tableOperate.load();
            fill(tableOperate.getAbsoluteRecord(tableOperate.getCurrentRow()));
        } else if (e.getSource() == Delete) {
            tableOperate.deleteRecord(getAllStrings());
            tableOperate.load();
            fill(tableOperate.getAbsoluteRecord(tableOperate.getCurrentRow()));
        } else if (e.getSource() == Cancel) {
            fill(tableOperate.getAbsoluteRecord(tableOperate.getCurrentRow()));
        } else if (e.getSource()==db) {
            String sql=(String)"'"+pagePanel.getTable().getValueAt(0,0)+"'"+","+"'"+pagePanel.getTable().getValueAt(0,1)+"'"+","+pagePanel.getTable().getValueAt(0,2)+","+pagePanel.getTable().getValueAt(0,3)+","+pagePanel.getTable().getValueAt(0,4);
            String hh=(String)pagePanel.getTable().getValueAt(0,1);
            new Diaobo(sql,hh).diaobo();
        }

    }
    public String[] getAllStrings() {
        String[] content = new String[tableOperate.getColumnCount()];
        for (int i = 0; i < tableOperate.getColumnCount(); i++) {
            content[i] = jtfs[i].getText();
        }
        return content;
    }

}