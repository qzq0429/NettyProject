package com.java.mynetty.charpter10;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public class IntegerToStringEncoder extends MessageToMessageEncoder<Integer> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		out.add(String.valueOf(msg));
	}

}
