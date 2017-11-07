package com.dragon.basic.java.net.socket.udp.echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * UDP客户端（因UDP协议不是面向连接的，发送的后果可能会数据报文丢失或者乱序）
 * 1、向服务器端发送回馈字符串
 * 2、在receive()方法上最多阻塞等待3秒钟，在超时前若没有收到响应，则重发请求（最多重发5次）
 * 3、终止客户端
 *
 */
public class UDPEchoClientTimeout {
	
	private static final int TIMEOUT = 3000;	// 超时时间
	private static final int MAXTRIES = 5;	// 最多重发次数
	
	public static void main(String[] args) {
		try {
			// 此类表示用来发送和接收数据报包的套接字。
			DatagramSocket datagramSocket = new DatagramSocket();
			datagramSocket.setSoTimeout(TIMEOUT);	// Maximum receive blocking time(milliseconds)[设置receive()方法最大阻塞时间]
			
//			byte[] bytesToSend = "udp packet message is yumin123.teststststststststststststsyuminyumintststststststststststststststststsyuminyumintstststststststststststststststststsyuminyumintststststststststststststststststststststststststststststststststststststststyuminyuminstststststststststststststststsyuminyumintstststst..".getBytes();
			byte[] bytesToSend = "udp packet 2".getBytes();
			InetAddress serverAddress = InetAddress.getLocalHost();
			// DatagramPacket: 此类表示数据报包。
			// 发送的数据包
			DatagramPacket sendPacket = new DatagramPacket(bytesToSend, bytesToSend.length, serverAddress, 8099);
			
			// 接收的数据包
			DatagramPacket receivePacket = new DatagramPacket(new byte[bytesToSend.length], bytesToSend.length);
			
			int tries = 0;	// 报文可能丢失，若丢失则重发
			boolean receiveResponse = false;
			
			do {
				try {
					// 发送报文
					datagramSocket.send(sendPacket);
					
					// 接收报文
					datagramSocket.receive(receivePacket);
					if(!receivePacket.getAddress().equals(serverAddress)) {	// Check source
						throw new IOException("接收到未知数据包");
					}
					receiveResponse = true;
				} catch (IOException e) {
					tries += 1;
					System.out.println("Time out, " + (MAXTRIES - tries) + " more tries...");
				}
			} while ((!receiveResponse) && (tries < MAXTRIES));  
			
			if(receiveResponse) {
				System.out.println("Received: " + new String(receivePacket.getData()));
			} else {
				System.out.println("No response, Giving up...");
			}
			
			// 关闭客户端Socket
			datagramSocket.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}