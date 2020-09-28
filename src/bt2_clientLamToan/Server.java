package bt2_clientLamToan;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{

	public static void main(String[] args) throws IOException {
		Server serverform = new Server();
		System.out.println("Server is started!");
		 try (ServerSocket server = new ServerSocket(7575)) {
            while (true) {
                Socket socket = server.accept();
                try {
                    new ClientTask(socket);
            		System.out.println("Connected: " + socket);
                } catch (Exception e) {
                    socket.close();
                    System.out.println(e.getMessage());
                    System.out.println(e.getStackTrace());
                }
            }
	     }
		 catch(Exception e) {
	    	 System.out.println(e);
	     }
	}
	
	public static class ClientTask extends Thread {
        private Socket socket;
//        private PrintWriter out;

        public ClientTask(Socket _socket) {
            this.socket = _socket;
            this.start();
        }

        @Override
        public void run() {
            this.doMission();
        }

        private void doMission() {
            try {
            	DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            	DataInputStream dis = new DataInputStream(socket.getInputStream());
            	System.out.println("....Server is waiting...");
            	String st = dis.readUTF();
            	dos.writeUTF(Calculator.calculator(st)+"");
            	dos.flush();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.getStackTrace());
            } finally {
                try {
                    this.socket.close();
                    System.out.println("Closed socket" + this.socket);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println(e.getStackTrace());
                }
            }
        }
        
	}
}
