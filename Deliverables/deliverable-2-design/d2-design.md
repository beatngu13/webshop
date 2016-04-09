# Aufteilung
- Login Service
- User Service
- Category Service
- Product Service

# Login Service
- Benutzer
- Passwörter

## Request
- Benutzername
- Passwort

## Response
- Token (https://jwt.io/)

# User Service
- Benutzer
- Rollen

## Request
- Token

## Response
- User Objekt
- User Objekt Array

## Endpunkte
| Verb | URI | Parameter | Beschreibung |
|------|-----|-----------|--------------|
| GET | /user |  | Gibt alle Benutzer zurück |
| GET | /user/{id} |  | Gibt genau einen Benutzer zurück |
| POST | /user |  | Fügt einen neuen Benutzer hinzu |
| POST | /user/{id} |  | Ändert einen bestehenden Benutzer |

# Category Service
- Kategorie

## Request
- Token

## Response
- Category Objekt
- Category Objekt Array

## Endpunkte

# Product Service
- Produkt

## Endpunkte

## Request
- Token

## Response
- Product Objekt
- Product Objekt Array