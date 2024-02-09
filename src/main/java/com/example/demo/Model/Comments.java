package com.example.demo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "comments")
@Entity
@Data
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_comment;
    @ManyToOne
    @JoinColumn(name = "id_post")
    private Posts id_post;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users id_user;
    @NotNull(message = "The content not be mandatory.")
    private String content;
    @NotNull(message = "The time stamp not be mandatory.")
    private LocalDateTime time_stamp;

    public Comments() {

    }
}
