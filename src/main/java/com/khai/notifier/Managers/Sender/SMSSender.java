package com.khai.notifier.Managers.Sender;

import com.khai.notifier.Managers.Output.Output;
import com.khai.notifier.Models.Message.Message;
import com.khai.notifier.Models.User.User;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * SMSSender uses HTTP POST method to use sms-fly.com API
 * Inits with user and pass of sms-fly.com account
 */
public class SMSSender extends Sender {

    // Constants Section
    private static String HOST_NAME                          = "sms-fly.com";
    private static String API_ENDPOINT                       = "http://sms-fly.com/api/api.noai.php";
    private static String REQUEST_HEADER_HOST_KEY            = "Host:";
    private static String REQUEST_HEADER_AUTH_KEY            = "Authorization:";
    private static String REQUEST_HEADER_AUTH_VALUE          = "Basic";
    private static String REQUEST_HEADER_CONTENT_TYPE_KEY    = "Content-type:" ;
    private static String REQUEST_HEADER_CONTENT_TYPE_VALUE  = "text/xml";
    private static String REQUEST_HEADER_CONTENT_LENGTH_KEY  = "Content-Length:";
    private static String REQUEST_BODY_HEADER                = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
    private static String REQUEST_BODY_RECIPIENT_PLACEHOLDER = "USERPHONE";
    private static String REQUEST_BODY_BODY_PLACEHOLDER      = "REQESTBODY";
    private static String REQUEST_BODY_OPERATION_SEND        = "<operation>SENDSMS</operation>";
    private static String REQUEST_BODY_OPERATION_SEND_BODY   =
            "<request>" +
                "<message start_time=\"AUTO\" end_time=\"AUTO\" lifetime=\"4\" desc=\"description\">" +
                    "<recipient>"  + REQUEST_BODY_RECIPIENT_PLACEHOLDER + "</recipient>" +
                    "<body>"       + REQUEST_BODY_BODY_PLACEHOLDER      + "</body>" +
                "</message>" +
            "</request>";


    // Properties
    private String username;
    private String password;


    // Init
    public SMSSender(String user, String pass) {
        this.username = user;
        this.password = pass;
    }

    // Functions
    @Override
    public void send(Message message, String phone) {

        try {
            sendPost(message.getText(), phone);
        } catch (Exception e) {
            Output.error("Error sending sms to user with phone: " + user.getPhone() + ".\nDescription: " + e.getLocalizedMessage() + ".\n");
        }
    }

    /**
     * Sends HTTP POST with XML
     *
     * @param body  Message body
     * @param phone User phone number
     * @throws Exception Exception could be thrown in case of http post request/response
     */
    private void sendPost(String body, String phone) throws Exception {

        byte[] encodedAuthorizationHeaderValue = Base64.encodeBase64((username + ":" + password).getBytes());
        String authorizationHeaderValue        = new String(encodedAuthorizationHeaderValue);

        String url = API_ENDPOINT;

        String responseBody = REQUEST_BODY_OPERATION_SEND_BODY;
        responseBody        = responseBody.replace(REQUEST_BODY_RECIPIENT_PLACEHOLDER, phone);
        responseBody        = responseBody.replace(REQUEST_BODY_BODY_PLACEHOLDER,      body);

        String xml = REQUEST_BODY_HEADER + REQUEST_BODY_OPERATION_SEND + responseBody;

        HttpEntity entity = new ByteArrayEntity(xml.getBytes("UTF-8"));
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post     = new HttpPost(url);

        // add headers
        post.setHeader(REQUEST_HEADER_HOST_KEY,           HOST_NAME);
        post.setHeader(REQUEST_HEADER_AUTH_KEY,           REQUEST_HEADER_AUTH_VALUE + " " + authorizationHeaderValue);
        post.setHeader(REQUEST_HEADER_CONTENT_TYPE_KEY,   REQUEST_HEADER_CONTENT_TYPE_VALUE);
        post.setHeader(REQUEST_HEADER_CONTENT_LENGTH_KEY, Integer.toString(xml.length()));
        post.setEntity(entity);

        Output.info("Sending 'POST' request to URL : " + url);
        Output.info("Post parameters : " + post.getEntity());

        // get response
        HttpResponse response     = client.execute(post);
        HttpEntity responseEntity = response.getEntity();
        String content            = EntityUtils.toString(responseEntity);

        // TODO: response body has to be managed
        int statusCode = response.getStatusLine().getStatusCode();
        switch (statusCode) {

            case 200:
                Output.success("SMS request sent successfully.");
                break;

            default:
                Output.error("SMS request error.\nResponse Body:" + content);
                break;
        }
    }
}