package com.lms.libmanage.entity.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    private Integer id;
    @NotNull(message = "Firstname must be specified!")
    private String firstname;

    @NotNull(message = "Lastname must be specified!")
    private String lastname;

    @NotNull(message = "Username must be specified!")
    private String username;

    @NotNull(message = "Email address must be specified!")
    private String email;

    private String password;


    private String phoneNumber;


    private String address;

}
