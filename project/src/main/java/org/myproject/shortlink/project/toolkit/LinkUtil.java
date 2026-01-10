package org.myproject.shortlink.project.toolkit;

import jakarta.servlet.http.HttpServletRequest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Locale;

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

    /**
     * 从请求获得IP地址
     * @param request
     * @return
     */
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

    /**
     * 从请求获得操作系统
     * @param request
     * @return
     */
    public static String getOs(HttpServletRequest request) {
        String ua = request.getHeader("User-Agent");
        if (ua == null || ua.isBlank()) {
            return "Unknown";
        }
        String s = ua.toLowerCase(Locale.ROOT);

        // 移动端优先（避免 iPhone 同时包含 Mac OS X 字样导致误判）
        if (s.contains("android")) return "Android";
        if (s.contains("iphone") || s.contains("ipad") || s.contains("ipod")) return "iOS";

        // 桌面端
        if (s.contains("windows nt")) return "Windows";
        if (s.contains("mac os x") || s.contains("macintosh")) return "macOS";

        // Linux / Unix
        if (s.contains("linux")) return "Linux";
        if (s.contains("x11")) return "Unix";

        return "Unknown";
    }

    /**
     * 获取用户访问浏览器
     *
     * @param request 请求
     * @return 访问浏览器
     */
    public static String getBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.toLowerCase().contains("edg")) {
            return "Microsoft Edge";
        } else if (userAgent.toLowerCase().contains("chrome")) {
            return "Google Chrome";
        } else if (userAgent.toLowerCase().contains("firefox")) {
            return "Mozilla Firefox";
        } else if (userAgent.toLowerCase().contains("safari")) {
            return "Apple Safari";
        } else if (userAgent.toLowerCase().contains("opera")) {
            return "Opera";
        } else if (userAgent.toLowerCase().contains("msie") || userAgent.toLowerCase().contains("trident")) {
            return "Internet Explorer";
        } else {
            return "Unknown";
        }
    }
}
