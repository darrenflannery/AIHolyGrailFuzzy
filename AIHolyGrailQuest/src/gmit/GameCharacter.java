package gmit;

public interface GameCharacter extends Runnable{

	public abstract void setAccuracy(int accuracy);

	public abstract int getAccuracy();
	
	public abstract int getObservance();

	public abstract int getAggressiveness();

	public abstract void setAggressiveness(int aggressiveness);

	public abstract void setObservance(int observance);
	
	public abstract SearchAlgorithm getSearch();

	public abstract void setSearch(SearchAlgorithm search);
	
	public abstract String getName();

	public abstract void setName(String name);

	public abstract void setCurrLoc(Location location);

	public abstract Location getCurrLoc();

}
