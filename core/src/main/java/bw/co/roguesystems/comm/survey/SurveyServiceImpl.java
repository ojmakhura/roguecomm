// license-header java merge-point
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 * TEMPLATE:    SpringServiceImpl.vsl in andromda-spring cartridge
 * MODEL CLASS: AndroMDAModel::backend::bw.co.roguesystems.comm::survey::SurveyService
 * STEREOTYPE:  Service
 */
package bw.co.roguesystems.comm.survey;

import bw.co.roguesystems.comm.message.CommMessageDTO;
import bw.co.roguesystems.comm.survey.question.QuestionDTO;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @see bw.co.roguesystems.comm.survey.SurveyService
 */
@Service("surveyService")
@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
public class SurveyServiceImpl
    extends SurveyServiceBase
{
    public SurveyServiceImpl(
        SurveyDao surveyDao,
        SurveyRepository surveyRepository,
        MessageSource messageSource
    ) {
        
        super(
            surveyDao,
            surveyRepository,
            messageSource
        );
    }

    /**
     * @see bw.co.roguesystems.comm.survey.SurveyService#findById(String)
     */
    @Override
    protected SurveyDTO handleFindById(String id)
        throws Exception
    {
        Survey survey = this.surveyRepository.findById(id)
            .orElseThrow(() -> new Exception("Survey not found for id: " + id));

        return this.surveyDao.toSurveyDTO(survey);
    }

    /**
     * @see bw.co.roguesystems.comm.survey.SurveyService#save(SurveyDTO)
     */
    @Override
    protected SurveyDTO handleSave(SurveyDTO survey)
        throws Exception
    {
        Survey surveyEntity = this.surveyDao.surveyDTOToEntity(survey);
        surveyEntity = this.surveyRepository.save(surveyEntity);
        return this.surveyDao.toSurveyDTO(surveyEntity);
    }

    /**
     * @see bw.co.roguesystems.comm.survey.SurveyService#remove(String)
     */
    @Override
    protected boolean handleRemove(String id)
        throws Exception
    {
        this.surveyRepository.deleteById(id);
        return true;
    }

    /**
     * @see bw.co.roguesystems.comm.survey.SurveyService#getAll()
     */
    @Override
    protected Collection<SurveyDTO> handleGetAll()
        throws Exception
    {
        Collection<Survey> surveys = this.surveyRepository.findAll();
        return this.surveyDao.toSurveyDTOCollection(surveys);
    }

    /**
     * @see bw.co.roguesystems.comm.survey.SurveyService#search(String)
     */
    @Override
    protected Collection<SurveyDTO> handleSearch(String criteria)
        throws Exception
    {
        // TODO implement protected  Collection<SurveyDTO> handleSearch(String criteria)
        throw new UnsupportedOperationException("bw.co.roguesystems.comm.survey.SurveyService.handleSearch(String criteria) Not implemented!");
    }

    /**
     * @see bw.co.roguesystems.comm.survey.SurveyService#getAll(Integer, Integer)
     */
    @Override
    protected Page<SurveyDTO> handleGetAll(Integer pageNumber, Integer pageSize)
        throws Exception
    {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Survey> surveys = this.surveyRepository.findAll(pageRequest);
        return surveys.map(survey -> this.surveyDao.toSurveyDTO(survey));
    }

    /**
     * @see bw.co.roguesystems.comm.survey.SurveyService#addQuestion(String, QuestionDTO)
     */
    @Override
    protected SurveyDTO handleAddQuestion(String id, QuestionDTO question)
        throws Exception
    {
        // TODO implement protected  SurveyDTO handleAddQuestion(String id, QuestionDTO question)
        throw new UnsupportedOperationException("bw.co.roguesystems.comm.survey.SurveyService.handleAddQuestion(String id, QuestionDTO question) Not implemented!");
    }

    /**
     * @see bw.co.roguesystems.comm.survey.SurveyService#findByReference(String)
     */
    @Override
    protected SurveyDTO handleFindByReference(String reference)
        throws Exception
    {

        Survey survey = this.surveyRepository.findByReference(reference);

        return this.surveyDao.toSurveyDTO(survey);
    }

    @Override
    protected SurveyDTO handleLaunch(String id, Set<String> destinations) throws Exception {

        Survey survey = this.surveyRepository.getReferenceById(id);
        String firstQuestion = survey.getQuestions().stream()
            .sorted((q1, q2) -> Integer.compare(q1.getQuestionNo(), q2.getQuestionNo()))
            .findFirst()
            .orElseThrow(() -> new Exception("Survey has no questions"))
            .getText();

        Collection<CommMessageDTO> messages = destinations.stream().map(destination -> {
            CommMessageDTO message = new CommMessageDTO();
            message.setDestinations(List.of(destination));
            message.setSource(survey.getSource());
            message.setText(firstQuestion);

            return message;
        }).toList();

        

        return this.surveyDao.toSurveyDTO(survey);
    }

}