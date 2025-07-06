package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/group/{groupId}/detail")
public class GroupDetailController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping
    public String showGroupDetail(@PathVariable Long groupId, Model model) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        List<Member> members = memberRepository.findByGroup(group);
        List<Expense> expenses = expenseRepository.findByGroup(group);

        // 簡易的に清算計算結果もここであとで載せる想定
        // 今はまず履歴だけ渡す
        model.addAttribute("group", group);
        model.addAttribute("members", members);
        model.addAttribute("expenses", expenses);

        return "groupDetail";
    }
}
