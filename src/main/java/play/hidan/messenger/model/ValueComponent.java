package play.hidan.messenger.model;

public class ValueComponent {
	
	//ValueQuantity
	private String valueString;
	private double valueDouble;
	
	private String unit; //
	private String valueType;  // Glucose [Moles/volume] in Blood
	
	
		
	public ValueComponent() {
		
	}



	public String getValueString() {
		return valueString;
	}



	public void setValueString(String valueString) {
		this.valueString = valueString;
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



	public String getValueType() {
		return valueType;
	}



	public void setValueType(String valueType) {
		this.valueType = valueType;
	}



		
	
	
	
	

}
