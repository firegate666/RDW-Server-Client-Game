package de.mb.util;

/*
 * Created on 06.02.2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class WebstartFileProvider {

	final static Logger log = Logger.getLogger(WebstartFileProvider.class);

	public static File getFile(String string) throws IOException {
		log.info("Requesting file: " + string);
		try {
			File file = new File(string);
			WebstartFileProvider wsfp = new WebstartFileProvider();
			if (file.exists() && file.canRead()) {
				return file;
			} else {
				log.info("try to create temp file for " + "/resource/"
						+ file.getName());
				InputStream is = wsfp.getClass().getResourceAsStream("/resource/" + file.getName());
				log.info(is);
				File returnfile = File.createTempFile("temp", file.getName());
				BufferedWriter bw = new BufferedWriter(new FileWriter(
						returnfile));
				BufferedReader in = new BufferedReader(
						new InputStreamReader(is));
				String str;
				while ((str = in.readLine()) != null) {
					bw.write(str);
				}
				bw.flush();
				is.close();
				in.close();
				bw.close();
				return returnfile;
			}
		} catch (Exception e) {
			log.error("File " + string + " not found", e);
			throw (RuntimeException) e;
		}
	}
}