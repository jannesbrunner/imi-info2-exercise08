package imi.calculator;



public class CalcEngine {

	private Postfix postfix; // For Internal RPN Calculation
	private String lastDP; // Holds the last calculatet Value
	
	
	public CalcEngine() {
		// This will do the magic
		 this.postfix = new Postfix();
		 this.lastDP = "0";
	}
	
	

	// Engine Stuff-------------------
	
	public String getLastDisplayValue() {
		return lastDP;
	}
	
	
	// Takes a math expression in Dec display style and returns the solution as a new display value
	public String getDecSolution(String displayValue) {
		
		if(displayValue.equals(lastDP)) {
			return displayValue;
		} else {
		displayValue = postfix.infixToPostfix(displayValue);
		Integer solution = (int) postfix.evaluate(displayValue);
		lastDP = solution.toString();
		return solution.toString();
		}
	}
	// Takes a math expression in Hex display style and returns the solution as a new display value
	public String getHexSolution(String displayValue) {
		// Converting from Hex to Dec for internal calculation
		if(displayValue.equals(lastDP)) {
			return displayValue;
		} else {
		
		displayValue = getDecDisplayValue(displayValue);
		
		displayValue = postfix.infixToPostfix(displayValue);
		Integer solution = (int) postfix.evaluate(displayValue);
		String HexSolution = getHexDisplayValue(solution.toString());
		lastDP = getHexDisplayValue(solution.toString());
		return HexSolution;
		}
	}

	// Transcribes the current Display Value from Dec to Hex
	public String getHexDisplayValue(String displayValue) {
		String array[] = displayValue.split("\\s");
		for (int i = 0; i < array.length; i++) {
			if (array[i].matches("\\d*")) {
				int value = Integer.parseInt(array[i]);
				array[i] = Integer.toHexString(value);
			}
		}

		displayValue = "";
		for (int i = 0; i < array.length; i++) {
			if (i == array.length -1) { displayValue += array[i]; } 
			else { displayValue += array[i] + " "; }
		}
		return displayValue;

	}
	
	// Transcribes the current Display Value from Hex to Dec
	public String getDecDisplayValue(String displayValue) {
		String array[] = displayValue.split("\\s");
		for (int i = 0; i < array.length; i++) {
			if (array[i].matches("(?:0[xX])?[0-9a-fA-F]+")) {
				Integer dezi = Integer.parseInt(array[i], 16);
				array[i] = dezi.toString();
				
			}
		}
		displayValue = "";
		for (int i = 0; i < array.length; i++) {
			if (i == array.length -1) { displayValue += array[i]; } 
			else { displayValue += array[i] + " "; }
		}
		return displayValue;
		
	}
	
	// returns the last item in the Display Value times -1 
	public String togglePosNeg(String displayValue) {
		
		
		
			Integer number = Integer.parseInt(displayValue);
			number = number *(-1);
			displayValue = Integer.toString(number);
		
		
		
		
		return displayValue;
	}
	
	


	
	
	// ------------------------------------ // 
	
	// Formal Stuff...
		public String getTitle() {
			
			return "Calculator 2.0";
		}

		public String getAuthor() {
			
			return "Katharina Wunder & Jannes Brunner";
		}
		
		public String getVersion() {
			return "v 1.0";
		}



		
	
	
	
	
	
	
	
	
	

}
