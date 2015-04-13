package gmit;

import java.util.Comparator;

public class AStarHeuristicNodeComparator implements Comparator<Location>{
	private GameCharacter gc;
	
	public AStarHeuristicNodeComparator(GameCharacter gc){
		this.gc = gc;
	}
	
	public int compare(Location location1, Location location2) {
		if (location1.getScore(gc) > location2.getScore(gc)){
			return 1;
		}else if (location1.getScore(gc) < location2.getScore(gc)){
			return -1;
		}else{
			return 0;
		}
	}
}
