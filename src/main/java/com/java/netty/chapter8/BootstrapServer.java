package com.java.netty.chapter8;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

public class BootstrapServer {
	public void bootstrap() {
		NioEventLoopGroup group = new NioEventLoopGroup();
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(group)
		.channel(NioServerSocketChannel.class)
		.childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
			
			@Override
			protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("Recevied value");
				
				while(msg.isReadable()) {
					msg.readByte();
				}
				ctx.writeAndFlush(Unpooled.copiedBuffer("this translate Ok",CharsetUtil.UTF_8));
			}
		});
		
		ChannelFuture future = bootstrap.bind(new InetSocketAddress(7));
		future.addListener(new ChannelFutureListener() {
			
			public void operationComplete(ChannelFuture future) throws Exception {
				// TODO Auto-generated method stub
			if(future.isSuccess()) {
				System.out.println("Server bound");
			}else {
				System.err.println("Bind attempt failed");
				future.cause().printStackTrace();
			}
			}
		});
	}
	public static void main(String[] args) {
		new BootstrapServer().bootstrap();
	}
}
