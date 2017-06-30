import java.util.Comparator;

public class PathH1Comparator implements Comparator<Board> {

	@Override
	public int compare(Board x, Board y) {
		int func1 = x.getDistanceFromRoot()+ x.getH1();
		int func2 = y.getDistanceFromRoot()+ y.getH1();
		if(func1 < func2){
			return -1;
		}
		if(func1 > func2){
			return 1;
		}
		return 0;
	}
	
}
