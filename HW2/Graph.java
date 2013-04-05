
public class Graph {
	private Point[][] graphMatrix;
	
	public Graph(int[][] matrix){
		graphMatrix = new Point[matrix.length][matrix[0].length];
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[0].length; j++){
				graphMatrix[i][j] = new Point(matrix[i][j]);
			}
		}
	}
	
	public Point[][] getMatrix(){
		return graphMatrix;
	}
	
	public Point[] getColumn(int j){
		if(j < 0 || j > graphMatrix.length) return null;
		Point[] colJvector = new Point[graphMatrix.length];
		for(int i = 0; i < graphMatrix.length; i++){
			colJvector[i] = graphMatrix[i][j];
		}
		return colJvector;
	}
	
	public void print(){
		for(int i = 0; i < graphMatrix.length; i++){
			for(int j = 0; j < graphMatrix[0].length; j++){
				System.out.print(graphMatrix[i][j].getIfSeen() + "\t");
			}
			System.out.println();
		}
	}
}
