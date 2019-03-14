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
	  //ͨ��������
    private Selector selector;
    public NioServer init(int port) throws IOException{
    	//�򿪹ܵ�
    	ServerSocketChannel  serverSocketChannel=ServerSocketChannel.open();
    	//����Ϊ������
    	serverSocketChannel.configureBlocking(false);	
    	serverSocketChannel.socket().bind(new InetSocketAddress(port));
    	//��ȡͨ��������
    	selector=Selector.open();
        //��ͨ����������ͨ���󶨣���Ϊ��ͨ��ע��SelectionKey.OP_ACCEPT�¼���
        //ֻ�е����¼�����ʱ��Selector.select()�᷵�أ�����һֱ������
    	serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    	return this;
    }
    public void listen()throws IOException{
    	  System.out.println("�������������ɹ�");
    	  while(true){
    		  //����ע����¼�����ʱ���������أ�����������
    		  selector.select();  
    		  //��ȡselector�еĵ�������ѡ����Ϊע����¼�
    		  Iterator<SelectionKey> it=selector.selectedKeys().iterator();
    		  while(it.hasNext()){
    			  SelectionKey key=it.next();
    			  //ɾ����ѡkey����ֹ�ظ�����
    			  it.remove();
    			  if(key.isAcceptable()){
    				  ServerSocketChannel serverSocketChannel=(ServerSocketChannel) key.channel();
    				  SocketChannel socketChannel=serverSocketChannel.accept();
    				  socketChannel.configureBlocking(false);
    				  socketChannel.write(ByteBuffer.wrap("wqnmlgb".getBytes()));
    				  socketChannel.register(selector, SelectionKey.OP_READ);
    				  System.out.println("�ͻ������������¼�");
    			  }else if(key.isReadable()){
    				  SocketChannel socketChannel=(SocketChannel) key.channel();
    				  //������ȡ���ݻ�����
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
