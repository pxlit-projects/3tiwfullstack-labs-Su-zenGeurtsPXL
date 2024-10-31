package be.pxl.services;

import be.pxl.services.domain.Notification;
import be.pxl.services.repository.NotificationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class NotificationServiceTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NotificationRepository notificationRepository;

    @Container
    private static final MySQLContainer sqlContainer =
            new MySQLContainer("mysql:5.7.37");

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @BeforeEach
    public void initEach() {
        notificationRepository.deleteAll();

        for (int i = 0; i < 10; i++) {
            Notification notification = Notification.builder()
                    .from("Employee Service")
                    .to("Department Service")
                    .subject("Add")
                    .message("Added new Employee with department id 4")
                    .build();
            notificationRepository.save(notification);
        }
    }

    @Test
    public void testAdd() throws Exception {
        Notification notification = Notification.builder()
                .from("Employee Service")
                .to("Department Service")
                .subject("Add")
                .message("Added new Employee with department id 4")
                .build();

        String notificationRequest = objectMapper.writeValueAsString(notification);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/notification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(notificationRequest))
                .andExpect(status().isCreated());

        assertEquals(11, notificationRepository.findAll().size());
    }

    @Test
    public void testFindAll() throws Exception {
        List<Notification> expectedNotifications = notificationRepository.findAll();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/notification"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(expectedNotifications.size()))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expectedNotifications)));

    }
}
