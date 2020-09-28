package bt1;

//Client connect den Server de lay ngay thang nam theo ICT

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Date;

public class TCPTimeServer {
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(8976);
		System.out.println("Server is started");
		while(true) {
			Socket socket  = server.accept();
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			String time = new Date().toString();
			dos.writeUTF("Server tra lai ngay gio: " +time);
			socket.close();
		}
	}
}
