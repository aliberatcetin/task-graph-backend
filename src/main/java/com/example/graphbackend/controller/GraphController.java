package com.example.graphbackend.controller;


import com.example.graphbackend.domain.response.GraphResponse;
import com.example.graphbackend.model.TASK_STATE;
import com.example.graphbackend.model.Task;
import com.example.graphbackend.repository.ITaskRepository;
import com.example.graphbackend.service.GraphService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/graph")
@CrossOrigin(maxAge = 3600)
public class GraphController {

    private final GraphService graphService;
    private final ITaskRepository taskRepository;

    @GetMapping
    public GraphResponse getGraph() {
        List<Task> tasks = taskRepository.findAll();
        return graphService.buildGraph(tasks);
    }

    @GetMapping("/{id1}/{id2}")
    public boolean isTherePath(@PathVariable String id1, @PathVariable String id2) {
        return graphService.isTherePath(id1, id2);
    }

    @GetMapping("/{id}")
    public Optional<Task> getTaskById(@PathVariable String id) {
        return taskRepository.findById(id);
    }
    @GetMapping("/executionDetail/{id}")
    public Optional<Task> getTaskByIdForExecution(@PathVariable String id) {
        Optional<Task> task = taskRepository.findById(id);
        if(task.isEmpty()){
            return null;
        }
        task.get().setDependencies(new LinkedHashSet<>());
        return task;
    }

    @PutMapping
    Task updateTask(@RequestBody Task task) {
        Task t = graphService.update(task);
        return t;
    }

    @PostMapping
    Task createTask(@RequestBody Task task) {
        Task t = graphService.save(task);
        return t;
    }

    @GetMapping("/run/bulk")
    void topologicalSort() {
        graphService.bulkRun();
    }

    @PostMapping("/run")
    void runTask(@RequestBody Task task) {
        graphService.runTask(task);
    }

    @GetMapping("/clearall")
    void runTask() {
        taskRepository.clearAll();
    }

    @GetMapping("/run/{id}/{state}")
    void updateTaskState(@PathVariable String id, @PathVariable TASK_STATE state) {
        graphService.updateTaskState(id, state);
    }

    @DeleteMapping("{id}")
    void deleteTask(@PathVariable String id) {
        taskRepository.deleteById(id);
    }

    @DeleteMapping("/{id1}/{id2}")
    void detach(@PathVariable String id1, @PathVariable String id2) {
       graphService.deletePath(id1, id2);
    }

    @GetMapping("/{id}/dependants")
    List<Task> getDependants(@PathVariable String id){
        return taskRepository.findNodesDependentOnTask(id);
    }

}
