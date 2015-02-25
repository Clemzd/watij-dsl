package fil.emn.watij.interpreter.model.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import fil.emn.watij.FindMethod;
import fil.emn.watij.interpreter.model.Model;
import fil.emn.watij.interpreter.model.html.EnumTag;

public class FindWebElement {
	/** xpath requests constants. */
	private final static String XPATH_BEGIN = "//";
	private final static String XPATH_CONTAINS_ALL_ATTRIBUTES = "[@*[contains(.,'";
	private final static String XPATH_CONTAINS_INNER_TEXT = "[text()[contains(.,'";
	private final static String XPATH_END = "')]]";
	private final static String XPATH_SEPARATOR = " | ";

	/**
	 * Find web element.
	 * @param model
	 * @param findMethod
	 * 		find method to research the element : ID, NAME...
	 * @param searchValue
	 * @param tags
	 * @return
	 */
	public static WebElement find(Model model, FindMethod findMethod, String searchValue, EnumTag... tags) {
		if (findMethod == FindMethod.UNSPECIFIED) {
			// if the user didn't specify anything we research in all the pages
			return findByXpath(model, searchValue, tags);
		} else {
			// otherwise we reseach following the find method
			return findByFindMethod(model, findMethod, searchValue);
		}
	}

	/**
	 * Research by xpath (@link http://www.w3schools.com/xpath/)
	 * 
	 * @param model
	 * @param searchValue
	 * @param tags
	 * @return
	 */
	private static WebElement findByXpath(Model model, String searchValue, EnumTag... tags) {
		WebElement webElement = null;

		StringBuilder xpathRequest = new StringBuilder();
		EnumTag tag;
		int i = 0;
		while (i < tags.length) {
			tag = tags[i];
			// research  by all possible attribute
			xpathRequest
				.append(XPATH_BEGIN)
				.append(tag.getTagName())
				.append(XPATH_CONTAINS_ALL_ATTRIBUTES)
				.append(searchValue)
				.append(XPATH_END)
			// research by inner text (eg: <a...>inner text</a>)
				.append(XPATH_SEPARATOR)
				.append(XPATH_BEGIN)
				.append(tag.getTagName())
				.append(XPATH_CONTAINS_INNER_TEXT)
				.append(searchValue)
				.append(XPATH_END);
			
			// we do not add " | " after the last xpath request (otherwise it fails).
			if (i < tags.length - 1) {
				xpathRequest.append(XPATH_SEPARATOR);
			}
			i++;
		}
		final String xpath = String.format(xpathRequest.toString(), searchValue);
		webElement = model.getWebdriver().findElement(By.xpath(xpath));
		return webElement;
	}

	/**
	 * Find by find Method.
	 * 
	 * @param model
	 * @param findMethod
	 * @param searchValue
	 * @return
	 */
	private static WebElement findByFindMethod(Model model, FindMethod findMethod, String searchValue) {
		By by = null;
		switch (findMethod) {
		case ID:
			by = By.id(searchValue);
			break;
		case NAME:
			by = By.name(searchValue);
			break;
		case TEXT:
			by = By.linkText(searchValue);
			break;
		default:
			by = By.id(searchValue);
			break;
		}
		return model.getWebdriver().findElement(by);
	}
}
