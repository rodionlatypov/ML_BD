# ML Big Data: Home Assignment 3

Код лежит в файле: https://github.com/rodionlatypov/ML_BD/blob/HA3/LinearRegression.sc

Данные брал отсюда: https://www.kaggle.com/kumarajarshi/life-expectancy-who/version/1

Использовал только две фичи (adult mortality и GDP), но код не ограничивает кол-во фитчей. 

Функция fit возвращает оценки коэффициентов регрессии (первый коэффициент - intercept). Функия predict - оцененные целевые переменные по X-ам и коэффициентам. 

Результаты выглядят следующим образом (посчитал R-squared - меру фита - для train и test выборок как подтверждение, что fit и predict работают и получается примерно одинаковый результат для train и test выборок): https://github.com/rodionlatypov/ML_BD/blob/HA3/results.txt


**R-squared on a train set, 0.49592735676330435**

**R-squared on a test set, 0.518582359056066**


train.csv https://github.com/rodionlatypov/ML_BD/blob/HA3/train.csv
test.csv https://github.com/rodionlatypov/ML_BD/blob/HA3/test.csv
