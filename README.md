# Search for places

## Requirement

- Kotlin-1.3 +  / target jdk 1.8
- Gradle



## Installment

- IntelliJ에 프로젝트를 임포트해서 실행하세요.

  **또는**

- 직접 JAR 를 생성하세요

  - 프로젝트 디렉토리에서, `/gradlew clean`  and `/gradlew build`  
  -  `build/libs/chianti-0.0.1-SNAPSHOT.jar`  가 생겨요.

  **또는**

- 프로젝트 디렉터리에 동봉한 JAR을 사용하세요.

  - `chianti-0.0.1-SNAPSHOT.jar`



## Run Apps

다음 명령어로 앱을 실행합니다. `java -jar chianti-0.0.1-SNAPSHOT.jar`

- 별도 프로파일이 필요 없습니다. 기본 프로파일로 실행하고, 테스트는 "test" 프로파일로 실행됩니다.
- 테스트 계정
  - ID : **ryan** / PW :  **chianti**
  - ID : **kon** / PW :  **chianti**



## Used External Library

- Ehcache : 외부 API 응답 결과 캐싱 목적
- Retrofit2 : 외부 호출 인터페이스화 및 핸들링 목적