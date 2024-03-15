package com.example.graphbackend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;
import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node("Task")
@Data
@AllArgsConstructor
public class Task {

    @Id
    private String id;
    private String title;
    private String task;
    private String type;
    private String inputSource;
    private String output;
    DATA_TYPE dataType = DATA_TYPE.INTEGER;
    private String input;
    private String runLog;
    TASK_STATE taskState = TASK_STATE.READY;
    private final float x;
    private final float y;
    @Relationship(type = "DEPENDANT_TO", direction = OUTGOING)
    private Set<Task> dependencies = new LinkedHashSet<>();
    private Set<String> targets;

    public void addTarget(String id){
        targets.add(id);
    }
}
