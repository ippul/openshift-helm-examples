package com.redhat.example.pam;

import org.jbpm.kie.services.impl.KModuleDeploymentUnit;
import org.jbpm.services.api.DeploymentService;
import org.jbpm.services.api.ProcessService;
import org.jbpm.services.api.RuntimeDataService;
import org.jbpm.services.api.UserTaskService;
import org.jbpm.services.api.model.DeploymentUnit;
import org.jbpm.services.api.model.ProcessInstanceDesc;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.internal.runtime.manager.context.EmptyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/pam7-services")
public class WorkflowController {

    @Autowired
    private DeploymentUnitBuilder deploymentUnitBuilder;

    @Autowired
    private ProcessService processService;

    @Autowired
    private DeploymentService deploymentService;

    @Autowired
    private RuntimeDataService runtimeDataService;

    @Autowired
    private UserTaskService userTaskService;

    @PostConstruct
    public void init(){
        deploymentService.deploy(deploymentUnitBuilder.getDeploymentUnit());
    }

    @GetMapping("/execute")
    public ResponseEntity<ProcessInstanceDesc> executeBusinessProcess() {
        Long processInstanceId = processService.startProcess(deploymentUnitBuilder.getDeploymentUnit().getIdentifier(), "my-example-project.my-process-with-humantask");
        ProcessInstanceDesc processInstanceById = runtimeDataService.getProcessInstanceById(processInstanceId);
        return ResponseEntity.ok(processInstanceById);
    }

    @GetMapping("/claim")
    public ResponseEntity<String> claimAdnCompelte() {
        List<Long> tasksByProcessInstanceId = runtimeDataService.getTasksByProcessInstanceId(1l);
        userTaskService.claim(tasksByProcessInstanceId.get(0), "Claudio");
        userTaskService.start(tasksByProcessInstanceId.get(0), "Claudio");
        userTaskService.complete(tasksByProcessInstanceId.get(0), "Claudio", new HashMap<>());
        return ResponseEntity.ok("All good");
    }
}
