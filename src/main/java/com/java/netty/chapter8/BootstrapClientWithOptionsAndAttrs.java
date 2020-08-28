package com.java.netty.chapter8;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

public class BootstrapClientWithOptionsAndAttrs {
	
	public void bootstrap() {
	//创建一个AttributeKey以标识该属性
	final AttributeKey<Integer> id = AttributeKey.newInstance("ID");
	//创建引导
	Bootstrap bootstrap = new Bootstrap();
	//设置 EventLoopGroup,用来提供处理Channel事件的EventLoop
	bootstrap.group(new NioEventLoopGroup())
				.channel(NioSocketChannel.class)
				.handler(new SimpleChannelInboundHandler<ByteBuf>() {
					
					@Override
					public void channelActive(ChannelHandlerContext ctx) throws Exception {
						// TODO Auto-generated method stub
						ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!",CharsetUtil.UTF_8));
					}
					
					@Override
					public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
						// TODO Auto-generated method stub
						Attribute<Integer> a = ctx.channel().attr(id);
						a.set(1);
						a = ctx.channel().attr(id);
						System.out.println(a.get());
						System.out.println("register");
					}
					@Override
					protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
						// TODO Auto-generated method stub
						System.out.println(msg.toString(CharsetUtil.UTF_8));
					}
				});
	bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
		.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
	bootstrap.attr(id, 123456);
	ChannelFuture future = bootstrap.connect(new InetSocketAddress("127.0.0.1",8));
	future.syncUninterruptibly();
	}
	public static void main(String[] args) {
		new BootstrapClientWithOptionsAndAttrs().bootstrap();
	}
}
