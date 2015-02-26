package fil.emn.watij.interpreter.model;

import fil.emn.watij.BoolBooleanExpression;
import fil.emn.watij.BooleanExpression;
import fil.emn.watij.CompareExpression;
import fil.emn.watij.ComputeExpression;
import fil.emn.watij.Condition;
import fil.emn.watij.StringBooleanExpression;
import fil.emn.watij.VariableInt;

public class ModelCondition {

	public static boolean eval(Condition condition) {
		boolean conditionResult = false;
		CompareExpression compareExpression = condition.getCompare();
		BooleanExpression booleanExpression = condition.getBoolean();

		System.out.println("\ncompareExpression: " + compareExpression);
		System.out.println("booleanExpression: " + booleanExpression);
		if (compareExpression != null) {
			ComputeExpression leftExpression = (ComputeExpression) compareExpression.getComputeExpression();
			// LEFT
			int left = calculateComputeExpression(leftExpression);
			System.out.println("left is " + left);
			if (compareExpression.getCompareOp() != null) {
				// RIGHT
				int right = calculateComputeExpression(compareExpression.getRight());
				System.out.println("right " + right);
				switch (compareExpression.getCompareOp()) {
				case "<":
					conditionResult = left < right;
					break;
				case ">":
					conditionResult = left > right;
					break;
				}
				System.out.println("conditionResult " + conditionResult);
			} else if(compareExpression.getBool_Op()!= null){
				// RIGHT
				int right = calculateComputeExpression(compareExpression.getRight());
				System.out.println("right " + right);
				switch (compareExpression.getBool_Op()) {
				case "==":
					conditionResult = left == right;
					break;
				case "!=":
					conditionResult = left != right;
					break;
				}
				System.out.println("conditionResult " + conditionResult);
			}else{
				// ONLY LEFT
				conditionResult = left == 0;
			}
		} else if (booleanExpression instanceof StringBooleanExpression) {
			System.out.println("booleanExpression instanceof StringBooleanExpression");
			StringBooleanExpression expression = (StringBooleanExpression) booleanExpression;
			conditionResult = evaluateStringBooleanExpression(expression);
		}  else if (booleanExpression instanceof BoolBooleanExpression) {
			BoolBooleanExpression expression = (BoolBooleanExpression) booleanExpression;
			conditionResult = evaluateBoolBooleanExpression(expression);
		}
		return conditionResult;
	}

	/**
	 * Evaluation a string-composed boolean expression
	 * 
	 * @param expression
	 */
	private static boolean evaluateStringBooleanExpression(StringBooleanExpression expression) {
		String left;
		if (expression.getSubExpressionString().getVarString() != null) {
			left = expression.getSubExpressionString().getVarString().getString();
		} else {
			left = expression.getSubExpressionString().getString();
		}
		System.out.println("left string : " + left);
		boolean result = false;
		if (expression.getBoolOp() != null) {
			boolean right = evaluateStringBooleanExpression(expression.getRight());
			System.out.println("right string : " + right);
			switch (expression.getBoolOp()) {
			case "==":
				result = left.equals(right);
				break;
			case "!=":
				result = !left.equals(right);
				break;
			}
			System.out.println("result : " + result);
		}
		return result;
	}

	/**
	 * Evaluation an bool-composed boolean expression
	 * 
	 * @param expression
	 */
	private static boolean evaluateBoolBooleanExpression(BoolBooleanExpression expression) {
		boolean left;
		if (expression.getSubExpressionBool().getVarBool() != null) {
			left = "true".equals(expression.getSubExpressionBool().getVarBool().getBool());
		} else {
			left = "true".equals(expression.getSubExpressionBool().getBool());
		}
		boolean result = false;
		if (expression.getBoolOp() != null) {
			boolean right = evaluateBoolBooleanExpression(expression.getRight());
			switch (expression.getBoolOp()) {
			case "&&":
				result = left && right;
				break;
			case "||":
				result = left || right;
				break;
			}
		}
		// Not
		if (expression.getNotOp() != null) {
			result = !result;
		}
		return result;
	}

	/**
	 * Return the number calculated from a computeExpression
	 * 
	 * @param computeExpression
	 * @return number
	 */
	public static int calculateComputeExpression(ComputeExpression expression) {
		int left = 0;
		if (expression.getSubExpression() instanceof VariableInt) {
			VariableInt varInt = expression.getSubExpression().getVarInt();
			left = ModelCondition.calculateComputeExpression(varInt.getComputeExpression());
		} else {
			left = expression.getSubExpression().getInt();
		}
		// Minus
		if (expression.getMinusOp() != null) {
			left = -left;
		}
		if (expression.getComputeOp() != null) {
			int right = calculateComputeExpression(expression.getRight());
			switch (expression.getComputeOp()) {
			case "+":
				return left + right;
			case "-":
				return left - right;
			case "*":
				return left * right;
			case "/":
				return left / right;
			}
		} else {
			// ONLY ONE INT
			return left;
		}
		return left;
	}
}
