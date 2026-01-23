package bw.co.roguesystems.comm.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.rabbitmq")
public class RabbitProperties {

    private final String host;
    private final int port;
    private final String username;
    private final String password;
    // Email properties
    private final String emailHandler;
    private final String emailDispatchExchange;
    private final String emailDispatchQueue;
    private final String emailDispatchRoutingKey;
    private final String emailQueueExchange;
    private final String emailQueue;
    private final String emailQueueRoutingKey;
    // SMS properties
    private final String smsHandler;
    private final String smsDispatchExchange;
    private final String smsDispatchQueue;
    private final String smsDispatchRoutingKey;
    private final String smsQueueExchange;
    private final String smsQueue;
    private final String smsQueueRoutingKey;
    // WhatsApp properties
    private final String whatsappHandler;
    private final String whatsappDispatchExchange;
    private final String whatsappDispatchQueue;
    private final String whatsappDispatchRoutingKey;
    private final String whatsappQueueExchange;
    private final String whatsappQueue;
    private final String whatsappQueueRoutingKey;
    // Facebook Post properties
    private final String facebookPostHandler;
    private final String facebookPostDispatchExchange;
    private final String facebookPostDispatchQueue;
    private final String facebookPostDispatchRoutingKey;
    private final String facebookPostQueueExchange;
    private final String facebookPostQueue;
    private final String facebookPostQueueRoutingKey;
    // Facebook Message properties
    private final String facebookMessageHandler;
    private final String facebookMessageDispatchExchange;
    private final String facebookMessageDispatchQueue;
    private final String facebookMessageDispatchRoutingKey;
    private final String facebookMessageQueueExchange;
    private final String facebookMessageQueue;
    private final String facebookMessageQueueRoutingKey;

    public RabbitProperties(String host, int port, String username, String password,
            String emailHandler, String emailDispatchExchange,
            String emailDispatchQueue, String emailDispatchRoutingKey,
            String emailQueueExchange, String emailQueue, String emailQueueRoutingKey,
            String smsHandler, String smsDispatchExchange,
            String smsDispatchQueue, String smsDispatchRoutingKey,
            String smsQueueExchange, String smsQueue, String smsQueueRoutingKey,
            String whatsappHandler, String whatsappDispatchExchange,
            String whatsappDispatchQueue, String whatsappDispatchRoutingKey,
            String whatsappQueueExchange, String whatsappQueue, String whatsappQueueRoutingKey,
            String facebookPostHandler, String facebookPostDispatchExchange,
            String facebookPostDispatchQueue, String facebookPostDispatchRoutingKey,
            String facebookPostQueueExchange, String facebookPostQueue, String facebookPostQueueRoutingKey,
            String facebookMessageHandler, String facebookMessageDispatchExchange,
            String facebookMessageDispatchQueue, String facebookMessageDispatchRoutingKey,
            String facebookMessageQueueExchange, String facebookMessageQueue, String facebookMessageQueueRoutingKey) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.emailHandler = emailHandler;
        this.emailDispatchExchange = emailDispatchExchange;
        this.emailDispatchQueue = emailDispatchQueue;
        this.emailDispatchRoutingKey = emailDispatchRoutingKey;
        this.emailQueueExchange = emailQueueExchange;
        this.emailQueue = emailQueue;
        this.emailQueueRoutingKey = emailQueueRoutingKey;
        this.smsHandler = smsHandler;
        this.smsDispatchExchange = smsDispatchExchange;
        this.smsDispatchQueue = smsDispatchQueue;
        this.smsDispatchRoutingKey = smsDispatchRoutingKey;
        this.smsQueueExchange = smsQueueExchange;
        this.smsQueue = smsQueue;
        this.smsQueueRoutingKey = smsQueueRoutingKey;
        this.whatsappHandler = whatsappHandler;
        this.whatsappDispatchExchange = whatsappDispatchExchange;
        this.whatsappDispatchQueue = whatsappDispatchQueue;
        this.whatsappDispatchRoutingKey = whatsappDispatchRoutingKey;
        this.whatsappQueueExchange = whatsappQueueExchange;
        this.whatsappQueue = whatsappQueue;
        this.whatsappQueueRoutingKey = whatsappQueueRoutingKey;
        this.facebookPostHandler = facebookPostHandler;
        this.facebookPostDispatchExchange = facebookPostDispatchExchange;
        this.facebookPostDispatchQueue = facebookPostDispatchQueue;
        this.facebookPostDispatchRoutingKey = facebookPostDispatchRoutingKey;
        this.facebookPostQueueExchange = facebookPostQueueExchange;
        this.facebookPostQueue = facebookPostQueue;
        this.facebookPostQueueRoutingKey = facebookPostQueueRoutingKey;
        this.facebookMessageHandler = facebookMessageHandler;
        this.facebookMessageDispatchExchange = facebookMessageDispatchExchange;
        this.facebookMessageDispatchQueue = facebookMessageDispatchQueue;
        this.facebookMessageDispatchRoutingKey = facebookMessageDispatchRoutingKey;
        this.facebookMessageQueueExchange = facebookMessageQueueExchange;
        this.facebookMessageQueue = facebookMessageQueue;
        this.facebookMessageQueueRoutingKey = facebookMessageQueueRoutingKey;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmailDispatchQueue() {
        return emailDispatchQueue;
    }

    public String getEmailDispatchRoutingKey() {
        return emailDispatchRoutingKey;
    }

    public String getEmailQueue() {
        return emailQueue;
    }

