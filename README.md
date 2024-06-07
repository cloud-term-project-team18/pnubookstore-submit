# pnu-book-store
## 프로젝트 소개
> 부산대 학생을 위한 전공서적 거래 플랫폼.

> 부산대학교 학생들이 더 이상 사용하지 않는 전공 서적을 쉽게 거래하기 위해 사용할 수 있는 플랫폼으로,
> 
> 판매자는 쉽게 전공 서적을 처리할 수 있고, 구매자는 싼 가격으로 전공 서적을 구매할 수 있습니다.

## 멤버 소개
> 김태호(Devops, BackEnd): 사이트 CI/CD, 오토스케일링, 데이터 크롤링, 인프라 담당
> 
> 김주언(BackEnd): 사이트의 물품에 관련한 기능(등록,삭제,구매) 담당
> 
> 문성재(BackEnd): 사이트의 회원가입, 로깅 담당
> 
> 최지광(FrontEnd): 프론트엔드 담당

## 프로젝트 필요성
> 학기 초 전공 서적이 필요한 강의들이 많습니다. 이에 학생들은 많은 돈을 매 학기마다 전공 서적을 구매하는데 사용합니다.
> 
> 어떤 학생은 동아리, 학과 선후배 관계를 통해서 싸게 전공 서적을 구매할 수 있지만, 이에 해당하지 않는 학생의 경우 
> 싼 가격의 중고 서적을 구매하기 어렵습니다.

> 저희는 이러한 문제를 전공서적 판매 사이트 개발로 통해 누구나 싼 값에 전공 서적을 구매할 수 있는 시스템을 
> 구축하고자 합니다.
> 
> 이때, 대부분의 학생들은 필요한 책을 전공과목의 이름과 교수님으로 구분하지 정확한 책의 이름과 저자, 출판사를 외우고
> 구매하려는 학생은 적습니다. 
> 
> 타 서비스는 책의 이름을 알아야만 검색이 가능한 반면, 저희는 이러한 점을 생각하여 저희 판매사이트는 책의 이름을 몰라도 학과,과목명,교수이름만 
> 알면 책을 검색할 수 있도록 만들었습니다.

> 이후, 거래가 결제까지 진행된다면 판매자가 지정된 물품보관소에 서적을 넣고, 비밀번호를 설정, 
> 구매자에게 지정된 위치와 비밀번호를 공유해 비대면으로 거래가 진행될 수 있도록 합니다.

## 관련 기술/논문/특허 조사내용 소개
![사용 기술](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/stack.png)
> **Server**
>  * Spring boot : Web Application Server
>  * NGINX : Web Server
>  * MySQL : Data 저장을 위한 RDBMS
>  * redis : session 정보 저장을 위한 In-Memory DB

> **Devops**
>  * kubernetes : 컨테이너를 쉽고 빠르게 배포/확장하고 관리를 자동화해주는 오픈소스 플랫폼
>  * HELM : 쿠버네티스 어플리케이션 패키징 툴
>  * GitHub Actions : CI(Continuous Integration)
>  * argo : CD(Continuous Delivery)

> **Infra**
>  * Terraform : 코드형 인프라스트럭쳐(IaC) 자동화 도구
>  * Naver Cloud Platform : 클라우드 컴퓨팅 서비스

## 프로젝트 개발 결과물 소개
> **Architecture**
> ![architecture](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/architecture.png)

> Terraform을 사용해 구축된 Naver Cloud Platform 상의 Kubernetes 아키텍쳐이다. 공개 서브셋에 위치한 ALB Ingress Controller는 외부 트래픽을 라우팅한다. private subnet1에선 NginX와 Spring을 통해 웹 서비스를 제공하고, Fluent-Bit으로 로그를 Elasticsearch로 전송한다.

> 데이터베이스는 MySQL과 redis를 통해 이루어지고, 각각 데이터 저장과 세션 관리를 담당한다. 로그 관리는 ElasticSearch, 시각화는는 Kibana를 사용한다.

> ArgoCD를 통해 리소스의 지속적인 배포 및 업데이트를 관리한다. NAT Gateway를 통해 프라이빗 네트워크 내의 자원이 인터넷에 접근할 수 있게한다. 

> **웹 어플리케이션 구성도**
> ![autoscaling](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/AutoScaling.png)

