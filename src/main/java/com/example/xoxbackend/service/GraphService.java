package com.example.xoxbackend.service;

import com.example.xoxbackend.domain.response.GraphResponse;
import com.example.xoxbackend.domain.response.Relation;
import com.example.xoxbackend.model.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GraphService {

    public GraphResponse buildGraph(List<Task> tasks) {
        List<Relation> relations = new ArrayList<>();
        tasks.forEach(task -> {
            task.getDependencies().forEach(dependency -> {
                relations.add(new Relation(dependency.getId(),task.getId(), "empy"));
            });
        });

        return new GraphResponse(tasks, relations);
    }


}
