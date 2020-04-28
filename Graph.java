import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class for an undirected graph. Integers are used to identify nodes 
 * (i.e. the nodes are labeled 0 through n-1).
 */
public class Graph {
    private List<ArrayList<Integer>> adjacencyList;
    private int size;
    
    /**
     * Initializes a graph of size n. All valid nodes in this graph thus have integer
     * between 0 to n (including 0).
     * @param n the number of nodes in the graph
     * @throws IllegalArgumentException if n is negative
     */
    public Graph(int n) {
        // case where input is negative
        if (n < 0) {
            throw new IllegalArgumentException("Number of vertices cannot be negative");
        }
        
        // make the adjacency list of each edge empty
        this.adjacencyList = new ArrayList<ArrayList<Integer>>();
        this.size = 0;
        for (int i = 0; i < n; i++) {
            this.adjacencyList.add(new ArrayList<Integer>());
            this.size += 1;
        }
    }

    /**
     * Method for returning the number of nodes in the graph.
     * @return the number of nodes in the graph
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Method for determining if an edge exists between two nodes.
     * @param u a node
     * @param v a node
     * @return true if the edge u-v is in the graph
     * @throws IllegalArgumentException if a specified node does not exist
     */
    public boolean hasEdge(int u, int v) {
        // u not present in graph case
        if (u < 0 || u >= this.adjacencyList.size()) {
            throw new IllegalArgumentException("" + u + " not present in the graph");
        }
        
        // v not present in graph case
        if (v < 0 || v >= this.adjacencyList.size()) {
            throw new IllegalArgumentException("" + v + " not present in the graph");
        }
        
        boolean uvFound = false;
        
        for (Integer a : this.adjacencyList.get(u)) {
            if (a.equals(v)) {
                uvFound = true;
            }
        }
        
        return uvFound;
    }

    /**
     * Creates an edge between nodes u and v if it does not already exist.
     * @param u one node to connect
     * @param v the other node to connect
     * @return true if the graph changed as a result of this call, false otherwise (i.e. if
     *         the edge is already present)
     * @throws IllegalArgumentException if a specified node does not exist
     */
    public boolean addEdge(int u, int v) {
        // u not present in graph case
        if (u < 0 || u >= this.adjacencyList.size()) {
            throw new IllegalArgumentException("" + u + " not present in the graph");
        }
        
        // v not present in graph case
        if (v < 0 || v >= this.adjacencyList.size()) {
            throw new IllegalArgumentException("" + v + " not present in the graph");
        }
        
        boolean found = this.hasEdge(u, v);
        
        if (!found) {
            this.adjacencyList.get(u).add(new Integer(v));
            return true;
        } else {
            return false;
        }
        
    }

    /**
     * Returns the neighbors of the specified node.
     * @param v the node
     * @return all neighbors of the specified node or an empty set if there are no neighbors. If
     *         there is a self-loop on v, include v in the neighbor set.
     * @throws IllegalArgumentException if the specified node does not exist
     */
    public Set<Integer> getNeighbors(int v) {
        // v not present case
        if (v < 0 || v >= this.adjacencyList.size()) {
            throw new IllegalArgumentException("" + v + " not present in the graph");
        }
        
        Set<Integer> neighbors = new TreeSet<Integer>();
        for (Integer a : this.adjacencyList.get(v)) {
            neighbors.add(a);
        }
        return Collections.unmodifiableSet(neighbors);
    }
    
    public Map<Integer, Double> pageRank() {
        Map<Integer, Double> ranks = new HashMap<Integer, Double>();
        Map<Integer, Double> prevRanks = new HashMap<Integer, Double>();
        Map<Integer, Double> rankDiff = new HashMap<Integer, Double>();
        
        // initialize the rank values
        for (int i = 0; i < this.size; i++) {
            ranks.put(i, 1.0 / this.size);
            prevRanks.put(i, 1.0 / this.size);
        }
        
        // initialize the rank-difference values
        for (int i = 0; i < this.size; i++) {
            rankDiff.put(i, 0.0);
        }
        
        
        do {
            // compute the the change of each rank
            for (int i = 0; i < this.size; i++) {
                int numNeighbors = this.adjacencyList.get(i).size();
                for (int neighbor : this.adjacencyList.get(i)) {
                    double currDiff = rankDiff.get(neighbor);
                    rankDiff.put(neighbor, currDiff + ranks.get(i) / numNeighbors);
                }
            }
            
            // apply the change on each node's rank
            for (int i = 0; i < this.size; i++) {
                int numNeighbors = this.adjacencyList.get(i).size();
                prevRanks.put(i, ranks.get(i));
                if (numNeighbors != 0) {
                    ranks.put(i, rankDiff.get(i));
                } else {
                    ranks.put(i, ranks.get(i) + rankDiff.get(i));
                }
            }
            
            // reset the changes to zero
            for (int i = 0; i < this.size; i++) {
                rankDiff.put(i, 0.0);
            }
        } while (computeDiff(prevRanks, ranks) >= 0.00001);
        return ranks;
    }
    
    /**
     * Method for checking how much the pagerank values of each node have changed
     * @param prevRanks, the previous ranks of each node
     * @param ranks, the current ranks of each node
     * @return the average of the differences between the previous and current ranks
     */
    private double computeDiff(Map<Integer, Double> prevRanks, Map<Integer, Double> ranks) {
        double avg = 0.0;
        for (int i = 0; i < this.size; i++) {
            avg += Math.abs(ranks.get(i) - prevRanks.get(i)) / this.size;
        }
        return avg;
    }
}