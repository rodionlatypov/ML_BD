# ML Big Data: Home Assignment 2

**Скриншоты лежат здесь**: https://github.com/rodionlatypov/ML_BD/tree/HA2/Screenshots

## Блок 1

**Развернул локальный Hive-сервер**

![Hive](https://github.com/rodionlatypov/ML_BD/blob/HA2/Screenshots/Hive.png)

**Подключился к нему через Hue**

![Hue](https://github.com/rodionlatypov/ML_BD/blob/HA2/Screenshots/Hue.png)

**Подключился к нему через JDBC**

![Beeline](https://github.com/rodionlatypov/ML_BD/blob/HA2/Screenshots/Beeline.png)

## Блок 2

### 1)

**Взгляд на таблицу через JDBC**

![DB through jdbc](https://github.com/rodionlatypov/ML_BD/blob/HA2/Screenshots/Database%20in%20beeline.png)

**Через Hue**

![DB in Hue](https://github.com/rodionlatypov/ML_BD/blob/HA2/Screenshots/Database%20in%20hue.png)


**Пример запроса**

![Query example](https://github.com/rodionlatypov/ML_BD/blob/HA2/Screenshots/Query%20example.png)


### 2)

a)

select artist_mb
from artists
where scrobbles_lastfm in (SELECT max(scrobbles_lastfm) FROM artists)

 
 	artist_mb
1	The Beatles


b)

select a.col, count(a.col) as cnt
from (select explode(split(tags_lastfm, ';')) from artists) a
group by a.col
order by cnt desc


seen live	81278


d) Страна исполнителя с максимальным числом слушателей на last_fm

select country_mb
from artists
where listeners_lastfm in (SELECT max(listeners_lastfm) FROM artists)


country_mb
 

 	country_mb
1	United Kingdom

