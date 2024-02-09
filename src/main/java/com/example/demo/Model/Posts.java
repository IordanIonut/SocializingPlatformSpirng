package com.example.demo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
@Table(name = "posts")
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_post;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users id_user;
    @NotNull(message = "The content not be mandatory.")
    private String content;
    @NotNull(message = "The time stamp be mandatory.")
    private LocalDateTime time_stamp;
    public Posts() {

    }
}
