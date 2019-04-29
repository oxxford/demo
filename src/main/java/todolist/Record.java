package todolist;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
class Record {
    private @Id @GeneratedValue Long id;
    private @ManyToOne @JoinColumn(name = "ID_FK") Message message;
    private String title;
    private String description;
    private Timestamp dateUpdated;
    private Boolean isCompleted;

    Record() {}

    Record(Message message) {
        this.message = message;
        this.title = message.getTitle();
        this.description = message.getDescription();
        this.dateUpdated = message.getDateUpdated() == null ? message.getDateCreated() : message.getDateUpdated();
        this.isCompleted = message.getIsCompleted();
    }

}
