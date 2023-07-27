package com.redhat.example.pam;

import org.jbpm.kie.services.impl.KModuleDeploymentUnit;
import org.jbpm.services.api.model.DeploymentUnit;
import org.kie.api.runtime.manager.RuntimeManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DeploymentUnitBuilder {

    @Value("${kjar.groupId}")
    private String groupId;

    @Value("${kjar.artifactId}")
    private String artifactId;

    @Value("${kjar.version}")
    private String version;

    private DeploymentUnit deploymentUnit;

    @PostConstruct
    public void init() {
        deploymentUnit = new KModuleDeploymentUnit(groupId, artifactId, version);
    }

    public DeploymentUnit getDeploymentUnit(){
        return deploymentUnit;
    }
}
