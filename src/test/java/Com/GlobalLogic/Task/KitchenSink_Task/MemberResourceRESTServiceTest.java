package Com.GlobalLogic.Task.KitchenSink_Task;

import Com.GlobalLogic.Task.KitchenSink_Task.Model.Member;
import Com.GlobalLogic.Task.KitchenSink_Task.Repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class MemberResourceRESTServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        repository.deleteAll(); // Clear the repository before each test
    }

    private Member createMember(String name, String email, String phoneNumber) {
        Member member = new Member();
        member.setName(name);
        member.setEmail(email);
        member.setPhoneNumber(phoneNumber);
        return repository.save(member); // Save and return the member
    }

    @Test
    public void testListAllMembers() throws Exception {
        createMember("John Doe", "john@mailinator.com", "123456789");

        mockMvc.perform(MockMvcRequestBuilders.get("/members")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john@mailinator.com"));
    }

    @Test
    public void testLookupMemberById() throws Exception {
        Member savedMember = createMember("Jane Doe", "jane@mailinator.com", "987654321");

        mockMvc.perform(MockMvcRequestBuilders.get("/members/" + savedMember.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("jane@mailinator.com"));
    }

    @Test
    public void testCreateMember() throws Exception {
        Member newMember = new Member();
        newMember.setName("Alice Smith");
        newMember.setEmail("alice@mailinator.com");
        newMember.setPhoneNumber("5551234567");

        mockMvc.perform(MockMvcRequestBuilders.post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newMember)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Registration successful!"));

        assertEquals(1, repository.findAll().size());
    }

   /* @Test
    public void testCreateMemberWithEmailConflict() throws Exception {
        createMember("Bob Johnson", "bob@mailinator.com", "5559876543");

        Member newMember = new Member();
        newMember.setName("Robert");
        newMember.setEmail("bob@mailinator.com");
        newMember.setPhoneNumber("5551234567");

        mockMvc.perform(MockMvcRequestBuilders.post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newMember)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.email").value("Email taken"));

        assertEquals(1, repository.findAll().size());
    }

    @Test
    public void testCreateMemberWithValidationErrors() throws Exception {
        Member invalidMember = new Member();
        invalidMember.setName(""); // Invalid name
        invalidMember.setEmail("invalid-email"); // Invalid email format
        invalidMember.setPhoneNumber("123"); // Invalid phone number

        mockMvc.perform(MockMvcRequestBuilders.post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidMember)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("must not be blank"))
                .andExpect(jsonPath("$.email").value("must be a well-formed email address"))
                .andExpect(jsonPath("$.phoneNumber").value("Phone number is invalid"));
    }*/
}