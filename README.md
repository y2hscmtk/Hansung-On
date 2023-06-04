# HansungOn
안드로이드 프로그래밍 기말 대체 과제

HansungOn은 Kaggle 영화 데이터셋(https://www.kaggle.com/datasets/rounakbanik/the-movies-dataset )에서 다운받은
약 5만개 가량의 영화 메타데이터 movies_metadata.csv를 이용하여 만든 어플리케이션이다.

OTT앱에서 실제 영상 시청기능을 제외한 나머지 기능들을 구현하였으며 아래의 조건들을 만족한다.

[메인 페이지]
- 인기순 Top 20 영화 리스트 (평점 개수/평균 평점의 내림차순으로 재정렬이 가능
- 최소 3개 장르 이상의 Top 20 영화 리스트 (장르의 선택은 자유롭게 가능함) 
- 메인 페이지, 영화검색 페이지, 영화 상세보기 페이지 최소 3개의 탭을 포함해야한다. 
(아무 영화도 선택되지 않을 경우 영화 상세보기 페이지는 평균 평점이 가장 높은 영화를 기본으로 보여주면 됨)

![1](https://github.com/y2hscmtk/HansungOn/assets/109474668/d8972226-f02a-4804-8bc4-ea8959bc6787)

https://github.com/y2hscmtk/HansungOn/assets/109474668/d767fdc5-6585-4ee5-bd2e-5c380e50c74a

[영화검색 페이지] 
- 영화검색 페이지는 최소 영화명과 배우명을 입력하는 텍스트필드와, 검색을 수행하는 버튼으로 구성되어야 한다. 
- 영화명과 배우명을 입력하면 그와 일치하는 영화 리스트가 movies_metadata.csv 파일의 vote_average의 내림차순으로 반환되어야 한다. 
- 검색된 영화의 snippet (검색 결과의 설명 또는 요약)은 반드시 영화의 포스터, 제목, 아이디, overview의 첫 번째 문장이 반환되야 한다. 
Overview의 첫 문장이 너무 길다면, 문장의 뒷 부분은 적절히 생략하여 나타내야 한다. 
- 최대 반환되는 영화의 개수는 5개이다. 
- 검색된 영화를 터치하면 영화 상세보기 페이지로 이동할 수 있어야 한다.

![2](https://github.com/y2hscmtk/HansungOn/assets/109474668/c454a1c2-4a86-4934-9270-2ef376a1c515)

https://github.com/y2hscmtk/HansungOn/assets/109474668/0778bffe-32d4-49ef-83ab-d4d3a03143f6

[영화 상세보기 페이지] 
- 영화 상세보기 페이지는 네이버 시리즈온의 영화 상세보기 페이지
(예: https://m.serieson.naver.com/v2/movie/563014)를 참조하여 유사하게 디자인하며, 
TMDB사이트 (예: https://www.themoviedb.org/movie/862-toy-story)를 참조하여 주어진 데이터셋 에서 활용 가능한 정보를 최대한 활용하여야 한다. 
- 해당 영화에 대한 IMDB의 10개의 user review를 ‘Featured’ 순으로 볼 수 있어야 한다. 
(HINT: links.csv 파일을 활용하여 IMDB에 접근하라) user review는 IMDB에서 scraping 하여 파일 형태로 저장한 후 출력해도 되고 실시간으로 IMDB에 접근하여 출력해도 관계 없음. 
- 새로운 평점을 남기는 기능을 가지고 있어야 한다. 
- 포스터 이미지는 영화에 상관없이 이클래스에 업로드 된 sample_poster 이미지로 통일하여 사용하라. => 파이썬 BeautifulSoup을 이용하여 100개 이미지를 다운받아 사용하였음

[부가기능] 
- 선택된 영화와 유사한 10개 이하 의 영화를 추천하는 기능
(HINT: https://github.com/tdebatty/java-string-similarity#cosine-similarity
를 활용하면 두 영화 plot이 얼마나 유사한지 구할 수 있다. 
(제시된 오픈소스 이외에 다른 오픈소스 소프트웨어를 사용하거나 직접 구현하는 것이 허용되나, 오직 코사인 유사도(https://wikidocs.net/24603)에 기반하여 구현되어야 한다.)
- 네이버 시리즈온과의 디자인 테마 유사성

![3](https://github.com/y2hscmtk/HansungOn/assets/109474668/c3757730-c90b-4315-80af-2ef20c4556ab)

https://github.com/y2hscmtk/HansungOn/assets/109474668/0c0cd3b1-c8df-4fe3-8c6d-f3b3937e2fc2






