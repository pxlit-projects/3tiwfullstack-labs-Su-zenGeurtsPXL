package be.pxl.services;

import be.pxl.services.domain.Organization;
import be.pxl.services.repository.OrganizationRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class OrganizationServiceTests {

    @Autowired
    MockMvc mockMvc;

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
                    .employees(null)
                    .departments(null)
                    .build();
            organizationRepository.save(organization);
        }
    }

    // TODO: Fix commented line in testFindById
    @Test
    public void testFindById() throws Exception {
        List<Organization> organizations = organizationRepository.findAll();
        Organization expectedOrganization = organizations.get(0);
        int id = expectedOrganization.getId().intValue();

        String expectedId = "\"id\":" + id;
        String expectedName = "\"name\":\"" + expectedOrganization.getName() + "\"";
        String expectedAddress = "\"address\":\"" + expectedOrganization.getAddress() + "\"";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/organization/" + id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.jsonPath("$").value(expectedOrganization))
                .andExpect(content().string(containsString(expectedId)))
                .andExpect(content().string(containsString(expectedName)))
                .andExpect(content().string(containsString(expectedAddress)));
    }

    // TODO: Add testFindByIdWithDepartments
    // TODO: Add testFindByIdWithDepartmentsAndEmployees
    // TODO: Add testFindByIdWithEmployees

}
