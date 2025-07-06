package com.example.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "groups")  // ← 予約語 group 回避OK
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Member> members;  // ← 追加

    public Group() {
    }

    public Long getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Member> getMembers() {       // ← 追加
        return members;
    }

    public void setMembers(List<Member> members) {  // ← 追加
        this.members = members;
    }
}
