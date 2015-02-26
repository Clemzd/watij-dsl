package fil.emn.watij.interpreter.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;

import fil.emn.watij.Read;
import fil.emn.watij.interpreter.model.html.EnumInputType;
import fil.emn.watij.interpreter.model.html.EnumTag;
import fil.emn.watij.interpreter.model.utils.FindWebElement;

public class ModelRead {

	public static List<WebElement> eval(Model model, Map<String, Object> envVar, Map<String, Object> envFunction, Read read) {
		List<WebElement> listWebElement = new ArrayList<WebElement>();
		switch (read.getType()) {
		case CHECKBOX:
			listWebElement = FindWebElement.findByTagNameAndInputType(model, EnumInputType.CHECKBOX, EnumTag.INPUT);
			break;
		case IMG:
			listWebElement = FindWebElement.findByTagName(model, EnumTag.IMG);
			break;
		case UNSPECIFIED:
			listWebElement = readBySearchValue(model, envVar, read);
			break;
		}

		return listWebElement;
	}

	private static List<WebElement> readBySearchValue(Model model, Map<String, Object> envVar, Read read) {
		List<WebElement> listWebElement;
		final String searchValue = (String) model.getSearchValue(envVar, read.getReference());
		listWebElement = FindWebElement.findElements(model, read.getFindMethod(), null, searchValue, EnumTag.values());
		return listWebElement;
	}

}
