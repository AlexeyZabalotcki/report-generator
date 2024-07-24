//package com.report.generator.demo;
//
//import com.report.generator.demo.repository.MessageRepository;
//import com.report.generator.demo.repository.UsersRepository;
//import com.report.generator.demo.repository.entity.Message;
//import com.report.generator.demo.repository.entity.Users;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//import java.util.Objects;
//import java.util.Random;
//import java.util.UUID;
//
//@TestPropertySource(properties = "app.scheduling.enable=false")
//@SpringBootTest
//class DemoApplicationTests {
//
//    @Autowired
//    private MessageRepository messageRepository;
//    @Autowired
//    private UsersRepository usersRepository;
//
//    private static final Instant NOW = Instant.now();
//
//    private static final List<String> NAMES = List.of("Juce", "Loni", "Alsantrius", "Miromice", "Nuliax", "Topmen",
//        "Modar", "GawelleN", "Rageseeker", "Bliss", "Envias", "Gralinda", "Kitaxe", "Azago", "Blackbrand",
//        "Kakashkaliandiia", "Kirizan", "Kizshura", "Manesenci", "DARTSKRIMER", "DrayLOVE");
//
//    @Test
//    void generateData() {
//        generateUsers();
//        Random r = new Random();
//        long count = messageRepository.count();
//        for (long i = count; i <= 10000; i++) {
//            messageRepository.save(Message.builder()
//                .messageDate(getRandomDate(r))
//                .userId(r.nextInt(21) + 1)
//                .text(UUID.randomUUID().toString())
//                .build());
//        }
//    }
//
//    void generateUsers() {
//        Random r = new Random();
//        for (String name : NAMES) {
//            if (Objects.nonNull(usersRepository.getUsersByName(name))) {
//                continue;
//            }
//            usersRepository.save(Users.builder()
//                .name(name)
//                .rating(r.nextInt(1000))
//                .build());
//        }
//    }
//
//    private Instant getRandomDate(Random r) {
//        Instant dateTo = NOW.truncatedTo(ChronoUnit.DAYS).minus(1, ChronoUnit.SECONDS);
//        long dateFrom = dateTo.truncatedTo(ChronoUnit.DAYS).getEpochSecond();
//        long date = r.nextInt((int) (dateTo.getEpochSecond() - dateFrom)) + dateFrom;
//        return Instant.ofEpochSecond(date);
//    }
//}
