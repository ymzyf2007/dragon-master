package com.dragon.basic.java.net.socket.udp.echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * UDP服务端
 * 1、创建一个DatagramSocket实例，指定本地端口号，并可以选择指定本地地址。此时，服务器已经准备好从任何客户端接收数据报文
 * 2、使用DatagramSocket类的receive()方法接收一个DatagramPacket实例。当receive()方法返回时，数据报文
 *   就包含了客户端的地址，这样我们就知道了回复信息应该发送到什么地方。
 *
 */
public class UDPEchoServer {
	
	private static final int ECHOMAX = 255;	// 数据报文最大字节数，大于255，则丢弃其余部分
	
	// 修改程序，使其隔一个数据报文发送一次回馈信息，设置一个标志位
	private static boolean isEcho = false;
	
	public static void main(String[] args) {
		try {
			DatagramSocket socket = new DatagramSocket(8099);
			DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);
			while(true) {
				try {
					socket.receive(packet);
					System.out.println("Handling client at " + packet.getAddress().getHostAddress() + " on port " + packet.getPort());
					System.out.println("UDPEchoServer receive data " + new String(packet.getData()));
					
//					socket.send(packet);	// 发送收到的内容
//					packet.setLength(ECHOMAX);
					
					// 修改程序，使其隔一个数据报文发送一次回馈信息
					if(isEcho) {
						socket.send(packet);	// 发送收到的内容
						packet.setLength(ECHOMAX);
						isEcho = false;
					}
					
					isEcho = true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

}