import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MyLitComponent } from "../../assets/my-lit-component";

@Component({
  selector: 'app-test-lit-component',
  templateUrl: './test-lit.component.html',
  styleUrls: ['./test-lit.component.scss']
})
export class TestLitComponent implements OnInit {
  // @ViewChild('litContainer') litContainer!: ElementRef;

  ngOnInit(): void {
    const litElement = new MyLitComponent();
    //this.litContainer.nativeElement.appendChild(litElement);
  }
}
