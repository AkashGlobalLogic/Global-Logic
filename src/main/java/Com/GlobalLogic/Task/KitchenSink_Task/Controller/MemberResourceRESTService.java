package Com.GlobalLogic.Task.KitchenSink_Task.Controller;

import Com.GlobalLogic.Task.KitchenSink_Task.Model.Member;
import Com.GlobalLogic.Task.KitchenSink_Task.Repository.MemberRepository;
import Com.GlobalLogic.Task.KitchenSink_Task.Service.MemberRegistration;
import jakarta.validation.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Log
@RestController
@RequestMapping("/members")
@Validated
public class MemberResourceRESTService {

    @Autowired
    private Validator validator;

    @Autowired
    private MemberRepository repository;

    @Autowired
    private MemberRegistration registration;

       @GetMapping
        public ResponseEntity<List<Member>> listAllMembers() {
        List<Member> members = repository.findAllByOrderByName();
        return ResponseEntity.ok(members);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Member> lookupMemberById(@PathVariable("id") String id) {
           Optional<Member> memberOptional = repository.findById(id);
           return memberOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

        @PostMapping
        public ResponseEntity<Map<String, String>> createMember(@Valid @RequestBody Member member) {
            try {
                validateMember(member);
                registration.register(member);
                Map<String, String> response = new HashMap<>();
                response.put("message", "Registration successful!");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } catch (ConstraintViolationException ce) {
                return createViolationResponse(ce.getConstraintViolations());
            } catch (ValidationException e) {
                Map<String, String> response = new HashMap<>();
                response.put("email", "Email taken");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            } catch (Exception e) {
                Map<String, String> response = new HashMap<>();
                response.put("error", e.getMessage());
                return ResponseEntity.badRequest().body(response);
            }
        }

        private void validateMember(Member member) throws ConstraintViolationException, ValidationException {
            Set<ConstraintViolation<Member>> violations = validator.validate(member);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(new HashSet<>(violations));
            }
            if (emailAlreadyExists(member.getEmail())) {
                throw new ValidationException("Unique Email Violation");
            }
        }

        private ResponseEntity<Map<String, String>> createViolationResponse(Set<ConstraintViolation<?>> violations) {
            log.fine("Validation completed. Violations found: " + violations.size());

            Map<String, String> responseObj = new HashMap<>();
            for (ConstraintViolation<?> violation : violations) {
                responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return ResponseEntity.badRequest().body(responseObj);
        }

        private boolean emailAlreadyExists(String email) {
            return repository.findByEmail(email) != null;
        }
    }
