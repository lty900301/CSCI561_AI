
public class Main {
	public static final int GREEDY = 1;
	public static final int ASTAR = 2;
	private static final int USER_NUM = 30;
	private static final int MOVIE_NUM = 12;
	private static final int START = 1;
	private static final int END = 12;
	private static final int[] heuristicDis = {12, 10, 9, 10, 7, 6, 7, 6, 4, 4, 3, 0};
	
	public static void main(String args[]){
		
		//This is where to define the matrix structure.
		int matrix[][] = new int[USER_NUM][MOVIE_NUM];
		getIndicatorMatrix(matrix);
		Graph indicatorGraph = new Graph(matrix);
//		indicatorGraph.print();
		Dissimilarity movieDis = new Dissimilarity(indicatorGraph);
//		movieDis.printDissimilarity();
		Search.search(GREEDY, movieDis, heuristicDis, START - 1, END - 1);
		System.out.println();
		Search.search(ASTAR, movieDis, heuristicDis, START - 1, END - 1);
	}
	
	//Hard code initialization of matrix in this problem
	private static void getIndicatorMatrix(int[][] matrix){
		matrix[0][0] = 1;	matrix[0][2] = 1;
		matrix[1][1] = 1;	matrix[1][7] = 1;
		matrix[2][3] = 1;	matrix[2][7] = 1;
		matrix[3][2] = 1;	matrix[3][4] = 1;
		matrix[4][5] = 1;	matrix[4][8] = 1;
		matrix[5][3] = 1;	matrix[5][6] = 1;
		matrix[6][0] = 1;	matrix[6][1] = 1;
		matrix[7][2] = 1;	matrix[7][6] = 1;
		matrix[8][3] = 1;	matrix[8][4] = 1;
		matrix[9][1] = 1;	matrix[9][5] = 1;
		matrix[10][4] = 1;	matrix[10][8] = 1;
		matrix[11][3] = 1;	matrix[11][6] = 1;
		matrix[12][0] = 1;	matrix[12][2] = 1;
		matrix[13][5] = 1;	matrix[13][9] = 1;
		matrix[14][1] = 1;	matrix[14][4] = 1;
		matrix[15][6] = 1;	matrix[15][8] = 1;
		matrix[16][2] = 1;	matrix[16][5] = 1;
		matrix[17][3] = 1;	matrix[17][4] = 1;
		matrix[18][1] = 1;	matrix[18][7] = 1;
		matrix[19][0] = 1;	matrix[19][2] = 1;
		matrix[20][7] = 1;	matrix[20][9] = 1;
		matrix[21][0] = 1;	matrix[21][3] = 1;
		matrix[22][6] = 1;	matrix[22][10] = 1;
		matrix[23][0] = 1;	matrix[23][2] = 1;
		
		matrix[25][0] = 1;	matrix[25][1] = 1;
		matrix[26][8] = 1;	matrix[26][11] = 1;
		matrix[27][6] = 1;
		matrix[28][10] = 1;	matrix[28][11] = 1;
		matrix[29][9] = 1;	matrix[29][11] = 1;
	}
}