package fil.emn.watij.interpreter.model;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;

import fil.emn.watij.Click;
import fil.emn.watij.SubExpressionRead;
import fil.emn.watij.interpreter.model.html.EnumTag;
import fil.emn.watij.interpreter.model.utils.FindWebElement;

public class ModelClick {

	private static EnumTag[] tagsForClick = new EnumTag[] { EnumTag.A, EnumTag.BUTTON, EnumTag.IMG, EnumTag.INPUT };

	public static void execute(Model model, Map<String, Object> envVar, Map<String, Object> envFunction, Click click) {
		SubExpressionRead subExpressionRead = click.getReference();
		// directly a string (without variable)
		if (subExpressionRead.getString() != null) {
			System.out.println("directly a string (without variable)");
			FindWebElement.findElement(model, click.getFindMethod(), click.getReference().getString(), tagsForClick).click();
		// use of a variable
		} else {
			final Object object = model.getSearchValue(envVar, click.getReference());
			// read the returns a list of element
			if (object instanceof List) {
				System.out.println("read the returns a list of element");
				for (WebElement webElement : (List<WebElement>) object) {
					webElement.click();
				}
				// read that returns 1 web element
			} else if (object instanceof WebElement) {
				System.out.println("read that returns 1 web element");
				((WebElement) object).click();
				// variable string
			} else if (object instanceof String) {
				System.out.println(" variable string" + (String) object);
				FindWebElement.findElement(model, click.getFindMethod(), (String) object, tagsForClick).click();
			}
		}
	}
}
