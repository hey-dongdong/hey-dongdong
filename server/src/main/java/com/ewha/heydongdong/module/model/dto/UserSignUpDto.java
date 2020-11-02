package com.ewha.heydongdong.module.model.dto;

import com.ewha.heydongdong.infra.exception.InvalidRequestParameterException;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpDto {

    private String userId;

    private String userName;

    private String password;

    private String email;

    private String phone;

    public void validate() {
        if (userId == null)
            throw new InvalidRequestParameterException("Invalid userId");
        if (userName == null)
            throw new InvalidRequestParameterException("Invalid userName");
        if (password == null)
            throw new InvalidRequestParameterException("Invalid password");
        if (email == null)
            throw new InvalidRequestParameterException("Invalid email");
        if (phone == null)
            throw new InvalidRequestParameterException("Invalid phone");
    }
}
