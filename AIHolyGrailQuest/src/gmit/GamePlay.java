package gmit;
import java.util.Scanner;
public class GamePlay {
	
	private static Scanner sc = new Scanner(System.in);
	
	//create main character
	static MyCharacter myChar = new MyCharacter();
	private static MyDomParser parser = new MyDomParser();
	
	public static void main(String[] args) {
		
		System.out.println("It is the year 1187 and the crusader army of the Kingdom of Jerusalem has been all but destroyed "
				+ "by Saladin at the Horns of Hattin.\nAs a Templar knight, having survived the battle,"
				+ "you have been entrusted with going to Jerusalem, \ntaking the Holy Grail from the Temple "
				+ "Mount and brining it safely to Cyprus\n\n"
				
				+ "You are have reached Jerusalem and a saracen army is searchin for you on their way to Cyprus,"
				+ "\nReturn the Holy Grail to Cyprus by avoiding the saracen Army\n\n");	
		//parse XML file
		parser.parse();
		
		start();
	}
	
	public static void start(){
		//set the starting location of main character
		myChar.setCurrLoc(parser.getFullLocationList().get(1));
		myChar.look();
		
		//while character is still alive
		while(myChar.getLifeForce()>=1){
			input();
		}
	}
	
	public static void input(){
		System.out.println("What would you like to do? (move, look, fight)?");
			String in = sc.nextLine().toLowerCase();
			switch(in){
				case "move":
					if(myChar.isFighting())
					{
						FuzzyLogic fl = new FuzzyLogic();
						int attack = (int) fl.Attack(myChar.getAttacked().getAggressiveness(), myChar.getAttacked().getAccuracy());
						myChar.setLifeForce(myChar.getLifeForce()-attack);
						System.out.println("You got away, "+ myChar.getAttacked().getName() + " did " + attack + " damage to your health!");
			
						if(myChar.getLifeForce()>0){
							System.out.println("Your new health is " + GamePlay.myChar.getLifeForce());
						}
						else{
							System.out.println("Your health is 0");
							System.out.println("You're Dead");
							System.exit(0);
						}
					}
					move();
					if(myChar.getCurrLoc().isGoalNode()){
						System.out.println("You Win!");
						System.exit(0);
					}
					myChar.setFighting(false);
					break;
				case "look":
					MyCharacter.look();
					break;
				case "fight":
					fight();
					break;
				default:
					System.out.println("Invalid input...");
					break;    		 
		}
	}
	
	
	public static void move(){
		boolean exitExists = false;
		String m="";
		System.out.println("Which direction?");
		m = sc.nextLine();
		for (Direction d : Direction.values()) {
			if (d.name().equalsIgnoreCase(m)) 
			{
				exitExists=true; 
			}
		}
		if(exitExists){
			Direction dir = Direction.valueOf(m.toUpperCase());
			boolean hasExit = myChar.move(dir);
			if(!hasExit){
				System.out.println("Cant go that direction, try again...");
				move();
			}
		}
		else{
			System.out.println("You cant go that way, try again...");
			move();
		}
	}
	
	public static void fight(){
		System.out.println("Who do you want to fight?");
		String input = sc.nextLine();
		if(myChar.getCurrLoc().getCharList().containsKey(input)){
			myChar.setAttacked(myChar.getCurrLoc().getCharList().get(input));
			myChar.fight(myChar.getCurrLoc().getCharList().get(input));
		}
		else{
			System.out.println(input + " is not in this location");
		}
	}
	
}