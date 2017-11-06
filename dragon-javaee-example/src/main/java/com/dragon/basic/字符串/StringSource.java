package com.dragon.basic.字符串;

import java.io.UnsupportedEncodingException;

/**
 * String源码解析
 * 
 */
public class StringSource {
	
	/*###############################1、charAt(int index)###############################/
	/**
	 * 返回指定索引处的char值。
	 * @param s
	 * 源码如下：
	 * public char charAt(int index) {
	 *     if ((index < 0) || (index >= count)) {
	 *         throw new StringIndexOutOfBoundsException(index);
	 *     }
	 *     return value[index + offset];
	 * }
	 */
	public static void testCharAt(String s) {
		if(s != null && !s.isEmpty()) {
			for(int i = 0; i < s.length(); i++) {
				System.out.println(s.charAt(i));
			}
		}
	}
	
//	codePointAt(int index) 返回指定索引处的字符（Unicode 代码点）。
//	codePointBefore(int index) 返回指定索引之前的字符（Unicode 代码点）。
//	codePointCount(int beginIndex, int endIndex) 返回此 String 的指定文本范围中的 Unicode 代码点数。
	
	/*###############################2、compareTo(String anotherString)###############################/
	/**
	 * 按字典顺序比较两个字符串。
	 * @param anotherString
	 * 源码如下：
	 public int compareTo(String anotherString) {
		int len1 = count;
		int len2 = anotherString.count;
		int n = Math.min(len1, len2);
		char v1[] = value;
		char v2[] = anotherString.value;
		int i = offset;
		int j = anotherString.offset;

		if (i == j) {
			int k = i;
			int lim = n + i;
			while (k < lim) {
				char c1 = v1[k];
				char c2 = v2[k];
				if (c1 != c2) {
					return c1 - c2;
				}
				k++;
			}
		} else {
			while (n-- != 0) {
				char c1 = v1[i++];
				char c2 = v2[j++];
				if (c1 != c2) {
					return c1 - c2;
				}
			}
		}
		return len1 - len2;
	}
	 */
	public static void testCompareTo(String anotherString) {
		String thisString = "abcdefg";
		int k = thisString.compareTo(anotherString);
		System.out.println("k=" + k);
	}
	
	/*###############################3、compareToIgnoreCase(String anotherString)###############################/
	/**
	 * 按字典顺序比较两个字符串，不考虑大小写。
	 * @param anotherString
	 */
	public static void testCompareToIgnoreCase(String anotherString) {
		String thisString = "abcdefg";
		int k = thisString.compareToIgnoreCase(anotherString);
		System.out.println("k=" + k);
	}
	
	/*###############################4、concat(String str)###############################/
	/**
	 * 将指定字符串连接到此字符串的结尾。
	 * @param str
	 * 源码如下：
	 public String concat(String str) {
		int otherLen = str.length();
		if (otherLen == 0) {
			return this;
		}
		char buf[] = new char[count + otherLen];
		getChars(0, count, buf, 0);
		str.getChars(0, otherLen, buf, count);
		return new String(0, count + otherLen, buf);
	}
	 */
	public static void testConcat(String str) {
		String thisString = "abcdefg";
		String newString = thisString.concat(str);
		System.out.println(newString);
	}
	
	/*###############################5、contains(CharSequence s)###############################/
	/**
	 * 当且仅当此字符串包含指定的 char 值序列时，返回 true。
	 * @param s
	 * 源码如下：
	public boolean contains(CharSequence s) {
        return indexOf(s.toString()) > -1;
    }
	 */
	public static void testContains(CharSequence s) {
		String thisString = "abcdefg";
		boolean exists = thisString.contains(s);
		System.out.println(exists ? thisString + " contains " + s : thisString + " not contains " + s);
	}
	
	/*###############################6、indexOf(String str)###############################/
	/**
	 * 返回指定子字符串在此字符串中第一次出现处的索引。
	 * @param str
	 * 源码如下：
	public int indexOf(String str) {
		return indexOf(str, 0);
    }
	 */
	public static void testIndexOf(String str) {
		String thisString = "abcdefg";
		int index = thisString.indexOf(str);
		System.out.println("index=" + index);
	}
	
