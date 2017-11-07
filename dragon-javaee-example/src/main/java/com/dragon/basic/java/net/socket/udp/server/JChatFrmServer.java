package com.dragon.basic.java.net.socket.udp.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class JChatFrmServer extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1891308673790140280L;

	private JTextArea jta;
	private JTextField jtf;
	private JButton jb;
	private JPanel jp;
	
	private ServerThread serverThread;

	public JChatFrmServer() {
		setTitle("服务器");
		jta = new JTextArea();
		jtf = new JTextField(15);
		jb = new JButton("发送");
		jb.addActionListener(this);
		jp = new JPanel();
		jp.add(jtf);
		jp.add(jb);

		this.add(jta, "Center");
		this.add(jp, "South");

		this.setBounds(300, 200, 300, 200);
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		serverThread = new ServerThread(jta);
		serverThread.start();

		/** 窗体关闭按钮事件 */
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(null,
						"<html><font size=3>确定退出吗？</html>", "系统提示",
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.INFORMATION_MESSAGE) == 0) {
					System.exit(0);
					serverThread.closeSocket();
				} else {
					return;
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jb) {
			byte[] buffer = jtf.getText().trim().getBytes();
			serverThread.sendData(buffer);
		}
	}

	public void showMessage(String message) {
		jta.append(message);
	}

	public static void main(String[] args) {
		new JChatFrmServer();
	}

}