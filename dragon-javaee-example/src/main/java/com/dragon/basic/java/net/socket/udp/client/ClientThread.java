package com.dragon.basic.java.net.socket.udp.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Properties;

import javax.swing.JTextArea;

public class ClientThread extends Thread {

	private String serverIP = "127.0.0.1";
	private int serverPORT = 8888;
	private int receivePORT = 6666;
	// 声明发送信息的数据报套结字
	private DatagramSocket sendSocket = null;
	// 声明发送信息的数据包
	private DatagramPacket sendPacket = null;
	// 声明接受信息的数据报套结字
	private DatagramSocket receiveSocket = null;
	// 声明接受信息的数据报
	private DatagramPacket receivePacket = null;

	// 缓冲数组的大小
	public static final int BUFFER_SIZE = 5120;
	private byte inBuf[] = null; // 接收数据的缓冲数组

	private JTextArea jta;

	// 构造函数
	public ClientThread(JTextArea jta) {
		this.jta = jta;
		getPropertiesInfo();
	}

	public void run() {
		try {
			inBuf = new byte[BUFFER_SIZE];
			receivePacket = new DatagramPacket(inBuf, inBuf.length);
			receiveSocket = new DatagramSocket(receivePORT);
		} catch (Exception e) {
			e.printStackTrace();
		}

		while (true) {
			if (receiveSocket == null) {
				break;
			} else {
				try {
					receiveSocket.receive(receivePacket);
					String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
					jta.append("收到数据" + message + "\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 该方法用来获得服务器属性文件中的IP、PORT
	 */
	private void getPropertiesInfo() {
		Properties prop = new Properties();
		InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("UDPServer.properties");
		try {
			// 获得相应的键值对
			prop.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 根据相应的键获得对应的值
		serverIP = prop.getProperty("server.ip");
		serverPORT = Integer.parseInt(prop.getProperty("serverudp.port"));
		receivePORT = Integer.parseInt(prop.getProperty("receiveudp.port"));
	}

	public void sendData(byte buffer[]) {
		try {
			InetAddress address = InetAddress.getByName(serverIP);
			sendPacket = new DatagramPacket(buffer, buffer.length, address, serverPORT);
			sendSocket = new DatagramSocket();
			sendSocket.send(sendPacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeSocket() {
		receiveSocket.close();
	}

}