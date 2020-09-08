package com.java.mynetty.charpter11.ssl;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLEngine;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class ServerSSLChannelInitializer extends ChannelInitializer<Channel> {

	@Override
	protected void initChannel(Channel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new IdleStateHandler(5, 0, 0,TimeUnit.SECONDS));
		pipeline.addLast(new ChannelInboundHandlerAdapter() {

			@Override
			public void channelActive(ChannelHandlerContext ctx) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("SSL连接成功");
			}
			
		});
		SSLEngine engine = ContextSSLClientFactory.getSslContext().createSSLEngine();
        engine.setUseClientMode(false); //设置为服务端模式
        engine.setNeedClientAuth(true); //需要验证客户端
        pipeline.addFirst("ssl", new SslHandler(engine));   //这个handler需要加到最前面
	}

}
