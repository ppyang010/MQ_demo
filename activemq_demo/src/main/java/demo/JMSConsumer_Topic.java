package demo;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by s on 2017/8/1.
 */
public class JMSConsumer_Topic {
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;//默认连接用户名
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;//默认连接密码
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;//默认连接地址
    private static final String url1="tcp://192.168.101.172:61616";
    private static final String url2="tcp://192.168.10.211:61616";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory;//连接工厂
        Connection connection = null;//连接

        Session session;//会话 接受或者发送消息的线程
        Destination destination;//消息的目的地

        MessageConsumer messageConsumer;//消息的消费者

        //实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(JMSConsumer_Topic.USERNAME, JMSConsumer_Topic.PASSWORD, url1);

        try {
            //通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            //持久化订阅需要设置
            connection.setClientID("longgg");
            //启动连接
            connection.start();
            //创建session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建一个连接HelloWorld的消息队列
            //destination = session.createQueue("hella");
            destination=session.createTopic("topic1");
            //创建消息消费者
            messageConsumer = session.createConsumer(destination);
            messageConsumer=session.createDurableSubscriber((Topic) destination,"longgg");

            while (true) {
                System.out.println("尝试获取消息：");
                //获取消息 等待100s  100s后获取null
                TextMessage textMessage = (TextMessage) messageConsumer.receive(100000);
                if(textMessage != null){
                    System.out.println("收到的消息:" + textMessage.getText());
                }else {
                    System.out.println("结束消息");
                    break;
                }
            }

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
