package com.quorum.api.messaging

import com.quorum.api.config.SecretManagerAccessor
import com.resend.Resend
import com.resend.core.exception.ResendException
import com.resend.services.emails.model.SendEmailRequest
import org.springframework.stereotype.Service

@Service
class ServicoEmail {

    fun sendEmail(to: String, subject: String, text: String) {
        val resend = Resend(SecretManagerAccessor.getSecret("mailPassword"))

        val sendEmailRequest = SendEmailRequest.builder()
            .from("Quorum API <auth@quorum-tech.io>")
            .to(to)
            .subject(subject)
            .html(text)
            .build()

        try {
            resend.emails().send(sendEmailRequest)
        } catch (e: ResendException) {
            e.printStackTrace()
        }
    }
}
