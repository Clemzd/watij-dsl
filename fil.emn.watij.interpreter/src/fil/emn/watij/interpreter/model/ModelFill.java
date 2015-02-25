package fil.emn.watij.interpreter.model;

import org.openqa.selenium.WebElement;

import fil.emn.watij.Fill;
import fil.emn.watij.interpreter.model.html.EnumTag;
import fil.emn.watij.interpreter.model.utils.FindWebElement;

public class ModelFill {

	private static EnumTag[] tagsForFill = new EnumTag[] { EnumTag.INPUT };

	public static void execute(Model model, Fill fill) {
		final String searchValue = model.getSearchValue(fill.getReference());
		final WebElement element = FindWebElement.find(model, fill.getFindMethod(), searchValue, tagsForFill);
		element.sendKeys(fill.getValue());
	}
}
