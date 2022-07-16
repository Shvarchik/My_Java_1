// волновой алгоритм
import java.util.*;

public class waveAlgorytm {

    static int count = 0;                       

    static boolean wave(int[][] map, int x, int y, int a, int b) {

        int n = map.length;
        map[x][y] = 1;                                                          // ставим 1 в отправную точку
        Deque<int[]> dq = new ArrayDeque<>();                                   // объявляем очередь для пар координат
        dq.addFirst(new int[] { x, y });                                        // координаты отправной точки добавляем в начало очереди
        int[] cell = new int[2];                                                // объявление массива для координат
        while (dq.size() != 0 && map[a][b] == 0) {                              // пока очередь не пуста до цели не дошли (в целевой точке 0)
            cell = dq.pollFirst();                                              // извлекаем пару координат (исходная позиция) из головы очереди
            int row = cell[0];
            int col = cell[1];
            for (int i = -1; i <= 1; i++) {                                      // во все соседние пустые (==0) позиции
                for (int j = -1; j <= 1; j++) {
                    x = row + i;
                    y = col + j;
                    if (x >= 0 && x < n && y >= 0 && y < map[x].length && map[x][y] == 0) {
                        map[x][y] = map[row][col]+1;                             // ставим значение на 1 больше, чемв исходной
                        dq.addLast(new int[] { x, y });                          // и добавляем их координаты в хвост очереди
                    }
                }
            }
        }
        if (dq.isEmpty())                                                        // если очередь пуста, возвращаем ложь, решения нет   
            return false;
        else
            return true;                                                
    }

    static void restorePath(int[][] map, int a, int b, String path) {           // восстановление пути, печать всех вариантов кратчайшего пути
                                                                                // (можно было бы не печатать, а заполнять ArrayList <String>
        path = path + "(" + a + "," + b + ")";
        if (map[a][b] == 1) {
            System.out.print(++count + ". ");
            System.out.println(path);
            return;
        } else
            path = path + "->";
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int x = a + i;
                int y = b + j;
                if (x >= 0 && x < map.length && y >= 0 && y < map[x].length && map[x][y] == map[a][b] - 1) {
                    restorePath(map, x, y, path);
                }
            }
        }
    }

    static void printMap(int[][] map) {                                         // печать поля

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.printf("%4d", map[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] map = new int[10][10];
        int startX = 1; // начальная точка
        int startY = 6;
        int finishX = 5; // конечная точка
        int finishY = 7;
        if (wave(map, startX, startY, finishX, finishY)) {
            printMap(map);
            System.out.println("Варианты пути:");
            String path = "";
            restorePath(map, finishX, finishY, path);
        } else
            System.out.println("нет решения");
    }
}