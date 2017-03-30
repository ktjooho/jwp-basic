package next.service;

import com.google.common.collect.Lists;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

/**
 * Created by nokdu on 2017-03-30.
 */
@RunWith(MockitoJUnitRunner.class)
public class QnaServiceTest {

    @Mock
    private QuestionDao questionDao;

    @Mock
    private AnswerDao answerDao;

    private QnaService qnaService;

    @Before
    public void setup( ) {
        qnaService = new QnaService(questionDao,answerDao);
    }

    public User newUser(String name) {
        return new User("1","12345", name, "test@gmail.com");
    }

    @Test
    public void deleteQuestion_null() throws  Exception {
        Question question = new Question(1L,"Juho");

        when(questionDao.findById(1L)).thenReturn(question);
        when(answerDao.findAllByQuestionId(1L)).thenReturn(Lists.newArrayList());

        qnaService.deleteQuestion(1L,newUser("Juho"));
        
    }








}
