package fil.emn.watij.interpreter.model;

import org.eclipse.emf.common.util.EList;

import fil.emn.watij.AlgoExpression;
import fil.emn.watij.Block;
import fil.emn.watij.BooleanExpression;
import fil.emn.watij.Condition;
import fil.emn.watij.Expression;
import fil.emn.watij.Loop;
import fil.emn.watij.VariableInt;

public class ModelLoop {
	public static void execute(Model model, Loop loop) {
		// Manage the condition
		boolean whileCondition = true;

		Condition cond = loop.getCond();
		Expression expression = cond.getExpression();

		if (expression instanceof AlgoExpression) {
			AlgoExpression algoExpression = (AlgoExpression) expression;
			// LEFT
			int left = findNumber(algoExpression);
			if (algoExpression.getOp() != null) {
				// RIGHT
				int right = 0;
				if (algoExpression.getSub1() instanceof VariableInt) {
					VariableInt varInt = algoExpression.getSub1().getVarInt();
					right = varInt.getInt();
				} else {
					right = algoExpression.getSub1().getInt();
				}
				switch (algoExpression.getOp()) {
				case "+":
					whileCondition = (left + right) == 0;
					break;
				case "-":
					whileCondition = (left - right) == 0;
					break;
				case "*":
					whileCondition = (left * right) == 0;
					break;
				case "/":
					whileCondition = (left / right) == 0;
					break;
				case "<":
					whileCondition = left < right;
					break;
				case ">":
					whileCondition = left > right;
					break;
				}
			} else {
				// ONLY ONE INT
				whileCondition = left == 0;
			}
		} else if (expression instanceof BooleanExpression) {
			whileCondition = true;
		}
		// Manage the action
		do {
			EList<Block> listBlock = loop.getBlock();
			for (Block LoopBlock : listBlock) {
				ModelBlock.execute(model, LoopBlock);
			}
		} while (whileCondition);
	}

	/**
	 * Return the number from an algoExpression
	 * 
	 * @param algoExpression
	 * @return number
	 */
	public static int findNumber(AlgoExpression algoExpression) {
		int number = 0;
		if (algoExpression.getSub1() instanceof VariableInt) {
			VariableInt varInt = algoExpression.getSub1().getVarInt();
			number = varInt.getInt();
		} else {
			number = algoExpression.getSub1().getInt();
		}
		// Minus
		if (algoExpression.getMinus() != null) {
			number = -number;
		}
		return number;
	}
}
