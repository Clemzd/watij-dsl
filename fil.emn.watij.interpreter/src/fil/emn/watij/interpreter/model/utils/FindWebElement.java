package fil.emn.watij.interpreter.model.utils;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import fil.emn.watij.FindMethod;
import fil.emn.watij.interpreter.model.Model;
import fil.emn.watij.interpreter.model.html.EnumInputType;
import fil.emn.watij.interpreter.model.html.EnumTag;

public class FindWebElement {
	/** xpath requests constants. */
	private final static String XPATH_BEGIN = "//";
	private final static String XPATH_TYPE = "[@type='";
	private final static String XPATH_CONTAINS_ALL_ATTRIBUTES = "[@*[contains(.,'";
	private final static String XPATH_CONTAINS_INNER_TEXT = "[text()[contains(.,'";
	private final static String XPATH_END_APOSTROPHE = "'";
	private final static String XPATH_END_PARENTHESE = ")";
	private final static String XPATH_END_SBRACKET = "]";
	private final static String XPATH_SEPARATOR = " | ";

	/** css request constants. */
	private final static String CSS_BEGIN_BRACKET = "[";
	private final static String CSS_BEGIN_TYPE = "type='";
	private final static String CSS_END_TYPE = "'";
	private final static String CSS_END_BRACKET = "]";
	
	/**
	 * Find web elements by tag name
	 * @param model
	 * @param inputType
	 * @param tags
	 * @return
	 */
	public static List<WebElement> findByTagName(Model model, EnumTag tag) {
		return model.getWebdriver().findElements(By.tagName(tag.getTagName()));
	}
	
	/**
	 * Find web elements by tag name and input type
	 * @param model
	 * @param inputType
	 * @param tags
	 * @return
	 */
	public static List<WebElement> findByTagNameAndInputType(Model model, EnumInputType inputType, EnumTag tag) {
		StringBuilder cssRequest = new StringBuilder(tag.getTagName())
			.append(CSS_BEGIN_BRACKET)
			.append(CSS_BEGIN_TYPE)
			.append(inputType)
			.append(CSS_END_TYPE)
			.append(CSS_END_BRACKET);
		System.out.println(cssRequest);
		return model.getWebdriver().findElements(By.cssSelector(cssRequest.toString()));
	}
	
	public static List<WebElement> findElements(Model model, FindMethod findMethod, EnumInputType inputType, String searchValue, EnumTag... tags) {
		List<WebElement> listWebElement = new ArrayList<WebElement>();
		if (findMethod == FindMethod.UNSPECIFIED || findMethod == null) {
			// if the user didn't specify anything we research in all the pages
			listWebElement.addAll(findByXpath(model, searchValue, inputType, tags));
		} else {
			// otherwise we reseach following the find method
			listWebElement.add(findByFindMethod(model, findMethod, searchValue));
		}
		return listWebElement;
	}
	
	/**
	 * Find web element.
	 * @param model
	 * @param findMethod
	 * 		find method to research the element : ID, NAME...
	 * @param searchValue
	 * @param tags
	 * @return
	 */
	public static WebElement findElement(Model model, FindMethod findMethod, String searchValue, EnumTag... tags) {
		if (findMethod == FindMethod.UNSPECIFIED || findMethod == null) {
			// if the user didn't specify anything we research in all the pages
			return findByXpath(model, searchValue, null, tags).get(0);
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
	private static List<WebElement> findByXpath(Model model, String searchValue, EnumInputType inputType, EnumTag... tags) {
		List<WebElement> listWebElement = null;

		StringBuilder xpathRequest = new StringBuilder();
		EnumTag tag;
		int i = 0;
		while (i < tags.length) {
			tag = tags[i];
			
			if(inputType==null){
				generateXpathRequest(xpathRequest, searchValue, tag);
			}else{
				generateXpathRequest(xpathRequest, tag, inputType);
			}
			
			// we do not add " | " after the last xpath request (otherwise it fails).
			if (i < tags.length - 1) {
				xpathRequest.append(XPATH_SEPARATOR);
			}
			i++;
		}
		System.out.println(xpathRequest.toString());
		listWebElement = model.getWebdriver().findElements(By.xpath(xpathRequest.toString()));
		return listWebElement;
	}
	
	private static void generateXpathRequest(StringBuilder xpathRequest, EnumTag tag, EnumInputType inputType) {
		
		// read multiple elements by tag and type
		xpathRequest
			.append(XPATH_BEGIN)
			.append(tag.getTagName())
			.append(XPATH_TYPE)
			.append(inputType.getInputType())
			.append(XPATH_END_APOSTROPHE)
			.append(XPATH_END_SBRACKET);
	}
	
	private static void generateXpathRequest(StringBuilder xpathRequest, String searchValue, EnumTag tag) {
		
		// research  by all possible attribute
		xpathRequest
			.append(XPATH_BEGIN)
			.append(tag.getTagName())
			.append(XPATH_CONTAINS_ALL_ATTRIBUTES)
			.append(searchValue)
			.append(XPATH_END_APOSTROPHE)
			.append(XPATH_END_PARENTHESE)
			.append(XPATH_END_SBRACKET)
			.append(XPATH_END_SBRACKET)
			
			// research by inner text (eg: <a...>inner text</a>)
			.append(XPATH_SEPARATOR)
			.append(XPATH_BEGIN)
			.append(tag.getTagName())
			.append(XPATH_CONTAINS_INNER_TEXT)
			.append(searchValue)
			.append(XPATH_END_APOSTROPHE)
			.append(XPATH_END_PARENTHESE)
			.append(XPATH_END_SBRACKET)
			.append(XPATH_END_SBRACKET);
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
