import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solver {
	public static ArrayList<Integer> GOAL = new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,8));	
	
	public Solver(String text, int heuristicNo) throws FileNotFoundException{
		//Initialize components
		if(heuristicNo == 1){
			Comparator<Board> comparator = new PathH1Comparator();
			frontier = new PriorityQueue<Board>(10, comparator);
		}
		if(heuristicNo == 2){
			Comparator<Board> comparator = new PathH2Comparator();
			frontier = new PriorityQueue<Board>(10, comparator);
		}
		Board myBoard;
		if(text == "USERINPUT"){
			myBoard = new Board();
		}
		else{
			myBoard = new Board(text);
		}
		exploredSet = new HashSet<ArrayList<Integer>>();
		int solvable = 0;
		searchCost = 0;
		//check if board is solvable
		if(isSolvable(myBoard)){
			solvable = 1;
		}
		else{
			System.out.println("Not solvable");
		}
		while(solvable == 1){
			//add current state to frontier. If frontier already contains elements, remove top element from frontier
			if(frontier.isEmpty()){
				currentBoard = myBoard;
				frontier.add(currentBoard);
			}
			else{
				currentBoard = frontier.peek();
			}
			currentBoard.printArray();
			//check if current state is goal state
			if(isGoal(currentBoard.getOneDArray())){
				System.out.println("Solution found");
				currentBoard.printArray();
				break;
			}
			//if not goal state, remove current board from queue and generate children 
			else{
				//remove the current board from the queue
				frontier.remove();
				//generate the current board's children
				currentBoard.generateChildren();
				ArrayList<Board> children = currentBoard.getChildren();
				for(int i = 0; i < children.size(); i++){
					//check if frontier and explored set contains the child node or is empty, if not, add it to the frontier
					boolean isEmpty = exploredSet.isEmpty();
					boolean isExplored = exploredSet.contains(currentBoard.getOneDArray()); 
					if(currentBoard.getDistanceFromRoot()==0){
						if(!frontier.contains(children.get(i))&& (isEmpty)){
							frontier.add(children.get(i));
						}
					}
					else{
						if(!frontier.contains(children.get(i)) && (!isExplored)){
							frontier.add(children.get(i));
						}
					}
					searchCost++;
				}
				//add the current node to explored set
				exploredSet.add(currentBoard.getOneDArray());
			}
		}		
	}
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("***8-puzzle Solver Using A* Algorithms****");
		Scanner reader = new Scanner(System.in);
		int repeat = 1;
		while(repeat == 1){
			System.out.println("Enter 0 to write inputs, \n Enter 1 to take input from a text file: ");
			int choice = reader.nextInt();
			while(choice < 0 ||  choice > 1){
				System.out.println("Wrong input. Please choose between 0 and 1: ");
				choice = reader.nextInt();
			}
			if(choice == 0){
				System.out.println("Enter 1 for h1 heuristic, 2 for h2 heuristic: ");
				int choice1 = reader.nextInt();
				while(choice1 <1 || choice1 >2){
					System.out.println("Wrong input. Please choose between 1 and 2");
					choice1 = reader.nextInt();			
				}
				final long startTime = System.currentTimeMillis();
				Solver solver = new Solver("USERINPUT",choice1);
				final long endTime = System.currentTimeMillis();
				System.out.println("Total execution time: " + (endTime - startTime)+ "ms" );
				System.out.println("Total search cost (nodes generated): "+ solver.getSearchCost());

			}
			if(choice == 1){
				System.out.println("Enter 1 for h1 heuristic, 2 for h2 heuristic: ");
				int choice1 = reader.nextInt();
				while(choice1 <1 || choice1 >2){
					System.out.println("Wrong input. Please choose between 1 and 2");
					choice1 = reader.nextInt();			
				}
				final long startTime = System.currentTimeMillis();
				Solver solver = new Solver("infile.txt",choice1);
				final long endTime = System.currentTimeMillis();
				System.out.println("Total execution time: " + (endTime - startTime) + "ms");
				System.out.println("Total search cost (nodes generated): "+ solver.getSearchCost());

			}
			System.out.println("Try again? Enter 1 for yes, other numbers for no");
			repeat = reader.nextInt();
			if(repeat != 1){
				System.out.println("Bye!");
				break;
			}
		}	
	}
	//check if current state equals to the goal state
	public boolean isGoal(ArrayList<Integer> currentState){
		if(currentState.equals(GOAL)){
			return true;
		}
		return false;
	}
	//check if current state of the board is solvable
	public boolean isSolvable(Board board){
		int counter = inversionCount(board);
		return(counter%2==0);
	}
	
	//check if it's possible to solve the board
	private int inversionCount(Board board){
		int counter = 0;
		for(int i = 0; i < 8; i++){
			for(int j = i+1; j < 9; j++){
//				System.out.print(board.getOneDArray().get(i)+","+board.getOneDArray().get(j));
				if((board.getOneDArray().get(i)>board.getOneDArray().get(j)
						&&(board.getOneDArray().get(j)>0))){
					counter++;
//					System.out.print(","+"inv");
				}
//				System.out.println();
			}
		}
		return counter;
	}
	//get the solution board
	public Board getCurrentBoard(){
		return currentBoard;
	}
	//get the total numbers of nodes generated
	public int getSearchCost(){
		return searchCost;
	}
	private PriorityQueue<Board> frontier;
	private HashSet<ArrayList<Integer>> exploredSet;
	private Board currentBoard;
	private int searchCost;

}
