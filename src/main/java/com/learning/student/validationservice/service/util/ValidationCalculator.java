package com.learning.student.validationservice.service.util;

import com.learning.student.validationservice.persistance.model.Student;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationCalculator {

    @Autowired
    private KieContainer kieContainer;

    public CustomMessages triggerRules(Student student) {
        KieSession kieSession = kieContainer.newKieSession();
        CustomMessages messages = new CustomMessages();
        kieSession.setGlobal("messages", messages);
        kieSession.insert(student);
        kieSession.fireAllRules();
        return messages;
    }
}
