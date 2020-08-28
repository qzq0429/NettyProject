package com.java.netty.chapter11;

import java.io.File;
import java.io.FileInputStream;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.channel.socket.nio.NioSocketChannel;

public class FileRegionWriteHandler extends ChannelInboundHandlerAdapter {
	private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();
	private static final File FILE_FROM_SOMEWHERE = new File("");
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		File file = FILE_FROM_SOMEWHERE;
		Channel channel = CHANNEL_FROM_SOMEWHERE;
		
		FileInputStream in = new FileInputStream(file);
		FileRegion region = new DefaultFileRegion(
				in.getChannel(),0,file.length());
		channel.writeAndFlush(region).addListener(
				new ChannelFutureListener() {
					
					public void operationComplete(ChannelFuture future) throws Exception {
						// TODO Auto-generated method stub
						if(!future.isSuccess()) {
							Throwable cause = future.cause();
						}
					}
				}
				);
		
	}
}
