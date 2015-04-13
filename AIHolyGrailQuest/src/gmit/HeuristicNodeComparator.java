package gmit;

import java.util.*;
public class HeuristicNodeComparator implements Comparator<Location>{
	public int compare(Location location1, Location location2) {
		if (location1.getApproximateDistanceFromGoal() > location2.getApproximateDistanceFromGoal()){
			return 1;
		}else if (location1.getApproximateDistanceFromGoal() < location2.getApproximateDistanceFromGoal()){
			return -1;
		}else{
			return 0;
		}
	}
}

