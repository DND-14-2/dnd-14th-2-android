## 요구사항
1. 가계부 상세화면(LedgerDetailScreen)에서 사용되는 앱바에서 수정 아이콘 버튼을 클릭하면, 가계부 수정화면(LedgerUpdateScreen)으로 이동한다.
2. 디자인은 가계부 생성화면(LedgerCreateScreen)에서 사용하는 것과 동일하다. 하지만 생성과 수정이라는 개념이 다르다.
3. 가계부 수정화면은 가계부 상세화면에서 사용한 LedgerId 값을 기반으로 로컬 데이터를 읽어와서 사용자에게 보여준다.
4. 수정과정에서도 생성화면과 동일하게 1, 2 번째 스텝이 있다.
5. 생성과정과 다른 것은 수정과정에서는 처음에 로컬 데이터를 읽어오기 때문에 uiState에 값들이 선택되어 있다.
6. 사용자는 이를 수정할 수 있다.
7. 뒤로가기 시, 생성과정과 같은 맥락으로 다이얼로그를 노출해서 뒤로가기를 처리한다. 다이얼로그의 타이틀은 "수정을 취소하시겠어요?" 디스크립션은"지금까지 내용들은 저장되지 않아요"이다.
8. 2번째 단계에서 입력완료 버튼이 활성화된 상태에서 입력을 완료하면, PUT ledgers/{ledgerId} API를 사용한다.
9. API 요구사항은 다음과 같다. ledgerId(Path), 
RequestBody는 다음과 같은 형식이다.
   {
   "amount": 15000,
   "type": "EXPENSE",
   "category": "FOOD",
   "description": "저녁",
   "occurredOn": "2026-01-24",
   "paymentMethod": "CREDIT_CARD",
   "memo": "수정된 메모"
   }
API 성공 시, RemoteLedger Data class 로 정의된 값들을 받는다.
10. API 성공하면 Room에 캐싱하도록 한다.
11. 저장에 성공하면 다시 가계부 상세화면으로 이동한다. 당연 수정된 데이터가 보여야하고 아마 Room 데이터를 observing 하니까 이 부분은 크게 고려할 필요는 없을 것 같다.
12. 구조는 Clean Architecture + MVVM + Repository Pattern + ErrorHandle in Presentation Layer 이다. 자세한 구조는 기존 코드에서 ledger 관련된 것들을 참고하면 된다.

참고). 함수 이름과 변수명 파일명 클래스명 등은 명시적으로 작성해줘.
파일 생성 시에도 패키지 위치를 고려해줘.