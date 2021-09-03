package com.learning.student.validationservice.service.util;

import com.learning.student.validationservice.persistance.model.Grade;
import com.learning.student.validationservice.persistance.model.Mark;
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

    public static boolean isAverageBelowFive(List grades) {
        List<Double> averages = new ArrayList<>();
        for (Grade grade : new ArrayList<Grade>(grades)) {
            Double averageGrade = grade.getMarks().stream()
                    .mapToDouble(Mark::getMark)
                    .average()
                    .orElse(0.0);
            averages.add(averageGrade);
        }
        return averages.stream().anyMatch(a -> a < 5.0);
    }

    public CustomMessages triggerRules(Student student) {
        KieSession kieSession = kieContainer.newKieSession();
        CustomMessages messages = new CustomMessages();
        kieSession.setGlobal("messages", messages);
        kieSession.insert(student);
        kieSession.fireAllRules();
        kieSession.dispose();
        return messages;
    }
}
