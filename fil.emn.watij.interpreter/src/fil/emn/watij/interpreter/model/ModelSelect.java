package fil.emn.watij.interpreter.model;

import org.openqa.selenium.WebElement;

import fil.emn.watij.Select;

public class ModelSelect {

	public static void execute(Model model, Select select) {
		WebElement webElement = model.findWebElement(select.getFindMethod(), model.getSearchValue(select.getReference()));
		org.openqa.selenium.support.ui.Select selectWebElement = new org.openqa.selenium.support.ui.Select(webElement);
		selectWebElement.selectByVisibleText(select.getOptionText());
	}

}
