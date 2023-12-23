package com.example.xoxbackend.repository;

import com.example.xoxbackend.model.Task;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITaskRepository extends Neo4jRepository<Task, String> {
    List<Task> findOneByTitle(String title);

    @Query("MATCH(task1:Task {id: $id1})-[c:DEPENDENT_TO]-(task2:Task {id:$id2}) DETACH DELETE c")
    public String deleteContact(String id1, String id2);
}
