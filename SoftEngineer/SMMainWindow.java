package SoftEngineer;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class SMMainWindow extends WindowAdapter {
	static String ip;
	JFrame f = new JFrame("教务处管理员页面");
	JTabbedPane tp = new JTabbedPane();
	SystemManagerUI sm = new SystemManagerUI();
	JFrame before;

	void initWindow() {
		// 创建选项卡面板
		tp.addTab("学生界面管理", sm.initPanel(ip));
		tp.setSelectedIndex(0);// 设置默认选项卡为学生成绩管理
		f.add(tp);
		f.setBounds(350, 300, 900, 600);
		f.setLocationRelativeTo(null);
		f.addWindowListener(this);// 添加窗口关闭的事件监听器

		f.setVisible(true);
	}

	@Override
	public void windowClosing(WindowEvent windowEvent) {
		super.windowClosing(windowEvent);
		before.setVisible(true);
	}

	public SMMainWindow(String ip, JFrame before) {
		SMMainWindow.ip = ip;
		this.before=before;
		initWindow();
	}
}
