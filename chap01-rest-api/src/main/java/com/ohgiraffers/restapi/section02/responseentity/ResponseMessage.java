package com.ohgiraffers.restapi.section02.responseentity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ResponseMessage {

    private int httpStatus;

    private String message;

    private Map<String, Object> results;

}
