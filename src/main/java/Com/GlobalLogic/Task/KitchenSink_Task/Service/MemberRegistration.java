package Com.GlobalLogic.Task.KitchenSink_Task.Service;


import Com.GlobalLogic.Task.KitchenSink_Task.Model.Member;
import Com.GlobalLogic.Task.KitchenSink_Task.Repository.MemberRepository;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Log
@Service
public class MemberRegistration {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;


    @Transactional
    public void register(Member member) throws Exception {
        memberRepository.save(member);
    }
}
