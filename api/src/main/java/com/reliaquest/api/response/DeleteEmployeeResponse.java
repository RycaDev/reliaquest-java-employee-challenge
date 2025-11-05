package com.reliaquest.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteEmployeeResponse {
    Boolean data;
    String status;
}
