package com.happytalk.main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
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

import static java.awt.FlowLayout.CENTER;

public class Doje extends JFrame {

    static DojeDao dao = new DojeDao();
    static HashMap<String, Object> hm = new HashMap<>();
    static String header[] = {"No", "이름(NAME)", "제목(title)", "내용(contents)", "작성시간"};
    static DefaultTableModel model = new DefaultTableModel(header, 0);
    static Logger logger = Logger.getLogger("app");
    static JTable main_table = new JTable(model);
    static String combo_list[] = {"title", "contents", "name"};
    static JComboBox search_list = new JComboBox(combo_list);
    static JTextField search_text = new JTextField(28);
    static JPanel page_btn = new JPanel();
    static search_windows sw = null;
    static insert_windows insert = null;
    static edit_windows update = null;
    static delete_windows_YES del = null;

    public static void main(String[] args) {
        logger.info("####################### Doje Start ########################");
        sw = new search_windows();
        insert = new insert_windows();
        update = new edit_windows();
        del = new delete_windows_YES();

        long stasrtTime = System.currentTimeMillis();

        Dimension dim = new Dimension(1170, 294);
        JFrame main_frame = new JFrame("GUI Table");
        main_frame.setBackground(Color.WHITE);
        main_frame.setLocation(100, 100);
        main_frame.setPreferredSize(dim);

        JButton save = new JButton("저장");
        save.setBackground(new Color(224, 255, 255));
        JButton edit = new JButton("수정");
        edit.setBackground(new Color(224, 255, 255));
        JButton search = new JButton("검색");
        search.setBackground(new Color(224, 255, 255));
        JButton delete = new JButton("삭제");
        delete.setBackground(new Color(224, 255, 255));
        JButton exit = new JButton("나가기");
        exit.setBackground(new Color(224, 255, 255));
        JPanel search_bar = new JPanel();
        search_bar.setBackground(new Color(255, 240, 245));
        search_bar.add(save);
        search_bar.add(edit);
        search_bar.add(search_list);
        search_bar.add(search_text);
        search_bar.add(search);
        search_bar.add(delete);
        search_bar.add(exit);
        main_frame.add(search_bar, BorderLayout.NORTH);
        search_bar.setSize(300, 150);
        search_list.setBackground(new Color(255,192,203));
        page_btn.setBackground(new Color(255, 240, 245));
        main_table.setBackground(new Color(255, 255, 240));
        main_frame.add(main_table, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(main_table);
        main_frame.add(scrollPane);
        main_frame.add(page_btn, BorderLayout.SOUTH);
        main_frame.pack();
        main_frame.setVisible(true);

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                insert.insert_code();

            }
        });

        search.addActionListener(new ActionListener() {//콤보박스 설정
            @Override
            public void actionPerformed(ActionEvent e) {
                if(search_text == null || search_text.getText().length() == 0 ) {
                    page_btn.removeAll();
                    sw.search_value(true);
                }
                else{
                    page_btn.removeAll();
                    sw.search_value(false);
                }

            }
        });

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (main_table.getSelectedRow() == -1) {
                    JFrame f;
                    f = new JFrame();
                    JOptionPane.showMessageDialog(f, "수정할 내용을 선택을 해주십시오.", "**경고**", JOptionPane.ERROR_MESSAGE);
                } else {
                    update.edit_code();
                }
            }
        });

        delete.addActionListener(new ActionListener() {
            JTextField tf = new JTextField();

            @Override
            public void actionPerformed(ActionEvent e) {
                if (main_table.getSelectedRow() == -1) {
                    JFrame f;
                    f = new JFrame();
                    JOptionPane.showMessageDialog(f, "삭제할 내용을 선택을 해주십시오.", "**경고**", JOptionPane.ERROR_MESSAGE);
                } else {
                    JFrame del_option = new JFrame();
                    int result = JOptionPane.showConfirmDialog(del_option, "한번 삭제한 값은 더이상 되돌릴 수 없습니다.\n정말로 삭제하시겠습니까?", "확인창", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        del.delete_code();
                        tf.setText("예");
                    } else {
                        tf.setText("아니오");
                    }
                }
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        long responseTime = System.currentTimeMillis() - stasrtTime;

        logger.info("####################### Doje End(" + responseTime + "msc) ########################");
    }

    static class search_windows extends Frame {
        search_windows() {
            search_value(true);
        }

        public void search_value(boolean first) {
            String comboBox_list = search_list.getSelectedItem().toString();
            String get_search_text = search_text.getText();
            if (first == true) {
                comboBox_list = "first";
            }


            int block_count = 10;
            int i = 0;
            int all_count = 0;
            hm.clear();
            hm.put("start", i);
            hm.put(comboBox_list, get_search_text);
            System.out.println(hm);
            all_count = dao.countUserList(hm);
            model.setRowCount(0);
            page_btn.removeAll();
            List<Users> Users_Result = dao.findUserList(hm);
            for (Users userList : Users_Result) {
                main_table.setModel(model);
                model.addRow(new Object[]{userList.getNo(), userList.getNAME(), userList.getTITLE(), userList.getCONTENTS(), userList.getMAKE_TIME()});
            }
            int block_num = (int) Math.ceil((double) all_count / block_count);
            System.out.println("총 페이지 수 : " + block_num);
            if(block_num == 0){
                JFrame f;
                f = new JFrame();
                JOptionPane.showMessageDialog(f, "찾는 값이 없습니다.", "**안내**", JOptionPane.ERROR_MESSAGE);
                search_text.setText("");
                sw.search_value(true);
            }
            if (first == true) {
                for (i = 1; i <= block_num; i++) {
                    int ather = i;
                    JButton page_num = new JButton(Integer.toString(i));
                    page_num.setBackground(new Color(224, 255, 255));
                    page_btn.revalidate();
                    page_btn.repaint();
                    page_btn.add(page_num);
                    page_num.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                hm.clear();
                                model.setRowCount(0);
                                int page = ather;
                                page = (page - 1) * 10;
                                hm.put("start", page);
                                System.out.println(hm);
                                model.setRowCount(0);
                                List<Users> Users_Result = dao.findUserList(hm);
                                for (Users userList : Users_Result) {
                                    main_table.setModel(model);
                                    model.addRow(new Object[]{userList.getNo(), userList.getNAME(), userList.getTITLE(), userList.getCONTENTS(), userList.getMAKE_TIME()});
                                }
                                hm.clear();
                            }
                        });
                }
            }
            else if(first == false) {
                for (i = 1; i <= block_num; i++) {
                    int ather = i;
                    JButton page_num = new JButton(Integer.toString(i));
                    page_num.setBackground(new Color(224, 255, 255));
                    page_btn.revalidate();
                    page_btn.repaint();
                    page_btn.add(page_num);
                    page_num.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            model.setRowCount(0);
                            int page = ather;
                            page = (page - 1) * 10;
                            hm.put("start", page);
                            System.out.println(hm);
                            model.setRowCount(0);

                            List<Users> Users_Result = dao.findUserList(hm);
                            for (Users userList : Users_Result) {
                                main_table.setModel(model);
                                model.addRow(new Object[]{userList.getNo(), userList.getNAME(), userList.getTITLE(), userList.getCONTENTS(), userList.getMAKE_TIME()});
                            }

                        }
                    });
                }
            }
        }
    }

    static class insert_windows extends JFrame {

        insert_windows() {
        }

        public void insert_code(){
            JTextField fild_id = new JTextField(30);
            JTextField fild_name = new JTextField(30);
            JTextField fild_title = new JTextField(30);

            JTextField fild_contents = new JTextField(30);

            Dimension dim = new Dimension(400, 150);
            DojeDao dao = new DojeDao();

            JDialog insert_frame = new JDialog(this, "추가할 값을 입력하시오", true);
            insert_frame.setLocation(200, 400);
            insert_frame.setPreferredSize(dim);


            JPanel insert_name = new JPanel();
            insert_name.setLayout(new BoxLayout(insert_name, BoxLayout.X_AXIS));
            insert_name.add(new JLabel("이름     "));
            insert_name.add(fild_name);
            insert_name.setBackground(new Color(224, 255, 255));
            JPanel insert_title = new JPanel();
            insert_title.setLayout(new BoxLayout(insert_title, BoxLayout.X_AXIS));
            insert_title.add(new JLabel("제목     "));
            insert_title.add(fild_title);
            insert_title.setBackground(new Color(224, 255, 255));
            JPanel insert_contents = new JPanel();
            insert_contents.setLayout(new BoxLayout(insert_contents, BoxLayout.X_AXIS));
            insert_contents.add(new JLabel("내용     "));
            insert_contents.setSize(300, 150);
            insert_contents.add(fild_contents);
            insert_contents.setBackground(new Color(224, 255, 255));
            JButton insert_btn = new JButton("저장");
            insert_btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (fild_name.getText() != null && fild_name.getText().equals("") || fild_title.getText() != null && fild_title.getText().equals("") || fild_contents.getText() != null && fild_contents.getText().equals("")) {
                        JFrame f;
                        f = new JFrame();
                        JOptionPane.showMessageDialog(f, "공백이있습니다.", "**오류**", JOptionPane.ERROR_MESSAGE);
                    } else {
                        HashMap hm = new HashMap();
                        hm.put("name", fild_name.getText());
                        hm.put("title", fild_title.getText());
                        hm.put("contents", fild_contents.getText());
                        dao.putUserList(hm);

                        sw.search_value(true);
                        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                        insert_frame.dispose();
                    }


                }
            });
            insert_btn.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    System.out.println(e.getKeyChar() + " keyTyped key");
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if (fild_name.getText() != null && fild_name.getText().equals("") || fild_title.getText() != null && fild_title.getText().equals("") || fild_contents.getText() != null && fild_contents.getText().equals("")) {
                            JFrame f;
                            f = new JFrame();
                            JOptionPane.showMessageDialog(f, "공백이있습니다.", "**오류**", JOptionPane.ERROR_MESSAGE);
                        } else {
                            HashMap hm = new HashMap();
                            hm.put("name", fild_name.getText());
                            hm.put("title", fild_title.getText());
                            hm.put("contents", fild_contents.getText());
                            dao.putUserList(hm);

                            sw.search_value(false);

                            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                            insert_frame.dispose();

                        }
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });

            JPanel insert_panel = new JPanel();
            insert_panel.setLayout(new BoxLayout(insert_panel, BoxLayout.Y_AXIS));
            insert_panel.add(insert_name);
            insert_panel.add(insert_title);
            insert_panel.add(insert_contents);

            insert_frame.add(insert_panel, BorderLayout.CENTER);
            insert_frame.add(insert_btn, BorderLayout.SOUTH);
            insert_btn.setBackground(new Color(255, 240, 245));
            insert_frame.pack();
            insert_frame.setVisible(true);
        }

    }


    static class edit_windows extends JFrame {

        edit_windows() {
        }

        public void edit_code(){
            int row = main_table.getSelectedRow();
            String No = (String) main_table.getValueAt(row, 0);
            String name = (String) main_table.getValueAt(row, 1);
            String title = (String) main_table.getValueAt(row, 2);
            String contents = (String) main_table.getValueAt(row, 3);
            JTextField fild_pn3 = new JTextField(No);
            JTextField fild_name3 = new JTextField(name);
            JTextField fild_title3 = new JTextField(title);
            JTextField fild_contents3 = new JTextField(contents);

            Dimension dim = new Dimension(400, 150);
            DojeDao dao = new DojeDao();
            JDialog edit_frame = new JDialog(this, "수정할 값을 입력하세요.", true);
            edit_frame.setLocation(200, 400);
            edit_frame.setPreferredSize(dim);

            JPanel edit_name = new JPanel();
            edit_name.setLayout(new BoxLayout(edit_name, BoxLayout.X_AXIS));
            edit_name.add(new JLabel("이름  "));
            edit_name.setBackground(new Color(224, 255, 255));

            JPanel edit_title = new JPanel();
            edit_title.setLayout(new BoxLayout(edit_title, BoxLayout.X_AXIS));
            edit_title.add(new JLabel("제목  "));
            edit_title.setBackground(new Color(224, 255, 255));

            JPanel edit_contents = new JPanel();
            edit_contents.setLayout(new BoxLayout(edit_contents, BoxLayout.X_AXIS));
            edit_contents.add(new JLabel("내용  "));
            edit_contents.setBackground(new Color(224, 255, 255));
            edit_name.add(fild_name3);
            fild_name3.setEnabled(false);
            edit_title.add(fild_title3);
            edit_contents.add(fild_contents3);

            JButton edit_btn = new JButton("수정");
            edit_btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (fild_pn3.getText() != null && fild_pn3.getText().equals("") || fild_name3.getText() != null && fild_name3.getText().equals("") ||
                            fild_title3.getText() != null && fild_title3.getText().equals("") || fild_contents3.getText() != null && fild_contents3.getText().equals("")) {
                        JFrame f;
                        f = new JFrame();
                        JOptionPane.showMessageDialog(f, "공백이 있습니다.", "**오류**", JOptionPane.ERROR_MESSAGE);
                    } else {
                        hm.clear();
                        hm.put("No", fild_pn3.getText());
                        hm.put("name", fild_name3.getText());
                        hm.put("title", fild_title3.getText());
                        hm.put("contents", fild_contents3.getText());

                        dao.editUserList(hm);

                        sw.search_value(false);
                        edit_frame.dispose();
                    }
                }

            });

            edit_btn.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    System.out.println(e.getKeyChar() + " keyTyped key");
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if (fild_pn3.getText() != null && fild_pn3.getText().equals("") || fild_name3.getText() != null && fild_name3.getText().equals("") ||
                                fild_title3.getText() != null && fild_title3.getText().equals("") || fild_contents3.getText() != null && fild_contents3.getText().equals("")) {
                            JFrame f;
                            f = new JFrame();
                            JOptionPane.showMessageDialog(f, "공백이 있습니다.", "**오류**", JOptionPane.ERROR_MESSAGE);
                        } else {
                            hm.put("No", fild_pn3.getText());
                            hm.put("name", fild_name3.getText());
                            hm.put("title", fild_title3.getText());
                            hm.put("contents", fild_contents3.getText());


                            dao.editUserList(hm);

                            sw.search_value(false);
                            edit_frame.dispose();
                        }
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });

            edit_btn.setBackground(new Color(255, 240, 245));

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(edit_name);
            panel.add(edit_title);
            panel.add(edit_contents);

            edit_frame.add(panel, BorderLayout.CENTER);
            edit_frame.add(edit_btn, BorderLayout.SOUTH);
            edit_frame.pack();
            edit_frame.setVisible(true);
        }
    }


    static class delete_windows_YES extends JFrame {

        delete_windows_YES() {
        }

        public void delete_code(){
            int row = main_table.getSelectedRow();
            String row_count = (String) main_table.getValueAt(row, 0);
            JTextField fild_pn2 = new JTextField(row_count);

            HashMap hm = new HashMap();

            hm.put("No", fild_pn2.getText());

            dao.delUserList(hm);

            sw.search_value(true);
        }

    }
}