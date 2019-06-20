import { Injectable } from '@angular/core';
import { combineLatest, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { getServerUrl } from 'app/app.constants';
import { map } from 'rxjs/internal/operators';

@Injectable({ providedIn: 'root' })
export class LightningService {
    constructor(private http: HttpClient) {}

    public getInfo(): Observable<any> {
        return combineLatest(
            this.http.get(getServerUrl() + 'api/lnd'),
            this.http.get(getServerUrl() + 'api/lnd/permissions'),
            this.http.get(getServerUrl() + 'api/lnd/channels'),
            this.http.get(getServerUrl() + 'api/bitcoin'),
            this.http.get(getServerUrl() + 'api/bitcoin/price/CHF')
        ).pipe(
            map((result: any[]) => ({
                lndInfo: result[0],
                macaroons: result[1],
                lndChannels: result[2].channels,
                bitcoinInfo: result[3],
                bitcoinPrice: result[4]
            }))
        );
    }

    public getNodeInfo(nodeId): Observable<any> {
        return this.http.get(getServerUrl() + 'api/lnd/nodeinfo/' + nodeId);
    }
}
