import { PrismaClient } from "@prisma/client";
import {v4 as uuidv4} from 'uuid';

const prisma = new PrismaClient();

export const get = async (
  objectId: string,
  page?: number | undefined,
  pageSize?: number | undefined
) => {
  return prisma.comment.findMany({
    skip: page ? page * pageSize : undefined,
    take: pageSize,
    where: {
      objectId: objectId,
    },
    orderBy: {
      createdAt: "desc",
    },
  });
};

export const comment = async (objectId: string, userId: string, content: string) => {
  return prisma.comment.create({
    data: {
      id: `comment-${uuidv4()}`,
      content: content,
      userId: userId,
      objectId: objectId,
    },
  });
};
