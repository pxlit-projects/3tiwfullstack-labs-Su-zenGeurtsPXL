package be.pxl.services.services;

import be.pxl.services.domain.dto.OrganizationResponse;

public interface IOrganizationService {
    OrganizationResponse getOrganizationsById(Long id);

    OrganizationResponse getOrganizationByIdWithDepartments(Long id);

    OrganizationResponse getOrganizationByIdWithDepartmentsAndEmployees(Long id);

    OrganizationResponse getOrganizationByIdWithEmployees(Long id);
}
