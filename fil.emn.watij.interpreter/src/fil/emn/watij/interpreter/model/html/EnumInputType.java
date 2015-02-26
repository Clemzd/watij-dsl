package fil.emn.watij.interpreter.model.html;

public enum EnumInputType {
	CHECKBOX("checkbox"), RADIO("radio");

	private String inputType = "";

	EnumInputType(String tag) {
		this.inputType = tag;
	}

	public String getInputType() {
		return this.inputType;
	}
}