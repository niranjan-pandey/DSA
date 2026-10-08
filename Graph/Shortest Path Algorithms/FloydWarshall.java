import java.util.*;

public class FloydWarshall {

    /*
    FLOYD-WARSHALL ALGORITHM

    Floyd-Warshall is a Dynamic Programming based algorithm used to find the
    shortest distance between EVERY PAIR of vertices in a weighted graph.

    It works for:
    1- Directed graphs
    2- Undirected graphs
    3- Graphs with positive edge weights
    4- Graphs with negative edge weights

    IMPORTANT:
    Floyd-Warshall does NOT work correctly if the graph contains a negative cycle
    when we are trying to find meaningful shortest paths.

    The main idea is:
    For every intermediate node k (via), check whether going from i -> k -> j
    gives a shorter path than the current i -> j path.

    Formula ->> dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j])
    */


    /*
    TIME AND SPACE COMPLEXITY
    TC = O(V^3)
    SC = O(V^2)
    */


    /*
    IMPORTANT INITIALIZATION RULES

    1- Distance from a node to itself is always 0.
       dist[i][i] = 0

    2- If there is a direct edge from i -> j, store its weight.
       dist[i][j] = weight

    3- If there is NO direct edge from i -> j, store INF.
       dist[i][j] = INF

    4- INF should be a sufficiently large value.

    5- Before doing dist[i][k] + dist[k][j] make sure both distances are not INF.
    */

    //INF represents that there is currently NO PATH between two nodes.
    private static final int INF = Integer.MAX_VALUE;

    //=========================================================
    // FLOYD-WARSHALL ALGORITHM - Find the shortest distance between every pair of nodes.
    //=========================================================
    public static void floydWarshall(int[][] dist) {

        int n = dist.length;

        /*
        k = Intermediate node
        We try every node k as an intermediate node between source i and destination j.

        IMPORTANT: k loop must be the OUTERMOST loop.
        */
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) { //Source
                for (int j = 0; j < n; j++) { //Destination

                    //If either i -> k or k -> j does not exist, then we cannot use k as an intermediate node.
                    if (dist[i][k] == INF || dist[k][j] == INF)
                        continue;

                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
    }


    //=========================================================
    // CHECK FOR NEGATIVE CYCLE
    // If dist[i][i] becomes negative for any node i,
    // then the graph contains a negative cycle.
    //=========================================================

    public static boolean hasNegativeCycle(int[][] dist) {

        int n = dist.length;
        for (int i = 0; i < n; i++) {
            /*
            Normally distance from a node to itself is 0.

            If it becomes negative, it means we can start from
            the node, travel through a cycle and come back to
            the same node with a negative total cost.

            Therefore, a negative cycle exists.
            */
            if (dist[i][i] < 0) {
                return true;
            }
        }
        return false;
    }


    //=========================================================
    // PRINT DISTANCE MATRIX
    //=========================================================
    public static void printMatrix(int[][] dist) {
        int n = dist.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dist[i][j] == INF) {
                    System.out.print("INF ");
                } else {
                    System.out.print(dist[i][j] + " ");
                }
            }
            System.out.println();
        }
    }


    //=========================================================
    // MAIN
    //=========================================================
    public static void main(String[] args) {

        /*
        Example Graph:

                 5
            0 --------> 1
            |           |
           10           3
            |           |
            v           v
            3 <---------2
                 1

        Edges:

        0 -> 1 = 5
        0 -> 3 = 10
        1 -> 2 = 3
        2 -> 3 = 1

        Initially:

             0    1    2    3
        0    0    5   INF   10
        1   INF   0    3    INF
        2   INF  INF   0     1
        3   INF  INF  INF    0
        */


        //Number of nodes
        int n = 4;


        /*
        Initialize the distance matrix.

        INF means there is NO DIRECT EDGE between the nodes.

        dist[i][i] = 0 because the distance from a node
        to itself is always 0.
        */
        int[][] dist = {

                {0,   5,   INF, 10},
                {INF, 0,   3,   INF},
                {INF, INF, 0,   1},
                {INF, INF, INF, 0}

        };

        //=====================================================
        // INITIAL DISTANCE MATRIX
        //=====================================================

        System.out.println("===== INITIAL DISTANCE MATRIX =====");

        printMatrix(dist);


        //=====================================================
        // FLOYD-WARSHALL
        //=====================================================

        floydWarshall(dist);


        //=====================================================
        // SHORTEST DISTANCE MATRIX
        //=====================================================

        System.out.println();
        System.out.println("===== SHORTEST DISTANCE MATRIX =====");

        printMatrix(dist);


        //=====================================================
        // CHECK FOR NEGATIVE CYCLE
        //=====================================================

        System.out.println();

        if (hasNegativeCycle(dist)) {
            System.out.println("Negative cycle exists");
        } else {
            System.out.println("No negative cycle exists");
        }
    }
}