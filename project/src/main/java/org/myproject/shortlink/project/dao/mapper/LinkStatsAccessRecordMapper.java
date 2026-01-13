package org.myproject.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.myproject.shortlink.project.dao.entity.LinkAccessLogsDO;
import org.myproject.shortlink.project.dto.request.ShortLinkGroupStatsAccessRecordReqDTO;
import org.myproject.shortlink.project.dto.request.ShortLinkStatsAccessRecordReqDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface LinkStatsAccessRecordMapper extends BaseMapper<LinkAccessLogsDO> {
    /**
     * 获取用户信息是否是新老访客
     */
    @Select("<script> " +
            "SELECT " +
            "    user, " +
            "    CASE " +
            "        WHEN MIN(create_time) BETWEEN #{param.beginDate} AND #{param.endDate} THEN '新访客' " +
            "        ELSE '老访客' " +
            "    END AS uvType " +
            "FROM " +
            "    t_link_access_logs " +
            "WHERE " +
            "    full_short_url = #{param.fullShortUrl} " +
            "    AND gid = #{param.gid} " +
            "    AND user IN " +
            "    <foreach item='item' index='index' collection='userAccessLogsList' open='(' separator=',' close=')'> " +
            "        #{item} " +
            "    </foreach> " +
            "GROUP BY " +
            "    user" +
            "    </script>"
    )
    List<Map<String, Object>> selectUvTypeByUsers(@Param("param") ShortLinkStatsAccessRecordReqDTO requestParam, @Param("userAccessLogsList")List<String> userAccessLogsList);

    /**
     * 获取分组下用户信息是否是新老访客
     */
    @Select("<script> " +
            "SELECT " +
            "    user, " +
            "    CASE " +
            "        WHEN MIN(create_time) BETWEEN #{param.beginDate} AND #{param.endDate} THEN '新访客' " +
            "        ELSE '老访客' " +
            "    END AS uvType " +
            "FROM " +
            "    t_link_access_logs " +
            "WHERE " +
            "    gid = #{param.gid} " +
            "    AND user IN " +
            "    <foreach item='item' index='index' collection='userAccessLogsList' open='(' separator=',' close=')'> " +
            "        #{item} " +
            "    </foreach> " +
            "GROUP BY " +
            "    user" +
            "    </script>"
    )
    List<Map<String, Object>> selectGroupUvTypeByUsers(@Param("param") ShortLinkGroupStatsAccessRecordReqDTO requestParam, @Param("userAccessLogsList")List<String> userAccessLogsList);
}
