package fil.emn.watij.interpreter.model;

import fil.emn.watij.Goto;

public class ModelGoto {

	public static void execute(Model model, Goto gotoModel) {
		// we always wait before doing a goto, cause the webpage can do
		// redirection and selnenium
		// won't understand that the current task is done.
		// TODO verify that 1000 is a good choice for waiting 
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		model.getWebdriver().get(gotoModel.getUrl());
	}

}
