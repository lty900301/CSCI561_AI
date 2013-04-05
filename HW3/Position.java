import java.util.ArrayList;
import java.util.HashSet;


public class Position{
	//Enumerate all the possible states that can be in an intersection on the board.
	public enum State{
		EMPTY, BLACK, WHITE, OCCUPIED;
	}
	
	private State posState;
	private int row;
	private int column;
	public ArrayList<Position> neighbors = new ArrayList<Position>();
	public HashSet<Position> connSet = new HashSet<Position>();
	public ArrayList<Position> liberties = new ArrayList<Position>();
	
	public Position(){
		posState = State.EMPTY;
	}
	
	public Position(State s){
		posState = s;
	}
	
	public Position(int row, int column){
		posState = State.EMPTY;
		this.row = row;
		this.column = column;
	} 
	
	public int hashCode() {
        int hash = 1;
        hash = hash * 17 + row();
        hash = hash * 31 + column();
        int stateCode = 0;
        if(state() == State.EMPTY) stateCode = 0;
        else if(state() == State.BLACK) stateCode = 1;
        else if(state() == State.WHITE) stateCode = 2;
        else if(state() == State.OCCUPIED) stateCode = 3;
        hash = hash * 13 + stateCode;
        return hash;
    }
	
	public boolean equals(Position p){
		if(p == null || p.getClass() != this.getClass()) return false;
		if(this == p) return true;
		else return false;
	}

	
	public State state(){
		return posState;
	}
	
	public char getStateInit(){
		if(posState == State.EMPTY) return 'E';
		else if(posState == State.BLACK) return 'B';
		else if(posState == State.WHITE) return 'W';
		else if(posState == State.OCCUPIED) return 'O';
		else return 'N';
	}
	
	public int row(){
		return row;
	}
	
	public int column(){
		return column;
	}
	
	
	public void setWhite(){
		posState = State.WHITE;
	}
	
	public void setBlack(){
		posState = State.BLACK;
	}
	
	public void setEmpty(){
		posState = State.EMPTY;
	}
	
	public void setOccupied(){
		posState = State.OCCUPIED;
		neighbors.clear();
		connSet.clear();
		liberties.clear();
	}

	
	public boolean isNeighbor(Position p){
		if(p == null) return false;
		if((Math.abs(this.row() - p.row()) == 0 && 
				Math.abs(this.column() - p.column()) == 1) || 
				(Math.abs(this.row() - p.row()) == 1 && 
					Math.abs(this.column() - p.column()) == 0)){
			return true;
		}
		else return false;
	}
	
	public boolean isNeighborConnected(Position p){
		if(!isNeighbor(p)) return false;
		if(p.state() == this.state()) return true;
		else return false;
	}
	
	public boolean isLiberty(){
		if(this.posState == State.EMPTY) return true;
		else return false;
	}
	
	public boolean isBelonged(Player.PlayerType playerType){
		if(playerType == Player.PlayerType.BLACK && this.state() == State.BLACK) return true;
		else if(playerType == Player.PlayerType.WHITE && this.state() == State.WHITE) return true;
		return false;
	}

	
	public Position customizeClone(){
		Position p = new Position();
		
		p.posState = this.posState;
		p.row = this.row;
		p.column = this.column;
		
		return p;
	}
}
