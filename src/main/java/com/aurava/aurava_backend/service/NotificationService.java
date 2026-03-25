package com.aurava.aurava_backend.service;

import com.aurava.aurava_backend.DTO.NotificationResponse;

import java.util.List;

public interface NotificationService {

    void createNotification(String message);

    List<NotificationResponse> getMyNotifications();

    void markAsRead(Long id);
}