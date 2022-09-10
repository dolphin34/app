package com.example.ghtk.sheet;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.Sheets.Builder;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

/**
 * Created by duy.bui
 * Date: 3/30/21
 **/

@Component
public class GoogleSheetClient {

    private static final String APPLICATION_NAME = "Project New";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);

    private volatile Sheets service;

    public Sheets getService() throws IOException, GeneralSecurityException {
        Sheets localRef = service;
        if (localRef == null) {
            synchronized (this) {
                localRef = service;
                if (localRef == null) {
                    NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
                    service = localRef = new Builder(httpTransport,
                            JSON_FACTORY,
                            getCredentialByServiceAccount(httpTransport))
                            .setApplicationName(APPLICATION_NAME)
                            .build();
                }
            }
        }
        return localRef;
    }

    private Credential getCredentialByServiceAccount(final NetHttpTransport httpTransport) throws IOException, GeneralSecurityException {
        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId("test-ghtk-41423@high-wave-362111.iam.gserviceaccount.com")
                .setServiceAccountPrivateKeyFromP12File(FileUtils.getResourceAsStream("high-wave-362111-411a3641aefe.p12"))
                .setServiceAccountScopes(SCOPES).build();

        credential.refreshToken();

        return credential;
    }

}