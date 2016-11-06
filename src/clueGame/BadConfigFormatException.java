package clueGame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception {
	
	public BadConfigFormatException() {
		super("Whoa there, your files have not been configured properly");
		FileWriter writer;
		try {
			writer = new FileWriter("logfile.txt", true);
			BufferedWriter bwriter = new BufferedWriter(writer);
			PrintWriter out = new PrintWriter(bwriter);
			out.println("Whoa there, your files have not been configured properly");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public BadConfigFormatException(String message){
		super(message);
		FileWriter writer;
		try {
			writer = new FileWriter("logfile.txt", true);
			BufferedWriter bwriter = new BufferedWriter(writer);
			PrintWriter out = new PrintWriter(bwriter);
			out.println(message);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
