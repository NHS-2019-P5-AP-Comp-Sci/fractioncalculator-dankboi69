/**
 * @author Mr. Rasmussen
 */

package fracCalc;
//Checkpoint 1
import java.io.*;
import java.util.*;

public class FracCalc {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to FracCalc V. 1.0 by Alex Niu. Type a calculation below");
		String nextinput = "0";
		// Sentinel loop- begins value of nextinput at "0" so it will run at least once
		while (!(nextinput.equals("quit"))) {
			String nextLine = sc.nextLine();
			if (nextLine.contains("quit")) {
				nextinput = "quit";
				// If the next line has quit in it, it ends the loop
			} else {
				String solution = produceAnswer(nextLine);
				// Produces answer using input
				System.out.println(solution);
				System.out.println("Enter another calculation below, or type quit to quit");
				// Allows further lines of input
			}
		}
		System.out.println("Thanks for using FracCalc!");
		sc.close();
		// Prevents memory leakage
	}

	public static String trashproduceAnswer(String input) {
		// This was the original method for one operation. It is used once in
		// ProduceAnswer for the last case
		int operand = input.indexOf(" ") + 1;
		// Finds the index of the operand, as it is always right after the 1st space
		char operation = input.charAt(operand);
		// Finds what operation is to be done
		String one = input.substring(0, operand - 1);
		String two = input.substring(operand + 2);
		// Two substrings: divides the input into two fractions
		String answer = "";
		if (operation == '+') {
			answer = add(one, two);
		} else if (operation == '-') {
			answer = subtract(one, two);
		} else if (operation == '*') {
			answer = multiply(one, two);
		} else if (operation == '/') {
			answer = divide(one, two);
		} else {
			answer = "ERROR: Input is in an invalid format.";
		}
		// if and else if and else loops to do the correct operation
		return answer;
		// Returns the answer to main
	}

	public static String produceAnswer(String input) {
		// This one supports multiple operations
		String answer = "";
		int count = 0;
		for (int i = 0; i < input.length(); i++) {
			char next = input.charAt(i);
			if (next == ' ') {
				count++;
			}
		}
		// Sees how many operations there are
		count = count / 2;
		boolean error = false;
		String changingstring = input;
		String cumulative = "";
		while (count > 0 && error == false) {
			int first = changingstring.indexOf(" ") + 1;
			String errorcatch = changingstring.substring(first);
			int second = errorcatch.indexOf(" ");
			if (second != 1) {
				error = true;
				answer = "ERROR: Input is in an invalid format.";
			}
			// Error catching
			else if (count == 1) {
				answer = trashproduceAnswer(changingstring);
			}
			// If there are only 1 operation, use original produceAnswer
			else {
				int uno = changingstring.indexOf(" ");
				int operand = uno + 1;
				char operation = changingstring.charAt(operand);
				String one = changingstring.substring(0, uno);
				changingstring = changingstring.substring(operand + 2);
				int dos = changingstring.indexOf(" ");
				String two = changingstring.substring(0, dos);
				changingstring = changingstring.substring(dos);
				if (operation == '+') {
					cumulative = add(one, two);
				} else if (operation == '-') {
					cumulative = subtract(one, two);
				} else if (operation == '*') {
					cumulative = multiply(one, two);
				} else if (operation == '/') {
					cumulative = divide(one, two);
				}
				// Pretty similar to stuff in originalproduceanswer
				else {
					cumulative = "ERROR: Input is in an invalid format.";
					break;
				}
				// Further error catching
				changingstring = cumulative + changingstring;
			}
			count--;
			// Iteration management
		}
		return answer;
	}

	public static String unitTest1(String input) {
		int operand = input.indexOf(" ") + 1;
		char operation = input.charAt(operand);
		String one = input.substring(0, operand - 1);
		String two = input.substring(operand + 2);
		return two;
		// For Unit Test One: Uses substrings to return the second frac
	}

	public static String unitTest2(String input) {
		String second = unitTest1(input);
		String whole = "0";
		String num = "0";
		String denom = "0";
		if (second.contains("_")) {
			int place = second.indexOf("_");
			int place2 = second.indexOf("/");
			whole = second.substring(0, place);
			num = second.substring(place + 1, place2);
			denom = second.substring(place2 + 1);
		} else if (!(second.contains("/"))) {
			whole = second;
			denom = "1";
		} else {
			int place = second.indexOf("/");
			num = second.substring(0, place);
			denom = second.substring(place + 1);
		}
		String solution = "whole:" + whole + " numerator:" + num + " denominator:" + denom;
		return solution;
		// For unit test 2: Uses substrings mostly in order to get whole, num, and denom
		// of second frac
	}

	public static String add(String one, String two) {
		String components = toComponents(one, two);
		if (components.equals("ERROR: Cannot divide by zero.")) {
			String error = "ERROR: Cannot divide by zero.";
			return error;
		} else {
			// Uses toComponents method to split two fracs into 4 pieces- numerators and
			// denominators
			int value1 = components.indexOf(" ");
			int value2 = components.indexOf(".");
			int value3 = components.indexOf("/");
			/*
			 * As I was not allowed to use a String array to return multiple things from
			 * toComponents, I put all 4 answers divided by certain keywords all in one
			 * line, and use these 3 lines above to split the string apart
			 */
			String num1 = components.substring(0, value1);
			String denom1 = components.substring(value1 + 1, value2);
			String num2 = components.substring(value2 + 1, value3);
			String denom2 = components.substring(value3 + 1);
			// Uses substrings to get the pieces of the fracs
			int num1REAL = Integer.parseInt(num1) * Integer.parseInt(denom2);
			int denom1REAL = Integer.parseInt(denom1) * Integer.parseInt(denom2);
			int num2REAL = Integer.parseInt(num2) * Integer.parseInt(denom1);
			// Does cross-multiplication to create a common denominator
			int numerator = num1REAL + num2REAL;
			int denominator = denom1REAL;
			String solution = numerator + "/" + denominator;
			// Simple addition of fractions- also puts it into a good form
			String realsolution = toProperForm(solution);
			// Uses toProperForm method to convert answer to proper form
			return realsolution;
			// Returns to ProduceAnswer
		}
	}

	public static String subtract(String one, String two) {
		String components = toComponents(one, two);
		if (components.equals("ERROR: Cannot divide by zero.")) {
			String error = "ERROR: Cannot divide by zero.";
			return error;
		} else {
			int value1 = components.indexOf(" ");
			int value2 = components.indexOf(".");
			int value3 = components.indexOf("/");
			String num1 = components.substring(0, value1);
			String denom1 = components.substring(value1 + 1, value2);
			String num2 = components.substring(value2 + 1, value3);
			String denom2 = components.substring(value3 + 1);
			int num1REAL = Integer.parseInt(num1) * Integer.parseInt(denom2);
			int denom1REAL = Integer.parseInt(denom1) * Integer.parseInt(denom2);
			int num2REAL = Integer.parseInt(num2) * Integer.parseInt(denom1);
			int denom2REAL = Integer.parseInt(denom2) * Integer.parseInt(denom1);
			int numerator = num1REAL - num2REAL;
			// Subtract is the same as add function, but it does - here instead of +
			int denominator = denom1REAL;
			String solution = numerator + "/" + denominator;
			String realsolution = toProperForm(solution);
			return realsolution;
		}
	}

	public static String multiply(String one, String two) {
		String components = toComponents(one, two);
		if (components.equals("ERROR: Cannot divide by zero.")) {
			String error = "ERROR: Cannot divide by zero.";
			return error;
		} else {
			int value1 = components.indexOf(" ");
			int value2 = components.indexOf(".");
			int value3 = components.indexOf("/");
			String num1 = components.substring(0, value1);
			String denom1 = components.substring(value1 + 1, value2);
			String num2 = components.substring(value2 + 1, value3);
			String denom2 = components.substring(value3 + 1);
			// Above things are similar to previous methods: gets the components
			int numerator = (Integer.parseInt(num1)) * (Integer.parseInt(num2));
			int denominator = (Integer.parseInt(denom1)) * (Integer.parseInt(denom2));
			// Does standard frac multiplication: top times top, bottom times bottom
			String solution = numerator + "/" + denominator;
			// Formatting
			String realsolution = toProperForm(solution);
			// To proper form
			if (one.equals("0") || two.equals("0")) {
				realsolution = "0";
			}
			// If either of the numerators are 0, the product is therefore 0
			return realsolution;
			// Returns solution to ProduceAnswer
		}
	}

	public static String divide(String one, String two) {
		if (two.equals("0")) {
			String error = "ERROR: Cannot divide by zero.";
			return error;
			// Standard error for division by zero
		} else if (one.equals("0")) {
			String zero = "0";
			return zero;
			// If it is 0/ something, it is automatically 0
		} else {
			String components = toComponents(one, two);
			if (components.equals("ERROR: Cannot divide by zero.")) {
				String error = "ERROR: Cannot divide by zero.";
				return error;
			} else {
				int value1 = components.indexOf(" ");
				int value2 = components.indexOf(".");
				int value3 = components.indexOf("/");
				String num1 = components.substring(0, value1);
				String denom1 = components.substring(value1 + 1, value2);
				String num2 = components.substring(value2 + 1, value3);
				String denom2 = components.substring(value3 + 1);
				int numerator = (Integer.parseInt(num1)) * (Integer.parseInt(denom2));
				int denominator = (Integer.parseInt(denom1)) * (Integer.parseInt(num2));
				// Pretty much same as multiplication, but flips the second fraction
				if ((numerator < 0) && (denominator < 0)) {
					numerator = Math.abs(numerator);
					denominator = Math.abs(denominator);
				} else if (denominator < 0 && numerator >= 0) {
					numerator = numerator * -1;
					denominator = denominator * -1;
				}
				// Ensures the numerator has the negative sign, rather than denominator
				String solution = numerator + "/" + denominator;
				String realsolution = toProperForm(solution);
				// To proper form
				return realsolution;
				// Returns to produceAnswer
			}
		}
	}

	public static String toComponents(String one, String two) {
		String oneimproper = toImproper(one);
		String twoimproper = toImproper(two);
		// Turns the inputs into improper form
		int spacesone = oneimproper.indexOf("/");
		int spacestwo = twoimproper.indexOf("/");
		String uno = oneimproper.substring(0, spacesone);
		String dos = oneimproper.substring(spacesone + 1);
		String three = twoimproper.substring(0, spacestwo);
		String four = twoimproper.substring(spacestwo + 1);
		// Uses index and substring to obtain the four parts
		String solution = uno + " " + dos + "." + three + "/" + four;
		// Uses spanish for one and two to prevent duplicate local variables
		if (dos.equals("0") || four.equals("0")) {
			solution = "ERROR: Cannot divide by zero.";
		}
		return solution;
		// Return
	}

	public static String toImproper(String input) {
		String answer = input;
		// If none of these loops are run, that means input is already improper or a
		// simple frac
		if (input.contains("_")) {
			int under = answer.indexOf("_");
			int divisor = answer.indexOf("/");
			String one = answer.substring(0, under);
			String two = answer.substring(under + 1, divisor);
			String three = answer.substring(divisor + 1);
			int orig = Integer.parseInt(one);
			int num = Integer.parseInt(two);
			int denom = Integer.parseInt(three);
			if (orig < 0) {
				num = num * -1;
			}
			orig = orig * denom;
			num = num + orig;
			answer = num + "/" + denom;
			// For mixed fractions, it multiples original by denominator and adds up
		} else if (!(input.contains("/"))) {
			answer = answer + "/1";
		}
		// If it is an int, add a /1 to put it in improper form
		return answer;
		// Returns

	}

	public static String toProperForm(String input) {
		// Easily the most lengthy part, toProperForm is also the most important part
		String answer = "";
		int divide = input.indexOf("/");
		String rawnum = input.substring(0, divide);
		String rawdenom = input.substring(divide + 1);
		int num = Integer.parseInt(rawnum);
		int denom = Integer.parseInt(rawdenom);
		// Fraction is in improper form: lines above convert it into numerator and
		// denominator
		if ((num / denom) >= 0) {
			// Case for if frac is positive
			if (num % denom == 0) {
				int divisor = num / denom;
				answer = String.valueOf(divisor);
				// It is an integer
			} else if (num < denom) {
				int GCD = 1;
				for (int i = 2; i < denom; i++) {
					if ((num % i == 0) && (denom % i == 0) && (i > GCD)) {
						GCD = i;
					}
				}
				num = num / GCD;
				denom = denom / GCD;
				// Reduces fraction using GCD
				answer = num + "/" + denom;
				// Proper form

			} else {
				int orig = num / denom;
				num = num - (orig * denom);
				// Adds a mixed number to the front
				int GCD = 1;
				if (num % denom == 0) {
					orig = orig + (num / denom);
					answer = orig + "";
				}
				for (int i = 2; i < denom; i++) {
					if ((num % i == 0) && (denom % i == 0) && (i > GCD)) {
						GCD = i;
					}
				}
				num = num / GCD;
				denom = denom / GCD;
				// Reduces the fraction, also ensures there are no cases such as 1_7/7
				if (num == 0) {
					answer = orig + "";
				} else {
					answer = orig + "_" + num + "/" + denom;
				}
				// Cases for if there is a numerator or not
			}
			return answer;
			// Returns
		} else {
			num = Math.abs(num);
			denom = Math.abs(denom);
			if (num % denom == 0) {
				int divisor = num / denom;
				answer = String.valueOf(divisor);
			} else if (num < denom) {
				int GCD = 1;
				for (int i = 2; i < denom; i++) {
					if ((num % i == 0) && (denom % i == 0) && (i > GCD)) {
						GCD = i;
					}
				}
				num = num / GCD;
				denom = denom / GCD;
				answer = num + "/" + denom;

			} else {
				int orig = num / denom;
				num = num - (orig * denom);
				int GCD = 1;
				if (num % denom == 0) {
					orig = orig + (num / denom);
					answer = orig + "";
				}
				for (int i = 2; i < denom; i++) {
					if ((num % i == 0) && (denom % i == 0) && (i > GCD)) {
						GCD = i;
					}
				}
				num = num / GCD;
				denom = denom / GCD;
				if (num == 0) {
					answer = orig + "";
				} else if (orig == 0) {
					answer = num + "/" + denom;
				} else {
					answer = orig + "_" + num + "/" + denom;
				}
			}
			answer = "-" + answer;
			return answer;
			// Very similar to above method, but this one first absolute values then adds a
			// negative sign to answer
		}
	}
}
