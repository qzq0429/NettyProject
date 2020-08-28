package com.java.netty.chapter11;

import java.io.Serializable;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

public class MarshallingInitializer extends ChannelInitializer<Channel> {
	private final MarshallerProvider marshallerProvider;
	private final UnmarshallerProvider unmarshallerProvider;
	
	public MarshallingInitializer(UnmarshallerProvider unmarshallerProvider,
			MarshallerProvider marshallerProvider) {
		// TODO Auto-generated constructor stub
		this.marshallerProvider = marshallerProvider;
		this.unmarshallerProvider = unmarshallerProvider;
	}
	
	@Override
	protected void initChannel(Channel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new MarshallingDecoder(unmarshallerProvider));
		pipeline.addLast(new MarshallingEncoder(marshallerProvider));
		pipeline.addLast(new ObjectHandler());
	}
	
	public static final class ObjectHandler
		extends SimpleChannelInboundHandler<Serializable>{
		protected void channelRead0(io.netty.channel.ChannelHandlerContext ctx, Serializable msg) throws Exception {
			
			
		};
	}
}
