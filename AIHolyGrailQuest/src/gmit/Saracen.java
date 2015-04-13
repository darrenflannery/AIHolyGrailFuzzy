package gmit;

import java.util.Random;

public class Saracen extends AbstractGameCharacter{

private float lifeForce = 100.00f;

	
	public void run() {
		while (lifeForce > 0.00f){
			Random rand = new Random();
			int  n = rand.nextInt(5000) + 100;
			
			try {
				Thread.sleep(n);
				//System.out.println(this.getName() + " is awake");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Location start = this.currLoc;			
			SearchAlgorithm sa = this.getSearch();
			sa.search(this, start);

			if(this.currLoc.isGoalNode()){
				break;
			}
			
			if(this.currLoc.checkForMainCharacter()){
				fight();
				break;
			}
		}
		
	}
	
	public void fight(){
		GamePlay.myChar.setAttacked(this);
		GamePlay.myChar.setFighting(true);
		System.out.println(this.getName() + " attacked you, choose to defend yourself (fight) or try run away (move)");
	}

}
