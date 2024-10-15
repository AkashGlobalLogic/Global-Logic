package Com.GlobalLogic.Task.KitchenSink_Task.Controller;


import Com.GlobalLogic.Task.KitchenSink_Task.Model.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberResourceRESTService.class)
class MemberResourceRESTServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberResourceRESTService memberResourceRESTService;
    Member member1;
    Member member2;
    List<Member> memberList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        member1 = new Member("1", "Akash", "ajash@gmail.com", "9103450545");
        member2 = new Member("2", "Raju", "Raju23@gmail.com", "89345362092");
        memberList.add(member1);
        memberList.add(member2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testListAllMembers() throws Exception {
        ResponseEntity<List<Member>> responseEntity = ResponseEntity.ok(memberList);
        when(memberResourceRESTService.listAllMembers()).thenReturn(responseEntity);
        this.mockMvc.perform(get("/members")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void lookupMemberById() throws Exception {
        ResponseEntity<Member> responseEntity = ResponseEntity.ok(member2);
        when(memberResourceRESTService.lookupMemberById("2")).thenReturn(responseEntity);
        this.mockMvc.perform(get("/members/2")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void createMember() throws Exception {

        ResponseEntity<Map<String, String>> responseEntity = ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap("message", "Registration successful!"));


        when(memberResourceRESTService.createMember(any(Member.class))).thenReturn(responseEntity);

        // Converting the member object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String memberJson = objectMapper.writeValueAsString(member1);

        // Performing the POST request with JSON content
        this.mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(memberJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Registration successful!"));
    }
}
