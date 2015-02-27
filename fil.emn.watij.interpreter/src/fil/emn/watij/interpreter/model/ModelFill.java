package fil.emn.watij.interpreter.model;

import java.util.Map;

import org.openqa.selenium.WebElement;

import fil.emn.watij.Fill;
import fil.emn.watij.Procedure;
import fil.emn.watij.interpreter.model.html.EnumTag;
import fil.emn.watij.interpreter.model.utils.FindWebElement;

public class ModelFill {

	private static EnumTag[] tagsForFill = new EnumTag[] { EnumTag.INPUT };

	public static void execute(Model model, Map<String, Object> envVar, Map<String, Procedure> envProcedure, Fill fill) {
		final WebElement element = FindWebElement.findElement(model, fill.getFindMethod(), fill.getReference().getString(), tagsForFill);
		element.sendKeys(fill.getValue());
	}
}
