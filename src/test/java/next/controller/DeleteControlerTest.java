package next.controller;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.mockito.MockitoAnnotations;

import next.controller.qna.DeleteQuestionController;
import next.model.Question;
import next.service.QnaService;

public class DeleteControlerTest {

	/*
	 * Dao를 사용하는 모든 콘트롤러는 DB가 설치와 테이블이 생성되어있어야함.
	 * 디비가 존재하지 않는 상태에서도, 로직을 테스트하고 싶다.
	 * Map을 활용한 메모리 DB와 
	 */
	
	@Mock
	QnaService service;
	
	@InjectMocks
	DeleteQuestionController controller;
	
	
	@Test
	public void example1() {
		MockitoAnnotations.initMocks(this);
		when(service.findById(0)).thenReturn(new Question(0, "jude", "juho", "hello", new Date(), 2));
		
		Question question = service.findById(0);
		
		verify(service,atLeast(1)).findById(0);
		
		
		
		
		
	}
	
	
}
