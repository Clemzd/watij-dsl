package fil.emn.watij.interpreter.model;

import java.util.Map;

import fil.emn.watij.Operation;

public class ModelOperation {
	public static void execute(Model model, Map<String, Object> envVar, Map<String, Object> envFunction, Operation operation) {
		if (operation.getGoto() != null) {
			ModelGoto.execute(model, operation.getGoto());
		} else if (operation.getClick() != null) {
			ModelClick.execute(model, envVar, envFunction, operation.getClick());
		} else if (operation.getFill() != null) {
			ModelFill.execute(model, envVar, envFunction, operation.getFill());
		} else if (operation.getSelect() != null) {
			ModelSelect.execute(model, envVar, envFunction, operation.getSelect());
		} else if (operation.getClose() != null) {
			// Close the browser
			model.getWebdriver().quit();
		}
	}
}
