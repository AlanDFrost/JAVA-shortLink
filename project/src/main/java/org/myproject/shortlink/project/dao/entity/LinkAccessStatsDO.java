package org.myproject.shortlink.project.dao.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_link_access_stats")
public class LinkAccessStatsDO extends BaseDO{
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 完整短链接
     */
    private String fullShortUrl;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 日期
     */
    private Date date;

    /**
     * 访问量（PV）
     */
    private Integer pv;

    /**
     * 独立访客数（UV）
     */
    private Integer uv;

    /**
     * 独立 IP 数（UIP）
     */
    private Integer uip;

    /**
     * 小时（0~23）
     */
    private Integer hour;

    /**
     * 星期（1~7）
     */
    private Integer weekday;
}
