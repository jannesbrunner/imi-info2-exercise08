package imi.calculator;

public class Postfix {

	private double rhs;
	private double lhs;

	public Postfix() {

	}

	public double evaluate(String pfx) {

		IStack<String> ourStack = new Stack<String>();

		String[] parts = pfx.split(" ");
		for (int i = 0; i < parts.length; i++) {

			if (parts[i].matches("\\d+")) {

				ourStack.push((parts[i]));

			}

			else if (parts[i].matches("[\\+\\*\\/\\-\\^]")) {
				
				rhs = Double.valueOf((ourStack.pop()));
				lhs = Double.valueOf((ourStack.pop()));

				double resultTemp = 0;

				switch (parts[i]) {
				case "*":
					resultTemp = lhs * rhs;
					break;
				case "+":
					resultTemp = lhs + rhs;
					break;
				case "-":
					resultTemp = lhs - rhs;
					break;
				case "/":
					if (rhs == 0){
						throw new RuntimeException();
					}
					double doubleTemp = lhs / (double) rhs;
					resultTemp = Math.round(doubleTemp);
					break;
				case "^":
					resultTemp = Math.pow(lhs, rhs);
					break;
				}

				String resultString =  Double.toString(resultTemp);
				ourStack.push(resultString);
			}

		}
		String result = ourStack.pop();
		double resultDouble = Double.valueOf(result);
		int resultInt = (int) resultDouble;
		
		return resultInt;

	}

	public String infixToPostfix(String ifx) {

		IStack<String> ourStack = new Stack<String>();
		String result = "";

		String[] parts = ifx.split(" ");

		for (int i = 0; i < parts.length; i++) {

			// If number, add to result
			if (parts[i].matches("\\d+")) {
				result += parts[i] + " ";
			}

			else if (parts[i].equals("(")) {
				ourStack.push((parts[i]));
			}

			else if (parts[i].equals(")")) {

				while (!ourStack.top().equals("(")) {
					result += ourStack.top() + " ";
					ourStack.pop();
				}
				if (ourStack.isEmpty()) {
					throw new RuntimeException();
				}
				ourStack.pop();
			}

			else if (parts[i].matches("[\\+\\*\\/\\-\\^]")) {
				
				int curr = precedence(parts[i]);

				while (!ourStack.isEmpty()
						&& (((precedence(ourStack.top()) > curr) 
								|| ((ourStack.top().matches("[\\-\\/\\^]") && precedence(ourStack.top()) == curr))))) {
					result += ourStack.top() + " ";
					ourStack.pop();
				}

				ourStack.push(parts[i]);
			}

		}

		while (!ourStack.isEmpty()) {
			result = result + ourStack.top() + " ";
			ourStack.pop();
		}
		return result;
	}

	private int precedence(String input) {

		switch (input) {
		case "+":
		case "-":
			return 1;
		case "*":
		case "/":
			return 2;
		case "^":
			return 3;
		default:
			return 0;
		}

	}
}
