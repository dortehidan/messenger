package play.hidan.messenger.model;

public class ValueType {
	
	//ValueQuantity
	private String valueString;
	private long valueLong;
	private double valueDouble;
	
	private String unit; //
	private String codeDisplay;  // Glucose [Moles/volume] in Blood
	
	
		
	public ValueType() {
		
	}



	public String getValueString() {
		return valueString;
	}



	public void setValueString(String valueString) {
		this.valueString = valueString;
	}



	public long getValueLong() {
		return valueLong;
	}



	public void setValueLong(long valueLong) {
		this.valueLong = valueLong;
	}



	public double getValueDouble() {
		return valueDouble;
	}



	public void setValueDouble(double valueDouble) {
		this.valueDouble = valueDouble;
	}



	public String getUnit() {
		return unit;
	}



	public void setUnit(String unit) {
		this.unit = unit;
	}



	public String getCodeDisplay() {
		return codeDisplay;
	}



	public void setCodeDisplay(String codeDisplay) {
		this.codeDisplay = codeDisplay;
	}
	
	
	
	
	

}
