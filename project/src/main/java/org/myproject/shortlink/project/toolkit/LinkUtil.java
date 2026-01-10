package org.myproject.shortlink.project.toolkit;

import jakarta.servlet.http.HttpServletRequest;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.myproject.shortlink.project.common.constant.ShortLinkConstant.DEFAULT_CACHE_VALID_TIME;

/**
 * 获取短链接缓存有效时间（毫秒）
 */
public class LinkUtil {
    public static long getLinkCacheValidTime(LocalDateTime validate) {
        if (validate == null) {
            return DEFAULT_CACHE_VALID_TIME;
        }

        long millis = Duration.between(LocalDateTime.now(), validate).toMillis();

        // 如果已经过期，直接返回 0 或最小 TTL
        return Math.max(millis, 0);
    }

    public static String getActualIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        // 处理多级代理的情况
        if (ipAddress != null && ipAddress.contains(",")) {
            ipAddress = ipAddress.split(",")[0].trim();
        }

        return ipAddress;
    }

}
