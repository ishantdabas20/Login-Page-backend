package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RolePermissionRequest {

    private Long roleId;
    private List<Long> permissionIds;

}