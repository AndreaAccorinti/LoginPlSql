import {
  Component,
  ChangeDetectionStrategy,
  ViewChild,
  TemplateRef,
  Inject,
  OnInit
} from '@angular/core';
import {
  startOfDay,
  endOfDay,
  addDays,
  isSameDay,
  isSameMonth,
  format,
} from 'date-fns';
import { Subject } from 'rxjs';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {
  CalendarEvent,
  CalendarEventAction,
  CalendarEventTimesChangedEvent,
  CalendarView,
} from 'angular-calendar';
import { Attendance, CalendarEventForRange } from '../model/model';
import { User } from '../assets/user';
import { AttendanceService } from '../attendance/attendance.service';
import { raggruppaGiorniDescizione,setColor } from '../assets/utils';
import { colors } from '../environments/environments';


@Component({
  selector: 'app-attendance-dialog',
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrls: ['./attendance-dialog.component.scss'],
  templateUrl: './attendance-dialog.component.html',
})
export class AttendanceDialogComponent implements OnInit {

  @ViewChild('modalContent', { static: true }) modalContent: TemplateRef<any> | null = null;

  view: CalendarView = CalendarView.Month;

  CalendarView = CalendarView;

  viewDate: Date = new Date();

  descEventList : string [] = ["Lavoro", "Ferie", "Permesso (Rol)", "Malattia", "Assenza Ingiustificata"];

  data: Attendance [] | undefined;

  modalData: {
    action: string;
    event: CalendarEvent;
  } | null = null;

  actions: CalendarEventAction[] = [
    {
      label: '<i class="fas fa-fw fa-pencil-alt"></i>',
      a11yLabel: 'Edit',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this.handleEvent('Edited', event);
      },
    },
    {
      label: '<i class="fas fa-fw fa-trash-alt"></i>',
      a11yLabel: 'Delete',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this.events = this.events.filter((iEvent) => iEvent !== event);
        this.handleEvent('Deleted', event);
      },
    },
  ];

  refresh = new Subject<void>();

  events: CalendarEventForRange[] = [];

  activeDayIsOpen: boolean = true;
  selectedRange: [Date, Date] | null = null;

  constructor(
    private modal: NgbModal,
    @Inject(NgbActiveModal) public activeModal: NgbActiveModal,
    private service: AttendanceService) {}

  ngOnInit(): void {
    if (!!this.data) {
      this.events = raggruppaGiorniDescizione(this.data);
      console.log(this.events);
    }
  }

  onDateChangeHandler(dateRange: any, event: any) {
    if(!!dateRange.from) {
      event.start = dateRange.from;
      event.end = dateRange.from;
    }
    if(!!dateRange.to) {
      event.end = dateRange.to;
    }
    this.refresh.next();
  }

  dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
    if (isSameMonth(date, this.viewDate)) {
      if (
        (isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) ||
        events.length === 0
      ) {
        this.activeDayIsOpen = false;
      } else {
        this.activeDayIsOpen = true;
      }
      this.viewDate = date;
    }
  }

  eventTimesChanged({
    event,
    newStart,
    newEnd,
  }: CalendarEventTimesChangedEvent): void {
    this.events = this.events.map((iEvent) => {
      if (iEvent === event) {
        return {
          ...event,
          start: newStart,
          end: newEnd,
        };
      }
      return iEvent;
    });
    this.handleEvent('Dropped or resized', event);
  }

  handleEvent(action: string, event: CalendarEvent): void {
    this.modalData = { event, action };
    this.modal.open(this.modalContent, { size: 'lg' });
  }

  addEvent(): void {
    this.events = [
      ...this.events,
      {
        title: 'Lavoro',
        start: startOfDay(new Date()),
        end: endOfDay(new Date()),
        range: {from: new Date() , to: new Date() },
        color: colors['green'],
        draggable: true,
        resizable: {
          beforeStart: true,
          afterEnd: true,
        },
        start_m: '09:00',
        end_m: '13:00',
        start_p: '14:00',
        end_p: '18:00'
      },
    ];
  }

  deleteEvent(eventToDelete: CalendarEvent) {
    this.events = this.events.filter((event) => event !== eventToDelete);
  }

  setView(view: CalendarView) {
    this.view = view;
  }

  closeOpenMonthViewDay() {
    this.activeDayIsOpen = false;
  }

  closeModal() {
    this.activeModal.close();
  }

  saveCalendar() {
      let attendances: Attendance[] = [];
      const jsonData = localStorage.getItem('user');
      let userData: User = new User();
      if (jsonData != null)
        userData = JSON.parse(jsonData);
      this.events.forEach( (event) => {
        if (event.end != null && event.end != undefined) {
          for(let date: Date = event.start; date <= event.end; date = addDays(date, 1)) {
            const attendance: Attendance =
              {
                data: format(date, "yyyy-MM-dd'T'HH:mm:ss.SSSX"),
                descrizione: event.title,
                username: userData,
                inizioMattina: this.getTimestampFromDateTime(date, event.start_m),
                fineMattina: this.getTimestampFromDateTime(date, event.end_m),
                inizioPomeriggio: this.getTimestampFromDateTime(date, event.start_p),
                finePomeriggio: this.getTimestampFromDateTime(date, event.end_p)
              };
            if (!!event.id) {
              attendance.id = +event.id;
            }
            attendances = [ ...attendances, attendance];
          }
        }
      });
      if(attendances.length) {
        this.service.addAttendances(attendances)?.subscribe( (result) => {
          this.closeModal();
        });
      }
  }

  getTimestampFromDateTime(date: Date, time: string|undefined): string {
    return format(date, "yyyy-MM-dd'T'") + time + ':00.000+01';

  }

  setColor(event: CalendarEvent) {
    setColor(event);
  }
}
