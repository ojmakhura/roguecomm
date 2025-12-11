// license-header java merge-point
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 * TEMPLATE:    SpringServiceImpl.vsl in andromda-spring cartridge
 * MODEL CLASS: AndroMDAModel::backend::bw.co.roguesystems.comm::session::SessionService
 * STEREOTYPE:  Service
 */
package bw.co.roguesystems.comm.session;

import java.util.Collection;
import java.util.Collections;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @see bw.co.roguesystems.comm.session.SessionService
 */
@Service("sessionService")
@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
public class SessionServiceImpl
    extends SessionServiceBase
{
    public SessionServiceImpl(
        SessionDao sessionDao,
        SessionRepository sessionRepository,
        MessageSource messageSource
    ) {
        
        super(
            sessionDao,
            sessionRepository,
            messageSource
        );
    }

    /**
     * @see bw.co.roguesystems.comm.session.SessionService#findById(String)
     */
    @Override
    protected SessionDTO handleFindById(String id)
        throws Exception
    {

        Session session = this.sessionRepository.findById(id)
            .orElseThrow(() -> new Exception("Session not found for id: " + id));

        return this.sessionDao.toSessionDTO(session);
    }

    /**
     * @see bw.co.roguesystems.comm.session.SessionService#save(SessionDTO)
     */
    @Override
    protected SessionDTO handleSave(SessionDTO session)
        throws Exception
    {

        Session entity = this.sessionDao.sessionDTOToEntity(session);
        entity = this.sessionRepository.save(entity);
        return this.sessionDao.toSessionDTO(entity);
    }

    /**
     * @see bw.co.roguesystems.comm.session.SessionService#remove(String)
     */
    @Override
    protected boolean handleRemove(String id)
        throws Exception
    {

        this.sessionRepository.deleteById(id);
        return true;
    }

    /**
     * @see bw.co.roguesystems.comm.session.SessionService#getAll()
     */
    @Override
    protected Collection<SessionDTO> handleGetAll()
        throws Exception
    {

        Collection<Session> sessions = this.sessionRepository.findAll();
        return this.sessionDao.toSessionDTOCollection(sessions);
    }

    /**
     * @see bw.co.roguesystems.comm.session.SessionService#search(String)
     */
    @Override
    protected Collection<SessionDTO> handleSearch(String criteria)
        throws Exception
    {
        // TODO implement protected  Collection<SessionDTO> handleSearch(String criteria)
        throw new UnsupportedOperationException("bw.co.roguesystems.comm.session.SessionService.handleSearch(String criteria) Not implemented!");
    }

    /**
     * @see bw.co.roguesystems.comm.session.SessionService#getAll(Integer, Integer)
     */
    @Override
    protected Page<SessionDTO> handleGetAll(Integer pageNumber, Integer pageSize)
        throws Exception
    {

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Session> sessions = this.sessionRepository.findAll(pageRequest);
        return sessions.map(session -> this.sessionDao.toSessionDTO(session));
    }

    /**
     * @see bw.co.roguesystems.comm.session.SessionService#next(String)
     */
    @Override
    protected SessionDTO handleNext(String reference)
        throws Exception
    {
        // TODO implement protected  SessionDTO handleNext(String reference)
        throw new UnsupportedOperationException("bw.co.roguesystems.comm.session.SessionService.handleNext(String reference) Not implemented!");
    }

    /**
     * @see bw.co.roguesystems.comm.session.SessionService#findByReference(String)
     */
    @Override
    protected SessionDTO handleFindByReference(String reference)
        throws Exception
    {

        Session session = this.sessionRepository.findByReference(reference);
        if (session == null)
        {
            throw new SessionServiceException("Session not found for reference: " + reference);
        }

        return this.sessionDao.toSessionDTO(session);
    }

    /**
     * @see bw.co.roguesystems.comm.session.SessionService#getBySurveyReference(String)
     */
    @Override
    protected Collection<SessionDTO> handleGetBySurveyReference(String surveyReference)
        throws Exception
    {
        Specification<Session> specification = (root, query, cb) -> {
            return cb.equal(root.get("survey").get("reference"), surveyReference);
        };

        Collection<Session> sessions = this.sessionRepository.findAll(specification);
        return this.sessionDao.toSessionDTOCollection(sessions);
    }

    /**
     * @see bw.co.roguesystems.comm.session.SessionService#getBySurveyReference(String, Integer, Integer)
     */
    @Override
    protected Page<SessionDTO> handleGetBySurveyReference(String surveyReference, Integer pageNumber, Integer pageSize)
        throws Exception
    {

        Specification<Session> specification = (root, query, cb) -> {
            return cb.equal(root.get("survey").get("reference"), surveyReference);
        };

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Session> sessions = this.sessionRepository.findAll(specification, pageRequest);
        return sessions.map(session -> this.sessionDao.toSessionDTO(session));
    }

    /**
     * @see bw.co.roguesystems.comm.session.SessionService#getBySurvey(String)
     */
    @Override
    protected Collection<SessionDTO> handleGetBySurvey(String surveyId)
        throws Exception
    {

        Specification<Session> specification = (root, query, cb) -> {
            return cb.equal(root.get("survey").get("id"), surveyId);
        };

        Collection<Session> sessions = this.sessionRepository.findAll(specification);
        return this.sessionDao.toSessionDTOCollection(sessions);
    }

    /**
     * @see bw.co.roguesystems.comm.session.SessionService#getBySurvey(String, Integer, Integer)
     */
    @Override
    protected Page<SessionDTO> handleGetBySurvey(String surveyId, Integer pageNumber, Integer pageSize)
        throws Exception
    {

        Specification<Session> specification = (root, query, cb) -> {
            return cb.equal(root.get("survey").get("id"), surveyId);
        };

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Session> sessions = this.sessionRepository.findAll(specification, pageRequest);
        return sessions.map(session -> this.sessionDao.toSessionDTO(session));
    }

}