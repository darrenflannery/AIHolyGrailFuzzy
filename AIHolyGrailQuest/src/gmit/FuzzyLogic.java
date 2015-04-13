	package gmit;

	import net.sourceforge.jFuzzyLogic.*;

	public class FuzzyLogic {
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
	        //System.out.println("Chance of attack:" + fis.getVariable("chanceOfAttack").getValue()); 
	        return fis.getVariable("chanceOfAttack").getValue();
	    }
	    
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
	        //System.out.println("Attacking Power:" + fis.getVariable("Attack").getValue()); 
	        return fis.getVariable("Attack").getValue();
	    }
	}
