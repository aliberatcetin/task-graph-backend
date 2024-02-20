package com.example.graphbackend.repository;

import com.example.graphbackend.model.TASK_STATE;
import com.example.graphbackend.model.Task;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Book;
import java.util.List;

public interface ITaskRepository extends Neo4jRepository<Task, String> {
    @Query("MATCH(task1:Task {id: $id1})-[c:DEPENDENT_TO]-(task2:Task {id:$id2}) DETACH DELETE c")
    public String deleteContact(String id1, String id2);

    @Query("MATCH path = (startNode:Task)-[:DEPENDENT_TO*]->(endNode:Task) WHERE startNode.id = $id1 AND endNode.id = $id2 RETURN path is not NULL")
    public Boolean isTherePath(@Param("id1") String id1, @Param("id2") String id2);

    @Query("MATCH (s:Task) WHERE NOT (s)<-[:DEPENDENT_TO]-(:Task) RETURN s")
    List<Task> findTasksWithNoDependencies();

    @Query("MATCH (a:Task {id:$id}) SET a.taskState = $taskState")
    void updateTaskState(@Param("id") String id, @Param("taskState") TASK_STATE taskState);
}
