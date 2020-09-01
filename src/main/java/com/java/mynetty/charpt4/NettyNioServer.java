package com.java.mynetty.charpt4;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyNioServer {
	public void serve(int port) throws InterruptedException {
		ServerBootstrap bootstrap = new ServerBootstrap();
		final NioEventLoopGroup boss = new NioEventLoopGroup();
		final NioEventLoopGroup work = new NioEventLoopGroup();
		final ByteBuf buf = 
				Unpooled.unreleasableBuffer(Unpooled.
						copiedBuffer("Hi!\r\n", Charset.forName("UTF-8")));
		bootstrap
				.group(boss, work)
				.channel(NioServerSocketChannel.class)
				.localAddress(new InetSocketAddress(7))
				.childHandler(new ChannelInitializer() {
					@Override
					protected void initChannel(Channel ch) throws Exception {
						// TODO Auto-generated method stub
						ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
							@Override
							public void channelActive(ChannelHandlerContext ctx) throws Exception {
								// TODO Auto-generated method stub
								ctx.writeAndFlush(Unpooled.unreleasableBuffer(buf));
							}
						});
					}
				})
				;
		ChannelFuture f = bootstrap.bind().sync();
		f.channel().closeFuture().sync();
	}
}
