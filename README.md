![intro image](assets/intro-image.png)

# 🎀 AI Java

> *귀여운 Java 프로젝트 - 계산기와 다이어리의 완벽한 조화*

```
    ✨ 🎀 나의 다이어리 + 계산기 🎀 ✨
    
    귀엽고 실용적인 Java 애플리케이션
```

---

## 📱 프로젝트 소개

**AI Java**는 일상의 순간들을 기록하고, 간단한 계산을 도와주는 귀여운 Java 애플리케이션입니다.

✨ **주요 기능:**
- 📝 **나의 다이어리**: 소중한 순간들을 기록하고 관리
- 🧮 **계산기**: 편리한 계산 기능
- 💾 **데이터 저장**: 작성한 다이어리 자동 저장

---

## 🏗️ 프로젝트 구조

```
ai-java/
│
├── 📁 src/main/java/com/aicore/
│   ├── 📝 Diary.java           # 다이어리 핵심 기능
│   ├── 🧮 Calculator.java      # 계산기 기능
│   ├── 💾 DataManager.java     # 데이터 저장/로드
│   ├── 🎨 UI.java             # 사용자 인터페이스
│   └── 🚀 Main.java           # 메인 실행 클래스
│
├── 📁 src/test/java/
│   ├── DiaryTest.java
│   ├── CalculatorTest.java
│   └── DataManagerTest.java
│
├── 📁 data/                    # 저장된 다이어리 데이터
├── pom.xml                     # Maven 설정
├── README.md                   # 이 파일
└── LICENSE                     # 라이센스
```

---

## ✨ 주요 기능

### 📝 다이어리 ��능

작성한 일기를 날짜별로 저장하고 관리합니다.

```java
// 다이어리 작성 예제
Diary diary = new Diary();
diary.addEntry(new Date(), "오늘도 행복한 하루였어! 🌟");
diary.save();
```

**지원하는 기능:**
- ✏️ 새로운 일기 작성
- 🔍 날짜별 검색
- 📖 저장된 일기 조회
- 🗑️ 일기 삭제

---

### 🧮 계산기 기능

기본 산술 연산과 고급 계산을 지원합니다.

```java
// 계산기 사용 예제
Calculator calc = new Calculator();
double result = calc.add(10, 5);        // 15
result = calc.multiply(3, 4);           // 12
result = calc.divide(20, 4);            // 5
```

**지원하는 연산:**
- ➕ 덧셈
- ➖ 뺄셈
- ✖️ 곱셈
- ➗ 나눗셈

---

## 💻 설치 및 실행

### 요구 사항
- Java 11 이상
- Maven 3.6 이상

### 설치 단계

```bash
# 프로젝트 클론
git clone https://github.com/zieeeuun/ai-java.git
cd ai-java

# 의존성 설치
mvn clean install

# 프로젝트 실행
mvn exec:java -Dexec.mainClass="com.aicore.Main"

# 테스트 실행
mvn test
```

---

## 📚 사용 예제

### 다이어리 작성

```java
import com.aicore.Diary;

public class DiaryExample {
    public static void main(String[] args) {
        Diary diary = new Diary();
        
        // 새 일기 작성
        diary.addEntry("2026-05-08", "오늘은 날씨가 정말 좋았어! ☀️");
        diary.addEntry("2026-05-09", "친구들과 만나서 즐거웠어! 😊");
        
        // 저장
        diary.save();
        
        // 전체 일기 조회
        diary.displayAll();
    }
}
```

### 계산기 사용

```java
import com.aicore.Calculator;

public class CalcExample {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        
        System.out.println("10 + 5 = " + calc.add(10, 5));
        System.out.println("10 - 5 = " + calc.subtract(10, 5));
        System.out.println("10 × 5 = " + calc.multiply(10, 5));
        System.out.println("10 ÷ 5 = " + calc.divide(10, 5));
    }
}
```

---

## 🎨 설계 철학

✨ **귀여움** - 따뜻하고 친근한 디자인  
🎯 **실용성** - 일상에서 바로 쓸 수 있는 기능  
📦 **간결함** - 복잡하지 않고 직관적인 구조  

---

## 🤝 기여 가이드

기여를 환영합니다! 😊

1. Fork this repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## 📝 라이센스

MIT 라이센스 - 자유롭게 사용하세요!

---

## 👤 작성자

**zieeeuun** 💕

- GitHub: [@zieeeuun](https://github.com/zieeeuun)
- 프로젝트 완성: 2026-05-08

---

## 💬 문의

질문이나 피드백은 [Issues](https://github.com/zieeeuun/ai-java/issues)에서 가능합니다! 🌟

---

```
🎀 ✨ 매일 조금씩 더 귀여워지는 프로젝트 ✨ 🎀

"다이어리에 기록하고, 계산기로 계산하고,
 행복한 하루를 만들어보세요!"

Made with 💕 and ✨ by zieeeuun
```
