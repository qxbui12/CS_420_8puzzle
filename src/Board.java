import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Board implements MyBoard{
	public Board(){
		oneDArray = new ArrayList<Integer>();
		distanceFromRoot = 0;
		InitializeArray();
		h1 = hamming();
		h2 = manhattan();
		
	}
	public Board(String file) throws FileNotFoundException{
		oneDArray = new ArrayList<Integer>();
		distanceFromRoot = 0;
		Scanner reader = new Scanner(new File(file));
		for(int i = 0; i < 9; i++){
			oneDArray.add(reader.nextInt());
		}
		h1 = hamming();
		h2 = manhattan();
	}
	public Board(ArrayList<Integer> array, int distance){
		oneDArray = new ArrayList<Integer>();
		oneDArray = copyBoard(array);
		distanceFromRoot = distance + 1;
		h1 = hamming();
		h2 = manhattan();
	}

	@Override
	public int hamming() {
		int sum = 0;
		ArrayList<Integer> hold = Solver.GOAL;
		for(int i = 1; i < 9; i++){
			if(oneDArray.indexOf(i)!= hold.indexOf(i)){
				sum++;
			}
		}
//		System.out.println("Hamming sum: " + sum);
		return sum;
	}

	@Override
	//Manhattan heuristic function
	public int manhattan() {
		int sum = 0;
		ArrayList<Integer> hold = Solver.GOAL;
		for(int i = 1; i < 9; i++){
			int currentStatemodInd = oneDArray.indexOf(i)%3;
			int goalStatemodInd = hold.indexOf(i)%3;
			int rowDifference = Math.abs(rowPos(oneDArray.indexOf(i))-rowPos(hold.indexOf(i)));
//			System.out.println("Solve for "+ i);
//			System.out.println("horizontal dif: " + Math.abs(goalStatemodInd-currentStatemodInd));
//			System.out.println("row dif: " + rowDifference);
			sum += Math.abs(goalStatemodInd-currentStatemodInd)+rowDifference;
		}
//		System.out.println("Manhattan sum: "+ sum);
		return sum;
	}

	@Override
	public boolean equals(Board y) {
		ArrayList<Integer> hold = Solver.GOAL;
		if(oneDArray.equals(hold)){
			return true;
		}
		return false;
	}

	@Override
	public Iterable<Board> neighbors() {
		// TODO Auto-generated method stub
		return null;
	}
	public void printArray(){
		int j = 0;
		for(int i = 0; i < 9; i++){
			System.out.print(oneDArray.get(i)+" ");
			j++;
			if(j ==3){
				System.out.println();
				j = 0;
			}
		}
		System.out.println("Depth: "+getDistanceFromRoot());
	}
	private void InitializeArray(){
		System.out.println("Enter unique numbers from 0 to 9");
		Scanner reader = new Scanner(System.in);
		for (int i = 0; i < 9; i++){
			oneDArray.add(reader.nextInt());
		}
	}
	//generate successors of current Board
	public void generateChildren(){
		children = new ArrayList<Board>();
		if((getHole() != 0) && (getHole() != 3) && (getHole() != 6)){
			swapAdd(getHole()-1, getHole(), children);
		}
		if((getHole() != 0) && (getHole() != 1) && (getHole() != 2)){
			swapAdd(getHole()-3, getHole(), children);
		}
		if((getHole() != 2) && (getHole() != 5) && (getHole() != 8)){
			swapAdd(getHole()+1, getHole(), children);
		}
		if((getHole() != 6) && (getHole() != 7) && (getHole() != 8)){
			swapAdd(getHole()+3, getHole(), children);
		}	
	}
	public ArrayList<Integer> copyBoard(ArrayList<Integer> board){
		ArrayList<Integer> copy = new ArrayList<Integer>();
		for(int i = 0 ; i < board.size(); i++){
			copy.add(board.get(i));
		}
		return copy;
	}
	private void swapAdd(int d, int s, ArrayList<Board> boardList){
		ArrayList<Integer> copy = copyBoard(oneDArray);
        int temp = copy.get(d);
        copy.set(d, copy.get(s));
        copy.set(s, temp);
		Board child = new Board(copy, getDistanceFromRoot());
		boardList.add(child);
	}
	//find the row of a number in the ArrayList corresponding to its 2D array row
	private int rowPos(int index){
		if(index <= 2){
			return 0;
		}
		else if((index >= 3) && (index <= 5)){
			return 1;
		}
		else{
			return 2;
		}
	}
	//return the hole index
	public int getHole(){
		return oneDArray.indexOf(0);
	}
	//return the ArrayList version of the 2D array
	public ArrayList<Integer> getOneDArray(){
		return oneDArray;
	}
	public ArrayList<Board> getChildren(){
		return children;
	}
	public int getDistanceFromRoot(){
		return distanceFromRoot;
	}
	public int getH1(){
		return h1;
	}
	public int getH2(){
		return h2;
	}
	private ArrayList<Integer> oneDArray;
	private ArrayList<Board> children;
	private int distanceFromRoot;
	private int h1;
	private int h2;
}
