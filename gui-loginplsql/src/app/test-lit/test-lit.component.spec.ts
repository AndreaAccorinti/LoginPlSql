import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestLitComponent } from './test-lit.component';

describe('TestLitComponent', () => {
  let component: TestLitComponent;
  let fixture: ComponentFixture<TestLitComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TestLitComponent]
    });
    fixture = TestBed.createComponent(TestLitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
