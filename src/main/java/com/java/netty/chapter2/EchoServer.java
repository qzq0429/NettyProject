package com.java.netty.chapter2;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
	private final int port;
	
	public EchoServer(int port) {
		this.port = port;
	}
	
	public static void main(String[] args) throws InterruptedException {
		int port  = 7;
		new EchoServer(port).start();
	}
	
	public void start() throws InterruptedException {
		//EchoServerHandler继承了ChannelInboundHandlerAdapter类
		final EchoServerHandler serverHandler = new EchoServerHandler();
		
		EventLoopGroup group = new NioEventLoopGroup();
		try {	
		ServerBootstrap  b = new ServerBootstrap();
		b.group(group)
			.channel(NioServerSocketChannel.class)
			.localAddress(new InetSocketAddress(port))
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					//在line的末尾添加handler()
					ch.pipeline().addLast(new EchoServerHandler());
				}
			
			});
		ChannelFuture f = b.bind().sync();
		System.out.println(EchoServer.class.getName() + 
				" started and listening for connections on " + f.channel());

		f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			group.shutdownGracefully().sync();
		}
	}
}
