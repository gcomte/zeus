import { Component, OnInit } from '@angular/core';
import { LightningService } from 'app/admin/lightning/lightning.service';
import { HttpErrorResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';
import * as _ from 'lodash';
import { MacaroonClass } from './MacaroonClass';

@Component({
    selector: 'jhi-lightning',
    templateUrl: './lightning.component.html'
})
export class JhiLightningComponent implements OnInit {
    info: any = null;
    channels = [];
    activeChannels = [];
    macaroons = [];
    macaroonClassAdmin = MacaroonClass.ADMIN;

    constructor(private jhiAlertService: JhiAlertService, private lightningService: LightningService) {}

    ngOnInit() {
        this.refresh();
    }

    refresh() {
        this.lightningService.getInfo().subscribe(
            res => {
                this.info = res;
                this.channels = this.info.lndChannels;
                this.activeChannels = this.channels.filter(c => c.active);
                this.macaroons = this.info.macaroons;

                this.channels.forEach(c => {
                    this.lightningService.getNodeInfo(c.remote_pubkey).subscribe(info => (c.remote = info));
                });
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    rebalanceChannel() {
        console.log('rebalanceChannel!');

        this.refresh();
    }

    settleChannel() {
        console.log('settle!');

        this.refresh();
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    capitalize(key) {
        return _.startCase(key);
    }
}
