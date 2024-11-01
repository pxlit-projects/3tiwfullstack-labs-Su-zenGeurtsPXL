package be.pxl.services.services;

import be.pxl.services.domain.dto.EmployeeRequest;
import be.pxl.services.domain.dto.EmployeeResponse;

import java.util.List;

public interface IEmployeeService {
    void addEmployee(EmployeeRequest employeeRequest);

    EmployeeResponse getEmployeeById(Long id);

    List<EmployeeResponse> getAllEmployees();

    EmployeeResponse getEmployeeByDepartment(Long departmentId);

    EmployeeResponse getEmployeeByOrganization(Long organizationId);
}
