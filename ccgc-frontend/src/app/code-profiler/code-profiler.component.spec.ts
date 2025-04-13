import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CodeProfilerComponent } from './code-profiler.component';

describe('CodeProfilerComponent', () => {
  let component: CodeProfilerComponent;
  let fixture: ComponentFixture<CodeProfilerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CodeProfilerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CodeProfilerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
