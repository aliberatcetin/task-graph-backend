package com.example.xoxbackend.domain.response;

import com.example.xoxbackend.model.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GraphResponse {

    private List<Task> nodes;
    private List<Relation> edges;
}