	/*###############################7、indexOf(String str, int fromIndex)###############################/
	/**
	 * 返回指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始。 
	 * @param str
	 * @param fromIndex
	 * 源码如下：
	public int indexOf(String str, int fromIndex) {
        return indexOf(value, offset, count, str.value, str.offset, str.count, fromIndex);
    }
	 */
	public static void testIndexOf(String str, int fromIndex) {
		String thisString = "abcdefg";
		int index = thisString.indexOf(str, fromIndex);
		System.out.println("index=" + index);
	}
	
	/*###############################8、indexOf(int ch)###############################/
	/**
	 * 返回指定字符在此字符串中第一次出现处的索引。
	 * @param ch
	 * 源码如下：
	public int indexOf(int ch) {
		return indexOf(ch, 0);
	}
	 */
	public static void testIndexOf(int ch) {
		String thisString = "abcdefg";
		int index = thisString.indexOf(ch);
		System.out.println("index=" + index);
	}
	
	/*###############################9、indexOf(int ch, int fromIndex)###############################/
	/**
	 * 返回在此字符串中第一次出现指定字符处的索引，从指定的索引开始搜索。 
	 * @param ch
	 * @param fromIndex
	 * 源码如下：
	public int indexOf(int ch, int fromIndex) {
		int max = offset + count;
		char v[] = value;

		if (fromIndex < 0) {
			fromIndex = 0;
		} else if (fromIndex >= count) {
			// Note: fromIndex might be near -1>>>1.
			return -1;
		}

		int i = offset + fromIndex;
		if (ch < Character.MIN_SUPPLEMENTARY_CODE_POINT) {
			// handle most cases here (ch is a BMP code point or a
			// negative value (invalid code point))
			for (; i < max; i++) {
				if (v[i] == ch) {
					return i - offset;
				}
			}
			return -1;
		}

		if (ch <= Character.MAX_CODE_POINT) {
			// handle supplementary characters here
			char[] surrogates = Character.toChars(ch);
			for (; i < max; i++) {
				if (v[i] == surrogates[0]) {
					if (i + 1 == max) {
						break;
					}
					if (v[i + 1] == surrogates[1]) {
						return i - offset;
					}
				}
			}
		}
		return -1;
	}
	 */
	public static void testIndexOf(int ch, int fromIndex) {
		String thisString = "abcdefg";
		int index = thisString.indexOf(ch, fromIndex);
		System.out.println("index=" + index);
	}
	
	/*###############################12、copyValueOf(char data[])###############################/
	/**
	 * 返回指定数组中表示该字符序列的 String。
	 * @param data[]
	 * 源码如下：
	public static String copyValueOf(char data[]) {
		return copyValueOf(data, 0, data.length);
	}
	 */
	public static String testCopyValueOf(char data[]) {
		return String.copyValueOf(data);
	}
	
	/*###############################13、copyValueOf(char[] data, int offset, int count)###############################/
	/**
	 * 返回指定数组中表示该字符序列的 String。
	 * @param data[]
	 * @param offset
	 * @param count
	 * 源码如下：
	public static String testCopyValueOf(char data[]) {
		return String.copyValueOf(data);
	}
	 */
	public static String testCopyValueOf(char data[], int offset, int count) {
		return String.copyValueOf(data, offset, count);
	}
	
	/*###############################14、startsWith(String prefix)###############################/
	/**
	 * 测试此字符串是否以指定的前缀开始。
	 * @param prefix
	 * 源码如下：
	public boolean startsWith(String prefix) {
		return startsWith(prefix, 0);
	}
	 */
	public static void testStartsWith(String prefix) {
		String thisString = "abcdef";
		boolean flag = thisString.startsWith(prefix);
		System.out.println(flag ? thisString + " startsWith " + prefix : thisString + " not startsWith " + prefix);
	}
	
