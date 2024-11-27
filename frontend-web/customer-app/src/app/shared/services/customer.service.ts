import {inject, Injectable} from '@angular/core';
import {Customer} from "../models/customer.model";
import {Filter} from "../models/filter.model";
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})

export class CustomerService {
  api: string = environment.apiUrl;

  http: HttpClient = inject(HttpClient);

  getCustomers(): Observable<Customer[]> {
    return this.http.get<Customer[]>(this.api);
  }

  addCustomer(customer: Customer): Observable<Customer> {
    return this.http.post<Customer>(this.api, customer);
  }

  filterCustomers(filter: Filter): Observable<Customer[]> {
    return this.http.get<Customer[]>(this.api).pipe(
      map((customers: Customer[]) => customers.filter(customer => this.isCustomerMatchingFilter(customer, filter)))
    );
  }

  updateCustomer(customer: Customer): Observable<Customer>{
    return this.http.put<Customer>('/api/customers/' + customer.id, customer);
  }

  getCustomer(id: number): Observable<Customer> {
    return this.http.get<Customer>('api/customers/' + id);
  }


  public isCustomerMatchingFilter(customer: Customer, filter: Filter): boolean {
    const matchesName = customer.name.toLowerCase().includes(filter.name.toLowerCase());
    const matchesCity = customer.city.toLowerCase().includes(filter.city.toLowerCase());
    const matchesVat = filter.vat ? customer.vat === filter.vat : true;

    return matchesName && matchesCity && matchesVat;
  }
}
