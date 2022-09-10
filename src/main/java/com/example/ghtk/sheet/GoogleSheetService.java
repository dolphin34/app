package com.example.ghtk.sheet;

import com.example.ghtk.api.DataModel;
import com.example.ghtk.api.Status;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class GoogleSheetService {

    private final String range;
    private final String spreadsheetId;
    private final GoogleSheetClient client;

    public GoogleSheetService(@Value("${google.api.services.sheets.spreadsheet-id}") String spreadsheetId,
                              @Value("${google.api.services.sheets.range}") String range,
                              GoogleSheetClient client) {
        this.range = range;
        this.spreadsheetId = spreadsheetId;
        this.client = client;
    }

    public void send(DataModel model) {
        try {
            final List<List<Object>> values = List.of(
                    Arrays.asList(
                            model.getLabel_id(),
                            Status.getText(model.getStatus_id()),
                            model.getAction_time(),
                            model.getReason_code(),
                            model.getReason()
                    )
            );

            client.getService()
                    .spreadsheets().values().append(spreadsheetId, range, new ValueRange().setValues(values))
                    .setValueInputOption("RAW")
                    .execute();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
