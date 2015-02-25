package fil.emn.watij.interpreter.model.html;

public enum EnumTag {
	A("a"), BUTTON("button"), IMG("img"), INPUT("input"), SELECT("select");

	private String tag = "";

	EnumTag(String tag) {
		this.tag = tag;
	}

	public String getTagName() {
		return this.tag;
	}
}
