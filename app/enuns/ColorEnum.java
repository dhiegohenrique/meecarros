package enuns;

public enum ColorEnum {

	WHITE("#FFFFFF", "Branco"),
	BLACK("#000000", "Preto"),
	GREEN("#008000", "Verde");
	
	private String id;
	private String color;

	private ColorEnum(String id, String color) {
		this.id = id;
		this.color = color;
	}
	
	public String getColor() {
		return this.color;
	}
	
	public String getId() {
		return this.id;
	}
}