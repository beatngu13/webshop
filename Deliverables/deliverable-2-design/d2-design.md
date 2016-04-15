# Überblick
- Login Service
- User Service
- Product Service

## Login Service
Nimmt Benutzername und Passwort entgegen und gibt einen Zugriffs-Token zurück. Dieser Token wird für die Authentifizierung gegenüber den anderen Services verwendet.

Der Dienst ist optional und wird als Stub umgesetzt.

### Endpunkte
| Verb | URI | Parameter | Beschreibung |
|------|-----|-----------|--------------|
| POST | /login |  | Führt den Login-Vorgang aus |

### Request
Beispiel:

```javascript
{
    "user": "bob",
    "password": "1234"
}
```

### Response
Kodiert den Benutzernamen und die Berechtigung (Rolle) als JSON WEb Token (https://jwt.io/).

Beispiel:

```javascript
{
    "token": "abc.def.ghi"
}
```

## User Service
Dient dem Anlegen von Benutzern und der Rollen, wobei letzteres optional ist. Das Token wird jeweils im `Header` der Anfrage übermittelt.

### Endpunkte
| Verb | URI | Parameter | Beschreibung |
|------|-----|-----------|--------------|
| GET  | /user      | filter | Gibt alle, oder je nach Filter nur einige, Benutzer zurück |
| GET  | /user/{id} |  | Gibt genau einen Benutzer zurück |
| POST | /user      |  | Fügt einen neuen Benutzer hinzu |
| PUT  | /user/{id} |  | Ändert einen bestehenden Benutzer (optional) |
| DELETE  | /user/{id} |  | Löscht einen bestehenden Benutzer (optional) |

## Request/Response
Beispiel:
- Request: POST /user/bob

```javascript
{
    "user": "bob",
    "password" "1234",
    "role": "user"
}
```

- Response:

```javascript
{
    "id": "1234",
    "username": "hlüning",
    "fistname": "Horst",
    "lastname": "Lüning",
    "password": "1234"
    "rolen": "admin"
}
```

## Product Service
Dient dem Anlegen, Abfragen und Ändern von Produkten und Kategorien. Das Token wird jeweils im `Header` der Anfrage übermittelt.

### Endpunkte
| Verb | URI | Parameter | Beschreibung |
|------|-----|-----------|--------------|
| GET  | /category      | filter | Gibt alle, oder je nach Filter nur einige, Kategorien zurück (optional) |
| GET  | /category/{id} |  | Gibt genau eine Kategorie zurück (optional) |
| POST | /category      |  | Fügt einen neue Kategorie hinzu |
| PUT  | /category/{id} |  | Ändert eine bestehende Kategorie |
| DELETE  | /category/{id} |  | Ändert eine bestehende Kategorie |
| GET  | /product      | filter | Gibt alle, oder je nach Filter nur einige, Produkte zurück |
| GET  | /product/{id} |  | Gibt genau ein Product zurück |
| POST | /product      |  | Fügt eine neues Produkt hinzu |
| PUT  | /product/{id} |  | Ändert ein bestehendes Produkt (optional) |
| DELETE  | /product/{id} |  | Entfernt ein bestehendes Produkt |

Als Variation könnten auch über `/category` die Produkte der jeweiligen Kategorie zurückgegeben werden.

### Request/Response
Beispiel: GET /category/12

```javascript
{
    "id": "12",
    "name": "user",
    "products": ["a", "b", "c", "d", "e"] // Variante
}
```

Beispiel: GET /product/?name=Jameson

```javascript
{
    "products": ["1234", "765"]
}
```

Beispiel: GET /product/?minPrice=100

```javascript
{
    "products": ["12", "15", "45", "57", "96"]
}
```

Beispiel: GET /product/1234

```javascript
{
    "id": "1234",
    "name": "Jameson Limited Reserve 18 Jahre",
    "price": "79.90",
    "category": "whisky",
    "details": "Selten werden irische Whiskeys so alt abgefüllt. Dies ist eine Komposition lang gereifter irischer Whiskeys. Sicherlich einer der besten Whiskeys aus Irland."
}
```

Beispiel:
- Request: POST /product

```javascript
{
    "name": "Jameson Gold",
    "price": "49.90",
    "category": "whisky",
    "details": "Der Jameson Gold ist der hochwertigste Jameson in unlimitierter Abfüllung. Ein frischer Whiskey mit komplexen Pot Still Charakter, der durch eine Portion Sherryfass Whiskey abgemildert wird. Der Abgang ist lang und kräftig."
}
```
- Resonse (Optional auch nur ID)

```javascript
{
    "id": "765",
    "name": "Jameson Gold",
    "price": "49.90",
    "category": "whisky",
    "details": "Der Jameson Gold ist der hochwertigste Jameson in unlimitierter Abfüllung. Ein frischer Whiskey mit komplexen Pot Still Charakter, der durch eine Portion Sherryfass Whiskey abgemildert wird. Der Abgang ist lang und kräftig."
}
```