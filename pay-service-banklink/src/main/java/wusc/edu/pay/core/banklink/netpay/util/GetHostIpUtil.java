package wusc.edu.pay.core.banklink.netpay.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class GetHostIpUtil {

	public static String getHostIp() {
		Enumeration<NetworkInterface> netInterfaces = null;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					InetAddress address = ips.nextElement();
					if (address instanceof Inet4Address) {
						if (address.getHostAddress().startsWith("172")) {
							return address.getHostAddress();
						}
					}
				}
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(GetHostIpUtil.getHostIp());
	}

}
