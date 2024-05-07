import { format, subDays } from "date-fns";
import { Attendance, CalendarEventForRange } from "../model/model";
import * as moment from "moment";
import { CalendarEvent } from "angular-calendar";
import { colors } from "../environments/environments";


export function raggruppaGiorniDescizione(attendances: Attendance[]): CalendarEventForRange[] {
    const event: CalendarEventForRange[] = [];
    let eventoCorrente: CalendarEventForRange | undefined;
    const dateFormat = 'YYYY-MM-DDTHH:mm:ss';
    for (const attendance of attendances) {
      const oneDayBefore = moment(attendance.data).subtract(1, 'days');
      if (!eventoCorrente) {
        eventoCorrente = {
          id: attendance.id,  
          start: new Date(attendance.data),
          start_m: timestampToTime(attendance.inizioMattina),
          end_m: timestampToTime(attendance.fineMattina),
          start_p: timestampToTime(attendance.inizioPomeriggio),
          end_p: timestampToTime(attendance.finePomeriggio),
          title: attendance.descrizione,
        };
      } else if (
        eventoCorrente.title === attendance.descrizione &&
        eventoCorrente.start_m === timestampToTime(attendance.inizioMattina) && 
        eventoCorrente.end_m === timestampToTime(attendance.fineMattina) && 
        eventoCorrente.start_p === timestampToTime(attendance.inizioPomeriggio) && 
        eventoCorrente.end_p === timestampToTime(attendance.finePomeriggio) &&
        ( moment(eventoCorrente.start).format('YYYY-MM-DD') === oneDayBefore.format('YYYY-MM-DD') ) ||
        ( eventoCorrente.end && moment(eventoCorrente.end).format('YYYY-MM-DD') ===  oneDayBefore.format('YYYY-MM-DD') )
      ) {
        eventoCorrente.end = new Date(attendance.data); // Aggiorna la data di fine dell'evento
      } else {
        if (!!eventoCorrente.end)
            eventoCorrente.range = { from: eventoCorrente.start , to: eventoCorrente.end };
        else 
            eventoCorrente.range = { from: eventoCorrente.start , to: eventoCorrente.start };
        setColor(eventoCorrente);
        event.push(eventoCorrente);
        eventoCorrente = {
            id: attendance.id,  
            start: new Date(attendance.data),
            start_m: timestampToTime(attendance.inizioMattina),
            end_m: timestampToTime(attendance.fineMattina),
            start_p: timestampToTime(attendance.inizioPomeriggio),
            end_p: timestampToTime(attendance.finePomeriggio),
            title: attendance.descrizione,
        };
      }
    }
  
    if (eventoCorrente) {
        if (!!eventoCorrente.end)
            eventoCorrente.range = { from: eventoCorrente.start , to: eventoCorrente.end };
        else 
            eventoCorrente.range = { from: eventoCorrente.start , to: eventoCorrente.start };
      setColor(eventoCorrente);
      event.push(eventoCorrente);
    }
  
    return event;
  }


  export function timestampToTime(timestamp: string): string {
    return format(new Date(timestamp), 'HH:mm');
  }


  export function setColor(event: CalendarEvent) {
    switch(event.title) {
      case 'Lavoro': 
            event.color = colors['green'];
            break;
      case 'Ferie' : 
            event.color = colors['red'];
            break;
      case 'Permesso (Rol)' : 
            event.color = colors['blue'];
            break;
      case  'Malattia' : 
            event.color = colors['yellow'];
            break;
      case 'Assenza Ingiustificata' : 
            event.color = colors['citrine'];
            break;
      default: 
            event.color = colors['nyanza'];

    } 
  }