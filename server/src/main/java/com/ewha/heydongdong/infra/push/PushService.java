package com.ewha.heydongdong.infra.push;

import com.ewha.heydongdong.module.model.domain.User;
import com.ewha.heydongdong.module.model.domain.datatype.Progress;
import com.ewha.heydongdong.module.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class PushService {

    private static final String firebase_server_key = "AAAABRgzR6A:APA91bE79xfoGqFfD1GqlprGIpRgPDyor64viDKh7gxyDOCuFH1vGQO_VdFTnOWKK7V1ANOm0Ms-P0aSCeQQ1jCW26s4fM5BpKsYGy11vqlx1IoOPpVcpOd41CzjiW6mUrq8b6is3aHd";
    private static final String firebase_api_url = "https://fcm.googleapis.com/fcm/send";

    @Autowired
    private UserService userService;

    public void sendCustomerPush(User user, Progress progress) throws JSONException, InterruptedException {

        String notifications = CustomerPush.buildCustomerPushOnProgressUpdate(userService.getUserDeviceToken(user), progress);
        HttpEntity<String> request = new HttpEntity<>(notifications);

        CompletableFuture<String> pushNotification = sendPush(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            String firebaseResponse = pushNotification.get();
        } catch (InterruptedException e) {
            log.error("InterruptedException on CustomerPush");
            throw new InterruptedException();
        } catch (ExecutionException e) {
            log.error("ExecutionException on CustomerPush");
        }
    }

    @Async
    public CompletableFuture<String> sendPush(HttpEntity<String> entity) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + firebase_server_key));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);

        String firebaseResponse = restTemplate.postForObject(firebase_api_url, entity, String.class);
        return CompletableFuture.completedFuture(firebaseResponse);
    }
}