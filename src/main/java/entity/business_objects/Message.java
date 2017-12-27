package entity.business_objects;

public class Message {
    private String email;
    private String subject;
    private String textContent;

    public String getEmail() {
        return email;
    }

    public String getSubject() {
        return subject;
    }

    public String getTextContent() {
        return textContent;
    }

    public Message(String email, String subject, String textContent) {
        this.email = email;
        this.subject = subject;
        this.textContent = textContent;
    }
}