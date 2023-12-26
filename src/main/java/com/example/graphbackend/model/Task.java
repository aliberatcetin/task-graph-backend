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
    private final String id;
    private final String title;
    private final String task;
    private final String type;
    private final String inputSource;
    private final String output;
    private final String input;
    private final float x;
    private final float y;
    @Relationship(type = "DEPENDENT_TO", direction = INCOMING)
    private Set<Task> dependencies = new LinkedHashSet<>();
    private Set<String> targets = new HashSet<>();
}
