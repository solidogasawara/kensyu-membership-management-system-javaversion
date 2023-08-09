package enums;

public class ParamEnums {
	// 性別を表すenum
	public enum Gender {
		// DBに保存する際は、bit型で保存する
		// 0 = 男性、1 = 女性 を表す
		
		// 男性
		MALE(0),
		
		// 女性
		FEMALE(1);
		
		private final int bitNum;
		
		private Gender(int bitNum) {
			this.bitNum = bitNum;
		}
		
		public int getBitNum() {
			return this.bitNum;
		}
	}
	
	// 会員状態を表すenum
	public enum MemberStatus {
		// DBに保存する際は、bit型で保存する
		// 0 = 退会済み、1 = 有効 を表す
		
		// 有効
		ACTIVE(1),
		
		// 退会済み
		INACTIVE(0);
		
		private final int bitNum;
		
		private MemberStatus(int bitNum) {
			this.bitNum = bitNum;
		}
		
		public int getBitNum() {
			return this.bitNum;
		}
	}
	
	// 削除フラグを表すenum
	public enum DeleteFlag {
		// DBに保存する際は、bit型で保存する
		// 0 = 非削除、1 = 削除 を表す
		
		// 非削除
		NOT_DELETED(0),
		
		// 削除
		DELETED(1);
		
		private final int bitNum;
		
		private DeleteFlag(int bitNum) {
			this.bitNum = bitNum;
		}
		
		public int getBitNum() {
			return this.bitNum;
		}
	}
}
