# Kerims inlämning
## Projektrapport: Systemintegration
### Deltagare: Kerim Kozo

### Projektbeskrivning
Jag har skapat en applikation där en User kan genom ett API lägga till personer i en lista som både sparas lokalt på kafka servern men också lokalt i en MySQL databas.
Skriv dokumentation som beskriver ditt Apache Kafka-klusters
konfiguration och hur producenten och konsumenterna är
implementerade.



### Vem har gjort vad?
Jag arbetade ensam.

### Planering & Genomförande
Min plan var att vidareutveckla exempelkoden som vi fick under kursen då mitt syfte är att faktiskt lära mig vad det är jag håller på med. Mitt arbete bestod utav att
visualisera det jag ville uppnå och försöka skapa det genom kod. Jag började med att färdigställa min springboot applikation så att jag kunde få Get & Post att fungera med postman.
Därefter skapade jag en modul för att använda mig utan apache clients så att jag inte behövde använda postman. När jag fick det att fungera skapade jag tester & la till en error handler.
Jag hade väldigt många olika planer under arbetets gång men då jag hade väldgt mycket problem med att få det som jag ville så fick jag nöja mig med att få det att fungera.

### Utmaningar & Exempel
Det var ett flertal utmaningar med detta projekt. Min tankeresa började med att jag ville få usern att browsa till localhost där den fick en flashig frontend där Usern kunde få get requests genom att trycka på
knappar jag hade skapat, samt nyttja post genom att lägga till användarnamn & lösen. Men jag märkte snabbt att jag hade för lite frontend kunskap för att få allt att funger samt då jag fick det fömredlat att allt skulle ske via terminalen
så ändrade jag mig istället till att köra med apache clients & lägga den ideen åt sidan. med Till en början var det enkelt att få sätt upp & köra sin kafka Server. Därefter blev det mer komplext när vi involverade
Tanken med apache clients var att ha en modul som interragerar med användaren där man kan skicka HTTP requests genom en lista av användarvänliga alternativ. Jag hade så mycket problem med att få modulerna till att fungera
att jag vid sidan om försökte göra ett dubblearbete genom att köra apache clients inom springboot. Arbetet blev väldigt rörigt men som tur var lyckades jag få modulerna att fungera.
Eloge till Sameer som spenderade halva sin kväll med mig för att få modulerna att fungera!

## Slutsatser
### Vad gick bra/dåligt & vad hade du kunnat gjort annorlunda?
Jag lyckades åstadkomma en fungerande app vilket jag är nöjd med. Dock är den inte som jag hade önskat mig. Jag hade flera ideer som inte kunde möjliggöras, främst på grund utav att
jag inte hittade lösning på problemet. Jag hade till exempel vid ett moment lyckats skriva ut alla usern från databasen i spring boot men responsen kom tillbaka tom till min andra modul. När jag fick mina moduler till att funka försvann detta problemet vilket är väldigt märkligt.

jag skriva ut alla users från databasen via min spring boot konsoll men när jag försökte få samma print i min maven modul
kom den tillbaka som tom/null. Jag spenderade en hel kväll med flera olika lösningar men den ville bara inte föra över informationen. Jag skapade till och med en separat klass
för att skriva över informationen om alla users vid mitt API anrop på @Getmapping printAllUsersFromDB men det fungerade heller inte. Det är något bakomliggande som inte möjliggör att 
informationen förs över från spring till min maven modul. 

Jag är väldigt nöjd med min error handler, inte nog med att jag med en simpel kod kan hantera alla errors som kommer till min spring boot. Jag slapp även skriva en massa kod i min client modul och kunde nu istälelt bara
printa response.body()för att få ut informationen. Fungerade allt fick jag infon jag ville ha, blev det en error kom infon från error handlern istället. Det jag dock valde att göra i mina metoder i HTTP classen var att
lägga in en try catch då jag tycker det är en extra säkerthet "ifall att" samt så slipper jag skriva throws ... i mina metoder.

### Lärdomar & möjligheter
Jag har fått insikt i hur man arbetar med Apache Kafka & Spring boot som jag hoppas att jag kommer kunna nytta framöver.
Nu är det upp till mig att vidare utforska och skriva mer avancerad kod. Denna kunskap som jag har fått från kursen kommer jag ha nytta av när jag söker framtida tjänster som efterfrågar
denna kunskap, då vet jag vad rollen innebär och vad jag kan erbjuda. Det största "problemet" som jag dock har märkt under min tid som kodare är att jag inte har problem med min kreativitet
eller att skriva kod, utan jag fallerar alltid på min begränsade kunskap om hur koden fungerar i bakgrunden. Många gånger får man inte det resultated man förväntade sig, i vissa fall leder
erroren till en förståelse och lösning, andra gånger ett hopplöst sökande efter en lösning. Jag är dock väldigt nöjd med min insats baserat på den tid jag har en möjlighet att lägga på projektet.
Kan bara föreställa mig vilket skillnad det är när man har tid, ett team & en mentor på plats som på ett arbete.
