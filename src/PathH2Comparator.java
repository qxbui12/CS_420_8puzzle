import java.util.Comparator;

public class PathH2Comparator implements Comparator<Board> {

	@Override
	public int compare(Board x, Board y) {
		int func1 = x.getDistanceFromRoot()+ x.getH2();
		int func2 = y.getDistanceFromRoot()+ y.getH2();
		if(func1 < func2){
			return -1;
		}
		if(func1 > func2){
			return 1;
		}
		return 0;
	}
	
}
