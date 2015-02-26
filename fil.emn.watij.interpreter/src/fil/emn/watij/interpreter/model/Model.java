package fil.emn.watij.interpreter.model;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import fil.emn.watij.Block;
import fil.emn.watij.Include;
import fil.emn.watij.Program;
import fil.emn.watij.SubExpressionRead;
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

	public Object getSearchValue(Map<String, Object> envVar, SubExpressionString reference) {
		Object value = null;
		if (reference.getVarString() != null) {
			value = envVar.get(reference.getVarString().getName());
		}else{
			value = reference.getString();
		}
		return value;
	}
	
	public Object getSearchValue(Map<String, Object> envVar, SubExpressionRead reference) {
		Object value = null;
		if (reference.getVarStringOrRead() != null) {
			value = envVar.get(reference.getVarStringOrRead().getName());
		}
		return value;
	}

	public void execute() {
		// TODO handle procedures
		
		EList<Block> mainProcedure = program.getMainProcedure();
		/** Create environement. */
		// Environment of variables, key is the variable name, object is the variable model
		Map<String, Object> envVar = new HashMap<String, Object>();
		
		// Environement of functions, key is the function name, object is the function model
		Map<String, Object> envFunction = new HashMap<String, Object>();
		
		for (Block block : mainProcedure) {
			ModelBlock.execute(this, envVar, envFunction, block);
		}
	}

	public WebDriver getWebdriver() {
		return webdriver;
	}

}
