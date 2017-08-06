package mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * 消息消费者
 * Created by Administrator on 2017/8/6.
 */
@Service
public class ConsumerService {
    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     * 接受消息
     * @param destination
     * @return
     */
    public TextMessage receive(Destination destination){
        TextMessage textMessage =null;
        try {
            textMessage = (TextMessage) jmsTemplate.receive(destination);
            System.out.println("从队列" + destination.toString() + "收到了消息：\t"
                    + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return textMessage;
    }

}
