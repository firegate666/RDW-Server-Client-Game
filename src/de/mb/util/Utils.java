package de.mb.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;

import org.apache.log4j.Logger;

/**
 * Some useful methods which can be used anywhere :)
 *
 * @author mbehnke
 *
 */
public class Utils {
	final static Logger log = Logger.getLogger(Utils.class);

	public static String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}

	/**
	 * Write content to file using default encoding UTF-8
	 *
	 * @param filename
	 * @param content
	 * @return
	 */
	public static boolean writeToFile(String filename, String content) {
		return Utils.writeToFile(filename, content, "UTF-8");
	}

	/**
	 * read string from file
	 *
	 * @param filename
	 * @return
	 */
	public static String readFromFile(String filename) {
		File f = new File(filename);
		return readFromFile(f);
	}

	/**
	 * read string from file
	 *
	 * @param f
	 * @return
	 */
	public static String readFromFile(File f) {
		String result = "";
		String line = null;
		FileReader fis = null;
		BufferedReader dis = null;
		try {
			fis = new FileReader(f);
			dis = new BufferedReader(fis);
			while ((line = dis.readLine()) != null)
				result += line + "\n";
		} catch (FileNotFoundException e) {
			log.error(e);
			result = e.getMessage();
		} catch (IOException e) {
			log.error(e);
			result = e.getMessage();
		} finally {
			if (dis != null)
				try {
					dis.close();
				} catch (IOException e) {
					log.error(e);
					result = e.getMessage();
				}
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					log.error(e);
					result = e.getMessage();
				}
		}
		return result;
	}

	/**
	 * Write content to file setting character encoding
	 *
	 * @param filename
	 * @param content
	 * @param encoding
	 * @return
	 */
	public static boolean writeToFile(String filename, String content,
			String encoding) {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		try {
			fos = new FileOutputStream(filename);
			osw = new OutputStreamWriter(fos, encoding);
			osw.write(content);
			osw.close();
			fos.close();
			return true;
		} catch (FileNotFoundException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} finally {
			if (osw != null)
				try {
					osw.close();
				} catch (IOException e) {
					log.error(e);
				}
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					log.error(e);
				}
		}
		return false;
	}

	public static boolean chmodToExecutable(String filename) {
		String command = "chmod 700";
		command += " " + filename;
		try {
			Process proc = Runtime.getRuntime().exec(command);
			return true;
		} catch (IOException e) {
			log.error(e);
			return false;
		}
	}

	/**
	 * create softlink
	 *
	 * @param source
	 * @param destination
	 * @return
	 */
	public static boolean createSoftlink(String source, String destination) {
		return createSymlink(source, destination, false);
	}

	/**
	 * create hardlink
	 *
	 * @param source
	 * @param destination
	 * @return
	 */
	public static boolean createHardlink(String source, String destination) {
		return createSymlink(source, destination, true);
	}

	/**
	 * create symlink for source
	 *
	 * @param source
	 * @param destination
	 * @param hardlink
	 *            ignored if soure is directory
	 * @return
	 */
	public static boolean createSymlink(String source, String destination,
			boolean hardlink) {
		String command = "ln";
		if (!hardlink || (new File(source).isDirectory())) // directories must
			// not be hardlinked
			command = command + " -s";

		command += " " + source;
		command += " " + destination;

		try {
			Process proc = Runtime.getRuntime().exec(command);
			return true;
		} catch (IOException e) {
			log.error(e);
			return false;
		}
	}

	/**
	 * Extend number with leading zeroes
	 *
	 * @param value
	 *            number
	 * @param digits
	 *            number of digits
	 * @return
	 */
	public static String leadingZero(int value, int digits) {
		String result = "" + value;
		while (result.length() < digits)
			result = "0" + result;
		return result;

	}

	/**
	 * return timestamp for local time as YY-MM-DD HH:MM:SS
	 *
	 * @return timestamp
	 */
	public static String getTimestamp() {
		String result = "";
		Calendar c = Calendar.getInstance();
		result += leadingZero(c.get(Calendar.YEAR), 2);
		result += "-" + leadingZero(c.get(Calendar.MONTH) + 1, 2);
		result += "-" + leadingZero(c.get(Calendar.DATE), 2);
		result += " " + leadingZero(c.get(Calendar.HOUR_OF_DAY), 2);
		result += ":" + leadingZero(c.get(Calendar.MINUTE), 2);
		result += ":" + leadingZero(c.get(Calendar.SECOND), 2);
		return result;
	}

}
