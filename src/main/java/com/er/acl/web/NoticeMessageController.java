package com.er.acl.web;

import com.er.acl.domain.NoticeMessage;
import com.er.acl.repositories.NoticeMessageRepo;
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

    private final NoticeMessageRepo messageRepo;

    @GetMapping("/")
    public String getAll(Model model) {
        log.info("GET: /");
        model.addAttribute("messages", messageRepo.findAll());
        return "index";
    }

    @PostMapping("/messages")
    public String addMessage(NoticeMessage noticeMessage, Model model) {
        log.info("POST: /messages");
        messageRepo.save(noticeMessage);
        model.addAttribute("messages", messageRepo.findAll());
        return "redirect:/";
    }

    @PostMapping("/messages/update")
    public String updateMessage(NoticeMessage noticeMessage, Model model) {
        log.info("POST: /messages/update");
        messageRepo.save(noticeMessage);
        model.addAttribute("messages", messageRepo.findAll());
        return "redirect:/";
    }

    @PostMapping("/messages/delete")
    public String deleteMessage(NoticeMessage noticeMessage, Model model) {
        log.info("POST: /messages/delete");
        messageRepo.delete(noticeMessage);
        model.addAttribute("messages", messageRepo.findAll());
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
        model.addAttribute("message", messageRepo.findById(id)
        .orElseThrow(RuntimeException::new));
        return "update-message";
    }
}
