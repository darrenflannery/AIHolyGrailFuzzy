	package gmit;

	import net.sourceforge.jFuzzyLogic.*;

	public class FuzzyLogic {
		//used to determine chance of attack if enemy character is in your location
	    public double fuzzyChanceOfAttack(int aggressiveness, int observance) {
	        // Load from 'FCL' file
	        String fileName = "fcl/chanceOfAttack.fcl";
	        FIS fis = FIS.load(fileName,true);

	        // Error while loading?
	        if( fis == null ) { 
	            System.err.println("Can't load file: '" + fileName + "'");
	            return 0;
	        }

	        //FunctionBlock functionBlock = fis.getFunctionBlock("chanceOfAttack");

	        // Set inputs
	        fis.setVariable("aggressiveness", aggressiveness);
	        fis.setVariable("observance", observance);

	        // Evaluate
	        fis.evaluate();
	       
	     // Show output variable
	        return fis.getVariable("chanceOfAttack").getValue();
	    }
	    
	    //Used to determine attack power of enemy characters
	    public double Attack(int aggressiveness, int accuracy) {
	        // Load from 'FCL' file
	        String fileName = "fcl/Attack.fcl";
	        FIS fis = FIS.load(fileName,true);

	        // Error while loading?
	        if( fis == null ) { 
	            System.err.println("Can't load file: '" + fileName + "'");
	            return 0;
	        }

	        //FunctionBlock functionBlock = fis.getFunctionBlock("chanceOfAttack");

	        // Set inputs
	        fis.setVariable("aggressiveness", aggressiveness);
	        fis.setVariable("accuracy", accuracy);

	        // Evaluate
	        fis.evaluate();
	       
	     // Show output variable
	        return fis.getVariable("Attack").getValue();
	    }
	}
