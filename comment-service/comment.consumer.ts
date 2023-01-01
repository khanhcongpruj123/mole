import kafka from "@kafka";
import Logger from "@libs/logger";

/**
 * This is consumers for comment bussines logic
 * This consumer subcribe topics: create-commnent (create comment)
 */
(async () => {
  const consumer = kafka.consumer({
    groupId: "comment-group"
  });
  
  await consumer.connect();
  Logger.info("Kafka connected!")
  
  await consumer.subscribe({
    topics: ["create-comment"]
  })

  await consumer.run({
    eachMessage: async ({ topic, partition, message, heartbeat, pause }) => {
      Logger.info(`Consume message from ${topic} ${partition}: ${message.value}`);
    },
  });
  
})();