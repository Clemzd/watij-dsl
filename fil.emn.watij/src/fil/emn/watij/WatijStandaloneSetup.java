/*
* generated by Xtext
*/
package fil.emn.watij;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class WatijStandaloneSetup extends WatijStandaloneSetupGenerated{

	public static void doSetup() {
		new WatijStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

