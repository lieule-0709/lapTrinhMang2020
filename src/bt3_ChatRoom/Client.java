package bt3_ChatRoom;

import java.awt.Button;
import java.awt.Color;
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
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Client extends JFrame implements ActionListener {
	JButton send, exit, reset;
	JPanel p1;
	JLabel lbName;
	JTextArea messArea;
	JTextField mess;
	JScrollPane SCmesBox;
	String name;
	Socket  socket;
	Scanner scan;
	PrintWriter printWriter;
	
	public Client(String name) throws IOException{
		//connect server
		try {
			this.socket = new Socket("localhost", 7373);
			this.scan = new Scanner(this.socket.getInputStream());
			this.printWriter = new PrintWriter(this.socket.getOutputStream(), true);
	    }catch (Exception e) {
	    	System.out.println(e.getMessage());
	    	System.out.println(e.getStackTrace());
            this.socket.close();
	    }

		this.name = name;
		this.initForm();
        // listening server{
		try {
			while(true){
				String st = scan.nextLine();
				System.out.println(st);
	           this.messArea.insert(st + "\n", this.messArea.getText().length());
	           scan.reset();
	        }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            this.socket.close();
        }
		
        
	}
	
	public void initForm() {
		this.messArea = new JTextArea(20, 32);
        this.mess = new JTextField(32);
        this.SCmesBox = new JScrollPane(this.messArea);
        lbName = new JLabel("   ");

		send = new JButton("Send");
		reset = new JButton("Reset");
		exit = new JButton("Exit");
		p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));

        mess.requestFocusInWindow();
		
        this.messArea.setEditable(false);
        this.messArea.setBackground(Color.WHITE);
        this.messArea.setLineWrap(true);
        
        this.mess.addActionListener(this);
		send.addActionListener(this);
		reset.addActionListener(this);
		exit.addActionListener(this);
        
        this.SCmesBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		p1.add(send);
		p1.add(reset);
		p1.add(exit);

        this.setTitle("Chat Room");
        this.add(lbName);
        this.add(this.SCmesBox);
        this.add(this.mess);
        this.add(p1);
        this.setSize(400, 455);
        this.setResizable(false);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setVisible(true);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JFrame login = new JFrame();
        login.setBounds(100, 100, 200, 150);
        login.setLayout(new FlowLayout(FlowLayout.CENTER));
        login.setResizable(false);
        JTextField input = new JTextField(15);
        JButton submit = new JButton("login");
        login.add(new JLabel("Enter your name"));
        login.add(input);
        login.add(submit);
        login.setVisible(true);
        submit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String _name = input.getText();
                if (_name.length() > 0) {
                    try {
                        name = _name;
                        lbName.setText(name+ "      ");
                        login.setVisible(false);
                        login.dispose();
                    } catch (Exception excep) {
                        JOptionPane.showMessageDialog(login, excep.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(login, "Please complete all field!");
                    input.requestFocus();
                }
            }
        });
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == send) {
			try{
				printWriter.println(name + ": " + mess.getText());
				messArea.insert("you: "+ mess.getText()+ "\n", messArea.getText().length());
				mess.setText("");
		        mess.requestFocusInWindow();
			}catch(Exception exep) {
				System.out.print(exep);
			}
		}
		if(e.getSource() == reset) { messArea.setText(""); mess.setText("");
        mess.requestFocusInWindow();
		}
		if(e.getSource() == exit) { System.exit(0);}
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		Client client = new Client("lieule");
        client.requestFocusInWindow();
	}
}
