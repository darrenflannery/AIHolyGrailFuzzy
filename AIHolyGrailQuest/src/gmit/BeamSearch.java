package gmit;
import java.util.*;
public class BeamSearch implements SearchAlgorithm{
	private static Object monitor = new Object();
	LinkedList<Location> queue = new LinkedList<Location>();
	HeuristicNodeComparator sorter = new HeuristicNodeComparator();
	private int beamWidth= 10;
	private boolean stopSearch = false;
	
	public void search(GameCharacter gc, Location loc){
		loc.setVisited(gc, true);
		queue.addFirst(loc);
		while((!queue.isEmpty()) && (!stopSearch)){
			queue.removeFirst();
			
			if (loc.isGoalNode()){
				break;
			}else{
				Location[] children = loc.children();	
				Collections.sort(Arrays.asList(children), sorter);
				int bound = 0;
				if (children.length < beamWidth){
					bound = children.length;
				}else{
					bound = beamWidth;
				}
				for (int i = 0; i < bound; i++) {
					if (!children[i].isVisited(gc)){
						queue.addLast(children[i]);
					}
				}
				Collections.sort(queue, sorter); //Sort the queue
				Location newLoc = queue.getFirst();
				//sleep for distance between the two locations....
				try {
					Thread.sleep(loc.calculateDistance(newLoc)*10);
					if(loc.checkForMainCharacter()){
						//dont display if in battle
						if(!GamePlay.myChar.isFighting())
						{
							System.out.println(gc.getName() + " just left " + GamePlay.myChar.getCurrLoc().getName() + ", you escaped unharmed...");
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				loc = newLoc;
				loc.setVisited(gc ,true);
				
				//set the new location
				gc.getCurrLoc().removeFromCharacterList(gc.getName());
				gc.setCurrLoc(loc);
				gc.getCurrLoc().addToCharacterList(gc.getName(), gc);
				
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
					else continue;
				}
			}
		}
		return;
	}
}
