package com.java.netty.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ShortToByteEncoder extends MessageToByteEncoder<Short> {
@Override
protected void encode(ChannelHandlerContext ctx, Short msg, ByteBuf out) throws Exception {
	// TODO Auto-generated method stub
	out.writeShort(msg);
}
}