	/*###############################15、startsWith(String prefix, int toffset)###############################/
	/**
	 * 测试此字符串从指定索引开始的子字符串是否以指定前缀开始。
	 * @param prefix
	 * @param toffset
	 * 源码如下：
	public boolean startsWith(String prefix, int toffset) {
		char ta[] = value;
		int to = offset + toffset;
		char pa[] = prefix.value;
		int po = prefix.offset;
		int pc = prefix.count;
		// Note: toffset might be near -1>>>1.
		if ((toffset < 0) || (toffset > count - pc)) {
			return false;
		}
		while (--pc >= 0) {
			if (ta[to++] != pa[po++]) {
				return false;
			}
		}
		return true;
	}
	 */
	public static void testStartsWith(String prefix, int toffset) {
		String thisString = "abcdef";
		boolean flag = thisString.startsWith(prefix, toffset);
		System.out.println(flag ? thisString + " startsWith " + prefix : thisString + " not startsWith " + prefix);
	}
	
	/*###############################16、endsWith(String suffix)###############################/
	/**
	 * 测试此字符串是否以指定的后缀结束。
	 * @param suffix
	 * 源码如下：
	public boolean endsWith(String suffix) {
		return startsWith(suffix, count - suffix.count);
	}
	 */
	public static void testEndsWith(String suffix) {
		String thisString = "abcdef";
		boolean flag = thisString.endsWith(suffix);
		System.out.println(flag ? thisString + " endsWith " + suffix : thisString + " not endsWith " + suffix);
	}
	
	/*###############################17、equals(Object anObject)###############################/
	/**
	 * 将此字符串与指定的对象比较。
	 * @param anObject
	 * 源码如下：
	public boolean equals(Object anObject) {
		if (this == anObject) {
			return true;
		}
		if (anObject instanceof String) {
			String anotherString = (String) anObject;
			int n = count;
			if (n == anotherString.count) {
				char v1[] = value;
				char v2[] = anotherString.value;
				int i = offset;
				int j = anotherString.offset;
				while (n-- != 0) {
					if (v1[i++] != v2[j++])
						return false;
				}
				return true;
			}
		}
		return false;
	}
	 */
	public static void testEquals(String str) {
		String thisString = "abcdef";
		boolean flag = thisString.equals(str);
		System.out.println(flag ? thisString + " equals " + str : thisString + " not equals " + str);
	}
	
	/*###############################18、regionMatches(boolean ignoreCase, int toffset, String other, int ooffset, int len)###############################/
	/**
	 * 测试两个字符串区域是否相等。
	 * 源码如下：
	public boolean regionMatches(boolean ignoreCase, int toffset, String other, int ooffset, int len) {
		char ta[] = value;
		int to = offset + toffset;
		char pa[] = other.value;
		int po = other.offset + ooffset;
		// Note: toffset, ooffset, or len might be near -1>>>1.
		if ((ooffset < 0) || (toffset < 0) || (toffset > (long) count - len) || (ooffset > (long) other.count - len)) {
			return false;
		}
		while (len-- > 0) {
			char c1 = ta[to++];
			char c2 = pa[po++];
			if (c1 == c2) {
				continue;
			}
			if (ignoreCase) {
				char u1 = Character.toUpperCase(c1);
				char u2 = Character.toUpperCase(c2);
				if (u1 == u2) {
					continue;
				}
				if (Character.toLowerCase(u1) == Character.toLowerCase(u2)) {
					continue;
				}
			}
			return false;
		}
		return true;
	}
	 */
	public static void testRegionMatches(boolean ignoreCase, int toffset, String other, int ooffset, int len) {
		String thisString = "abcdef";
		boolean flag = thisString.regionMatches(ignoreCase, toffset, other, ooffset, len);
		System.out.println(flag ? thisString + " regionMatches " + other : thisString + " not regionMatches " + other);
	}
	
	/*###############################19、regionMatches(int toffset, String other, int ooffset, int len)###############################/
	/**
	 * 测试两个字符串区域是否相等。
	 * 源码如下：
	public boolean regionMatches(int toffset, String other, int ooffset, int len) {
		char ta[] = value;
		int to = offset + toffset;
		char pa[] = other.value;
		int po = other.offset + ooffset;
		if ((ooffset < 0) || (toffset < 0) || (toffset > (long) count - len) || (ooffset > (long) other.count - len)) {
			return false;
		}
		while (len-- > 0) {
			if (ta[to++] != pa[po++]) {
				return false;
			}
		}
		return true;
	}
	 */
	public static void testRegionMatches(int toffset, String other, int ooffset, int len) {
		String thisString = "abcdef";
		boolean flag = thisString.regionMatches(toffset, other, ooffset, len);
		System.out.println(flag ? thisString + " regionMatches " + other : thisString + " not regionMatches " + other);
	}
	
