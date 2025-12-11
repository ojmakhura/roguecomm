// license-header java merge-point
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 * TEMPLATE:    SpringServiceImpl.vsl in andromda-spring cartridge
 * MODEL CLASS: AndroMDAModel::backend::bw.co.roguesystems.comm::survey::question::QuestionService
 * STEREOTYPE:  Service
 */
package bw.co.roguesystems.comm.survey.question;

import java.util.Collection;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @see bw.co.roguesystems.comm.survey.question.QuestionService
 */
@Service("questionService")
@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
public class QuestionServiceImpl
    extends QuestionServiceBase
{
    public QuestionServiceImpl(
        QuestionDao questionDao,
        QuestionRepository questionRepository,
        MessageSource messageSource
    ) {
        
        super(
            questionDao,
            questionRepository,
            messageSource
        );
    }

    /**
     * @see bw.co.roguesystems.comm.survey.question.QuestionService#findById(String)
     */
    @Override
    protected QuestionDTO handleFindById(String id)
        throws Exception
    {
        Question question = this.questionRepository.findById(id)
            .orElseThrow(() -> new Exception("Question not found for id: " + id));

        return this.questionDao.toQuestionDTO(question);
    }

    /**
     * @see bw.co.roguesystems.comm.survey.question.QuestionService#save(QuestionDTO)
     */
    @Override
    protected QuestionDTO handleSave(QuestionDTO question)
        throws Exception
    {
        Question questionEntity = this.questionDao.questionDTOToEntity(question);
        Question savedQuestion = this.questionRepository.save(questionEntity);
        return this.questionDao.toQuestionDTO(savedQuestion);
    }

    /**
     * @see bw.co.roguesystems.comm.survey.question.QuestionService#remove(String)
     */
    @Override
    protected boolean handleRemove(String id)
        throws Exception
    {

        this.questionRepository.deleteById(id);
        return true;
    }

    /**
     * @see bw.co.roguesystems.comm.survey.question.QuestionService#getAll()
     */
    @Override
    protected Collection<QuestionDTO> handleGetAll()
        throws Exception
    {
        Collection<Question> questions = this.questionRepository.findAll();
        return this.questionDao.toQuestionDTOCollection(questions);
    }

    /**
     * @see bw.co.roguesystems.comm.survey.question.QuestionService#search(String)
     */
    @Override
    protected Collection<QuestionDTO> handleSearch(String criteria)
        throws Exception
    {
        // TODO implement protected  Collection<QuestionDTO> handleSearch(String criteria)
        throw new UnsupportedOperationException("bw.co.roguesystems.comm.survey.question.QuestionService.handleSearch(String criteria) Not implemented!");
    }

    /**
     * @see bw.co.roguesystems.comm.survey.question.QuestionService#getAll(Integer, Integer)
     */
    @Override
    protected Page<QuestionDTO> handleGetAll(Integer pageNumber, Integer pageSize)
        throws Exception
    {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Question> questions = this.questionRepository.findAll(pageRequest);
        return questions.map(question -> this.questionDao.toQuestionDTO(question));
    }

}