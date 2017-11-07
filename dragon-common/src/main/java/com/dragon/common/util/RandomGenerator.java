package com.dragon.common.util;

/**
 * 随机数工具类
 *
 */
public class RandomGenerator {
	
	private static java.util.Random random = new java.util.Random();

	private static char[] data = new char[] { '0', '1', '2', '3', '4', '5',
		'6', '7', '8', '9', 'A', 'a', 'B', 'b', 'C', 'c', 'D', '0', '1',
		'2', '3', '4', '5', 'd', 'E', 'e', 'F', 'f', 'G', 'g', 'H', 'h',
		'I', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'i', 'J',
		'j', 'K', 'k', 'L', 'M', 'm', '6', '7', '8', '9', 'N', 'n', 'O',
		'o', 'P', 'p', 'Q', 'q', 'R', 'r', 'S', 's', 'T', 't', 'U', 'u',
		'V', 'v', '0', '1', '2', '3', '4', '5', 'W', 'w', 'X', 'x', 'Y',
		'y', 'Z', 'z' };

	private static char[] data2 = new char[] { '0', '1', '2', '3', '4', '5',
		'6', '7', '8', '9', 'A', 'B', 'C', 'D', '0', '1', '2', '3', '4',
		'5', 'E', 'F', 'G', 'H', 'I', '0', '1', '2', '3', '4', '5', '6',
		'7', '8', '9', 'J', 'K', 'L', 'M', '6', '7', '8', '9', 'N', 'O',
		'P', 'Q', 'R', 'S', 'T', 'U', 'V', '0', '1', '2', '3', '4', '5',
		'W', 'X', 'Y', 'Z' };
	
	private RandomGenerator() {
	}
	
	/**
	 * 随机生成指定长度的数据,包含英文
	 * @param size
	 * @return
	 */
	public static String buildRandomChar(int size) {
		StringBuffer sb = new StringBuffer();
		int tmp = 0;
		for(int i = 0; i < size; i++) {
			tmp = random.nextInt(99);
			if(tmp >= data.length) {
				i--;
				continue;
			}
			sb.append(data[tmp]);
		}
		return sb.toString();
	}
	
	/**
	 * 随机生成指定长度的数据,包含大写英文
	 * @param size
	 * @return
	 */
	public static String buildRandomUpperChar(int size) {
		StringBuffer sb = new StringBuffer();
		int tmp = 0;
		for(int i = 0; i < size; i++) {
			tmp = random.nextInt(99);
			if(tmp >= data2.length) {
				i--;
				continue;
			}
			sb.append(data2[tmp]);
		}
		return sb.toString();
	}
	
	/**
	 * 生成纯数字的随机数
	 * @param size
	 * @return
	 */
	public static String buildRandomNumber(int size) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < size; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println("buildRandomChar(int size)=【"+ buildRandomChar(6) +"】");
		System.out.println("buildRandomUpperChar(int size)=【"+ buildRandomUpperChar(6) +"】");
		System.out.println("buildRandomNumber(int size)=【"+ buildRandomNumber(6) +"】");
	}
	
}