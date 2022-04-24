package com.buddiend.buddiend.controllers.video;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//? jwt imports
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//? http client
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.json.simple.JSONObject;
//? to convert json
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/video")
public class VideoController {
    String VIDEOSDK_API_KEY = "354a7b83-9444-41dd-97ca-c90f81db7763";
    String VIDEOSDK_SECRET_KEY = "3863e5f2b9f01909410ca3663053e0b3d75cf33a27d6658051788fc9c4291a2e";
    String VIDEOSDK_API_ENDPOINT = "https://api.videosdk.live";

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    @GetMapping(value="/get-token",
            produces = "application/json")
    public JSONObject generateToken() {
        Map<String, Object> payload = new HashMap<>();

        payload.put("apikey", VIDEOSDK_API_KEY);
        payload.put("permissions", new String[]{"allow_join", "allow_mod"});

        String token = Jwts.builder().setClaims(payload)
                .setExpiration(new Date(System.currentTimeMillis() + 86400 * 1000))
                .signWith(SignatureAlgorithm.HS256,VIDEOSDK_SECRET_KEY.getBytes()).compact();

        JSONObject tokenJsonObject = new JSONObject();
        tokenJsonObject.put("token", token);

        return tokenJsonObject;
    }

    @PostMapping(
            value="/create-meeting",
            produces = "application/json"
    )
    public String createMeeting(@RequestBody String requestBody) throws IOException, InterruptedException, ParseException {

        //JsonParser to  json
        JSONParser parser = new JSONParser();
        String token;

        JSONObject jsonBody=(JSONObject) parser.parse(requestBody.toString());
        token=(String)jsonBody.get("token");

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .uri(URI.create(VIDEOSDK_API_ENDPOINT + "/api/meetings"))
                .setHeader("Authorization", token) // add request header
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }


    @PostMapping(
            value="/validate-meeting/{meetingId}",
            produces = "application/json"
    )
    public String validateMeeting(@RequestBody String requestBody,@PathVariable String meetingId) throws ParseException, IOException, InterruptedException {
        JSONParser parser = new JSONParser();
        String token;

        JSONObject jsonBody=(JSONObject) parser.parse(requestBody.toString());
        token=(String)jsonBody.get("token");
        String id=meetingId;
        String url= VIDEOSDK_API_ENDPOINT+"/api/meetings/"+id;

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .uri(URI.create(url))
                .setHeader("Authorization", token) // add request header
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return (response.body());
    }
}
