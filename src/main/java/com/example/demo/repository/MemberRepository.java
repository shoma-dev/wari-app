package com.example.demo.repository;

import com.example.demo.model.Member;
import com.example.demo.model.Group; // ← 追加
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; // ← 追加

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByGroup(Group group);
}


