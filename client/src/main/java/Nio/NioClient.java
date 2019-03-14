package Nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioClient {
	// �ܵ�������
	private Selector selector;

	public NioClient init(String ip, int port) throws IOException {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		selector = Selector.open();
	     //�ͻ������ӷ���������Ҫ����channel.finishConnect();����ʵ��������ӡ�
		socketChannel.connect(new InetSocketAddress(ip,port));
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		return this;
	}
	public void listen() throws IOException {
		 System.out.println("�ͻ�������");
		while(true){
			 
			selector.select();
			 Iterator<SelectionKey> ite = selector.selectedKeys().iterator();
			 while(ite.hasNext()){
				 SelectionKey key=ite.next();
				 ite.remove();
				 if(key.isConnectable()){
					   SocketChannel channel=(SocketChannel)key.channel();
					   //����������ӣ����������
					   if(channel.isConnectionPending()){
						   channel.finishConnect();
					   }
					   channel.configureBlocking(false);
					   channel.write(ByteBuffer.wrap("hello".getBytes()));
					   channel.register(selector, SelectionKey.OP_READ);
				 }
				 else if(key.isReadable()){
	                    SocketChannel channel = (SocketChannel)key.channel();
	                    
	                    ByteBuffer buffer = ByteBuffer.allocate(10);
	                    channel.read(buffer);
	                    byte[] data = buffer.array();
	                    String message = new String(data);
	                    
	                    System.out.println("recevie message from server:, size:" + buffer.position() + " msg: " + message);

				 }
			 }
		}
	}
	  public static void main(String[] args) throws IOException {
	        new NioClient().init("localhost", 9999).listen();
	    }
}
