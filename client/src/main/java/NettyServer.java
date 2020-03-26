package Netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyServer {
	public static void main(String[] args) throws InterruptedException {
		// 创建线程组 accept
		//NioEventLoopGroup将事件注册到EventLoop的Selector上进行事件轮询
		EventLoopGroup baseGroup = new NioEventLoopGroup(1);
		EventLoopGroup workGroup2 = new NioEventLoopGroup();
		try{
			ServerBootstrap b=new ServerBootstrap();
			//channel用于创建具体通道实例
			//handler处理服务端通道请求
			//childHandler处理具体Socket请求
			b.group(baseGroup, workGroup2).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 10)
			.handler(new LoggingHandler(LogLevel.DEBUG)).childHandler(new ChannelInitializer<NioSocketChannel>(){

				@Override
				protected void initChannel(NioSocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					 ch.pipeline().addLast(new StringDecoder());
				     ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                         @Override
                         protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                             System.out.println(msg);
                         }


                     });
				}});
			//通过bind启动服务
			ChannelFuture f=b.bind(7777).sync();
			f.channel().close().sync();
		
		}finally{
			
		}
	}
}
