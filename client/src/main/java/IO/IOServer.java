package IO;

import java.io.IOException;
import java.net.ServerSocket;

public class IOServer {
	public static void main(String args[]){
		try {
			ServerSocket serverSocket = new ServerSocket(8000);
//			new Thread(() -> )
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