    public String getEmailQueueRoutingKey() {
        return emailQueueRoutingKey;
    }

    public String getEmailDispatchExchange() {
        return emailDispatchExchange;
    }

    public String getEmailQueueExchange() {
        return emailQueueExchange;
    }

    public String getEmailHandler() {
        return emailHandler;
    }

    public String getSmsHandler() {
        return smsHandler;
    }

    public String getSmsDispatchExchange() {
        return smsDispatchExchange;
    }

    public String getSmsDispatchQueue() {
        return smsDispatchQueue;
    }

    public String getSmsDispatchRoutingKey() {
        return smsDispatchRoutingKey;
    }

    public String getSmsQueueExchange() {
        return smsQueueExchange;
    }

    public String getSmsQueue() {
        return smsQueue;
    }

    public String getSmsQueueRoutingKey() {
        return smsQueueRoutingKey;
    }

    public String getWhatsappHandler() {
        return whatsappHandler;
    }

    public String getWhatsappDispatchExchange() {
        return whatsappDispatchExchange;
    }

    public String getWhatsappDispatchQueue() {
        return whatsappDispatchQueue;
    }

    public String getWhatsappDispatchRoutingKey() {
        return whatsappDispatchRoutingKey;
    }

    public String getWhatsappQueueExchange() {
        return whatsappQueueExchange;
    }

    public String getWhatsappQueue() {
        return whatsappQueue;
    }

    public String getWhatsappQueueRoutingKey() {
        return whatsappQueueRoutingKey;
    }

    public String getFacebookPostHandler() {
        return facebookPostHandler;
    }

    public String getFacebookPostDispatchExchange() {
        return facebookPostDispatchExchange;
    }

    public String getFacebookPostDispatchQueue() {
        return facebookPostDispatchQueue;
    }

    public String getFacebookPostDispatchRoutingKey() {
        return facebookPostDispatchRoutingKey;
    }

    public String getFacebookPostQueueExchange() {
        return facebookPostQueueExchange;
    }

    public String getFacebookPostQueue() {
        return facebookPostQueue;
    }

    public String getFacebookPostQueueRoutingKey() {
        return facebookPostQueueRoutingKey;
    }

    public String getFacebookMessageHandler() {
        return facebookMessageHandler;
    }

    public String getFacebookMessageDispatchExchange() {
        return facebookMessageDispatchExchange;
    }

    public String getFacebookMessageDispatchQueue() {
        return facebookMessageDispatchQueue;
    }

    public String getFacebookMessageDispatchRoutingKey() {
        return facebookMessageDispatchRoutingKey;
    }

    public String getFacebookMessageQueueExchange() {
        return facebookMessageQueueExchange;
    }

    public String getFacebookMessageQueue() {
        return facebookMessageQueue;
    }

    public String getFacebookMessageQueueRoutingKey() {
        return facebookMessageQueueRoutingKey;
    }

    @Override
    public String toString() {
        return "RabbitProperties [host=" + host + ", port=" + port + ", username=" + username + ", password=" + password
                + ", emailHandler=" + emailHandler + ", emailDispatchExchange=" + emailDispatchExchange
                + ", emailDispatchQueue=" + emailDispatchQueue + ", emailDispatchRoutingKey=" + emailDispatchRoutingKey
                + ", emailQueueExchange=" + emailQueueExchange + ", emailQueue=" + emailQueue
                + ", emailQueueRoutingKey=" + emailQueueRoutingKey
                + ", smsHandler=" + smsHandler + ", smsDispatchExchange=" + smsDispatchExchange
                + ", smsDispatchQueue=" + smsDispatchQueue + ", smsDispatchRoutingKey=" + smsDispatchRoutingKey
                + ", smsQueueExchange=" + smsQueueExchange + ", smsQueue=" + smsQueue
                + ", smsQueueRoutingKey=" + smsQueueRoutingKey
                + ", whatsappHandler=" + whatsappHandler + ", whatsappDispatchExchange=" + whatsappDispatchExchange
                + ", whatsappDispatchQueue=" + whatsappDispatchQueue + ", whatsappDispatchRoutingKey=" + whatsappDispatchRoutingKey
                + ", whatsappQueueExchange=" + whatsappQueueExchange + ", whatsappQueue=" + whatsappQueue
                + ", whatsappQueueRoutingKey=" + whatsappQueueRoutingKey
                + ", facebookPostHandler=" + facebookPostHandler + ", facebookPostDispatchExchange=" + facebookPostDispatchExchange
                + ", facebookPostDispatchQueue=" + facebookPostDispatchQueue + ", facebookPostDispatchRoutingKey=" + facebookPostDispatchRoutingKey
                + ", facebookPostQueueExchange=" + facebookPostQueueExchange + ", facebookPostQueue=" + facebookPostQueue
                + ", facebookPostQueueRoutingKey=" + facebookPostQueueRoutingKey
                + ", facebookMessageHandler=" + facebookMessageHandler + ", facebookMessageDispatchExchange=" + facebookMessageDispatchExchange
                + ", facebookMessageDispatchQueue=" + facebookMessageDispatchQueue + ", facebookMessageDispatchRoutingKey=" + facebookMessageDispatchRoutingKey
                + ", facebookMessageQueueExchange=" + facebookMessageQueueExchange + ", facebookMessageQueue=" + facebookMessageQueue
                + ", facebookMessageQueueRoutingKey=" + facebookMessageQueueRoutingKey + "]";
    }

}
