package gmit;

import java.util.Random;

public class Wander implements SearchAlgorithm {
	public void search(GameCharacter gc, Location loc){
		//System.out.println(gc.getName()+ " is visiting " + gc.getCurrLoc().getName());
		Random rand = new Random();
		int n = rand.nextInt(loc.exitListSize());
		Location[] exits = loc.children();
		loc.removeFromCharacterList(gc.getName());
		
		Location newLoc = exits[n];
		
		//SLEEP for distance between the two locations....
		try {
			Thread.sleep(loc.calculateDistance(newLoc)*10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//set the new location
		gc.getCurrLoc().removeFromCharacterList(gc.getName());
		gc.setCurrLoc(newLoc);
		gc.getCurrLoc().addToCharacterList(gc.getName(), gc);
		
		//Game character enters your location
		if(gc.getCurrLoc().checkForMainCharacter()){
			//dont display if in battle
			if(!GamePlay.myChar.isFighting())
			{
				System.out.println(gc.getName() + " just appeared in " + GamePlay.myChar.getCurrLoc().getName());
			}
			
			//Fuzzy chance of attack
			FuzzyLogic fl = new FuzzyLogic();
			double chanceOfAttack=fl.fuzzyChanceOfAttack(gc.getAggressiveness(), gc.getObservance());;
			int  i = rand.nextInt(100) + 1;
			if((i<=chanceOfAttack)&&(!GamePlay.myChar.isFighting())){
				return;
			}
			
		}
	}
}
