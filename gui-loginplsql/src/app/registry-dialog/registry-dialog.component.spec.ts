import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistryDialogComponent } from './registry-dialog.component';

describe('RegistryDialogComponent', () => {
  let component: RegistryDialogComponent;
  let fixture: ComponentFixture<RegistryDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RegistryDialogComponent]
    });
    fixture = TestBed.createComponent(RegistryDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
