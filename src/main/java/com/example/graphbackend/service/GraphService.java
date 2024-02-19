package com.example.graphbackend.service;

import com.example.graphbackend.client.MpiClient;
import com.example.graphbackend.domain.response.GraphResponse;
import com.example.graphbackend.domain.response.Relation;
import com.example.graphbackend.model.EXECUTION_TYPE;
import com.example.graphbackend.model.TASK_STATE;
import com.example.graphbackend.model.Task;
import com.example.graphbackend.model.TaskExecution;
import com.example.graphbackend.repository.ITaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.SetUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GraphService {

    private final ITaskRepository taskRepository;

    private final MpiClient mpiClient;

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
        Task task = taskRepository.findById(taskId).get();
        task.addTarget(taskToAddToTargets.getId());
        Task task1 = taskRepository.save(task);
        Task task2 = taskRepository.findById(taskId).get();
        System.out.println("1");
    }

    public Task update(Task task) {
        Optional<Task> oldTask = taskRepository.findById(task.getId());
        if (task.getDependencies().size() != 0) {
            task.setInput(task.getDependencies().stream().findFirst().get().getId()+".txt");
        }
        Task task1 = taskRepository.save(task);
        buildRelationIfNeeds(oldTask.get(), task);
        return task1;
    }

    public Task save(Task task) {
        if (task.getTargets() == null) {
            task.setTargets(new LinkedHashSet<>());
        }
        if (task.getDependencies() == null) {
            task.setDependencies(new LinkedHashSet<>());
        }
        task.setInput("data.txt");
        task.setOutput(task.getId() + ".txt");
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

    public void runTask(Task task) {
        List<String> ids = task.getDependencies().stream().map(Task::getId).collect(Collectors.toList());
        List<Task> dependencies = taskRepository.findAllById(ids);
        if (dependencies.stream().anyMatch(task_ -> task_.getTaskState() != TASK_STATE.TERMINATED)) {
            return;
        }
        /*task.setTaskState(TASK_STATE.RUNNING);
        update(task);*/
        TaskExecution taskExecution = new TaskExecution(EXECUTION_TYPE.SINGLE, task.getId());
        mpiClient.runTask(taskExecution);
    }

    public void updateTaskState(String id, TASK_STATE state) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            Task task_ = task.get();
            task_.setTaskState(state);
            taskRepository.save(task_);

            if (task_.getTaskState() == TASK_STATE.TERMINATED) {
                Set<String> targets = task_.getTargets();
                targets.forEach(target -> {
                    runTask(taskRepository.findById(target).get());
                });
            }

        }


    }

    public void bulkRun() {
        List<Task> initial = initial();
        initial.forEach(this::runTask);
    }

    public ArrayList<ArrayList<String>> getExecutableGraph() {
        List<Task> tasks = taskRepository.findAll();
        Map<String, Task> taskMap = new HashMap<>();
        tasks.forEach(task -> taskMap.put(task.getId(), task));
        //Stack<String> stack =  topologicalSort(tasks, taskMap);
        return createClusters(taskMap, tasks);
    }


    private void topologicalSortUtil(Map<String, Task> taskMap, Task task, Map<String, Boolean> visited, Stack<String> stack) {
        visited.put(task.getId(), true);
        for (String i : task.getTargets()) {
            if (!visited.containsKey(i)) {
                topologicalSortUtil(taskMap, taskMap.get(i), visited, stack);
            }
        }
        stack.push(task.getId());
    }

    public Stack<String> topologicalSort(List<Task> tasks, Map<String, Task> taskMap) {
        Stack<String> stack = new Stack<>();
        Map<String, Boolean> visited = new HashMap<>();

        for (int i = 0; i < tasks.size(); i++) {
            if (!visited.containsKey(tasks.get(i).getId())) {
                topologicalSortUtil(taskMap, tasks.get(i), visited, stack);
            }
        }
        return stack;
    }

    public ArrayList<ArrayList<String>> createClusters(Map<String, Task> taskMap, List<Task> tasks) {
        ArrayList<ArrayList<String>> clusters = new ArrayList<>();
        Map<String, Integer> indegree = new HashMap<>();
        for (Task task : tasks) {
            indegree.put(task.getId(), 0);
        }

        // Compute indegree (number of incoming edges) for each vertex
        for (int i = 0; i < tasks.size(); i++) {
            for (String node : tasks.get(i).getTargets()) {
                indegree.put(node, indegree.get(node) + 1);
            }
        }

        Stack<String> sortedNodes = topologicalSort(tasks, taskMap);

        while (!sortedNodes.isEmpty()) {
            ArrayList<String> cluster = new ArrayList<>();
            int count = sortedNodes.size();
            while (count-- > 0) {
                String node = sortedNodes.pop();
                if (indegree.get(node) == 0) {
                    cluster.add(node);
                    for (String adjacentNode : taskMap.get(node).getTargets()) {
                        indegree.put(adjacentNode, indegree.get(node) - 1);
                    }
                }
            }
            if (!cluster.isEmpty()) {
                clusters.add(cluster);
            }
        }

        return clusters;
    }

    public List<Task> initial() {
        return taskRepository.findTasksWithNoDependencies();
    }

    public List<Task> topologicalSort() {
        List<Task> tasks = taskRepository.findAll();
        Map<String, Task> taskMap = new HashMap<>();
        Map<String, Integer> indegree = new HashMap<>();
        Queue<Task> queue = new LinkedList<>();
        List<Task> sortedTasks = new ArrayList<>();

        for (Task task : tasks) {
            taskMap.put(task.getId(), task);
            indegree.put(task.getId(), 0);
        }

        for (Task task : tasks) {
            for (Task dependency : task.getDependencies()) {
                indegree.put(dependency.getId(), indegree.getOrDefault(dependency.getId(), 0) + 1);
            }
        }

        for (Task task : tasks) {
            if (indegree.get(task.getId()) == 0) {
                queue.add(task);
            }
        }

        while (!queue.isEmpty()) {
            Task current = queue.poll();
            sortedTasks.add(current);

            for (Task dependent : current.getDependencies()) {
                int currentIndegree = indegree.get(dependent.getId()) - 1;
                indegree.put(dependent.getId(), currentIndegree);

                if (currentIndegree == 0) {
                    queue.add(taskMap.get(dependent.getId()));
                }
            }
        }

        return sortedTasks;
    }


    public List<Task> scheduleTasks() {
        List<Task> tasks = taskRepository.findAll();
        List<Task> result = new ArrayList<>();
        Map<Task, Integer> inDegree = new HashMap<>();
        Queue<Task> queue = new LinkedList<>();

        // Initialize in-degree for each task
        for (Task task : tasks) {
            inDegree.put(task, 0);
        }

        // Calculate in-degree for each task
        for (Task task : tasks) {
            for (Task dependent : task.getDependencies()) {
                inDegree.put(dependent, inDegree.get(dependent) + 1);
            }
        }

        // Add tasks with in-degree 0 to the queue
        for (Task task : tasks) {
            if (inDegree.get(task) == 0) {
                queue.add(task);
            }
        }

        // Perform topological sort
        while (!queue.isEmpty()) {
            Task current = queue.poll();
            result.add(current);

            for (Task dependent : current.getDependencies()) {
                inDegree.put(dependent, inDegree.get(dependent) - 1);
                if (inDegree.get(dependent) == 0) {
                    queue.add(dependent);
                }
            }
        }

        // Check for a cycle in the graph
        if (result.size() != tasks.size()) {
            throw new RuntimeException("Graph contains a cycle. Topological sorting is not possible.");
        }

        return result;
    }

}
