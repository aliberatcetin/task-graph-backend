package com.example.graphbackend.domain.response;

import com.example.graphbackend.model.Task;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GraphResponse {

    private List<Task> nodes;
    private List<Relation> edges;
}
