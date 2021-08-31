package com.learning.student.validationservice.service.util;

import com.learning.student.validationservice.persistance.model.Grade;
import com.learning.student.validationservice.persistance.model.Student;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    //the subject with average > 5
    private List<Double> calculateAverages(Student student) {
        List<Double> averages = new ArrayList<>();
        for (Grade grade : student.getGrades()) {
            Double averageGrade = grade.getMarks().stream()
                    .mapToDouble(mark -> mark.getMark())
                    .average()
                    .orElse(0.0);
            averages.add(averageGrade);
        }
        return averages;
    }
}
