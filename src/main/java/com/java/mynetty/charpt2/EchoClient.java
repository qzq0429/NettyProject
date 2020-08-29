package com.java.mynetty.charpt2;

import java.net.InetSocketAddress;

import com.java.netty.chapter8.BootstrapClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {
	public static int port = 8001;
	public static String address = "127.0.0.1";

	public static void main(String[] args) throws InterruptedException {

		NioEventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap client = new Bootstrap();
			client.group(group).localAddress(port).channel(NioSocketChannel.class)
					.remoteAddress(new InetSocketAddress(address, 8000)).handler(new ChannelInitializer<Channel>() {
						protected void initChannel(Channel ch) throws Exception {
							ch.pipeline().addLast(new EchoClientHandler());
						};
					});

			ChannelFuture f = client.connect().sync();
			f.channel().closeFuture().sync();
		} finally {
			// TODO: handle exception
			// 如果group没有顺利关闭则会一直阻塞
			group.shutdownGracefully().sync();
		}
	}
}
