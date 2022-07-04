# Komponenten

## Mockito

```Java
UI mockedUI=Mockito.mock(UI.class,Answers.RETURNS_DEEP_STUBS);
    Mockito.when(mockedUI.getSession().getAttribute("current_User"))
    .thenReturn(new UserDTO());
    try(MockedStatic<UI> utilities=Mockito.mockStatic(UI.class)){
    utilities.when(()->UI.getCurrent()).thenReturn(mockedUI);
    assertNotNull(SessionAttributes.getCurrentUser());
    }
```

Mockito fälscht hier den Output eines Programms.

- Der Singleton UI wird im Try With Resource Block mit einem Mock ersetzt.
- Dieser gibt dann über die getCurrent()-Methode eine Instanz der UI zurück.
- Diese ist ebenfalls ein Mock.

Potenzial hierbei:

- Die UI-Klasse wird nicht mehr verwendet.
    - Dadurch wird keine Sitzung benötigt.
- Nicht im aktuellen Testfall benötigte Methoden werden nicht aufgerufen.
    - Dadurch werden die Tests schneller und spezifischer.
- Die Unit-Tests testen dann sozusagen nur Units und keine Funktionalitäten.

## Selenium

## JUnit

Teilweise werden bei Junit Tests mit der Annotation

```Java
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
```

verwendet. Diese Annotation ermöglichen es, einen Test an einer bestimmten
Stelle in der Reihenfolge mit ``@Order(7)`` an stelle x (hier 7) zu fixieren.

# Frontend Tests

## Abhängigkeiten

Einige Frontend-Tests sind bereits mit Junit und Mockito abgedeckt.
Mockito hat hier teilweise spezifische Bestimmungen, welche
Klasse mit welchem Label als "Tab" generiert werden soll.
Wenn ein Neuer Menüpunkt
im [AppView](../../src/main/java/org/hbrs/academicflow/view/common/layouts/AppView.java)-Layout
erstellt wird, muss im zugehörigen Junit-Test ein
``when(...).thenReturn(...)``-Statement erstellt werden.
Dieses Statement sorgt dafür, dass für einen erwarteten
Aufruf der Tab-Util Methoden der Aufruf gelingt, wenn er
erwartungsgemäß aufgerufen wird. Derselbe Aufruf wird dann
jedoch einen Fehler auslösen, wenn er fehlerhaft ist.

# Spring und Löschungen

Wenn es in einem ``@SpringBootTest`` zu einer Löschung kommt, muss
dieser Test mit der Annotation ``@Transactional`` versehen werden.
Nur dann weiß Spring, dass der Test einen Transaktions-Service bekommt.
Dieses Feature ist bei Spring nicht sonderlich gut dokumentiert,
das Verhalten kann durch gutes Loggen bei Löschfehlern erkannt werden.