package fil.emn.watij.interpreter.model;

import fil.emn.watij.Click;

public class ModelClick {
	public static void execute(Model model, Click click) {
		model.findWebElement(click.getFindMethod(), model.getSearchValue(click.getReference())).click();
	}
}
