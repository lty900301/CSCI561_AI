import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class GoBoard {
	private Position[][] positions;
	
	public GoBoard(){
		positions = new Position[19][19];
		for(int i = 0; i < positions.length; i++){
			for(int j = 0; j < positions[0].length; j++){
				positions[i][j] = new Position(i, j);
			}
		}
		updateLiberties();
	}
	
	public GoBoard(Position[][] board){
		positions = board;
		updateLiberties();
	}
	
	public GoBoard(Go.Specific type){
		if(type == Go.Specific.CSCI565HW3){
			int boardSize = 6;
			
			positions = new Position[boardSize][boardSize];
			for(int i = 0; i < positions.length; i++){
				for(int j = 0; j < positions[0].length; j++){
					positions[i][j] = new Position(i, j);
				}
			}
			positions[0][0].setWhite();
			positions[0][2].setBlack();
			positions[0][3].setWhite();
			positions[0][5].setBlack();
			positions[1][1].setWhite();
			positions[1][2].setBlack();
			positions[1][4].setBlack();
			positions[2][0].setWhite();
			positions[2][2].setWhite();
			positions[2][3].setBlack();
			positions[2][5].setBlack();
			positions[3][1].setBlack();
			positions[3][2].setBlack();
			positions[3][3].setBlack();
			positions[3][4].setWhite();
			positions[3][5].setBlack();
			positions[4][0].setWhite();
			positions[4][1].setBlack();
			positions[4][2].setWhite();
			positions[4][4].setWhite();
			positions[4][5].setBlack();
			positions[5][0].setWhite();
			positions[5][2].setWhite();
			positions[5][3].setBlack();
			positions[5][4].setWhite();
			positions[5][5].setWhite();
			
			updateLiberties();
		}
	}
	
	public void printBoard(){
		//print the board with each Position: State(4 neighbor's State, how many connections);
		updateNeighbors();
		for(int i = 0; i < positions.length; i++){
			for(int j = 0; j < positions[0].length; j++){
				System.out.print(positions[i][j].getStateInit() + "(");
				for(int k = 0; k < positions[i][j].neighbors.size(); k++){
					System.out.print(positions[i][j].neighbors.get(k).getStateInit() + ",");
				}
				if(positions[i][j].neighbors.size() < 4){
					for(int k = 0; k < 4 - positions[i][j].neighbors.size(); k++){
						System.out.print(" ,");
					}
				}
				System.out.print(positions[i][j].connSet.size() + ",");
				System.out.print(positions[i][j].liberties.size());
				System.out.print(")" + "\t");
			}
			System.out.println();
		}
		
		//print the Utility Value for the current board configuration
		System.out.println("The utility value for B is " + utilityValue(Player.PlayerType.BLACK));
	}

	public void printBoard2(){
		for(int i = 0; i < positions.length; i++){
			for(int j = 0; j < positions[0].length; j++){
				System.out.print(positions[i][j].getStateInit() + " ");
			}
			System.out.println();
		}
		System.out.println(this.utilityValue(Player.PlayerType.BLACK));
	}
	
	public Position[][] getPositions(){
		return positions;
	}

	private void updateNeighbors(){
		for(int i = 0; i < positions.length; i++){
			for(int j = 0; j < positions[0].length; j++){
				//clear all the neighbors previously stored;
				positions[i][j].neighbors.clear();
				if(j < positions[0].length - 1) 
					positions[i][j].neighbors.add(positions[i][j + 1]);
				if(i < positions.length - 1) 
					positions[i][j].neighbors.add(positions[i + 1][j]);
				if(j > 0) 
					positions[i][j].neighbors.add(positions[i][j - 1]);
				if(i > 0) positions[i][j].neighbors.add(positions[i - 1][j]);
			}
		}
	} 
	
	private void updateConnectionSet(){
		//Re-find all the neighbors
		updateNeighbors();
		
		//clear all the connections this Position previously stored
		for(int i = 0; i < positions.length; i++){
			for(int j = 0; j < positions.length; j++){
				positions[i][j].connSet.clear();
			}
		}
		
		for(int i = 0; i < positions.length; i++){
			for(int j = 0; j < positions.length; j++){
				//add itself to the connectedSet
				positions[i][j].connSet.add(positions[i][j]);
				
				//find it's neighbors and add all the neighbor's connections if connected
				for(int k = 0; k < positions[i][j].neighbors.size(); k++){
					if(positions[i][j].isNeighborConnected(positions[i][j].neighbors.get(k))){
						if(positions[i][j].neighbors.get(k).connSet.size() > 0){
							positions[i][j].connSet.addAll(
									positions[i][j].neighbors.get(k).connSet);
						}
					}
				}
				
				//update all the already neighbored Positions to have the same connSet
				Iterator<Position> iter = positions[i][j].connSet.iterator();
				while(iter.hasNext()){
					Position next= iter.next();
					if(next == positions[i][j]) continue;
					next.connSet.addAll(positions[i][j].connSet);
				}
			}
		}
	}

	public void updateLiberties(){
		updateConnectionSet();
		Position[][] current = this.getPositions();
		
		for(int i = 0; i < current.length; i++){
			for(int j = 0; j < current[0].length; j++){
				//Clear the Liberties List to avoid replica
				current[i][j].liberties.clear();
			}
		}
		
		for(int i = 0; i < current.length; i++){
			for(int j = 0; j < current[0].length; j++){
				Position curPosition = current[i][j];
				Position.State curState = curPosition.state();
				
				if((curState == Position.State.BLACK || curState == Position.State.WHITE) &&
						curPosition.liberties.size() == 0){
					
					//Get all the liberties for current connected Set
					Iterator<Position> iter = curPosition.connSet.iterator();
					while(iter.hasNext()){
						Position next = iter.next();
						ArrayList<Position> nextNeibors = next.neighbors;
						for(Position p:nextNeibors){
							if(p.isLiberty()){
								curPosition.liberties.add(p);
							}
						}
					}
					
					//Set every Position in this set to have same liberties
					//This will avoid recalculations.
					iter = curPosition.connSet.iterator();
					while(iter.hasNext()){
						Position next = iter.next();
						next.liberties = curPosition.liberties;
					}
				}
			}
		}
	}
	
	public int utilityValue(Player.PlayerType player){
		if(player == null || 
				(player != Player.PlayerType.BLACK && player != Player.PlayerType.WHITE)) 
			System.exit(-1);
		int utility = 0;
		for(int i = 0; i < positions.length; i++){
			for(int j = 0; j < positions[0].length; j++){
				if(positions[i][j].state() == Position.State.BLACK){
					if(player == Player.PlayerType.BLACK) utility++;
					else if (player == Player.PlayerType.WHITE) utility--;
				} else if(positions[i][j].state() == Position.State.WHITE){
					if(player == Player.PlayerType.WHITE) utility++;
					else if (player == Player.PlayerType.BLACK) utility--;
				}
			}
		}
		return utility;
	}
	
	public HashSet<Position> getAllLiberties(Player.PlayerType playerType){
		updateLiberties();
		HashSet<Position> liberties = new HashSet<Position>();
		for(int i = 0; i < this.getPositions().length; i++){
			for(int j = 0; j < this.getPositions()[0].length; j++){
				if((playerType == Player.PlayerType.BLACK && 
						this.getPositions()[i][j].state() == Position.State.BLACK) || 
							(playerType == Player.PlayerType.WHITE && 
								this.getPositions()[i][j].state() == Position.State.WHITE)){
					for(Position liberty:this.getPositions()[i][j].liberties){
						liberties.add(liberty);
					}
				}
			}
		}
		return liberties;
	}

	public void removeCaptured(Player.PlayerType player){
		updateLiberties();
		for(int i = 0; i < positions.length; i++){
			for(int j = 0; j < positions[0].length; j++){
				if(positions[i][j].isBelonged(player) && positions[i][j].liberties.size() == 0){
					positions[i][j].setOccupied();
				}
			}
		}
	}
	
	public GoBoard customizeCopy(){
		//Preserve the state and create a new state that can be operated
		Position[][] newBoard = new Position[this.getPositions().length][this.getPositions()[0].length];
		for(int i = 0; i < newBoard.length; i++){
			for(int j = 0; j < newBoard[0].length; j++){
				newBoard[i][j] = this.getPositions()[i][j].customizeClone();
			}
		}
		return new GoBoard(newBoard);
	}
}
