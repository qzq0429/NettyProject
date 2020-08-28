package com.java.netty.chapter8;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import io.netty.bootstrap.Bootstrap;
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
import io.netty.util.CharsetUtil;

public class BootstrapSharingEventLoopGroup {

	public void bootstrap() {
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(new NioEventLoopGroup(),new NioEventLoopGroup())
			.channel(NioServerSocketChannel.class)
			.childHandler(
							new SimpleChannelInboundHandler<ByteBuf>() {
								ChannelFuture connectFuture;
								public void channelActive(ChannelHandlerContext ctx) {
									Bootstrap bootstrap = new Bootstrap();
									bootstrap.channel(NioSocketChannel.class).handler(
											new SimpleChannelInboundHandler<ByteBuf>() {
												@Override
												public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
													// TODO Auto-generated method stub
													System.out.println("register data");
												}
												@Override
												public void channelActive(ChannelHandlerContext ctx) {
													// TODO Auto-generated method stub
													ctx.writeAndFlush(Unpooled.copiedBuffer("this translate Ok",CharsetUtil.UTF_8));
												}
												@Override
												protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg)
														throws Exception {
													// TODO Auto-generated method stub
													System.out.println(msg.toString(CharsetUtil.UTF_8));
													System.out.println("Received data");
												}
											}
											);
									bootstrap.group(ctx.channel().eventLoop());
									connectFuture = bootstrap.connect(
											new InetSocketAddress("127.0.0.1", 7)
											);
								}
								@Override
								protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
									// TODO Auto-generated method stub
									System.out.println("connectFuture");
									if(connectFuture.isDone()) {
									System.out.println("Received data");
									}
								}
							});
		ChannelFuture future = bootstrap.bind(new InetSocketAddress(8));
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
		new BootstrapSharingEventLoopGroup().bootstrap();
	}
}
