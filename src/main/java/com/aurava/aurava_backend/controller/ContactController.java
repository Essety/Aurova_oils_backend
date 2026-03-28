package com.aurava.aurava_backend.controller;

import com.aurava.aurava_backend.entity.ContactMessage;
import com.aurava.aurava_backend.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    public ContactMessage sendMessage(@RequestBody ContactMessage message){

        return contactService.saveMessage(message);
    }
}