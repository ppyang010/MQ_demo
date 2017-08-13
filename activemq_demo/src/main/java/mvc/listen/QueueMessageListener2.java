package mvc.listen;

import mvc.dao.SpikeDao;
import mvc.model.Spike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by Administrator on 2017/8/13.
 */
@Component("queueMessageListener2")
public class QueueMessageListener2 implements SessionAwareMessageListener {
    @Autowired
    SpikeDao spikeDao;
    @Override
    public void onMessage(Message message, Session session) throws JMSException {
        TextMessage tm = (TextMessage) message;
        try {
            System.out.println("QueueMessageListener监听到了文本消息：\t"
                    + tm.getText());
            //do something ...
            Spike spike=new Spike();
            spike.setNum(1);
            spike.setRemarks(tm.getText());
            spikeDao.addSpike(spike);
            session.recover();
//            //手动签收
//            tm.acknowledge();
//            System.out.println("手动签收成功");
        } catch (JMSException e) {
            //消息确认失败 重发消息
            session.recover();
            e.printStackTrace();
        }
    }
}
