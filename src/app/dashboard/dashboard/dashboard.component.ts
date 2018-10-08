import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'qy-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  constructor() { }

  ngOnInit() {
    console.log("DashboardComponent ngOnInit");
  }

}
