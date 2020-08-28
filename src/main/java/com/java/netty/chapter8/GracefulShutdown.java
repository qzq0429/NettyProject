package com.java.netty.chapter8;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.oio.OioSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;

public class GracefulShutdown {
	public static void main(String[] args) {
		new GracefulShutdown().bootstrap1();
	}
	public void bootstrap() {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group)
			.channel(NioSocketChannel.class)
			.handler(new SimpleChannelInboundHandler<ByteBuf>() {
				@Override
				public void channelActive(ChannelHandlerContext ctx) throws Exception {
					// TODO Auto-generated method stub
					ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!",CharsetUtil.UTF_8));
				}
				@Override
				protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
					// TODO Auto-generated method stub
					System.out.println("Received data");
				}
			});
		bootstrap.connect(new InetSocketAddress("127.0.0.1",7));
		
		Future<?> future = group.shutdownGracefully();
		future.syncUninterruptibly();
	}
	
	  public void bootstrap1() {
	        EventLoopGroup group = new NioEventLoopGroup();
	        //创建一个新的 Bootstrap 类的实例，以创建新的客户端Channel
	        Bootstrap bootstrap = new Bootstrap();
	        //指定一个适用于 NIO 的 EventLoopGroup 实现
	        bootstrap.group(group)
	            //指定一个适用于 OIO 的 Channel 实现类
	            .channel(OioSocketChannel.class)
	            //设置一个用于处理 Channel的 I/O 事件和数据的 ChannelInboundHandler
	                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
	                @Override
	                protected void channelRead0(
	                    ChannelHandlerContext channelHandlerContext,
	                    ByteBuf byteBuf) throws Exception {
	                    System.out.println("Received data");
	                }
	             });
	        //尝试连接到远程节点
	        ChannelFuture future = bootstrap.connect(
	                new InetSocketAddress("www.manning.com", 80));
	        future.syncUninterruptibly();
	    }
}
