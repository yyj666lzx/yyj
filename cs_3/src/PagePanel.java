import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class PagePanel extends JTabbedPane implements TreeSelectionListener{
    JTree tree;
    Statement stmt;
    ResultSet rs;
    Connection con;
    static JTable table;
    static DefaultTableModel tableModel;
    String[] columnNames = {"mc", "hh", "kcs","pjj","kzcj"};
    public PagePanel() {
        super();
        Addtree();
        tableModel = new DefaultTableModel(columnNames,0);
        // 创建表格组件
        table = new JTable(tableModel);
        table.setFont(new Font("宋体", Font.PLAIN, 18));
        JScrollPane scrolltree = new JScrollPane(tree);
        JScrollPane scrolltable = new JScrollPane(table);

        scrolltree.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrolltree.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrolltable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrolltable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add("目录树", scrolltree);
        add("清单", scrolltable);

    }
    public PagePanel(int type) {
        super();
        if (type == 1) {
            JScrollPane scrolltree = new JScrollPane(tree);
            scrolltree.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrolltree.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            add("目录树", scrolltree);
        } else {
            JScrollPane scrolltable = new JScrollPane(table);
            scrolltable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrolltable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            add("清单", scrolltable);
        }
    }
    public void Addtree() {
        DefaultMutableTreeNode[] nodes = new DefaultMutableTreeNode[7];
        String hh = "", mc = "", kcs = "", pjj = "", kczj = "";
        int count = 0;
        try {
            con = GetDBConnection.connectDB("java", "root", "0000");
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from clggb order by hh");
            while (rs.next()) {
                mc = rs.getString("mc").trim();
                hh = rs.getString("hh").trim();
                kcs = rs.getString("kcs").trim();
                pjj = rs.getString("pjj").trim();
                kczj = rs.getString("kczj").trim();
                count = hh.length() / 2;
                if (count == 0) {
                    nodes[0] = new DefaultMutableTreeNode(mc);
                } else {
                    nodes[count] = new DefaultMutableTreeNode(new Goods(mc, hh, kcs, pjj, kczj));
                    nodes[count - 1].add(nodes[count]);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        tree = new JTree(nodes[0]);
        tree.setFont(new Font("宋体", Font.PLAIN, 25)); // 设置字体为宋体，大小为25
        tree.addTreeSelectionListener(this);
    }
    public JTree getTree() {
        return tree;
    }

    public JTable getTable() {
        return table;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (node == null) {
            return;
        }
        if (node.isLeaf()){
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            Goods s = (Goods) node.getUserObject();
            String[] rowData = {s.name, s.hh, s.kcs,s.pjj,s.kczj};
            model.addRow(rowData);
        }
    }
}