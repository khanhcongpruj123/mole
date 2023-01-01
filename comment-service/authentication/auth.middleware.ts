import { NextFunction, RequestHandler, Response, Request } from "express";
import { AuthRequest } from "@auth/auth.type";
import * as kratosService from "../kratos/kratos.service";

/**
 * Auth middleware will get bearer token and check it by "whoAmI" Kratos API
 * Then, set auth session is kratos auth session
 * User can get user id, session id,... by auth session
 */
const authMiddleware = async (
  req: AuthRequest,
  res: Response,
  next: NextFunction
) => {
  try {
    const authToken = req.headers.authorization?.replace("Bearer ", "");
    var authSession = await (await kratosService.whoAmI(authToken)).data
    req.authSession = authSession;
    next();
  } catch (error: any) {
    res.send(error);
  }
};

export default authMiddleware;
