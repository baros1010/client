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
		// �����߳��� accept
		//NioEventLoopGroup���¼�ע�ᵽEventLoop��Selector�Ͻ����¼���ѯ
		EventLoopGroup baseGroup = new NioEventLoopGroup(1);
		EventLoopGroup workGroup2 = new NioEventLoopGroup();
		try{
			ServerBootstrap b=new ServerBootstrap();
			//channel���ڴ�������ͨ��ʵ��
			//handler��������ͨ������
			//childHandler�������Socket����
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
			//ͨ��bind��������
			ChannelFuture f=b.bind(7777).sync();
			f.channel().close().sync();
		
		}finally{
			
		}
	}
}
