package gmit;

import java.util.Map;
import java.util.Random;

public class MyCharacter{
	
	private static Location currLoc;
	private static int lifeForce = 100;
	private boolean fighting = false;
	private GameCharacter attacked;

	public GameCharacter getAttacked() {
		return attacked;
	}

	public void setAttacked(GameCharacter attacked) {
		this.attacked = attacked;
	}

	public boolean isFighting() {
		return fighting;
	}

	public void setFighting(boolean fighting) {
		this.fighting = fighting;
	}

	public int getLifeForce() {
		return lifeForce;
	}

	public void setLifeForce(int lifeForce) {
		this.lifeForce = lifeForce;
	}

	public Location getCurrLoc() {
		return currLoc;
	}

	public void setCurrLoc(Location currLoc) {
		currLoc.containsMainChar(true);
		MyCharacter.currLoc = currLoc;
	}
	
	public boolean move(Direction direction){
		Map <Direction, Location> exits = this.currLoc.getExitsMap();
		if(exits.containsKey(direction)){
			this.currLoc.containsMainChar(false);
			setCurrLoc(exits.get(direction));
			
			//give health boost to character
			lifeForce=lifeForce+currLoc.getHealthBoost();
			currLoc.setHealthBoost(0);
			
			look();
			
			return true;
		}
		else {
			return false;
		}
	}
	
	public static void look(){
		//location
		System.out.println("You are in: " + currLoc.getName());
		//health
		System.out.println("Your health is: " + lifeForce);
		//description
		System.out.println(currLoc.getDescription());
		//characters
		for(GameCharacter gc: currLoc.getCharList().values()){
			System.out.println(gc.getName()+ " is in this location");
		}
	}
	
	public void fight(GameCharacter gc){
		//if you choose fight, your damage will be a random number between 0 and 1.5* the attack of the saracen
		FuzzyLogic fl = new FuzzyLogic();
		int attack = (int) fl.Attack(gc.getAggressiveness(), gc.getAccuracy());
		Random rand = new Random();
		int  n = rand.nextInt(attack*2) + 0;
		System.out.println("You are fighting " + gc.getName());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(gc.getName() + " did " + n + " damage to your health!");
		GamePlay.myChar.setFighting(false);
		GamePlay.myChar.setLifeForce(GamePlay.myChar.getLifeForce() - n);
		
		if(GamePlay.myChar.getLifeForce()>0){
			System.out.println("Your new health is " + GamePlay.myChar.getLifeForce());
			GamePlay.input();
		}
		else{
			System.out.println("Your health is 0");
			System.out.println("You're Dead");
			System.exit(0);
		}
	}
}
