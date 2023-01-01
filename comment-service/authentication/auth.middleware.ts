import { NextFunction, RequestHandler, Response, Request } from "express";
import { AuthRequest } from "@auth/auth.type";
import * as kratosService from "../kratos/kratos.service";

/**
 * Auth middleware will get bearer token and check it by "whoAmI" Kratos API
 * Then, set auth session is kratos auth session
 * User can get user id, session id,... by auth session
 */
const authMiddleware = (
  req: AuthRequest,
  res: Response,
  next: NextFunction
) => {
  const authToken = req.headers.authorization?.replace("Bearer ", "");
  // get token from kratos
  // return 401 when request has error
  kratosService
    .whoAmI(authToken)
    .then((v) => {
      if (v.status == 200) {
        req.authSession = v.data;
        next();
      } else {
        res.sendStatus(401);
      }
    })
    .catch((e) => res.sendStatus(401));
};

export default authMiddleware;
