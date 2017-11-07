package com.dragon.basic.java.net.socket.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * TCP服务器端
 * 1、创建ServerSocket实例并指定本地端口。此套接字的功能是监听该指定端口收到的连接
 * 2、重复执行：
 * 	a、调用ServerSocket的accept()方法以获取下一个客户端连接。基于新建立的客户端
 * 	连接，创建一个Socket实例，并由accept()方法返回。
 * 	b、使用所返回的Socket实例的InputStream和OutputStream与客户端进行通信。
 *  c、通信完成后，使用Socket类的close()方法关闭该客户端套接字连接。
 *
 */
public class TCPEchoServer {
	
	private static final int BUFSIZE = 32;	// Size of receive buffer
	
	public static void main(String[] args) {
		try {
			// Create a server socket to accept client connection requests
			// 创建ServerSocket用来接收（监听）Socket客户端的连接请求
			ServerSocket serverSocket = new ServerSocket(8088);
			
			// Size of received message
			byte[] receiveBuf = new byte[BUFSIZE];	// Receive buffer
			
			while(true) {	// Run forever, accepting and serving connections
				// accept()方法，将阻塞等待，直到有向ServerSocket实例指定端口的新的连接请求到来。
				Socket clientSocket = serverSocket.accept();	// Get client connection
				SocketAddress clientAddress = clientSocket.getRemoteSocketAddress();
				
				System.out.println("Handling client at " + clientAddress);
				
				InputStream in = clientSocket.getInputStream();
				OutputStream out = clientSocket.getOutputStream();
				
				// Receive until client closes connection, indicated by -1 return
				while(in.read(receiveBuf) != -1) {
					System.out.println("Server receive message: " + new String(receiveBuf));
					out.write(("server echo Message: " + new String(receiveBuf)).getBytes());
				} 
				clientSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}