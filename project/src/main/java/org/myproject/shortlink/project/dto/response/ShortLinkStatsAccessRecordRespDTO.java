package org.myproject.shortlink.project.dto.response;

import lombok.Data;

/**
 * 单个短链接监控日志返回体
 */
@Data
public class ShortLinkStatsAccessRecordRespDTO {
    /**
     * 完整短链接
     */
    private String fullShortUrl;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 用户标识
     */
    String user;

    /**
     * 访客类型
     */
    private String uvType;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * IP
     */
    private String ip;

    /**
     * 访问设备
     */
    private String device;

    /**
     * 访问网络
     */
    private String network;

    /**
     * 地区名称
     */
    private String locale;

    /** 创建方式：0 接口创建、1 控制台创建 */
    private Integer createdType;
}
