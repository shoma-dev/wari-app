package com.example.demo.controller;

import com.example.demo.model.Group;
import com.example.demo.model.Member;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/group/new")
    public String showGroupForm() {
        return "groupForm";
    }

    @PostMapping("/createGroup")
    public String createGroup(
            @RequestParam String groupName,
            @RequestParam(required = false) String members,
            Model model
    ) {
        Group group = new Group();
        group.setGroupName(groupName);
        groupRepository.save(group);

        if (members != null && !members.isEmpty()) {
            String[] memberNames = members.split(",");
            for (String name : memberNames) {
                Member m = new Member();
                m.setName(name.trim());
                m.setGroup(group);
                memberRepository.save(m);
            }
        }

        // 完了画面にグループIDとURLを渡す
        model.addAttribute("groupId", group.getId());
        model.addAttribute("shareUrl", "http://localhost:8080/group/" + group.getId() + "/members");
        return "groupShare";
    }

    @GetMapping("/group/{groupId}")
    public String showGroupDetail(@PathVariable Long groupId, Model model) {
    Group group = groupRepository.findById(groupId).orElseThrow();
    model.addAttribute("group", group);
    model.addAttribute("members", group.getMembers());
    return "groupDetail";
}


}
