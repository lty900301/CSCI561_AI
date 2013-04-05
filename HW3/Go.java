
public class Go {
	public enum Specific{
		CSCI565HW3;
	}
	
	public static void main(String args[]){
		GoBoard state = new GoBoard(Specific.CSCI565HW3);
		Player B = new Player(Player.PlayerType.BLACK);
		B.makeMove(state);
	}
}