package com.cctpl.fooddeliveryadmin.Notification;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAA5tjI8Zg:APA91bFwdVrW_YHUjqOLeTihdPoBDHKpWW6-W3F3mMOvvVeFKYq4UQHK9UiODME67XkGoaa2BxXcfbCrckGPruEOAFaOpvcctB4VUqU7-DXbkFYNExbbdslyDIoEbvbg-o5uvMx4amWR"
    })

    @POST("fcm/send")
    Call<ResponseBody> sendNotification(@Body NotificationSender body);

}
