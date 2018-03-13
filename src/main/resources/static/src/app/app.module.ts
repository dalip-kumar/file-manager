import { BrowserModule } from '@angular/platform-browser';
import { NgModule, ErrorHandler } from '@angular/core';
//import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { AppComponent } from './component/app.component';
import {TradesTableComponent} from './component/files/app.tradestable.component';
import {TradeDetailComponent} from './component/trade-detail/app.tradedetail.component';

import { ReactiveFormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import {TradeService} from './service/app.files.service';
import {GlobalErrorHandler} from './service/app.error-handler';
import { ErrorHandlerComponent } from './component/error/app.error.component';


@NgModule({
  declarations: [
    AppComponent,
    TradesTableComponent,
    ErrorHandlerComponent,
    TradeDetailComponent
  ],
  imports: [
    BrowserModule,
  //  FormsModule,
    HttpModule,
    ReactiveFormsModule,
    ModalModule.forRoot()
    
  ],
  providers: [
    TradeService,
    {
      provide: ErrorHandler, 
      useClass: GlobalErrorHandler
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
