import {Component, inject, OnInit} from '@angular/core';
import {AsyncPipe} from "@angular/common";
import {Observable} from "rxjs";
import {CustomerItemComponent} from "../customer-item/customer-item.component";
import {AddCustomerComponent} from "../add-customer/add-customer.component";
import {FilterComponent} from "../filter/filter.component";
import {Customer} from "../../../shared/models/customer.model";
import {Filter} from "../../../shared/models/filter.model";
import {CustomerService} from "../../../shared/services/customer.service";

@Component({
  selector: 'app-customer-list',
  standalone: true,
  imports: [CustomerItemComponent, FilterComponent, AddCustomerComponent, AsyncPipe],
  templateUrl: './customer-list.component.html',
  styleUrl: './customer-list.component.css'
})
export class CustomerListComponent implements OnInit {
  filteredData$!: Observable<Customer[]>;
  customerService: CustomerService = inject(CustomerService);

  ngOnInit(): void {
    this.fetchData();
  }

  handleFilter(filter: Filter){
    this.filteredData$ = this.customerService.filterCustomers(filter);
  }

  fetchData(): void {
    this.filteredData$ = this.customerService.getCustomers();
  }
}
