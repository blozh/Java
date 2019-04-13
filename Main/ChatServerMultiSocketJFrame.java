package Main;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class ChatServerMultiSocketJFrame extends JFrame implements ActionListener{
	private String name;
	private JComboBox combox;
	private JTextField text;
	private JTabbedPane tab;
	public ChatServerMultiSocketJFrame(int port,String name) throws Exception{
		super("聊天室 "+name+"  "+InetAddress.getLocalHost());
		this.setBounds(320,240,440,240);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		JToolBar toolbar=new JToolBar();
		this.getContentPane().add(toolbar,"North");
		toolbar.add(new JLabel("主机"));
		combox=new JComboBox();
		combox.addItem("127.0.0.1");
		toolbar.add(combox);
		combox.setEditable(true);
		toolbar.add(new JLabel("端口"));
		text=new JTextField(""+port);
		toolbar.add(text);
		JButton button_connect=new JButton("连接");
		button_connect.addActionListener(this);
		toolbar.add(button_connect);
		tab=new JTabbedPane();
		this.getContentPane().add(tab);

		this.setVisible(true);
		this.name=name;
		while(true) {//这不会是死循环吗？
			Socket client=new ServerSocket(port).accept();
			tab.addTab(name,new TabPageJPanel(client));
			tab.setSelectedIndex(tab.getTabCount()-1);
			port++;
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand()=="连接") {
			String host=(String)combox.getSelectedItem();
			int port=Integer.parseInt(text.getText());
			try {
				tab.addTab(name, new TabPageJPanel(new Socket(host,port)));
				tab.setSelectedIndex(tab.getTabCount()-1);
			}catch(IOException es) {
				es.printStackTrace();
			}
		}
	}//actionPerformed
	private class TabPageJPanel extends JPanel implements Runnable,ActionListener{
		Socket socket;
		Thread thread;
		JTextArea text_receiver;
		JTextField text_sender;
		JButton buttons[];
		PrintWriter cout;
		int index;
		TabPageJPanel(Socket socket){
			super(new BorderLayout());
			this.text_receiver=new JTextArea();
			this.text_receiver.setEditable(false);
			this.add(new JScrollPane(text_receiver));
			
			JPanel panel=new JPanel();
			this.add(panel,"South");
			
			this.text_sender=new JTextField(16);
			panel.add(this.text_sender);
			this.text_sender.addActionListener(this);
			String strs[]= {"发送","离线","删除页"};
			buttons=new JButton[strs.length];
			for(int i=0;i<buttons.length;i++) {
				buttons[i]=new JButton(strs[i]);
				panel.add(buttons[i]);
				buttons[i].addActionListener(this);
			}
			buttons[2].setEnabled(false);
			this.setVisible(true);
			this.socket=socket;
			this.thread=new Thread(this);
			this.thread.start();
			
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getActionCommand().equals("发送")) {
				this.cout.println(name+"说： "+text_sender.getText());
				this.cout.flush();
				text_receiver.append("我说： "+text_sender.getText()+"\n");
				text_sender.setText("");
			}
			if(e.getActionCommand().equals("离线")) {
				text_receiver.append("我离线\n");
				this.cout.println(name+"离线\n"+"bye");
				buttons[0].setEnabled(false);
				buttons[1].setEnabled(false);
				buttons[2].setEnabled(true);
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				this.cout=new PrintWriter(socket.getOutputStream(),true);
				this.cout.println(name);
				this.cout.flush();
				
				BufferedReader cin=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String name=cin.readLine();
				index=tab.getSelectedIndex();
				//tab.setTitleAt(index,name);
				String aline=cin.readLine();
				while(aline!=null&&!aline.equals("bye")) {
					tab.setSelectedIndex(index);
					text_receiver.append(aline+"\r\n");
					Thread.sleep(1000);
					aline=cin.readLine();
				}
				cin.close();
				cout.close();
				socket.close();
				buttons[0].setEnabled(false);
				buttons[1].setEnabled(false);
				buttons[2].setEnabled(false);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	public static void main(String args[]) throws Exception{
		new ChatServerMultiSocketJFrame(12001,"花仙子");
	}
}

