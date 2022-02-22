package com.classpath.oauth2client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/userdetails")
@RequiredArgsConstructor
public class UserController {

    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @GetMapping
    public Map<String, String> userDetails(OAuth2AuthenticationToken oAuth2AuthenticationToken){
        Map<String, String> resultMap = new HashMap<>();
        OAuth2User principal = oAuth2AuthenticationToken.getPrincipal();
        OAuth2AuthorizedClient oAuth2AuthorizedClient = oAuth2AuthorizedClientService
                                                            .loadAuthorizedClient(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId(), principal.getName());
        String tokenValue = oAuth2AuthorizedClient.getAccessToken().getTokenValue();
        String principalName = oAuth2AuthorizedClient.getPrincipalName();
        Set<String> scopes = oAuth2AuthorizedClient.getAccessToken().getScopes();
        String expiryTime = oAuth2AuthorizedClient.getAccessToken().getExpiresAt().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_DATE_TIME);

        resultMap.put("Access-token ", tokenValue);
        resultMap.put("Principal ", principalName);
        resultMap.put("scopes ", scopes.toString());
        resultMap.put("Expiry time ", expiryTime);

        return resultMap;
    }
}