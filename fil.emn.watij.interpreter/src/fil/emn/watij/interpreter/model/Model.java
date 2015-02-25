package fil.emn.watij.interpreter.model;

import org.eclipse.emf.common.util.EList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import fil.emn.watij.Block;
import fil.emn.watij.FindMethod;
import fil.emn.watij.Include;
import fil.emn.watij.Program;
import fil.emn.watij.SubExpressionString;

public class Model {
	private WebDriver webdriver;
	private Program program;

	/** Constructeur privee */
	private Model(Program program) {
		this.program = program;
	}

	/** Instance unique non pr�init */
	private static Model INSTANCE = null;

	/** Point d'acces pour l'instance unique du singleton */
	public static Model getInstance(Program program) {
		if (INSTANCE == null) {
			INSTANCE = new Model(program);
			findBrowser(program);
		}
		return INSTANCE;
	}

	private static void findBrowser(Program program) {
		Include include = program.getInclude();
		switch (include.getBrowser()) {
		case CHROME:
			// todo
			break;
		case FIREFOX:
			INSTANCE.webdriver = new FirefoxDriver();
			break;
		case IE:
			// TODO
			break;
		}
	}

	public WebElement findWebElement(FindMethod findMethod, String searchValue) {
		WebElement webElement = null;
		switch (findMethod) {
		case ID:
			webElement = getWebdriver().findElement(By.id(searchValue));
			break;
		case NAME:
			webElement = getWebdriver().findElement(By.name(searchValue));
			break;
		case TEXT:
			webElement = getWebdriver().findElement(By.linkText(searchValue));
			break;
		case SRC:
			// TODO not only images
			final String xpath = String.format("//img[contains(@src, '%s')]", searchValue);
			webElement = getWebdriver().findElement(By.xpath(xpath));
			break;
		}
		return webElement;
	}

	public String getSearchValue(SubExpressionString reference) {
		String value = null;
		if (reference.getVarString() != null) {
			// TODO get the value of the variable
		} else {
			value = reference.getString();
		}
		return value;
	}

	public void execute() {
		EList<Block> mainProcedure = program.getMainProcedure();
		for (Block block : mainProcedure) {
			ModelBlock.execute(this, block);
		}
	}

	public WebDriver getWebdriver() {
		return webdriver;
	}

}
