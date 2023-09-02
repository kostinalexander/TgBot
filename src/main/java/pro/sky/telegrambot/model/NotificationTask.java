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
    private LocalDateTime data;
    @Column(name = "chat_id")
    private long chat_id;

    public NotificationTask(LocalDateTime data,String textMessage ) {
        this.id = getId();
        this.textMessage = textMessage;
        this.data = data;

    }
    public NotificationTask(LocalDateTime data,long chat_id,String textMessage ) {

        this.textMessage = textMessage;
        this.chat_id = chat_id;
        this.data = data;


    }

    public NotificationTask() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return chat_id == that.chat_id && Objects.equals(id, that.id) && Objects.equals(textMessage, that.textMessage) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, textMessage, data, chat_id);
    }

    public long getChat_id() {
        return chat_id;
    }

    public void setChat_id(long chat_id) {
        this.chat_id = chat_id;
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

}
