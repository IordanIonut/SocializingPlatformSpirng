package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.http.HttpStatusCode;

@Entity
@Table(name = "groupsmembers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupsMembers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_group_member;
    @ManyToOne
    @JoinColumn(name = "id_group")
    private Groups id_group;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users id_user;
}
