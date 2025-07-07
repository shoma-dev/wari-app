package com.example.demo.controller;

import com.example.demo.model.Expense;
import com.example.demo.model.Group;
import com.example.demo.model.Member;
import com.example.demo.model.Settlement;
import com.example.demo.repository.ExpenseRepository;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GroupDetailController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping("/group/{groupId}/detail")
    public String showGroupDetail(@PathVariable Long groupId, Model model) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        List<Member> members = memberRepository.findByGroup(group);
        List<Expense> expenses = expenseRepository.findByGroup(group);

        // 残高計算
        Map<Member, Integer> balances = new HashMap<>();
        for (Member member : members) {
            balances.put(member, 0);
        }

        for (Expense expense : expenses) {
            int split = expense.getAmount() / expense.getParticipants().size();
            for (Member participant : expense.getParticipants()) {
                if (participant.equals(expense.getPayer())) {
                    balances.put(participant, balances.get(participant) + (expense.getAmount() - split));
                } else {
                    balances.put(participant, balances.get(participant) - split);
                }
            }
        }

        // settlements 計算
        List<Settlement> settlements = new ArrayList<>();
        List<Member> plusMembers = balances.entrySet().stream()
                .filter(e -> e.getValue() > 0)
                .map(Map.Entry::getKey)
                .toList();

        List<Member> minusMembers = balances.entrySet().stream()
                .filter(e -> e.getValue() < 0)
                .map(Map.Entry::getKey)
                .toList();

        int idxPlus = 0;
        int idxMinus = 0;

        while (idxPlus < plusMembers.size() && idxMinus < minusMembers.size()) {
            Member plus = plusMembers.get(idxPlus);
            Member minus = minusMembers.get(idxMinus);

            int plusAmount = balances.get(plus);
            int minusAmount = -balances.get(minus);

            int settlementAmount = Math.min(plusAmount, minusAmount);

            settlements.add(new Settlement(minus, plus, settlementAmount));

            balances.put(plus, plusAmount - settlementAmount);
            balances.put(minus, -(minusAmount - settlementAmount));

            if (balances.get(plus) == 0) idxPlus++;
            if (balances.get(minus) == 0) idxMinus++;
        }

        model.addAttribute("group", group);
        model.addAttribute("expenses", expenses);
        model.addAttribute("settlements", settlements);
        model.addAttribute("members", members);
        model.addAttribute("balances", balances);

        return "groupDetail";
    }
}
