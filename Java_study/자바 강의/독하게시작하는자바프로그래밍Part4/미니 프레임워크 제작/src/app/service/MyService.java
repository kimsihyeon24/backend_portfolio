package app.service;

import app.repository.MyRepository;
import framework.annotation.MyComponent;
import framework.annotation.MyAutowired;

/**
 * 프레임워크에 의해 관리될 서비스 컴포넌트.
 */
@MyComponent("myService")
public class MyService {

    @MyAutowired
    private MyRepository myRepository;

    public MyService() {
        System.out.println("MyService() constructor");
    }

    public void doSomething() {
        System.out.println("MyService.doSomething()");
        if (myRepository != null) {
            myRepository.saveData("DATA");
        } else {
            System.out.println("Error: MyRepository has not been injected!");
        }
    }
}
