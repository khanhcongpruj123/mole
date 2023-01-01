import { Kafka } from "kafkajs";

const kafka = new Kafka({
  clientId: "mole",
  brokers: ["localhost:9092"],
});

export default kafka;