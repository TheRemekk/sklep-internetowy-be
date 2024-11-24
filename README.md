# Projekt "Sklep internetowy" - część backend

Stworzone z użyciem [Spring Boot](https://github.com/spring-projects/spring-boot) na wersji 3.1.X.  
Projekt tworzony był podczas kursu na witrynie Udemy pt. "(2024) Angular i Java Spring Od zera do Fullstack developera". Na bieżąco planuję rozwijać ten projekt i dodawać nowe możliwości lub zmieniać design.

Link do oryginalnego projektu stworzonego przez autora kursu [GitLab](https://gitlab.com/udemycourses3053026/projekt-wspolny-fe).

## Spis treści

1. [Opis](#l1)
2. [Technologie i funkcjonalności](#l2)
3. [Konfiguracja projektu](#l3)
4. [Struktura projektu](#l4)

<a id="l1"></a>
## Opis

Backend został opracowany przy użyciu Java Spring Boot, co zapewnia stabilną i skalowalną strukturę aplikacji.

<a id="l2"></a>

## Technologie i funkcjonalności
* Spring Boot: Framework użyty do stworzenia backendu i obsługi API REST.
* Docker: Używany do uruchomienia bazy danych PostgreSQL, co zapewnia izolację środowiska i łatwą konfigurację.
* Flyway: Automatyzuje migracje schematów bazy danych podczas uruchamiania aplikacji.
* FTP: Wymagany zewnętrzny serwer FTP, który należy skonfigurować ręcznie.

<a id="l3"></a>

## Konfiguracja projektu

Aby uruchomić ten projekt lokalnie, wykonaj poniższe kroki:

### 1. Klonowanie repozytorium
```bash
  cd <scieżka_w_której_chcesz_umieścić_projekt>
  git clone https://github.com/TheRemekk/sklep-internetowy-be
  ``` 

### 2. Uruchomienie bazy danych w Dockerze

```bash
  docker run --name sklepdb -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres
  ``` 

### 3. Ręcznie ustawienie FTP

Można to zrobić przykładowo z użyciem Filezilla Server. 
Domyślne ustawienia FTP z projektu to:

```bash
#FTP CONFIG
ftp.server=localhost
ftp.port=8001
ftp.username=sklep
ftp.password=12345678
ftp.origin=/home/sklep
``` 

Ważne! Pobieranie i wysyłanie danych do FTP nie używa SFTP więc w Filezilla Server należy ustawić w konfiguracji protokołów: "Explicit FTP over TLS and insecure plain FTP".

### 4. Pozostałe ustawienia  
Aby wszystko w projekcie było w pełni skonfigurowana należy jeszcze w module auth oraz order uzupełnić w application.properties.yml:
* E-mail na który będą przychodzić powiadomienia przy zakładaniu konta/zmiany hasła

```bash
#MAIL
notification.mail=przykladowy_mail@gmail.com
notification.password=fjfjdmusmvslfwis  
```

* Potrzebne jest oficjalne konto lub sandbox na PayU

```bash
#PAYU
payu.client-id= # ID klienta generowany przez PayU
payu.client-secret= # Klucz secret klienta generowany przez PayU
payu.client-key=  # Klucz klienta generowany przez PayU
```
<a id="l4"></a>
## Struktura projektu

W projekcie znajduje się kilka modułów z różnymi funkcjonalnościami:
* auth: Odpowiada za uwierzytelnianie i autoryzację użytkownika
* basket: Służy do tworzenia i modyfikacji koszyka na produkty
* eureka: Moduł obsługujący mikroserwisy, pozostałe moduły są tutaj rejestrowane i mają przypisany port
* gateway: Służy do obsługi wielu instancji w aplikacji
* file-service: Moduł do zapisywania i odczytywania zdjęć produktów z FTP
* product-service: Moduł do zarządzania produktami
* order: Służy do zarządzania zamówieniami, czyli tworzenia zamówień, obsługi dostawy i płatności z użyciem sandboxa PayU
* RegisterEndpointInformation: Służy do zarządzania endpointami w aplikacji
