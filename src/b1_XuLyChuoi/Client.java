package b1_XuLyChuoi;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client extends Frame implements ActionListener {

	Label l1, l2, l3, l4, l5, l6, l7;
	TextField t1, t2, t3, t4, t5, t6;
	Button send, exit, reset;
	Panel p1, p2, p3;
	
	public Client() {
		l1 = new Label("Xử lí chuỗi");
		l2 = new Label("nhập chuỗi: ");
		l3 = new Label("chuỗi hoa: ");
		l4 = new Label("chuỗi thường: ");
		l5 = new Label("chuỗi đảo hoa thường: ");
		l6 = new Label("số từ: ");
		l7 = new Label("số nguyên âm: ");
		t1 = new TextField("");
		t1.setPreferredSize( new Dimension( 30, 100));
		t2 = new TextField("");
		t2.setEditable(false);
		t2.setPreferredSize( new Dimension( 30, 100));
		t3 = new TextField("");
		t3.setEditable(false);
		t3.setPreferredSize( new Dimension( 30, 100));
		t4 = new TextField("");
		t4.setEditable(false);
		t4.setPreferredSize( new Dimension( 30, 100));
		t5 = new TextField("");
		t5.setEditable(false);
		t5.setPreferredSize( new Dimension( 30, 100));
		t6 = new TextField("");
		t6.setEditable(false);
		t6.setPreferredSize( new Dimension( 30, 100));
//		ketqua.setPreferredSize( new Dimension( 30, 100));
//		ketqua.setEditable(false);
		
		send = new Button("Send");
		reset = new Button("Reset");
		exit = new Button("Exit");
		
		send.addActionListener(this);
		reset.addActionListener(this);
		exit.addActionListener(this);
		
		p1 = new Panel(new FlowLayout());
		p2 = new Panel(new GridLayout(6, 2));
		p3 = new Panel(new FlowLayout());
		
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
		
		p3.add(send);
		p3.add(reset);
		p3.add(exit);
		
		setLayout( new GridLayout(3,1));
		add(p1);
		add(p2);
		add(p3);
		
		setBounds(100, 100, 400, 400);
		setVisible(true);	
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == send) {
			try{
				@SuppressWarnings("resource")
				Socket socket = new Socket("localhost", 7171);
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());;
				try{
					dos.writeUTF(t1.getText());
					dos.flush();
					//nhan data tu server
					String st = dis.readUTF();
					t2.setText(st);
					st = dis.readUTF();
					t3.setText(st);
					st = dis.readUTF();
					t4.setText(st);
					st = dis.readUTF();
					t5.setText(st);
					st = dis.readUTF();
					t6.setText(st);
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
