import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Location} from '@angular/common';
import {AuthorityModel, PageType, UserAuthorityModel, UserModel} from "../../../model/model";
import {AuthorityService} from "../../service/authority.service";
import {AuthorityFa} from "../../../model/lang.fa";
import {map} from "rxjs/operators";
import {forkJoin, Observable} from "rxjs";
import {MatListOption} from "@angular/material";
import {UserService} from "../../service/user.service";
import {BackServiceMessageService} from "@fartak/backservice-common";

@Component({
  selector: 'bs-page-assign-authority',
  templateUrl: './assign-authority-page.component.html',
  styleUrls: ['./assign-authority-page.component.css']
})
export class AssignAuthorityPageComponent implements OnInit {

  private type: PageType;
  pageType = PageType;
  private userId: number;
  langFa: AuthorityFa;

  private authorityMap: Map<number, AuthorityModel> = new Map<number, AuthorityModel>();
  unAssignedMap: Map<number, AuthorityModel> = new Map<number, AuthorityModel>();
  assignedMap: Map<number, AuthorityModel> = new Map<number, AuthorityModel>();
  isDirty = false;
  userFormValue: UserModel;

  constructor(private route: ActivatedRoute,
              private location: Location,
              private messageService: BackServiceMessageService,
              private authorityService: AuthorityService,
              private service: AuthorityService,
              private userService: UserService) {
    this.langFa = new AuthorityFa();
    this.route.params.subscribe(params => {
      this.userId = params['id'];
      this.type = params['type'];
    });
  }

  ngOnInit() {
    this.loadData();
  }

  private loadData() {
    let all: Observable<AuthorityModel[]> = this.service
      .userActive()
      .pipe(
        map(value => {
          if (value && value.response) {
            return value.response;
          }
          return [];
        }));
    let assigned: Observable<UserAuthorityModel[]> = this.userService
      .assignedAuthorities(this.userId.toString())
      .pipe(
        map(value => {
          if (value && value.response) {
            this.userFormValue = value.response;

            if (value.response.userAuthorities) {
              return value.response.userAuthorities;
            }
          }
          return [];
        }));

    forkJoin({
      all: all,
      assigned: assigned
    }).subscribe(value => {
      this.assignedMap.clear();
      this.authorityMap.clear();
      this.unAssignedMap.clear();

      for (const e of value.assigned) {
        if (e.id && e.authority && e.authority.id) this.assignedMap.set(e.authority.id, e.authority);
      }

      for (const e of value.all) {
        if (e.id) this.authorityMap.set(e.id, e);
        if (!this.assignedMap.has(e.id)) this.unAssignedMap.set(e.id, e);
      }
      this.isDirty = false;
    });
  }

  onAssignClicked($event: MouseEvent, selectedList: MatListOption[]) {
    $event.preventDefault();
    for (const selected of selectedList) {
      const key = selected.value;
      let model = this.authorityMap.get(key);
      this.unAssignedMap.delete(key);
      this.assignedMap.set(key, model);
    }
    this.isDirty = true;
  }

  onUnAssignClicked($event: MouseEvent, selectedList: MatListOption[]) {
    $event.preventDefault();
    for (const selected of selectedList) {
      const key = selected.value;
      let model = this.authorityMap.get(key);
      this.assignedMap.delete(key);
      this.unAssignedMap.set(key, model);
    }
    this.isDirty = true;
  }

  getListValues<K, V>(map: Map<K, V>) {
    return Array.from(map.values());
  }

  onExitButtonClicked($event: MouseEvent) {
    $event.preventDefault();
    this.location.back();
  }

  onSaveButtonClicked($event: MouseEvent) {
    $event.preventDefault();
    this.authorityService
      .assignAuthorities(this.userId.toString(), Array.from(this.assignedMap.values()))
      .subscribe(value => {
        this.messageService.add("Assigned", "OK");
        this.location.back();
      });
  }
}
