package be.pxl.services;

import be.pxl.services.domain.Notification;
import be.pxl.services.services.NotificationService;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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

    @Test
    public void testSendMessage() throws Exception {
        Notification notification = Notification.builder()
                .sender("Tom")
                .message("Employee created")
                .build();

        String notificationRequest = objectMapper.writeValueAsString(notification);

        Logger logger = (Logger) LoggerFactory.getLogger(NotificationService.class); // Replace with your class
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/notification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(notificationRequest))
                .andExpect(status().isCreated());

        List<ILoggingEvent> logs = listAppender.list;

        assertEquals("Receiving notification...", logs.get(0).getFormattedMessage());
        assertEquals("Sending... Employee created", logs.get(1).getFormattedMessage());
        assertEquals("TO Tom", logs.get(2).getFormattedMessage());
    }
}
