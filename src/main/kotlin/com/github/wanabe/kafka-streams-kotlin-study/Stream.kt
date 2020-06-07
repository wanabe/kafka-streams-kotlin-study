package com.github.wanabe.kafka_streams_kotlin_study

import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.KStream
import java.util.Properties

class Stream<K, V>(brokers: String, var topic: String, keyType: Serde<K>, valueType: Serde<V>, f: (KStream<K, V>) -> Unit) {
    var props = Properties()
    var streamsBuilder = StreamsBuilder()
    var kafkaStreams: KafkaStreams
    init {
        props["bootstrap.servers"] = brokers
        props["application.id"] = "kafka-streams-sandbox-$topic"
        App.logger.debug("kafka-streams-sandbox-$topic $streamsBuilder")
        val firstStream: KStream<K, V> = streamsBuilder
            .stream<K, V>(topic, Consumed.with(keyType, valueType))
        f(firstStream)
        val topology = streamsBuilder.build()
        kafkaStreams = KafkaStreams(topology, props)
    }
    fun start(f: () -> Unit) {
        App.logger.debug("start $topic")
        kafkaStreams.start()
        f()
        kafkaStreams.close()
        App.logger.debug("end $topic")
    }
}
