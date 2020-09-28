package bt1_UDP_xuLyChuoi;

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
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server{
	DatagramSocket serverSocket;
	
	public Server() {
		try{
			serverSocket = new DatagramSocket(9876);
            while (true) {
        		byte[] receiveData = new byte[1024];
        		System.out.println("Server is started");
	            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	            serverSocket.receive(receivePacket);
                try {
                    new ClientTask(receivePacket);
            		System.out.println("Connected: " + receivePacket.getAddress().toString());
                } catch (Exception e) {
//                    socket.close();
                    System.out.println(e.getMessage());
                    System.out.println(e.getStackTrace());
                }
            }
	     }
		 catch(Exception e) {
	    	 System.out.println(e);
	     }
	}
	public static void main(String[] args) throws IOException {
		new Server();
	}
	
	public class ClientTask extends Thread {
        private DatagramPacket receivePacket;

        public ClientTask(DatagramPacket _receivePacket) {
            this.receivePacket = _receivePacket;
            this.start();
        }

        @Override
        public void run() {
            this.doMission();
        }

        private void doMission() {
            try {
                System.out.println("Connected: " + this.receivePacket.getAddress().toString());
            	System.out.println("....Server is waiting...");

    			byte[] sendData = new byte[1024];
	              int port = receivePacket.getPort();
	              InetAddress IPAddress = receivePacket.getAddress();
	              String st = new String(receivePacket.getData());
	              
	              String strSend = reverse(st).trim() +"&" +handle(st, "upper").trim() +"&"+ handle(st, "lower").trim()  +"&"+ handle(st, "reverseUL").trim() +"&" + handle(st, "demTu").trim();
	              
	              sendData = strSend.toString().getBytes();
	              System.out.print(strSend);
	              
	              DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAddress,port);
	              serverSocket.send(sendPacket);
	              
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.getStackTrace());
            } finally {
                try {
//                    this.socket.close();
//                    System.out.println("Closed socket" + this.socket);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println(e.getStackTrace());
                }
            }
        }
        
    	public String reverse(String s) {
    		String rep ="";
    		for(int i = s.length() - 1; i >= 0; i--) {
    			rep = rep + s.charAt(i);
    		}
    		return rep;
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
	    			case "reverseUL":{
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
