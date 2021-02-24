package de.valentin.master.core.avatar;

public class SkinColor{
	int red;
	int green;
	int blue;
	
	public SkinColor(int red, int green, int blue) {
		
		boolean validInput = validValue(red) &&
				validValue(green) &&
				validValue(blue);
		assert validInput : "RGB values need to be between 0 and 255";
		
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public SkinColor(String colorString) {
		
		int red;
		int green;
		int blue;
		String[] rgb = colorString.split(",");
		try {
			red = Integer.parseInt(rgb[0]);
			green = Integer.parseInt(rgb[1]);
			blue = Integer.parseInt(rgb[2]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("color String has not been provided correctly. It needs to have the form 'r,g,b'");
		}
		
		boolean validInput = validValue(red) &&
				validValue(green) &&
				validValue(blue);
		assert validInput : "RGB values need to be between 0 and 255";
		
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	@Override
	public String toString() {
		return red + "," + green + "," + blue;
	}
	
	public int getRed() {
		return red;
	}
	public void setRed(int red) {
		this.red = red;
	}
	public int getGreen() {
		return green;
	}
	public void setGreen(int green) {
		this.green = green;
	}
	public int getBlue() {
		return blue;
	}
	public void setBlue(int blue) {
		this.blue = blue;
	}
	
	private boolean validValue(int value) {
		return value >= 0 && value <= 255;
	}
}