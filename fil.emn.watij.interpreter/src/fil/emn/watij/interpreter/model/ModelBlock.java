package fil.emn.watij.interpreter.model;

import fil.emn.watij.Block;
import fil.emn.watij.Operation;

public class ModelBlock {
	public static void execute(Model model, Block block) {
		if (block.getBegin() != null) {
			for (Operation operation : block.getBegin().getOperation()) {
				ModelOperation.execute(model, operation);
			}
		} else if (block.getLoop() != null) {
			ModelLoop.execute(model, block.getLoop());
		} else if (block.getConditional() != null) {
			ModelConditional.execute(model, block.getConditional());
		}
	}
}
