package be.pxl.services.controller;

import be.pxl.services.domain.dto.EmployeeRequest;
import be.pxl.services.domain.dto.EmployeeResponse;
import be.pxl.services.services.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final IEmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody EmployeeRequest employeeRequest) {
        employeeService.addEmployee(employeeRequest);
    }

    @GetMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse findById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeResponse> findAll() {
        return employeeService.getAllEmployees();
    }

    @GetMapping(path = "/department/{departmentId}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse findByDepartment(@PathVariable Long departmentId) {
        return employeeService.getEmployeeByDepartment(departmentId);
    }

    @GetMapping(path = "/organization/{organizationId}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse findByOrganization(@PathVariable Long organizationId) {
        return employeeService.getEmployeeByOrganization(organizationId);
    }
}
