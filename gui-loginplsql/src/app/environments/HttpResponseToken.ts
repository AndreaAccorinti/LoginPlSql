export class HttpResponseToken {
  constructor(
    public token: string,
    public response: string,
    public user: UserSession
  ) {}
}

export interface UserSession {
  username: string,
  password: string,
  name: string,
  surname: string,
  email: string,
  telephone: string
}
