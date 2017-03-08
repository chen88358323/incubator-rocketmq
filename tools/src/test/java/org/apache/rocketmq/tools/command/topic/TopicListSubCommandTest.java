/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.rocketmq.tools.command.topic;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.srvutil.ServerUtil;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TopicListSubCommandTest {
    @Test
    public void testGetTopicList() {
        TopicListSubCommand cmd = new TopicListSubCommand();
        Options options = ServerUtil.buildCommandlineOptions(new Options());
        String[] subargs = new String[] {"-n 127.0.0.1:9876"};
        final CommandLine commandLine =
            ServerUtil.parseCmdLine("mqadmin " + cmd.commandName(), subargs, cmd.buildCommandlineOptions(options), new PosixParser());
//        assertThat(commandLine.getOptionValue('t').trim()).isEqualTo("unit-test");

        cmd.execute(commandLine, options, null);
    }


    //DefaultCluster
    @Test
    public void testExecute() {
        TopicListSubCommand cmd = new TopicListSubCommand();
        Options options = ServerUtil.buildCommandlineOptions(new Options());
//        String[] subargs = new String[] {"-c"};//,"-n 127.0.0.1:9876"
        String[] subargs = new String[] {"-p"};
        CommandLine commandLine =
                ServerUtil.parseCmdLine("mqadmin " + cmd.commandName(), subargs, cmd.buildCommandlineOptions(options), new PosixParser());
//        assertThat(commandLine.getOptionValue('t').trim()).isEqualTo("unit-test");
//        testProducer();
        cmd.execute(commandLine, options, null);
    }


    private void testProducer(){
        DefaultMQProducer producer = new DefaultMQProducer("pg");
        producer.setNamesrvAddr("127.0.0.1:9876");
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        int i=0;
        while(i<1000){
//        for (int i = 0; i < 1000; i++) {
            try {

                /*
                 * Create a message instance, specifying topic, tag and message body.
                 */
                Message msg = new Message("topic_test" /* Topic */,
                        "TagA" /* Tag */,
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );

                /*
                 * Call send message to deliver message to one of brokers.
                 */
                SendResult sendResult = producer.send(msg);

                System.out.println( sendResult);
                i++;
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }

        /*
         * Shut down once the producer instance is not longer in use.
         */
        producer.shutdown();
    }
}