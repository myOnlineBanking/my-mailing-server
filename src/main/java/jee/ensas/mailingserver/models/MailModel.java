package jee.ensas.mailingserver.models;

import lombok.Data;

@Data
public class MailModel {
    private String emailReceiver;
    private String subject;
    private String content;
}
