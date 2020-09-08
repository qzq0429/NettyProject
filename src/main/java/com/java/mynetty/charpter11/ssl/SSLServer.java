package com.java.mynetty.charpter11.ssl;

import java.net.InetSocketAddress;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.java.mynetty.charpt2.EchoServer;
import com.java.mynetty.charpt2.EchoServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class SSLServer {
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
		.childHandler(new ServerSSLChannelInitializer());
		
		ChannelFuture f = bootstrap.bind().sync();
		f.channel().closeFuture().sync();
		
		}catch (Exception e) {
			// TODO: handle exception
		}
			

			
	}
}
