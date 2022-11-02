# Zeiten

Für Zeiten verwenden wir Instant.

```Java
Instant.now(); // Gibt uns die aktuelle Systemzeit in Millisekunden.
```

# Buttons

Alle Buttons werden mit der Util-Klasse erstellt.

Die Klasse org.hbrs.academicflow.view.common.components.LabeledButtonCollection ist eine
Klasse, mit welcher man standardisierte Buttons erstellen kann.
<!-- Kleines Beispiel --> 
Zum Beispiel kann man einen Button wie folgt anlegen:

````java

// für einen Button ohne Link
Button clickable=LabeledButtonCollection().defaultButton("Click me");
// für einen Button mit Link
    Button urlButton=LabeledButtonCollection().generalButton("Click me","url");

````