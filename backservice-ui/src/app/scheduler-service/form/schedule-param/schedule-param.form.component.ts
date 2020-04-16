import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ScheduleParamFa} from "../../../model/lang.fa";
import {PageType, ScheduleParamModel} from "../../../model/model";
import {MyErrorStateMatcher} from "../../../model/error-state-matcher";

/**
 * @author Abbas Alishirvani
 */
@Component({
  selector: 'bs-schedule-param-form',
  templateUrl: 'schedule-param.form.component.html',
  styleUrls: ['schedule-param.form.component.css']
})
export class ScheduleParamFormComponent implements OnInit {


  formGroup: FormGroup;
  langFa = new ScheduleParamFa();
  matcher = new MyErrorStateMatcher();

  pageType = PageType;
  @Output() submitted: EventEmitter<ScheduleParamModel>;
  @Input() showSubmitButtons: boolean = true;
  @Input() showCancelButtons: boolean = true;
  @Input() showSearchButtons: boolean = false;
  @Input() showResetButtons: boolean = false;
  @Input() type: PageType;

  @Input() set patchValue(value: ScheduleParamModel) {
    if (value && this.formGroup) {
      this.formGroup.patchValue(value);
    }
  }

  constructor(private fb: FormBuilder) {
    this.submitted = new EventEmitter();
  }

  private initForm() {
    this.formGroup = this.fb.group({
      id: [null],
      version: [null],
      serviceName: [null, (this.type === PageType.SEARCH) ? null : Validators.required],
      cron: [null, (this.type === PageType.SEARCH) ? null : Validators.required],
      url: [null, (this.type === PageType.SEARCH) ? null : Validators.required],
      actionName: [null, (this.type === PageType.SEARCH) ? null : Validators.required],
      enabled: [{value: true, disabled: this.type === PageType.VIEW}, Validators.required]
    });
  }

  ngOnInit(): void {
    this.initForm();
  }

  onFormSubmit(value: any) {
    this.submitted.emit(value);
  }

  cancelForm($event: MouseEvent) {
    $event.preventDefault();
    this.submitted.emit(null);
  }

  searchClicked($event: MouseEvent) {
    $event.preventDefault();
    this.submitted.emit(this.formGroup.value);
  }

  resetClicked($event: MouseEvent) {
    $event.preventDefault();
    this.formGroup.reset();
  }
}
