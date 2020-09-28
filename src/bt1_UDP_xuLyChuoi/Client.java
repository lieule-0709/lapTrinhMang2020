package bt1_UDP_xuLyChuoi;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client extends Frame implements ActionListener {

	Label l1, l2, l3, l4, l5, l6, l7;
	TextField t1, t2, t3, t4, t5, t6;
	TextArea textArea;
	Button send, exit, reset;
	Panel p1, p2, p3, p4;
	
	public Client() {
		setBounds(100, 100, 500, 500);
		setResizable(false);
		
		l1 = new Label("Xử lí chuỗi");
		l2 = new Label("nhập chuỗi: ");
		l3 = new Label("chuỗi đảo: ");
		l4 = new Label("chuỗi hoa: ");
		l5 = new Label("chuỗi thường: ");
		l6 = new Label("chuỗi đảo hoa thường: ");
		textArea = new TextArea();
		textArea.setSize(400, 100);
		l7 = new Label("số từ: ");
		t1 = new TextField("");
		t1.setPreferredSize( new Dimension( 30, 100));
		t2 = new TextField("");
		t2.setEditable(false);
		t2.setPreferredSize( new Dimension( 30, 100));
		t3 = new TextField("");
		t3.setEnabled(false);
		t3.setPreferredSize( new Dimension( 30, 100));
		t4 = new TextField("");
		t4.setEnabled(false);
		t4.setPreferredSize( new Dimension( 30, 100));
		t5 = new TextField("");
		t5.setEnabled(false);
		t5.setPreferredSize( new Dimension( 30, 100));
		t6 = new TextField("");
		t6.setEnabled(false);
		t6.setPreferredSize( new Dimension( 30, 100));
		
		send = new Button("Send");
		reset = new Button("Reset");
		exit = new Button("Exit");
		
		send.addActionListener(this);
		reset.addActionListener(this);
		exit.addActionListener(this);
		
		p1 = new Panel(new FlowLayout());
		p1.setPreferredSize(new Dimension(400, 50));
		p2 = new Panel(new GridLayout(6, 2));
		p2.setPreferredSize(new Dimension(400, 200));
		p3 = new Panel(new GridLayout(1,1));
		p3.setPreferredSize(new Dimension(400, 150));
		p4 = new Panel(new FlowLayout());
		
		p1.add(l1);
		
		p2.add(l2);
		p2.add(t1);
		p2.add(l3);
		p2.add(t2);	
		p2.add(l4);
		p2.add(t3);
		p2.add(l5);
		p2.add(t4);	
		p2.add(l6);
		p2.add(t5);
		p2.add(l7);
		p2.add(t6);
		
		p3.add(textArea);
		
		p4.add(send);
		p4.add(reset);
		p4.add(exit);
		
		setLayout(new FlowLayout());
		add(p1);
		add(p2);
		add(p3);
		add(p4);
		
		
		setVisible(true);	
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == send) {
			try{
				DatagramSocket clientSocket = new DatagramSocket();//gan cong
				InetAddress IPAddress = InetAddress.getByName("localhost");
				byte[] sendData = new byte[1024];
				byte[] receiveData = new byte[1024];
				
				try{
					sendData = t1.getText().getBytes();
					DatagramPacket sendPacket =
					new DatagramPacket(sendData, sendData.length,IPAddress,9876);
					clientSocket.send(sendPacket);
					DatagramPacket receivePacket =	new DatagramPacket(receiveData, receiveData.length);
					clientSocket.receive(receivePacket);
					String st= new String(receivePacket.getData());
					
					System.out.print(st);
					
					String [] repStr = st.split("&");
					t2.setText(repStr[0]);
					t3.setText(repStr[1]);
					t4.setText(repStr[2]);
					t5.setText(repStr[3]);
					t6.setText(repStr[4]);
					clientSocket.close();
					
				}catch(Exception exep) {
					System.out.println(exep);
				}
			}catch(Exception exep) {
				System.out.print(exep);
			}
		}
		if(e.getSource() == reset) { t1.setText(""); t2.setText(""); t3.setText(""); t4.setText(""); t5.setText(""); t6.setText("");
		}
		if(e.getSource() == exit) { System.exit(0);}
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		Client client = new Client();
		client.addWindowListener( new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		
	}
}
