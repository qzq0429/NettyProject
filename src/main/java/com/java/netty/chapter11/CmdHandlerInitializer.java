package com.java.netty.chapter11;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LineBasedFrameDecoder;

public class CmdHandlerInitializer extends ChannelInitializer<Channel> {
	private static final byte SPACE = (byte)' ';
	@Override
	protected void initChannel(Channel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new CmdDecoder(64*1024));
		
	}
	
	public static final class Cmd {
		private final ByteBuf name;
		private final ByteBuf args;
		
		public Cmd(ByteBuf name,ByteBuf args) {
			this.name = name;
			this.args = args;
		}
		
        public ByteBuf name() {
            return name;
        }

        public ByteBuf args() {
            return args;
        }
	}
	
	public static final class CmdDecoder extends LineBasedFrameDecoder{
		
		CmdDecoder(int maxLength){
			super(maxLength);
		}
		
		@Override
		protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
			// TODO Auto-generated method stub
			ByteBuf frame = (ByteBuf)super.decode(ctx, buffer);
			if(frame ==  null) {
				return null;
			}
			int index = frame.indexOf(frame.readerIndex(), frame.writerIndex(), SPACE);
			return new Cmd(frame.slice(frame.readerIndex(),index),frame.slice(index+1,frame.writerIndex()));
		}
		
	}
	
	public static final class CmdHandler
		extends SimpleChannelInboundHandler<Cmd> {
        @Override
        public void channelRead0(ChannelHandlerContext ctx, Cmd msg)
            throws Exception {
            // Do something with the command
            //处理传经 ChannelPipeline 的 Cmd 对象
        }
		
	}
}
