package org.myproject.shortlink.admin.controller;

import lombok.RequiredArgsConstructor;
import org.myproject.shortlink.admin.common.convention.result.Result;
import org.myproject.shortlink.admin.common.convention.result.Results;
import org.myproject.shortlink.admin.dto.request.GroupSaveReqDTO;
import org.myproject.shortlink.admin.dto.request.GroupSortReqDTO;
import org.myproject.shortlink.admin.dto.request.GroupUpdateReqDTO;
import org.myproject.shortlink.admin.dto.response.GroupSearchRespDTO;
import org.myproject.shortlink.admin.service.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
短链接分组控制层
 */
@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/api/short-link/v1/group")
    public Result<Void> saveGroup(@RequestBody GroupSaveReqDTO requestParam) {
        groupService.saveGroup(requestParam.getGroupName());
        return Results.success();
    }

    @GetMapping("/api/short-link/v1/group")
    public Result<List<GroupSearchRespDTO>> listGroup() {
        return Results.success(groupService.listGroup());
    }

    @PutMapping("/api/short-link/v1/group")
    public Result<Void> updateGroup(@RequestBody GroupUpdateReqDTO requestParam) {
        String groupName = requestParam.getGroupName();
        String gid = requestParam.getGid();
        groupService.updateGroup(gid, groupName);
        return Results.success();
    }

    @DeleteMapping("/api/short-link/v1/group")
    public Result<Void> deleteGroup(@RequestParam String gid) {
        groupService.deleteGroup(gid);
        return Results.success();
    }

    @PostMapping("/api/short-link/v1/group/sort")
    public Result<Void> sortGroup(@RequestBody List<GroupSortReqDTO> requestParam) {
        groupService.sortGroup(requestParam);
        return Results.success();
    }
}
