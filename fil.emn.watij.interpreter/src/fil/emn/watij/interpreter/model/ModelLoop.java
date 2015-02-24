package fil.emn.watij.interpreter.model;

import org.eclipse.emf.common.util.EList;

import fil.emn.watij.Block;
import fil.emn.watij.Condition;
import fil.emn.watij.Loop;

public class ModelLoop {
	public static void execute(Model model, Loop loop) {
		// The condition in managed by ModelCondition
		Condition cond = loop.getCond();

		// Loop action
		do {
			EList<Block> listBlock = loop.getBlock();
			for (Block LoopBlock : listBlock) {
				ModelBlock.execute(model, LoopBlock);
			}
		} while (ModelCondition.execute(cond));
	}
}