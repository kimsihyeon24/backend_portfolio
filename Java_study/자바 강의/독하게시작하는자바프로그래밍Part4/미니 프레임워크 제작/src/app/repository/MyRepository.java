package app.repository;

import framework.annotation.MyComponent;

/**
 * 프레임워크에 의해 관리될 리포지토리 컴포넌트.
 */
@MyComponent
public class MyRepository {

    public MyRepository() {
        System.out.println("MyRepository() constructor");
    }

    public void saveData(String data) {
        System.out.println("MyRepository.saveData(): " + data);
    }
}