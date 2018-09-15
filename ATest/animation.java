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
				Thread.sleep(50);		//延迟
			} catch(Exception ex) { }
		}	*/	//此处代码可以使动画开场正常执行
	}

	public void actionPerformed(ActionEvent event) {		//此处单击按钮后使得动画只存在初末状态，并无中间轨迹
		new Thread(new Runnable() {
			@Override
			public void run() {
				while ( x < 130 & y < 130 ) {
					x++;
					y++;
					frame.repaint();

					try {
						Thread.sleep(50);		//延迟，避免一下跑完
					} catch(Exception ex) { }
				}
			}
		}).start();
	}
}