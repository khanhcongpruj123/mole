import { Kafka } from "kafkajs";

const kafka = new Kafka({
  clientId: "mole",
  brokers: ["localhost:29092"],
});

export default kafka;