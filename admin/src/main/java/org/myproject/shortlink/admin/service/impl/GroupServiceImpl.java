package org.myproject.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.myproject.shortlink.admin.common.biz.user.UserContext;
import org.myproject.shortlink.admin.common.convention.result.Result;
import org.myproject.shortlink.admin.dao.entity.GroupDO;
import org.myproject.shortlink.admin.dao.mapper.GroupMapper;
import org.myproject.shortlink.admin.dto.request.GroupSortReqDTO;
import org.myproject.shortlink.admin.dto.response.GroupSearchRespDTO;
import org.myproject.shortlink.admin.remote.ShortLinkActualRemoteService;
import org.myproject.shortlink.admin.remote.dto.response.ShortLinkGroupCountQueryRespDTO;
import org.myproject.shortlink.admin.service.GroupService;
import org.myproject.shortlink.admin.toolkit.RandomGnerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {
    private final ShortLinkActualRemoteService shortLinkActualRemoteService;
    @Override
    public void saveGroup(String groupName) {
        String gid;
        do {
            gid = RandomGnerator.genrateRandomString();
        } while (hasGid(UserContext.getUsername(), gid));

        GroupDO groupDO = GroupDO.builder()
                .gid(gid)
                .name(groupName)
                .username(UserContext.getUsername())
                .sortOrder(0)
                .build();
        baseMapper.insert(groupDO);
    }

    @Override
    public void saveGroup(String userName, String groupName) {
        String gid;
        do {
            gid = RandomGnerator.genrateRandomString();
        } while (hasGid(userName, gid));

        GroupDO groupDO = GroupDO.builder()
                .gid(gid)
                .name(groupName)
                .username(userName)
                .sortOrder(0)
                .build();
        baseMapper.insert(groupDO);
    }

    @Override
    public boolean hasGid(String username, String gid) {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getGid, gid)
                .eq(GroupDO::getUsername, Optional.ofNullable(username).orElse(UserContext.getUsername()));
        GroupDO groupDO = baseMapper.selectOne(queryWrapper);
        return groupDO != null;
    }

    @Override
    public List<GroupSearchRespDTO> listGroup() {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getDelFlag, 0)
                .orderByDesc(GroupDO::getSortOrder,  GroupDO::getUpdateTime);
        List<GroupDO> groupList = baseMapper.selectList(queryWrapper);
        Result<List<ShortLinkGroupCountQueryRespDTO>> listResult = shortLinkActualRemoteService.listGroupShortLinkCount(groupList.stream().map(GroupDO::getGid).toList());

        List<GroupSearchRespDTO> GroupSearchList = BeanUtil.copyToList(groupList, GroupSearchRespDTO.class);
        GroupSearchList.forEach(each -> {
            Optional<ShortLinkGroupCountQueryRespDTO> first = listResult.getData().stream()
                    .filter(item -> Objects.equals(item.getGid(), each.getGid()))
                    .findFirst();
            first.ifPresent(item -> each.setShortLinkCount(item.getShortLinkCount()));
        });
        return GroupSearchList;
    }

    @Override
    public void updateGroup(String gid, String groupName) {
        LambdaUpdateWrapper<GroupDO> updateWrapper = Wrappers.lambdaUpdate(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getGid, gid)
                .eq(GroupDO::getDelFlag, 0);
        GroupDO groupDO = new GroupDO();
        groupDO.setName(groupName);
        baseMapper.update(groupDO, updateWrapper);
    }

    @Override
    public void deleteGroup(String gid) {
        LambdaUpdateWrapper<GroupDO> updateWrapper = Wrappers.lambdaUpdate(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getGid, gid)
                .eq(GroupDO::getDelFlag, 0)
                .set(GroupDO::getDelFlag, 1);

        baseMapper.update(null, updateWrapper);
    }

    @Override
    public void sortGroup(List<GroupSortReqDTO> requestParam) {
        for (GroupSortReqDTO dto : requestParam) {
            LambdaUpdateWrapper<GroupDO> wrapper = Wrappers.lambdaUpdate(GroupDO.class)
                    .eq(GroupDO::getUsername, UserContext.getUsername())
                    .eq(GroupDO::getGid, dto.getGid())
                    .eq(GroupDO::getDelFlag, 0)
                    .set(GroupDO::getSortOrder, dto.getSortOrder())
                    .set(GroupDO::getUpdateTime, LocalDateTime.now());

            baseMapper.update(null, wrapper);
        }
    }
}