> HTTP로 들어오는 트래픽은 HTTPS로 자동 리다이렉트되어 보안을 유지한다. 로드 밸런서는 설정된 규칙에 따라 트래픽을 백엔드 서비스 그룹으로 전달한다.

> 노드 풀 아래에는 여러 노드가 배치되어 있으며, 각 노드엔 Nginx와 백엔드 서버가 포함된 Pod가 존재한다. 오토스케일링 기능을 통해 자동으로 노드 및 파드의 수를 조정해 트래픽 변동에 따른 자원을 효율적으로 관리할 수 있다.

> **이메일 인증 과정**
> ![email](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/email.png)

> 사설 서브넷에는 Spring 애플리케이션이 포함된 Pod가 배치되어있다. Pod는 SMTP 프로토콜을 사용해 이메일을 전송한다. 공개 서브넷엔 NAT가 위치해 사설 네트워크에서 발생하는 이메일 트래픽을 외부로 전송한다. 이메일은 NAT를 거쳐 IGT를 통해 인터넷에 접속하고, 외부 이메일 서비스로 전송된다.

> **CI/CD**
> ![CICD](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/CICD.png)

> 개발자는 코드 변경사항을 BackEnd GitHub 레포지토리에 push한다. 이벤트를 GitHub Actions가 받아 코드를 자동으로 빌드하고 테스트하며, 빌드된 이미지를 Naver Container Registry에 푸시한다. 이후 GitHub Actions이 k8s manifests가 존재하는 레포지토리에 이미지 태그 변경사항을 업데이트하고 push한다. ArgoCD는 레포지토리를 주기적으로 폴링해 변경사항을 감지하고, 감지된 변경사항을 k8s 클러스터에 자동으로 적용한다.

> 또한 DevOps 팀원이 k8s manifests파일을 리포지토리에 업데이트하고 push한다. 이후 ArgoCD가 레포지토리를 주기적으로 폴링해 변경사항을 감지하고, 감지된 변경사항을 k8s 클러스터에 자동으로 적용한다.

> **Batch**
> ![batch](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/batch.png)

> DevOps 엔지니어는 'kubectl 명령어'를 통해 k8s 클러스터 내에서 수강편람 추출 배치 작업을 실행한다. 결과는 MySQL 데이터베이스에 저장되고, 이 데이터베이스는 VPC 내의 리소스만 접근할 수 있기에 데이터의 보안을 유지한다.

## 사용방법
>  * 웹 브라우저를 통해 사이트에 접속이 가능하다.
>  * 페이지의 login, signUp 버튼을 통해 로그인, 회원가입이 가능하다.
>  * 로그인 후 상단의 물품 게시판에서 판매 중인 서적을 확인할 수 있다.
>  * 물품 게시판의 검색 폼을 이용해 대학, 학과, 교수, 강좌에 맞는 서적을 검색할 수 있다.
>  * 상단의 물품 등록을 통해 판매하고 싶은 서적을 등록할 수 있다.
>  * 상단의 myPage에서 내 정보와 구매, 판매한 물품을 확인할 수 있다.

## 활용방안 소개
> 해당 서비스는 부산대학생을 위해 구성되어있으며, 학과, 과목, 교수 또한 이에 맞게 구성되어있다. 하지만 타 대학들의 기본구조가 비슷하기 때문에, 약간의 코드 수정을 하면 다른 대학교도 충분히 사용가능한 대학교 중고서적 거래 플랫폼이 될 것이다. 

## 부하테스트 실행 결과
> **부하 테스트 실행**
> ![](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/cloud4.png)

> **노드 스케일링**
> ![](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/cloud1.png)

> **Pod 스케일링**
> ![](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/cloud2.png)

> **스케일링결과**
> ![](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/cloud3.png)


## UI소개
> **메인페이지**
> ![index1](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/index1.png)

> **이메일 인증 및 회원가입**
> ![ev1](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/email_verification.png)
> ![ev2](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/email_verification2.png)
> ![ev3](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/email_verification3.png)
> ![signUp](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/signUp.png)

> **거래게시판**
> ![board](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/search.gif)

> **상세 페이지**
> ![detail1](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/detail1.png)
> ![detail2](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/detail2.png)

> **물품 등록**
> ![register](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/register.png)

> **myPage**
> ![myPage](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/myPage.png)

> **구매 물품**
> ![purchased](https://github.com/cloud-term-project-team18/pnu-book-store/blob/main/Purchased.png)
