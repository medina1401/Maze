import java.util.Arrays;

public class SimpleMazeSolver {

    static final char WALL = '#';
    static final char PATH = ' ';
    static final char START = 'S';
    static final char EXIT = 'E';
    static final char VISITED = '.';

    static final int ROWS = 11;
    static final int COLS = 21;

    static char[][] maze = new char[ROWS][COLS];
    static boolean[][] visited = new boolean[ROWS][COLS];

    static void createMaze() {
        for (int i = 0; i < ROWS; i++) {
            Arrays.fill(maze[i], WALL);
        }
    }

    public static void main(String[] args) {
        createMaze();
    }
}

