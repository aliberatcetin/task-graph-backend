package com.example.graphbackend.service;

import com.example.graphbackend.domain.response.GraphResponse;
import com.example.graphbackend.domain.response.Relation;
import com.example.graphbackend.model.Task;
import com.example.graphbackend.repository.ITaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.SetUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class GraphService {

    private final ITaskRepository taskRepository;

    public GraphResponse buildGraph(List<Task> tasks) {
        List<Relation> relations = new ArrayList<>();
        tasks.forEach(task -> {
            task.getDependencies().forEach(dependency -> {
                relations.add(new Relation(dependency.getId(), task.getId(), "empty"));
            });
        });

        return new GraphResponse(tasks, relations);
    }

    public void removeOutgoingEdge(String taskId, Task taskToRemoveFromTargets) {
        Optional<Task> task = taskRepository.findById(taskId);
        task.get().getTargets().remove(taskToRemoveFromTargets);
        taskRepository.save(task.get());
    }

    public boolean isTherePath(String id1, String id2) {
        return taskRepository.isTherePath(id1, id2) != null;
    }

    public void addOutgoingEdge(String taskId, Task taskToAddToTargets) {
        Optional<Task> task = taskRepository.findById(taskId);
        task.get().getTargets().add(taskToAddToTargets.getId());
        taskRepository.save(task.get());
    }

    public Task update(Task task) {
        Optional<Task> oldTask = taskRepository.findById(task.getId());
        buildRelationIfNeeds(oldTask.get(), task);
        return taskRepository.save(task);
    }

    public Task save(Task task) {
        if (task.getTargets() == null) {
            task.setTargets(new LinkedHashSet<>());
        }
        if (task.getDependencies() == null) {
            task.setDependencies(new LinkedHashSet<>());
        }
        return taskRepository.save(task);
    }

    public void buildRelationIfNeeds(Task oldTask, Task taskToCreateOrUpdate) {
        if (oldTask.getDependencies().size() > taskToCreateOrUpdate.getDependencies().size()) {
            SetUtils.SetView<Task> difference = SetUtils.difference(oldTask.getDependencies(), taskToCreateOrUpdate.getDependencies());
            removeOutgoingEdge(difference.stream().findFirst().get().getId(), taskToCreateOrUpdate);
        } else if (oldTask.getDependencies().size() < taskToCreateOrUpdate.getDependencies().size()) {
            SetUtils.SetView<Task> difference = SetUtils.difference(taskToCreateOrUpdate.getDependencies(), oldTask.getDependencies());
            addOutgoingEdge(difference.stream().findFirst().get().getId(), taskToCreateOrUpdate);
        }

    }

}
