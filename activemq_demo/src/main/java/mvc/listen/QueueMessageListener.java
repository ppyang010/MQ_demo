package mvc.listen;

import mvc.dao.SpikeDao;
import mvc.model.Spike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 监听消息队列
 * Created by Administrator on 2017/8/6.
 */
@Component("queueMessageListener")
public class QueueMessageListener implements MessageListener {

    @Autowired
    SpikeDao spikeDao;
    @Override
    public void onMessage(Message message) {
        TextMessage tm = (TextMessage) message;
        try {
            System.out.println("QueueMessageListener监听到了文本消息：\t"
                    + tm.getText());
            //do something ...
            Spike spike=new Spike();
            spike.setNum(1);
            spike.setRemarks(tm.getText());
            spikeDao.addSpike(spike);
            //手动签收
//            tm.acknowledge();
            System.out.println("手动签收成功");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
