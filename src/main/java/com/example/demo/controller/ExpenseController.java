package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/group/{groupId}/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/new")
    public String showExpenseForm(@PathVariable Long groupId, Model model) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        List<Member> members = memberRepository.findByGroup(group);

        model.addAttribute("group", group);
        model.addAttribute("members", members);

        return "expenseForm";
    }

    @PostMapping("/create")
    public String createExpense(
            @PathVariable Long groupId,
            @RequestParam Long payerId,
            @RequestParam(required = false) List<Long> participantIds,
            @RequestParam String description,
            @RequestParam Integer amount
    ) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        Member payer = memberRepository.findById(payerId).orElseThrow();

        Expense expense = new Expense();
        expense.setGroup(group);
        expense.setPayer(payer);
        expense.setDescription(description);
        expense.setAmount(amount);
        expense.setDate(new Date());

        if (participantIds != null) {
            List<Member> participants = memberRepository.findAllById(participantIds);
            expense.setParticipants(participants);
        }

        expenseRepository.save(expense);

        return "redirect:/group/" + groupId + "/detail";
    }
}
