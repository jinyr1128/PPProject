# PPProject(PlayPalace)


## 프로젝트 개요
PPProject는 Java와 Spring, Spring Security, 그리고 Gradle을 이용하여 구현한 웹 애플리케이션이다.<br>
사용자 인증, 프로필 관리, 게시물 및 댓
글 CRUD 기능, 뉴스 피드 기능 등을 제공한다.
---
## 기능
### 사용자 인증 기능
- 회원가입 기능: 새로운 사용자는 ID와 비밀번호를 이용해 서비스에 가입할 수 있다. 비밀번호는 암호화되어 저장된다.
- 로그인 및 로그아웃 기능: 사용자는 자신의 계정으로 서비스에 로그인하고 로그아웃할 수 있다.
### 프로필 관리
- 프로필 수정 기능: 사용자는 자신의 이름, 한 줄 소개 등의 기본 정보를 조회하고 수정할 수 있다. 비밀번호 수정 시에는 비밀번호를 한 번 더 입력받는 과정이 필요하다.
### 게시물 CRUD 기능
- 게시물 작성, 조회, 수정, 삭제 기능: 게시물 조회를 제외한 나머지 기능은 인가(Authorization) 개념이 적용되며, 이는 JWT와 같은 토큰으로 검증된다. 사용자는 자신이 작성한 글만 삭제할 수 있다.
- 게시물 작성, 수정, 삭제 시 새로고침 기능: 게시물 작성, 수정, 삭제를 할 때마다 조회 API를 다시 호출하여 최신의 게시물 내용을 화면에 보여준다.
### 뉴스 피드 기능
- 뉴스 피드 페이지: 사용자는 다른 사용자의 게시물을 한 눈에 볼 수 있는 뉴스 피드 페이지를 이용할 수 있다.
### 댓글 CRUD 기능
- 댓글 작성, 조회, 수정, 삭제 기능: 사용자는 게시물에 댓글을 작성하고, 자신이 작성한 댓글을 수정하거나 삭제할 수 있다. 인가(Authorization) 개념이 적용된다.
- 댓글 작성, 수정, 삭제 시 새로고침 기능: 댓글 작성, 수정, 삭제를 할 때마다 조회 API를 다시 호출하여 최신의 댓글 목록을 화면에 보여준다.
---
## 기술 스택
- ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) JAR 17
-  ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) 
- Spring Boot 3.1.5
- Spring Security 6
- JPA/Hibernate
- ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white) 8.4
- ![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
- ![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
- ![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
- 	![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
- 	![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
## 구현 방법
 이 프로젝트는 Java와 Spring, Spring Security, 그리고 Gradle을 이용하여 구현되었다.<br> 사용자 인증은 Spring Security를 이용하였으며, 데이터베이스 연동은 Spring Data JPA를 이용하였다. <br>또한, Gradle을 이용하여 프로젝트의 의존성을 관리하였다.

---
## 설치 및 실행 방법
### 필요 사항
- Java 11 이상
- Gradle
- MySQL

### 실행 방법
이 프로젝트를 실행하려면 Java와 Gradle이 설치되어 있어야 한다. <br>그 후, 소스 코드를 클론하고 Gradle을 이용하여 빌드하면 된다. 빌드가 완료되면 생성된 jar 파일을 이용하여 서버를 실행할 수 있다.

---
# API 명세서

---
## 게시글 관련 API

### 게시글 작성
- Method: `POST`
- URL: `/api/articles`
- Request: `{"title": "제목", "content": "내용"}`
- Response: `201, 생성된 게시글`

### 모든 게시글 조회
- Method: `GET`
- URL: `/api/articles`
- Response: `200, 모든 게시글`

### 특정 게시글 조회
- Method: `GET`
- URL: `/api/articles/{id}`
- Response: `200, 특정 게시글`

### 특정 게시글 수정
- Method: `PUT`
- URL: `/api/articles/{id}`
- Request: `{"title": "제목", "content": "내용"}`
- Response: `200, 수정된 게시글`

### 특정 게시글 삭제
- Method: `DELETE`
- URL: `/api/articles/{id}`
- Response: `204`

### 댓글 생성
- Method: `POST`
- URL: `/api/comments/{articleId}`
- Request: `{"comment": "댓글 내용", "articleId": "게시글 ID", "userId": "사용자 ID"}`
- Response: `201, 생성된 댓글`

### 댓글 수정
- Method: `PUT`
- URL: `/api/comments/{commentId}`
- Request: `{"comment": "댓글 내용", "articleId": "게시글 ID", "userId": "사용자 ID"}`
- Response: `200, 수정된 댓글`

### 댓글 삭제
- Method: `DELETE`
- URL: `/api/comments/{commentId}`
- Response: `204`
---
## 사용자 관련 API

---
### 회원가입
- Method: `POST`
- URL: `/user/signup`
- Request: `{"email": "이메일", "username": "닉네임", "password": "비밀번호", "passwordCheck": "비밀번호 확인", "introduction": "자기 소개 (선택사항)"}`
- Response: `200, 메시지`

### 회원가입 페이지 요청
- Method: `GET`
- URL: `/user/signup`
- Response: `가입 HTML`

### 로그인 페이지 요청
- Method: `GET`
- URL: `/user/login-page`
- Response: `로그인 HTML`

### 로그아웃
- Method: `GET`
- URL: `/logout/page`
- Response: `200, 메시지`

### 프로필 수정 페이지 요청
- Method: `GET`
- URL: `/profile/page`
- Response: `200, 수정할 수 있는 정보 전달`

### 프로필 수정
- Method: `PUT`
- URL: `/profile`
- Request: `{"email": "이메일", "username": "닉네임", "introduction": "자기 소개 (선택사항)"}`
- Response: `200, 수정된 수 있는 정보 전달`

### 비밀번호 변경
- Method: `PUT`
- URL: `/profile/password`
- Request: `{"oldPassword": "현재 비밀번호", "newPassword": "변경할 비밀번호", "passwordCheck": "변경할 비밀번호 확인"}`
- Response: `200, 메시지`

### 엑세스 토큰 재발급
- Method: `GET`
- URL: `/user/refresh-token`
- Response: `200, 메시지`
- --
![API1.PNG](img%2FAPI1.PNG)
![API2.PNG](img%2FAPI2.PNG)
---
## ERD
![project_ERD.PNG](img%2Fproject_ERD.PNG)

---

## 디렉토리 구조
![스크린샷 2023-11-27 오전 12.07.33.png](img%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-11-27%20%EC%98%A4%EC%A0%84%2012.07.33.png)
![스크린샷 2023-11-27 오전 12.07.45.png](img%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-11-27%20%EC%98%A4%EC%A0%84%2012.07.45.png)

---

## 실행화면

---

### 로그인 페이지
![스크린샷 2023-11-27 오전 12.49.59.png](img%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-11-27%20%EC%98%A4%EC%A0%84%2012.49.59.png)
### 회원가입 페이지
![스크린샷 2023-11-27 오전 12.50.05.png](img%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-11-27%20%EC%98%A4%EC%A0%84%2012.50.05.png)
### 홈페이지
![스크린샷 2023-11-27 오전 12.50.23.png](img%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-11-27%20%EC%98%A4%EC%A0%84%2012.50.23.png)
### 게시물 작성 페이지
![스크린샷 2023-11-27 오전 12.50.40.png](img%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-11-27%20%EC%98%A4%EC%A0%84%2012.50.40.png)
### 프로필 수정 페이지
![스크린샷 2023-11-27 오전 12.50.57.png](img%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-11-27%20%EC%98%A4%EC%A0%84%2012.50.57.png)

---
## 팀원별 회고
### 진유록
- 회고
### 최재석
이번 프로젝트에서 저는 회원관련쪽(가입,로그인,회원정보수정,인증)쪽을 담당 했는데 저번 개인과제의 사항과 많이 비슷하지만 <br/>
저번과 다르게  리프레시 토큰 방식,로그아웃,회원정보 수정을 추가 했습니다. <br/>
리프레시 토큰은 액세스 토큰이 만료일시 리프레시 토큰을 통해 액세스 토큰을 재발급 받도록 하는건데 코드 구현 작업 다 마치고 나서 <br/>
왜 이렇게 했지? 생각든 부분이 있습니다. <br/>
일단 처음이라 액세스 재발급 받을때 직접 클라가 재발급 받는 url요청을 하도록 했는데 생각해보면 액세스 토큰 인증 필요한 api요청시 액세스 토큰 만료면
자동으로 리프레시 토큰 검사후 액세스 토큰 재발급 하는게 보통의 경우 아닌가? 생각 들어서 다음에도 리프레시 토큰 방식 한다면 이렇게 해볼겁니다!<br/>
그리고 로그아웃은 처음에 "Jwt는 서버에서 액세스 토큰의 정보를 저장하지 않고,액세스 토큰 정보 필요한 api요청시 토큰 검사후 시큐리티컨텍스트에 넣고 요청한 api작업이 끝나면
자동으로 시큐리티 컨텍스트에서 사라지는 방식인데 로그아웃이 존재하나??!" 생각이 들었는데 그래도 안전을 위해 리프레시 토큰만 로그아웃시
블랙리스트로 등록 하자해서 DB에 리프레시 토큰 정보 등록하는 방식으로 했습니다. 그리고 이부분을 알아보니 Redis라는 것을 사용한다는데 시간이 부족해서
일반 DB테이블을 이용했습니다.<br/>
이렇게 리프레시 토큰,로그아웃을 처음 도전해 보니 예외처리 해야 하는 경우가 좀 있어서 복잡했지만 JWT랑 시큐리티에 대해 다시 복습해서<br/>
저번 개인과제때보다 더욱 이해가 잘 됐고,회원 정보 수정쪽에서 변경시 왜 DB에 적용 안되나 문제 일어났는데  영속성 컨텍스트의 더티체킹쪽도<br/>
다시 복습하고 해결해서 이번엔 여러 가지 복습 위주를 한 것 같습니다.<br/>
예외로 저희팀은 애초에 다른팀과 다르게 저 포함 3명 이였는데 한분은 아프셔서 참여 못 하시고 2명에서 작업해서 선택이나 도전사항은 못하고 <br/>
필수쪽만 했던게 약간 아쉽긴 했지만 둘이서만 이렇게 온 것도 잘 한 거구나 느꼈습니다.<br/>

