package org.myproject.shortlink.project.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.myproject.shortlink.project.dao.entity.ShortLinkDO;

/*
短链接持久层
 */
@Mapper
public interface ShortLinkMapper extends BaseMapper<ShortLinkDO> {
    @Update("UPDATE t_link SET total_pv = total_pv + #{total_pv}, total_uv = total_uv + #{total_uv}, total_uip = total_uip + #{total_uip} WHERE gid = #{gid} AND full_short_url = #{fullShortUrl}")
    void incrementStats(
            @Param("gid") String gid,
            @Param("fullShortUrl") String fullShortUrl,
            @Param("total_pv") Integer total_pv,
            @Param("total_uv") Integer total_uv,
            @Param("total_uip") Integer total_uip
            );
}
