package com.java.mynetty.charpt4;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Deque;
import java.util.LinkedList;

import org.w3c.dom.Node;

public class PlainOIoServer {
	public void serve(int port) throws IOException {
		final ServerSocket socket = new ServerSocket(port);
		for (;;) {
			final Socket clientSocket = socket.accept();
			System.out.println("Accepted connection from " + clientSocket);
			new Thread(new Runnable() {
				public void run() {
					OutputStream out;
					try {
						out = clientSocket.getOutputStream();
						out.write("Hi!\r\n".getBytes(Charset.forName("UTF-8")));
						out.flush();
						clientSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally {
						try {
							clientSocket.close();
						}catch (IOException ex) {
							
						}
					}

				}
			});

		}

	}
	
}
