
public class Point {
	private boolean isUserI_Seen_MovieJ;
	public Point(int oneORzero){
		if(oneORzero > 0) isUserI_Seen_MovieJ = true;
		else isUserI_Seen_MovieJ = false;
	}
	
	public boolean getIfSeen(){
		return isUserI_Seen_MovieJ;
	}
}
