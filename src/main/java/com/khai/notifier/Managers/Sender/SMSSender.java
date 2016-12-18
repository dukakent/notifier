package com.khai.notifier.Managers.Sender;

import com.khai.notifier.Models.Message.Message;
import com.khai.notifier.Models.User.User;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * SMSSender uses HTTP POST method to use sms-fly.com API
 * Inits with user and pass of sms-fly.com account
 */
public class SMSSender extends Sender {

    private String     username;
    private String     password;
    private static String HOST_NAME = "sms-fly.com";

    public SMSSender(String user, String pass) {
        this.username = user;
        this.password = pass;
    }

    @Override
    public void send(Message message, User user) {

        try {
            sendPost(message.getText(), user.getPhone());
        } catch (Exception e) {
            System.out.println("Error sending sms to user with phone: " + user.getPhone());
        }
    }

    /**
     * Sends HTTP POST with XML
     * @param body Message body
     * @param phone User phone number
     * @throws Exception
     */
    private void sendPost(String body, String phone) throws Exception {

        byte[] encodedAuthorizationHeaderValue = Base64.encodeBase64((username+":"+password).getBytes());
        String authorizationHeaderValue = new String(encodedAuthorizationHeaderValue);
        String url = "http://sms-fly.com/api/api.noai.php";
        String xml =
                "<?xml version=\"1.0\" encoding=\"utf-8\"?><request>\n" +
                        "<operation>SENDSMS</operation>\n" +
                        "<message start_time=\"AUTO\" end_time=\"AUTO\" lifetime=\"4\" desc=\"description\">" +
                        "<recipient>" + phone + "</recipient>\n" +
                        "<body>" + body +"</body>\n" +
                        "</message>\n" +
                        "</request>";

        HttpEntity entity = new ByteArrayEntity(xml.getBytes("UTF-8"));
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        // add headers
        post.setHeader("Host:", HOST_NAME);
        post.setHeader("Authorization:", "Basic " + authorizationHeaderValue);
        post.setHeader("Content-type:", "text/xml");
        post.setHeader("Content-Length:", Integer.toString(xml.length()));

        post.setEntity(entity);

        HttpResponse response = client.execute(post);
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
    }
}