import { CalendarEvent } from "angular-calendar";
import { User } from "../assets/user";

export interface Attendance {
    id?: number;
    data: string;
    username?: User;
    descrizione: string;
    inizioMattina: string;
    fineMattina: string;
    inizioPomeriggio: string;
    finePomeriggio: string;
    rimborsoSpese?: number;
}

export interface CalendarEventForRange<MetaType=any> extends CalendarEvent {
    start_m?: string;
    end_m?: string;
    start_p?: string;
    end_p?: string;
    range?: Range;
}

export interface Range {
    from: Date;
    to: Date;
}