
```
 ______       _____________        ____________________       ____
|      |     |             |      |                    |     |    |
| User | --> | HTTP Server | -->  | Application Server | --> | DB |
|______|     |_____________|      |____________________|     |____|
```

**HTTP (Web) сервер (Nginx, Apache HTTPD)**
1) первым принимает запросы от пользователей;
2) отдает и управляет статическим контентом - картинки, видео, стили, странички;
3) может перенаправить запрос на Application Server;
4) может играть роль Load Balancer'а;
5) может кешировать ответы, чтобы каждый раз на один запрос не дергать Java приложение;
6) поддерживает только Servlets и JSP;
7) поддерживает SSL и HTTPS.

**Application Server**
1) предоставляет клиенту "доступ" к бизнес-логике используя различные протоколы, включая HTTP;
2) отвечает да динамический контект;
3) поддерживает многопоточность;
4) могут поддерживать SSL, кеширование, маршрутизация и управлять статическим контентом, но не так гибко и быстро как HTTP Server.
5) бывают трех типов:
    * J2EE совместимые - GlassFish, WildFly, WebLogic, WebSphere. Полностью реализуют спецификацию J2EE, включая Servlets API, EJB, Distributed Transactions, JMS.
    * Контейнеры сервлетов - Tomcat, Jetty, Undertow. Реализуют спецификацию J2EE частично, самое главное - Servlets API.
    * Non-J2EE - Undertow, Play Framework. Реализуют работу с HTTP не через Servlets API, а через собственные интерфейсы.

**Сервлет**

Сервлет - это Java класс, который получает запросы, обрабатывает их и возвращает ответ. За отправку запроса к определенному сервлету отвечает Application Server (контейнер сервлетов). Application Server отвечает за получение и обработку самого HTTP запроса на уровне сети, тогда как сам сервлет реализует бизнес-логику. То, что именно должен делать контейнер сервлетов, определяется спецификацией Java Servlet Specification. Сервлет создается и умирает вместе с контейнером.  
В самом сервлете описывается поведение в переопределенных методах: init(), doGet(), doPost(), destroy() и тд.


Клиент отправляет HTTP запрос на HTTP Server -->  
HTTP Server перенаправляет запрос на Application Server ->>  
Application Server конвертирует запрос в объект, с которым умеет работать сервлет ->>  
Application Server создает новый поток для каждого запроса ->>  
Запрос обрабатывается в определенном методе (doGet(), doPost()) ->>  
Сервлет возвращает объект на Application Server ->>  
Application Server конвертирует объект в HTTP респонс ->>  
Application Server возвращает респонс клиенту.

**web.xml**

Дескриптор web.xml нужен для того, чтобы контейнер сервлетов знал, на какой именно сервлет нужно направить запрос: <servlet-mapping> указывается url паттерн и имя сервлета.  

**Filter**

Servlet Filter это подлключаемый компонент, который может перехватывать HTTP запросы и обрабатывать их каким-то образом перед отправкой на сервлет, а так же обрабатывать ответы перед отправкой на контейнер:  
* аутентификация
* формирование тела запроса или хедера
* сжатие данные
* изменение кодировки и тд  

Файлы сервлетов настраиваются и подключаются в том же дескрипторе web.xml. Можно иметь несколько фильтров для одного ресурса, можно создавать цепочки фильтров ([Chain of Responsibility](https://www.journaldev.com/1617/chain-of-responsibility-design-pattern-in-java)). В классе фильтра предоставляется реализация для переопределенных методов: init(), doFilter(), destroy(). Порядок вызова фильтров определяется исходя из конфигурации дескриптора, при этом сначала обрабатываются url паттерны сервлетов, а потом их имена.


**Listener**

[Servlet Listener guide](https://www.journaldev.com/1945/servletcontextlistener-servlet-listener-example)

Это классы, которые могут ловить ивенты: сессия создана или удалена, добавлены или удалены аттрибуты сессии:  
```
AsyncListener  
ServletContextListener  
ServletContextAttributeListener  
ServletRequestListener  
ServletRequestAttributeListener  
HttpSessionListener  
HttpSessionAttributeListener  
HttpSessionBindingListener  
HttpSessionActivationListener
```

События:
* события уровня приложения (контекста) - ServletContextListener, ServletContextAttributeListener, etc;
* события уровня сессии - HttpSessionListener, HttpSessionAttributeListener, etc..  

На каждом из этих уровней можно работать с двумя типами событий:
* изменения жизненного цикла - ServletContextListener, HttpSessionListener, etc.;
* изменений аттрибутов - HttpSessionListener, HttpSessionAttributeListener, etc..




