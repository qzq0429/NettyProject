package com.java.netty.chapter8;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

public class BootstrapWithInitializer {
	
	public void bootstrap() {
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(new NioEventLoopGroup(),new NioEventLoopGroup())
		.channel(NioServerSocketChannel.class)
		.childHandler(new ChannelInitializerImpl());
		ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080));
	}
	final class ChannelInitializerImpl extends ChannelInitializer<Channel> {

		@Override
		protected void initChannel(Channel ch) throws Exception {
			// TODO Auto-generated method stub
			ChannelPipeline pipeline = ch.pipeline();
			pipeline.addLast(new HttpClientCodec());
			pipeline.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
		}
		
	}
}
