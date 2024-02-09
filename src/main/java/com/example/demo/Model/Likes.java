package com.example.demo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Table(name = "likes")
@Entity
@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_like;
    @ManyToOne
    @JoinColumn(name = "id_post")
    private Posts id_post;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users id_user;
    @NotNull(message = "The number not be mandatory.")
    private Long number;
    public Likes() {

    }
}