	/*###############################20、equalsIgnoreCase(String anotherString)###############################/
	/**
	 * 将此 String 与另一个 String 比较，不考虑大小写。
	 * 源码如下：
	public boolean equalsIgnoreCase(String anotherString) {
		return (this == anotherString) ? true : (anotherString != null)
				&& (anotherString.count == count)
				&& regionMatches(true, 0, anotherString, 0, count);
	}
	 */
	public static void testEqualsIgnoreCase(String anotherString) {
		String thisString = "abcdef";
		boolean flag = thisString.equalsIgnoreCase(anotherString);
		System.out.println(flag ? thisString + " equalsIgnoreCase " + anotherString : thisString + " not equalsIgnoreCase " + anotherString);
	}
	
	/*###############################21、byte[] getBytes()###############################/
	/**
	 * 使用平台的默认字符集将此 String 编码为 byte 序列，并将结果存储到一个新的 byte 数组中
	 * 源码如下：
	public byte[] getBytes() {
		return StringCoding.encode(value, offset, count);
	}
	 */
	public static void testGetBytes() {
		String thisString = "abcdef";
		byte[] sbyte = thisString.getBytes();
		System.out.println("sbyte.length=" + sbyte.length);
	}
	
	/*###############################22、byte[] getBytes(String charsetName)###############################/
	/**
	 * 使用指定的字符集将此 String 编码为 byte 序列，并将结果存储到一个新的 byte 数组中。
	 * 源码如下：
	public byte[] getBytes(String charsetName)
			throws UnsupportedEncodingException {
		if (charsetName == null)
			throw new NullPointerException();
		return StringCoding.encode(charsetName, value, offset, count);
	}
	 */
	public static void testGetBytes(String charsetName) throws UnsupportedEncodingException {
		String thisString = "abcdef";
		byte[] sbyte = thisString.getBytes(charsetName);
		System.out.println("sbyte.length=" + sbyte.length);
	}
	
	/*###############################23、getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)###############################/
	/**
	 * 将字符从此字符串复制到目标字符数组。
	 * 源码如下：
	public void getChars(int srcBegin, int srcEnd, char dst[], int dstBegin) {
		if (srcBegin < 0) {
			throw new StringIndexOutOfBoundsException(srcBegin);
		}
		if (srcEnd > count) {
			throw new StringIndexOutOfBoundsException(srcEnd);
		}
		if (srcBegin > srcEnd) {
			throw new StringIndexOutOfBoundsException(srcEnd - srcBegin);
		}
		System.arraycopy(value, offset + srcBegin, dst, dstBegin, srcEnd - srcBegin);
	}
	 */
	public static void testGetChars(String source, int srcBegin, int srcEnd, char dst[], int dstBegin) {
		source.getChars(0, source.length(), dst, 0);
	}
	
	/*###############################24、int hashCode()###############################/
	/**
	 * 返回此字符串的哈希码。
	 * 源码如下：
	public int hashCode() {
		int h = hash;
		int len = count;
		if (h == 0 && len > 0) {
			int off = offset;
			char val[] = value;

			for (int i = 0; i < len; i++) {
				h = 31 * h + val[off++];
			}
			hash = h;
		}
		return h;
	}
	 */
	public static int testHashCode() {
		String thisString = "abcdef";
		return thisString.hashCode();
	}
	
	/*###############################25、boolean isEmpty()###############################/
	/**
	 * 当且仅当 length() 为 0 时返回 true。
	 * 源码如下：
	public boolean isEmpty() {
		return count == 0;
	}
	 */
	public static boolean testEmpty() {
		String thisString = "abcdef";
		return thisString.isEmpty();
	}
	
	/*###############################26、int lastIndexOf(String str)###############################/
	/**
	 * 返回指定子字符串在此字符串中最右边出现处的索引。
	 * 源码如下：
	public int lastIndexOf(String str) {
		return lastIndexOf(str, count);
	}
	 */
	public static void testLastIndexOf(String str) {
		String thisString = "abdddcdedf";
		int index = thisString.lastIndexOf(str);
		System.out.println("lastIndexOf=" + index);
	}
	
