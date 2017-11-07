package com.dragon.basic.java.net.socket.udp.client;

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

public class JChatFrmClient extends JFrame implements ActionListener {

	private static final long serialVersionUID = -6331006337509088750L;

	private JTextArea jta;
	private JTextField jtf;
	private JButton jb;
	private JPanel jp;
	
	private ClientThread clientThread;

	public JChatFrmClient() {
		setTitle("客户端");
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
		clientThread = new ClientThread(jta);
		clientThread.start();

		/** 窗体关闭按钮事件 */
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(null,
						"<html><font size=3>确定退出吗？</html>", "系统提示",
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.INFORMATION_MESSAGE) == 0) {
					System.exit(0);
					clientThread.closeSocket();
				} else {
					return;
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jb) {
			byte buffer[] = jtf.getText().trim().getBytes();
			clientThread.sendData(buffer);
		}
	}
	
	public static void main(String[] args) {
		new JChatFrmClient();
	}

}