package be.pxl.services;

import be.pxl.services.client.EmployeeClient;
import be.pxl.services.domain.Department;
import be.pxl.services.repository.DepartmentRepository;
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
public class DepartmentTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeClient employeeClient;

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
                    .name("Marketing")
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
                .position("Content Specialist")
                .build();

        String departmentRequest = objectMapper.writeValueAsString(department);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/department")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departmentRequest))
                .andExpect(status().isCreated());

        assertEquals(11, departmentRepository.count());
    }

    @Test
    public void testFindById() throws Exception {
        List<Department> departments = departmentRepository.findAll();
        Department expectedDepartment = departments.get(0);
        int id = expectedDepartment.getId().intValue();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department/" + id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(expectedDepartment));
    }

    @Test
    public void testFindAll() throws Exception {
        List<Department> expectedDepartments = departmentRepository.findAll();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expectedDepartments)));
    }

    @Test
    public void testFindByOrganization() throws Exception {
        List<Department> departments = departmentRepository.findAll();
        Long organizationId = departments.get(0).getOrganizationId();
        List<Department> expectedDepartments = departmentRepository.findByOrganizationId(organizationId);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department/organization/" + organizationId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expectedDepartments)));
    }

    @Test
    public void testFindByOrganizationWithEmployees() throws Exception {
        List<Department> departments = departmentRepository.findAll();
        Long organizationId = departments.get(0).getOrganizationId();
        List<Department> expectedDepartments = departmentRepository.findByOrganizationId(organizationId);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/department/organization/" + organizationId + "/with-employees"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(expectedDepartments.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees").isArray());

        verify(employeeClient, times(expectedDepartments.size())).findByDepartment(anyLong());
    }
}
