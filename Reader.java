import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Reader {    
    static Map<Integer, String> invIndex = new HashMap<Integer, String>();
    
    
    /**  Read lines from a file with graph edges where each edge is a pair of nodes (represented 
     *   as integers) with a space between them
     */    
    public static Graph readFile(String fileName) throws IOException {        
        // file reader takes in the string file name, where 
        // buffered reader takes in the file reader        
        FileReader fr = new FileReader(fileName);        
        BufferedReader br = new BufferedReader(fr);        
        
        Map<String, Integer> index = new HashMap<String, Integer>();
        
        // find the maximum value of a node in an input file which will also be 1 less that the 
        // graph size
        String line = br.readLine();
        int currInd = 0;
        while (line != null) {
            String[] vertices = line.split("\\s+");
            String node1 = vertices[0];
            String node2 = vertices[1];
            if (!index.containsKey(node1)) {
                index.put(node1, currInd);
                invIndex.put(currInd, node1);
                currInd++;
            }
            
            if (!index.containsKey(node2)) {
                index.put(node2, currInd);
                invIndex.put(currInd, node2);
                currInd++;
            }            
            line = br.readLine();
        }
        br.close();
        // create the graph
        fr = new FileReader(fileName);
        br = new BufferedReader(fr);
        Graph graph = new Graph(currInd);
        line = br.readLine();
        while (line != null) {
            // add each edge to the graph
            String[] vertices = line.split("\\s+");
            int vertex1 = index.get(vertices[0]);
            int vertex2 = index.get(vertices[1]);
            graph.addEdge(vertex1, vertex2);
            line = br.readLine();
        }
        br.close();
        return graph;
    }    
    
    /**
     * Method for finding the maximum value in an array
     * @param arr, the input array
     * @return the maximum value
     */
    public static int max(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i <  arr.length; i++) {
            max = Math.max(max, arr[i]);
        }
        return max;
    }
    
    public static void main(String[] args) throws IOException {
        Graph g1 = readFile("edge_list.txt");
        Map<Integer, String> upennInvIndex = new HashMap<Integer, String>();
        upennInvIndex.putAll(invIndex);
        
        Graph g2 = readFile("edge_list_google.txt");
        Map<Integer, String> googleInvIndex = new HashMap<Integer, String>();
        googleInvIndex.putAll(invIndex);
        
        Graph g3 = readFile("edge_list_piazza.txt");
        Map<Integer, String> piazzaInvIndex = new HashMap<Integer, String>();
        piazzaInvIndex.putAll(invIndex);
        
        Graph g4 = readFile("edge_list_spotify.txt");
        Map<Integer, String> spotifyInvIndex = new HashMap<Integer, String>();
        spotifyInvIndex.putAll(invIndex);
        
        Graph g5 = readFile("edge_list_usgov_corona.txt");
        Map<Integer, String> usgovInvIndex = new HashMap<Integer, String>();
        usgovInvIndex.putAll(invIndex);
        
        printRanks(g1, upennInvIndex);
        System.out.println();
        
        printRanks(g2, googleInvIndex);
        System.out.println();
        
        printRanks(g3, piazzaInvIndex);
        System.out.println();
        
        printRanks(g4, spotifyInvIndex);
        System.out.println();
        
        printRanks(g5, usgovInvIndex);
        System.out.println();
    }
    
    /**
     * Method for printing the ranks of an input graph in decreasing order
     * @param g, the input graph
     * @param invIndex, a mapping of each node index in g to the nodes url
     */
    private static void printRanks(Graph g, Map<Integer, String> invIndex) {
        Map <Integer, Double> ranks = g.pageRank();
        Object[] arr = ranks.values().toArray();
        Arrays.sort(arr);
        // print the ranks in descending order
        for (int i = arr.length - 1; i >= 0; i--) {
          for (int j = 0; j < g.getSize(); j++) {
              if (ranks.get(j) == arr[i]) {
                  System.out.println(invIndex.get(j) + " : " + (double) arr[i]);
                  ranks.remove(j);
                  break;
              }
          }
        }
    }
}