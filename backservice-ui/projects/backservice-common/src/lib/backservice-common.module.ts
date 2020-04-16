import {ModuleWithProviders, NgModule} from '@angular/core';
import {MatProgressSpinnerModule, MatSnackBarModule} from "@angular/material";
import {OverlayModule} from "@angular/cdk/overlay";
import {BackServiceMessageService} from "./service/backservice-message.service";


@NgModule({
  declarations: [],
  imports: [
    MatSnackBarModule,
    OverlayModule,
    MatProgressSpinnerModule
  ],
  providers: [
    BackServiceMessageService
  ],
  exports: []
})
export class BackServiceCommonModule {
  public static forRoot(environment): ModuleWithProviders<BackServiceCommonModule> {
    return {
      ngModule: BackServiceCommonModule,
      providers: [{provide: 'env', useValue: environment}]
    };
  }
}
