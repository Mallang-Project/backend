# ☁️ Mallang ☁️
### 감정 기반 영화 추천 & 마음처방전

<table>
<tr>
<td width="220px">

<img width="200" alt="image" src="https://github.com/user-attachments/assets/0d997178-9479-4807-9126-964373460500" />

</td>
<td>

**🌟 약 200명 사용자 피드백 반영**

**🌟 영남대학교 도전학기제 선발 프로젝트로 전공 6학점 인정 + 장학금 수혜**

**🌟 한 학기 동안 기획 → 개발 → 배포 → 실사용자 피드백 → 3차 리팩토링 사이클 완주**

</td>
</tr>
</table>

<br>

## 🎬 프로젝트 소개
- **기간**: 2025.03 ~ 2025.05
- **개발 인원**: 백엔드 2명 / 프론트엔드 1명
- **목표**: 테스트를 통해 사용자의 현재 감정을 분석하고, 그 감정과 어울리는 영화와 AI 마음처방전을 제안
- **핵심 가치**
  - 정확한 추천: 감정·성향 중심의 가중치 추천
  - 정서적 위로: GPT 기반 한마디 생성
  - 공유 경험: 카카오톡 링크 카드로 간편 공유
- **서비스 주소**: **https://malang-delta.vercel.app**

<br>

## 🔧 기술 스택

| 구분 | 백엔드 | 프론트엔드 |
|------|--------|-------------|
| **언어** | ![Java](https://img.shields.io/badge/Java-007396?style=flat&logo=java&logoColor=white) | ![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=javascript&logoColor=black) ![HTML](https://img.shields.io/badge/HTML-E34F26?style=flat&logo=html5&logoColor=white) ![CSS](https://img.shields.io/badge/CSS-1572B6?style=flat&logo=css3&logoColor=white) |
| **프레임워크** | ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=springboot&logoColor=white) | ![React](https://img.shields.io/badge/React-61DAFB?style=flat&logo=react&logoColor=black) |
| **인프라** | ![EC2](https://img.shields.io/badge/EC2-F58536?style=flat&logo=amazonaws&logoColor=white) ![RDS](https://img.shields.io/badge/RDS-527FFF?style=flat&logo=amazonrds&logoColor=white) ![Nginx](https://img.shields.io/badge/Nginx-009639?style=flat&logo=nginx&logoColor=white) ![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF?style=flat&logo=githubactions&logoColor=white) | ![Vercel](https://img.shields.io/badge/Vercel-000000?style=flat&logo=vercel&logoColor=white) | |

**협업  :**  ![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat&logo=github&logoColor=white) ![Notion](https://img.shields.io/badge/Notion-000000?style=flat&logo=notion&logoColor=white) ![Figma](https://img.shields.io/badge/Figma-F24E1E?style=flat&logo=figma&logoColor=white) ![Discord](https://img.shields.io/badge/Discord-5865F2?style=flat&logo=discord&logoColor=white)

<br>

## ✨ 주요 화면 및 기능
### 1) 감정 분석 설문
- 여러 테스트 질문을 통해 사용자 감정 분석
- 답변에 따라 사용자에게 태그 생성
<p align="left"> <img src="https://github.com/user-attachments/assets/bf71f97f-fcfe-43cb-b5be-396accdf9214" width="150"/> <img src="https://github.com/user-attachments/assets/db290b94-a81a-47dd-9aea-af4f7acf0660" width="150"/> <img src="https://github.com/user-attachments/assets/5a9d61f1-a37a-408a-9b20-e2674be7e9ac" width="150"/> <img src="https://github.com/user-attachments/assets/4762946f-4abe-46be-bd78-72093d3056e9" width="150"/> </p>

### 2) 영화 추천 (TMDB 연동)
- **TMDB API**로 영화 메타데이터 수집 ->  번역/요약 후 **감정·성향 태깅**  
- 태그 점수식 가중치 ->  상위 후보 중 **랜덤 3편** 노출
<p align="left"> <img src="https://github.com/user-attachments/assets/9c1524cb-2531-4fcd-b4ed-5de941b15bde" width="200"/> <img src="https://github.com/user-attachments/assets/0ff55b61-9b8d-497b-9ac4-f5ca951b7272" width="200"/> </p>


### 3) AI가 전하는 마음처방전
- 사용자 감정 태그를 포함한 프롬프트로 **GPT**가 따뜻한 문장을 생성
<img width="200" height="300" alt="image" src="https://github.com/user-attachments/assets/5269a664-63a9-4579-b511-e14cd8634cb5" />

### 4) 카카오톡 공유
- 추천 결과(영화+메시지)를 **정적 HTML**로 저장 → **카카오 링크 카드** 공유  
<img width="200" height="300" alt="image" src="https://github.com/user-attachments/assets/df5ba674-4f5e-42eb-96ef-87b6c45746c5" />

### 5) 리뷰/피드백 작성
- 이용 직후 **간단 평가/의견** 수집 플로우 제공 -> 관리자 페이지에서 확인
<p align="left"> <img src="https://github.com/user-attachments/assets/911da6f6-ae0c-493e-8fcd-1a8a246929dc" width="200"/> <img src="https://github.com/user-attachments/assets/3b5ac97d-d04e-46ad-b8c0-ed96e6383209" width="300"/> </p>
