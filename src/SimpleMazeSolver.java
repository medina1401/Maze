import java.util.*;

public class SimpleMazeSolver {

    // Символы для отображения лабиринта
    static final char WALL = '#';
    static final char PATH = ' ';
    static final char START = 'S';
    static final char EXIT = 'E';
    static final char VISITED = '.';

    // Размеры лабиринта (нечётные значения для корректной генерации)
    static final int ROWS = 11;
    static final int COLS = 21;

    static char[][] maze = new char[ROWS][COLS];
    static boolean[][] visited = new boolean[ROWS][COLS];

    // Заполнение лабиринта стенами
    static void createMaze() {
        for (int i = 0; i < ROWS; i++) {
            Arrays.fill(maze[i], WALL);
        }
    }

    // Генерация лабиринта рекурсивным методом
    static void generateMaze(int x, int y) {
        maze[x][y] = PATH;

        int[][] directions = { {0, 2}, {2, 0}, {0, -2}, {-2, 0} };
        List<int[]> dirList = Arrays.asList(directions);
        Collections.shuffle(dirList);

        for (int[] dir : dirList) {
            int dx = dir[0], dy = dir[1];
            int nx = x + dx, ny = y + dy;

            if (nx > 0 && ny > 0 && nx < ROWS - 1 && ny < COLS - 1 && maze[nx][ny] == WALL) {
                maze[x + dx / 2][y + dy / 2] = PATH;
                generateMaze(nx, ny);
            }
        }
    }

    // Поиск пути от S к E
    static boolean solveMaze(int x, int y) {
        if (x < 0 || y < 0 || x >= ROWS || y >= COLS) return false;
        if (maze[x][y] == WALL || visited[x][y]) return false;
        if (maze[x][y] == EXIT) return true;

        visited[x][y] = true;

        // Попытка движения во всех 4 направлениях
        if (solveMaze(x + 1, y) || solveMaze(x - 1, y) || solveMaze(x, y + 1) || solveMaze(x, y - 1)) {
            if (maze[x][y] != START) maze[x][y] = VISITED;
            return true;
        }

        return false;
    }

    // Вывод лабиринта в консоль
    static void printMaze() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // 1. Создание лабиринта
        createMaze();

        // 2. Генерация случайного лабиринта
        generateMaze(1, 1);

        // 3. Установка стартовой и конечной точки
        maze[1][1] = START;
        maze[ROWS - 2][COLS - 2] = EXIT;

        // 4. Печать сгенерированного лабиринта
        System.out.println("Generated Maze:");
        printMaze();

        // 5. Решение лабиринта и вывод результата
        System.out.println("\nSolved Maze:");
        solveMaze(1, 1);
        printMaze();
    }
}
