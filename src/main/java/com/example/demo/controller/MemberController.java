package com.example.demo.controller;

import com.example.demo.model.Group;
import com.example.demo.model.Member;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/group/{groupId}/members")
public class MemberController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping
    public String listMembers(@PathVariable Long groupId, Model model) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        List<Member> members = memberRepository.findByGroup(group);

        model.addAttribute("group", group);
        model.addAttribute("members", members);

        return "memberList";
    }

    @GetMapping("/new")
    public String showMemberForm(@PathVariable Long groupId, Model model) {
    Group group = groupRepository.findById(groupId)
        .orElseThrow(() -> new IllegalArgumentException("グループが見つかりません: " + groupId));
    model.addAttribute("group", group);
        return "memberForm";
    }


    @PostMapping("/create")
    public String createMember(@PathVariable Long groupId, @RequestParam String name) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        Member member = new Member();
        member.setName(name);
        member.setGroup(group);
        memberRepository.save(member);

        return "redirect:/group/" + groupId + "/members";
    }

    @GetMapping("/{memberId}")
public String showMemberDetail(@PathVariable Long groupId, @PathVariable Long memberId, Model model) {
    Group group = groupRepository.findById(groupId).orElseThrow();

    // メンバー取得
    Member member = memberRepository.findById(memberId).orElse(null);
    if (member == null) {
        // メンバーがいないなら一覧にリダイレクト
        return "redirect:/group/" + groupId + "/members";
    }

    model.addAttribute("group", group);
    model.addAttribute("member", member);
    return "memberDetail";
}


}
