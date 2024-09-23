import {ComponentFixture, TestBed} from '@angular/core/testing';

import {JumbotronContentComponent} from './jumbotron-content.component';

describe('JumbotronContentComponent', () => {
  let component: JumbotronContentComponent;
  let fixture: ComponentFixture<JumbotronContentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JumbotronContentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JumbotronContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
