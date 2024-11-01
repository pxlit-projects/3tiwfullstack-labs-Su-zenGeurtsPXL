package be.pxl.services.services;

import be.pxl.services.client.DepartmentClient;
import be.pxl.services.client.EmployeeClient;
import be.pxl.services.domain.Department;
import be.pxl.services.domain.Employee;
import be.pxl.services.domain.Organization;
import be.pxl.services.domain.dto.DepartmentResponse;
import be.pxl.services.domain.dto.EmployeeResponse;
import be.pxl.services.domain.dto.OrganizationResponse;
import be.pxl.services.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationService implements IOrganizationService {

    private final OrganizationRepository organizationRepository;
    private final DepartmentClient departmentClient;
    private final EmployeeClient employeeClient;

    @Override
    public OrganizationResponse getOrganizationsById(Long id) {
        return organizationRepository.findById(id)
                .map(this::mapToOrganizationResponse)
                .orElse(null);
    }

    @Override
    public OrganizationResponse getOrganizationByIdWithDepartments(Long id) {
        return organizationRepository.findById(id)
                .map(organization -> {
                    OrganizationResponse organizationResponse = mapToOrganizationResponse(organization);
                    organizationResponse.setDepartments(departmentClient.findByOrganization(id)
                            .stream().map(this::mapToDepartmentResponse).toList());
                    return organizationResponse;
                })
                .orElse(null);
    }

    @Override
    public OrganizationResponse getOrganizationByIdWithDepartmentsAndEmployees(Long id) {
        return organizationRepository.findById(id)
                .map(organization -> {
                    OrganizationResponse organizationResponse = mapToOrganizationResponse(organization);
                    organizationResponse.setDepartments(departmentClient.findByOrganizationWithEmployees(id)
                            .stream().map(this::mapToDepartmentResponseWithEmployees).toList());
                    return organizationResponse;
                })
                .orElse(null);
    }

    @Override
    public OrganizationResponse getOrganizationByIdWithEmployees(Long id) {
        return organizationRepository.findById(id)
                .map(organization -> {
                    OrganizationResponse organizationResponse = mapToOrganizationResponse(organization);
                    organizationResponse.setEmployees(employeeClient.findByOrganization(id)
                            .stream().map(this::mapToEmployeeResponse).toList());
                    return organizationResponse;
                })
                .orElse(null);
    }

    private OrganizationResponse mapToOrganizationResponse(Organization organization) {
        return OrganizationResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .address(organization.getAddress())
                .build();
    }

    private DepartmentResponse mapToDepartmentResponse(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .position(department.getPosition())
                .build();
    }

    private DepartmentResponse mapToDepartmentResponseWithEmployees(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .employees(department.getEmployees().stream().map(this::mapToEmployeeResponse).toList())
                .name(department.getName())
                .position(department.getPosition())
                .build();
    }

    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .departmentId(employee.getDepartmentId())
                .name(employee.getName())
                .age(employee.getAge())
                .position(employee.getPosition())
                .build();
    }
}
