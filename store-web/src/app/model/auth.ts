import {Role} from "./role";

export class Auth {
  constructor(readonly id: number, readonly token: string, readonly role: Role) {
  }
}
