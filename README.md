Web automation testing writing in Java and Xtext.
=================================================
See watij examples to see how does it works.

This project contains a set of Eclipse projects : 
- The interpreter written in Java : <i>fil.emn.watij.interpreter</i>
- The Xtext project : <i>fil.emn.watij</i>
    * With the grammar : Watij.xtext
- The project generated by Xtext : <i>fil.emn.watij</i>

To start a Watij file : 
1 - Run an Eclipse application 
2 - Create a new project inside the new Eclipse Application
3 - Create a file which has the *.watij extension (you can copy / paste the example below)
4 - Start the intepretation by clicking of the "Start Watij" button.

```
browser = require Firefox;
browser
	.goto(http://campus.mines-nantes.fr)
	.click("Connexion")
	.click("CZAM")
	.select("Ecole des Mines de Nantes")
	.click("Cliquez ici pour continuer")
	.click("Sauvegarder")
	.fill("username", "cguet12")
	.fill("password", "********")
	.click("warn")
	.click("Connexion")
	.goto(https://nte.gemtech.fr/campus/course/view.php?id=1415)
	.close()
	;
```
