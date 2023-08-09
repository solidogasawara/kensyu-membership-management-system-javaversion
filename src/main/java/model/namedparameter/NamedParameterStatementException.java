package model.namedparameter;

public class NamedParameterStatementException extends RuntimeException {
	static final String MES_CANNOT_USE_SAME_PARAMETERNAME = "同じパラメータ名を使うことはできません";
	static final String MES_NOT_USED_PARAMETERNAME = "クエリに指定されたパラメータ名が使用されていませんでした: ";
	static final String MES_EMPTY_SQL = "SQL文が空です";
	static final String MES_NULL_CONNECTION = "Connectionオブジェクトがnullです";
	
	public NamedParameterStatementException(String message) {
		super(message);
	}
}
