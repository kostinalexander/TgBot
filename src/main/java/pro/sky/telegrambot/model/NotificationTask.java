package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "notification_task")
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String textMessage;
    private LocalDateTime data; // date

    public NotificationTask(String textMessage, LocalDateTime data) {
        this.id = getId();
        this.textMessage = textMessage;
        this.data = data;
    }

    public NotificationTask() {
    }

    public Long getId() {
        return id;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }


    public void setData(LocalDateTime data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(id, that.id) && Objects.equals(textMessage, that.textMessage) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, textMessage, data);
    }
}
