package com.example.demo.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

// 追加で必要
import com.example.demo.model.Group;
import com.example.demo.model.Member;

@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Group group;

    @ManyToOne
    private Member payer;

    @ManyToMany
    private List<Member> participants;

    private String description;
    private Integer amount;
    private Date date;

    // --- getter / setter 省略しても可、必要なら記載 ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Group getGroup() { return group; }
    public void setGroup(Group group) { this.group = group; }

    public Member getPayer() { return payer; }
    public void setPayer(Member payer) { this.payer = payer; }

    public List<Member> getParticipants() { return participants; }
    public void setParticipants(List<Member> participants) { this.participants = participants; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
}
