import {Injectable} from '@angular/core';
import {Http, Response, RequestOptions, Headers, URLSearchParams, RequestOptionsArgs} from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import { Console } from '@angular/core/src/console';
import {Subject} from 'rxjs/Subject';
import {Observable} from 'rxjs/Rx';

@Injectable()
export class TradeService {
  headers: Headers;
  options: RequestOptions;
  baseUrl:string = 'http://localhost:8080/files';
  fileDataUrl: string = this.baseUrl+'/duplicate';
  
  trades;
  private fileDetail = new Subject<any>();
  
  private tradeFilters = new Subject<any>();

  private errorLogs = new Subject<any>();

  constructor(private http:Http){
   // this.headers = new Headers({ 'Content-Type': 'application/json', 
    //'Accept': 'q=0.8;application/json;q=0.9' });
  //  this.options = new RequestOptions({headers: this.headers });
  }

  getFiles(){
    return this.http.get(this.fileDataUrl, this.options)
    .map((res:Response) => res.json());
  }

  setViewFiles(files){
    this.fileDetail.next(files);
  }

  getViewFiles(){
    return this.fileDetail.asObservable();
  }

  loadFile(fileName){
    var data = encodeURI(fileName);
    return this.http.get(this.baseUrl+'/loadfile?file='+data, this.options)
    .map((res:Response) => res);
  }

  deleteFile(fileName){
    var data = encodeURI(fileName);
    return this.http.get(this.baseUrl+'/removefile?file='+data, this.options)
    .map((res:Response) => res);
  }

  handleError(errorMessage){
    this.errorLogs.next(errorMessage);
  }
  getErrorLog(){
    return this.errorLogs.asObservable();
  }
}
