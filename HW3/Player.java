import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Player{
	public enum PlayerType {
		BLACK, WHITE;
	}
	
	private PlayerType playerType;
	private static final int searchDepth = 4;
	private HashMap<Integer, LinkedList<Position>> results;
	private LinkedList<Position> backtrack;
	private int finalUtility = Integer.MIN_VALUE;
	private int prunnedNodes = 0;
	
	public Player(PlayerType playerType){
		this.playerType = playerType;
	}
	
	public void minmax(GoBoard state){
		if(playerType == null) return;
		//Preserve the state and create a new state that can be operated
		GoBoard opState = state.customizeCopy();
		int depth = 0;
		finalUtility = maxValue(opState, depth);
		return;
	}
	
	public void alphaBeta(GoBoard state){
		if(playerType == null) return;
		//Preserve the state and create a new state that can be operated
		GoBoard opState = state.customizeCopy();
		int depth = 0;
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		backtrack = new LinkedList<Position>();
		results = new HashMap<Integer, LinkedList<Position>>();
		finalUtility = maxValue(opState, depth, alpha, beta);
	}

	private int maxValue(GoBoard state, int depth){
		//Do the terminal Test
		if(terminalTest(state, playerType, depth)) return state.utilityValue(playerType);
		//Preserve the state and create a new state that can be operated
		GoBoard opState = state.customizeCopy();
		int utilityMAX = Integer.MIN_VALUE;
		//Do the algorithm as follows
		Iterator<Position> iter = opState.getAllLiberties(playerType).iterator();
		while(iter.hasNext()){
			Position op = iter.next();
			int currentDepth = depth;
			GoBoard successor = apply(op, opState, playerType);
			utilityMAX = Math.max(utilityMAX, minValue(successor, ++currentDepth));
		}
		return utilityMAX;
	}
	
	private int minValue(GoBoard state, int depth){
		//Calculate the opposite player color
		PlayerType oppoPlayerType;
		if(playerType == PlayerType.BLACK) oppoPlayerType = PlayerType.WHITE;
		else oppoPlayerType = PlayerType.BLACK;
		//Do the terminal Test
		if(terminalTest(state, oppoPlayerType, depth)) return state.utilityValue(playerType);
		//Preserve the state and create a new state that can be operated
		GoBoard opState = state.customizeCopy();
		//Do the algorithm as follows
		int utilityMIN = Integer.MAX_VALUE;
		Iterator<Position> iter = opState.getAllLiberties(oppoPlayerType).iterator();
		while(iter.hasNext()){
			Position op = iter.next();
			int currentDepth = depth;
			GoBoard successor = apply(op, opState, oppoPlayerType);
			utilityMIN = Math.min(utilityMIN, maxValue(successor, ++currentDepth));
		}
		return utilityMIN;
	}

	private int maxValue(GoBoard state, int depth, int alpha, int beta){
		//Do the terminal Test
		if(terminalTest(state, playerType, depth)){
			int utility = state.utilityValue(playerType);
			if(!results.containsKey(utility)){
				LinkedList<Position> result = new LinkedList<Position>();
				result.addAll(backtrack);
				results.put(utility, result);
			}
			return utility;
		}
		//Preserve the state and create a new state that can be operated
		GoBoard opState = state.customizeCopy();
		//v <- negative Infinity
		int v = Integer.MIN_VALUE;
		//Set all the op utility value to be negative infinity
		HashSet<Position> liberties = opState.getAllLiberties(playerType);
		Iterator<Position> iter = liberties.iterator();
		int countNotPrunned = 0;
		while(iter.hasNext()){
			countNotPrunned++;
			Position op = iter.next();
			int currentDepth = depth;
			backtrack.add(op);
			GoBoard successor = apply(op, opState, playerType);
			v = Math.max(v, minValue(successor, ++currentDepth, alpha, beta));
			backtrack.removeLast();
			if(v >= beta){
				prunnedNodes += liberties.size() - countNotPrunned;
				return v;
			}
			alpha = Math.max(alpha, v);
		}
		return v;
	}
	
	private int minValue(GoBoard state, int depth, int alpha, int beta){
		//Calculate the opposite Player
		PlayerType oppoPlayerType;
		if(playerType == PlayerType.BLACK) oppoPlayerType = PlayerType.WHITE;
		else oppoPlayerType = PlayerType.BLACK;
		//Terminal Test
		if(terminalTest(state, oppoPlayerType, depth)){
			int utility = state.utilityValue(playerType);
			if(!results.containsKey(utility)){
				LinkedList<Position> result = new LinkedList<Position>();
				result.addAll(backtrack);
				results.put(utility, result);
			}
			return utility;
		}
		//Preserve the state and create a new state that can be operated
		GoBoard opState = state.customizeCopy();
		
		int v = Integer.MAX_VALUE;
		HashSet<Position> liberties = opState.getAllLiberties(oppoPlayerType);
		Iterator<Position> iter = liberties.iterator();
		int countNotPrunned = 0;
		while(iter.hasNext()){
			countNotPrunned++;
			Position op = iter.next();
			int currentDepth = depth;
			backtrack.add(op);
			GoBoard successor = apply(op, opState, oppoPlayerType);
			v = Math.min(v, maxValue(successor, ++currentDepth, alpha, beta));
			backtrack.removeLast();
			if(v <= alpha){
				prunnedNodes += liberties.size() - countNotPrunned;
				return v;
			} 
			beta = Math.min(beta, v);
		}
		return v;
	}
	
	private boolean terminalTest(GoBoard state, PlayerType playerType, int depth){
		if(depth >= searchDepth) return true;
		if(state.getAllLiberties(playerType).size() == 0) return true;
		return false;
	}
	
	private GoBoard apply(Position op, GoBoard state, PlayerType playert){
		PlayerType oppoPlayerType;
		if(playert == PlayerType.BLACK) oppoPlayerType = PlayerType.WHITE;
		else oppoPlayerType = PlayerType.BLACK;
		
		GoBoard newBoard = state.customizeCopy();
		Position[][] next = newBoard.getPositions();
		if(playert == PlayerType.BLACK) next[op.row()][op.column()].setBlack();
		else if(playert == PlayerType.WHITE) next[op.row()][op.column()].setWhite();
		newBoard.removeCaptured(oppoPlayerType);
		return newBoard;
	}

	public void makeMove(GoBoard state){
		double before = System.currentTimeMillis();
		alphaBeta(state);
		double after = System.currentTimeMillis();
		printABresult();
		
		System.out.println("Comparison:");
		System.out.println("Minmax with prunning: running time " + (after - before)/1000 + "s;");
		System.out.println("Minmax with pruning: pruned " + prunnedNodes + " nodes;");
		before = System.currentTimeMillis();
		minmax(state);
		after = System.currentTimeMillis();
		System.out.println("Minmax without prunning: running time " + (after - before)/1000 + "s;");
	}
	
	public void printABresult(){
		System.out.println("Best strategy:");
		LinkedList<Position> actions = results.get(finalUtility);
		for(int i = 0; i < actions.size(); i++){
			System.out.println("Depth " + i + ": Player B places stone at (" +
					(actions.get(i).row()+1) + "," + (actions.get(i).column()+1) + ").");
			i++;
			System.out.println("Depth " + i + ": Player W places stone at (" +
					(actions.get(i).row()+1) + "," + (actions.get(i).column()+1) + ").");
		}
		System.out.println("Depth 4: utility value of current board configuration is " + finalUtility);
	}
}
