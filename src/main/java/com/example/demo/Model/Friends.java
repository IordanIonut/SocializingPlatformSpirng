package com.example.demo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Table(name = "friends")
@Entity
@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
public class Friends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_friend;
    @ManyToOne
    @JoinColumn(name = "id_user_1")
    private Users id_user_1;
    @ManyToOne
    @JoinColumn(name = "id_user_2")
    private Users id_user_2;
    @NotNull(message = "The status not be mandatory.")
    private String status;
    public Friends() {

    }
}
