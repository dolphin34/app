package com.example.ghtk.api;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataModel {
    String hash;
    String partner_id;
    String label_id;
    Integer status_id;
    String action_time;
    String reason_code;
    String reason;
    BigDecimal weight;
    BigDecimal fee;
    BigDecimal pick_money;
    Integer return_part_package;
}
