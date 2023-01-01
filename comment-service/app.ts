import "module-alias/register";

import express, { Request, Response } from "express";
import compression from "compression";
import helmet from "helmet";
import multer from "multer";
import { PrismaClient } from "@prisma/client";
import logger from "@libs/logger";
import commentRouterV1 from "@comment/comment.router.v1";
import authMiddleware from "@auth/auth.middleware";
import kafkaClient from "@kafka";

// create express app
const app = express();

// connect to database
new PrismaClient().$connect().then(() => {
  logger.info("Database connected!");
});

// connect to kafka
const kafka = kafkaClient;
const producer = kafka.producer();
producer.connect().then(() => {
  var message = `Kafka connect at ${new Date()}`;
  logger.info(message);
  producer.send({
    topic: "comment-service-connection",
    messages: [
      {
        value: message,
      },
    ],
  });
});

// setup body parser
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(compression());
app.use(helmet());
app.use(multer().any());

app.use(authMiddleware);

app.use("/api/v1", commentRouterV1);

// start server
export default app;
