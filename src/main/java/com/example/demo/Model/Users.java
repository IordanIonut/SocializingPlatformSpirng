package com.example.demo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "users")
@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;
    @NotNull(message = "The username not be mandatory.")
    private String username;
    @NotNull(message = "The password not be mandatory.")
    private String password;
    @NotNull(message = "The fist name not be mandatory.")
    private String first_name;
    @NotNull(message = "The last name not be mandatory.")
    private String last_name;
    @Email
    @NotNull(message = "The email not be mandatory.")
    private String email;
    @NotNull(message = "The date of birth must not be null.")
    private LocalDateTime date_of_birth;

    public Users() {

    }
}
