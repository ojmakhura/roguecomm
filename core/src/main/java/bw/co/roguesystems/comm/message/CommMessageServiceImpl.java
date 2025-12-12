// license-header java merge-point
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 * TEMPLATE:    SpringServiceImpl.vsl in andromda-spring cartridge
 * MODEL CLASS: AndroMDAModel::backend::bw.co.roguesystems.comm::message::CommMessageService
 * STEREOTYPE:  Service
 */
package bw.co.roguesystems.comm.message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import bw.co.roguesystems.comm.properties.RabbitProperties;

/**
 * @see bw.co.roguesystems.comm.message.CommMessageService
 */
@Service("commMessageService")
@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
public class CommMessageServiceImpl
    extends CommMessageServiceBase
{
    private final RabbitTemplate rabbitTemplate;
    private final RabbitProperties rabbitProperties;
    
    public CommMessageServiceImpl(
        CommMessageDao commMessageDao,
        CommMessageRepository commMessageRepository,
        MessageSource messageSource,
        RabbitTemplate rabbitTemplate,
        RabbitProperties rabbitProperties
    ) {
        
        super(
            commMessageDao,
            commMessageRepository,
            messageSource
        );

        this.rabbitTemplate = rabbitTemplate;
        this.rabbitProperties = rabbitProperties;
    }

    /**
     * @see bw.co.roguesystems.comm.message.CommMessageService#findById(String)
     */
    @Override
    protected CommMessageDTO handleFindById(String id)
        throws Exception
    {

        CommMessage commMessage = this.commMessageRepository.findById(id)
            .orElseThrow(() -> new CommMessageServiceException("CommMessage not found for id: " + id));

        return this.commMessageDao.toCommMessageDTO(commMessage);
    }

    /**
     * @see bw.co.roguesystems.comm.message.CommMessageService#save(CommMessageDTO)
     */
    @Override
    protected CommMessageDTO handleSave(CommMessageDTO message)
        throws Exception
    {
        CommMessage commMessage = this.commMessageDao.commMessageDTOToEntity(message);
        commMessage = this.commMessageRepository.save(commMessage);
        return this.commMessageDao.toCommMessageDTO(commMessage);
    }

    /**
     * @see bw.co.roguesystems.comm.message.CommMessageService#remove(String)
     */
    @Override
    protected boolean handleRemove(String id)
        throws Exception
    {
        this.commMessageRepository.deleteById(id);
        return true;
    }

    /**
     * @see bw.co.roguesystems.comm.message.CommMessageService#getAll()
     */
    @Override
    protected Collection<CommMessageDTO> handleGetAll()
        throws Exception
    {
        Collection<CommMessage> commMessages = this.commMessageRepository.findAll();
        return this.commMessageDao.toCommMessageDTOCollection(commMessages);
    }

    /**
     * @see bw.co.roguesystems.comm.message.CommMessageService#search(String)
     */
    @Override
    protected Collection<CommMessageDTO> handleSearch(String criteria)
        throws Exception
    {
        // TODO implement protected  Collection<CommMessageDTO> handleSearch(String criteria)
        throw new UnsupportedOperationException("bw.co.roguesystems.comm.message.CommMessageService.handleSearch(String criteria) Not implemented!");
    }

    /**
     * @see bw.co.roguesystems.comm.message.CommMessageService#getAll(Integer, Integer)
     */
    @Override
    protected Page<CommMessageDTO> handleGetAll(Integer pageNumber, Integer pageSize)
        throws Exception
    {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<CommMessage> commMessages = this.commMessageRepository.findAll(pageRequest);
        return commMessages.map(commMessage -> this.commMessageDao.toCommMessageDTO(commMessage));
    }

    /**
     * @see bw.co.roguesystems.comm.message.CommMessageService#sendMessage(Set<CommMessageDTO>)
     */
    @Override
    protected Collection<CommMessageDTO> handleSendMessage(Set<CommMessageDTO> messages)
        throws Exception
    {
        Collection<CommMessageDTO> emailMessages = new ArrayList<>();
        Collection<CommMessageDTO> whatsappMessages = new ArrayList<>();
        Collection<CommMessageDTO> smsMessages = new ArrayList<>();

        Collection<CommMessageDTO> savedMessages = new ArrayList<>();

        for (CommMessageDTO message : messages) {

            if (message.getSendNow()) {
                switch (message.getPlatform()) {
                    case EMAIL:
                        emailMessages.add(message);
                        break;

                    case WHATSAPP:
                        whatsappMessages.add(message);
                        break;
                    case SMS:
                        smsMessages.add(message);
                        break;
                    default:
                        break;
                }
            } else {

                savedMessages.add(message);
            }
        }

        if (!emailMessages.isEmpty()) {
            System.out.println("=========> " + rabbitProperties.getEmailQueueExchange());
            rabbitTemplate.convertAndSend(rabbitProperties.getEmailQueueExchange(),
                    rabbitProperties.getEmailQueueRoutingKey(), emailMessages);
        }

        if (!whatsappMessages.isEmpty()) {
            System.out.println("++++++++++++++++++++++++++> " + rabbitProperties.getWhatsappQueueExchange());
            rabbitTemplate.convertAndSend(rabbitProperties.getWhatsappQueueExchange(),
                    rabbitProperties.getWhatsappQueueRoutingKey(), whatsappMessages);
        }

        if (!smsMessages.isEmpty()) {
            System.out.println("---------------------------------> " + rabbitProperties.getWhatsappQueueExchange());
            rabbitTemplate.convertAndSend(rabbitProperties.getSmsQueueExchange(),
                    rabbitProperties.getSmsQueueRoutingKey(), smsMessages);
        }

        if(!savedMessages.isEmpty()) {
            Collection<CommMessage> commMessageEntities = savedMessages.stream().map(m -> commMessageDao.commMessageDTOToEntity(m)).toList();
            Collection<CommMessage> savedCommMessages = commMessageRepository.saveAll(commMessageEntities);
            Collection<CommMessageDTO> savedCommMessageDTOs = commMessageDao.toCommMessageDTOCollection(savedCommMessages);
            messages.clear();
            messages.addAll(savedCommMessageDTOs);
        }

        return messages;
    }

}