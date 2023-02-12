import {Role} from "./role";

export class Auth {
  constructor(readonly userId: number, readonly token: string, readonly role: Role) {
  }
}
