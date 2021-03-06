package imi.calculator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.sun.corba.se.impl.encoding.CodeSetComponentInfo;

/**
 * A graphical user interface for the calculator. No calculation is being done
 * here. This class is responsible just for putting up the display on screen. It
 * then refers to the "CalcEngine" to do all the real work.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2008.03.30
 */
public class HexUserInterface implements ActionListener {

	private CalcEngine calc;
	private boolean showingAuthor;

	private JFrame frame;
	private JTextField display;
	private JLabel status;
	private Component[] hexComponents;
	private Component[] operators;
	private Component[] setOperators;
	private Component[] numbers;
	private Component[] notSetComponents;
	private boolean isHex;
	private JToggleButton toggleButton;
	private JToggleButton setToggleButton;
	private String displayValue;
	private boolean isSetMode;
	private String setOperation;

	private boolean operatorPressed;

	/**
	 * Create a user interface.
	 * 
	 * @param engine
	 *            The calculator engine.
	 */
	public HexUserInterface(CalcEngine engine) {
		displayValue = "";
		calc = engine;
		showingAuthor = true;
		operatorPressed = false;
		makeFrame();
		frame.setVisible(true);
		isSetMode = false;
		setOperation = "";

	}

	protected void makeFrame() {

		hexComponents = new Component[7];
		frame = new JFrame(calc.getTitle());

		JPanel contentPane = (JPanel) frame.getContentPane();
		contentPane.setLayout(new BorderLayout(8, 8));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

		display = new JTextField();
		contentPane.add(display, BorderLayout.NORTH);

		JPanel buttonPanel = new JPanel(new GridLayout(6, 5));

		addButton(buttonPanel, "7");
		addButton(buttonPanel, "8");
		addButton(buttonPanel, "9");
		toggleButton = new JToggleButton("Hex", false);
		toggleButton.addActionListener(this);
		buttonPanel.add(toggleButton);
		addButton(buttonPanel, "/");
		addButton(buttonPanel, "CL");

		addButton(buttonPanel, "4");
		addButton(buttonPanel, "5");
		addButton(buttonPanel, "6");
		addButton(buttonPanel, "(");
		addButton(buttonPanel, "*");
		addButton(buttonPanel, "CE");

		addButton(buttonPanel, "1");
		addButton(buttonPanel, "2");
		addButton(buttonPanel, "3");
		addButton(buttonPanel, ")");
		addButton(buttonPanel, "+");

		addButton(buttonPanel, "?");

		addButton(buttonPanel, "n");
		addButton(buttonPanel, "0");

		addButton(buttonPanel, "+/-");
		addButton(buttonPanel, "^");

		addButton(buttonPanel, "-");
		addButton(buttonPanel, "=");

		addButton(buttonPanel, "A");
		addButton(buttonPanel, "B");
		addButton(buttonPanel, "C");
		addButton(buttonPanel, "D");
		addButton(buttonPanel, "E");
		addButton(buttonPanel, "F");

		setToggleButton = new JToggleButton("Set", false);
		setToggleButton.addActionListener(this);
		buttonPanel.add(setToggleButton);
		addButton(buttonPanel, ",");
		addButton(buttonPanel, "{ ");
		addButton(buttonPanel, " }");
		addButton(buttonPanel, "∩");
		addButton(buttonPanel, "∪");

		Component[] allComponents = buttonPanel.getComponents();
		hexComponents[0] = allComponents[24];
		hexComponents[1] = allComponents[25];
		hexComponents[2] = allComponents[26];
		hexComponents[3] = allComponents[27];
		hexComponents[4] = allComponents[28];
		hexComponents[5] = allComponents[29];
		hexComponents[6] = allComponents[20];

		notSetComponents = new Component[9];

		notSetComponents[0] = allComponents[3];
		notSetComponents[1] = allComponents[5];
		notSetComponents[2] = allComponents[9];
		notSetComponents[3] = allComponents[11];
		notSetComponents[4] = allComponents[16];
		notSetComponents[5] = allComponents[20];
		notSetComponents[6] = allComponents[4];
		notSetComponents[7] = allComponents[15];
		notSetComponents[8] = allComponents[21];
		

		numbers = new Component[10];

		numbers[0] = allComponents[0];
		numbers[1] = allComponents[1];
		numbers[2] = allComponents[2];
		numbers[3] = allComponents[6];
		numbers[4] = allComponents[7];
		numbers[5] = allComponents[8];
		numbers[6] = allComponents[12];
		numbers[7] = allComponents[13];
		numbers[8] = allComponents[14];
		numbers[9] = allComponents[19];

		operators = new Component[4];

		operators[0] = allComponents[4];
		operators[1] = allComponents[10];
		operators[2] = allComponents[16];
		operators[3] = allComponents[22];

		setOperators = new Component[6];

		setOperators[0] = allComponents[32];
		setOperators[1] = allComponents[33];
		setOperators[2] = allComponents[34];
		setOperators[3] = allComponents[35];
		setOperators[4] = allComponents[31];
		setOperators[5] = allComponents[18];

		for (Component tempComp : hexComponents) {
			tempComp.setEnabled(false);
		}

		for (Component tempComp : setOperators) {
			tempComp.setEnabled(false);
		}

		hexComponents[6].setEnabled(true);

		for (Component tempOperator : operators) {
			tempOperator.setEnabled(false);
		}

		contentPane.add(buttonPanel, BorderLayout.CENTER);

		status = new JLabel(calc.getAuthor());
		contentPane.add(status, BorderLayout.SOUTH);

		frame.pack();
	}

