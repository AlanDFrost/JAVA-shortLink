package org.myproject.shortlink.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserLoginRespDTO {
    /*
    用户token
     */
    private String token;
}
