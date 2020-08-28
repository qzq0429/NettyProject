package com.java.netty.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpAggregatorInitializer extends ChannelInitializer<Channel> {
	private final boolean isClient;
	
	public HttpAggregatorInitializer(boolean isClient) {
		// TODO Auto-generated constructor stub
		this.isClient = isClient;
	}
	
	@Override
	protected void initChannel(Channel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline pipeline = ch.pipeline();
		if(isClient) {
			pipeline.addLast("codec",new HttpClientCodec());
		} else {
			pipeline.addLast("codec",new HttpServerCodec());
		}
		
		pipeline.addLast("aggregator", new HttpObjectAggregator(512 * 1024));
	}
	
	private void initChannel() {
		// TODO Auto-generated method stub

	}

}
