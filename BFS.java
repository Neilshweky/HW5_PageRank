import java.util.ArrayDeque;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for running Breadth-First Search on a given graph as well as computing the shortest path 
 * between two nodes of that graph. The paths are represented as lists of integers corresponding 
 * to nodes.
 */
public final class BFS {
    
    /**
     * An implementation of the BFS algorithm
     * @param g, the graph to run BFS on
     * @param src, the start/source node
     * @param result, the list nodes in the order visited
     * @param marked, an array showing which nodes have been marked/discovered
     * @param parent, an array showing the parent of each node
     * @param frontiers, an array where each index contains the frontier that that index/node is in
     * @return the list of nodes visited in the BFS order
     * @throws IllegalArgumentException if src is not in the graph
     * @throws IllegalArgumentException if the specified graph is null
     */
    public static List<Integer> bfsImpl(Graph g, int src, List<Integer> result, 
            boolean[] marked, Integer[] parent, int[] frontiers) {
        // null graph case        
        if (g == null) {
            throw new IllegalArgumentException("Graph can't be null");
        }
        
        // source not present in graph case
        if (src > g.getSize() - 1 || src < 0) {
            throw new IllegalArgumentException("" + src + " is not present in the graph");
        }
        
        // BFS algorithm
        ArrayDeque<Integer> q = new ArrayDeque<Integer>();
        marked[src] = true;
        q.addLast(src);
        parent[src] = src;
        while (q.size() != 0) {
            int v = q.pollFirst();
            result.add(v);
            for (int w : g.getNeighbors(v)) {
                if (!marked[w]) {
                    frontiers[w] = frontiers[v] + 1;
                    parent[w] = v;
                    marked[w] = true;
                    q.addLast(w);
                }
            }
        }
        
        // check if all nodes have been marked/discovered
        for (int i = 0; i < marked.length; i++) {
            if (!marked[i]) {
                return bfsImpl(g, i, result, marked, parent, frontiers);
            }
        }
        return result;
    }
    /**
     * A method for checking whether there exists a path between two given nodes
     * @param g, the graph to run BFS on
     * @param src, the start/source node
     * @param tgt, the target node
     * @param parent, an array showing the parent of each node
     * @return true if there exists a path and false otherwise
     * @throws IllegalArgumentException if src or the tgt is not in the graph
     * @throws IllegalArgumentException if the specified graph is null
     */
    public static boolean bfsPath(Graph g, int src, int tgt, Integer[] parent) {
        // null graph case        
        if (g == null) {
            throw new IllegalArgumentException("Graph can't be null");
        }
        
        // source not present in graph case
        if (src > g.getSize() - 1 || src < 0) {
            throw new IllegalArgumentException("" + src + " is not present in the graph");
        }
        
        // target not present in graph case
        if (tgt > g.getSize() - 1 || tgt < 0) {
            throw new IllegalArgumentException("" + tgt + " is not present in the graph");
        }
        
        // BFS algorithm
        ArrayDeque<Integer> q = new ArrayDeque<Integer>();
        boolean[] marked = new boolean[g.getSize()];
        marked[src] = true;
        
        // variable used to check if the target has been found
        boolean found = false;
        
        q.addLast(src);
        parent[src] = src;
        while (q.size() != 0) {
            int v = q.pollFirst();
            for (int w : g.getNeighbors(v)) {
                if (!marked[w]) {
                    parent[w] = v;
                    marked[w] = true;
                    q.addLast(w);
                    if (w == tgt) {
                        found = true;
                        break;
                    }
                }
            }
            if (found) {
                break;
            }
        }
        return found;
    }
    
    /**
     * Method for checking whether whether a graph is connected or not
     * @param g, the input graph
     * @param src, the start/source node
     * @return true if the graph is connected and false otherwise
     * @throws IllegalArgumentException if src is not in the graph
     * @throws IllegalArgumentException if the specified graph is null
     */
    public static boolean isConnected(Graph g, int src) {
        // null graph case        
        if (g == null) {
            throw new IllegalArgumentException("Graph can't be null");
        }
        
        // source not present in graph case
        if (src > g.getSize() - 1 || src < 0) {
            throw new IllegalArgumentException("" + src + " is not present in the graph");
        }
        
        // BFS algorithm
        ArrayDeque<Integer> q = new ArrayDeque<Integer>();
        boolean[] marked = new boolean[g.getSize()];
        Integer[] parent = new Integer[g.getSize()];
        marked[src] = true;
        
        q.addLast(src);
        parent[src] = src;
        while (q.size() != 0) {
            int v = q.pollFirst();
            for (int w : g.getNeighbors(v)) {
                if (!marked[w]) {
                    parent[w] = v;
                    marked[w] = true;
                    q.addLast(w);
                }
            }
        }
        
        // check if there are unmarked/undiscovered nodes
        for (int i = 0; i < marked.length; i++) {
            if (!marked[i]) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Returns a shortest path from the src to tgt by executing a breadth-first search.
     * If there are multiple shortest paths, this method returns any one of them. 
     * @param g the graph
     * @param src the node from which to search
     * @param tgt the node to find via {@code src}
     * @return a list of nodes of the shortest path from src to tgt, or an
     *         empty list if there is no path from src to tgt.
     * @throws IllegalArgumentException if src and/or tgt is not in the graph
     * @throws IllegalArgumentException if the specified graph is null
     */
    public static List<Integer> getShortestPath(Graph g, int src, int tgt) {
        // null graph case        
        if (g == null) {
            throw new IllegalArgumentException("Graph can't be null");
        }
        
        // source not present in graph case
        if (src > g.getSize() - 1 || src < 0) {
            throw new IllegalArgumentException("" + src + " is not present in the graph");
        }
        
        // target not present in graph case
        if (tgt > g.getSize() - 1 || tgt < 0) {
            throw new IllegalArgumentException("" + tgt + " is not present in the graph");
        }
        
        // source is the same as target case
        if (src == tgt) {
            LinkedList<Integer> path = new LinkedList<Integer>();
            path.add(src);
            return Collections.unmodifiableList(path);
        }
        
        LinkedList<Integer> path = new LinkedList<Integer>();
        
        Integer[] parent = new Integer[g.getSize()];
        
        // create the shortest path
        if (bfsPath(g, src, tgt, parent)) {
            int curr = tgt;
            path.addFirst(tgt);
            while (curr != src) {
                path.addFirst(parent[curr]);
                curr = parent[curr];
            }
            return Collections.unmodifiableList(path);
        } else {
            return Collections.unmodifiableList(path);
        }
    }
}