import { NextFunction, RequestHandler, Response, Request } from "express";
import { AuthRequest } from "@auth/auth.type";
import * as kratosService from "../kratos/kratos.service";

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
