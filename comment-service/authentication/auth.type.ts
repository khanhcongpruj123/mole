import { Request } from "express";

export type AuthRequest = Request & {
  authSession: any
}