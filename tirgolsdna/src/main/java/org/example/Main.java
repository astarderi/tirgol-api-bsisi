package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            //אפשרות לבחירת אקראית או מהמשתמש
            Scanner scanner = new Scanner(System.in);
            System.out.println("Choose amount: ");
            int amount = scanner.nextInt();
            //שורה לבקשת GET אם אני רוצה לקבל משהו
            //שורה לבקשת POST אם אני רוצה לשלוח משהו שישמור לי
            HttpGet getRequest = new HttpGet("https://opentdb.com/api.php?amount=" + amount + "&category=19&difficulty=easy&type=multiple");
            //שורה לשמירת התשובה של השרת
            HttpResponse response = client.execute(getRequest);
            //הדפסת התוצאה
            String questions = EntityUtils.toString(response.getEntity());
            //המרת STRING לאובייקט הזה והדפסה שלו
            Response responseObject = new ObjectMapper().readValue(questions, Response.class);
            System.out.println("Question: ");
            System.out.println(responseObject.getResults().get(0).getQuestion());
            //הדפסת רשימה
            System.out.println("Incorrect answers: ");
            Question question = responseObject.getResults().get(0);
            for (String answer : question.getIncorrect_answers()){
                System.out.println(answer);
            }
            //המשך הדפסה
            System.out.println("Correct answer: ");
            System.out.println(question.getCorrect_answer());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}