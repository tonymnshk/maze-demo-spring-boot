package com.peoplenet.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileIO {

	private List<String> lines = new ArrayList<>();

	public FileIO() { }

	public FileIO(String path) {

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			String currentLine;

			while ((currentLine = br.readLine()) != null) {
				//System.out.println(currentLine);
				lines.add(currentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public List<String> getLines() {
		return lines;
	}

}