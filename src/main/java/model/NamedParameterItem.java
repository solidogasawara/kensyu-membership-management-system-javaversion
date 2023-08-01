package model;

public class NamedParameterItem {
	// パラメータの順番
	private int index;
	// パラメータの名前
	private String parameterName;
	// データ
	private Object value;
	// データの型
	private Class<?> dataType;
	
	public int getIndex() {
		return index;
	}
	public String getParameterName() {
		return parameterName;
	}
	public Object getValue() {
		return value;
	}
	public Class<?> getDataType() {
		return dataType;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public void setDataType(Class<?> dataType) {
		this.dataType = dataType;
	}
}
