import { Router, Response } from "express";
import * as commentService from "@comment/comment.service";
import logger from "@libs/logger";
import { AuthRequest } from "comment-service/authentication/auth.type";

const router = Router({ mergeParams: true });

type GetCommentRequest = AuthRequest & {
  query: {
    objectId: string;
    page?: number | undefined;
    pageSize?: number | undefined;
  };
};

type CommentRequest = AuthRequest & {
  body: {
    objectId: string;
    content: string;
  };
};

router.get("/comment", async (req: GetCommentRequest, res: Response) => {
  try {
    const { objectId, page, pageSize } = req.query;
    logger.info(`Get comment for object ${objectId}`);
    res.json(await commentService.get(objectId, page, pageSize as number));
  } catch (error: any) {
    logger.error(error);
    res.sendStatus(500);
  }
});

router.post("/comment", async (req: CommentRequest, res: Response) => {
  try {
    const userId = req.authSession.identity.id;
    const { objectId, content } = req.body;
    logger.info(`User ${userId} comment object ${req.body.objectId}`);
    res.json(await commentService.comment(objectId, userId, content));
  } catch (error: any) {
    logger.error(error);
    res.sendStatus(500);
  }
});

export default router;
