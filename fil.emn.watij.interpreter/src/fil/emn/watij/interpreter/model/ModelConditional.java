package fil.emn.watij.interpreter.model;

import java.util.Map;

import org.eclipse.emf.common.util.EList;

import fil.emn.watij.Block;
import fil.emn.watij.Condition;
import fil.emn.watij.Conditional;
import fil.emn.watij.Procedure;

public class ModelConditional {
	public static void execute(Model model, Map<String, Object> envVar, Map<String, Procedure> envProcedure, Conditional conditional) {
		// The condition in managed by ModelCondition
		Condition cond = conditional.getCondition();

		// if actions
		if (ModelCondition.eval(envVar, cond)) {
			EList<Block> listBlock = conditional.getBlock();
			for (Block block : listBlock) {
				ModelBlock.execute(model, envVar, envProcedure, block);
			}
			// else actions
		} else if (conditional.getBlockElse().size() > 0) {
			for (Block block : conditional.getBlockElse()) {
				ModelBlock.execute(model, envVar, envProcedure, block);
			}
		}
		;
	}
}
