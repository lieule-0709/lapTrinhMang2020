package bt3_ChatRoom;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server{

    private List<ClientTask> tasks;
    private ServerSocket serversk;
    private String db;
    
	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.waitingConnection();
	}
	
    public Server() throws IOException {
        this.serversk = new ServerSocket(7373);
        this.tasks = new ArrayList<>();
        this.db = "";
        System.out.println("Server is running ...");
    }

    public void waitingConnection() throws IOException {
        while (true) {
            Socket socket = serversk.accept();
            try {
                addTask(new ClientTask(socket));
            } catch (Exception e) {
                socket.close();
                System.out.println(e.getMessage());
                System.out.println(e.getStackTrace());
            }
        }
    }
    
    private synchronized void broadCast(ClientTask fromTask, String message) {
        for (int i = 0; i < this.tasks.size(); i++) {
        	ClientTask task = this.tasks.get(i);
            if (!task.equals(fromTask)) {
                try {
                    task.send(message);
                } catch (Exception e) {
                    task.close();
                    this.tasks.remove(i--);
                    System.out.println(e.getMessage());
                    System.out.println(e.getStackTrace());
                }
            }
        }
    }

    public synchronized void addTask(ClientTask task) {
    	tasks.add(task);
    }
    
    public synchronized void removeTask(ClientTask task) {
    	tasks.remove(task);
    }
    
	public class ClientTask extends Thread {
        private Socket socket;
    	public PrintWriter printWriter;
    	private Scanner scan;

        public ClientTask(Socket _socket) throws IOException {
            this.socket = _socket;
            this.scan = new Scanner(this.socket.getInputStream());            
            this.printWriter = new PrintWriter(this.socket.getOutputStream(), true);

            this.start();
        }

        @Override
        public void run() {
            this.doMission();
        }

        private void doMission() {
        	try {
        		while(true) {
        			String st = scan.nextLine();
        			broadCast(this, st);
        		}
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

        public void send(String message) throws IOException {
        	System.out.println("sv send: "+ message);
            this.printWriter.println(message);
        }

        public void close() {
            try {
                this.socket.close();
                removeTask(this);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getStackTrace());
            }
        }
	}
}
