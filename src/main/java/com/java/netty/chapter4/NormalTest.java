package com.java.netty.chapter4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
public class NormalTest {

	   private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();
	    /**
	     * 代码清单 4-5 写出到 Channel
	     */
	    public static void writingToChannel() {
	        Channel channel = CHANNEL_FROM_SOMEWHERE; // Get the channel reference from somewhere
	        //创建持有要写数据的 ByteBuf
	        ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);
	        ChannelFuture cf = channel.writeAndFlush(buf);
	        //添加 ChannelFutureListener 以便在写操作完成后接收通知
	        cf.addListener(new ChannelFutureListener() {
	            public void operationComplete(ChannelFuture future) {
	                //写操作完成，并且没有错误发生
	                if (future.isSuccess()) {
	                    System.out.println("Write successful");
	                } else {
	                    //记录错误
	                    System.err.println("Write error");
	                    future.cause().printStackTrace();
	                }
	            }
	        });
	    }
	    
	    private final static Random random = new Random();
	    private static final ByteBuf BYTE_BUF_FROM_SOMEWHERE = Unpooled.buffer(1024);
	    /**
	     * 代码清单 5-1 支撑数组
	     */
	    public static void heapBuffer() {
	        ByteBuf heapBuf = Unpooled.copiedBuffer("adfasdf", Charset.forName("UTF-8")); //get reference form somewhere
	        //检查 ByteBuf 是否有一个支撑数组
	        if (heapBuf.hasArray()) {
	            //如果有，则获取对该数组的引用
	            byte[] array = heapBuf.array();
	            heapBuf.readByte();
	            System.out.println(heapBuf.arrayOffset());
	            //计算第一个字节的偏移量
	            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
	            System.out.println(offset);
	            //获得可读字节数
	            int length = heapBuf.readableBytes();
	            //使用数组、偏移量和长度作为参数调用你的方法
	        }
	    }

	    /**
	     * 代码清单 4-6 从多个线程使用同一个 Channel
	     */
	    public static void writingToChannelFromManyThreads() {
	        final Channel channel = CHANNEL_FROM_SOMEWHERE; // Get the channel reference from somewhere
	        //创建持有要写数据的ByteBuf
	        final ByteBuf buf = Unpooled.copiedBuffer("your data",
	                CharsetUtil.UTF_8);
	        //创建将数据写到Channel 的 Runnable
	        Runnable writer = new Runnable() {
	            public void run() {
	                channel.write(buf.duplicate());
	            }
	        };
	        //获取到线程池Executor 的引用
	        Executor executor = Executors.newCachedThreadPool();

	        //递交写任务给线程池以便在某个线程中执行
	        // write in one thread
	        executor.execute(writer);

	        //递交另一个写任务以便在另一个线程中执行
	        // write in another thread
	        executor.execute(writer);
	        //...
	    }
	    
	    public static void main(String[] args) {
	    	heapBuffer();
		}
}
