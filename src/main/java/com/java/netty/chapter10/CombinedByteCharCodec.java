package com.java.netty.chapter10;

import io.netty.channel.CombinedChannelDuplexHandler;

public class CombinedByteCharCodec extends CombinedChannelDuplexHandler<ByteToCharDecoder, CharToByteEncoder> {
	public CombinedByteCharCodec() {
		// TODO Auto-generated constructor stub
	}
}
