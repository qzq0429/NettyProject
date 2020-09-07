package com.java.mynetty.charpter10;

public class CombinedChannelDuplexHandler extends io.netty.channel.CombinedChannelDuplexHandler<ByteToCharDecoder, CharToByteEncoder> {
public CombinedChannelDuplexHandler() {
	// TODO Auto-generated constructor stub
	super(new ByteToCharDecoder(), new CharToByteEncoder());
}
}
