# Тестовое задание для MrSoft.by


## Запуск приложения на локальной машине
```
	git clone https://github.com/pkukharenka/mrSoft.git
	cd mr-test
	./mvnw spring-boot:run
```

Далее можно получить доступ к приложению по адресу: http://localhost:8080/

## Database configuration

По умолчанию тестовое приложение использует базу данных в памяти (H2), которая
заполняется при запуске данными из фалйла ./resources/data.sql. 


## Note

Для функционирования второй задачи (Second Task) необходимо наличие плагина CORS, т.к.
сервер не поддерживает CrossOrigin.