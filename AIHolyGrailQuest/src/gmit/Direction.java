package gmit;

public enum Direction {
	N, S, E, W;

	public String toString(){
		if (this==N){
			return "North";
		}
		if (this==S){
			return "South";
		}
		if (this==E){
			return "East";
		}
		else return "West";
	}
}