	/*###############################27、int lastIndexOf(String str, int fromIndex)###############################/
	/**
	 * 返回指定子字符串在此字符串中最后一次出现处的索引，从指定的索引开始反向搜索。
	 * 源码如下：
	public int lastIndexOf(String str, int fromIndex) {
		return lastIndexOf(value, offset, count, str.value, str.offset,
				str.count, fromIndex);
	}
	 */
	public static void testLastIndexOf(String str, int fromIndex) {
		String thisString = "abdddcdedf";
		int index = thisString.lastIndexOf(str, fromIndex);
		System.out.println("lastIndexOf=" + index);
	}
	
	/*###############################28、int lastIndexOf(int ch)###############################/
	/**
	 * 返回指定字符在此字符串中最后一次出现处的索引。
	 * 源码如下：
	public int lastIndexOf(int ch) {
		return lastIndexOf(ch, count - 1);
	}
	 */
	public static void testLastIndexOf(int ch) {
		String thisString = "abdddcdedf";
		int index = thisString.lastIndexOf(ch);
		System.out.println("lastIndexOf=" + index);
	}
	
	/*###############################29、int lastIndexOf(int ch, int fromIndex)###############################/
	/**
	 * 返回指定字符在此字符串中最后一次出现处的索引，从指定的索引处开始进行反向搜索。
	 * 源码如下：
	public int lastIndexOf(int ch, int fromIndex) {
		int min = offset;
		char v[] = value;
		int i = offset + ((fromIndex >= count) ? count - 1 : fromIndex);
		if (ch < Character.MIN_SUPPLEMENTARY_CODE_POINT) {
			for (; i >= min; i--) {
				if (v[i] == ch) {
					return i - offset;
				}
			}
			return -1;
		}
		int max = offset + count;
		if (ch <= Character.MAX_CODE_POINT) {
			char[] surrogates = Character.toChars(ch);
			for (; i >= min; i--) {
				if (v[i] == surrogates[0]) {
					if (i + 1 == max) {
						break;
					}
					if (v[i + 1] == surrogates[1]) {
						return i - offset;
					}
				}
			}
		}
		return -1;
	}
	 */
	public static void testLastIndexOf(int ch, int fromIndex) {
		String thisString = "abdddcdedf";
		int index = thisString.lastIndexOf(ch, fromIndex);
		System.out.println("lastIndexOf=" + index);
	}
	
	/*###############################30、int length()###############################/
	/**
	 * 返回此字符串的长度。
	 * 源码如下：
	public int length() {
		return count;
	}
	 */
	public static void testLength() {
		String thisString = "abdddcdedf";
		int len = thisString.length();
		System.out.println("len=" + len);
	}
	
	/*###############################31、String replace(char oldChar, char newChar)###############################/
	/**
	 * 返回一个新的字符串，它是通过用 newChar 替换此字符串中出现的所有 oldChar 得到的。
	 * 源码如下：
	public String replace(char oldChar, char newChar) {
		if (oldChar != newChar) {
			int len = count;
			int i = -1;
			char[] val = value;
			int off = offset;

			while (++i < len) {
				if (val[off + i] == oldChar) {
					break;
				}
			}
			if (i < len) {
				char buf[] = new char[len];
				for (int j = 0; j < i; j++) {
					buf[j] = val[off + j];
				}
				while (i < len) {
					char c = val[off + i];
					buf[i] = (c == oldChar) ? newChar : c;
					i++;
				}
				return new String(0, len, buf);
			}
		}
		return this;
	}
	 */
	public static void testReplace(char oldChar, char newChar) {
		String thisString = "abdddcdedf";
		String newString = thisString.replace(oldChar, newChar);
		System.out.println("newString=" + newString);
	}
	
	/*###############################32、String replaceAll(String regex, String replacement)###############################/
	/**
	 * 使用给定的 replacement 替换此字符串所有匹配给定的正则表达式的子字符串。
	 * 源码如下：
	public String replaceAll(String regex, String replacement) {
		return Pattern.compile(regex).matcher(this).replaceAll(replacement);
	}
	 */
	public static void testReplaceAll(String regex, String replacement) {
		String thisString = "abdddcdedf";
		String newString = thisString.replaceAll(regex, replacement);
		System.out.println("replaceAll newString=" + newString);
	}
	
