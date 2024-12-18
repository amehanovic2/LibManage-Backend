package com.lms.libmanage.entity.user;

import com.lms.libmanage.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Integer id;

    private String uuid;
    private String firstname;
    private String lastname;
    private String email;

    private String username;

    private String address;

    private String phoneNumber;

    private String role;

    public UserResponse(User user) {
        id = user.getId();
        uuid = user.getUuid();
        email = user.getEmail();
        username = user.getUsername();
        firstname = user.getFirstname();
        lastname = user.getLastname();
        address = user.getAddress();
        phoneNumber = user.getPhoneNumber();
        role = user.getRole().name();
    }
}

