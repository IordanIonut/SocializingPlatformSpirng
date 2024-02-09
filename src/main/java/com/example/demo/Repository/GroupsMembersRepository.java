package com.example.demo.Repository;

import com.example.demo.Model.GroupsMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsMembersRepository extends JpaRepository<GroupsMembers, Long> {
}
