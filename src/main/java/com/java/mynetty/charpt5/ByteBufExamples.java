package com.java.mynetty.charpt5;

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
	
	public static void byteBufRelativeAccess() {
		ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE;
		for(int i=0;i<buffer.capacity();i++) {
			byte b = buffer.getByte(i);
			System.out.println(b);
		}
	}
	
	public static void readAllData() {
		ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE;
		//byteBufProcessor();
		while(buffer.isReadable()) {
			System.out.println(buffer.readByte());
		}
	}
	
	public static void write() {
		ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE;
		buffer.writeByte((byte) '\r');
		while(buffer.writableBytes()>4) {
			buffer.writeInt(random.nextInt());
		}
	}
	
	public static void byteBufProcessor() {
		ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE;
		int index = buffer.forEachByte(ByteProcessor.FIND_CR);
		System.out.println("index: "+index);
	}
	
	public static void byteBufSlice() {
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks",utf8);
		ByteBuf sliced = buf.slice(0, 15);
		System.out.println(sliced.toString(utf8));
		buf.setByte(0, (byte)'J');
		assert buf.getByte(0) == sliced.getByte(0);
		System.out.println(buf.toString(utf8));
	}
	
	public static void byteBufCopy() {
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!",utf8);
		ByteBuf cpy = buf.copy();
		cpy.setByte(0, (byte)'J');
		System.out.println(cpy.toString(utf8));
		System.out.println(buf.toString(utf8));
	}
	
	
	public static void byteBufSetGet() {
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!",utf8);
		int readerIndex = buf.readerIndex();
		int writerIndex = buf.writerIndex();
		System.out.println(buf.toString(utf8) +  "readerIndex: "+buf.readerIndex()+" , "+"writerIndex: "+buf.writerIndex());
		buf.setByte(0, (byte)'B');
		System.out.println(buf.toString(utf8)+ "readerIndex: "+buf.readerIndex()+" , "+"writerIndex: "+buf.writerIndex());
	}
	
	public static void referenceCounting() {
		Channel channel = CHANNEL_FROM_SOMEWHERE;
		ByteBufAllocator allocator = channel.alloc();
		ByteBuf buffer = allocator.directBuffer();
		if(buffer.refCnt() == 2) System.out.println(false);;
	}
	
	public static void byteBufWriteRead() {
		Charset utf8 = Charset.forName("UTF-8");
		ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!",utf8);
		int readerIndex = buf.readerIndex();
		int writerIndex = buf.writerIndex();
		System.out.println(buf.toString(utf8) +  "readerIndex: "+buf.readerIndex()+" , "+"writerIndex: "+buf.writerIndex());
		buf.writeByte((byte)'?');
		System.out.println(buf.toString(utf8)+ 
				"readerIndex: "+buf.readerIndex()+" , "
				+"writerIndex: "+buf.writerIndex()+" , "
				+"cap: "+buf.capacity()+" , "
				+"isReadable: "+buf.isReadable()+" , "
				+"isWritable: "+buf.isWritable()+" , "
				+"readableBytes: "+buf.readableBytes()+" , "
				+"writableBytes: "+buf.writableBytes()+" , "
				+"capacity: "+buf.capacity()+" , "
				+"maxCapacity: "+buf.maxCapacity()+" , "
				+"hasArray: "+buf.hasArray()+" , "
				+"array: "+buf.array()+" , "
				);
		
	}
	public static void main(String[] args) {
//		byteBufRelativeAccess();
//		write();
//		readAllData();
//		byteBufSlice();
//		 byteBufCopy();
//		byteBufSetGet();
//		byteBufWriteRead();
		Channel channel = CHANNEL_FROM_SOMEWHERE;
		ByteBufAllocator allocator = channel.alloc();
		ByteBuf buffer = allocator.directBuffer();
		
		referenceCounting();
	}
}
