package com.er.acl.web;

import com.er.acl.domain.NoticeMessage;
import com.er.acl.services.NoticeMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NoticeMessageController {

    private final NoticeMessageService service;

    @GetMapping("/")
    public String getAll(Model model) {
        log.info("GET: /");
        model.addAttribute("messages", service.getAll());
        return "index";
    }

    @PostMapping("/messages")
    public String addMessage(NoticeMessage noticeMessage, Model model) {
        log.info("POST: /messages");
        service.add(noticeMessage);
        model.addAttribute("messages", service.getAll());
        return "redirect:/";
    }

    @PostMapping("/messages/update")
    public String updateMessage(NoticeMessage noticeMessage, Model model) {
        log.info("POST: /messages/update");
        service.update(noticeMessage);
        model.addAttribute("messages", service.getAll());
        return "redirect:/";
    }

    @PostMapping("/messages/delete")
    public String deleteMessage(NoticeMessage noticeMessage, Model model) {
        log.info("POST: /messages/delete");
        service.delete(noticeMessage);
        model.addAttribute("messages", service.getAll());
        return "redirect:/";
    }

    @GetMapping("/messages/add")
    @PreAuthorize("hasRole('USER')")
    public String getCreatePage() {
        log.info("GET: /messages/add");
        return "add-message";
    }

    @GetMapping("/messages/{id}/edit")
    @PreAuthorize("hasRole('USER')")
    public String getUpdatePage(@PathVariable("id") long id, Model model) {
        log.info("GET: /messages/{}/edit", id);
        model.addAttribute("message", service.getById(id));
        return "update-message";
    }
}
