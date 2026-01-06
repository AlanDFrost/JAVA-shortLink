package org.myproject.shortlink.admin.remote;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.myproject.shortlink.admin.common.convention.result.Result;
import org.myproject.shortlink.admin.remote.dto.request.RecycleBinSaveReqDTO;
import org.myproject.shortlink.admin.remote.dto.request.ShortLinkCreateReqDTO;
import org.myproject.shortlink.admin.remote.dto.request.ShortLinkPageReqDTO;
import org.myproject.shortlink.admin.remote.dto.request.ShortLinkUpdateReqDTO;
import org.myproject.shortlink.admin.remote.dto.response.ShortLinkCreateRespDTO;
import org.myproject.shortlink.admin.remote.dto.response.ShortLinkGroupCountQueryRespDTO;
import org.myproject.shortlink.admin.remote.dto.response.ShortLinkPageRespDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface ShortLinkRemoteService {

    /** 后管调用中台创建短链接 */
    default Result<ShortLinkCreateRespDTO> createShortLink (ShortLinkCreateReqDTO requestparam) {
        String requestBodyStr = HttpUtil.post("http://127.0.0.1:8001/api/short-link/project/v1/create", JSON.toJSONString(requestparam));
        return JSON.parseObject(requestBodyStr, new TypeReference<>() {
        });
    }

    /** 后管调用中台查询短链接分组 */
    default Result<IPage<ShortLinkPageRespDTO>> pageShortLink (ShortLinkPageReqDTO requestparam) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gid", requestparam.getGid());
        requestMap.put("current", requestparam.getCurrent());
        requestMap.put("size", requestparam.getSize());
        String resultPageStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/project/v1/page", requestMap);

        return JSON.parseObject(resultPageStr, new TypeReference<>() {
        });
    }

    /** 查询分组中的短链接数量 */
    default Result<List<ShortLinkGroupCountQueryRespDTO>> listGroupShortLinkCount(List<String> gids) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gids", gids);
        String resultPageStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/project/v1/count", requestMap);
        return JSON.parseObject(resultPageStr, new TypeReference<>() {});
    }

    /** 修改分组中的短链接 */
    default void updateShortLink(ShortLinkUpdateReqDTO requestparam) {
        HttpUtil.post("http://127.0.0.1:8001/api/short-link/project/v1/update", JSON.toJSONString(requestparam));
    }

    /** 短链接删除至回收站 */
    default void saveRecycleBin(RecycleBinSaveReqDTO requestParam) {
        HttpUtil.post("http://127.0.0.1:8001/api/short-link/projectv1/recycle-bin/save", JSON.toJSONString(requestParam));
    }

    default  Result<IPage<ShortLinkPageRespDTO>> pageRecycleBin (ShortLinkPageReqDTO requestparam) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gid", requestparam.getGid());
        requestMap.put("current", requestparam.getCurrent());
        requestMap.put("size", requestparam.getSize());
        String resultPageStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/project/v1/recycle-bin/page", requestMap);

        return JSON.parseObject(resultPageStr, new TypeReference<>() {
        });
    }
}
