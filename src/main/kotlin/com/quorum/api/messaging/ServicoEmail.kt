package com.quorum.api.messaging

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class ServicoEmail(private val emailSender: JavaMailSender) {

    fun sendEmail(to: String, subject: String, text: String) {
        val message = SimpleMailMessage().apply {
            setTo(to)
            setSubject(subject)
            setText(text)
        }
        emailSender.send(message)
    }
}
