package com.happytalk.main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.jar.Attributes;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;

import com.happytalk.common.CommonUtil;
import com.happytalk.dao.DojeDao;
import com.happytalk.model.Users;

import javax.print.attribute.standard.JobMessageFromOperator;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.*;
import javax.swing.JMenu;
import javax.swing.JMenuBar;


public class Doje extends JFrame {
    static DojeDao dao = new DojeDao();
    static HashMap<String, Object> hm = new HashMap<>();
    static List<Users> userListResult = dao.getUserList(hm);
    static String header[] = {"No.","name", "title", "contents", "upload_date"};
    static DefaultTableModel model = new DefaultTableModel(header, 0);
    static Logger logger = Logger.getLogger("app");
    static JTable main_table = new JTable(model);

    public static void main(String[] args) {

        logger.info("####################### Doje Start ########################");


        long stasrtTime = System.currentTimeMillis();


        Vector<String> row = new Vector();
        for (Users userList : userListResult) {
            model.addRow(new Object[]{userList.getPN(),userList.getName(), userList.getTITLE(), userList.setCONTENTS(), userList.setReg_date()});
        }

        Dimension dim = new Dimension(500, 600);
        JFrame f = new JFrame("GUI Table");
        f.setLocation(100, 100);
        f.setPreferredSize(dim);

        JButton save = new JButton("save");
        JButton load = new JButton("load");
        JButton edit = new JButton("edit");
        JButton search = new JButton("search");
        JButton delete = new JButton("delete");
        JButton exit = new JButton("exit");

        JPanel search_bar = new JPanel();
        JTextField search_text = new JTextField(28);
        search_bar.add(search_text);
        search_bar.add(search);
        f.add(search_bar);
        f.add(search_text, BoxLayout.X_AXIS);
        search_bar.setSize(300,150);

        f.setLayout(new FlowLayout());
        JScrollPane scrollPane = new JScrollPane(main_table);
        f.add(scrollPane);
        f.pack();
        f.setVisible(true);
        f.add(save);
        f.add(load);
        f.add(edit);
        f.add(delete);
        f.add(exit);
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<String> row = new Vector();
                List<Users> userListResult = dao.getUserList(hm);
                model.setRowCount(0);

                for (Users userList : userListResult) {
                    main_table.setModel(model);
                    model.addRow(new Object[]{userList.getPN(),userList.getName(), userList.getTITLE(), userList.setCONTENTS(), userList.setReg_date()});
                }
            }
        });
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new newWindow();

            }
        });

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                HashMap hm = new HashMap();
                dao.getUserList(hm);
                model.setRowCount(0);

                for (Users userList : userListResult) {
                String search = search_text.getText();
                if (search.equals(userList.getPN())||search.equals(userList.getName())||search.equals(userList.getTITLE())||search.equals(userList.getCONTENTS())||search.equals(userList.setReg_date())) {
                    main_table.setModel(model);
                    model.addRow(new Object[]{userList.getPN(), userList.getName(), userList.getTITLE(), userList.setCONTENTS(), userList.setReg_date()});
                }
                }


            }
        });

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new newWindow3();
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new newWindow2();
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        try {

            HashMap<String, Object> hm = new HashMap<>();
            hm.put("ID", "a");
            List<Users> userListResult = dao.getUserList(hm);
            for (Users userList : userListResult) {
                logger.info(userList.getId() + " | " + userList.getName());
            }

        } catch (Exception ex) {
            CommonUtil.printErrorLog(logger, ex);
        } finally {
            long responseTime = System.currentTimeMillis() - stasrtTime;

            logger.info("####################### Doje End(" + responseTime + "msc) ########################");

        }
    }


    static class newWindow extends JFrame {

        JTextField fild_id1 = new JTextField();
        JTextField fild_name1 = new JTextField();
        JTextField fild_title1 = new JTextField();
        JTextField fild_contents1 = new JTextField();

        newWindow() {                                                                                                       //insert


            Dimension dim = new Dimension(400, 150);
            DojeDao dao = new DojeDao();
            HashMap<String, Object> hm = new HashMap<>();

            JFrame f2 = new JFrame("추가할 값을 입력하세요.");
            f2.setLocation(200, 400);
            f2.setPreferredSize(dim);

            JPanel id = new JPanel();
            id.setLayout(new BoxLayout(id, BoxLayout.X_AXIS));
            id.add(new JLabel("ID : "));
            id.add(fild_id1);

            JPanel name = new JPanel();
            name.setLayout(new BoxLayout(name, BoxLayout.X_AXIS));
            name.add(new JLabel("name : "));
            name.add(fild_name1);

            JPanel title = new JPanel();
            title.setLayout(new BoxLayout(title, BoxLayout.X_AXIS));
            title.add(new JLabel("title :  "));
            title.add(fild_title1);

            JPanel contents = new JPanel();
            contents.setLayout(new BoxLayout(contents, BoxLayout.X_AXIS));
            contents.add(new JLabel("contents :  "));
            contents.add(fild_contents1);

            JButton btn = new JButton("insert");
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        HashMap hm = new HashMap();
                        hm.put("id", fild_id1.getText());
                        hm.put("name", fild_name1.getText());
                        hm.put("title", fild_title1.getText());
                        hm.put("contents", fild_contents1.getText());

                        dao.putUserList(hm);

                        Vector<String> row = new Vector();
                        List<Users> userListResult = dao.getUserList(hm);
                        model.setRowCount(0);

                        for (Users userList : userListResult) {
                            main_table.setModel(model);
                            model.addRow(new Object[]{userList.getPN(), userList.getName(), userList.getTITLE(), userList.setCONTENTS(), userList.setReg_date()});
                        }

                        JFrame f;
                        f = new JFrame();
                        JOptionPane.showMessageDialog(f, "Error in Upload.", "Alert", JOptionPane.WARNING_MESSAGE);


                    f2.dispose();

                }
            });
            f2.pack();
            f2.setVisible(true);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(id);
            panel.add(name);
            panel.add(title);
            panel.add(contents);

            f2.add(panel, BorderLayout.CENTER);
            f2.add(btn, BorderLayout.SOUTH);
            f2.pack();
            f2.setVisible(true);
        }
    }

    static class newWindow3 extends JFrame {
        int row = main_table.getSelectedRow();
        String No = (String) main_table.getValueAt(row, 0);
        String name = (String) main_table.getValueAt(row, 1);
        String checktitle = (String) main_table.getValueAt(row, 2);
        String contents = (String) main_table.getValueAt(row, 3);
        JTextField fild_pn3 = new JTextField(No);
        JTextField fild_name3 = new JTextField(name);
        JTextField fild_checktitle3 = new JTextField(checktitle);
        JTextField fild_contents3 = new JTextField(contents);
        HashMap<String, Object> hm = new HashMap<>();

        newWindow3() {                                                                                                      //update
            Dimension dim = new Dimension(400, 150);
            DojeDao dao = new DojeDao();

            JFrame f3 = new JFrame("수정할 값을 입력하세요.");
            f3.setLocation(200, 400);
            f3.setPreferredSize(dim);

            JPanel name = new JPanel();
            name.setLayout(new BoxLayout(name, BoxLayout.X_AXIS));
            name.add(new JLabel("name : "));
            name.add(fild_name3);

            JPanel title = new JPanel();
            title.setLayout(new BoxLayout(title, BoxLayout.X_AXIS));
            title.add(new JLabel("title :"));
            title.add(fild_checktitle3);

            JPanel contents = new JPanel();
            contents.setLayout(new BoxLayout(contents, BoxLayout.X_AXIS));
            contents.add(new JLabel("contents :"));
            contents.add(fild_contents3);



            JButton btn = new JButton("update");
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    hm.put("pn",fild_pn3.getText());
                    hm.put("name", fild_name3.getText());
                    hm.put("title", fild_checktitle3.getText());
                    hm.put("contents", fild_contents3.getText());


                    dao.editUserList(hm);

                    Vector<String> row = new Vector();
                    List<Users> userListResult = dao.getUserList(hm);
                    model.setRowCount(0);

                    for (Users userList : userListResult) {
                        main_table.setModel(model);
                        model.addRow(new Object[]{userList.getPN(),userList.getName(), userList.getTITLE(), userList.setCONTENTS(), userList.setReg_date()});
                    }

                    f3.dispose();
                }
            });
            f3.pack();
            f3.setVisible(true);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(name);
            panel.add(title);
            panel.add(contents);

            f3.add(panel, BorderLayout.CENTER);
            f3.add(btn, BorderLayout.SOUTH);
            f3.pack();
            f3.setVisible(true);
        }

    }

    static class newWindow2 extends JFrame {
        int row = main_table.getSelectedRow();
        String value1 = (String) main_table.getValueAt(row, 0);
        JTextField fild_pn2 = new JTextField(value1);
        JTextField fild_contents2 = new JTextField();
        Dimension dim = new Dimension(400, 150);
        DojeDao dao = new DojeDao();
        HashMap<String, Object> hm = new HashMap<>();

        DefaultTableModel model = new DefaultTableModel(header, 1);

        newWindow2() {                                                                                                      //delete
            Dimension dim = new Dimension(400, 150);
            DojeDao dao = new DojeDao();

            JFrame f4 = new JFrame("delete");
            f4.setLocation(200, 400);
            f4.setPreferredSize(dim);

            JPanel pn = new JPanel();
            pn.add(fild_pn2);

            JPanel btn_panel = new JPanel();
            JLabel warning_del = new JLabel("삭제하시겠습니까?");
            JButton btn_YES = new JButton("Yes");
            JButton btn_NO = new JButton("No");

            btn_NO.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    f4.dispose();
                }
            });


            btn_YES.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {


                    HashMap hm = new HashMap();

                    hm.put("pn", fild_pn2.getText());

                    dao.delUserList(hm);

                    Vector<String> row = new Vector();
                    List<Users> userListResult = dao.getUserList(hm);


                    model.setRowCount(0);

                    for (Users userList : userListResult) {
                        main_table.setModel(model);
                        model.addRow(new Object[]{userList.getPN(),userList.getName(), userList.getTITLE(), userList.setCONTENTS(), userList.setReg_date()});
                    }

                    f4.dispose();
                }
            });
            f4.pack();
            f4.setVisible(true);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(pn);

            f4.add(panel);
            panel.setVisible(false);
            f4.add(warning_del,BorderLayout.CENTER);
            btn_panel.add(btn_YES);
            btn_panel.add(btn_NO);
            f4.add(btn_panel,BorderLayout.SOUTH);
            f4.pack();
            f4.setVisible(true);
        }

    }
}
