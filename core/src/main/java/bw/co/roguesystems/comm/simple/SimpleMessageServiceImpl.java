// license-header java merge-point
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 * TEMPLATE:    SpringServiceImpl.vsl in andromda-spring cartridge
 * MODEL CLASS: AndroMDAModel::backend::bw.co.roguesystems.comm::simple::SimpleMessageService
 * STEREOTYPE:  Service
 */
package bw.co.roguesystems.comm.simple;

import java.util.Collection;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @see bw.co.roguesystems.comm.simple.SimpleMessageService
 */
@Service("simpleMessageService")
@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
public class SimpleMessageServiceImpl
    extends SimpleMessageServiceBase
{
    public SimpleMessageServiceImpl(
        SimpleMessageDao simpleMessageDao,
        SimpleMessageRepository simpleMessageRepository,
        MessageSource messageSource
    ) {
        
        super(
            simpleMessageDao,
            simpleMessageRepository,
            messageSource
        );
    }

    /**
     * @see bw.co.roguesystems.comm.simple.SimpleMessageService#findById(String)
     */
    @Override
    protected SimpleMessageDTO handleFindById(String id)
        throws Exception
    {
        SimpleMessage simpleMessage = this.simpleMessageRepository.findById(id)
            .orElseThrow(() -> new Exception("SimpleMessage not found for id: " + id));

        return this.simpleMessageDao.toSimpleMessageDTO(simpleMessage);
    }

    /**
     * @see bw.co.roguesystems.comm.simple.SimpleMessageService#save(SimpleMessageDTO)
     */
    @Override
    protected SimpleMessageDTO handleSave(SimpleMessageDTO message)
        throws Exception
    {

        SimpleMessage messageEntity = this.simpleMessageDao.simpleMessageDTOToEntity(message);
        messageEntity = this.simpleMessageRepository.save(messageEntity);
        return this.simpleMessageDao.toSimpleMessageDTO(messageEntity);
    }

    /**
     * @see bw.co.roguesystems.comm.simple.SimpleMessageService#remove(String)
     */
    @Override
    protected boolean handleRemove(String id)
        throws Exception
    {

        this.simpleMessageRepository.deleteById(id);
        return true;
    }

    /**
     * @see bw.co.roguesystems.comm.simple.SimpleMessageService#getAll()
     */
    @Override
    protected Collection<SimpleMessageDTO> handleGetAll()
        throws Exception
    {
        Collection<SimpleMessage> messages = this.simpleMessageRepository.findAll();
        return this.simpleMessageDao.toSimpleMessageDTOCollection(messages);
    }

    /**
     * @see bw.co.roguesystems.comm.simple.SimpleMessageService#search(String)
     */
    @Override
    protected Collection<SimpleMessageDTO> handleSearch(String criteria)
        throws Exception
    {
        // TODO implement protected  Collection<SimpleMessageDTO> handleSearch(String criteria)
        throw new UnsupportedOperationException("bw.co.roguesystems.comm.simple.SimpleMessageService.handleSearch(String criteria) Not implemented!");
    }

    /**
     * @see bw.co.roguesystems.comm.simple.SimpleMessageService#getAll(Integer, Integer)
     */
    @Override
    protected Page<SimpleMessageDTO> handleGetAll(Integer pageNumber, Integer pageSize)
        throws Exception
    {

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<SimpleMessage> messages = this.simpleMessageRepository.findAll(pageRequest);
        return messages.map(message -> this.simpleMessageDao.toSimpleMessageDTO(message));
    }

    @Override
    protected SimpleMessageDTO handleSendMessage(SimpleMessageDTO message) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleSendMessage'");
    }

}