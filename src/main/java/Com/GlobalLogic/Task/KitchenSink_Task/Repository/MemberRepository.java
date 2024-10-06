package Com.GlobalLogic.Task.KitchenSink_Task.Repository;

import Com.GlobalLogic.Task.KitchenSink_Task.Model.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends MongoRepository<Member, String> {

    Member findByEmail(String email);

    List<Member> findAllByOrderByName();


}
