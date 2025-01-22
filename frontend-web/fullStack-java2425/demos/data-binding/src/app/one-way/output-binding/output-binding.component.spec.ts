import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OutputBindingComponent } from './output-binding.component';

describe('OutputBindingComponent', () => {
  let component: OutputBindingComponent;
  let fixture: ComponentFixture<OutputBindingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OutputBindingComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OutputBindingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
