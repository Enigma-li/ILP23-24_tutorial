import java.util.*;

// Helper class to record the Puzzle node
class PuzzleNode {

    int[][] state;  // puzzle status
    int g;          // Cost from the start node to this node
    int h;          // Heuristic cost (estimated cost to reach the goal)
    int f;          // Total cost (f = g + h)
    PuzzleNode parent;  // come from information

    public PuzzleNode(int[][] state) {
        this.state = state;
        g = 0;
        h = 0;
        f = 0;
        parent = null;
    }
}

public class FifteenPuzzleSolver {

    // initialize goal status
    private static final int[][] GOAL_STATE = {
        {1, 2, 3, 4},
        {5, 6, 7, 8},
        {9, 10, 11, 12},
        {13, 14, 15, 0}
    };

    // A* search
    public static List<int[][]> solvePuzzle(int[][] startState) {
        
        // Create priority queue ordered by f value
        PriorityQueue<PuzzleNode> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.f));
        
        // visted set
        Set<String> closedSet = new HashSet<>();

        // Create the start node
        PuzzleNode startNode = new PuzzleNode(startState);
        startNode.g = 0;
        startNode.h = calculateHeuristic(startState);
        startNode.f = startNode.g + startNode.h;

        // start from the first node
        openSet.add(startNode);

        while (!openSet.isEmpty()) {

            // get the node with smallest cost
            PuzzleNode currentNode = openSet.poll();

            // check: early exit
            if (isGoalState(currentNode.state)) {
                // Reconstruct and return the solution path
                List<int[][]> solutionPath = new ArrayList<>();
                while (currentNode != null) {
                    solutionPath.add(currentNode.state);
                    currentNode = currentNode.parent;
                }
                Collections.reverse(solutionPath);
                return solutionPath;
            }

            // record visited
            closedSet.add(Arrays.deepToString(currentNode.state));

            // find the empty position of the current status
            int[] emptyPosition = findEmptyPosition(currentNode.state);
            int emptyRow = emptyPosition[0];
            int emptyCol = emptyPosition[1];

            // find the neighbors: next status after moving tile to the empty tile
            for (int[] move : getValidMoves(emptyRow, emptyCol)) {
                int newRow = move[0];
                int newCol = move[1];
                
                // get the new status matrix

                // Note: shallow copy would copy reference instead of the value 
                // int[][] newState = Arrays.copyOf(currentNode.state, currentNode.state.length);

                int[][] newState = deepCopy(currentNode.state);
                newState[emptyRow][emptyCol] = currentNode.state[newRow][newCol];
                newState[newRow][newCol] = 0;

                // put into frontier
                if (!closedSet.contains(Arrays.deepToString(newState))) {
                    PuzzleNode newNode = new PuzzleNode(newState);
                    newNode.g = currentNode.g + 1;
                    newNode.h = calculateHeuristic(newNode.state);
                    newNode.f = newNode.g + newNode.h;
                    newNode.parent = currentNode;
                    openSet.add(newNode);
                }
            }
        }

        return null; // No solution found
    }

    // Helper class for value copying
    public static int[][] deepCopy(int[][] array){
        int[][] res_array = new int[array.length][];
        for(int i=0; i<array.length; i++){
            res_array[i] = new int[array[i].length];
            for(int j=0; j<array[i].length; j++){
                res_array[i][j] = array[i][j];
            }
        }
        return res_array;
    }

    // check goal
    public static boolean isGoalState(int[][] state) {
        return Arrays.deepEquals(state, GOAL_STATE);
    }

    public static int calculateHeuristic(int[][] state) {
        // This heuristic calculates the number of misplaced tiles
        int h = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] != GOAL_STATE[i][j] && state[i][j] != 0) {
                    h++;
                }
            }
        }
        return h;
    }

    public static int[] findEmptyPosition(int[][] state) {
        int[] position = new int[2];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] == 0) {
                    position[0] = i;
                    position[1] = j;
                    return position;
                }
            }
        }
        return position;
    }

    // movement
    public static List<int[]> getValidMoves(int row, int col) {
        List<int[]> moves = new ArrayList<>();
        if (row > 0) {
            moves.add(new int[] {row - 1, col});
        }
        if (row < 3) {
            moves.add(new int[] {row + 1, col});
        }
        if (col > 0) {
            moves.add(new int[] {row, col - 1});
        }
        if (col < 3) {
            moves.add(new int[] {row, col + 1});
        }
        return moves;
    }

    // print
    public static void printState(int[][] state) {
        for (int[] row : state) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println();
    }

    // main function
    public static void main(String[] args) {
        
        // initialize start state
        
        // int[][] startState = {
        //     {1, 2, 3, 4},
        //     {5, 6, 7, 8},
        //     {9, 10, 11, 12},
        //     {13, 14, 0, 15}
        // };

        int[][] startState = {
            {5, 1, 2, 4},
            {9, 6, 3, 8},
            {13, 15, 10, 11},
            {14, 0, 7, 12}
        };

        // search
        List<int[][]> solution = solvePuzzle(startState);
        if (solution != null) {
            System.out.println("Solution found!");
            int step_cnt = 0;
            for (int[][] state : solution) {
                System.out.println("Step: " + ++step_cnt);
                printState(state);
                
            }
        } else {
            System.out.println("No solution found.");
        }
    }

}
