package bt2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("localhost", 7000);
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());;
		//nhap chuoi de gui den server
		Scanner kb = new Scanner(System.in);
		while(true) {
			System.out.print("Client: ");
			String msg = kb.nextLine();
			dos.writeUTF("Client: "+ msg);
			dos.flush();
			//nhan data tu server
			String st = dis.readUTF();
			System.out.println(st);
			kb = kb.reset();
		}
	}

}
