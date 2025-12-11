import { FormBuilder } from "@angular/forms";

import {PropertySearchOrder} from '@models/bw/co/roguesystems/comm/property-search-order';

export class SearchObject<T> {
    criteria?: T;
    
    paged?: boolean = false;
    
    pageNumber?: number;
    
    pageSize?: number;
    
    sortings?: Array<PropertySearchOrder>;
    
    constructor() {
    }
}
