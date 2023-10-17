# Kerims inlämning
## Projektrapport: Systemintegration
### Deltagare: Kerim Kozo

### Projektbeskrivning
Jag har skapat en applikation där en User kan genom ett API lägga till personer i en lista som både sparas lokalt på kafka servern men också lokalt i en MySQL databas.
Jag har en producer som skickar informationen till min kafka server samt två konsumers som lyssnar på min kafka server. En av konsumerna skriver ut informationen till min databas i form av en logg,
den andra sparar informationen i min databas. Min properties fil är konfiguerad för att kunna köra på localhost:9092 för mina konsumenter & producer. Den innehåller även inloggningsuppgifter för att kunna ansluta till min lokala databas samt
databasen som kommer att användas för projektet. Min hibernate satte jag på update så att den skapar mina scheman men modifierar inte dem. Min logging nivå satte jag på Error då jag försökte minimera alla loggar i spring & spring test
för att få en bättre överblick. I mitt kafka kluster valde jag att köra på det som var förinställt från min kafka mapp. Jag har inte lagt någon större vikt på att fördela mina partitions för en parallel bearbetning,
skapat replikor eller att skapa en backup av mina topics. Projektet handlar mer om att få igång ett program som använder en grundläggande CRUD funktionalitet med hjälp av Apache Kafka & Spring boot.


### Vem har gjort vad?
Jag arbetade ensam.

### Planering & Genomförande
Min plan var att vidareutveckla exempelkoden som vi fick under kursen då mitt syfte är att faktiskt lära mig vad det är jag håller på med. Mitt arbete bestod utav att
visualisera det jag ville uppnå och försöka skapa det genom kod. Jag började med att färdigställa min springboot applikation så att jag kunde få Get & Post att fungera med postman.
Därefter skapade jag en modul för att använda mig utan apache clients så att jag inte behövde använda postman. När jag fick det att fungera skapade jag tester & la till en error handler.
Jag hade väldigt många olika planer under arbetets gång men då jag hade väldgt mycket problem med att få det som jag ville så fick jag nöja mig med att få det att fungera.

### Utmaningar & Exempel
Det var ett flertal utmaningar med detta projekt. Min tankeresa började med att jag ville få usern att browsa till localhost där den fick en flashig frontend där Usern kunde få get requests genom att trycka på
knappar jag hade skapat, samt nyttja post genom att lägga till användarnamn & lösen. Men jag märkte snabbt att jag hade för lite frontend kunskap för att få allt att fungera samt då jag fick det förmedlat att allt skulle ske via terminalen
så ändrade jag mig istället till att köra med apache client & lägga den ideen åt sidan. Till en början var det enkelt att få sätt upp & köra sin kafka Server. Därefter blev det mer komplext när vi la till en modul för att använda apache client.
Tanken med apache clients var att ha en modul som interragerar med användaren där man kan skicka HTTP requests genom en lista av användarvänliga alternativ. Jag hade så mycket problem med att få modulerna till att fungera
att jag vid sidan om försökte göra ett dubblearbete genom att köra apache client inom springboot. Arbetet blev väldigt rörigt men som tur var lyckades jag få modulerna att fungera. Eloge till Sameer som spenderade halva sin kväll med mig för att få modulerna att fungera!

En utmaning jag hade var att uppdatera id:et i databasen vid för en ny post eller delete. Jag listade ut att jag kan manuellt ställa in mitt ID baserat på antal users som redan finns i databasen & addera ett. Dock att uppdatera befintliga ID:n
vid en delete var en riktigt utmaning. Jag hade flera sätt som jag försökte få den till att fungera effektivt men det slutade med att jag tog en enkel men ineffektiv lösning vilket är att ta bort allt som finns in databasen, därefter
lägga till alla users som inte togs bort, samt decrementa alla ID:n efter den borttagna usern. En väldigt ineffektiv lösning men den fungerar.

## Slutsatser
### Vad gick bra/dåligt & vad hade du kunnat gjort annorlunda?
Jag lyckades åstadkomma en fungerande app vilket jag är nöjd med. Dock finns där delar där man hade kunnat finputsa och snygga till koden vilket blir ett arbete framöver. Det svåraste för mig i detta projekt var att jag inte fick mina moduler att funka.
Min upplevelse i detta projekt var ungefär såhär, 10 % faktiskt kodning & 90 % felsökning. 
Förutom problem med moduler stötte jag på ett par andra problem. T.ex lyckades jag inte vid ett tillfälle skriva ut alla users från databasen via min app modul utan enbart printa den i spring boot.
Vid en närmare undersökning så kom infon tillbaka som tom/null. Jag spenderade en hel kväll med flera olika lösningar men den ville bara inte föra över informationen. Jag skapade till och med en separat klass
för att skriva över informationen om alla users när jag gör mitt API anrop men det fungerade heller inte. Jag är dock väldigt nöjd med min error handler, inte nog med att jag med en simpel kod kan hantera alla errors som kommer till min spring boot. Jag slapp även skriva en massa kod i min andra modul och kunde nu iställt bara
printa response.body()för att få ut informationen. Fungerade allt fick jag infon jag ville ha, blev det en error kom infon från error handlern istället. Det jag dock valde att göra i mina metoder i HTTP klassen var att
lägga in en try catch då jag tycker det är en extra säkerthet "ifall att" samt så slipper jag skriva throws ... i mina metoder.

### Lärdomar & möjligheter
Jag har fått insikt i hur man arbetar med Apache Kafka & Spring boot som jag hoppas att jag kommer kunna nyttja framöver.
Nu är det upp till mig att vidare utforska och skriva mer avancerad kod. Denna kunskap som jag har fått från kursen kommer jag ha nytta av när jag söker framtida tjänster som efterfrågar
denna kunskap, då vet jag vad rollen innebär och vad jag kan erbjuda. Det största "problemet" som jag dock har märkt under min tid som kodare är att jag inte har problem med min kreativitet
eller att skriva kod, utan jag fallerar alltid på min begränsade kunskap om hur koden fungerar i bakgrunden. Många gånger får man inte det resultated man förväntade sig, i vissa fall leder
erroren till en förståelse och lösning, andra gånger ett hopplöst sökande efter en lösning. Jag är dock väldigt nöjd med min insats baserat på den tid jag har en möjlighet att lägga på projektet.
Kan bara föreställa mig vilket skillnad det är när man har tid, ett team & en mentor på plats som på ett arbete.
