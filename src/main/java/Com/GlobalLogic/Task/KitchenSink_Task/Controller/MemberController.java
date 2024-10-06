/*
package Com.GlobalLogic.Task.KitchenSink_Task.Controller;


import Com.GlobalLogic.Task.KitchenSink_Task.Model.Member;
import Com.GlobalLogic.Task.KitchenSink_Task.Service.MemberRegistration;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("newMember") // Keep newMember in the session
public class MemberController {

        @Autowired
        private MemberRegistration memberRegistration;

        @ModelAttribute("newMember")
        public Member newMember() {
            return new Member(); // Initializes a new Member object
        }

        @GetMapping("/register") // Map GET request for registration page
        public String showRegistrationForm(Model model) {
            model.addAttribute("newMember", newMember());
            return "register"; // Return the name of the registration view (Thymeleaf template)
        }

        @PostMapping("/register") // Map POST request for registration
        public String register(@ModelAttribute("newMember") Member newMember, Model model) {
            try {
                memberRegistration.register(newMember);
                model.addAttribute("message", "Registration successful!");
                return "register"; // Redirect to a success page after registration
            } catch (Exception e) {
                String errorMessage = getRootErrorMessage(e);
                model.addAttribute("errorMessage", errorMessage);
                return "register"; // Return to the registration form with an error message
            }
        }

    @GetMapping("/success") // Map GET request for success page
        public String showSuccess(Model model) {
            model.addAttribute("message", "You have registered successfully!"); // Add a success message
            return "register"; // Return the name of the success view (Thymeleaf template)
        }

        private String getRootErrorMessage(Exception e) {
            String errorMessage = "Registration failed. See server log for more information.";
            if (e == null) {
                return errorMessage;
            }

            Throwable t = e;
            while (t != null) {
                errorMessage = t.getLocalizedMessage();
                t = t.getCause();
            }
            return errorMessage;
        }

}*/
