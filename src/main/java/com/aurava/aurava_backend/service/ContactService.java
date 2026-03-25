package com.aurava.aurava_backend.service;

import com.aurava.aurava_backend.entity.ContactMessage;

public interface ContactService {

    ContactMessage saveMessage(ContactMessage message);

}