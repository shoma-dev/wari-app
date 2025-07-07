package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SettlementService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private MemberRepository memberRepository;

    public Map<Member, Integer> calculateBalances(Group group) {
        List<Member> members = memberRepository.findByGroup(group);
        List<Expense> expenses = expenseRepository.findByGroup(group);

        // 各メンバーの立て替え総額
        Map<Member, Integer> paidTotal = new HashMap<>();
        for (Member m : members) {
            paidTotal.put(m, 0);
        }

        int totalAmount = 0;
        for (Expense expense : expenses) {
            // 立て替え者
            Member payer = expense.getPayer();
            int amount = expense.getAmount();
            totalAmount += amount;
            paidTotal.put(payer, paidTotal.get(payer) + amount);
        }

        int average = members.size() > 0 ? totalAmount / members.size() : 0;

        // 各メンバーの差額
        Map<Member, Integer> balances = new HashMap<>();
        for (Member m : members) {
            int paid = paidTotal.get(m);
            balances.put(m, paid - average);
            // プラス → 受け取る
            // マイナス → 支払う
        }

        return balances;
    }
}
