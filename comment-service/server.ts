import http from "http";
import app from "./app";
import logger from "@libs/logger"

// create server
const server = http.createServer(app);
// start server
server.listen(3000, () => {
  logger.info("Server is running ion port 3000");
});