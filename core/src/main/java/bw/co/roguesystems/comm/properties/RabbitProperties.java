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

    public RabbitProperties(String host, int port, String username, String password,
            String emailHandler, String emailDispatchExchange,
            String emailDispatchQueue, String emailDispatchRoutingKey,
            String emailQueueExchange, String emailQueue, String emailQueueRoutingKey,
            String smsHandler, String smsDispatchExchange,
            String smsDispatchQueue, String smsDispatchRoutingKey,
            String smsQueueExchange, String smsQueue, String smsQueueRoutingKey,
            String whatsappHandler, String whatsappDispatchExchange,
            String whatsappDispatchQueue, String whatsappDispatchRoutingKey,
            String whatsappQueueExchange, String whatsappQueue, String whatsappQueueRoutingKey) {
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
                + ", whatsappQueueRoutingKey=" + whatsappQueueRoutingKey + "]";
    }

}
