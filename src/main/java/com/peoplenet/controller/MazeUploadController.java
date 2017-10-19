package com.peoplenet.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.peoplenet.core.PathFinder;
import com.peoplenet.util.FileIO;

@Controller
@RequestMapping("/upload")
public class MazeUploadController {
	
	// This tmp dir should work. If you got IOException, please create a tmp dir on your system
	// For example,
	// public static final String TMP_DIR = "C:/tmp";
	public static final String TMP_DIR = System.getProperty("java.io.tmpdir");

	@GetMapping
	public String loadMaze() {
		return "maze-upload";
	}

	@PostMapping
	public String findRoute(MultipartHttpServletRequest request, Model model) throws IOException {

		MultipartFile multipartFile = request.getFile("maze-file");

		String mazeFileName = multipartFile.getOriginalFilename();

		BufferedWriter bw = Files.newBufferedWriter(Paths.get(TMP_DIR + "/" + mazeFileName));
		bw.write(new String(multipartFile.getBytes()));
		bw.flush();

		// load maze input file
		FileIO fileIO = new FileIO(TMP_DIR + "/" + mazeFileName);

		// create maze array
		List<String> lines = fileIO.getLines();
		int rows = lines.size();
		int columns = lines.get(0).length();
		char[][] maze = new char[rows][columns];
		// System.out.println("rows: " + rows + " cols: " + columns);
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < columns; y++) {
				maze[x][y] = lines.get(x).charAt(y);
				// System.out.print(maze[x][y]);
			}
			// System.out.println();
		}

		PathFinder finder = new PathFinder();
		finder.setMaze(maze);
		finder.startFinding();

		// System.out.println(finder.printRoute());
		// System.out.println("Total steps: " + finder.countSteps());

		model.addAttribute("mazeRoute", finder.printRoute());
		model.addAttribute("totalSteps", "Total steps: " + finder.countSteps());
		return "maze-upload";
	}

}