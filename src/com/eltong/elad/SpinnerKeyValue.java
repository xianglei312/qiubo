package com.eltong.elad;

/**
 * @author XXD
 * @note 下拉框键值对应封装类
 */
public class SpinnerKeyValue {

	public String value = "";
	public String id = "";

	public SpinnerKeyValue(String value, String id) {
		this.value = value;
		this.id = id;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return value;
	}
}
