import { Component, OnInit, OnDestroy,TemplateRef,ViewChild   } from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {TradeService} from 'app/service/app.files.service';

import {Subscription} from "rxjs/Subscription";


import { DomSanitizer } from '@angular/platform-browser';
import { BsModalService,ModalDirective } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';


@Component({
  selector: 'app-file-detail',
  templateUrl: './app.tradedetail.component.html',
  styleUrls: ['./app.tradedetail.component.css'],
  providers: []
})
export class TradeDetailComponent  implements OnInit, OnDestroy{
  
  modalRef: BsModalRef;
  files;

  
  @ViewChild('tradeDataModal') 
  tradeRecordModal: ModalDirective;

  tradeRecordSub: Subscription;

  base64Image;


  constructor(private tradeService: TradeService,
                private modalService: BsModalService,
                private _sanitizer: DomSanitizer){
               
  }

  ngOnInit(){
  
    this.tradeRecordSub = this.tradeService.getViewFiles().subscribe(data => {
      
      this.files = data;
      this.base64Image = null;
      this.tradeRecordModal.show();
      
      
    });
   
   
  }
  
  onView(file){debugger;
    this.isPreviewable(file);
  }

  onDelete(file){
    this.tradeService.deleteFile(file).subscribe(data => {
      alert(data.text());
    });

  }
  isPreviewable(file){
    var fileExt = this.getFileExtn(file);
    var supportedFiles = ["JPG","JPEG","GIF","PNG","TIF","BMP"];


    if(supportedFiles.indexOf(fileExt) > -1 ){
      

      this.tradeService.loadFile(file).subscribe(data => {
        this.base64Image = this._sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' 
                 + data.text());

      });

    }

    return (supportedFiles.indexOf(fileExt) > -1 );
  }

  getFileExtn(fileName){
    var result =  fileName.substring(fileName.lastIndexOf('.')+1, fileName.length) || fileName;
    return result.toUpperCase();
  }
  cancel(){
    this.files = undefined;
    
    this.tradeRecordModal.hide();
    
  }

 
  ngOnDestroy() {
    
    if(this.tradeRecordSub){
      this.tradeRecordSub.unsubscribe();
    }

   
  }

  
 
}


