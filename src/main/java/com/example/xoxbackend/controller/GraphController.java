package com.example.xoxbackend.controller;


import com.example.xoxbackend.domain.response.GraphResponse;
import com.example.xoxbackend.model.Task;
import com.example.xoxbackend.repository.ITaskRepository;
import com.example.xoxbackend.service.GraphService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping
    Task updateTask(@RequestBody Task task) {
        Task t =  taskRepository.save(task);
        return t;
    }

    @PostMapping
    Task createTask(@RequestBody Task task) {
        Task t =  taskRepository.save(task);
        return t;
    }

    @DeleteMapping("{id}")
    void deleteTask(@PathVariable String id) {
        taskRepository.deleteById(id);
    }

    @GetMapping("/{id1}/{id2}")
    void detach(@PathVariable String id1, @PathVariable String id2){
        taskRepository.deleteContact(id1,id2);
    }



}
