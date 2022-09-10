package com.example.ghtk.api;

import com.example.ghtk.sheet.GoogleSheetService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RequestMapping
@RestController
public class CallBackApi {

    @Value("${hash}")
    private String secretHash;
    private final GoogleSheetService service;

    public CallBackApi(GoogleSheetService service) {
        this.service = service;
    }

    @PostMapping(value = "/api/call-back", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    void callback(DataModel dataModel) {
        if (secretHash.equals(dataModel.getHash())
                && Objects.equals(Status.DELAY_DELIVERY.getCode(), dataModel.getStatus_id())) {
            service.send(dataModel);
        }
    }
}
