package gmit;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MyDomParser {

	private static Map <Integer, Location> fullLocationList = new HashMap<Integer, Location>();
	static Location currLocation = new Location();
	

	
	public Map<Integer, Location>  getFullLocationList (){
		return fullLocationList;
	}
	
	public void parse(){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("network.xml");
			
			//get locations
			NodeList locationsList = doc.getElementsByTagName("location");			
			for(int i=0; i<locationsList.getLength();i++){
				
				//create new node
				Node l = locationsList.item(i);
				try {
					Location loc = (Location) Class.forName("gmit.Location").newInstance();
					
					//get attributes
					if(l.getNodeType()==Node.ELEMENT_NODE){
						Element location = (Element) l;
						String name = location.getAttribute("name");
						int id = Integer.parseInt(location.getAttribute("id"));
						int approxDistanceFromGoal = Integer.parseInt(location.getAttribute("approxDistanceFromGoal"));
						String goalNodeString = location.getAttribute("goalNode");
						int healthBoost = Integer.parseInt(location.getAttribute("healthBoost"));
						
						//set goal node
						switch(goalNodeString){
							case "true":
								loc.setGoalNode(true);
								break;
							default:
								loc.setGoalNode(false);
								break;	
						}
						
						//set id
						loc.setId(id);
						
						//set name of location
						loc.setName(name);
						
						//set distance
						loc.setApproximateDistanceFromGoal(approxDistanceFromGoal);
						
						//set healthboost
						loc.setHealthBoost(healthBoost);
						
						//get description elements
						NodeList descList = location.getElementsByTagName("description");
						for(int j=0; j<descList.getLength();j++){
							Node d = descList.item(j);

							//set description
							if(d.getNodeType()==Node.ELEMENT_NODE){
								Element description = (Element) d;
								loc.setDescription(description.getTextContent());
							}
						}
						
						//add to list
						fullLocationList.put(id, loc);
					}
					
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
					
			//get edges and create network of locations
			NodeList edgesList = doc.getElementsByTagName("edge");
			for(int i=0; i<edgesList.getLength();i++){
				Node ed = edgesList.item(i);

				if(ed.getNodeType()==Node.ELEMENT_NODE){
					Element edge = (Element) ed;

					String direction = edge.getAttribute("direction");
					
					
					int distance = Integer.parseInt(edge.getAttribute("distance"));
					int fromID = Integer.parseInt(edge.getAttribute("from"));
					int toID = Integer.parseInt(edge.getAttribute("to"));

					Exit exit = (Exit) Class.forName("gmit.Exit").newInstance();
					
					//set direction
					exit.setDirection(Direction.valueOf(direction));
					
					//set the distance
					exit.setDistance(distance);
					
					//set location
					if(fullLocationList.containsKey(toID)){
						exit.setLoc(fullLocationList.get(toID));
					}
					
					//add to current location
					if(fullLocationList.containsKey(fromID)){
						//fullLocationList.get(fromID).addToExitList(Direction.valueOf(direction), exit);
						fullLocationList.get(fromID).addToExitList(fullLocationList.get(toID), exit);
					}

				}
			}
			
			//get game characters
			NodeList gameCharList = doc.getElementsByTagName("game-character");
			for(int i=0; i<gameCharList.getLength();i++){
				
				//create new node
				Node gc = gameCharList.item(i);
			
				//get attributes
				if(gc.getNodeType()==Node.ELEMENT_NODE){
					Element gameCharacter = (Element) gc;
					
					//create instance of game character
					GameCharacter gameChar = (GameCharacter) Class.forName(gameCharacter.getAttribute("type")).newInstance();
					
					//set search algorithm
					gameChar.setSearch((SearchAlgorithm) Class.forName(gameCharacter.getAttribute("search")).newInstance());

					//get/set name
					String name = gameCharacter.getAttribute("name");
					gameChar.setName(name);
					
					//aggressiveness and observence and accuracy
					int aggressiveness = Integer.parseInt(gameCharacter.getAttribute("aggressiveness"));
					int observance = Integer.parseInt(gameCharacter.getAttribute("observance"));
					int accuracy = Integer.parseInt(gameCharacter.getAttribute("accuracy"));
					gameChar.setAggressiveness(aggressiveness);
					gameChar.setObservance(observance);
					gameChar.setAccuracy(accuracy);
					
					//get starting location
					int start = Integer.parseInt(gameCharacter.getAttribute("startingLocation"));
					//set starting location
					if(fullLocationList.containsKey(start)){
						gameChar.setCurrLoc(fullLocationList.get(start));
					}
					gameChar.getCurrLoc().addToCharacterList(gameChar.getName(), gameChar);
					
					Thread t = new Thread(gameChar);
					t.start();
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
	
}
