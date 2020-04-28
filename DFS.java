import java.util.List;
import java.util.Stack;

/**
 * Class for running Depth-First Search on a given graph.
 */
public final class DFS {
    
    /**
     * An implementation of the DFS algorithm
     * @param g, the graph to run DFS on
     * @param src, the start/source node
     * @param result, the list nodes in the order visited
     * @param marked, an array showing which nodes have been marked/discovered
     * @param parent, an array showing the parent of each node
     * @return the list of nodes visited in the DFS order
     * @throws IllegalArgumentException if src is not in the graph
     * @throws IllegalArgumentException if the specified graph is null
     */
    public static List<Integer> dfsImpl(Graph g, int src, List<Integer> result, 
            boolean[] marked, Integer[] parent) {
        // invalid inputs
        if (g == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }

        if (src < 0 || src >= g.getSize()) {
            throw new IllegalArgumentException("Source not present in graph");
        }
        
        // DFS algorithm
        Stack<Integer> s = new Stack<Integer>();
        marked[src] = true;
        
        s.push(src);
        parent[src] = src;
        while (s.size() != 0) {
            int v = s.pop();
            result.add(v);
            for (int w : g.getNeighbors(v)) {
                if (!marked[w]) {
                    parent[w] = v;
                    marked[w] = true;
                    s.push(w);
                }
            }
        }
        
        // check if there are any unmarked/undiscovered nodes
        for (int i = 0; i < marked.length; i++) {
            if (!marked[i]) {
                dfsImpl(g, i, result, marked, parent);
            }
        }
        return result;
    }
}