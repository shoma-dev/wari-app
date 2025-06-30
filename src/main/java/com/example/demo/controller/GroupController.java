package com.example.demo.controller;



import com.example.demo.model.Group;
import com.example.demo.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    @GetMapping("/group/new")
    public String showGroupForm() {
        return "groupForm";
    }

    @PostMapping("/group/create")
    public String createGroup(@RequestParam String groupName, Model model) {
        Group group = new Group();
        group.setGroupName(groupName);
        groupRepository.save(group);

        model.addAttribute("group", group);
        return "groupCreated";
    }
}
