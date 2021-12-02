/*
 * This is a coding project on creating a Basic Calculator using JavaFX 12
 * (using LinkedList)
 * 
 * Version: 5
 */

package calculators5;

import java.text.NumberFormat;
import java.util.LinkedList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BasicCalculator5 extends Application {
	
	/* Top Pane */
	Label lblCalculator;
	TextField txtInput;
	
	/* Center pane */
	//1st row buttons
	Button btnOFF;
	Button btnLBracket;
	Button btnRBracket;
	Button btnON;
	
	//2nd row buttons
	Button btnClear;
	Button btnBackSpace;
	Button btnPercent;
	Button btnAnswer; 	//display result to screen and truncate .0 if currentNumber is 
						//a whole number of variable strFinalResult
	//3rd row buttons
	Button btnMplus;	//add to calculator memory
	Button btnMminus;	//minus from calculator memory
	Button btnMRecall; //show result of operations of M+/M-, clear it on 2nd click
	Button btnDivide;
	
	//4th row buttons
	Button btn7;
	Button btn8;
	Button btn9;
	Button btnMultiply;
	
	//5th row buttons
	Button btn4;
	Button btn5;
	Button btn6;
	Button btnSubtract;
	
	//6th row buttons
	Button btn1;
	Button btn2;
	Button btn3;
	Button btnAdd;
	
	//7th row buttons
	Button btnSign;
	Button btn0;
	Button btnDot;
	Button btnEqual;

	/*
	 * class/global variables: ONLY primitive data, for e.g. double, are 
	 * initialized, each with their own default value, and not objects such 
	 * as String 
	 */
	
	//store current text in a real time text variable for debugging purposes
	private String strRealTimeNumber = ""; //must be initialized to empty string
	
	private String strCurrentText = ""; //text currently on display (cannot be null)
	private boolean isDotExist; 		//dot does not exist initially
	private boolean isOperator; 		//operator does not exist initially
	private boolean isDigitExist; 		//digit(s) not exist initially
	private boolean isMplus;			//set to true is btnMplus is clicked
	private boolean isMminus;			//set to true is btnMminus is clicked
	private int opCountSign = 0;		//count the number of operators "-" when 
										//multiplying by -1, AND the number of 
										//operators
	private int countLBracket = 0;
	private int countRBracket = 0;
	
	//double value stored in calculator memory after memory-type operations are
	//performed
	private double dblCalMemory;
	
	//to store final result which can be used (as a new input value) for further
	//computations
	private String strAnswer = "";
	
	//NumberFormat nf class variable with setting to 11 decimal places
	private static NumberFormat nf = NumberFormat.getNumberInstance();
	
	public static void main(String[] args) {
		nf.setMaximumFractionDigits(11);	//set to 11 decimal places on screen
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		/* Top portion of a BorderPane that contains a VBox with label and input
		 *  field each of which is placed in a HBox
		 * */
		
		/* Label lblCalculator */
		//create the label for Calculator Name 
		lblCalculator = new Label("Basic Calculator FX5");
		
		/* TextField txtInput */
		//create the calculator input text field
		txtInput = new TextField();
		
		//set alignment of input numbers to the right
		txtInput.setAlignment(Pos.CENTER_RIGHT);
		
		//set the preferred size of the text field in columns (how long the control)
		txtInput.setPrefColumnCount(50);
		
		//do not allow input to be keyed in using other sources except the buttons
		txtInput.setEditable(false );
		
		//create a HBox to contain lblCalculator
		HBox paneCalculatorLabel = new HBox(lblCalculator);
		
		//aligned label to the right of HBox
		paneCalculatorLabel.setAlignment(Pos.CENTER_RIGHT); 
		
		//fix the width of HBox
		paneCalculatorLabel.setMinWidth(200); 
		paneCalculatorLabel.setMaxWidth(200);
		
		//create a HBox to contain txtInput 
		HBox paneInputText = new HBox(txtInput);
		paneInputText.setMinWidth(200);	
		paneInputText.setMaxWidth(200);
		
		//create a VBox to contain 2 HBoxes that contain label and text field 
		//respectively
		VBox paneTop = new VBox(10, paneCalculatorLabel, paneInputText);
		paneTop.setPadding(new Insets(10)); //all spacings done by VBox
		
		/* Center portion of a BorderPane that a GridPane with contains 24 buttons */
			
		/* 1st row buttons */
		//create buttons for 1st row
		btnOFF = new Button("OFF");			
		btnLBracket = new Button("(");
		btnRBracket = new Button(")");
		btnON = new Button("ON");
		
		//set minimum width of button
		btnOFF.setMinWidth(42);
		btnLBracket.setMinWidth(42);
		btnRBracket.setMinWidth(42);
		btnON.setMinWidth(42);
		
		//Action events for 1st row button 
		btnOFF.setOnAction(e -> btnSwitch_Click(btnOFF.getText()));
		btnLBracket.setOnAction(e -> btnBracket_Click(btnLBracket.getText()));
		btnRBracket.setOnAction(e -> btnBracket_Click(btnRBracket.getText()));
		btnON.setOnAction(e -> btnSwitch_Click(btnON.getText()));	

		/* 2nd row buttons */
		//create buttons for 2nd row
		btnClear = new Button("CLR");
		btnBackSpace = new Button("DEL");
		btnAnswer = new Button("ANS");
		btnPercent = new Button("%");
		
		//set minimum width of button
		btnClear.setMinWidth(42);
		btnBackSpace.setMinWidth(42);
		btnAnswer.setMinWidth(42);
		btnPercent.setMinWidth(42);
		
		//Action events for 1st row button 
		btnClear.setOnAction(e -> btnClear_Click());
		btnBackSpace.setOnAction(e -> btnBackSpace_Click());
		btnAnswer.setOnAction(e -> btnAnswer_Click());
		btnPercent.setOnAction(e -> btnPercent_Click());	

		/* 3rd row buttons */
		//create buttons for 3rd row
		btnMplus = new Button("M+");
		btnMminus = new Button("M-");
		btnMRecall = new Button("MRC");
		btnDivide = new Button("\u00F7"); //unicode for divide symbol: \u00F7
		
		//set minimum width of button
		btnMplus.setMinWidth(42);
		btnMminus.setMinWidth(42);
		btnMRecall.setMinWidth(42);
		btnDivide.setMinWidth(42);
		
		//Action event on buttons
		btnMplus.setOnAction(e -> btnMemoryOperator_Click(btnMplus.getText()));
		btnMminus.setOnAction(e -> btnMemoryOperator_Click(btnMminus.getText()));
		btnMRecall.setOnAction(e -> btnMRecall_Click());
		btnDivide.setOnAction(e -> btnOperator_Click("/"));
		
		/* 4th row buttons */
		//create buttons for 4th row
		btn7 = new Button("7");
		btn8 = new Button("8");
		btn9 = new Button("9");
		btnMultiply = new Button("\u00D7"); //unicode for multiply symbol: \u00D7
		
		//set minimum width of button
		btn7.setMinWidth(42);
		btn8.setMinWidth(42);
		btn9.setMinWidth(42);
		btnMultiply.setMinWidth(42);
		
		//Action event on buttons
		btn7.setOnAction(e -> btnDigit_Click(btn7.getText()));
		btn8.setOnAction(e -> btnDigit_Click(btn8.getText()));
		btn9.setOnAction(e -> btnDigit_Click(btn9.getText()));
		btnMultiply.setOnAction(e -> btnOperator_Click("x"));
		
		/* 5th row buttons */
		//create buttons for 5th row
		btn4 = new Button("4");
		btn5 = new Button("5");
		btn6 = new Button("6");
		btnSubtract = new Button("-");
		
		//set minimum width of button
		btn4.setMinWidth(42);
		btn5.setMinWidth(42);
		btn6.setMinWidth(42);
		btnSubtract.setMinWidth(42);
			
		//Action event on buttons
		btn4.setOnAction(e -> btnDigit_Click(btn4.getText()));
		btn5.setOnAction(e -> btnDigit_Click(btn5.getText()));
		btn6.setOnAction(e -> btnDigit_Click(btn6.getText()));
		btnSubtract.setOnAction(e -> btnOperator_Click(btnSubtract.getText()));
		
		/* 6th row buttons */
		//create buttons for 6th row
		btn1 = new Button("1");
		btn2 = new Button("2");
		btn3 = new Button("3");
		btnAdd = new Button("+");
		
		//set minimum width of button
		btn1.setMinWidth(42);
		btn2.setMinWidth(42);
		btn3.setMinWidth(42);
		btnAdd.setMinWidth(42);
		
		//Action event on buttons
		btn1.setOnAction(e -> btnDigit_Click(btn1.getText()));
		btn2.setOnAction(e -> btnDigit_Click(btn2.getText()));
		btn3.setOnAction(e -> btnDigit_Click(btn3.getText()));
		btnAdd.setOnAction(e -> btnOperator_Click(btnAdd.getText()));
		
		/* 7th row buttons */
		//create buttons for 7th row
		btnSign = new Button("+/-");
		btn0 = new Button("0");
		btnDot = new Button(".");
		btnEqual = new Button("=");
		
		//set minimum width of buttons
		btnSign.setMinWidth(42);
		btn0.setMinWidth(42);
		btnDot.setMinWidth(42);
		btnEqual.setMinWidth(42);
		
		//Action event on buttons
		btnSign.setOnAction(e -> btnSign_Click());
		btn0.setOnAction(e -> btnDigit_Click(btn0.getText()));
		btnDot.setOnAction(e -> btnDot_Click(btnDot.getText()));
		btnEqual.setOnAction(e -> btnEqual_Click());
		
		//create GridPane grid and add all these buttons to each of its 7 rows
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10)); 	//all spacings are done by GridPane
		grid.setHgap(10);					//even between columns 
		grid.setVgap(10);					//and rows
		
		//add the buttons to the grid pane
		grid.addRow(0, btnOFF, btnLBracket, btnRBracket, btnON);
		grid.addRow(1, btnClear, btnBackSpace, btnAnswer, btnPercent);
		grid.addRow(2, btnMplus, btnMminus, btnMRecall, btnDivide);
		grid.addRow(3, btn7, btn8, btn9, btnMultiply);
		grid.addRow(4, btn4, btn5, btn6, btnSubtract);
		grid.addRow(5, btn1, btn2, btn3, btnAdd);
		grid.addRow(6, btnSign, btn0, btnDot, btnEqual);
		
		//set column widths:
		//create column constraints to control the size of the columns
		//in a grid pane
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(25);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(25);
		ColumnConstraints col3 = new ColumnConstraints();
		col3.setPercentWidth(25);
		ColumnConstraints col4 = new ColumnConstraints();
		col4.setPercentWidth(25);
		
		//add column constraints to grid pane
		grid.getColumnConstraints().addAll(col1, col2, col3, col4);
		
		//create BorderPane paneMain and add VBox paneTop and GridPane grid to it
		BorderPane paneMain = new BorderPane();
		paneMain.setTop(paneTop);
		paneMain.setCenter(grid);
		
		//create the scene and the stage
		Scene scene = new Scene(paneMain);
		primaryStage.setScene(scene);
		primaryStage.show();		
	}
	
	private void btnBracket_Click(String strBracket) {
		if(strBracket.equals("(")) {
			if(!strCurrentText.equals("")) {
				if(strCurrentText.substring(strCurrentText.length()-1)
						.matches("\\d") || strCurrentText
							.substring(strCurrentText.length()-1)
								.matches("\\)")  ) {
					strCurrentText += "x(";
					opCountSign++;
				    System.out.println("opCountSign = " + opCountSign);
				}
				else if(strCurrentText.substring(strCurrentText.length()-1)
						.equals(".")) {
					;
				}
				else{
					strCurrentText += "(";
				}	
			}
			else { //empty screen (no text)
				strCurrentText += "(";
			}
		}
		else {	//Right Bracket ")"
			if(!strCurrentText.equals("")) {
				if((strCurrentText.substring(strCurrentText.length()-1).equals(".")) 
					|| (strCurrentText.substring(strCurrentText.length()-1).equals("+"))
					|| (strCurrentText.substring(strCurrentText.length()-1).equals("-"))
					|| (strCurrentText.substring(strCurrentText.length()-1).equals("x"))	
					|| (strCurrentText.substring(strCurrentText.length()-1).equals("/"))) {
					;
				}
				else { 
					strCurrentText += ")";
				}
			}
			else {	//empty screen (no text)
				strCurrentText += ")";
			}
		}
		
		txtInput.setText(strCurrentText);
		
		//store current text in a real time text variable for debugging purposes
		strRealTimeNumber = strCurrentText;
		System.out.println(strRealTimeNumber);
	}

	private void btnSwitch_Click(String strSwitch) {
		if(strSwitch.equals("OFF")) {
			disableAllButtonsExceptBtnON(); //btnON is not switched
			btnClear_Click();			//clear everything when switching off 
			txtInput.setDisable(true);	//disable input text field
		}
		else { // "ON"
			enableAllButtonsExceptBtnON();
			txtInput.setDisable(false);	//enable input text field		
		}
	}

	private void enableAllButtonsExceptBtnON() {
		//1st row buttons
		btnOFF.setDisable(false);
		btnLBracket.setDisable(false);
		btnRBracket.setDisable(false);
	
		//2nd row buttons
		btnClear.setDisable(false);
		btnBackSpace.setDisable(false);
		btnPercent.setDisable(false);
		btnAnswer.setDisable(false); 	
		
		//3rd row buttons
		btnMplus.setDisable(false);	
		btnMminus.setDisable(false);
		btnMRecall.setDisable(false);
		btnDivide.setDisable(false);
		
		//4th row buttons
		btn7.setDisable(false);
		btn8.setDisable(false);
		btn9.setDisable(false);
		btnMultiply.setDisable(false);
		
		//5th row buttons
		btn4.setDisable(false);
		btn5.setDisable(false);
		btn6.setDisable(false);
		btnSubtract.setDisable(false);
		
		//6th row buttons
		btn1.setDisable(false);
		btn2.setDisable(false);
		btn3.setDisable(false);
		btnAdd.setDisable(false);
		
		//7th row buttons
		btnSign.setDisable(false);
		btn0.setDisable(false);;
		btnDot.setDisable(false);
		btnEqual.setDisable(false);;		
	}

	private void disableAllButtonsExceptBtnON() {
		//1st row buttons
		btnOFF.setDisable(true);
		btnLBracket.setDisable(true);
		btnRBracket.setDisable(true);
	
		//2nd row buttons
		btnClear.setDisable(true);
		btnBackSpace.setDisable(true);
		btnPercent.setDisable(true);
		btnAnswer.setDisable(true); 	
		
		//3rd row buttons
		btnMplus.setDisable(true);	
		btnMminus.setDisable(true);
		btnMRecall.setDisable(true);
		btnDivide.setDisable(true);
		
		//4th row buttons
		btn7.setDisable(true);
		btn8.setDisable(true);
		btn9.setDisable(true);
		btnMultiply.setDisable(true);
		
		//5th row buttons
		btn4.setDisable(true);
		btn5.setDisable(true);
		btn6.setDisable(true);
		btnSubtract.setDisable(true);
		
		//6th row buttons
		btn1.setDisable(true);
		btn2.setDisable(true);
		btn3.setDisable(true);
		btnAdd.setDisable(true);
		
		//7th row buttons
		btnSign.setDisable(true);
		btn0.setDisable(true);;
		btnDot.setDisable(true);
		btnEqual.setDisable(true);;	
	}

	private void btnClear_Click() {
		//Clear / reset all class variables (except dblCalMemory which is done my
		//clearMemoryValue() method)
		strRealTimeNumber = ""; 
		strCurrentText = ""; 
		isDotExist = false; 
		isOperator = false;
		isDigitExist = false; 
		strAnswer = "";
		isMplus = true;	//so that the dblCalMemory still retain its memory value
		isMminus = true;
		opCountSign = 0;
		countLBracket = 0;
		countRBracket = 0;
		
		//empty the screen
		txtInput.setText("");
	}

	private void btnBackSpace_Click() {
		if(txtInput.getText().equals(""))
			return;
		
		//Create a String builder so as to use its deleteCharAt method
		//Its constructor takes in a String, here being strCurrentText
		StringBuilder sbCurrentText = new StringBuilder(strCurrentText);
		
		//delete last character on each click
		char op = sbCurrentText.charAt(sbCurrentText.length() - 1);
		
		if((op == '+') || (op == '-') || (op == 'x') || (op == '/'))
			isOperator = false;
		
		if(sbCurrentText.charAt(sbCurrentText.length() - 1) == '+'
			||sbCurrentText.charAt(sbCurrentText.length() - 1) == '-'
			||sbCurrentText.charAt(sbCurrentText.length() - 1) == 'x'
			||sbCurrentText.charAt(sbCurrentText.length() - 1) == '/') {
			opCountSign--;
		}
		System.out.println("opCountSign = " + opCountSign);
			
		sbCurrentText.deleteCharAt(sbCurrentText.length() - 1);
		
		//convert StringBuilder back to String
		strCurrentText = sbCurrentText.toString();

		//display current text on display after removing last character each time
		txtInput.setText(strCurrentText);
		
		//so that the dblCalMemory still retain its memory value
		isMplus = true;
		isMminus = true;
		
		//update real time number: strRealTimeNumber for debugging
		strRealTimeNumber = strCurrentText;
		System.out.println(strRealTimeNumber);
	}

	private void btnAnswer_Click() {
		if(txtInput.getText().equals(""))
			return;
		
		try {
			double dblAnswer = Double.parseDouble(strAnswer);
			
			//format answer to be displayed
			txtInput.setText("" + nf.format(dblAnswer));
			
			strCurrentText = strAnswer;
		}catch(NumberFormatException e) {
			txtInput.setText("Math Error");
		}
		
		//store strCurrentText into strRealTimeNumber for debugging purposes
		strRealTimeNumber = strCurrentText;
		System.out.println(strRealTimeNumber);	
	
		//so that the dblCalMemory still retain its memory value
		isMplus = true;
		isMminus = true;
	}

	private String addRemoveBracket(String strTempCal) {
		for(int i=0; i<strTempCal.length(); i++) {
			char c = strTempCal.charAt(i);
			if(c == '(')
				countLBracket++;
			
			if(c == ')')
				countRBracket++;
		}
		
		String strCal;
		StringBuilder sbTempCal;
		int diff;
		if(countRBracket < countLBracket) {
			diff = countLBracket - countRBracket;
			for(int i=0; i<diff; i++) {
				strTempCal += ")";
				countRBracket++;
			}
				
			//enclosed expression in Outermost a pair of brackets before compute
			strCal = "(" + strTempCal + ")";
			countRBracket++;
			countLBracket++;
		}
		else if(countRBracket > countLBracket) {
			diff = countRBracket - countLBracket;
			sbTempCal = new StringBuilder(strTempCal);
			for(int i=0; i<diff; i++) {
				sbTempCal.deleteCharAt(sbTempCal.length()-1);
				countRBracket--;
			}
	
			strTempCal = sbTempCal.toString();
			strCal = "(" + strTempCal +")";
			countRBracket++;
			countLBracket++;
		}
		else {// when the number of left and right brackets are equal
			strCal = "(" + strTempCal + ")";
			countRBracket++;
			countLBracket++;
		}		
		txtInput.setText(strCal);
		
		return strCal;
	}
	
	//perform Order of Operations on multiple operators +,- X,/ from left to right
	private void btnEqual_Click() {
		//get input string expression WITHOUT Outermost bracket yet
		String strTempCal = txtInput.getText();
		
		if(strTempCal.length() == 0)
			return;
		
		//string expression with outermost bracket
		String strCal = addRemoveBracket(strTempCal); 
		
		//convert each character of strCal and numbers to a linked list, llCal
		LinkedList<String> llCal = convertStringToLinkedList(strCal); 
		
		//print debugging: strCal, countLBracket, countRBracket and llCal
		System.out.println("strCal: " +strCal);
		System.out.println("LBracket= " +countLBracket);
		System.out.println("RBracket= " +countRBracket);	
		System.out.println("llCal: " +llCal.toString());
		
		//compute the calculations with one or more operators that may be different
		LinkedList<String> resultLL = computeExp(llCal);
		
		//display result of calculation on screen and truncate .0 if needed
		strCurrentText = truncateDotZero(Double.parseDouble(resultLL.get(0)));
		
		double dblFinalResult = Double.parseDouble(strCurrentText);
				
		txtInput.setText("" + nf.format(dblFinalResult));
		
		//store proper floating point to exact value on display after formatting 
		//to 1st element of resultAL if values lie between -1000 and 1000 (exclusive)
		
		if((dblFinalResult > -1000) && (dblFinalResult < 1000)) {
			//store & update formatted result into 1st element
			resultLL.set(0, txtInput.getText());
			
			//retrieve 1st element & store formatted result in strCurrentText
			strCurrentText = resultLL.get(0); 
		}
		
		//store strCurrentText into strRealTimeNumber for debugging purposes
		strRealTimeNumber = strCurrentText;
		System.out.println(strRealTimeNumber);	
	
		//store strCurrentText (final result) to strAnswer which can be treated 
		//as an input number for further computations
		strAnswer = strCurrentText;	
	}
	
	private LinkedList<String> convertStringToLinkedList(String strCal) {
		LinkedList<String> llCal = new LinkedList<>();

		String s = "";	// to hold negative numbers
		char cp = ' ';
		char ca = ' ';
		
		//loop through the string expression, strCal
		for(int i=0; i<strCal.length(); i++) {
			char c = strCal.charAt(i);
			
			switch(c) {
				case '(':
					llCal.add(c + "");
					break;
				case '-':			
					//for minus operator 
					//check if previous character is a ')' or a digit 0 - 9 AND
					//if character after '-' is a '(' or a digit 0 - 9
					cp = strCal.charAt(i-1);
					ca = strCal.charAt(i+1);
					
					if((cp == ')' || cp == '0' || cp == '1' || cp == '2' || cp == '3' 
							|| cp == '4' || cp == '5' || cp == '6' 
							|| cp == '7' || cp == '8' || cp == '9') &&
						(ca == '(' || ca == '0' || ca == '1' || ca == '2' || ca == '3' 
							|| ca== '4' || ca == '5' || ca == '6' 
							|| ca == '7' || ca == '8' || ca == '9')) {
						llCal.add(c + ""); //add the minus operator
						break;
					}
					//add negative sign if previous character is '('
					if(cp == '(') {
						s += c + ""; 		
						System.out.println("-ve sign 's': "+s);
					}	
					break;
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					s += c + "";
					
					System.out.println("digit add to-ve sign/dot/op/( 's': "+s);
					
					if(strCal.charAt(i+1) == '.') {
						s += strCal.charAt(i+1);
						
						System.out.println("dot add to-ve number 's': "+s);
						
						break;
					}					

					if(strCal.charAt(i+1) == ')' || strCal.charAt(i+1) == '+'
							|| strCal.charAt(i+1) == '-' || strCal.charAt(i+1) == 'x'
							|| strCal.charAt(i+1) == '/') {
						llCal.add(s); 
						
						s = ""; //clear string that just contain the double value
						System.out.println("after clearing 's': "+s);
					}                   
					break;
				case ')':
					llCal.add(c + "");
					break;
				case 'x':
					llCal.add(c + "");
					break;
				case '+':
					llCal.add(c + "");
					break;
				case '/':
					llCal.add(c + "");
					break;
				default:
					break;
			}
		}	
		return llCal;
	}

	private LinkedList<String> computeExp(LinkedList<String> llCal) {
		LinkedList<String> llTempExp = new LinkedList<>();
		LinkedList<String> llTempResult = new LinkedList<>();
		int count = 0; // count the number of "(" as loop progresses
		int indexRBracket = 0;
		int indexLBracket = 0;
	
		//loop through linked list llCal of changing size
		for(int i=0; i<llCal.size(); i++) {
			//search for "("
			if(llCal.get(i).equals("(")) {
				count++;
				indexLBracket = i;
			}
			
			if(llCal.get(i).equals(")")) {
				indexRBracket = i;
				
				//get each sub expression in brackets
				for(int j=indexLBracket+1; j<indexRBracket; j++) {
					llTempExp.add(llCal.get(j));
				} 
				
				//compute each sub expression if llTempExp is not empty
				if(!llTempExp.isEmpty()) {
					llTempResult = compute(llTempExp);
					
					//replace old expression with returned intermediate result
					for(int k=indexLBracket; k<indexRBracket+1; k++) {
						llCal.remove(k);
						k--;				//to keep
						indexRBracket--;	//in range
					}
					count--; //one less bracket
					llCal.add(indexLBracket, llTempResult.get(0));
				
					System.out.println("llCal after replace: " + llCal.toString());
					
					llTempExp.remove();	//to maintain only one element in llTempExp 		
				}
					
				if(count > 0)
					indexLBracket = 0;	//reset index of left bracket
				
				i = indexLBracket; //reset i so it fits in llCal of reduced size
			}	
		}			
		return llCal;
	}


	private LinkedList<String> compute(LinkedList<String> llCal) {
		//compute Multiply & Divide from left to right 1st for 
		//precedence order: 1
		LinkedList<String> tempLL = computeMultiplyDivide(llCal);
				
		//compute Addition & Subtraction from left to right 2nd for 
		//precedence order: 2
		LinkedList<String> resultLL = computeAddSubtract(tempLL);
		
		//print debug: resultLL
		printDebugStringLL(resultLL, "resultLL @index " );
		
		return resultLL;
	}
	
	private LinkedList<String> computeAddSubtract(LinkedList<String> llTempAS) {	
		
		//store and calculate at the same time for "+" and "-"
		for(int i = 0; i < llTempAS.size(); i++) {
			if(i % 2 == 1) {//odd indexes
			
				if(llTempAS.get(i).equals("+")) {
					llTempAS.set(0, 
							Double.toString((Double.parseDouble(llTempAS.get(0))) 
									+ (Double.parseDouble(llTempAS.get(i+1)))));
					
					//remove some linked list elements					
					llTempAS.remove(i);	//element is removed one at a time
					llTempAS.remove(i);	//for a linked list
					i--;
					i--;
					
					i++; //to move on to the next number in llTempAS
				}
				
				if(llTempAS.get(i).equals("-")) {
					llTempAS.set(0, 
							Double.toString((Double.parseDouble(llTempAS.get(0))) 
									- (Double.parseDouble(llTempAS.get(i+1)))));
						
					//remove some linked list elements
					llTempAS.remove(i);
					llTempAS.remove(i);
					i--;
					i--;
					
					i++; //to move on to the next number in llTempAS
				}
			}
		}	
		
		//print debug: llTempAS
		printDebugStringLL(llTempAS, "llTempAS @index " );
		
		return llTempAS; //2-element linked list: result in element1, ? in element 2
	}

	private LinkedList<String> computeMultiplyDivide(LinkedList<String> llTempMD) {
		
		//moves to the right and calculate at the same time for "x" and "/"
		for(int i = 0; i < llTempMD.size(); i++) {//size changes due to elements
												 //removal
			//check for certain operator
			if(llTempMD.get(i).equals("+") || llTempMD.get(i).equals("-")) 
				continue; //skip "+" & "-"
			
			if(llTempMD.get(i).equals("x")) {
				llTempMD.set(i+1,  
						Double.toString((Double.parseDouble(llTempMD.get(i-1))) 
								* (Double.parseDouble(llTempMD.get(i+1)))));
				
				//remove some linked list elements
				llTempMD.remove(i);
				llTempMD.remove(i-1);
				i--;
				i--;
				
				i++; //to move on to the next number in the llTempMD
			}
			
			if(llTempMD.get(i).equals("/")) {
				llTempMD.set(i+1,  
						Double.toString((Double.parseDouble(llTempMD.get(i-1))) 
								/ (Double.parseDouble(llTempMD.get(i+1)))));
				
				//empty some linked list elements
				llTempMD.remove(i);
				llTempMD.remove(i-1);
				i--;
				i--;
				
				i++; //to move on to the next number in the llTempMD
			}		
		}
		
		//print debug: llTempMD
		printDebugStringLL(llTempMD, "llTempMD @index ");

		return llTempMD;	
	}
	
	private void printDebugStringLL(LinkedList<String> strLL, String msgLL) {
		for(int i=0; i<strLL.size(); i++)
			System.out.println(msgLL + i + ": " + strLL.get(i));
	}
	
	private void btnPercent_Click() {
		if(txtInput.getText().equals(""))
			return;
		
		try {
			double dblPercent = 0.01 * Double.parseDouble(strRealTimeNumber);
			strCurrentText = truncateDotZero(dblPercent);
			
			double dblFinalResult = Double.parseDouble(strCurrentText);
					
			txtInput.setText("" + nf.format(dblFinalResult));
			
			strCurrentText = txtInput.getText();
		}
		catch(NumberFormatException e) {
			txtInput.setText("Math Error");
		}
		strAnswer = strCurrentText;	
		
		//so that the dblCalMemory still retain its memory value
		isMplus = true;
		isMminus = true;
		
		//store current text in a real time text variable for debugging purposes
		strRealTimeNumber = strCurrentText;
		System.out.println(strRealTimeNumber);
	}
	
	private void clearMemoryValue() {
		dblCalMemory = 0.0;
		strCurrentText = truncateDotZero(dblCalMemory);
		txtInput.setText(strCurrentText);
	}
	
	private void displayMemoryValue() {
		//display Memory Value;		
		txtInput.setText("" + nf.format(dblCalMemory));
		
		strCurrentText = truncateDotZero(dblCalMemory);
	}
	
	private void btnMRecall_Click() {
		if(isMplus || isMminus) {
			displayMemoryValue();
			isMplus = false;
			isMminus = false;
		}	
		else {
			clearMemoryValue();
		}
		
		//print real time number to console for debugging purposes
		strRealTimeNumber = strCurrentText;
		System.out.println(strRealTimeNumber);
	}

	private void btnMemoryOperator_Click(String strMemOp) {
		if(txtInput.getText().equals(""))
			return;
		
		try {
			switch(strMemOp) {
				case "M+":
					dblCalMemory 
						= dblCalMemory + Double.parseDouble(strRealTimeNumber);
					isMplus = true; //that is "M+" is clicked
					System.out.println("M+");
					break;
				case "M-":
					dblCalMemory 
						= dblCalMemory - Double.parseDouble(strRealTimeNumber);
					isMminus = true; //that is "M-" is clicked
					System.out.println("M-");
					break;
				default:
					break;				
			}
		}catch(NumberFormatException e) {
			txtInput.setText("Math Error");
		}
		//clear current text to prevent next input digit to be concatenated to 
		//the previous input value
		strCurrentText = "";
	}

	//a helper method (with parameter) for displaying results of calculation
	private String truncateDotZero(double dblCurrentNumber) {
		String strCurrentNumber = Double.toString(dblCurrentNumber);
		
		//compare strCurrentNumber with a Regular Expression
		String regexp = "-?\\d{1,}.0";
		
		//if double number is with a .0, remove it
		if(strCurrentNumber.matches(regexp)) {
			String strTempNumber 
				= strCurrentNumber.substring(0, strCurrentNumber.length() - 2);
			strCurrentNumber 
				= strCurrentNumber.replaceAll(strCurrentNumber, strTempNumber);
		}
		return strCurrentNumber;
	}

	private void btnSign_Click() {
		if(strCurrentText.equals(""))
			return;
		
		try {
			//loop from the end of strCurrentText with index j
			for(int j=strCurrentText.length()-1; j>=0; j--) {
				char c = strCurrentText.charAt(j);
				
				if(c=='+'||c=='-'||c=='x'||c=='/'||c=='(') {
					//create new text with brackets containing negative number
					createNewText(j);
					break;
				}
				else { //no operators and so there is only a positive number
					if(opCountSign >= 1)
						continue;
					
					//sign changes each time as -1 is multiplied to the number
					double dblCurrentNumber = 0.0;
					
					//if there is a 1st left bracket "(", extract substring
					if(strCurrentText.substring(0,1).equals("(")) {
						strCurrentText = strCurrentText.substring(1);
						
						dblCurrentNumber = Double.parseDouble(strCurrentText) * -1;
						strCurrentText = truncateDotZero(dblCurrentNumber);
						opCountSign++;
						System.out.println("opCountSign = " +opCountSign);
								
						if(dblCurrentNumber < 0.0) {
							strCurrentText = "((" + strCurrentText + ")";
							break;
						}		
					}
					else { //use original string provided there are no operators
								
						dblCurrentNumber = Double.parseDouble(strCurrentText) * -1;
						strCurrentText = truncateDotZero(dblCurrentNumber);
						opCountSign++;
						System.out.println("opCountSign = " +opCountSign);
						
						if(dblCurrentNumber < 0.0) {
							strCurrentText = "(" + strCurrentText + ")";
							break;
						}	
					}			
				}
			}		
			//display current text
			txtInput.setText(strCurrentText);
		}
		catch(NumberFormatException e) {
			txtInput.setText("Math Error");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}			
		//store current text in a real time text variable for debugging purposes
		strRealTimeNumber = strCurrentText;
		System.out.println(strRealTimeNumber);
	}

	private void createNewText(int j) {
		//sign changes each time as -1 is multiplied to the number
		double dblCurrentNumber 
			= Double.parseDouble(strCurrentText.substring(j+1)) * -1;
		
		opCountSign++;
		System.out.println("opCountSign = " +opCountSign);
		
		//truncate .0 
		String strTempText = truncateDotZero(dblCurrentNumber);
	
		//add brackets around if current number is negative
		if(dblCurrentNumber < 0.0)
			strTempText = "(" + strTempText + ")";
		
		//use string builder to delete old characters to be replaced
		StringBuilder sbCurrentText = new StringBuilder(strCurrentText);
		
		for(int k=strCurrentText.length()-1; k>j; k--) {
			sbCurrentText.deleteCharAt(sbCurrentText.length()-1);
		}
		
		//append new temporary text strTempText to sbCurrentText
		sbCurrentText.append(strTempText);
		
		//store the new text from sbCurrentText in strCurrentText
		strCurrentText = sbCurrentText.toString();	
}

	private void btnOperator_Click(String strOperator) {
	//if text on screen and digit on button is "+", clear screen & exit method
		if(txtInput.getText().equals("+") && strOperator.equals("+")) {
			strCurrentText = "";
			isOperator = false;
			isDotExist = false;
			return;
		}
	//if text on screen and digit on button is "-", clear screen & exit method
		else if(txtInput.getText().equals("-") && strOperator.equals("-")) {
			strCurrentText = "";
			isOperator = false;
			isDotExist = false;
			return;
		}
	//if text on screen and digit on button is "X", clear screen & exit method
		else if(txtInput.getText().equals("x") && strOperator.equals("x")) {
			strCurrentText = "";
			isOperator = false;
			isDotExist = false;
			return;
		}
	//if text on screen and digit on button is "/", clear screen & exit method
		else if(txtInput.getText().equals("/") && strOperator.equals("/")) {
			strCurrentText = "";
			isOperator = false;
			isDotExist = false;
			return;
		}
		
		//if previous character is neither a dot nor an operator
		//then concatenate an operator
		if(!isDotExist && !isOperator) { 
			
			//don't concatenate operator except "-" if previous character 
			//is "(" or ")"
			if(!strCurrentText.equals("")) {
				if(strCurrentText.substring(strCurrentText.length()-1)
					.matches("\\(")) {
					; //do nothing	
				}
				else {
					strCurrentText += strOperator; 
					txtInput.setText(strCurrentText);
					isOperator = true;	
				}
			}
		}
		
		//set to true once an operator is clicked
		isOperator = true;
		
		//so that the dblCalMemory still retain its memory value
		isMplus = true;
		isMminus = true;
		
		//for debugging of number of operators, note: -1 has one operator "-"
		opCountSign++;
	    System.out.println("opCountSign = " + opCountSign);

		//store current text in a real time text variable for debugging purposes
		strRealTimeNumber = strCurrentText;
		System.out.println(strRealTimeNumber);
	}
	
	private void btnDot_Click(String strDot) {
	//if text on screen and digit on button is ".", clear screen & exit method
		if(txtInput.getText().equals(".") && strDot.equals(".")) {
			strCurrentText = "";
			isDotExist = false;
			return;
		}
	//if current text is not empty, dot doesn't exist and previous character 
	//is not an operator then concatenate a dot 
		if((!strCurrentText.equals("")) && (!isDotExist) && (!isOperator)) { 
			
			//don't concatenate dot if previous character is "(" or ")"
			if(strCurrentText.substring(strCurrentText.length()-1)
				.matches("\\(") || strCurrentText.substring(strCurrentText.length()-1)
				.matches("\\)")) {
				;
			}
			else {
				strCurrentText += strDot;
				txtInput.setText(strCurrentText);
				isDotExist = true;
				isOperator = false;
			}	
		}
		//store current text in a real time text variable for debugging purposes
		strRealTimeNumber = strCurrentText;
		System.out.println(strRealTimeNumber);
	} 

	private void btnDigit_Click(String strDigit) {
	//if text on screen and digit on button is 0, clear screen and exit method
		if(txtInput.getText().equals("0") && strDigit.equals("0")) {
			strCurrentText = "";
			return;
		}
		
	//create strings of digits by string concatenation with no leading "0"
		if(strCurrentText.equals("0")) {	
			strCurrentText = "";
			strCurrentText += strDigit;
		}
		else {
			//change from e.g ")2" to ")x2"
			if(!strCurrentText.equals("")) {
				if(strCurrentText.substring(strCurrentText.length()-1)
						.matches("\\)")  ) {
					strCurrentText += "x" + strDigit;
					opCountSign++;
				    System.out.println("opCountSign = " + opCountSign);
				}
				else {
					strCurrentText += strDigit;
				}	
			}
			else { //empty string strCurrentText
				strCurrentText += strDigit;
			}
		}
		
		txtInput.setText(strCurrentText);
		isDigitExist = true;
		
	//if digit exists then can concatenate with a dot or an operator
		if(isDigitExist) {
			isDotExist = false;
			isOperator = false;
		}
		
	//store current text in a real time text variable for debugging purposes
		strRealTimeNumber = strCurrentText;
		System.out.println(strRealTimeNumber);
	}
}
