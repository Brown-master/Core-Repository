# 2022-Open Source Project
# 도로 질서 기여를 위한 견인차량 사고 할당 시스템 
#### Project name : 견人
#### Team name : Brown Master
#### Project execution period : 2022.09.29~2022.12.08
#### GIT LAB URL : [GIT LAB](https://github.com/Brown-master/Core-Repository)
-----------------------
## Description
견人은 견인차량들이 견인하게될 사고차량들을 사고현장에 도착하기 전 미리 제한적으로 할당해주는 어플로, 교통사고 발생후   
사고현장에 빨리 도착하기 위해 견인차량의 차주들이 행하는 불법 행위에 따르는 2차 사고를 방지함으로서 도로 질서를 안정을 목표로 한다.

주요 기능은 어플의 사용자인 견인차량의 차주들에게 적정 거리내에 있는 사고 정보들을 제공하는 것으로  
차주들은 원하는 사고를 선택하여 해당 사고 견인의 우선권을 획득하게 된다. 

이러한 기능을 통해 견인차량들이 사고 정보를 더욱 쉽게 획득할 수 있도록 하고 우선권을 통해  
빠른 사고 현장 도착을 위한 경쟁이 줄어듬으로서 2차 사고와 혼잡 역시 줄어들 것을 기대할 수 있다. 

해당 프로젝트는 서버 구동에 관련된 패키지를 포함하며 클라이언트, DB와의 통신 API로부터 사고 데이터를 받아와 가공하는 기능을 한다. 



![흐름도2](https://user-images.githubusercontent.com/69377952/206194451-aa4ad0e0-71d3-4797-a402-052e6fa513de.png)
\<서비스 흐름도\>

## Language
> JAVA(JDK): version 17.0.4.1 \<Server\>

## Open Source, Open API
>Spring Boot framework: version '2.7.4'

>its Open API [<홈페이지>](https://www.its.go.kr/opendata/opendataList?service=event)


## Dependencies
> 'io.spring.dependency-management' version '1.0.14.RELEASE'

> 'org.springframework.boot:spring-boot-starter-data-jpa'

> 'org.springframework.boot:spring-boot-starter-web'

> 'junit:junit:4.13.2'

> 'org.projectlombok:lombok'

> 'org.springframework.boot:spring-boot-devtools'

> 'mysql:mysql-connector-java'

> 'org.projectlombok:lombok'

> 'org.springframework.boot:spring-boot-starter-test'

> 'org.json:json:20220924'

> 'com.h2database:h2:2.1.214'

> 'com.google.code.gson:gson:2.10'


## Facakage
`Controller` 클라리언트 통신을 위한 컨트롤러 클래스를 포함

`Service` 도메인 로직을 위한 서비스 클래스를 포함

`Repository` 데이터베이스 통신을 위한 리파지토리 클래스를 포함

`Data` 서버에서 데이터처리를 위해 사용하는 클래스를 포함

`Entity` 데이터베이스에 저장할 데이터 클래스를 포함 


## Files
`AccidentController.java`

`MatchingController.java`

`AccidentService.java`

`AccidentRepository.java`

`MatchingRepository.java`

`MemorySearchRepository.java`

`Search.java`

`Wait.java`

`Distance.java`
