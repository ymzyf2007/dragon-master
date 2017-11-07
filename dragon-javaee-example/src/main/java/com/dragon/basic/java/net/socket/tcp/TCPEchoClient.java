package com.dragon.basic.java.net.socket.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 典型的TCP客户端
 * 1、创建一个Socket实例：构造函数向指定的远程主机和端口建议一个TCP连接
 * 2、通过套接字的输入输出流进行通信：一个Socket连接实例包括一个InputStream和一个OutputStream
 * 3、使用Socket类的close()方法关闭连接
 *
 */
public class TCPEchoClient {
	
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("127.0.0.1", 8088);
			System.out.println("Connected to server...sending echo string");
			
			OutputStream out = socket.getOutputStream();
			byte[] data = new String("yumin send Message...").getBytes();
			// 发送消息到服务器
			out.write(data);
			
			// 接收从服务器端返回的报文
			InputStream in = socket.getInputStream();
			// 总共从服务器接收多少字节
			int totalBytesRcvd = 0;
			int bytesRcvd;
			
			while(totalBytesRcvd < data.length) {
				if((bytesRcvd = in.read(data, totalBytesRcvd, data.length - totalBytesRcvd)) == -1) {
					totalBytesRcvd += bytesRcvd;
				}
			}
			System.out.println("Received: " + new String(data));
			
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}