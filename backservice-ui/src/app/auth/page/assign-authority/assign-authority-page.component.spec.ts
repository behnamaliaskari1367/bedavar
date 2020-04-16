import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignAuthorityPageComponent } from './assign-authority-page.component';

describe('AssignAuthorityPageComponent', () => {
  let component: AssignAuthorityPageComponent;
  let fixture: ComponentFixture<AssignAuthorityPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignAuthorityPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignAuthorityPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
