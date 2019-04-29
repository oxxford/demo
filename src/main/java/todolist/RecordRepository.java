package todolist;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findRecordByMessageId(Long id);

    @Transactional
    void deleteRecordByMessageId(Long id);
}
