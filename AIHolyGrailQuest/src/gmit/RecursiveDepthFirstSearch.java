package gmit;

import java.util.Random;

public class RecursiveDepthFirstSearch implements SearchAlgorithm {
	
	private Location prevLoc;
	private int travelTime = 0;
	private int totalTravelTime =0;
	private boolean stopSearch = false;
	
	public void search(GameCharacter gc, Location loc) {
		//System.out.println(gc.getName() + " visiting " + loc.getName());
		if (loc.isGoalNode()){
			//System.out.println("Reached goal node " + loc.getName());
			gc.setCurrLoc(loc);
			stopSearch = true;
			return;
		}else{
			prevLoc = loc;
			
			//set the new location
			gc.getCurrLoc().removeFromCharacterList(gc.getName());
			gc.setCurrLoc(loc);
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
				Random rand = new Random();
				int  n = rand.nextInt(100) + 1;
				if((n<=chanceOfAttack)&&(!GamePlay.myChar.isFighting())){
					stopSearch=true;
				}
			}
			
			loc.setVisited(gc,true);
			Location[] children = loc.children();
			for (int i = 0; i < children.length; i++) {	
				if(stopSearch)break;
				if (!children[i].isVisited(gc)){
					if(prevLoc.calculateDistance(children[i])<=0){
						//BACK TRACK to previous location
						//subtract totalTravelTime when at the previous location from current totalTravelTime to know how much to back track
						//then add distance from back tracking to previous location to the distance to new location
						travelTime = (totalTravelTime - loc.getDistanceTravelled(gc)) + loc.calculateDistance(children[i]);
					}
					else{
						travelTime = prevLoc.calculateDistance(children[i]);
						totalTravelTime = totalTravelTime + travelTime;
					}
					children[i].setDistanceTravelled(gc, totalTravelTime);
				
					//SLEEP for distance between nodes
					try {
						//System.out.println(travelTime);
						Thread.sleep(travelTime*10);
						if(prevLoc.checkForMainCharacter()){
							//dont display if in battle
							if(!GamePlay.myChar.isFighting())
							{
								System.out.println(gc.getName() + " just left " + GamePlay.myChar.getCurrLoc().getName() + ", you escaped unharmed...");
							}
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					search(gc,children[i]);	
				}
			}
		}
	}

}
