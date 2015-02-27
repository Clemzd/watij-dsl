package fil.emn.watij.interpreter.model;

import java.util.Map;

import fil.emn.watij.Operation;
import fil.emn.watij.Procedure;

public class ModelOperation {
	public static void execute(Model model, Map<String, Object> envVar, Map<String, Procedure> envProcedure, Operation operation) {
		if (operation.getGoto() != null) {
			ModelGoto.execute(model, operation.getGoto());
		} else if (operation.getClick() != null) {
			ModelClick.execute(model, envVar, envProcedure, operation.getClick());
		} else if (operation.getFill() != null) {
			ModelFill.execute(model, envVar, envProcedure, operation.getFill());
		} else if (operation.getSelect() != null) {
			ModelSelect.execute(model, envVar, envProcedure, operation.getSelect());
		} else if (operation.getClose() != null) {
			// Close the browser
			model.getWebdriver().quit();
		}
	}
}
