import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AddCustomerComponent } from './add-customer.component';
import { CustomerService } from '../../../shared/services/customer.service';
import { Router } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { of } from 'rxjs';
import {Customer} from "../../../shared/models/customer.model";

describe('AddCustomerComponent', () => {
  let component: AddCustomerComponent;
  let fixture: ComponentFixture<AddCustomerComponent>;
  let customerServiceMock: jasmine.SpyObj<CustomerService>;
  let routerMock: jasmine.SpyObj<Router>;

  beforeEach(() => {
    customerServiceMock = jasmine.createSpyObj('CustomerService', ['addCustomer']);
    routerMock = jasmine.createSpyObj('Router', ['navigate']);

    TestBed.configureTestingModule({
      imports: [AddCustomerComponent, ReactiveFormsModule],
      providers: [
        { provide: CustomerService, useValue: customerServiceMock },
        { provide: Router, useValue: routerMock }
      ]
    });

    fixture = TestBed.createComponent(AddCustomerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the form with required controls and validators', () => {
    expect(component.customerForm).toBeTruthy();
    expect(component.customerForm.controls['name'].valid).toBeFalse();
    expect(component.customerForm.controls['email'].valid).toBeFalse();
    expect(component.customerForm.controls['avatar'].value).toBe('default.png');
  });

  it('should call addCustomer on form submit and navigate on success', () => {
    const mockCustomer = {
      name: 'John Doe',
      email: 'john.doe@example.com',
      avatar: 'avatar.png',
      city: 'Cityville',
      address: '123 Main St',
      country: 'Countryland',
      vat: 123
    };

    component.customerForm.setValue(mockCustomer);
    customerServiceMock.addCustomer.and.returnValue(of(mockCustomer as Customer));

    component.onSubmit();

    expect(customerServiceMock.addCustomer).toHaveBeenCalledWith(mockCustomer as Customer);

    expect(component.customerForm.pristine).toBeTrue();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/customers']);
  });
});
