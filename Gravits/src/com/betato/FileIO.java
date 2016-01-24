package com.betato;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FileIO {
	private static final String ENTRY_SEPERATOR = "|";
	
	public boolean write(ArrayList<Body> bodies, File path) {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"));
			// Write all bodies in body list to file
			for (Body body : bodies) {
				// Write one line per body, separating parameters with the specified character(s)
				writer.write(
						String.valueOf(body.mass + ENTRY_SEPERATOR + 
						String.valueOf(body.radius) + ENTRY_SEPERATOR + 
						body.position.toString() + ENTRY_SEPERATOR + 
						body.acceleration.toString() + ENTRY_SEPERATOR + 
						body.velocity.toString()));
				writer.newLine();
			}
			// Close writer and return true to indicate success
			writer.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public ArrayList<Body> read(File path) {
		try {
			ArrayList<Body> bodies = new ArrayList<Body>();
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line = reader.readLine();
			// Read lines until end of file
			while (line != null) {
				// Split string at designated character and read file entries to body list
				String[] bodyParams = line.split(ENTRY_SEPERATOR);
				double mass = Double.parseDouble(bodyParams[0]);
				double radius = Double.parseDouble(bodyParams[1]);
				Vec2d position = new Vec2d().parseVec2d(bodyParams[2]);
				Vec2d acceleration = new Vec2d().parseVec2d(bodyParams[3]);
				Vec2d velocity = new Vec2d().parseVec2d(bodyParams[4]);
				Body b = new Body(mass, radius, position, acceleration, velocity);
				bodies.add(b);
				line = reader.readLine();
			}
			// Close reader when reading complete
			reader.close();
			return bodies;
		} catch (IOException | NumberFormatException e) {
			return null;
		}
	}

	public ArrayList<File> getFiles(String directory) {
		try {
			ArrayList<File> files = new ArrayList<File>();
			File folder = new File(directory);
			// Loop through directory and add files to list
			for (File fileEntry : folder.listFiles()) {
				if (fileEntry.isFile()) {
					files.add(fileEntry);
				}
			}
			// Return list of files
			return files;
		} catch (Exception e) {
			// Return null if an error occurred
			return null;
		}
	}
}
