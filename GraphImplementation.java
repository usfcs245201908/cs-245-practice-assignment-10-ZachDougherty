import java.util.*;

public class GraphImplementation implements Graph {
    private int[][] matrix;
    private int size;
    public GraphImplementation(int vertices) {
        matrix = new int[vertices][vertices];
        size = vertices;
    }


    /**
     * This function creates edges in the adjacency matrix
     * src that src vertices represent a row of the
     * matrix and target vertices represent a column
     * of the matrix.  
     */
    public void addEdge(int src, int tar) throws Exception {
        if (src >= 0 && src < size && tar >= 0 && tar < size) {
            matrix[src][tar] = 1;
        } else {
            throw new Exception("Sorry, either the source or "+
            "target vertex does not exist in this graph");
        }
    }

    public List<Integer> neighbors(int vertex) throws Exception {
        if (vertex >= 0 && vertex < size) {     
            List<Integer> neighbours = new ArrayList<Integer>();
            for (int i=0; i < size; i++) {
                if (matrix[vertex][i] == 1)
                    neighbours.add(i);
            }
            return neighbours;
        } else {
            throw new Exception("Vertex out of bounds.");
        }
    }


    /************************************************
     * If a graph is cyclical, we will end up with
     * a 'sorted' array that is empty, since it will
     * never find an element of the 'sum' array
     * which is equal to zero. 
     *************************************************/
    public List<Integer> topologicalSort() {
        int[] sum = new int[size];
        for (int src=0; src < size; src++) {
            for (int tar=0; tar < size; tar++) {
                sum[src] += matrix[tar][src];
            }
        }
        List<Integer> sorted = new ArrayList<Integer>();
        String msg = "";
        if (findZero(sum) == -1) {
            msg = "The graph is cyclical and so" +
            " a topological sort cannot be " +
            "performed. Returned List is empty";
        } else {
            for (int count=0; count < size; count++) {
                int next = findZero(sum);
                sorted.add(next);
                sum[next] = -1;
                for (int i=0; i < size; i++) {
                    sum[i] -= matrix[next][i];
                }
            }
        }
        if (msg.length() > 0) {
            System.out.println(msg);
        }
        return sorted;
    }

    /*****************************************
     * Returns the index of the first element
     * equal to zero in a given array. If a 
     * graph is cyclical, then there will
     * be no vertex such that the number
     * of incoming edges will be equal to 
     * 0. Inour implementation, this
     * means that our 'sum' array will
     * have no values equal to 0, and so 
     * if we have looped through the 
     * entire array without returning an
     * index, we know we have cyclical
     * behaviour, and thus return -1 to
     * provide an indication of that.
     ******************************************/
    public int findZero(int[] arr) {
        for (int edge=0; edge < arr.length; edge++) {
            if (arr[edge] == 0) {
                return edge;
            }
        }
        return -1;
    }

    // toString function for a given array
    public String toS(int[] arr) {
        String a = "[";
        for (int i=0; i < arr.length; i++) {
            a+= "" + arr[i] + ", ";
        }
        return a+="]";
    }
    
}