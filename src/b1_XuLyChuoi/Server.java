package b1_XuLyChuoi;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server{
	public static void main(String[] args) throws IOException {
		Server serverform = new Server();
		System.out.println("Server is started!");
		 try (ServerSocket server = new ServerSocket(7171)) {
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
                System.out.println("Connected: " + this.socket);
                
//                out = new PrintWriter(this.socket.getOutputStream(), true);

            	DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            	DataInputStream dis = new DataInputStream(socket.getInputStream());
            	System.out.println("....Server is waiting...");
            	String st = dis.readUTF();
            	dos.writeUTF(handle(st, "upper"));
            	dos.flush();
            	dos.writeUTF(handle(st, "lower"));
            	dos.flush();
            	dos.writeUTF(handle(st, "reverse"));
            	dos.flush();
            	dos.writeUTF(handle(st, "demTu"));
            	dos.flush();
            	dos.writeUTF(handle(st, "soNguyenAm"));
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
        
    	public String handle(String s, String type) {
    		String rep = "";
    		int soTu = 0;
    		int soNguyenAm=0;
    		for(int i = 0; i < s.length(); i++) {
    			switch(type) {
	    			case "upper":{
	    				if(s.charAt(i) >=97 && s.charAt(i) <= 122) {rep += (char)(s.charAt(i) - 32);}
	    				else rep += s.charAt(i);
	    				break;
	    			}
	    			case "lower":{
	    				if(s.charAt(i) >=65 && s.charAt(i) <= 90) {rep += (char)(s.charAt(i) + 32);}
	    				else rep += s.charAt(i);
	    				break;
	    			}
	    			case "reverse":{
	    				if(s.charAt(i) >=97 && s.charAt(i) <= 122) {rep += (char)(s.charAt(i) - 32);}
	    				else if(s.charAt(i) >=65 && s.charAt(i) <= 90) {rep += (char)(s.charAt(i) + 32);}
	    				else rep += s.charAt(i);
	    				break;
	    			}
	    			case "demTu":{
	    				if(s.charAt(i) == ' ' && i+1<s.length() && s.charAt(i+1)!= ' ') soTu++;
	    				break;
	    			}
	    			case "soNguyenAm":{
	    				if((s.charAt(i)+"").matches("[ueoaiUEOAI]")) soNguyenAm++;
	    				break;
	    			}
    			}
    		}
    		if(s.charAt(0) != ' ') {soTu++;};
	    		return type=="demTu"?soTu+"":type=="soNguyenAm"?soNguyenAm+"":rep;
		}
	}
}
