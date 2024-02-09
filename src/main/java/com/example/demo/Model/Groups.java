package com.example.demo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Table(name = "`groups`")
@Entity
@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_group;
    @NotNull(message = "The name not be mandatory.")
    private String name;
    @NotNull(message = "The description not be mandatory.")
    private String description;

    public Groups() {

    }
}
