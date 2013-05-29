package Datenanalyse.Uebung1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * The <code>FileWriter</code> opens, closes and writes in Files.
 * 
 * @author Dennis Haegler - s0532338
 * 
 */
public class FileWriter {

	/** BufferedWrite used to write in the file */
	private BufferedWriter bufferedWriter;

	/**
	 * Status of the FileWriter. It is open if the BufferedWriter were been
	 * initialized.
	 */
	private boolean isOpen;

	/**
	 * Constructs an empty FileWriter.
	 */
	public FileWriter() {
		this.bufferedWriter = null;
		this.isOpen = false;
	}

	/**
	 * Constructs a FileWriter for the file in the given path. The Constructor
	 * will initializes the BufferedWriter with the file from the given path.
	 * 
	 * @param path the path where the file lies.
	 */
	public FileWriter(String path) {
		initBufferedWriter(path);
	}

	/**
	 * Opens a file on the given path.
	 * 
	 * @param path
	 *            is the path of the file where the file lies.
	 */
	public void openFile(String path) {
		initBufferedWriter(path);
	}

	/**
	 * Writes the given test in the file, if the file is opened.
	 * 
	 * @param text
	 *            the test to wish to write in the file.
	 */
	public void write(String text) {
		try {
			this.bufferedWriter.write(text);
		} catch (IOException e) {
			App.logger.error("Cannot write in file.");
		}
	}

	/**
	 * Closes a file.
	 */
	public void closeFile() {
		try {
			this.bufferedWriter.close();
		} catch (IOException e) {
			System.err.println("Problem closing to file.");
			App.logger.error("Erro by closing the file");
		}
	}

	/**
	 * Returns the status of the <code>FileWriter</code>.
	 * 
	 * @return the status of the FileWriter.
	 */
	public boolean isOpen() {
		return isOpen;
	}

	/**
	 * Initializes a the <code></code>
	 * 
	 * @param path
	 */
	private void initBufferedWriter(String path) {
		try {
			File file = new File(path);
			FileOutputStream fileOut = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fileOut);
			this.bufferedWriter = new BufferedWriter(osw);
			this.isOpen = true;
		} catch (IOException io) {
			App.logger.error("Erro by open file " + path);
		}
	}
}
