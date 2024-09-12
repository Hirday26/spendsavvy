package com.project.expensetracker.services;

import com.project.expensetracker.repo.ExpenseRepository;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class ExpenseService {

    @Autowired
    public ExpenseRepository expenseRepository;
  

    public List<Map<String, Object>> getCategories() {
        return expenseRepository.getCategories();
    }


    public ResponseEntity<Map<String, Object>> addExpense(Map<String, Object> body) {
        String expenseName = (String) body.get("expenseName");
        int expenseAmount = Integer.parseInt((String) body.get("expenseAmount"));
        String expenseDescription = (String) body.get("expenseDescription");
        String expenseDate = (String) body.get("expenseDate");
        int categoryNameID = Integer.parseInt((String) body.get("categoryNameID"));
        String tags = (String) body.get("tags");
        Integer userId = Integer.parseInt((String) body.get("userId"));
        int noOfRows = expenseRepository.addExpense(expenseName, expenseAmount, expenseDescription, expenseDate, categoryNameID, tags, userId);
        if (noOfRows > 0) {
            return ResponseEntity.ok(Map.of("status", "success adding expense"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("status", "error adding expense"));
    }

    public List<Map<String, Object>> getExpenses(int userId) {
        return expenseRepository.getExpenses(userId);
    }

    public List<Map<String, Object>> getExpensesCategoryWise(int userId) {
        return expenseRepository.getExpensesCategoryWise(userId);
    }


    @Scheduled(cron = "0 0/3 * * * *")
    public void getPhoneNumber() {
        List<Map<String,Object>> phone = expenseRepository.getPhoneNumber();
        System.out.println(phone);
        for (Map<String, Object> map : phone) {
            System.out.println(map.get("phone_number"));
            String phone_number = (String) map.get("phone_number");
            int userId = (int) map.get("user_id");
            sendWhatsappMessage(phone_number,userId);
        }
    }

    //     using twilio im scheduling a whatsapp convo and will ask users to give expense aand will save in db

    public void sendWhatsappMessage(String phone, int userId) {
        System.out.println("sending whatsapp message");

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:+91"+phone), // to
                        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"), // from
                        "Hey!  Its long time you've not entered your expense so you can just make a entry here we will do the rest" +
                                "Please enter your expense details in the following format: \n" +
                                "User ID: "+userId+"\n" +
                                "Expense Name: \n" +
                                "Expense Amount: \n" +
                                "Expense Description: \n" +
                                "Expense Date: \n" +
                                "Category Name ID: \n" +
                                "Tags: \n" +
                                "Thank you! :\n" +
                                " Copy the above message and paste it in the chat and fill the details."
                )
                .create();

        System.out.println(message.getSid() + " " + message.getStatus());

    }
    public boolean addExpenseFromWhatsapp(String body) throws JSONException {
        String[] lines = body.split("\n");
        String expenseName = "";
        String expenseAmount = "";
        String expenseDescription = "";
        String expenseDate = "";
        String categoryNameID = "";
        String tags = "";
       String userId="";

        // Loop through each line and extract details based on labels
        for (String line : lines) {
            if (line.contains("Expense Name:")) {
                expenseName = line.split(":")[1].trim();
            } else if (line.contains("Expense Amount:")) {
                expenseAmount = line.split(":")[1].trim();
            } else if (line.contains("Expense Description:")) {
                expenseDescription = line.split(":")[1].trim();
            } else if (line.contains("Expense Date:")) {
                expenseDate = line.split(":")[1].trim();
            } else if (line.contains("Category Name ID:")) {
                categoryNameID = line.split(":")[1].trim();
            } else if (line.contains("Tags:")) {
                tags = line.split(":")[1].trim();
            }
            else if (line.contains("User ID:")) {
                userId = line.split(":")[1].trim();
            }
        }

        // Print or use the extracted details
        System.out.println("Expense Name: " + expenseName);
        System.out.println("Expense Amount: " + expenseAmount);
        System.out.println("Expense Description: " + expenseDescription);
        System.out.println("Expense Date: " + expenseDate);
        System.out.println("Category Name ID: " + categoryNameID);
        System.out.println("Tags: " + tags);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("expenseName", expenseName);
        jsonObject.put("expenseAmount", expenseAmount);
        jsonObject.put("expenseDescription", expenseDescription);
        jsonObject.put("expenseDate", expenseDate);
        jsonObject.put("categoryNameID", categoryNameID);
        jsonObject.put("tags", tags);
        jsonObject.put("userId", userId);

        // Print the JSON object
        System.out.println(jsonObject);
        if(Objects.equals(addExpense(jsonObject.toMap()), ResponseEntity.ok(Map.of("status", "success adding expense"))))
        {
            return true;
        }
        return false;

    }
    public Map<String, Object> getTotalMonthlyExpense(int userId) {
        return expenseRepository.getTotalMonthlyExpense(userId);
    }
}


