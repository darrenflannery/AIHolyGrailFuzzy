package gmit;

import java.util.*;

public class AStar implements SearchAlgorithm{
	private PriorityQueue<Location> open = new PriorityQueue<Location>();
	private List<Location> closed = new ArrayList<Location>();
	private List<Location> path = new ArrayList<Location>();
	private int nodecount =0 ;	
	
	public void search(GameCharacter gc, Location start){
		AStarHeuristicNodeComparator sorter = new AStarHeuristicNodeComparator(gc);
		open = new PriorityQueue<Location>(20, sorter);
		start.setDistanceTravelled(gc,0);
		open.add(start);
		while(!open.isEmpty()){
			Location node = open.poll();
			if (node.isGoalNode()){
				while(node.getParent(gc) != null){
					path.add(node);
					node = node.getParent(gc);
				}
				path.add(node);
				Collections.reverse(path);
				
				travel(gc, path);
				break;
			}
			pushSuccessors(gc,node);
			closed.add(node);			
		}
	}
	
	public void pushSuccessors(GameCharacter gc, Location node){
		Location[] children = node.children();
		for (int i = 0; i < children.length; i++) {
			nodecount++;
			Location child = children[i];
			float score = HeuristicCalculator.getHeuristicValue(node.getDistanceTravelled(gc) + node.calculateDistance(child), child.getApproximateDistanceFromGoal()
					//, child.getTerrain(), child.getDanger()
					);
			
			//System.out.println(gc.getName() + ": is at " + node.getName() + " : " + child.getName() + ": " + score);
			if ((open.contains(child) || closed.contains(child)) && child.getScore(gc) < score){
				continue;
			}else{
				open.remove(child);
				closed.remove(child);
				child.setParent(gc,node);		
				child.setDistanceTravelled(gc, node.getDistanceTravelled(gc) + node.calculateDistance(child));
				open.add(child);
			}
		}
	}
	
	public void travel(GameCharacter gc, List<Location> path){
		for(int i=0;i<path.size();i++){
			
			//set new location
			gc.getCurrLoc().removeFromCharacterList(gc.getName());
			gc.setCurrLoc(path.get(i));
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
					break;
				}
				else continue;			
			}
			
			if(i<path.size()-1){
				try {
					//sleep for distance between the two locations....
					Thread.sleep(path.get(i).calculateDistance(path.get(i+1))*15);
					if(path.get(i).checkForMainCharacter()){
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
			}
		}
	}
	
}
