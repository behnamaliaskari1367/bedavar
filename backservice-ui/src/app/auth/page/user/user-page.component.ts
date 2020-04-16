import {Component, Input, OnInit} from '@angular/core';
import {PageType, UserModel} from "../../../model/model";
import {UserFa} from "../../../model/lang.fa";
import {Location} from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../../service/user.service";
import {BackServiceMessageService} from "@fartak/backservice-common";

@Component({
  selector: 'bs-page-user',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit {

  @Input() type: PageType;
  langFa: UserFa;
  private id: string;
  value: UserModel;

  constructor(private route: ActivatedRoute,
              private backServiceMessageService: BackServiceMessageService,
              private userService: UserService,
              private location: Location) {
    this.langFa = new UserFa();
    this.route.params.subscribe(params => {
      this.id = params['id'];
      this.type = params['type'];
    });
  }

  onFormSubmitted($event: UserModel) {
    if (!$event || !this.type) {
      this.location.back();
      return;
    }

    if (this.type === PageType.NEW) {
      this.userService
        .add($event)
        .subscribe(value => {
          this.backServiceMessageService.add('User Added', 'OK');
          this.location.back();
        });
    } else if (this.type === PageType.EDIT) {
      this.userService
        .edit($event)
        .subscribe(value => {
          this.backServiceMessageService.add('User Edited', 'OK');
          this.location.back();
        });
    }
  }

  ngOnInit(): void {
    if (this.type === PageType.EDIT) {
      this.userService
        .get(this.id)
        .subscribe(value => {
          this.value = value.response;
        });
    }
  }
}
