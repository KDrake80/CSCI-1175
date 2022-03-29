/*
 * Kevin Drake
 * 3/2/22
 * This is the Server class the runs the program. It communicates with the client class to send/receive an account
 */
import java.util.*;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;

public class BarterServer extends Application {
	List<Account> accounts = new ArrayList<>();
	ObjectOutputStream outputClient;
	ObjectInputStream inputClient;
	ObjectInputStream inputFile;
	ObjectOutputStream outputFile;
	ServerSocket server;
	Socket socket;
	Account clientAccount;
	
	@Override
	public void start(Stage stage) {
		try {
			server = new ServerSocket(8000);
			socket = server.accept();
			inputFile = new ObjectInputStream(
					new DataInputStream(new FileInputStream("BarterData.dat")));
			accounts = retrieveList();
			inputClient = new ObjectInputStream(socket.getInputStream());
			String name = inputClient.readUTF();
			String pass = inputClient.readUTF();
			if (accounts.size() == 0) {
				clientAccount = new Account(name, pass);
			}
			else {
				for (int i = 0; i < accounts.size(); i++) {
					if (accounts.get(i).getAccountName().equalsIgnoreCase(name)) {
						clientAccount = accounts.get(i);
						accounts.remove(i);
					}
					else {
						clientAccount = new Account(name, pass);
					}
				}
			}
			outputClient = new ObjectOutputStream(socket.getOutputStream());
			outputClient.writeObject(clientAccount);
			Object o = inputClient.readObject();
			clientAccount = (Account)o;
			accounts.add(clientAccount);
			outputFile = new ObjectOutputStream(
					new DataOutputStream(new FileOutputStream("BarterData.dat")));
			for (int i = 0; i < accounts.size(); i++) {
				outputFile.writeObject(accounts.get(i));
			}
			outputFile.close();
			inputFile.close();
			inputClient.close();
			outputClient.close();
			socket.close();
		}
		catch (EOFException of) {
			System.out.println(": )");
		}
		catch (IOException io) {
			io.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Account> retrieveList(){
		List<Account> result = new ArrayList<>();
		try {
			while(true) {
				result.add((Account)(inputFile.readObject()));
			}
		}
		catch (EOFException ex) {

		}
		catch (IOException io) {
			System.out.println("Retrieval Error");
		}
		catch (Exception ex) {

		}
		try {
			inputFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void saveAccounts() {
		try {
			for (int i = 0; i < accounts.size(); i++) {
				outputFile.writeObject(accounts.get(i));
			}
			outputFile.close();
		}
		catch (IOException io) {
			io.printStackTrace();
		}
	}
	
	class LogIn implements Runnable {
		public void run() {
			try {
				outputFile = new ObjectOutputStream(
						new DataOutputStream(new FileOutputStream("BarterData.dat")));
				String name = inputClient.readUTF();
				String pass = inputClient.readUTF();
				for (Account a: accounts) {
					if (name.equalsIgnoreCase(a.getAccountName())) {
						clientAccount = a;
					}	
				}
			}
			catch (IOException i) {
				i.printStackTrace();
			}
		}
	}
}
