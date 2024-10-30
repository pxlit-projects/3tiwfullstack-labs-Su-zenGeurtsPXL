package be.pxl.services.services;

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
                    organizationResponse.setDepartments(organization.getDepartments().stream().map(this::mapToDepartmentResponse).toList());
                    return organizationResponse;
                })
                .orElse(null);
    }

    @Override
    public OrganizationResponse getOrganizationByIdWithDepartmentsAndEmployees(Long id) {
        return organizationRepository.findById(id)
                .map(organization -> {
                    OrganizationResponse organizationResponse = mapToOrganizationResponse(organization);
                    organizationResponse.setDepartments(organization.getDepartments().stream().map(this::mapToDepartmentResponse).toList());
                    organizationResponse.setEmployees(organization.getEmployees().stream().map(this::mapToEmployeeResponse).toList());
                    return organizationResponse;
                })
                .orElse(null);
    }

    @Override
    public OrganizationResponse getOrganizationByIdWithEmployees(Long id) {
        return organizationRepository.findById(id)
                .map(organization -> {
                    OrganizationResponse organizationResponse = mapToOrganizationResponse(organization);
                    organizationResponse.setEmployees(organization.getEmployees().stream().map(this::mapToEmployeeResponse).toList());
                    return organizationResponse;
                })
                .orElse(null);
    }

    private OrganizationResponse mapToOrganizationResponse(Organization organization) {
        return OrganizationResponse.builder()
                .name(organization.getName())
                .address(organization.getAddress())
                .build();
    }

    private DepartmentResponse mapToDepartmentResponse(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
    }

    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .name(employee.getName())
                .build();
    }
}
