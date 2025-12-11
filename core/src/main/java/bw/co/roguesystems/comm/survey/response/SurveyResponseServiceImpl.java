// license-header java merge-point
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 * TEMPLATE:    SpringServiceImpl.vsl in andromda-spring cartridge
 * MODEL CLASS: AndroMDAModel::backend::bw.co.roguesystems.comm::survey::response::SurveyResponseService
 * STEREOTYPE:  Service
 */
package bw.co.roguesystems.comm.survey.response;

import java.util.Collection;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @see bw.co.roguesystems.comm.survey.response.SurveyResponseService
 */
@Service("surveyResponseService")
@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
public class SurveyResponseServiceImpl
    extends SurveyResponseServiceBase
{
    public SurveyResponseServiceImpl(
        SurveyResponseDao surveyResponseDao,
        SurveyResponseRepository surveyResponseRepository,
        MessageSource messageSource
    ) {
        
        super(
            surveyResponseDao,
            surveyResponseRepository,
            messageSource
        );
    }

    /**
     * @see bw.co.roguesystems.comm.survey.response.SurveyResponseService#findById(String)
     */
    @Override
    protected SurveyResponseDTO handleFindById(String id)
        throws Exception
    {
        // TODO implement protected  SurveyResponseDTO handleFindById(String id)
        throw new UnsupportedOperationException("bw.co.roguesystems.comm.survey.response.SurveyResponseService.handleFindById(String id) Not implemented!");
    }

    /**
     * @see bw.co.roguesystems.comm.survey.response.SurveyResponseService#save(SurveyResponseDTO)
     */
    @Override
    protected SurveyResponseDTO handleSave(SurveyResponseDTO response)
        throws Exception
    {
        // TODO implement protected  SurveyResponseDTO handleSave(SurveyResponseDTO response)
        throw new UnsupportedOperationException("bw.co.roguesystems.comm.survey.response.SurveyResponseService.handleSave(SurveyResponseDTO response) Not implemented!");
    }

    /**
     * @see bw.co.roguesystems.comm.survey.response.SurveyResponseService#remove(String)
     */
    @Override
    protected boolean handleRemove(String id)
        throws Exception
    {
        // TODO implement protected  boolean handleRemove(String id)
        throw new UnsupportedOperationException("bw.co.roguesystems.comm.survey.response.SurveyResponseService.handleRemove(String id) Not implemented!");
    }

    /**
     * @see bw.co.roguesystems.comm.survey.response.SurveyResponseService#getAll()
     */
    @Override
    protected Collection<SurveyResponseDTO> handleGetAll()
        throws Exception
    {
        // TODO implement protected  Collection<SurveyResponseDTO> handleGetAll()
        throw new UnsupportedOperationException("bw.co.roguesystems.comm.survey.response.SurveyResponseService.handleGetAll() Not implemented!");
    }

    /**
     * @see bw.co.roguesystems.comm.survey.response.SurveyResponseService#search(String)
     */
    @Override
    protected Collection<SurveyResponseDTO> handleSearch(String criteria)
        throws Exception
    {
        // TODO implement protected  Collection<SurveyResponseDTO> handleSearch(String criteria)
        throw new UnsupportedOperationException("bw.co.roguesystems.comm.survey.response.SurveyResponseService.handleSearch(String criteria) Not implemented!");
    }

    /**
     * @see bw.co.roguesystems.comm.survey.response.SurveyResponseService#getAll(Integer, Integer)
     */
    @Override
    protected Page<SurveyResponseDTO> handleGetAll(Integer pageNumber, Integer pageSize)
        throws Exception
    {
        // TODO implement protected  Page<SurveyResponseDTO> handleGetAll(Integer pageNumber, Integer pageSize)
        throw new UnsupportedOperationException("bw.co.roguesystems.comm.survey.response.SurveyResponseService.handleGetAll(Integer pageNumber, Integer pageSize) Not implemented!");
    }

    /**
     * @see bw.co.roguesystems.comm.survey.response.SurveyResponseService#getBySessionReference(String)
     */
    @Override
    protected Collection<SurveyResponseDTO> handleGetBySessionReference(String sessionReference)
        throws Exception
    {
        // TODO implement protected  Collection<SurveyResponseDTO> handleGetBySessionReference(String sessionReference)
        throw new UnsupportedOperationException("bw.co.roguesystems.comm.survey.response.SurveyResponseService.handleGetBySessionReference(String sessionReference) Not implemented!");
    }

    /**
     * @see bw.co.roguesystems.comm.survey.response.SurveyResponseService#getBySessionReference(String, Integer, Integer)
     */
    @Override
    protected Page<SurveyResponseDTO> handleGetBySessionReference(String sessionReference, Integer pageNumber, Integer pageSize)
        throws Exception
    {
        // TODO implement protected  Page<SurveyResponseDTO> handleGetBySessionReference(String sessionReference, Integer pageNumber, Integer pageSize)
        throw new UnsupportedOperationException("bw.co.roguesystems.comm.survey.response.SurveyResponseService.handleGetBySessionReference(String sessionReference, Integer pageNumber, Integer pageSize) Not implemented!");
    }

    /**
     * @see bw.co.roguesystems.comm.survey.response.SurveyResponseService#getBySession(String)
     */
    @Override
    protected Collection<SurveyResponseDTO> handleGetBySession(String sessionId)
        throws Exception
    {
        // TODO implement protected  Collection<SurveyResponseDTO> handleGetBySession(String sessionId)
        throw new UnsupportedOperationException("bw.co.roguesystems.comm.survey.response.SurveyResponseService.handleGetBySession(String sessionId) Not implemented!");
    }

    /**
     * @see bw.co.roguesystems.comm.survey.response.SurveyResponseService#getBySession(String, Integer, Integer)
     */
    @Override
    protected Page<SurveyResponseDTO> handleGetBySession(String sessionId, Integer pageNumber, Integer pageSize)
        throws Exception
    {
        // TODO implement protected  Page<SurveyResponseDTO> handleGetBySession(String sessionId, Integer pageNumber, Integer pageSize)
        throw new UnsupportedOperationException("bw.co.roguesystems.comm.survey.response.SurveyResponseService.handleGetBySession(String sessionId, Integer pageNumber, Integer pageSize) Not implemented!");
    }

}