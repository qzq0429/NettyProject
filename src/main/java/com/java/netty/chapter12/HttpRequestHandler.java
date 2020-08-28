package com.java.netty.chapter12;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	private final String wsUri;
	private static final File INDEX;
	
	static {
		URL location = HttpRequestHandler.class
				.getProtectionDomain()
				.getCodeSource().getLocation();
		try {
		String path = location.toURI() + "index.html";
		path = !path.contains("file:") ? path : path.substring(5);
		INDEX=new File("C:\\Users\\Michael Qi\\Desktop\\index.html");
		}catch (Exception e) {
			// TODO: handle exception
            throw new IllegalStateException(
                    "Unable to locate index.html", e);
		}
	}
	
	public HttpRequestHandler(String wsUri) {
		// TODO Auto-generated constructor stub
		this.wsUri = wsUri;
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
		// TODO Auto-generated method stub
		if(wsUri.equalsIgnoreCase(msg.getUri())){
			System.out.println("h1::"+msg.refCnt());
			ctx.fireChannelRead(msg.retain());
			System.out.println("h2::"+msg.refCnt());
		}else {
			if(HttpHeaders.is100ContinueExpected(msg)) {
				send100Continue(ctx);
			}
			RandomAccessFile file = new RandomAccessFile(INDEX,"r");
			HttpResponse response = new DefaultHttpResponse(
					msg.getProtocolVersion(),HttpResponseStatus.OK);
			response.headers().set(
					HttpHeaders.Names.CONTENT_TYPE,
					"text/plain; charset=UTF-8");
			boolean keepAlive = HttpHeaders.isKeepAlive(msg);
			
			if(keepAlive) {
				response.headers().set(HttpHeaders.Names.CONTENT_LENGTH,file.length());
				response.headers().set(HttpHeaders.Names.CONNECTION,HttpHeaders.Values.KEEP_ALIVE);
			}
			ctx.write(response);
			if(ctx.pipeline().get(SslHandler.class) == null){
				ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
			}else {
				ctx.write(new ChunkedNioFile(file.getChannel()));
			}
			
			ChannelFuture future = ctx.writeAndFlush(
					LastHttpContent.EMPTY_LAST_CONTENT
					);
			if((!keepAlive)) {
				future.addListener(ChannelFutureListener.CLOSE);
			}
		} 
	}
	
	private static void send100Continue(ChannelHandlerContext ctx) {
		FullHttpResponse response = new DefaultFullHttpResponse(
			HttpVersion.HTTP_1_1,HttpResponseStatus.CONTINUE);
			ctx.writeAndFlush(response);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
        //cause.printStackTrace();
        ctx.close();
	}

}