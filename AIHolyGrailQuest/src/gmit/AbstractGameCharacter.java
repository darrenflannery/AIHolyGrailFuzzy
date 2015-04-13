package gmit;

public abstract class AbstractGameCharacter implements GameCharacter{
	protected String name;
	protected Location currLoc;
	protected SearchAlgorithm search;
	protected int aggressiveness;
	protected int observance;
	protected int accuracy;

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public int getObservance() {
		return observance;
	}

	public int getAggressiveness() {
		return aggressiveness;
	}

	public void setAggressiveness(int aggressiveness) {
		this.aggressiveness = aggressiveness;
	}

	public void setObservance(int observance) {
		this.observance = observance;
	}

	public SearchAlgorithm getSearch() {
		return search;
	}

	public void setSearch(SearchAlgorithm search) {
		this.search = search;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getCurrLoc() {
		return currLoc;
	}

	public void setCurrLoc(Location currLoc) {
		this.currLoc = currLoc;
	}

}
