package com.java.netty.chapter8;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public class BootstrapClient {
	public static void main(String args[]) {
		BootstrapClient client = new BootstrapClient();
		client.bootstrap();
	}
	
	public void bootstrap(){
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group)
					.channel(NioSocketChannel.class)
					.remoteAddress(new InetSocketAddress("www.manning.com",80))
					.handler(new SimpleChannelInboundHandler<ByteBuf>() {

						@Override
						protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
							// TODO Auto-generated method stub
							System.out.println("Received data");
							AttributeKey<Integer> id = AttributeKey.newInstance("ID");
							Integer idValue = ctx.channel().attr(id).get();
							System.out.println(idValue);
						}

					});
		ChannelFuture future = bootstrap.connect();
		future.addListener(new ChannelFutureListener() {

			public void operationComplete(ChannelFuture future) throws Exception {
				// TODO Auto-generated method stub
				if(future.isSuccess()) {
					System.out.println("Connection established");
				}else {
					System.err.println("Connection attempt failed");
					future.cause().printStackTrace();
				}
			}
			
		});
	}
}
