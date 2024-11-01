package be.pxl.services;

import be.pxl.services.client.DepartmentClient;
import be.pxl.services.client.EmployeeClient;
import be.pxl.services.domain.Organization;
import be.pxl.services.repository.OrganizationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class OrganizationServiceTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private EmployeeClient employeeClient;

    @MockBean
    private DepartmentClient departmentClient;

    @Autowired
    private OrganizationRepository organizationRepository;

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
        organizationRepository.deleteAll();

        for (int i = 0; i < 10; i++) {
            Organization organization = Organization.builder()
                    .name("Summit Technologies")
                    .address("202 Birch Lane")
                    .build();
            organizationRepository.save(organization);
        }
    }
    @Test
    public void testFindById() throws Exception {
        List<Organization> organizations = organizationRepository.findAll();
        Organization expectedOrganization = organizations.get(0);
        int id = expectedOrganization.getId().intValue();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/" + id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(expectedOrganization));
    }

    @Test
    public void testFindByIdWithDepartments() throws Exception {
        List<Organization> organizations = organizationRepository.findAll();
        Organization expectedOrganization = organizations.get(0);
        int id = expectedOrganization.getId().intValue();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/" + id + "/with-departments"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedOrganization.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(expectedOrganization.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departments").isArray());

        verify(departmentClient, times(1)).findByOrganization(anyLong());
    }

    @Test
    public void testFindByIdWithEmployees() throws Exception {
        List<Organization> organizations = organizationRepository.findAll();
        Organization expectedOrganization = organizations.get(0);
        int id = expectedOrganization.getId().intValue();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/" + id + "/with-employees"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedOrganization.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(expectedOrganization.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees").isArray());

        verify(employeeClient, times(1)).findByOrganization(anyLong());
    }

    @Test
    public void testFindByIdWithDepartmentsAndEmployees() throws Exception {
        List<Organization> organizations = organizationRepository.findAll();
        Organization expectedOrganization = organizations.get(0);
        int id = expectedOrganization.getId().intValue();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/" + id + "/with-departments-and-employees"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedOrganization.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(expectedOrganization.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departments").isArray());

        verify(departmentClient, times(1)).findByOrganizationWithEmployees(anyLong());
    }
}
