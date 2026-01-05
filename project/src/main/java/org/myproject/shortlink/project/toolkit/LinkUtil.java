package org.myproject.shortlink.project.toolkit;

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
}
