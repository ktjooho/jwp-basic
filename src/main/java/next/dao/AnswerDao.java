package next.dao;

import next.model.Answer;

import java.util.List;

/**
 * Created by nokdu on 2017-03-30.
 */
public interface AnswerDao {
    Answer insert(Answer answer);

    Answer findById(long answerId);

    List<Answer> findAllByQuestionId(long questionId);

    void delete(Long answerId);
}
