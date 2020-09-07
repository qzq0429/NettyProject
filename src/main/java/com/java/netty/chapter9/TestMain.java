package com.java.netty.chapter9;

import com.java.mynetty.charpter9.AbsIntegerEncoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

public class TestMain {

	public static void main(String[] args) {
		//extracted();
		//extracted01();
		extracted02();
	}

	private static void extracted() {
		ByteBuf buf = Unpooled.buffer();
		for(int i = 0; i < 9; i++) {
			buf.writeByte(i);
		}
		ByteBuf input = buf.duplicate();
		EmbeddedChannel channel = new EmbeddedChannel(
				new FixedLengthFrameDecoder(3)
				);
		channel.writeInbound(input.retain());
		channel.finish();
		ByteBuf read = (ByteBuf) channel.readInbound();
		System.out.println(buf.readSlice(3).equals(read));
		
		read = (ByteBuf) channel.readInbound();
		System.out.println(buf.readSlice(3).equals(read));
		
		read = (ByteBuf) channel.readInbound();
		System.out.println(buf.readSlice(3).equals(read));
		
		System.out.println(channel.readInbound()==null);
		buf.release();
	}
	
	private static void extracted01() {
		ByteBuf buf = Unpooled.buffer();
		for(int i = 0; i < 9; i++) {
			buf.writeByte(i);
		}
		ByteBuf input = buf.duplicate();
		
		EmbeddedChannel channel  = new EmbeddedChannel(
				new FixedLengthFrameDecoder(3)
				);
		System.out.println(channel.writeInbound(input.readBytes(2)));
		System.out.println(channel.writeInbound(input.readBytes(7)));
		System.out.println(channel.finish());
		
		ByteBuf read = (ByteBuf) channel.readInbound();
		ByteBuf b = buf.readSlice(3);
		System.out.println(b.equals(read));
		read.release();
		
		read = (ByteBuf) channel.readInbound();
		System.out.println(buf.readSlice(3).equals(read));
		read.release();
		
		read = (ByteBuf) channel.readInbound();
		System.out.println(buf.readSlice(3).equals(read));
		read.release();
	}
	
	private static void extracted02() {
		ByteBuf buf = Unpooled.buffer();
		for (int i = 1; i < 10; i++) {
			buf.writeInt(i*-1);
		}
		
		EmbeddedChannel channel = new EmbeddedChannel(
				new AbsIntegerEncoder()
				);
		channel.writeOutbound(buf);
		channel.finish();
		
		for (int i = 1; i < 10; i++) {
			System.out.println(i == (Integer)channel.readOutbound());
		}
		System.out.println(null == channel.readOutbound());
	}
}
