import {Customer} from "../../../shared/models/customer.model";
import {CustomerService} from "../../../shared/services/customer.service";
import {CustomerListComponent} from "./customer-list.component";

import {ComponentFixture, TestBed } from "@angular/core/testing";
import {Filter} from "../../../shared/models/filter.model";
import {of} from "rxjs";

describe('CustomerListComponent', () => {
  let component: CustomerListComponent;
  let fixture: ComponentFixture<CustomerListComponent>;
  let customerServiceMock: jasmine.SpyObj<CustomerService>;
  const mockCustomers: Customer[] = [
    new Customer('Dries Swinnen', 'dries@d-ries.be', 'Pelt', 'mystreet', 'Belgium', 21),
    new Customer('Alice Johnson', 'alice.johnson@example.com', 'Brussels', 'anotherstreet', 'Belgium', 35)
  ];

  beforeEach(() => {
    customerServiceMock = jasmine.createSpyObj('CustomerService', ['getCustomers', 'filterCustomers']);

    TestBed.configureTestingModule({
      imports: [CustomerListComponent],
      providers: [
        { provide: CustomerService, useValue: customerServiceMock }
      ]
    });

    fixture = TestBed.createComponent(CustomerListComponent);
    component = fixture.componentInstance;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should call fetchData on initialization', () => {
    spyOn(component, 'fetchData');
    fixture.detectChanges();
    expect(component.fetchData).toHaveBeenCalled();
  });

  it('should fetch customers and set filteredData$', () => {

    customerServiceMock.getCustomers.and.returnValue(of(mockCustomers));

    component.fetchData();

    expect(customerServiceMock.getCustomers).toHaveBeenCalled();
    component.filteredData$.subscribe(data => {
      expect(data).toEqual(mockCustomers);
    });
  });

  it('should filter customers based on the filter criteria', () => {
    const filter: Filter = { name: 'dries', city: '', vat: undefined };
    const filteredCustomers: Customer[] = [new Customer('Dries Swinnen', 'dries@d-ries.be', 'Pelt', 'mystreet', 'Belgium', 21)];
    customerServiceMock.filterCustomers.and.returnValue(of(filteredCustomers));

    component.handleFilter(filter);

    expect(customerServiceMock.filterCustomers).toHaveBeenCalledWith(filter);
    component.filteredData$.subscribe(data => {
      expect(data).toEqual(filteredCustomers);
    });
  });

});


