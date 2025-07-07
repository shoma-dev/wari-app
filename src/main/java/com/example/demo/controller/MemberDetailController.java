package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/group/{groupId}/member")
public class MemberDetailController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping("/{memberId}/details")
    @ResponseBody
    public List<Expense> getMemberDetails(
            @PathVariable Long groupId,
            @PathVariable Long memberId
    ) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();
        return expenseRepository.findByGroupAndParticipantsContaining(group, member);
    }
}
