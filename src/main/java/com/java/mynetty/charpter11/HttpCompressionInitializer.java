package com.java.mynetty.charpter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentCompressor;

public class HttpCompressionInitializer extends ChannelInitializer<Channel> {

	private final boolean isClient;
	
	public HttpCompressionInitializer(boolean isClient) {
		this.isClient = isClient;
	}
	
	@Override
	protected void initChannel(Channel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline  pipeline = ch.pipeline();
		if (isClient) {
			pipeline.addLast("codec", new HttpClientCodec());
			pipeline.addLast("decompressor",new HttpContentCompressor());
		} else {
			pipeline.addLast("codec", new HttpClientCodec());
			pipeline.addLast("compressor",new HttpContentCompressor());
		}
	}
}
