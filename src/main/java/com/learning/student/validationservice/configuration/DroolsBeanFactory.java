//package com.learning.student.validationservice.configuration;
//
//import org.kie.api.KieServices;
//import org.kie.api.builder.KieBuilder;
//import org.kie.api.builder.KieFileSystem;
//import org.kie.api.builder.KieModule;
//import org.kie.api.builder.KieRepository;
//import org.kie.api.builder.ReleaseId;
//import org.kie.api.runtime.KieContainer;
//import org.kie.api.runtime.KieSession;
//import org.kie.internal.io.ResourceFactory;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//public class DroolsBeanFactory {
//    private static final String RULES_PATH = "com/learning/student/drools/rules/";
//    private KieServices kieServices = KieServices.Factory.get();
//
//    private KieFileSystem getKieFileSystem() throws IOException {
//        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
//        //TODO replace with your files
//        List<String> rules = Arrays.asList("BackwardChaining.drl", "SuggestApplicant.drl");
//        for (String rule : rules) {
//            kieFileSystem.write(ResourceFactory.newClassPathResource(rule));
//        }
//        return kieFileSystem;
//    }
//
//    public KieContainer getKieContainer() throws IOException {
//        getKieRepository();
//
//        KieBuilder kb = kieServices.newKieBuilder(getKieFileSystem());
//        kb.buildAll();
//
//        KieModule kieModule = kb.getKieModule();
//        return kieServices.newKieContainer(kieModule.getReleaseId()); //the container
//    }
//
//    private void getKieRepository() {
//        final KieRepository kieRepository = kieServices.getRepository();
//        kieRepository.addKieModule(new KieModule() {
//            public ReleaseId getReleaseId() {
//                return kieRepository.getDefaultReleaseId();
//            }
//        });
//    }
//
//    public KieSession getKieSession() {
//        getKieRepository();
//        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
//
//        kieFileSystem.write(ResourceFactory.newClassPathResource("com/learning/student/drools/rules/BackwardChaining.drl"));
//        kieFileSystem.write(ResourceFactory.newClassPathResource("com/learning/student/drools/rules/SuggestApplicant.drl"));
//
//        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
//        kb.buildAll();
//        KieModule kieModule = kb.getKieModule();
//
//        KieContainer kContainer = kieServices.newKieContainer(kieModule.getReleaseId());
//
//        return kContainer.newKieSession();
//
//    }
//}
