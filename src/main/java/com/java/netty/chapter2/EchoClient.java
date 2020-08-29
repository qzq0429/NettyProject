package com.java.netty.chapter2;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

public class EchoClient {
	private final String host;
	private final int port;
	
	public EchoClient(String host,int port) {
		this.host = host;
		this.port = port;
	}
	public static void main(String[] args) throws InterruptedException {
		new EchoClient("127.0.0.1", 7).start();;
	}
	public void start() throws InterruptedException {
		//初始化工作线程组
		EventLoopGroup group = new NioEventLoopGroup();
		try {
		//Bootstrap为引导类
		Bootstrap b = new Bootstrap();
        //设置引导类的线程工作组
		b.group(group) //
			.channel(NioSocketChannel.class) 		//设置Channel的类型
			.remoteAddress(new InetSocketAddress(host,port)) //设置连接的远程服务器的地址和端口
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					ch.pipeline().addLast(new EchoClientHandler());
				}
				
			});
		//如果未连接则一直阻塞
		ChannelFuture f = b.connect().sync();
		f.channel().writeAndFlush(Unpooled.copiedBuffer("haha",CharsetUtil.UTF_8));
		//未关闭则一致阻塞
		f.channel().closeFuture().sync();
		} finally {
			// TODO: handle exception
			//如果group没有顺利关闭则会一直阻塞
			group.shutdownGracefully().sync();
		}
	}
}
