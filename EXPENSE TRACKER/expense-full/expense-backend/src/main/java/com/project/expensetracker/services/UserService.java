package com.project.expensetracker.services;

import com.project.expensetracker.repo.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    JavaMailSender javaMailSender;

    /**
     * Registers a new user.
     *
     * @param body A map containing user registration details.
     * @return ResponseEntity indicating the status of the registration.
     */
    public ResponseEntity<Map<String,Object>> registerUser( Map<String,Object> body) {
        String username = (String)body.get("username");
        String password = (String)body.get("password");
        String email = (String)body.get("email");
        String fullname = (String) body.get("fullname");
        String dob = (String) body.get("dob");
        String mobile = (String) body.get("mobile");
        int incomeType = (int) body.get("incomeType");

        int noOfRows  = userRepository.registerUser(fullname,username,email,password,mobile,dob,incomeType);
        if(noOfRows >0) {
            return ResponseEntity.ok(Map.of("status","successful"));
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("status","Unsuccessful"));
    }

    /**
     * Logs in a user.
     *
     * @param body A map containing user login credentials.
     * @return A map containing the result of the login operation.
     */
    public Map<String,Object> loginUser( Map<String,Object> body) {
        String username = (String)body.get("username");
        String password = (String)body.get("password");
        return userRepository.loginUser(username,password);
    }

    /**
     * Retrieves cookies as a HashMap from HttpServletRequest.
     *
     * @param cookies The cookies array from HttpServletRequest.
     * @return A HashMap containing cookies.
     */
    private Map<String,String > getCookiesAsHashMap(Cookie[] cookies) {
        Map<String , String> cookieMap = new HashMap<>();
        for(Cookie c : cookies) {
            cookieMap.put(c.getName() , c.getValue());
        }
        return  cookieMap;
    }

    /**
     * Checks if the user token is valid.
     *
     * @param httpServletRequest The HttpServletRequest containing user cookies.
     * @return True if the token is valid, false otherwise.
     */
    public boolean isValidToken(HttpServletRequest httpServletRequest) {
        Cookie [] cookies = httpServletRequest.getCookies();
        if(cookies==null) {
            return false;
        }
        Map<String , String> cookieMap = getCookiesAsHashMap(cookies);

        Map<String , Object> result = userRepository.isValidToken(Integer.parseInt(cookieMap.get("userid")) , cookieMap.get("token"));
        Integer isValid = (Integer) result.get("validYN");
        return isValid == 1;
    }

    /**
     * Retrieves the list of income types.
     *
     * @return A list of income types.
     */
    public List<Map<String , Object>> getIncomeTypes() {
        return userRepository.getIncomeTypes();
    }

    /**
     * Sends emails to users at a scheduled time.
     *
     * @throws MessagingException If there is an error while sending the email.
     */
    @Scheduled(cron = "0 10 * * * 2")
    public void sendPromotionMails() throws MessagingException {
        System.out.println("Current time is :: " + LocalDate.now());
        List<Map<String,Object>> emails =userRepository.getEmails();
        for(Map<String,Object> email : emails) {
            sendMail((String) email.get("email") , "http://localhost:4200");
        }
    }

    /**
     * Sends an email to the specified email address.
     *
     * @param email The recipient's email address.
     * @param link The link to include in the email.
     * @throws MessagingException If there is an error while sending the email.
     */
    public void sendMail(String email , String link) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        try {
            mimeMessageHelper.setSubject("Welcome to Our Expense Tracker!");
            mimeMessageHelper.setTo(email);

            // HTML content for the email body
            String htmlContent = "<html>" +
                    "<body style=\"font-family: Arial, sans-serif;\">" +
                    "<h2>Welcome to Our Expense Tracker!</h2>" +
                    "<p>We're excited to have you on board.</p>" +
                    "<p>To start managing your expenses and budgets, click the button below:</p>" +
                    "<p><a href=\"" + link + "\" style=\"display: inline-block; padding: 10px 20px; background-color: #007bff; color: #fff; text-decoration: none; border-radius: 5px;\">Go to Expense Tracker</a></p>" +
                    "<p>If you have any questions or need assistance, feel free to contact us.</p>" +
                    "<p>Thank you!</p>" +
                    "</body>" +
                    "</html>";
//            mimeMessageHelper.se
            mimeMessageHelper.setText(htmlContent, true); // Set the HTML content
        } catch (Exception e) {
            e.printStackTrace();
        }
        javaMailSender.send(mimeMessage);
        System.out.println("Email sent to :: " + email);
    }
}
