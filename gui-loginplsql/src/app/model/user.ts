export class User {
  constructor(
    public id: string = "",
    public username: string = "",
    public password: string = "",
    public name: string = "",
    public surname: string = "",
    public email: string = "",
    public telephone: string = "",
    public role: string = "",
    public monthlyWorkdays?: string,
    public vacationDays?: string,
    public sickLeaveDays?: string,
    public workdays?: string,
    public status?: string
  ) {}
}
