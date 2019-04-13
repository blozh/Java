package SoftEngineer;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.alibaba.fastjson.JSON;

public class SystemManagerUI {
	static String ip = "192.168.31.248";
	JLabel[] l = new JLabel[6];
	JTextField[] tf = new JTextField[6];
	JButton[] b = new JButton[3];
	Font font = new Font("Simsun", Font.PLAIN, 15);
	JPanel p = new JPanel();
	JScrollPane sp;/* 用JScrollPane装载JTable，这样超出范围的列就可以通过滚动条来查看 */
	GridBagLayout gb = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();

	private DefaultTableModel model = new DefaultTableModel();
	private JTable t = new JTable(model);

	JPanel initPanel(String ip) {
		this.ip = ip;
		p.setLayout(gb);
		l[0] = new JLabel("学号");
		l[1] = new JLabel("姓名");
		l[2] = new JLabel("性别");
		l[3] = new JLabel("年龄");
		l[4] = new JLabel("班级");
		l[5] = new JLabel("学院");

		b[0] = new JButton("查询");
		b[1] = new JButton("修改");
		b[2] = new JButton("删除");

		b[0].setBackground(Color.white);
		b[1].setBackground(Color.white);
		b[2].setBackground(Color.white);

		b[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand() == "查询") {
					try {
						doQuery();
					} catch (Exception e1) {
					}
				}
			}

			

		});
		b[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				update();
			}

			private void update() {
				Map map = new HashMap();
				map.put("stuId", tf[0].getText());
				map.put("name", tf[1].getText());
				map.put("sex", tf[2].getText().equals("女")?2+"":1+"");
				map.put("age", tf[3].getText());
				try {
					Demo.post(map, "http://"+ SystemManagerUI.ip +":8080/ComprehensiveEvaluation/admin/update");
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					try {
						doQuery();
					} catch (Exception e) {
					}
				}
			}

		});
		b[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delete();
			}

			private void delete() {
				String stuId = tf[0].getText();
				Map map = new HashMap();
				map.put("stuId", stuId);
				try {
					String s=Demo.post(map, "http://" + SystemManagerUI.ip + ":8080/ComprehensiveEvaluation/admin/delete");
				} catch (Exception e) {
				}
				finally{
					try {
						doQuery();
					} catch (Exception e) {
					}
				}
			}
			
		});

		model.setColumnIdentifiers(new String[] { "学号", "姓名", "性别", "年龄", "班级", "学院" });
		sp = new JScrollPane(t);// 当t=null时，如果执行sp=new
								// JScrollPane(t)然后再让t指向一个有内容的Jtable的话，JScroll依然为空

		addJComponent(b[0], 0, 0, 8, 1, 1);
		addJComponent(b[1], 0, 1, 8, 1, 1);
		addJComponent(b[2], 0, 2, 8, 1, 1);

		gbc.ipadx = 100;

		for (int i = 0; i < 6; i++) {
			addJComponent(l[i], 0, i + 3, 3, 1, 0);
		}
		for (int i = 0; i < 6; i++) {
			tf[i] = new JTextField();
			addJComponent(tf[i], 1, i + 3, 5, 1, 1);
		}

		addJComponent(sp, 6, 0, 4, 10, 4);
		return p;
	}


	JTable getJTable() {
		return t;
	}

	void addJComponent(JComponent c, int x, int y, int gx, int gy, int weightx) {
		gbc.weightx = weightx;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = gx;
		gbc.gridheight = gy;
		gb.setConstraints(c, gbc);
		c.setFont(font);
		p.add(c);
	}
	public void doQuery() throws Exception {
		 model.setNumRows(0);
		String s = Demo.get("http://" + SystemManagerUI.ip + ":8080/ComprehensiveEvaluation/admin/getStudent");
		List<Student> stus = JSON.parseArray(s, Student.class);
		for (Student stu : stus) {
			model.addRow(new String[] { stu.getStuId(), stu.getName(), stu.getSex() == 1 ? "男" : "女",
					stu.getAge() + "", stu.getClassid(), stu.getCollegeid() });
		}
	}

}

