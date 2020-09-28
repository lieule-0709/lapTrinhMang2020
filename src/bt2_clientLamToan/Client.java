package bt2_clientLamToan;

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

	Label l1, l2, l3;
	TextField t1, t2, t3, t4, t5, t6;
	Button send, exit, reset;
	Panel p1, p2, p3;
	
	public Client() {
		l1 = new Label("LÀM TOÁN");
		l2 = new Label("nhập biểu thức: ");
		l3 = new Label("kết quả: ");
		t1 = new TextField("");
		t1.setPreferredSize( new Dimension( 30, 100));
		t2 = new TextField("");
		t2.setEditable(false);
		t2.setPreferredSize( new Dimension( 30, 100));
//		ketqua.setPreferredSize( new Dimension( 30, 100));
//		ketqua.setEditable(false);
		
		send = new Button("Send");
		reset = new Button("Reset");
		exit = new Button("Exit");
		
		send.addActionListener(this);
		reset.addActionListener(this);
		exit.addActionListener(this);
		
		p1 = new Panel(new FlowLayout());
		p2 = new Panel(new GridLayout(2, 2));
		p3 = new Panel(new FlowLayout());
		
		p1.add(l1);
		
		p2.add(l2);
		p2.add(t1);
		p2.add(l3);
		p2.add(t2);	
		
		p3.add(send);
		p3.add(reset);
		p3.add(exit);
		
		setLayout( new GridLayout(3,1));
		add(p1);
		add(p2);
		add(p3);
		
		setBounds(100, 100, 400, 200);
		setVisible(true);	
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == send) {
			try{
				@SuppressWarnings("resource")
				Socket socket = new Socket("localhost", 7575);
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());;
				try{
					dos.writeUTF(t1.getText());
					dos.flush();
					//nhan data tu server
					String st = dis.readUTF();
					t2.setText(st);
				}catch(Exception exep) {
					System.out.println(exep);
				}
			}catch(Exception exep) {
				System.out.print(exep);
			}
		}
		if(e.getSource() == reset) { t1.setText(""); t2.setText("");
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
