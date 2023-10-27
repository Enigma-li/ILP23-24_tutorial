import java.util.*;

// Cell class to help record status of grid
//  Since we are using priotiry queue and hashset, implement some functions, e.g., hashCode, equals are needed
class Cell {

    int row, col;   // cell position
    int f, g, h;    // A* algorithm value parameters
    Cell parent;    // parent record: come from

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        parent = null;
        f = 0;
        g = 0;
        h = 0;
    }

    @Override
    public int hashCode(){
        return Objects.hash(row, col);
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }

        if(obj == null || getClass() != obj.getClass()){
            return false;
        }

        Cell other = (Cell)obj;
        return other.row == row && other.col == col;
    }

}

public class AStarMazeSolver {

    private static final int[][] DIRS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; // 4 possible movement directions
    //private static final int[][] DIRS = {{-1, -1}, {1, -1}, {-1, 1}, {1, 1}, {0, 1}, {0, -1}, {1, 0}, {-1, 0}}; // 8 possible movement directions


    // global defined variables for the search
    static PriorityQueue<Cell> openSet;     // frontier
    static HashSet<Cell> closedSet;         // visited
    static List<Cell> path;                 // resulting path

    // A* search algorith
    public static boolean findShortestPath(char[][] maze, Cell start, Cell goal) {
        
        // maze size
        int numRows = maze.length;
        int numCols = maze[0].length;

        // add start to the queue first
        openSet.add(start);

        // once there is element in the queue, then keep running
        while (!openSet.isEmpty()) {
            
            // get the cell with smallest cost
            Cell current = openSet.poll();

            // mark the cell to be visited
            closedSet.add(current);
            
            // find the goal: early exit
            if (current.row == goal.row && current.col == goal.col) {
                
                // Reconstruct the path: trace by find the parent cell
                path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = current.parent;
                }
                Collections.reverse(path);
                
                return true;
            }
            
            // search neighbors
            for (int[] dir : DIRS) {

                // neighor cell location
                int newRow = current.row + dir[0];
                int newCol = current.col + dir[1];

                if (newRow >= 0 && newRow < numRows && newCol >= 0 && newCol < numCols && maze[newRow][newCol] != '#' 
                    && !closedSet.contains(new Cell(newRow, newCol))) { 
                    
                    // new movement is always 1 cost even it is diagonal 
                    int tentativeG = current.g + 1;
                    
                    // find the cell if it is in the frontier but not visited to see if cost updating is needed
                    Cell existing_neighbor = findNeighbor(newRow, newCol);

                    if(existing_neighbor != null){
                        // Check if this path is better than any previously generated path to the neighbor
                        if(tentativeG < existing_neighbor.g){
                            // update cost, parent information
                            existing_neighbor.parent = current;
                            existing_neighbor.g = tentativeG;
                            existing_neighbor.h = heuristic(existing_neighbor, goal);
                            existing_neighbor.f = existing_neighbor.g + existing_neighbor.h;
                        }
                    }
                    else{   
                        // or directly add this cell to the frontier
                        Cell neighbor = new Cell(newRow, newCol);
                        neighbor.parent = current;
                        neighbor.g = tentativeG;
                        neighbor.h = heuristic(neighbor, goal);
                        neighbor.f = neighbor.g + neighbor.h;

                        openSet.add(neighbor);
                    }
                }
            }
        }

        // No path found
        return false;
    }

    // Helper function to find and return the neighbor cell
    //  Java priority queue cannot return a specific element
    public static Cell findNeighbor(int row, int col){
        if(openSet.isEmpty()){
            return null;
        }

        Iterator<Cell> iterator = openSet.iterator();

        Cell find = null;
        while (iterator.hasNext()) {
            Cell next = iterator.next();
            if(next.row == row && next.col == col){
                find = next;
                break;
            }
        }
        return find;
    }

    // Helper function to check if a cell is part of the path
    public static boolean isInPath(int row, int col) {
        for (Cell cell : path) {
            if (cell.row == row && cell.col == col) {
                return true;
            }
        }
        return false;
    }
    
    public static int heuristic(Cell a, Cell b) {
        // A simple heuristic: Manhattan distance
        return Math.abs(a.row - b.row) + Math.abs(a.col - b.col);
    }

    // main function
    public static void main(String[] args) {
        char[][] maze = {
            "############################################################".toCharArray(),
            "#..........................................................#".toCharArray(),
            "#.............................#............................#".toCharArray(),
            "#.............................#............................#".toCharArray(),
            "#.............................#............................#".toCharArray(),
            "#.......S.....................#............................#".toCharArray(),
            "#.............................#............................#".toCharArray(),
            "#.............................#............................#".toCharArray(),
            "#.............................#............................#".toCharArray(),
            "#.............................#............................#".toCharArray(),
            "#.............................#............................#".toCharArray(),
            "#.............................#............................#".toCharArray(),
            "#######.#######################################............#".toCharArray(),
            "#....#........#............................................#".toCharArray(),
            "#....#........#............................................#".toCharArray(),
            "#....##########............................................#".toCharArray(),
            "#..........................................................#".toCharArray(),
            "#..........................................................#".toCharArray(),
            "#..........................................................#".toCharArray(),
            "#..........................................................#".toCharArray(),
            "#..........................................................#".toCharArray(),
            "#...............................##############.............#".toCharArray(),
            "#...............................#........G...#.............#".toCharArray(),
            "#...............................#............#.............#".toCharArray(),
            "#...............................#............#.............#".toCharArray(),
            "#...............................#............#.............#".toCharArray(),
            "#...............................###########..#.............#".toCharArray(),
            "#..........................................................#".toCharArray(),
            "#..........................................................#".toCharArray(),
            "############################################################".toCharArray()
        };

        // Find the start and goal positions
        Cell start = null, goal = null;
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                if (maze[row][col] == 'S') {
                    start = new Cell(row, col);
                } else if (maze[row][col] == 'G') {
                    goal = new Cell(row, col);
                }
            }
        }

        // initialize the global variable
        openSet = new PriorityQueue<>(Comparator.comparingInt(c -> c.f));
        closedSet = new HashSet<>();

        // Run A* algorithm to find the shortest path
       if(findShortestPath(maze, start, goal)){

            System.out.println("Path count: " + path.size());
            System.out.println("Searched squares count: " + closedSet.size());

            // Mark the explored grid with blank spaces
            for (int row = 0; row < maze.length; row++) {
                for (int col = 0; col < maze[0].length; col++) {
                    if (maze[row][col] != '#' && maze[row][col] != 'S' && maze[row][col] != 'G' && !isInPath(row, col) && closedSet.contains(new Cell(row, col))) {
                        maze[row][col] = ' ';
                    }
                }
            }

            // Print the maze with '*' to indicate the path
            for (Cell cell : path) {
                if(maze[cell.row][cell.col] != 'S' && maze[cell.row][cell.col] != 'G'){
                    maze[cell.row][cell.col] = '*';
                }
            }

            for (char[] row : maze) {
                System.out.println(new String(row));
            }
        }
        else{
            System.out.println("No path found!");
        }
    }
}
