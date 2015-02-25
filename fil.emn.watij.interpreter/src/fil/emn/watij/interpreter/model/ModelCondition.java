package fil.emn.watij.interpreter.model;

import fil.emn.watij.BoolBooleanExpression;
import fil.emn.watij.BooleanExpression;
import fil.emn.watij.CompareExpression;
import fil.emn.watij.ComputeExpression;
import fil.emn.watij.Condition;
import fil.emn.watij.IntBooleanExpression;
import fil.emn.watij.StringBooleanExpression;
import fil.emn.watij.VariableInt;

public class ModelCondition {

	public static boolean execute(Condition condition) {
		boolean conditionResult = false;
		CompareExpression compareExpression = condition.getCompare();
		BooleanExpression booleanExpression = condition.getBoolean();

		if (compareExpression != null) {
			ComputeExpression leftExpression = (ComputeExpression) compareExpression.getComputeExpression();
			// LEFT
			int left = calculateComputeExpression(leftExpression);
			if (compareExpression.getCompareOp() != null) {
				// RIGHT
				int right = 0;
				if (leftExpression.getSubExpression() instanceof VariableInt) {
					VariableInt varInt = leftExpression.getSubExpression().getVarInt();
					right = calculateComputeExpression(varInt.getComputeExpression());
				} else {
					right = leftExpression.getSubExpression().getInt();
				}
				switch (compareExpression.getCompareOp()) {
				case "<":
					conditionResult = left < right;
					break;
				case ">":
					conditionResult = left > right;
					break;
				}
			} else {
				// ONLY LEFT
				conditionResult = left == 0;
			}
		} else if (booleanExpression instanceof StringBooleanExpression) {
			StringBooleanExpression expression = (StringBooleanExpression) booleanExpression;
			conditionResult = evaluateStringBooleanExpression(expression);
		} else if (booleanExpression instanceof IntBooleanExpression) {
			IntBooleanExpression expression = (IntBooleanExpression) booleanExpression;
			conditionResult = evaluateIntBooleanExpression(expression);
		} else if (booleanExpression instanceof BoolBooleanExpression) {
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
		boolean result = false;
		if (expression.getBoolOp() != null) {
			boolean right = evaluateStringBooleanExpression(expression.getRight());
			switch (expression.getBoolOp()) {
			case "&&":
				result = true;
				break;
			case "||":
				result = true;
				break;
			case "==":
				result = left.equals(right);
				break;
			case "!=":
				result = !left.equals(right);
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
	 * Evaluation an int-composed boolean expression
	 * 
	 * @param expression
	 */
	private static boolean evaluateIntBooleanExpression(IntBooleanExpression expression) {
		boolean left = calculateComputeExpression(expression.getComputeExpression()) == 0;
		boolean result = false;
		if (expression.getBoolOp() != null) {
			boolean right = evaluateIntBooleanExpression(expression.getRight());
			switch (expression.getBoolOp()) {
			case "&&":
				result = left && right;
				break;
			case "||":
				result = left || right;
				break;
			case "==":
				result = left == right;
				break;
			case "!=":
				result = left != right;
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
			case "==":
				result = left == right;
				break;
			case "!=":
				result = left != right;
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
