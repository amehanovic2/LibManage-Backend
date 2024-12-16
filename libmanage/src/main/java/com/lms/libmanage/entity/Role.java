package com.lms.libmanage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @NotBlank(message = "Role name cannot be empty")
    @Size(max = 255, message = "Role name cannot exceed 255 characters")
    @Column(name = "role_name", nullable = false)
    private String roleName;
}
