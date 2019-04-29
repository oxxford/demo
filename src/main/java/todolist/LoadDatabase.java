package todolist;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(MessageRepository messageRepository, RecordRepository recordRepository) {
        return args -> {
            Message m1 = new Message("OS", "Succi oral exam");
            Message m2 = new Message("ML", "Khan test");

            messageRepository.save(m1);
            messageRepository.save(m2);

            Record r1 = new Record(m1);
            Record r2 = new Record(m2);

            recordRepository.save(r1);
            recordRepository.save(r2);

            //log.info("Preloading " + messageRepository.save());
            //log.info("Preloading " + messageRepository.save());
        };
    }
}
