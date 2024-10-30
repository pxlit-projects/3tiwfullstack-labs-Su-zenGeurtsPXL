package be.pxl.services;

import be.pxl.services.domain.Employee;
import be.pxl.services.repository.EmployeeRepository;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
            int id = (i % 4) + 1;
            Employee employee = Employee.builder()
                    .departmentId((long) id)
                    .organizationId((long) id)
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
                .age(24)
                .name("Jan")
                .position("student")
                .build();

        String employeeRequest = objectMapper.writeValueAsString(employee);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeRequest))
                .andExpect(status().isCreated());

        int expectedSize = employeeRepository.findAll().size();
        assertEquals(expectedSize, employeeRepository.findAll().size());
    }

    @Test
    public void testFindById() throws Exception {
        List<Employee> employees = employeeRepository.findAll();
        int id = employees.get(0).getId().intValue();

        MvcResult mockResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/" + id))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(objectMapper.writeValueAsString(employees.get(0)), mockResult.getResponse().getContentAsString());
    }

    @Test
    public void testFindAll() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/employee"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(objectMapper.writeValueAsString(employeeRepository.findAll()), mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testFindByDepartment() throws Exception {
        List<Employee> employees = employeeRepository.findAll();
        int departmentId = employees.get(0).getDepartmentId().intValue();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/department/" + departmentId))
                .andExpect(status().isOk())
                .andReturn();

        List<Employee> expectedEmployees = employeeRepository.findAll()
                .stream().filter(employee -> employee.getDepartmentId().equals((long) departmentId)).toList();
        assertEquals(objectMapper.writeValueAsString(expectedEmployees), mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testFindByOrganization() throws Exception {
        List<Employee> employees = employeeRepository.findAll();
        int organizationId = employees.get(1).getOrganizationId().intValue();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/organization/" + organizationId))
                .andExpect(status().isOk())
                .andReturn();

        List<Employee> expectedEmployees = employeeRepository.findAll()
                .stream().filter(employee -> employee.getDepartmentId().equals((long) organizationId)).toList();
        assertEquals(objectMapper.writeValueAsString(expectedEmployees), mvcResult.getResponse().getContentAsString());
    }
}
