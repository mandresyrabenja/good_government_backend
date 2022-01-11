package mg.gov.goodGovernment.database;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class SequenceGeneratorService {
    private final MongoOperations mongoOperations;

    public long generateSequence(String seqName) {
        Query query = new Query();
        DatabaseSequence counter = mongoOperations.findAndModify(query.addCriteria(Criteria.where("_id").is(seqName)),
                new Update().inc("seq",1), new FindAndModifyOptions().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }
}
