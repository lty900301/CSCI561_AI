import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args){
		
		//This is where to define the matrix structure.
		int matrix[][] = {
				{12, 11, 17, 16, 7, 13, 12, 15},
				{16, 17, 9, 15, 15, 7, 6, 12},
				{20, 10, 14, 8, 17, 9, 13, 16},
				{19, 20, 13, 7, 6, 9, 15, 9},
				{18, 15, 19, 10, 8, 9, 14, 12}
				};
		
		//Initiate the graph using the given values
		Graph g = new Graph(matrix);
		
		//Create a user interface that can parse in the user's choice.
		System.out.println("Please select the algorithms from BFS, DFS, UCS.");
		String choice = getChoice().toUpperCase();
		
		//Switch case block cannot handle strings, parse string into char
		char c = shortenString(choice);
		
		/*
		 * invoke the serch method:
		 * void search(char choice, Graph g, Node start, Node end) 
		 */
		search(c, g, g.list.get(2-1).get(1-1), g.list.get(4-1).get(8-1));
	}
	
	/**
	 * Select the Searching Method as the choice parameter indicated. The main
	 * algorithm in this function is to switch case different Method. The 'P'
	 * choice is to print the graph. 'B' for breadth first search. 'D' for depth
	 * first search. And 'U' for uniform cost search. 'Q' to quit the program. 
	 * Other choices are wrong input.
	 * 
	 * @param choice
	 * @param g
	 * @param start
	 * @param end
	 */
	public static void search(char choice, Graph g, Node start, Node end){
		switch (choice) {
		case 'B': Search.breadthFirstSearch(start, end);
			break;
		case 'D': Search.depthFirstSearch(start, end);
			break;
		case 'U': Search.uniformCostSearch(start, end);
			break;
		case 'P':
			//invoke the Graph class's buitIn print method
			g.printGraph();
			break;
		case 'Q':
			break;
		default: {
			System.out.println("Wrong input. Please try from BFS, DFS, UCS.");
			choice = shortenString(getChoice().toUpperCase());
			search(choice, g, start, end);
		}
			break;
		}
		return;
	}
	
	/**
	 * Get the char from string, which is more convenient in a Switch Case block.
	 * @param choice
	 * @return
	 */
	public static char shortenString(String choice){
		if(choice.equals("BFS")){
			return 'B';
		} else if(choice.equals("DFS")){
			return 'D';
		} else if(choice.equals("UCS")){
			return 'U';
		} else if(choice.equals("PRINT")){
			return 'P';
		} else if(choice.equals("QUIT")){
			return 'Q';
		} else return 'W';
	}
	
	/**
	 * This method is used to get User's input from user interface, specifically 
	 * for this program, it is used to get user's choice of which searching method.
	 * 
	 * @return choice
	 */
	public static String getChoice(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String choice = "";
		try {
			choice = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return choice;
	}
}
