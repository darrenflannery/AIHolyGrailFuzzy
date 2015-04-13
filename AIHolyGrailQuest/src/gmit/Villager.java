package gmit;

public class Villager extends AbstractGameCharacter {

private float lifeForce = 100.00f;
	
	public void run(){
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (lifeForce > 0.00f){	
			Location start = this.currLoc;
			Wander wanderSearch = new Wander();
			wanderSearch.search(this, start);
		}
		
		System.out.println(this.getName() + " just died....");
	}
	
}
