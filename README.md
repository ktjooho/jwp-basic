#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
 * ContextLoadListener에서 DB-SQL을 실행.
 * * ContextLoadListener는 WebListener의 한 형태.
 * * 서블릿 컨테이너는 웹 어플의 상태를 관리하는 ServletContext를 들고있다.
 * * 이 컨텍스트가 초기화되며느 ContextLoadListner 한테 알림이 간다. 
 * * Servlet Context의 Lifecycle 이 변경됬을때 발생하는 이벤트를 수신한다.
 * * * 가령 여기서는 context가 초기화.
 * * * servlet context listner는 여러개가 될 수 있다... 
 * * * 그 Listner들은 WebListner의 어노테이션으로 등록이 되서... 통지가 되는듯. 
 * Dispatcher 서블릿이라는 컨테이너에 담기는 서블릿은 하나만 존재.
 * * 이 Servlet에서 URL을 분배하는 역할을 한다... 
 * * 서블릿 컨테이너는 클라이언트로부터 최초 요청시에, 해당 Servlet을 생성한다.
 * * 하지만 loadOnStartup과 같은 property 변경을 통해서, 서블릿 컨테이너가 시작하는 시점에서 인스턴스를 생성할 수도 있다.
 

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* 우리가 만들어놓은 ResourceFilter와 CharacterEncoding Filter를 통해서, UTF-8이 아니거나, 정적자원에 대한것은 사전에 걸러버린다.
* 그 이후에는 DisaptcherServlet이 요청을 받아, 해당 url에 매핑된 콘트롤러를 연결시켜준다.. 

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
*  
