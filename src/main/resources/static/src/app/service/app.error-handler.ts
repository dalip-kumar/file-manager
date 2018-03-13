import {ErrorHandler, Injectable, Injector} from '@angular/core';
import { TradeService } from 'app/service/app.files.service';

@Injectable()
export class GlobalErrorHandler implements ErrorHandler{

    constructor(private injector:Injector){}

    handleError(error){
        debugger;
        console.log('ERROR -------');
        const tradeService = this.injector.get(TradeService);
        const message = error.message ? error.message : error.toString();
        tradeService.handleError(message);
        throw error;
    }
}