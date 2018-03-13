


import { Component,OnInit,OnDestroy,ViewChild } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { TradeService } from 'app/service/app.files.service';
import { ModalDirective } from 'ngx-bootstrap/modal';
@Component({
  selector: 'app-error-component',
  templateUrl: './app.error.component.html',
  styleUrls: ['./app.error.component.css']
})


export class ErrorHandlerComponent  implements OnInit, OnDestroy{

    errorMessageSub: Subscription;
    errorMessage;
    
  @ViewChild('errorModal') 
  tradeRecordModal: ModalDirective;

    constructor(private tradeService:TradeService){}
    
    ngOnInit(){
        this.errorMessageSub = this.tradeService.getErrorLog().subscribe(data => {
            this.errorMessage = data;
            this.tradeRecordModal.show();
          });
    }
    
    cancel(){
        this.tradeRecordModal.hide();
    }

    ngOnDestroy(){
        if(this.errorMessageSub){
            this.errorMessageSub.unsubscribe();
          }
    }
}