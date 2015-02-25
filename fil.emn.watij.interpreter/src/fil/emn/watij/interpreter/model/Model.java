package fil.emn.watij.interpreter.model;

import org.eclipse.emf.common.util.EList;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import fil.emn.watij.Block;
import fil.emn.watij.Include;
import fil.emn.watij.Program;
import fil.emn.watij.SubExpressionString;

/**
 * High point of watij mode.
 * 
 * @author Clement
 *
 */
public class Model {

	private WebDriver webdriver;
	private Program program;

	/** Constructeur privee */
	public Model(Program program) {
		this.program = program;
		this.findBrowser(program);
	}

	private void findBrowser(Program program) {
		Include include = program.getInclude();
		switch (include.getBrowser()) {
		case CHROME:
			// todo
			break;
		case FIREFOX:
			this.webdriver = new FirefoxDriver();
			break;
		case IE:
			// TODO
			break;
		}
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
