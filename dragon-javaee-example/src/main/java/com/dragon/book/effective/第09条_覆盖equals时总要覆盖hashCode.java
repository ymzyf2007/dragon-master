package com.dragon.book.effective;

import java.util.HashMap;
import java.util.Map;

public class 第09条_覆盖equals时总要覆盖hashCode {
	
	public static void main(String[] args) {
		Map<PhoneNumber, String> m = new HashMap<PhoneNumber, String>();
		m.put(new PhoneNumber(707, 867, 5309), "Alex");
		
		System.out.println(m.get(new PhoneNumber(707, 867, 5309)));
		// 输出为：null
		// 因为这里涉及两个PhoneNumber对象实例，第一个用于插入到HashMap中，第二个实例与第一个相等，被用于获取。
		// 由于PhoneNumber类没有覆盖hashCode方法，从而导致两个的实例具有不相等的散列码
	}

}

final class PhoneNumber {
	private final short areaCode;
	private final short prefix;
	private final short lineNumber;
	
	public PhoneNumber(int areaCode, int prefix, int lineNumber) {
		rangeCheck(areaCode, 999, "area code");
		rangeCheck(prefix, 999, "prefix");
		rangeCheck(lineNumber, 9999, "line number");
		this.areaCode = (short) areaCode;
		this.prefix = (short) prefix;
		this.lineNumber = (short) lineNumber;
	}

	private void rangeCheck(int arg, int max, String name) {
		if(arg < 0 || arg > max) {
			throw new IllegalArgumentException(name + "：" + arg);
		}
	}

	@Override
	public boolean equals(Object o) {
		if(o == this)
			return true;
		if(!(o instanceof PhoneNumber))
			return false;
		PhoneNumber pn = (PhoneNumber) o;
		return pn.lineNumber == this.lineNumber && pn.prefix == this.prefix && pn.areaCode == this.areaCode;
	}

	// 解决问题，重写equals时再重写hashCode
	@Override
	public int hashCode() {
		// 虽然这是合法的，但是及其恶劣，因为它使每个对象都具有相同的散列码，因此，每个对象都被映射到同一个散列桶中，使散列表退化为链表
//		return 42;
		// 合理方式【为不相等的对象生成不相等的散列码】
		int result = 17;
		result = 31 * result + areaCode;
		result = 31 * result + prefix;
		result = 31 * result + lineNumber;
		return result;
	}
	
}