package com.aurava.aurava_backend.repository;

import com.aurava.aurava_backend.entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<ContactMessage,Long> {
}