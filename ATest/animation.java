package ATest;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class animation implements ActionListener {
	int x = 20;
	int y =20;
	JFrame frame;

	class MyPanel extends JPanel {
		public void paintComponent(Graphics g) {
			g.setColor(Color.blue);
			g.fillOval(x,y,100,100);
		}
	}

	public static void main(String[] args) {
		animation a = new animation();
		a.go();
	}
	
	public void go() {
		frame = new JFrame();
		MyPanel panel = new MyPanel();
		JButton button = new JButton("start");
		
		button.addActionListener(this);
		
		frame.getContentPane().add(BorderLayout.CENTER,panel);
		frame.getContentPane().add(BorderLayout.SOUTH,button);
		frame.setSize(300,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	/*	while (x < 130 & y <130 ) {
			x++;
			y++;

			frame.repaint();

			try {
				Thread.sleep(50);		//�ӳ�
			} catch(Exception ex) { }
		}	*/	//�˴��������ʹ������������ִ��
	}

	public void actionPerformed(ActionEvent event) {		//�˴�������ť��ʹ�ö���ֻ���ڳ�ĩ״̬�������м�켣
		new Thread(new Runnable() {
			@Override
			public void run() {
				while ( x < 130 & y < 130 ) {
					x++;
					y++;
					frame.repaint();

					try {
						Thread.sleep(50);		//�ӳ٣�����һ������
					} catch(Exception ex) { }
				}
			}
		}).start();
	}
}