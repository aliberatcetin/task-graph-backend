package com.example.graphbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskExecution {
    private EXECUTION_TYPE executionType;
    private String id;
}
