package Utility;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.sun.security.jgss.GSSUtil;
import org.json.JSONObject;



public class EmailConnector {

    final String APIurl= "https://script.google.com/macros/s/AKfycbwbFB9wBm_D2BRBAIPd4Dazar1qQly2tJuZhIhi2GYOM7k3omS0XhIvT8dNZ-l3wMEq7g/exec";
    HttpClient client;
    HttpRequest request;
    HttpResponse<String> response;

    JSONObject json;



    public String SendOtp(String email, String name){
        System.out.println("Sending OTP......");

        try{
            client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();

            request = HttpRequest.newBuilder().uri(URI.create(APIurl+"?sentByBank=true&email="+email+"&name="+name)).build();

            response = client.send(request,HttpResponse.BodyHandlers.ofString());


        }
        catch (InterruptedException  e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String responseBody= response.body().replace("[","").replace("]","");
        return responseBody;

    }

    public String verifyOTp(int otp,String email, long token){
        System.out.println("Verifing......");
        try{
            client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();

            request = HttpRequest.newBuilder().uri(URI.create(APIurl+"?otpverify=true&email="+email+"&token_number="+token+"&otp="+otp)).GET().build();

            response = client.send(request,HttpResponse.BodyHandlers.ofString());


        }
        catch (InterruptedException  e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String responseBody= response.body().replace("[","").replace("]","");
        return responseBody;
    }


    public String sendAcccountConfirmation(String username,String email, long accountno,String name){
        try {

            String jsonBody="{ \"username\":\""+username+"\", \"email\": \""+email+"\",\"accountno\":"+accountno+",\"name\":\""+name+"\"}";


            client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();

            request =  HttpRequest.newBuilder()
                    .uri(new URI(APIurl+"?BankAccountConfirm=true"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (InterruptedException  e){
            e.printStackTrace();
        }  catch (URISyntaxException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        String responseBody= response.body().replace("[","").replace("]","");
        return responseBody;
    }


    public static void main(String[] args) {
//        JSONObject json;
//        EmailConnector ec = new EmailConnector();
//        String res= ec.SendOtp("dbinod2020@gmail.com","Bino");
//        json = new JSONObject(res);
//        System.out.println(json.getString("message"));

//        String res= ec.sendAcccountConfirmation("kalua12","cdigital898@gmail.com",10010025,"kalua");
//        System.out.println(json.getString("message"));

    }

//    public static void main(String[] args) {
//
//        JSONObject json;
//        EmailConnector ec = new EmailConnector();
//        String res= ec.SendOtp("dbinod2020@gmail.com","Bino");
////        String verifyRes= ec.verifyOTp(358169,"dbinod2020@gmail.com",743351452);
//
////        System.out.println(res.body());
//
//        json = new JSONObject(res);
//
//        System.out.println(json.getString("token_number"));
//        System.out.println(json.getString("message"));
//        System.out.println(json.getBoolean("sent_status"));
//
////        System.out.println(json.getString("message"));
//
//
//    }
}
