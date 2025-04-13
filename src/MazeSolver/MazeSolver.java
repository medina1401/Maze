import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MazeSolver {

    // Constants to represent wall and path
    private static final char WALL = '#';
    private static final char PATH = ' ';
    private static final char VISITED = '.';
    private static final char EXIT = 'E';
    private static final char START = 'S';

    private int rows, cols;
    private char[][] maze;
    private int[] start, exit;

    // Directions for moving in the maze (up, down, left, right)
    private static final int[] dirX = {-1, 1, 0, 0};  // Row offsets
    private static final int[] dirY = {0, 0, -1, 1};  // Column offsets

    public MazeSolver(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.maze = new char[rows][cols];
        this.start = new int[]{1, 1};  // Top-left corner as the starting point
        this.exit = new int[]{rows - 2, cols - 2};  // Bottom-right corner as the exit
        generateMaze();
    }

    // Method to generate the maze using recursive backtracking
    private void generateMaze() {
        // Initialize the maze with walls
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = WALL;
            }
        }

        // Make the starting point and exit passable
        maze[start[0]][start[1]] = START;
        maze[exit[0]][exit[1]] = EXIT;

        // Start maze generation from (1, 1)
        recursiveBacktracking(1, 1);

        // Make sure the exit is passable
        maze[exit[0]][exit[1]] = EXIT;
    }

    // Recursive backtracking to generate the maze
    private void recursiveBacktracking(int x, int y) {
        // List of possible directions (up, down, left, right)
        ArrayList<int[]> directions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            directions.add(new int[]{dirX[i], dirY[i]});
        }

        // Shuffle the directions randomly to ensure random maze generation
        Collections.shuffle(directions);

        // Explore all possible directions
        for (int[] direction : directions) {
            int newX = x + direction[0] * 2;
            int newY = y + direction[1] * 2;

            if (isValidCell(newX, newY) && maze[newX][newY] == WALL) {
                // Mark the new cell as passable
                maze[newX][newY] = PATH;

                // Also mark the cell between the current cell and the new cell as passable
                maze[x + direction[0]][y + direction[1]] = PATH;

                // Recursively visit the new cell
                recursiveBacktracking(newX, newY);
            }
        }
    }

    // Check if a cell is within bounds and not a wall
    private boolean isValidCell(int x, int y) {
        return x > 0 && x < rows - 1 && y > 0 && y < cols - 1;
    }

    // Print the maze
    public void printMaze() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }

    // Method to find a path from start to exit using recursion (DFS)
    public boolean findPath(int x, int y, boolean[][] visited) {
        // Base case: if we reach the exit
        if (x == exit[0] && y == exit[1]) {
            maze[x][y] = VISITED;  // Mark the exit on the path
            return true;
        }

        // If the cell is invalid, return false
        if (!isValidCell(x, y) || maze[x][y] == WALL || visited[x][y]) {
            return false;
        }

        // Mark this cell as visited
        visited[x][y] = true;

        // Try moving in all four directions (up, down, left, right)
        for (int i = 0; i < 4; i++) {
            int newX = x + dirX[i];
            int newY = y + dirY[i];

            // If a valid path is found, mark this cell and return true
            if (findPath(newX, newY, visited)) {
                maze[x][y] = VISITED;
                return true;
            }
        }

        return false;  // No path found
    }

    public static void main(String[] args) {
        // Read maze dimensions from user input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of rows: ");
        int rows = scanner.nextInt();
        System.out.print("Enter number of columns: ");
        int cols = scanner.nextInt();

        // Ensure maze is at least 10x10
        if (rows < 10 || cols < 10) {
            System.out.println("Maze size must be at least 10x10.");
            return;
        }

        // Create and generate the maze
        MazeSolver mazeSolver = new MazeSolver(rows, cols);

        System.out.println("Generated Maze:");
        mazeSolver.printMaze();

        // Find a path from start to exit
        boolean[][] visited = new boolean[rows][cols];
        if (mazeSolver.findPath(mazeSolver.start[0], mazeSolver.start[1], visited)) {
            System.out.println("\nSolved Maze with Path:");
            mazeSolver.printMaze();
        } else {
            System.out.println("No path found.");
        }
    }
}

