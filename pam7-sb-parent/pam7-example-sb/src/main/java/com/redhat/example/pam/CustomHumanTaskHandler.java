package com.redhat.example.pam;

import com.redhat.example.jpa.MyCustomEntity;
import org.jbpm.kie.services.impl.KModuleDeploymentUnit;
import org.jbpm.services.api.DeploymentService;
import org.jbpm.services.api.model.DeploymentUnit;
import org.jbpm.services.task.wih.LocalHTWorkItemHandler;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component("Human Task")
public class CustomHumanTaskHandler extends LocalHTWorkItemHandler {

    @Autowired
    private DeploymentUnitBuilder deploymentUnitBuilder;

    @Autowired
    private DeploymentService deploymentService;

    @PersistenceContext(unitName = "org.jbpm.domain")
    private EntityManager entityManager;


    @PostConstruct
    public void init(){
        RuntimeManager runtimeManager = deploymentService.getRuntimeManager(deploymentUnitBuilder.getDeploymentUnit().getIdentifier());
        super.setRuntimeManager(runtimeManager);
    }

    @Override
    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        // Your custom logic to handle the work item, which involves creating or updating CustomEntity instances
        MyCustomEntity customEntity = new MyCustomEntity();
        customEntity.setName("Claudio");
        entityManager.persist(customEntity);

        System.out.println("****************************************************");
        System.out.println("****************************************************");
        System.out.println("****************************************************");
        System.out.println("*************** Executing custom WIH ***************");
        System.out.println("****************************************************");
        System.out.println("****************************************************");
        System.out.println("****************************************************");
        super.executeWorkItem(workItem, manager);
    }

    @Override
    public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
        System.out.println("****************************************************");
        System.out.println("****************************************************");
        System.out.println("****************************************************");
        System.out.println("***************** Abort custom WIH *****************");
        System.out.println("****************************************************");
        System.out.println("****************************************************");
        System.out.println("****************************************************");
        super.abortWorkItem(workItem, manager);
    }
}
