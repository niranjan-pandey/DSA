import java.util.*;
public class DSU {

    /*
    DSU (Disjoint Set Union) is a data structure used to efficiently maintain and merge groups of connected elements
    and check whether two elements belong to the same group.
    */

    /*
    TIME AND SPACE COMPLEXITY
    TC = O(α(n)) alpha(n)  DSU operations are O(α(n)) amortized, which is practically O(1).
    SC = O(n)
     */

    /*
    DON'T USE BOTH AT A TIME (unionByRank & unionBySize)
    IF YOU DON'T WANT TOTAL COUNT OF NODES EVERY PARENT HAVE THEN USED - Union by Rank
    IF YOU WANT COUNT OF TOTAL NODES EVERY PARENT HAVE THEN USED - Union by Size
     */

    //parent[i] stores the parent of node i. If parent[i] == i, then i is the root of its set.
    //rank[i] represents the height of the tree when i is the root.
    //size[i] represents the number of nodes in the set whose root is i.
    private int[] parent;
    private int[] rank;
    private int[] size;

    //Constructor
    public DSU(int n) {
        parent = new int[n];
        rank = new int[n];
        size = new int[n];

        //Initially, every node is its own parent and rank of every node is 0
        //and size of every node is 1 because every node is parent of itself initially
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
            size[i] = 1;
        }
    }

    //=========================================================
    //FIND WITH PATH COMPRESSION - During find(node), make every node on the path point directly to the root, making future find() operations faster.
    //=========================================================
    public int find(int node) {

        //If node is its own parent, node is the root.
        if(parent[node] == node) {
            return node;
        }
        // Path compression
        return parent[node] = find(parent[node]);
    }

    //=========================================================
    //UNION BY RANK - Attach the tree with the smaller rank (height) under the root of the tree with the larger rank.
    //                Putting smaller rank tree under larger rank tree will not increate the overall tree height
    //                because smaller tree directly point to larger tree at its height is already smaller so it will be not increase the overall height
    //                But putting (combining) larger tree under smaller tree will increase the height
    //=========================================================
    public void unionByRank(int u, int v) {

        //Find roots of both nodes.
        int rootU = find(u);
        int rootV = find(v);

        //Already in the same set.
        if (rootU == rootV) {
            return;
        }

        //Attach smaller-rank tree under larger-rank tree.
        if (rank[rootU] < rank[rootV]) {
            parent[rootU] = rootV;
        } else if (rank[rootU] > rank[rootV]) {
            parent[rootV] = rootU;
        } else {
            //Both trees have the same rank.
            //Make rootU the parent of rootV.
            parent[rootV] = rootU;

            //Height increases by 1.
            rank[rootU]++;
        }
    }

    //=========================================================
    //UNION BY SIZE - Logic is similar to unionByRank
    //=========================================================
    public void unionBySize(int u, int v) {

        //Find roots of both nodes.
        int rootU = find(u);
        int rootV = find(v);

        //Already in the same set.
        if (rootU == rootV) {
            return;
        }

        //Attach smaller set under larger set
        if (size[rootU] < size[rootV]) {
            parent[rootU] = rootV;
            //Update size of new root because our goal is to maintain the total node count
            size[rootV] += size[rootU];
        } else {
            parent[rootV] = rootU;
            //Update size of new root because our goal is to maintain the total node count
            size[rootU] += size[rootV];
        }
    }

    //=========================================================
    //CHECK CONNECTIVITY (Returns true if u and v belong to the same set.)
    //=========================================================
    public boolean connected(int u, int v) {
        return find(u) == find(v);
    }

    //=========================================================
    //MAIN
    //=========================================================
    public static void main(String[] args) {

        //Number of nodes
        int n = 7;

        /*
             Nodes: 0   1   2   3   4   5   6
             Initially: {0} {1} {2} {3} {4} {5} {6}
        */

        //=====================================================
        //UNION BY RANK
        //=====================================================

        System.out.println("===== UNION BY RANK =====");
        DSU dsu = new DSU(n);
        dsu.unionByRank(0, 1);
        dsu.unionByRank(1, 2);
        dsu.unionByRank(3, 4);
        dsu.unionByRank(4, 5);

        //Sets become: {0, 1, 2} {3, 4, 5} {6}
        System.out.println("Are 0 and 2 connected? " + dsu.connected(0, 2)); //true
        System.out.println("Are 0 and 3 connected? " + dsu.connected(0, 3)); //false
        System.out.println("Are 3 and 5 connected? " + dsu.connected(3, 5)); //true
        System.out.println("Are 5 and 6 connected? " + dsu.connected(5, 6)); //false

        //=====================================================
        //NEW DSU FOR UNION BY SIZE
        //=====================================================

        System.out.println("===== UNION BY SIZE =====");
        DSU dsuSize = new DSU(n);
        dsuSize.unionBySize(0, 1);
        dsuSize.unionBySize(1, 2);
        dsuSize.unionBySize(3, 4);
        dsuSize.unionBySize(4, 5);

        //Sets: {0, 1, 2} {3, 4, 5} {6}
        System.out.println("Are 0 and 2 connected? " + dsuSize.connected(0, 2)); //true
        System.out.println("Are 0 and 3 connected? " + dsuSize.connected(0, 3)); //false
        System.out.println("Are 3 and 5 connected? " + dsuSize.connected(3, 5)); //true
        System.out.println("Are 5 and 6 connected? " + dsuSize.connected(5, 6)); //false
    }
}