package com.java.netty.chapter10;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class IntegerToStringDecoder extends MessageToMessageDecoder<Integer> {

	@Override
	protected void decode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		out.add(String.valueOf(msg));
	}

}
