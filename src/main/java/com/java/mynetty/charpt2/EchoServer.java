package com.java.mynetty.charpt2;

import java.net.InetSocketAddress;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoServer {
	public static int port = 8000;

	public static void main(String[] args) {
		final EchoServerHandler echoServerHandler = new EchoServerHandler();
		NioEventLoopGroup group = new NioEventLoopGroup();
		try {	
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap
		.localAddress(new InetSocketAddress(port))
		.group(group)
		.channel(NioServerSocketChannel.class)
		.childHandler(new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel ch) throws Exception {
				// TODO Auto-generated method stub
				ch.pipeline().addLast(echoServerHandler);
			}
			});
		
		ChannelFuture f = bootstrap.bind().sync();
		ScheduledFuture<?> future = f.channel().eventLoop().schedule(
				new Runnable() {
					public void run() {
						// TODO Auto-generated method stub
						System.out.println("Hello World!");
					}
				},60,TimeUnit.SECONDS
				);	
		System.out.println(EchoServer.class.getName() + 
				" started and listening for connections on " + f.channel());
		f.channel().closeFuture().sync();
		
		}catch (Exception e) {
			// TODO: handle exception
		}
			

			
	}
}
