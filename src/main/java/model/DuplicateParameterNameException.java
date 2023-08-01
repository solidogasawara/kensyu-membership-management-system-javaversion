package model;

public class DuplicateParameterNameException extends RuntimeException {
	private static final String MESSAGE = "同じパラメータ名を使うことはできません";
	
	public DuplicateParameterNameException() {
		super(MESSAGE);
	}
}
