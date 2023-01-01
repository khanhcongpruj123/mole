import { PrismaClient } from "@prisma/client";
import { v4 as uuidv4 } from "uuid";
import kafka from "@kafka";
import Logger from "@libs/logger";

const prisma = new PrismaClient();

export const get = async (
  objectId: string,
  page?: number | undefined,
  pageSize?: number | undefined
) => {
  return prisma.comment.findMany({
    skip: page && pageSize ? page * pageSize : undefined,
    take: pageSize ? Number(pageSize) : undefined,
    where: {
      objectId: objectId,
    },
    orderBy: {
      createdAt: "desc",
    },
  });
};

export const comment = async (
  objectId: string,
  userId: string,
  content: string
) => {
  Logger.info("Send create comment request to queue!");
  var producer = kafka.producer();
  producer.connect().then(() => {
    producer.send({
      topic: "create-comment",
      messages: [
        {
          key: "comment-key",
          value: "Create comment message",
        },
      ],
    });
  });
  return {
    id: `comment-${uuidv4()}`,
    content: content,
    userId: userId,
    objectId: objectId,
  };
};
