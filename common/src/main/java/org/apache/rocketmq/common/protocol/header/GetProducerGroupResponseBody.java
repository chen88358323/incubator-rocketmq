package org.apache.rocketmq.common.protocol.header;


import org.apache.rocketmq.remoting.protocol.RemotingSerializable;

import java.util.Set;

/**
 * Created by chicheng on 2017/3/7.
 */
public class GetProducerGroupResponseBody extends RemotingSerializable {
    private Set<String> consumerIdList;

    public Set<String> getConsumerIdList() {
        return consumerIdList;
    }

    public void setConsumerIdList(Set<String> consumerIdList) {
        this.consumerIdList = consumerIdList;
    }
}