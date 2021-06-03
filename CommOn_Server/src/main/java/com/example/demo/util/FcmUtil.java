package com.example.demo.util;


import java.io.FileInputStream;

import org.springframework.stereotype.Component;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;

@Component
public class FcmUtil {
	// YS LAPTOP SETTING.
	//private String json_path = "C:/Users/강윤식/git/start_up_team_server/mobile/src/main/webapp/resoureces/fcm/buspush-28fed-firebase-adminsdk-i4i4h-0b4d9a46ee.json";
	// GCP SETTING
	private String json_path = "/home/yunsik/git/repository/src/main/webapp/resources/fcm/commonpro-86a9c-firebase-adminsdk-i8ne6-9c9898c7fd.json";
	
	private String firebase_url = "https://commonPro.firebaseio.com";
	
    public void send_FCM(String tokenId, String title, String content,String id) {
        try {    
            //본인의 json 파일 경로 입력
            FileInputStream refreshToken = new FileInputStream(json_path);
            
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .setDatabaseUrl(firebase_url)
                    .build();
            
            //Firebase 처음 호출시에만 initializing 처리
            if(FirebaseApp.getApps().isEmpty()) { 
                FirebaseApp.initializeApp(options);
            }
            
            //String registrationToken = 안드로이드 토큰 입력;
            String registrationToken = tokenId;

            
            Message message = Message.builder()
            		.putData("id",id)
            		.putData("title", title)
            		.putData("content", content)
            		.setToken(registrationToken)
            		.build();
            
            //message 작성
            Message msg = Message.builder()
                    .setAndroidConfig(AndroidConfig.builder()
                        .setTtl(3600 * 1000) // 1 hour in milliseconds
                        .setPriority(AndroidConfig.Priority.NORMAL)
                        .setNotification(AndroidNotification.builder()
                            .setTitle(title)
                            .setBody(content)
                            .setIcon("stock_ticker_update")
                            .setColor("#f45342")
                            .build())
                        .build())
                    .setToken(registrationToken)
                    .build();

            //메세지를 FirebaseMessaging 에 보내기
            //String response = FirebaseMessaging.getInstance().send(msg);
            String response = FirebaseMessaging.getInstance().send(message);
            
            //결과 출력
            System.out.println("Successfully sent message: " + response);
                 
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}