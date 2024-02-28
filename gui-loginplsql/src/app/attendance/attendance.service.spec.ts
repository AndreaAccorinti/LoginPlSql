import {AttendanceService} from "./attendance.service";
import {TestBed} from "@angular/core/testing";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {enviroments, mock_attandance} from "../environments/environments";

describe('AttendanceService', () => {
	let attendanceService: AttendanceService,
      httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AttendanceService]
    });
    attendanceService = TestBed.inject(AttendanceService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

	it('sys attendence list', () => {
    const testData = {
      ID: 2066851825,
      DATE_ATTENDANCE: "2024-02-01 01:00:00.000000",
      ID_USER: 1097361026,
      DESCRIPTION_: "corso oracle",
      MORNING_I: "2024-02-01 09:00:00.000000",
      MORNING_E: "2024-02-01 13:00:00.000000",
      AFTERNOON_I: "2024-02-01 14:00:00.000000",
      AFTERNOON_E: "2024-02-01 18:00:00.000000",
      REIMBURSEMENT: 20
    };

    localStorage.setItem('access_token', 'test');

    attendanceService.getAttendanceForSys().subscribe( response => {
      expect(response).toHaveSize(10);

    });
    const req = httpTestingController.expectOne(enviroments.urlApi+'attendance_list_systmp');
    expect(req.request.method).toEqual('POST');
    req.flush(mock_attandance);
	});
});
