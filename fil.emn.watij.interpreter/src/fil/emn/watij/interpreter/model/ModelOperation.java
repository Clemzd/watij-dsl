package fil.emn.watij.interpreter.model;

import fil.emn.watij.Operation;

public class ModelOperation {
	public static void execute(Model model, Operation operation) {
		if (operation.getGoto() != null) {
			ModelGoto.execute(model, operation.getGoto());
		} else if (operation.getClick() != null) {
			ModelClick.execute(model, operation.getClick());
		} else if (operation.getFill() != null) {
			ModelFill.execute(model, operation.getFill());
		} else if (operation.getSelect() != null) {
			ModelSelect.execute(model, operation.getSelect());
		} else if (operation.getClose() != null) {
			// Close the browser
			model.getWebdriver().quit();
		}
	}
}
