package fil.emn.watij.interpreter.model;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;

import fil.emn.watij.Block;
import fil.emn.watij.CallProcedure;
import fil.emn.watij.Parameter;
import fil.emn.watij.Procedure;
import fil.emn.watij.VariableFunction;

public class ModelCallProcedure {

	public static void execute(Model model, Map<String, Object> envVar, Map<String, Procedure> envProcedure, CallProcedure call) {
		// get procedure from the name
		final String callProcedureName = call.getProcedure().getName();
		Procedure procedure = envProcedure.get(callProcedureName);

		if (procedure != null) {
			EList<Parameter> listProcedureParameters = procedure.getParameter();
			int arityToRespect = listProcedureParameters.size();
			int arityFunctionCall = call.getParam().size();
			int arityError = arityFunctionCall - arityToRespect;

			// Not enough param
			if (arityError < 0) {
				throw new RuntimeException("Your forgot " + arityError * -1 + " parameter(s) for the procedure " + callProcedureName);
			}
			// To many params
			if (arityError > 0) {
				throw new RuntimeException("You have " + arityError + " parameter(s) more than needed for the procedure " + callProcedureName);
			}
			// No arity errors
			if (arityError == 0) {
				// initialize parameters
				Map<String, Object> envParameters = new HashMap<String, Object>();
				int i = 0;
				for (VariableFunction variableFunction : call.getParam()) {
					Object value = null;
					if (variableFunction.getValueString() != null) {
						value = variableFunction.getValueString();
					} else if (variableFunction.getValueInt() != 0) {
						value = variableFunction.getValueInt();
					} else if (variableFunction.getValueBool() != null) {
						value = variableFunction.getValueBool();
					}
					final String nameOfParameter = listProcedureParameters.get(i).getName();
					envParameters.put(nameOfParameter, value);
					i++;
				}

				for (Block block : procedure.getBlock()) {
					// we inject the passed parameters to the procedure
					ModelBlock.execute(model, envParameters, envProcedure, block);
				}
			}

		} else {
			throw new RuntimeException("Procedure " + callProcedureName + "does not exists!");
		}

	}
}
