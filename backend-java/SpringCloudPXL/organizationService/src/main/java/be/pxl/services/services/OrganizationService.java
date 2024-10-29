package be.pxl.services.services;

import be.pxl.services.domain.Organization;
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
                    OrganizationResponse or = mapToOrganizationResponse(organization);
                    or.setDepartments(organization.getDepartments());
                    return or;
                })
                .orElse(null);
    }

    @Override
    public OrganizationResponse getOrganizationByIdWithDepartmentsAndEmployees(Long id) {
        return organizationRepository.findById(id)
                .map(organization -> {
                    OrganizationResponse or = mapToOrganizationResponse(organization);
                    or.setDepartments(organization.getDepartments());
                    or.setEmployees(organization.getEmployees());
                    return or;
                })
                .orElse(null);
    }

    @Override
    public OrganizationResponse getOrganizationByIdWithEmployees(Long id) {
        return organizationRepository.findById(id)
                .map(organization -> {
                    OrganizationResponse or = mapToOrganizationResponse(organization);
                    or.setEmployees(organization.getEmployees());
                    return or;
                })
                .orElse(null);
    }

    private OrganizationResponse mapToOrganizationResponse(Organization organization) {
        return OrganizationResponse.builder()
                .name(organization.getName())
                .address(organization.getAddress())
                .build();
    }
}