	/*###############################33、String replaceFirst(String regex, String replacement)###############################/
	/**
	 * 使用给定的 replacement 替换此字符串匹配给定的正则表达式的第一个子字符串。
	 * 源码如下：
	public String replaceFirst(String regex, String replacement) {
		return Pattern.compile(regex).matcher(this).replaceFirst(replacement);
	}
	 */
	public static void testReplaceFirst(String regex, String replacement) {
		String thisString = "abdddcdddedf";
		String newString = thisString.replaceFirst(regex, replacement);
		System.out.println("replaceFirst newString=" + newString);
	}
	
	/*###############################34、String substring(int beginIndex)###############################/
	/**
	 * 返回一个新的字符串，它是此字符串的一个子字符串。
	 * 源码如下：
	public String substring(int beginIndex) {
		return substring(beginIndex, count);
	}
	 */
	public static void testSubstring(int beginIndex) {
		String thisString = "abcdef";
		String newString = thisString.substring(beginIndex);
		System.out.println("substring newString=" + newString);
	}
	
	/*###############################35、String substring(int beginIndex, int endIndex)###############################/
	/**
	 * 返回一个新字符串，它是此字符串的一个子字符串。 
	 * 源码如下：
	public String substring(int beginIndex, int endIndex) {
		if (beginIndex < 0) {
			throw new StringIndexOutOfBoundsException(beginIndex);
		}
		if (endIndex > count) {
			throw new StringIndexOutOfBoundsException(endIndex);
		}
		if (beginIndex > endIndex) {
			throw new StringIndexOutOfBoundsException(endIndex - beginIndex);
		}
		return ((beginIndex == 0) && (endIndex == count)) ? this : new String(
				offset + beginIndex, endIndex - beginIndex, value);
	}
	 */
	public static void testSubstring(int beginIndex, int endIndex) {
		String thisString = "abcdef";
		String newString = thisString.substring(beginIndex, endIndex);
		System.out.println("substring newString=" + newString);
	}
	
	/*###############################36、char[] toCharArray()###############################/
	/**
	 * 将此字符串转换为一个新的字符数组。 
	 * 源码如下：
	public char[] toCharArray() {
		char result[] = new char[count];
		getChars(0, count, result, 0);
		return result;
	}
	 */
	public static char[] testToCharArray() {
		String thisString = "abcdef";
		return thisString.toCharArray();
	}
	
	/*###############################37、String trim()###############################/
	/**
	 * 返回字符串的副本，忽略前导空白和尾部空白。
	 * 源码如下：
	public String trim() {
		int len = count;
		int st = 0;
		int off = offset;
		char[] val = value;

		while ((st < len) && (val[off + st] <= ' ')) {
			st++;
		}
		while ((st < len) && (val[off + len - 1] <= ' ')) {
			len--;
		}
		return ((st > 0) || (len < count)) ? substring(st, len) : this;
	}
	 */
	public static void testTrim() {
		String thisString = " abcdef ";
		String newString = thisString.trim();
		System.out.println("trim String=" + newString);
	}
     
