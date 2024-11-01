package be.pxl.services;

import be.pxl.services.client.NotificationClient;
import be.pxl.services.domain.Employee;
import be.pxl.services.domain.NotificationRequest;
import be.pxl.services.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class EmployeeTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NotificationClient notificationClient;

    @Autowired
    private EmployeeRepository employeeRepository;

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
        employeeRepository.deleteAll();

        for (int i = 0; i < 10; i++) {
            int departmentId = (i % 4) + 1;
            int organizationId = (i % 3) + 1;
            Employee employee = Employee.builder()
                    .departmentId((long) departmentId)
                    .organizationId((long) organizationId)
                    .age(24)
                    .name("Jan")
                    .position("student")
                    .build();
            employeeRepository.save(employee);
        }
    }

    @Test
    public void testAdd() throws Exception {
        Employee employee = Employee.builder()
                .departmentId(5L)
                .organizationId(4L)
                .age(24)
                .name("Jan")
                .position("student")
                .build();

        String employeeRequest = objectMapper.writeValueAsString(employee);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeRequest))
                .andExpect(status().isCreated());

        assertEquals(11, employeeRepository.findAll().size());
        verify(notificationClient, times(1)).sendNotification(any(NotificationRequest.class));
    }

    @Test
    public void testFindById() throws Exception {
        List<Employee> employees = employeeRepository.findAll();
        Employee expectedEmployee = employees.get(0);
        int id = expectedEmployee.getId().intValue();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/" + id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(expectedEmployee));
    }

    @Test
    public void testFindAll() throws Exception {
        List<Employee> expectedEmployees = employeeRepository.findAll();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(expectedEmployees.size()))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expectedEmployees)));

    }

    @Test
    public void testFindByDepartment() throws Exception {
        List<Employee> employees = employeeRepository.findAll();
        Long departmentId = employees.get(0).getDepartmentId();
        List<Employee> expectedEmployees = employeeRepository.findByDepartmentId(departmentId);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/department/" + departmentId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(expectedEmployees.size()))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expectedEmployees)));
    }

    @Test
    public void testFindByOrganization() throws Exception {
        List<Employee> employees = employeeRepository.findAll();
        Long organizationId = employees.get(0).getOrganizationId();

        List<Employee> expectedEmployees = employeeRepository.findByOrganizationId(organizationId);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/organization/" + organizationId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(expectedEmployees.size()))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expectedEmployees)));
    }
}
