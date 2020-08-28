package com.java.netty.chapter4;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyNioServer {
	public void server(int port) throws Exception {
		final ByteBuf buf = 
				Unpooled.unreleasableBuffer(Unpooled.
						copiedBuffer("Hi!\r\n", Charset.forName("UTF-8")));
	NioEventLoopGroup group  = new NioEventLoopGroup();
	
	ServerBootstrap b = new ServerBootstrap();
	b.group(group)
	 .channel(NioServerSocketChannel.class)
	 .localAddress(new InetSocketAddress(7))
	 .childHandler(new ChannelInitializer<SocketChannel>() {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			// TODO Auto-generated method stub
			ch.pipeline().addLast(
					new ChannelInboundHandlerAdapter() {
				@Override
				public void channelActive(ChannelHandlerContext ctx) throws Exception {
					// TODO Auto-generated method stub
					
					ctx.writeAndFlush(buf.duplicate()).addListener(ChannelFutureListener.CLOSE
							);
				}
			});
			
		}
	});
	ChannelFuture f = b.bind().sync();
	f.channel().closeFuture().sync();
	}
}
