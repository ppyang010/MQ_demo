package mvc.web;

import mvc.service.ConsumerService;
import mvc.service.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.TextMessage;

/**
 * Created by Administrator on 2017/8/6.
 */
@RequestMapping("/mq/")
@Controller
public class MessageController {
    private Logger logger = LoggerFactory.getLogger(MessageController.class);
    @Resource(name = "demoQueueDestination")
    private Destination demoQueueDestination;

    @Resource(name = "demoTopicDestination")
    private Destination demoTopicDestination;


    //队列消息生产者
    @Resource(name = "producerService")
    private ProducerService producer;

    //队列消息消费者
    @Resource(name = "consumerService")
    private ConsumerService consumer;

    @RequestMapping("/sendMsg")
    public void send(String msg){
        logger.info(Thread.currentThread().getName()+"------------send to jms Start");
        producer.sendMsg(demoTopicDestination,msg);
        logger.info(Thread.currentThread().getName()+"------------send to jms End");
    }


    @RequestMapping(value= "/receiveMsg",method = RequestMethod.GET)
    @ResponseBody
    public Object receive(){
        logger.info(Thread.currentThread().getName()+"------------receive from jms Start");
        TextMessage tm = consumer.receive(demoQueueDestination);
        logger.info(Thread.currentThread().getName()+"------------receive from jms End");
        return tm;
    }
}
