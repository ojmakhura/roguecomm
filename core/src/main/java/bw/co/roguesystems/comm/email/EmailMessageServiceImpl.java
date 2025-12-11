// license-header java merge-point
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 * TEMPLATE:    SpringServiceImpl.vsl in andromda-spring cartridge
 * MODEL CLASS: AndroMDAModel::backend::bw.co.roguesystems.comm::email::EmailMessageService
 * STEREOTYPE:  Service
 */
package bw.co.roguesystems.comm.email;

import java.util.Collection;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @see bw.co.roguesystems.comm.email.EmailMessageService
 */
@Service("emailMessageService")
@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
public class EmailMessageServiceImpl
    extends EmailMessageServiceBase
{
    public EmailMessageServiceImpl(
        EmailMessageDao emailMessageDao,
        EmailMessageRepository emailMessageRepository,
        MessageSource messageSource
    ) {
        
        super(
            emailMessageDao,
            emailMessageRepository,
            messageSource
        );
    }

    /**
     * @see bw.co.roguesystems.comm.email.EmailMessageService#findById(String)
     */
    @Override
    protected EmailMessageDTO handleFindById(String id)
        throws Exception
    {

        EmailMessage emailMessage = this.emailMessageRepository.getReferenceById(id);
        return this.emailMessageDao.toEmailMessageDTO(emailMessage);
    }

    /**
     * @see bw.co.roguesystems.comm.email.EmailMessageService#save(EmailMessageDTO)
     */
    @Override
    protected EmailMessageDTO handleSave(EmailMessageDTO emailMessage)
        throws Exception
    {

        EmailMessage entity = this.emailMessageDao.emailMessageDTOToEntity(emailMessage);
        entity = this.emailMessageRepository.save(entity);
        return this.emailMessageDao.toEmailMessageDTO(entity);
    }

    /**
     * @see bw.co.roguesystems.comm.email.EmailMessageService#remove(String)
     */
    @Override
    protected boolean handleRemove(String id)
        throws Exception
    {

        this.emailMessageRepository.deleteById(id);
        return true;
    }

    /**
     * @see bw.co.roguesystems.comm.email.EmailMessageService#getAll()
     */
    @Override
    protected Collection<EmailMessageDTO> handleGetAll()
        throws Exception
    {
        Collection<EmailMessage> entities = this.emailMessageRepository.findAll();
        return this.emailMessageDao.toEmailMessageDTOCollection(entities);
    }

    /**
     * @see bw.co.roguesystems.comm.email.EmailMessageService#search(String)
     */
    @Override
    protected Collection<EmailMessageDTO> handleSearch(String criteria)
        throws Exception
    {
        // TODO implement protected  Collection<EmailMessageDTO> handleSearch(String criteria)
        throw new UnsupportedOperationException("bw.co.roguesystems.comm.email.EmailMessageService.handleSearch(String criteria) Not implemented!");
    }

    /**
     * @see bw.co.roguesystems.comm.email.EmailMessageService#getAll(Integer, Integer)
     */
    @Override
    protected Page<EmailMessageDTO> handleGetAll(Integer pageNumber, Integer pageSize)
        throws Exception
    {

        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<EmailMessage> page = this.emailMessageRepository.findAll(pageRequest);
        return page.map(emailMessage -> this.emailMessageDao.toEmailMessageDTO(emailMessage));
    }

    @Override
    protected EmailMessageDTO handleSendEmail(EmailMessageDTO emailMessage) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleSendEmail'");
    }
}