	public void checkOperators() {
		if (operatorPressed) {
			greyOperators("off");
		} else {
			greyOperators("on");
		}
	}

	// @override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();

		switch (command) {
		case "=":

			try {
				if (isSetMode) {
					displayValue = setOperations(displayValue, setOperation);
				} else {

					// Error Handling.
					String displayTemp = displayValue;
					displayTemp = displayTemp.replace("( ", "");
					displayTemp = displayTemp.replace(" )", "");
					if ((displayTemp.length() % 2) == 0 || displayTemp.length() == 1 || displayTemp.equals("")) {
						break;
					}

					if (!isHex)

						displayValue = calc.getDecSolution(displayValue);

					if (isHex)
						displayValue = calc.getHexSolution(displayValue);
					break;
				}
			} catch (Exception e) {
				displayValue = "ERROR - " + e;
				//e.printStackTrace();
				break;

			}
		case "?":
			showInfo();
			break;

		case "CL":
			displayValue = "";
			operatorPressed = true;

			break;

		case "CE":
			isSetMode = false;
			if (displayValue.equals("")) {
				break;
			}
			displayValue = displayValue.substring(0, displayValue.length() - 1);
			operatorPressed = true;

			break;

		case "Hex":
			if (!displayValue.equals("")) {
				switchHex();
				displayValue = calc.getHexDisplayValue(displayValue);
			} else {
				switchHex();
			}
			break;

		case "Dec":
			if (!displayValue.equals("")) {
				switchHex();
				displayValue = calc.getDecDisplayValue(displayValue);
			} else {
				switchHex();
			}
			break;

		case "+":

			displayValue += " " + command + " ";

			operatorPressed = true;
			checkOperators();

			break;

		case "-":
			displayValue += " " + command + " ";
			operatorPressed = true;
			checkOperators();
			setOperation = command;
			break;

		case "*":
			displayValue += " " + command + " ";
			operatorPressed = true;
			checkOperators();

			break;

		case "/":
			displayValue += " " + command + " ";
			operatorPressed = true;
			checkOperators();
			break;

		case "+/-":

			if (displayValue.matches("^[-+]?(?:0[xX])?[0-9a-fA-F]+")) {
				displayValue = calc.togglePosNeg(displayValue);
				break;
			}

			break;

		case "(":
			displayValue += command + " ";
			operatorPressed = false;
			checkOperators();
			break;

		case ")":
			displayValue += " " + command;
			operatorPressed = false;
			checkOperators();
			break;

		case "^":
			displayValue += " " + command + " ";
			operatorPressed = true;
			checkOperators();
			break;

		case ",":
			displayValue += command + " ";
			break;

		case "∩":
			displayValue += " " + command + " ";
			setOperation = command;
			break;

		case "∪":
			displayValue += " " + command + " ";
			setOperation = command;
			break;

		case "Set":

			switchSetMode();
			break;
			
		case "n":

			displayValue = calc.getNofSet(displayValue);
			break;

		default:

			displayValue += command;
			operatorPressed = false;
			checkOperators();

			for (Component temp : operators) {
				temp.setEnabled(true);
			}

			break;
		}

		redisplay();

	}

	// @override
	protected void redisplay() {

		display.setText("" + displayValue);

	}

	private void switchHex() {

		if (isHex) {
			toggleButton.setText("Hex");
			for (Component tempComp : hexComponents) {
				tempComp.setEnabled(false);

			}

			hexComponents[6].setEnabled(true);

			isHex = false;
		}

		else {
			toggleButton.setText("Dec");
			for (Component tempComp : hexComponents) {
				tempComp.setEnabled(true);
			}
			hexComponents[6].setEnabled(false);

			isHex = true;
		}
	}

	private void switchSetMode() {

		if (!isSetMode) {

			displayValue = "";

			for (Component tempComp : setOperators) {
				tempComp.setEnabled(true);
			}

			for (Component temp : operators) {
				temp.setEnabled(false);
			}

			for (Component temp : notSetComponents) {
				temp.setEnabled(false);
			}

			isSetMode = true;
		} else {
			displayValue = "";

			for (Component tempComp : setOperators) {
				tempComp.setEnabled(false);
			}

			for (Component temp : operators) {
				temp.setEnabled(true);
			}

			for (Component temp : notSetComponents) {
				temp.setEnabled(true);
			}

			for (Component temp : numbers) {
				temp.setEnabled(true);
			}

			isSetMode = false;
		}

	}

	public String setOperations(String displayString, String setOperator) {

		String newDisplayValue = "";
		switch (setOperator) {
		case "∩":
			newDisplayValue = calc.intersectionSets(displayString);
			break;
		case "∪":
			newDisplayValue = calc.unionSets(displayString);
			break;
		case "-":
			newDisplayValue = calc.subtractSets(displayString);
			break;

		default:
			newDisplayValue = "Calculation error! Wrong operator! You suck!";
			break;
		}

		return newDisplayValue;
	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

	private void addButton(Container panel, String buttonText) {
		JButton button = new JButton(buttonText);
		button.addActionListener(this);
		panel.add(button);
	}

	private void showInfo() {
		if (showingAuthor)
			status.setText(calc.getVersion());
		else
			status.setText(calc.getAuthor());

		showingAuthor = !showingAuthor;
	}

	private void greyOperators(String state) {
		if (state.equals("off")) {
			for (Component temp : operators) {
				temp.setEnabled(false);

			}
		}

		else {
			for (Component temp : operators) {
				temp.setEnabled(true);

			}
		}
	}
}
