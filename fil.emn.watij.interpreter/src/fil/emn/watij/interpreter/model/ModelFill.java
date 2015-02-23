package fil.emn.watij.interpreter.model;

import org.openqa.selenium.WebElement;

import fil.emn.watij.Fill;

public class ModelFill {

	public static void execute(Model model, Fill fill) {
		final String searchValue = model.getSearchValue(fill.getReference());
		final WebElement element = model.findWebElement(fill.getFindMethod(), searchValue);
		element.sendKeys(fill.getValue());
	}
}
