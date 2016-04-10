---
title: Deliverable 1 – Analyse der Legacy Anwendung
author:
    - Simon Blum
    - Johannes Dillmann
    - Julian Keppel
    - Daniel Kraus
documentclass: scrartcl
urlcolor: blue
...

Die vorliegende Dokumentation befasst sich mit der bereitgestellten Legacy-Anwendung des Webshops. Es wird sowohl die Struktur und Architektur der Anwendung, sowie das Verhalten und die Kommunikation zwischen den Komponenten beleuchtet.

# Strukturelle Analyse #

## Architektur der Anwendung ##
Die gesamte Anwendung ist als monolitische Struktur in einem gemeinsamen Archiv gebündelt. Sie ist in mehrere Schichten eingeteilt, welche jeweils in Package-Ebene voneinander getrennt sind. Der View-Layer wird durch JSP-Files und dem Struts-2-Framework implementiert. Von hier aus werden die Requests vom Browser an die jeweiligen Controller-Instanzen geleitet. Die Controller erzeugen die jeweils benötigten Manager-Instanzen aus dem Business-Logic-Layer. Von hier aus gelangen die Aufrufe zu den Datenzugriffsobjekten (DAO), welche die Verbindung zur Datenbank durch das JPA-Framework Hibernate kapseln. 

Die folgende Abbildung zeigt die Architektur und alle Hauptkomponenten im Überblick:

![Image](diagrams/ComponentDiagram.png)

## Klassenstruktur ##
Die Struktur der Klassen bildet im Wesentlichen das MVC (Model View Controller) Architekturmuster ab. Eine Ausnahme stellen dabei jedoch die Views dar, da diese als JSPs (Java Server Pages) vorliegen. Die Controller liegen im gleichnamigen Paket und stellen, im Kontext von Struts 2, jeweils Actions dar. So existiert beispielsweise für das Anlegen einer Kategorie die Klasse `AddCategoryAction` oder für das Abrufen von Details zu einem Produkt die Klasse `ProductDetailsAction`. Nach diesem Schema sind alle Aktionen auf einen bestimmten Controller abbildbar. 

Der Zugriff auf die Datenbank beziehungsweise die Model Schicht erfolgt über die Klassen im Paket `model`. Alle notwendigen Entitäten wie Benutzer, Kategorie oder Produkte sind als eigenständige Klassen gekapselt. Die Interaktion mit diesen Elementen geschieht, auf Ebene der Geschäftslogik, über diverse Manager-Klassen. Diese Logik ist wiederum in ein spezifisches Interface sowie eine zugehörige Implementierung geteilt. Für Produkte existiert dabei das Interface `ProductManager` und die zugehörige Implementierung `ProductManagerImpl` welche die Zugriffe entsprechend kapselt. Der Zugriff auf die Datenbank erfolgt über, jeweils zu den Elementen passenden, DAO Klassen. Die Klasse `GenericHibernateDAO` implementiert selbst das Interface `IGenericDAO` und stellt eine Basisklasse dar, welche durch entsprechende Spezialisierungen erweitert wird. Als Beispiel sei hierbei die Klasse `CategoryDAO` genannt, welche für den Zugriff auf `Category` Objekte aus der Datenbank zuständig ist. 

## Datenmodell ##
Das folgende Entity Relationship Modell repräsentiert das Datenmodell der Legacy Anwendung. Es existieren vier Datenobjekte, welche die Kunden(`Customer`), die Rolle des einzelnen Kunden(`Role`), Kategorien(`Category`) und Produkte(`Product`) darstellen. Dabei kann ein Kunde lediglich eine Rolle einnehmen. Ein Produkt kann alleinig einer Kategorie zugehörig sein. Der Zusammenhang zwischen Kunde und Produkt spiegelt eine `1:n` Beziehung dar, das heißt ein Kunde kann beliebig viele Produkte erwerben.

![Image](diagrams/ER_Diagram_VIS_WebShop.png)

# Analyse des Verhaltens #

## Formale Spezifikation ##
TODO

## Ablauf eines Beispiel-Workflows ##
Anhand des Workflows "Produkt hinzufügen" wird exemplarisch die Kommunikation sowie das Verhalten der einzelnen Komponenten gezeigt. Es fällt auf, dass offensichtlich bei jeder Anfrage ein neuer `ProductManager`, `CategoryManager` sowie die zugehörigen Datenzugriffsobjekte erstellt werden. 

Im folgenden Sequenzdiagramm ist der beispielhafte Workflow innerhalb der gesamten Anwendung abgebildet:

![Image](diagrams/SequenceDiagram_DddProduct.png)
