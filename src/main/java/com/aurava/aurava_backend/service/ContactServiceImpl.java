package com.aurava.aurava_backend.service;

import com.aurava.aurava_backend.entity.ContactMessage;
import com.aurava.aurava_backend.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Override
    public ContactMessage saveMessage(ContactMessage message) {

        return contactRepository.save(message);
    }
}