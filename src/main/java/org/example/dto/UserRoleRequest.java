package org.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleRequest {

    private long userId;
    private long roleId;

}
