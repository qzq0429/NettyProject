package com.java.netty.chapter4;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Random;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ByteProcessor;

public class ByteBufExamples {
	private final static Random random = new Random();
	private static final ByteBuf BYTE_BUF_FROM_SOMEWHERE = Unpooled.buffer(1000);
	private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();
	 private static final ChannelHandlerContext CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE=null;
	public static void heapBuffer() {
		ByteBuf heapBuf = BYTE_BUF_FROM_SOMEWHERE;
		if (heapBuf.hasArray()) {
			byte[] array = heapBuf.array();
			int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
			int length = heapBuf.readableBytes();
			handleArray(array, offset, length);
		}

	}

	public static void handleArray(byte[] array, int offset, int len) {

	}

	public static void directBuffer() {
		ByteBuf directBuf = BYTE_BUF_FROM_SOMEWHERE;
		if (!directBuf.hasArray()) {
			int length = directBuf.readableBytes();

			byte[] array = new byte[length];
			directBuf.getBytes(directBuf.readerIndex(), array);
			handleArray(array, 0, length);
		}
	}

	public static void byteBufferComposite(ByteBuffer header, ByteBuffer body) {
		ByteBuffer[] message = new ByteBuffer[] { header, body };
		ByteBuffer message2 = ByteBuffer.allocate(header.remaining() + body.remaining());
		message2.put(header);
		message2.put(body);
		message2.flip();
	}
	
	public static void byteBufComposite() {
		CompositeByteBuf messageBuf = Unpooled.compositeBuffer();
		ByteBuf headerBuf = BYTE_BUF_FROM_SOMEWHERE;
		ByteBuf bodyBuf = BYTE_BUF_FROM_SOMEWHERE;
		
		messageBuf.addComponents(headerBuf,bodyBuf);
		
		messageBuf.removeComponent(0);
		
		for(ByteBuf buf : messageBuf) {
			System.out.println(buf.toString());
		}

	}
	
	public static void byteBufCompositeArray() {
		CompositeByteBuf compBuf = Unpooled.compositeBuffer();
		int length = compBuf.readableBytes();
		byte[] array = new byte[length];
		compBuf.getBytes(compBuf.readerIndex(), array);
		handleArray(array,0,array.length);
	}
	
	public static void byteBufRelativeAccess() {
		ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE;
		for(int i = 0; i <buffer.capacity();i++) {
			byte b = buffer.getByte(i);
			System.out.println(b);
		}
	}
	
	public static void readAllData() {
		ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE;
		while(buffer.isReadable()) {
			System.out.println(buffer.readByte());
		}
	}
	
	public static void write() {
		ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE;
		while(buffer.writableBytes() >= 4 ) {
			buffer.writeInt(random.nextInt());
		}
	}
	
	public static void byteBufProcessor() {
		ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE;
		int index = buffer.forEachByte(ByteProcessor.FIND_CR);// @formatter:off
		// @formatter:on
	}
	
	public static void byteBufSlice() {
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks",utf8);
		ByteBuf sliced = buf.slice(0,15);
		System.out.println(sliced.toString(utf8));
		buf.setByte(0,(byte)'J');
		assert buf.getByte(0) == sliced.getByte(0);
	}
	
	public static void byteBufCopy() {
		Charset utf8 = Charset.forName("UTF-8");
		
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!",utf8);
		ByteBuf copy = buf.copy(0, 15);
		System.out.println(copy.toString(utf8));
		buf.setByte(0, (byte)'J');
		assert buf.getByte(0) != copy.getByte(0);
	}
	
	public static void byteBufSetGet() {
		
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!",utf8);
		System.out.println((char)buf.getByte(0));
		int readerIndex = buf.readerIndex();
		int writerIndex = buf.writerIndex();
		buf.setByte(0,(byte)'B');
		System.out.println((char)buf.getByte(0));
		assert readerIndex == buf.readerIndex();
		assert writerIndex == buf.writerIndex();
	}
	
	public static void byteBufWriteRead() {
		Charset utf8 = Charset.forName("UTF-8"); 
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!",utf8);
		System.out.println((char)buf.readByte());
		int readerIndex = buf.readerIndex();
		int writerIndex = buf.writerIndex();
		
		buf.writeByte((byte)'?');
		
		assert readerIndex == buf.readerIndex();
		assert writerIndex != buf.writerIndex();
	}
	
	public static void main(String[] args) {
		 byteBufSetGet();
		 byteBufWriteRead();
	}
	
	public static void obtainingByteBufAllocatorRefence() {
		Channel channel = CHANNEL_FROM_SOMEWHERE;
		ByteBufAllocator allocator = channel.alloc();
		ChannelHandlerContext ctx = CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE;
		ctx.alloc();
	}
	
	public static void referenceCounting() {
		Channel channel = CHANNEL_FROM_SOMEWHERE;
		ByteBufAllocator allocator = channel.alloc();
		ByteBuf buffer = allocator.directBuffer();
		assert buffer.refCnt() == 1;
	}
	
	public static void releaseReferenceCountedObject() {
		ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE;
		boolean released = buffer.release();

	}
}
