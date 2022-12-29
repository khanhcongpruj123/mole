import "module-alias/register";

import express, { Request, Response } from "express";
import compression from "compression";
import helmet from "helmet";
import multer from "multer";
import { PrismaClient } from "@prisma/client";
import logger from "@libs/logger"

// create express app
const app = express();

// connect to database
new PrismaClient().$connect().then(() => {
  logger.info("Database connected!");
});

// setup body parser
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(compression());
app.use(helmet());
app.use(multer().any());

app.get("/", (req: Request, res: Response) => {
  res.json({message: "This is comment service"})
})

// start server
export default app;