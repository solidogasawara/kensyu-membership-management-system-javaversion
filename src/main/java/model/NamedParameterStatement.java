package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 名前付きパラメータを使用したSQL文からPreparedStatementを生成するクラス
 */
public class NamedParameterStatement {
	private Connection con;
	private String sql;
	
	// NamedParameterItemを格納するList
	// パラメータ名やデータなどが保存される
	private List<NamedParameterItem> items;
	
	// パラメータ名の重複をチェックするためのList
	private List<String> parameterNames;
	
	/**
	 * コンストラクタ
	 * 
	 * ConnectionオブジェクトとSQL文を引数に取る
	 * SQL文は以下のように、パラメータ名を書いたものを渡す
	 * 
	 * SELECT * FROM table_name WHERE id = @id OR name = @name
	 * 
	 * @param con
	 * @param sql
	 */
	public NamedParameterStatement(Connection con, String sql) {
		this.con = con;
		this.sql = sql;
		
		items = new ArrayList<NamedParameterItem>();
		parameterNames = new ArrayList<String>();
	}
	
	/*
	 * パラメータを追加するメソッド
	 * 実際には、NamedParameterItemにパラメータ名やデータを格納しているだけで、
	 * SQL文にパラメータがセットされたりはしない
	 * 
	 * 同じパラメータ名を使用することはできず、使用した場合は例外が投げられる
	 * */
	
	public void parameterAdd(String name, Integer value) {
		if(!parameterNames.contains(name)) {
			NamedParameterItem item = new NamedParameterItem();
	        item.setParameterName(name);
	        item.setValue(value);
	        item.setDataType(Integer.class);
	        
	        items.add(item);
	        parameterNames.add(name);
		} else {
			throw new DuplicateParameterNameException();
		}
    }
	
	public void parameterAdd(String name, String value) {
		if(!parameterNames.contains(name)) {
	        NamedParameterItem item = new NamedParameterItem();
	        item.setParameterName(name);
	        item.setValue(value);
	        item.setDataType(String.class);
	        
	        items.add(item);
		} else {
			throw new DuplicateParameterNameException();
		}
    }
	
	public void parameterAdd(String name, Date value) {
		if(!parameterNames.contains(name)) {
			NamedParameterItem item = new NamedParameterItem();
	        item.setParameterName(name);
	        item.setValue(value);
	        item.setDataType(Date.class);
	        
	        items.add(item);
		} else {
			throw new DuplicateParameterNameException();
		}
    }
	
	public void parameterAdd(String name, Double value) {
		if(!parameterNames.contains(name)) {
	        NamedParameterItem item = new NamedParameterItem();
	        item.setParameterName(name);
	        item.setValue(value);
	        item.setDataType(Double.class);
	        
	        items.add(item);
		} else {
			throw new DuplicateParameterNameException();
		}
    }
	
	public void parameterAdd(String name, Boolean value) {
		if(!parameterNames.contains(name)) {
	        NamedParameterItem item = new NamedParameterItem();
	        item.setParameterName(name);
	        item.setValue(value);
	        item.setDataType(Boolean.class);
	        
	        items.add(item);
		} else {
			throw new DuplicateParameterNameException();
		}
    }
	
	// 渡されたパラメータがどの順番なのかを調べて、itemsに保存されているNamedParameterItemのindexにその順番を格納する
	private void calcurateIndex() {
		// パラメータ名と、そのパラメータ名の文字の位置を格納するMap
		Map<String, Integer> parameterMap = new HashMap<>();
        
		// indexOfメソッドを利用してパラメータ名の文字位置を調べてMapに格納する
        for(NamedParameterItem item : items) {
            int index = sql.indexOf(item.getParameterName());
            
            parameterMap.put(item.getParameterName(), index);
        }
        
        // 文字位置を基準に昇順にparameterMapの中身をソートするList
        List<Map.Entry<String, Integer>> sortedList = new ArrayList<Map.Entry<String, Integer>>(parameterMap.entrySet());
        
        // 文字位置を基準にソートする
        Collections.sort(sortedList, new Comparator<Map.Entry<String, Integer>>() {
        	@Override
            public int compare(Entry<String, Integer> entry1, Entry<String, Integer> entry2) {
                return ((Integer) entry1.getValue()).compareTo((Integer) entry2.getValue());
            }
        });
        
        // NamedParameterItemのindexフィールドに順番を格納する
        for(int i = 0; i < items.size(); i++) {
            NamedParameterItem item = items.get(i);
            
            for(int j = 0; j < sortedList.size(); j++) {
                Entry<String, Integer> entry = sortedList.get(j);
                
                if(item.getParameterName().equals(entry.getKey())) {
                    item.setIndex(j + 1);
                    break;
                }
            }
        }
	}
	
	// パラメータ名をプレースホルダに変換する
	private String convertNamedParametersToPlaceholders() {
		String convertedSql = sql;
		
		// パラメータ名を「?」に変換する
		for(NamedParameterItem item : items) {
			convertedSql = convertedSql.replace(item.getParameterName(), "?");
		}
		
		return convertedSql;
	}
	
	public PreparedStatement prepareStatement() throws SQLException {
		// インデックスを計算
		calcurateIndex();
		
		// SQL文にあるパラメータ名をプレースホルダに変換する
		String convertedSql = convertNamedParametersToPlaceholders();
		
		PreparedStatement pstmt = con.prepareStatement(convertedSql);
		
		// itemsの要素数だけループして、それぞれの型にあったメソッドが呼び出されて
		// PreparedStatementに値がセットされていく
		for(NamedParameterItem item : items) {
			// 保存されているデータの型で分岐する
			if(item.getDataType() == Integer.class) {
				Integer value = (Integer) item.getValue();
				
				pstmt.setInt(item.getIndex(), value);
			} else if (item.getDataType() == String.class) {
				String value = (String) item.getValue();
				
				pstmt.setString(item.getIndex(), value);
			} else if (item.getDataType() == Date.class) {
				Date value = (Date) item.getValue();
				
				pstmt.setDate(item.getIndex(), value);
			} else if (item.getDataType() == Double.class) {
				Double value = (Double) item.getValue();
				
				pstmt.setDouble(item.getIndex(), value);
			} else if (item.getDataType() == Boolean.class) {
				Boolean value = (Boolean) item.getValue();
				
				pstmt.setBoolean(item.getIndex(), value);
			}
		}
		
		return pstmt;
	}
}
