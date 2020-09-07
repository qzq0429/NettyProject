package com.java.mynetty.charpter10;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

public class SafeByteToMessageDecoder extends ByteToMessageDecoder {
	private static final int MAX_FRAME_SIZE = 1024;
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		int readable = in.readableBytes();
		if(readable > MAX_FRAME_SIZE) {
			in.skipBytes(readable);
			throw new TooLongFrameException("Frame too big");
		}
	}

}