	/**
	 * String源码，String中的contains、indexOf等方法均用该方法
	 * @param source
	 * @param sourceOffset
	 * @param sourceCount
	 * @param target
	 * @param targetOffset
	 * @param targetCount
	 * @param fromIndex
	 * @return
	 */
	static int indexOf(char[] source, int sourceOffset, int sourceCount,
			char[] target, int targetOffset, int targetCount, int fromIndex) {
		if (fromIndex >= sourceCount) {	// 若指定索引大于等于源字符串长度，并且子串长度不为0，则直接返回-1
			return (targetCount == 0 ? sourceCount : -1);
		}
		if (fromIndex < 0) {	// 如果指定索引小于0，则也是直接从0开始匹配
			fromIndex = 0;
		}
		if (targetCount == 0) {
			return fromIndex;
		}

		char first = target[targetOffset];	// 第一个匹配的字符
		// 匹配最大位置，比如主串是：abcdef；子串：de；则匹配到0 + (6 - 2) = 4;（假设sourceOffset为0），则第一个字符匹配到第4个字符e，如果这个字符都不匹配，则不需要往下走了
		int max = sourceOffset + (sourceCount - targetCount);

		for (int i = sourceOffset + fromIndex; i <= max; i++) {
			/* Look for first character. */
			// 查找第一个匹配字符
			if (source[i] != first) {
				while (++i <= max && source[i] != first);
			}

			/* Found first character, now look at the rest of v2 */
			// 已经找到第一个字符，现在查找剩下字符串
			if (i <= max) {
				int j = i + 1;	// 主串从第二个字符开始，k是子串第二个字符开始
				int end = j + targetCount - 1;	// 结束位置
				for (int k = targetOffset + 1; j < end && source[j] == target[k]; j++, k++)
					;

				if (j == end) {
					/* Found whole string. */
					return i - sourceOffset;
				}
			}
		}
		return -1;
	}
	
	static int lastIndexOf(char[] source, int sourceOffset, int sourceCount,
			char[] target, int targetOffset, int targetCount, int fromIndex) {
		// Check arguments; return immediately where possible. For consistency, don't check for null str.
		int rightIndex = sourceCount - targetCount;
		if (fromIndex < 0) {
			return -1;
		}
		if (fromIndex > rightIndex) {
			fromIndex = rightIndex;
		}
		/* Empty string always matches. */
		if (targetCount == 0) {
			return fromIndex;
		}

		int strLastIndex = targetOffset + targetCount - 1;
		char strLastChar = target[strLastIndex];
		int min = sourceOffset + targetCount - 1;
		int i = min + fromIndex;

		startSearchForLastChar: while (true) {
			while (i >= min && source[i] != strLastChar) {
				i--;
			}
			if (i < min) {
				return -1;
			}
			int j = i - 1;
			int start = j - (targetCount - 1);
			int k = strLastIndex - 1;

			while (j > start) {
				if (source[j--] != target[k--]) {
					i--;
					continue startSearchForLastChar;
				}
			}
			return start - sourceOffset + 1;
		}
	}
	
