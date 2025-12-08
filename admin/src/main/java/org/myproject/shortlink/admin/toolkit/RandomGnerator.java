package org.myproject.shortlink.admin.toolkit;

import java.security.SecureRandom;

/*
模块分组ID随机生成器
 */
public final class RandomGnerator {
    // 可选字符集（数字 + 大写字母 + 小写字母）
    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    // 更安全的随机数生成器（比 Random 更不容易重复）
    private static final SecureRandom RANDOM = new SecureRandom();

    /*
    生成随机的6位分组ID
     */
    public static String genrateRandomString() {
        return generate(6);
    }

    public static String generate(int length) {
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHAR_POOL.length());
            sb.append(CHAR_POOL.charAt(index));
        }
        return sb.toString();
    }
}
