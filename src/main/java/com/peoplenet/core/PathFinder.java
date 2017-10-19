package com.peoplenet.core;

public class PathFinder {

	private final static int MAX_VALUE = Integer.MAX_VALUE;

	private int shortestSolution = MAX_VALUE;

	private char[][] maze = null;

	public char[][] getMaze() {
		return maze;
	}

	public void setMaze(char[][] maze) {
		this.maze = maze;
	}

	private char[][] outputMaze = null;

	public void startFinding() {
		// find start point 'A'
		int startX = 0;
		int startY = 0;
		for (int x = 0; x < maze.length; x++) {
			for (int y = 0; y < maze[0].length; y++) {
				if (maze[x][y] == 'A') {
					startX = x;
					startY = y;
					break;
				}
				if (startX != 0) // found 'A', no need to continue
					break;
			}
		}

		shortestSolution = MAX_VALUE;

		step(startX, startY, 0);
		outputMaze[startX][startY] = 'A';
	}

	// recursion, not a good solution, but it is straightforward for now
	private int step(int x, int y, int count) {

		// found destination point
		if (maze[x][y] == 'B') {
			shortestSolution = count;
			this.cloneMaze();
			return count;
		}

		// it is a wall or on the path
		if (maze[x][y] == '#' || maze[x][y] == '@') {
			return MAX_VALUE;
		}
		// will be great than min path, cannot be the shortest path
		if (count == shortestSolution) {
			return MAX_VALUE;
		}

		// mark this point as part of the path
		maze[x][y] = '@';
		int result = MAX_VALUE;

		int newResult = MAX_VALUE;

		// go Right
		if (y + 1 < this.maze[0].length) { // boundary check
			newResult = step(x, y + 1, count + 1);
			if (newResult < result) {
				result = newResult;
			}
		}

		// go Up
		if (x - 1 > -1) { // boundary check
			newResult = step(x - 1, y, count + 1);
			if (newResult < result) {
				result = newResult;
			}
		}

		// go Left
		if (y - 1 > -1) { // boundary check
			newResult = step(x, y - 1, count + 1);
			if (newResult < result) {
				result = newResult;
			}
		}

		// go Down
		if (x + 1 < this.maze.length) { // boundary check
			newResult = step(x + 1, y, count + 1);
			if (newResult < result) {
				result = newResult;
			}
		}

		maze[x][y] = '.';

		if (result < MAX_VALUE) {
			return result;
		}

		// this point can't be part of the route
		return MAX_VALUE;
	}

	private void cloneMaze() {
		outputMaze = new char[this.maze.length][this.maze[0].length];
		for (int x = 0; x < this.maze.length; x++) {
			for (int y = 0; y < this.maze[0].length; y++) {
				outputMaze[x][y] = maze[x][y];
			}
		}
	}

	public int countSteps() {
		return this.shortestSolution;
	}

	public String printRoute() {
		if (outputMaze == null) {
			return "No solution found";
		}

		StringBuilder sb = new StringBuilder();
		for (int x = 0; x < this.maze.length; x++) {
			for (int y = 0; y < this.maze[0].length; y++) {
				sb.append(outputMaze[x][y]);
			}
			sb.append("\n");
		}

		return sb.toString();

	}

}
