package com.java.mynetty.charpter10;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class IntegerToString<Integer> extends MessageToMessageDecoder<Integer> {

	@Override
	protected void decode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		out.add(String.valueOf(msg));
	}

}
