package be.pxl.services.services;

import be.pxl.services.client.NotificationClient;
import be.pxl.services.domain.Employee;
import be.pxl.services.domain.NotificationRequest;
import be.pxl.services.domain.dto.EmployeeRequest;
import be.pxl.services.domain.dto.EmployeeResponse;
import be.pxl.services.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final EmployeeRepository employeeRepository;
    private final NotificationClient notificationClient;

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
        rabbitTemplate.convertAndSend("employeeQueue", "Added employee with name: " + employee.getName());

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .message("Employee created")
                .sender("Tom")
                .build();
        notificationClient.sendNotification(notificationRequest);
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
        return employeeRepository.findByDepartmentId(departmentId)
                .stream()
                .map(this::mapToEmployeeResponse)
                .toList();
    }

    @Override
    public List<EmployeeResponse> getEmployeesByOrganization(Long organizationId) {
        return employeeRepository.findByOrganizationId(organizationId)
                .stream()
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
