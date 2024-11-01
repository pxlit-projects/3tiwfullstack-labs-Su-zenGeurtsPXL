package be.pxl.services.services;

import be.pxl.services.client.EmployeeClient;
import be.pxl.services.domain.Department;
import be.pxl.services.domain.Employee;
import be.pxl.services.domain.dto.DepartmentRequest;
import be.pxl.services.domain.dto.DepartmentResponse;
import be.pxl.services.domain.dto.EmployeeResponse;
import be.pxl.services.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService implements IDepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeClient employeeClient;

    @Override
    public void addDepartment(DepartmentRequest departmentRequest) {
        Department department = Department.builder()
                .organizationId(departmentRequest.getOrganizationId())
                .name(departmentRequest.getName())
                .position(departmentRequest.getPosition())
                .build();

        departmentRepository.save(department);
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .map(this::mapToDepartmentResponse)
                .orElse(null);
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(this::mapToDepartmentResponse)
                .toList();
    }

    @Override
    public List<DepartmentResponse> getDepartmentsByOrganization(Long organizationId) {
        return departmentRepository.findByOrganizationId(organizationId)
                .stream()
                .map(this::mapToDepartmentResponse)
                .toList();
    }

    @Override
    public List<DepartmentResponse> getDepartmentsByOrganizationWithEmployees(Long organizationId) {
                return departmentRepository.findByOrganizationId(organizationId)
                .stream()
                .map(department -> {
                    DepartmentResponse departmentResponse = mapToDepartmentResponse(department);
                    departmentResponse.setEmployees(employeeClient.findByDepartment(department.getId())
                            .stream().map(this::mapToEmployeeResponse).toList());
                    return departmentResponse;
                })
                .toList();
    }

    private DepartmentResponse mapToDepartmentResponse(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .organizationId(department.getOrganizationId())
                .name(department.getName())
                .position(department.getPosition())
                .build();
    }

    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .organizationId(employee.getOrganizationId())
                .name(employee.getName())
                .age(employee.getAge())
                .position(employee.getPosition())
                .build();
    }
}
