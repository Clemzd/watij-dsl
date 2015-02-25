package fil.emn.watij.interpreter.model;

import fil.emn.watij.Click;
import fil.emn.watij.interpreter.model.html.EnumTag;
import fil.emn.watij.interpreter.model.utils.FindWebElement;

public class ModelClick {
	
	private static EnumTag[] tagsForClick = new EnumTag[] { EnumTag.A, EnumTag.BUTTON, EnumTag.IMG, EnumTag.INPUT };

	public static void execute(Model model, Click click) {
		FindWebElement.find(model, click.getFindMethod(), model.getSearchValue(click.getReference()), tagsForClick).click();
	}
}
