package be.pxl.services;

import be.pxl.services.domain.Department;
import be.pxl.services.repository.DepartmentRepository;
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

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class DepartmentTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DepartmentRepository departmentRepository;

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
        departmentRepository.deleteAll();

        for (int i = 0; i < 10; i++) {
            int organizationId = (i % 4) + 1;
            Department department = Department.builder()
                    .organizationId((long) organizationId)
                    .name("MarketingTEST")
                    .employees(null)
                    .position("Content Specialist")
                    .build();
            departmentRepository.save(department);
        }
    }

    @Test
    public void testAdd() throws Exception{
        Department department = Department.builder()
                .organizationId(1L)
                .name("Marketing")
                .employees(null)
                .position("Content Specialist")
                .build();

        String departmentRequest = objectMapper.writeValueAsString(department);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departmentRequest))
                .andExpect(status().isCreated());

        assertEquals(11, departmentRepository.count());
    }

    // TODO: Fix commented line in testFindById
    @Test
    public void testFindById() throws Exception {
        List<Department> departments = departmentRepository.findAll();
        Department expectedDepartment = departments.get(0);
        int id = expectedDepartment.getId().intValue();

        String expectedId = "\"id\":" + id;
        String expectedOrganizationId = "\"organizationId\":" + expectedDepartment.getOrganizationId();
        String expectedName = "\"name\":\"" + expectedDepartment.getName();
        String expectedPosition = "\"position\":\"" + expectedDepartment.getPosition();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department/" + id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.jsonPath("$").value(expectedDepartment))
                .andExpect(content().string(containsString(expectedId)))
                .andExpect(content().string(containsString(expectedOrganizationId)))
                .andExpect(content().string(containsString(expectedName)))
                .andExpect(content().string(containsString(expectedPosition)));
    }

    // TODO: Fix commented line in testFindAll
    @Test
    public void testFindAll() throws Exception {
        List<Department> expectedDepartments = departmentRepository.findAll();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expectedDepartments)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(expectedDepartments.size()));
    }

    // TODO: Fix commented line in testFindByOrganization
    @Test
    public void testFindByOrganization() throws Exception {
        List<Department> departments = departmentRepository.findAll();
        Long organizationId = departments.get(0).getOrganizationId();

        List<Department> expectedDepartments = departments
                .stream()
                .filter(department -> department.getOrganizationId().equals(organizationId))
                .toList();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department/organization/" + organizationId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expectedDepartments)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(expectedDepartments.size()));
    }

    // TODO: Add testFindByOrganizationWithEmployees
}
