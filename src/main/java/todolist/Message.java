package todolist;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Entity
class Message {

    private @Id @GeneratedValue Long id;
    private String title;
    private String description;
    private Timestamp dateCreated;
    private Timestamp dateUpdated;
    private Boolean isCompleted;

    Message() {}

    Message(String title, String description) {
        this.title = title;
        this.description = description;
        this.dateCreated = new Timestamp(System.currentTimeMillis());
        this.dateUpdated = null;
        this.isCompleted = false;
    }
}
