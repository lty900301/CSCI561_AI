
public class Dissimilarity {
	private static final int T0 = 5;
	private int[][] disimilarityMatrix;
	
	public Dissimilarity(Graph g){
		int numOfMovies = g.getMatrix()[0].length;
		disimilarityMatrix = new int[numOfMovies][numOfMovies];
		
		for(int i = 0; i < disimilarityMatrix.length; i++){
			for(int j = 0; j < disimilarityMatrix[0].length; j++){
				disimilarityMatrix[i][j] = Integer.MAX_VALUE;
			}
		}
		
		for(int i = 0; i < g.getMatrix().length; i++){
			for(int j = 0; j < g.getMatrix()[0].length; j++){
				if(g.getMatrix()[i][j].getIfSeen()){
					for(int k = j; k < g.getMatrix()[0].length; k++){
						if(g.getMatrix()[i][k].getIfSeen()){
							 int d = ((j + 1 + k + 1) % T0 + 1) * 
									 (T0 - dotProduct(g.getColumn(j), g.getColumn(k)));
							 if(j == k) continue;
							 else if(d < disimilarityMatrix[j][k]){
								 disimilarityMatrix[j][k] = d;
								 disimilarityMatrix[k][j] = d;
							 }
						}
					}
				}
			}
		}
	}
	
	public int[][] getDisimilarity(){
		return disimilarityMatrix;
	}
	
	public int dotProduct(Point[] col1, Point[] col2){
		int result = 0;
		if(col1.length != col2.length) return result;
		for(int i = 0; i < col1.length; i++){
			if(col1[i].getIfSeen() && col2[i].getIfSeen()) result += 1;
		}
		return result;
	}
	
	public void printDissimilarity(){
		for(int i = 0; i < disimilarityMatrix.length; i++){
			for(int j = 0; j < disimilarityMatrix[0].length; j++){
				if(disimilarityMatrix[i][j] == Integer.MAX_VALUE){
					System.out.print(0 + "\t");
				}
				else System.out.print(disimilarityMatrix[i][j] + "\t");
			}
			System.out.println();
		}
	}
}
