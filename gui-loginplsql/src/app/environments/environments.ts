import { EventColor } from 'calendar-utils';
export const environments = {
  urlApi: 'http://localhost:8080/api/',
  //urlApi: 'http://13.39.23.43:8080/api/',
  mockAttandanceUrl: '../environments/mock-data.json'
}

export const sidebar_list = [
  {
    title: "Home",
    icon: "bi bi-house"
  },
  {
    title: "Attendance",
    icon: "bi bi-card-heading"
  },
  {
    title: "Anagrafica",
    icon: "bi bi-person"
  }
]

export const mock_attandance = [
  {
    ID: 2066851825,
    DATE_ATTENDANCE: "2024-02-01 01:00:00.000000",
    ID_USER: 1097361026,
    DESCRIPTION_: "corso oracle",
    MORNING_I: "2024-02-01 09:00:00.000000",
    MORNING_E: "2024-02-01 13:00:00.000000",
    AFTERNOON_I: "2024-02-01 14:00:00.000000",
    AFTERNOON_E: "2024-02-01 18:00:00.000000",
    REIMBURSEMENT: 20
  },
  {
    ID: 622727166,
    DATE_ATTENDANCE: "2024-02-02 01:00:00.000000",
    ID_USER: 1097361026,
    DESCRIPTION_: "corso oracle",
    MORNING_I: "2024-02-02 09:00:00.000000",
    MORNING_E: "2024-02-02 13:00:00.000000",
    AFTERNOON_I: "2024-02-02 14:00:00.000000",
    AFTERNOON_E: "2024-02-02 18:00:00.000000",
    REIMBURSEMENT: 20
  },
  {
    ID: 1177708808,
    DATE_ATTENDANCE: "2024-02-05 01:00:00.000000",
    ID_USER: 1097361026,
    DESCRIPTION_: "corso oracle",
    MORNING_I: "2024-02-05 09:00:00.000000",
    MORNING_E: "2024-02-05 13:00:00.000000",
    AFTERNOON_I: "2024-02-05 14:00:00.000000",
    AFTERNOON_E: "2024-02-05 18:00:00.000000",
    REIMBURSEMENT: 20
  },
  {
    ID: 225425180,
    DATE_ATTENDANCE: "2024-02-06 01:00:00.000000",
    ID_USER: 1097361026,
    DESCRIPTION_: "corso oracle",
    MORNING_I: "2024-02-06 09:00:00.000000",
    MORNING_E: "2024-02-06 13:00:00.000000",
    AFTERNOON_I: "2024-02-06 14:00:00.000000",
    AFTERNOON_E: "2024-02-06 18:00:00.000000",
    REIMBURSEMENT: 20
  },
  {
    ID: 380977335,
    DATE_ATTENDANCE: "2024-02-07 01:00:00.000000",
    ID_USER: 1097361026,
    DESCRIPTION_: "corso oracle",
    MORNING_I: "2024-02-07 09:00:00.000000",
    MORNING_E: "2024-02-07 13:00:00.000000",
    AFTERNOON_I: "2024-02-07 14:00:00.000000",
    AFTERNOON_E: "2024-02-07 18:00:00.000000",
    REIMBURSEMENT: 20
  },
  {
    ID: 211525203,
    DATE_ATTENDANCE: "2024-02-08 01:00:00.000000",
    ID_USER: 1097361026,
    DESCRIPTION_: "corso oracle",
    MORNING_I: "2024-02-08 09:00:00.000000",
    MORNING_E: "2024-02-08 13:00:00.000000",
    AFTERNOON_I: "2024-02-08 14:00:00.000000",
    AFTERNOON_E: "2024-02-08 18:00:00.000000",
    REIMBURSEMENT: 20
  },
  {
    ID: 1331140741,
    DATE_ATTENDANCE: "2024-02-09 01:00:00.000000",
    ID_USER: 1097361026,
    DESCRIPTION_: "corso oracle",
    MORNING_I: "2024-02-09 09:00:00.000000",
    MORNING_E: "2024-02-09 13:00:00.000000",
    AFTERNOON_I: "2024-02-09 14:00:00.000000",
    AFTERNOON_E: "2024-02-09 18:00:00.000000",
    REIMBURSEMENT: 20
  },
  {
    ID: 74696458,
    DATE_ATTENDANCE: "2024-02-12 01:00:00.000000",
    ID_USER: 1097361026,
    DESCRIPTION_: "corso oracle",
    MORNING_I: "2024-02-12 09:00:00.000000",
    MORNING_E: "2024-02-12 13:00:00.000000",
    AFTERNOON_I: "2024-02-12 14:00:00.000000",
    AFTERNOON_E: "2024-02-12 18:00:00.000000",
    REIMBURSEMENT: 20
  },
  {
    ID: 1226186188,
    DATE_ATTENDANCE: "2024-02-13 01:00:00.000000",
    ID_USER: 1097361026,
    DESCRIPTION_: "corso oracle",
    MORNING_I: "2024-02-13 09:00:00.000000",
    MORNING_E: "2024-02-13 13:00:00.000000",
    AFTERNOON_I: "2024-02-13 14:00:00.000000",
    AFTERNOON_E: "2024-02-13 18:00:00.000000",
    REIMBURSEMENT: 20
  },
  {
    ID: 905438068,
    DATE_ATTENDANCE: "2024-02-14 01:00:00.000000",
    ID_USER: 1097361026,
    DESCRIPTION_: "corso oracle",
    MORNING_I: "2024-02-14 09:00:00.000000",
    MORNING_E: "2024-02-14 13:00:00.000000",
    AFTERNOON_I: "2024-02-14 14:00:00.000000",
    AFTERNOON_E: "2024-02-14 18:00:00.000000",
    REIMBURSEMENT: 20
  }
];

export const colors: Record<string, EventColor> = {
  red: {
    primary: '#ad2121',
    secondary: '#FAE3E3',
  },
  blue: {
    primary: '#1e90ff',
    secondary: '#D1E8FF',
  },
  yellow: {
    primary: '#e3bc08',
    secondary: '#FDF1BA',
  },
  green: {
    primary: '#7CFC00',
    secondary: '#008000',
  },
  nyanza: {
    primary: '#ECFFDC',
    secondary: '#C1E1C1',
  },
  citrine: {
    primary: '#581845',
    secondary: '#C1E1C1',
  }
};


