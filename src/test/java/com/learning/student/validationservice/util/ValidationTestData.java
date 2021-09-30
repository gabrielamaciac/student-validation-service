package com.learning.student.validationservice.util;

import com.learning.student.validationservice.controller.model.AddressDto;
import com.learning.student.validationservice.controller.model.GradeDto;
import com.learning.student.validationservice.controller.model.MarkDto;
import com.learning.student.validationservice.controller.model.StudentDto;
import com.learning.student.validationservice.integration.model.ValidationResponse;
import com.learning.student.validationservice.persistance.model.Address;
import com.learning.student.validationservice.persistance.model.Grade;
import com.learning.student.validationservice.persistance.model.Mark;
import com.learning.student.validationservice.persistance.model.Student;
import com.learning.student.validationservice.persistance.model.ValidationDetail;
import com.learning.student.validationservice.service.util.CustomMessages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ValidationTestData {
    public static final String VALIDATION_ID = "4ce2dddb-782b-4305-b7e3-9981fda23699";
    public static final String STUDENT_ID = "4ce2dddb-438a-4305-b7e3-9981fda59355";
    public static final String ERROR_NAME = "Some error name";
    public static final String ERROR_DESCRIPTION = "Some error description";

    public static final UUID VALIDATION_UUID = UUID.fromString(VALIDATION_ID);
    public static final UUID STUDENT_UUID = UUID.fromString(STUDENT_ID);

    public static final String TEST_FIRST_NAME = "Test FirstName";
    public static final String TEST_LAST_NAME = "Test LastName";
    public static final String TEST_CNP = "Test CNP";
    public static final String DATE_OF_BIRTH = "1987-12-10";
    public static final String TEST_CITY = "Test City";
    public static final String TEST_COUNTRY = "Test Country";
    public static final String TEST_NUMBER = "Test Number";
    public static final String TEST_STREET = "Test Street";
    public static final String TEST_SUBJECT = "Test Subject";
    public static final String DATE_RECEIVED = "2020-03-12";

    public static final String STUDENT_JSON_WITH_ID = "{\n" +
            "    \"id\": \"4ce2dddb-438a-4305-b7e3-9981fda59355\",\n" +
            "    \"firstName\": \"Test FirstName\",\n" +
            "    \"lastName\": \"Test LastName\",\n" +
            "    \"cnp\": \"Test CNP\",\n" +
            "    \"dateOfBirth\": \"1987-12-03\",\n" +
            "    \"address\": {\n" +
            "        \"street\": \"Test Street\",\n" +
            "        \"number\": \"Test Number\",\n" +
            "        \"city\": \"Test City\",\n" +
            "        \"country\": \"Test Country\"\n" +
            "    },\n" +
            "    \"grades\": [\n" +
            "        {\n" +
            "            \"subject\": \"Grammar\",\n" +
            "            \"marks\": [\n" +
            "                {\n" +
            "                    \"dateReceived\": \"2020-03-12\",\n" +
            "                    \"mark\": 10.0\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    ],\n" +
            "    \"valid\": true\n" +
            "}";

    private ValidationTestData() {
    }

    public static ValidationDetail getValidationDetails() {
        return new ValidationDetail(VALIDATION_UUID, STUDENT_UUID, ERROR_NAME, ERROR_DESCRIPTION);
    }

    public static Student getStudent() {
        Address address = new Address(TEST_CITY, TEST_COUNTRY, TEST_NUMBER, TEST_STREET);
        Mark mark = new Mark(DATE_RECEIVED, 10.0);
        Grade grade = new Grade(TEST_SUBJECT, Collections.singletonList(mark));
        return new Student(STUDENT_UUID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_CNP, DATE_OF_BIRTH, address, Collections.singletonList(grade), true);
    }

    public static Student getInvalidStudent() {
        Address address = new Address(null, TEST_COUNTRY, TEST_NUMBER, TEST_STREET);
        Mark mark = new Mark(DATE_RECEIVED, 4.0);
        Grade grade = new Grade(TEST_SUBJECT, Collections.singletonList(mark));
        return new Student(STUDENT_UUID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_CNP, DATE_OF_BIRTH, address, Collections.singletonList(grade), true);
    }

    public static StudentDto getStudentDto() {
        AddressDto address = new AddressDto(TEST_CITY, TEST_COUNTRY, TEST_NUMBER, TEST_STREET);
        MarkDto mark = new MarkDto(DATE_RECEIVED, 10.0);
        GradeDto grade = new GradeDto(TEST_SUBJECT, Collections.singletonList(mark));
        return new StudentDto(STUDENT_ID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_CNP, DATE_OF_BIRTH, address, Collections.singletonList(grade), true);
    }

    public static List<Grade> getGrades(Double mark1, Double mark2) {
        Mark firstMark = new Mark(DATE_RECEIVED, mark1);
        Mark secondMark = new Mark(DATE_RECEIVED, mark2);
        List<Mark> marks = new ArrayList<>();
        marks.add(firstMark);
        marks.add(secondMark);
        return Collections.singletonList(new Grade(TEST_SUBJECT, marks));
    }

    public static CustomMessages getCustomMessages() {
        CustomMessages customMessages = new CustomMessages();
        customMessages.addMessage(ERROR_NAME, ERROR_DESCRIPTION);
        return customMessages;
    }

    public static ValidationResponse getValidationResponse() {
        return new ValidationResponse(STUDENT_UUID, true);
    }

    public static String getStudentJson(Double grade1, Double grade2) {
        return "{\n" +
                "    \"firstName\": \"Test FirstName\",\n" +
                "    \"lastName\": \"Test LastName\",\n" +
                "    \"cnp\": \"Test CNP\",\n" +
                "    \"dateOfBirth\": \"1987-12-03\",\n" +
                "    \"address\": {\n" +
                "        \"street\": \"Test Street\",\n" +
                "        \"number\": \"Test number\",\n" +
                "        \"city\": \"Test City\",\n" +
                "        \"country\": \"Test Country\"\n" +
                "    },\n" +
                "    \"grades\": [\n" +
                "        {\n" +
                "            \"subject\": \"Geografie\",\n" +
                "            \"marks\": [\n" +
                "                {\n" +
                "                    \"dateReceived\": \"2020-03-12\",\n" +
                "                    \"mark\": " + grade1 + "\n" +
                "                },\n" +
                "                {\n" +
                "                    \"dateReceived\": \"2020-03-12\",\n" +
                "                    \"mark\": " + grade2 + "\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }

    public static String getErrorForStudentJson(String uuid) {
        return "[{\"studentId\":\"" + uuid + "\",\"errorName\":\"Grade average below 5.\",\"errorDescription\":\"There is at least one subject for which the average is below 5.\"}]";
    }

}
