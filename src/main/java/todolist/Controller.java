package todolist;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private final MessageRepository messageRepository;
    private final RecordRepository recordRepository;

    Controller(MessageRepository messageRepository, RecordRepository recordRepository) {
        this.messageRepository = messageRepository;
        this.recordRepository = recordRepository;
    }

    @GetMapping("/api/records")
    List<Message> records() {
        return messageRepository.findAll();
    }

    @PostMapping("/api/add")
    Message add(@RequestBody Message newMessage) {
        return messageRepository.save(newMessage);
    }

    @GetMapping("/api/{id}/history")
    String history(@PathVariable Long id) {
        return serializeRecord(recordRepository.findRecordByMessageId(id));
    }

    @PostMapping("/api/{id}/edit")
    Message edit(@RequestBody Message newMessage, @PathVariable Long id) {

        return messageRepository.findById(id)
                .map(Message -> {
                    if (newMessage.getTitle() != null)
                        Message.setTitle(newMessage.getTitle());

                    if (newMessage.getDescription() != null)
                        Message.setDescription(newMessage.getDescription());

                    Message.setDateUpdated(new Timestamp(System.currentTimeMillis()));
                    Message.setIsCompleted(newMessage.getIsCompleted());

                    recordRepository.save(new Record(Message));
                    return messageRepository.save(Message);
                })
                .orElseGet(() -> {
                    newMessage.setId(id);
                    return messageRepository.save(newMessage);
                });
    }

    @DeleteMapping("/api/{id}")
    void delete(@PathVariable Long id) {
        recordRepository.deleteRecordByMessageId(id);
        messageRepository.deleteById(id);
    }

    private String serializeRecord(List<Record> records){
        StringBuilder result = new StringBuilder();

        for (Record record : records) {
            Message message = record.getMessage();

            result.append("Message with id ").append(message.getId()).append(" at timestamp ").append(record.getDateUpdated()).append(":");

            if (record.getTitle() != null)
                result.append(" title changed to ").append(record.getTitle()).append(";");

            if (record.getDescription() != null)
                result.append(" description changed to ").append(record.getDescription()).append(";");

            result.append(" completed: ").append(record.getIsCompleted());
        }

        return result.toString();
    }
}
