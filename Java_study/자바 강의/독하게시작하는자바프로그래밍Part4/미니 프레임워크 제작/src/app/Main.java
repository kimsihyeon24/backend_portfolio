package app;

import app.repository.MyRepository;
import framework.core.MyApplicationContext;
import app.service.MyService;

public class Main {
    public static void main(String[] args) {
        System.out.println(
                "===== My Spring-like Framework Application Start =====");

        //1. MyApplicationContext 초기화 (컨테이너 생성 및 빈 스캔, 생성, 주입 시작)
        // "app" 패키지 내의 @MyComponent를 찾도록 지시
        MyApplicationContext context = new MyApplicationContext("app");

        System.out.println("\n--- Get bean from context ---");
        try {
            //2. 컨텍스트로부터 MyService 빈 가져오기
            MyService myService = context.getBean("myService");

            //3. MyService의 메소드 호출 (MyRepository가 주입되었는지 확인)
            if (myService != null) {
                myService.doSomething();
            } else {
                System.out.println("ERROR: Failed to get MyService bean.");
            }

            //타입(빈 1개, 싱글톤)으로 빈 가져오기
            MyRepository myRepositoryFromContext =
                    context.getBean(app.repository.MyRepository.class);
            System.out.println("\nMyRepository: " + myRepositoryFromContext);
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println(
                "===== My Spring-like Framework Application End =====");
    }
}

