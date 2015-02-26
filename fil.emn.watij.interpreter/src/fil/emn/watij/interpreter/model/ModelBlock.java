package fil.emn.watij.interpreter.model;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;

import fil.emn.watij.Block;
import fil.emn.watij.Operation;
import fil.emn.watij.VariableBool;
import fil.emn.watij.VariableInt;
import fil.emn.watij.VariableStringOrRead;

public class ModelBlock {
	public static void execute(Model model, Map<String, Object> envVar, Map<String, Object> envFunction, Block block) {
		if (block.getVariableStringOrRead() != null) {
			VariableStringOrRead variableStringOrRead = block.getVariableStringOrRead();
			// read case
			if(variableStringOrRead.getVarRead() != null){
				final List<WebElement> listWebElement = ModelRead.eval(model, envVar, envFunction, variableStringOrRead.getVarRead());
				envVar.put(variableStringOrRead.getName(), listWebElement);
			// string case
			}else{
				envVar.put(variableStringOrRead.getName(), variableStringOrRead.getString());
			}
		} else if (block.getVarInt() != null) {
			final VariableInt variableInt = block.getVarInt();
			final int value = ModelCondition.calculateComputeExpression(variableInt.getComputeExpression());
			envVar.put(variableInt.getName(), value);
		} else if (block.getVarBoolean() != null) {
			final VariableBool variableBool = block.getVarBoolean();
			envVar.put(variableBool.getName(), variableBool.getBool());
		} else if (block.getLoop() != null) {
			ModelLoop.execute(model, envVar, envFunction, block.getLoop());
		} else if (block.getConditional() != null) {
			ModelConditional.execute(model, envVar, envFunction, block.getConditional());
		} else if (block.getBegin() != null) {
			for (Operation operation : block.getBegin().getOperation()) {
				ModelOperation.execute(model, envVar, envFunction, operation);
			}
		} else if (block.getCall() != null) {
			// TODO implement that
		}
	}
}
