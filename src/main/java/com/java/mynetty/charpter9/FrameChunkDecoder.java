package com.java.mynetty.charpter9;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

public class FrameChunkDecoder extends ByteToMessageDecoder {

	private final int maxFrameSize;
	
	public FrameChunkDecoder(int maxFrameSize) {
		// TODO Auto-generated constructor stub
		this.maxFrameSize = maxFrameSize;
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		int readableBytes = in.readableBytes();
		if(readableBytes > maxFrameSize) {
			in.clear();
			throw new TooLongFrameException();
		}
		ByteBuf buf = in.readBytes(readableBytes);
		out.add(buf);
	}

}
