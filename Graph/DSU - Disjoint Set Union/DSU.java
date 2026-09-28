//Union Find Disjoin Set Example Code

public class DSU  {
	public static void main(String[] args) {
		
	}

	//Brute Force
	private static int find(int node, int[] parent) {

		if(node == parent[node])
			return node;

		return find(parent[node], parent)
	}

	//Brute Force
	private static union(int x, int y, int[] parent) {

		int x_parent = find(x, parent);
		int y_parent = find(y, parent);

		if(x_parent != y_parent) {
			parent[x_parent] = y_parent;
		}
	}

	//Optimal
	private static int unionbyrankFind(int node, int[] parent) {

		if(node == parent[node])
			return node;

		//Update Parent of all nodes with main parent after recursion call [path compression]
		return parent[node] = unionbyrankFind(parent[node], parent);
	}

	//Optimal
	private static int unionbyRank(int x, int y, int[] parent, int[] rank) {

		int x_parent = unionbyrankFind(x, parent);
		int y_parent = unionbyrankFind(y, parent);

		if(x_parent == y_parent)
			return;

		if(rank[x_parent] > rank[y_parent]) {
			parent[y_parent] = x_parent;
		}
		else if(rank[x_parent] < rank[y_parent]) {
			parent[x_parent] = y_parent;
		} 
		else {
			parent[x_parent] = y_parent;
			rank[y_parent] += 1;
		}
	}
}