	public static void main(String[] args) {
		// 1、charAt(int index)：返回指定索引处的char值。
//		testCharAt("asdf");
		// 2、compareTo(String anotherString)：按字典顺序比较两个字符串。
//		testCompareTo("asdf");
		// 3、compareToIgnoreCase(String anotherString)：按字典顺序比较两个字符串，不考虑大小写。
//		testCompareToIgnoreCase("aBCDEFG");
		// 4、concat(String str)：将指定字符串连接到此字符串的结尾。
//		testConcat("_test1");	// 输出：abcdefg_test1
		// 5、contains(CharSequence s)：当且仅当此字符串包含指定的 char 值序列时，返回 true。
//		testContains("bcd");
		// 6、indexOf(String str)：返回指定子字符串在此字符串中第一次出现处的索引。
//		testIndexOf("cd");
		// 7、indexOf(String str, int fromIndex)：返回指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始。 
//		testIndexOf("cd", 4);
		// 8、indexOf(int ch)：返回指定字符在此字符串中第一次出现处的索引。
//		testIndexOf('c');
		// 9、indexOf(int ch, int fromIndex)：返回在此字符串中第一次出现指定字符处的索引，从指定的索引开始搜索。  
//		testIndexOf('c', 3);
		// 10、contentEquals(CharSequence cs)：将此字符串与指定的 CharSequence 比较。  
		// 11、contentEquals(StringBuffer sb)：将此字符串与指定的 StringBuffer 比较。  
        // 12、copyValueOf(char data[])：返回指定数组中表示该字符序列的 String。
//		System.out.println(testCopyValueOf(new char[] {'a', 'b', 'c', 'd', 'e', 'f'}));
		// 13、copyValueOf(char data[], int offset, int count)：返回指定数组中表示该字符序列的 String。
//		System.out.println(testCopyValueOf(new char[] {'a', 'b', 'c', 'd', 'e', 'f'}, 2, 4));
		// 14、startsWith(String prefix)：测试此字符串是否以指定的前缀开始。
//		testStartsWith("bc");
		// 15、startsWith(String prefix, int toffset)：测试此字符串从指定索引开始的子字符串是否以指定前缀开始。
//		testStartsWith("bc", 1);
		// 16、endsWith(String suffix)：测试此字符串是否以指定的后缀结束。
//		testEndsWith("ef");
		// 17、equals(Object anObject)：将此字符串与指定的对象比较。
//		testEquals("abcdef");
		// 18、regionMatches(boolean ignoreCase, int toffset, String other, int ooffset, int len)：测试两个字符串区域是否相等。
//		testRegionMatches(true, 3, "De", 0, 2);
		// 19、regionMatches(int toffset, String other, int ooffset, int len)：测试两个字符串区域是否相等。
//		testRegionMatches(3, "de", 0, 2);
		// 20、equalsIgnoreCase(String anotherString)：将此 String 与另一个 String 比较，不考虑大小写。
//		testEqualsIgnoreCase("AbCDef");
		// 21、byte[] getBytes()：使用平台的默认字符集将此 String 编码为 byte 序列，并将结果存储到一个新的 byte 数组中
//		testGetBytes();
		// 22、byte[] getBytes(String charsetName)：使用指定的字符集将此 String 编码为 byte 序列，并将结果存储到一个新的 byte 数组中。
//		try {
//			testGetBytes("UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		// 23、getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)：将字符从此字符串复制到目标字符数组。
//		String str = "abcdef";
//		char[] dst = new char[str.length()];
//		testGetChars(str, 0, str.length(), dst, 0);
//		for(int i = 0; i < dst.length; i++) {
//			System.out.println("dst[" + i + "]=" + dst[i]);
//		}
		// 24、int hashCode()：返回此字符串的哈希码。
//		System.out.println("hashCode=" + testHashCode());
		// 25、boolean isEmpty()：当且仅当 length() 为 0 时返回 true。
//		System.out.println(testEmpty() ? "empty" : "not empty");
		// 26、int lastIndexOf(String str)：返回指定子字符串在此字符串中最右边出现处的索引。
//		testLastIndexOf("d");
		// 27、int lastIndexOf(String str, int fromIndex)：返回指定子字符串在此字符串中最后一次出现处的索引，从指定的索引开始反向搜索。
//		testLastIndexOf("d", 7);	// abdddcdedf
		// 28、int lastIndexOf(int ch)：返回指定字符在此字符串中最后一次出现处的索引。
//		testLastIndexOf('d');
		// 29、int lastIndexOf(int ch, int fromIndex)：返回指定字符在此字符串中最后一次出现处的索引，从指定的索引处开始进行反向搜索。
//		testLastIndexOf('d', 5);
		// 30、int length()：返回此字符串的长度。
//		testLength();
		// 31、String replace(char oldChar, char newChar)：返回一个新的字符串，它是通过用 newChar 替换此字符串中出现的所有 oldChar 得到的。
//		testReplace('d', 'z');
		// 32、String replaceAll(String regex, String replacement)：使用给定的 replacement 替换此字符串所有匹配给定的正则表达式的子字符串。
//		testReplaceAll("ddd", "yumin");
		// 33、String replaceFirst(String regex, String replacement)：使用给定的 replacement 替换此字符串匹配给定的正则表达式的第一个子字符串。
//		testReplaceFirst("ddd", "yumin");
		// 34、String substring(int beginIndex)：返回一个新的字符串，它是此字符串的一个子字符串。
//		testSubstring(1);
		// 35、String substring(int beginIndex, int endIndex)：返回一个新字符串，它是此字符串的一个子字符串。
//		testSubstring(1, 2);
		// 36、char[] toCharArray()：将此字符串转换为一个新的字符数组。
//		char[] value = testToCharArray();
//		for(int i = 0; i < value.length; i++) {
//			System.out.println(value[i]);
//		}
		// 37、String trim()：返回字符串的副本，忽略前导空白和尾部空白。
//		testTrim();
		
//		char[] source = new char[] {'a', 'b', 'c', 'd', 'e', 'f'};
//		char[] target = new char[] {'d', 'e'};
//		int index = indexOf(source, 0, source.length, target, 0, target.length, 0);
//		System.out.println("index=" + index);
	}
}