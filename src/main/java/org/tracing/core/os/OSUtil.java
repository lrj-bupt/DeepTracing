package org.tracing.core.os;

import java.net.*;
import java.util.Enumeration;
import java.util.List;

public class OSUtil {
    private static volatile String HOST_NAME;
    private static volatile List<String> IPV4_LIST;

    public static String getHostName() {
        try {
            InetAddress host = InetAddress.getLocalHost();
            HOST_NAME = host.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return HOST_NAME;
    }

    public static List<String> getAllIPV4() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while(interfaces.hasMoreElements()) {
                NetworkInterface nextElement = interfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = nextElement.getInetAddresses();
                nextElement.getInterfaceAddresses();
                nextElement.getMTU();  // 最大传输单元
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if(inetAddress instanceof Inet4Address) {
                        String address = inetAddress.getHostAddress();
                        IPV4_LIST.add(address);
                    }
                }
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }

        return IPV4_LIST;
    }
}
