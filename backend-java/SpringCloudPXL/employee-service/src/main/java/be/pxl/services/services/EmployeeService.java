package be.pxl.services.services;

import be.pxl.services.domain.Employee;
import be.pxl.services.domain.dto.EmployeeRequest;
import be.pxl.services.domain.dto.EmployeeResponse;
import be.pxl.services.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService{

    private final EmployeeRepository employeeRepository;

    @Override
    public void addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = Employee.builder()
                .organizationId(employeeRequest.getOrganizationId())
                .departmentId(employeeRequest.getDepartmentId())
                .name(employeeRequest.getName())
                .position(employeeRequest.getPosition())
                .age(employeeRequest.getAge())
                .build();

        employeeRepository.save(employee);
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(this::mapToEmployeeResponse)
                .orElse(null);
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToEmployeeResponse)
                .toList();
    }

    @Override
    public List<EmployeeResponse> getEmployeesByDepartment(Long departmentId) {
        return employeeRepository.findAll()
                .stream()
                .filter(employee -> employee.getDepartmentId().equals(departmentId))
                .map(this::mapToEmployeeResponse)
                .toList();
    }

    @Override
    public List<EmployeeResponse> getEmployeesByOrganization(Long organizationId) {
        return employeeRepository.findAll()
                .stream()
                .filter(employee -> employee.getOrganizationId().equals(organizationId))
                .map(this::mapToEmployeeResponse)
                .toList();
    }

    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .organizationId(employee.getOrganizationId())
                .departmentId(employee.getDepartmentId())
                .name(employee.getName())
                .position(employee.getPosition())
                .age(employee.getAge())
                .build();
    }
}
