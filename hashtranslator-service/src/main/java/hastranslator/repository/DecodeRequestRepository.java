package hastranslator.repository;

import hastranslator.entity.DecodeRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecodeRequestRepository extends MongoRepository<DecodeRequest, String> {
}
