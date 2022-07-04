# **Szenarien (Anlehnung an MockUps)**
## Unternehmer- & Studierendensicht
#
## **Szenario 1: Registrieren** 
### **Vorbedingung:**
Die Website wurde aufgerufen
### **Normaler Ablauf:**
Ich habe noch kein Konto, daher klicke ich auf den Button "Not a user yet? Register now!" auf der erschienen Landing-Seite. 
Ich werde auf eine weitere Seite zum Registrieren weitergeleitet auf der ich in Felder meine Angaben eintragen kann. Anschließend klicke ich auf "registrieren".
### **Nachbedingung**
Die Daten, welche bei der Registrierung eingetragen wurden, werden in der Datenbank gespeichert (Sprint 1) und es wurde eine E-Mail zur Bestätigung an den Benutzer geschickt (Sprint 2).
<br/><br/>

## **Szenario 2: Einloggen in die Anwendung**
### **Vorbedingung**
Ein Konto existiert bereits nach der Registrierung und die Login-Seite ist aufgerufen
### **Normaler Ablauf:**
Ich trage meine E-Mail als auch mein Passwort in die vorgegebenen Felder ein und drücke den Button "Login". 

### **Nachbedingung**
Der Benutzer wird auf die Startseite für angemeldete Nutzer weitergeleitet. 
<br/><br/>

## **Szenario 3: Ausloggen aus der Anwendung**
### **Vorbedingung**
Der Benutzer ist eingeloggt
### **Normaler Ablauf:**
Ich klicke auf den Button oben rechts in der Leiste auf "Log Out".
<br/>

### **Nachbedingung**
Der Benutzer wird aus seinem Account abgemeldet und wird auf die Landing-Seite weitergeleitet.
<br/><br/>

## **Szenario 4: Profil bearbeiten (Unternehmersicht)**
### **Vorbedingung**
Der Benutzer ist eingeloggt
### **Normaler Ablauf:**
Ich klicke auf den Button "Mein Konto" und gelange damit auf eine Seite bei der ich optional viele Felder (wie Name, Vorname, Telefonnummer etc.) ausfüllen kann und ein Profilbild einstellen kann. Anschließend klicke ich unten auf den Button "speichern".

### **Nachbedingung**
Die geänderten und ergänzten Profildaten werden auf der Datenbank gespeichert. 
<br/><br/>


## **Szenario 5: Stellenanzeigen anzeigen lassen (Studierendensicht)**
### **Vorbedingung**
Der Studierende ist eingeloggt und auf der Startseite
### **Normaler Ablauf:**
Ich klicke auf den Button "Stellenanzeigen suchen" und gelange damit auf eine Seite bei der ich alle Stellenanzeigen als Balken aufgelistet sehe. Somit habe ich ein kurzes Preview von jeder Stellenanzeige. Sobald ich eine Stellenanzeige anklicke, werde ich auf einer weitere Seite geleitet auf der ich mir die Stellenanzeige im Detail anzeigen lassen kann. 

### **Nachbedingung**
Sobald ich zurück in die Listenansicht geklickt wird, gelangt der Studierende an die Stelle der Liste, wo er zuletzt aufgehört hat zu suchen.
<br/><br/>

## **Szenario 6: Stellenanzeigen anzeigen lassen (Studierendensicht)**
### **Vorbedingung**
Der Studierende ist eingeloggt und auf der Startseite
### **Normaler Ablauf:**
Ich klicke auf den Button "Stellenanzeigen suchen" und gelange damit auf eine Seite bei der ich alle Stellenanzeigen als Balken aufgelistet sehe. Somit habe ich ein kurzes Preview von jeder Stellenanzeige. Sobald ich eine Stellenanzeige anklicke, werde ich auf einer weitere Seite geleitet auf der ich mir die Stellenanzeige im Detail anzeigen lassen kann. 

### **Nachbedingung**
Sobald zurück in die Listenansicht geklickt wird, gelangt der Studierende an die Stelle der Liste, wo er zuletzt aufgehört hat zu suchen.
<br/><br/>

## **Szenario 7: Als Student kann ich nach Stellenanzeigen suchen (Studierendensicht)**
### **Vorbedingung**
Der Studierende ist eingeloggt und auf der Jobanzeigenseite
### **Normaler Ablauf:**
Ich sehe diverse Stellenanzeigen von Unternehmen. Ich kann in dem Suchfeld nach einer Stellenanzeige suchen. Dafür gebe ich ein Stichwort in das Feld ein und drücke auf den Button "suchen". Finde ich eine passende Anzeige kann ich diese im Detail angucken.

### **Nachbedingung**
Möchte ich mein Suchstichwort ändern, weil keine passende Anzeige für mich dabei war, so gebe ich ein neues Stichwort ein und drücke wieder auf "suchen".
<br/><br/>

## **Szenario 8: Als Student kann ich Unternehmen bewerten (Studierendensicht)**
### **Vorbedingung**
Der Studierende ist eingeloggt und auf der Jobanzeigenseite
### **Normaler Ablauf:**
Wenn ich ein Unternehmen bewerten will, klicke ich zuerst auf den Button "Profil von (Unternehmen)". Dort wird mir eine genaue Beschreibung des Unternehmens angezeigt (Unternehmensprofil). Zusätzlich ist darunter ein Button "Jetzt Bewertung verfassen". Wenn ich auf den klicke, kann ich das Unternehmen mit Sternen von 1 (sehr schlecht) - 5 (sehr gut) und einem Kommentar bewerten. Danach drücke ich auf den Button "Bewertung absenden", um meine Bewertung abzuschließen.

### **Nachbedingung**
Habe ich ein Unternehmen bewertet, so sehe ich die durchschnittliche Bewertung in Sternen. Zusätzlich wird angezeigt, wie viele Benutzer ein Unternehmen bewertet haben.


