package framework.core;

import framework.annotation.MyAutowired;
import framework.annotation.MyComponent;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Spring의 ApplicationContext와 유사한 역할을 하는 핵심 컨테이너.
 * @MyComponent가 붙은 클래스를 찾아 빈으로 등록하고
 * @MyAutowired 필드에 의존성을 주입합니다.
 */
public class MyApplicationContext {

    private final Map<String, Object> beans = new HashMap<>();
    private final Map<String, Class<?>> beanClasses = new HashMap<>();

    public MyApplicationContext(String basePackage) {
        System.out.println("*MyApplicationContext: " + basePackage);
        try {
            //1. 컴포넌트 스캔 및 빈 등록
            scanComponents(basePackage);
            //2. 빈 인스턴스 생성
            instantiateBeans();
            //3. 의존성 주입
            autowireBeans();
        } catch (Exception e) {
            System.err.println(
                    "ERROR: failed to initialize MyApplicationContext: "
                            + e.getMessage());
            e.printStackTrace();
        }
    }

    private void scanComponents(String basePackage) throws Exception {
        System.out.println("Component scan started: " + basePackage);
        String path = basePackage.replace('.', '/');
        ClassLoader classLoader =
                Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(path);

        List<File> directories = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File file = new File(resource.toURI());
            if (file.isDirectory()) {
                directories.add(file);
            }
        }

        Set<Class<?>> foundClasses = new HashSet<>();
        for (File directory : directories) {
            System.out.println("\tDirectory scan started: " +
                    directory.getAbsolutePath()); // 디버깅용
            findClassesInDirectory(directory, basePackage, foundClasses);
        }

        if (foundClasses.isEmpty()) {
            System.err.println("Warning: Failed to find @MyComponent class");
        }

