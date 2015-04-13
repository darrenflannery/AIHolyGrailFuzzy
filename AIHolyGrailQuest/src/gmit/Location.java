package gmit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Location {

	private String name;
	private String description;
	private int id;
	private boolean containsMainChar = false;
	private boolean goalNode = false;
	private Map <GameCharacter, Location> parent = new HashMap<GameCharacter, Location>();
	private Map <String, GameCharacter> characterList = new HashMap<String, GameCharacter>();
	private Map <Location, Exit> exitList = new HashMap<Location, Exit>();
	private Map<GameCharacter, Boolean> visited = new HashMap<GameCharacter, Boolean>();
	private Map<GameCharacter, Integer> distanceTravelled = new HashMap<GameCharacter, Integer>();
	private int approximateDistanceFromGoal = 0;
	private int healthBoost = 0;
	
	public int getHealthBoost() {
		return healthBoost;
	}

	public void setHealthBoost(int healthBoost) {
		this.healthBoost = healthBoost;
	}

	public void setGoalNode(boolean bool){
		goalNode = bool;
	}
	
	public boolean isGoalNode(){
		return goalNode;
	}
	
	public Map<Direction, Location> getExitsMap(){
		List<Exit> exitArrayList = new ArrayList<Exit>(exitList.values());
		Map <Direction, Location> exits = new HashMap<Direction, Location>();
		for(int i=0; i<exitArrayList.size();i++){
			Direction dir = exitArrayList.get(i).getDirection();
			Location loc = exitArrayList.get(i).getLoc();
			exits.put(dir, loc);
		}
		return exits;
	}


	public void setVisited(GameCharacter gc, boolean v){
		visited.put(gc, v);
	}
	
	public boolean isVisited(GameCharacter gc){
		if(visited.containsKey(gc)){
			return visited.get(gc);
		}
		else return false;
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void addToCharacterList(String name, GameCharacter gc){
		characterList.put(name, gc);
	}
	
	public void removeFromCharacterList(String name){
		if(characterList.containsKey(name)){
			characterList.remove(name);
		}
	}
	
	public Map<String, GameCharacter> getCharList(){
		return characterList;
	}
	
	public Map<Location, Exit> getExitList(){
		return exitList;
	}
	
	public int exitListSize(){
		return exitList.size();
	}
	
	public void addToExitList(Location l, Exit e){
		exitList.put(l, e);
	}
	
	//convert exit map to list of locations
	public List<Location> locationList(Map<Location, Exit> map){
		List<Exit> exitArrayList = new ArrayList<Exit>(map.values());
		List<Location> locationList = new ArrayList<Location>();
		for(int i=0; i<exitArrayList.size();i++) locationList.add(exitArrayList.get(i).getLoc());
		return locationList;
	}
	
	public Location[] children(){
		return (Location[]) locationList(exitList).toArray(new Location[exitList.size()]);
	}
	
	public void containsMainChar(boolean bool){
		containsMainChar = bool;
	}
	
	public boolean checkForMainCharacter(){
			return containsMainChar;
	}
	
	public int getApproximateDistanceFromGoal() {
		return approximateDistanceFromGoal;
	}

	public void setApproximateDistanceFromGoal(int approximateDistanceFromGoal) {
		this.approximateDistanceFromGoal = approximateDistanceFromGoal;
	}
	
	public int calculateDistance(Location loc){
		if(exitList.containsKey(loc)){
			return exitList.get(loc).getDistance();
		}
		else return 0;
	}
	
	public int getDistanceTravelled(GameCharacter gc) {
		if(distanceTravelled.containsKey(gc)){
			return distanceTravelled.get(gc);
		}
		else return 0;
	}

	public void setDistanceTravelled(GameCharacter gc, int depth) {
		distanceTravelled.put(gc, depth);
	}
	
	public Location getParent(GameCharacter gc) {
		if(parent.containsKey(gc)){
			return parent.get(gc);
		}
		else return null;
	}

	public void setParent(GameCharacter gc, Location loc) {
		parent.put(gc,loc);
	}
	
	public float getScore(GameCharacter gc) {
		return HeuristicCalculator.getHeuristicValue(getDistanceTravelled(gc), approximateDistanceFromGoal);
	}
	
}
