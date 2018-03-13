import { Component, TemplateRef, OnInit, OnDestroy  } from '@angular/core';
import {TradeService} from 'app/service/app.files.service';
import { BsModalService,ModalDirective } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';
@Component({
  selector: 'app-metallica-trades',
  templateUrl: './app.tradestable.component.html',
  styleUrls: ['./app.tradestable.component.css'],
  providers: []
})
export class TradesTableComponent  implements OnInit{

  modalRef: BsModalRef;
  allFiles;
  
  constructor(private tradeService:TradeService, private modalService: BsModalService){
    tradeService.getFiles().subscribe(data => {
      this.allFiles = data
      
      }
    );
    
  }

  onView(files){
    
    this.tradeService.setViewFiles(files);
  }
  ngOnInit(){
   
    
  }
 
   
}
