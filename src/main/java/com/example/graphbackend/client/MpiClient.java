package com.example.graphbackend.client;

import com.example.graphbackend.config.FeignMpiClientConfig;
import com.example.graphbackend.model.Task;
import com.example.graphbackend.model.TaskExecution;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(
        name = "mpiApi",
        url = "${feign.client.config.mpi-api.url}",
        configuration = FeignMpiClientConfig.class
)
public interface MpiClient {
    @RequestMapping(method = RequestMethod.POST, value = "/api")
    void runTask(@RequestBody TaskExecution execution);
}
