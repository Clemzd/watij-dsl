package fil.emn.watij.interpreter.model;

import java.util.Map;

import org.eclipse.emf.common.util.EList;

import fil.emn.watij.Block;
import fil.emn.watij.Condition;
import fil.emn.watij.Loop;

public class ModelLoop {
	public static void execute(Model model, Map<String, Object> envVar, Map<String, Object> envFunction, Loop loop) {
		// The condition in managed by ModelCondition
		Condition cond = loop.getCond();

		// Loop action
		do {
			EList<Block> listBlock = loop.getBlock();
			for (Block LoopBlock : listBlock) {
				ModelBlock.execute(model, envVar, envFunction, LoopBlock);
			}
		} while (ModelCondition.eval(cond));
	}
}