        for (Class<?> clazz : foundClasses) {
            if (clazz.isAnnotationPresent(MyComponent.class)) {
                MyComponent myComponent = clazz.getAnnotation(MyComponent.class);
                String beanName = myComponent.value();
                if (beanName.isEmpty()) {
                    beanName = getDefaultBeanName(clazz);
                }
                beanClasses.put(beanName, clazz);
                System.out.println("\t@MyComponent class found: " +
                        clazz.getName() + " (Bean: " + beanName + ")");
            }
        }
        System.out.println("Component scan completed.");
        System.out.println(beanClasses.size() + "bean registered");
    }

    // 디렉토리 내에서 클래스를 재귀적으로 찾는 메소드 이름 변경
    private void findClassesInDirectory(
            File directory, String packageName, Set<Class<?>> foundClasses)
            throws ClassNotFoundException {
        if (!directory.exists()) {
            return;
        }
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                findClassesInDirectory(file, packageName +
                        "." + file.getName(), foundClasses);
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' +
                        file.getName().substring(0, file.getName().length() - 6);
                try {
                    foundClasses.add(Class.forName(className));
                    System.out.println("\tClass file found: " + className);
                } catch (NoClassDefFoundError e) {
                    System.err.println("\tNoClassDefFoundError: " +
                            className + " (" + e.getMessage() + ")");
                }
            }
        }
    }

    private String getDefaultBeanName(Class<?> clazz) {
        String simpleName = clazz.getSimpleName();
        return Character.toLowerCase(simpleName.charAt(0)) +
                simpleName.substring(1);
    }

    private void instantiateBeans() throws Exception {
        System.out.println("\nBean instance create started:");
        // 의존성 주입을 위해 모든 빈을 먼저 생성 (순환 참조 고려 안함)
        // 빈 클래스 맵의 키셋을 리스트로 변환하여 순회 중 수정 방지
        List<String> beanNamesToInstantiate =
                new ArrayList<>(beanClasses.keySet());

        for (String beanName : beanNamesToInstantiate) {
            Class<?> clazz = beanClasses.get(beanName);

            if (beans.containsKey(beanName)) {
                continue; // 이미 생성된 빈은 스킵
            }

            try {
                //기본 생성자(private 포함)로 인스턴스 생성
                Constructor<?> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                Object beanInstance = constructor.newInstance();
                beans.put(beanName, beanInstance);
                System.out.println("\tBean creation completed: " +
                        beanName + " (" + clazz.getName() + ")");
            } catch (NoSuchMethodException e) {
                System.err.println(e.getMessage());
            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("Bean instance creation completed.");
        System.out.println(beans.size() + "bean(s) created.");
    }

    private void autowireBeans() throws Exception {
        System.out.println("\nDependency injection started:");
        for (Object bean : beans.values()) {
            Class<?> clazz = bean.getClass();
            System.out.println("\t" + getDefaultBeanName(clazz) +
                    "' field scan: " + clazz.getName());

            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(MyAutowired.class)) {
                    //@MyAutowired 어노테이션이 붙은 필드 찾음
                    Class<?> fieldType = field.getType();
                    System.out.println("\t@MyAutowired field found: " +
                            field.getName());

                    Object dependency = null;
                    List<Object> matchingBeans = new ArrayList<>();
                    for (Object b : beans.values()) {
                        if (fieldType.isAssignableFrom(b.getClass())) {
                            matchingBeans.add(b);
                        }
                    }

                    if (matchingBeans.size() == 1) {
                        dependency = matchingBeans.get(0);
                        System.out.println("\tMatched bean found: " +
                                dependency.getClass().getName());
                    } else if (matchingBeans.size() > 1) {
                        //동일 타입의 빈이 여러 개일 경우 필드 이름으로 매칭 시도
                        String fieldBeanName = getDefaultBeanName(fieldType);
                        if (beans.containsKey(fieldBeanName) &&
                                fieldType.isAssignableFrom(
                                        beans.get(fieldBeanName).getClass())) {
                            dependency = beans.get(fieldBeanName);
                            System.out.println("\tMatched bean by name found: "
                                    + dependency.getClass().getName());
                        } else {
                            System.err.println("\tERROR: " + clazz.getName() +
                                    "의 " + field.getName() + " 필드에 주입할 " +
                                    fieldType.getName() + " 타입의 빈이 여러 개입니다. 명확한 빈을 찾을 수 없습니다.");
                            continue;
                        }
                    } else {
                        System.err.println("\tERROR: " + clazz.getName() +
                                "의 " + field.getName() + " 필드에 주입할 " +
                                fieldType.getName() + " 타입의 빈을 찾을 수 없습니다.");
                        continue;
                    }

                    if (dependency != null) {
                        field.setAccessible(true); // private 필드에도 주입 가능하도록 설정
                        field.set(bean, dependency); // 필드에 의존성 주입
                        System.out.println("\tDependency injection completed: " +
                                dependency.getClass().getSimpleName());
                    }
                }
            }
        }
        System.out.println("Dependency injection completed.");
    }

    public <T> T getBean(String beanName) {
        if (!beans.containsKey(beanName)) {
            // 이 오류 메시지는 호출 측에서 처리
            return null; // 빈이 없으면 null 반환
        }
        return (T) beans.get(beanName);
    }

    public <T> T getBean(Class<T> requiredType) throws Exception {
        List<Object> matchingBeans = new ArrayList<>();
        for (Object b : beans.values()) {
            if (requiredType.isAssignableFrom(b.getClass())) {
                matchingBeans.add(b);
            }
        }
        if (matchingBeans.size() == 1) {
            return (T) matchingBeans.get(0);
        } else if (matchingBeans.size() > 1) {
            throw new RuntimeException("타입 " + requiredType.getName() + "의 빈이 여러 개 존재합니다. 이름으로 가져오세요.");
        } else {
            // 이 오류 메시지는 호출 측에서 처리
            throw new RuntimeException("타입 " + requiredType.getName() + "의 빈을 찾을 수 없습니다.");
        }
    }
}