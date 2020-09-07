package com.java.mynetty.charpter8;

import com.java.netty.chapter8.BootstrapServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public class boostrapwithoption {
	public static void main(String[] args) throws InterruptedException {
		ServerBootstrap server = new ServerBootstrap();
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup work = new NioEventLoopGroup();
		server.group(boss, work)
		.channel(NioServerSocketChannel.class)
		.localAddress(8000)
		.childHandler(new ChannelInitializer<Channel>() {
		protected void initChannel(Channel ch) throws Exception {
			ch.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>() {

				
				@Override
				public void channelActive(ChannelHandlerContext ctx) throws Exception {
					// TODO Auto-generated method stub
					Attribute k = ctx.channel().attr(AttributeKey.valueOf("ID"));
					System.out.println(String.valueOf(k.get()));
				}

				@Override
				protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
					// TODO Auto-generated method stub

				}
			});
		};
		});
		
		server.bind().sync();
	}
}
