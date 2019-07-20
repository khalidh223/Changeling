import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * CS201 Data Structures
 * Professor: Sneha Narayan
 *
 * Main executable file that transforms first word into the second,
 * using the HashMap data structure.
 *
 * @author Khalid Hussain, Walt Li
 */

public class Changeling {

    // Class variable
    // A graph that store all the 3-letter words
    private Map<String, Set<String>> graph = new HashMap<>();


    /**
     * Constructor of the class
     * initiating a graph
     *
     */
    public Changeling(){
    }

    /**
     * Pairing a word with a set of all its neighbors, and
     * adding the pair into the HashMap
     *
     * @param word
     * @param filename
     */
    public void addSet(String word, String filename){
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
        } catch (FileNotFoundException ex){
            System.err.println(filename + " not found.");
            System.exit(4);
        }

        Set<String> connections = new HashSet<>();
        while (sc.hasNextLine()){
            String neighbor = sc.nextLine();
            if (oneLetterDifferent(word, neighbor)){
                connections.add(neighbor);
            }
        }
        graph.put(word, connections);
    }

    /**
     *
     * A helper method to determine if two words are neighbors.
     *
     *
     * @param word1
     * @param word2
     * @return whether the two words have only one-word difference or not
     */
    private boolean oneLetterDifferent(String word1, String word2){
        int sum = 0;
        for (int i = 0; i < word1.length(); i++){
            if (word1.charAt(i) == word2.charAt(i)) {
                sum++;
            }
        }
        if (sum == 2){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Using Breadth-First-Search to reach the finalword, and
     * make a chart to trace backward for later to recognize the shortest path
     *
     * @param startword
     * @param finalword
     * @return a chart to refer backward
     */
    public HashMap<String, String> findSolution(String startword, String finalword){
        ArrayList<String> visited = new ArrayList<>();
        ArrayList<String> identified = new ArrayList<>();
        Queue<String> queue = new LinkedList<>();
        HashMap<String, String> traceBackChart = new HashMap<>();

        identified.add(startword);
        queue.offer(startword);

        while (!queue.isEmpty()){
            String current = queue.poll();
            visited.add(current);

            Set<String> neighbors = graph.get(current);
            for (String neighbor : neighbors) {
                if (!identified.contains(neighbor)) {
                    queue.offer(neighbor);
                    identified.add(neighbor);
                    // store the predecessor of this neighbor into a Map
                    traceBackChart.put(neighbor, current);
                }
                if (neighbor.equals(finalword)) {
                    visited.add(neighbor);
                    return traceBackChart;
                }
            }
        }
        return null;
    }

    /**
     * Trace back the shortest path and store it in a stack
     *
     * @param traceBackChart
     * @param startword
     * @param finalword
     * @return the stack that contains the path
     */
    public Deque<String> readSolution(HashMap<String, String> traceBackChart, String startword, String finalword){
        Deque<String> stack = new ArrayDeque<>();
        String word = finalword;
        while (word != startword){
            stack.push(word);
            word = traceBackChart.get(word);
        }
        stack.push(startword);
        return stack;
    }


    public static void main(String[] args){

        // catch the error of not having 3 words input
        if (args.length != 3){
            System.err.println("Invalid amount of arguments.");
            System.exit(1);
        }

        String filename = args[0];
        String startword = args[1];
        String finalword = args[2];

        //catch the error of not inputting words with 3 letters
        if (startword.length() != 3 || finalword.length() != 3){
            System.err.println("Invalid length of word.");
            System.exit(2);
        }


        //load graph from file
        Changeling changeling = new Changeling();

        File file = new File(filename);

        Scanner sc1 = null;
        try {
            sc1 = new Scanner(file);
        } catch (FileNotFoundException ex){
            System.err.println(filename + " not found.");
            System.exit(3);
        }

        // adding word-set pairs one by one to the map
        while (sc1.hasNextLine()){
            String word = sc1.nextLine();
            changeling.addSet(word, filename);
        }

        //trace back and find and print solution for the changeling
        HashMap<String, String> traceBackChart = changeling.findSolution(startword, finalword);
        if (traceBackChart == null){//no solution
            System.out.println("There is no solution for this changeling.");
        } else {//print the solution in order
            Deque<String> path = changeling.readSolution(traceBackChart, startword, finalword);
            while (!path.isEmpty()){
                String word = path.pop();
                System.out.println(word);
            }
        }
    }

}
