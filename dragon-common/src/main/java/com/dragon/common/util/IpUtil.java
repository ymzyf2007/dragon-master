package com.dragon.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * IP处理工具类
 *
 */
public class IpUtil {

	/**
     * 判断IP地址的合法性，采用了正则表达式的方法来判断
     * return true，合法
     * */
    public static boolean ipCheck(String text) {
        if (text != null && !text.isEmpty()) {
            // 定义正则表达式
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            // 判断ip地址是否与正则表达式匹配
            if (text.matches(regex)) {
                // 返回判断信息
                return true;
            } else {
                // 返回判断信息
                return false;
            }
        }
        return false;
    }
    
//    public static String getIpAddr(HttpServletRequest request) {
//		String ip = request.getHeader("x-forwarded-for");
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_CLIENT_IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//        	ip = request.getRemoteAddr();
//        }
//        return ip;
//	}
    
    private static Map<String, String> maskLength_mask = new HashMap<String, String>();
	static {
		maskLength_mask.put("16", "255.255.0.0");
		maskLength_mask.put("17", "255.255.128.0");
		maskLength_mask.put("18", "255.255.192.0");
		maskLength_mask.put("19", "255.255.224.0");
		maskLength_mask.put("20", "255.255.240.0");
		maskLength_mask.put("21", "255.255.248.0");
		maskLength_mask.put("22", "255.255.252.0");
		maskLength_mask.put("23", "255.255.254.0");
		maskLength_mask.put("24", "255.255.255.0");
		maskLength_mask.put("25", "255.255.255.128");
		maskLength_mask.put("26", "255.255.255.192");
		maskLength_mask.put("27", "255.255.255.224");
		maskLength_mask.put("28", "255.255.255.240");
		maskLength_mask.put("29", "255.255.255.248");
		maskLength_mask.put("30", "255.255.255.252");
		maskLength_mask.put("31", "255.255.255.254");
		maskLength_mask.put("32", "255.255.255.255");
	}
	
	/**
	 * 存放网段的起始IP地址和结束IP地址
	 */
	public static class NetworkBean {
		
		private String startAddress;
		
		private long startAddressNumber;
		
		private String endAddress;
		
		private long endAddressNumber;

		public String getStartAddress() {
			return startAddress;
		}

		public void setStartAddress(String startAddress) {
			this.startAddress = startAddress;
			this.startAddressNumber = IpUtil.ipToLong(this.startAddress);
		}

		public long getStartAddressNumber() {
			return startAddressNumber;
		}

		public String getEndAddress() {
			return endAddress;
		}

		public void setEndAddress(String endAddress) {
			this.endAddress = endAddress;
			this.endAddressNumber = IpUtil.ipToLong(this.endAddress);
		}

		public long getEndAddressNumber() {
			return endAddressNumber;
		}

	}
	
	/**
	 * 通过掩码位数获得子网掩码
	 * @param maskLength
	 * @return
	 */
	public static String maskByMaskLength(String maskLength) {
		return maskLength_mask.get(maskLength);
	}
	
	/**
	 * 把IP地址转换为长整型
	 * @param ip
	 * @return
	 */
	public static long ipToLong(String ip) {
		if(ip == null || "".equals(ip)) {
			return -1;
		}
		StringTokenizer st = new StringTokenizer(ip, ".");
		long returnIP = 0;
		if (st.countTokens() != 4) {
			return -1;
		}
		while (st.hasMoreTokens()) {
			returnIP = (returnIP << 8) + Integer.parseInt(st.nextToken());
		}
		return returnIP;
	}
	
	/**
	 * 把IP地址转换为长整型
	 * @param ip
	 * @return
	 */
	/*public static long ipToLong2(String ip) {
		if(ip == null || "".equals(ip)) {
			return -1;
		}
		long[] ipTemp = new long[4];
		int index1 = ip.indexOf(".");
		int index2 = ip.indexOf(".", index1 + 1);
		int index3 = ip.indexOf(".", index2 + 1);
		ipTemp[0] = Long.parseLong(ip.substring(0, index1));
		ipTemp[1] = Long.parseLong(ip.substring(index1 + 1, index2));
		ipTemp[2] = Long.parseLong(ip.substring(index2 + 1, index3));
		ipTemp[3] = Long.parseLong(ip.substring(index3 + 1));
		
		return (ipTemp[0] << 24) + (ipTemp[1] << 16) + (ipTemp[2] << 8) + ipTemp[3];    
	}*/
	
	/**
	 * 把长整型转换为ip地址
	 * 
	 * @param ip long
	 * @return String
	 */
	public static String longToIp(long ip) {
		String partString = "";
		long ipTemp = ip;
		long ipPart = -1;
		while (true) {
			ipPart = ipTemp % 256;
			ipTemp = ipTemp >> 8;
			partString = String.valueOf(ipPart) + "." + partString;
			if (ipTemp < 1) {
				break;
			}
		}
		return partString.substring(0, partString.length() - 1);
	}
	
	/**
	 * 把长整型转换为ip地址
	 * 
	 * @param ip long
	 * @return String
	 */
	/*public static String longToIp2(long longIP) {
		StringBuffer sb = new StringBuffer();
        sb.append(String.valueOf(longIP>>>24));//直接右移24位
        sb.append(".");
        sb.append(String.valueOf((longIP&0x00FFFFFF)>>>16)); //将高8位置0，然后右移16位
        sb.append(".");
        sb.append(String.valueOf((longIP&0x0000FFFF)>>>8));
        sb.append(".");
        sb.append(String.valueOf(longIP&0x000000FF));
        
        return sb.toString(); 
	}*/
	
	/**
	 * 提供一个ip地址(如192.168.1.250), 以及掩码的长度(如24), 得到网络地址（如192.168.1.0）
	 */
	public static long getNetworkAddr(long ip, int maskLength) {
		return ip & (long) (Math.pow(2, 32) - 1 - (Math.pow(2, 32 - maskLength) - 1));
	}
	
	/**
	 * 根据子网掩码获取掩码位数
	 * 
	 * @param maskIp
	 * @return
	 */
	public static int getMaskBitByMaskIp(String maskIp) {
		int bit = 0;
		long maskl = ipToLong(maskIp);
		while(true) {
			if(maskl == 0) {
				break;
			}
			maskl = ((maskl << 1) & 0x0FFFFFFFFL);
			bit++;
		}
		return bit;
	}
	
	/**
	 * 根据一个IP地址和一个子网掩码获取该IP地址所在网段的起始IP地址和结束IP地址，并保存到NetworkBean中
	 * @param ip
	 * @param mask
	 * @return
	 */
	public static NetworkBean getRelatedSubnet(String ip, String mask) {
		NetworkBean netBean = new NetworkBean();
		long ipl = ipToLong(ip);
		long maskl = ipToLong(mask);

		long startIpl = ipl & maskl;
		long endIpl = ipl | ((1l << 32) - 1 - maskl);

		netBean.setStartAddress(longToIp(startIpl));
		netBean.setEndAddress(longToIp(endIpl));
		return netBean;
	}
	
	public static void main(String[] args) {
		String ip = "192.168.5.4";
    	System.out.println(ipCheck(ip));
		
		NetworkBean test = getRelatedSubnet("192.168.22.7", "255.255.255.252");
		System.out.println(test.getStartAddress());
		System.out.println(test.getEndAddress());
	}
	
}