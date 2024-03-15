package com.example.graphbackend.repository;

import com.example.graphbackend.model.TASK_STATE;
import com.example.graphbackend.model.Task;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Book;
import java.util.List;

public interface ITaskRepository extends Neo4jRepository<Task, String> {
    @Query("MATCH(task1:Task {id: $id1})-[c:DEPENDANT_TO]-(task2:Task {id:$id2}) DETACH DELETE c")
    public String deleteContact(String id1, String id2);

    @Query("MATCH path = (startNode:Task)-[:DEPENDANT_TO*]->(endNode:Task) WHERE startNode.id = $id1 AND endNode.id = $id2 RETURN path is not NULL")
    public Boolean isTherePath(@Param("id1") String id1, @Param("id2") String id2);

    @Query("MATCH (node:Task) WHERE NOT (node)-[:DEPENDANT_TO]->() RETURN node")
    List<Task> findTasksWithNoDependencies();

    @Query("MATCH (a:Task {id:$id}) SET a.taskState = $taskState")
    void updateTaskState(@Param("id") String id, @Param("taskState") TASK_STATE taskState);

    @Query("MATCH (t:Task) OPTIONAL MATCH (t)-[:DEPENDANT_TO]->(d:Task) RETURN t, COLLECT(d) as dependencies")
    List<Task> findAllTasks();

    @Query("MATCH (n:Task {id:$id}) SET n.targets = [value in n.targets WHERE value <> $target]")
    void removeTarget(@Param("id") String id, @Param("target") String target);

    @Query("MATCH (dependent:Task)-[:DEPENDANT_TO]->(target:Task {id: $taskId}) RETURN dependent")
    List<Task> findNodesDependentOnTask(@Param("taskId") String taskId);

    @Query("MATCH (task:Task {id: $taskId})-[r:DEPENDANT_TO]->(dependency:Task)\n" +
            "with task, COLLECT({relationship: r, dependency: dependency}) AS outgoingEdges\n" +
            "return all(edgeInfo IN outgoingEdges WHERE edgeInfo.dependency.taskState = 'TERMINATED') AS allDependenciesTerminated;")
    public boolean isReadyToRun(@Param("taskId") String taskId);

    @Query("MATCH (task:Task)\n" +
            "SET task.taskState = 'READY';")
    void clearAll();

    @Query("MATCH (n:Task {id:$taskId}) return n.taskState = 'RUNNING'")
    public boolean isRunning(@Param("taskId") String taskId);
}

