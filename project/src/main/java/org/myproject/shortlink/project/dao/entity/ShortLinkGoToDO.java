package org.myproject.shortlink.project.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * 短链接跳转实体
 */
@Data
@Builder
@TableName("t_link_goto")  // ← 请改成你的实际表名
public class ShortLinkGoToDO {
    private long id;

    private String gid;

    private String fullShortUrl;
}
