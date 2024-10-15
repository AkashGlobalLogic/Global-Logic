package Com.GlobalLogic.Task.KitchenSink_Task.Service;

import Com.GlobalLogic.Task.KitchenSink_Task.Model.Member;
import Com.GlobalLogic.Task.KitchenSink_Task.Repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {MemberService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class MemberServiceTest {

        @MockBean
        private MemberRepository memberRepository;

        @Autowired
        private MemberService memberService;

        @Test
        public void testGetMemberByEmail() {
            Member member = new Member();
            member.setEmail("jane.doe@example.org");
            member.setId("42");
            member.setName("Name");
            member.setPhoneNumber("6625550144");
            when(memberRepository.findByEmail("jane.doe@example.org")).thenReturn(member);
            Member actualMemberByEmail = memberService.getMemberByEmail("jane.doe@example.org");
            verify(memberRepository).findByEmail(eq("jane.doe@example.org"));
            assertSame(member, actualMemberByEmail);
        }

        @Test
        public void testGetAllMembersOrderedByName() {
            ArrayList<Member> memberList = new ArrayList<>();
            when(memberRepository.findAllByOrderByName()).thenReturn(memberList);
            List<Member> actualAllMembersOrderedByName = memberService.getAllMembersOrderedByName();
            verify(memberRepository).findAllByOrderByName();
            assertTrue(actualAllMembersOrderedByName.isEmpty());
            assertSame(memberList, actualAllMembersOrderedByName);
        }

}