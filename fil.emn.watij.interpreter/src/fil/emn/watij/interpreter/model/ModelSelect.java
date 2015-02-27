package fil.emn.watij.interpreter.model;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import fil.emn.watij.Procedure;
import fil.emn.watij.Select;
import fil.emn.watij.interpreter.model.html.EnumAttribute;
import fil.emn.watij.interpreter.model.html.EnumTag;

public class ModelSelect {

	public static void execute(Model model, Map<String, Object> envVar, Map<String, Procedure> envProcedure, Select modelSelect) {
		final WebElement select = model.getWebdriver().findElement(By.tagName(EnumTag.SELECT.getTagName()));
		final List<WebElement> allOptions = select.findElements(By.tagName(EnumAttribute.OPTION.getAttributeName()));
		for (WebElement option : allOptions) {
			if (option.getText().contains(modelSelect.getOptionText().getString())) {
				option.click();
			}
		}
	}

}
