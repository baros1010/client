package Nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioServer {
	  //通道管理器
    private Selector selector;
    public NioServer init(int port) throws IOException{
    	//打开管道
    	ServerSocketChannel  serverSocketChannel=ServerSocketChannel.open();
    	//设置为非阻塞
    	serverSocketChannel.configureBlocking(false);	
    	serverSocketChannel.socket().bind(new InetSocketAddress(port));
    	//获取通道管理器
    	selector=Selector.open();
        //将通道管理器与通道绑定，并为该通道注册SelectionKey.OP_ACCEPT事件，
        //只有当该事件到达时，Selector.select()会返回，否则一直阻塞。
    	serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    	return this;
    }
    public void listen()throws IOException{
    	  System.out.println("服务器端启动成功");
    	  while(true){
    		  //当有注册的事件到达时，方法返回，否则阻塞。
    		  selector.select();  
    		  //获取selector中的迭代器，选中项为注册的事件
    		  Iterator<SelectionKey> it=selector.selectedKeys().iterator();
    		  while(it.hasNext()){
    			  SelectionKey key=it.next();
    			  //删除已选key，防止重复处理
    			  it.remove();
    			  if(key.isAcceptable()){
    				  ServerSocketChannel serverSocketChannel=(ServerSocketChannel) key.channel();
    				  SocketChannel socketChannel=serverSocketChannel.accept();
    				  socketChannel.configureBlocking(false);
    				  socketChannel.write(ByteBuffer.wrap("wqnmlgb".getBytes()));
    				  socketChannel.register(selector, SelectionKey.OP_READ);
    				  System.out.println("客户端请求连接事件");
    			  }else if(key.isReadable()){
    				  SocketChannel socketChannel=(SocketChannel) key.channel();
    				  //创建读取数据缓冲器
                      ByteBuffer buffer = ByteBuffer.allocate(10);
                      int reader=socketChannel.read(buffer);
                      byte[] data = buffer.array();
                      String message = new String(data);
                      System.out.println("receive message from client, size:" + buffer.position() + " msg: " + message);
    			  }
    		  }
    	  }
    	
    }
    public static void main(String[] args){
    	try {
			new NioServer().init(9999).listen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
