package fil.emn.watij.interpreter.model.html;

public enum EnumAttribute {
	OPTION("option"), VALUE("value");

	private String attribute = "";

	EnumAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getAttributeName() {
		return this.attribute;
	}
}
